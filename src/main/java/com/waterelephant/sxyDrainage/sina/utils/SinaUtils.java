//
//package com.waterelephant.sxyDrainage.sina.utils;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.waterelephant.sxyDrainage.utils.kaNiuUtil.BASE64Decoder;
//import com.waterelephant.utils.CdnUploadTools;
//import com.waterelephant.utils.CommUtils;
//
///**
// * ClassName:SinaUtils <br/>
// * Function: 新浪操作工具类 <br/>
// * 
// * @author YANHUI
// * @version 1.0
// */
//public class SinaUtils {
//
//	// 订单状态映射
//	private static Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>();
//
//	static {
//		ORDER_STATUS_MAP.put("1", "999"); // 进件失败
//		ORDER_STATUS_MAP.put("2", "402"); // 进件成功
//		ORDER_STATUS_MAP.put("3", "402"); // 进件成功
//
//		ORDER_STATUS_MAP.put("4", "403"); // 审核成功
//		ORDER_STATUS_MAP.put("7", "999"); // 审核失败
//		ORDER_STATUS_MAP.put("8", "999"); // 审核失败
//
//		ORDER_STATUS_MAP.put("9", "509"); // 放款成功
//		ORDER_STATUS_MAP.put("13", "213"); // 逾期
//
//		ORDER_STATUS_MAP.put("6", "201"); // 结清
//
//		ORDER_STATUS_MAP.put("5", "701"); // 待放款
//		ORDER_STATUS_MAP.put("11", "701"); // 签约成功
//		ORDER_STATUS_MAP.put("12", "502");
//		ORDER_STATUS_MAP.put("14", "502");
//	}
//
//	public static String convertOrderStatus(Long oriStatus) {
//		if (oriStatus == null) {
//			return null;
//		}
//
//		return ORDER_STATUS_MAP.get(String.valueOf(oriStatus));
//	}
//
//	/**
//	 * 
//	 * @Title: getWorkYear @Description: @param @param type @return
//	 *         String @throws
//	 */
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
//	 * @Title: getIndustry @Description: @param @param industry @return
//	 *         String @throws
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
//	/**
//	 * 上传至oss（code0091）
//	 * 
//	 * @param url
//	 * @param fileName（fileName
//	 *            + ".jpg"）
//	 * @return
//	 */
//	public static String urlUpload(InputStream inputStream, String fileName) {
//		String uploadPath = "";
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			uploadPath = "upload/backend/" + sdf.format(new Date()) + "/" + fileName + ".jpg";
//			if (!CommUtils.isNull(inputStream)) {
//				// 上传至oss
//				CdnUploadTools.uploadPic(inputStream, uploadPath);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (!CommUtils.isNull(inputStream)) {
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//		return uploadPath;
//	}
//}
