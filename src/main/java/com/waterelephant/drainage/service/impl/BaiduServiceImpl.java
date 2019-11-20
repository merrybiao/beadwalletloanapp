package com.waterelephant.drainage.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.drainage.baidu.util.Constant;
import com.waterelephant.drainage.service.BaiduService;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.CommUtils;

@Service
public class BaiduServiceImpl implements BaiduService {

	@Autowired
	private BwBorrowerService bwBorrowerService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	private Logger logger = LoggerFactory.getLogger(BaiduServiceImpl.class);
	/** 工单编号前缀 */
	private final static String PRE = "B";

	/**
	 * 生成工单号
	 * 
	 * @return 工单号
	 */
	public static String generateOrderNo() {
		StringBuffer orderNo = new StringBuffer(PRE);
		orderNo.append(CommUtils.convertDateToString(new Date(), "yyyyMMddhhmmssSSS"));
		orderNo.append(CommUtils.getRandomNumber(3));
		return orderNo.toString();
	}

	/**
	 * 根据用户身份证号和用户名查询用户信息
	 */
	@Override
	public BwBorrower getUserInfoByIdCardAndName(String idCard, String name) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter(idCard.substring(0, idCard.length() - 5), name);
		return borrower;
	}

	/**
	 * 生成空白工单
	 *
	 * @param borrowerId
	 * @return
	 */
	@Override
	public Long saveOrder(Long borrowerId) {
		BwOrder bwOrder = new BwOrder();
		bwOrder.setBorrowerId(borrowerId);
		bwOrder.setCreateTime(new Date());
		bwOrder.setUpdateTime(new Date());
		bwOrder.setStatusId(1L);
		bwOrder.setProductType(1);
		bwOrder.setOrderNo(generateOrderNo());
		Long orderId = bwOrderService.add(bwOrder);
		return orderId;
	}

	/**
	 * 将工单号与水象工单号绑定
	 *
	 * @author GuoKun
	 * @param orderId
	 * @param orderNo
	 * @return
	 */
	@Override
	public BwOrderRong saveBwROrder(Long orderId, String orderNo) throws Exception {
		BwOrderRong bwOrderRong = new BwOrderRong();
		logger.info("订单号为：" + orderNo);
		bwOrderRong.setOrderId(orderId);
		bwOrderRong.setThirdOrderNo(orderNo);
		bwOrderRong.setChannelId(Long.valueOf(Constant.CHANNEL));
		bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
		bwOrderRongService.save(bwOrderRong);
		return bwOrderRong;
	}

	@Override
	public String getRepayDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 30);
		Date date = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

}
