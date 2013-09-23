package com.att.developer.http;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import com.att.developer.AttException;


public class AttResponseFile extends AttResponse {
	private File file;
	private boolean cacheAsString;
	
	public AttResponseFile(AttResponse other, File file, boolean cacheAsString) throws AttException {
		super(other);
		
		this.cacheAsString = cacheAsString;
		this.file = file;
		
		boolean success = (httpResp.getStatusLine().getStatusCode() < 400);
		
		if(!success || cacheAsString) {
			getResponseString(); //saves the string in memory
		}
		
		if(success) {
			writeToFile(file);
		}
	}
	
	public AttResponseFile(AttResponse other, File file) throws AttException {
		this(other, file, false);
	}
	
	public AttResponseFile(AttResponse other, String filePath) throws AttException {
		this(other, new File(filePath));
	}

	public File getFile() {
		return file;
	}
	
	private static String fileToString(File file) throws AttException {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			return Charset.defaultCharset().decode(bb).toString();
		} catch(Exception e) {
			throw new AttException(e);
		} finally {
			try { stream.close(); } catch(Exception ignore) {}
		}
	}
	
	public String getResponseString() throws AttException {
		if(cacheAsString) return super.getResponseString();
		
		return fileToString(file);
	}
}

