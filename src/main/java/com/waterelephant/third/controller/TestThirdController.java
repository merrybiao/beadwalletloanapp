package com.waterelephant.third.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.third.entity.request.BasicInfo;
import com.waterelephant.third.entity.request.CallRecord;
import com.waterelephant.third.entity.request.CompanyInfo;
import com.waterelephant.third.entity.request.Contact;
import com.waterelephant.third.entity.request.IdentifyInfo;
import com.waterelephant.third.entity.request.Operator;
import com.waterelephant.third.entity.request.RequestApproveConfirm;
import com.waterelephant.third.entity.request.RequestBankCardInfo;
import com.waterelephant.third.entity.request.RequestCheckUser;
import com.waterelephant.third.entity.request.RequestPullOrderStatus;
import com.waterelephant.third.entity.request.RequestPush;
import com.waterelephant.third.entity.request.RequestSign;
import com.waterelephant.third.entity.request.ResquestContract;
import com.waterelephant.third.utils.HttpClientHelper;
import com.waterelephant.utils.AESUtil;
import com.waterelephant.utils.MyDateUtils;

/**
 * 统一对外接口 - 测试
 * 
 * 
 * Module:
 * 
 * TestThirdController.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */

public class TestThirdController {

	public static void main(String[] args) {
		// 测试用户接口
		// testCheckUser();
		// 测试订单推送接口
		testPushOrder();
		// 测试银行卡签约
		// testSign();

		// Map<String, String> map = new HashMap<>();
		// map.put("partnerCode", "1");
		// map.put("operateType", "2");
		// map.put("timeStamp", "3");
		// map.put("data", "4");
		// Map<String, String> treeMap = new TreeMap<>(map);
		// StringBuilder stringBuilder = new StringBuilder();
		// for (Map.Entry<String, String> entry : treeMap.entrySet()) {
		// stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		// }
		// stringBuilder.append("urikkjjoaojvm84zcvzbpqoiad");
		// System.out.println(stringBuilder.toString());
		// String sign = MD5Util.getMd5Value(stringBuilder.toString()).toUpperCase();
		// System.out.println(sign);

		// String string = UriEncoder.decode(
		// "result=%7B%22agreeno%22%3A%222017090451929383%22%2C%22oid_partner%22%3A%22201608101001022519%22%2C%22sign%22%3A%22FNamNpB7PAGvPEJ%2BVeHSqUvXPYqXz0ZEtKvH9NOBI0nDCXfWSNCH8jcwLM4OX2Z34SXdBaNrM9sn6T4f5I%2B5%2Bx%2F7aHUJIoNp1RLIJkVVq%2BI8lx1ozNJFv7sCsjD%2BApbWSW7qcSOWzfdRtt0KrX2PeoZUYPbK6vUh4eCO0Usu3LA%3D%22%2C%22sign_type%22%3A%22RSA%22%2C%22user_id%22%3A%221001966%22%7D");
		// System.out.println(string);

		// SystemAuditDto systemAuditDto = new SystemAuditDto();
		// systemAuditDto.setIncludeAddressBook(0);
		// systemAuditDto.setOrderId(40305364L);
		// systemAuditDto.setBorrowerId(1001922L);
		// systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
		// systemAuditDto.setName("张冲");
		// systemAuditDto.setPhone("15972182935");
		// systemAuditDto.setIdCard("421022199307063953");
		// systemAuditDto.setChannel(349);
		// systemAuditDto.setThirdOrderId("20170901");
		// String string = JsonUtils.toJson(systemAuditDto);
		// System.out.println(string);

		// 测试 修改银行卡信息
		// testUpdateCard();
		// 测试 获取合同地址
		// testContractUrl();
		// 测试 获取订单状态
		// testOrderStatus();
		// 测试 签约
		// testSignContract();

		// test();

		// testCloud();
	}

	/**
	 * 统一对外接口 - 检查用户（测试）
	 */
	private static void testCheckUser() {
		try { /// third/interface/checkUser.do
			String url = "http://106.14.238.126:8092/beadwalletloanapp/third/interface/checkUser.do";// 路径
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appId", "ZDCWKL07USIO9OE");

			/*
			 * 此处使用AES-128-ECB加密模式，key需要为16位。
			 */
			String cKey = "IEvQIBADANBgkqhk";
			// 需要加密的字串
			RequestCheckUser requestCheckUser = new RequestCheckUser();
			requestCheckUser.setName("催催冲");
			requestCheckUser.setIdCard("4210221993****3954");
			//requestCheckUser.setMobile("159****2935");
			String cSrc = JSON.toJSONString(requestCheckUser);

			System.out.println(cSrc);
			// 加密
			String enString = AESUtil.Encrypt(cSrc, cKey);
			System.out.println("加密后的字串是：" + enString);
			// 解密
			String DeString = AESUtil.Decrypt(enString, cKey);
			System.out.println(DeString);

			paramMap.put("request", enString);

			String json = HttpClientHelper.post(url, "utf-8", paramMap);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 统一对外接口 - 检查订单（测试）
	 */
	private static void testPushOrder() {
		try {
			String url = "http://localhost:8080/beadwalletloanapp/third/interface/pushOrder.do";
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("appId", "ZDCWKL07USIO9OE");

			RequestPush requestPush = new RequestPush();

			BasicInfo basicInfo = new BasicInfo();
			basicInfo.setThirdOrderNo("2017110311400");

			basicInfo.setName("冲");
			basicInfo.setIdCard("421022199307063954");
			basicInfo.setRegisterPhone("15972182936");

			basicInfo.setApplyDate("2017-09-01 00:00:00");
			basicInfo.setLoanAmount(100000);
			basicInfo.setIsInstalment(1);
			basicInfo.setPeriod(30);
			basicInfo.setDesc("建设国家原因");

			basicInfo.setBankCardNum("6228480052234401413");
			basicInfo.setBankName("中国农业银行");
			basicInfo.setBankPhone("15972182935");
			basicInfo.setBankProvince("420");
			basicInfo.setBankCity("100");

			basicInfo.setSesameScore(714);

			basicInfo.setHouseProvince("湖北省");
			basicInfo.setHouseAddress("金融港");
			basicInfo.setHouseCity("武汉市");
			basicInfo.setHouseDistrict("江夏区");
			basicInfo.setHaveCar(0);
			basicInfo.setHaveHouse(0);
			basicInfo.setEmail("3204230651@qq.com");
			basicInfo.setMarriage(0);
			basicInfo.setFirstName("爸爸");
			basicInfo.setFirstPhone("15900000000");
			basicInfo.setSecondName("妈妈");
			basicInfo.setSecondPhone("15800000000");
			requestPush.setBasicInfo(basicInfo);

			List<CallRecord> callRecords = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				CallRecord callRecord = new CallRecord();
				callRecord.setCallTime("2017-05-2" + i + " 20:16:32");
				callRecord.setCallType(1);
				callRecord.setReceivePhone("1590000222" + i);
				callRecord.setTradeAddr("武汉");
				callRecord.setTradeTime("1" + i);
				callRecord.setTradeType(1);
				callRecords.add(callRecord);
			}
			requestPush.setCallRecords(callRecords);

			CompanyInfo companyInfo = new CompanyInfo();
			companyInfo.setCompanyName("阿里巴巴");
			companyInfo.setJobTime("10年");
			companyInfo.setIncome("1");
			companyInfo.setIndustry("电商");
			requestPush.setCompanyInfo(companyInfo);

			List<Contact> contacts = new ArrayList<>();
			for (int i = 1000; i < 1005; i++) {
				Contact contact = new Contact();
				contact.setName("马" + i);
				contact.setPhone("1593333" + i);
				contacts.add(contact);
			}
			requestPush.setContacts(contacts);

			IdentifyInfo identifyInfo = new IdentifyInfo();
			identifyInfo.setAddress("湖北省武汉市江夏区88号");
			identifyInfo.setIdCard("421022199307063953");
			identifyInfo.setIssuedBy("湖北省武汉市民政局");
			identifyInfo.setName("冲");
			identifyInfo.setNation("汉");
			identifyInfo.setValidDate("1999.10.10-2019.10.10");
			identifyInfo
					.setFrontFile("http://www.sznews.com/ent/images/attachement/jpg/site3/20141011/4437e629783815a2bce256.jpg");
			identifyInfo.setBackFile("http://img.chinawutong.com/huiyuan/uppic/s_636082493850545988.png");
			identifyInfo.setNatureFile("http://scimg.jb51.net/allimg/160506/77-1605060939520-L.jpg");
			requestPush.setIdentifyInfo(identifyInfo);

			Operator operator = new Operator();
			operator.setAddr("湖北省武汉市");
			operator.setIdCard("421022199307063953");
			operator.setPhone("15972182935");
			operator.setRealName("冲");
			operator.setRegTime("2016-1-1");
			operator.setSource("移动");
			operator.setBalance("10");
			requestPush.setOperator(operator);

			/*
			 * 此处使用AES-128-ECB加密模式，key需要为16位。
			 */
			String cKey = "IEvQIBADANBgkqhk";
			// 需要加密的字串
			String cSrc = JSON.toJSONString(requestPush);

			System.out.println(cSrc);
			// 加密
			String enString = AESUtil.Encrypt(cSrc, cKey);
			System.out.println("加密后的字串是：" + enString);
			// 解密
			String DeString = AESUtil.Decrypt(enString, cKey);
			System.out.println(DeString);

			paramMap.put("request", enString);

			String json = HttpClientHelper.post(url, "utf-8", paramMap);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 统一对外接口 - 绑卡（测试）
	 */
	private static void testSign() {
		try {
			String url = "http://localhost:8080/beadwalletloanapp/third/interface/bindCard.do";
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("appId", "ZDCWKL07USIO9OE");

			RequestSign requestSign = new RequestSign();
			requestSign.setAccountName("张冲");
			requestSign.setBankCardNo("6217002870001944972");
			requestSign.setIdCard("421022199307063953");
			requestSign.setThirdOrderNo("201711151902528710");
			requestSign.setUserId("1234568296");
			requestSign.setUrl("http://p2pactive.free.ngrok.cc/dorong-openapi/callback/reback/page");

			String cKey = "IEvQIBADANBgkqhk";
			// 需要加密的字串
			String cSrc = JSON.toJSONString(requestSign);
			System.out.println(cSrc);
			// 加密
			String enString = AESUtil.Encrypt(cSrc, cKey);
			System.out.println("加密后的字串是：" + enString);
			// 解密
			String DeString = AESUtil.Decrypt(enString, cKey);
			System.out.println(DeString);

			paramMap.put("request", enString);

			HttpClientHelper.post(url, "utf-8", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试 修改银行卡信息
	 */
	private static void testUpdateCard() {
		try {
			String url = "http://localhost:8080/beadwalletloanapp/third/interface/updateBankCard.do";
			Map<String, String> params = new HashMap<>();
			params.put("appId", "WGQEBCJ04751F8C");

			RequestBankCardInfo requestBankCardInfo = new RequestBankCardInfo();
			requestBankCardInfo.setThirdOrderNo("20170909");
			requestBankCardInfo.setBankCardNum("6215581818002985950");
			requestBankCardInfo.setBankName("ICBC");
			requestBankCardInfo.setBankPhone("15727158711");

			String cKey = "7fv5WOT6HstUZUfX";
			// 需要加密的字串
			String cSrc = JSON.toJSONString(requestBankCardInfo);
			System.out.println(cSrc);
			// 加密
			String enString = AESUtil.Encrypt(cSrc, cKey);
			System.out.println("加密后的字串是：" + enString);
			// 解密
			String DeString = AESUtil.Decrypt(enString, cKey);
			System.out.println(DeString);

			params.put("request", enString);

			HttpClientHelper.post(url, "utf-8", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试 获取合同地址
	 */
	private static void testContractUrl() {
		try {
			String url = "http://localhost:8080/beadwalletloanapp/third/interface/getContract.do";
			Map<String, String> params = new HashMap<>();
			params.put("appId", "29G7236CGAF1C3C");

			ResquestContract resquestContract = new ResquestContract();
			resquestContract.setThirdOrderNo("1111111111111");

			String cKey = "7fv5WOT6HstUZUfX";
			// 需要加密的字串
			String cSrc = JSON.toJSONString(resquestContract);
			System.out.println(cSrc);
			// 加密
			String enString = AESUtil.Encrypt(cSrc, cKey);
			System.out.println("加密后的字串是：" + enString);
			// 解密
			String DeString = AESUtil.Decrypt(enString, cKey);
			System.out.println(DeString);

			params.put("request", enString);

			HttpClientHelper.post(url, "utf-8", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试 获取订单状态
	 */
	private static void testOrderStatus() {
		try {
			String url = "http://localhost:8080/beadwalletloanapp/third/interface/pullOrderStatus.do";
			Map<String, String> params = new HashMap<>();
			params.put("appId", "ZDCWKL07USIO9OE");

			RequestPullOrderStatus requestPullOrderStatus = new RequestPullOrderStatus();
			requestPullOrderStatus.setThirdOrderNo("201711171623292935");

			String cKey = "IEvQIBADANBgkqhk";
			// 需要加密的字串
			String cSrc = JSON.toJSONString(requestPullOrderStatus);
			System.out.println(cSrc);
			// 加密
			String enString = AESUtil.Encrypt(cSrc, cKey);
			System.out.println("加密后的字串是：" + enString);
			// 解密
			String DeString = AESUtil.Decrypt(enString, cKey);
			System.out.println(DeString);

			params.put("request", enString);

			HttpClientHelper.post(url, "utf-8", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试 签约
	 */
	private static void testSignContract() {
		try {
			String url = "http://106.14.238.126:8092/beadwalletloanapp/third/interface/signContract.do";
			Map<String, String> params = new HashMap<>();
			params.put("appId", "ZDCWKL07USIO9OE");

			RequestApproveConfirm requestApproveConfirm = new RequestApproveConfirm();
			requestApproveConfirm.setThirdOrderNo("201711062005195852");

			String cKey = "IEvQIBADANBgkqhk";
			// 需要加密的字串
			String cSrc = JSON.toJSONString(requestApproveConfirm);
			System.out.println(cSrc);
			// 加密
			String enString = AESUtil.Encrypt(cSrc, cKey);
			System.out.println("加密后的字串是：" + enString);
			// 解密
			String DeString = AESUtil.Decrypt(enString, cKey);
			System.out.println(DeString);

			params.put("request", enString);

			HttpClientHelper.post(url, "utf-8", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void test() {
		try {
			int num = 0;
			String string = HttpClientHelper.post("http://www.abcxs.com/book/15507/" + num + ".html", "gbk", "");
			string = string.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "").replaceAll("<br />", "");
			System.out.println(string);
			Integer aa = new Integer(4);
			Integer bb = new Integer(4);
			System.out.println(aa == bb);
			System.out.println(Integer.valueOf("89") == Integer.valueOf("89"));
			System.out.println(Integer.valueOf("555") == Integer.valueOf("555"));
			System.out.println(Integer.valueOf("543") == Integer.parseInt("543"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testCloud() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date addDays = MyDateUtils.addDays(new Date(), -5);
			StringBuilder sb = new StringBuilder();
			String startTime = sb.append(sdf.format(addDays)).append(" 00:00:00").toString();
			StringBuilder sb2 = new StringBuilder();
			String endTime = sb2.append(sdf.format(addDays)).append(" 23:59:59").toString();

			int pageSize = 5;
			int pageNum = 1;
			String url = "http://localhost:8080/beadwalletloanapp/saas/getData.do";
			Map<String, String> params = new HashMap<>();
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			params.put("pageSize", String.valueOf(pageSize));
			params.put("pageNum", String.valueOf(pageNum));
			String data = HttpClientHelper.post(url, "utf-8", params);
			List<BwBorrower> bwBorrowers = JSON.parseArray(data, BwBorrower.class);
			System.out.println(bwBorrowers);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
