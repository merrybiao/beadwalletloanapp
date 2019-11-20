package com.waterelephant.capitalDemo.controller;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.capitalDemo.utils.AppResponseResult;
import com.waterelephant.capitalDemo.utils.TestUtils;
import com.waterelephant.capitalDemo.utils.WoNiuContext;
import com.waterelephant.capitalDemo.utils.WoNiuSignUtils;
import com.waterelephant.entity.WoNiuOrder;
import com.waterelephant.service.BwCapitalPushService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.TestWoNiuService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.service.impl.BwRepaymentPlanService;

@Controller
@RequestMapping("/capital/woniu")
public class TestWoNiuController {
	private Logger logger = Logger.getLogger(TestWoNiuController.class);

	private static String signkey = WoNiuContext.get("woNiu.signKey");		// 123456

	@Resource
	private BwOrderService bwOrderService;

	@Resource
	private BwCapitalPushService bwCapitalPushService;

	@Resource
	private IBwOrderPushInfoService bwOrderPushInfoService;

	@Resource
	private BwOrderProcessRecordService bwOrderProcessRecordService;

	@Resource
	private BwBorrowerService bwBorrowerService;

	@Resource
	private BwRepaymentPlanService bwRepaymentPlanService;

	@Resource
	private BwProductDictionaryService bwProductDictionaryService;

	@Resource
	private TestWoNiuService bwWoNiuService;

	@ResponseBody
	@RequestMapping("/testOrderPush.do")
	public AppResponseResult getOrderPush(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			String check = TestUtils.checkOrderPushData(request);

			if (StringUtils.isNotBlank(check)) {
				appResponseResult.setCode("1000");
				appResponseResult.setMsg("参数错误");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			String params = request.getParameter("params");
			Map<String, String> hm = (Map<String, String>) JSON.parse(params);
			TreeMap<String, String> tr = new TreeMap<>();
			String sign = request.getParameter("sign");
			tr.put("thirdNo", CommUtils.toString(hm.get("thirdNo")));
			tr.put("userName", CommUtils.toString(hm.get("userName")));
			tr.put("mobile", CommUtils.toString(hm.get("mobile")));
			tr.put("idCard", CommUtils.toString(hm.get("idCard")));
			tr.put("bankCard", CommUtils.toString(hm.get("bankCard")));
			tr.put("type", CommUtils.toString(hm.get("type")));
			tr.put("loanAmount", hm.get("loanAmount"));

			System.out.println("sign===============" + sign);

			boolean flag = WoNiuSignUtils.verifySign(JSON.toJSONString(tr), signkey, sign);

			if (!flag) {
				appResponseResult.setCode("1001");
				appResponseResult.setMsg("签名错误");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			WoNiuOrder woNiuOrder = JSONObject.parseObject(JSON.toJSONString(hm), WoNiuOrder.class);

			if (CommUtils.isNull(woNiuOrder)) {
				logger.info("转换水象推送信息异常");
				appResponseResult.setCode("1000");
				appResponseResult.setMsg("参数错误");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			// 下面逻辑是蜗牛对入参的操作
			
			
			

			appResponseResult.setCode("0");
			appResponseResult.setMsg("接收成功");
			appResponseResult.setResult("SUCCESS");
		} catch (Exception e) {
			appResponseResult.setCode("1002");
			appResponseResult.setMsg("接收异常");
			appResponseResult.setResult("FAIL");
			logger.error("接收水象放款信息异常", e);
		}
		return appResponseResult;
	}
}
