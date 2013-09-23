package com.att.developer.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.att.developer.AttException;


public class AttResponse {
	private HttpClient httpClient;
	protected HttpResponse httpResp;
	
	private String cachedString;
	private boolean readStream;
	
	public AttResponse(HttpClient httpClient, HttpResponse resp) throws AttException {
		this.httpClient = httpClient;
		httpResp = resp;
		readStream = false;
	}
	
	public AttResponse(AttResponse other) {
		//Copy the properties of the other
		httpResp = other.httpResp;
		cachedString = other.cachedString;
		readStream = other.readStream;
	}
	
	public int getStatusCode() {
		return httpResp.getStatusLine().getStatusCode();
	}
	
	public String getReasonPhrase() {
		return httpResp.getStatusLine().getReasonPhrase();
	}
	
	public String getStatusLine() {
		return httpResp.getStatusLine().toString();
	}
	
	public void consume() {
		//TODO: find an alternative to consume that works on android
		//try { EntityUtils.consume(httpResp.getEntity()); } catch (Exception ignore) {}
		try { httpClient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
	}
	
	protected byte[] getBinaryByteArray() throws AttException {
		if(readStream) return null;
		
		byte[] data;
		readStream = true;
		
		try {
			data = EntityUtils.toByteArray(httpResp.getEntity());
			return data;
		} catch (Exception e) {
			throw new AttException(e);
		} finally {
			consume();
		}
	}
	
	protected boolean writeTo(OutputStream out) throws AttException {
		if(readStream) {
			if(cachedString == null) return false;
			
			PrintStream ps = new PrintStream(out);
			ps.print(cachedString);
			ps.flush();
			
		} else {
			
			HttpEntity respEntity = httpResp.getEntity();
			readStream = true;
			
			try { respEntity.writeTo(out); }
			catch(Exception e) { throw new AttException(e); }
			finally { consume(); }
		}
		
		return true;
	}
	
	protected boolean writeToFile(File file) throws AttException {
		OutputStream fileStream;
		try { fileStream = new FileOutputStream(file); } 
		catch(Exception e) { throw new AttException(e); }
		
		boolean result = writeTo(fileStream);
		
		try { fileStream.close(); }
		catch(Exception e) { throw new AttException(e); }
		finally { consume(); }
		
		return result;
	}
	
	protected void writeToFile(String filePath) throws AttException {
		writeToFile(new File(filePath));
	}
	
	public String getResponseString() throws AttException {
		if(readStream) return cachedString;
		
		try {
			cachedString = EntityUtils.toString(httpResp.getEntity());
			readStream = true;
			return cachedString;
		} catch (Exception e) {
			throw new AttException(e);
		} finally {
			consume();
		}
	}
	
	public JSONObject getResponseJSONObject() throws AttException {
		String responseString = getResponseString();
		if(responseString == null) return null;
		
		try { return new JSONObject(responseString); }
		catch(Exception e) { throw new AttException(e); }
		finally { consume(); }
	}
	
	public Map<String, List<String>> getResponseHeaders() {
		Header[] baseHeaders = httpResp.getAllHeaders();
		Map<String, List<String>> formattedHeaders = new HashMap<String, List<String>>();
		for(int i = 0; i < baseHeaders.length; i++) {
			String headerName = baseHeaders[i].getName();
			String headerVal = baseHeaders[i].getValue();
			List<String> headerVals = formattedHeaders.get(headerName);
			if(headerVals == null) {
				headerVals = new ArrayList<String>();
				formattedHeaders.put(headerName, headerVals);
			}
			
			headerVals.add(headerVal);
		}
		
		return formattedHeaders;
	}
}

