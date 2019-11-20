package com.waterelephant.drainage.test;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.utils.HttpClientHelper;
import com.waterelephant.drainage.util.youyu.*;
import com.waterelephant.utils.RedisUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TestYouyuController {

	public static void main(String[] args) {
		// testRsa1();
		//testRsa2();
		testRsa6();
		// testRsa7();
		 //testRsa8();
		// testRsa11();
		 //testRsa12();
		// testRsa13();
		// testRsa9();
		 //testRsa14();
		// testRsa15();
		// testRsa16();
		//testRsa17();
		// System.out.println(new Date().getTime());
	}
	//测试卡后四位不显示问题
	@Test
	public static void testRsa17() {
		TreeMap<String , Object> treeMap = new TreeMap<>();
		String sign ="abc";
		Map<String , String > map = new HashMap<>();
		map.put("name", "李三");
		map.put("age", "26");
		map.put("sec", "男");
		treeMap.put("sign", sign);
		treeMap.put("data", map);
		String jsonString = JSON.toJSONString(treeMap);
		String url = "http://localhost:8080/beadwalletloanapp/youyu/interface/test.do";
		 String post=HttpClientHelper.post(url, "UTF-8", jsonString);
		System.out.println(post);
	}
	@Test
	public static void testRsa16() {
		String sign ="kSueAUGQ7WhWyhXdRlAv8meG4I98oWwDFKup2MY1hoSFWfMGt+fLpHsWXMPqczErNWdq9CkrIr48zt88lVpqaUI8QurxQyNdpZD0t+vx6k0FPItSSYEtsMm9IhJEek2TxdWT5HnK7r1aZ6T5z/hg0uF//CwLm3MrWWqdA5EvjuY=";
		
		String biz_data = "IVNyGF6GAQmnSjLM7PMlzsuHRaTrKdgh";
		String des_key = "pwVDVIuYDzanuBhFtkLu+LT5LnXlYIqLQWods4Cb3cEKBO+OvevqGRzKqqZ4qOAgyaaSExlwgc4jMUKxM6gU6TlYVrOhgviFWVUJsBQlSXFew7kTHoXoeDcGmpXy5EEjOu90snEQcmAKdMuOrEkP15NdvU1SPxTLxphojmCI5KE=";
		TreeMap<String, String> treeMap = new TreeMap<>();
		treeMap.put("biz_data", biz_data);
		treeMap.put("des_key", des_key);
		treeMap.put("sign", sign);
		treeMap.put("code", "1");
		treeMap.put("channel", "RAQHEFD57251H7C");
		treeMap.put("biz_enc", "1");
		String url = "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/finish.do";
		 String post=HttpClientHelper.post(url, "UTF-8", treeMap);
		
		System.out.println(post);
		
	}
	
	@Test
	public static void testRsa15() {
		String directUrl = "/youyu/interface/montageURL.do?channelId=" + 326 + "&orderNO="
				+"B20171130034226740269" + "&torder_no" + 2447;
		RedisUtils.hset("third:bindCard:selfUrl:" + 326, "orderNO_" + "B20171130034226740269", directUrl);
		System.out.println(66);
		
	}
	@Test
	public static void testRsa14() {
		String biz_data = "PMrUtMZFKZ4Js2CN4+hWW2oGU2oPzX5E";
		String des_key = "UZkVVLXLfkTnHVHCVbC6S2H3ZclKAj/sBAKm2WKt4GwOZr+HWgD3CF+g3TswIAuha5e6+brhW1TLku07BotHQ6MEXYmj3/sUCfc+DAMpU3//vjJUOIuZTTWvzJsZ2t1XWxZE6Cs8TKIvfs5HOXjLVQ4+s9A7Rhg1W6YnceiM0Ig=";
		String sign = "SpFIPpD0jdZNPatsrppknfr3C3QRN334DfwvwwnXPw0ARgoVk1HKilkgdDNVFMMqoAaXDZbtLktDuS5VGjF0vmvU+trwsYvzRelqdKywgaE29b28Chen/J3XxRX2taHnTRMksipVTyYzSWR8q8ZN7m60RFbVCOGU9W081OrSrcw=";
		TreeMap<String, String> treeMap = new TreeMap<>();
		treeMap.put("biz_data", biz_data);
		treeMap.put("des_key", des_key);
		treeMap.put("sign", sign);
		treeMap.put("code", "1");
		treeMap.put("channel", "RAQHEFD57251H7C");
		treeMap.put("biz_enc", "1");
		// "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/checkUser.do";
		// String url
		// ="http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/PushOrder.do";
		String url = "http://localhost:8080/beadwalletloanapp/youyu/interface/repayment.do";
		// String url =
		// "http://localhost:8080/beadwalletloanapp/youyu/bindCard.do";
		String post = HttpClientHelper.post(url, "UTF-8", treeMap);
		System.out.println(post);
	}

	/**
	 * 签约
	 * @Title: testRsa13
	 * @Description: TODO    
	 * @return void    
	 * @throws
	 */
	@Test
	public static void testRsa13() {
		String youyusiyao="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMB2hXbMFrpqMqsujChLtdCDwZssHpcmYDrjgfEAUvtKaKs8O+ux4wrKPhVlowPmDsTCS0GhQi5XiW6AYyLODAmWe9pbRbhe6KMpDvF8D5p3wMiVnalFRK+TxzoYd09Jhy9lY/02ZpjztFJ0G68giHPGOLvj4xQbJ9ThzBZTHCN7AgMBAAECgYAhkQo56+JS5M6teFLNfFbbZP9RNuKm7fR+kMtK4wmV9iZHJxw0QTQd36PwS2eg+HC+9Dv32E4Ykv/PG+kuWs1SWVIw3kzPyaJajT7R32vUcHQmeCZwvNwbULmGIco3pR5Z9Gksde13UwQRhVCzkvH0LQl1nVdNeJoz5djL+pPIgQJBAOHzVk+xWATNoJlLTUXO8N2VnLFdnfe+ZEz/ZOTJ5xwHuOLCsKB/xm19SJ24a3h5Fm2vdjDd0Hgovi4oRF4KeuECQQDaDxLtqgKWWc6+Udawl772egihtcnrLpUrKx9xCbIxZSUDHrQ3EtPEiaCVnC2UrLPG33yk48jbfU7fbuv/MqXbAkEAnnyBsRpy48Ob/4p7JBkYiESWCS7iS9EnJ38ItRYN3nJoM95d5+ZYN5pmIgMmlvVQTxWA8JvVy0LAyz2BXvk44QJBAMqpCdGCmUb9Do2JZ/vV/G/8uPr6BkCimZZ2TJF1Dnyj4UNGDP3GbLSTqICDl0U/QRJK8QAah7mee2hjIcibXNMCQFdQohuQZk3CzsskDwPBTGULyBW+oq7KUHHnx0s296NfWFLH9zW1f8UIrjJFNo2toBeztxeB211M+DyLRX+Qxb4=";
		String order_no = "2327";// 订单号
		Map<String, String> map = new HashMap<>();
		map.put("order_no", order_no);
		String jsonString = JSON.toJSONString(map);
		
		 RsaService rsaService = new RsaServiceImpl(youyusiyao,YouyuConstant.SHUIXIANG_PUBLICKEY,RsaService.PKCS8);
	        DesService desService = new DesServiceImpl();
	        String key = desService.getRandomDesKey(16);
	        String biz_data  = desService.desEncrypt(jsonString,key);
	        String des_key = rsaService.encrypt(key);
	        String sign = rsaService.generateSign(biz_data);
	        
			TreeMap<String, String> treeMap = new TreeMap<>();
			treeMap.put("biz_data", biz_data);
			treeMap.put("des_key", des_key);
			treeMap.put("sign", sign);
			treeMap.put("code", "1");
			treeMap.put("channel", "1572");
			treeMap.put("biz_enc", "1");
			
			
			String url = "http://localhost:8080/beadwalletloanapp/youyu/interface/updateSignContract.do";
			String post = HttpClientHelper.post(url, "UTF-8", treeMap);
			System.out.println(post);
		
		
	}
	/**
	 * 试算
	 * @Title: testRsa12
	 * @Description: TODO    
	 * @return void    
	 * @throws
	 */
	@Test
	public static void testRsa12() {
		String youyusiyao="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMB2hXbMFrpqMqsujChLtdCDwZssHpcmYDrjgfEAUvtKaKs8O+ux4wrKPhVlowPmDsTCS0GhQi5XiW6AYyLODAmWe9pbRbhe6KMpDvF8D5p3wMiVnalFRK+TxzoYd09Jhy9lY/02ZpjztFJ0G68giHPGOLvj4xQbJ9ThzBZTHCN7AgMBAAECgYAhkQo56+JS5M6teFLNfFbbZP9RNuKm7fR+kMtK4wmV9iZHJxw0QTQd36PwS2eg+HC+9Dv32E4Ykv/PG+kuWs1SWVIw3kzPyaJajT7R32vUcHQmeCZwvNwbULmGIco3pR5Z9Gksde13UwQRhVCzkvH0LQl1nVdNeJoz5djL+pPIgQJBAOHzVk+xWATNoJlLTUXO8N2VnLFdnfe+ZEz/ZOTJ5xwHuOLCsKB/xm19SJ24a3h5Fm2vdjDd0Hgovi4oRF4KeuECQQDaDxLtqgKWWc6+Udawl772egihtcnrLpUrKx9xCbIxZSUDHrQ3EtPEiaCVnC2UrLPG33yk48jbfU7fbuv/MqXbAkEAnnyBsRpy48Ob/4p7JBkYiESWCS7iS9EnJ38ItRYN3nJoM95d5+ZYN5pmIgMmlvVQTxWA8JvVy0LAyz2BXvk44QJBAMqpCdGCmUb9Do2JZ/vV/G/8uPr6BkCimZZ2TJF1Dnyj4UNGDP3GbLSTqICDl0U/QRJK8QAah7mee2hjIcibXNMCQFdQohuQZk3CzsskDwPBTGULyBW+oq7KUHHnx0s296NfWFLH9zW1f8UIrjJFNo2toBeztxeB211M+DyLRX+Qxb4=";
		Map<String, String> map = new HashMap<>();
		map.put("loan_money", "1000.00");
		map.put("money_unit", "1");
		map.put("loan_term", "14");
		map.put("term_unit", "1");
		String jsonString = JSON.toJSONString(map);
		 RsaService rsaService = new RsaServiceImpl(youyusiyao,YouyuConstant.SHUIXIANG_PUBLICKEY,RsaService.PKCS8);
	        DesService desService = new DesServiceImpl();
	        String key = desService.getRandomDesKey(16);
	        String biz_data  = desService.desEncrypt(jsonString,key);
	        String des_key = rsaService.encrypt(key);
	        String sign = rsaService.generateSign(biz_data);
	    	TreeMap<String, String> treeMap = new TreeMap<>();
			treeMap.put("biz_data", biz_data);
			treeMap.put("des_key", des_key);
			treeMap.put("sign", sign);
			treeMap.put("code", "1");
			treeMap.put("channel", "1572");
			treeMap.put("biz_enc", "1");
			String url = "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/count.do";
			String post = HttpClientHelper.post(url, "UTF-8", treeMap);
			System.out.println(post);
			
		
	}
	
	/**
	 * 合同获取
	 * @Title: testRsa11
	 * @Description: TODO    
	 * @return void    
	 * @throws
	 */
	@Test
	public static void testRsa11() {
		
		String youyusiyao="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMB2hXbMFrpqMqsujChLtdCDwZssHpcmYDrjgfEAUvtKaKs8O+ux4wrKPhVlowPmDsTCS0GhQi5XiW6AYyLODAmWe9pbRbhe6KMpDvF8D5p3wMiVnalFRK+TxzoYd09Jhy9lY/02ZpjztFJ0G68giHPGOLvj4xQbJ9ThzBZTHCN7AgMBAAECgYAhkQo56+JS5M6teFLNfFbbZP9RNuKm7fR+kMtK4wmV9iZHJxw0QTQd36PwS2eg+HC+9Dv32E4Ykv/PG+kuWs1SWVIw3kzPyaJajT7R32vUcHQmeCZwvNwbULmGIco3pR5Z9Gksde13UwQRhVCzkvH0LQl1nVdNeJoz5djL+pPIgQJBAOHzVk+xWATNoJlLTUXO8N2VnLFdnfe+ZEz/ZOTJ5xwHuOLCsKB/xm19SJ24a3h5Fm2vdjDd0Hgovi4oRF4KeuECQQDaDxLtqgKWWc6+Udawl772egihtcnrLpUrKx9xCbIxZSUDHrQ3EtPEiaCVnC2UrLPG33yk48jbfU7fbuv/MqXbAkEAnnyBsRpy48Ob/4p7JBkYiESWCS7iS9EnJ38ItRYN3nJoM95d5+ZYN5pmIgMmlvVQTxWA8JvVy0LAyz2BXvk44QJBAMqpCdGCmUb9Do2JZ/vV/G/8uPr6BkCimZZ2TJF1Dnyj4UNGDP3GbLSTqICDl0U/QRJK8QAah7mee2hjIcibXNMCQFdQohuQZk3CzsskDwPBTGULyBW+oq7KUHHnx0s296NfWFLH9zW1f8UIrjJFNo2toBeztxeB211M+DyLRX+Qxb4=";
		String order_no = "2327";// 订单号
		Map<String, String> map = new HashMap<>();
		map.put("order_no", order_no);
		String jsonString = JSON.toJSONString(map);
		
		 RsaService rsaService = new RsaServiceImpl(youyusiyao,YouyuConstant.SHUIXIANG_PUBLICKEY,RsaService.PKCS8);
	        DesService desService = new DesServiceImpl();
	        String key = desService.getRandomDesKey(16);
	        String biz_data  = desService.desEncrypt(jsonString,key);
	        String des_key = rsaService.encrypt(key);
	        String sign = rsaService.generateSign(biz_data);
//	        System.out.println("des_key===>"+rsa_encrypt_deskey);
//	        System.out.println("sign===>"+sign);
//	        System.out.println("data===>"+des_encrypt_data);
			TreeMap<String, String> treeMap = new TreeMap<>();
			treeMap.put("biz_data", biz_data);
			treeMap.put("des_key", des_key);
			treeMap.put("sign", sign);
			treeMap.put("code", "1");
			treeMap.put("channel", "1572");
			treeMap.put("biz_enc", "1");
			
			
			String url = "http://localhost:8080/beadwalletloanapp/youyu/interface/agreement.do";
			String post = HttpClientHelper.post(url, "UTF-8", treeMap);
			System.out.println(post);
	        
	        
	        
	}
	
	
	
	
	

	@Test
	public static void testRsa10() {
		TreeMap<String, TreeMap<String, String>> treeMap = new TreeMap<>();
		// 封装订单信息orderinfo
		TreeMap<String, String> orderinfo = new TreeMap<>();
		String order_no = "20017060100001";// 订单号
		String user_name = "何龙云";// 用户名
		String user_mobile = "17671292070";// 用户手机号
		String application_amount = "10000.00";// 贷款金额
		String application_term = "12";// 贷款期限
		String order_time = "1511833632089";// 下单时间
		String status = "60";// 订单状态
		String city = "武汉";
		String company = "水象分期";
		String product = "水象分期";
		String user_id = "4208811991032624836"; // 用户ID
		String term_unit = "1"; // 期限单位 1-天，2-月
		orderinfo.put("order_no", order_no);
		orderinfo.put("user_name", user_name);
		orderinfo.put("user_mobile", user_mobile);
		orderinfo.put("application_amount", application_amount);
		orderinfo.put("application_term", application_term);
		orderinfo.put("order_time", order_time);
		orderinfo.put("status", status);
		orderinfo.put("city", city);
		orderinfo.put("company", company);
		orderinfo.put("product", product);
		orderinfo.put("user_id", user_id);
		orderinfo.put("term_unit", term_unit);
		treeMap.put("orderinfo", orderinfo);

		// 封装产品信息applydetail
		TreeMap<String, String> applydetail = new TreeMap<>();
		String applicationamount = "1000";// application_amount
		String asset_auto_type = "1";
		String is_op_type = "3";
		String loan_term = "30";
		String termunit = "1";// term_unit
		String user_education = "1";
		String userid = "420881199103264836";// user_id
		String user_income_by_card = "9999.00";
		String usermobile = "17671292070";// user_mobile
		String username = "何龙云";// user_name
		String user_social_security = "1";
		String work_period = "3";
		applydetail.put("applicationamount", applicationamount);
		applydetail.put("asset_auto_type", asset_auto_type);
		applydetail.put("is_op_type", is_op_type);
		applydetail.put("loan_term", loan_term);
		applydetail.put("term_unit", termunit);
		applydetail.put("user_education", user_education);
		applydetail.put("user_id", userid);
		applydetail.put("user_income_by_card", user_income_by_card);
		applydetail.put("user_mobile", usermobile);
		applydetail.put("user_name", username);
		applydetail.put("user_social_security", user_social_security);
		applydetail.put("work_period", work_period);
		treeMap.put("applydetail", applydetail);

		// 身份证信息审核idinfo
		TreeMap<String, String> idinfo = new TreeMap<>();
		idinfo.put("authority", "无为县公安局");
		idinfo.put("id_hand_imgs", "[]");
		idinfo.put("id_live_head_img",
				"http://t.huishuaka.com/imgs/youyuloan/userid/2017/201711/20171110/17691298879325480213.jpg");
		idinfo.put("id_live_img",
				"http://t.huishuaka.com/imgs/youyuloan/userid/2017/201711/20171110/17613309466098391690.jpg");
		idinfo.put("id_live_score", "85.66");
		idinfo.put("id_negative_img",
				"http://t.huishuaka.com/imgs/youyuloan/userid/2017/201711/20171110/12881401117082966115.jpg");
		idinfo.put("id_positive_img",
				"http://t.huishuaka.com/imgs/youyuloan/userid/2017/201711/20171110/4117100611327736323.jpg");
		idinfo.put("user_id", "420881199103264836");
		idinfo.put("user_name", "何龙云");
		idinfo.put("valid_period", "2013.07.30-2023.07.30");
		treeMap.put("idinfo", idinfo);
		
		
		
		
		
		

	}

	/**
	 * 1.14
	 * @Title: testRsa2 @Description: 有鱼-绑卡测试 @return void @throws
	 */
	@Test
	public static void testRsa2() {

		String yoiyusiyao = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMB2hXbMFrpqMqsujChLtdCDwZssHpcmYDrjgfEAUvtKaKs8O+ux4wrKPhVlowPmDsTCS0GhQi5XiW6AYyLODAmWe9pbRbhe6KMpDvF8D5p3wMiVnalFRK+TxzoYd09Jhy9lY/02ZpjztFJ0G68giHPGOLvj4xQbJ9ThzBZTHCN7AgMBAAECgYAhkQo56+JS5M6teFLNfFbbZP9RNuKm7fR+kMtK4wmV9iZHJxw0QTQd36PwS2eg+HC+9Dv32E4Ykv/PG+kuWs1SWVIw3kzPyaJajT7R32vUcHQmeCZwvNwbULmGIco3pR5Z9Gksde13UwQRhVCzkvH0LQl1nVdNeJoz5djL+pPIgQJBAOHzVk+xWATNoJlLTUXO8N2VnLFdnfe+ZEz/ZOTJ5xwHuOLCsKB/xm19SJ24a3h5Fm2vdjDd0Hgovi4oRF4KeuECQQDaDxLtqgKWWc6+Udawl772egihtcnrLpUrKx9xCbIxZSUDHrQ3EtPEiaCVnC2UrLPG33yk48jbfU7fbuv/MqXbAkEAnnyBsRpy48Ob/4p7JBkYiESWCS7iS9EnJ38ItRYN3nJoM95d5+ZYN5pmIgMmlvVQTxWA8JvVy0LAyz2BXvk44QJBAMqpCdGCmUb9Do2JZ/vV/G/8uPr6BkCimZZ2TJF1Dnyj4UNGDP3GbLSTqICDl0U/QRJK8QAah7mee2hjIcibXNMCQFdQohuQZk3CzsskDwPBTGULyBW+oq7KUHHnx0s296NfWFLH9zW1f8UIrjJFNo2toBeztxeB211M+DyLRX+Qxb4=";

		RsaService rsaService = new RsaServiceImpl(yoiyusiyao, YouyuConstant.SHUIXIANG_PUBLICKEY, RsaService.PKCS8);
		DesService desService = new DesServiceImpl();
		String key = desService.getRandomDesKey(16);
		TreeMap<String, String> treeMap = new TreeMap<>();
		String order_no = "2327"; // 订单编号
		String user_id = "4208827637165113"; // 身份证号
		String open_bank = "招商银行"; // 开户行
		String bank_code = "CMBC"; // 银行code
		String user_name = "张三"; // 用户名
		String user_mobile = "17671293366"; // 开户手机
		String bank_card = "6225883170654706"; // 银行卡号
		String bank_province = "湖北省"; // 开户行所在省
		String bank_city = "武汉市"; // 开户行所在市
		String device_type = "iphone"; // 设备类型
		String device_num = "123456"; // 设备型号

		treeMap.put("order_no", order_no);
		treeMap.put("user_id", user_id);
		treeMap.put("open_bank", open_bank);
		treeMap.put("bank_code", bank_code);
		treeMap.put("user_name", user_name);
		treeMap.put("user_mobile", user_mobile);
		treeMap.put("bank_card", bank_card);
		treeMap.put("bank_province", bank_province);
		treeMap.put("bank_city", bank_city);
		treeMap.put("device_type", device_type);
		treeMap.put("device_num", device_num);

		String hello = JSON.toJSONString(treeMap);
		String biz_data = desService.desEncrypt(hello, key);//
		String des_key = rsaService.encrypt(key);//
		String sign = rsaService.generateSign(biz_data);//
		TreeMap<String, String> map = new TreeMap<>();
		map.put("biz_data", biz_data);
		map.put("des_key", des_key);
		map.put("sign", sign);
		map.put("code", "1");
		map.put("channel", "1572");
		map.put("biz_enc", "1");

		String url = "http://localhost:8080/beadwalletloanapp/youyu/bindCard.do";
		String post = HttpClientHelper.post(url, "utf-8", map);
		System.out.println(post);
	}

	/**
	 * 
	 * @Title: testRsa1 @Description: 有鱼-检查用户测试 @return void @throws
	 */
	@Test
	public static void testRsa1() {
		String code = "1";//
		String channel = "1572";//
		String biz_enc = "1";//
		String biz_data = "nNpLoce4neJYppBh5vD1EhmuF+7uf1xTq52INddRX6lIPHEaKoyIO6zxbGlKEIuxVidzp/A6WAOFAp4TphOvAp1CDdLLIV30ZIy2OB39YC2TwukA3zzZvQ==";
		String des_key = "Gb8MApEUlUlQ64H1DpDhU9V77R/YmX6wiPPBZxj8LZl+6VGr02vH9CMZuC2arYa81kGEadOEZoI1CRyDmPCfEXYj2VIaWKFLTEXadCKYbuT+gUG946nMdImlWPfD3NJwzpVXRC7/cgPi8cbKIn520lVyW7pPwa5Vva5Qi1B+VFs=";
		String sign = "ZMfxv1Bc82Hhkf/vlcke0upTw1gPW5zUJB7BUw8EFtlpGc/Co0yjTowI0Q7WIs8hqcNBLuQqH4kemBlPH9NICv5B09Me3bEhwQ+Fv2PgCe6HVxfE1/HWAvZml/pI5egOSXOuiEpwlVP3BmW5eGQ7I3yZj07tqYcWII1WR4BUxAo=";

		TreeMap<String, String> treeMap = new TreeMap<>();
		treeMap.put("biz_data", biz_data);
		treeMap.put("des_key", des_key);
		treeMap.put("sign", "");
		treeMap.put("code", "1");
		treeMap.put("channel", "1572");
		treeMap.put("biz_enc", "1");
		// "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/checkUser.do";
		// String url =
		// "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/PushOrder.do";
		String url = "http://localhost:8080/beadwalletloanapp/youyu/interface/checkUser.do";
		String post = HttpClientHelper.post(url, "UTF-8", treeMap);
		System.out.println(post);
	}

	/**
	 * 
	 * @Title: testRsa6 @Description:有鱼订单推送测试 @return void @throws
	 */
	@Test
	public static void testRsa6() {
		
		String sign ="Xn1au97/q+SgJZaNlOV6tmx9gsBB8FgDpj+ZM00B9GtNJG5/gH83nj+g1ss0iKWIWcbdxzNU6tOcty9PiQc6OK8XnyunqYJPH/+5OtYQ/OT7qiTX+m4avirT7+YxhzE2VnTzGyWn8Qd5fj/zfN2+yGIc1Gp6QGqAADYklUUAj4U=";
		String biz_data ="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";
		String des_key = "LE0x7KRJyH3o6ocoxSx1xaYBS1a0VcIoL4YG0PH7MQhq1WQMijSKcYmd1bBYm0uBtiHM8EoSin0+ggSGkQ+gVyxDTgljgaFfWagARGytad0SuTvqeScrhubyPD8gk+m6SsKiZmPksdI6nRrCZq6i85OA4eAX02w+oU4UFbmZll4=" ;
		TreeMap<String, String> treeMap = new TreeMap<>();
		treeMap.put("biz_data", biz_data);
		treeMap.put("des_key", des_key);
		treeMap.put("sign", sign);
		treeMap.put("code", "1");
		treeMap.put("channel", "RAQHEFD57251H7C");
		treeMap.put("biz_enc", "1");
		 String url =
		"http://localhost:8080/beadwalletloanapp/youyu/interface/PushOrder.do";
		//String url = "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/PushOrder.do";
		String post = HttpClientHelper.post(url, "UTF-8", treeMap);
		System.out.println(post);
	}

	/**
	 * 订单推送
	 * @Title: testRsa7
	 * @Description: TODO    
	 * @return void    
	 * @throws
	 */
	@Test
	public static void testRsa7() {
		String biz_data = "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";
		String des_key = "cA0Xy9qffssWN7DU0CTvA1CFg1NXXJuixVu0Ch9LD5D3CMM19fjqEkjX76lC8Xct9x3trbcylsdC8sLHjO7I+ctgvfS1KoNinq8izWooBkB9ZwtNplLqDGeuP7FycYh2r39fdUPxKlBS691IL7/XNOURhMCUCl70Uux8cUKAfrw=";
		String sign = "cfw+YPIiRTvlL6IPRgpxNfrCQmRlWlsZo5AYh8F9U+Mc2/QovSXcLJYAt5wMLjd4opewbVQEnir48Xm0n1E5KlAcMLZg0j4XcQTpRovY/tA77XvqBBds+KGrBHMb0USN8dSTjd4xbeUt9FLj3UKPVSml9ywtNHtZ6TaEQVJgRQs=";
		TreeMap<String, String> treeMap = new TreeMap<>();
		treeMap.put("biz_data", biz_data);
		treeMap.put("des_key", des_key);
		treeMap.put("sign", sign);
		treeMap.put("code", "1");
		treeMap.put("channel", "RAQHEFD57251H7C");
		treeMap.put("biz_enc", "1");
		// "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/checkUser.do";
		String url = "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/PushOrder.do";
		// String url =
		// "http://localhost:8080/beadwalletloanapp/youyu/bindCard.do";
		String post = HttpClientHelper.post(url, "UTF-8", treeMap);
		System.out.println(post);
	}

	/**
	 * 
	 * @Title: testRsa8 @Description:查询状态 @return void @throws
	 */
	@Test
	public static void testRsa8() {
		String biz_data = "6LR+/p6G6AqJEvTddZjrsnojwKqSmGsG";
		String des_key = "aNJwtyvQKLMABIiqhda85xvW4RLYSn+u9AqaKS27cWkY8SSsi5CBHJHKizedUWmelCbi3TlPRHRzIF3Hgy9Kmx3h7y/+Q8uoAxA6JxLA711LZ82feWgLBVD6SbiTwiQe3jmVo6df3H+HJYEppMoHuCME2XDNC7wzcnhwBq6th8U=";
		String sign = "mee37XV/nfSyuz0iU8Beo5ucqryNZBUqgPoKJmz6tp3TkgInnTqWHvEBxL93QBdTxx2lcKY7LDrIoVBOf45v9QlvAhYHdK7KgJVeo58DNtGyiYmHYAM8OY1xhFg9UEk2HlA7AirXXosRMLG1/053aC8qjNx6YkvD7chiCgNDCog=";
		TreeMap<String, String> treeMap = new TreeMap<>();
		treeMap.put("biz_data", biz_data);
		treeMap.put("des_key", des_key);
		treeMap.put("sign", sign);
		treeMap.put("code", "1");
		treeMap.put("channel", "RAQHEFD57251H7C");
		treeMap.put("biz_enc", "1");
		// "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/checkUser.do";
		// String url
		// ="http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/PushOrder.do";
		String url = "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/queryOrderStatus.do";
		// String url =
		// "http://localhost:8080/beadwalletloanapp/youyu/bindCard.do";
		String post = HttpClientHelper.post(url, "UTF-8", treeMap);
		System.out.println(post);
	}

	/**
	 * 
	 * @Title: testRsa9 @Description: 绑卡测试 @return void @throws
	 */
	@Test
	public static void testRsa9() {
		String youyusy = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMB2hXbMFrpqMqsujChLtdCDwZssHpcmYDrjgfEAUvtKaKs8O+ux4wrKPhVlowPmDsTCS0GhQi5XiW6AYyLODAmWe9pbRbhe6KMpDvF8D5p3wMiVnalFRK+TxzoYd09Jhy9lY/02ZpjztFJ0G68giHPGOLvj4xQbJ9ThzBZTHCN7AgMBAAECgYAhkQo56+JS5M6teFLNfFbbZP9RNuKm7fR+kMtK4wmV9iZHJxw0QTQd36PwS2eg+HC+9Dv32E4Ykv/PG+kuWs1SWVIw3kzPyaJajT7R32vUcHQmeCZwvNwbULmGIco3pR5Z9Gksde13UwQRhVCzkvH0LQl1nVdNeJoz5djL+pPIgQJBAOHzVk+xWATNoJlLTUXO8N2VnLFdnfe+ZEz/ZOTJ5xwHuOLCsKB/xm19SJ24a3h5Fm2vdjDd0Hgovi4oRF4KeuECQQDaDxLtqgKWWc6+Udawl772egihtcnrLpUrKx9xCbIxZSUDHrQ3EtPEiaCVnC2UrLPG33yk48jbfU7fbuv/MqXbAkEAnnyBsRpy48Ob/4p7JBkYiESWCS7iS9EnJ38ItRYN3nJoM95d5+ZYN5pmIgMmlvVQTxWA8JvVy0LAyz2BXvk44QJBAMqpCdGCmUb9Do2JZ/vV/G/8uPr6BkCimZZ2TJF1Dnyj4UNGDP3GbLSTqICDl0U/QRJK8QAah7mee2hjIcibXNMCQFdQohuQZk3CzsskDwPBTGULyBW+oq7KUHHnx0s296NfWFLH9zW1f8UIrjJFNo2toBeztxeB211M+DyLRX+Qxb4=";

		String order_no = "910326"; // 订单编号 string
		String user_id = "420881199103265947"; // 身份证号 string
		String open_bank = "招商银行"; // 开户行 string 开户行名称
		String bank_code = "CMBC"; // 银行code string CCB
		String user_name = "何龙云"; // 用户名 string 银行卡开户名
		String user_mobile = "17671292070"; // 开户手机 string 银行卡开户手机号
		String bank_card = "6225882714191163"; // 银行卡号 string 银行卡号
		String bank_province = "湖北"; // 开户行所在省 string 汉字简称，如：山东
		String bank_city = "武汉"; // 开户行所在市 string 汉字简称，如：济南
		String device_type = "ios";// 设备类型 string ios,android
		String device_num = "iphone6s";// 设备型号 string iphone7,2

		TreeMap<String, String> treeMap = new TreeMap<>();
		treeMap.put("order_no", order_no);
		treeMap.put("user_id", user_id);
		treeMap.put("open_bank", open_bank);
		treeMap.put("bank_code", bank_code);
		treeMap.put("user_name", user_name);
		treeMap.put("user_mobile", user_mobile);
		treeMap.put("bank_card", bank_card);
		treeMap.put("bank_province", bank_province);
		treeMap.put("bank_city", bank_city);
		treeMap.put("device_type", device_type);
		treeMap.put("device_num", device_num);
		String data_user = JSON.toJSONString(treeMap);

		RsaService rsaService = new RsaServiceImpl(youyusy, YouyuConstant.SHUIXIANG_PUBLICKEY, RsaService.PKCS8);
		DesService desService = new DesServiceImpl();
		String key = desService.getRandomDesKey(16);
		String biz_data = desService.desEncrypt(data_user, key);
		String des_key = rsaService.encrypt(key);
		String sign = rsaService.generateSign(biz_data);
		TreeMap<String, String> tree = new TreeMap<>();
		tree.put("biz_data", biz_data);
		tree.put("des_key", des_key);
		tree.put("sign", sign);
		tree.put("code", "1");
		tree.put("channel", "RAQHEFD57251H7C");
		tree.put("biz_enc", "1");
		// "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/checkUser.do";
		// "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/updateSignContract.do";

		// String url
		// ="http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/PushOrder.do";
		// String url =
		// "http://106.14.238.126:8092/beadwalletloanapp/youyu/interface/queryOrderStatus.do";
		String url = "http://localhost:8080/beadwalletloanapp/youyu/bindCard.do";
		String post = HttpClientHelper.post(url, "UTF-8", tree);
		System.out.println(post);
	}

}
