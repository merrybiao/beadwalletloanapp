package com.waterelephant.drainage.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.waterelephant.drainage.service.RongShuService;

/**
 * @author xiaoXingWu
 * @time 2017年11月8日
 * @since JDK1.8
 * @description
 */
@Controller("rongshutestController")
public class TestController {

   @Autowired
   private  RongShuService  rongShuService ;
	
	@RequestMapping("rongShu/test.do")
	
	public   void  test(HttpServletRequest  request){
		String  pushInfo=request.getParameter("pushInfo");
		System.out.println(pushInfo);
		
	}
}

