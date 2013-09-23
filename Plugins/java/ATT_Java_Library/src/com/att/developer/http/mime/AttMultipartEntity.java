package com.att.developer.http.mime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicHeader;

public class AttMultipartEntity extends AbstractHttpEntity {
	public static final String PUBLIC_CONTENT_TYPE = "multipart/form-data";
	
	private static final String TWO_HYPHENS = "--";
	private static final String CR_LF = "\r\n";
	
	private String boundary;
	
	protected AttBodyPartMain mainPart;
	protected List<AttBodyPart> parts;
	
	private int numPartsHandled = 0;
	
	private int separatorLength;
	private long contentLength = 0;
	private boolean repeatable = true;
	
	
	public AttMultipartEntity() {
		super();
		parts = new ArrayList<AttBodyPart>();
		boundary = "boundary" + ("" + Math.random()).substring(2);
		
		int basicSeparatorLength = TWO_HYPHENS.length() + boundary.length() + CR_LF.length();
		
		//Set content length to the end separator length
		contentLength = basicSeparatorLength + TWO_HYPHENS.length();
		separatorLength = basicSeparatorLength + CR_LF.length();
		
		this.setContentType(PUBLIC_CONTENT_TYPE);
	}
	
	public String getBoundary() {
		return boundary;
	}
	
	public void setMainPart(AttBodyPartMain mainPart) {
		//NOTE: If a main part is set, it changes the Multipart/Related based on RFC2387
		this.setContentType("multipart/related; start=\"" + AttBodyPartMain.MAIN_PART_START + 
				"\"; type=\"" + mainPart.getContentType() + "\"");
		
		if(this.mainPart == null) {
			contentLength += separatorLength;
		} else {
			contentLength -= this.mainPart.getContentLength();
		}
		contentLength += mainPart.getContentLength();
		
		this.mainPart = mainPart;
	}
	
	public void addPart(AttBodyPart p) {
		if(p == null) return;
		
		if(p instanceof AttBodyPartMain) {
			setMainPart((AttBodyPartMain) p);
		} else {
			parts.add(p);
		}
	}
	
	/*
	private InputStream stringToInputStream(String in) throws UnsupportedEncodingException {
		return new ByteArrayInputStream((in).getBytes("UTF-8"));
	}
	*/

	public InputStream getContent() throws IOException {
		/*
		Vector<InputStream> streams = new Vector<InputStream>();
		List<AttBodyPart> allParts = getAllParts();
		
		for(AttBodyPart attachment: allParts) {
			streams.add(stringToInputStream(TWO_HYPHENS + boundary + CR_LF));
			streams.add(attachment.getContent());
			streams.add(stringToInputStream(CR_LF);
		}
		
		streams.add(stringToInputStream(TWO_HYPHENS + boundary + TWO_HYPHENS + CR_LF));
		
		return new SequenceInputStream(streams.elements());
		*/
		throw new UnsupportedOperationException(
				"Multipart form entity does not implement #getContent()");
	}
	

	private void handleAllParts() {
		if(numPartsHandled == parts.size()) return;
		
		for(int i = numPartsHandled; i < parts.size(); i++) {
			AttBodyPart part = parts.get(i);
			
			contentLength += part.getContentLength() + separatorLength;
			repeatable = repeatable && part.isRepeatable();
		}
		
		numPartsHandled = parts.size();
	}

	public long getContentLength() {
		handleAllParts();
		return contentLength;
	}

	public boolean isRepeatable() {
		handleAllParts();
		return repeatable;
	}

	public boolean isStreaming() {
		return !isRepeatable();
	}
	
	private List<AttBodyPart> getAllParts() {
		if(mainPart == null)  return parts;
		
		List<AttBodyPart> allParts = new ArrayList<AttBodyPart>(parts);
		allParts.add(0, mainPart);
		return allParts;
	}

	public void writeTo(OutputStream out) throws IOException {
		PrintStream ps = new PrintStream(out);
		
		List<AttBodyPart> allParts = getAllParts();
		
		for(AttBodyPart attachment:  allParts) {
			ps.print(TWO_HYPHENS + boundary + CR_LF);
			attachment.writeTo(out);
			ps.print(CR_LF);
		}
		
		ps.print(TWO_HYPHENS + boundary + TWO_HYPHENS + CR_LF);
	}
	
	private String addBoundary(String ctValue) {
		ctValue = ctValue.replaceAll(";\\s*boundary=\".*?\"", ""); //Remove previous boundary
		return ctValue + "; boundary=\"" + boundary + "\"";
	}
	
	public void setContentType(String ctString) {
		if(ctString == null) {
			super.setContentType(ctString);
			return;
		}
		
		super.setContentType(addBoundary(ctString));
	}
	
	public void setContentType(Header contentType) {
		if(contentType == null || contentType.getValue() == null) {
			super.setContentType(contentType);
			return;
		}
		
		super.setContentType(new BasicHeader(
				contentType.getName(), addBoundary(contentType.getValue())
		));
	}
}