package com.waterelephant.rongCarrier.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.beadwallet.service.rong360.entity.response.City_list;
import com.beadwallet.service.rong360.entity.response.RongValidCityJSON;
import com.beadwallet.service.rong360.service.BeadWalletRongCarrierService;
import com.waterelephant.rongCarrier.entity.RongValidCity;
import com.waterelephant.rongCarrier.service.RongValidCityService;
import com.waterelephant.service.BaseService;

/**
 * 融360 - 可用城市（code0084）
 * 
 * 
 * Module:
 * 
 * RongValidCityServiceImpl.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <融360 - 可用城市>
 */
@Service
public class RongValidCityServiceImpl extends BaseService<RongValidCity, Long> implements RongValidCityService {
	@Override
	public boolean save(RongValidCity rongValidCity) {
		return mapper.insert(rongValidCity) > 0;
	}

	/**
	 * 融360 - 可用城市（社保） - 批量添加
	 * 
	 * @see com.waterelephant.rongCarrier.service.RongValidCityService#saveBatch(java.util.List)
	 */
	@Override
	public boolean saveBatchOfSheBao() throws Exception {

		// 第一步：从接口获取数据
		RongValidCityJSON rongValidCityJSON = BeadWalletRongCarrierService.validCityOfSheBao();
		if (rongValidCityJSON == null) {
			return false;
		}

		// 第二步：解析数据
		if (200 != rongValidCityJSON.getError()) {
			return false;
		}
		List<City_list> city_lists = rongValidCityJSON.getCrawler_api_insure_getValidCity_response().getCity_list();

		// 第三步：删除数据库中旧数据
		if (city_lists != null && city_lists.size() > 0) {
			String deleteSQL = "DELETE FROM `bw_rong_validCity` WHERE `type` = " + RongValidCity.TYPE_SHEBAO;
			sqlMapper.delete(deleteSQL);
		}

		// 第四步：插入数据库
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("INSERT INTO bw_rong_validCity");
		sBuilder.append("(");
		sBuilder.append("  `city_id`,");
		sBuilder.append("  `city_name`,");
		sBuilder.append("  `province_name`,");
		sBuilder.append("  `type`");
		sBuilder.append(")");
		sBuilder.append("VALUES");
		for (int i = 0; i < city_lists.size(); i++) {
			City_list city_list = city_lists.get(i);
			sBuilder.append("(");
			sBuilder.append("	'" + city_list.getRong_city_id() + "',");
			sBuilder.append("	'" + city_list.getRong_city_name() + "',");
			sBuilder.append("	'" + city_list.getProvince_name() + "',");
			sBuilder.append("	'" + RongValidCity.TYPE_SHEBAO + "'");
			sBuilder.append(")");
			if (i != (city_lists.size() - 1)) {
				sBuilder.append(",");
			}
		}
		return sqlMapper.insert(sBuilder.toString()) > 0;
	}

	@Override
	public boolean deleteZ(RongValidCity rongValidCity) {
		return mapper.delete(rongValidCity) > 0;
	}

	/**
	 * 融360 - 根据城市名判断是否可用
	 * 
	 * @param cityName
	 * @return
	 */
	@Override
	public boolean findByCityNameOfLike(String cityName, int type) {
		if (StringUtils.isEmpty(cityName) == true) {
			return false;
		}
		cityName = cityName.trim(); // 去空格
		if (cityName.length() > 2) { // 取前2个字到数据库中进行模糊查询
			cityName = cityName.substring(0, 2);
		}
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select");
		sBuilder.append("  `id`,");
		sBuilder.append("  `city_id`,");
		sBuilder.append("  `city_name`,");
		sBuilder.append("  `province_name`,");
		sBuilder.append("  `type`");
		sBuilder.append(" from  bw_rong_validCity");
		sBuilder.append(" where city_name like '%" + cityName + "%'");
		sBuilder.append(" and `type` = " + type);
		return sqlMapper.selectList(sBuilder.toString(), RongValidCity.class).size() > 0;
	}

	/**
	 * 融360 - 查询可用省份
	 * 
	 * @see com.waterelephant.rongCarrier.service.RongValidCityService#findList(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findProvinceList(int type) {
		String sql = "SELECT DISTINCT(province_name) as provinceName  FROM bw_rong_validCity WHERE `type` = " + type;
		List<Map<String, Object>> mapList = sqlMapper.selectList(sql);
		return mapList;
	}

	/**
	 * 融360 - 查询可用城市
	 * 
	 * @see com.waterelephant.rongCarrier.service.RongValidCityService#findList(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findCityList(int type, String province) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(" SELECT DISTINCT(city_name) AS cityName");
		sBuilder.append(" FROM bw_rong_validCity");
		sBuilder.append(" WHERE `type` = " + type);
		sBuilder.append(" AND province_name = '" + province + "'");
		List<Map<String, Object>> mapList = sqlMapper.selectList(sBuilder.toString());
		return mapList;
	}
}
