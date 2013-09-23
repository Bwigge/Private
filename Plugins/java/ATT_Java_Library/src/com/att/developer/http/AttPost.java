package com.att.developer.http;

import java.io.File;
import java.io.PrintStream;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.AttUtil;

public class AttPost extends AttRequest {
	
	public static final String JSON_BODY = "body";
	public static final String JSON_FILE_PATH = "filePath";
	public static final String JSON_ATTACHMENTS = "attachments";
	
	public AttPost(String path, Map<String, ParameterConfig> headerConfig) {
		super(path, headerConfig);
	}
	
	/*
	//Expose the queryString options when needed
	public AttPost(String path, Map<String, ParameterConfig> headerConfig, Map<String, ParameterConfig> queryStringConfig) {
		super(path, headerConfig, queryStringConfig);
	}
	*/
	
	/*
	//TODO:
	1.  Add support for creating an entity and saving then sending that
	2.  Seperate out the single and multi part requests (extend the AttPost to AttPostMultiPart)
		a.	Create a way to pack up the "body" as the first part of the request
		b.  Create a way to pass in file configs and send the files as the parts
		b.  Create a way to send other things (other than just files) as the parts
	*/
	
	
	//This is the constructor for MimePosts
	
	
	public AttResponse send(Map<String, String> headers, String body, String contentType) throws AttException {
		StringEntity reqEntity;
		try {
			reqEntity = new StringEntity(body);
			reqEntity.setContentType(contentType);
		} catch(Exception e) { throw new AttException(e); }
		
		return send(headers, reqEntity);
	}
	
	public AttResponse send(Map<String, String> headers, String body) throws AttException {
		return send(headers, body, headers.get(HeaderTitles.CONTENT_TYPE));
	}
	
	public AttResponse send(Map<String, String> headers, JSONObject body) throws AttException {
		return send(headers, body.toString(), "application/json");
	}
	
	//Methods for sending a file
	
	public AttResponse send(Map<String, String> headers, File file, boolean chunked) throws AttException {
		return send(headers, file, null, chunked);
	}
	
	public AttResponse send(Map<String, String> headers, File file, String encoding, boolean chunked) throws AttException {
		FileEntity reqEntity = new FileEntity(file, encoding);
		reqEntity.setContentType(encoding);
		reqEntity.setChunked(chunked);
		
		return send(headers, reqEntity);
	}
	
	//This is the base send method for sending posts
	protected AttResponse send(Map<String, String> headers, HttpEntity body) throws AttException {
		HttpPost post = new HttpPost(route);
		addHeaders(headers, post);
		post.setEntity(body);
		Header[] contentTypes = post.getHeaders(HeaderTitles.CONTENT_TYPE);
		
		if((contentTypes == null || contentTypes.length == 0) &&
				(body.getContentType() == null || body.getContentType().getValue() == null))
		{
			throw new AttException("Content-Type header is required for POSTing a String of data");
		}
		
		AttResponse resp = execute(post);
		
		//TODO: find an alternative to consume that works on android
		//try { EntityUtils.consume(body); } catch(Exception ignore) {}
		
		return resp;
	}
	
	
	protected AttResponse execute(HttpPost post) throws AttException {
		if(AttUtil.DEBUG) printRequest(post);
		return super.execute(post);
	}
	
	
	//Methods for debugging
	protected void printRequest(HttpPost post) throws AttException {
		printRequest(System.out, post);
	}
	
	protected void printRequest(PrintStream out, HttpPost post) throws AttException {
		try {
			super.printRequest(out, post);
			out.println();
			post.getEntity().writeTo(out);
			out.println();
		} catch(Exception e) { throw new AttException(e); }
	}
}
