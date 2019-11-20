package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.utils.CommUtils;


@Service
public class BwWorkInfoServiceImpl extends BaseService<BwWorkInfo, Long> implements IBwWorkInfoService{
	@Autowired
	private BwBorrowerService bwBorrowerService;

	@Override
	public int save(BwWorkInfo bwWorkInfo,Long bId){
		int num = mapper.insert(bwWorkInfo);
		// 修改借款人状态
		if (num > 0) {
			BwBorrower bb = bwBorrowerService.findBwBorrowerById(bId);
			if (!CommUtils.isNull(bb)) {
				bb.setAuthStep(3);
				num = bwBorrowerService.updateBwBorrower(bb);
			}
		}
		return num;
	}

	@Override
	public int update(BwWorkInfo bwWorkInfo){
		return mapper.updateByPrimaryKey(bwWorkInfo);
	}

	@Override
	public BwWorkInfo findBwWorkInfoById(Object obj){
		BwWorkInfo bwi = new BwWorkInfo();
		bwi.setId(Long.parseLong(obj.toString()));
		return mapper.selectOne(bwi);
	}

	@Override
	public List<BwWorkInfo> findByIdAndOrderId(Long id, Long orderId){
//		Example example  = new Example(BwInsureAccout.class);
//		example.or().andEqualTo("", id).andEqualTo("", orderId);
//		return mapper.selectByExample(example);
		String sql = "select * from bw_work_info where id = '"+id+"' and order_id = '"+orderId+"'";
		return sqlMapper.selectList(sql,BwWorkInfo.class);
	}
	
	@Override
	public BwWorkInfo findBwWorkInfoByAttr(BwWorkInfo workInfo) {
		return mapper.selectOne(workInfo);
	}
	
	
	public BwWorkInfo findBwWorkInfoByOrderId(Long orderId){
		String sql = "select o.* from bw_work_info o where o.order_id=#{orderId} LIMIT 1";
		return sqlMapper.selectOne(sql,orderId,BwWorkInfo.class);
	}

	@Override
	public int addBwWorkInfo(BwWorkInfo bwWorkInfo) {
		return mapper.insert(bwWorkInfo);
	}
	
	
}
