//package com.waterelephant.kouDai.controller;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.beadwallet.service.kouDai.service.KouDaiServiceSDK;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.kouDai.service.KouDaiService;
//import com.waterelephant.utils.AppResponseResult;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.zhengxin91.entity.ZxCeshi;
//import com.waterelephant.zhengxin91.service.ZxCeshiService;
//
///**
// * 访问Service服务器获取口袋征信数据
// *
// * @author GuoKun
// * @version 1.0
// * @create_date 2017/4/28 15:24
// */
//@Controller
//public class KouDaiController {
//
//	private Logger logger = Logger.getLogger(KouDaiController.class);
//
//	@Autowired
//	private KouDaiService kouDaiService;
//
//	/**
//	 * 获取口袋黑名单
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("app/koudai/queryBlack.do")
//	public AppResponseResult queryBlack(HttpServletRequest request, HttpServletResponse response) {
//		AppResponseResult result = new AppResponseResult();
//		String name = request.getParameter("realName");
//		String id_card = request.getParameter("idCard");
//		String mobile = request.getParameter("mobile");
//		String borrow_id = request.getParameter("borrowId");
//		String order_id = request.getParameter("orderId");
//		// 参数非空和身份证判断
//
//		if (CommUtils.isNull(order_id)) {
//			result.setCode("1004");
//			result.setMsg("订单ID为空");
//			return result;
//		}
//		if (CommUtils.isNull(mobile)) {
//			result.setCode("1004");
//			result.setMsg("订单ID为空");
//			return result;
//		}
//		if (CommUtils.isNull(borrow_id)) {
//			result.setCode("1005");
//			result.setMsg("借款人ID为空");
//			return result;
//		}
//		if (CommUtils.isNull(name)) {
//			result.setCode("1001");
//			result.setMsg("姓名为空");
//			return result;
//		}
//		if (CommUtils.isNull(id_card)) {
//			result.setCode("1002");
//			result.setMsg("身份证号码为空");
//			return result;
//		}
//
//		Map map = new HashMap();
//		map.put("name", name);
//		map.put("id_card", id_card);
//		map.put("mobile", mobile);
//		kouDaiService.queryBlack(map);
//
//		return null;
//	}
//
//	@Autowired
//	private ZxCeshiService zxCeshiService;
//
//	@RequestMapping("/app/koudai/cesss.do")
//	public void cesss(String begin, String end) {
//		// List<ZxCeshi> zxCeshiList = null;
//		// try {
//		// zxCeshiList = zxCeshiService.findByBorrowId(begin, end);
//		// } catch (Exception e) {
//		// e.printStackTrace();
//		// }
//		// int i = 0;
//		// StringBuilder s = new StringBuilder();
//		// if(zxCeshiList!= null) {
//		// for (ZxCeshi zxCeshi : zxCeshiList) {
//		// Map<String, String> reqMap = new HashMap<>();
//		// reqMap.put("name", zxCeshi.getName());
//		// reqMap.put("id_card", zxCeshi.getCardId());
//		// reqMap.put("mobile", zxCeshi.getPhone() + "");
//		// Response response = kouDaiService.queryBlack(reqMap);
//		// if("true".equals(response.getObj()+"")) {
//		// s.append(" 姓名："+zxCeshi.getName()+", 身份证：" + zxCeshi.getCardId() + ", 手机号码：" + zxCeshi.getPhone());
//		// i++;
//		// }
//		// }
//		// }
//		// System.out.println(i);
//		// System.out.println(s);
//		// }
//
//		List<ZxCeshi> zxCeshiList = null;
//		try {
//			zxCeshiList = zxCeshiService.findByBorrowId(begin, end);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		int i = 0;
//		StringBuilder s = new StringBuilder();
//
//		logger.info("----------口袋征信黑名单------开始---------");
//		for (ZxCeshi zxCeshi : zxCeshiList) {
//			BwBorrower bwBorrower = new BwBorrower();
//			bwBorrower.setName(zxCeshi.getName());
//			bwBorrower.setIdCard(zxCeshi.getCardId());
//			bwBorrower.setPhone(zxCeshi.getPhone());
//			boolean b = kouDaiCheckService(bwBorrower);
//			logger.info("----------口袋征信黑名单------结束---------");
//			System.out.println("~~~~~~!!!!!!!!!!!!!!!!!!!!!!!  姓名：" + bwBorrower.getName() + "   身份证："
//					+ bwBorrower.getIdCard() + "  电话：" + bwBorrower.getPhone() + "  结果：" + b);
//		}
//	}
//
//	private boolean kouDaiCheckService(BwBorrower borrower) {
//		logger.info("----------口袋征信黑名单------开始---------");
//		try {
//			boolean b = false;
//			String token = RedisUtils.get("koudai");
//			if (CommUtils.isNull(token)) {
//				logger.info(" ~~~~~~~~~~~~~~~~~~~~~~ 口袋征信获取token开始");
//				token = KouDaiServiceSDK.queryToken();
//				RedisUtils.setex("koudai", token, 82800);
//				b = kouDaiBlack(borrower, token);
//				if (b) {
//					return false;
//				}
//
//			} else {
//				b = kouDaiBlack(borrower, token);
//				if (b) {
//					return false;
//				}
//			}
//		} catch (Exception e) {
//			logger.error(e.toString());
//		}
//		logger.info("----------口袋征信黑名单------结束---------");
//		return true;
//		// **************口袋征信黑名单查询结束**************
//	}
//
//	/**
//	 * 口袋黑名单查询 命中返回true
//	 *
//	 * @param borrower
//	 * @param token
//	 * @return
//	 * @throws Exception
//	 */
//	private boolean kouDaiBlack(BwBorrower borrower, String token) throws Exception {
//		boolean is = false;
//		Map reqMap = new HashMap();
//		reqMap.put("name", borrower.getName());
//		reqMap.put("id_card", borrower.getIdCard());
//		reqMap.put("mobile", borrower.getPhone() + "");
//		reqMap.put("token", token);
//		logger.info(" ~~~~~~~~~~~~~~~~~~~~~~ 口袋征信获取黑名单开始: id_card: " + reqMap.get("id_card") + " token: " + token);
//		String responseBlack = KouDaiServiceSDK.queryBlack(reqMap);
//		if ("true".equals(responseBlack)) {
//			return true;
//		}
//		return is;
//	}
//}
