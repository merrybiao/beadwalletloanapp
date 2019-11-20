package com.waterelephant.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.ExtraConfig;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.ExtraConfigService;

/**
 * 
 * Module:银行绑定次数
 * 
 * ExtraConfigServiceImpl.java
 * 
 * @author wangkun
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class ExtraConfigServiceImpl extends BaseService<ExtraConfig, Long> implements ExtraConfigService {

	@Override
	public String findCountExtraConfigByCode(String code) {
		String value = "0";
		String sql = "select value from extra_config where code = #{code}";
		Map<String, Object> selectOne = sqlMapper.selectOne(sql, code);
		if (!CommUtils.isNull(selectOne)) {
			value = selectOne.get("value").toString();
		}
		return value;
	}

}
