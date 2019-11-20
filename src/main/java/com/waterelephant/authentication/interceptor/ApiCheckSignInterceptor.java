package com.waterelephant.authentication.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.waterelephant.authentication.ApiSignUtils;
import com.waterelephant.authentication.annotation.ApiCheckSign;
import com.waterelephant.authentication.request.BodyReaderHttpServletRequestWrapper;
import com.waterelephant.authentication.service.SystemAuthenticationService;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.utils.AppResponseResult;

public class ApiCheckSignInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(ApiCheckSignInterceptor.class);
	
	@Resource
	private SystemAuthenticationService authenticationService;
	
	/**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
     * 返回值：true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
     */
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//只对POST做签名校验
		if (!(HttpMethod.POST.name().equals(request.getMethod()))) {
            return true;
        }
		
		//没有加ApiCheckSign注解的方法不做验签处理
		if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ApiCheckSign apiSign = handlerMethod.getMethodAnnotation(ApiCheckSign.class);
            if(null == apiSign) return true;
		}

		//用户请求地址
		String requestUrl = request.getRequestURI();
		String contextPath = request.getContextPath();
		String contentType = request.getContentType();
		//用户写了多个"///"，只保留一个
		requestUrl = requestUrl.replace(contextPath, "").replaceAll("/+", "/");
		//一下类容需要做验签
		logger.info("----【Api验签】----我是华丽的分割线-----请求{}----",requestUrl);
		
		//获取参数
		Map<String,String> paramsMap = new HashMap<>();
    	switch(contentType) {
	    	case "application/json":
	    		BodyReaderHttpServletRequestWrapper copyRequest = (BodyReaderHttpServletRequestWrapper)request;
	    		String params = copyRequest.getBody();
				paramsMap = JSON.parseObject(params, Map.class);
	    		break;
	    	default:
	    		paramsMap = ApiSignUtils.getRequestParamMap(request);
	    		break;
    	}
    	
    	
//    	if(null != paramsMap && !paramsMap.isEmpty() || "true".equals(paramsMap.get("test"))) return true;
    	
    	try {
			//验证token
			boolean isSuccess = authenticationService.verifyToken(paramsMap);
			if(!isSuccess) {
				//验证token返回结果不为空 则验证失败
				AppResponseResult responseResult = new AppResponseResult();
				responseResult.setCode("300");
				responseResult.setMsg("请求失败，token无效或已过期~");
				String resultJson = JSON.toJSONString(responseResult);
				logger.info("----【ApiAuthInterceptor】请求地址：{},验证token失败，返回：{}---",requestUrl,resultJson);
				returnHttpResponse(response, resultJson);
				return false;
			}
			//获取appKey
			String appKey = paramsMap.get(ApiSignUtils.REQUEST_PARAM_APPKEY);
			//获取签名key
			String signKey = authenticationService.getSignKey(appKey);
			//验签
			boolean checkSign = authenticationService.verifySign(paramsMap,signKey);
			if(!checkSign) {
				//签名结果不为空 则签名失败
				AppResponseResult responseResult = new AppResponseResult();
				responseResult.setCode("300");
				responseResult.setMsg("参数错误~签名失败，请重新签名！");
				String resultJson = JSON.toJSONString(responseResult);
				logger.info("----【ApiAuthInterceptor】请求地址：{},验签失败，返回：{}---",requestUrl,resultJson);
				returnHttpResponse(response, resultJson);
				return false;
			}
		} catch (IllegalArgumentException | BusinessException e) {
			AppResponseResult responseResult = new AppResponseResult();
			responseResult.setCode("300");
			responseResult.setMsg(e.getMessage());
			String resultJson = JSON.toJSONString(responseResult);
			logger.error("----【ApiAuthInterceptor】请求地址：{},验签失败，返回：{}---",requestUrl,resultJson);
			returnHttpResponse(response, resultJson);
			return false;
		} catch (Exception e) {
			AppResponseResult responseResult = new AppResponseResult();
			responseResult.setCode("500");
			responseResult.setMsg("请求失败~系统权鉴失败~");
			String resultJson = JSON.toJSONString(responseResult);
			logger.error("----【ApiAuthInterceptor】请求地址：{},验签失败，异常：{}---",requestUrl,e.getMessage());
			returnHttpResponse(response, resultJson);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
     */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
	
	/**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，类似于try-catch-finally中的finally，但仅调用处理器执行链中
     */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//用户请求地址
        String requestUrl = request.getRequestURI();
        String contextPath = request.getContextPath();
        requestUrl = requestUrl.replace(contextPath, "").replaceAll("/+", "/");
		Long start = null;
        if(request instanceof BodyReaderHttpServletRequestWrapper) {
        	BodyReaderHttpServletRequestWrapper copyRequest = (BodyReaderHttpServletRequestWrapper) request;
        	start = copyRequest.getReqTime();
		}
        if(null == start || start.longValue() <=0) return;
        long end  = System.currentTimeMillis();
        logger.info("----请求{}耗时：{}ms----",requestUrl,(end-start.longValue()));
	}
	
	private void returnHttpResponse(HttpServletResponse response,String resultJSON) {
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(resultJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
