/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.base;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.capital.service.CapitalKoudaiService;
import com.beadwallet.service.utils.HttpClientHelper;
import com.beadwallet.service.utils.HttpRequest;
import com.waterelephant.bd.ChannelService;
import com.waterelephant.dto.MoxieTaskDto;
import com.waterelephant.entity.BwCapitalWithhold;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.fulinfk.service.IFulinfkAppService;
import com.waterelephant.rongCarrier.entity.BwDataExternal;
import com.waterelephant.rongCarrier.entity.XgOverall;
import com.waterelephant.rongCarrier.mapper.BwDataExternalMapper;
import com.waterelephant.rongCarrier.service.XgOverallService;
import com.waterelephant.service.BwCapitalWithholdService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.sms.service.SendMessageCommonService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.ElasticSearchUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * @author 崔雄健
 * @date 2017年3月15日
 * @description
 */
@Controller
@RequestMapping("/app/test")
public class TestController {
	@Resource
	private ChannelService channelService;
	@Resource
	private BwOrderRongService bwOrderRongService;
	// @Resource
	// private MqProducer mqProducer;
	@Resource
	private BwRejectRecordService bwRejectRecordService;
	@Resource
	private XgOverallService xgOverallService;
	@Resource
	private SendMessageCommonService sendMessageCommonService;
	@Resource
	private IBwOrderService bwOrderService;
	@Resource
	private BwCapitalWithholdService bwCapitalWithholdService;
	@Resource
	private IFulinfkAppService iFulinfkAppService;
	// @Resource
	// protected CustomService customService;
	@Resource
	private BwDataExternalMapper bwDataExternalMapper;

	@RequestMapping("/test1.do")
	public void test1(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("controller:test1");
			// channelService.sendOrderStatus("12", "452", "6");

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(1231l);
			bwOrderRongService.save(bwOrderRong);
			int i = 2 / 0;
			System.out.println(3);
		} catch (Exception e) {
		}
	}

	@RequestMapping("/test2.do")
	public void test2(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("controller:test1");
			// channelService.sendOrderStatus("12", "452", "6");

			MoxieTaskDto moxieTaskDto = new MoxieTaskDto();
			moxieTaskDto.setMobile("15071361821");
			moxieTaskDto.setOrder_id("486");
			moxieTaskDto.setTask_id("fwawfaw");
			moxieTaskDto.setUser_id("109");
			moxieTaskDto.setAccount(3);
			// RedisUtils.rpush(SystemConstant.MOXIE_CARRIER_KEY, JsonUtils.toJson(moxieTaskDto));
			RedisUtils.hset(SystemConstant.MOXIE_CARRIER_TEM, "fwawfaw", JsonUtils.toJson(moxieTaskDto));

		} catch (Exception e) {
		}
	}

	@RequestMapping("/test3.do")
	public void test3(HttpServletRequest request, HttpServletResponse response) {
		try {
			String value = RedisUtils.hget(SystemConstant.MOXIE_CARRIER_TEM, "fwawfaw");
			MoxieTaskDto moxieTaskDto = JsonUtils.fromJson(value, MoxieTaskDto.class);
			// 魔蝎回调 通话记录
			RedisUtils.rpush(SystemConstant.MOXIE_CARRIER_KEY, JsonUtils.toJson(moxieTaskDto));

			RedisUtils.hdel(SystemConstant.MOXIE_CARRIER_TEM, "fwawfaw");
		} catch (Exception e) {
		}
	}

	@RequestMapping("/test4.do")
	public void test4() {
		// channelService.sendOrderStatus("12", "452", "6");
		Map map = new HashMap<>();
		map.put("channelId", "9");
		map.put("thirdOrderId", "452");
		map.put("statusId", "6");
		// mqProducer.sendMsg("hehe", JSON.toJSONString(map));
	}

	@RequestMapping("/test5.do")
	public String test5() throws Exception {
		List<Map<String, Object>> list = bwOrderRongService.btsj();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			System.out.println(i + ":" + map.get("userId") + "," + map.get("search_id"));

			Thread.sleep(1000);
			String params = "outUniqueId=1&authChannel=1&state=report&userId=" + map.get("userId") + "&search_id="
					+ map.get("search_id");
			map.put("outUniqueId", 1);
			map.put("state", "report");
			map.put("authChannel", 1);

			// Map<String, String> map1 = new HashMap<>();
			// map1.put("userId", map.get("userId") + "");
			// map1.put("search_id", map.get("search_id") + "");
			// map1.put("outUniqueId", "1");
			// map1.put("state", "report");
			// map1.put("authChannel", "1");
			System.out.println(params);
			String returnstr = HttpRequest
					.doPost("https://www.beadwallet.com/beadwalletloanapp/app/callBack/operateBackH5.do", params);
			// String returnstr = HttpRequest
			// .doGet("http://www.beadwallet.com/beadwalletloanapp/app/callBack/operateBackH5.do" + "?" + params);
			System.out.println(i + ":" + returnstr);

		}

		return "zj520";
	}

	@RequestMapping("/test7.do")
	public String test7() throws Exception {
		try {
			Long orderId = 1164502L;
			XgOverall xgOverall = xgOverallService.findXgOverall(109L);
			if (xgOverall != null && !CommUtils.isNull(xgOverall.getScore())) {
				if (DoubleUtil.sub(Double.parseDouble(xgOverall.getScore()), new Double("0.195")) > 0) {
					// updateOrder(orderId);
					// BwRejectRecord record = new BwRejectRecord();
					// record.setRejectType(1);
					// record.setRejectInfo("西瓜分C3、D类用户拒绝");
					// record.setCreateTime(now);
					// record.setSource(16); // 西瓜分
					// record.setOrderId(orderId);
					// bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
					System.out.println("西瓜分C3、D类用户拒绝");

				}
				if (xgOverall != null && "2".equals(CommUtils.toString(xgOverall.getIfCallEmergency1()))
						&& "2".equals(CommUtils.toString(xgOverall.getIfCallEmergency2()))) {
					// updateOrder(1164502);
					// BwRejectRecord record = new BwRejectRecord();
					// record.setRejectType(1);
					// record.setRejectInfo("西瓜报告未联系过紧急联系人");
					// record.setCreateTime(now);
					// record.setSource(16); // 西瓜分
					// record.setOrderId(orderId);
					// bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
					// return false;
					System.out.println("西瓜报告未联系过紧急联系人");
				}
			}
		} catch (Exception e) {
			System.out.println("----------西瓜分认证------异常---------");
		}
		return "test7";
	}

	@RequestMapping("/test6.do")
	public String test6() throws Exception {
		int i = bwRejectRecordService.findRejectRecordByPhone("15827543107");
		System.out.println("sssssssssssssss::" + i);
		return "test6";
	}

	@RequestMapping("/test8.do")
	public String test8(HttpServletRequest request, HttpServletResponse response) {
		String phones = request.getParameter("phone");
		String msg = request.getParameter("msg");
		boolean bo = sendMessageCommonService.commonSendMessage(phones, msg);
		if (bo) {
			return "success";
		}

		return "error";
	}

	@RequestMapping("/test9.do")
	public String test9(HttpServletRequest request, HttpServletResponse response) {
		String orderId = request.getParameter("orderId");
		Map map = bwOrderService.findCapitalKoudaiD(Long.parseLong(orderId));

		if (map != null) {
			BwCapitalWithhold bwCapitalWithhold = new BwCapitalWithhold();
			bwCapitalWithhold.setCapitalId(2);
			bwCapitalWithhold.setCardNo(CommUtils.toString(map.get("card_no")));
			bwCapitalWithhold.setOrderId(Long.parseLong(CommUtils.toString(map.get("id"))));
			bwCapitalWithhold.setOrderNo(CommUtils.toString(map.get("order_no")));
			bwCapitalWithhold.setCreateTime(new Date());
			bwCapitalWithhold.setPushStatus(0);
			bwCapitalWithhold.setMoney(new BigDecimal("0.01"));
			bwCapitalWithholdService.save(bwCapitalWithhold);

			map.put("push_id", map.get("order_no") + "K" + bwCapitalWithhold.getId());
			map.put("money", "0.01");
			String str = CapitalKoudaiService.withholdPush(JSON.toJSONString(map));
			System.out.println(str);

			return str;
		}

		return "error";
	}

	@RequestMapping("/test10.do")
	public String test10(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap<>();
		map.put("name", "崔雄健");
		map.put("mobile", "15071361812");
		map.put("idCardNo", "420602199110092511");
		map.put("key", "Z5IRVVtE");

		String responseString = iFulinfkAppService.saveFullinkReport(map);
		System.out.println(responseString);
		return "error";

	}

	@RequestMapping("/test11.do")
	public void test11(HttpServletRequest request, HttpServletResponse response) {
		try {
			// System.out.println("sss" + customService.findCapitalBaseNew(123111L));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/test13.do")
	public void test13(HttpServletRequest request, HttpServletResponse response) {
		try {
			// System.out.println("sss" + customService.findCapitalBaseNew(123111L));
			List<BwDataExternal> sss = bwDataExternalMapper.queryBwDataExternalByOrderId("1543825424707",
					"'jd','taobao'");
			System.out.println(sss.size() + "s#################");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/test12.do")
	public void test12(HttpServletRequest request, HttpServletResponse response) {
		String maxDate = "";
		String id = request.getParameter("id");
		Long startTime = System.currentTimeMillis();
		Long borrowerId = Long.parseLong(id);
		if (CommUtils.isNull(maxDate)) {
			Client client = null;

			client = ElasticSearchUtils.getInstance().getClient();
			// 查询数据
			// SearchRequestBuilder requestBuilder = client.prepareSearch(SystemConstant.ES_INDEX)
			// .setTypes("BwOperateVoice").setSearchType(SearchType.QUERY_AND_FETCH)
			// .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("borrower_id", borrowerId)));
			// .setScroll(TimeValue.timeValueMinutes(8)).setFrom(0).setSize(1)
			// .addSort("call_time", SortOrder.DESC);

			// SearchResponse response1 = requestBuilder.get();
			//
			// for (SearchHit hit : response1.getHits()) {
			// if (hit != null) {
			// maxDate = CommUtils.toString(hit.getSource().get("call_time"));
			// break;
			// }
			// }
			SearchRequestBuilder searchBuilder = client.prepareSearch(SystemConstant.ES_INDEX)
					.setTypes("BwOperateVoice").setSearchType(SearchType.COUNT)
					.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("borrower_id", borrowerId)))
					.setSize(0);
			SearchResponse response1 = searchBuilder.get();
			long length = response1.getHits().totalHits();

			Long endTime = System.currentTimeMillis();
			// 总耗时
			Long time1 = endTime - startTime;
			// System.out.println(maxDate);
			// System.out.println(id + "测试最大时间:"
			// + DateUtil.getDateString(new Date(Long.parseLong(maxDate)), DateUtil.yyyy_MM_dd_HHmmss) + ",总耗时："
			// + time1);
			System.out.println(id + "测试条数:" + length + ",总耗时：" + time1);

		}

	}

	public static void main(String[] args) throws Exception {
		Map map = new HashMap<>();
		map.put("name", "崔雄健");
		map.put("mobile", "15071361812");
		map.put("idCardNo", "420602199110092511");
		map.put("key", "Z5IRVVtE");

		String resp = HttpClientHelper.post("http://106.14.238.126:8094/beadwalletservice" + "/fulinfk/getReport.do",
				"utf-8", map);
		String resp1 = HttpRequest.doPost("http://106.14.238.126:8094/beadwalletservice" + "/fulinfk/getReport.do",
				"name=崔雄健&mobile=15071361812&idCardNo=420602199110092511&key=Z5IRVVtE");
		System.out.println(resp);
		System.out.println(resp1);
	}

}
