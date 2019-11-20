package com.waterelephant.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.utils.CommUtils;

@Service
public class BwPersonInfoServiceImpl extends BaseService<BwPersonInfo, Long> implements IBwPersonInfoService {

	@Override
	public Long save(BwPersonInfo bwPersonInfo) {
		mapper.insert(bwPersonInfo);
		return bwPersonInfo.getId();
	}

	@Override
	public BwPersonInfo getByIdAndOrderId(Long id, Long orderId) {
		String sql = "select p.* from bw_person_info p where p.id=" + id + " AND p.order_id=" + orderId + "";
		return sqlMapper.selectOne(sql, BwPersonInfo.class);
	}

	@Override
	public int update(BwPersonInfo bwPersonInfo) {
		return mapper.updateByPrimaryKey(bwPersonInfo);
	}

	@Override
	public BwPersonInfo findBwPersonInfoByOrderId(Long orderId) {
		String sql = "select * from bw_person_info p where p.order_id = " + orderId;
		List<BwPersonInfo> list = sqlMapper.selectList(sql, BwPersonInfo.class);
		return CommUtils.isNull(list) ? null : list.get(0);
	}

	@Override
	public BwPersonInfo findBwPersonInfoById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int count(String phone) {
		String sql = "SELECT COUNT(id) FROM bw_person_info WHERE relation_phone = #{phone} OR unrelation_phone= #{phone}";
		return sqlMapper.selectOne(sql, phone, Integer.class);
	}

	@Override
	public Map<String, Object> getOrgId(String cityName) {
		String sql = "select id from sys_organization where org_name LIKE '%" + cityName + "' LIMIT 1";
		return sqlMapper.selectOne(sql);
	}

	@Override
	public Map<String, Object> getCityId(String cityName) {
		String sql = "select id from sys_city where city_name LIKE '%" + cityName + "' LIMIT 1";
		return sqlMapper.selectOne(sql);
	}

	@Override
	public BwPersonInfo getbyAttr(BwPersonInfo bwPersonInfo) {
		return mapper.selectOne(bwPersonInfo);
	}

	@Override
	public int countRelation(String phone, Long userId) {
		String sql = "select count(a.id) from bw_person_info a where a.relation_phone = '" + phone
				+ "' AND a.order_id NOT IN(SELECT b.id FROM bw_order b WHERE b.borrower_id = " + userId + ")";
		return sqlMapper.selectOne(sql, phone, Integer.class);
	}

	@Override
	public int countUnRelation(String phone, Long userId) {
		String sql = "select count(a.id) from bw_person_info a where a.unrelation_phone = '" + phone
				+ "' AND a.order_id NOT IN(SELECT b.id FROM bw_order b WHERE b.borrower_id = " + userId + ")";
		return sqlMapper.selectOne(sql, phone, Integer.class);
	}

	@Override
	public int addBwPersonInfo(BwPersonInfo bwPersonInfo) {
		return mapper.insert(bwPersonInfo);
	}

	@Override
	public BwPersonInfo findBwPersonInfoByOrderId(String orderId) {
		BwPersonInfo bwPersonInfo = new BwPersonInfo();
		bwPersonInfo.setOrderId(Long.parseLong(orderId));
		return mapper.selectOne(bwPersonInfo);
	}

	@Override
	public BwPersonInfo findBwPersonInfoByOrderIdNew(String orderId) {
		String sql = " select * from bw_person_info where order_id=" + orderId + " order by create_time desc limit 1 ";
		return sqlMapper.selectOne(sql, BwPersonInfo.class);
	}

	@Override
	public void add(BwPersonInfo bwPersonInfo) {
		bwPersonInfo.setId(null);
		mapper.insert(bwPersonInfo);

	}

	@Override
	public BwWorkInfo queryWork(Long orderId) {
		String sql = " select * from bw_work_info where order_id=" + orderId;
		return sqlMapper.selectOne(sql, BwWorkInfo.class);
	}

	@Override
	public void addWork(BwWorkInfo bwWorkInfo) {
		String sql = "INSERT into bw_work_info(order_id,call_time,com_name,industry,income,work_years,create_time,update_time) VALUES (#{orderId},#{callTime},#{comName},#{industry},#{income},#{workYears},now(),now())";
		sqlMapper.insert(sql, bwWorkInfo);

	}

}
