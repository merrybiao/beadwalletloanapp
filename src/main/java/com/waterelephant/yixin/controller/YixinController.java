package com.waterelephant.yixin.controller;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.utils.CommUtils;
import com.google.gson.Gson;
import com.waterelephant.yixin.dto.BackYiXinDto;
import com.waterelephant.yixin.dto.param.YiXinDataDto;
import com.waterelephant.yixin.dto.param.YiXinParamDto;
import com.waterelephant.yixin.service.YiXinService;
import com.waterelephant.yixin.util.RC4_128_V2;

import net.sf.json.JSONObject;

/**
 * 宜信对外接口
 * 
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月15日 下午4:44:32
 */
@Controller
@RequestMapping("/yixin/game/over")
public class YixinController {

	@Autowired
	private YiXinService yiXinService;

	private Logger logger = Logger.getLogger(YixinController.class);

	/**
	 * 借款和风险信息
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/v2/queryborrowingRisk.do")
	public BackYiXinDto queryborrowingRisk(HttpServletRequest request, HttpServletResponse response) {
		BackYiXinDto backYiXinDto = new BackYiXinDto();
		try {
			logger.info("提供借款和风险信息的查询接口---开始");
			PropertiesConfiguration config = new PropertiesConfiguration("api.properties");
			String rc4key = config.getString("password");
			logger.info("提供借款和风险信息的查询接口---获取加密密钥:" + rc4key);
			// 参数
			String params = request.getParameter("params");
			if (CommUtils.isNull(params)) {
				backYiXinDto.setErrorCode("4012");
				backYiXinDto.setMessage("params参数为空");
				return backYiXinDto;
			}
			logger.info("提供借款和风险信息的查询接口---获取加密参数:" + params);
			JSONObject json = new JSONObject().fromObject(params);
			// 解密
			String params_result = json.getString("params");
			String decrypt = URLDecoder.decode(params_result, "utf-8");
			String decryptResult = RC4_128_V2.decode(decrypt, rc4key);
			if (CommUtils.isNull(decryptResult)) {
				backYiXinDto.setErrorCode("4200");
				backYiXinDto.setMessage("params参数解密失败");
				return backYiXinDto;
			}
			logger.info("提供借款和风险信息的查询接口---获取加密参数解密后为:" + decryptResult);
			// 转成参数的dto类
			YiXinParamDto yiXinParamDto = new Gson().fromJson(decryptResult, YiXinParamDto.class);
			if (CommUtils.isNull(yiXinParamDto)) {
				backYiXinDto.setErrorCode("4002");
				backYiXinDto.setMessage("params参数解密失败");
				return backYiXinDto;
			}

			if (CommUtils.isNull(yiXinParamDto.getData().getIdNo())
					|| CommUtils.isNull(yiXinParamDto.getData().getName())) {
				backYiXinDto.setErrorCode("4012");
				backYiXinDto.setMessage("params参数为空");
				return backYiXinDto;
			}
			logger.info("提供借款和风险信息的查询接口---获取加密参数解密后转为参数对象成功");
			// 获取返回值
			backYiXinDto = yiXinService.backYiXinDto(yiXinParamDto, rc4key);
			logger.info("提供借款和风险信息的查询接口---操作成功");
			return backYiXinDto;
		} catch (Exception e) {
			logger.error("提供借款和风险信息的查询接口---操作异常" + e);
			e.printStackTrace();
			backYiXinDto.setErrorCode("5000");
			backYiXinDto.setMessage("操作异常" + e);
			return backYiXinDto;
		}
	}

	@ResponseBody
	@RequestMapping("/queryborrowingRisk2.do")
	public BackYiXinDto queryborrowingRisk2(HttpServletRequest request, HttpServletResponse response) {
		BackYiXinDto backYiXinDto = new BackYiXinDto();
		YiXinParamDto yiXinParamDto = new YiXinParamDto();
		String id_card = request.getParameter("id_card");
		String name = request.getParameter("name");
		PropertiesConfiguration config;
		try {
			config = new PropertiesConfiguration("api.properties");
			String rc4key = config.getString("password");
			YiXinDataDto data = new YiXinDataDto();
			data.setIdNo(id_card);
			data.setName(name);
			yiXinParamDto.setData(data);
			backYiXinDto = yiXinService.backYiXinDto(yiXinParamDto, rc4key);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		return backYiXinDto;
	}

}
