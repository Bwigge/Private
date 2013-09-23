package com.att.developer.http.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.http.AttGet;
import com.att.developer.http.AttRequest;
import com.att.developer.http.AttResponse;
import com.att.developer.http.AttRequest.HeaderTitles;
import com.att.developer.http.AttRequest.ParameterConfig;
import com.att.developer.http.mime.AttBodyPart;
import com.att.developer.http.mime.AttPostMultipart;

public class AttIMMN {
	
	public static final String PATH = "/rest/1/MyMessages";
	
	public static final String QUERY_HEADER_COUNT = "HeaderCount";
	public static final String QUERY_INDEX_CURSOR = "IndexCursor";
	
	public static final String JSON_MESSAGE_ID_KEY = "messageId";
	public static final String JSON_PART_NUMBER_KEY = "partNumber";
	
	private AttPostMultipart sendMessageReq;
	private AttGet getMessageHeadersReq;
	private AttGet getMessageContentReq;
	
	public AttIMMN() {
		//Set up request basics for sendMMS
		Map<String, ParameterConfig> sendMessageHeaderConfig = new HashMap<String, ParameterConfig>();
		sendMessageHeaderConfig.put(HeaderTitles.ACCEPT, new ParameterConfig.Optional());
		sendMessageHeaderConfig.put(HeaderTitles.CONTENT_TYPE, new ParameterConfig.Required());
		
		sendMessageReq = new AttPostMultipart(PATH, sendMessageHeaderConfig);
		sendMessageReq.setAuthType(AttRequest.TokenType.USER_AUTHORIZATION);
		
		//Set up request basics for getMMSStatus
		Map<String, ParameterConfig> getMessageDetailsConfig = new HashMap<String, ParameterConfig>();
		getMessageDetailsConfig.put(HeaderTitles.ACCEPT, new ParameterConfig.Optional());
		
		Map<String, ParameterConfig> getMessageQueryConfig = new HashMap<String, ParameterConfig>();
		getMessageQueryConfig.put(QUERY_HEADER_COUNT, new ParameterConfig.Optional()); //TODO: Throw error if larger than 500?
		getMessageQueryConfig.put(QUERY_INDEX_CURSOR, new ParameterConfig.Optional());
		
		
		getMessageHeadersReq = new AttGet(PATH, getMessageDetailsConfig, getMessageQueryConfig);
		getMessageHeadersReq.setAuthType(AttRequest.TokenType.USER_AUTHORIZATION);
		
		getMessageContentReq = new AttGet(PATH + "/", getMessageDetailsConfig);
		getMessageContentReq.setAuthType(AttRequest.TokenType.USER_AUTHORIZATION);
	}
	
	//////////////////////////// SEND MESSAGE METHODS ////////////////////////////
	
	public AttResponse sendMessage(JSONObject params) throws AttException {
		Map<String, String> headers = AttPostMultipart.getHeadersMap(params);
		AttBodyPart[] attachments = AttPostMultipart.bodyPartsFromParams(params);
		JSONObject bodyObj = params.optJSONObject(AttPostMultipart.JSON_BODY);
		if(bodyObj != null) {
			return sendMessage(headers, bodyObj, attachments);
		}
		
		String bodyString = params.optString(AttPostMultipart.JSON_BODY);
		
		return sendMessage(headers, bodyString, attachments);
	}
	
	public AttResponse sendMessage(Map<String, String> headers, String body, AttBodyPart[] attachmentDetails) throws AttException {
		return sendMessageReq.send(headers, body, attachmentDetails);
	}
	
	public AttResponse sendMessage(Map<String, String> headers, JSONObject body, AttBodyPart[] attachmentDetails) throws AttException {
		return sendMessageReq.send(headers, body, attachmentDetails);
	}
	
	
	//////////////////////////// GET MESSAGE HEADERS METHODS ////////////////////////////
	
	public AttResponse getMessageHeaders(JSONObject params) throws AttException {
		return getMessageHeaders(AttGet.getHeadersMap(params), AttGet.getQueryMap(params));
	}
	
	public AttResponse getMessageHeaders(Map<String, String> headers, Map<String, String> queryMap) throws AttException {
		return getMessageHeadersReq.send(headers, null, queryMap);
	}
	
	public AttResponse getMessageHeaders(Map<String, String> headers, int headerCount, String indexCursor) throws AttException {
		
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(QUERY_HEADER_COUNT, "" + headerCount);
		queryMap.put(QUERY_INDEX_CURSOR, indexCursor);
		
		return getMessageHeaders(headers, queryMap);
	}
	
	public AttResponse getMessageHeaders(int headerCount, String indexCursor) throws AttException {
		return getMessageHeaders(null, headerCount, indexCursor);
	}
	
	
	//////////////////////////// GET MESSAGE CONTENT METHODS ////////////////////////////
	
	public AttResponse getMessageContent(JSONObject params) throws AttException {
		return getMessageContent(AttGet.getHeadersMap(params), 
				params.optString(JSON_MESSAGE_ID_KEY), params.optString(JSON_PART_NUMBER_KEY));
	}
	
	public AttResponse getMessageContent(Map<String, String> headers, String messageId, String partNumber) throws AttException {
		String appendURL = "/" + messageId + "/" + partNumber;
		
		return getMessageContentReq.send(headers, appendURL);
	}
}
