package com.waterelephant.installment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.installment.dto.AuthInfoDto;
import com.waterelephant.installment.service.AuthInfoService;
import com.waterelephant.installment.service.OrderService;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderStatusRecordService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.StringUtil;

import tk.mybatis.mapper.entity.Example;

/**
 * AuthInfoServiceImpl.java
 * 
 * @author 胡林浩
 * @since JDK 1.8
 * @version 1.0
 * @description: 做验证信息的Service
 */
@Service
public class AuthInfoServiceImpl extends BaseService<BwOrder, Long> implements AuthInfoService {

	private Logger logger = Logger.getLogger(AuthInfoServiceImpl.class);
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private OrderService orderService;
	@Autowired
	BwOrderStatusRecordService bwOrderStatusRecordService;

	// 查询工单认证信息
	@Override
	public Map<String, Object> findAutherized(Long borrowerId, int productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AuthInfoDto> list = new ArrayList<AuthInfoDto>();
		// 查询工单
		BwOrder order = orderService.getOrderByProductType(borrowerId, productType);
		if (null != order.getId()) {
			StringBuilder sql = new StringBuilder("select a.auth_type,a.photo_state from bw_order_auth a where 1=1 ");
			sql.append(" and a.order_id = ").append(order.getId());
			list = sqlMapper.selectList(sql.toString(), AuthInfoDto.class);
		}
		map.put("list", list);
		map.put("orderStatus", order.getStatusId());
		return map;
	}

	@Override
	public Map<String, Object> getBorrowerType(Long borrowerId, int productType) {
		// 查询该用户是否在黑名单
		String blackSql = "select sort from bw_blacklist where card = (select id_card from bw_borrower where id = "
				+ borrowerId + ") and status = 1 ";
		Integer sort = sqlMapper.selectOne(blackSql, Integer.class);
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询当前借款人是否存在被拒工单，如果是永久被拒则永不创建工单，非永久被拒则三个月之后再次创建工单
		Example example1 = new Example(BwOrder.class);
		example1.createCriteria().andEqualTo("statusId", 7l).andEqualTo("borrowerId", borrowerId)
				.andEqualTo("productType", productType);
//		example1.setOrderByClause(" createTime desc ");
		example1.orderBy("createTime").desc();
		List<BwOrder> rejectList = mapper.selectByExample(example1);
		boolean isExpire = false;
		String whiteTime = "";
		if (!CommUtils.isNull(rejectList)) {
			Long rejectOId = rejectList.get(0).getId();
			logger.info("借款人" + borrowerId + "的拒绝工单id：" + rejectOId);
			// 根据工单id查找拒绝记录
			BwRejectRecord record = new BwRejectRecord();
			record.setOrderId(rejectOId);
			record = bwRejectRecordService.findBwRejectRecordByAtta(record);
			logger.info("借款人" + borrowerId + ",被拒信息:" + record);
			record = record == null ? new BwRejectRecord() : record;
			Integer rejectType = StringUtil.toInteger(record.getRejectType());
			if (rejectType == 1) {
				sort = 2;
			}
			if (rejectType == 0) {
				sort = 1;
			}
			Date blackTime = record.getCreateTime();// 黑名单时间
			Date whiteDate = MyDateUtils.addDays(blackTime, ActivityConstant.BLACKTIME);
			isExpire = DateUtil.isBeforeTime(whiteDate, new Date());
			whiteTime = DateUtil.getDateString(whiteDate, "yyyy年MM月dd日");
		}
		map.put("sort", StringUtil.toInteger(sort));
		map.put("isExpire", isExpire);
		map.put("whiteTime", StringUtil.toString(whiteTime));
		return map;
	}

	@Override
	public List<Map<String, Object>> getBwOrderStatusRecordByOrderId(String orderId) {
		if (null == orderId) {
			return new ArrayList<Map<String, Object>>();
		}
		String sql = "select id, order_id,order_status,msg,dialog_style  from bw_order_status_record  where order_id = "
				+ orderId + " and effective = " + ActivityConstant.BWORDERSTATUSRECORD_EFFECTIVE.EFFECTIVE_1;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = sqlMapper.selectList(sql);
		return list;
	}

	@Override
	public void updateRecord(Integer id) {
		sqlMapper.update("update bw_order_status_record set effective = "
				+ ActivityConstant.BWORDERSTATUSRECORD_EFFECTIVE.EFFECTIVE_0 + "  where id = " + id + "");
		logger.info("ID：" + id + "，弹框 变为无效");
	}
}
