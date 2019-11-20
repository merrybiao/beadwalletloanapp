package com.waterelephant.haoDai.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.beanutils.locale.converters.DoubleLocaleConverter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.entity.request.PostLoanInfo;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entiyt.middle.EmergencyContact;
import com.beadwallet.service.entiyt.middle.HaoDaiResponseData;
import com.beadwallet.service.entiyt.middle.UserEquipmentData;
import com.beadwallet.service.serve.BeadWalletHaoDaiService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.dto.DataBackDto;
import com.waterelephant.dto.RepayDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwCheckRecord;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.BwUserEquipment;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.entity.OrderState;
import com.waterelephant.haoDai.service.HaoDaiService;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCheckRecordService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.BwUserEquipmentService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.sms.service.SendMessageCommonService;
import com.waterelephant.utils.CdnUploadTools;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RecommendCodeUtil;
import com.waterelephant.utils.RedisUtils;

/**
 * 
 * @ClassName: HaoDaiService
 * @Description: TODO(好贷网业务实现)
 * @author SongYaJun
 * @date 2016年11月23日 下午3:34:38
 *
 */
@Service("haoDaiService")
public class HaoDaiServiceImpl extends BaseService<BwOrder, Long> implements HaoDaiService {

	private Logger logger = Logger.getLogger(HaoDaiServiceImpl.class);

	@Resource
	private IBwBorrowerService iBwBorrowerService;

	@Resource
	private IBwOrderService iBwOrderService;

	@Resource
	private IBwPersonInfoService iBwPersonInfoService;

	@Resource
	private IBwAdjunctService iBwAdjunctService;

	@Resource
	private IBwBankCardService iBwBankCardService;

	@Resource
	private BwOrderRongService bwOrderRongService;

	@Resource
	private BwBorrowerService bwBorrowerService;

	@Resource
	private BwUserEquipmentService bwUserEquipmentService;

	@Resource
	private BwCheckRecordService bwCheckRecordService;

	@Resource
	private IBwOrderPushInfoService iBwOrderPushInfoService;

	@Resource
	private IBwRepaymentPlanService iBwRepaymentPlanService;

	@Resource
	private BwOrderService bwOrderService;

	@Resource
	private BwOperateVoiceService bwOperateVoiceService;

	@Resource
	private IBwWorkInfoService iBwWorkInfoService;

	@Resource
	private BwRejectRecordService bwRejectRecordService;

	@Resource
	private BwOrderAuthService bwOrderAuthService;

	@Resource
	private SendMessageCommonService sendMessageCommonService;

	@Override
	public Map<String, Long> addLoanUserInfo(Response<HaoDaiResponseData> haoDaiResponseData, String phone) {
		Map<String, Long> map = new HashMap<String, Long>();
		try {
			HaoDaiResponseData haoDaiResponse = haoDaiResponseData.getObj();
			/************************* 借款人信息 ***********************************/
			// 根据手机号查询第三方传过来的用户是否在我们平台注册过,如果注册则不注册。
			// 校验该手机号是否在我们平台注册过
			BwBorrower borrower = new BwBorrower();
			borrower.setPhone(phone);
			borrower.setFlag(1);// 未删除的
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			logger.info("=========校验该手机号是否在我们平台注册过:" + borrower == null);
			/********************* 不为空说明在我们平台注册过,不给于借款 ********************/
			if (CommUtils.isNull(borrower)) {
				String password = this.getRandomCharAndNumr(6); // 随机生成密码
				borrower = new BwBorrower();
				borrower.setName(haoDaiResponse.getLoanUserName()); // 借款人姓名
				borrower.setPassword(CommUtils.getMD5(password.getBytes())); // 用户密码
				borrower.setIdCard(haoDaiResponse.getIdenCard()); // 身份证
				borrower.setPhone(phone); // 手机号
				borrower.setFlag(1); // 表示用户未删除
				borrower.setAuthStep(5); // 5：运营商认证
				borrower.setState(1); // 1表示启用
				borrower.setCreateTime(new Date()); // 创建时间
				borrower.setRegisterAddr(haoDaiResponse.getLiveAddress()); // 注册地址
				borrower.setChannel(12); // 来源
				Integer[] ins = CommUtils.checkIdCard(haoDaiResponse.getIdenCard());
				if (ins != null && ins.length == 2) {
					borrower.setAge(ins[0]);
					borrower.setSex(ins[1]);
				}

				borrower.setCreateTime(new Date());
				// 添加借款人
				iBwBorrowerService.addBwBorrower(borrower);
				logger.info("===============================添加借款人成功!");
				String code = RecommendCodeUtil.toSerialCode(borrower.getId()); // 获取借款人邀请码
				borrower.setInviteCode(code);
				borrower.setUpdateTime(new Date());
				// 更新借款人邀请码
				iBwBorrowerService.updateBwBorrowerInviteCode(borrower);
				logger.info("================================更新借款人邀请码");
				// 发送短信
				// MsgReqData msgReqData = new MsgReqData();
				// msgReqData.setPhone(phone);
				// msgReqData.setMsg("【水象借点花】恭喜您已成功注册水象借点花！您的登录账号：" + phone + "，初始密码：" + password
				// + "，如有问题致电水象借点花客服：400-827-6999，我们将竭诚为您服务。");
				// // 短信的环境 0 真是环境 1 测试环境
				// msgReqData.setType("0");
				// Response<Object> rsp = BeadWalletSendMsgService.sendMsg(msgReqData);
				// logger.info("发送短信" + rsp.getRequestMsg());
				String msg = "恭喜您已成功注册水象借点花！您的登录账号：" + phone + "，初始密码：" + password
						+ "，如有问题致电水象借点花客服：400-827-6999，我们将竭诚为您服务。";
//				boolean bo = sendMessageCommonService.commonSendMessage(phone, msg);
//				if (bo) {
//					logger.info("短信发送成功！");
//				} else {
//					logger.info("短信发送失败！");
//				}
				MessageDto messageDto = new MessageDto();
				messageDto.setBusinessScenario("1");
				messageDto.setPhone(phone);
				messageDto.setMsg(msg);
				messageDto.setType("1");
				RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
			}
			/************************* 工单信息 ***********************************/
			Integer status = iBwOrderService.findOrderStatusByBorrowerId(borrower.getId());
			logger.info("===========================================借款工单状态" + status);
			if (!CommUtils.isNull(status)) {
				if (status != 6 && status != 7 && status != 8) {
					logger.info("=========================您有借款工单未还清");
					return map;
				}
			}
			BwOrder bwOrder = new BwOrder();
			bwOrder.setBorrowerId(borrower.getId()); // 借款人id
			bwOrder.setOrderNo(OrderUtil.generateOrderNo()); // 工单编号
			// bwOrder.setBorrowAmount(Double.valueOf(haoDaiResponse.getLoanMoney()));
			// // 借款金额
			bwOrder.setBorrowUse(this.loanUsage(haoDaiResponse.getLoanUsage())); // 借款用途
			bwOrder.setRepayTerm(1); // 借款期限
			bwOrder.setStatusId(1L); // 工单状态
			bwOrder.setRepayType(1); // 1为先息后本
			bwOrder.setCreateTime(new Date()); // 创建时间
			bwOrder.setFlag(1); // 1表示为工单未删除
			bwOrder.setChannel(12); // 来源
			bwOrder.setCreateTime(new Date());
			bwOrder.setProductId(3);
			bwOrder.setProductType(1);// 单期
			String avoidFineDate = ResourceBundle.getBundle("haodai").getString("avoidFineDate");
			bwOrder.setAvoidFineDate(CommUtils.isNull(avoidFineDate) ? 3 : Integer.parseInt(avoidFineDate));// 免息天数（默认3天）

			// 添加工单,工单状态为初审
			iBwOrderService.addBwOrder(bwOrder);
			logger.info("================================添加工单成功");
			/********************* 保存好贷网第三方工单ID *****************************/
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(bwOrder.getId());
			bwOrderRong.setThirdOrderNo(haoDaiResponse.getOrderId().toString());
			bwOrderRong.setChannelId(Long.valueOf(12));
			BwOrderRong bwRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			if (CommUtils.isNull(bwRong)) {// 是否已存在，如果不存在记录则新增
				bwOrderRong.setCreateTime(new Date());
				bwOrderRongService.save(bwOrderRong);
				logger.info("================================保存好贷工单id:" + bwOrderRong.getThirdOrderNo());
			}

			/************************* 工单个人信息 ***********************************/
			BwPersonInfo bwPersonInfo = new BwPersonInfo();
			bwPersonInfo.setCityName(haoDaiResponse.getLiveProvinceName() + haoDaiResponse.getCity()); // 申请城市名称
			bwPersonInfo.setAddress(haoDaiResponse.getLiveAddress()); // 居住地址
			List<EmergencyContact> emergencyContact = haoDaiResponse.getEmergencyContact();
			bwPersonInfo.setRelationName(emergencyContact.get(0).getName()); // 家属联系人
			bwPersonInfo.setRelationPhone(emergencyContact.get(0).getPhone()); // 家属联系人电话
			bwPersonInfo.setUnrelationName(emergencyContact.get(1).getName()); // 非紧急联系人姓名
			bwPersonInfo.setUnrelationPhone(emergencyContact.get(1).getPhone()); // 非紧急联系人电话
			if (haoDaiResponse.getMarriage() == 2) {
				bwPersonInfo.setMarryStatus(1); // 婚姻状况
			} else {
				bwPersonInfo.setMarryStatus(0); // 婚姻状况
			}
			if (haoDaiResponse.getCarType() == 1) {
				bwPersonInfo.setCarStatus(0); // 是否有车
			} else {
				bwPersonInfo.setCarStatus(1); // 是否有车
			}
			if (haoDaiResponse.getHouseType() == 1) {
				bwPersonInfo.setHouseStatus(1); // 是否有房
			} else {
				bwPersonInfo.setHouseStatus(0); // 是否有房
			}
			bwPersonInfo.setOrderId(bwOrder.getId()); // 工单编号
			bwPersonInfo.setCreateTime(new Date()); // 创建时间
			// 添加个人工单信息
			iBwPersonInfoService.addBwPersonInfo(bwPersonInfo);
			logger.info("================================添加个人工单信息");
			/************************ 个人工作信息 ****************************************/
			BwWorkInfo bwWorkInfo = new BwWorkInfo();
			bwWorkInfo.setOrderId(bwOrder.getId()); // 工单编号
			bwWorkInfo.setCallTime("10:00-12:00"); // 方便接听电话时间
			bwWorkInfo.setComName(haoDaiResponse.getUnitName()); // 公司名称
			bwWorkInfo.setWorkYears(this.getUnitExperience(haoDaiResponse.getUnitExperience())); // 工龄
			bwWorkInfo.setIndustry(this.getIndustry(haoDaiResponse.getIndustry())); // 所属行业
			bwWorkInfo.setCreateTime(new Date()); // 创建时间
			iBwWorkInfoService.addBwWorkInfo(bwWorkInfo);

			/************************ 银行卡信息 **************************************/
			BwBankCard bwbankCard = iBwBankCardService.findBwBankCardByBoorwerId(borrower.getId());
			if (CommUtils.isNull(bwbankCard)) {
				BwBankCard bankCard = new BwBankCard();
				bankCard.setBorrowerId(borrower.getId()); // 借款人id
				bankCard.setCardNo(haoDaiResponse.getCardCode()); // 银行卡号
				bankCard.setCityCode(String.valueOf(haoDaiResponse.getBankCity())); // 城市code
				bankCard.setProvinceCode(String.valueOf(haoDaiResponse.getBankProvince())); // 省份code
				bankCard.setBankCode(haoDaiResponse.getBankCode()); // 银行code
				bankCard.setBankName(haoDaiResponse.getBankName()); // 银行名称
				bankCard.setSignStatus(0); // 0:未签约
				bankCard.setCreateTime(new Date());
				iBwBankCardService.addBwBankCard(bankCard);
				logger.info("================================添加银行卡信息");
			}
			BwOrderAuth bwOrderAuth = null;
			// 添加个人信息认证
			bwOrderAuth = bwOrderAuthService.findBwOrderAuth(bwOrder.getId(), 2);
			Date now = new Date();
			if (CommUtils.isNull(bwOrderAuth)) {
				logger.info("=================添加认证信息");
				bwOrderAuth = new BwOrderAuth();
				bwOrderAuth.setOrderId(bwOrder.getId());
				bwOrderAuth.setAuth_type(2);
				bwOrderAuth.setAuth_channel(Integer.valueOf(12)); // 渠道
				bwOrderAuth.setCreateTime(now);
				bwOrderAuth.setUpdateTime(now);
				logger.info("================当前时间:" + now);
				bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
			}
			/************************ 工单附件 **************************************/
			List<BwAdjunct> BwAdjunctList = new ArrayList<>();
			// 身份证正面
			BwAdjunct frontPhoto = new BwAdjunct();
			frontPhoto.setAdjunctType(1); // 类型
			frontPhoto.setAdjunctPath(this.upLoadPath()); // 生成上传图片路劲
			frontPhoto.setBorrowerId(borrower.getId()); // 借款人编号
			frontPhoto.setOrderId(bwOrder.getId()); // 工单编号
			frontPhoto.setCreateTime(new Date()); // 创建时间
			BwAdjunctList.add(frontPhoto);
			// 身份证反面
			BwAdjunct backPhoto = new BwAdjunct();
			backPhoto.setAdjunctType(2); // 类型
			backPhoto.setAdjunctPath(this.upLoadPath()); // 生成上传图片路劲
			backPhoto.setBorrowerId(borrower.getId()); // 借款人编号
			backPhoto.setOrderId(bwOrder.getId()); // 工单编号
			backPhoto.setCreateTime(new Date()); // 创建时间
			BwAdjunctList.add(backPhoto);
			// 持证自拍
			BwAdjunct idenScene = new BwAdjunct();
			idenScene.setAdjunctType(3); // 类型
			idenScene.setAdjunctPath(this.upLoadPath()); // 生成上传图片路劲
			idenScene.setBorrowerId(borrower.getId()); // 借款人编号
			idenScene.setOrderId(bwOrder.getId()); // 工单编号
			idenScene.setCreateTime(new Date()); // 创建时间
			BwAdjunctList.add(idenScene);
			for (int i = 0; i < BwAdjunctList.size(); i++) {
				logger.info("==============================添加图片");
				iBwAdjunctService.save(BwAdjunctList.get(i)); // 保存图片
			}
			List<String> UploadPath = new ArrayList<>();
			UploadPath.add(haoDaiResponse.getIdenFrontPhoto()); // 身份证正面url
			UploadPath.add(haoDaiResponse.getIdenBackPhoto()); // 身份证反面url
			UploadPath.add(haoDaiResponse.getIdenScene()); // 持证自拍url
			ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
			for (int i = 0; i < BwAdjunctList.size(); i++) {
				final int index = i;
				cachedThreadPool.execute(new Runnable() {
					@Override
					public void run() {
						logger.info("================" + index + "======" + UploadPath.get(index));
						HaoDaiServiceImpl haoDaiService = new HaoDaiServiceImpl();
						haoDaiService.urlUpload(UploadPath.get(index), BwAdjunctList.get(index).getAdjunctPath());
						logger.info("==========上传的图片路径为:" + BwAdjunctList.get(index).getAdjunctPath());
					}
				});
			}
			logger.info("================================上传附件成功");
			// 添加持证自拍认证
			bwOrderAuth = bwOrderAuthService.findBwOrderAuth(bwOrder.getId(), 3);
			if (CommUtils.isNull(bwOrderAuth)) {
				logger.info("=================添加认证信息");
				bwOrderAuth = new BwOrderAuth();
				bwOrderAuth.setOrderId(bwOrder.getId());
				bwOrderAuth.setAuth_type(3);
				bwOrderAuth.setAuth_channel(Integer.valueOf(12)); // 渠道
				bwOrderAuth.setCreateTime(now);
				bwOrderAuth.setUpdateTime(now);
				logger.info("================当前时间:" + now);
				bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
			}
			map.put("userId", borrower.getId());
			logger.info("================================借款人id:" + borrower.getId());
			map.put("orderId", bwOrder.getId());
			logger.info("================================工单id:" + bwOrder.getId());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public RepayDto findRepayDto(Long orderId) {
		String sql = "select r.reality_repay_money realityRepayMoney,o.create_time createTime,o.repay_term repayTerm"
				+ ",o.repay_type repayType,r.repay_time repayTime from bw_repayment_plan r LEFT JOIN bw_order o "
				+ " ON r.order_id=o.id where r.order_id=#{orderId}" + " AND o.status_id=9";
		return sqlMapper.selectOne(sql, orderId, RepayDto.class);
	}

	/**
	 * 上传至oss @Title: urlUpload @Description: TODO(这里用一句话描述这个方法的作用) @param @param url 上传图片当前路径 @param @param uploadPath
	 * 保存oss路径 @param @return 设定文件 @return String 返回类型 @throws
	 */
	private void urlUpload(String url, String uploadPath) {
		InputStream inputStream = this.getInputStream(url);
		if (!CommUtils.isNull(inputStream)) {
			// 上传至oss
			CdnUploadTools.uploadPic(inputStream, uploadPath);
		}
	}

	/**
	 * 生成上传oss图片路径 @Title: upLoadPath @Description: TODO(这里用一句话描述这个方法的作用) @param @return 设定文件 @return String
	 * 返回类型 @throws
	 */
	private String upLoadPath() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int number = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;// 产生1000-9999的随机数
		String uploadPath = "upload/backend/" + sdf.format(new Date()) + "/" + sdf2.format(new Date()) + number
				+ ".jpg";
		return uploadPath;
	}

	/**
	 * 从服务器获得一个输入流（这里指的是获取img输入流）
	 * 
	 * @param url 图pain路径
	 * @return
	 */
	private InputStream getInputStream(String imgUrl) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(imgUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 设置网络连接超时时间
			httpURLConnection.setConnectTimeout(3000);
			// 设置应用程序要从网络连接读取数据
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("GET");
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode == 200) {
				// 从服务器返回一个输入流
				inputStream = httpURLConnection.getInputStream();
			}
		} catch (MalformedURLException e) {
			logger.error("------------url错误----------MalformedURLException", e);
		} catch (IOException e) {
			logger.error("------------IOException----------io异常---------------------", e);
		}
		return inputStream;
	}

	@Override
	public int addBwUserEquipment(Long orderId) {
		int count = 0;
		// 调用接口2.3获取用户设备信息
		Response<UserEquipmentData> respData = null;
		try {
			String newId = bwOrderRongService.findThirdOrderNoByOrderId(String.valueOf(orderId));
			respData = BeadWalletHaoDaiService.getUserFacility(newId);
			if (respData.getRequestCode().equals("1000")) {
				if (!CommUtils.isNull(respData.getObj())) {
					BwUserEquipment bwUserEquipment = new BwUserEquipment();
					CommUtils.copyPropertiesIgnoreNull(respData.getObj(), bwUserEquipment);
					count = bwUserEquipmentService.save(bwUserEquipment);
				}
			}
		} catch (Exception e) {
			logger.error("============================获取设备信息失败!");
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Map<String, String> getLoanStatusData(Long orderId) {
		Map<String, String> content = new HashMap<>();
		List<OrderState> orderStates = null;
		OrderState orderState = new OrderState();
		try {
			String thirdOrderNo = bwOrderRongService.findThirdOrderNoByOrderId(String.valueOf(orderId));
			if (!CommUtils.isNull(thirdOrderNo)) {
				String states = null;
				Date createTime = null;
				orderStates = new ArrayList<OrderState>();
				content.put("orderId", String.valueOf(thirdOrderNo));
				// 查询工单状态
				int orderStatus = iBwOrderService.findOrderStatusByOrderId(orderId);
				if (orderStatus == 7 || orderStatus == 8) {
					createTime = bwRejectRecordService.findCreateTimeByOrderId(orderId);
					logger.info("==========================未通过的贷款订单时间戳:" + createTime);
					orderState.setState(9);
					orderState.setTime(createTime.getTime());
					orderStates.add(orderState);
					states = JsonUtils.toJson(orderStates);
					content.put("state", states);
					content.put("code", "1000");
					return content;
				}
				// 贷款订单通过审核
				if (orderStatus == 4) {
					orderStates = this.state1(orderStates, orderId, orderState);
					states = JsonUtils.toJson(orderStates);
					content.put("state", states);
					content.put("code", "1000");
					return content;
				}

				// 筹款中，等待到账
				if (orderStatus == 5 || orderStatus == 12 || orderStatus == 14 || orderStatus == 11) {
					orderStates = this.state1(orderStates, orderId, orderState);
					orderStates = this.state2(orderStates, orderId, orderState);
					states = JsonUtils.toJson(orderStates);
					content.put("state", states);
					content.put("code", "1000");
					return content;
				}

				// 放款已到账
				if (orderStatus == 9) {
					orderStates = this.state1(orderStates, orderId, orderState);
					orderStates = this.state2(orderStates, orderId, orderState);
					orderStates = this.state3(orderStates, orderId, orderState);
					states = JsonUtils.toJson(orderStates);
					content.put("code", "1000");
					content.put("state", states);
				}

				// 贷款还款已偿清
				if (orderStatus == 6) {
					orderStates = this.state1(orderStates, orderId, orderState);
					orderStates = this.state2(orderStates, orderId, orderState);
					orderStates = this.state3(orderStates, orderId, orderState);
					createTime = iBwOrderService.findUpdateTimeByOrderId(orderId);
					orderState.setState(4);
					orderState.setTime(createTime == null ? new Date().getTime() : createTime.getTime());
					orderStates.add(orderState);
					states = JsonUtils.toJson(orderStates);
					content.put("code", "1000");
					content.put("state", states);
				}

			}
		} catch (Exception e) {
			content.put("code", "1001");
			logger.info("=================贷款订单状态获取失败！");
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public PostLoanInfo getLoanInfo(Long orderId) {
		String content = null;
		// 获取好贷工单号
		PostLoanInfo postLoanInfo = new PostLoanInfo();
		try {
			String thirdOrderNo = bwOrderRongService.findThirdOrderNoByOrderId(String.valueOf(orderId));
			logger.info("=======================好贷工单号:" + thirdOrderNo);

			if (!CommUtils.isNull(thirdOrderNo)) {
				postLoanInfo.setOrderId(String.valueOf(thirdOrderNo));
				// 获取还款计划
				BwRepaymentPlan bwRepaymentPlan = iBwRepaymentPlanService.getBwRepaymentPlanByOrderId(orderId);
				logger.info("========================获取还款计划");
				if (!CommUtils.isNull(bwRepaymentPlan)) {
					postLoanInfo.setRepaymentDate(bwRepaymentPlan.getRepayTime().getDate()); // 每月还款日
					postLoanInfo.setRepaymentMoney(bwRepaymentPlan.getRealityRepayMoney()); // 每月还款金额(元)
					postLoanInfo.setFirstTime(bwRepaymentPlan.getRepayTime().getTime()); // 首次还款日
					BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
					logger.info("====================获取工单信息");
					if (!CommUtils.isNull(bwRepaymentPlan)) {
						double zjw = bwRepaymentPlan.getZjw() == null ? 0 : bwRepaymentPlan.getZjw(); //（code0090）
						postLoanInfo.setContractMoney(bwOrder.getContractAmount()); // 合同金额(元)
						postLoanInfo.setLoanMoney(bwOrder.getBorrowAmount() + zjw); // 贷款金额
						postLoanInfo.setLoanMonth(bwOrder.getRepayTerm()); // 贷款期限
						postLoanInfo.setInterestRate(bwOrder.getBorrowRate()); // 月贷款利率
					}
				}
			}
			if (!CommUtils.isNull(postLoanInfo)) {
				content = JsonUtils.toJson(postLoanInfo);
				logger.info("===============贷后相关信息：" + content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postLoanInfo;
	}

	@Override
	public String getDataBack(Long borrowerId) {
		String content = null;
		try {
			List<BwOperateVoice> bwOperateVoices = bwOperateVoiceService.queryOperateVoice(borrowerId);
			if (!CommUtils.isNull(bwOperateVoices)) {
				DataBackDto dataBackDto = new DataBackDto();
				dataBackDto.setBwOperateVoices(bwOperateVoices);
				BwBorrower bwBorrower = iBwBorrowerService.findBwBorrowerById(borrowerId);
				dataBackDto.setTel(bwBorrower.getPhone());
				dataBackDto.setDataTime(new Date().getTime());
				content = JsonUtils.toJson(dataBackDto);
			}
		} catch (Exception e) {
			logger.info("=====================获取运营商数据失败!");
			e.printStackTrace();
		}
		return content;
	}

	private List<OrderState> state1(List<OrderState> orderStates, Long orderId, OrderState orderState) {
		BwCheckRecord bwCheckRecord = new BwCheckRecord();
		bwCheckRecord.setOrderId(orderId);
		bwCheckRecord.setResult(4);
		bwCheckRecord.setStatusId(3L);
		Date createTime = null;
		try {
			createTime = bwCheckRecordService.findCreateTimeByOrderId(bwCheckRecord);
			logger.info("==========================贷款订单通过审核时间戳:" + createTime);
		} catch (Exception e) {
			logger.info("==========================贷款订单通过审核时间获取失败！:");
			e.printStackTrace();
		}
		orderState.setState(1);
		orderState.setTime(createTime == null ? new Date().getTime() : createTime.getTime());
		orderStates.add(orderState);
		return orderStates;
	}

	private List<OrderState> state2(List<OrderState> orderStates, Long orderId, OrderState orderState) {
		Date createTime = null;
		try {
			createTime = iBwOrderPushInfoService.findCreateTimeByOrderId(orderId);
			logger.info("==========================筹款中，等待到账时间戳:" + createTime);
		} catch (Exception e) {
			logger.info("==========================筹款中，等待到账时间获取失败！");
			e.printStackTrace();
		}
		orderState.setState(2);
		orderState.setTime(createTime == null ? new Date().getTime() : createTime.getTime());
		orderStates.add(orderState);
		return orderStates;
	}

	private List<OrderState> state3(List<OrderState> orderStates, Long orderId, OrderState orderState) {
		Date createTime = null;
		try {
			createTime = iBwOrderPushInfoService.findLoanTimeByOrderId(orderId);
			logger.info("==========================放款已到账时间戳:" + createTime);
		} catch (Exception e) {
			logger.info("==========================放款已到账时间戳获取失败!");
			e.printStackTrace();
		}
		orderState.setState(3);
		orderState.setTime(createTime == null ? new Date().getTime() : createTime.getTime());
		orderStates.add(orderState);
		return orderStates;
	}

	/**
	 * 转换借款用途
	 * 
	 * @param loanUsage
	 * @return
	 */
	private String loanUsage(int loanUsage) {
		String usage = "";
		switch (loanUsage) {
		case 1:
			usage = "房屋装修";
			break;
		case 2:
			usage = "购车";
			break;
		case 3:
			usage = "教育/培训";
			break;
		case 4:
			usage = "医疗";
			break;
		case 5:
			usage = "购物/娱乐";
			break;
		case 6:
			usage = "日常消费";
			break;
		case 7:
			usage = "扩大生产/经营";
			break;
		case 8:
			usage = "购买货物/原料/设备";
			break;
		case 9:
			usage = "经营资金周转";
			break;
		default:
			usage = "其他";
			break;
		}
		return usage;
	}

	/**
	 * 随机生成密码
	 * 
	 * @param length 长度
	 * @return
	 */
	private String getRandomCharAndNumr(int length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean b = random.nextBoolean();
			if (i % 2 == 0) {
				str += (char) (97 + random.nextInt(26));// 取得大写字母
			} else {
				str += String.valueOf(random.nextInt(10));
			}
		}
		return str;
	}

	/**
	 * 转换所属行业
	 * 
	 * @param industry
	 * @return
	 */
	private String getIndustry(Integer industry) {
		String industry1 = null;
		if (!CommUtils.isNull(industry)) {
			switch (industry) {
			case 1:
				industry1 = "农、林、牧、渔业";
				break;
			case 2:
				industry1 = "采掘业";
				break;
			case 3:
				industry1 = "制造业";
				break;
			case 4:
				industry1 = "电力、燃气及水的生产和供应业";
				break;
			case 5:
				industry1 = "建筑业";
				break;
			case 6:
				industry1 = "交通运输、仓储和邮政业";
				break;
			case 7:
				industry1 = "信息传输、计算机服务和软件业";
				break;
			case 8:
				industry1 = "批发和零售业";
				break;
			case 9:
				industry1 = "住宿和餐饮业";
				break;
			case 10:
				industry1 = "金融业";
				break;
			}
		}
		return industry1;
	}

	/**
	 * 转换工作年限
	 * 
	 * @param unitExperience
	 * @return
	 */
	private String getUnitExperience(Integer unitExperience) {
		String workingYears = null;
		if (!CommUtils.isNull(unitExperience)) {
			if (unitExperience < 1) {
				workingYears = "一年一内";
				return workingYears;
			}
			if (unitExperience >= 1 && unitExperience <= 3) {
				workingYears = "1-3年";
				return workingYears;
			}
			if (unitExperience >= 3 && unitExperience <= 5) {
				workingYears = "3-5年";
				return workingYears;
			}
			if (unitExperience >= 5 && unitExperience <= 10) {
				workingYears = "5-10年";
				return workingYears;
			}
			if (unitExperience > 10) {
				workingYears = "10年以上";
				return workingYears;
			}
		}
		return workingYears;
	}

	public static void main(String[] args) {
		HaoDaiServiceImpl haoDaiServiceImpl = new HaoDaiServiceImpl();
		for (int i = 0; i < 100; i++) {
			String str = haoDaiServiceImpl.getRandomCharAndNumr(6);
			System.out.println(str);
		}
		// String urlImg =
		// "http://openapi.api.test.haodai.com/Public/upload/shandai/profile/2016/10/19b2f19f9bd9_957.jpg";
		// HaoDaiServiceImpl haoDaiServiceImpl = new HaoDaiServiceImpl();
		// System.out.println(haoDaiServiceImpl.urlUpload(urlImg));
	}

}
