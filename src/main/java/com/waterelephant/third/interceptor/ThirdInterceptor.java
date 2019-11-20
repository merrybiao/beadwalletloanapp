package com.waterelephant.third.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.utils.CommUtils;

/**
 * 统一对外接口 - 拦截器（0091）
 * 
 * @author zhangchong
 *
 */
public class ThirdInterceptor implements HandlerInterceptor {
	@Autowired
	IBwOrderChannelService bwOrderChannelService;

	/**
	 * 统一对外接口 - 预处理
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		if (uri.indexOf("/third/interface") > 0) {
			// 第一步：获取参数
			String appId = request.getParameter("appId"); // 机构标识
			String requests = request.getParameter("request");// 请求参数

			ThirdResponse thirdResponse = new ThirdResponse();
			// 第二步：校验参数
			if (CommUtils.isNull(appId) == true) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("参数appId不合法");
				response.getWriter().write(JSON.toJSONString(thirdResponse));
				return false;
			}
			if (CommUtils.isNull(requests) == true) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("参数requests不合法");
				response.getWriter().write(JSON.toJSONString(thirdResponse));
				return false;
			}

			// 第三步：校验参数合法性
			// 机构标识 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
			if (CommUtils.isNull(orderChannel) == true) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("机构标识不存在");
				response.getWriter().write(JSON.toJSONString(thirdResponse));
				return false;
			}
			return true;
		} else {
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
