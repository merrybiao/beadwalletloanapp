package com.waterelephant.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waterelephant.service.ITempService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.dto.StatiTempDataDto;
import com.waterelephant.utils.AppResponseResult;

@Controller
@RequestMapping("/stati/temp")
public class StatiTempController {

	private Logger logger = Logger.getLogger(StatiTempController.class);

	@Autowired
	private ITempService tempService;

	/**
	 * 查询统计数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getStatiData.do")
	public AppResponseResult getStatiData(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			StatiTempDataDto dto = new StatiTempDataDto();
			Long orderCount = tempService.findTodayOrderCount();
			Double orderAmount = tempService.findTodayOrderAmount();
			Long signCount = tempService.findSignOrderCount();
			Long pushCount = tempService.findNotPushOrderCount();
			Long checkCount = tempService.findCheckOrderCount();
			Long repayCount = tempService.findRepayOrderCount();
			List<Map<String, Object>> list = tempService.findWeekOrderCount();
			dto.setOrderCount(orderCount);
			dto.setOrderAmount(orderAmount);
			dto.setSignCount(signCount);
			dto.setPushCount(pushCount);
			dto.setCheckCount(checkCount);
			dto.setRepayCount(repayCount);
			dto.setList(list);
			respResult.setCode("0000");
			respResult.setMsg("查询成功");
			respResult.setResult(dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			respResult.setCode("1001");
			respResult.setMsg("系统异常");
		}
		return respResult;
	}

}
