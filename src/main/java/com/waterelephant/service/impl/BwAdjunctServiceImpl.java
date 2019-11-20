package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.exception.BusException;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwAdjunctServiceImpl extends BaseService<BwAdjunct, Long> implements IBwAdjunctService {

	@Autowired
	private BwOrderService bwOrderService;

	@Override
	public Long save(BwAdjunct bwAdjunct) {
		mapper.insert(bwAdjunct);
		return bwAdjunct.getId();
	}

	@Override
	public int update(BwAdjunct bwAdjunct) {
		return mapper.updateByPrimaryKey(bwAdjunct);
	}

	@Override
	public BwAdjunct getById(Long id, Long orderId) {
		BwAdjunct baj = new BwAdjunct();
		baj.setId(id);
		baj.setOrderId(orderId);
		return mapper.selectOne(baj);
	}

	@Override
	public List<BwAdjunct> findBwAdjunctByOrderId(Long orderId) {
		BwAdjunct adjunct = new BwAdjunct();
		adjunct.setOrderId(orderId);
		return mapper.select(adjunct);
	}

	@Override
	public BwAdjunct findBwAdjunctByAttr(BwAdjunct bwAdjunct) {
		// return mapper.selectOne(bwAdjunct); // 数据库存在多条记录的情况，因此改为以下查询方式（liuDaodao）
		List<BwAdjunct> bwAdjunctList = mapper.select(bwAdjunct);
		if (bwAdjunctList != null && bwAdjunctList.size() > 0) {
			return bwAdjunctList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<BwAdjunct> findBwAdjunctPhotoByOrderId(Long orderId) {
		Example example = new Example(BwAdjunct.class);
		List<Object> photoAdjunctTypeList = new ArrayList<>();
		photoAdjunctTypeList.add("1");// 身份证正面
		photoAdjunctTypeList.add("2");// 身份证反面
		photoAdjunctTypeList.add("3");// 持证照
		photoAdjunctTypeList.add("4");// 工牌
		example.createCriteria().andEqualTo("orderId", orderId).andIn("adjunctType", photoAdjunctTypeList);
		return mapper.selectByExample(example);
	}

	/**
	 * 活体认证 - 连表查询附件和来源 code0088
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findBwAdjunctAndVerifySource(Long orderId) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(" select");
		sBuilder.append(" 	a.id as 'id',");
		sBuilder.append(" 	a.adjunct_type as 'adjunctType',");
		sBuilder.append(" 	a.adjunct_path as 'adjunctPath',");
		sBuilder.append(" 	a.adjunct_desc as 'adjunctDesc',");
		sBuilder.append(" 	a.borrower_id as 'borrowerId',");
		sBuilder.append(" 	a.order_id as 'orderId',");
		sBuilder.append(" 	a.create_time as 'createTime',");
		sBuilder.append(" 	a.update_time as 'updateTime',");
		sBuilder.append(" 	a.photo_state as 'photoState',");
		sBuilder.append(" 	IFNULL(b.verify_source,'1') as 'verifySource'");
		sBuilder.append(" from");
		sBuilder.append(" bw_adjunct a left join bw_verify_source b on a.id = b.adjunct_id");
		sBuilder.append(" where a.order_id = " + orderId);

		return this.sqlMapper.selectList(sBuilder.toString());
	}

	@Override
	public List<BwAdjunct> findBwAdjunctByOrderIdNew(Long orderId) {
		String sql = "SELECT * FROM bw_adjunct a WHERE a.order_id = #{orderId} AND a.adjunct_type in (11,13) ";
		return sqlMapper.selectList(sql, orderId, BwAdjunct.class);
	}

	@Override
	public List<BwAdjunct> adjunctType(Long id) throws BusException {
		if (CommUtils.isNull(id)) {
			throw new BusException("查询工单Id为空");
		}
		String sql = " select * from bw_adjunct where  adjunct_type between 1 and 10 and order_id=" + id;
		return sqlMapper.selectList(sql, BwAdjunct.class);
	}

	@Override
	public void add(BwAdjunct bwAdjunct) {
		bwAdjunct.setId(null);
		mapper.insert(bwAdjunct);

	}

	@Override
	public BwAdjunct findBwAdjunctByOrderId(Long orderId, int i) {
		String sql = "select * from bw_adjunct where order_id =" + orderId + " AND adjunct_type = " + i + " limit 0,1";
		BwAdjunct adjunct = this.sqlMapper.selectOne(sql, BwAdjunct.class);
		if (adjunct != null) {
			adjunct.setAdjunctPath(SystemConstant.IMG_URL + adjunct.getAdjunctPath());
		}
		return adjunct;
	}

	@Override
	public Long save(String localName, String sessionid, String Id) {
		BwAdjunct bwAdjunct = new BwAdjunct();
		bwAdjunct.setAdjunctPath(localName);
		bwAdjunct.setAdjunctType(Integer.valueOf(sessionid));
		bwAdjunct.setCreateTime(new Date());
		if (StringUtils.isNotEmpty(Id)) {
			bwAdjunct.setBorrowerId(this.queryBorrowerIdByOrderId(Long.valueOf(Id)));
			bwAdjunct.setOrderId(Long.valueOf(Id));
		}
		this.mapper.insert(bwAdjunct);
		return bwAdjunct.getId();
	}

	@Override
	public Long queryBorrowerIdByOrderId(Long orderId) {
		String sql = "select borrower_id from bw_order where id = " + orderId;
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public List<BwAdjunct> findAdjunctByOrderIdAndAdjunctType(Long orderId, Integer adjunctType) {
		BwAdjunct bwAdjunct = new BwAdjunct();
		bwAdjunct.setOrderId(orderId);
		bwAdjunct.setAdjunctType(adjunctType);
		return mapper.select(bwAdjunct);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwAdjunctService#getPhotoStateByOrderId(java.lang.Long)
	 */
	@Override
	public int getPhotoStateByOrderId(Long orderId) {
		String sql = "select photo_state from bw_adjunct where order_id = #{orderId} order by create_time limit 1";
		Map<String, Object> selectOne = sqlMapper.selectOne(sql, orderId);
		int photoState = 0;
		if (!CommUtils.isNull(selectOne) && !selectOne.isEmpty()) {
			photoState = (int) selectOne.get("photo_state");
		}
		return photoState;
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwAdjunctService#findBwAdjunct(java.lang.Long)
	 */
	@Override
	public BwAdjunct findBwAdjunct(Long orderId) {
		BwOrder order = bwOrderService.selectByPrimaryKey(orderId);
		BwAdjunct bwAdjunct = new BwAdjunct();
		if (StringUtil.toInteger(order.getProductType()) == 1) {
			bwAdjunct = sqlMapper
					.selectOne("select * from bw_adjunct where (adjunct_type=13 or adjunct_type=17) and order_id="
							+ orderId + " order by `create_time` DESC limit 1", BwAdjunct.class);
		}
		if (StringUtil.toInteger(order.getProductType()) == 2) {
			bwAdjunct = sqlMapper.selectOne("select * from bw_adjunct where adjunct_type=17 and order_id=" + orderId
					+ " order by `create_time` DESC limit 1", BwAdjunct.class);
		}
		return bwAdjunct;
	}
	
	
	@Override
	public Map<Integer,BwAdjunct> queryBwAdjunctByOrderId(Long orderId) {
		Example example = new Example(BwAdjunct.class);
		example.createCriteria().andEqualTo("orderId", orderId);
		List<BwAdjunct> list =  mapper.selectByExample(example);
		Map<Integer,BwAdjunct> result = null;
		if(null != list && !list.isEmpty()) {
			result = new HashMap<>();
			for(BwAdjunct entity : list) {
				result.put(entity.getAdjunctType(), entity);
			}
		}
		return result;
	}

}
