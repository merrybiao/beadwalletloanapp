/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 
 * Module:
 * 
 * ControllerUtil.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ControllerUtil {
	private static Logger logger = Logger.getLogger(ControllerUtil.class);

	/**
	 * 获取请求参数Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		if (request != null) {
			Enumeration<?> parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String name = String.valueOf(parameterNames.nextElement());
				String value = "";
				if (!StringUtils.isEmpty(name)) {
					value = request.getParameter(name);
				}
				paramMap.put(name, value);
			}
		}
		return paramMap;
	}

	/**
	 * 判断是否ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		boolean isAjax = false;
		if (request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
			isAjax = true;
		}
		return isAjax;
	}

	/**
	 * 锁定同步请求
	 * 
	 * @param lockKey
	 * @param lockSeconds
	 * @return false：异常或已有正在处理请求；true：添加锁定数据成功，继续下一步
	 */
	public static boolean lockRequest(String lockKey, int lockSeconds) {
		Long lockKeyResult = RedisUtils.setNxAndEx(lockKey,
				CommUtils.convertDateToString(new Date(), SystemConstant.YMD_HMS), lockSeconds);
		logger.info("【ControllerUtil.lockRequest】lockKey=" + lockKey + "重复提交,lockKeyResult:" + lockKeyResult);
		if (lockKeyResult == null || lockKeyResult <= 0L) {// redis异常或已有线程在处理
			return false;
		}
		return true;
	}
	
	/**
	 * 通过uri和requestMapping注解获取对应方法
	 * 
	 * @param clazz
	 * @param uri
	 * @return
	 */
	public static Method getMethodByRequestMappingAndUri(Class<?> clazz, String uri) {
		Method[] methods = clazz.getDeclaredMethods();
		if (methods != null && methods.length > 0 && StringUtils.isNotEmpty(uri)) {
			for (Method method : methods) {
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				if (requestMapping != null) {
					String[] valueArr = requestMapping.value();
					if (valueArr != null && valueArr.length > 0) {
						String value = requestMapping.value()[0];
						if (uri.contains(value)) {
							return method;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * @Description 返回文本数据
	 * @author 恩奇
	 * @Date 2016年7月13日 下午6:33:12
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	public static void printText(HttpServletResponse response, String content) {
		if (StringUtils.isNotEmpty(content)) {
			OutputStream outputStream = null;
			try {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain;charset=UTF-8");
				outputStream = response.getOutputStream();
				byte[] by = new byte[1024];
				ByteArrayInputStream input = new ByteArrayInputStream(content.getBytes("utf-8"));
				int byInt = 0;
				while ((byInt = input.read(by)) != -1) {
					outputStream.write(by, 0, byInt);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}