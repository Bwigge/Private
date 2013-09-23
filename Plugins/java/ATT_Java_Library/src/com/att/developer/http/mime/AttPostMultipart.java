package com.att.developer.http.mime;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.http.AttPost;
import com.att.developer.http.AttRequest;
import com.att.developer.http.AttResponse;

public class AttPostMultipart extends AttPost {
	
	public static final String JSON_ATTACHMENTS = "attachments";
	
	public AttPostMultipart(String path, Map<String, ParameterConfig> headerConfig) {
		super(path, headerConfig);
		//this.headerConfig.put("MIME-Version", new ParameterConfig.Backup("1.0"));
	}
	
	//Methods for sending multi-part mime message
	public AttResponse send(Map<String, String> headers, AttBodyPart[] attachments) throws AttException {
		AttMultipartEntity mimeEntity = new AttMultipartEntity();
		
		for(int i = 0; i < attachments.length; i++) {
			if(attachments[i] != null) mimeEntity.addPart(attachments[i]);
		}
		
		Map<String, String> myHeaders = new HashMap<String, String>(headers);
        String contentType = myHeaders.remove(AttRequest.HeaderTitles.CONTENT_TYPE); //This will be handled by the mimeEntity
        if(contentType != null) mimeEntity.setContentType(contentType);
		
		return send(myHeaders, mimeEntity);
	}
	
	protected AttResponse send(Map<String, String> headers, AttBodyPartMain mainPart, AttBodyPart[] attachments) throws AttException {
		AttMultipartEntity mimeEntity = new AttMultipartEntity();
		
		mimeEntity.setMainPart(mainPart);
		
		for(int i = 0; i < attachments.length; i++) {
			mimeEntity.addPart(attachments[i]);
		}
		
        Map<String, String> myHeaders = new HashMap<String, String>(headers);
        myHeaders.put(AttRequest.HeaderTitles.CONTENT_TYPE, mimeEntity.getContentType().getValue());
		
		return send(myHeaders, mimeEntity);
	}
	
	public AttResponse send(Map<String, String> headers, String mainPartContent, AttBodyPart[] attachments) throws AttException {
		if(!headers.containsKey(HeaderTitles.CONTENT_TYPE)) {
			throw new AttException("Missing Content-Type header that defines content of the main MIME part");
		}
		
		AttBodyPartMain mainPart = (mainPartContent == null) ?
				null : new AttBodyPartMain(mainPartContent, headers.get(HeaderTitles.CONTENT_TYPE));
		
		return send(headers, mainPart, attachments);
	}
	
	public AttResponse send(Map<String, String> headers, JSONObject mainPartContent, AttBodyPart[] attachments) throws AttException {
		AttBodyPartMain mainPart = (mainPartContent == null) ? null : new AttBodyPartMain(mainPartContent);
		
		return send(headers, mainPart, attachments);
	}
	
	public AttResponse send(Map<String, String> headers, JSONObject mainPartContent, JSONArray attachments) throws AttException {
		return send(headers, mainPartContent, bodyPartsFromJSONArray(attachments));
	}
	
	public static AttBodyPart[] bodyPartsFromJSONArray(JSONArray attachmentsArr) throws AttException {
		int numAttachments = (attachmentsArr == null) ? 0 : attachmentsArr.length();
		
		AttBodyPart[] attachments = new AttBodyPart[numAttachments];
		for(int i = 0; i < numAttachments; i++) {
			JSONObject fileDetailObject = attachmentsArr.optJSONObject(i);
			if(fileDetailObject != null && fileDetailObject.length() > 0) {
				attachments[i] = AttBodyPart.convert(fileDetailObject);
			}
		}
		
		return attachments;
	}
	
	public static AttBodyPart[] bodyPartsFromParams(JSONObject params) throws AttException {
		return bodyPartsFromJSONArray(params.optJSONArray(JSON_ATTACHMENTS));
	}
}
