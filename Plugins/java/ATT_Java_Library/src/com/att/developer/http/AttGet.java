package com.att.developer.http;

import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import com.att.developer.AttException;

public class AttGet extends AttRequest {
	
	public AttGet(String path, Map<String, ParameterConfig> headerConfig) {
		super(path, headerConfig);
	}
	
	public AttGet(String path, Map<String, ParameterConfig> headerConfig, Map<String, ParameterConfig> queryStringConfig) {
		super(path, headerConfig, queryStringConfig);
	}
	
	/*
	protected AttResponse execute(HttpGet get) throws AttException {
		try {
			printRequest(get);
		} catch(Exception e) {
			throw new AttException(e);
		}
		
		return super.execute(get);
	}
	*/
	
	//This is the constructor for MimePosts
	public AttResponse send(Map<String, String> headers, String urlAppend, Map<String, String> queryStringParams) throws AttException {
		HttpGet req = new HttpGet(route + (urlAppend == null ? "" : urlAppend) + getQueryString(queryStringParams));
        addHeaders(headers, req);
		
		return execute(req);
	}
	
	public AttResponse send(Map<String, String> headers, String urlAppend) throws AttException {
		return send(headers, urlAppend, null);
	}
	
	public AttResponse send(JSONObject headers, String urlAppend) throws AttException {
		return send(jsonToMap(headers), urlAppend);
	}
}
