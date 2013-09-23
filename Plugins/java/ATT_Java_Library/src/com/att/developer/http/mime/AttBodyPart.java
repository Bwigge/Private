/**
 * This class adds a wrapper with headers to an HttpEntity
 */

package com.att.developer.http.mime;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.AttUtil;
import com.att.developer.http.AttPost;
import com.att.developer.http.AttRequest;

public class AttBodyPart {
	
	public static final class AttachmentProperties {
		public static final String JSON_BODY = AttPost.JSON_BODY;
		public static final String JSON_FILE_PATH = AttPost.JSON_FILE_PATH;
		public static final String JSON_FILE_NAME = "fileName";
		public static final String JSON_MIME_TYPE = "mimeType";
		public static final String JSON_NAME = "name";
		public static final String JSON_ENCODING = "encoding";
	}
	
	private Map<String, String> headers;
	private String name;
	protected HttpEntity content;
	
	private static final String CR_LF = "\r\n";
	private static final String HEADER_SEPERATOR = ": ";
	
	public AttBodyPart(AttBodyPart other) {
		headers = new HashMap<String, String>(other.headers);
		this.name = other.name;
		this.content = other.content;
	}
	
	public AttBodyPart(String name, HttpEntity content) {
		this.name = name;
		this.content = content;
		
		headers = new HashMap<String, String>();
		String contentDisp = "form-data; name=\"" + this.name + "\"";
		setHeader(AttRequest.HeaderTitles.CONTENT_DISPOSITION, contentDisp);
	}
	
	public static void formatFileContentDisposition(AttBodyPart abp, String fileName) {
		String contentDisp = abp.getHeaderValue(AttRequest.HeaderTitles.CONTENT_DISPOSITION);
		//contentDisp = contentDisp.replace("form-data", "attachment");
		contentDisp += "; filename=\"" + fileName + "\"";
		abp.setHeader(AttRequest.HeaderTitles.CONTENT_DISPOSITION, contentDisp);
		abp.setHeader(AttRequest.HeaderTitles.CONTENT_ID, fileName);
	}
	
	private static String generateHeaderLine(String title, String value) {
		if(AttUtil.isEmpty(title) || AttUtil.isEmpty(value)) return "";
		return title + HEADER_SEPERATOR + value + CR_LF;
	}
	
	public static String generateHeaders(Map<String, String> headers) {
		String headerString = "";
		for(Map.Entry<String, String> header: headers.entrySet()) {
			headerString += generateHeaderLine(header.getKey(),  header.getValue());
		}
		headerString += CR_LF;
		
		return headerString;
	}
	
	private Map<String, String> formatHeader() {
		Map<String, String> myHeaders = new HashMap<String, String>(headers);
		AttUtil.addHeaderToMapIfNeeded(content.getContentType(), myHeaders);
		AttUtil.addHeaderToMapIfNeeded(content.getContentEncoding(), myHeaders);
		
		return myHeaders;
	}
	
	private String getHeaderString() {
		return generateHeaders(formatHeader());
	}
	
	public boolean setHeader(String headerName, String headerVal) {
		if(AttUtil.isEmpty(headerName) || 
				AttUtil.isEmpty(headerVal)) return false;
		
		headers.put(headerName, headerVal);
		
		return true;
	}
	
	public boolean setHeader(Header h) {
		if(h == null) return false;
		
		setHeader(h.getName(), h.getValue());
		
		return true;
	}
	
	private String getHeaderValue(Header h, String name) {
		if(h == null || !h.getName().equalsIgnoreCase(name)) return null;
		
		return h.getValue();
	}
	
	public String getHeaderValue(String headerName) {
		String val = getHeaderValue(content.getContentType(), headerName);
		if(val != null) return val;
		
		val = getHeaderValue(content.getContentEncoding(), headerName);
		if(val != null) return val;
		
		return headers.get(headerName);
	}
	
	public boolean isRepeatable() {
		return content.isRepeatable();
	}
	
	public void writeTo(OutputStream out) throws IOException {
		PrintStream ps = new PrintStream(out);
		ps.print(getHeaderString());
		content.writeTo(out);
	}
	
	public String getName() {
		return name;
	}
	
	public long getContentLength() {
		return getHeaderString().length() + content.getContentLength();
	}
	
	public String getContentType() {
		return getHeaderValue(AttRequest.HeaderTitles.CONTENT_TYPE);
	}
	
	public static AttBodyPart convert(JSONObject obj) throws AttException {
		boolean hasBody = obj.has(AttachmentProperties.JSON_BODY);
		boolean hasFilePath = obj.has(AttachmentProperties.JSON_FILE_PATH);
		
		if(hasBody && hasFilePath) {
			throw new AttException("Attachments can only have either \"" + 
					AttachmentProperties.JSON_BODY + "\" or \"" +
					AttachmentProperties.JSON_FILE_PATH + "\"");
		}
		
		if(hasBody) {
			//TODO Set transfer encoding to be base64? add a parameter for that?
			String name = obj.optString(AttachmentProperties.JSON_NAME);
			AttBodyPart abp = new AttBodyPartString(
				obj.optString(AttachmentProperties.JSON_BODY),
				obj.optString(AttachmentProperties.JSON_MIME_TYPE),
				name,
				obj.optString(AttachmentProperties.JSON_ENCODING)
			);
			
			if(obj.has(AttachmentProperties.JSON_FILE_NAME)) {
				AttBodyPartFile.formatFileContentDisposition(abp, obj.optString(AttachmentProperties.JSON_FILE_NAME));
			}
			
			return abp;
		} else if(hasFilePath) {
			return new AttBodyPartFile(
				obj.optString(AttachmentProperties.JSON_FILE_PATH),
				obj.optString(AttachmentProperties.JSON_MIME_TYPE),
				obj.optString(AttachmentProperties.JSON_NAME),
				obj.optString(AttachmentProperties.JSON_FILE_NAME)
			);
		}
		
		return null;
	}
}
