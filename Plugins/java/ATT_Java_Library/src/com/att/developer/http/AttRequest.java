/**
 * This object has the base information needed for AttRequests
 * Extended objects should implement the send Method
 * 
 * Checkout http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
 */

package com.att.developer.http;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.AttUtil;
import com.att.developer.http.api.AttAuthenticate;
import com.att.developer.http.mime.AttBodyPart;
import com.att.developer.http.mime.AttPostMultipart;

public class AttRequest {
	public static final String PROTOCOL = "https://";
	public static final String DOMAIN = "api.att.com";
	public static final String ORIGIN = PROTOCOL + DOMAIN;
	
	public static enum TokenType {
		NO_AUTHORIZATION, ACCESS_AUTHORIZATION, USER_AUTHORIZATION
	};

	//Titles of headers used in AT&T
	public static final class HeaderTitles {
		public static final String AUTHORIZATION = "Authorization";
		public static final String ACCEPT = "Accept";
		public static final String CONTENT_TYPE = HTTP.CONTENT_TYPE;
		public static final String CONTENT_LENGTH = HTTP.CONTENT_LEN;
		public static final String CONTENT_ID = "Content-ID";
		public static final String CONTENT_LANGUAGE = "Content-Language";
		public static final String CONTENT_DISPOSITION = "Content-Disposition";
		public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
		public static final String X_SPEECHCONTEXT = "X-SpeechContext";
		public static final String X_SPEECHSUBCONTEXT = "X-SpeechSubContext";
		public static final String X_ARG = "X-Arg";
		public static final String UDID = "Udid";
		public static final String TRANSFER_ENCODING = HTTP.TRANSFER_ENCODING;//"Transfer-Encoding";
	}
	
	public static final String JSON_METHOD = "method";
	public static final String JSON_HEADERS = "headers";
	public static final String JSON_QUERY = "query";
	public static final String JSON_URL = "url";
	public static final String JSON_OPTIONS = "options";
	public static final String JSON_RES_FILE_PATH = "responseFilePath";
	
	
	/*
	//TODO Move "JSON" namespaced parameters to the following, extend in POST and POST-Multipart
	public static final class JSONParameterKeys {
		public static final String METHOD = "method";
		public static final String HEADERS = "headers";
		public static final String QUERY = "query";
		public static final String URL = "url";
		public static final String OPTIONS = "options";
		public static final String RES_FILE_PATH = "responseFilePath";
	}
	 */
	

	protected String route;
	protected Map<String, ParameterConfig> headerConfig;
	protected Map<String, ParameterConfig> queryStringConfig;
	
	private TokenType authActionType;
	
	//Saves resources by using a single httpClient, check out what it takes to use HttpURLConnection
	/*
	private static DefaultHttpClient httpClient;
	static {
		httpClient = getHttpClient();
		
		//Do we need to uninstantiate this?
		//try { httpClient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
	}
	*/
	
	public AttRequest(String path, Map<String, ParameterConfig> headerConfig) {
		setPath(path);
		this.headerConfig = headerConfig;
		
		authActionType = TokenType.ACCESS_AUTHORIZATION;
		
		if(AttUtil.DEBUG) {
			HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
				public boolean verify(String string,SSLSession ssls) {
					return true;
				}
			});
		}
	}
	
	public AttRequest(String path, Map<String, ParameterConfig> headerConfig, Map<String, ParameterConfig> queryStringConfig) {
		this(path, headerConfig);
		this.queryStringConfig = queryStringConfig;
	}
	
	
	@SuppressWarnings("deprecation")
	private static DefaultHttpClient getHttpClient() throws AttException {
		if(AttUtil.DEBUG) {
			DefaultHttpClient httpClient = null;
			try {
				KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        
				trustStore.load(null, null);
				
				SSLSocketFactory sf = SSLSocketFactory.getSocketFactory();
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				
		        HttpParams params = new BasicHttpParams();
		        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		
		        SchemeRegistry registry = new SchemeRegistry();
		        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		        registry.register(new Scheme("https", sf, 443));
		        
		        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
	
				httpClient = new DefaultHttpClient(ccm, params);
				//httpClient.getParams().setParameter(
				//		 ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new AttException(e);
			}
			
			return httpClient;
		} else {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			//httpClient.getParams().setParameter(
			//		 ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
			
			return httpClient;
		}
	}
	
	public void setPath(String path) {
		if(AttUtil.isEmpty(path)) {;
			route = ORIGIN;
		} else if(path.indexOf('/') == 0) {
			route = ORIGIN + path;
		} else {//if(path.indexOf(ORIGIN) == 0) { //Ensures that requests only go to the ORIGIN
			route = path;
		}
	}
	
	public void setAuthType(TokenType newAuthType) {
		authActionType = newAuthType;
	}
	
	protected AttResponse execute(HttpRequestBase req) throws AttException {
		DefaultHttpClient client = getHttpClient();
		
		HttpResponse resp;
		try {
			if(AttUtil.DEBUG) printRequest(req);
			resp = client.execute(req);
		} catch(Exception e) { 
			try { client.getConnectionManager().shutdown(); } catch (Exception ignore) {}
			throw new AttException(e); 
		}
		
		AttResponse attResp = new AttResponse(client, resp);
		
		//TODO: Have resp check for token refresh error, if it occurs, refresh the token and try again
		
		//System.out.println("-------------------------------------------------------------");
		//System.out.println(attResp.getStatusLine());
		//System.out.println(attResp.getResponseString());
		return attResp;
	}
	
	/*
	//TODO:
	1.  Check out using HttpURLConection instead.
	*/
	
	public static Map<String, String> jsonToMap(JSONObject obj) {
		if(obj == null) return null;
		
		@SuppressWarnings("unchecked")
		Iterator<String> nameItr = obj.keys();
		Map<String, String> outMap = new HashMap<String, String>();
		while(nameItr.hasNext()) {
		    String name = nameItr.next();
			outMap.put(name, obj.optString(name, null));
		}
		
		return outMap;
	}
	
	public static Map<String, String> getHeadersMap(JSONObject args) {
		return jsonToMap(args.optJSONObject(JSON_HEADERS));
	}
	
	public static Map<String, String> getQueryMap(JSONObject args) {
		return jsonToMap(args.optJSONObject(JSON_QUERY));
	}
	
	private Map<String, String> getValuesFromParamConfigs(Map<String, ParameterConfig> paramConfigs, Map<String, String> paramInputs) throws AttException {
		if(paramConfigs == null || paramInputs == null) return null;
		
		Map<String, String> result = new HashMap<String, String>();
		
		for(Map.Entry<String, ParameterConfig> pConfig: paramConfigs.entrySet()) {
			String paramKey = pConfig.getKey();
			String paramVal = pConfig.getValue().getValue(paramKey, paramInputs.get(paramKey), paramInputs);
			if(!AttUtil.isEmpty(paramVal)) {
				result.put(paramKey, paramVal);
			}
		}
		
		return result;
	}
	
	protected void addHeaders(Map<String, String> headers, AbstractHttpMessage httpMsg) throws AttException {
		if(headers == null) return;
		
		Map<String, String> headersToAdd;
		if(headerConfig == null) {
			headersToAdd = headers;
		} else {
			headersToAdd = getValuesFromParamConfigs(headerConfig, headers);
		}
		
		for(Map.Entry<String, String> headerToAdd: headersToAdd.entrySet()) {
			httpMsg.setHeader(headerToAdd.getKey(), headerToAdd.getValue());
		}
		
		//Special case, add auth token if it's passed in through the header
		if(headers.containsKey(HeaderTitles.AUTHORIZATION)) {
			httpMsg.setHeader(HeaderTitles.AUTHORIZATION, headers.get(HeaderTitles.AUTHORIZATION));
		} else {
			//TODO Add logic for fetching user auth access tokens as well
			if(authActionType != TokenType.NO_AUTHORIZATION) {
				String accessToken;
				try {
					accessToken = AttAuthenticate.getAccessToken(authActionType == TokenType.USER_AUTHORIZATION);
				} catch(Exception e) {
					//if AttAuthenticate is not a class in this package, then the authorization header must be passed in
					throw new AttException("Header \"" + HeaderTitles.AUTHORIZATION + "\" must be defined");
				}
				httpMsg.setHeader(HeaderTitles.AUTHORIZATION, AttAuthenticate.AUTH_PREFIX + accessToken);
			}
		}
	}
	
	public static String uriEncodeMap(Map<String, String> params) {
		List<NameValuePair> paramsToFormat = new ArrayList<NameValuePair>();
		
		for(Map.Entry<String, String> param: params.entrySet()) {
			paramsToFormat.add(new BasicNameValuePair(param.getKey(), param.getValue()));
		}
		
		return URLEncodedUtils.format(paramsToFormat, null);
	}
	
	protected String getQueryString(Map<String, String> queryStringInputs) throws AttException {
		Map<String, String> paramsToAdd = getValuesFromParamConfigs(queryStringConfig, queryStringInputs);
		if(paramsToAdd == null) return "";
		
		String encodedParams = uriEncodeMap(paramsToAdd);
		
		return (encodedParams.equals("")) ? "" : ("?" + encodedParams);
	}
	
	public interface ParameterConfig {
		public String getValue(String key, String value, Map<String, String> paramInputs) throws AttException;
		
		
		//Predefined commonly used ParameterConfig objects
		public class Optional implements ParameterConfig {
			public String getValue(String key, String val, Map<String, String> paramInputs) throws AttException {
				return val;
			}
		}
		
		public class Backup implements ParameterConfig {
			private String backup;
			
			public Backup(String backup) {
				this.backup = backup;
			}
			
			public String getValue(String key, String val, Map<String, String> paramInputs) {
				if(AttUtil.isEmpty(val)) return backup;
				return val;
			}
		}
		
		public class Required implements ParameterConfig {

			public String getValue(String key, String val, Map<String, String> headers) throws AttException {
				if(val == null) {
					throw new AttException("Required Parameter \"" + key + "\" is missing");
				}
				return val;
			}
		}
		
		public class Conditional implements ParameterConfig {
			//Override this to return a different back up parameter
			String backup = null;
			
			public String getValue(String key, String val, Map<String, String> paramInputs) throws AttException {
				if(shouldAdd(key, val, paramInputs)) return val;
				else return backup;
			}
			
			//Override this function to compute if an input should be used
			protected boolean shouldAdd(String key, String val, Map<String, String> paramInputs) throws AttException {
				return false;
			}
		}
	}
	
	//Protected helper methods for debugging
	protected void printHeaders(PrintStream out, AbstractHttpMessage httpMsg) {
		Header[] headers = httpMsg.getAllHeaders();
		for(int i = 0; i < headers.length; i++) {
			out.println(headers[i].getName() + ": " + headers[i].getValue());
		}
	}
	
	protected void printRequest(PrintStream out, HttpRequestBase httpReq) throws IOException {
		out.println(httpReq.getRequestLine());
		out.println("***************HTTP Header**************");
		printHeaders(out, httpReq);
	}
	
	protected void printRequest(HttpRequestBase httpReq) throws IOException {
		printRequest(System.out, httpReq);
	}
	
	//Use these functions to build up requests
	protected void addHeaders(JSONObject params) {
		
	}
	
	public static AttResponse send(JSONObject params) throws AttException {
		
		AttResponse res = null;
		String reqMethod = params.optString(JSON_METHOD, "GET");
		String url;
		try { url = params.getString(JSON_URL); }
		catch(Exception e) { throw new AttException(e); }
		
		Map<String, String> headers = jsonToMap(params.optJSONObject(JSON_HEADERS));
		
		if(reqMethod.equals("GET")) {
			AttGet req = new AttGet(url, null);
			
			//TODO handle either JSON or String for query string
			//TODO: Do this as a instance method 
			res = req.send(headers, "", jsonToMap(params.optJSONObject(JSON_QUERY)));
		} else if(reqMethod.equals("POST")) {
			JSONObject options = params.optJSONObject(JSON_OPTIONS);
			boolean isMultipart = options != null && options.optBoolean("isMultiplart");
			
			//Ignore Content-Length header, this is set by the entity post
			headers.remove(HeaderTitles.CONTENT_LENGTH);
			
			String stringBody = params.optString(AttPost.JSON_BODY, null);
			boolean isStringBody = (stringBody != null);
			JSONObject bodyObj = null;
			if(!isStringBody) bodyObj = params.optJSONObject(AttPost.JSON_BODY);
			
			if(params.has("attachments") || isMultipart) {
				AttPostMultipart req = new AttPostMultipart(url, null);
				AttBodyPart[] attachments = AttPostMultipart.bodyPartsFromParams(params);
				
				//TODO: Do this as a instance method of AttPostMultipart
				if(isStringBody) {
					res = req.send(headers, stringBody, attachments);
				} else if(bodyObj != null) {
					res = req.send(headers, bodyObj, attachments);
				} else  {
					res = req.send(headers, attachments);
				}
			} else {
				AttPost req = new AttPost(url, null);
				
				//TODO: Do this as a instance method of AttPost
				if(params.has(AttPost.JSON_FILE_PATH)) {
					if(params.has(AttPost.JSON_BODY)) {
						throw new AttException("Both \"" + AttPost.JSON_FILE_PATH + "\" and \"" +
								AttPost.JSON_BODY + "\" are defined for this post");
					}
					
					res = req.send(headers, new File(params.optString(AttPost.JSON_FILE_PATH, null)), false);
				} else if(isStringBody) {
					res = req.send(headers, stringBody);
				} else {
					res = req.send(headers, bodyObj);
				}
			}
		}
		
		if(params.has(JSON_RES_FILE_PATH)) {
			res = new AttResponseFile(res, new File(params.optString(JSON_RES_FILE_PATH, null)));
		}
		
		return res;
	}
}
