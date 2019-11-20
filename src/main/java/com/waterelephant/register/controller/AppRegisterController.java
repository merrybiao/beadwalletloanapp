package com.waterelephant.register.controller;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waterelephant.entity.BwBorrowerDetail;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.entity.BwBlacklist;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.faceID.utils.DateUtils;
import com.waterelephant.register.entity.JdqResp;
import com.waterelephant.register.entity.RegisterReqData;
import com.waterelephant.register.entity.RegisterResData;
import com.waterelephant.register.entity.RegisterResp;
import com.waterelephant.register.entity.UserAttribute;
import com.waterelephant.register.util.Codec;
import com.waterelephant.register.util.DESUtil;
import com.waterelephant.register.util.MessageSign;
import com.waterelephant.register.util.RSAUtil;
import com.waterelephant.register.util.RegisterConstant;
import com.waterelephant.register.util.RegisterUtils;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;
 
@Controller
@RequestMapping("/app/register")
public class AppRegisterController {
	
	Logger logger = Logger.getLogger(AppRegisterController.class);
	 
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	
	@Autowired
	private IBwOrderChannelService orderChannelService;
	
	@Autowired
	private BwBlacklistService bwBlacklistService;
	
	private static String NEW_USER_CODE = "0";
	private static String OLD_USER_CODE = "1";
	private static String BLACK_USER_CODE = "2";
	
	private static String DES_KEY_SUFFIX = ".DES";
	private static String PUB_KEY_SUFFIX = ".PUB";
	private static String NEW_USER_RETURN_URL_SUFFIX = ".newUser.returnUrl";
	private static String OLD_USER_RETURN_URL_SUFFIX = ".oldUser.returnUrl";
	private static String CALL_BACK = "callBack";
	private static String SECRET_KEY = "secretKey";
	private static String APPLY_URL = "applyUrl";
	private static String CALL_BACK_DES = "callback.des";
	
	@ResponseBody
	@RequestMapping("/register.do")
	public RegisterResp<RegisterResData> register(HttpServletRequest request, HttpServletResponse response) {
		RegisterResp<RegisterResData> resp = new RegisterResp<RegisterResData>();
		RegisterResData resData = new RegisterResData();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String sessionId = DateUtils.getDateHMToString();						//通过当前时间获得唯一标识符
		String methodName = "AppRegisterController.register";					//当前方法名
		String channel = null;
		try {
			logger.info(sessionId+":"+methodName+"start");						//方法开始
			String check = RegisterUtils.commonCheck(request);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMessage(check);
				logger.info(sessionId+":"+methodName+" end,返回结果："+JSON.toJSONString(resp));//日志打印方法名和返回值
				return resp;
			}
			//参数格式正确
			String data = request.getParameter("data").replaceAll(" ", "+");
			channel = request.getParameter("channel");
			String sign = request.getParameter("sign").replaceAll(" ", "+");
			String desKey = RegisterConstant.KEY_MAP.get(DES_KEY_SUFFIX);
			String pubKey = RegisterConstant.KEY_MAP.get(PUB_KEY_SUFFIX);
			String newUserReturnUrl = RegisterConstant.KEY_MAP.get(NEW_USER_RETURN_URL_SUFFIX);
			String oldUserReturnUrl = RegisterConstant.KEY_MAP.get(OLD_USER_RETURN_URL_SUFFIX);
			String callBack = RegisterConstant.KEY_MAP.get(CALL_BACK);
			String callBackDes = RegisterConstant.KEY_MAP.get(CALL_BACK_DES);
			if (StringUtils.isBlank(desKey)) {
				resp.setCode("101");
				resp.setMessage("获取对称加密密钥为空");
				logger.info(sessionId+":"+methodName+"["+channel+"]"+ " end,返回结果："+JSON.toJSONString(resp));
				return resp;
			}
			
			logger.info("开始解密请求报文,data="+data+",desKey="+desKey);
			data = DESUtil.decryption(data.replaceAll(" ", "+"), desKey);//对data进行DES解密
			logger.info(sessionId+":"+methodName+"data解密结果："+JSON.toJSONString(data));
			
			logger.info(sessionId+":"+methodName+"开始验签");
			boolean checkSign = RSAUtil.checksign(pubKey, data, sign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMessage("验证签名失败");
				logger.info(sessionId+":"+methodName+"["+channel+"]"+ " end,返回结果："+JSON.toJSONString(resp));
				return resp;
			}
			logger.info(sessionId+":"+methodName+"["+channel+"]"+ "验证签名通过");
			
			RegisterReqData reqData = JSONObject.parseObject(data, RegisterReqData.class);//解析data为reqData对象

			if (CommUtils.isNull(reqData)) {
				resp.setCode("101");
				resp.setMessage("data请求参数无效");
				logger.info(sessionId+":"+methodName+"["+channel+"]"+ " end,返回结果："+JSON.toJSONString(resp));
				return resp;
			}
			
			if (CommUtils.isNull(reqData.getPhone())) {
				resp.setCode("101");
				resp.setMessage("获取手机号码为空");
				logger.info(sessionId+":"+methodName+"["+channel+"]"+ " end,返回结果："+JSON.toJSONString(resp));
				return resp;
			}
			
			logger.info(sessionId+":"+methodName+"开始查询渠道表,channelCode="+channel);
			BwOrderChannel bwOrderChannel = orderChannelService.getOrderChannelByCode(channel);
			logger.info(sessionId+":"+methodName+"结束查询渠道表,bwOrderChannel="+JSONObject.toJSONString(bwOrderChannel));
			
			if (CommUtils.isNull(bwOrderChannel)) {
				resp.setCode("101");
				resp.setMessage("渠道无效");
				logger.info(sessionId+":"+methodName+"["+channel+"]"+ " end,返回结果："+JSON.toJSONString(resp));
				return resp;
			}
			
			resp.setCode("200");
			resp.setMessage("成功");
			
			String phone = reqData.getPhone();
			logger.info(sessionId+":"+methodName+"开始查询借款人信息,phone="+phone);
			BwBorrower borrower = new BwBorrower();
			borrower.setPhone(phone);
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			logger.info(sessionId+":"+methodName+"结束查询接款人信息,borrower="+JSONObject.toJSONString(borrower));
			if (CommUtils.isNull(borrower)) {
				// 创建借款人
				logger.info(sessionId+":"+methodName+"借款人信息不存在，开始创建借款人");
				String password = RegisterUtils.getRandNum(1,999999);//生成一个六位数加上a的密码
				borrower = new BwBorrower();
				borrower.setPhone(phone);
				borrower.setPassword(CommUtils.getMD5(password.getBytes()));
				borrower.setAuthStep(1);
				borrower.setFlag(1);
				borrower.setState(1);
				borrower.setChannel(bwOrderChannel.getId());
				borrower.setCreateTime(Calendar.getInstance().getTime());
				borrower.setUpdateTime(Calendar.getInstance().getTime());
				bwBorrowerService.addBwBorrower(borrower);

				Date nowDate = new Date();
				BwBorrowerDetail bwBorrowerDetail = new BwBorrowerDetail();
				bwBorrowerDetail.setBorrowerId(borrower.getId());
				bwBorrowerDetail.setUserType(1);
				bwBorrowerDetail.setCreateTime(nowDate);
				bwBorrowerDetail.setUpdateTime(nowDate);

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
					logger.error(sessionId+":"+methodName+"发送短信异常:",e);
				}
				
				resData.setPhone(phone);
				resData.setUserType(NEW_USER_CODE);
				if (StringUtils.isNotBlank(newUserReturnUrl)) {
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("borrowerId", borrower.getId().toString());
					dataMap.put("channel", channel);
					dataMap.put("phone", phone);
					dataMap.put("password", borrower.getPassword());
					dataMap.put("flag", String.valueOf(borrower.getFlag()));
					dataMap.put("authStep", String.valueOf(borrower.getAuthStep()));
					dataMap.put("state", String.valueOf(borrower.getState()));
					dataMap.put("channelId", String.valueOf(borrower.getChannel()));
					String dataJson = JSONObject.toJSONString(dataMap);
					String enJson = DESUtil.encryption(dataJson, callBackDes);//将JSON加密
					resData.setReturnUrl(RegisterUtils.getReturnUrl(new Object[]{enJson}, callBack));
					resp.setResult(resData);
				}
				stopWatch.stop();
				logger.info(sessionId+":"+methodName+"["+channel+"]"+ " end,resp="+resp+",costTime="+stopWatch.getTotalTimeMillis());
				return resp;
			}
			
			Example example = new Example(BwBlacklist.class);
			String idNo = borrower.getIdCard();
			
			if (StringUtils.isNotBlank(idNo)) {
				example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card",
						idNo.toUpperCase());
				logger.info(sessionId+":"+methodName+"开始查询黑名单记录,idCard="+idNo.toUpperCase());
				List<BwBlacklist> blackList = bwBlacklistService.findBwBlacklistByExample(example);
				logger.info(sessionId+":"+methodName+"结束查询黑名单记录,blackList="+JSONObject.toJSONString(blackList));
				if (!CommUtils.isNull(blackList)) {
					resData.setPhone(phone);
					resData.setUserType(BLACK_USER_CODE);
					resData.setReturnUrl(RegisterUtils.getReturnUrl(new Object[]{phone,channel}, oldUserReturnUrl));
					resp.setResult(resData);//如果是黑名单返回的returnUrl是空的
					logger.info(sessionId+":"+methodName+"["+channel+"]"+ " end,resp="+resp);
					return resp;
				}
			}
			
			resData.setUserType(OLD_USER_CODE);
			resData.setPhone(phone);
			if (StringUtils.isNotBlank(oldUserReturnUrl)) {
				resData.setReturnUrl(RegisterUtils.getReturnUrl(new Object[]{phone,channel}, oldUserReturnUrl));
				resp.setResult(resData);
			}
		} catch (Exception e) {
			logger.error(sessionId+":"+methodName+"["+channel+"]"+ " 异常", e);
			resp.setCode("101");
			resp.setMessage("系统异常，请稍后再试");
		}
		
		logger.info(sessionId+":"+methodName+"["+channel+"]"+ " end,resp="+resp);
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/registerJieDianQian.do")
	public JdqResp registerJieDianQian(HttpServletRequest request) {
		JdqResp resp = new JdqResp();

		String methodName = "AppRegisterController.registerJieDianQian";
		logger.info(methodName + " start");
		
		try {
			logger.info("开始验证请求参数");
			String check = RegisterUtils.checkJieDianQian(request);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				logger.info(methodName + " end,resp="+resp);
				return resp;
			}
			logger.info("请求参数验证通过");
			
			String apply_no = request.getParameter("apply_no");
			String channel_no = request.getParameter("channel_no");
			String apply_info = request.getParameter("apply_info").replaceAll(" ", "+");
			String user_attribute = request.getParameter("user_attribute").replaceAll(" ", "+");
			String timestamp = request.getParameter("timestamp");
			String sign = request.getParameter("sign");
			
			TreeMap<String, String> treeMap = new TreeMap<String, String>();
			
			treeMap.put("apply_no", apply_no);
			treeMap.put("channel_no", channel_no);
			treeMap.put("apply_info", apply_info);
			treeMap.put("user_attribute", user_attribute);
			treeMap.put("timestamp", timestamp);
			
			String plainText = MessageSign.paramTreeMapToString(treeMap);
			
			logger.info("开始验证签名,sign="+sign+",plainText="+plainText);
			
			String mySign = MessageSign.signParams(RegisterConstant.KEY_MAP.get(SECRET_KEY), plainText);
			if (!mySign.equals(sign)) {
				resp.setCode("101");
				resp.setMsg("验证签名失败");
				logger.info(methodName + " end,resp="+resp);
				return resp;
			}
			logger.info("验证签名成功");
			
			logger.info("开始解密:"+user_attribute);
			String userAttrJson = Codec.base64StrDecode(RegisterConstant.KEY_MAP.get(SECRET_KEY), user_attribute);
			logger.info("解密结果:"+userAttrJson);
			
			UserAttribute userAttribute = JSONObject.parseObject(userAttrJson.trim(), UserAttribute.class);
			
			if (CommUtils.isNull(userAttribute)) {
				resp.setCode("101");
				resp.setMsg("参数错误");
				logger.info(methodName + " end,userAttribute is null,resp="+resp);
				return resp;
			}
			
			String idCard = userAttribute.getIdcard();
			String name = userAttribute.getName();
			String mobile = userAttribute.getMobilephone();
			
			if (StringUtils.isBlank(name)) {
				resp.setCode("101");
				resp.setMsg("参数错误");
				logger.info(methodName + " end,name is null,resp="+resp);
				return resp;
			}
			
			if (StringUtils.isBlank(idCard)) {
				resp.setCode("101");
				resp.setMsg("参数错误");
				logger.info(methodName + " end,idcard is null,resp="+resp);
				return resp;
			}
			
			if (StringUtils.isBlank(mobile)) {
				resp.setCode("101");
				resp.setMsg("参数错误");
				logger.info(methodName + " end,mobile is null,resp="+resp);
				return resp;
			}
			
			logger.info("开始查询渠道表,channelCode="+channel_no);
			BwOrderChannel bwOrderChannel = orderChannelService.getOrderChannelByCode(channel_no);
			logger.info("结束查询渠道表,bwOrderChannel="+JSONObject.toJSONString(bwOrderChannel));
			
			if (CommUtils.isNull(bwOrderChannel)) {
				resp.setCode("101");
				resp.setMsg("渠道无效");
				logger.info("bwOrderChannel is null");
				logger.info(methodName + " end,resp="+resp);
				return resp;
			}
			
			BwBorrower borrower = new BwBorrower();
			borrower.setIdCard(userAttribute.getIdcard());
			logger.info("开始查询借款人信息,idcard:"+userAttribute.getIdcard());
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			logger.info("结束查询借款人信息,borrower:"+JSONObject.toJSONString(borrower));
			if (CommUtils.isNull(borrower)) {
				// 创建借款人
				logger.info("借款人信息不存在，开始创建借款人");
				String password = RegisterUtils.getRandNum(1,999999);
				borrower = new BwBorrower();
				borrower.setName(name);
				borrower.setIdCard(idCard);
				borrower.setAge(RegisterUtils.getAgeByIdCard(idCard));
				borrower.setSex(RegisterUtils.getSexByIdCard(idCard));
				borrower.setPhone(mobile);
				borrower.setPassword(CommUtils.getMD5(password.getBytes()));
				borrower.setAuthStep(1);
				borrower.setFlag(1);
				borrower.setState(1);
				borrower.setChannel(bwOrderChannel.getId());
				borrower.setCreateTime(Calendar.getInstance().getTime());
				borrower.setUpdateTime(Calendar.getInstance().getTime());
				bwBorrowerService.addBwBorrower(borrower);
				logger.info("生成的借款人id:" + borrower.getId());
				
				// 发送短信
				try {
					String message = RegisterUtils.getMsg(password);
					MessageDto messageDto = new MessageDto();
					messageDto.setBusinessScenario("1");
					messageDto.setPhone(mobile);
					messageDto.setMsg(message);
					messageDto.setType("1");
					RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
				} catch (Exception e) {
					logger.error("发送短信异常:",e);
				}
				
				resp.setIs_new_user("1");
				resp.setCode("0");
				resp.setApply_url(RegisterConstant.KEY_MAP.get(APPLY_URL));
				logger.info(methodName + " end,resp="+resp);
				return resp;
			}
			
			if (bwOrderChannel.getId().intValue() == borrower.getChannel().intValue()) {
				resp.setIs_new_user("2");
			}else {
				resp.setIs_new_user("3");
			}
			
			resp.setCode("0");
			resp.setApply_url(RegisterConstant.KEY_MAP.get(APPLY_URL));
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("101");
			resp.setMsg("系统异常，请稍后再试");
		}
		
		logger.info(methodName + " end,resp="+resp);
		return resp;
	}
	
	/**
	 * 回调
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/callBack.do")
	public String callBack(HttpServletRequest request, HttpServletResponse response) {
		String methodName = "AppRegisterController.callBack";
		String sessionId = DateUtils.getDateHMToString();//通过当前时间获得唯一标识符
		logger.info(sessionId+methodName+" start");
		String newUserReturnUrl = null;
		
		try {
			String secretData = request.getParameter("data").replaceAll(" ", "+");
			String callBackDes = RegisterConstant.KEY_MAP.get(CALL_BACK_DES);
			String data = DESUtil.decryption(secretData, callBackDes);
			
			Map dataMap = JSONObject.parseObject(data, Map.class);
			String borrowerId = (String) dataMap.get("borrowerId");
			String channel = (String) dataMap.get("channel");
			
			String channelId = (String) dataMap.get("channelId");
			String phone = (String) dataMap.get("phone");
			String password = (String) dataMap.get("password");
			String flag = (String) dataMap.get("flag");
			String authStep = (String) dataMap.get("authStep");
			String state = (String) dataMap.get("state");
			
			newUserReturnUrl = RegisterConstant.KEY_MAP.get(NEW_USER_RETURN_URL_SUFFIX);
			newUserReturnUrl = RegisterUtils.getReturnUrl(new Object[]{channel}, newUserReturnUrl);
			String token = CommUtils.getUUID();
			request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
			SystemConstant.SESSION_APP_TOKEN.put(borrowerId, token);
			Cookie uidcookie = new Cookie("cookie_uuid", borrowerId);
			uidcookie.setPath("/");
			Cookie tokencookie = new Cookie("cookie_token", token);
			tokencookie.setPath("/");
			AppResponseResult result = new AppResponseResult();
			result.setCode("000");
			result.setMsg("登录成功");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loginToken", token);
			
			Map<String, String> loginUser = new HashMap<String, String>();
			loginUser.put("id", borrowerId);
			loginUser.put("phone", phone);
			loginUser.put("password", password);
			loginUser.put("flag", flag);
			loginUser.put("authStep", authStep);
			loginUser.put("state", state);
			loginUser.put("channel", channelId);
			map.put("loginUser", JSONObject.toJSONString(loginUser));
			result.setResult(map);
			Cookie userinfocookie = new Cookie("channel_cookie_user_info", JSONObject.toJSONString(result));
			userinfocookie.setPath("/");
			response.addCookie(uidcookie);
			response.addCookie(tokencookie);
			response.addCookie(userinfocookie);
		} catch (Exception e) {
			logger.error(sessionId+methodName+" occured exception:", e);
		}
		
		return "redirect:" + newUserReturnUrl;
	}
	
//	@ResponseBody
//	@RequestMapping("/registerJieDianQian.do")
//	public RegisterResp<RegisterResData> registerJieDianQian1(HttpServletRequest request) {
//		RegisterResp<RegisterResData> resp = new RegisterResp<RegisterResData>();
//		RegisterResData resData = new RegisterResData();
//		resp.setResult(resData);
//
//		String methodName = "AppRegisterController.registerJieDianQian";
//		logger.info(methodName + " start");
//		
//		try {
//			logger.info("开始验证请求参数");
//			String check = RegisterUtils.checkJieDianQian(request);
//
//			if (StringUtils.isNotBlank(check)) {
//				resp.setCode("101");
//				resp.setMessage(check);
//				logger.info(methodName + " end,resp="+resp);
//				return resp;
//			}
//			logger.info("请求参数验证通过");
//			
//			
//			String mobile = request.getParameter("mobile");
//			String name = request.getParameter("name");
//			String idCard = request.getParameter("idcard");
//			String sign = request.getParameter("sign");
//			
//			TreeMap<String, String> treeMap = new TreeMap<String, String>();
//			
//			treeMap.put("mobile", mobile);
//			treeMap.put("name", name);
//			treeMap.put("idcard", idCard);
//			
//			String plainText = MessageSign.paramTreeMapToString(treeMap);
//			
//			logger.info("开始验证签名,sign="+sign+",plainText="+plainText);
//			
//			String mySign = MessageSign.signParams(RegisterConstant.KEY_MAP.get(SECRET_KEY), plainText);
//			if (!mySign.equals(sign)) {
//				resp.setCode("101");
//				resp.setMessage("验证签名失败");
//				logger.info(methodName + " end,resp="+resp);
//				return resp;
//			}
//			logger.info("验证签名成功");
//			
//			logger.info("开始查询借款人信息,idCard="+idCard);
//			BwBorrower borrower = new BwBorrower();
//			borrower.setIdCard(idCard);
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			logger.info("结束查询接口人信息,borrower="+JSONObject.toJSONString(borrower));
//			if (CommUtils.isNull(borrower)) {
//				// 创建借款人
//				logger.info("借款人信息不存在，开始创建借款人");
//				String password = RegisterUtils.getRandomPwd();
//				borrower = new BwBorrower();
//				borrower.setName(name);
//				borrower.setIdCard(idCard);
//				borrower.setAge(RegisterUtils.getAgeByIdCard(idCard));
//				borrower.setSex(RegisterUtils.getSexByIdCard(idCard));
//				borrower.setPhone(mobile);
//				borrower.setPassword(CommUtils.getMD5(password.getBytes()));
//				borrower.setAuthStep(1);
//				borrower.setFlag(1);
//				borrower.setState(1);
//				borrower.setChannel(1111111111);
//				borrower.setCreateTime(Calendar.getInstance().getTime());
//				borrower.setUpdateTime(Calendar.getInstance().getTime());
//				bwBorrowerService.addBwBorrower(borrower);
//				logger.info("生成的借款人id:" + borrower.getId());
//				 
//				// 发送短信
//				try {
//					String message = RegisterUtils.getMsg(password);
////					MsgReqData msg = new MsgReqData();
////					msg.setPhone(mobile);
////					msg.setMsg(message);
////					msg.setType("0");
////					logger.info("开始发送密码短信,phone="+mobile);
////					Response<Object> sendResp = BeadWalletSendMsgService.sendMsg(msg);
////					logger.info("发送完成,发送结果："+JSONObject.toJSONString(sendResp));
////					boolean bo = sendMessageCommonService.commonSendMessage(mobile, message);
////					if (bo) {
////						logger.info("短信发送成功！");
////					}else {
////						logger.info("短信发送失败！");					
////					}
//					MessageDto messageDto = new MessageDto();
//					messageDto.setBusinessScenario("1");
//					messageDto.setPhone(mobile);
//					messageDto.setMsg(message);
//					messageDto.setType("1");
//					RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
//				} catch (Exception e) {
//					logger.error("发送短信异常:",e);
//				}
//			}
//			
//			resp.setCode("200");
//			resp.setMessage("成功");
//		} catch (Exception e) {
//			logger.error(methodName + " 异常", e);
//			resp.setCode("101");
//			resp.setMessage("系统异常，请稍后再试");
//		}
//		
//		return resp;
//	}
	
//	public static void main(String[] args) {
//		String data = "{\"phone\":\"13550000012\"}";
//		try {
//			String prikeyvalue = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAPCa7bJh5+Ap+3wZB6iyHpYd1FWAjPvPA4s61p7l7fRyU7TwVm1FwJtpmzdPHv4YZkXvUpmotlE0w6WnEzhihbtcki2F4alc+t4T//9pKdG7IFzOVHhaYDpyDRjRp19c/SUErwxe/IUhEcTSCfjHmh2CX4AqxOKYp0XN30O1VDftAgMBAAECgYBLZ5KU9CASXbXmB4Yh9WimjUStYzNL+23V5aO7er3ffTEEpMnBRzn8utUdOVuyMH1k74MIixpQSuQB92GLqzzThrgPFCRdaVGapNHydyTQZxtAXuxvXZHkGFNWc1DaIaFROVikbxL2RfY1YEHzrXcLMCeCdr7E8BLHi9BCSs7llQJBAPmsCcfQnFv3rDOSdDalIvCpsQC9iyBc3MXsqEQSd5F5ZoC2CKiXGHOHPUO63klXXklQFwyWWxTxwgDbfgzLjkcCQQD2tA+4/PoJnM2CsMixrGlXiVYcOlos2Q3zB5BqZEo3yNwCf1SO4U0B8SRl8jjM+ZZJ0NIZis4TX6q7NLrQUR4rAkAS8vmzDqQvXrbVGzJyy0nBlj923OvoQuQGqZWDxRsKEXq0Hhy+HumeKw99SnuAeX8QdWGqdOboygh9ZCtG8VQVAkB87FzxzZhHedFPFnnqDmYhX3ftP1ceUqMPp4rndPVoHhvaX+PPniLCkvtdbDR7Bv+lTUq2iGUlxlHMDj1y80CVAkBFDAd5XanlJym4tkUs9Ib4UbGcLhXDWYeKwBJVc0c+awmxpSIN8A+UEpUbeOoC+lzST1F84+NqM0HG+Lxd/NMU";
//			String sign = RSAUtil.sign(prikeyvalue, data);
//			System.out.println("xx:"+sign);
//			String enData = DESUtil.encryption("{\"phone\":\"13550000012\"}", "JDWAHFHW");
//			System.out.println(enData);
//			String test1 = "wjQw4Z9y24mZGdoMPEuqDQfiYmo5lsuW";
//			System.out.println(DESUtil.decryption(enData, "JDWAHFHW"));
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//	}
}