import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.http.AttPost;
import com.att.developer.http.AttRequest;
import com.att.developer.http.AttResponse;
import com.att.developer.http.AttResponseFile;
import com.att.developer.http.AttRequest.HeaderTitles;
import com.att.developer.http.api.AttAuthenticate;
import com.att.developer.http.api.AttIMMN;
import com.att.developer.http.api.AttMMS;
import com.att.developer.http.api.AttSMS;
import com.att.developer.http.api.AttSpeech;
import com.att.developer.http.api.AttWAPPush;
import com.att.developer.http.mime.AttBodyPart;
import com.att.developer.http.mime.AttPostMultipart;

public class ATTJavaPluginTester {
	
	public static String accessToken = "Bearer bfd265cc7752b81044977d3346508ed7";
	public static String authAccessToken = "Bearer 5e9f91a00430c47af156c7a88f0c3509";
	
	public static JSONObject getFileObject(String filePath, String type, String fileName) throws JSONException {
		JSONObject file = new JSONObject();
		file.put("filePath", filePath);
		if(fileName != null) file.put("fileName", fileName);
		if(type != null) file.put("fileType", type);
		return file;
	}
	
	public static JSONObject getFileTextObject(String textBody, String fileName) throws JSONException {
		JSONObject file = new JSONObject();
		
		String base64String = Base64.encodeBase64String(textBody.getBytes());
		
		file.put(AttBodyPart.AttachmentProperties.JSON_BODY, base64String);
		file.put(AttBodyPart.AttachmentProperties.JSON_MIME_TYPE, "text/plain");
		file.put(AttBodyPart.AttachmentProperties.JSON_NAME, fileName);
		file.put(AttBodyPart.AttachmentProperties.JSON_ENCODING, "base64");
		return file;
	}
	
	public static JSONObject getRawFileObject(String filePath, String type, String fileName) throws JSONException {
		JSONObject file = new JSONObject();
		file.put(AttBodyPart.AttachmentProperties.JSON_FILE_PATH, filePath);
		if(fileName != null) file.put(AttBodyPart.AttachmentProperties.JSON_NAME, fileName);
		if(type != null) file.put(AttBodyPart.AttachmentProperties.JSON_MIME_TYPE, type);
		return file;
	}
	
	public static void main(String[] args) {
		
		AttAuthenticate.setKeys("76cb2aee7696627a6d27f959efa1a4d4", 
				"7a0b131721d380fc", "ADS,CMS,MMS,PAYMENT,SMS,SPEECH,TTS,WAP,TL,IMMN,MIM");
		
		AttAuthenticate.setOAuthCode("8G2t1IMuVcbf2kH6UfE5MjTRf");
		
		try {
			testRawMMS();
			//testMMS();
			//testSMS();
			//testSpeech();
			//testIMMN();
			//testWAPPush();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testRawMMS() throws JSONException, AttException {
		
		JSONObject rawObj = new JSONObject();
		rawObj.put(AttRequest.JSON_URL, AttRequest.ORIGIN + AttMMS.PATH);
		rawObj.put(AttRequest.JSON_METHOD, "POST");
		
		JSONObject body = new JSONObject();
		
		JSONObject bodyWrapper = new JSONObject();
		bodyWrapper.put("address", "tel:4252143421");
		bodyWrapper.put("subject", "This raw MMS worked!");
		bodyWrapper.put("priority", "High");
		body.put("outboundMessageRequest", bodyWrapper);
		
		rawObj.put(AttPost.JSON_BODY, body);
		
		JSONObject headers = new JSONObject();
		headers.put(AttRequest.HeaderTitles.CONTENT_TYPE, "application/json");
		headers.put(AttRequest.HeaderTitles.ACCEPT, "application/json");
		headers.put(AttRequest.HeaderTitles.AUTHORIZATION, accessToken);
		rawObj.put(AttRequest.JSON_HEADERS, headers);
		
		JSONArray attachments = new JSONArray();
		
		//attachments.put(new JSONObject());
		attachments.put(getFileTextObject("Test of base64 encoded object", "test.txt"));
		//attachments.put(getFileObject("test.txt", null, null));
		//attachments.put(getFileObject("test.png", null, null));
		
		rawObj.put(AttPostMultipart.JSON_ATTACHMENTS, attachments);
		
		AttResponse resp = AttRequest.send(rawObj);
		printResponse("raw sendMMS", resp);
	}
	
	public static void testMMS() throws JSONException, AttException {
		AttMMS mms = new AttMMS();
		
		JSONObject tester = new JSONObject();
		
		JSONObject body = new JSONObject();
		body.put("address", "tel:4252143421");
		body.put("subject", "This MMS worked!");
		body.put("priority", "High");
		tester.put("body", body);
		
		JSONObject headers = new JSONObject();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put(AttRequest.HeaderTitles.AUTHORIZATION, accessToken);
		tester.put("headers", headers);
		
		JSONArray attachments = new JSONArray();
		
		attachments.put(new JSONObject());
		//attachments.put(getFileObject("test.txt", null, null));
		//attachments.put(getFileObject("test.png", null, null));
		
		tester.put("attachments", attachments);
		
		AttMMS.AttSendMMSResponse mmsResp = mms.sendMMS(tester);
		System.out.println("\nsendMMS response:");
		System.out.println(mmsResp.getResponseString());
		
		Map<String, String> statusHeaders = new HashMap<String, String>();
		statusHeaders.put(AttRequest.HeaderTitles.ACCEPT, "application/json");
		statusHeaders.put(AttRequest.HeaderTitles.AUTHORIZATION, accessToken);
		
		String msgID = mmsResp.getMessageId();
		if(msgID != null) {
			AttResponse mmsStatus = mms.getMMSStatus(statusHeaders, mmsResp.getMessageId());
			System.out.println("\ngetMMSStatus response:");
			System.out.println(mmsStatus.getResponseString());
			//mmsStatus.consume(); //Ensures the request is closed
		}
	}
	
	public static void testSMS() throws JSONException, AttException {
		AttSMS sms = new AttSMS();
		
		JSONObject tester = new JSONObject();
		
		JSONObject body = new JSONObject();
		body.put("address", "tel:4252143421");
		body.put("message", "This SMS worked!");
		tester.put("body", body);
		
		JSONObject headers = new JSONObject();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		tester.put("headers", headers);
		
		AttSMS.AttSendSMSResponse resp = sms.sendSMS(tester);
		System.out.println("\nsendSMS response:");
		System.out.println(resp.getResponseString());
		
		Map<String, String> statusHeaders = new HashMap<String, String>();
		statusHeaders.put(AttRequest.HeaderTitles.ACCEPT, "application/json");
		
		AttResponse smsStatusResp = sms.getSMSStatus(statusHeaders, resp.getMessageId());
		System.out.println("\ngetSMSStatus response:");
		System.out.println(smsStatusResp.getResponseString());
		
		AttResponse getSMSResp = sms.getSMS(statusHeaders, "57426374");
		System.out.println("\ngetSMS response:");
		System.out.println(getSMSResp.getResponseString());
	}
	
	public static void testSpeech() throws JSONException, AttException {
		AttSpeech speech = new AttSpeech();
		
		Map<String, String> sstHeaders = new HashMap<String, String>();
		sstHeaders.put(HeaderTitles.CONTENT_TYPE, "audio/wav");
		sstHeaders.put(HeaderTitles.ACCEPT, "application/json");
		sstHeaders.put(HeaderTitles.X_SPEECHCONTEXT, AttSpeech.SpeechContext.GENERIC);
		//sstHeaders.put(HeaderTitles.X_SPEECHSUBCONTEXT, "chat");
		sstHeaders.put(HeaderTitles.CONTENT_LANGUAGE, AttSpeech.LanguageContent.ES_US);
		sstHeaders.put(HeaderTitles.TRANSFER_ENCODING, "chunked");
		//sstHeaders.put(HeaderTitles.X_ARG, "HasMultipleNBest=true");
		
		AttResponse sstResp = speech.speechToText(sstHeaders, "test.wav");
		printResponse("speechToText", sstResp);
		
		Map<String, String> ttsHeaders = new HashMap<String, String>();
		ttsHeaders.put(HeaderTitles.CONTENT_TYPE, "text/plain");
		ttsHeaders.put(HeaderTitles.ACCEPT, "audio/wav");
		//ttsHeaders.put(HeaderTitles.CONTENT_LANGUAGE, AttSpeech.LanguageContent.ES_US);
		//ttsHeaders.put(HeaderTitles.X_ARG, "VoiceName=mike");
		
		String text = "This is a test of the java plugin text to speech";
		String filePath = "textToSpeech.wav";
		
		AttResponse ttsResp = speech.textToSpeech(ttsHeaders, text, filePath);
		printResponse("textToSpeech", ttsResp, false);
	}
	
	public static void testIMMN() throws JSONException, AttException {
		//KywZYqhyCvxXcogcb5D8oDLpO
		//String authAccessToken = AttAuthenticate.AUTH_PREFIX + "sbIQSBOATqwUUEAXBjbLwXOaj";//"vvtrjXcxk9cUepzhmMV5FTGsN";//"Q4i0ZedAiDpqxIgH7NO7pEGq8";
		
		AttIMMN immn = new AttIMMN();
		
		JSONObject tester = new JSONObject();
		
		JSONObject body = new JSONObject();
		JSONArray addresses = new JSONArray();
		addresses.put("tel:4252143421");
		body.put("Addresses", addresses);
		body.put("Subject", "Test of immn");
		body.put("Text", "The immn message worked!");
		tester.put(AttPost.JSON_BODY, body);
		
		JSONObject headers = new JSONObject();
		headers.put(AttRequest.HeaderTitles.CONTENT_TYPE, "application/json");
		headers.put(AttRequest.HeaderTitles.ACCEPT, "application/json");
		headers.put(AttRequest.HeaderTitles.AUTHORIZATION, authAccessToken);
		tester.put(AttRequest.JSON_HEADERS, headers);
		
		AttResponse resp = immn.sendMessage(tester);
		printResponse("sendIMMN", resp);
		
		Map<String, String> statusHeaders = new HashMap<String, String>();
		statusHeaders.put(AttRequest.HeaderTitles.ACCEPT, "application/json");
		statusHeaders.put(AttRequest.HeaderTitles.AUTHORIZATION, authAccessToken);
		
		resp = immn.getMessageHeaders(statusHeaders, 100, null);
		printResponse("getMessageHeaders", resp);
		
		resp = immn.getMessageContent(statusHeaders, "S893", "1");
		printResponse("getMessageContent", resp, false);
		new AttResponseFile(resp, "test.gif");
	}
	
	public static void testWAPPush() throws AttException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(AttRequest.HeaderTitles.CONTENT_TYPE, "application/json");
		headers.put(AttRequest.HeaderTitles.ACCEPT, "application/json");
		headers.put(AttRequest.HeaderTitles.AUTHORIZATION, accessToken);
		
		AttWAPPush wap = new AttWAPPush();
		AttResponse resp = wap.sendWAPPush(headers, "tel:+14252143421", "This is a test push message from the Java Plugin", AttRequest.ORIGIN);
		printResponse("sendWAPPush", resp);
	}
	
	private static void printResponse(String title, AttResponse resp) throws AttException {
		printResponse(title, resp, true);
	}
	
	private static void printResponse(String title, AttResponse resp, boolean printString) throws AttException {
		System.out.println("\n" + title + " response:");
		System.out.println(resp.getStatusLine());
		if(printString) System.out.println(resp.getResponseString());
	}
}
