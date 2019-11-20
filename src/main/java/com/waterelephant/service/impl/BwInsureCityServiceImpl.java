package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.beadwallet.service.entity.response.CityRspData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entiyt.middle.City;
import com.beadwallet.service.serve.BeadWalletInsureService;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwInsureCityService;

@Service
public class BwInsureCityServiceImpl extends BaseService<com.waterelephant.entity.BwInsureCity,Long> implements IBwInsureCityService{

	@Override
	public boolean create(){
		boolean result = false;
		//获取社保可支持城市列表
		Response<CityRspData> cityRes = BeadWalletInsureService.getValidCity();
		List<City> cities = cityRes.getObj().getCity_list();
		List<com.waterelephant.entity.BwInsureCity> list = new ArrayList<>();
		for (City insureCity : cities) {
			com.waterelephant.entity.BwInsureCity city = new com.waterelephant.entity.BwInsureCity();
			city.setInsureCode(insureCity.getRong_city_id());
			city.setName(insureCity.getRong_city_name());
			list.add(city);
		}
		String sql = "insert into bw_city(id,insure_code,name) values(#{id},#{insureCode},#{name})";
		for (com.waterelephant.entity.BwInsureCity city : list) {
			if (sqlMapper.insert(sql, city)>0) {
				result = true;
			}
		}
//		for (com.waterelephant.entity.City city : list) {
//			if (mapper.insert(city)>0) {
//				result = true;
//			}
//		}
		return result;
	}

	@Override
	public List<com.waterelephant.entity.BwInsureCity> getCitys() {
		String sql = "SELECT * FROM bw_insure_city WHERE is_disable = 0";
		return sqlMapper.selectList(sql, com.waterelephant.entity.BwInsureCity.class);
	}

	@Override
	public com.waterelephant.entity.BwInsureCity getCity(Long id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	

}
