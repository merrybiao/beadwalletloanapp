package com.waterelephant.cashloan.controller;   // code0002

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.cashloan.entity.CashLoanReq;
import com.waterelephant.cashloan.entity.CashLoanRes;
import com.waterelephant.cashloan.entity.PullOrderReqData;
import com.waterelephant.cashloan.entity.PullOrderResData;
import com.waterelephant.cashloan.entity.PushUserReqData;
import com.waterelephant.cashloan.entity.PushUserResData;
import com.waterelephant.cashloan.util.CashLoanContext;
import com.waterelephant.cashloan.util.CashLoanUtils;
import com.waterelephant.entity.BwBlacklist;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOrderTem;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.register.util.DESUtil;
import com.waterelephant.register.util.RegisterUtils;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOrderTemService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;

@Controller
@RequestMapping("/app/cashLoan")
public class AppCashLoanController {
	private Logger logger = Logger.getLogger(AppCashLoanController.class);
	
	@Autowired
	private IBwBorrowerService bwBorrowerService;	//借款人信息表
	
	@Autowired
	private BwBlacklistService bwBlacklistService;	//根据身份证查询黑白名单
	
	@Autowired
	private IBwOrderService bwOrderService;		//借款工单
	
	@Autowired
	private BwRejectRecordService bwRejectRecordService;	//认证被拒记录
	
	@Autowired
	private IBwOrderChannelService orderChannelService;
	
	@Autowired
	private BwOrderRongService bwOrderRongService;
	
	@Autowired
	private BwOrderTemService bwOrderTemService;
	
	private static String CHANNEL = CashLoanContext.get("channel");							//渠道码
	private static String NEW_USER_RETURN_URL = CashLoanContext.get("newUser.returnUrl");	//新用户
	private static String OLD_USER_RETURN_URL = CashLoanContext.get("oldUser.returnUrl");	//老用户
	private static String CALL_BACK_DES = CashLoanContext.get("callBack.des");				//加密
	private static String CALL_BACK_URL = CashLoanContext.get("callBack.url");				//新用户注册成功,回调的url
	
	
	/**
	 * 从第三方接收用户信息,其他数据	
	 */
	@ResponseBody
	@RequestMapping("/pushUserInfo")								
	public CashLoanRes pushUserInfo(HttpServletRequest req){
		CashLoanRes res = new CashLoanRes();										//返回的对象
		String methodName = "[CASHLOAN] AppCashLoanController.pushUserInfo";
		
		try {
			CashLoanReq cashLoanReq = CashLoanUtils.convertReq2CashLoanReq(req);	//从Request中获取appid商户号,signkey数据签名串,data业务数据,封装到cashLoanReq
//			logger.info(methodName+" start,cashLoanReq="+cashLoanReq);
			String check = CashLoanUtils.commonCheck(req);		//请求的参数是否为空？
			
			if (StringUtils.isNotBlank(check)) {				//判断是否为空
				res.setCode("102");								//状态码
				res.setMessage(check);							//异常信息
				logger.info(methodName+" end,res="+res);
				return res;										//响应pojo
			}
			
			// 正常接收参数
			String data = cashLoanReq.getData();			//业务数据
			String signKey = cashLoanReq.getSignkey();		//数据签名串
			
			// 解密
			logger.info("开始解密,enData="+data);
			String desData = CashLoanUtils.desData(data);	//解析传递过来的业务数据	  desData
			logger.info("解密结果,desData="+desData);
			
			// 验证签名
//			logger.info("开始验证签名,desData="+desData+",signKey="+signKey);
			boolean checkResult = CashLoanUtils.checkSign(desData, signKey);		// 通过解密后的业务数据 验证 数据签名串
			logger.info("验证签名结果:"+checkResult);
			
			if (!checkResult) {
				res.setCode("102");
				res.setMessage("验证签名失败");
				logger.info(methodName+" end,res="+res);
				return res;
			}
			
			
			PushUserReqData pushUserReqData = CashLoanUtils.convertData2PushUserReqData(desData);			//把天眼传递过来的用户数据封装到pojo
			
			//检查用户是否存在    效验身份证,手机号,姓名是否和数据库匹配,判断是否为新用户,黑名单,重复
			boolean result = checkUserInfo(pushUserReqData.getScureid(), pushUserReqData.getMobile(), pushUserReqData.getRealname());
			
			//不匹配,返回异常
			if (!result) {
				res.setCode("102");
				res.setMessage("用户有未完成的订单或为黑名单用户");
				logger.info(methodName+" end,res="+res);
				return res;
			}
			
			
			logger.info("开始查询渠道表,channelCode="+CHANNEL);											
			BwOrderChannel bwOrderChannel = orderChannelService.getOrderChannelByCode(CHANNEL);			//2MH765BGHJ51I3A   bw_order_channel
			logger.info("结束查询渠道表,bwOrderChannel="+JSONObject.toJSONString(bwOrderChannel));
														
			if (CommUtils.isNull(bwOrderChannel)) {														//如果渠道号为空,异常
				res.setCode("102");
				res.setMessage("渠道无效");
				logger.info(methodName+" end,res="+res);
				return res;
			}
			
			PushUserResData pushUserResData = new PushUserResData();			//响应回调的对象  referrer_url+is_succeed
			
			String phone = pushUserReqData.getMobile();							// 通过第三方传递的手机号码,判断是不是新用户  	 bw_borrower
			logger.info("开始查询借款人信息,phone="+phone);
			BwBorrower borrower = new BwBorrower();
			borrower.setPhone(phone);
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);		//根据手机号码，查询是否存在对应借款人
			logger.info("结束查询接口人信息,borrower="+JSONObject.toJSONString(borrower));
			if (CommUtils.isNull(borrower)) {									//如果没有,创建借款人
				// 创建借款人
				logger.info("借款人信息不存在，开始创建借款人");
				String password = RegisterUtils.getRandNum(1,999999);			//设置随机密码
				borrower = new BwBorrower();
				borrower.setPhone(phone);										//手机号
				borrower.setName(pushUserReqData.getRealname());				//真实姓名
				borrower.setIdCard(pushUserReqData.getScureid());				//身份证号码
				borrower.setPassword(CommUtils.getMD5(password.getBytes()));	//设置用户密码,加密
				borrower.setAuthStep(1);										//'认证步骤 1:个人信息认证 2:工作信息认证 3:图片上传认证 4:已认证 5:运营商认证',
				borrower.setFlag(1);											// '删除标志 0:已删除 1:未删除',
				borrower.setState(1);											// 借款人状态 0:禁用 1:启用',
				borrower.setChannel(bwOrderChannel.getId());					//'注册渠道:1.ios 2.app 3.微信 4.融360， 17 分期管家'
				borrower.setCreateTime(Calendar.getInstance().getTime());		// 设置更新时间
				borrower.setUpdateTime(Calendar.getInstance().getTime());
				bwBorrowerService.addBwBorrower(borrower);						//保存借款人信息到数据库
				logger.info("生成的借款人id:" + borrower.getId());
				
				// 发送短信
				try {
					String message = RegisterUtils.getMsg(password);
					MessageDto messageDto = new MessageDto();
					messageDto.setBusinessScenario("1");
					messageDto.setPhone(phone);
					messageDto.setMsg(message);
					messageDto.setType("1");
					RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
				} catch (Exception e) {
					logger.error("发送短信异常:",e);
				}
				
				
				if (StringUtils.isNotBlank(CALL_BACK_URL)) {															//判断回调的url是否存在			
					Map<String, String> dataMap = new HashMap<String, String>();										
					dataMap.put("borrowerId", borrower.getId().toString());
					dataMap.put("channel", CHANNEL);
					dataMap.put("phone", phone);
					dataMap.put("password", borrower.getPassword());
					dataMap.put("flag", String.valueOf(borrower.getFlag()));
					dataMap.put("authStep", String.valueOf(borrower.getAuthStep()));
					dataMap.put("state", String.valueOf(borrower.getState()));
					dataMap.put("channelId", String.valueOf(borrower.getChannel()));
					String dataJson = JSONObject.toJSONString(dataMap);
					String enJson = DESUtil.encryption(dataJson, CALL_BACK_DES);										//加密
					
					//新用户,把注册加密后的用户信息json串,封装到响应的pojo
					//callBack.url=https://www.beadwallet.com/beadwalletloanapp/app/cashLoan/callBack.do?data={0} ,enJson
					pushUserResData.setReferrer_url(RegisterUtils.getReturnUrl(new Object[]{enJson}, CALL_BACK_URL));	
				}
				
			}else {
				// 老用户,直接跳转到登录,渠道名称
				// https://www.beadwallet.com/loanpage/html/Home/login.html?tel={0}&cid={1}		{phone,CHANNEL}
				pushUserResData.setReferrer_url(RegisterUtils.getReturnUrl(new Object[]{phone,CHANNEL}, OLD_USER_RETURN_URL));	
			}
			
			BwOrderTem bwOrderTem = new BwOrderTem();		//生成工单	bw_order_tem
			bwOrderTem.setChannelKey("cashloan");			
			bwOrderTem.setCreateTime(Calendar.getInstance().getTime());
			bwOrderTem.setPhone(phone);
			bwOrderTem.setThirdOrderNo(pushUserReqData.getSid());
			
			bwOrderTemService.save(bwOrderTem);				//生成工单
			
			pushUserResData.setIs_succeed("1");		//1推送成功 	2平台已存在此订单		 3推送失败
			String resData = CashLoanUtils.convertPushUserResData2Data(pushUserResData);		////拼接回调的响应结果. referrer_url &+ is_succeed
			logger.info("resData="+resData);
			res.setData(CashLoanUtils.enData(resData));		// 加密data,封装到res中
			res.setCode("200");
			res.setMessage("成功");
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			res.setCode("102");
			res.setMessage("系统异常，请稍后再试");
		}
		
		logger.info(methodName+" end,res="+res);
		return res;								// 响应第三方 
	}
	
	/**
	 * 回调
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/callBack.do")		//第三方登录
	public String callBack(HttpServletRequest request, HttpServletResponse response) {
		String methodName = "[CASHLOAN] AppCashLoanController.callBack";
		logger.info(methodName+" start");
		String newUserReturnUrl = null;
		
		try {
			String secretData = request.getParameter("data").replaceAll(" ", "+");
			String data = DESUtil.decryption(secretData, CALL_BACK_DES);					//解密,json数据
			Map dataMap = JSONObject.parseObject(data, Map.class);							//将json数据转换成map对象
			String borrowerId = (String) dataMap.get("borrowerId");							//得到每一个参数
			String channel = (String) dataMap.get("channel");
			
			String channelId = (String) dataMap.get("channelId");
			String phone = (String) dataMap.get("phone");
			String password = (String) dataMap.get("password");
			String flag = (String) dataMap.get("flag");
			String authStep = (String) dataMap.get("authStep");
			String state = (String) dataMap.get("state");
			
			newUserReturnUrl = RegisterUtils.getReturnUrl(new Object[]{channel}, NEW_USER_RETURN_URL);	// 带渠道号,封装到首页url
			String token = CommUtils.getUUID();															// 创建随机令牌 token
			request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);			// app会话token存入session
			SystemConstant.SESSION_APP_TOKEN.put(borrowerId, token);									// 借款人id,  token存入map
			Cookie uidcookie = new Cookie("cookie_uuid", borrowerId);									// cookie 存入借款人id
			uidcookie.setPath("/");																		// 统一服务器,设置共享 
			Cookie tokencookie = new Cookie("cookie_token", token);			
			tokencookie.setPath("/");
			AppResponseResult result = new AppResponseResult();											//app响应结果
			result.setCode("000");																		//服务器响应app结果对象
			result.setMsg("登录成功");
			Map<String, Object> map = new HashMap<String, Object>();									//创建map
			map.put("loginToken", token);																//把token存入map													
			
			Map<String, String> loginUser = new HashMap<String, String>();								
			loginUser.put("id", borrowerId);															//把借款人信息封装到map
			loginUser.put("phone", phone);
			loginUser.put("password", password);
			loginUser.put("flag", flag);
			loginUser.put("authStep", authStep);
			loginUser.put("state", state);
			loginUser.put("channel", channelId);
			map.put("loginUser", JSONObject.toJSONString(loginUser));											//把用户的登录信息封装到 map中
			
			result.setResult(map);																				//把map封装到   app响应结果result对象中
			Cookie userinfocookie = new Cookie("channel_cookie_user_info", JSONObject.toJSONString(result));	//把result转换成json,放入cookie
			userinfocookie.setPath("/");
			response.addCookie(uidcookie);
			response.addCookie(tokencookie);
			response.addCookie(userinfocookie);
		} catch (Exception e) {
			logger.error(methodName+" occured exception:", e);
		}
		
		return "redirect:" + newUserReturnUrl;																	//重定向到首页
	}
	
	/**
	 * 	第三方调用我方接口,查询工单
	 */
	@ResponseBody
	@RequestMapping("/pullOrderInfo")												
	public CashLoanRes pullOrderInfo(HttpServletRequest req){
		CashLoanRes res = new CashLoanRes();
		String methodName = "[CASHLOAN] AppCashLoanController.pullOrderInfo";
		
		try {
			CashLoanReq cashLoanReq = CashLoanUtils.convertReq2CashLoanReq(req);	//从Request中获取appid商户号,signkey数据签名串,data业务数据,封装到cashLoanReq
			logger.info(methodName+" start,cashLoanReq="+cashLoanReq);
			String check = CashLoanUtils.commonCheck(req);							//判断请求参数是否为空
			
			if (StringUtils.isNotBlank(check)) {									//异常
				res.setCode("102");
				res.setMessage(check);
				logger.info(methodName+" end,res="+res);
				return res;
			}
			
			String data = cashLoanReq.getData();
			String signKey = cashLoanReq.getSignkey();
			
			// 解密
			logger.info("开始解密,enData="+data);
			String desData = CashLoanUtils.desData(data);
			logger.info("解密结果,desData="+desData);
			
			// 验证签名
			logger.info("开始验证签名,desData="+desData+",signKey="+signKey);
			boolean checkResult = CashLoanUtils.checkSign(desData, signKey);		
			logger.info("验证签名结果:"+checkResult);
			
			if (!checkResult) {
				res.setCode("102");
				res.setMessage("验证签名失败");
				logger.info(methodName+" end,res="+res);
				return res;
			}
			
			PullOrderReqData pullOrderReqData = CashLoanUtils.convertData2PullOrderReqData(desData);		//响应结果 推送订单 send_time请求时间戳,sid会话标识
			
			if (StringUtils.isBlank(pullOrderReqData.getSid())) {
				res.setCode("102");
				res.setMessage("缺少必要参数");
				logger.info(methodName+" end,Sid is null,res="+res);
				return res;
			}
			
			if (StringUtils.isBlank(pullOrderReqData.getSend_time())) {
				res.setCode("102");
				res.setMessage("缺少必要参数");
				logger.info(methodName+" end,Send_time is null,res="+res);
				return res;
			}
			
			BwOrderRong bwOrderRong = new BwOrderRong();													// bw_order_rong 第三方工单
			bwOrderRong.setThirdOrderNo(pullOrderReqData.getSid());											// 设置第三方工单id
			logger.info("开始查询第三方订单,thirdOrderNo="+pullOrderReqData.getSid());
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);							// 去数据库查询第三方工单
			logger.info("结束查询第三方订单,bwOrderRong="+JSONObject.toJSONString(bwOrderRong));
			
			if (CommUtils.isNull(bwOrderRong)) {															// 如果没有工单信息,响应
				res.setCode("103");
				res.setMessage("没有查询到订单信息");
				logger.info(methodName+" end,bwOrderRong is null,res="+res);
				return res;
			}
			
			BwOrder bwOrder = new BwOrder();																//bw_order  工单表
			bwOrder.setId(bwOrderRong.getOrderId());														// 设置工单id												
			logger.info("开始查询订单,id="+bwOrder.getId());
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);											// 数据库查询工单
			logger.info("结束查询订单,bwOrder="+JSONObject.toJSONString(bwOrder));
			
			if (CommUtils.isNull(bwOrder)) {
				res.setCode("103");
				res.setMessage("没有查询到订单信息");
				logger.info(methodName+" end,bwOrder is null,res="+res);
				return res;
			}
			
			String status = CashLoanUtils.convertStatus(bwOrder.getStatusId());		//'工单状态id 1草稿  2初审  3终审  4待签约  5待放款  6结束  7拒绝  8撤回  9还款中  11待生成合同  12待债匹  13逾期  14债匹中',
			
			if (StringUtils.isBlank(status)) {										//状态空,异常
				res.setCode("102");
				res.setMessage("系统异常");
				logger.info(methodName+" end,当前状态["+bwOrder.getStatusId()+"]无法映射为现金贷状态,res="+res);
				return res;
			}
			logger.info("当前状态["+bwOrder.getStatusId()+"]映射为现金贷状态为["+bwOrder.getStatusId()+"]");
			
			logger.info("开始查询借款人信息,id="+bwOrder.getBorrowerId());
			BwBorrower borrower = bwBorrowerService.findBwBorrowerById(bwOrder.getBorrowerId());		// 查询借款人信息
			logger.info("结束查询接口人信息,borrower="+JSONObject.toJSONString(borrower));
			
			if (CommUtils.isNull(borrower)) {															//如果借款人没查到,异常
				res.setCode("102");
				res.setMessage("系统异常");
				logger.info(methodName+" end,borrower is null,res="+res);
				return res;
			}
			
			PullOrderResData pullOrderResData = new PullOrderResData();											//响应订单状态
			pullOrderResData.setStatus(status);																	//状态码
			pullOrderResData.setMobile(borrower.getPhone());													//借款人手机号
			pullOrderResData.setRealname(borrower.getName());													//借款人姓名
			pullOrderResData.setScureid(borrower.getIdCard());													//借款人身份证
			pullOrderResData.setProduct_name("水象分期");															//产品名称
			pullOrderResData.setSend_time(String.valueOf(Calendar.getInstance().getTime().getTime() / 1000));	//请求响应时间戳
			pullOrderResData.setUpdate_time(String.valueOf(bwOrder.getUpdateTime().getTime() / 1000));			//订单状态更新时间戳
			pullOrderResData.setSid(bwOrderRong.getThirdOrderNo());												//会话标识
		
			if (CommUtils.isNull((bwOrder.getCreditLimit()))) {								//creditLimit  有审批金额就返回审批金额,没有就返回0
				pullOrderResData.setAmount("0");
			}else {
				pullOrderResData.setAmount(String.valueOf(bwOrder.getCreditLimit()));						
			}
			
																										//待放款
			//pullOrderResData.setAmount(String.valueOf(bwOrder.getCreditLimit()));						//creditLimit  审批金额
			pullOrderResData.setPeriod("1");															//设置借款期限
			pullOrderResData.setRate("0.18");															//借款利率（保留两位小数)
			pullOrderResData.setRepayment("1");															//还款方式 （1等额本息还款 2等额本金还款 3等额递增(减) 4按期付息还本）
			
			String resData = CashLoanUtils.convertPullOrderResData2Data(pullOrderResData);					//封装响应数据
			logger.info("resData="+resData);
			String enData = CashLoanUtils.enData(resData);													//将数据进行加密
			res.setData(enData);
			res.setCode("200");
			res.setMessage("成功");
		}catch(Exception e){
			logger.error(methodName + " 异常", e);
			res.setCode("102");
			res.setMessage("系统异常，请稍后再试");
		}
		
		logger.info(methodName+" end,res="+res);
		
		return res;
	}
	
	private boolean checkUserInfo(String idCard, String phone, String name){

		// 1判断是否存在借款人信息，如果不存在则是新用户,返回200+is_reloan=0，否则查询绑卡信息
		logger.info("开始查询借款人信息,idCard:" + idCard);		
		BwBorrower borrower = new BwBorrower();
		borrower.setIdCard(idCard);					//根据用户填入的身份证信息来查询
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		logger.info("查询结果:" + JSONObject.toJSONString(borrower));

		if (CommUtils.isNull(borrower)) {
//			logger.info("借款人信息为空");
			return true;
		}

		if (!name.equals(borrower.getName())) {
//			logger.info("借款人姓名不一致");
			return false;
		}

//		logger.info("开始验证手机号");
		if (!phone.equals(borrower.getPhone())) {
//			logger.info("手机号不一致");
			return false;
		}

//		logger.info("开始验证身份证");
		if (!idCard.equals(borrower.getIdCard())) {
//			logger.info("身份证不一致");
			return false;
		}

		// 3判断是否是黑名单，如果存在则返回400，如果没有则查询是否有进行中的订单
//		logger.info("开始验证系统平台黑名单");
		Example example = new Example(BwBlacklist.class);
		String idNo = borrower.getIdCard();		
		// sort 1：黑名单，2：灰名单拒，3：白名单
		// status '状态审核：0，未审核，1通过,2 拒绝'
		// card  身份证号
		example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card",		//bw_blacklist 黑名单记录
				idNo.toUpperCase());
		List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
		if (!CommUtils.isNull(desList)) {
			return false;
		}

		// 4查询是否有进行中的订单，如果存在则返回400，否则查询是否有被拒记录
		logger.info("开始查询进行中的订单:borrowerId=" + borrower.getId());
		Long count = bwOrderService.findProOrder(String.valueOf(borrower.getId()));					//这是那个表???
		logger.info("结束查询进行中的订单:count:" + count);
		if (!CommUtils.isNull(count) && count.intValue() > 0) {
//			logger.info("有进行中的订单");
			return false;
		}

		// 5查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
//		logger.info("开始查询拒绝记录");
		BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());		// bw_reject_record  被拒表
		logger.info("结束查询拒绝记录,rejectRecord=" + JSONObject.toJSONString(record));
		if (!CommUtils.isNull(record)) {
			// 永久拒绝
			if ("0".equals(String.valueOf(record.getRejectType()))) {			//0.永久被拒 1.非永久被拒',
//				logger.info("有永久拒绝记录");
				return false;
			} else {
				Date rejectDate = record.getCreateTime();
				long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime())
						/ (24 * 60 * 60 * 1000);
				if (day <= 30) {
//					logger.info("临时拒绝还未过期");
					return false;
				}
			}
		}
		
		return true;
	}
}