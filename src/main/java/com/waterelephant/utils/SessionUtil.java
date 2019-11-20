package com.waterelephant.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * session 工具类
 * @author yanfuxing
 *
 */
public class SessionUtil {
		
	/**
	 * 销毁会话
	 * @param key
	 * @param session
	 * @return
	 */
	public static boolean delSession(HttpServletRequest request,HttpServletResponse response){
		boolean status = true;
		request.getSession().invalidate();
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JSESSIONID".equalsIgnoreCase(cookie.getName())){
				cookie.setMaxAge(0);
	            response.addCookie(cookie);
	        }
		}
		return true;
	} 
	
	
}
