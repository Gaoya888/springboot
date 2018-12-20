package com.bo.spring.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {

	private static final String[] pcHeaders = new String[] { "Windows 98", "Windows ME", "Windows 2000", "Windows XP",
			"Windows NT", "Ubuntu" };

	private static final Map<String, String> devicemap = new HashMap<String, String>();
	static {
		devicemap.put("iPhone", "iPhone");
		devicemap.put("iPad", "iPad");
		devicemap.put("iPad", "iPad");
		devicemap.put("MQQBrowser", "Android");
		devicemap.put("UCWEB", "Android");
		devicemap.put("Mobile", "Android");
		devicemap.put("Android", "Android");
		devicemap.put("Mobi", "Android");
		devicemap.put("BrowserNG", "Nokia");
		devicemap.put("Windows Phone", "Windows Phone");
		devicemap.put("SAMSUNG", "Android");
		devicemap.put("HUAWEI", "Android");
		devicemap.put("Lenovo", "Android");
		devicemap.put("GIONEE", "Android");
		devicemap.put("HTC", "Android");
		devicemap.put("ZTE", "Android");
		devicemap.put("LENOVO", "Android");
		devicemap.put("okhttp", "Android");
		
	}

	/**
	 * 获取IP
	 */
	public static String getIp() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("X-Forwarded-For");
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
			ip = request.getRemoteAddr();
		}
		if (StringUtils.isNotEmpty(ip)) {
			String[] ipArray = ip.split(",");
			return ipArray[0];
		} else {
			return ip;
		}
	}

	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	public static String checkAgentDeviceName() {
		String userAgent = getRequest().getHeader("user-agent");
		for (int i = 0; userAgent != null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {
			if (userAgent.contains(pcHeaders[i])) {
				return "PC";
			}
		}
		for (String item : devicemap.keySet()) {
			if (userAgent.contains(item)) {
				return devicemap.get(item);
			}
		}
		return "未知";
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setConnectTimeout(300000);// 连接超时时间
			conn.setReadTimeout(300000);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		return response;
	}

	/**
	 * @param list传入需要重构的list
	 * @param map传入重构的原则,key是对象之前的属性名,value是之后的属性名
	 * @return
	 */
	public static <T> List<Map<String, Object>> toJson(List<T> list, Map<String, Object> map) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		try {
			Set<String> keys = map.keySet();
			for (T t : list) {
				Map<String, Object> tempMap = new HashMap<>();
				Field[] fs = t.getClass().getDeclaredFields();
				for (Field f : fs) {
					f.setAccessible(true);
					Object val = f.get(t);
					if (val != null) {
						if (keys.contains(f.getName())) {
							tempMap.put(map.get(f.getName()).toString(), val);
						} else {
							tempMap.put(f.getName(), val);
						}
					}
				}
				resultList.add(tempMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}


	public static String getOSName() {
		String str = "未知";
		String info = getRequest().getHeader("user-agent").toLowerCase();
		if (info.indexOf("windows") != -1 || info.indexOf("win32") != -1) {
			if (info.indexOf("windows nt 5.0") != -1) {
				str = "Windows Server 2000";
			} else if (info.indexOf("windows nt 5.1") != -1) {
				str = "Windows XP";
			} else if (info.indexOf("windows nt 5.2") != -1) {
				str = "Windows Server 2003";
			} else if (info.indexOf("windows nt 6.0") != -1) {
				str = "Windows Server 2008";
			} else if (info.indexOf("windows nt 6.1") != -1) {
				str = "Windows 7";
			} else if (info.indexOf("windows nt 6.2") != -1) {
				str = "Windows 8";
			}
		} else if (info.indexOf("macintosh") != -1 || info.indexOf("mac os x") != -1) {
			str = "Macintosh";
		} else if (info.indexOf("linux") != -1) {
			str = "Linux";
		} else if (info.indexOf("adobeair") != -1) {
			str = "Adobeair";
		}
		return str;
	}
	
	public static String getDevice() {
		String userAgent = getRequest().getHeader("user-agent");
		for (int i = 0; userAgent != null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {
			if (userAgent.contains(pcHeaders[i])) {
				return "PC";
			}
		}
		for (String item : devicemap.keySet()) {
			if (userAgent.contains(item)) {
				return devicemap.get(item);
			}
		}
		return "PC";
	}

}