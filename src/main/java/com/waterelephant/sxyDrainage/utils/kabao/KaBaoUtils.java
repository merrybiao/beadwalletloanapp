//
//package com.waterelephant.sxyDrainage.utils.kabao;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//
///**
// * ClassName:KaBaoUtils <br/>
// * Function: 51卡宝操作工具类  <br/>
// * @author   liwanliang
// * @version   1.0  
// */
//public class KaBaoUtils {
//
//	// 订单状态映射
//    private static Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>();
//    
//    static{
//    	ORDER_STATUS_MAP.put("1", "302");   // 进件失败
//    	ORDER_STATUS_MAP.put("2", "301");   //进件成功	
//    	ORDER_STATUS_MAP.put("3", "301");	//进件成功
//    	
//    	ORDER_STATUS_MAP.put("4", "401");   // 审核成功
//    	
//    	ORDER_STATUS_MAP.put("7", "403");   // 审核失败
//    	ORDER_STATUS_MAP.put("8", "403");   // 审核失败
//    	
//        ORDER_STATUS_MAP.put("9", "501");   // 放款成功 还款中
//        ORDER_STATUS_MAP.put("13", "604");  // 逾期
//        
//        ORDER_STATUS_MAP.put("6", "605");   // 结清
//        
//        ORDER_STATUS_MAP.put("5", "701");   // 签约成功待放款
//        ORDER_STATUS_MAP.put("11", "701");	// 签约成功待放款
//        ORDER_STATUS_MAP.put("12", "701");  // 签约成功待放款
//        ORDER_STATUS_MAP.put("14", "701");	// 签约成功待放款
//    }
//    public static String convertOrderStatus(Long oriStatus){
//        if (oriStatus == null){
//            return null;
//        }
//
//        return ORDER_STATUS_MAP.get(String.valueOf(oriStatus));
//    }
//		
//    
//    /**
//     * 
//     * @Title: getWorkYear  
//     * @Description:  
//     * @param @param type
//     * @return String      
//     * @throws
//     */
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
//	 * @Title: getIndustry  
//	 * @Description:  
//	 * @param @param industry
//	 * @return String      
//	 * @throws
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
////	 public static void main(String[] args) {
////		String convertOrderStatus = KaBaoUtils.convertOrderStatus(4L);
////		System.out.println(convertOrderStatus);
////		
////	}
//}
//
