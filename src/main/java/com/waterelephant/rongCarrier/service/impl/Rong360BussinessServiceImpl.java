package com.waterelephant.rongCarrier.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.rong360.service.BeadWalletRongBjQufenqiService;
import com.beadwallet.service.rong360.service.BeadWalletRongExternalService;
import com.beadwallet.service.rong360.service.BeadWalletRongWhFenqiyunService;
import com.waterelephant.rongCarrier.service.Rong360BussinessService;

@Service
public class Rong360BussinessServiceImpl implements Rong360BussinessService {

	@Override
	public Map<String, Object> queryData(String searchId, String type,String appId, boolean gzip) {
		String result = null;
		//appId为空默认为速秒的appId
		if(StringUtils.isEmpty(appId)) appId = "1000052";
		switch (appId) {
			case "1000052"://为空或appId为速秒的
				result = BeadWalletRongExternalService.getData(searchId,type,gzip);
				break;
			case "2010805"://分七云的appid
				result = BeadWalletRongWhFenqiyunService.getData(searchId,type,gzip);
				break;
			case "2010826"://去分期的appid
				result = BeadWalletRongBjQufenqiService.getData(searchId,type,gzip);
				break;
			case "3000002"://上海水象分期
			default:
				result = BeadWalletRongExternalService.getData(searchId,type,appId,gzip);
//				throw new IllegalArgumentException("传入参数[appId]不合法~");
		}
		return JSONObject.parseObject(result);
	}

}
