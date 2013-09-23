package com.att.developer.http.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.http.AttResponse;
import com.att.developer.http.AttRequest.HeaderTitles;
import com.att.developer.http.AttRequest.ParameterConfig;
import com.att.developer.http.mime.AttBodyPart;
import com.att.developer.http.mime.AttBodyPartString;
import com.att.developer.http.mime.AttPostMultipart;

public class AttWAPPush {
	
	public static final String PATH = "/1/messages/outbox/wapPush";
	
	private static final String VAL_PUSH_CONTENT_TYPE = "text/vnd.wap.si";
	private static final String HEADER_X_WAP_APPLICATION_ID = "text/vnd.wap.si";
	private static final String VAL_X_WAP_APPLICATION_ID = "x-wap-application:wml.ua";
	
	private static String wapContentHeadersString;
	static {
		Map<String, String> wapContentHeaders = new HashMap<String, String>();
		wapContentHeaders.put(AttPostMultipart.HeaderTitles.CONTENT_TYPE, VAL_PUSH_CONTENT_TYPE);
		wapContentHeaders.put(HEADER_X_WAP_APPLICATION_ID, VAL_X_WAP_APPLICATION_ID);
		
		wapContentHeadersString = AttBodyPart.generateHeaders(wapContentHeaders);
	}
	
	AttPostMultipart sendWAPPushReq;
	
	public AttWAPPush() {
		Map<String, ParameterConfig> sendWAPPushHeaderConfig = new HashMap<String, ParameterConfig>();
		sendWAPPushHeaderConfig.put(HeaderTitles.ACCEPT, new ParameterConfig.Optional());
		sendWAPPushHeaderConfig.put(HeaderTitles.CONTENT_TYPE, new ParameterConfig.Required());
		
		sendWAPPushReq = new AttPostMultipart(PATH, sendWAPPushHeaderConfig);
	}
	
	/*
	//NOTE: Titanium uses Java 5 and javax.xml.stream was added in Java 6 so this doesn't work
	protected String buildWapXmlMessage(String message, String sourceURL) throws AttException {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		StringWriter outputBuilder = new StringWriter();
		XMLStreamWriter xmlWriter;
		try {
			xmlWriter = outputFactory.createXMLStreamWriter(outputBuilder);
			
			xmlWriter.writeStartDocument(null);
			xmlWriter.writeDTD("<!DOCTYPE si PUBLIC \"-//WAPFORUM//DTD SI 1.0//EN\" \"http://www.wapforum.org/DTD/si.dtd\">");
			xmlWriter.writeStartElement("si");
			
			xmlWriter.writeStartElement("indication");
			xmlWriter.writeAttribute("href", sourceURL);
			xmlWriter.writeAttribute("action", "signal-medium");
			xmlWriter.writeAttribute("si-id", "6532");
			
			xmlWriter.writeCharacters(message);
			
			xmlWriter.writeEndElement();
			
			xmlWriter.writeEndElement();
			
		} catch (Exception e) {
			throw new AttException(e);
		}
		
		return outputBuilder.toString();
	}
	*/
	
	protected String buildWapXmlMessage(String message, String sourceURL) throws AttException {
		//<?xml version="1.0" ?><!DOCTYPE si PUBLIC "-//WAPFORUM//DTD SI 1.0//EN" "http://www.wapforum.org/DTD/si.dtd"><si><indication href="https://api.att.com" action="signal-medium" si-id="6532">This is a test push message from the Java Plugin</indication></si>
		String wapMsg = "<?xml version=\"1.0\" ?>\n";
		wapMsg += "<!DOCTYPE si PUBLIC \"-//WAPFORUM//DTD SI 1.0//EN\" \"http://www.wapforum.org/DTD/si.dtd\">\n";
		wapMsg += "<si><indication href=\"" + sourceURL + "\" action=\"signal-medium\" si-id=\"6532\">";
		wapMsg += message;
		wapMsg += "</indication></si>";
		
		return wapMsg;
	}
	
	//TODO: How are multiple addresses passed in?
	public AttResponse sendWAPPush(Map<String, String> headers, String address, String message, String sourceURL) throws AttException {
		
		JSONObject addressWrapper = new JSONObject();
		try { 
			addressWrapper.put("address", address);
		} catch(Exception e) { throw new AttException(e); }
		
		//NOTE: Bug on the server requires that some headers to be in the content body
		String messageContent = wapContentHeadersString + buildWapXmlMessage(message, sourceURL);
		
		AttBodyPart wapMessageAttachment = new AttBodyPartString(messageContent, "text/xml", "PushContent");
		
		return sendWAPPushReq.send(headers, addressWrapper, new AttBodyPart[]{ wapMessageAttachment });
	}
	
	public AttResponse sendWAPPush(Map<String, String> headers, JSONObject body, JSONArray attachments) throws AttException {
		return sendWAPPushReq.send(headers, body, attachments);
	}
}
