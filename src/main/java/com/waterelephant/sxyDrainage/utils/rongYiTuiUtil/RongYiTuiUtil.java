///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.rongYiTuiUtil;
//
//import java.text.ParsePosition;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * Module: 
// * RongYiTuiHttpclientUtil.java 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class RongYiTuiUtil {
//	/**
//	 * 日期前推或后推天数,其中day表示天数
//	 * 
//	 * @param date yyyy-MM-dd
//	 * @param day 向前推为负数，向后推为正数
//	 * @return
//	 */
//	public static Date getPreDay(Date dateDate, int day) {
//		Calendar gc = Calendar.getInstance();
//		gc.setTime(dateDate);
//		gc.add(Calendar.DATE, day);
//		return gc.getTime();
//	}
//
//	/**
//	 * 将指定格式的字符串转为日期时间对象
//	 * 
//	 * @param strDate 时间
//	 * @param format 格式
//	 * @return 时间 date
//	 */
//	public static Date formatToDate(String strDate, String format) {
//		if (strDate == null) {
//			return null;
//		}
//		SimpleDateFormat formatter = new SimpleDateFormat(format);
//		ParsePosition pos = new ParsePosition(0);
//		Date strtodate = formatter.parse(strDate, pos);
//		return strtodate;
//	}
//
//	/**
//	 * 将日期时间对象转为 指定格式的字符串
//	 * 
//	 * @param strDate
//	 * @param format
//	 * @return string
//	 */
//	public static String formatToStr(Date dateDate, String format) {
//		if (dateDate == null) {
//			return null;
//		}
//		SimpleDateFormat formatter = new SimpleDateFormat(format);
//		String dateString = formatter.format(dateDate);
//		return dateString;
//	}
//
//	public static String getJob(int type) {
//		if(1==type){
//			return "企业主 ";
//		}else if(2==type){
//			return "个体户 ";
//		}else if(3==type){
//			return "工薪族 ";
//		}else if(4==type){
//			return "学生 ";
//		}else if(5==type){
//			return "自由职业 ";
//		}
//		return "";
//	}
//
//	// 1：0-5个月 2：6-11个月 3：1-3年 4： 3-7年 5：7年以上
//	public static String getWorkYear(int type) {
//		if (1 == type) {
//			return "0-5个月 ";
//		} else if (2 == type) {
//			return "6-11个月 ";
//		} else if (3 == type) {
//			return "1-3年 ";
//		} else if (4 == type) {
//			return "3-7年 ";
//		} else if (5 == type) {
//			return "7年以上 ";
//		}
//		return "";
//	}
//
//	/**
//	 * 
//	 * 转换成融易推订单状态
//	 * 
//	 * @param type
//	 * @return
//	 */
//	// 当前工单状态 1草稿 2初审 3终审 4待签约 5待放款 6结束 7拒绝 8撤回 9还款中 11待生成合同 12待债匹 13逾期 14债匹中
//	// 10 申请中 20 审核通过 30审核拒绝 40 用户确认 50 用户提现60 投资中 70 放款中 80 已放款 90 正常还款中 100 宽限期内 110 坏账 120 逾期 130 已结清 199 贷款失败
//	public static int toRytState(int orderStatus) {
//		if (1 == orderStatus || 2 == orderStatus || 3 == orderStatus) {
//			return 10;
//		} else if (4 == orderStatus) {
//			return 20;
//		} else if (5 == orderStatus) {
//			return 60;
//		} else if (6 == orderStatus) {
//			return 130;
//		} else if (7 == orderStatus || 8 == orderStatus) {
//			return 30;
//		} else if (9 == orderStatus) {
//			return 80;
//		} else if (11 == orderStatus || 12 == orderStatus || 14 == orderStatus) {
//			return 70;
//		} else if (13 == orderStatus) {
//			return 120;
//		}
//		return 199;
//	}
//
//	// public static String post(String url, Map<String, String> params) {
//	// if (StringUtils.isBlank(url)) {
//	// return null;
//	// }
//	// CloseableHttpClient httpclient = HttpClients.createDefault();
//	// HttpPost httpPost = new HttpPost(url);
//	// CloseableHttpResponse response = null;
//	// String content = null;
//	// try {
//	// List<org.apache.http.NameValuePair> formparams = new ArrayList<org.apache.http.NameValuePair>();
//	// for (Map.Entry<String, String> entry : params.entrySet()) {
//	// formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//	// }
//	// UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
//	// httpPost.setEntity(uefEntity);
//	// httpPost.setHeader("Accept", "application/json");
//	// httpPost.setHeader("content-type", "content-type");
//	// httpPost.setHeader("accept-encoding", "gzip, deflate");
//	// // if(){
//	// // httpPost.setHeader("authorization", "Bearer
//	// //
//	// eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjdjZGIwOTJiZDRiYzQ0NjY4NjBiZjIyODVkODU4NTA2NDVhNmJmZTQxMWUwNmIxMWVkZWYyZDM0NmM0YzY4MTlkYTQyZDM5NDM0MWIzOWRjIn0");
//	// // }
//	//
//	// response = httpclient.execute(httpPost);
//	// if (response.getStatusLine().getStatusCode() == 200) {
//	// try {
//	// content = EntityUtils.toString(response.getEntity(), "UTF-8");
//	// } finally {
//	// response.close();
//	// }
//	// }
//	// } catch (ClientProtocolException e) {
//	// e.printStackTrace();
//	// } catch (IOException e) {
//	// e.printStackTrace();
//	// } finally {
//	// try {
//	// httpclient.close();
//	// } catch (IOException e) {
//	// e.printStackTrace();
//	// }
//	// }
//	// return content;
//	// }
//
//	// public static String getAccessToken() {
//	// String accessToken = "";
//	// try{
//	// String url = RongYiTuiConstant.RYT_URL + "/openapi/v1/oauth/token";
//	//
//	// }catch(Exception e){
//	//
//	// }
//	// return accessToken;
//	// }
//
//}
