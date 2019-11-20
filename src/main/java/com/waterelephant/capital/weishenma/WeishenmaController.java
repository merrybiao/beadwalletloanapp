package com.waterelephant.capital.weishenma;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.capital.service.CapitalWeishenmaService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwCapitalOrder;
import com.waterelephant.entity.BwCapitalPush;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderPushInfo;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.WsmOrderPush;
import com.waterelephant.service.BwCapitalOrderService;
import com.waterelephant.service.BwCapitalPushService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IWsmOrderPushService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.MD5Util;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;


@Controller
@RequestMapping("/weishenma")
public class WeishenmaController {
	 private Logger logger = Logger.getLogger(WeishenmaController.class);
	 @Autowired
	 private IBwOrderService bwOrderService;
	 @Autowired
	 private BwCapitalPushService bwCapitalPushService;
	 @Autowired
	 private IBwOrderPushInfoService bwOrderPushInfoService;
	 @Autowired
	 private IBwRepaymentPlanService bwRepaymentPlanService;
	 @Autowired
	 private IWsmOrderPushService wsmOrderPushService;
	 @Autowired
	 private BwProductDictionaryService bwProductDictionaryService;
	 @Autowired
	 private BwCapitalOrderService bwCapitalOrderService;
	 @Autowired
	 private BwOrderProcessRecordService bwOrderProcessRecordService;
	 @Autowired
	 private IBwBorrowerService bwBorrowerService;
	 @Autowired
	 private BwOverdueRecordService bwOverdueRecordService;
	/**
	 * 微神马放款状态回调
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/loanAdvicePush.do")
	public void loanAdvice(HttpServletRequest request, HttpServletResponse response){
		try {
			Date now = new Date();
			logger.info("进入请求");
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("请求参数"+params);
			//将其转为Map
			@SuppressWarnings("unchecked")
			Map<String, String> map = JSON.parseObject(params, Map.class);
			if(!CommUtils.isNull(map)){
				response.getWriter().append("success");
				String state= map.get("state");
				logger.info("返回的状态为放款状态为："+state);
				if("success".equals(state)){
					//放款成功,修改4个表的信息
					//1.修改wsm_order_push表
					String shddh = map.get("shddh");
					logger.info("商户订单号为："+shddh);
					String payTime = map.get("pay_time");
					Date loanTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(payTime);
					logger.info("放款时间为："+payTime);
					String contractLink = map.get("contract_link");
					logger.info("合同链接"+contractLink);
					String bankRate = map.get("bank_rate");
					logger.info("银行利率："+bankRate);
					String bank = map.get("bank");
					logger.info("放款银行名："+bank);
					String serviceCost = map.get("service_cost");
					logger.info("服务费："+serviceCost);
					String totalInterest = map.get("total_interest");
					logger.info("总利息："+totalInterest);
					//查询到原有的订单
					WsmOrderPush wsmOrderPush = wsmOrderPushService.findWsmOrderByWsmOrderNo(shddh);
					String orderId = "";
					if(!CommUtils.isNull(wsmOrderPush)){
						orderId = wsmOrderPush.getOrderId();
						if("1".equals(wsmOrderPush.getStatus())){
							logger.info("微神马重复推送此订单："+orderId);
							return ;
						}
						wsmOrderPush.setCode("0000");
						wsmOrderPush.setContractLink(contractLink);
						wsmOrderPush.setMsg("放款成功");
						wsmOrderPush.setPaymentTime(payTime);
						wsmOrderPush.setUpdateTime(now);
						wsmOrderPush.setStatus("1");
						wsmOrderPush.setBank(bank);
						wsmOrderPush.setBankRate(Double.valueOf(bankRate));
						wsmOrderPush.setServiceCost(Double.valueOf(serviceCost));
						wsmOrderPush.setTotalInterest(Double.valueOf(totalInterest));
						wsmOrderPushService.updateWsmOrderSelective(wsmOrderPush);
					}
					//更新capitalOrder表
				
					
					BwOrder bwOrder = bwOrderService.findBwOrderById(orderId); // 查询订单
					
					BwCapitalOrder bwCapitalOrder = bwCapitalOrderService.queryBwCapitalOrder(shddh, WeiShenMaConstant.CAPITALID);
					if(!CommUtils.isNull(bwCapitalOrder)){
						bwCapitalOrder.setOrderStatus("900");//放款成功
						bwCapitalOrder.setUpdateTime(now);
						bwCapitalOrderService.updateBwCapitalOrder(1, "900", "放款成功", bwOrder.getOrderNo());
					}
					
					BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(Long.valueOf(orderId), WeiShenMaConstant.CAPITALID); // 查询渠道为微神马的对应工单
					if (bwCapitalPush != null) { // 如果有订单，且状态为放款成功
						if (2 == bwCapitalPush.getPushStatus()) {
							logger.info("微神马推送处理结果：重复推送，当前工单号：" + orderId);
							return ;
						}
					} else { // 如果没有订单，创建订单
						bwCapitalPush = new BwCapitalPush();
						bwCapitalPush.setOrderId(Long.valueOf(orderId));
						bwCapitalPush.setCreateTime(now);
						bwCapitalPush.setCapitalId(WeiShenMaConstant.CAPITALID); // 渠道设置为微神马
						bwCapitalPush.setPushCount(0); // 发送次数
					}

					if (CommUtils.isNull(bwCapitalPush.getId())) {
						bwCapitalPushService.save(bwCapitalPush); // 保存资方订单信息
					}
					bwCapitalPush.setCode("0000"); // 获取状态码
					bwCapitalPush.setMsg("放款成功"); // 响应结果
					bwCapitalPush.setUpdateTime(now);

					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					//更新order_push_info 表
					BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(Long.valueOf(orderId),WeiShenMaConstant.CAPITALID);
					if (bwOrderPushInfo != null) {
						bwOrderPushInfo.setFullTime(loanTime);
						bwOrderPushInfo.setPushStatus(2); // 状态。0：推送失败，1：推送成功，2：接收成功，3：接收失败
						bwOrderPushInfo.setPushRemark("接收成功");
						bwOrderPushInfo.setUpdateTime(now);
						bwOrderPushInfo.setLoanTime(loanTime);//放款时间
						bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
					}
					//生成还款计划表
					List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
							.listBwRepaymentPlanByOrderId(Long.valueOf(orderId));

					if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
						
						logger.info("微神马推送处理结果：重复推送，当前工单号：" + orderId);
						return ;
					}
					// 还款时间
					Date repaymentTime = null;
					if (2 == bwOrder.getProductType()) {
						bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, payTime);
					} else {
						BwProductDictionary bwProductDictionary = bwProductDictionaryService
								.findBwProductDictionaryById(bwOrder.getProductId());

						String termType = null;
						String term = null;

						// 产品表
						if (bwProductDictionary != null) {
							termType = bwProductDictionary.getpTermType();
							term = bwProductDictionary.getpTerm();
						}

						if ("1".equals(termType)) {
							repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
						} else if ("2".equals(termType)) {
							repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
						} else {
							repaymentTime = MyDateUtils.addDays(loanTime, 30);
						}
						// 根据工单信息和指定还款时间生成借款人还款计划
						bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
								bwProductDictionary);
					}
					// 更新工单处理记录
					bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(Long.valueOf(orderId), loanTime);
					
					//放款通知和短信提醒
					try {
						// 渠道放款成功 - 通知
						String channel = String.valueOf(bwOrder.getChannel());
						HashMap<String, String> hm = new HashMap<>();
						hm.put("channelId", channel);
						hm.put("orderId", String.valueOf(bwOrder.getId()));
						hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
						hm.put("result", "放款成功");
						String hmData = JSON.toJSONString(hm);
						RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);

						// 通知短信
						Long borrowerId = bwOrder.getBorrowerId();
						BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
						String sex = bwBorrower.getSex() + "";
						String userName = bwBorrower.getName();

						MessageDto messageDto = new MessageDto();
						messageDto.setBusinessScenario("1"); // 营销短信
						messageDto.setPhone(bwBorrower.getPhone());
						messageDto.setType("1"); // 文字短信
						if ("0".equals(sex)) {
							messageDto.setMsg("【水象分期】尊敬的" + userName + "女士，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						} else {
							messageDto.setMsg("【水象分期】尊敬的" + userName + "先生，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						}

						RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					//通知状态不为success
					String shddh = map.get("shddh");//商户订单号
					String errorcode = map.get("errorcode");//错误代码
					String errorMsg = WsmErrorMsg.map.get(errorcode);//错误信息
					//修改状态BwCapitalPush wsmOrderPush状态
					WsmOrderPush wsmOrderPush = wsmOrderPushService.findWsmOrderByWsmOrderNo(shddh);
					String orderId = "";
					if(!CommUtils.isNull(wsmOrderPush)){
						orderId = wsmOrderPush.getOrderId();
						wsmOrderPush.setStatus("0");//放款失败
						wsmOrderPush.setCode(errorcode);
						wsmOrderPush.setMsg(errorMsg);
						wsmOrderPushService.updateWsmOrderSelective(wsmOrderPush);
						
					}
					BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(Long.valueOf(orderId), WeiShenMaConstant.CAPITALID); // 查询渠道为微神马的对应工单
					if(!CommUtils.isNull(bwCapitalPush)){
						Integer pushCount = bwCapitalPush.getPushCount();
						if(2 != bwCapitalPush.getPushStatus()){
							bwCapitalPush.setCode(errorcode);
							bwCapitalPush.setMsg(errorMsg);
							bwCapitalPush.setPushCount(pushCount+1);
							bwCapitalPush.setUpdateTime(now);
							bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
						}
					}
				}
			}
//			response.getWriter().append("success");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 订单状态查询主要适用于，长时间未收到异步通知的订单
	 * 这个地方的入参应该是我方的定单号，然后在capitalOrder表中去查询推送的订单的三方订单号
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loanOrderQuery.do")
	public AppResponseResult  loanOrderQuery(HttpServletRequest request, HttpServletResponse response){
		AppResponseResult result = new AppResponseResult();
		try {
			String params = request.getParameter("params");
			if(CommUtils.isNull(params)){
				result.setCode("110");
				result.setMsg("请求参数为空");
				logger.info("微神马请求参数为空");
				return result;
			}
			JSONArray array = JSONArray.parseArray(params);
			logger.info("查询的订单号为："+JSON.toJSONString(array));
			//将我方订单号转化为微神马商户号（查询capitalOrder表）
			JSONArray shddhList = new JSONArray();
			for (Object object : array) {
				String orderId = CommUtils.toString(object);
				Map map = bwCapitalOrderService.queryBwCapitalOrderOrderId(orderId,WeiShenMaConstant.CAPITALID);
				if(CommUtils.isNull(map)){
					logger.info("订单号为："+orderId+"的商户订单不存在");
					continue;
				}
				String shddh = CommUtils.toString(map.get("capital_no"));
				logger.info("orderId为："+orderId+"对应的商户订单号："+shddh);
				shddhList.add(shddh);
			}
			
			String shddhParams = shddhList.toString();
		
			String data = CapitalWeishenmaService.queryLoanOrderState(shddhParams);
			if(CommUtils.isNull(data)){
				result.setCode("100");
				result.setMsg("查询异常");
				logger.info("调用微神马查询返回结果为空");
				return result;
			}
			result.setCode("200");
			result.setMsg("成功");
			result.setResult(data);
			JSONArray orderQueryList = JSONArray.parseArray(data);
			if(orderQueryList.size()>0){
				for(int i=0;i<orderQueryList.size();i++){
					JSONObject json = orderQueryList.getJSONObject(i);
					 String state= CommUtils.toString(json.getString("state"));
					 String shddh = CommUtils.toString(json.getString("shddh"));
					 //根据商户订单号查询CapitalOrder
					 BwCapitalOrder capitalOrder = bwCapitalOrderService.queryBwCapitalOrder(shddh,WeiShenMaConstant.CAPITALID);
					 Long orderId = capitalOrder.getOrderId();
					 //查询相应的订单
					 BwOrder bwOrder = bwOrderService.findBwOrderById(orderId+"");
					 if(CommUtils.isNull(bwOrder)){
						 logger.info("订单号为："+orderId+"的订单不存在");
						 continue;
					 }
					 WsmOrderPush shenMaOrder = wsmOrderPushService.findWsmOrderByWsmOrderNo(shddh);
					 if(CommUtils.isNull(shenMaOrder)){
						 shenMaOrder = new WsmOrderPush();
						 shenMaOrder.setOrderId(orderId.toString());
						 shenMaOrder.setWsmOrderNo(shddh);
						 shenMaOrder.setCreateTime(new Date());
						 shenMaOrder.setStatus("-2");//刚创建，状态为-2
						 wsmOrderPushService.addWsmOrder(shenMaOrder);
					 }
					 //查询capitalPush
					 BwCapitalPush bwCapitalPush  = bwCapitalPushService.queryBwCapitalPush(orderId, WeiShenMaConstant.CAPITALID);
					 if (bwCapitalPush != null) {
							if ((2 == bwCapitalPush.getPushStatus())) {
								logger.info("该订单状态已经为放款成功状态" + orderId);
							}
					 }else{
							bwCapitalPush = new BwCapitalPush();
							bwCapitalPush.setOrderId(orderId);
							bwCapitalPush.setCreateTime(new Date());
							bwCapitalPush.setCapitalId(WeiShenMaConstant.CAPITALID);
							bwCapitalPush.setPushCount(1);
							bwCapitalPush.setPushStatus(-2);//刚创建，状态为-2
							bwCapitalPushService.save(bwCapitalPush);
					 }
					 //根据返回来的订单状态做状态更改
					 if("success".equals(state)){
						 String loanDate = StringUtil.toString(json.get("pay_time"));
						 Date loanTime = null;
						 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						 try {
								loanDate = DateUtil.getDateString(CommUtils.convertStringToDate(loanDate, "yyyyMMdd"),
										"yyyy-MM-dd");
								loanTime = format.parse(loanDate);

							} catch (Exception e) {
								logger.error("还款时间异常：" + orderId, e);
								continue;
							}
						 //表示放款成功
						 //查询wsmOrderPush,更新状态信息
						 if(!"1".equals(shenMaOrder.getStatus())){
							 //如果原来保存的状态不是成功状态则更新状态，如果是放款成功状态则不变
							 shenMaOrder.setCode("200");
							 shenMaOrder.setMsg("成功");
							 shenMaOrder.setStatus("1");
							 shenMaOrder.setPaymentTime(json.getString("pay_time"));
							 shenMaOrder.setUpdateTime(new Date());
							 shenMaOrder.setContractLink(json.getString("contract_link"));
							 shenMaOrder.setBank(json.getString("bank"));
							 shenMaOrder.setBankRate(Double.valueOf(json.getString("bankRate")));
							 shenMaOrder.setServiceCost(Double.valueOf(json.getString("serviceCost")));
							 shenMaOrder.setTotalInterest(Double.valueOf(json.getString("totalInterest")));
							 wsmOrderPushService.updateWsmOrderSelective(shenMaOrder);
						 }
						 //查看CapitalPush表状态
						 if(2!=bwCapitalPush.getPushStatus()){
							 bwCapitalPush.setCode("0000");
							 bwCapitalPush.setMsg("放款成功");
							 bwCapitalPush.setPushStatus(2);
							 bwCapitalPush.setUpdateTime(new Date());
							 bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
						 }
						 bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(orderId, loanTime);
						//查询相应的还款计划
						 try {
							 List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(orderId);
							 if(CommUtils.isNull(bwRepaymentPlanList)||bwRepaymentPlanList.size()==0){
								 // 还款时间
								 Date repaymentTime = null;
								 if (2 == bwOrder.getProductType()) {
									 bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate); // 保存还款计划
									 
								 } else {
									 BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(bwOrder.getProductId());
									 String termType = null;
									 String term = null;
									 if (bwProductDictionary != null) {
										 termType = bwProductDictionary.getpTermType();
										 term = bwProductDictionary.getpTerm();
									 }
									 if ("1".equals(termType)) {
										 repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
									 } else if ("2".equals(termType)) {
										 repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
									 } else {
										 repaymentTime = MyDateUtils.addDays(loanTime, 30);
									 }
									 bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,bwProductDictionary);
								 }
								 
							 }
						} catch (Exception e) {
							logger.error("订单号为："+orderId+"生成还款计划异常"+e);
						}
						 
						//放款通知和短信提醒
							try {
								// 渠道放款成功 - 通知
								String channel = String.valueOf(bwOrder.getChannel());
								HashMap<String, String> hm = new HashMap<>();
								hm.put("channelId", channel);
								hm.put("orderId", String.valueOf(bwOrder.getId()));
								hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
								hm.put("result", "放款成功");
								String hmData = JSON.toJSONString(hm);
								RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);

								// 通知短信
								Long borrowerId = bwOrder.getBorrowerId();
								BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
								String sex = bwBorrower.getSex() + "";
								String userName = bwBorrower.getName();

								MessageDto messageDto = new MessageDto();
								messageDto.setBusinessScenario("1"); // 营销短信
								messageDto.setPhone(bwBorrower.getPhone());
								messageDto.setType("1"); // 文字短信
								if ("0".equals(sex)) {
									messageDto.setMsg("【水象分期】尊敬的" + userName + "女士，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
								} else {
									messageDto.setMsg("【水象分期】尊敬的" + userName + "先生，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
								}

								RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
							} catch (Exception e) {
								logger.info("放款通知和短信提醒异常");
								e.printStackTrace();
							}
				 
					 }else{
						 //表示放款失败
						 String errorcode = CommUtils.toString(json.getString("errorcode"));
						 if(!"1".equals(shenMaOrder.getStatus())){
							 //如果原来保存的状态不是成功状态则更新状态，如果是放款成功状态则不变
							 shenMaOrder.setCode(errorcode);
							 shenMaOrder.setMsg(WsmErrorMsg.map.get(errorcode));
							 shenMaOrder.setStatus("0");
							 shenMaOrder.setUpdateTime(new Date());
							 wsmOrderPushService.updateWsmOrderSelective(shenMaOrder);
						 }
						 //查看CapitalPush表状态
						 if(2!=bwCapitalPush.getPushStatus()){
							 bwCapitalPush.setCode(errorcode);
							 bwCapitalPush.setMsg(WsmErrorMsg.map.get(errorcode));
							 bwCapitalPush.setPushStatus(3);
							 bwCapitalPush.setUpdateTime(new Date());
							 bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
							 logger.info("订单号为："+orderId+"放款失败");
						 }
						 
					 }
				}
			}
		} catch (Exception e) {
			logger.info("系统异常"+e);
		}
		return result;
	}
	
	//贷后接口
	
	
	
	
	/**
	 * 
	 * 贷后查询订单状态接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/afterLoanQuery.do")
	public AppResponseResult afterLoanQuery(HttpServletRequest request, HttpServletResponse response){
		AppResponseResult result = new AppResponseResult();
		try {
			logger.info("进入请求");
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("请求参数"+params);
			if(CommUtils.isNull(params)){
				result.setCode("110");
				result.setMsg("微神马请求参数为空");
				logger.info("微神马请求参数为空");
				return result;
			}
			//将请求转为WeishenmaRequest对象
			WeishenmaRequest weishenmaRequest = JSON.parseObject(params, WeishenmaRequest.class);
			if(CommUtils.isNull(weishenmaRequest)){
				result.setCode("120");
				result.setMsg("微神马请求参数格式不正确");
				logger.info("微神马请求参数格式不正确");
				return result;
			}
			String sign = weishenmaRequest.getSign();
			String timestamp = weishenmaRequest.getTimestamp();
			List<String> list = weishenmaRequest.getOrderNo();
			if(CommUtils.isNull(sign)){
				result.setCode("110");
				result.setMsg("微神马请求参数sign为空");
				logger.info("微神马请求参数sign为空");
				return result;
			}
			if(CommUtils.isNull(list)){
				result.setCode("110");
				result.setMsg("微神马请求参数orderNo为空");
				logger.info("微神马请求参数orderNo为空");
				return result;
			}
			if(CommUtils.isNull(timestamp)){
				result.setCode("110");
				result.setMsg("微神马请求参数timestamp为空");
				logger.info("微神马请求参数timestamp为空");
				return result;
			}
			//验签
			StringBuffer sb = new StringBuffer();
			sb.append(WeiShenMaConstant.SIGNKEY);
			for (String string : list) {
				sb.append(string);	
			}
			String original = sb.append(timestamp).toString();
			String ciphertext = MD5Util.md5(original);
			if(!sign.equals(ciphertext)){
				result.setCode("100");
				result.setMsg("MD5验签不通过");
				logger.info("微神马请求参数MD5验签不通过");
				return result;
			}
			//验签通过后（查询capitalOrder表，根据capital_no和capital_id查询）
			
			try {
				List<WeishenmaResponse> weishenmaResponseList = new ArrayList<>();
				BwOrder bwOrder = new BwOrder();
				for (String shddh : list) {
					WeishenmaResponse weishenmaResponse = new WeishenmaResponse();
					List<WsmSchedules> wsmSchedulesList = new ArrayList<>();
					BwCapitalOrder capitalOrder= bwCapitalOrderService.queryBwCapitalOrder(shddh, WeiShenMaConstant.CAPITALID);
					weishenmaResponse.setShddh(shddh);
					
					if(CommUtils.isNull(capitalOrder)){
						weishenmaResponse.setLoanSuccess(0);
						logger.info("微神马订单号对应的capitalOrder不存在：微神马订单号"+shddh);
						continue;
					}
					Long orderId= capitalOrder.getOrderId();
					 //获得我方订单Id,开始查询我方订单的还款状态
					bwOrder = bwOrderService.findBwOrderById(orderId+""); // 查询订单
					if(CommUtils.isNull(bwOrder)){
						weishenmaResponse.setLoanSuccess(0);
						logger.info("微神马订单号对应的bwOrder不存在：我方订单Id为"+orderId);
						continue;
					}
					//根据订单状态：如果订单状态不为6：结束，9：还款中，13：逾期 则continue
					Long statusId = bwOrder.getStatusId();
					//做个非空，避免报空指针，虽然不可能为空
					if(CommUtils.isNull(statusId)){
						weishenmaResponse.setLoanSuccess(0);
						logger.info("微神马订单号对应的bwOrder的订单状态为空：我方订单号"+orderId);
						continue;
					}
					if(6!=statusId&&9!=statusId&&13!=statusId){
						weishenmaResponse.setLoanSuccess(0);
						logger.info("微神马订单号对应的bwOrder的订单状态不为结束，还款中，逾期：我方订单号"+orderId);
						continue;
					}
					//放款成功
					weishenmaResponse.setLoanSuccess(1);
					//根据order获得用户基本信息
					Long borrowerId = bwOrder.getBorrowerId();
					BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
					if(!CommUtils.isNull(bwBorrower)){
						weishenmaResponse.setUserName(bwBorrower.getName());
						weishenmaResponse.setIdCard(bwBorrower.getIdCard());
						logger.info("借款人姓名："+bwBorrower.getName()+"借款人身份证号："+bwBorrower.getIdCard());
					}
					//获得产品类型 protect_type 1：单期，2：分期
					Integer productType = bwOrder.getProductType();
					logger.info("订单类型为："+productType);
					//已还款且结清
					if(6==statusId){
						logger.info("订单状态为已还清"+orderId);
						Integer repayStatus = 2;
						if(1==productType){
							//单期
							weishenmaResponse.setPeriodTotal(1);//总分期
							weishenmaResponse.setAmount(bwOrder.getBorrowAmount());//应还本金
							
							WsmSchedules wsmSchedules = new WsmSchedules();
							wsmSchedules.setPeriodStage(1);//为一期
							wsmSchedules.setInterest(0.0);//应还利息
							wsmSchedules.setRepaidInterest(0.0);//已还利息
							
							//查询还款计划表repayStatus为2表示已还款且结清
							BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.findRepaymentPlanByOrderIdAndRepayStatus(orderId, repayStatus);
							logger.info("查询到还款计划："+JSON.toJSONString(bwRepaymentPlan));
							if(!CommUtils.isNull(bwRepaymentPlan)){
								Date repayTime = bwRepaymentPlan.getRepayTime();//应还款时间
								Date updateTime = bwRepaymentPlan.getUpdateTime();//实际还款时间
								//将date转为指定格式的字符串
								String dueAt = DateUtil.getDateString(repayTime,"yyyy-MM-dd");
								String repayAtPartial = DateUtil.getDateString(updateTime,"yyyy-MM-dd");
								wsmSchedules.setDueAt(dueAt);//应还款时间
								wsmSchedules.setRepayAtPartial(repayAtPartial);//实际还款时间
								wsmSchedules.setRepayAt(repayAtPartial);//全部结清时间
								
								weishenmaResponse.setLastRepayAt(repayAtPartial);//最近还款日期
								
								logger.info("最近还款日期："+repayAtPartial);
								//还款金额
								Double principal = bwRepaymentPlan.getRepayCorpusMoney(); 
								wsmSchedules.setPrincipal(principal);//应还本金
								wsmSchedules.setRepaiCapital(principal);//已还本金
								wsmSchedules.setStatus(3);//设置还款状态（1.还款中 2.本息已完清 罚息未还清 3.全部已还清 ）
								Long repayId = bwRepaymentPlan.getId();
								//判断是否有逾期记录，若有获取已还罚息和逾期天数
								BwOverdueRecord bwOverdueRecord = bwOverdueRecordService.queryBwOverdueByRepayId(repayId);
								
								if(CommUtils.isNull(bwOverdueRecord)){
									wsmSchedules.setOverdues(0);//逾期天数
									wsmSchedules.setRepaidPenalty(0.0);
									logger.info("该笔订单没有逾期记录");
								}else{
									wsmSchedules.setOverdues(bwOverdueRecord.getOverdueDay());//逾期天数
									logger.info("逾期天数为："+bwOverdueRecord.getOverdueDay());
									if(2==bwOverdueRecord.getOverdueStatus()){
										wsmSchedules.setRepaidPenalty(bwOverdueRecord.getOverdueAccrualMoney()-bwOverdueRecord.getAdvance());//已还罚息金额
									}else{
										wsmSchedules.setRepaidPenalty(0.0);
									}
								}
							}
							wsmSchedulesList.add(wsmSchedules);
							weishenmaResponse.setSchedules(wsmSchedulesList);
						}
						if(2==productType){
							//分期产品，获取分期的期数
							Integer periodTotal = bwOrder.getBorrowNumber();//分期数
							weishenmaResponse.setPeriodTotal(periodTotal);//总分期
							weishenmaResponse.setAmount(bwOrder.getBorrowAmount());//应还本金
							//查询还款计划表
							 List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService.findRepaymentPlanListByOrderIdAndRepayStatus(orderId, repayStatus);
							if(!CommUtils.isNull(bwRepaymentPlanList)){
								for (BwRepaymentPlan bwRepaymentPlan : bwRepaymentPlanList) {
									WsmSchedules wsmSchedules = new WsmSchedules();
									Date repayTime = bwRepaymentPlan.getRepayTime();//应还款时间
									Date updateTime = bwRepaymentPlan.getUpdateTime();//实际还款时间
									
									//第几期
									Integer periodStage = bwRepaymentPlan.getNumber();
									wsmSchedules.setPeriodStage(periodStage);
									
									
									//将date转为指定格式的字符串
									String dueAt = DateUtil.getDateString(repayTime,"yyyy-MM-dd");
									String repayAtPartial = DateUtil.getDateString(updateTime,"yyyy-MM-dd");
									if(!CommUtils.isNull(repayAtPartial)){
										wsmSchedules.setRepayAtPartial(repayAtPartial);//实际还款时间
										wsmSchedules.setRepayAt(repayAtPartial);//全部结清时间
										weishenmaResponse.setLastRepayAt(repayAtPartial);//最近还款日期
									}
									wsmSchedules.setDueAt(dueAt);//应还款时间
						
									//还款金额
									Double principal = bwRepaymentPlan.getRepayCorpusMoney(); 
									wsmSchedules.setPrincipal(principal);//应还本金
									wsmSchedules.setRepaiCapital(principal);//已还本金
									wsmSchedules.setStatus(3);//设置还款状态（1.还款中 2.本息已完清 罚息未还清 3.全部已还清 ）
									
									
									Long repayId = bwRepaymentPlan.getId();
									//判断是否有逾期记录，若有获取已还罚息和逾期天数
									BwOverdueRecord bwOverdueRecord = bwOverdueRecordService.queryBwOverdueByRepayId(repayId);
									if(CommUtils.isNull(bwOverdueRecord)){
										wsmSchedules.setOverdues(0);//逾期天数
										wsmSchedules.setRepaidPenalty(0.0);//已还罚息
									}else{
										wsmSchedules.setOverdues(bwOverdueRecord.getOverdueDay());//逾期天数
										if(2==bwOverdueRecord.getOverdueStatus()){
											wsmSchedules.setRepaidPenalty(bwOverdueRecord.getOverdueAccrualMoney()-bwOverdueRecord.getAdvance());//已还罚息金额
										}
									}
									wsmSchedulesList.add(wsmSchedules);
								}
								weishenmaResponse.setSchedules(wsmSchedulesList);
								
							}
						}
					}
					//还在还款中,不存在逾期记录
					if(9==statusId){
						logger.info("订单状态为还款中："+orderId);
						//单期还款中
						if(1==productType){
							Integer repayStatus = 1;//表示未还款
							weishenmaResponse.setPeriodTotal(1);//总分期
							weishenmaResponse.setAmount(bwOrder.getBorrowAmount());//应还本金
							
							WsmSchedules wsmSchedules = new WsmSchedules();
							wsmSchedules.setPeriodStage(1);//为一期
							wsmSchedules.setInterest(0.0);//应还利息
							wsmSchedules.setRepaidInterest(0.0);//已还利息
							//根据订单号，和repayStatus为1来获取还款计划，从而得到还款时间信息(这里注意，办理展期会生成另外的一条还款计划，原来的那条还款计划的状态为4，展期)
							BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.findRepaymentPlanByOrderIdAndRepayStatus(orderId,repayStatus);
							if(!CommUtils.isNull(bwRepaymentPlan)){
								Date repayTime = bwRepaymentPlan.getRepayTime();//应还款时间
//								Date updateTime = bwRepaymentPlan.getUpdateTime();//实际还款时间
								//将date转为指定格式的字符串
								String dueAt = DateUtil.getDateString(repayTime,"yyyy-MM-dd");
//								String repayAtPartial = DateUtil.getDateString(updateTime,"yyyy-MM-dd");
								wsmSchedules.setDueAt(dueAt);//应还款时间
//								wsmSchedules.setRepayAtPartial(repayAtPartial);//实际还款时间
//								wsmSchedules.setRepayAt(repayAtPartial);//全部结清时间
//								weishenmaResponse.setLastRepayAt(repayAtPartial);//最近还款日期
								wsmSchedules.setOverdues(0);//逾期天数
								wsmSchedules.setRepaidPenalty(0.0);//已还罚息
								Double principal = bwRepaymentPlan.getRepayCorpusMoney(); 
								wsmSchedules.setPrincipal(principal);//应还本金
								wsmSchedules.setRepaiCapital(0.0);//已还本金
								wsmSchedules.setStatus(1);//设置还款状态（1.还款中 2.本息已完清 罚息未还清 3.全部已还清 ）
							}else{
								logger.info("获取订单号为："+orderId+"的还款计划为空");
							}
							wsmSchedulesList.add(wsmSchedules);
							weishenmaResponse.setSchedules(wsmSchedulesList);
							
						}
						//分期还款中
						if(2==productType){
							//分期产品，获取分期的期数
							Integer periodTotal = bwOrder.getBorrowNumber();//分期数
							weishenmaResponse.setPeriodTotal(periodTotal);//总分期
							weishenmaResponse.setAmount(bwOrder.getBorrowAmount());//应还本金
							//根据订单号获取所有的状态不为展期的还款计划
							List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService.findRepaymentPlanListByOrderId(orderId);
							if(!CommUtils.isNull(bwRepaymentPlanList)){
								for (BwRepaymentPlan bwRepaymentPlan : bwRepaymentPlanList) {
									WsmSchedules wsmSchedules = new WsmSchedules();
									Date repayTime = bwRepaymentPlan.getRepayTime();//应还款时间
									Date updateTime = bwRepaymentPlan.getUpdateTime();//实际还款时间
									//第几期
									Integer periodStage = bwRepaymentPlan.getNumber();
									wsmSchedules.setPeriodStage(periodStage);
									
									//将date转为指定格式的字符串
									String dueAt = DateUtil.getDateString(repayTime,"yyyy-MM-dd");
									if(!CommUtils.isNull(updateTime)){
										String repayAtPartial = DateUtil.getDateString(updateTime,"yyyy-MM-dd");
										wsmSchedules.setRepayAtPartial(repayAtPartial);//实际还款时间
										wsmSchedules.setRepayAt(repayAtPartial);//全部结清时间
										weishenmaResponse.setLastRepayAt(repayAtPartial);//最近还款日期
									}
									wsmSchedules.setDueAt(dueAt);//应还款时间
									
									Double principal = bwRepaymentPlan.getRepayCorpusMoney(); 
									wsmSchedules.setPrincipal(principal);//应还本金
									if(2==bwRepaymentPlan.getRepayStatus()){
										//表示该期已还清
										wsmSchedules.setRepaiCapital(principal);//已还本金
										wsmSchedules.setStatus(3);//设置还款状态（1.还款中 2.本息已完清 罚息未还清 3.全部已还清 ）
									}else{
										wsmSchedules.setRepaiCapital(0.0);//已还本金
										wsmSchedules.setStatus(1);//设置还款状态（1.还款中 2.本息已完清 罚息未还清 3.全部已还清 ）
									}
									Long repayId = bwRepaymentPlan.getId();
									//判断是否有逾期记录，若有获取已还罚息和逾期天数
									BwOverdueRecord bwOverdueRecord = bwOverdueRecordService.queryBwOverdueByRepayId(repayId);
									if(CommUtils.isNull(bwOverdueRecord)){
										wsmSchedules.setOverdues(0);//逾期天数
										wsmSchedules.setRepaidPenalty(0.0);//已还罚息
									}else{
										wsmSchedules.setOverdues(bwOverdueRecord.getOverdueDay());//逾期天数
										if(2==bwOverdueRecord.getOverdueStatus()){
											wsmSchedules.setRepaidPenalty(bwOverdueRecord.getOverdueAccrualMoney()-bwOverdueRecord.getAdvance());//已还罚息金额
										}
									}
									wsmSchedulesList.add(wsmSchedules);
								}
								weishenmaResponse.setSchedules(wsmSchedulesList);
								
							}
						}
					}
					//订单状态为逾期
					if(13==statusId){
						logger.info("订单状态为逾期："+orderId);
						//单期逾期状态
						if(1==productType){
							Integer repayStatus = 3;//表示逾期,状态为垫付
							weishenmaResponse.setPeriodTotal(1);//总分期
							weishenmaResponse.setAmount(bwOrder.getBorrowAmount());//应还本金
							
							WsmSchedules wsmSchedules = new WsmSchedules();
							wsmSchedules.setPeriodStage(1);//为一期
							wsmSchedules.setInterest(0.0);//应还利息
							wsmSchedules.setRepaidInterest(0.0);//已还利息
							//根据订单号，和repayStatus为1来获取还款计划，从而得到还款时间信息(这里注意，办理展期会生成另外的一条还款计划，原来的那条还款计划的状态为4，展期)
							BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.findRepaymentPlanByOrderIdAndRepayStatus(orderId,repayStatus);
							if(!CommUtils.isNull(bwRepaymentPlan)){
								Date repayTime = bwRepaymentPlan.getRepayTime();//应还款时间
//								Date updateTime = bwRepaymentPlan.getUpdateTime();//实际还款时间
								//将date转为指定格式的字符串
								String dueAt = DateUtil.getDateString(repayTime,"yyyy-MM-dd");
//								String repayAtPartial = DateUtil.getDateString(updateTime,"yyyy-MM-dd");
								wsmSchedules.setDueAt(dueAt);//应还款时间
//								wsmSchedules.setRepayAtPartial(repayAtPartial);//实际还款时间
								
//								weishenmaResponse.setLastRepayAt(repayAtPartial);//最近还款日期
								
								Double principal = bwRepaymentPlan.getRepayCorpusMoney(); 
								wsmSchedules.setPrincipal(principal);//应还本金
								wsmSchedules.setRepaiCapital(0.0);//已还本金
								wsmSchedules.setStatus(1);//设置还款状态（1.还款中 2.本息已完清 罚息未还清 3.全部已还清 ）
								
								
								Long repayId = bwRepaymentPlan.getId();
								//判断是否有逾期记录，若有获取已还罚息和逾期天数
								BwOverdueRecord bwOverdueRecord = bwOverdueRecordService.queryBwOverdueByRepayId(repayId);
								if(CommUtils.isNull(bwOverdueRecord)){
									wsmSchedules.setOverdues(0);//逾期天数
									wsmSchedules.setRepaidPenalty(0.0);//已还罚息
								}else{
									wsmSchedules.setOverdues(bwOverdueRecord.getOverdueDay());//逾期天数
									logger.info("商户订单号为："+shddh+"逾期天数为："+bwOverdueRecord.getOverdueDay());
									if(2==bwOverdueRecord.getOverdueStatus()){
										Double repaidPenalty  = bwOverdueRecord.getOverdueAccrualMoney()-bwOverdueRecord.getAdvance();
										wsmSchedules.setRepaidPenalty(repaidPenalty);//已还罚息金额
										logger.info("商户订单号为："+shddh+"已还罚息为："+repaidPenalty);
									}
								}
							}else{
								logger.info("获取订单号为："+orderId+"的还款计划为空");
							}
							wsmSchedulesList.add(wsmSchedules);
							weishenmaResponse.setSchedules(wsmSchedulesList);
							
						}
						if(2==productType){
							//分期产品，获取分期的期数
							Integer periodTotal = bwOrder.getBorrowNumber();//分期数
							weishenmaResponse.setPeriodTotal(periodTotal);//总分期
							weishenmaResponse.setAmount(bwOrder.getBorrowAmount());//应还本金
							//根据订单号获取所有的状态不为展期的还款计划
							List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService.findRepaymentPlanListByOrderId(orderId);
							if(!CommUtils.isNull(bwRepaymentPlanList)){
								for (BwRepaymentPlan bwRepaymentPlan : bwRepaymentPlanList) {
									WsmSchedules wsmSchedules = new WsmSchedules();
									Date repayTime = bwRepaymentPlan.getRepayTime();//应还款时间
									Date updateTime = bwRepaymentPlan.getUpdateTime();//实际还款时间
									//第几期
									Integer periodStage = bwRepaymentPlan.getNumber();
									wsmSchedules.setPeriodStage(periodStage);
									
									//将date转为指定格式的字符串
									if(!CommUtils.isNull(updateTime)){
										String repayAtPartial = DateUtil.getDateString(updateTime,"yyyy-MM-dd");
										wsmSchedules.setRepayAtPartial(repayAtPartial);//实际还款时间
										wsmSchedules.setRepayAt(repayAtPartial);//全部结清时间
										weishenmaResponse.setLastRepayAt(repayAtPartial);//最近还款日期
									}
									String dueAt = DateUtil.getDateString(repayTime,"yyyy-MM-dd");
									wsmSchedules.setDueAt(dueAt);//应还款时间
						
									Double principal = bwRepaymentPlan.getRepayCorpusMoney(); 
									wsmSchedules.setPrincipal(principal);//应还本金
									if(2==bwRepaymentPlan.getRepayStatus()){
										//表示该期已还清
										wsmSchedules.setRepaiCapital(principal);//已还本金
										wsmSchedules.setStatus(3);//设置还款状态（1.还款中 2.本息已完清 罚息未还清 3.全部已还清 ）
									}else{
										wsmSchedules.setRepaiCapital(0.0);//已还本金
										wsmSchedules.setStatus(1);//设置还款状态（1.还款中 2.本息已完清 罚息未还清 3.全部已还清 ）
									}
									Long repayId = bwRepaymentPlan.getId();
									//判断是否有逾期记录，若有获取已还罚息和逾期天数
									BwOverdueRecord bwOverdueRecord = bwOverdueRecordService.queryBwOverdueByRepayId(repayId);
									if(CommUtils.isNull(bwOverdueRecord)){
										wsmSchedules.setOverdues(0);//逾期天数
										wsmSchedules.setRepaidPenalty(0.0);//已还罚息
									}else{
										wsmSchedules.setOverdues(bwOverdueRecord.getOverdueDay());//逾期天数
										if(2==bwOverdueRecord.getOverdueStatus()){
											Double repaidPenalty  = bwOverdueRecord.getOverdueAccrualMoney()-bwOverdueRecord.getAdvance();
											wsmSchedules.setRepaidPenalty(repaidPenalty);//已还罚息金额
											logger.info("商户订单号为："+shddh+"已还罚息为："+repaidPenalty);
										}
									}
									wsmSchedulesList.add(wsmSchedules);
								}
								weishenmaResponse.setSchedules(wsmSchedulesList);
								
							}
						}
					}
					weishenmaResponseList.add(weishenmaResponse);
				}
				result.setCode("200");
				result.setMsg("成功");
				result.setResult(weishenmaResponseList);
			} catch (Exception e) {
				logger.info("系统异常："+e);
				result.setCode("100");
				result.setMsg("系统异常");
				return result;
			}

		} catch (Exception e) {
			logger.info("系统异常："+e);
			result.setCode("100");
			result.setMsg("系统异常");
			return result;
		}
		logger.info("返回结果为："+JSON.toJSONString(result));
		return result;
	}
	
	
}
