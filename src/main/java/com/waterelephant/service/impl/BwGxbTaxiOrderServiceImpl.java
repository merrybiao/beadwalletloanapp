package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.waterelephant.entity.BwGxbTaxiOrder;
import com.waterelephant.gxb.dto.TaxiOrderDto;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwGxbTaxiOrderService;

@Service
public class BwGxbTaxiOrderServiceImpl extends BaseService<BwGxbTaxiOrder, Long> implements BwGxbTaxiOrderService {

	@Override
	public int save(Long uid, List<TaxiOrderDto> orders) throws Exception {
		Assert.notEmpty(orders,"授权订单信息为空~");
		int result =0;
		for(TaxiOrderDto dto : orders) {
			BwGxbTaxiOrder record = new BwGxbTaxiOrder();
			record.setUid(uid);
			record.setActualPayFee(dto.getActualPayFee());
			record.setCurrency(dto.getCurrency());
			record.setBeginTime(dto.getBeginTime());
			record.setCityName(dto.getCityName());
			record.setCreateTime(dto.getCreateTime());
			record.setFinishTime(dto.getFinishTime());
			record.setFromAddress(dto.getFromAddress());
			record.setFromArea(dto.getFromArea());
			record.setFromLat(dto.getFromLat());
			record.setFromLng(dto.getFromLng());
			record.setFromName(dto.getFromName());
			record.setPayStatus(dto.getPayStatus());
			record.setStatus(dto.getStatus());
			record.setToAddress(dto.getToAddress());
			record.setToLat(dto.getToLat());
			record.setToLng(dto.getToLng());
			record.setToName(dto.getToName());
			record.setTotalFee(dto.getTotalFee());
			record.setDistrict(dto.getDistrict());
			record.setDriverCompany(dto.getDriverCompany());
			record.setDriverLevel(dto.getDriverLevel());
			record.setDriverName(dto.getDriverName());
			record.setDriverOrderCnt(dto.getDriverOrderCnt());
			record.setCarTitle(dto.getCarTitle());
			record.setCommentLevel(dto.getCommentLevel());
			record.setInsertTime(new Date());
			int count = insert(record);
			if(count >0)result ++;
			
		}
		 return result;
	}
	
	@Override
	public List<TaxiOrderDto> queryTaxiOrderBySequenceNo(String sequenceNo) throws Exception {
		String sql = "select "
				+ " t.pay_status,"
				+ " t.total_fee,"
				+ " t.currency,"
				+ " t.from_address,"
				+ " t.from_name,"
				+ " t.from_area,"
				+ " t.from_lng,"
				+ " t.from_lat,"
				+ " t.to_address,"
				+ " t.to_name,"
				+ " t.to_lng,"
				+ " t.to_lat,"
				+ " t.city_name,"
				+ " t.district,"
				+ " t.begin_time,"
				+ " t.finish_time,"
				+ " t.actual_pay_fee,"
				+ " t.create_time,"
				+ " t.`status`,"
				+ " t.product_name,"
				+ " t.product_type,"
				+ " t.driver_name,"
				+ " t.driver_company,"
				+ " t.driver_level,"
				+ " t.license_num,"
				+ " t.car_title,"
				+ " t.driver_order_cnt,"
				+ " t.comment_level"
				+ " FROM bw_gxb_taxi_order t"
				+ " JOIN bw_gxb_taxi_baseInfo t1 on t1.id = t.uid"
				+ " WHERE t1.sequence_no = '"+ sequenceNo +"'"
				+ " ORDER BY t.create_time DESC";
		
		return sqlMapper.selectList(sql, TaxiOrderDto.class);
	}

}
