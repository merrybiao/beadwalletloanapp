package com.waterelephant.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.waterelephant.annotation.LockAndSyncRequest;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.RedisUtils;

/**
 * 登录拦截器
 *
 * @author lujilong
 */
public class LockRequestInterceptor implements HandlerInterceptor {

	private Logger logger = Logger.getLogger(LockRequestInterceptor.class);

	/**
	 * 预处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			HttpSession session = request.getSession();
			String uri = request.getRequestURI();
			// 验证是否重复提交
			boolean lockedRequest = lockedRequest(request, session, handler);
			if (!lockedRequest) {
				Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
				logger.info(uri + "重复提交：orderId=" + paramMap.get("orderId") + paramMap + getBorrowerIdStr(request));
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				AppResponseResult result = new AppResponseResult();
				result.setCode("300");
				result.setMsg("请稍后再试");
				response.getWriter().write(JSON.toJSONString(result));
				return false;
			}
		} catch (Exception e) {
			logger.error("【LockRequestInterceptor.preHandle】异常,orderId=" + request.getParameter("orderId")
					+ getBorrowerIdStr(request));
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
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception excptn) throws Exception {
		try {
			HttpSession session = request.getSession();
			Map<String, Object> map = getLockRedisKeyAndSeconds(request, session, handler);
			if (map != null && !map.isEmpty()) {
				String lockRedisKey = (String) map.get("redisKey");
				if (StringUtils.isNotEmpty(lockRedisKey)) {
					logger.info("删除redis同步锁" + lockRedisKey);
					RedisUtils.del(lockRedisKey);
				}
			}
		} catch (Exception e) {
			logger.error("【LockRequestInterceptor.preHandle】异常,orderId=" + request.getParameter("orderId")
					+ getBorrowerIdStr(request));
		}
	}

	private String getBorrowerIdStr(HttpServletRequest request) {
		String borrowerId = null;
		borrowerId = request.getParameter("borrowerId");
		if (StringUtils.isEmpty(borrowerId)) {
			borrowerId = request.getParameter("borrower_id");
		}
		if (StringUtils.isEmpty(borrowerId)) {
			borrowerId = request.getParameter("bwId");
		}
		return ",borrowerId:" + borrowerId;
	}

	/**
	 * 该请求是否重复提交
	 * 
	 * @param request
	 * @param session
	 * @param handler
	 * @return false：重复提交；true：第一次提交
	 */
	private boolean lockedRequest(HttpServletRequest request, HttpSession session, Object handler) {
		boolean lockRequest = true;
		Map<String, Object> map = getLockRedisKeyAndSeconds(request, session, handler);
		if (map != null && !map.isEmpty()) {
			String lockRedisKey = (String) map.get("redisKey");
			Integer seconds = (Integer) map.get("seconds");
			if (seconds == null) {
				seconds = 20;
			}
			if (StringUtils.isNotEmpty(lockRedisKey)) {
				lockRequest = ControllerUtil.lockRequest(lockRedisKey, seconds);
			}
		}
		return lockRequest;
	}

	/**
	 * 根据请求获取方法注解，获取锁定需要用到的redis的key和锁定时间，请求结束会删除redis，若没删除默认30秒后删除<br />
	 * redis的key默认用常量+session的hashCode，也可以通过注解redisKeyAfterByRequestName自定义(request.getParameter(redisKeyAfterByRequestName))
	 * 
	 * @param request
	 * @param session
	 * @param handler
	 * @return
	 */
	private Map<String, Object> getLockRedisKeyAndSeconds(HttpServletRequest request, HttpSession session,
			Object handler) {
		Map<String, Object> map = new HashMap<String, Object>();
		String redisKey = null;
		Method method = null;
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			method = handlerMethod.getMethod();
		} else if (method == null) {
			method = ControllerUtil.getMethodByRequestMappingAndUri(handler.getClass(), request.getRequestURI());
		}
		if (method != null) {
			LockAndSyncRequest lockAnnotation = method.getAnnotation(LockAndSyncRequest.class);
			if (lockAnnotation != null && lockAnnotation.locked()) {
				String redisKeyPre = lockAnnotation.redisKeyPre();
				String redisKeyAfterByRequestName = lockAnnotation.redisKeyAfterByRequestName();
				int seconds = lockAnnotation.seconds();
				String redisKeyAfter = null;
				if (StringUtils.isNotEmpty(redisKeyAfterByRequestName)) {
					redisKeyAfter = request.getParameter(redisKeyAfterByRequestName);
				}
				if (StringUtils.isEmpty(redisKeyAfter)) {
					redisKeyAfter = "s" + session.hashCode() + "";
				}
				if (StringUtils.isNotEmpty(redisKeyAfter)) {
					redisKey = redisKeyPre + redisKeyAfter;
				}
				map.put("seconds", seconds);
			}
		}
		map.put("redisKey", redisKey);
		return map;
	}
}
