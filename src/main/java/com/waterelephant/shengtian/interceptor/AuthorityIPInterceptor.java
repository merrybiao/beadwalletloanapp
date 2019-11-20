
package com.waterelephant.shengtian.interceptor;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Objects;
import com.waterelephant.shengtian.util.NetWorkUtils;

/**
 * ClassName:AuthorityIPInterceptor <br/>
 * Function: 对用户的ip进行过滤,匹配是否是黑名单  <br/>
 * Date:     2018年5月10日 下午2:19:47 <br/>
 * @author   liwanliang
 * @version   1.0
 * @since    JDK 1.7
 * @see 	 
 */
public class AuthorityIPInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(HandlerInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("================统计盛天页面UV校验用户的ip开始================");
		//获取当前用的ip地址
		String userIp = NetWorkUtils.getIpAddress(request);
		
		ResourceBundle bundle = ResourceBundle.getBundle("ip"); 
		
		//读取配置文件中的黑名单ip
		String ipStr = bundle.getString("prohibited_ip");
		
		String[] ips = ipStr.split(",");
		
		for (String ip : ips) {
			//判断当前用户ip是否命中黑名单的ip
			if(Objects.equal(userIp, ip)){
				//命中
				logger.info("================当前用户的ip命中黑名单================ip:" + userIp);
				return false;
			}
		}
		logger.info("================统计盛天页面UV校验用户的ip结束================");
		//放行
		return true;
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

