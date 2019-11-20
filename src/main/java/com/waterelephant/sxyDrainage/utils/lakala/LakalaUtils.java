//package com.waterelephant.sxyDrainage.utils.lakala;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeSet;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.waterelephant.utils.DoubleUtil;
//
//public class LakalaUtils {
//	
//	
//	public static void main(String[] args) {
//		System.out.println(LakalaUtils.calculateRepayMoney(1500d, 4, 0.00686d));
//		//System.out.println(LakalaUtils.getFetureDate(14));
//	}
//	
//	
//	public static String getOrderStatus(long status){
//		if(11==status || 12==status)
//			return "A";
//		if(9==status)
//			return "B";
//		if(13==status)
//			return "D";
//		if(6==status)
//			return "E";
//		return null;
//	}
//	
//	public static String getBankCode(String bankCode){
//		if("9001".equals(bankCode))
//			return "CEB";
//		if("9002".equals(bankCode))
//			return "CMB";
//		if("9003".equals(bankCode))
//			return "ICBC";
//		if("9004".equals(bankCode))
//			return "ABC";
//		if("9005".equals(bankCode))
//			return "CCB";
//		if("9006".equals(bankCode))
//			return "BCOM";
//		if("9007".equals(bankCode))
//			return "GDB";
//		if("9009".equals(bankCode))
//			return "SPDB";
//		if("9010".equals(bankCode))
//			return "BOC";
//		if("9018".equals(bankCode))
//			return "CITIC";
//		if("9022".equals(bankCode))
//			return "CMBC";
//		if("9023".equals(bankCode))
//			return "CIB";
//		if("9036".equals(bankCode))
//			return "PAB";
//		if("9043".equals(bankCode))
//			return "HXB";
//		if("9065".equals(bankCode))
//			return "SHB";
//		return null;
//	}
//	
//	/** 
//	    * 获取未来 第 past 天的日期 
//	    * @param past 
//	    * @return 
//	    */  
//	   public static String getFetureDate(int past) {  
//	       Calendar calendar = Calendar.getInstance();  
//	       calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);  
//	       Date today = calendar.getTime();  
//	       SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");  
//	       String result = format.format(today);  
//	       return result;  
//	   }  
//	
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
//	
//}
