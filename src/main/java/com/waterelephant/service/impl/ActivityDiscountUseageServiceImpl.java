package com.waterelephant.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.ActivityDiscountUseage;
import com.waterelephant.service.ActivityDiscountUseageService;
import com.waterelephant.service.BaseService;

/**
 * 活动消费记录表
 * 
 * @author 王坤 E-mail:
 * @date 创建时间：2017年3月14日 下午5:02:37
 * @version 1.0
 * @parameter
 * @since
 * @return
 */

@Service
public class ActivityDiscountUseageServiceImpl extends BaseService<ActivityDiscountUseage, Long>
		implements ActivityDiscountUseageService {

	@Override
	public boolean addActivityDiscountUseageService(ActivityDiscountUseage activityDiscountUseage) {
		int result = mapper.insert(activityDiscountUseage);
		// System.out.println("activityDiscountUseage:-----------" + result);
		return result > 0 ? true : false;
	}

	@Override
	public Double getDiscountUseageByRepaymentPlanId(Long orderId) {
		String sql = "select amount from activity_discount_useage u RIGHT  OUTER JOIN activity_discount_distribute d on u.distribute_id = d.distribute_id where u.order_id="
				+ orderId;
		Map<String, Object> selectOne = sqlMapper.selectOne(sql);
		Double amount = 0.00;
		if (selectOne != null) {
			amount = Double.parseDouble(selectOne.get("amount").toString());
		}
		return amount;
	}

	/**
	 * 
	 * @see com.waterelephant.service.ActivityDiscountUseageService#deleteActivityDiscountUseage(java.lang.Long)
	 */
	@Override
	public void deleteActivityDiscountUseage(Long orderId) {
		sqlMapper.delete("delete from activity_discount_useage where order_id=" + orderId);
	}

}
