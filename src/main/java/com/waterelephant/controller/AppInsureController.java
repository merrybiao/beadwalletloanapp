package com.waterelephant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.FailFastProblemReporter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.service.entity.request.InsurePicReqData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entiyt.middle.LoginInfoResponse;
import com.beadwallet.service.entiyt.middle.Next;
import com.beadwallet.service.entiyt.middle.Param;
import com.beadwallet.service.entiyt.middle.PicResponse;
import com.beadwallet.service.entiyt.middle.RefresParam;
import com.beadwallet.service.entiyt.middle.Rule;
import com.waterelephant.entity.BwInsureCity;
import com.waterelephant.service.IBwInsureCityService;
import com.waterelephant.service.InsureService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.Base64Utils;

/**
 * app社保可支持城市控制器
 * 
 * @author huwei
 *
 */
@Controller
@RequestMapping("/app/insure")
public class AppInsureController {
	private Logger logger = Logger.getLogger(AppBwBorrowerController.class);

	@Autowired
	private IBwInsureCityService cityService;

	@Autowired
	private InsureService insureService;

	@ResponseBody
	@RequestMapping("/appCheckLogin/getCitys.do")
	public AppResponseResult getCitys(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		List<BwInsureCity> citys = cityService.getCitys();
		if (citys.size() > 0) {
			result.setCode("000");
			result.setMsg("获取社保城市成功");
			result.setResult(citys);
			return result;
		} else {
			result.setCode("101");
			result.setMsg("获取社保城市失败");
			result.setResult(citys);
			return result;
		}
	}

	@ResponseBody
	@RequestMapping("/appCheckLogin/getPicCode.do")
	public AppResponseResult getPicCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppResponseResult result = new AppResponseResult();
		String ruleId = null;
		String cityId = request.getParameter("cityId");
		String userId = request.getParameter("userId");
		if (CommUtils.isNull(cityId)) {
			result.setCode("102");
			result.setMsg("社保城市id为空");
			return result;
		}
		if (CommUtils.isNull(userId)) {
			result.setCode("103");
			result.setMsg("用户id为空");
			return result;
		}
		// 获取登录规则信息
		Response<LoginInfoResponse> resLInfo = insureService.getLoginInfo(Long.parseLong(cityId),Long.parseLong(userId));
		if (CommUtils.isNull(resLInfo)) {
			result.setCode("102");
			result.setMsg("登录规则获取结果为空");
			return result;
		}
		if (!resLInfo.getRequestCode().equals("200")) {
			result.setCode("102");
			result.setMsg(resLInfo.getRequestMsg());
			return result;
		}
		if (resLInfo.getObj().getCan_login().equals("0")) {
			result.setCode("102");
			result.setMsg("当前城市不支持登录");
			return result;
		}
		// 获取图片验证码
		InsurePicReqData insurePicReqData = null;
		List<Rule> rules = resLInfo.getObj().getRules();
		for (Rule rule : rules) {
			Next next = rule.getNext();
			List<Param> params = next.getParam();
			for (Param param : params) {
				if (!CommUtils.isNull(param.getRefresh_param())) {
					List<RefresParam> list = param.getRefresh_param();
					insurePicReqData = new InsurePicReqData();
					for (RefresParam refresParam : list) {
						if (refresParam.getKey().equals("type")) {
							insurePicReqData.setType(refresParam.getValue());
							logger.info("获取规则参数type:" + refresParam.getValue());
						}
						if (refresParam.getKey().equals("param_id")) {
							insurePicReqData.setParam_id(refresParam.getValue());
							logger.info("获取规则参数param_id:" + refresParam.getValue());
						}
						if (refresParam.getKey().equals("rule_id")) {
							ruleId = refresParam.getValue();
							insurePicReqData.setRule_id(ruleId);
							logger.info("获取规则参数ruleId:" + refresParam.getValue());
						}
					}
				}
			}
		}
		if (!CommUtils.isNull(insurePicReqData)) {
			insurePicReqData.setUserId(userId);
			Response<PicResponse> resPic = insureService.getPicCode(insurePicReqData);
			logger.info("获取图片返回消息:" + resPic.getRequestMsg());
			if (CommUtils.isNull(resPic.getObj())) {
				result.setCode("102");
				result.setMsg("图片验证码获取为空");
				return result;
			}
			result.setCode("000");
			result.setMsg("图片验证码获取成功");
			Map<String, Object> map = new HashMap<>();
			map.put("ruleId", ruleId);
			map.put("picCode", resPic.getObj());
			result.setResult(map);
			return result;
		} else {
			logger.info("获取图片验证码的参数对象为空");
			result.setCode("102");
			result.setMsg("获取图片验证码的参数为空");
			return result;
		}
	}

}
