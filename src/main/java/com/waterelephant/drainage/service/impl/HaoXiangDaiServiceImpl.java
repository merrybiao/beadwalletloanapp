/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.waterelephant.drainage.entity.haoxiangdai.HaoXiangDaiRequest;
import com.waterelephant.drainage.entity.haoxiangdai.HaoXiangDaiResponse;
import com.waterelephant.drainage.entity.haoxiangdai.PushUserVo;
import com.waterelephant.drainage.service.HaoXiangDaiService;
import com.waterelephant.entity.BwBlacklist;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.third.utils.ThirdConstant;
import com.waterelephant.third.utils.ThirdUtil;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.MD5Util;

import tk.mybatis.mapper.entity.Example;

/**
 * Module: 
 * HaoXiangDaiServiceImpl.java 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class HaoXiangDaiServiceImpl implements HaoXiangDaiService {
	private Logger logger = LoggerFactory.getLogger(HaoXiangDaiServiceImpl.class);
	private static String SIGN_KEY = "third.key.channel_";

	@Autowired
	private IBwOrderChannelService bwOrderChannelService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private BwBlacklistService bwBlacklistService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	
	/**
	 * 水象云进件接口
	 */
	@Override
	public HaoXiangDaiResponse savePushUser(long sessionId, HaoXiangDaiRequest haoXiangDaiRequest) {
		HaoXiangDaiResponse haoXiangDaiResponse = new HaoXiangDaiResponse();
		logger.info(sessionId + "：开始水象云进件接口");
		logger.info(sessionId + "水象云进件接口" + JSON.toJSONString(haoXiangDaiRequest));
		try {
			Map<String, Object> map = new HashMap<>();
			String requests = haoXiangDaiRequest.getRequest();
			String channelCode = haoXiangDaiRequest.getAppId();
			String sign = haoXiangDaiRequest.getSign();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			if (orderChannel == null) {
				haoXiangDaiResponse.setCode(ThirdResponse.CODE_PARAMETER);
				haoXiangDaiResponse.setMsg("渠道不存在");
				logger.info(sessionId + "结束水象云进件接口" + JSON.toJSONString(haoXiangDaiResponse));
				return haoXiangDaiResponse;
			}
			// 验签
			if (!checkSign(requests, channelCode, sign, orderChannel.getId())) {
				haoXiangDaiResponse.setCode(ThirdResponse.CODE_PARAMETER);
				haoXiangDaiResponse.setMsg("验签失败");
				logger.info(sessionId + "结束水象云进件接口" + JSON.toJSONString(haoXiangDaiResponse));
				return haoXiangDaiResponse;
			}
			PushUserVo pushUserVo = JSON.parseObject(requests, PushUserVo.class);
			String phone = pushUserVo.getMobile();
			String penetrate = pushUserVo.getPenetrate();
			if (StringUtils.isBlank(phone) || !regex(phone)) {
				haoXiangDaiResponse.setCode(ThirdResponse.CODE_PARAMETER);
				haoXiangDaiResponse.setMsg("手机号码错误");
				logger.info(sessionId + "水象云进件接口：" + JSON.toJSONString(haoXiangDaiResponse));
				return haoXiangDaiResponse;
			}
			// 是否黑名单
			Example example = new Example(BwBlacklist.class);
			example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("phone",
					phone.toUpperCase());
			List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
			if (CommUtils.isNull(desList) == false) {
				map.put("isCanLoan", 0);// 是否可以借款（0：否，1：是）
				map.put("rejectReason", 1);// 拒绝原因（1 黑名单，2 在贷，3 其他）
				map.put("remark", "黑名单用户");
				if (StringUtils.isNotBlank(penetrate)) {
					map.put("penetrate", penetrate);
				}
				haoXiangDaiResponse.setCode(ThirdResponse.CODE_NETERROR);
				haoXiangDaiResponse.setMsg("失败");
				haoXiangDaiResponse.setResponse(map);
				logger.info(sessionId + "结束水象云进件接口：" + JSON.toJSONString(haoXiangDaiResponse));
				return haoXiangDaiResponse;
			}
			// 是否是老用户
			BwBorrower borrower = bwBorrowerService.oldUserFilter3(phone);
			// boolean isStock = false;
			if (borrower == null) {
				map.put("isStock", 0);// 存量用户（0：否，1：是）
				// 创建借款人
				String password = ThirdUtil.getRandomPwd();
				borrower = new BwBorrower();
				borrower.setPhone(phone);
				borrower.setPassword(CommUtils.getMD5(password.getBytes()));
				borrower.setAuthStep(1);
				borrower.setFlag(1);
				borrower.setState(1);
				borrower.setChannel(orderChannel.getId()); // 表示该借款人来源
				borrower.setCreateTime(Calendar.getInstance().getTime());
				borrower.setUpdateTime(Calendar.getInstance().getTime());
				bwBorrowerService.addBwBorrower(borrower);
			} else {
				// isStock = true;
				map.put("isStock", 1);// 存量用户（0：否，1：是）

				// 是否进行中的订单
				Long count = bwOrderService.findProOrder(borrower.getId() + "");
				if (count != null && count.intValue() > 0) {
					map.put("isCanLoan", 0);// 是否可以借款（0：否，1：是）
					map.put("rejectReason", 2);// 拒绝原因（1 黑名单，2 在贷，3 其他）
					map.put("remark", "存在进行中订单");
					if (StringUtils.isNotBlank(penetrate)) {
						map.put("penetrate", penetrate);
					}
					haoXiangDaiResponse.setCode(ThirdResponse.CODE_NETERROR);
					haoXiangDaiResponse.setMsg("失败");
					haoXiangDaiResponse.setResponse(map);
					logger.info(sessionId + "结束水象云进件接口方法：" + JSON.toJSONString(haoXiangDaiResponse));
					return haoXiangDaiResponse;
				}
				// 是否有被拒记录
				BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());
				if (!CommUtils.isNull(record)) {
					map.put("isCanLoan", 0);// 是否可以借款（0：否，1：是）
					map.put("rejectReason", 3);// 拒绝原因（1 黑名单，2 在贷，3 其他）
					map.put("remark", "被机构审批拒绝中");
					if (StringUtils.isNotBlank(penetrate)) {
						map.put("penetrate", penetrate);
					}
					haoXiangDaiResponse.setCode(ThirdResponse.CODE_NETERROR);
					haoXiangDaiResponse.setMsg("失败");
					haoXiangDaiResponse.setResponse(map);
					logger.info(sessionId + "结束水象云进件接口方法" + JSON.toJSONString(haoXiangDaiResponse));
					return haoXiangDaiResponse;
				}
				map.put("isCanLoan", 0);// 是否可以借款（0：否，1：是）
				map.put("rejectReason", 3);// 拒绝原因（1 黑名单，2 在贷，3 其他）
				map.put("remark", "已存在用户");
				if (StringUtils.isNotBlank(penetrate)) {
					map.put("penetrate", penetrate);
				}
				haoXiangDaiResponse.setCode(ThirdResponse.CODE_NETERROR);
				haoXiangDaiResponse.setMsg("失败");
				haoXiangDaiResponse.setResponse(map);
				logger.info(sessionId + "结束水象云进件接口方法" + JSON.toJSONString(haoXiangDaiResponse));
				return haoXiangDaiResponse;

			}
			map.put("isCanLoan", 1);// 是否可以借款（0：否，1：是）
			map.put("rejectReason", 0);// 拒绝原因（1 黑名单，2 在贷，3 其他）
			if (StringUtils.isNotBlank(penetrate)) {
				map.put("penetrate", penetrate);
			}
			// // 判断是否有草稿状态的订单
			// List<BwOrder> boList = null;
			// BwOrder bwOrder = new BwOrder();
			// if (isStock) {
			// bwOrder.setBorrowerId(borrower.getId());
			// bwOrder.setStatusId(1L);
			// boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
			// }
			// if (boList != null && boList.size() > 0) {
			// for (BwOrder bwOrder_ : boList) {
			// if (orderChannel.getId() == bwOrder_.getChannel()) {
			// map.put("isCanLoan", 0);// 是否可以借款（0：否，1：是）
			// map.put("rejectReason", 3);// 拒绝原因（1 黑名单，2 在贷，3 其他）
			// map.put("remark", "已经存在用户");
			// if (StringUtils.isNotBlank(penetrate)) {
			// map.put("penetrate", penetrate);
			// }
			// haoXiangDaiResponse.setCode(ThirdResponse.CODE_NETERROR);
			// haoXiangDaiResponse.setMsg("失败");
			// haoXiangDaiResponse.setResponse(map);
			// logger.info(sessionId + "结束水象云进件接口方法" + JSON.toJSONString(haoXiangDaiResponse));
			// return haoXiangDaiResponse;
			// }
			// }
			// bwOrder = boList.get(0);
			// bwOrder.setChannel(orderChannel.getId());
			// bwOrder.setUpdateTime(Calendar.getInstance().getTime());
			// bwOrderService.updateBwOrder(bwOrder);
			// } else {
			// bwOrder = new BwOrder();
			// bwOrder.setOrderNo(OrderUtil.generateOrderNo());
			// bwOrder.setBorrowerId(borrower.getId());
			// bwOrder.setStatusId(1L);
			// bwOrder.setCreateTime(Calendar.getInstance().getTime());
			// bwOrder.setUpdateTime(Calendar.getInstance().getTime());
			// bwOrder.setChannel(orderChannel.getId());
			// bwOrderService.addBwOrder(bwOrder);
			// }
			// BwPersonInfo bwPersonInfo = null;
			// if (isStock) {
			// bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(bwOrder.getId());
			// }
			// if (bwPersonInfo == null) {
			// bwPersonInfo = new BwPersonInfo();
			// bwPersonInfo.setCreateTime(new Date());
			// bwPersonInfo.setOrderId(bwOrder.getId());
			// bwPersonInfo.setUpdateTime(new Date());
			// bwPersonInfoService.add(bwPersonInfo);
			// }
			haoXiangDaiResponse.setCode(ThirdResponse.CODE_SUCCESS);
			haoXiangDaiResponse.setMsg("成功");
			haoXiangDaiResponse.setResponse(map);
		} catch (Exception e) {
			logger.error(sessionId + "执行水象云进件接口接口异常", e);
			haoXiangDaiResponse.setCode(ThirdResponse.CODE_NETERROR);
			haoXiangDaiResponse.setMsg("系统错误");
		}
		logger.info(sessionId + "结束水象云进件接口：" + JSON.toJSONString(haoXiangDaiResponse));
		return haoXiangDaiResponse;
	}

	public boolean checkSign(String request, String appId, String sign, Integer channelId) {
		try {
			String signKey = ThirdConstant.THIRD_CONFIG.getString(SIGN_KEY + channelId);
			String md5 = MD5Util.md5(signKey + appId + request);
			if (sign.equals(md5)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	private boolean regex(String str) {
		String regEx = "^1[0-9]{10}$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
