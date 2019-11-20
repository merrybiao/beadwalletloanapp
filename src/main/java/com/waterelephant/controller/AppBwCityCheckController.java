package com.waterelephant.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.entity.BwCityCheck;
import com.waterelephant.service.IBwCityCheckService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.StringUtil;

import tk.mybatis.mapper.entity.Example;

@Controller
@RequestMapping("/app/cityCheck")
public class AppBwCityCheckController {
	private Logger logger = Logger.getLogger(AppBwCityCheckController.class);
	@Autowired
	private IBwCityCheckService bwCityCheckService;

	@ResponseBody
	@RequestMapping("/appCheckLogin/cityCheck.do")
	public AppResponseResult cityCheck(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String cityName = request.getParameter("cityName");
		logger.info("=========================定位获取到的城市名称=====================:" + cityName);

		if (CommUtils.isNull(cityName)) {
			result.setCode("801");
			result.setMsg("申请城市名称为空");
			return result;
		}
		Example example = new Example(BwCityCheck.class);
		example.createCriteria().andLike("cityName", "%" + cityName + "%").andEqualTo("isDisable", 1);
		List<BwCityCheck> list = bwCityCheckService.findBwCityCheckByExample(example);
		if (!CommUtils.isNull(list)) {
			logger.info("=========================当前城市正在开拓疆土=====================:" + cityName + ",IP:"
					+ StringUtil.toString(request.getRemoteAddr()));
			result.setCode("111");
			result.setMsg("当前城市正在开拓疆土");
			result.setResult(false);
			return result;
		} else {
			result.setCode("000");
			result.setMsg("当前城市可以申请借款");
			result.setResult(true);
			return result;
		}
	}
}
