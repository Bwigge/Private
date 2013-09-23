package com.att.developer.http.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.http.AttGet;
import com.att.developer.http.AttPost;
import com.att.developer.http.AttRequest;
import com.att.developer.http.AttResponse;
import com.att.developer.http.AttRequest.HeaderTitles;
import com.att.developer.http.AttRequest.ParameterConfig;

public class AttSMS {
	protected static final String MESSAGE_WRAPPER = "outboundSMSRequest";
	
	public static final String PATH = "/sms/v3/messaging";
	
	private AttPost sendSMSReq;
	private AttGet getSMSStatusReq;
	private AttGet getSMSReq;
	
	
	public AttSMS() {
		//Set up request basics for sendMMS
		Map<String, ParameterConfig> sendSMSHeaderConfig = new HashMap<String, ParameterConfig>();
		sendSMSHeaderConfig.put(HeaderTitles.ACCEPT, new ParameterConfig.Optional());
		sendSMSHeaderConfig.put(HeaderTitles.CONTENT_TYPE, new ParameterConfig.Required());
		
		sendSMSReq = new AttPost(PATH + "/outbox", sendSMSHeaderConfig);
		
		
		//Set up request basics for getMMSStatus
		Map<String, ParameterConfig> smsGetReqsHeaderConfig = new HashMap<String, ParameterConfig>();
		smsGetReqsHeaderConfig.put(HeaderTitles.ACCEPT, new ParameterConfig.Optional());
		
		getSMSStatusReq = new AttGet(PATH + "/outbox/", smsGetReqsHeaderConfig);
		getSMSReq = new AttGet(PATH + "/inbox/", smsGetReqsHeaderConfig);
	}
	
	public class AttSendSMSResponse extends AttResponse {
		private static final String RESPONSE_WRAPPER = "outboundSMSResponse";
		private String messageId;
		private String resourceURL;
		
		public AttSendSMSResponse(AttResponse resp) throws AttException {
			super(resp);
			
			JSONObject respObj = getResponseJSONObject();
			try {
				JSONObject msgWrapper = respObj.getJSONObject(RESPONSE_WRAPPER);
				messageId = msgWrapper.getString("messageId");
				resourceURL = msgWrapper.getJSONObject("resourceReference").getString("resourceURL");
			} catch(Exception e) {
				throw new AttException(e);
			}
		}
		
		public String getMessageId() {
			return messageId;
		}
		
		public String getResourceURL() {
			return resourceURL;
		}
	}
	
	public AttSendSMSResponse sendSMS(JSONObject args) throws AttException {
		//JSONObject result;
		Map<String, String> headers = AttRequest.jsonToMap(args.optJSONObject("headers"));
		String body = null;
		
		//TODO: Do some similar logic with MMS
		if(headers.get(HeaderTitles.CONTENT_TYPE).contains("application/json")) {
			JSONObject smsDetails = args.optJSONObject("body");
			if(smsDetails != null) {
				JSONObject smsDetailsWithWraper = new JSONObject();
				try {
					smsDetailsWithWraper.put(MESSAGE_WRAPPER, smsDetails);
				} catch(Exception e) {
					throw new AttException(e);
				}
				body = smsDetailsWithWraper.toString();
			}
		} 
		
		if(body == null){
			body = args.optString("body", null);
		}
		
		return sendSMS(headers, body);
	}
	
	public AttSendSMSResponse sendSMS(Map<String, String> headers, String body) throws AttException {
		if(body == null) {
			throw new AttException("Missing SMS body to send");
		}
		
		return new AttSendSMSResponse(sendSMSReq.send(headers, body));
	}
	
	public AttResponse getSMSStatus(JSONObject args) throws AttException {
		Map<String, String> headers = AttRequest.jsonToMap(args.optJSONObject("headers"));
		String msgID = args.optString("smsId");
		
		return getSMSStatus(headers, msgID);
	}
	
	public AttResponse getSMSStatus(Map<String, String> headers, String smsId) throws AttException {
		if(smsId == null || smsId.isEmpty()) {
			throw new AttException("Missing argument \"smsId\"");
    	}
		
		return getSMSStatusReq.send(headers, smsId);
	}
	
	public AttResponse getSMS(Map<String, String> headers, String shortCode) throws AttException {
		if(shortCode == null || shortCode.isEmpty()) {
			throw new AttException("Missing argument \"smsId\"");
    	}
		
		return getSMSReq.send(headers, shortCode);
	}
}
