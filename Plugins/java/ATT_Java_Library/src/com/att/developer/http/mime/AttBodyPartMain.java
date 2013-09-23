package com.att.developer.http.mime;

import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.http.AttRequest;

public class AttBodyPartMain extends AttBodyPartString {
	public static final String MAIN_PART_NAME = "root-fields";
	public static final String MAIN_PART_START = "<start>";
	
	AttBodyPartMain(String stringContent, String contentType) throws AttException {
		super(stringContent, contentType, MAIN_PART_NAME);
		setHeader(AttRequest.HeaderTitles.CONTENT_ID, MAIN_PART_START);
	}
	
	AttBodyPartMain(JSONObject obj) throws AttException {
		this(obj.toString(), "application/json");
	}
}
