package com.waterelephant.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.reflect.TypeToken;
import com.waterelephant.entity.FuiouCity;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.RedisUtils;

/**
 * 富友获取相关信息控制器
 * 
 * @author lujilong
 *
 */
@Controller
@RequestMapping("/app/fuiou")
public class AppFuiouController {
	private Logger logger = Logger.getLogger(AppFuiouController.class);

	/**
	 * 获取开户省份
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryProvince.do")
	public AppResponseResult queryProvince(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		Map<String, String> province = RedisUtils.hgetall("fuiou:province");
		result.setCode("000");
		result.setMsg("获取开户城市成功");
		result.setResult(province);
		return result;
	}

	/**
	 * 获取开户城市
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryCity.do")
	public AppResponseResult queryCity(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String provinceId = request.getParameter("provinceId");
		if (CommUtils.isNull(provinceId)) {
			result.setCode("701");
			result.setMsg("省份id为空");
			return result;
		}
		String cityStr = RedisUtils.hget("fuiou:city", provinceId);
		logger.info("获取redis城市字符串:" + cityStr);
		Type type = new TypeToken<List<FuiouCity>>() {
		}.getType();
		logger.info("获取type:" + type);
		List<FuiouCity> city = JsonUtils.fromJson(cityStr, type);
		logger.info("获取对应的city集合长度:" + city.size());
		result.setCode("000");
		result.setMsg("根据省份获取城市信息成功");
		result.setResult(city);
		return result;
	}

	/**
	 * 获取开户银行
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryBank.do")
	public AppResponseResult queryBank(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		Map<String, String> province = RedisUtils.hgetall("fuiou:bank");
		result.setCode("000");
		result.setMsg("获取开户银行成功");
		result.setResult(province);
		return result;
	}
}
