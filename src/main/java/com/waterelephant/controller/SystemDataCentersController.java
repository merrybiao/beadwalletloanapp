package com.waterelephant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.kouDai.service.KouDaiServiceSDK;
import com.beadwallet.service.rong360.service.BeadWalletRongCarrierService;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwOrderSpecialInfoService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.QueryOrderBusiService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;

@Controller
@RequestMapping("/app/dataCenters")
public class SystemDataCentersController {

	private static Logger logger = LoggerFactory.getLogger(SystemDataCentersController.class);

	@Resource
	private IBwOrderService bwOrderService;
	@Resource
	private IBwBorrowerService bwBorrowerService;
	
	@Resource
	private BwBlacklistService bwBlacklistService;
	
	@Resource
	private QueryOrderBusiService queryOrderBusiService;
	
	@Resource
	private BwOrderSpecialInfoService bwOrderSpecialInfoService;
	
	// @Resource
	// private SystemDataCentersService systemDataCentersService;
	//
	// @ResponseBody
	// @RequestMapping("/getData.do")
	// public String getData(HttpServletRequest request, HttpServletResponse response) {
	// Map<String, Object> returnMap = new HashMap<String, Object>();
	// try {
	//
	// String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
	// if (!CommUtils.isNull(jsonRequest)) {
	// JSONObject jsb = JSONObject.parseObject(jsonRequest);
	// String appId = CommUtils.toString(jsb.get("appId"));
	// String type = RedisUtils.hget("external:appId", appId);
	// if (!CommUtils.isNull(appId) && "1".equals(type)) {
	// String params = CommUtils.toString(jsb.get("params"));
	// String aesKey = RedisUtils.hget("external:aesKey", appId);
	// try {
	// String jsonStr = AESUtil.Decrypt(params, aesKey);
	//
	// Map<String, Object> map = JSON.parseObject(jsonStr);
	//
	// String dataStr = CommUtils.toString(map.get("dataStr"));
	// if (!CommUtils.isNull(dataStr)) {
	// String[] datas = dataStr.split(",");
	// for (String data : datas) {
	// if (CommUtils.isNull(data)) {
	// continue;
	// }
	// if ("101".equals(data)) {
	// // 口袋黑名单
	// Map<String, Object> kdMap = systemDataCentersService.queryKoudai(map);
	// returnMap.put("koudaiInfo", kdMap);
	// } else if ("102".equals(data)) {
	// // 白骑士
	// Map<String, Object> bqsMap = systemDataCentersService.queryBaiqishi(map);
	// returnMap.put("baiqishiInfo", bqsMap);
	// } else if ("103".equals(data)) {
	// // 中智诚
	// Map<String, Object> yhhxMap = systemDataCentersService.queryBaiqishi(map);
	// returnMap.put("rong360hxInfo", yhhxMap);
	// }
	// }
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// returnMap.put("code", "10002");
	// returnMap.put("msg", "秘钥不合法");
	// }
	//
	// } else {
	// returnMap.put("code", "99999");
	// returnMap.put("msg", "非法请求");
	// return JSON.toJSONString(returnMap);
	// }
	//
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// returnMap.put("code", "10003");
	// returnMap.put("msg", "请求异常");
	// return JSON.toJSONString(returnMap);
	// }
	//
	// return JSON.toJSONString(returnMap);
	// }

	@ResponseBody
	@RequestMapping("/getRongUserPortrait.do")
	public JSONObject getRongUserPortrait(HttpServletRequest request, HttpServletResponse response) {
		String str = "";
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			JSONObject jsb = JSONObject.parseObject(jsonRequest);
			String idCard = CommUtils.toString(jsb.get("idCard"));
			String name = CommUtils.toString(jsb.get("name"));
			String phone = CommUtils.toString(jsb.get("phone"));
			str = BeadWalletRongCarrierService.rongUserPortrait(idCard, name, phone);
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.parseObject("{\"code\":\"99999\",\"msg\":\"参数不合法\"}");
		}
		return JSON.parseObject(str);
	}

	@ResponseBody
	@RequestMapping("/getRongPhoneConsumptionK.do")
	public JSONObject getRongPhoneConsumptionK(HttpServletRequest request, HttpServletResponse response) {
		String str = "";
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			JSONObject jsb = JSONObject.parseObject(jsonRequest);
			String idCard = CommUtils.toString(jsb.get("idCard"));
			String name = CommUtils.toString(jsb.get("name"));
			String phone = CommUtils.toString(jsb.get("phone"));
			str = BeadWalletRongCarrierService.rongPhoneConsumptionK(idCard, name, phone);
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.parseObject("{\"code\":\"99999\",\"msg\":\"参数不合法\"}");
		}
		return JSON.parseObject(str);
	}

	@ResponseBody
	@RequestMapping("/getRongPhoneNetworklengthK.do")
	public JSONObject getRongPhoneNetworklengthK(HttpServletRequest request, HttpServletResponse response) {
		String str = "";
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			JSONObject jsb = JSONObject.parseObject(jsonRequest);
			String idCard = CommUtils.toString(jsb.get("idCard"));
			String name = CommUtils.toString(jsb.get("name"));
			String phone = CommUtils.toString(jsb.get("phone"));
			str = BeadWalletRongCarrierService.rongPhoneNetworklengthK(idCard, name, phone);
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.parseObject("{\"code\":\"99999\",\"msg\":\"参数不合法\"}");
		}
		return JSON.parseObject(str);
	}

	@ResponseBody
	@RequestMapping("/getRongPhonestatusK.do")
	public JSONObject getRongPhonestatusK(HttpServletRequest request, HttpServletResponse response) {
		String str = "";
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			JSONObject jsb = JSONObject.parseObject(jsonRequest);
			String idCard = CommUtils.toString(jsb.get("idCard"));
			String name = CommUtils.toString(jsb.get("name"));
			String phone = CommUtils.toString(jsb.get("phone"));
			str = BeadWalletRongCarrierService.rongPhonestatusK(idCard, name, phone);
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.parseObject("{\"code\":\"99999\",\"msg\":\"参数不合法\"}");
		}
		return JSON.parseObject(str);
	}

	@ResponseBody
	@RequestMapping("/queryKoudaiBlack.do")
	public JSONObject queryKoudaiBlack(HttpServletRequest request, HttpServletResponse response) {
//		JSONObject object = new JSONObject();
//		object.put("code", "9999");
//		object.put("msg", "[2018-10-23]接口已停用~");
//		return object;
		
		Map<String, String> data = null;
		String blackFlag = null;
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			JSONObject jsb = JSONObject.parseObject(jsonRequest);
			String id_card = CommUtils.toString(jsb.get("id_card"));
			String name = CommUtils.toString(jsb.get("name"));
			String mobile = CommUtils.toString(jsb.get("mobile"));
			// 获取token
			String token = RedisUtils.get("koudai");
			if (CommUtils.isNull(token)) {
				token = KouDaiServiceSDK.queryToken();
				RedisUtils.setex("koudai", token, 82800);
			}
			if (StringUtil.isEmpty(id_card) || StringUtil.isEmpty(name) || StringUtil.isEmpty(mobile)
					|| StringUtil.isEmpty(token)) {
				return JSON.parseObject("{\"code\":\"99999\",\"msg\":\"参数不合法\"}");
			} else {
				data = new HashMap<String, String>();
				data.put("id_card", id_card);
				data.put("name", name);
				data.put("mobile", mobile);
				data.put("token", token);
			}
			// 获取黑名单
			blackFlag = KouDaiServiceSDK.queryBlack(data);

			if ("-1".equals("blackFlag")) {
				token = KouDaiServiceSDK.queryToken();
				RedisUtils.setex("koudai", token, 82800);
				blackFlag = KouDaiServiceSDK.queryBlack(data);
			}

			return JSON.parseObject("{\"code\":\"000\",\"msg\":\"成功\",\"data\":\"" + blackFlag + "\"}");

		} catch (Exception e) {
			return JSON.parseObject("{\"code\":\"99999\",\"msg\":\"服务异常\",\"data\":\"\"}");
		}
	}

	@ResponseBody
	@RequestMapping("/queryOverdueSAAS.do")
	public JSONObject queryOverdueSAAS(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			JSONObject jsb = JSONObject.parseObject(jsonRequest);

			String phone = CommUtils.toString(jsb.get("phone"));
			String idCard = CommUtils.toString(jsb.get("idCard"));
			List<BwBorrower> bwBorrowerList = bwBorrowerService.findBwBorrowerByPhoneORIdcard(phone, idCard);
			if (bwBorrowerList != null && bwBorrowerList.size() > 0) {
				for (BwBorrower bwBorrower : bwBorrowerList) {
					int i = 0;
					if (bwBorrower != null) {
						i = bwOrderService.queryNowOverdue(bwBorrower.getId());
						if (i > 0) {
							return JSON.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-存在逾期工单\"}");
						}

						i = bwOrderService.queryLastOverdue(bwBorrower.getId(), "15");
						if (i > 0) {
							return JSON
									.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-最近一次借款逾期15天以上\"}");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return JSON.parseObject("{\"code\":\"99999\",\"status\":\"0\",\"msg\":\"参数不合法\"}");
		}
		return JSON.parseObject("{\"code\":\"0000\",\"status\":\"0\",\"msg\":\"通过\"}");
	}
	
	
	@ResponseBody
	@RequestMapping("/queryOverdueSAAS_V2.do")
	public JSONObject queryOverdueSAAS_V2(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			JSONObject jsb = JSONObject.parseObject(jsonRequest);

			String phone = CommUtils.toString(jsb.get("phone"));
			String idCard = CommUtils.toString(jsb.get("idCard"));
			
			// 外部黑名单验证 phone OR idCard 则命中
			int count = bwBlacklistService.findOutBlacklist(phone, idCard);
			if (count > 0) {
				return JSON
						.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-外部系统黑名单库有此用户信息\"}");
			}
			
			//查询借款人 根据phone OR idCard
			List<BwBorrower> bwBorrowerList = bwBorrowerService.findBwBorrowerByPhoneORIdcard(phone, idCard);
			if (bwBorrowerList != null && bwBorrowerList.size() > 0) {
				for (BwBorrower bwBorrower : bwBorrowerList) {
					int i = 0;
					if (bwBorrower != null) {
						i = bwOrderService.queryOrderIng(bwBorrower.getId());
						if (i > 0) {
							return JSON.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-存在进件中的工单\"}");
						}

						i = bwOrderService.queryLastOverdue(bwBorrower.getId(), "15");
						if (i > 0) {
							return JSON
									.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-最近一次借款逾期15天以上\"}");
						}
						
						// 系统黑名单验证
						i = bwBlacklistService.findBwBlacklistBy(bwBorrower.getIdCard(),1,1);
						if (i >0) {
							return JSON
									.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-系统黑名单库有此身份证信息\"}");
						}
						
						//进件中的工单
//						i = bwOrderService.querySmOrderIng(bwBorrower.getId());
//						if (i > 0) {
//							return JSON.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-存在进件中的工单\"}");
//						}
					}
				}
			}
			
			//根据电话查询工单特殊案件信息
			int total = bwOrderSpecialInfoService.queryOrderSpecialCountByPhone(phone);
			if(total >0) {
				return JSON
						.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-工单特殊案件信息库\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.parseObject("{\"code\":\"99999\",\"status\":\"0\",\"msg\":\"参数不合法\"}");
		}
		return JSON.parseObject("{\"code\":\"0000\",\"status\":\"0\",\"msg\":\"通过\"}");
	}
	
	
	@ResponseBody
	@RequestMapping("/queryOverdue.do")
	public JSONObject queryOverdue(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			JSONObject jsb = JSONObject.parseObject(jsonRequest);

			String phone = CommUtils.toString(jsb.get("phone"));
			String idCard = CommUtils.toString(jsb.get("idCard"));
			
			// 外部黑名单验证 phone OR idCard 则命中
			int count = bwBlacklistService.findOutBlacklist(phone, idCard);
			if (count > 0) {
				return JSON
						.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-外部系统黑名单库有此用户信息\"}");
			}
			
			//查询借款人 根据phone OR idCard
			List<BwBorrower> bwBorrowerList = bwBorrowerService.findBwBorrowerByPhoneORIdcard(phone, idCard);
			if (bwBorrowerList != null && bwBorrowerList.size() > 0) {
				for (BwBorrower bwBorrower : bwBorrowerList) {
					int i = 0;
					if (bwBorrower != null) {
						i = bwOrderService.queryNowOverdue(bwBorrower.getId());
						if (i > 0) {
							return JSON.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-存在逾期工单\"}");
						}

						i = bwOrderService.queryLastOverdue(bwBorrower.getId(), "15");
						if (i > 0) {
							return JSON
									.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-最近一次借款逾期15天以上\"}");
						}
						
						// 系统黑名单验证
						i = bwBlacklistService.findBwBlacklistBy(bwBorrower.getIdCard(),1,1);
						if (i >0) {
							return JSON
									.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-系统黑名单库有此身份证信息\"}");
						}
						
						//进件中的工单
//						i = bwOrderService.querySmOrderIng(bwBorrower.getId());
//						if (i > 0) {
//							return JSON.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-存在进件中的工单\"}");
//						}
					}
				}
			}
			
			//根据电话查询工单特殊案件信息
			int total = bwOrderSpecialInfoService.queryOrderSpecialCountByPhone(phone);
			if(total >0) {
				return JSON
						.parseObject("{\"code\":\"0000\",\"status\":\"1\",\"msg\":\"命中-工单特殊案件信息库\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.parseObject("{\"code\":\"99999\",\"status\":\"0\",\"msg\":\"参数不合法\"}");
		}
		return JSON.parseObject("{\"code\":\"0000\",\"status\":\"0\",\"msg\":\"通过\"}");
	}
	
	@ResponseBody
	@RequestMapping("/queryOverdue_v2.do")
	public JSONObject queryOverdueV2(HttpServletRequest request, HttpServletResponse response) {
		long star = System.currentTimeMillis();
		JSONObject result = new JSONObject();
		String jsonRequest = "";
		try {
			jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("----请求/queryOverdue_v2.do接口入参：{}----",jsonRequest);
			JSONObject jsb = JSONObject.parseObject(jsonRequest);

			String phone = jsb.getString("phone");
			String idCard = jsb.getString("idCard");
			
			Assert.hasText(phone,"缺少参数[phone]不能为空~");
			Assert.hasText(idCard,"缺少参数[idCard]不能为空~");
			
			Map<String,JSONObject> map = new HashMap<>();
			// 外部黑名单验证 phone OR idCard 则命中
			int blackCount = bwBlacklistService.findOutBlacklist(phone, idCard);
			if (blackCount > 0) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("msgCode", "1001");//外部黑名单
				jsonObject.put("message", "命中-外部系统黑名单库有此用户信息");
				map.put(jsonObject.getString("msgCode"), jsonObject);
			}
			
			//根据电话查询工单特殊案件信息
			int orderSpecialCount = bwOrderSpecialInfoService.queryOrderSpecialCountByPhone(phone);
			if(orderSpecialCount >0) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("msgCode", "1002");//工单特殊案件
				jsonObject.put("message", "命中-工单特殊案件信息库");
				map.put(jsonObject.getString("msgCode"), jsonObject);
			}
			
			//查询借款人 根据phone OR idCard
			List<BwBorrower> bwBorrowerList = bwBorrowerService.findBwBorrowerByPhoneORIdcard(phone, idCard);
			if (bwBorrowerList != null && bwBorrowerList.size() > 0) {
				for (BwBorrower bwBorrower : bwBorrowerList) {
					if (bwBorrower != null) {
						int count = 0;
						count = bwOrderService.queryNowOverdue(bwBorrower.getId());
						if (count > 0) {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("msgCode", "1003");//逾期工单
							jsonObject.put("message","命中-存在逾期工单");
							map.put(jsonObject.getString("msgCode"), jsonObject);
						}

						count = bwOrderService.queryLastOverdue(bwBorrower.getId(), "15");
						if (count > 0) {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("msgCode", "1004");//最近工单逾期15天以上
							jsonObject.put("message", "命中-最近一次借款逾期15天以上");
							map.put(jsonObject.getString("msgCode"), jsonObject);
						}
						
						// 系统黑名单验证
						count = bwBlacklistService.findBwBlacklistBy(bwBorrower.getIdCard(),1,1);
						if (count >0) {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("msgCode", "1005");//系统黑名单库
							jsonObject.put("message","命中-系统黑名单库有此身份证信息");
							map.put(jsonObject.getString("msgCode"), jsonObject);
						}
						
						//进件中的工单(速秒、商城)
						count = bwOrderService.querySmOrderIng(bwBorrower.getId());
						if (count > 0) {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("msgCode", "1006");//进件中的工单
							jsonObject.put("message","命中-存在进件中的工单");
							map.put(jsonObject.getString("msgCode"), jsonObject);
						}
					}
				}
			}
			result.put("code", "0000");
			result.put("msg", "请求接口成功");
			result.put("status", map.size()>0 ? "1":"0");
			result.put("list", map.values());
		} catch(IllegalArgumentException e) {
			result.put("code", "99999");
			result.put("msg", e.getMessage());
			result.put("status", "0");
		} catch (Exception e) {
			e.printStackTrace();
//			return JSON.parseObject("{\"code\":\"99999\",\"status\":\"0\",\"msg\":\"参数不合法\"}");
			result.put("code", "99999");
			result.put("msg", "请求接口失败~");
			result.put("status", "0");
		} finally {
			logger.info("----/queryOverdue_v2.do请求参数:{}接口耗时:{}ms----",jsonRequest,(System.currentTimeMillis()-star));
		}
		return result;
	}
	
	/**
	 * 查询进件中工单
	 * <p>请求参数JSON
	 * <p> 返回结果JSON 命中状态、历史逾期天数、SAAS标记为进件中的工单、当前逾期及当前未结清、速秒标记为进件中的工单
	 * @params idcard_no
	 * @params name
	 * @params mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/underway/order.do")
	public AppResponseResult queryUnderwayOrder(@RequestBody JSONObject jsonParams) {
		long star = System.currentTimeMillis();
		AppResponseResult responseResult = new AppResponseResult();
		
		logger.info("-------------/app/dataCenters/underway/order.do请求入参：{}",jsonParams);
		try {
			
			String idcardNo = jsonParams.getString("idcard_no");
			
			String name = jsonParams.getString("name");
			
			String mobile = jsonParams.getString("mobile");
			
			Assert.hasText(idcardNo, "缺少参数[idcard_no]不能为空~");
			Assert.hasText(mobile,"缺少参数[mobile]不能为空~");
//			Assert.hasText(name,"缺少参数[name]不能为空~");
			
			Map<String,Object> result = queryOrderBusiService.queryOrder(idcardNo, name, mobile);
			responseResult.setCode("0000");
			responseResult.setMsg("请求成功~");
			responseResult.setResult(result);
		} catch (IllegalArgumentException e) {
			logger.error("----------------/app/dataCenters/underway/order.do接口参数错误,请求参数：{},错误提示：{}",jsonParams,e.getMessage());
			responseResult.setCode("9999");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.getMessage();
			logger.error("----------------/app/dataCenters/underway/order.do接口系统异常，请求参数：{}，异常原因：{}",jsonParams,e.getMessage());
			responseResult.setCode("9999");
			responseResult.setMsg("请求失败，请稍后再试~");
		} finally {
			logger.info("----/app/dataCenters/underway/order.do请求入参：{}---耗时:{}ms",jsonParams,(System.currentTimeMillis() - star));
		}
		return responseResult;
		
	}

}
