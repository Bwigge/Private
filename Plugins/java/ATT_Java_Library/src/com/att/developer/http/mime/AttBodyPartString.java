package com.att.developer.http.mime;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.att.developer.AttException;
import com.att.developer.http.AttRequest;

public class AttBodyPartString extends AttBodyPart {
	private String contentString;
	
	//This function formats the data
	private static AttBodyPart makeAttBodyPart(String contentString, String contentType, String name) throws AttException {
		if(contentType == null) {
			contentType = "text/plain";
		}
		
		try {
			return new AttBodyPart(name, new ExposedStringEntity(contentString, contentType));
		} catch(Exception e) {
			throw new AttException(e);
		}
	}
	
	public AttBodyPartString(String contentString, String contentType, String name, String encoding) throws AttException {
		super(makeAttBodyPart(contentString, contentType, name));
		ExposedStringEntity myContent = (ExposedStringEntity) content;
		contentString = myContent.getString();
		
		//setHeader(content.getContentEncoding());
		
		if(encoding != null) {
			myContent.setContentEncoding(encoding);
		}
	}
	
	public AttBodyPartString(String contentString, String contentType, String name) throws AttException {
		this(contentString, contentType, name, null);
	}
	
	public String getContentString() {
		return contentString;
	}
	
	private static class ExposedStringEntity extends StringEntity {
		ExposedStringEntity(String contentString, String contentType) throws UnsupportedEncodingException {
			super(contentString);
			setContentType(contentType);
			setContentEncoding("8bit");
		}
		
		public String getString() {
			return new String(content);
		}
		
		public void setContentEncoding(String ceString) {
			super.setContentEncoding(new BasicHeader(AttRequest.HeaderTitles.CONTENT_TRANSFER_ENCODING, ceString));
		}
	}
}
