
package com.waterelephant.otherPayment.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public class CookieUtil {
	public static String extractCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		String tmp = "";
		if ((cookies != null))
			for (int i = 0; i < cookies.length; i++) {
				if (name.equals(cookies[i].getName())) {
					try {
						tmp = cookies[i].getValue();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		return tmp;
	}

	public static void addCookie(HttpServletResponse response, String name, String value) {
		try {
			Cookie cookie = new Cookie(name, value);
			// cookie.setMaxAge(3600*24*14);//2周
			// cookie.setPath(path);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addCookie(HttpServletResponse response, String name, String value, String path) {
		try {
			Cookie cookie = new Cookie(name, value);
			cookie.setPath(path);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
