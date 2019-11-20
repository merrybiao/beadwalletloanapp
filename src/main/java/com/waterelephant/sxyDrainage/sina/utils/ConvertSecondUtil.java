//
//package com.waterelephant.sxyDrainage.sina.utils;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * ClassName:ConvertSecondUtil <br/>
// * Function: 转换时间  <br/>
// * @author   YANHUI
// * @version   1.0  
// */
//public class ConvertSecondUtil {
//	
//	private static Logger logger = LoggerFactory.getLogger(ConvertSecondUtil.class);
//	
//	/**
//	 * @Title: getSecond  
//	 * @Description:时分秒格式 ： 00小时00分00秒----->转换秒数
//	 * @param @param time 格式：xx小时yy分zz秒
//	 * @param @return     
//	 * @return long  
//	 * @throws
//	 */
//	public static Long getSecond(String time){
//		try {
//			long s = 0L;
//			if(time.contains("小时")){
//				s=Integer.parseInt(time.substring(0,time.indexOf("小时")))*3600;    //小时
//			}
//			if(time.contains("分")){
//				s+=Integer.parseInt(time.substring(time.indexOf("时")+1,time.indexOf("分")))*60;    //分钟
//			}
//			s+=Integer.parseInt(time.substring(time.indexOf("分")+1,time.indexOf("秒")));    //秒
//			
//			return s;
//		} catch (Exception e) {
//			logger.info("时间格式转换发生异常:" + e);
//			return null;
//		}
//	}
//}
//
