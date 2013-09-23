package com.att.developer.http.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.AttUtil;
import com.att.developer.http.AttGet;
import com.att.developer.http.AttRequest;
import com.att.developer.http.AttResponse;
import com.att.developer.http.AttRequest.HeaderTitles;
import com.att.developer.http.AttRequest.ParameterConfig;
import com.att.developer.http.mime.AttBodyPart;
import com.att.developer.http.mime.AttPostMultipart;

public class AttMMS {
	private AttPostMultipart sendMMSReq;
	private AttGet getMMSStatusReq;
	
	protected static final String MESSAGE_WRAPPER = "outboundMessageRequest";
	public static final String PATH = "/mms/v3/messaging/outbox";
	
	public AttMMS() {
		//Set up request basics for sendMMS
		Map<String, ParameterConfig> sendMMSHeaderConfig = new HashMap<String, ParameterConfig>();
		sendMMSHeaderConfig.put(HeaderTitles.ACCEPT, new ParameterConfig.Optional());
		sendMMSHeaderConfig.put(HeaderTitles.CONTENT_TYPE, new ParameterConfig.Required());
		
		sendMMSReq = new AttPostMultipart(PATH, sendMMSHeaderConfig);
		
		
		//Set up request basics for getMMSStatus
		Map<String, ParameterConfig> getMMSStatusHeaderConfig = new HashMap<String, ParameterConfig>();
		getMMSStatusHeaderConfig.put(HeaderTitles.ACCEPT, new ParameterConfig.Optional());
		
		getMMSStatusReq = new AttGet(PATH + "/", getMMSStatusHeaderConfig);
	}
	
	public class AttSendMMSResponse extends AttResponse {
		private static final String RESPONSE_WRAPPER = "outboundMessageResponse";
		private String messageId;
		private String resourceURL;
		private boolean success = true;
		
		public AttSendMMSResponse(AttResponse resp) throws AttException {
			super(resp);
			
			JSONObject respObj = getResponseJSONObject();
			try {
				JSONObject msgWrapper = respObj.getJSONObject(RESPONSE_WRAPPER);
				messageId = msgWrapper.getString("messageId");
				resourceURL = msgWrapper.getJSONObject("resourceReference").getString("resourceURL");
			} catch(Exception e) {
				//throw new AttException(e);
				success = false;
			}
		}
		
		public boolean hadSuccess() {
			return success;
		}
		
		public String getMessageId() {
			return messageId;
		}
		
		public String getResourceURL() {
			return resourceURL;
		}
	}
	
	public AttSendMMSResponse sendMMS(Map<String, String> headers, String body, AttBodyPart[] attachments) throws AttException {
		
		return new AttSendMMSResponse(sendMMSReq.send(headers, body, attachments));
	}
	
	public AttSendMMSResponse sendMMS(JSONObject params) throws AttException {
		//JSONObject result;
		Map<String, String> headers = AttRequest.getHeadersMap(params);
		AttBodyPart[] attachments = AttPostMultipart.bodyPartsFromParams(params);
		JSONObject bodyObj = params.optJSONObject(AttPostMultipart.JSON_BODY);
		
		//System.out.println("params: " + params.toString());
		
		AttResponse resp;
		if(bodyObj != null) {
			JSONObject bodyWrapper = new JSONObject();
			try {
				bodyWrapper.put(MESSAGE_WRAPPER, bodyObj);
			} catch (Exception e) {
				throw new AttException(e);
			}
			
			resp = sendMMSReq.send(headers, bodyWrapper, attachments);
		} else {
			String bodyString = params.optString(AttPostMultipart.JSON_BODY);
			resp = sendMMSReq.send(headers, bodyString, attachments);
		}	
		
		return new AttSendMMSResponse(resp);
	}
	
	public AttResponse getMMSStatus(JSONObject params) throws AttException {
		Map<String, String> headers = AttRequest.jsonToMap(params.optJSONObject("headers"));
		String msgID = params.optString("id");
		
		return getMMSStatus(headers, msgID);
	}
	
	public AttResponse getMMSStatus(Map<String, String> headers, String id) throws AttException {
		if(AttUtil.isEmpty(id)) {
			throw new AttException("Missing argument \"id\"");
    	}
		
		return getMMSStatusReq.send(headers, id);
	}
}
