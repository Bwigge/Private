package com.att.developer.http.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.AttUtil;
import com.att.developer.http.AttPost;
import com.att.developer.http.AttRequest;
import com.att.developer.http.AttResponse;
import com.att.developer.http.AttResponseFile;
import com.att.developer.http.AttRequest.HeaderTitles;
import com.att.developer.http.AttRequest.ParameterConfig;

public class AttSpeech {
	protected static String PATH = "/speech/v3/";
	
	public static final String JSON_FILE_PATH = "filePath";
	
	public static interface SpeechContext {
		public static final String GENERIC = "Generic";
		public static final String GAMING = "Gaming";
		public static final String BUSINESS = "Business Search";
		public static final String WEB_SEARCH = "Web Search";
		public static final String SMS = "SMS";
		public static final String VTT = "Voicemail to text";
		public static final String QNA = "Question & Answer";
		public static final String TV = "TV";
		public static final String SOCIALMEDIA = "SocialMedia";
	}
	
	public static interface LanguageContent {
		public static final String EN_US = "en-US";
		public static final String ES_US = "es-US";
	}
	
	private AttPost speechToTextReq;
	private AttPost textToSpeechReq;
	
	public AttSpeech() {
		
		Map<String, ParameterConfig> sstHeaderConfig = new HashMap<String, ParameterConfig>();
		sstHeaderConfig.put(HeaderTitles.ACCEPT, new ParameterConfig.Optional());
		sstHeaderConfig.put(HeaderTitles.CONTENT_TYPE, new ParameterConfig.Required());
		sstHeaderConfig.put(HeaderTitles.TRANSFER_ENCODING, new ParameterConfig.Optional());
		sstHeaderConfig.put(HeaderTitles.X_SPEECHCONTEXT, new ParameterConfig.Optional());
		sstHeaderConfig.put(HeaderTitles.X_SPEECHSUBCONTEXT, new ParameterConfig.Conditional() {
			protected boolean shouldAdd(String key, String val, Map<String, String> paramInputs) {
				String speechContext = paramInputs.get(HeaderTitles.X_SPEECHCONTEXT);
				return (speechContext != null) && speechContext.equals(SpeechContext.GAMING);
			}
		});
		sstHeaderConfig.put(HeaderTitles.CONTENT_LANGUAGE, new ParameterConfig.Conditional() {
			protected boolean shouldAdd(String key, String val, Map<String, String> paramInputs) {
				String speechContext = paramInputs.get(HeaderTitles.X_SPEECHCONTEXT);
				return (speechContext == null) || speechContext.equals(SpeechContext.GENERIC);
			}
		});
		
		//NOTE: This will most likely be automatically added by FileEntity
		sstHeaderConfig.put(HeaderTitles.CONTENT_LENGTH, new ParameterConfig.Optional());
		
		//TODO: Figure out if there is a way to pass in a Map of arguments
		sstHeaderConfig.put(HeaderTitles.X_ARG, new ParameterConfig.Optional());
		
		speechToTextReq = new AttPost(PATH + "speechToText", sstHeaderConfig);
		
		
		Map<String, ParameterConfig> ttsHeaderConfig = new HashMap<String, ParameterConfig>();
		ttsHeaderConfig.put(HeaderTitles.ACCEPT, new ParameterConfig.Optional());
		ttsHeaderConfig.put(HeaderTitles.CONTENT_TYPE, new ParameterConfig.Required());
		sstHeaderConfig.put(HeaderTitles.CONTENT_LANGUAGE, new ParameterConfig.Optional());
		//NOTE: This will most likely be automatically added by FileEntity
		sstHeaderConfig.put(HeaderTitles.CONTENT_LENGTH, new ParameterConfig.Optional());
		sstHeaderConfig.put(HeaderTitles.X_ARG, new ParameterConfig.Optional());
		
		textToSpeechReq = new AttPost(PATH + "textToSpeech", ttsHeaderConfig);
	}
	
	public AttResponse speechToText(Map<String, String> headers, String filePath) throws AttException {
		return speechToText(headers, new File(filePath));
	}
	
	public AttResponse speechToText(Map<String, String> headers, File voiceRecording) throws AttException {
		//AttRequest.verifyFilePath(voiceRecording);
		if(!voiceRecording.exists()) {
			throw new AttException("File \"" + voiceRecording.getAbsolutePath() + "\" does not exist");
		}
		
		boolean chunked = false;
		if(headers != null) {
			String transferEncoding = headers.get(HeaderTitles.TRANSFER_ENCODING);
			if(transferEncoding != null && headers.get(HeaderTitles.TRANSFER_ENCODING).contains("chunked")) {
				chunked = true;
				//copy and remove transfer encoding from the headers
				//TODO: Move into AttPost
				headers = new HashMap<String, String>(headers);
				headers.remove(HeaderTitles.TRANSFER_ENCODING);
			}
		}
		
		return speechToTextReq.send(headers, voiceRecording, chunked);
	}
	
	private Map<String, String> getHeaders(JSONObject args) {
		Map<String, String> headers = null;
		JSONObject headerObj = args.optJSONObject("headers");
		if(headerObj != null) {
			headers = AttRequest.jsonToMap(headerObj);
			JSONObject xArgs = headerObj.optJSONObject(HeaderTitles.X_ARG);
			if(xArgs != null) {
				headers.put(HeaderTitles.X_ARG, 
						AttRequest.uriEncodeMap(AttRequest.jsonToMap(xArgs)).replace('&', ','));
			}
		}
		
		return headers;
	}
	
	public AttResponse speechToText(JSONObject args) throws AttException {
		String filePath;
		
		try {
			filePath = args.getString("filePath");
		} catch(Exception e) {
			throw new AttException(e);
		}
		
		Map<String, String> headers = getHeaders(args);
		
		return speechToText(headers, filePath);
	}
	
	public AttResponseFile textToSpeech(Map<String, String> headers, String text, String filePath) throws AttException {
		return textToSpeech(headers, text, new File(filePath));
	}
	
	public AttResponseFile textToSpeech(Map<String, String> headers, String text, File file) throws AttException {
		AttUtil.verifyFilePath(file);
		
		return new AttResponseFile(textToSpeechReq.send(headers, text), file);
	}
	
	public AttResponseFile textToSpeech(JSONObject args) throws AttException {
		Map<String, String> headers = getHeaders(args);
		
		return textToSpeech(headers, args.optString(AttPost.JSON_BODY), args.optString(JSON_FILE_PATH));
	}
}
