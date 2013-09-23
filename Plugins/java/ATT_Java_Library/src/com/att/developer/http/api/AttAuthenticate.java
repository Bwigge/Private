/**
 * AttAuthenticate is a static object used to fetch and hold the accessToken needed
 * for requests to the AT&T RESTful APIs
 */

package com.att.developer.http.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.att.developer.AttException;
import com.att.developer.http.AttPost;
import com.att.developer.http.AttRequest;
import com.att.developer.http.AttResponse;
import com.att.developer.http.AttRequest.ParameterConfig;

public class AttAuthenticate {
	public static final String PATH = "/oauth/access_token";
	
	public static final String AUTH_PREFIX = "Bearer ";
	
	private static String appKey;
	private static String secret;
	private static String oAuthCode;
	private static AttScope scope;
	
	private static Set<String> POSSIBLE_AUTH_SCOPE = new HashSet<String>(Arrays.asList(new String[] {
		"TL", "IMMN", "MIM"
	}));
	
	private static AttAccessTokenResponse basicTokens;
	private static AttAccessTokenResponse userAuthTokens;
	
	private static AttPost tokenReq;
	
	//Static constructor
	static {
		setupTokenReq();
	}
	
	public static boolean setKeys(String newAppKey, String newSecret, String newScopeString) {
		AttScope newScope = new AttScope(newScopeString);
		if(newAppKey.equals(appKey) && newSecret.equals(secret) && newScope.equals(scope)) {
			return false;
		}
		
		appKey = newAppKey;
		secret = newSecret;
		scope = newScope;
		
		return true;
	}
	
	public static boolean setOAuthCode(String newOAuthCode) {
		if(oAuthCode == null ? newOAuthCode == null : oAuthCode.equals(newOAuthCode)) return false;
		oAuthCode = newOAuthCode;
		
		return true;
	}
	
	private static void setupTokenReq() {
		Map<String, ParameterConfig> headers = new HashMap<String, ParameterConfig>();
		headers.put("Content-Type", new ParameterConfig.Backup("application/x-www-form-urlencoded"));
		headers.put("Accept", new ParameterConfig.Backup("application/json"));
		
		tokenReq = new AttPost(PATH, headers);
		//Don't authenticate b/c we'll go in an infinite loop
		tokenReq.setAuthType(AttRequest.TokenType.NO_AUTHORIZATION);
	}
	
	/*
	private static String urlEncodeMap(Map<String, String> map) {
		String 
		for(Map.Entry<String, String> keyVal: map.entrySet()) {
			
		}
	}
	*/
	
	private static String setupReqBody(boolean userAuth, boolean refresh) {
		Map<String, String> params = new HashMap<String,String>();
		params.put("client_id", appKey);
		params.put("client_secret", secret);
		//TODO: Eventually add logic for refreshing the token
		params.put("grant_type", refresh ? "refresh_token" : userAuth ? "authorization_code" : "client_credentials");
		
		
		if(refresh) {
			//TODO: determine what parameter to add based on grant type
		} else {
			if(userAuth) {
				params.put("code", oAuthCode);
			} else {
				params.put("scope", scope.getBasicScope());
			}
		}
		return AttRequest.uriEncodeMap(params);
	}
	
	private static String fetchAccessToken(boolean userAuth) throws AttException {
		Map<String, String> reqHeaders = new HashMap<String, String>();
		reqHeaders.put("Content-Type", null); //Use backup
		reqHeaders.put("Accept", null); //Use backup
		
		//TODO: Guess the grant type
		AttAccessTokenResponse resp = new AttAccessTokenResponse(tokenReq.send(reqHeaders, setupReqBody(userAuth, false)));
		if(userAuth) {
			userAuthTokens = resp;
		} else {
			basicTokens = resp;
		}
		
		return resp.getAccessToken();
	}
	
	private static String getCurrentAuthToken(boolean userAuth) {
		if(userAuth) {
			if(userAuthTokens != null) return userAuthTokens.getAccessToken();
		} else {
			if(basicTokens != null) return basicTokens.getAccessToken();
		}
		
		return null;
	}
	
	public static String getAccessToken(boolean userAuth) throws AttException {
		if(appKey == null || secret == null || scope == null || scope.isEmpty()) {
			throw new AttException(
				"Missing parameters: appKey, secret, and scope must all be set before fetching the token"
			);
		}
		
		if(userAuth && oAuthCode == null) {
			throw new AttException(
				"Missing parameter: call setOAuthCode(newOAuthCode) with an OAuthCode before fetching the token for this request"
			);
		}
		
		String currentAuthToken = getCurrentAuthToken(userAuth);
		if(currentAuthToken != null) return currentAuthToken;
		
		return fetchAccessToken(userAuth);
	}
	
	public static String getAccessToken() throws AttException {
		return getAccessToken(false);
	}
	
	public static String getAccessToken(String appKey, String secret, String scope, boolean userAuth) throws AttException {
		boolean keysChanged = setKeys(appKey, secret, scope);
		String currentAuthToken = getCurrentAuthToken(userAuth);
		
		if(!keysChanged && currentAuthToken != null) {
			return currentAuthToken;
		} else {
			return fetchAccessToken(userAuth);
		}
	}
	
	public static String getAccessToken(String appKey, String secret, String scope) throws AttException {
		return getAccessToken(appKey, secret, scope, false);
	}
	
	public static boolean hasScope(String scopes) {
		return (scope != null) && scope.hasScope(scopes);
	}
	
	
	//This object is used to maintain scope and separate out user authenticated scope
	private static class AttScope {
		private Set<String> allScope;
		private String basicScopeString;
		private String userAuthScopeString;
		private String scopeString;
		
		public AttScope(String newScope) {
			setScope(newScope);
		}
		
		public boolean setEmpty() {
			if(isEmpty()) return false;
			
			allScope = null;
			basicScopeString = null;
			userAuthScopeString = null;
			scopeString = null;
			
			return true;
		}
		
		public boolean isEmpty() {
			return scopeString == null;
		}
		
		private static String join(Collection<String> stringCollection, String seperator) {
			String total = "";
			boolean first = true;
			for(String s: stringCollection) {
				if(first) {
					first = false;
				} else {
					total += ",";
				}
				total += s;
			}
			
			return total;
		}
		
		public boolean setScope(String scope) {
			if(scope == null) {
				return setEmpty();
			}
			
			Set<String> newAllScope = new HashSet<String>(Arrays.asList(scope.split(",")));
			if(newAllScope.equals(allScope)) return false;
			
			Set<String> basicScope = new HashSet<String>(newAllScope);
			basicScope.removeAll(POSSIBLE_AUTH_SCOPE);
			basicScopeString = join(basicScope, ",");
			
			Set<String> userAuthScope = new HashSet<String>(newAllScope);
			userAuthScope.retainAll(POSSIBLE_AUTH_SCOPE);
			userAuthScopeString = join(userAuthScope, ",");
			
			scopeString = scope;
			allScope = newAllScope;
			
			return true;
		}
		
		public String getBasicScope() {
			return basicScopeString;
		}
		
		@SuppressWarnings("unused")
		public String getUserAuthScope() {
			return userAuthScopeString;
		}
		
		public boolean equals(Object other) {
			if(other == null) {
				return (this.allScope == null);
			}
			
			return ((AttScope) other).allScope.equals(this.allScope);
		}
		
		public boolean hasScope(String scopes) {
			return allScope.containsAll(Arrays.asList(scopes.split(",")));
		}
	}
	
	private static class AttAccessTokenResponse extends AttResponse {
		private String accessToken;
		private String refreshToken;
		
		public AttAccessTokenResponse(AttResponse other) throws AttException {
			super(other);
			
			JSONObject respObj = getResponseJSONObject();
			try {
				accessToken = respObj.getString("access_token");
				refreshToken = respObj.getString("refresh_token");
			} catch(Exception e) {
				System.out.println(this.getStatusLine());
				System.out.println(this.getResponseString());
				throw new AttException("Response for access token is invalid");
			}
		}
		
		public String getAccessToken() {
			return accessToken;
		}
		
		@SuppressWarnings("unused")
		public String getRefreshToken() {
			return refreshToken;
		}
	}
}
