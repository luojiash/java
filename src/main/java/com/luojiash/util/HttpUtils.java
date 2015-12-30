package com.luojiash.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Cluster-Client-Ip");//
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
		return ip;
	}
	
	/**
	 * If post request's Content-Type isn't application/x-www-form-urlencoded 
	 * (ex. application/json, text/plain), use this method to get post data.<br>
	 * You can use Spring's annotation @RequestBody instead. 
	 */
	public static String getPostData(HttpServletRequest request) throws IOException {
		BufferedReader br = request.getReader();
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			while ((line=br.readLine())!=null) {
				sb.append(line+"\n");
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}
}
