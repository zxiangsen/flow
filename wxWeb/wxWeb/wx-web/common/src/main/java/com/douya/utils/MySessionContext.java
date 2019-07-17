package com.douya.utils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lb on 2017/12/11.
 */
public class MySessionContext {
	public static Map<String, HttpSession> sessionMap = new HashMap<>();

	public static synchronized void addSeesion(HttpSession session) {
		if (session != null) {
			sessionMap.put(session.getId(), session);
		}
	}

	public static synchronized void delSession(HttpSession session) {
		if (session != null) {
			sessionMap.remove(session.getId());
		}
	}

	public static synchronized HttpSession getSession(String sessionId) {
		if (sessionId == null) {
			return null;
		}
		return (HttpSession)sessionMap.get(sessionId);
	}
}