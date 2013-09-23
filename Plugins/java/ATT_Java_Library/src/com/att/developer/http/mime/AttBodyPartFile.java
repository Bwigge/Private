package com.att.developer.http.mime;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;

import org.apache.http.entity.FileEntity;
import org.apache.http.message.BasicHeader;

import com.att.developer.AttUtil;
import com.att.developer.http.AttRequest;

public class AttBodyPartFile extends AttBodyPart {
	protected File file;
	protected String fileName;
	
	private static FileNameMap fileNameMap;
	static {
		fileNameMap = URLConnection.getFileNameMap();
	}
	
	//This function formats the data
	private static AttBodyPart makeAttBodyPart(File file, String contentType, String name) {
		
		if(AttUtil.isEmpty(name)) {
			name = file.getName();
		}
		
		if(AttUtil.isEmpty(contentType)) {
			contentType = fileNameMap.getContentTypeFor(name);
		}
		
		return new AttBodyPart(name, new ExposedFileEntity(file, contentType));
	}
	
	public AttBodyPartFile(File file, String contentType, String name, String fileName) {
		super(makeAttBodyPart(file, contentType, name));
		
		if(fileName == null) this.fileName = file.getName();
		else this.fileName = fileName;
		
		formatFileContentDisposition(this, getFileName());
		file = ((ExposedFileEntity) content).getFile();
	}
	
	public AttBodyPartFile(File file, String contentType, String name) {
		this(file, contentType, name, null);
	}
	
	public AttBodyPartFile(String filePath) {
		this(new File(filePath));
	}
	
	public AttBodyPartFile(File file) {
		this(file, null, null);
	}
	
	public AttBodyPartFile(String filePath, String contentType, String name, String fileName) {
		this(new File(filePath), contentType, name, fileName);
	}
	
	public AttBodyPartFile(String filePath, String contentType, String name) {
		this(filePath, contentType, name, null);
	}
	
	public String getFileName() {
		if(!AttUtil.isEmpty(fileName)) return fileName;
		return getName();
	}

	//Private class that exposes the file Object
	private static class ExposedFileEntity extends FileEntity {
		
		ExposedFileEntity(File file, String contentType) {
			super(file, contentType);
			setContentEncoding(new BasicHeader(AttRequest.HeaderTitles.CONTENT_TRANSFER_ENCODING, "binary"));
		}
		
		public File getFile() {
			return file;
		}
	}
}
