package com.waterelephant.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwAppVersion;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwAppVersionService;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

@Service
public class BwAppVersionService extends BaseService<BwAppVersion, Long> implements IBwAppVersionService {

	@Override
	public BwAppVersion findBwAppVersionById(Long id) {

		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwAppVersionService#findBwAppDetail(long)
	 */
	@Override
	public Map<String, Object> findBwAppDetail(String versionId, String storeNameChannel) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(
				"SELECT	id,	app_type appType,app_version appVersion,update_time updateTime,need_update needUpdate");
		sBuffer.append(" FROM bw_app_version where 1=1 ");
		if (StringUtil.isNotEmpty(versionId)) {
			sBuffer.append("and id =" + versionId);
		}
		Map<String, Object> map = sqlMapper.selectOne(sBuffer.toString());
		if ("true".equals(map.get("needUpdate").toString())) {
			map.put("needUpdate", 1);
		} else if ("false".equals(map.get("needUpdate").toString())) {
			map.put("needUpdate", 0);
		}
		if (StringUtil.isNotEmpty(storeNameChannel)) {
			String sqlLink = "SELECT link FROM bw_market_load  WHERE market_name ='" + storeNameChannel + "'";
			String link = sqlMapper.selectOne(sqlLink, String.class);
			if (StringUtil.isEmpty(link)) {
				map.put("apkDownPath", SystemConstant.APK_Down_Path);
			} else {
				map.put("apkDownPath", link);
			}
		} else {
			map.put("apkDownPath", SystemConstant.APK_Down_Path);
		}
		return map;
	}

}
