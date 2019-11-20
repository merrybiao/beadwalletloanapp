package com.waterelephant.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.capital.service.CapitalBaofooService;
import com.beadwallet.service.utils.HttpRequest;
import com.waterelephant.entity.BwCapitalExternalPush;
import com.waterelephant.entity.BwCapitalOrder;
import com.waterelephant.service.BwCapitalExternalPushService;
import com.waterelephant.service.BwCapitalOrderService;
import com.waterelephant.service.SystemCapitalCentersService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;

@Service
public class SystemCapitalCentersServiceImpl implements SystemCapitalCentersService {

	private Logger logger = Logger.getLogger(SystemCapitalCentersServiceImpl.class);

	@Resource
	private BwCapitalExternalPushService bwCapitalExternalPushService;
	@Resource
	private BwCapitalOrderService bwCapitalOrderService;

	@Override
	public String saveCapitalBaofoo(Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String orderNo = CommUtils.toString(map.get("orderNo"));
			BwCapitalExternalPush bwCapitalExternalPush = bwCapitalExternalPushService
					.queryBwCapitalExternalPush(orderNo, 8);
			if (bwCapitalExternalPush == null) {
				bwCapitalExternalPush = new BwCapitalExternalPush();
				bwCapitalExternalPush.setCapitalId(8);
				bwCapitalExternalPush.setOrderNo(orderNo);
				bwCapitalExternalPush.setCreateTime(new Date());
				bwCapitalExternalPush.setPushCount(1);
				bwCapitalExternalPush.setPushStatus(0);
				bwCapitalExternalPush.setRemark(CommUtils.toString(map.get("money")));
				bwCapitalExternalPushService.save(bwCapitalExternalPush);
			}
			logger.info("宝付放款请求入参：" + JSON.toJSONString(map));

			String str = CapitalBaofooService.orderPushExternal(JSON.toJSONString(map));

			JSONObject returnjson = (JSONObject) JSONObject.parse(str);
			logger.info("宝付代付--" + orderNo + "放款请求结束:" + returnjson);
			// JSONObject valueJson = (JSONObject) JSONObject.parse(value);

			JSONObject contentjson = (JSONObject) returnjson.get("trans_content");
			JSONObject headjson = (JSONObject) contentjson.get("trans_head");
			String code = CommUtils.toString(headjson.get("return_code"));
			String msg = CommUtils.toString(headjson.get("return_msg"));

			if (!"0000".equals(code)) {
				returnMap.put("code", code);
				returnMap.put("msg", msg);
				bwCapitalExternalPush.setCode(code);
				bwCapitalExternalPush.setMsg(msg);
				bwCapitalExternalPush.setUpdateTime(new Date());
				bwCapitalExternalPush.setPushCount(StringUtil.toInteger(bwCapitalExternalPush.getPushCount()) + 1);
				bwCapitalExternalPushService.updateBwCapitalExternalPush(bwCapitalExternalPush);
				return JSON.toJSONString(returnMap);
			}

			JSONArray jarr = contentjson.getJSONArray("trans_reqDatas");

			JSONObject arrJson = jarr.getJSONObject(0);

			JSONObject reqDataJson = arrJson.getJSONObject("trans_reqData");

			String batchid = CommUtils.toString(reqDataJson.get("trans_batchid"));
			String capitalOrderid = CommUtils.toString(reqDataJson.get("trans_orderid"));

			if ("0000".equals(code)) {
				bwCapitalExternalPush.setPushStatus(1);
			}

			bwCapitalExternalPush.setCode(code);
			bwCapitalExternalPush.setMsg(msg);
			bwCapitalExternalPush.setUpdateTime(new Date());
			bwCapitalExternalPush.setPushCount(StringUtil.toInteger(bwCapitalExternalPush.getPushCount()) + 1);
			bwCapitalExternalPushService.updateBwCapitalExternalPush(bwCapitalExternalPush);

			BwCapitalOrder bwCapitalOrder = bwCapitalOrderService.queryBwCapitalOrderByNo(orderNo, 8);

			if (bwCapitalOrder == null) {
				bwCapitalOrder = new BwCapitalOrder();

				bwCapitalOrder.setUpdateTime(new Date());
				bwCapitalOrder.setCapitalId(8L);
				bwCapitalOrder.setCreateTime(new Date());
				bwCapitalOrder.setType(0);
				bwCapitalOrder.setOrderNo(orderNo);
				bwCapitalOrder.setCapitalNo(batchid + "-" + capitalOrderid);
				bwCapitalOrder.setOrderStatus(code);
				bwCapitalOrder.setMessage(msg);
				bwCapitalOrderService.save(bwCapitalOrder);
			} else {
				bwCapitalOrder.setCapitalNo(batchid + "-" + capitalOrderid);
				bwCapitalOrder.setOrderStatus(code);
				bwCapitalOrder.setMessage(msg);
				bwCapitalOrderService.update(bwCapitalOrder);
			}
			returnMap.put("code", code);
			returnMap.put("msg", msg);
			returnMap.put("capitalNo", batchid + "-" + capitalOrderid);
		} catch (Exception e) {
			logger.error("推送异常" + e.getMessage());
			e.printStackTrace();
			returnMap.put("code", "9999");
			returnMap.put("msg", "请求异常");
		}
		return JSON.toJSONString(returnMap);
	}

	@Override
	public String saveCapitalBaofooBxgs(Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String orderNo = CommUtils.toString(map.get("orderNo"));
			BwCapitalExternalPush bwCapitalExternalPush = bwCapitalExternalPushService
					.queryBwCapitalExternalPush(orderNo, 8);
			if (bwCapitalExternalPush == null) {
				bwCapitalExternalPush = new BwCapitalExternalPush();
				bwCapitalExternalPush.setCapitalId(8);
				bwCapitalExternalPush.setOrderNo(orderNo);
				bwCapitalExternalPush.setCreateTime(new Date());
				bwCapitalExternalPush.setPushCount(1);
				bwCapitalExternalPush.setPushStatus(0);
				bwCapitalExternalPush.setRemark(CommUtils.toString(map.get("money")));
				bwCapitalExternalPushService.save(bwCapitalExternalPush);
			}
			logger.info("宝付放款请求入参：" + JSON.toJSONString(map));

			String str = CapitalBaofooService.orderPushFqbx(JSON.toJSONString(map));

			JSONObject returnjson = (JSONObject) JSONObject.parse(str);
			logger.info("宝付代付--" + orderNo + "放款请求结束:" + returnjson);
			// JSONObject valueJson = (JSONObject) JSONObject.parse(value);

			JSONObject contentjson = (JSONObject) returnjson.get("trans_content");
			JSONObject headjson = (JSONObject) contentjson.get("trans_head");
			String code = CommUtils.toString(headjson.get("return_code"));
			String msg = CommUtils.toString(headjson.get("return_msg"));

			if (!"0000".equals(code)) {
				returnMap.put("code", code);
				returnMap.put("msg", msg);
				bwCapitalExternalPush.setCode(code);
				bwCapitalExternalPush.setMsg(msg);
				bwCapitalExternalPush.setUpdateTime(new Date());
				bwCapitalExternalPush.setPushCount(StringUtil.toInteger(bwCapitalExternalPush.getPushCount()) + 1);
				bwCapitalExternalPushService.updateBwCapitalExternalPush(bwCapitalExternalPush);
				return JSON.toJSONString(returnMap);
			}

			JSONArray jarr = contentjson.getJSONArray("trans_reqDatas");

			JSONObject arrJson = jarr.getJSONObject(0);

			JSONObject reqDataJson = arrJson.getJSONObject("trans_reqData");

			String batchid = CommUtils.toString(reqDataJson.get("trans_batchid"));
			String capitalOrderid = CommUtils.toString(reqDataJson.get("trans_orderid"));

			if ("0000".equals(code)) {
				bwCapitalExternalPush.setPushStatus(1);
			}

			bwCapitalExternalPush.setCode(code);
			bwCapitalExternalPush.setMsg(msg);
			bwCapitalExternalPush.setUpdateTime(new Date());
			bwCapitalExternalPush.setPushCount(StringUtil.toInteger(bwCapitalExternalPush.getPushCount()) + 1);
			bwCapitalExternalPushService.updateBwCapitalExternalPush(bwCapitalExternalPush);

			BwCapitalOrder bwCapitalOrder = bwCapitalOrderService.queryBwCapitalOrderByNo(orderNo, 8);

			if (bwCapitalOrder == null) {
				bwCapitalOrder = new BwCapitalOrder();

				bwCapitalOrder.setUpdateTime(new Date());
				bwCapitalOrder.setCapitalId(8L);
				bwCapitalOrder.setCreateTime(new Date());
				bwCapitalOrder.setType(0);
				bwCapitalOrder.setOrderNo(orderNo);
				bwCapitalOrder.setCapitalNo(batchid + "-" + capitalOrderid);
				bwCapitalOrder.setOrderStatus(code);
				bwCapitalOrder.setMessage(msg);
				bwCapitalOrderService.save(bwCapitalOrder);
			} else {
				bwCapitalOrder.setCapitalNo(batchid + "-" + capitalOrderid);
				bwCapitalOrder.setOrderStatus(code);
				bwCapitalOrder.setMessage(msg);
				bwCapitalOrderService.update(bwCapitalOrder);
			}
			returnMap.put("code", code);
			returnMap.put("msg", msg);
			returnMap.put("capitalNo", batchid + "-" + capitalOrderid);
		} catch (Exception e) {
			logger.error("推送异常" + e.getMessage());
			e.printStackTrace();
			returnMap.put("code", "9999");
			returnMap.put("msg", "请求异常");
		}
		return JSON.toJSONString(returnMap);
	}

	@Override
	public String saveCapitalBaofooBjsc(Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String orderNo = CommUtils.toString(map.get("orderNo"));
			BwCapitalExternalPush bwCapitalExternalPush = bwCapitalExternalPushService
					.queryBwCapitalExternalPush(orderNo, 8);
			if (bwCapitalExternalPush == null) {
				bwCapitalExternalPush = new BwCapitalExternalPush();
				bwCapitalExternalPush.setCapitalId(8);
				bwCapitalExternalPush.setOrderNo(orderNo);
				bwCapitalExternalPush.setCreateTime(new Date());
				bwCapitalExternalPush.setPushCount(1);
				bwCapitalExternalPush.setPushStatus(0);
				bwCapitalExternalPush.setRemark(CommUtils.toString(map.get("money")));
				bwCapitalExternalPushService.save(bwCapitalExternalPush);
			}
			logger.info("宝付放款请求入参：" + JSON.toJSONString(map));

			String str = CapitalBaofooService.orderPushBjsc(JSON.toJSONString(map));

			JSONObject returnjson = (JSONObject) JSONObject.parse(str);
			logger.info("宝付代付--" + orderNo + "放款请求结束:" + returnjson);
			// JSONObject valueJson = (JSONObject) JSONObject.parse(value);

			JSONObject contentjson = (JSONObject) returnjson.get("trans_content");
			JSONObject headjson = (JSONObject) contentjson.get("trans_head");
			String code = CommUtils.toString(headjson.get("return_code"));
			String msg = CommUtils.toString(headjson.get("return_msg"));

			if (!"0000".equals(code)) {
				returnMap.put("code", code);
				returnMap.put("msg", msg);
				bwCapitalExternalPush.setCode(code);
				bwCapitalExternalPush.setMsg(msg);
				bwCapitalExternalPush.setUpdateTime(new Date());
				bwCapitalExternalPush.setPushCount(StringUtil.toInteger(bwCapitalExternalPush.getPushCount()) + 1);
				bwCapitalExternalPushService.updateBwCapitalExternalPush(bwCapitalExternalPush);
				return JSON.toJSONString(returnMap);
			}

			JSONArray jarr = contentjson.getJSONArray("trans_reqDatas");

			JSONObject arrJson = jarr.getJSONObject(0);

			JSONObject reqDataJson = arrJson.getJSONObject("trans_reqData");

			String batchid = CommUtils.toString(reqDataJson.get("trans_batchid"));
			String capitalOrderid = CommUtils.toString(reqDataJson.get("trans_orderid"));

			if ("0000".equals(code)) {
				bwCapitalExternalPush.setPushStatus(1);
			}

			bwCapitalExternalPush.setCode(code);
			bwCapitalExternalPush.setMsg(msg);
			bwCapitalExternalPush.setUpdateTime(new Date());
			bwCapitalExternalPush.setPushCount(StringUtil.toInteger(bwCapitalExternalPush.getPushCount()) + 1);
			bwCapitalExternalPushService.updateBwCapitalExternalPush(bwCapitalExternalPush);

			BwCapitalOrder bwCapitalOrder = bwCapitalOrderService.queryBwCapitalOrderByNo(orderNo, 8);

			if (bwCapitalOrder == null) {
				bwCapitalOrder = new BwCapitalOrder();

				bwCapitalOrder.setUpdateTime(new Date());
				bwCapitalOrder.setCapitalId(8L);
				bwCapitalOrder.setCreateTime(new Date());
				bwCapitalOrder.setType(0);
				bwCapitalOrder.setOrderNo(orderNo);
				bwCapitalOrder.setCapitalNo(batchid + "-" + capitalOrderid);
				bwCapitalOrder.setOrderStatus(code);
				bwCapitalOrder.setMessage(msg);
				bwCapitalOrderService.save(bwCapitalOrder);
			} else {
				bwCapitalOrder.setCapitalNo(batchid + "-" + capitalOrderid);
				bwCapitalOrder.setOrderStatus(code);
				bwCapitalOrder.setMessage(msg);
				bwCapitalOrderService.update(bwCapitalOrder);
			}
			returnMap.put("code", code);
			returnMap.put("msg", msg);
			returnMap.put("capitalNo", batchid + "-" + capitalOrderid);

		} catch (Exception e) {
			logger.error("推送异常" + e.getMessage());
			e.printStackTrace();
			returnMap.put("code", "9999");
			returnMap.put("msg", "请求异常");
		}
		return JSON.toJSONString(returnMap);
	}

	@Override
	public String updateCapitalBaofooQuery(String orderNo) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			BwCapitalOrder bwCapitalOrder = bwCapitalOrderService.queryBwCapitalOrderByNo(orderNo, 8);

			if (bwCapitalOrder == null) {
				returnMap.put("code", "10005");
				returnMap.put("msg", "无放款记录");
				return JSON.toJSONString(returnMap);
			}

			String capitalNo = bwCapitalOrder.getCapitalNo();
			Map map = new HashMap<>();
			map.put("order_no", orderNo);
			map.put("batchid", capitalNo.split("-")[0]);
			String returnStr = CapitalBaofooService.queryOrder(JSON.toJSONString(map));
			logger.info("宝付查询结果：" + returnStr);

			JSONObject returnjson = (JSONObject) JSONObject.parse(returnStr);

			JSONObject contentjson = (JSONObject) returnjson.get("trans_content");
			JSONObject headjson = (JSONObject) contentjson.get("trans_head");
			JSONArray jarr = contentjson.getJSONArray("trans_reqDatas");

			JSONObject arrJson = jarr.getJSONObject(0);

			JSONObject reqDataJson = arrJson.getJSONObject("trans_reqData");

			String state = CommUtils.toString(reqDataJson.get("state"));
			String loanDate = CommUtils.toString(reqDataJson.get("trans_endtime"));
			String msg = CommUtils.toString(reqDataJson.get("trans_remark"));

			BwCapitalExternalPush bwCapitalExternalPush = bwCapitalExternalPushService
					.queryBwCapitalExternalPush(orderNo, 8);

			if (bwCapitalExternalPush != null) { // 如果有订单，且状态为放款成功
				if ((2 == bwCapitalExternalPush.getPushStatus() && "1".equals(state))) {
					returnMap.put("code", "0000");
					returnMap.put("msg", msg);
					returnMap.put("state", state);
					returnMap.put("loanDate", loanDate);

					return JSON.toJSONString(returnMap);
				}
			} else {
				bwCapitalExternalPush = new BwCapitalExternalPush();
				bwCapitalExternalPush.setCapitalId(8);
				bwCapitalExternalPush.setOrderNo(orderNo);
				bwCapitalExternalPush.setCreateTime(new Date());
				bwCapitalExternalPush.setPushCount(1);
				bwCapitalExternalPush.setPushStatus(0);
				bwCapitalExternalPushService.save(bwCapitalExternalPush);
			}
			if ("1".equals(state)) {
				bwCapitalExternalPush.setPushStatus(2);
			} else {
				bwCapitalExternalPush.setPushStatus(3);// 失败
			}

			returnMap.put("code", "0000");
			returnMap.put("msg", msg);
			returnMap.put("state", state);
			returnMap.put("loanDate", loanDate);

			if (!CommUtils.isNull(msg)) {
				bwCapitalExternalPush.setMsg(msg);
			}

			bwCapitalExternalPush.setUpdateTime(new Date());
			bwCapitalExternalPushService.updateBwCapitalExternalPush(bwCapitalExternalPush);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", "9999");
			returnMap.put("msg", "请求异常");
		}
		return JSON.toJSONString(returnMap);
	}

	@Override
	public String updateCapitalBaofooNotity(String orderNo, String state, String loanDate, String msg) {
		try {
			BwCapitalExternalPush bwCapitalExternalPush = bwCapitalExternalPushService
					.queryBwCapitalExternalPush(orderNo, 8);
			if ("1".equals(state)) {
				bwCapitalExternalPush.setPushStatus(2);
			} else {
				bwCapitalExternalPush.setPushStatus(3);// 失败
			}
			bwCapitalExternalPush.setMsg(msg);
			bwCapitalExternalPush.setUpdateTime(new Date());
			bwCapitalExternalPushService.updateBwCapitalExternalPush(bwCapitalExternalPush);

			String returnKey = orderNo.substring(0, orderNo.indexOf("_"));
			String orderEno = orderNo.substring(orderNo.indexOf("_") + 1, orderNo.length());

			String returnUrl = RedisUtils.hget("external:return", returnKey);

			if (!CommUtils.isNull(returnUrl)) {
				Map map = new HashMap<>();
				// 北京放款工单
				map.put("orderNo", orderEno);
				map.put("state", state);
				map.put("code", "0000");
				map.put("msg", msg);
				map.put("loanDate", loanDate);
				HttpRequest.doPostByJson(returnUrl, JSON.toJSONString(map));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "OK";
	}

	public static void main(String[] args) throws Exception {
		Map map = new HashMap<>();
		// 北京放款工单
		map.put("orderNo", "sxyp2018071914266666");
		map.put("state", "1");
		map.put("code", "0000");
		map.put("msg", "");
		map.put("loanDate", "2018-07-19 12:12:12");
		System.out.println(JSON.toJSONString(map));

		String str = HttpRequest.doPostByJson("http://106.14.242.226:8080/coreservice/loan/pushCapitalCalbak",
				JSON.toJSONString(map));
		// String str = HttpRequest.doPostByJson("http://47.100.14.112:8608/loan_job_saas_fangkuan/baofoo/comeback.do",
		// JSON.toJSONString(map));
		// http: // 47.100.14.112:8608/loan_job_saas_fangkuan/baofoo/comeback.do
		System.out.println(str);
	}

}
