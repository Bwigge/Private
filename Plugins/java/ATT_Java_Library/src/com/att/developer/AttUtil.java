/**
 * This is a helper class that contains static, commonly used utility functions
 */

package com.att.developer;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

public class AttUtil {
	
	public static final boolean DEBUG = false;
	
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}
	
	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.size() == 0;
	}
	
	public static boolean isEmpty(Map<?,?> m) {
		return m == null || m.size() == 0;
	}
	
	public static boolean isEmpty(JSONObject o) {
		return o == null || o.length() == 0;
	}
	
	public static void addHeaderToMapIfNeeded(Header h, Map<String, String> map) {
		if(h == null || map == null) return;
		
		String headerName = h.getName();
		if(!map.containsKey(headerName)) {
			map.put(headerName, h.getValue());
		}
	}
	
	public static void verifyFilePath(File file) throws AttException {
		if(file == null) {
			throw new AttException(new NullPointerException("Missing file"));
		}
		
		if(!(new File(file.getAbsolutePath())).getParentFile().exists()) {
			throw new AttException("Folder path to file \"" + file.getAbsolutePath() + "\" does not exist");
		}
	}
}
