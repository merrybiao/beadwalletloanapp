package com.waterelephant.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.waterelephant.entity.PageCount;
import com.waterelephant.service.PageCountService;

/**
 * 页面访问统计
 * 
 * 
 * Module:
 * 
 * PageCountInterceptor.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class PageCountInterceptor implements HandlerInterceptor {

	private Logger logger = Logger.getLogger(PageCountInterceptor.class);

	@Autowired
	private PageCountService pageCountService;

	/**
	 * 预处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String ip = request.getRemoteAddr();
		String page = request.getRequestURI();
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		logger.info("doFilter sessionId=" + session.getId() + ",ip=" + ip + ",page=" + page + ",contextPath="
				+ contextPath + ",servletPath=" + servletPath);
		PageCount pageCount = new PageCount();
		pageCount.setSessionId(session.getId());
		pageCount.setIp(ip);
		pageCount.setPage(page);
		pageCount.setAccessTime(new Date());
		pageCount.setStayTime(0l);
		// 通过session id 和 ip，查出最近的一条访问记录
		PageCount bean = pageCountService.getLatestPageCount(session.getId(), ip);
		// 更改最近访问记录的停留时间，这里把两次访问记录的间隔时间算成上一次页面访问的停留时间
		if (bean != null) {
			long stayTime = (System.currentTimeMillis() - bean.getAccessTime().getTime()) / 1000;
			bean.setStayTime(stayTime);
			pageCountService.updateByPrimaryKeySelective(bean);
		}
		// 包含指定路径则保存
		if (page.contains("analogTest.do")) {
			pageCountService.insert(pageCount);
		}
		return true;
	}

	/**
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
