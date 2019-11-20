//package com.waterelephant.sxyDrainage.utils.rongshu;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeSet;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.br.util.signature.BairongSignature;
//import com.waterelephant.sxyDrainage.entity.rongshu.ContactInfoResponse;
//import com.waterelephant.utils.DoubleUtil;
//
//public class RongShuUtils {
//	
//	
//	public static void main(String[] args) {
//		System.out.println(RongShuUtils.calculateRepayMoney(1500d, 3, 0.00686d));
//	}
//	
//	/**
//	 * 榕树加密
//	 * @return
//	 */
//	public static String RSAEncrypt(Map<String,String> params,String priKey){
//		String sign = null;	
//		if(null!=params && !StringUtils.isBlank(priKey)){		
//		try {
//			sign = BairongSignature.signSHA1(params, priKey);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//		return sign;
//	}
//	
//	/**
//	 * 榕树验签
//	 * @param params
//	 * @param sign
//	 * @param pubKey
//	 */
//	public static boolean checkSign(Map<String,String> params, String sign, String pubKey){
//		boolean flag = false;
//		if(null!=params && !StringUtils.isBlank(pubKey) && !StringUtils.isBlank(sign)){		
//		try {
//			flag = BairongSignature.checkSignSHA1(params, sign, pubKey);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//		return flag;
//	}
//
//	//0-不拒绝;1-黑名单;2-在贷;3-短时拒绝;4-其他
//	public static int getRejectReason(int code){
//		if(0==code){
//			return 0;
//		}else if(2001==code){
//			return 1;
//		}else if(2002==code){
//			return 2;
//		}else if(2003==code){
//			return 3;
//		}else if(2004==code){
//			return 4;
//		}
//		return 4;
//	}
//	
//	public static Double calculateRepayMoney(Double amount, int term, Double interestRate) {
//			switch (term) {
//			case 1:
//				return DoubleUtil.round(amount / 4 * 4 * interestRate, 0);
//			case 2:
//				return DoubleUtil.round(amount / 4 * 3 * interestRate, 0);
//			case 3:
//				return DoubleUtil.round(amount / 4 * 2 * interestRate, 0);
//			case 4:
//				return DoubleUtil.round(amount / 4 * 1 * interestRate, 0);
//			default:
//				return 0.0D;
//			}
//		}
//	
//	public static String getWorkYear(String type) {
//		if (StringUtils.isBlank(type)) {
//			return "";
//		} else if ("110001".equals(type)) {
//			return "0-5个月 ";
//		} else if ("110002".equals(type)) {
//			return "6-11个月 ";
//		} else if ("110003".equals(type)) {
//			return "1-3年 ";
//		} else if ("110004".equals(type)) {
//			return "3-7年 ";
//		} else if ("110005".equals(type)) {
//			return "7年以上 ";
//		}
//		return "";
//	}
//	
//	
//	public static String getOperatorType(String type){
//		if (StringUtils.isBlank(type)) {
//			return "未知";
//		} else if ("0".equals(type)) {
//			return "未知";
//		} else if ("1".equals(type)) {
//			return "移动";
//		} else if ("2".equals(type)) {
//			return "联通";
//		} else if ("3".equals(type)) {
//			return "电信";
//		}
//		return "未知";
//	}
//	
//	public static int getMarryStatus(String type){
//		/*50001	已婚
//		50002	未婚
//		50003	丧偶
//		50004	离异
//		50005	再婚
//		50006	未知*/
//		if (StringUtils.isBlank(type)) {
//			return 0;
//		} else if ("50001".equals(type)) {
//			return 1;
//		} else if ("50002".equals(type)) {
//			return 0;
//		} else if ("50003".equals(type)) {
//			return 0;
//		} else if ("50004".equals(type)) {
//			return 0;
//		}else if ("50005".equals(type)) {
//			return 1;
//		}else if ("50006".equals(type)) {
//			return 0;
//		}
//		return 0;
//	}
//	
//	public static String getSignStrData(Map<String, String> requestMap) {
//		Set<String> sortedRequestKey = new TreeSet<>(requestMap.keySet());
//		StringBuilder sb = new StringBuilder();
//		for (String reqKey : sortedRequestKey) {
//			String value = requestMap.get(reqKey);
//			sb.append(reqKey).append("=").append(value).append("&");
//		}
//		String str = sb.toString();
//		String data = str.substring(0,str.length()-1);
//		try {
//			return data;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//	
//	/**
//	 * MD5加密
//	 * @param str
//	 * @return
//	 */
//	public static String getMD5(String str) {  
//        try {  
//            // 生成一个MD5加密计算摘要  
//            MessageDigest md = MessageDigest.getInstance("MD5");  
//            // 计算md5函数  
//            md.update(str.getBytes());  
//            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
//            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
//            return new BigInteger(1, md.digest()).toString(16);  
//        } catch (Exception e) {  
//           e.printStackTrace();  
//           return null;  
//        }  
//    } 
//	
//	/**
//	 * 解析榕树用户通讯录数据
//	 * @param response
//	 * @return
//	 */
//	public static synchronized List<ContactInfoResponse> getNameOrPhone(String response){
//		ContactInfoResponse contact = null;
//		List<ContactInfoResponse> contactList = new ArrayList<ContactInfoResponse>();
//		Map<String,Map<String,Object>> map = JSON.parseObject(response,Map.class);
//		//得到通讯录
//		Object object = map.get("userInfo").get("contact");
//		List<Map<String,Object>> list = JSON.parseObject(object.toString(), List.class);
//		int num = 0;
//		if(null!=list && 3<=list.size()){
//			for(int i=0;i<100;i++){
//				if(3==num){//如果已经取了3个人了，结束循环
//					break;
//				}
//				//取三个亲友手机号信息
//				Object objName = list.get(i).get("names");
//				Object objPhone = list.get(i).get("phones");
//				if(null==objName || null==objPhone){
//					continue;
//				}
//				JSONArray strName = (JSONArray)objName;
//				JSONArray strPhone = (JSONArray)objPhone;
//				contact = new ContactInfoResponse();
//				if(strName.size()>0){
//				contact.setNames(strName.getString(0));
//				}else{
//					continue;//如果姓名为空，结束当前循环，进入下一次
//				}
//				if(strPhone.size()>0){
//				contact.setPhones(strPhone.getString(0));
//				}else{
//					continue;//如果电话号码为空，结束当前循环，进入下一次
//				}
//				contactList.add(contact);
//				num++;
//			}
//		}
//		return contactList;
//	}
//	
//}
