package com.waterelephant.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.entity.BwAppVersion;
import com.waterelephant.service.IBwAppVersionService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;

/**
 * 检测app版本号
 * 
 * @author lujilong
 *
 */
@Controller
@RequestMapping("/app/version")
public class BwAppVersionController {

	@Autowired
	private IBwAppVersionService bwAppVersionService;

	@ResponseBody
	@RequestMapping("/findBwAppVersionById.do")
	public AppResponseResult findBwAppVersionById(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String versionId = request.getParameter("versionId");
		if (CommUtils.isNull(versionId)) {
			result.setCode("1001");
			result.setMsg("版本id为空");
			return result;
		}
		BwAppVersion bv = bwAppVersionService.findBwAppVersionById(Long.parseLong(versionId));
		result.setCode("000");
		result.setMsg("获取成功");
		result.setResult(bv);
		return result;
	}

	@ResponseBody
	@RequestMapping("/findBwAppDetail.do")
	public AppResponseResult findBwAppDetail(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String versionId = request.getParameter("versionId");
		String storeNameChannel = request.getParameter("storeNameChannel");
		try {
			if (CommUtils.isNull(versionId)) {
				result.setCode("1001");
				result.setMsg("版本id为空");
				return result;
			}
			Map<String, Object> map = bwAppVersionService.findBwAppDetail(versionId, storeNameChannel);
			result.setCode("000");
			result.setMsg("获取成功");
			result.setResult(map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}
}
