package com.waterelephant.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.dto.CheckInfoDto;
import com.waterelephant.dto.HistorOrderDto;
import com.waterelephant.service.CheckService;
import com.waterelephant.service.IBwInsureCityService;
import com.waterelephant.service.HistoryOrderCheck;
import com.waterelephant.utils.AppResponseResult;

@Controller
@RequestMapping("/check")
public class CheckController {

	@Autowired
	private HistoryOrderCheck hisService;
	
	@Autowired
	private IBwInsureCityService cityService;
	
	@Autowired
	private CheckService checkService;
	
	@ResponseBody
	@RequestMapping("/history.do")
	public AppResponseResult HistoryOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");
		List<HistorOrderDto> list = hisService.getOrders(phone);
		CheckInfoDto cInfoDto = new CheckInfoDto();
		checkService.historyOrder(cInfoDto);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/city.do")
	public AppResponseResult createCity(HttpServletRequest request, HttpServletResponse response) throws Exception{
		AppResponseResult result = new AppResponseResult();
		boolean res = cityService.create();
		System.out.println(result);
		return result;
	}
}
