/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.service.serve.BeadWalletYixinService;
import com.waterelephant.yixin.dto.YixinMainData;
import com.waterelephant.yixin.service.YixinDataService;

import net.sf.json.JSONObject;

/**
 * @author heqiwen
 * 这个类是我们从宜信阿福那边获取信贷信息和风控信息
 *
 */
@Controller
@RequestMapping("/yixindata")
public class YixinDataController {

	@Autowired
	private YixinDataService  yiXinService;
	
	private Logger logger = Logger.getLogger(YixinController.class);
	
	@ResponseBody 
	@RequestMapping("/queryborrowingRisk.do")
	public YixinMainData queryborrowingRisk(HttpServletRequest request,HttpServletResponse response){
		String idNo=request.getParameter("idNo");
		String name=request.getParameter("name");
		String queryReason=request.getParameter("queryReason");
		idNo="320325197111075014";
		name="董计亮";
		queryReason="10";
		//queryCreditData("320325197111075014","董计亮","10");
		/*String json=BeadWalletYixinService.queryCreditData(idNo, name, queryReason);
		try{
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = JSONObject.fromObject(json);
				
				System.out.println(jsonObject);
			}
		}catch(Exception e){
			
		}*/
		
		return yiXinService.saveAndQueryYixinDatas(idNo, name, queryReason);
	}
}
