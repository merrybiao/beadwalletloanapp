package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwGxbTaxiBaseInfo;
import com.waterelephant.gxb.dto.TaxiDataDto;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwGxbTaxiBaseInfoService;
@Service
public class BwGxbTaxiBaseInfoServiceImpl  extends BaseService<BwGxbTaxiBaseInfo, Long> implements BwGxbTaxiBaseInfoService {

	@Override
	public BwGxbTaxiBaseInfo save(String sequenceNo, TaxiDataDto dto) throws Exception {
		
		BwGxbTaxiBaseInfo record = new BwGxbTaxiBaseInfo();
		record.setSequenceNo(sequenceNo);
		record.setCompany(dto.getCompany());
		record.setGender(dto.getGender());
		record.setLevel(dto.getLevel());
		record.setLevelName(dto.getLevelName());
		record.setEmail(dto.getEmail());
		record.setName(dto.getName());
		record.setNickName(dto.getNickname());
		record.setPhone(dto.getPhone());
		record.setIdCard(dto.getIdCard());
		record.setStatus(dto.getStatus());
		record.setSesameScore(dto.getSesameScore());
		record.setLastUpdateDate(dto.getLastUpdateDate());
		record.setInsertTime(new Date());
		
		insert(record);
		return record;
	}

	@Override
	public TaxiDataDto queryTaxiDataBySequenceNo(String sequenceNo) throws Exception {
		
		String sql = "SELECT "
				+ " t.company,"
				+ " t.`name`,"
				+ " t.id_card,"
				+ " t.`status`,"
				+ " t.phone,"
				+ " t.nick_name,"
				+ " t.gender,"
				+ " t.`level`,"
				+ " t.level_name,"
				+ " t.email,"
				+ " t.sesame_score,"
				+ " t.last_update_date"
				+ " FROM bw_gxb_taxi_baseInfo t "
				+ " WHERE t.sequence_no = '"+ sequenceNo +"'";
		
		return sqlMapper.selectOne(sql, TaxiDataDto.class);
	}

}
