package com.waterelephant.zhengxin91.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.beadwallet.service.zhengxin91.entity.*;
import com.waterelephant.entity.BwBorrower;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.zhengxin91.service.ZhengXin91ServiceSDK;
import com.beadwallet.service.zhengxin91.util.JsonSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.zhengxin91.entity.CrimeEscapingDB;
import com.waterelephant.zhengxin91.entity.JyPunishBreakDB;
import com.waterelephant.zhengxin91.entity.JyPunishedDB;
import com.waterelephant.zhengxin91.entity.ZxBlack;
import com.waterelephant.zhengxin91.entity.ZxError;
import com.waterelephant.zhengxin91.entity.ZxLoanInfo;
import com.waterelephant.zhengxin91.entity.ZxTrxNo;
import com.waterelephant.zhengxin91.service.Zx91Service;
import com.waterelephant.zhengxin91.service.ZxBlackService;
import com.waterelephant.zhengxin91.service.ZxCrimeescapingService;
import com.waterelephant.zhengxin91.service.ZxErrorService;
import com.waterelephant.zhengxin91.service.ZxJyPunishBreakService;
import com.waterelephant.zhengxin91.service.ZxJyPunishedService;
import com.waterelephant.zhengxin91.service.ZxLoanInfoService;
import com.waterelephant.zhengxin91.service.ZxTrxNoService;
import com.waterelephant.zhengxin91.util.ZhengXin;
import com.waterelephant.zhengxin91.util.ZhengXin91Constant;

/**
 * 征信91
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 13:35
 */
@Service public class Zx91ServiceImpl extends BaseService<ZxLoanInfo, Long> implements Zx91Service {

	private Logger logger = Logger.getLogger(Zx91ServiceImpl.class);

	@Autowired private ZxLoanInfoService zxLoanInfoService; // 91征信 - 共享信息
	@Autowired private ZxBlackService zxBlackService; // 91征信 - 黑名单
	@Autowired private ZxTrxNoService zxTrxNoService; // 91征信 - 存储TrxNo
	@Autowired private IBwOrderService iBwOrderService;
	@Autowired private ZxCrimeescapingService zxCrimeescapingService; // 91征信 - 犯罪在逃
	@Autowired private ZxJyPunishBreakService zxJyPunishBreakService; // 91征信 - 人法
	@Autowired private ZxJyPunishedService zxJyPunishedService; // 91征信 - 人法
	@Autowired private ZxErrorService zxErrorService; // 91征信 - 调用错误保存

	/**
	 * 91征信 - 1.借贷信息查询接口 - 1001
	 *
	 * @param reqMap
	 * @return
	 */
	@Override public AppResponseResult saveTongBuGet(Map reqMap) {
		/*********************** 测试用 开始 **********************/
		String realName = (String) reqMap.get("realName");
		String idCard = (String) reqMap.get("idCard");
		Long borrowId = Long.parseLong((String) reqMap.get("borrowId"));
		Long orderId = Long.parseLong((String) reqMap.get("orderId"));
		/*********************** 测试用 **********************/
		AppResponseResult result = new AppResponseResult();
		Response s = new Response();
		try {
			// 创建
			s = ZhengXin91ServiceSDK.createTrxNo(reqMap);
		} catch (Exception e) {
			result.setCode("1000");
			result.setResult("网络繁忙，请稍后再试");
			return result;
		}
		result.setCode(s.getRequestCode());
		result.setMsg(s.getRequestMsg());
		/*********************** 测试用 开始 **********************/
		if ("200".equals(s.getRequestCode())) {
			Pkg2001SDK pkg2001SDK = (Pkg2001SDK) s.getObj();
			reqMap.clear();
			reqMap.put("trxNo", pkg2001SDK.getTrxNo());
			result.setCode("200");
			result = saveTrxNo(realName, idCard, borrowId, orderId, pkg2001SDK);
			/*********************** 测试用 **********************/
			// 根据沟通 在1.1接口之后，立刻调用1.2接口是不会有数据返回的
			// try {
			// s = ZhengXin91ServiceSDK.queryInfo(reqMap);
			// } catch (Exception e) {
			// e.printStackTrace();
			// result.setCode("1000");
			// result.setResult("网络繁忙，请稍后再试");
			// }
			// result.setCode(s.getRequestCode());
			// result.setMsg(s.getRequestMsg());
			// 91征信1.2接口返回正常
			// if ("200".equals(s.getRequestCode())) {
			// Pkg2002SDK pkg2002SDK = (Pkg2002SDK) s.getObj();
			// List<LoanInfo> loanInfoList = pkg2002SDK.getLoanInfos();
			// if (loanInfoList.size() > 0) {
			// // 保存ZxLoanInfo
			// result = saveZxLoanInfo(borrowId, orderId, loanInfoList);
			// if ("200".equals(result.getCode())) {
			// // 强规则判断
			// result = saveQiangGuiZe(realName, idCard, borrowId, loanInfoList);
			// return result;
			// } else {
			// return result;
			// }
			// } else {
			// // 保存TrxNo
			// result = saveTrxNo(realName, idCard, borrowId, orderId, pkg2001SDK);
			// }
			// } else {
			// // 保存TrxNo
			// result = saveTrxNo(realName, idCard, borrowId, orderId, pkg2001SDK);
			// }
		}
		return result;
	}


	/**
	 * 91征信 - 1.借贷信息查询接口 - 1003
	 *
	 * @param reqMap
	 * @return
	 */
	@Override public AppResponseResult saveTongBu1003(Map reqMap) {
		AppResponseResult result = new AppResponseResult();
		Response response1 = new Response();

		String idCard = null;
		String realName = null;
		Long borrowerId = null;
		Long orderId = null;
		try {
			 idCard = (String) reqMap.get("idCard");
			 realName = (String) reqMap.get("realName");
			 borrowerId = Long.valueOf((String) reqMap.get("borrowId"));
			 orderId = Long.valueOf((String) reqMap.get("orderId"));
		} catch (Exception e ) {
			e.printStackTrace();
		}
		BwBorrower borrower = new BwBorrower();
		borrower.setIdCard(idCard);
		borrower.setName(realName);
		try {
			// 查询数据库中的黑名单
			ZxBlack zxBlack = new ZxBlack();
			zxBlack.setIdCard(borrower.getIdCard());
			zxBlack.setName(borrower.getName());
			logger.info("开始CheckServiceImpl的91征信定时任务：" + JSONObject.toJSONString(borrower));
			List<ZxBlack> zxBlackList = zxBlackService.queryZxBlack91(borrower.getIdCard());
			logger.info("执行CheckServiceImpl的91征信定时任务，获取数据库黑名单：" + JSONObject.toJSONString(zxBlackList));

			// 1. 判断水象是否有黑名单
			if (zxBlackList != null && zxBlackList.size() > 0) {
				// 存在黑名单 - 返回false
				logger.info("结束CheckServiceImpl的91征信定时任务，数据库中存在姓名为【" + borrower.getName() + "】的黑名单");
			} else {
				// 2. 不存在黑名单 - 请求91征信
				Map<String, String> paramMap = new HashMap<>();
				paramMap.put("sessionId", " ~~~~~~~~~ ");
				paramMap.put("realName", borrower.getName());
				paramMap.put("idCard", borrower.getIdCard());
				logger.info("执行CheckServiceImpl的91征信定时任务，创建查询编号，入参：" + JSONObject.toJSONString(paramMap));
				Response<Object> response = ZhengXin91ServiceSDK.createTrxNo1003(paramMap);
				logger.info("执行CheckServiceImpl的91征信定时任务，创建查询编号，返回结果：" + JSONObject.toJSONString(paramMap));
				// 91征信 - 判断请求91征信是否成功
				if (response != null && "200".equals(response.getRequestCode())) { // 成功 - 保存查询编号
					logger.info("执行CheckServiceImpl的91征信定时任务，请求91征信返回200，开始保存");
					zxTrxNoService.deleteByBorrowerId(String.valueOf(borrowerId)); // 删除旧的查询编号
					Pkg2003SDK pkg2003SDK = (Pkg2003SDK) response.getObj();
					ZxTrxNo zxTrxNo = new ZxTrxNo();
					zxTrxNo.setIdCard(borrower.getIdCard());
					zxTrxNo.setRealName(borrower.getName());
					zxTrxNo.setBorrowerId(borrowerId);
					zxTrxNo.setCreateTime(new Date());
					zxTrxNo.setOrderId(orderId);
					zxTrxNo.setRealName(borrower.getName());
					zxTrxNo.setTrxNo(pkg2003SDK.getTrxNo());
					zxTrxNoService.save(zxTrxNo); // 保存新的查询编号
					saveZxLoanInfo(zxTrxNo.getBorrowerId(), zxTrxNo.getOrderId(), pkg2003SDK.getLoanInfos());

					boolean ish = ZhengXin.qiangGuiZe1003(pkg2003SDK.getLoanInfos());  //新的强规则
					if (ish) {
						ZxBlack zxBlack2 = new ZxBlack();
						zxBlack2.setBorrowId(zxTrxNo.getBorrowerId());
						zxBlackService.deleteZxBlack(zxBlack2); // 先删除后添加（此步骤可能多余）
						// 保存黑名单
						zxBlack2.setName(zxTrxNo.getRealName());
						zxBlack2.setIdCard(zxTrxNo.getIdCard());
						zxBlack2.setSource(20); // 91征信
						zxBlack2.setSourceItem(5);// 多头
						zxBlack2.setType(2);
						zxBlack2.setRemark("命中91征信: 多重负债");
						zxBlack2.setRejectType(1); //
						zxBlack2.setRejectInfo("命中91征信: 多重负债");
						zxBlack2.setCreateTime(new Date());
						zxBlack2.setUpdateTime(new Date());
						zxBlackService.saveZxBlack(zxBlack2);
						logger.info("执行CheckServiceImpl的91征信定时任务，命中多重负债，开始保存");
					}

					// 第三步：91黑名单（人人催黑名单，人人催风险名单）
					boolean isRiskList = ZhengXin.isRiskList(pkg2003SDK.getLoanInfos()); // 判断是否多重负债
					if (isRiskList == true) {
						ZxBlack zxBlack3 = new ZxBlack();
						zxBlack3.setBorrowId(zxTrxNo.getBorrowerId());
						zxBlackService.deleteZxBlack(zxBlack3); // 先删除后添加（此步骤可能多余）
						// 保存黑名单
						zxBlack3.setName(zxTrxNo.getRealName());
						zxBlack3.setIdCard(zxTrxNo.getIdCard());
						zxBlack3.setSource(20); // 91征信
						zxBlack3.setType(2);
						zxBlack3.setRemark("命中91征信黑名单规则");
						zxBlack3.setRejectType(1); //
						zxBlack3.setSourceItem(2); // 人人催黑名单
						zxBlack3.setRejectInfo("命中91征信黑名单规则");
						zxBlack3.setCreateTime(new Date());
						zxBlack3.setUpdateTime(new Date());
						zxBlackService.saveZxBlack(zxBlack3);
						// 返回
					}

					// 第四步：人法
					Map<String, String> paramMaps = new HashMap<>();
					paramMap.put("sessionId", "1212");
					paramMap.put("realName", zxTrxNo.getRealName());
					paramMap.put("idCard", zxTrxNo.getIdCard());
					boolean isPeopleCourt = isPeopleCourt(paramMap);
					if (isPeopleCourt == true) {
						ZxBlack zxBlack4 = new ZxBlack();
						zxBlack4.setBorrowId(zxTrxNo.getBorrowerId());
						zxBlackService.deleteZxBlack(zxBlack4); // 先删除后添加（此步骤可能多余）

						// 保存黑名单
						zxBlack4.setName(zxTrxNo.getRealName());
						zxBlack4.setIdCard(zxTrxNo.getIdCard());
						zxBlack4.setSource(20); // 91征信
						zxBlack4.setSourceItem(3); // 人法
						zxBlack4.setType(2);
						zxBlack4.setRemark("命中91征信人法规则");
						zxBlack4.setRejectType(1);
						zxBlack4.setRejectInfo("命中91征信人法规则");
						zxBlack4.setCreateTime(new Date());
						zxBlack4.setUpdateTime(new Date());
						zxBlackService.saveZxBlack(zxBlack4);
						// 返回
					}

					// 第五步：犯罪在逃
					boolean isCrimeEscaping = isCrimeEscaping(paramMap);
					if (isCrimeEscaping == true) {
						ZxBlack zxBlack5 = new ZxBlack();
						zxBlack5.setBorrowId(zxTrxNo.getBorrowerId());
						zxBlackService.deleteZxBlack(zxBlack5); // 先删除后添加（此步骤可能多余）
						// 保存黑名单
						zxBlack5.setName(zxTrxNo.getRealName());
						zxBlack5.setIdCard(zxTrxNo.getIdCard());
						zxBlack5.setSource(20); // 91征信
						zxBlack5.setSourceItem(4); // 犯罪
						zxBlack5.setType(2);
						zxBlack5.setRemark("命中91征信犯罪在逃规则");
						zxBlack5.setRejectType(1);
						zxBlack5.setRejectInfo("命中91征信犯罪在逃规则");
						zxBlack5.setCreateTime(new Date());
						zxBlack5.setUpdateTime(new Date());
						try {
							zxBlackService.saveZxBlack(zxBlack5);
						} catch (Exception e) {
							e.printStackTrace();
						}
						// 返回
					}
				} else { // 失败 - 向数据库写入失败原因
					logger.info("执行CheckServiceImpl的91征信定时任务，请求91征信未返回200，开始保存ZxError");

					ZxError zxError = new ZxError();
					zxError.setMsg("请求91征信，创建查询编号，返回结果：" + JSONObject.toJSONString(response));
					zxError.setCreateTime(new Date());
					if (response != null && response.getObj() != null) {
						Pkg2001SDK pkg2001SDK = (Pkg2001SDK) response.getObj();
						zxError.setTrxNo(pkg2001SDK.getTrxNo());
					}
					zxErrorService.saveZxError(zxError);

					logger.info(
							"执行CheckServiceImpl的91征信定时任务，请求91征信未返回200，保存的ZxError为：" + JSONObject.toJSONString(zxError));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("执行CheckServiceImpl的91征信定时任务异常，原因：" + e.getMessage());
		}
		return  null;
	}



	/**
	 * 91征信 - 测试定时任务用
	 *
	 * @see
	 */
	@Override public AppResponseResult saveZhenXin11(Map<String, String> paramMap) {
		String sessionId = DateUtil.getSessionId();
		AppResponseResult result = new AppResponseResult();
		logger.info(sessionId + ":开始Zx91ServiceImpl的zhenXin11（）方法，入参：" + JSONObject.toJSONString(paramMap));
		// 91征信
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// String sessionId = sdf.format(new Date());
		try {
			// 取参数
			String realName = paramMap.get("realName");
			String idCard = paramMap.get("idCard");
			String borrowId = paramMap.get("borrowId");
			String orderId = paramMap.get("orderId");

			// 查询数据库中的黑名单
			ZxBlack zxBlack = new ZxBlack();
			zxBlack.setIdCard(idCard);
			zxBlack.setName(realName);
			logger.info(sessionId + "：开始CheckServiceImpl的91征信定时任务：" + JSONObject.toJSONString(paramMap));
			List<ZxBlack> zxBlackList = zxBlackService.queryZxBlack91(idCard);
			logger.info(sessionId + "：执行CheckServiceImpl的91征信定时任务，获取数据库黑名单：" + JSONObject.toJSONString(zxBlackList));

			// 判断是否有黑名单
			if (zxBlackList != null && zxBlackList.size() > 0) {
				// 存在黑名单 - 返回false
				logger.info(sessionId + "：结束CheckServiceImpl的91征信定时任务，数据库中存在姓名为【" + realName + "】的黑名单");
				result.setCode("999");
				result.setMsg("数据库中存在姓名为【" + realName + "】的黑名单");
				return result;
			} else {
				// 不存在黑名单 - 请求91征信
				Map<String, String> paramMap1 = new HashMap<>();
				paramMap1.put("sessionId", sessionId);
				paramMap1.put("realName", realName);
				paramMap1.put("idCard", idCard);
				logger.info(sessionId + "：执行CheckServiceImpl的91征信定时任务，创建查询编号，入参：" + JSONObject.toJSONString(paramMap1));
				Response<Object> response = ZhengXin91ServiceSDK.createTrxNo(paramMap1);
				logger.info(
						sessionId + "：执行CheckServiceImpl的91征信定时任务，创建查询编号，返回结果：" + JSONObject.toJSONString(paramMap1));

				// 91征信 - 判断请求91征信是否成功
				if (response != null && "200".equals(response.getRequestCode()) == true) { // 成功 - 保存查询编号
					logger.info(sessionId + "：执行CheckServiceImpl的91征信定时任务，请求91征信返回200，开始保存ZxTrxNo");

					zxTrxNoService.deleteByBorrowerId(String.valueOf(borrowId)); // 删除旧的查询编号

					Pkg2001SDK pkg2001SDK = (Pkg2001SDK) response.getObj();
					ZxTrxNo zxTrxNo = new ZxTrxNo();
					zxTrxNo.setIdCard(idCard);
					zxTrxNo.setRealName(realName);
					zxTrxNo.setBorrowerId(Long.parseLong(borrowId));
					zxTrxNo.setCreateTime(new Date());
					zxTrxNo.setOrderId(Long.parseLong(orderId));
					zxTrxNo.setRealName(realName);
					zxTrxNo.setTrxNo(pkg2001SDK.getTrxNo());
					zxTrxNoService.save(zxTrxNo); // 保存新的查询编号

					logger.info(
							sessionId + "：执行CheckServiceImpl的91征信定时任务，保存的ZxTrxNo为：" + JSONObject.toJSONString(zxTrxNo));
				} else { // 失败 - 向数据库写入失败原因
					logger.info(sessionId + "：执行CheckServiceImpl的91征信定时任务，请求91征信未返回200，开始保存ZxError");

					ZxError zxError = new ZxError();
					zxError.setMsg("请求91征信，创建查询编号，返回结果：" + JSONObject.toJSONString(response));
					zxError.setCreateTime(new Date());
					if (response != null && response.getObj() != null) {
						Pkg2001SDK pkg2001SDK = (Pkg2001SDK) response.getObj();
						zxError.setTrxNo(pkg2001SDK.getTrxNo());
					}
					zxErrorService.saveZxError(zxError);

					logger.info(sessionId + "：执行CheckServiceImpl的91征信定时任务，请求91征信未返回200，保存的ZxError为：" + JSONObject
							.toJSONString(zxError));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(sessionId + "：执行CheckServiceImpl的91征信定时任务异常，原因：" + e.getMessage());
		}
		logger.info(sessionId + ":结束Zx91ServiceImpl的zhenXin11（）方法，结果：" + JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * 91征信 - 3. 借贷信息共享接口 & 借贷查询反馈接口 - 3001 & 3002
	 *
	 * @param postData
	 * @return
	 */
	@Override public byte[] saveYiBu(byte[] postData) {
		PkgHeader reqPkg = new PkgHeader(); // 请求报文
		PkgHeader rspPkg = new PkgHeader(); // 响应报文
		try {
			reqPkg.parseFormBytes(postData, reqPkg.getEncode()); // 解析请求报文
			// String sessionId = DateUtil.getSessionId();
			// logger.info(sessionId + "：91征信，消息派发前的请求报文（用于测试）：" + JSONObject.toJSONString(reqPkg));
			// logger.info(sessionId + "：91征信，消息派发前的响应报文（用于测试）：" + JSONObject.toJSONString(rspPkg));
			logger.info(" ~~~~~~~~~~~~~~~~~~ 91征信 接受到91征信请求 入参：【 " + rspPkg.toPkgStr() + "】");
			rspPkg = distributeReq(reqPkg, rspPkg); // 进行消息派发处理
		} catch (Exception e) {
			logger.info(" ~~~~~~~~~~~~~~~~~~ 91征信 回调入参异常，需联系91征信处理");
			e.printStackTrace();
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("系统异常消息处理失败");
			rspPkg.setMsgBody("");
			ZxError zxError = new ZxError();
			zxError.setMsg(e.getMessage());
			zxError.setCreateTime(new Date());
			zxErrorService.saveZxError(zxError);
		}
		rspPkg.setCustNo(ZhengXin91Constant.ZHENGXIN_CUST_NO); // 配置服务器编号
		rspPkg.setEncode(reqPkg.getEncode()); // 设置编码
		rspPkg.setEncryptType(reqPkg.getEncryptType()); // 设置加密类型
		rspPkg.setMsgType(reqPkg.getMsgType()); // 设置消息类型
		rspPkg.setVersion(reqPkg.getVersion());// 设置版本
		return rspPkg.toPkgBytes("UTF-8");
	}

	/**
	 * 91征信 - 异步信息 - 消息派发（3001,3002接口回调地址一致，需要派发处理请求）
	 *
	 * @param reqPkg
	 * @param rspPkg
	 * @return
	 */
	private PkgHeader distributeReq(PkgHeader reqPkg, PkgHeader rspPkg) {
		try {
			String txnCode = reqPkg.getTrxCode();
			switch (txnCode) {
			// 客户端查询处理
			case "3001": {
				rspPkg = saveDoTask3001(reqPkg, rspPkg);
				rspPkg.setTrxCode("4001");
			}
			break;
			case "3002": {
				rspPkg = saveDoTask3002(reqPkg, rspPkg);
				rspPkg.setTrxCode("4002");
			}
			break;
			default: {
				throw new Exception("未知的报文类型");
			}
			}
		} catch (Exception e) {
			logger.info(" ~~~~~~~~~~~~~~~~~~ 91征信 回调入参异常，需联系91征信处理");
			e.printStackTrace();
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("系统异常消息处理失败");
			rspPkg.setMsgBody("");
		}
		return rspPkg;
	}

	/**
	 * 91征信 - 异步信息 - 消息派发 - 处理3001
	 *
	 * @param reqPkg
	 * @param rspPkg
	 * @return
	 */
	private PkgHeader saveDoTask3001(PkgHeader reqPkg, PkgHeader rspPkg) {
		Pkg4001 pkg4001 = new Pkg4001();
		rspPkg.setTrxCode("4001"); // 说明返回报文
		reqPkg.setCustNo(ZhengXin91Constant.ZHENGXIN_CUST_NO); // 返回机构
		rspPkg.setSign(ZhengXin91Constant.ZHENGXIN_SIGN); // 签名
		Pkg3001 pkg3001 = null;
		try {
			pkg3001 = (Pkg3001) JsonSerializer.deserializer(reqPkg.getMsgBody(), new TypeReference<Pkg3001>() {
			});
			logger.info(" ~~~~~~~~~~~~~~~~~~~ 91征信 接受到91征信查询请求1.3 入参 【 " + pkg3001.getIdCard() + " 】");
		} catch (Exception e) {
			logger.info(" ~~~~~~~~~~~~~~~~~~ 91征信 回调入参异常，需联系91征信处理");
			e.printStackTrace();
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("提交参数有误");
			rspPkg.setMsgBody("");
			return rspPkg;
		}
		// 参数非空判断
		if (CommUtils.isNull(pkg3001)) {
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("提交参数为空");
			rspPkg.setMsgBody("");
			return rspPkg;
		}
		String companyCode = pkg3001.getCompanyCode(); // 发起查询的公司代码，具体指指令是由哪家公司请求 一般用作记录分析
		String realName = pkg3001.getRealName(); // 被查询人的姓名
		String idCard = pkg3001.getIdCard(); // 被查询人的身份证号
		if (CommUtils.isNull(realName)) {
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("姓名为空");
			rspPkg.setMsgBody("");
			return rspPkg;
		}
		if (CommUtils.isNull(idCard)) {
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("身份证为空");
			rspPkg.setMsgBody("");
			return rspPkg;
		}
		if (!CommUtils.isNull(companyCode)) {
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("查询机构为空");
			rspPkg.setMsgBody("");
			return rspPkg;
		}
		List<LoanInfo> loanInfos = new ArrayList<LoanInfo>();
		try {
			loanInfos = iBwOrderService.zhengxin(idCard);
			if (loanInfos.size() > 0) {
				for (LoanInfo loanInfo : loanInfos) {
					loanInfo.setCompanyCode(ZhengXin91Constant.ZHENGXIN_CUST_NO);
				}
			}
		} catch (Exception e) {
			logger.info(" ~~~~~~~~~~~~~~~~~~ 91征信 1.2 接口查询水象共享数据异常");
			e.printStackTrace();
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("查询失败");
			rspPkg.setMsgBody("");
			return rspPkg;
		}

		// 正常情况
		pkg4001.setLoanInfos(loanInfos);
		String msgBodyJson = JsonSerializer.serializer(pkg4001);
		rspPkg.setMsgBody(msgBodyJson);
		rspPkg.setRetCode("0000");
		rspPkg.setRetMsg("查询成功");
		return rspPkg;
	}

	/**
	 * 91征信 - 异步信息 - 消息派发 - 处理3002
	 *
	 * @param reqPkg
	 * @param rspPkg
	 * @return
	 */
	private PkgHeader saveDoTask3002(PkgHeader reqPkg, PkgHeader rspPkg) {
		try {
			Pkg3002 pkg3002 = (Pkg3002) JsonSerializer.deserializer(reqPkg.getMsgBody(), new TypeReference<Pkg3002>() {
			});
			String trxNo = pkg3002.getTrxNo(); // 发起查询时返回的编号用于做信息匹对
			List<LoanInfo> loanInfoList = pkg3002.getLoanInfos();
			logger.info(
					" ~~~~~~~~~~~~~~~~~~~ 91征信 接受到91征信查询请求1.4 入参 【 " + pkg3002.getTrxNo() + " 】" + "  查询条数：" + pkg3002
							.getLoanInfos().size());
			if (trxNo == null || loanInfoList == null) {
				rspPkg.setRetCode("9999");
				rspPkg.setRetMsg("提交参数有误");
				rspPkg.setSign(ZhengXin91Constant.ZHENGXIN_SIGN);
				rspPkg.setMsgBody("");
				return rspPkg;
			}
			AppResponseResult appResponseResult = saveZxservice(pkg3002); // 保存共享信息
			if (!"200".equals(appResponseResult.getCode())) {
				rspPkg.setRetCode("1001");
				rspPkg.setRetMsg("保存失败");
				rspPkg.setSign(ZhengXin91Constant.ZHENGXIN_SIGN);
			} else {
				// 正常情况
				rspPkg.setRetCode("0000");
				rspPkg.setRetMsg("成功接收到结果");
				rspPkg.setSign(ZhengXin91Constant.ZHENGXIN_SIGN); // 签名
			}
		} catch (Exception e) {
			logger.info(" ~~~~~~~~~~~~~~~~~~ 91征信 回调入参异常，需联系91征信处理");
			e.printStackTrace();
			rspPkg.setRetCode("9999");
			rspPkg.setRetMsg("提交参数有误");
			rspPkg.setSign(ZhengXin91Constant.ZHENGXIN_SIGN);
			rspPkg.setMsgBody("");

			ZxError zxError = new ZxError();
			zxError.setMsg(e.getMessage());
			zxError.setCreateTime(new Date());
			zxErrorService.saveZxError(zxError);
		}
		rspPkg.setMsgBody("");
		return rspPkg;
	}

	/**
	 * 91征信 - 异步信息 - 消息派发 - 处理3002 - 保存
	 *
	 * @param pkg3002
	 * @return
	 */
	public AppResponseResult saveZxservice(Pkg3002 pkg3002) {
		AppResponseResult result = new AppResponseResult();
		ZxTrxNo zxTrxNo = zxTrxNoService.findByTrxNo(pkg3002.getTrxNo());
		if (zxTrxNo != null) {
			result = saveZxLoanInfo(zxTrxNo.getBorrowerId(), zxTrxNo.getOrderId(), pkg3002.getLoanInfos()); // 保存共享信息
			// if ("200".equals(result.getCode())) {
			// 强规则判断
			// result = saveQiangGuiZe(zxTrxNo.getRealName(), zxTrxNo.getIdCard(), zxTrxNo.getBorrowerId(),
			// pkg3002.getLoanInfos());
			// return result;

			// 黑名单判断
			result = saveBlackList(zxTrxNo, pkg3002.getLoanInfos()); // 黑名单匹配
			// return result;
			// } else {
			// return result;
			// }
			return result;
		} else {
			result.setCode("200");
			return result;
		}
	}

	/**
	 * 91征信 - 异步信息 - 消息派发 - 处理3002 - 保存到水象数据库 - 贷款信息
	 *
	 * @param borrowId
	 * @param orderId
	 * @param loanInfoList
	 * @return
	 */
	private AppResponseResult saveZxLoanInfo(Long borrowId, Long orderId, List<LoanInfo> loanInfoList) {
		AppResponseResult result = new AppResponseResult();
		try {
			ZxLoanInfo zxLoanInfoD = new ZxLoanInfo();
			zxLoanInfoD.setBorrowId(borrowId);
			zxLoanInfoService.deleteZxLoanInfo(zxLoanInfoD);
		} catch (Exception e) {
			logger.info(" ~~~~~~~~~~~~~~~~~~~ 91征信 数据库删除ZxLoanInfo出错");
			e.printStackTrace();
			result.setCode("1311");
			result.setMsg("数据库删除共享信息异常");
			return result;
		}
		// 遍历共享结果存储数据库
		for (LoanInfo loanInfo : loanInfoList) {
			ZxLoanInfo zxLoanInfo = new ZxLoanInfo();
			zxLoanInfo.setArrearsAmount(loanInfo.getArrearsAmount());

			zxLoanInfo.setCreateTime(new Date());
			zxLoanInfo.setBorrowId(borrowId);
			zxLoanInfo.setOrderId(orderId);
			zxLoanInfo.setContractDate(loanInfo.getContractDate());
			zxLoanInfo.setCompanyCode(loanInfo.getCompanyCode());
			try {
				zxLoanInfo.setBorrowAmount(Integer.valueOf(loanInfo.getBorrowAmount()));
				zxLoanInfo.setBorrowState(Integer.valueOf(loanInfo.getBorrowState()));
				zxLoanInfo.setRepayState(Integer.valueOf(loanInfo.getRepayState()));
				zxLoanInfo.setLoanPeriod(Integer.valueOf(loanInfo.getLoanPeriod()));
				zxLoanInfo.setBorrowType(Integer.valueOf(loanInfo.getBorrowType()));
			} catch (Exception e) {
				logger.info(" ~~~~~~~~~~~~~~~~~~~ 91征信 ZxLoanInfo  Integer.valueOf转换异常");
			}
			try {
				zxLoanInfoService.saveZxLoanInfo(zxLoanInfo);
			} catch (Exception e) {
				logger.info(" ~~~~~~~~~~~~~~~~~~~ 91征信 数据库存储ZxLoanInfo出错");
				e.printStackTrace();
				result.setCode("1311");
				result.setMsg("数据库存储共享信息异常");
				return result;
			}
		}
		result.setCode("200");
		result.setMsg("保存共享信息数据成功");
		return result;
	}

	/**
	 * 91征信 - 异步信息 - 消息派发 - 处理3002 - 黑名单判断
	 *
	 * @param zxTrxNo
	 * @param loanInfoList
	 * @return
	 */
	private AppResponseResult saveBlackList(ZxTrxNo zxTrxNo, List<LoanInfo> loanInfoList) {
		AppResponseResult result = new AppResponseResult();
		result.setCode("200");
		result.setMsg("未命中任何规则");
		try {
			// 第一步：判断是否在数据库中存在黑名单
			ZxBlack zxBlack1 = new ZxBlack();
			zxBlack1.setIdCard(zxTrxNo.getIdCard());
			zxBlack1.setName(zxTrxNo.getRealName());
			List<ZxBlack> zxBlackList = zxBlackService.queryZxBlack91(zxTrxNo.getIdCard());
			if (zxBlackList != null && zxBlackList.size() > 0) {
				result.setCode("200");
				result.setMsg("平台中已存在黑名单");
				return result;
			}

			// 第二步：多重负债
			boolean isMultipleLiabilities = ZhengXin.isMultipleLiabilities(loanInfoList); // 判断是否多重负债
			// 判断是否有逾期且逾期金额为0或还款抓状态未知
			if (!isMultipleLiabilities) {
				boolean unclearSituation = ZhengXin.isUnclearSituation(loanInfoList);
				if (unclearSituation) {
					ZxBlack zxBlack2 = new ZxBlack();
					zxBlack2.setBorrowId(zxTrxNo.getBorrowerId());
					zxBlackService.deleteZxBlack(zxBlack2); // 先删除后添加（此步骤可能多余）
					// 保存黑名单
					zxBlack2.setName(zxTrxNo.getRealName());
					zxBlack2.setIdCard(zxTrxNo.getIdCard());
					zxBlack2.setSource(20); // 91征信
					zxBlack2.setSourceItem(5);// 多头
					zxBlack2.setType(2);
					zxBlack2.setRemark("命中91征信: 有逾期但逾期金额为0或还款状态未知");
					zxBlack2.setRejectType(2); // 改为待人审
					zxBlack2.setRejectInfo("命中91征信: 有逾期但逾期金额为0或还款状态未知");
					zxBlack2.setCreateTime(new Date());
					zxBlack2.setUpdateTime(new Date());
					zxBlackService.saveZxBlack(zxBlack2);
				}
			}
			if (isMultipleLiabilities == true) {
				ZxBlack zxBlack2 = new ZxBlack();
				zxBlack2.setBorrowId(zxTrxNo.getBorrowerId());
				zxBlackService.deleteZxBlack(zxBlack2); // 先删除后添加（此步骤可能多余）

				// 保存黑名单
				zxBlack2.setName(zxTrxNo.getRealName());
				zxBlack2.setIdCard(zxTrxNo.getIdCard());
				zxBlack2.setSource(20); // 91征信
				zxBlack2.setSourceItem(1);// 多头
				zxBlack2.setType(2);
				zxBlack2.setRemark("命中91征信多重负债规则");
				zxBlack2.setRejectType(2); // 改为待人审
				zxBlack2.setRejectInfo("命中91征信多重负债规则");
				zxBlack2.setCreateTime(new Date());
				zxBlack2.setUpdateTime(new Date());
				zxBlackService.saveZxBlack(zxBlack2);

				// 返回
				result.setCode("200");
				result.setMsg("命中91征信多重负债规则");
				return result;
			}

			// 第三步：91黑名单（人人催黑名单，人人催风险名单）
			boolean isRiskList = ZhengXin.isRiskList(loanInfoList); // 判断是否多重负债
			if (isRiskList == true) {
				ZxBlack zxBlack3 = new ZxBlack();
				zxBlack3.setBorrowId(zxTrxNo.getBorrowerId());
				zxBlackService.deleteZxBlack(zxBlack3); // 先删除后添加（此步骤可能多余）

				// 保存黑名单
				zxBlack3.setName(zxTrxNo.getRealName());
				zxBlack3.setIdCard(zxTrxNo.getIdCard());
				zxBlack3.setSource(20); // 91征信
				zxBlack3.setType(2);
				zxBlack3.setRemark("命中91征信黑名单规则");
				zxBlack3.setRejectType(2); // 待人审
				zxBlack3.setSourceItem(2); // 人人催黑名单
				zxBlack3.setRejectInfo("命中91征信黑名单规则");
				zxBlack3.setCreateTime(new Date());
				zxBlack3.setUpdateTime(new Date());
				zxBlackService.saveZxBlack(zxBlack3);

				// 返回
				result.setCode("200");
				result.setMsg("命中91征信黑名单规则");
				return result;
			}

			// 第四步：人法
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("sessionId", DateUtil.getSessionId());
			paramMap.put("realName", zxTrxNo.getRealName());
			paramMap.put("idCard", zxTrxNo.getIdCard());
			boolean isPeopleCourt = isPeopleCourt(paramMap);
			if (isPeopleCourt == true) {
				ZxBlack zxBlack4 = new ZxBlack();
				zxBlack4.setBorrowId(zxTrxNo.getBorrowerId());
				zxBlackService.deleteZxBlack(zxBlack4); // 先删除后添加（此步骤可能多余）

				// 保存黑名单
				zxBlack4.setName(zxTrxNo.getRealName());
				zxBlack4.setIdCard(zxTrxNo.getIdCard());
				zxBlack4.setSource(20); // 91征信
				zxBlack4.setSourceItem(3); // 人法
				zxBlack4.setType(2);
				zxBlack4.setRemark("命中91征信人法规则");
				zxBlack4.setRejectType(1);
				zxBlack4.setRejectInfo("命中91征信人法规则");
				zxBlack4.setCreateTime(new Date());
				zxBlack4.setUpdateTime(new Date());
				zxBlackService.saveZxBlack(zxBlack4);

				// 返回
				result.setCode("200");
				result.setMsg("命中91征信人法规则");
				return result;
			}

			// 第五步：犯罪在逃
			boolean isCrimeEscaping = isCrimeEscaping(paramMap);
			if (isCrimeEscaping == true) {
				ZxBlack zxBlack5 = new ZxBlack();
				zxBlack5.setBorrowId(zxTrxNo.getBorrowerId());
				zxBlackService.deleteZxBlack(zxBlack5); // 先删除后添加（此步骤可能多余）

				// 保存黑名单
				zxBlack5.setName(zxTrxNo.getRealName());
				zxBlack5.setIdCard(zxTrxNo.getIdCard());
				zxBlack5.setSource(20); // 91征信
				zxBlack5.setSourceItem(4); // 犯罪
				zxBlack5.setType(2);
				zxBlack5.setRemark("命中91征信犯罪在逃规则");
				zxBlack5.setRejectType(1);
				zxBlack5.setRejectInfo("命中91征信犯罪在逃规则");
				zxBlack5.setCreateTime(new Date());
				zxBlack5.setUpdateTime(new Date());
				try {
					zxBlackService.saveZxBlack(zxBlack5);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 返回
				result.setCode("200");
				result.setMsg("命中91征信犯罪在逃规则");
				return result;
			}
		} catch (Exception e) {
			result.setCode("2000");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 91征信 - 人法（是否请求成功）
	 */
	private boolean isPeopleCourt(Map<String, String> paramMap) {
		boolean isSuccess = false;
		String sessionId = paramMap.get("sessionId");
		logger.info(sessionId + ":进入app的Zx91ServiceImpl的isPeopleCourt（）方法，入参：" + JSONObject.toJSONString(paramMap));
		try {
			// 执行请求
			Response<Object> response = ZhengXin91ServiceSDK.peopleCourt(paramMap);
			logger.info(sessionId + ":执行app的Zx91ServiceImpl的isPeopleCourt（）方法，SDK返回结果：" + JSONObject
					.toJSONString(response));

			// 处理结果
			PeopleCourt peopleCourt = (PeopleCourt) response.getObj();
			if (peopleCourt != null) { // 判断是否为空
				if ("0000".equals(peopleCourt.getRetCode()) == true) { // 判断是否成功
					// 往数据库中插入人法信息
					Data data = peopleCourt.getData();
					if (data != null) {
						String realName = paramMap.get("realName"); // 姓名
						String idCard = paramMap.get("idCard"); // 身份证号

						List<JyPunishBreak> jyPunishBreakList = data.getJyPunishBreak();
						for (JyPunishBreak jyPunishBreak : jyPunishBreakList) {
							JyPunishBreakDB jyPunishBreakDB = new JyPunishBreakDB(jyPunishBreak);
							jyPunishBreakDB.setCardNum(idCard);
							jyPunishBreakDB.setName(realName);
							zxJyPunishBreakService.saveJyPunishBreakDB(jyPunishBreakDB);
						}

						List<JyPunished> jyPunishedList = data.getJyPunished();
						for (JyPunished jyPunished : jyPunishedList) {
							JyPunishedDB jyPunishedDB = new JyPunishedDB(jyPunished);
							jyPunishedDB.setCardNum(idCard);
							jyPunishedDB.setName(realName);
							zxJyPunishedService.saveJyPunishedDB(jyPunishedDB);
						}
						isSuccess =
								(jyPunishBreakList != null && jyPunishBreakList.size() > 0) || (jyPunishedList != null
										&& jyPunishedList.size() > 0); // 只要人民法院中有数据，表示命中人法规则
					}

				} else {
					logger.info(sessionId + ":执行app的Zx91ServiceImpl的isPeopleCourt（）方法，SDK返回人法信息，原因：" + response
							.getRequestMsg());
					isSuccess = false;
				}
			} else {
				logger.info(sessionId + ":执行app的Zx91ServiceImpl的isPeopleCourt（）方法，SDK未返回人法信息，原因：" + response
						.getRequestMsg());
				isSuccess = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(sessionId + ":执行app的Zx91ServiceImpl的isPeopleCourt（）方法异常：" + e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 91征信 - 犯罪在逃（是否请求成功）
	 */
	private boolean isCrimeEscaping(Map<String, String> paramMap) {
		boolean isSuccess = false;
		String sessionId = paramMap.get("sessionId");
		logger.info(sessionId + ":进入app的Zx91ServiceImpl的isCrimeEscaping（）方法，入参：" + JSONObject.toJSONString(paramMap));
		try {
			// 执行请求
			Response<Object> response = ZhengXin91ServiceSDK.crimeEscaping(paramMap);
			logger.info(sessionId + ":执行app的Zx91ServiceImpl的isCrimeEscaping（）方法，SDK返回结果：" + JSONObject
					.toJSONString(response));

			// 处理结果
			CrimeEscaping crimeEscaping = (CrimeEscaping) response.getObj();

			if (crimeEscaping != null) { // 判断是否为空
				if ("0000".equals(crimeEscaping.getRetCode()) == true) { // 判断是否成功
					// 往数据库中插入人法信息
					CrimeEscapingDB crimeEscapingDB = new CrimeEscapingDB(crimeEscaping);
					crimeEscapingDB.setName(paramMap.get("realName"));
					crimeEscapingDB.setIdCard(paramMap.get("idCard"));
					zxCrimeescapingService.saveCrimeEscapingDB(crimeEscapingDB);
					isSuccess = crimeEscaping.getStatus();
				} else {
					logger.info(sessionId + ":执行app的Zx91ServiceImpl的isCrimeEscaping（）方法，SDK返回人法信息，原因：" + response
							.getRequestMsg());
					isSuccess = false;
				}
			} else {
				logger.info(sessionId + ":执行app的Zx91ServiceImpl的isCrimeEscaping（）方法，SDK未返回人法信息，原因：" + response
						.getRequestMsg());
				isSuccess = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(sessionId + ":执行app的Zx91ServiceImpl的isCrimeEscaping（）方法异常：" + e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 91征信 - 异步信息 - 消息派发 - 处理3002 - 保存 - 强规则（已废弃）
	 *
	 * @param realName
	 * @param idCard
	 * @param borrowId
	 * @param loanInfoList
	 * @return
	 */
	@SuppressWarnings("unused") @Deprecated private AppResponseResult saveQiangGuiZe(String realName, String idCard,
			Long borrowId, List<LoanInfo> loanInfoList) {
		AppResponseResult result = new AppResponseResult();
		boolean b = ZhengXin.qiangGuiZe(loanInfoList);
		if (!b) {
			ZxBlack zxBlack = new ZxBlack();
			zxBlack.setBorrowId(borrowId);
			try {
				zxBlackService.deleteZxBlack(zxBlack);
			} catch (Exception e) {
				logger.info(" ~~~~~~~~~~~~~~~~~~~ 91征信 数据库删除zxBlack黑名单出错");
				e.printStackTrace();
				result.setCode("1311");
				result.setMsg("数据库删除黑名单异常");
				return result;
			}
			zxBlack.setCreateTime(new Date());
			zxBlack.setBorrowId(borrowId);
			zxBlack.setIdCard(idCard);
			zxBlack.setName(realName);
			zxBlack.setSource(1);
			zxBlack.setRejectType(1);
			try {
				zxBlackService.saveZxBlack(zxBlack);
			} catch (Exception e) {
				logger.info(" ~~~~~~~~~~~~~~~~~~~ 91征信 数据库存储zxBlack黑名单出错");
				e.printStackTrace();
				result.setCode("1311");
				result.setMsg("数据库存储黑名单异常");
				return result;
			}
		}
		result.setCode("200");
		result.setMsg("黑名单数据处理成功");
		result.setResult(b);
		return result;
	}

	// 保存TrxNo
	private AppResponseResult saveTrxNo(String realName, String idCard, Long borrowId, Long orderId,
			Pkg2001SDK pkg2001SDK) {
		AppResponseResult result = new AppResponseResult();
		try {
			zxTrxNoService.deleteByBorrowerId(String.valueOf(borrowId));
		} catch (Exception e) {
			logger.info(" ~~~~~~~~~~~~~~~~~~~ 91征信 数据库查找zxTrxNo出错");
			e.printStackTrace();
			result.setCode("1311");
			result.setMsg("数据查询TrxNo异常");
			return result;
		}
		// if (zxTrxNo == null) {
		ZxTrxNo zxTrxNo = new ZxTrxNo();
		zxTrxNo.setOrderId(orderId);
		zxTrxNo.setIdCard(idCard);
		zxTrxNo.setBorrowerId(borrowId);
		zxTrxNo.setRealName(realName);
		zxTrxNo.setTrxNo(pkg2001SDK.getTrxNo());
		zxTrxNo.setCreateTime(new Date());
		try {
			zxTrxNoService.save(zxTrxNo);
		} catch (Exception e) {
			logger.info(" ~~~~~~~~~~~~~~~~~~~ 91征信 数据库存储zxTrxNo出错");
			e.printStackTrace();
			result.setCode("1311");
			result.setMsg("数据存储TrxNo异常");
			return result;
		}
		// }
		// else {
		// zxTrxNo.setCreateTime(new Date());
		// zxTrxNo.setTrxNo(pkg2001SDK.getTrxNo());
		// zxTrxNo.setOrderId(orderId);
		// try {
		// zxTrxNoService.updateZxTrxNo(zxTrxNo);
		// } catch (Exception e) {
		// logger.info(" ~~~~~~~~~~~~~~~~~~~ 91征信 数据库更新zxTrxNo出错");
		// e.printStackTrace();
		// result.setCode("1311");
		// result.setResult("数据更新TrxNo异常");
		// return result;
		// }
		// }
		result.setCode("200");
		result.setMsg("TrxNo保存成功");
		return result;
	}

	/**
	 * 91征信 - 1.1 借贷信息查询接口 客户->平台（报文主体定义）
	 *
	 * @param paramMap
	 * @return
	 */
	@Override public boolean createTrxNo(Map<String, String> paramMap) {
		boolean isSuccess = false;
		String sessionId = paramMap.get("sessionId");
		logger.info(sessionId + ":进入app的Zx91ServiceImpl的createTrxNo（）方法，入参：" + JSONObject.toJSONString(paramMap));
		try {
			// 执行请求
			Response<Object> response = ZhengXin91ServiceSDK.createTrxNo(paramMap);
			logger.info(
					sessionId + ":执行app的Zx91ServiceImpl的createTrxNo（）方法，SDK返回结果：" + JSONObject.toJSONString(response));

			// 处理请求结果
			if ("200".equals(response.getRequestCode()) == true) {
				Pkg2001SDK pkg2001SDK = (Pkg2001SDK) response.getObj();
				if (pkg2001SDK != null) {
					ZxTrxNo zxTrxNo = new ZxTrxNo();
					zxTrxNo.setCreateTime(new Date());
					zxTrxNo.setIdCard(paramMap.get("idCard"));
					zxTrxNo.setRealName(paramMap.get("realName"));
					zxTrxNo.setTrxNo(pkg2001SDK.getTrxNo());
					isSuccess = zxTrxNoService.save(zxTrxNo) > 0;
				}
			} else {
				logger.info(
						sessionId + ":执行app的Zx91ServiceImpl的createTrxNo（）方法，SDK返回信息，原因：" + response.getRequestMsg());
				isSuccess = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(sessionId + ":执行app的Zx91ServiceImpl的peopleCourt（）方法异常：" + e.getMessage());
			isSuccess = false;
		}
		return isSuccess;
	}

}
