package sxdzsmsTest;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.utils.HttpClientHelper;
import com.waterelephant.bjsms.entity.CurrencyParam;
import com.waterelephant.bjsms.entity.MarketParam;
import com.waterelephant.bjsms.entity.RequestData;
import com.waterelephant.bjsms.entity.RequestShopIngSmsData;
import com.waterelephant.bjsms.entity.RequestSmsData;
import com.waterelephant.bjsms.entity.SmsContent;
import com.waterelephant.bjsms.utils.SmsMD5Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test1 {
	
	/**
	 * 测试亿美的火力卡短信
	 * @param args
	 */
	public static void main1(String[] args) {
		Map<String,String> info = new HashMap<String, String>();
		info.put("msg", "【火力卡】尊敬的毛先生，您在火力卡的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
		//info.put("msg", "【火力卡】您的验证码为521432,10分钟内有效！");
		info.put("phone", "18566789940");
		info.put("type", "1");
		info.put("businessScenario", "1");
		Map<String,String>  sms = new HashMap<String, String>();
		String jsonString1 = JSON.toJSONString(info);
		sms.put("secretkey", SmsMD5Util.encoding(jsonString1));
		sms.put("content", jsonString1);
		String jsonString = JSON.toJSONString(sms);		
		String resp  = HttpClientHelper.post("https://www.beadwallet.com/beadwalletloanapp/sms/message/sendshsms.do", "utf-8", jsonString);
		log.info(resp);
	}
	
	/**
	 * 亿美的【水象办公】
	 * RequestData<CurrencyParam>
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String,String> info = new HashMap<String, String>();
		info.put("msg", "【水象优品】20190917010000000011存储成功，取件码为：227063，请在2019-09-17+23:59:59之前前往湖北省武汉市江夏区光谷大道61号光谷智慧园26栋取出存放的物品，否则将被收取超时费用。超时收费规则：大柜1元%2F天%20中柜1.5元%2F天%20小柜2元%2F天");
		info.put("phone", "15827167589");
		info.put("type", "1");
		info.put("businessScenario", "1");
		Map<String,String>  sms = new HashMap<String, String>();
		String jsonString1 = JSON.toJSONString(info);
		sms.put("secretkey", SmsMD5Util.encoding(jsonString1));
		sms.put("data", jsonString1);
		String jsonString = JSON.toJSONString(sms);		
		String resp  = HttpClientHelper.post("https://www.beadwallet.com/beadwalletloanapp/sxdz/message/sedMessage.do", "utf-8", jsonString);
		log.info(resp);
	}
	
	
	public static void main5(String[] args) {
		SmsContent info = new SmsContent();
		info.setMsg("【水象优品】小主～您账户中的优惠券就要过期啦，记得使用哦！更多爆款打开水象优品回TD退订");
		info.setPhone("15271946272");
		info.setType("1");
		info.setBusinessScenario("1");
		System.out.println(JSON.toJSONString(info));
		RequestSmsData  sms = new RequestSmsData();
		String jsonString1 = JSON.toJSONString(info);
		sms.setSecretkey(SmsMD5Util.encoding(jsonString1));
		sms.setContent(info);
		String jsonString = JSON.toJSONString(sms);	
		System.out.println(jsonString);
		String resp  = HttpClientHelper.post("https://www.beadwallet.com/beadwalletloanapp/sxyp/message/sendMessage.do", "utf-8", jsonString);
		log.info(resp);
	}
	
	/**
	 * 亿美的【水象办公】
	 * RequestData<CurrencyParam>
	 * @param args
	 */
	public static void main8(String[] args) {
		SmsContent info = new SmsContent();
		info.setMsg("【水象办公】验证码：242859，10分钟有效，请勿向他人泄露。");
		info.setPhone("15827167589");
		info.setType("1");
		info.setBusinessScenario("1");
		RequestShopIngSmsData  sms = new RequestShopIngSmsData();
		String jsonString1 = JSON.toJSONString(info);
		sms.setSecretkey(SmsMD5Util.encoding(jsonString1));
		sms.setData(info);
		String jsonString = JSON.toJSONString(sms);		
		String resp  = HttpClientHelper.post("https://www.beadwallet.com/beadwalletloanapp/beijing/message/sedMessage.do", "utf-8", jsonString);
		log.info(resp);
	}
	
	/**
	 * 联麓的水象优品
	 * RequestData<CurrencyParam>
	 * @param args
	 */
	public static void main0(String[] args) {
		MarketParam info = new MarketParam();
		info.setContent("小主～您账户中的优惠券就要过期啦，记得使用哦！更多爆款打开“水象优品” 回TD退订");
		info.setPhones("18341343495,18340958273,18717467773,13589640526,18315840057");
		info.setSign("【水象优品】");
		info.setBatch("1-1");
		RequestData<MarketParam>  req = new RequestData<MarketParam>();
		req.setSecretkey( SmsMD5Util.encoding(JSON.toJSONString(info)));
		req.setParam(info);
		String resp  = HttpClientHelper.post("https://www.beadwallet.com/beadwalletloanapp/lianluMarket/message/sendMarketMsg.do", "utf-8", JSON.toJSONString(req));
		log.info(resp);
	}
	
	public static void main4(String[] args) {
		JSONObject info = new JSONObject();
		info.put("msg", "【水象分期】您的验证码为：9t4u57，请不要转告他人，1分钟内有效。");
		info.put("phone", "15827167589");
		info.put("type", 1);
		info.put("businessScenario", 1);
		JSONObject sms = new JSONObject();
		sms.put("secretkey", SmsMD5Util.encoding(info.toString()));
		sms.put("data", JSON.toJSONString(info));
		System.out.println(sms.toString());
	} 
}
