package com.waterelephant.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.SystemConstant;

import net.sf.json.JSONObject;

/**
 * 登录拦截器
 *
 * @author lujilong
 */
public class SecurityInterceptor implements HandlerInterceptor {

	private Logger logger = Logger.getLogger(SecurityInterceptor.class);
	// , "/app/my/keyMoney.do"
	private static final List<String> checkLoginUrlList = Arrays.asList(new String[] { "/pay/new/repayment.do" });

	/**
	 * 预处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		// 如果请求路径中包含appCheckLogin说明此接口需要验证登录
		if (uri.indexOf("/appCheckLogin") > 0 || contains(checkLoginUrlList, uri)) {
			HttpSession session = request.getSession();
			String loginToken = request.getParameter("loginToken");
			String appToken = (String) session.getAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN);
			logger.info("传递的loginToken：" + loginToken + ",session中的appToken：" + appToken);
			logger.info("request：" + request + ",session中：" + session);
			if (!CommUtils.isNull(loginToken) && !CommUtils.isNull(appToken) && appToken.equals(loginToken)) {
				return true;
			} else {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				// 如果回话中没有登录的信息那么直接返回。
				AppResponseResult result = new AppResponseResult();
				result.setCode("-1111");
				result.setMsg("登录失效，请登录");
				JSONObject jsonObject = JSONObject.fromObject(result);
				response.getWriter().write(jsonObject.toString());
				return false;
			}
		} else if (uri.indexOf("/appCheckToken") > 0) {
			if (uri.indexOf("appCheckToken/gotoLianLianSign.do") > 0) {
				return true;
			}
			if (uri.indexOf("appCheckToken/gotoLianLianSign2.do") > 0) {
				return true;
			}
			String loginToken = request.getParameter("loginToken");
			String bwId = request.getParameter("bwId");
			String session_token = SystemConstant.SESSION_APP_TOKEN.get(bwId);
			logger.info("request：" + request + ",session中：" + request.getSession());
			if (StringUtils.isBlank(loginToken) || !loginToken.equals(session_token)) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				// 如果回话中没有登录的信息那么直接返回。
				AppResponseResult result = new AppResponseResult();
				result.setCode("-1111");
				result.setMsg("登录失效，请登录");
				JSONObject jsonObject = JSONObject.fromObject(result);
				response.getWriter().write(jsonObject.toString());
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	/**
	 * 返回处理
	 */
	@Override
	public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav)
			throws Exception {
	}

	/**
	 * 后处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn)
			throws Exception {
	}

	private boolean contains(List<String> list, String str) {
		boolean containsBool = false;
		if (list != null && !list.isEmpty() && StringUtils.isNotEmpty(str)) {
			for (String itemStr : list) {
				if (str.contains(itemStr)) {
					containsBool = true;
					break;
				}
			}
		}
		return containsBool;
	}
}
