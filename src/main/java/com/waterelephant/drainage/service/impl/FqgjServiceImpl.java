package com.waterelephant.drainage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.baiqishi.entity.ServiceResult91;
import com.beadwallet.service.drainage.FqgjServiceSDK;
import com.beadwallet.service.sms.dto.MessageDto;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.waterelephant.drainage.entity.fqgj.DrainageResp;
import com.waterelephant.drainage.jsonentity.fqgj.*;
import com.waterelephant.drainage.service.FqgjService;
import com.waterelephant.drainage.util.fqgj.jkzj.UploadToAliyUtils;
import com.waterelephant.entity.*;
import com.waterelephant.drainage.util.fqgj.LogUtil;
import com.waterelephant.drainage.util.fqgj.ThreadLocalUtil;
import com.waterelephant.register.util.RegisterUtils;
import com.waterelephant.service.*;
import com.waterelephant.sms.service.SendMessageCommonService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/23 16:01
 */
@Service
public class FqgjServiceImpl implements FqgjService {
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	@Autowired
	private BwOperateBasicService bwOperateBasicService;
	@Autowired
	private BwOperateVoiceService bwOperateVoiceService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;
	@Autowired
	private SendMessageCommonService sendMessageCommonService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Autowired
	private IBwContactListService bwContactListService;

	private static LogUtil logger = new LogUtil(FqgjServiceImpl.class);

	/**
	 * 存储分期管家推送订单
	 *
	 * @author GuoKun
	 * @param fqgjOrder
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean saveOrderPush(FqgjOrder fqgjOrder) throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "FqgjServiceImpl.savePushOrder";
		logger.info(methodName + " start");
		FqgjOrderInfo orderInfo = fqgjOrder.getOrder_info();
		try {
			FqgjApplyDetail applyDetail = fqgjOrder.getApply_detail();
			logger.info("开始接收分期管家工单基本信息");
			String orderNo = orderInfo.getOrderNo();
			String name = orderInfo.getUserName();
			String phone = orderInfo.getUserMobile();
			String idCard = applyDetail.getIdCard();
			ThreadLocalUtil.set("BASEINFO-" + orderNo);
			logger.info("参数校验通过");
			logger.info("根据orderNo=" + orderNo + " 查询分期管家工单信息");
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
			logger.info("查询分期管家工单结果：" + JSONObject.toJSONString(bwOrderRong));

			// 如果工单已存在则不做处理
			if (!CommUtils.isNull(bwOrderRong)) {
				logger.info(methodName + " end，接收推单异常,分期管家工单已经存在，不做重复处理...");
				ThreadLocalUtil.remove();
				return false; // 流程结束
			}

			// 判断该用户是否有进行中订单
			logger.info("分期管家工单信息不存在，开始新增工单");
			BwBorrower borrower = addOrUpdateBwer(name, idCard, phone, orderInfo.getCity()); // 新增OR修改借款人信息
			logger.info("开始查询进行中的订单,borrowerId=" + borrower.getId());
			Long count = bwOrderService.findProOrder(String.valueOf(borrower.getId()));
			logger.info("结束查询进行中的订单,count=" + count);
			if (!CommUtils.isNull(count) && count.intValue() > 0) {
				logger.info(methodName + " end，接收推单异常,借款人[" + borrower.getId() + "]已经有进行中的订单,忽略此条订单");
				return false;
			}

			// 判断该用户是否已经有草稿状态的订单,如果有修改用户信息
			logger.info("判断该借款人是否已经有草稿工单");
			BwOrder bo = new BwOrder();
			bo.setBorrowerId(borrower.getId());
			bo.setStatusId(1L);
			bo.setProductType(1);
			logger.info("开始查询草稿状态的订单,bwOrder=" + JSONObject.toJSONString(bo));
			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bo);
			Long authOrderId;
			if (!CommUtils.isNull(boList) && boList.size() > 0) {
				logger.info("已经存在草稿信息");
				bo = boList.get(0);
				logger.info("修改工单渠道为349"); // 需要修改为生产
				bo.setChannel(349);
				bo.setCreateTime(Calendar.getInstance().getTime());
				bo.setUpdateTime(Calendar.getInstance().getTime());
				bwOrderService.updateBwOrder(bo);
				logger.info("修改工单渠道成功");
				logger.info("判断orderRong工单是否存在");

				// 判断是否存在orderRong工单
				BwOrderRong bwr = new BwOrderRong();
				bwr.setOrderId(bo.getId());
				bwr = bwOrderRongService.findBwOrderRongByAttr(bwr);
				if (CommUtils.isNull(bwr)) {
					logger.info("orderRong工单不存在，开始新增...");
					saveBwROrder(bo.getId(), orderNo);
					logger.info("orderRong工单新增成功");
				} else {
					logger.info("orderRong工单已存在，开始修改...");
					bwr.setChannelId(349L);
					bwr.setThirdOrderNo(orderNo);
					bwr.setCreateTime(Calendar.getInstance().getTime());
					bwOrderRongService.updateBwOrderRongNo(bwr);
				}

				// 判断修改工作信息表
				logger.info("判断工作信息是否存在");
				BwWorkInfo bwi = new BwWorkInfo();
				bwi.setOrderId(bo.getId());
				bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
				if (!CommUtils.isNull(bwi)) {
					logger.info("已经存在，开始修改工作信息");
					updateBwWorkInfo(bo.getId(), applyDetail.getWorkPeriod() + "", "0");
				} else {
					logger.info("不存在，开始新增工作信息");
					saveBwWorkInfo(bo.getId(), applyDetail.getWorkPeriod() + "", "0", borrower.getId());
				}
				authOrderId = bo.getId();
			} else {
				logger.info("不存在草稿");
				logger.info("开始新增工单");
				BwOrder order = saveBwOrder(borrower, fqgjOrder); // 新增工单
				logger.info("开始新增orderRong 工单");
				saveBwROrder(order.getId(), orderNo); // 新增orderRong工单
				logger.info("开始新增工作信息");
				saveBwWorkInfo(order.getId(), applyDetail.getWorkPeriod() + "", applyDetail.getIsOpType() + "",
						borrower.getId()); // 新增工作信息
				authOrderId = order.getId();
			}

			// 芝麻信用
			logger.info("开始更新芝麻信用......");
			addOrUpdateZmxy(authOrderId, borrower.getId(), fqgjOrder.getAdd_info());

			// 运营商
			logger.info("开始更新运营商......");
			addOrUpdateOperate(authOrderId, fqgjOrder.getAdd_info(), borrower.getId());

			// 通话记录
			logger.info("开始更新通话记录......");
			addOperateVoice(fqgjOrder.getAdd_info(), borrower.getId());

			// // 通讯录
			// logger.info("开始更新通讯录......");
			// addOrUpdateBwContactLists(fqgjOrder, borrower.getId());

			// 向bwOrderProcessRecord表中添加一条记录
			addOrUpdateOrderProcess(authOrderId);

			logger.info(methodName + " end,savePushOrder success,接收推单成功");
		} catch (Exception e) {
			logger.error(methodName + " end,savePushOrder occured error,接收推单异常:", e);
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				logger.info("手机号重复,反馈拒绝");
				try {
					// ApproveFeedBackReq req = new ApproveFeedBackReq();
					// req.setOrder_no(orderInfo.getOrderNo());
					// req.setConclusion(40);
					// req.setRemark("信用评分过低#拒绝客户");
					// req.setRefuse_time(new SimpleDateFormat("yyyy-MM-dd
					// HH:mm:ss").format(Calendar.getInstance().getTime()));
					BwOrderRong bwr = new BwOrderRong();
					bwr.setThirdOrderNo(orderInfo.getOrderNo());
					bwr = bwOrderRongService.findBwOrderRongByAttr(bwr);
					BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwr.getOrderId()));
					Map<String, String> params = new HashMap<String, String>();
					params.put("statusId", String.valueOf(bwOrder.getStatusId()));
					params.put("thirdOrderNo", bwr.getThirdOrderNo());
					params.put("creditLimit", String.valueOf(bwOrder.getCreditLimit()));
					logger.info("开始反馈拒绝,req=" + JSONObject.toJSONString(params));
					ServiceResult91 resp = FqgjServiceSDK.gainApprove(params);
					logger.info("结束反馈拒绝,resp=" + JSONObject.toJSONString(resp));
				} catch (Exception e2) {
					logger.error("反馈拒绝异常:", e2);
				}
			}
			return false;
		}

		stopWatch.stop();
		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
		ThreadLocalUtil.remove();
		return true;
	}

	/**
	 * 存储分期管家补充信息推送
	 *
	 * @author GuoKun
	 * @param fqgjSupplementOrderInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public DrainageResp saveAddInfo(FqgjSupplementOrderInfo fqgjSupplementOrderInfo) throws Exception {
		DrainageResp drainageResp = new DrainageResp();
		drainageResp.setCode("200");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "FqgjServiceImpl.saveAddInfo";
		logger.info(methodName + " start");

		try {
			// 根据订单号获取工单号
			String thirdOrderNo = fqgjSupplementOrderInfo.getOrder_no();
			ThreadLocalUtil.set("ADDINFO-" + thirdOrderNo);
			logger.info("开始查询分期管家工单, thirdOrderNo=" + thirdOrderNo);
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				drainageResp.setCode("222");
				drainageResp.setMsg("查询无此订单ID信息");
				return drainageResp;
			}
			logger.info("开始查询分期管家工单, bwOrderRong=" + JSONObject.toJSONString(bwOrderRong));
			logger.info("开始查询工单,id=" + bwOrderRong.getOrderId());
			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
			logger.info("结束查询工单,bwOrder=" + JSONObject.toJSONString(bwOrder));

			// 如果当前工单状态不为1表示工单补充信息已经接收完成(融360会重复推送7次)
			if (!CommUtils.isNull(bwOrder) && bwOrder.getStatusId() != null && 1 != bwOrder.getStatusId().intValue()) {
				logger.info(methodName + " end,接收补充信息异常,分期管家工单补充信息已经接收，不做重复处理...,tOrderNo=" + thirdOrderNo);
				ThreadLocalUtil.remove();
				drainageResp.setCode("200");
				drainageResp.setMsg("分期管家工单补充信息已经接收，不做重复处理");
				return drainageResp;
			}

			String positiveUrl = fqgjSupplementOrderInfo.getId_positive() == null ? null
					: fqgjSupplementOrderInfo.getId_positive().get(fqgjSupplementOrderInfo.getId_positive().size() - 1);
			String negativeUrl = fqgjSupplementOrderInfo.getId_negative() == null ? null
					: fqgjSupplementOrderInfo.getId_negative().get(fqgjSupplementOrderInfo.getId_negative().size() - 1);
			String photoAssay = fqgjSupplementOrderInfo.getPhoto_assay() == null ? null
					: fqgjSupplementOrderInfo.getPhoto_assay().get(fqgjSupplementOrderInfo.getPhoto_assay().size() - 1);

			if (StringUtils.isBlank(positiveUrl)) {
				logger.info(methodName + " end,接收补充信息异常, 身份证正面照为空");
				ThreadLocalUtil.remove();
				drainageResp.setCode("222");
				drainageResp.setMsg("身份证正面照为空");
				return drainageResp;
			}

			if (StringUtils.isBlank(negativeUrl)) {
				logger.info(methodName + " end,接收补充信息异常, 身份证反面照为空");
				ThreadLocalUtil.remove();
				drainageResp.setCode("222");
				drainageResp.setMsg("身份证反面照为空");
				return drainageResp;
			}

			if (StringUtils.isBlank(photoAssay)) {
				logger.info(methodName + " end,接收补充信息异常, 活体为空");
				ThreadLocalUtil.remove();
				drainageResp.setCode("222");
				drainageResp.setMsg("活体为空");
				return drainageResp;
			}

			// 上传照片
			logger.info("开始上传身份证正面照,分期管家身份证正面照图片URL:" + positiveUrl);
			positiveUrl = UploadToAliyUtils.urlUploadNew(positiveUrl, bwOrder.getOrderNo() + "_01.jpg");
			logger.info("当前工单号：" + bwOrderRong.getOrderId() + "的身份证正面url：" + positiveUrl);
			logger.info("开始上传身份证反面照,分期管家身份证反面照图片URL:" + negativeUrl);
			negativeUrl = UploadToAliyUtils.urlUploadNew(negativeUrl, bwOrder.getOrderNo() + "_02.jpg");
			logger.info("当前工单号：" + bwOrderRong.getOrderId() + "的身份证反面url：" + negativeUrl);
			logger.info("开始上传持证照,分期管家活体照图片URL:" + photoAssay);
			photoAssay = UploadToAliyUtils.urlUploadNew(photoAssay, bwOrder.getOrderNo() + "_03.jpg");
			logger.info("当前工单号：" + bwOrderRong.getOrderId() + "的身份证活体照url：" + photoAssay);
			logger.info("上传完成!!!!");

			// 更新公司信息
			logger.info("开始更新公司名称......");
			logger.info("company_name=" + fqgjSupplementOrderInfo.getCompany_name());
			BwWorkInfo bwi = new BwWorkInfo();
			bwi.setOrderId(bwOrderRong.getOrderId());
			bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
			bwi.setComName(fqgjSupplementOrderInfo.getCompany_name());
			bwi.setIndustry(getWorkType(String.valueOf(fqgjSupplementOrderInfo.getIndustry())));
			bwi.setUpdateTime(new Date());
			bwWorkInfoService.update(bwi);

			// 2：个人信息
			saveOrUpdateOrderAuth(bwOrderRong.getOrderId(), 2);

			// 3: 添加通讯录
			logger.info("------开始添加通讯录------");
			addOrUpdateConcast(fqgjSupplementOrderInfo.getContacts(), bwOrderRong.getOrderId());

			// 持证照
			logger.info("开始更新持证照信息...");
			saveOrUpdateCzz(bwOrderRong.getOrderId(), bwOrder.getBorrowerId(), photoAssay);
			logger.info("开始更新身份证正面照信息...");
			saveOrUpdateSfzzm(bwOrderRong.getOrderId(), bwOrder.getBorrowerId(), positiveUrl);
			logger.info("开始更新身份证反面照信息...");
			saveOrUpdateSfzfm(bwOrderRong.getOrderId(), bwOrder.getBorrowerId(), negativeUrl);

			// 3：拍照
			saveOrUpdateOrderAuth(bwOrderRong.getOrderId(), 3);

			// 亲属联系人
			logger.info("开始更新联系人信息...");
			drainageResp = saveOrUpdatePersonInfo(fqgjSupplementOrderInfo, bwOrderRong.getOrderId());
			if (!"200".equals(drainageResp.getCode())) {
				return drainageResp;
			}
			// 判断银行卡签约是否成功
			BwBankCard bbc = new BwBankCard();
			// 查询银行卡信息
			bbc.setBorrowerId(bwOrder.getBorrowerId());
			bbc = bwBankCardService.findBwBankCardByAttr(bbc);

			logger.info("开始判断是否连连签约成功...");
			if (CommUtils.isNull(bbc) || !"2".equals(String.valueOf(bbc.getSignStatus()))
					|| CommUtils.isNull(RedisUtils.hget("rong360:bindTag", thirdOrderNo))) {
				logger.info("连连签约未成功,不修改工单状态");
				stopWatch.stop();
				logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
				ThreadLocalUtil.remove();
				drainageResp.setCode("200");
				return drainageResp;
			}

			BwOrder nowOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
			if (!"1".equals(String.valueOf(nowOrder.getStatusId()))) {
				logger.info(methodName + " end,工单状态已被修改，不重复处理");
				ThreadLocalUtil.remove();
				drainageResp.setCode("200");
				return drainageResp;
			}

			logger.info("连连签约成功，设置工单状态为待审核");
			logger.info("开始查询进行中的订单,borrowerId=" + bwOrder.getBorrowerId());
			Long count = bwOrderService.findProOrder(String.valueOf(bwOrder.getBorrowerId()));
			logger.info("结束查询进行中的订单,count=" + count);

			if (count != null && count.intValue() > 0) {
				try {
					// 修改工单
					logger.info("将工单:" + bwOrder.getId() + "的状态修改为7");
					bwOrder.setStatusId(7L);
					bwOrder.setUpdateTime(Calendar.getInstance().getTime());
					bwOrderService.updateBwOrder(bwOrder);

					// ApproveFeedBackReq req = new ApproveFeedBackReq();
					// req.setOrder_no(bwOrderRong.getThirdOrderNo());
					// req.setConclusion(40);
					// req.setRemark("信用评分过低#拒绝客户");
					// req.setRefuse_time(new SimpleDateFormat("yyyy-MM-dd
					// HH:mm:ss").format(Calendar.getInstance().getTime()));
					// logger.info("开始反馈拒绝,req=" +
					// JSONObject.toJSONString(req));
					// ApproveFeedBackResp resp =
					// BeadWalletRong360Service.approveFeedBack(req);
					// logger.info("结束反馈拒绝,resp=" +
					// JSONObject.toJSONString(resp));
					Map<String, String> params = new HashMap<String, String>();
					params.put("statusId", String.valueOf(bwOrder.getStatusId()));
					params.put("thirdOrderNo", bwOrderRong.getThirdOrderNo());
					params.put("creditLimit", String.valueOf(bwOrder.getCreditLimit()));
					logger.info("开始反馈拒绝,req=" + JSONObject.toJSONString(params));
					ServiceResult91 resp = FqgjServiceSDK.gainApprove(params);
					logger.info("结束反馈拒绝,resp=" + JSONObject.toJSONString(resp));
				} catch (Exception e2) {
					logger.error("反馈拒绝异常:", e2);
				}
				ThreadLocalUtil.remove();
				logger.info(methodName + " end,已有进行中的订单,直接拒绝");
				drainageResp.setCode("200");
				return drainageResp;
			}

			// // 修改认证状态为4
			// logger.info("将借款人:" + bwOrder.getBorrowerId() + "的认证状态修改为4");
			// 修改工单
			logger.info("将工单:" + bwOrder.getId() + "的状态修改为2");
			bwOrder.setStatusId(2L);
			bwOrder.setUpdateTime(Calendar.getInstance().getTime());
			bwOrderService.updateBwOrder(bwOrder);
		} catch (Exception e) {
			logger.error(methodName + " end,saveAddInfo occured error,接收补充信息异常", e);
		}

		stopWatch.stop();
		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
		ThreadLocalUtil.remove();
		return drainageResp;
	}

	/**
	 * 新增OR修改借款人信息，并发送短信
	 *
	 * @author GuoKun
	 * @param name
	 * @param idCard
	 * @param phone
	 * @return
	 */
	private BwBorrower addOrUpdateBwer(String name, String idCard, String phone, String city) throws Exception {
		BwBorrower borrower = new BwBorrower();
		borrower.setIdCard(idCard);
		borrower.setFlag(1);// 未删除的
		logger.info("开始查询借款人,idCard=" + idCard);
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		logger.info("查询借款人信息为:" + JSONObject.toJSONString(borrower));

		// 如果借款人信息已存在就修改，不存在就新增
		if (!CommUtils.isNull(borrower)) {
			logger.info("借款人信息已存在，开始修改借款人信息：" + borrower.getId());
			// 已存在(更新借款人)
			borrower.setPhone(phone);
			borrower.setAuthStep(1);
			borrower.setFlag(1);
			borrower.setState(1);
			borrower.setChannel(349); // 表示该借款人来源于分期管家
			borrower.setIdCard(idCard);
			borrower.setName(name);
			borrower.setAge(getAgeByIdCard(idCard));
			borrower.setSex(getSexByIdCard(idCard));
			borrower.setUpdateTime(Calendar.getInstance().getTime());
			bwBorrowerService.updateBwBorrower(borrower);
		} else {
			logger.info("根据身份证没有查到借款人,开始根据手机号查询,phone=" + phone);
			BwBorrower myBorrower = new BwBorrower();
			myBorrower.setPhone(phone);
			borrower = bwBorrowerService.findBwBorrowerByAttr(myBorrower);
			logger.info("根据手机号查询借款人结果:" + JSONObject.toJSONString(borrower));

			if (!CommUtils.isNull(borrower) && StringUtils.isBlank(borrower.getIdCard())) {
				logger.info("借款人手机号存在,身份证为空,开始修改借款人身份证");
				borrower.setPhone(phone);
				borrower.setAuthStep(1);
				borrower.setFlag(1);
				borrower.setState(1);
				borrower.setChannel(3499); // 表示该借款人来源于分期管家
				borrower.setIdCard(idCard);
				borrower.setName(name);
				borrower.setAge(getAgeByIdCard(idCard));
				borrower.setSex(getSexByIdCard(idCard));
				borrower.setUpdateTime(Calendar.getInstance().getTime());
				bwBorrowerService.updateBwBorrower(borrower);
				logger.info("修改借款人身份证结束");
			} else {
				// 创建借款人
				logger.info("借款人信息不存在，开始创建借款人");
				String password = RegisterUtils.getRandNum(1,999999);//生成一个六位数加上a的密码;
				borrower = new BwBorrower();
				borrower.setPhone(phone);
				borrower.setPassword(CommUtils.getMD5(password.getBytes()));
				borrower.setAuthStep(1);
				borrower.setFlag(1);
				borrower.setState(1);
				borrower.setChannel(349); // 表示该借款人来源于分期管家
				borrower.setIdCard(idCard);
				borrower.setName(name);
				borrower.setAge(getAgeByIdCard(idCard));
				borrower.setSex(getSexByIdCard(idCard));
				borrower.setRegisterAddr(city);
				borrower.setCreateTime(Calendar.getInstance().getTime());
				borrower.setUpdateTime(Calendar.getInstance().getTime());
				bwBorrowerService.addBwBorrower(borrower);
				logger.info("生成的借款人id：" + borrower.getId());

				// 发送短信
				try {
					String message = getMsg(password);
					// MsgReqData msg = new MsgReqData();
					// msg.setPhone(phone);
					// msg.setMsg(message);
					// msg.setType("0");
					// logger.info("开始发送密码短信,phone="+phone);
					// Response<Object> response =
					// BeadWalletSendMsgService.sendMsg(msg);
					// logger.info("发送完成,发送结果："+JSONObject.toJSONString(response));
					// boolean bo =
					// sendMessageCommonService.commonSendMessage("1",phone,
					// message);
					// if (bo) {
					// logger.info("发送短信成功！");
					// }else {
					// logger.info("发送短信失败！");
					// }
					MessageDto messageDto = new MessageDto();
					messageDto.setBusinessScenario("1");
					messageDto.setPhone(phone);
					messageDto.setMsg(message);
					messageDto.setType("1");
					RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
				} catch (Exception e) {
					logger.error("发送短信异常:", e);
				}
			}
		}
		return borrower;
	}

	/**
	 * 创建工单
	 *
	 * @author GuoKun
	 * @param borrower
	 * @return
	 */
	private BwOrder saveBwOrder(BwBorrower borrower, FqgjOrder fqgjOrder) throws Exception {
		FqgjOrderInfo fqgjOrderInfo = fqgjOrder.getOrder_info();
		String cityName = fqgjOrderInfo.getCity();
		Map<String, Object> map = bwPersonInfoService.getCityId(cityName);
		long orgId = 0L;
		if (map.get("id") != null) {
			orgId = (long) map.get("id");
		}
		// 创建工单
		BwOrder order = new BwOrder();
		order.setOrderNo(OrderUtil.generateOrderNo());
		order.setBorrowerId(borrower.getId());
		order.setStatusId(1L);
		order.setCreateTime(Calendar.getInstance().getTime());
		order.setUpdateTime(Calendar.getInstance().getTime());
		order.setChannel(349);
		order.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
		order.setApplyPayStatus(0);
		order.setProductId(3);
		order.setProductType(1);
		order.setRepayType(1);
		order.setSubmitTime(Calendar.getInstance().getTime());
		order.setExpectMoney(fqgjOrderInfo.getAmount().doubleValue());
		order.setOrgId(orgId);
		bwOrderService.addBwOrder(order);
		logger.info("工单创建成功，工单号为：" + order.getId());
		return order;
	}

	/**
	 * 将工单号与水象工单号绑定
	 *
	 * @author GuoKun
	 * @param orderId
	 * @param orderNo
	 * @return
	 */
	private BwOrderRong saveBwROrder(Long orderId, String orderNo) throws Exception {
		BwOrderRong bwOrderRong = new BwOrderRong();
		logger.info("订单号为：" + orderNo);
		bwOrderRong.setOrderId(orderId);
		bwOrderRong.setThirdOrderNo(orderNo);
		bwOrderRong.setChannelId(349L);
		bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
		bwOrderRongService.save(bwOrderRong);
		return bwOrderRong;
	}

	/**
	 * 新增公司信息
	 *
	 * @author GuoKun
	 * @param orderId
	 * @param workPeriod
	 * @param borrowerId
	 */
	private void saveBwWorkInfo(Long orderId, String workPeriod, String workType, Long borrowerId) throws Exception {
		BwWorkInfo bwi = new BwWorkInfo();
		bwi.setOrderId(orderId);
		bwi.setCallTime("10:00 - 12:00");// 默认值（融360没有提供相关数据）
		bwi.setCreateTime(Calendar.getInstance().getTime());
		bwi.setWorkYears(getWorkPeriod(workPeriod));
		bwi.setIndustry(getWorkType(workType));
		bwWorkInfoService.save(bwi, borrowerId);
	}

	/**
	 * 芝麻分
	 *
	 * @author GuoKun
	 * @param authOrderId
	 * @param borrowerId
	 * @param addInfo
	 */
	public void addOrUpdateZmxy(Long authOrderId, Long borrowerId, FqgjAddInfo addInfo) throws Exception {
		// 芝麻信用分
		FqgjZhiMa zhima = addInfo.getZhima();
		if (!CommUtils.isNull(zhima)) {
			BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrowerId);
			logger.info("开始查询芝麻信用记录，borrowerId=" + borrowerId);
			if (CommUtils.isNull(bwZmxyGrade)) {
				logger.info("芝麻信用记录不存在，开始新增");
				bwZmxyGrade = new BwZmxyGrade();
				bwZmxyGrade.setBorrowerId(borrowerId);
				bwZmxyGrade.setZmScore(Integer.valueOf(zhima.getAli_trust_score().getScore()));
				bwZmxyGrade.setCreateTime(Calendar.getInstance().getTime());
				bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
				bwZmxyGrade.setBizNo(zhima.getOpen_id());
				bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
			} else {
				logger.info("芝麻信用记录已存在，开始修改");
				bwZmxyGrade.setZmScore(Integer.valueOf(zhima.getAli_trust_score().getScore()));
				bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
				bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
			}
			// 4：芝麻信用
			saveOrUpdateOrderAuth(authOrderId, 4);
		}
	}

	/**
	 * 创建工单信息
	 * 
	 * @param orderId
	 */
	private void addOrUpdateOrderProcess(Long orderId) {
		try {
			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
			bwOrderProcessRecord.setSubmitTime(new Date());
			bwOrderProcessRecord.setOrderId(orderId);
			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
		} catch (Exception e) {
			logger.error("addOrUpdateOrderProcess occured exception:", e);
		}
	}

	/**
	 * 添加验证记录
	 *
	 * @author GuoKun
	 * @param orderId
	 * @param authType
	 */
	private void saveOrUpdateOrderAuth(Long orderId, Integer authType) throws Exception {
		try {
			logger.info("判断验证记录是否存在,orderId=" + orderId + ",authType=" + authType);
			BwOrderAuth bwOrderAuth = null;
			bwOrderAuth = bwOrderAuthService.findBwOrderAuth(orderId, authType);
			logger.info("查询结果:" + JSONObject.toJSONString(bwOrderAuth));
			if (!CommUtils.isNull(bwOrderAuth)) {
				logger.info("验证记录已存在");
				return;
			}
			bwOrderAuth = new BwOrderAuth();
			logger.info("验证记录不存在，开始新增");
			bwOrderAuth.setOrderId(orderId);
			bwOrderAuth.setAuth_type(authType);
			bwOrderAuth.setAuth_channel(349);
			bwOrderAuth.setCreateTime(Calendar.getInstance().getTime());
			bwOrderAuth.setUpdateTime(Calendar.getInstance().getTime());
			bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
			logger.info("验证记录新增成功");
		} catch (Exception e) {
			logger.error("验证记录新增异常:", e);
		}

	}

	/**
	 * 添加运营商基本信息
	 *
	 * @author GuoKun
	 * @param authOrderId
	 * @param addInfo
	 * @param borrowerId
	 * @throws Exception
	 */
	private void addOrUpdateOperate(Long authOrderId, FqgjAddInfo addInfo, Long borrowerId) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FqgjMobile mobile = addInfo.getMobile();
		if (CommUtils.isNull(mobile)) {
			logger.info("addInfo.mobile is null");
			return;
		}
		FqgjCarrierUser user = mobile.getUser();
		if (CommUtils.isNull(user)) {
			logger.info("addInfo.mobile.user is null");
			return;
		}
		String phone = user.getPhone();
		if (CommUtils.isNull(phone)) {
			logger.info("addInfo.mobile.user.phone is null");
			return;
		}

		logger.info("开始查询运营商记录,borrowerId=" + borrowerId);
		BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
		if (CommUtils.isNull(bwOperateBasic)) {
			// 添加基本信息
			logger.info("运营商记录不存在，开始新增");
			bwOperateBasic = new BwOperateBasic();
			bwOperateBasic.setUserSource(user.getUserSource());
			bwOperateBasic.setIdCard(CommUtils.isNull(user.getIdCard()) ? "" : user.getIdCard());
			bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
			bwOperateBasic.setPhone(user.getPhone());
			bwOperateBasic.setPhoneRemain(CommUtils.isNull(user.getPhoneRemain()) ? "" : user.getPhoneRemain());
			bwOperateBasic.setRealName(CommUtils.isNull(user.getRealName()) ? "" : user.getRealName());
			bwOperateBasic.setCreateTime(new Date());
			bwOperateBasic.setUpdateTime(new Date());
			if (!CommUtils.isNull(user.getRegTime())) {
				bwOperateBasic.setRegTime(sdf.parse(user.getRegTime()));
			}
			bwOperateBasic.setBorrowerId(borrowerId);
			bwOperateBasicService.save(bwOperateBasic);
		} else {
			// 修改基本信息
			logger.info("运营商记录已存在，开始修改");
			bwOperateBasic.setUserSource(user.getUserSource());
			bwOperateBasic.setIdCard(CommUtils.isNull(user.getIdCard()) ? "" : user.getIdCard());
			bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
			bwOperateBasic.setPhone(user.getPhone());
			bwOperateBasic.setPhoneRemain(CommUtils.isNull(user.getPhoneRemain()) ? "" : user.getPhoneRemain());
			bwOperateBasic.setRealName(CommUtils.isNull(user.getRealName()) ? "" : user.getRealName());
			bwOperateBasic.setUpdateTime(new Date());
			if (!CommUtils.isNull(user.getRegTime())) {
				bwOperateBasic.setRegTime(sdf.parse(user.getRegTime()));
			}
			bwOperateBasic.setBorrowerId(borrowerId);
			bwOperateBasicService.update(bwOperateBasic);
		}
		// 1：运营商
		saveOrUpdateOrderAuth(authOrderId, 1);
	}

	/**
	 * 添加通话记录
	 *
	 * @author GuoKun
	 * @param addInfo
	 * @param borrowerId
	 * @throws Exception
	 */
	private void addOperateVoice(FqgjAddInfo addInfo, Long borrowerId) throws Exception {
		if (CommUtils.isNull(addInfo)) {
			logger.info("addOperateVoice occured exception:addInfo is null");
			return;
		}
		SimpleDateFormat sdf_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
		logger.info("查询最后通话记录" + callDate);
		FqgjMobile mobile = addInfo.getMobile();
		List<FqgjCarrierTel> tels = mobile.getTel();
		if (CommUtils.isNull(tels)) {
			logger.info("addOperateVoice occured exception:tels is null");
			return;
		}
		if (CommUtils.isNull(callDate)) {
			logger.info("最后通话记录为空" + tels.size());
			for (FqgjCarrierTel tel : tels) {
				List<FqgjCarrierTelData> lists = tel.getTeldata();
				logger.info("最后通话记录为空lists" + lists.size());
				if (!CommUtils.isNull(lists)) {
					for (FqgjCarrierTelData telData : lists) {
						try {
							BwOperateVoice bwOperateVoice = new BwOperateVoice();
							bwOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
							bwOperateVoice.setBorrower_id(borrowerId);
							// 检验日期格式
							String callTime = null;
							callTime = sdf_hms.format(sdf_hms.parse(telData.getCallTime()));
							bwOperateVoice.setCall_time(callTime);
							bwOperateVoice.setCall_type(Integer.parseInt(telData.getCallType()));
							bwOperateVoice.setReceive_phone(telData.getReceivePhone());
							bwOperateVoice.setTrade_addr(
									CommUtils.isNull(telData.getTradeAddr()) ? "" : telData.getTradeAddr());
							bwOperateVoice.setTrade_time(telData.getTradeTime());
							bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTradeType()));
							bwOperateVoiceService.save(bwOperateVoice);
						} catch (Exception e) {
							logger.error("保存通话记录异常,忽略此条通话记录...", e);
							continue;
						}
					}
				}
			}
		} else {
			for (FqgjCarrierTel tel : tels) {
				logger.info("最后通话记录非空" + tels.size());
				List<FqgjCarrierTelData> lists = tel.getTeldata();
				if (!CommUtils.isNull(lists)) {
					for (FqgjCarrierTelData telData : lists) {
						Date jsonCallData = sdf_hms.parse(telData.getCallTime());
						logger.info("最后通话记录非空lists" + lists.size());
						if (jsonCallData.after(callDate)) {
							try {
								BwOperateVoice bwOperateVoice = new BwOperateVoice();
								bwOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
								bwOperateVoice.setBorrower_id(borrowerId);
								// 检验日期格式
								String callTime = null;
								callTime = sdf_hms.format(sdf_hms.parse(telData.getCallTime()));
								bwOperateVoice.setCall_time(callTime);
								bwOperateVoice.setCall_type(Integer.parseInt(telData.getCallType()));
								bwOperateVoice.setReceive_phone(telData.getReceivePhone());
								bwOperateVoice.setTrade_addr(
										CommUtils.isNull(telData.getTradeAddr()) ? "" : telData.getTradeAddr());
								bwOperateVoice.setTrade_time(telData.getTradeTime());
								bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTradeType()));
								bwOperateVoiceService.save(bwOperateVoice);
							} catch (Exception e) {
								logger.error("保存通话记录异常,忽略此条通话记录...", e);
								continue;
							}
						}
					}
				}
			}
		}
	}

	private void addOrUpdateConcast(FqgjSupplementContact fqgjContacts, Long orderId) {
		try {
			logger.info("开始添加借款人通讯录：工单为：" + orderId + "");
			// 添加通讯录
			if (CommUtils.isNull(fqgjContacts)) {
				logger.info("fqgjContacts is null");
				return;
			}
			logger.info("工单id orderId=" + orderId);
			BwOrder bo = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bo)) {
				logger.info("未找到" + orderId + "工单");
				return;
			}
			logger.info("借款人id=" + bo.getBorrowerId());
			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(orderId);
			if (CommUtils.isNull(borrower)) {
				logger.info("借款人不存在");
				return;
			}
			List<FqgjSupplementPhone> phoneList = fqgjContacts.getPhone_list();
			if (CommUtils.isNull(phoneList)) {
				return;
			}
			// 轮询
			for (FqgjSupplementPhone fqgjContact : phoneList) {

				if (CommUtils.isNull(fqgjContact.getPhone())) {
					return;
				}
				if (CommUtils.isNull(fqgjContact.getName())) {
					return;
				}

				String phone = fqgjContact.getPhone().replaceAll(" ", "");
				logger.info("~~~~验证手机号是否合法~~~~~~" + fqgjContact.getPhone());
				if (phone.startsWith("+86")) {
					phone = phone.substring(3);
				}
				String pattern = "0?(13|14|15|17|18)[0-9]{9}";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(phone);
				if (m.matches()) {
					logger.info("借款人id为：" + borrower.getId());
					logger.info("开始查询联系人是否已存在通讯录");
					BwContactList result = bwContactListService.findByPhoneAndBwId(phone, borrower.getId());
					logger.info("结束查询联系人是否已存在通讯录");
					BwContactList bwContact = new BwContactList();
					if (CommUtils.isNull(result)) {
						bwContact.setPhone(phone);
						bwContact.setName(fqgjContact.getName());
						bwContact.setUpdateTime(new Date());
						bwContact.setBorrowerId(borrower.getId());
						bwContact.setCreateTime(new Date());
						logger.info("添加通讯录信息为：" + JSONObject.toJSONString(bwContact));
						bwContactListService.addBwContactList(bwContact);
					} else {
						bwContact.setPhone(phone);
						bwContact.setName(fqgjContact.getName());
						bwContact.setUpdateTime(new Date());
						bwContact.setBorrowerId(borrower.getId());
						bwContact.setId(result.getId());
						logger.info("添加通讯录信息为：" + JSONObject.toJSONString(bwContact));
						bwContactListService.updateBwContactList(bwContact);
					}
					logger.info("添加通讯录成功，借款人id" + borrower.getId());
				} else {
					logger.info("~~~~验证手机号不合法~~~~~~~~~~~");
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("通讯录添加失败", e);
		}

	}

	/**
	 * 计算年龄
	 *
	 * @author GuoKun
	 * @param idCard
	 * @return
	 */
	private int getAgeByIdCard(String idCard) throws Exception {
		Calendar c = Calendar.getInstance();
		int age = 0;
		int year = c.get(Calendar.YEAR);
		String ageNum = idCard.substring(6, 10);
		age = year - Integer.parseInt(ageNum);

		return age;
	}

	/**
	 * 判单性别
	 *
	 * @author GuoKun
	 * @param idCard
	 * @return
	 */
	private int getSexByIdCard(String idCard) throws Exception {
		int sex = 0;
		String sexNum = idCard.substring(idCard.length() - 2, idCard.length() - 1);
		if ((Integer.parseInt(sexNum)) % 2 == 0) {
			sex = 0;
		} else {
			sex = 1;
		}
		return sex;
	}

	/**
	 * 创建随机密码
	 *
	 * @author GuoKun
	 * @return
	 */
	private String getRandomPwd() throws Exception {
		char[] r = getChar();
		Random rr = new Random();
		char[] pw = new char[6];
		for (int i = 0; i < pw.length; i++) {
			int num = rr.nextInt(62);
			pw[i] = r[num];
		}
		return new String(pw);
	}

	private char[] getChar() throws Exception {
		char[] passwordLit = new char[62];
		char fword = 'A';
		char mword = 'a';
		char bword = '0';
		for (int i = 0; i < 62; i++) {
			if (i < 26) {
				passwordLit[i] = fword;
				fword++;
			} else if (i < 52) {
				passwordLit[i] = mword;
				mword++;
			} else {
				passwordLit[i] = bword;
				bword++;
			}
		}
		return passwordLit;
	}

	/**
	 * 短信编辑
	 *
	 * @author GuoKun
	 * @param pwd
	 * @return
	 */
	public String getMsg(String pwd) throws Exception {
		String pattern = "尊敬的用户您好，恭喜您成功注册，您的登录密码为：{0}，您还可以登录 t.cn/R6rhkzU 查看最新的借款进度哦！";
		String msg = MessageFormat.format(pattern, new Object[] { pwd });
		return msg;
	}

	/**
	 * 修改工作信息表
	 *
	 * @author GuoKun
	 * @param orderId
	 * @param workPeriod
	 */
	private void updateBwWorkInfo(Long orderId, String workPeriod, String workType) throws Exception {
		// 更新公司信息
		BwWorkInfo bwi = new BwWorkInfo();
		bwi.setOrderId(orderId);
		bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
		bwi.setCallTime("10:00 - 12:00");// 默认值（融360没有提供相关数据）
		bwi.setUpdateTime(Calendar.getInstance().getTime());
		bwi.setWorkYears(getWorkPeriod(workPeriod));
		bwi.setIndustry(getWorkType(workType));
		bwWorkInfoService.update(bwi);
	}

	/**
	 * 判断工作年限
	 *
	 * @author GuoKun
	 * @param key
	 * @return
	 */
	private String getWorkPeriod(String key) throws Exception {
		logger.info("start getWorkPeriod key=" + key);
		String workPeriod = null;
		if (StringUtils.isBlank(key)) {
			workPeriod = "1年以内";
		} else {
			switch (key) {
			case "1":
				workPeriod = "0-5个月";
				break;
			case "2":
				workPeriod = "6-11个月";
				break;
			case "3":
				workPeriod = "1-3年";
				break;
			case "4":
				workPeriod = "3-5年";
				break;
			case "5":
				workPeriod = "7年以上";
				break;
			default:
				workPeriod = "1年以内";
				break;
			}
		}

		logger.info("end getWorkPeriod workPeriod=" + workPeriod);

		return workPeriod;
	}

	/**
	 * 判断职业类别
	 *
	 * @author GuoKun
	 * @param key
	 * @return
	 * @author GuoKun
	 */
	private String getWorkType(String key) throws Exception {
		logger.info("start getWorkType key=" + key);
		String workType = null;
		switch (key) {
		case "1":
			workType = "金融";
			break;
		case "2":
			workType = "房地产/建筑";
			break;
		case "3":
			workType = "互联网/计算机";
			break;
		case "4":
			workType = "通讯";
			break;
		case "5":
			workType = "服务/教育培训";
			break;
		case "6":
			workType = "政府机关/非盈利机构";
			break;
		case "7":
			workType = "制造业";
			break;
		case "8":
			workType = "零售";
			break;
		case "9":
			workType = "广告业";
			break;
		case "10":
			workType = "贸易";
			break;
		case "11":
			workType = "医疗";
			break;
		case "12":
			workType = "物流/运输";
			break;
		default:
			workType = "未知";
			break;
		}

		logger.info("end getWorkPeriod workPeriod=" + workType);

		return workType;
	}

	/**
	 * 上传持证照
	 *
	 * @author GuoKun
	 * @param orderId
	 * @param borrowerId
	 * @param czzUrl
	 */
	private void saveOrUpdateCzz(Long orderId, Long borrowerId, String czzUrl) {
		BwAdjunct czzbaj = new BwAdjunct();
		czzbaj.setAdjunctType(3);
		czzbaj.setOrderId(orderId);
		czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);
		if (CommUtils.isNull(czzbaj)) {
			czzbaj = new BwAdjunct();
			czzbaj.setAdjunctType(3);
			czzbaj.setAdjunctPath(czzUrl);
			czzbaj.setOrderId(orderId);
			czzbaj.setBorrowerId(borrowerId);
			czzbaj.setCreateTime(new Date());
			bwAdjunctService.save(czzbaj);
		} else {
			czzbaj.setAdjunctPath(czzUrl);
			czzbaj.setUpdateTime(Calendar.getInstance().getTime());
			bwAdjunctService.update(czzbaj);
		}
	}

	/**
	 * 上传身份证正面照
	 *
	 * @author GuoKun
	 * @param orderId
	 * @param borrowerId
	 * @param sfzzmUrl
	 */
	private void saveOrUpdateSfzzm(Long orderId, Long borrowerId, String sfzzmUrl) {
		BwAdjunct sfzzmbaj = new BwAdjunct();
		sfzzmbaj.setAdjunctType(1);
		sfzzmbaj.setOrderId(orderId);
		sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);
		if (CommUtils.isNull(sfzzmbaj)) {
			sfzzmbaj = new BwAdjunct();
			sfzzmbaj.setAdjunctType(1);
			sfzzmbaj.setAdjunctPath(sfzzmUrl);
			sfzzmbaj.setOrderId(orderId);
			sfzzmbaj.setBorrowerId(borrowerId);
			sfzzmbaj.setCreateTime(Calendar.getInstance().getTime());
			bwAdjunctService.save(sfzzmbaj);
		} else {
			sfzzmbaj.setAdjunctPath(sfzzmUrl);
			sfzzmbaj.setUpdateTime(Calendar.getInstance().getTime());
			bwAdjunctService.update(sfzzmbaj);
		}
	}

	/**
	 * 上传身份证反面照
	 *
	 * @author GuoKun
	 * @param orderId
	 * @param borrowerId
	 * @param sfzfmUrl
	 */
	private void saveOrUpdateSfzfm(Long orderId, Long borrowerId, String sfzfmUrl) {
		BwAdjunct sfzfmbaj = new BwAdjunct();
		sfzfmbaj.setAdjunctType(2);
		sfzfmbaj.setOrderId(orderId);
		sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);
		if (CommUtils.isNull(sfzfmbaj)) {
			sfzfmbaj = new BwAdjunct();
			sfzfmbaj.setAdjunctType(2);
			sfzfmbaj.setAdjunctPath(sfzfmUrl);
			sfzfmbaj.setOrderId(orderId);
			sfzfmbaj.setBorrowerId(borrowerId);
			sfzfmbaj.setCreateTime(Calendar.getInstance().getTime());
			bwAdjunctService.save(sfzfmbaj);
		} else {
			sfzfmbaj.setAdjunctPath(sfzfmUrl);
			sfzfmbaj.setUpdateTime(Calendar.getInstance().getTime());
			bwAdjunctService.update(sfzfmbaj);
		}
	}

	/**
	 * 保存紧急联系人
	 * 
	 * @author GuoKun
	 * @param orderAddInfo
	 * @param orderId
	 */
	private DrainageResp saveOrUpdatePersonInfo(FqgjSupplementOrderInfo orderAddInfo, Long orderId) {
		logger.info("######保存紧急联系人#######");
		DrainageResp resp = new DrainageResp();
		if (CommUtils.isNull(orderAddInfo)) {
			logger.info("orderAddInfo is null");
			resp.setCode("102");
			resp.setMsg("紧急联系人信息为空");
			return resp;
		}

		String addr = orderAddInfo.getAddr_detail(); // 居住地址

		if (StringUtils.isBlank(addr)) {
			logger.info("addr is null");
			resp.setCode("102");
			resp.setMsg("用户居住地址为空");
			return resp;
		}

		// String cityName = "";
		// if (addr.contains("省")) {
		// cityName = addr.split(" ")[0] + addr.split(" ")[1]; // 城市
		// } else {
		// cityName = addr.split(" ")[0]; // 城市
		// }
		// 根据工单id查询出借款人信息
		logger.info("工单id orderId=" + orderId);
		BwOrder bo = bwOrderService.findBwOrderById(String.valueOf(orderId));
		if (CommUtils.isNull(bo)) {
			resp.setCode("102");
			resp.setMsg("工单不存在");
			return resp;
		}
		logger.info("借款人id=" + bo.getBorrowerId());
		BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(orderId);
		if (CommUtils.isNull(borrower)) {
			resp.setCode("102");
			resp.setMsg("借款人不存在");
			return resp;
		}
		String cityName = borrower.getRegisterAddr();
		logger.info("获取到城市为" + cityName);
		String haveMarry = orderAddInfo.getUser_marriage();
		// 亲属联系人
		String relationName = orderAddInfo.getEmergency_contact_personA_name();
		String relationPhone = orderAddInfo.getEmergency_contact_personA_phone();
		String unrelationName = orderAddInfo.getEmergency_contact_personB_name();
		String unrelationPhone = orderAddInfo.getEmergency_contact_personB_phone();

		if (StringUtils.isEmpty(relationName) || StringUtils.isEmpty(unrelationName)
				|| StringUtils.isEmpty(relationPhone) || StringUtils.isEmpty(unrelationPhone)) {
			resp.setCode("102");
			resp.setMsg("联系人姓名或手机号为空");
			logger.info("联系人姓名或手机号为空");
			return resp;
		}

		logger.info("根据工单号查询个人信息");
		BwPersonInfo bpi = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);

		if (CommUtils.isNull(bpi)) {
			logger.info("城市名称=" + cityName);
			// 添加
			bpi = new BwPersonInfo();
			bpi.setOrderId(orderId);
			if (haveMarry.equals("1")) {
				// 1.表示未婚
				bpi.setMarryStatus(0);
			} else {
				bpi.setMarryStatus(1);
			}
			bpi.setOrderId(orderId);
			bpi.setRelationName(CommUtils.isNull(relationName) ? "" : relationName);
			bpi.setRelationPhone(CommUtils.isNull(relationPhone) ? "" : relationPhone);
			bpi.setUnrelationName(CommUtils.isNull(unrelationName) ? "" : unrelationName);
			bpi.setUnrelationPhone(CommUtils.isNull(unrelationPhone) ? "" : unrelationPhone);
			bpi.setEmail(orderAddInfo.getUser_email());
			bpi.setCreateTime(Calendar.getInstance().getTime());
			bpi.setUpdateTime(Calendar.getInstance().getTime());
			bpi.setAddress(addr);
			bpi.setCityName(cityName);
			bwPersonInfoService.save(bpi);
		} else {
			// 更新
			logger.info("城市名称=" + cityName);
			bpi.setOrderId(orderId);
			if (haveMarry.equals("1")) {
				// 1.表示未婚
				bpi.setMarryStatus(0);
			} else {
				bpi.setMarryStatus(1);
			}
			bpi.setAddress(addr);
			bpi.setOrderId(orderId);
			bpi.setRelationName(relationName);
			bpi.setRelationPhone(relationPhone);
			bpi.setUnrelationName(unrelationName);
			bpi.setUnrelationPhone(unrelationPhone);
			bpi.setUpdateTime(Calendar.getInstance().getTime());
			bpi.setCityName(cityName);
			bwPersonInfoService.update(bpi);
		}
		resp.setCode("200");
		resp.setMsg("联系人信息保存成功！");
		logger.info("联系人信息保存成功！");
		return resp;
	}

	@SuppressWarnings("unused")
	private BwBorrower updateBorrower(Long borrowerId) {
		BwBorrower borrower = new BwBorrower();
		borrower.setId(borrowerId);
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		borrower.setAuthStep(4);
		borrower.setUpdateTime(Calendar.getInstance().getTime());
		bwBorrowerService.updateBwBorrower(borrower);
		return borrower;
	}

	// private void addOrUpdateBwContactLists(FqgjOrder fqgjOrder, Long
	// borrowerId){
	// // 录入通讯录
	// FqgjAddInfo addInfo = fqgjOrder.getAdd_info();
	//
	// if (CommUtils.isNull(addInfo)) {
	// logger.info("addInfo is null");
	// return;
	// }
	//
	// FqgjMobile fqgjMobile = addInfo.getMobile();
	//
	// if (CommUtils.isNull(fqgjMobile)) {
	// logger.info("contacts is null");
	// return;
	// }
	//
	// List<PhoneList> phoneLists = fqgjMobile.get();
	//
	// if (CommUtils.isNull(phoneLists)) {
	// logger.info("phoneLists is null");
	// return;
	// }
	//
	// List<BwContactList> list = new ArrayList<>();
	// for (PhoneList phoneList : phoneLists) {
	// if (CommUtils.isNull(phoneList.getName())) {
	// continue;
	// }
	// if (CommUtils.isNull(phoneList.getPhone())) {
	// continue;
	// }
	//
	// try {
	// BwContactList bwContactList = new BwContactList();
	// bwContactList.setBorrowerId(borrowerId);
	// bwContactList.setCreateTime(Calendar.getInstance().getTime());
	// bwContactList.setName(phoneList.getName().toString());
	// bwContactList.setPhone(phoneList.getPhone());
	// bwContactList.setUpdateTime(Calendar.getInstance().getTime());
	// list.add(bwContactList);
	// } catch (Exception e) {
	// logger.error("addOrUpdateBwContactLists occured exception, 忽略此条通讯录...",
	// e);
	// }
	// }
	// bwContactListService.addOrUpdateBwContactLists(list);
	// }
}
