package com.waterelephant.morpho.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.credit.morpho.MorphoCreditSDK;
import com.beadwallet.service.entity.response.Response;
import com.waterelephant.morpho.entity.BwMorphoBlacklist;
import com.waterelephant.morpho.entity.BwMorphoLoanInfo;
import com.waterelephant.morpho.entity.BwMorphoVelocityCheck;
import com.waterelephant.morpho.service.BwMorphoBlacklistService;
import com.waterelephant.morpho.service.BwMorphoLoanInfoService;
import com.waterelephant.morpho.service.BwMorphoVelocityCheckService;
import com.waterelephant.morpho.utils.MorphoMd5Util;

/**
 * code:18005
 * 北京闪蝶征信接口中转
 * @author Lion 
 */
@Controller
@RequestMapping("/morpho")
public class BJMorphoCreditController {
	private Logger logger = Logger.getLogger(BJMorphoCreditController.class);

	@Autowired
	private BwMorphoLoanInfoService bwMorphoLoanInfoService;
	@Autowired
	private BwMorphoBlacklistService bwMorphoBlacklistService;
	@Autowired
	private BwMorphoVelocityCheckService bwMorphoVelocityCheckService;

	@RequestMapping(value = "/credit.do", method = RequestMethod.POST)
	@ResponseBody
	public Response<Object> credit(@RequestParam(required = true) String json) {
		Response<Object> resp = new Response<Object>();// 响应对象
		String pid = null;// 身份证号
		String jsonData = null;// 闪蝶接口请求参数
		try {
			// 校验接口请求参数
			if (StringUtils.isBlank(json)) {
				resp.setRequestCode("444");
				resp.setRequestMsg("请求参数为空，请检查相关参数！");
				return resp;
			}
			logger.info("===================BJ闪蝶入参：" + json);
			try {
				// 获取请求参数
				JSONObject requestData = JSON.parseObject(json);
				String pk1 = requestData.get("pk").toString();
				JSONObject data = requestData.getJSONObject("data");
//				System.out.println("data参数tostring后的值为:" + data.toString());
				jsonData = data.toJSONString();
				pid = data.getString("pid");// 身份证号
				// 请求合法性校验
				String pk2 = MorphoMd5Util.encoding(jsonData);
				if(!pk2.equals(pk1)) {
					// 非法请求 返回
					resp.setRequestCode("445");
					resp.setRequestMsg("pk erro");
					return resp;
				}
			} catch (Exception e) {
				logger.error(e.getMessage() + "，入参参数缺失！");
				resp.setRequestCode("446");
				resp.setRequestMsg("data exception!");
				return resp;
			}
			// 判断是否有查询记录，若有记录，30天以内无需重复查询，30天之外删除原有记录，重新查询 code:18002-2
			if (StringUtils.isNotBlank(pid)) {
				List<BwMorphoLoanInfo> list1 = bwMorphoLoanInfoService.findRecordsByPid(pid);
				List<BwMorphoBlacklist> list2 = bwMorphoBlacklistService.findRecordsByPid(pid);
				List<BwMorphoVelocityCheck> list3 = bwMorphoVelocityCheckService.findRecordsByPid(pid);
				// 如果存在30天内的数据 则将该数据直接返回，无需再次申请闪蝶接口
				if (!list1.isEmpty() || !list2.isEmpty() || !list3.isEmpty()) {
					logger.info("=========================pid：" + pid +"的工单30天内存在闪蝶征信查询，无需再次查询，直接返回数据！");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("loanInfo", list1);
					jsonObject.put("blacklist", list2);
					jsonObject.put("velocityCheck", list3);
					resp.setRequestCode("200");
					resp.setRequestMsg("查询成功！");
					resp.setObj(jsonObject.toJSONString());
					logger.info("=========================pid：" + pid +"的请求成功，结束！");
					return resp;
				}
				// 如果不存在30天内的数据
				// 查询是否存在30天以上的查询记录
				List<BwMorphoLoanInfo> list4 = bwMorphoLoanInfoService.findRecordsPlusByPid(pid);
				List<BwMorphoBlacklist> list5 = bwMorphoBlacklistService.findRecordsPlusByPid(pid);
				List<BwMorphoVelocityCheck> list6 = bwMorphoVelocityCheckService.findRecordsPlusByPid(pid);
				// 存在30天以上的查询记录则执行删除操作后重新查询
				if (!list4.isEmpty() || !list5.isEmpty() || !list6.isEmpty()) {
					logger.info("=========================存在30天以上征信记录,删除pid：" + pid + "相关记录");
					bwMorphoLoanInfoService.deleteByPid(pid);
					bwMorphoBlacklistService.deleteByPid(pid);
					bwMorphoVelocityCheckService.deleteByPid(pid);
				}
			} else {
				// 身份证为空，无法调用接口
				logger.info("参数中身份证号pid为空，无法调用接口！");
				resp.setRequestCode("447");
				resp.setRequestMsg("调用闪蝶接口失败，请求参数中pid为空，请检查相关参数！");
				return resp;
			}
			// 请求闪蝶接口
			HashMap<String, String> paramMap = new HashMap<String,String>();
			paramMap.put("json", jsonData);
			Response<Object> morphoResponse = MorphoCreditSDK.credit(paramMap);
			if (!"200".equals(morphoResponse.getRequestCode())) {
				// 请求失败
				logger.info("============BJ闪蝶请求失败>>>>>>" + morphoResponse.getRequestMsg() + "《》"
						+ morphoResponse.getRequestCode());
				resp.setRequestMsg(morphoResponse.getRequestMsg());
				resp.setRequestCode(morphoResponse.getRequestCode());
				resp.setObj(morphoResponse.getObj());
				return resp;
			} else {
				// 请求成功
//				logger.info("==================BJ闪蝶请求成功，出参：" + morphoResponse.getObj().toString());
				// 保存返回数据
				String respJson = morphoResponse.getObj().toString();
				JSONObject mainData = JSON.parseObject(respJson);// 主数据
				// 1.1>解析loanInfo并保存
				JSONObject loanInfo = (JSONObject) mainData.get("loanInfo");// 批核&贷后数据
				JSONObject pidd = (JSONObject) loanInfo.get("pid");
				JSONObject timeScopes1 = (JSONObject) pidd.get("timeScopes");
				// 1.1.1>保存pid中的D90
				JSONObject pidD90 = (JSONObject) timeScopes1.get("D90");
				BwMorphoLoanInfo pD90 = JSONObject.parseObject(pidD90.toJSONString(), BwMorphoLoanInfo.class);
				pD90.setVeidooType("pid");
				pD90.setReportType("D90");
				pD90.setCreateTime(new Date());
				pD90.setOrderId(0l);
				pD90.setIdNo(pid);
				bwMorphoLoanInfoService.save(pD90);
				// 1.1.2>保存pid中的D360
				JSONObject pidD360 = (JSONObject) timeScopes1.get("D360");
				BwMorphoLoanInfo pD360 = JSONObject.parseObject(pidD360.toJSONString(), BwMorphoLoanInfo.class);
				pD360.setVeidooType("pid");
				pD360.setReportType("D360");
				pD360.setCreateTime(new Date());
				pD360.setOrderId(0l);
				pD360.setIdNo(pid);
				bwMorphoLoanInfoService.save(pD360);
				// 1.2>解析mobile并保存
				JSONObject mobile = (JSONObject) loanInfo.get("mobile");
				JSONObject timeScopes2 = (JSONObject) mobile.get("timeScopes");
				// 1.2.1>保存mobile中的D90
				JSONObject mobileD90 = (JSONObject) timeScopes2.get("D90");
				BwMorphoLoanInfo mD90 = JSONObject.parseObject(mobileD90.toJSONString(), BwMorphoLoanInfo.class);
				mD90.setVeidooType("mobile");
				mD90.setReportType("D90");
				mD90.setCreateTime(new Date());
				mD90.setOrderId(0l);
				mD90.setIdNo(pid);
				bwMorphoLoanInfoService.save(mD90);
				// 1.2.2>保存mobile中的D360
				JSONObject mobileD360 = (JSONObject) timeScopes2.get("D360");
				BwMorphoLoanInfo mD360 = JSONObject.parseObject(mobileD360.toJSONString(), BwMorphoLoanInfo.class);
				mD360.setVeidooType("mobile");
				mD360.setReportType("D360");
				mD360.setCreateTime(new Date());
				mD360.setOrderId(0l);
				mD360.setIdNo(pid);
				bwMorphoLoanInfoService.save(mD360);
				// 2.1>解析blacklist并保存
				JSONObject blacklist = (JSONObject) mainData.get("blacklist");
				// 2.1.1>保存pid数据
				JSONObject blackPid = (JSONObject) blacklist.get("pid");
				BwMorphoBlacklist pidBL = JSONObject.parseObject(blackPid.toJSONString(), BwMorphoBlacklist.class);
				pidBL.setCreateTime(new Date());
				pidBL.setVeidooType("pid");
				pidBL.setOrderId(0l);
				pidBL.setIdNo(pid);
				bwMorphoBlacklistService.save(pidBL);
				// 2.1.2>保存mobile数据
				JSONObject blackMobile = (JSONObject) blacklist.get("mobile");
				BwMorphoBlacklist mobileBL = JSONObject.parseObject(blackMobile.toJSONString(),
						BwMorphoBlacklist.class);
				mobileBL.setCreateTime(new Date());
				mobileBL.setVeidooType("mobile");
				mobileBL.setOrderId(0l);
				mobileBL.setIdNo(pid);
				bwMorphoBlacklistService.save(mobileBL);
				// 3.1>解析velocityCheck并保存
				JSONArray velocityCheckArr = (JSONArray) mainData.get("velocityCheck");
				JSONObject velocityCheck = (JSONObject) velocityCheckArr.get(0);
				// 3.1.1>保存pid数据
				JSONObject pidV = (JSONObject) velocityCheck.get("pid");
				JSONArray arr1 = (JSONArray) pidV.get("timeScopes");
				for (Object obj : arr1) {
					BwMorphoVelocityCheck pV = JSONObject.parseObject(obj.toString(), BwMorphoVelocityCheck.class);
					pV.setCreateTime(new Date());
					pV.setOrderId(0l);
					pV.setVeidooType("pid");
					pV.setIdNo(pid);
					bwMorphoVelocityCheckService.save(pV);
				}
				// 3.1.2>保存mobile数据
				JSONObject mobileV = (JSONObject) velocityCheck.get("mobile");
				JSONArray arr2 = (JSONArray) mobileV.get("timeScopes");
				for (Object obj : arr2) {
					BwMorphoVelocityCheck mV = JSONObject.parseObject(obj.toString(), BwMorphoVelocityCheck.class);
					mV.setCreateTime(new Date());
					mV.setOrderId(0l);
					mV.setVeidooType("mobile");
					mV.setIdNo(pid);
					bwMorphoVelocityCheckService.save(mV);
				}
				// 数据处理结束，返回结果
				// 1.数据库取出数据
				List<BwMorphoLoanInfo> list1 = bwMorphoLoanInfoService.findRecordsByPid(pid);
				List<BwMorphoBlacklist> list2 = bwMorphoBlacklistService.findRecordsByPid(pid);
				List<BwMorphoVelocityCheck> list3 = bwMorphoVelocityCheckService.findRecordsByPid(pid);
				// 2.返回结果
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("loanInfo", list1);
				jsonObject.put("blacklist", list2);
				jsonObject.put("velocityCheck", list3);
				resp.setRequestCode("200");
				resp.setRequestMsg("查询成功！");
				resp.setObj(jsonObject.toJSONString());
				logger.info("==============pid:" + pid + ",BJ闪蝶征信接口请求成功,方法结束！");
				return resp;
			}
		} catch (Exception e) {
			logger.error("==============pid:" + pid + ",闪蝶征信异常，请求失败！" + e);
			e.printStackTrace();
			resp.setRequestCode("500");
			resp.setRequestMsg("闪蝶征信异常，请求失败！" + e);
			return resp;
		}
	}
}
