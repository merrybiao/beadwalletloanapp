package com.waterelephant.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwVerifySource;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwVerifySourceService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwVerifySourceServiceImpl extends BaseService<BwVerifySource, Long> implements BwVerifySourceService {
	
	private Logger logger = LoggerFactory.getLogger(BwVerifySourceServiceImpl.class);
	
	/**
	 * 根据orderId查询出认证来源 code0088
	 */
	@Override
	public BwVerifySource getVerifySource(Long photoId) {
		String sql = "select verify_source from bw_verify_source a where a.adjunct_id = " + photoId;
		return sqlMapper.selectOne(sql, BwVerifySource.class);
	}

	/**
	 * 添加或更新
	 */
	@Override
	public boolean saveOrUpdateVerfiySource(Long photoId, Long borrowerId, Long orderId, Long verifySource) {
		BwVerifySource bwVerifySource = getVerifySource(photoId);
		if (bwVerifySource == null) {
			logger.info("开始保存bwVerifySource信息...");
			StringBuilder sqlBuid = new StringBuilder();
			sqlBuid.append("insert into bw_verify_source(adjunct_id,borrower_id,order_id,verify_source) value(");
			sqlBuid.append(photoId);
			sqlBuid.append(",");
			sqlBuid.append(borrowerId);
			sqlBuid.append(",");
			sqlBuid.append(orderId);
			sqlBuid.append(",");
			sqlBuid.append(verifySource);
			sqlBuid.append(")");
			logger.info("结束保存bwVerifySource信息...");
			return sqlMapper.insert(sqlBuid.toString()) > 0;
		} else {
			logger.info("开始更新bwVerifySource信息,入参：" + String.valueOf(verifySource));
			String sql = "update bw_verify_source set verify_source = " + verifySource + " where adjunct_id = " + photoId;
			logger.info("结束更新bwVerifySource信息：" + String.valueOf(verifySource) + "，保存结果：" + sqlMapper.update(sql));
			return sqlMapper.update(sql) > 0;
		}
	}
	
	@Override
	public BwVerifySource queryBwVerifySourceByAdjunctId(Long adjunctId) {
		Example example = new Example(BwVerifySource.class);
		example.createCriteria().andEqualTo("adjunctId", adjunctId);
		
		java.util.List<BwVerifySource> list = selectByExample(example);
		return null == list || list.isEmpty() ? null : list.get(0);
	}
	

	@Override
	public boolean saveOrUpdateBwVerifySource(Long adjunctId,Long borrowerId,Long orderId,Long source) {
		BwVerifySource verifySource = queryBwVerifySourceByAdjunctId(adjunctId);
		if(null == verifySource) {
			verifySource = new BwVerifySource();
			verifySource.setAdjunctId(adjunctId);
			verifySource.setBorrowerId(borrowerId);
			verifySource.setOrderId(orderId);
			verifySource.setVerifySource(source);
			return insert(verifySource) >0;
		} else {
			verifySource.setAdjunctId(adjunctId);
			verifySource.setBorrowerId(borrowerId);
			verifySource.setOrderId(orderId);
			verifySource.setVerifySource(source);
			return updateByPrimaryKeySelective(verifySource)>0;
		}
		
	}

	
}
