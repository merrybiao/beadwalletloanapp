package com.waterelephant.jiufu.controller;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.faceID.utils.DateUtils;
import com.waterelephant.jiufu.entity.JiufuResp;
import com.waterelephant.jiufu.entity.JiufuRespData;
import com.waterelephant.jiufu.util.JiufuConstant;
import com.waterelephant.jiufu.util.JiufuUtils;
import com.waterelephant.jiufu.util.RSAUtil;
import com.waterelephant.register.util.DESUtil;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;


@Controller
@RequestMapping("/app/jiufu")
public class AppJiufuController {
	Logger logger = Logger.getLogger(AppJiufuController.class);
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	
	@Autowired
	private IBwOrderChannelService orderChannelService;
	
	private static String PRI_KEY = "private_key";
	private static String PUB_KEY = "public_key";
	private static String OLD_USER_URL = "oldUser.returnUrl";
	private static String NEW_USER_URL = "newUser.returnUrl";
	private static String CALLBACK_URL = "callBack";
	private static String CALLBACKDES = "callBackDES";
	private static String ENCODE = "utf-8";
	
	/**
	 * 玖富联合注册
	 * 
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/register.do")
	public JiufuResp<JiufuRespData> register(HttpServletRequest request, HttpServletResponse response){
		JiufuResp<JiufuRespData> resp = new JiufuResp<JiufuRespData>();
		JiufuRespData respData = new JiufuRespData();
//		JiufuReq req = new JiufuReq();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String sessionId = DateUtils.getDateHMToString();								
		String methodName = "AppJiufuController.register";		//通过时间和方法名获得请求的标识符
		//1.对请求进行非空判断
		String check = JiufuUtils.commonCheck(request);
		if(StringUtils.isNotBlank(check)){
			resp.setCode("101");
			resp.setMessage(check);
			resp.setStatus("0");
			logger.info(sessionId+":"+methodName+"end,返回结果："+JSON.toJSONString(resp));
			return resp;
		}
		//2.获取解密的私钥字符串和公钥字符串并进行非空判断
		String priKey = JiufuConstant.KEY_MAP.get(PRI_KEY);
		String pubKey = JiufuConstant.KEY_MAP.get(PUB_KEY);
		String newUserReturnUrl = JiufuConstant.KEY_MAP.get(NEW_USER_URL);
		String callBack = JiufuConstant.KEY_MAP.get(CALLBACK_URL);
		String oldUserReturnUrl = JiufuConstant.KEY_MAP.get(OLD_USER_URL);
		String secretKey = JiufuConstant.KEY_MAP.get(CALLBACKDES);
		if(StringUtils.isBlank(priKey)){
			resp.setCode("101");
			resp.setMessage("获取私钥字符串为空");
			resp.setStatus("0");
			logger.info(sessionId+":"+methodName+"end,返回结果："+JSON.toJSONString(resp));
			return resp;
		}
		if(StringUtils.isBlank(pubKey)){
			resp.setCode("101");
			resp.setMessage("获取公钥字符串为空");
			resp.setStatus("0");
			logger.info(sessionId+":"+methodName+"end,返回结果："+JSON.toJSONString(resp));
			return resp;
		}
		if(StringUtils.isBlank(newUserReturnUrl)){
			resp.setCode("101");
			resp.setMessage("获取新用户返回地址为空");
			resp.setStatus("0");
			logger.info(sessionId+":"+methodName+"end,返回结果："+JSON.toJSONString(resp));
			return resp;
		}
		if(StringUtils.isBlank(callBack)){
			resp.setCode("101");
			resp.setMessage("获取新用户回调地址为空");
			resp.setStatus("0");
			logger.info(sessionId+":"+methodName+"end,返回结果："+JSON.toJSONString(resp));
			return resp;
		}
		if(StringUtils.isBlank(oldUserReturnUrl)){
			resp.setCode("101");
			resp.setMessage("获取新用户回调地址为空");
			resp.setStatus("0");
			logger.info(sessionId+":"+methodName+"end,返回结果："+JSON.toJSONString(resp));
			return resp;
		}
		//3.对公钥和私钥字符串进行还原
		PrivateKey privateKey = null;
		PublicKey publicKey = null;
		try {
			privateKey = RSAUtil.getPrivateKey(priKey);
			publicKey = RSAUtil.getPublicKey(pubKey);
		} catch (Exception e) {
			resp.setCode("101");
			resp.setMessage("获取公钥私钥失败");
			resp.setStatus("0");
			logger.error(sessionId+":"+methodName+"end,返回结果："+JSON.toJSONString(resp),e);
			e.printStackTrace();
			return resp;
		}
		//4.使用私钥对请求数据进行解密
		String channelID = request.getParameter("channel_id").replaceAll(" ", "+");//叮当分配给渠道的ID
		String dingDangID = request.getParameter("dingdang_id").replaceAll(" ", "+");//渠道分配给订单的ID 对应的是叮当在我们数据库中的channel
		String serialNum = request.getParameter("serial_number").replaceAll(" ", "+");
		String mobile = request.getParameter("mobile").replaceAll(" ", "+");
		String name = request.getParameter("name").replaceAll(" ", "+");
		String idCard = request.getParameter("cert_id").replaceAll(" ", "+");
		try {
//			channelID = RSAUtil.decrypt(privateKey, req.getChannel_id());
//			dingDangID =	RSAUtil.decrypt(privateKey, req.getDingdang_id());
//			serialNum = RSAUtil.decrypt(privateKey, req.getSerial_number());
//			mobile =	RSAUtil.decrypt(privateKey, req.getMobile());
//			name = RSAUtil.decrypt(privateKey, req.getName());
//			idCard =	RSAUtil.decrypt(privateKey, req.getCert_id());
			channelID = RSAUtil.decrypt(privateKey, channelID);
			dingDangID =	RSAUtil.decrypt(privateKey, dingDangID);
			serialNum = RSAUtil.decrypt(privateKey, serialNum);
			mobile =	RSAUtil.decrypt(privateKey, mobile);
			name = RSAUtil.decrypt(privateKey, name);
			try {
				name = new String(name.getBytes(), ENCODE);
			} catch (UnsupportedEncodingException e1) {
				
				e1.printStackTrace();
			}  
			idCard =	RSAUtil.decrypt(privateKey, idCard);
			
		} catch (Exception e) {
			resp.setCode("101");
			resp.setMessage("请求数据解密失败");
			resp.setStatus("0");
			logger.error(sessionId+":"+methodName+"end,返回结果："+JSON.toJSONString(resp), e);
			e.printStackTrace();
			return resp;
		}
		//5.判断是否为叮当的请求？
		logger.info(sessionId+":"+methodName+"开始查询渠道表,dingDangID="+dingDangID);
		BwOrderChannel bwOrderChannel = orderChannelService.getOrderChannelByCode(dingDangID);
		logger.info(sessionId+":"+methodName+"结束查询渠道表,bwOrderChannel="+JSONObject.toJSONString(bwOrderChannel));
		if (CommUtils.isNull(bwOrderChannel)) {
			resp.setCode("101");
			resp.setMessage("渠道无效");
			resp.setStatus("0");
			logger.info(sessionId+":"+methodName+"["+dingDangID+"]"+ " end,返回结果："+JSON.toJSONString(resp));
			return resp;
		}
		//6.到此请求成功
		resp.setCode("200");
		resp.setMessage("成功");
		resp.setStatus("1");
		//7.对用户做新老用户判断（1.通过身份证号在bw_borrower查询，如果没查到，在用手机号码在该表中查询）
		BwBorrower borrower = new BwBorrower();
		borrower.setIdCard(idCard);
		logger.info(sessionId+":"+methodName+"开始查询借款人信息,idCard="+idCard);
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		logger.info(sessionId+":"+methodName+"结束查询接款人信息,borrower="+JSONObject.toJSONString(borrower));
		if(CommUtils.isNull(borrower)){
			//查询结果为空，再通过手机号码查询
			BwBorrower borrower2 = new BwBorrower();
			borrower2.setPhone(mobile);
			logger.info(sessionId+":"+methodName+"开始查询借款人信息,mobile="+mobile);
			borrower2 = bwBorrowerService.findBwBorrowerByAttr(borrower2);
			logger.info(sessionId+":"+methodName+"结束查询接款人信息,borrower2="+JSONObject.toJSONString(borrower2));
			if(CommUtils.isNull(borrower2)){
				//电话号码不存在，确定借款人不存在，开始创建借款人
				logger.info(sessionId+":"+methodName+"借款人信息不存在，开始创建借款人");
				String password = JiufuUtils.getRandNum(1,999999);//生成一个六位数加上a的密码
				borrower = new BwBorrower();
				borrower.setPhone(mobile);
				borrower.setName(name);
				borrower.setIdCard(idCard);
				borrower.setAge(JiufuUtils.getAgeByIdCard(idCard));
				borrower.setSex(JiufuUtils.getSexByIdCard(idCard));
				borrower.setPassword(CommUtils.getMD5(password.getBytes()));
				borrower.setAuthStep(1);
				borrower.setFlag(1);
				borrower.setState(1);
				borrower.setChannel(bwOrderChannel.getId());
				borrower.setCreateTime(Calendar.getInstance().getTime());
				borrower.setUpdateTime(Calendar.getInstance().getTime());
				bwBorrowerService.addBwBorrower(borrower);
				logger.info("生成的借款人id:" + borrower.getId());
				//给用户发送密码短信
				try{
					String message = JiufuUtils.getMsg(password);
					MessageDto messageDto = new MessageDto();
					messageDto.setBusinessScenario("1");
					messageDto.setPhone(mobile);
					messageDto.setMsg(message);
					messageDto.setType("1");
					RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
				}catch(Exception e){
					logger.error(sessionId+":"+methodName+"发送短信异常:",e);
				}
				//往返回参数data中存值(数据进行加密处理)channel_id，serial_number，regist_code，url
				//本需求要求对URL进行SRA加密，但是data数据过大，导致RSA加密报异常，现在将flag,authStep,state状态不在这里装如data，在callback中直接赋值
				//这个地方还是长了，为了最缩短，只能传一个borrower的ID过去，虽然在callback里面做数据库查询操作不好。。但是这个借款人ID一定要加密
				//1.获取加密前的callbackURL
//					Map<String, String> dataMap = new HashMap<String, String>();
//					dataMap.put("b", borrower.getId().toString());						//borrowerID
//					dataMap.put("c", dingDangID);										//channel
//					dataMap.put("p", mobile);											//phone
//					dataMap.put("pw", borrower.getPassword());							//password
//					dataMap.put("flag", String.valueOf(borrower.getFlag()));
//					dataMap.put("authStep", String.valueOf(borrower.getAuthStep()));
//					dataMap.put("state", String.valueOf(borrower.getState()));
//					dataMap.put("cid", String.valueOf(borrower.getChannel()));			//channelID
//					String dataJson = JSONObject.toJSONString(dataMap);
					//将dataJson进行RSA加密
					try {
						//使用DES加密保证我们的信息的安全性
//						dataJson = Base64.encode(GzipUtil.compress(dataJson,ENCODE));
						String borrowerID = borrower.getId().toString(); 
						String enString = DESUtil.encryption(borrowerID, secretKey);
						//对enJson进行压缩处理，不然这个enJson会导致url长度过长使RSA加密时出现明文长度非法异常，压缩还是不行
//						GzipUtil.compress(enJson,ENCODE);
						String url = JiufuUtils.getReturnUrl(new Object[]{enString}, callBack);
						logger.info(sessionId+":"+methodName+"callbackURL:"+url);
						//对URL进行加密，添加到data中
						url = RSAUtil.encrypt(publicKey, url.getBytes());
						respData.setUrl(url);
						//将其他信息也放入data
						
						channelID = RSAUtil.encrypt(publicKey, channelID.getBytes());
						serialNum = RSAUtil.encrypt(publicKey, serialNum.getBytes());
						respData.setChannel_id(channelID);
						respData.setChannel_id(serialNum);
						
						String registCode = "0";//标识是新用户
						registCode = RSAUtil.encrypt(publicKey, registCode.getBytes());
						respData.setRegist_code(registCode);
						resp.setData(respData);
					} catch (Exception e) {
						resp.setCode("101");
						resp.setMessage("CallBack数据加密异常");
						resp.setStatus("0");
						logger.error(sessionId+":"+methodName+"CallBack数据加密异常",e);
						e.printStackTrace();
						return resp;
					}
				stopWatch.stop();
				logger.info(sessionId+":"+methodName+"end,costTime="+stopWatch.getTotalTimeMillis());
				return resp;	
			}else{
				//电话存在，身份证不存在，是老用户，完善用户资料
				borrower2.setIdCard(idCard);
				borrower2.setName(name);
				borrower2.setAge(JiufuUtils.getAgeByIdCard(idCard));
				borrower2.setSex(JiufuUtils.getSexByIdCard(idCard));
				int i = bwBorrowerService.updateBwBorrower(borrower2);
				if(i!=0){
					logger.info(sessionId+":"+methodName+"用户存在更新用户信息成功");
				}
				
				String url = JiufuUtils.getReturnUrl(new Object[]{mobile,dingDangID}, oldUserReturnUrl);
				logger.info(url.length());
				logger.info(sessionId+":"+methodName+"老用户url为："+url);
				String registCode = "1";//标识是老用户
				try {
					url = RSAUtil.encrypt(publicKey, url.getBytes());
					registCode = RSAUtil.encrypt(publicKey, registCode.getBytes());
					//这个地方还是把channel_id和serial_number加密一次
					channelID = RSAUtil.encrypt(publicKey, channelID.getBytes());
					serialNum = RSAUtil.encrypt(publicKey, serialNum.getBytes());
				} catch (Exception e) {
					logger.error(sessionId+":"+methodName+"老用户返回数据加密异常",e);
					e.printStackTrace();
				}
				respData.setChannel_id(channelID);
				respData.setSerial_number(serialNum);
				respData.setRegist_code(registCode);
				respData.setUrl(url);
				resp.setData(respData);
				return resp;
			}
		}else{
			//身份证存在，判断为老用户，做用户的手机号比较，如果手机号码不同，修改用户的手机号？返回相应的数据
			String phone = borrower.getPhone();
			if(StringUtils.isNotBlank(phone)){
				if(!mobile.equals(phone)){
					//手机号码和资料中不一样，更改数据库中的手机号码？
					borrower.setPhone(mobile);
					int i = bwBorrowerService.updateBwBorrower(borrower);
					if(i!=0){
						logger.info(sessionId+":"+methodName+"用户存在更新用户信息成功");
					}
				}
			}
			String url = JiufuUtils.getReturnUrl(new Object[]{mobile,dingDangID}, oldUserReturnUrl);
			logger.info(url.length());
			logger.info(sessionId+":"+methodName+"老用户url为："+url);
			String registCode = "1";//标识是老用户
			try {
				url = RSAUtil.encrypt(publicKey, url.getBytes());
				registCode = RSAUtil.encrypt(publicKey, registCode.getBytes());
				channelID = RSAUtil.encrypt(publicKey, channelID.getBytes());
				serialNum = RSAUtil.encrypt(publicKey, serialNum.getBytes());
			} catch (Exception e) {
				logger.error(sessionId+":"+methodName+"老用户返回数据加密异常",e);
				e.printStackTrace();
			}
			respData.setChannel_id(channelID);
			respData.setSerial_number(serialNum);
			respData.setRegist_code(registCode);
			respData.setUrl(url);
			resp.setData(respData);
			return resp;
		}
	
	} 
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/callBack.do")
	public String callBack(HttpServletRequest request, HttpServletResponse response) {
		String methodName = "AppJiufuController.callBack";
		String sessionId = DateUtils.getDateHMToString();//通过当前时间获得唯一标识符
		logger.info(sessionId+methodName+" start");
		String newUserReturnUrl = null;
		BwBorrower borrower = null;
		try {
			String secretData = request.getParameter("data").replaceAll(" ", "+");
			String secretKey = JiufuConstant.KEY_MAP.get(CALLBACKDES);
			//对secretData进行解密
			String borrowerID = DESUtil.decryption(secretData, secretKey);
			Long borrowerid = Long.valueOf(borrowerID);
			logger.info(sessionId+":"+methodName+"开始查询借款人信息,borrowerID="+borrowerid);
			borrower = bwBorrowerService.findBwBorrowerById(borrowerid);//根据借款人ID查询获得借款人信息
			logger.info(sessionId+":"+methodName+"结束查询接款人信息,borrower="+JSONObject.toJSONString(borrower));
			
			if(!CommUtils.isNull(borrower)){
				String borrowerId =borrower.getId().toString();
				String channelId = borrower.getChannel().toString();//查询获得渠道ID
				String phone = borrower.getPhone();
				String password = borrower.getPassword();
				String flag = borrower.getFlag().toString();
				String authStep = borrower.getAuthStep().toString();;
				String state = borrower.getState().toString();
				//根据渠道ID查询渠道表
				BwOrderChannel bwOrderChannel = new BwOrderChannel();
				bwOrderChannel.setId(borrower.getChannel());
				logger.info(sessionId+":"+methodName+"开始查询渠道信息,channelId="+channelId);
				bwOrderChannel = orderChannelService.findBwOrderChannel(bwOrderChannel);
				logger.info(sessionId+":"+methodName+"结束查询渠道信息,bwOrderChannel="+JSONObject.toJSONString(bwOrderChannel));
				String channel = bwOrderChannel.getChannelCode(); 
				logger.info(sessionId+":"+methodName+"渠道号="+channel);
			//解密打印日志
//			String data = DESUtil.decryption(secretData, secretKey);
//			logger.info(sessionId+":"+methodName+"+data:"+data);
//			
//			Map dataMap = JSONObject.parseObject(data, Map.class);
//			String borrowerId = (String) dataMap.get("borrowerId");
//			String channel = (String) dataMap.get("channel");
//			
//			String channelId = (String) dataMap.get("channelId");
//			String phone = (String) dataMap.get("phone");
//			String password = (String) dataMap.get("password");
//			String flag = (String) dataMap.get("flag");
//			String authStep = (String) dataMap.get("authStep");
//			String state = (String) dataMap.get("state");
			
			newUserReturnUrl = JiufuConstant.KEY_MAP.get(NEW_USER_URL);
			newUserReturnUrl = JiufuUtils.getReturnUrl(new Object[]{channel}, newUserReturnUrl);
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
			}
		} catch (Exception e) {
			logger.error(sessionId+methodName+" occured exception:", e);
		}
		
		return "redirect:" + newUserReturnUrl;
	}
}
