package com.waterelephant.channel.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwProductTerm;
import com.waterelephant.entity.ExtraConfig;
import com.waterelephant.utils.AppResponseResult;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

	private Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	// 根据产品编号查询产品信息
	@RequestMapping(value = "getProductInfo.do")
	@ResponseBody
	public AppResponseResult getProductInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		BwProductDictionary bwProductDictionary = new BwProductDictionary();
		try {
			String pNo = request.getParameter("pNo");// 产品编号
			if (StringUtils.isEmpty(pNo)) {
				result.setResult(bwProductDictionary);
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg(ActivityConstant.ErrorMsg.PARAM_ERROR);
				return result;
			}
			bwProductDictionary = productService.getProductInfo(pNo);
			result.setResult(bwProductDictionary);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getProductInfo;e=" + e);
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
		}
		return result;
	}

	// 根据产品id和期数返回产品配置信息
	@RequestMapping(value = "getProductTermInfo.do")
	@ResponseBody
	public AppResponseResult getProductTermInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		BwProductTerm bwProductTerm = new BwProductTerm();
		try {
			String idStr = request.getParameter("id");// 产品id
			String termNumStr = request.getParameter("termNum");// 期数
			int id = 0;
			int termNum = 0;
			if (StringUtils.isEmpty(idStr) || StringUtils.isEmpty(termNumStr)) {
				result.setResult(bwProductTerm);
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg(ActivityConstant.ErrorMsg.PARAM_ERROR);
				return result;
			} else {
				id = Integer.parseInt(idStr);
				termNum = Integer.parseInt(termNumStr);
			}
			bwProductTerm = productService.getProductTermInfo(id, termNum);
			result.setResult(bwProductTerm);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getProductTermInfo;e=" + e);
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
		}
		return result;
	}

	// 查询配置信息列表
	@RequestMapping(value = "getExtraConfigList.do")
	@ResponseBody
	public AppResponseResult getExtraConfigList(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		List<ExtraConfig> extraConfig = new ArrayList<ExtraConfig>();
		try {
			extraConfig = productService.getExtraConfigList();
			result.setResult(extraConfig);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getExtraConfigList;e=" + e);
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
		}
		return result;
	}

}
