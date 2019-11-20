package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditRecordService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
/**
 * 授信认证记录
 * @author dinglinhao
 *
 */
@Service
public class BwCreditRecordServiceImpl extends BaseService<BwCreditRecord, Long> implements BwCreditRecordService {

	@Override
	public BwCreditRecord queryByCreditId(Long creditId, Integer creditType) {
		Example example =Example.builder(BwCreditRecord.class)
				.where(Sqls.custom()
						.andEqualTo("relationId", creditId)
						.andEqualTo("creditType", creditType)
						.andEqualTo("dataType", "task")
						.andEqualTo("relationType", 1))
				.orderByDesc("createTime")
				.build();
		List<BwCreditRecord> list = selectByExample(example);
		
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public BwCreditRecord queryByOrderId(Long orderId, Integer creditType) {
		Example example =Example.builder(BwCreditRecord.class)
				.where(Sqls.custom()
						.andEqualTo("relationId", orderId)
						.andEqualTo("creditType", creditType)
						.andEqualTo("dataType", "task")
						.andEqualTo("relationType", 0))
				.orderByDesc("createTime")
				.build();
		List<BwCreditRecord> list = selectByExample(example);
		
		return list.isEmpty()?null:list.get(0);
	}
	
	

}
