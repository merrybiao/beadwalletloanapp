
package com.waterelephant.shengtian.controller;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.waterelephant.shengtian.entity.BwCountUV;
import com.waterelephant.shengtian.service.CountAccessNumService;
import com.waterelephant.shengtian.util.CookieUtils;
import com.waterelephant.shengtian.util.DateUtil;
import com.waterelephant.shengtian.util.NetWorkUtils;
import com.waterelephant.shengtian.util.RandomUtils;
import com.waterelephant.utils.StringUtil;
import com.yeepay.g3.utils.common.encrypt.Base64;


/**
 * ClassName:CountAccessNumController <br/>
 * Function: TODO  <br/>
 * Date:     2018年5月10日 上午9:30:49 <br/>
 * @author   liwanliang
 * @version   1.0
 * @since    JDK 1.7
 * @see 	 
 */

@Controller
public class CountAccessNumController {
	
	private static Logger logger = Logger.getLogger(CountAccessNumController.class);
	
	@Autowired
	private CountAccessNumService countAccessNumService;
	
	//Cookie名称
	private static final String COOKIE_NAME = "Access_User_Cookie";
	
	//Cookie过期时间---秒
	private static final Integer EXP_TIME = 60 * 60 * 24;
	
	@RequestMapping("sxy/shengtian/accessuv/count.do")
	public void countShengTianPV(HttpServletRequest request, HttpServletResponse response){
		logger.info("==================================统计盛天页面的UV记录开始==================================");
		
		BwCountUV countUV = new BwCountUV();
		try {
			//获取用户IP地址
			String userIp = NetWorkUtils.getIpAddress(request);
			countUV.setUserIp(userIp);
			logger.info("【countShengTianPV】:当前用户的IP地址：" + userIp);
		} catch (IOException e) {
			logger.info("【countShengTianPV】:获取用户ip发生异常" + e);
		}
		
		String createTime = DateUtil.getCurrentDateString(DateUtil.yyyy_MM_dd_HHmmss);
		countUV.setCreateTime(DateUtil.stringToDate(createTime, DateUtil.yyyy_MM_dd_HHmmss));
		
		//获取指定格式的当前时间字符串
		String currentTime = DateUtil.getCurrentDateString(DateUtil.yyyy_MM_dd);
		//当前时间  + 10位随机数字作为Cookie的值
		String cookieVal = currentTime + ":" + Base64.encode(String.valueOf(RandomUtils.getRandom(10)));
		countUV.setCookieVal(cookieVal);
		
		//获取客户端Cookie
		Cookie cookie = CookieUtils.getCookie(request, COOKIE_NAME);
		
		//判断客戶端是否有指定名称的Cookie
		if(StringUtil.isEmpty(cookie)){//没有
			logger.info("【countShengTianPV】:客户端没有Cookie,创建新的Cookie写回客户端");
			//创建新的Cookie写回客户端
			CookieUtils.setCookie(response, COOKIE_NAME, cookieVal, EXP_TIME);
			
			//执行插入数据
			countAccessNumService.insertAccessNumRecord(countUV);
			logger.info("==================================统计盛天页面的UV记录结束==================================");
			return;
		}
		
		//根据COOKIE_NAME获取Cookie中的值
		String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME);
		String cookieValTime = "";
		if(StringUtils.isNotBlank(cookieValue)){
			//获取Cookie中存储的日期
			cookieValTime = cookieValue.split(":")[0];
		}
		
		logger.info("【countShengTianPV】：Cookie中存储的日期：" + cookieValTime + ",当前日期：" + currentTime);
		
		//判断Cookie中的日期是否与当前日期匹配
		if(!Objects.equals(cookieValTime, currentTime)){
			logger.info("【countShengTianPV】:客户端有Cookie,但是时间匹配失败,创建新的Cookie写回客户端");
			//不匹配---说明用户当天是第一次访问,需要为用户创建新的Cookie
			CookieUtils.setCookie(response, COOKIE_NAME, cookieVal, EXP_TIME);
		}
		
		//当客户端存在Cookie时,将客户端的Cookie值存进数据库中
		countUV.setCookieVal(cookieValue);
		
		//执行插入数据
		countAccessNumService.insertAccessNumRecord(countUV);
		
		logger.info("==================================统计盛天页面的UV记录结束==================================");
	}
	
}

