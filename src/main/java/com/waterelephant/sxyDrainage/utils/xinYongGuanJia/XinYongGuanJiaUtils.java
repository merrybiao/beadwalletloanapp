//package com.waterelephant.sxyDrainage.utils.xinYongGuanJia;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.collections.set.SynchronizedSortedSet;
//import org.apache.commons.lang.StringUtils;
//
///**
// * 
// * <p>Title: XinYongGuanJiaUtils</p>  
// * <p>Description: </p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//public class XinYongGuanJiaUtils {
//	// 订单状态映射
//    private static Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>();
//    
//    static{
//    	ORDER_STATUS_MAP.put("1", "402");  // 审核中
//    	ORDER_STATUS_MAP.put("2", "402");
//    	ORDER_STATUS_MAP.put("3", "402");
//    	
//    	ORDER_STATUS_MAP.put("4", "401");  // 审核成功
//    	ORDER_STATUS_MAP.put("7", "403");  // 审核失败
//    	ORDER_STATUS_MAP.put("8", "403");  // 审核失败
//    	
//        ORDER_STATUS_MAP.put("9", "501");  // 放款成功
//        ORDER_STATUS_MAP.put("13", "604"); // 逾期
//        
//        ORDER_STATUS_MAP.put("6", "605");  // 结清
//        
//        ORDER_STATUS_MAP.put("5", "502");  // 待放款
//        ORDER_STATUS_MAP.put("11", "502");
//        ORDER_STATUS_MAP.put("12", "502");
//        ORDER_STATUS_MAP.put("14", "502");
//    }
//    public static String convertOrderStatus(Long oriStatus){
//        if (oriStatus == null){
//            return null;
//        }
//
//        return ORDER_STATUS_MAP.get(String.valueOf(oriStatus));
//    }
//		
////    public static void main(String[] args) {
////    	String convertOrderStatus = XinYongGuanJiaUtils.convertOrderStatus(1l);
////    	System.out.println(convertOrderStatus);
////    	
////	}
//	public static String getWorkYear(String type) {
//		if (StringUtils.isBlank(type)) {
//			return "";
//		} else if ("1".equals(type)) {
//			return "0-5个月 ";
//		} else if ("2".equals(type)) {
//			return "6-11个月 ";
//		} else if ("3".equals(type)) {
//			return "1-3年 ";
//		} else if ("4".equals(type)) {
//			return "3-7年 ";
//		} else if ("5".equals(type)) {
//			return "7年以上 ";
//		}
//		return "";
//	}
//
//	/**
//	 * 
//	 * @param industry
//	 * @return
//	 */
//	public static String getIndustry(String industry) {
//		if (StringUtils.isBlank(industry)) {
//			return "";
//		} else if ("A".equals(industry.toUpperCase())) {
//			return "农牧业 ";
//		} else if ("B".equals(industry.toUpperCase())) {
//			return "商贸建筑业";
//		} else if ("C".equals(industry.toUpperCase())) {
//			return "服务业";
//		} else if ("D".equals(industry.toUpperCase())) {
//			return "金融保险业 ";
//		} else if ("E".equals(industry.toUpperCase())) {
//			return "公务员 ";
//		} else if ("F".equals(industry.toUpperCase())) {
//			return "军队 公安 警察 等 ";
//		} else if ("G".equals(industry.toUpperCase())) {
//			return "学生 宗教 自由职业者 ";
//		} else if ("H".equals(industry.toUpperCase())) {
//			return "赌坊 KTV 其他娱乐业 ";
//		} else if ("I".equals(industry.toUpperCase())) {
//			return "无业 ";
//		} else if ("J".equals(industry.toUpperCase())) {
//			return "其他 ";
//		}
//		return "";
//	}
//	
//}
