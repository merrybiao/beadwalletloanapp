// package com.waterelephant.loanwallet.controller;
//
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.math.BigDecimal;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.apache.commons.lang.StringUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.util.StopWatch;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseBody;
//
// import com.alibaba.fastjson.JSON;
// import com.alibaba.fastjson.JSONObject;
// import com.beadwallet.entity.lianlian.Agreement;
// import com.beadwallet.entity.lianlian.CardQueryResult;
// import com.beadwallet.entity.lianlian.NotifyNotice;
// import com.beadwallet.entity.lianlian.NotifyResult;
// import com.beadwallet.entity.lianlian.PlanResult;
// import com.beadwallet.entity.lianlian.RepayRequest;
// import com.beadwallet.entity.lianlian.RepaymentPlan;
// import com.beadwallet.entity.lianlian.RepaymentResult;
// import com.beadwallet.entity.lianlian.SignalLess;
// import com.beadwallet.servcie.LianLianPayService;
// import com.beadwallet.service.loanwallet.entity.LoanWalletUserData;
// import com.beadwallet.service.sms.dto.MessageDto;
// import com.beadwallet.utils.RSAUtil;
// import com.waterelephant.entity.BwBankCard;
// import com.waterelephant.entity.BwBlacklist;
// import com.waterelephant.entity.BwBorrower;
// import com.waterelephant.entity.BwCreditImformation;
// import com.waterelephant.entity.BwOrder;
// import com.waterelephant.entity.BwOrderRong;
// import com.waterelephant.entity.BwOverdueRecord;
// import com.waterelephant.entity.BwPlatformRecord;
// import com.waterelephant.entity.BwRejectRecord;
// import com.waterelephant.entity.BwRepaymentPlan;
// import com.waterelephant.loanwallet.entity.ConfirmData;
// import com.waterelephant.loanwallet.entity.LoanWalletResp;
// import com.waterelephant.loanwallet.entity.PullApproveReqData;
// import com.waterelephant.loanwallet.entity.PullApproveResData;
// import com.waterelephant.loanwallet.entity.PullBindCardStatusReqData;
// import com.waterelephant.loanwallet.entity.PullBindCardStatusResData;
// import com.waterelephant.loanwallet.entity.PullRepayStatusReqData;
// import com.waterelephant.loanwallet.entity.PullRepayStatusResData;
// import com.waterelephant.loanwallet.entity.PullWithDrawStatusReqData;
// import com.waterelephant.loanwallet.entity.PullWithDrawStatusResData;
// import com.waterelephant.loanwallet.entity.RepayData;
// import com.waterelephant.loanwallet.utils.BankUtil;
// import com.waterelephant.loanwallet.utils.LoanWalletConstant;
// import com.waterelephant.loanwallet.utils.LoanWalletLogUtil;
// import com.waterelephant.loanwallet.utils.LoanWalletUtils;
// import com.waterelephant.service.BwBlacklistService;
// import com.waterelephant.service.BwCreditInformationService;
// import com.waterelephant.service.BwOrderRongService;
// import com.waterelephant.service.BwOverdueRecordService;
// import com.waterelephant.service.BwPlatformRecordService;
// import com.waterelephant.service.BwRejectRecordService;
// import com.waterelephant.service.IBwBankCardService;
// import com.waterelephant.service.IBwOrderService;
// import com.waterelephant.service.IBwRepaymentPlanService;
// import com.waterelephant.service.IBwRepaymentService;
// import com.waterelephant.service.impl.BwBorrowerService;
// import com.waterelephant.utils.CommUtils;
// import com.waterelephant.utils.GenerateSerialNumber;
// import com.waterelephant.utils.RedisUtils;
// import com.waterelephant.utils.SystemConstant;
//
// import tk.mybatis.mapper.entity.Example;
//
/// **
// *
// *
// *
// * Module:
// *
// * LoanWalletController.java
// *
// * @author liuDaodao
// * @since JDK 1.8
// * @version 1.0
// * @description: <贷款钱包>
// */
// @Controller
// public class LoanWalletController {
// private LoanWalletLogUtil logger = new LoanWalletLogUtil(LoanWalletController.class);
//
// @Autowired
// BwBorrowerService bwBorrowerService;
//
// @Autowired
// private BwOrderRongService bwOrderRongService;
//
// @Autowired
// private IBwOrderService bwOrderService;
//
// @Autowired
// private IBwBankCardService bwBankCardService;
//
// @Autowired
// private IBwRepaymentPlanService bwRepaymentPlanService;
//
// @Autowired
// private BwOverdueRecordService bwOverdueRecordService;
//
// @Autowired
// private BwPlatformRecordService bwPlatformRecordService;
//
// @Autowired
// private IBwRepaymentService bwRepaymentService;
//
// @Autowired
// private BwBlacklistService bwBlacklistService;
//
// @Autowired
// private BwRejectRecordService bwRejectRecordService;
//
// @Autowired
// private BwCreditInformationService bwCreditInformationService;
//
// private static String LOAN_WALLET_XUDAI = "xudai:order_id";
// private static String USER_INFO_REDIS = "loanWallet:userInfo";
//
// /**
// * 贷款钱包 - 第一步：判断用户是否存在（通过身份证号）
// *
// * @param request
// * @param response
// * @return
// */
// @ResponseBody
// @RequestMapping("loanWallet/isExistsByIdCard.do")
// public Map<String, Object> isThereByIdCard(HttpServletRequest request, HttpServletResponse response) {
// String methodName = "LoanWalletController.isThereByIdCard";
// logger.set("ISTHERE");
// Map<String, Object> returnMap = new HashMap<>(); // 返回值
// Map<String, Object> resultMap = new HashMap<>(); // 接口数据
// resultMap.put("IsThere", 1); // 1:存在，2不存在
// try {
// // 第一步：取参
// String data = request.getParameter("data"); // JSON数据
// String token = request.getParameter("token"); // 秘钥
// // logger.info("data="+data+"&token="+token); // 去掉日志1001
//
// // 第二步：验证入参token
// boolean isEqual = LoanWalletUtils.tokenIsEqual(data, token);
// if (isEqual == false) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "token验证不通过");
// returnMap.put("result", resultMap);
// logger.info(methodName + " end,resp=token验证不通过"); // 修改日志1001
// return returnMap;
// }
//
// // 第三步：根据cardNumber查找平台中的用户
// JSONObject jsonObject = JSONObject.parseObject(data);
// String cardNumber = jsonObject.getString("cardNumber"); // 身份证号
// String phone = jsonObject.getString("phone");
//
// if (StringUtils.isBlank(cardNumber)) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "身份证为空");
// returnMap.put("result", resultMap);
// logger.info(methodName + " end,resp=身份证为空"); // 修改日志1001
// return returnMap;
// }
//
// if (StringUtils.isBlank(phone)) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "手机号为空");
// returnMap.put("result", resultMap);
// logger.info(methodName + " end,resp=手机号为空"); // 修改日志1001
// return returnMap;
// }
//
// String idCardTop2 = cardNumber.substring(0, 2); // 截取身份证前2位
// String idCardTop4 = cardNumber.substring(0, 4); // 截取身份证前4位
// String idCardTop6 = cardNumber.substring(0, 6); // 截取身份证前6位
// boolean bol2 = CommUtils.isNull(RedisUtils.hget("system:reject_area", idCardTop2))
// && CommUtils.isNull(RedisUtils.hget("system:reject_area", idCardTop4))
// && CommUtils.isNull(RedisUtils.hget("system:reject_area", idCardTop6));
// if (!bol2) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "身份证所在区域不符合要求");
// returnMap.put("result", resultMap);
// return returnMap;
// }
//
// BwBorrower borrower = new BwBorrower();
// borrower.setIdCard(cardNumber);
// borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//
// // 第四步：判断该用户是否存在并处理
// if (borrower == null) {
// resultMap.put("IsThere", 2); // 2用户不存在
// returnMap.put("code", "SUCCESS");
// returnMap.put("message", "平台中未查询到该用户，可以推送用户信息");
// returnMap.put("result", resultMap);
// logger.info(methodName + " end,resp=平台中未查询到该用户，可以推送用户信息"); // 修改日志1001
// return returnMap;
// }
//
// // 判断手机号是否一致
// if (!phone.equals(borrower.getPhone())) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "手机号不一致");
// returnMap.put("result", resultMap);
// return returnMap;
// }
//
// // 3判断是否是黑名单，如果存在则返回400，如果没有则查询是否有进行中的订单
// logger.info("开始验证系统平台黑名单");
// Example example = new Example(BwBlacklist.class);
// String idNo = borrower.getIdCard();
// example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card",
// idNo.toUpperCase());
// List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
// if (!CommUtils.isNull(desList)) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "该用户为黑名单用户,不需推送用户信息");
// returnMap.put("result", resultMap);
// return returnMap;
// }
//
// Long count = bwOrderService.findProcessOrder(String.valueOf(borrower.getId()));
//
// if (count != null && count.intValue() > 0) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "该用户有进行中的订单,不需推送用户信息");
// returnMap.put("result", resultMap);
// return returnMap;
// }
//
// // 5查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
// logger.info("开始查询拒绝记录");
// BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());
// logger.info("结束查询拒绝记录,rejectRecord=" + JSONObject.toJSONString(record));
// if (!CommUtils.isNull(record)) {
// // 永久拒绝
// if ("0".equals(String.valueOf(record.getRejectType()))) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "该用户被永久拒绝,不需推送用户信息");
// returnMap.put("result", resultMap);
// return returnMap;
// } else {
// Date rejectDate = record.getCreateTime();
// long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime())
// / (24 * 60 * 60 * 1000);
// if (day <= 7) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "该用户被临时拒绝还未过期,不需推送用户信息");
// returnMap.put("result", resultMap);
// return returnMap;
// }
// }
// }
//
// try {
// logger.info("开始查询平台征信黑名单,idCard=" + cardNumber);
// BwCreditImformation bwCreditImformation = bwCreditInformationService.findByIdCard(cardNumber);
// logger.info("结束查询平台征信黑名单,bwCreditImformation=" + JSONObject.toJSONString(bwCreditImformation));
//
// if (!CommUtils.isNull(bwCreditImformation)) {
// resultMap.put("IsThere", 1); // 1用户在合作平台存在贷款钱包不需推送该用户数据
// returnMap.put("code", "ERROR");
// returnMap.put("message", "不需推送用户信息");
// returnMap.put("result", resultMap);
// return returnMap;
// }
// } catch (Exception e) {
// }
//
// resultMap.put("IsThere", 2);
// returnMap.put("code", "SUCCESS");
// returnMap.put("message", "平台中未查询到该用户，可以推送用户信息");
// returnMap.put("result", resultMap);
// logger.info(methodName + " end,resp=平台中未查询到该用户，可以推送用户信息"); // 修改日志1001
// } catch (Exception e) {
// resultMap.put("IsThere", 1);
// returnMap.put("code", "ERROR");
// returnMap.put("message", "系统异常：" + e.getMessage());
// returnMap.put("result", resultMap);
// logger.error(methodName + " occured exception:", e);
// }
// logger.info(methodName + " end"); // 修改日志1001
// logger.remove();
// return returnMap;
// }
//
// /**
// * 贷款钱包 - 查询用户借款信息
// *
// * @param request
// * @param response
// * @return
// */
// @ResponseBody
// @RequestMapping("loanWallet/queryUserLoanInfo.do")
// public Map<String, Object> queryUserLoanInfo(HttpServletRequest request, HttpServletResponse response) {
// logger.set("USERLOANINFO");
// logger.info("开始loanApp层的queryUserLoanInfo（）方法");
// Map<String, Object> returnMap = new HashMap<>(); // 返回值
// Map<String, Object> resultMap = new HashMap<>(); // 接口数据
// try {
// // 第一步：取参
// String data = request.getParameter("data"); // JSON数据
// // String code = request.getParameter("code"); // 渠道标识
// String token = request.getParameter("token"); // 秘钥
//
// logger.info(":执行loanApp层的queryUserLoanInfo（）方法"); // 修改日志1001
//
// // 第二步：验证入参
// String paramMsg = LoanWalletUtils.commonCheck(request);
// if (paramMsg != null) {
// logger.info(":执行loanApp层的queryUserLoanInfo（）方法，借贷钱包入参有误：" + paramMsg);
// returnMap.put("code", "ERROR");
// returnMap.put("message", paramMsg);
// returnMap.put("result", resultMap);
// return returnMap;
// }
// boolean isEqual = LoanWalletUtils.tokenIsEqual(data, token);
// if (isEqual == false) {
// logger.info(":执行loanApp层的queryUserLoanInfo（）方法，借贷钱包入参token验证未通过");
// returnMap.put("code", "ERROR");
// returnMap.put("message", "token验证未通过");
// returnMap.put("result", resultMap);
// return returnMap;
// }
//
// // 第三步：获取数据（融订单/订单/逾期/还款计划）
// JSONObject dataJson = JSONObject.parseObject(data);
// String orderNo = dataJson.getString("orderNo"); // 借贷钱包用户ID
//
// BwOrderRong bwOrderRong = new BwOrderRong(); // 融订单
// bwOrderRong.setThirdOrderNo(orderNo);
// bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
// if (bwOrderRong == null) {
// returnMap.put("code", "ERROR");
// returnMap.put("message", "通过借贷钱包用户ID【" + orderNo + "】未查询到该用户信息");
// returnMap.put("result", resultMap);
// logger.info(":结束loanApp层的queryUserLoanInfo（）方法，返回结果：通过借贷钱包用户ID【" + orderNo + "】未查询到该用户信息"); // 修改日志1001
// return returnMap;
// }
//
// BwOrder bwOrder = new BwOrder(); // 订单
// bwOrder.setId(bwOrderRong.getOrderId());
// bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//
// if (bwOrder == null) {
// returnMap.put("code", "ERROR");
// returnMap.put("message", "通过借贷钱包用户ID【" + orderNo + "】未查询到该用户订单");
// returnMap.put("result", resultMap);
// logger.info(":结束loanApp层的queryUserLoanInfo（）方法，返回结果：通过借贷钱包用户ID【" + orderNo + "】未查询到该用户订单"); // 修改日志1001
// return returnMap;
// }
//
// BwOverdueRecord bwOverdueRecord = new BwOverdueRecord(); // 逾期
// bwOverdueRecord.setOrderId(bwOrder.getId());
// bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
// double verdueAccrualMoney = 0;
// if (bwOverdueRecord != null) {
// verdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney();
// }
//
// BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan(); // 还款计划
// bwRepaymentPlan.setOrderId(bwOrder.getId());
// bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
//
// Double borrowAmount = bwOrder.getBorrowAmount() == null ? 0D : bwOrder.getBorrowAmount() ;
//
// // 第四步：返回
// resultMap.put("amount", borrowAmount*Float.parseFloat("1.03")); // 应还款总金额
// resultMap.put("poundage", "0"); // 手续费
// resultMap.put("interest", borrowAmount * 0.18); // 应收利息
// resultMap.put("overdueFine", verdueAccrualMoney); // 滞纳金
// if (!CommUtils.isNull(bwRepaymentPlan)) {
// resultMap.put("repaymentDate", bwRepaymentPlan.getUpdateTime()); // 还款日期
// }
//
// returnMap.put("code", "SUCCESS");
// returnMap.put("message", "请求成功");
// returnMap.put("result", resultMap);
// } catch (Exception e) {
// returnMap.put("code", "ERROR");
// returnMap.put("message", "请求异常：" + e.getMessage());
// returnMap.put("result", resultMap);
// logger.error(":执行loanApp层的queryUserLoanInfo（）方法异常：", e);
// }
// logger.info(":结束loanApp层的queryUserLoanInfo（）方法"); // 修改日志1001
// logger.remove();
// return returnMap;
// }
//
// /**
// * 修改OR新增银行卡
// *
// * @param borrower
// * @param confirmData
// * @param bankCode
// * @param bankName
// * @return
// * @throws InterruptedException
// */
// private BwBankCard saveOrUpdateBBC(BwBorrower borrower, ConfirmData confirmData, String bankCode, String bankName) {
//
// BwBankCard bbc = new BwBankCard();
// // 查询银行卡信息
// bbc.setBorrowerId(borrower.getId());
// bbc = findBwBankCardByAttrProxy(bbc);
//
// // 如果不存在就新增，如果存在就修改
// if (CommUtils.isNull(bbc)) {
// logger.info("银行卡信息不存在，开始新增");
// bbc = new BwBankCard();
// bbc.setBorrowerId(borrower.getId());
// bbc.setCardNo(confirmData.getBankCard());
// bbc.setBankCode(bankCode);
// bbc.setBankName(bankName);
// bbc.setPhone(confirmData.getPhone());
// bbc.setSignStatus(0);
// bbc.setCreateTime(Calendar.getInstance().getTime());
// bwBankCardService.saveBwBankCard(bbc, borrower.getId());
// } else {
// logger.info("银行卡信息已存在，开始修改");
//
// bbc.setBorrowerId(borrower.getId());
// bbc.setCardNo(confirmData.getBankCard());
// bbc.setBankCode(bankCode);
// bbc.setBankName(bankName);
// bbc.setPhone(confirmData.getPhone());
// bbc.setSignStatus(0);
// bbc.setUpdateTime(Calendar.getInstance().getTime());
// bwBankCardService.update(bbc);
// }
//
// return bbc;
// }
//
// /**
// * 确认借款
// *
// * @param request
// * @return
// */
// @ResponseBody
// @RequestMapping("loanWallet/confirm.do")
// public LoanWalletResp<Map<String, String>> confirm(HttpServletRequest request, HttpServletResponse response) {
// logger.set("CONFIRM");
// StopWatch stopWatch = new StopWatch();
// stopWatch.start();
// LoanWalletResp<Map<String, String>> resp = new LoanWalletResp<Map<String, String>>();
// String methodName = "LoanWalletController.confirm";
// logger.info(methodName + " start");
//
// try {
// logger.info("开始验证请求参数");
// String check = LoanWalletUtils.commonCheck(request);
//
// if (StringUtils.isNotBlank(check)) {
// resp.setCode("ERROR");
// resp.setMessage(check);
// methodEnd(stopWatch, methodName, check, resp);
// return resp;
// }
//
// String data = request.getParameter("data");
// String code = request.getParameter("code");
// String token = request.getParameter("token");
//
// ConfirmData confirmData = JSONObject.parseObject(data, ConfirmData.class);
//
// if (CommUtils.isNull(confirmData)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "repayData is null", resp);
// return resp;
// }
//
// logger.set("CONFIRM-" + confirmData.getOrderNo());
// logger.info("请求参数验证通过");
// // logger.info("data=" + data + ",code=" + code + ",token=" + token); // 去掉日志1001
//
// // 根据bankCode从REDIS中获取对应的bankName
// String bankName = BankUtil.getname(confirmData.getBankCard());
// if(CommUtils.isNull(bankName)){
// resp.setCode("ERROR");
// resp.setMessage("暂不支持，请更换银行卡");
// methodEnd(stopWatch, methodName, "bankName is null", resp);
// return resp;
// }
// String phone = confirmData.getPhone();
// String idNumber = confirmData.getCardNo();
// String bankCode = BankUtil.convertToBankCode(bankName);
//
//
// // 获取借款人信息
// BwBorrower borrower = new BwBorrower();
// borrower.setIdCard(idNumber);
// borrower.setFlag(1);// 未删除的
//
// borrower = findBwBorrowerByAttrProxy(borrower);
//
// logger.info("借款人查询结果：" + JSONObject.toJSONString(borrower));
//
// if (CommUtils.isNull(borrower)) {
// logger.info("借款人不存在，开始创建借款人...");
// String password = "123456a";
// // 创建借款人
// borrower = new BwBorrower();
// borrower.setPhone(phone);
// borrower.setPassword(CommUtils.getMD5(password.getBytes()));
// borrower.setCreateTime(Calendar.getInstance().getTime());
// borrower.setAuthStep(1);
// borrower.setFlag(1);
// borrower.setState(1);
// borrower.setChannel(11);
// borrower.setIdCard(idNumber);
// borrower.setName(confirmData.getRealName());
// borrower.setAge(LoanWalletUtils.getAgeByIdCard(idNumber));
// borrower.setSex(LoanWalletUtils.getSexByIdCard(idNumber));
// bwBorrowerService.addBwBorrower(borrower);
// logger.info("生成的借款人id：" + borrower.getId());
//
// // 发送初始密码短信
// sendPwdMsg(password, phone);
// }
//
// // 查询银行卡信息
// BwBankCard bbc = saveOrUpdateBBC(borrower, confirmData, bankCode, bankName);
//
// Map<String, String> map = new HashMap<String, String>();
//
// String userId = String.valueOf(borrower.getId());// 借款人id
// String idNo = borrower.getIdCard();// 证件号码 18位
// String accName = borrower.getName();// 姓名
// String cardNo = bbc.getCardNo();// 银行卡号
// String orderNo = confirmData.getOrderNo();
//
// map.put("url", SystemConstant.NOTIRY_URL + "/loanWallet/signLoanWalletCard.do?userId=" + userId + "&idNo="
// + idNo + "&accName=" + accName + "&cardNo=" + cardNo + "&orderNo=" + orderNo);
// resp.setCode("SUCCESS");
// resp.setMessage("成功");
// resp.setResult(map);
// } catch (Exception e) {
// logger.error(methodName + " 异常", e);
// resp.setCode("ERROR");
// resp.setMessage("系统异常，请稍后再试");
// }
// methodEnd(stopWatch, methodName, "", resp);
// return resp;
// }
//
// @ResponseBody
// @RequestMapping("loanWallet/pullBindCardStatus.do")
// public LoanWalletResp<PullBindCardStatusResData> pullBindCardStatus(HttpServletRequest request) {
// logger.set("PULLBINDCARDSTATUS");
// StopWatch stopWatch = new StopWatch();
// stopWatch.start();
// String methodName = "LoanWalletController.pullBindCardStatus";
// logger.info(methodName + " start");
// LoanWalletResp<PullBindCardStatusResData> resp = new LoanWalletResp<PullBindCardStatusResData>();
// PullBindCardStatusResData result = new PullBindCardStatusResData();
// resp.setResult(result);
// try {
// logger.info("开始验证请求参数");
// String check = LoanWalletUtils.commonCheck(request);
//
// if (StringUtils.isNotBlank(check)) {
// resp.setCode("ERROR");
// resp.setMessage(check);
// methodEnd(stopWatch, methodName, check, resp);
// return resp;
// }
//
// String data = request.getParameter("data");
// String code = request.getParameter("code");
// String token = request.getParameter("token");
// // logger.info("data=" + data + ",code=" + code + ",token=" + token); // 去掉日志1001
//
// PullBindCardStatusReqData reqData = JSONObject.parseObject(data, PullBindCardStatusReqData.class);
//
// if (CommUtils.isNull(reqData)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "PullBindCardStatusReqData is null", resp);
// return resp;
// }
// logger.set("PULLBINDCARDSTATUS-" + reqData.getOrderNo());
// logger.info("请求参数验证通过");
// // logger.info("data=" + data + ",code=" + code + ",token=" + token); // 去掉日志1001
//
// BwOrderRong bwOrderRong = new BwOrderRong();
// bwOrderRong.setThirdOrderNo(reqData.getOrderNo());
// bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
//
// if (CommUtils.isNull(bwOrderRong)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "贷款钱包订单不存在", resp);
// return resp;
// }
//
// BwOrder bwOrder = findBwOrderByIdProxy(bwOrderRong.getOrderId());
// if (CommUtils.isNull(bwOrder)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "订单不存在", resp);
// return resp;
// }
//
// BwBankCard bankCard = findBwBankCardByBoorwerIdProxy(bwOrder.getBorrowerId());
// if (CommUtils.isNull(bankCard)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "卡信息不存在", resp);
// return resp;
// }
//
// resp.setCode("SUCCESS");
// result.setOrderNo(reqData.getOrderNo());
// logger.info("原状态:" + bwOrder.getStatusId() + "---》目标状态:"
// + LoanWalletUtils.convertBindStatus(bankCard.getSignStatus()));
// if (LoanWalletUtils.convertBindStatus(bankCard.getSignStatus()) != null) {
// result.setCode(LoanWalletUtils.convertBindStatus(bankCard.getSignStatus()));
// } else {
// result.setCode("error");
// }
// } catch (Exception e) {
// logger.error(methodName + " 异常", e);
// resp.setCode("ERROR");
// resp.setMessage("系统异常，请稍后再试");
// }
// methodEnd(stopWatch, methodName, "", resp);
// return resp;
// }
//
// @ResponseBody
// @RequestMapping("loanWallet/pullRepayStatus.do")
// public LoanWalletResp<PullRepayStatusResData> pullRepayStatus(HttpServletRequest request) {
// logger.set("PULLREPAYSTATUS");
// StopWatch stopWatch = new StopWatch();
// stopWatch.start();
// String methodName = "LoanWalletController.pullRepayStatus";
// logger.info(methodName + " start");
// LoanWalletResp<PullRepayStatusResData> resp = new LoanWalletResp<PullRepayStatusResData>();
// PullRepayStatusResData result = new PullRepayStatusResData();
// resp.setResult(result);
// try {
// logger.info("开始验证请求参数");
// String check = LoanWalletUtils.commonCheck(request);
//
// if (StringUtils.isNotBlank(check)) {
// resp.setCode("ERROR");
// resp.setMessage(check);
// methodEnd(stopWatch, methodName, check, resp);
// return resp;
// }
//
// String data = request.getParameter("data");
// String code = request.getParameter("code");
// String token = request.getParameter("token");
// // logger.info("data=" + data + ",code=" + code + ",token=" + token); // 去掉日志1001
//
// PullRepayStatusReqData reqData = JSONObject.parseObject(data, PullRepayStatusReqData.class);
//
// if (CommUtils.isNull(reqData)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "PullRepayStatusReqData is null", resp);
// return resp;
// }
// logger.set("PULLREPAYSTATUS-" + reqData.getOrderNo());
// logger.info("请求参数验证通过");
// // logger.info("data=" + data + ",code=" + code + ",token=" + token); // 去掉日志1001
//
// BwOrderRong bwOrderRong = new BwOrderRong();
// bwOrderRong.setThirdOrderNo(reqData.getOrderNo());
// bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
//
// if (CommUtils.isNull(bwOrderRong)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "贷款钱包订单不存在", resp);
// return resp;
// }
//
// BwOrder bwOrder = findBwOrderByIdProxy(bwOrderRong.getOrderId());
// if (CommUtils.isNull(bwOrder)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "订单不存在", resp);
// return resp;
// }
//
// resp.setCode("SUCCESS");
// logger.info("原状态:" + bwOrder.getStatusId() + "---》目标状态:"
// + LoanWalletUtils.convertRepayStatus(bwOrder.getStatusId()));
// if (LoanWalletUtils.convertRepayStatus(bwOrder.getStatusId()) != null) {
// result.setCode(LoanWalletUtils.convertRepayStatus(bwOrder.getStatusId()));
// } else {
// result.setCode("error");
// }
// result.setOrderNo(reqData.getOrderNo());
// } catch (Exception e) {
// logger.error(methodName + " 异常", e);
// resp.setCode("ERROR");
// resp.setMessage("系统异常，请稍后再试");
// }
// methodEnd(stopWatch, methodName, "", resp);
// return resp;
// }
//
// @ResponseBody
// @RequestMapping("loanWallet/pullWithDrawStatus.do")
// public LoanWalletResp<PullWithDrawStatusResData> pullWithDrawStatus(HttpServletRequest request) {
// logger.set("PULLWITHDRAWSTATUS");
// StopWatch stopWatch = new StopWatch();
// stopWatch.start();
// String methodName = "LoanWalletController.pullWithDrawStatus";
// logger.info(methodName + " start");
// LoanWalletResp<PullWithDrawStatusResData> resp = new LoanWalletResp<PullWithDrawStatusResData>();
// PullWithDrawStatusResData result = new PullWithDrawStatusResData();
// resp.setResult(result);
// try {
// logger.info("开始验证请求参数");
// String check = LoanWalletUtils.commonCheck(request);
//
// if (StringUtils.isNotBlank(check)) {
// resp.setCode("ERROR");
// resp.setMessage(check);
// methodEnd(stopWatch, methodName, check, resp);
// return resp;
// }
//
// String data = request.getParameter("data");
// String code = request.getParameter("code");
// String token = request.getParameter("token");
// // logger.info("data=" + data + ",code=" + code + ",token=" + token); // 去掉日志1001
//
// PullWithDrawStatusReqData reqData = JSONObject.parseObject(data, PullWithDrawStatusReqData.class);
//
// if (CommUtils.isNull(reqData)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "PullWithDrawStatusReqData is null", resp);
// return resp;
// }
// logger.set("PULLWITHDRAWSTATUS-" + reqData.getOrderNo());
// // logger.info("请求参数验证通过");
// // logger.info("data=" + data + ",code=" + code + ",token=" + token); // 去掉日志1001
//
// BwOrderRong bwOrderRong = new BwOrderRong();
// bwOrderRong.setThirdOrderNo(reqData.getOrderNo());
// bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
//
// if (CommUtils.isNull(bwOrderRong)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "贷款钱包订单不存在", resp);
// return resp;
// }
//
// BwOrder bwOrder = findBwOrderByIdProxy(bwOrderRong.getOrderId());
// if (CommUtils.isNull(bwOrder)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "订单不存在", resp);
// return resp;
// }
//
// resp.setCode("SUCCESS");
// // logger.info("原状态:" + bwOrder.getStatusId() + "---》目标状态:"
// // + LoanWalletUtils.convertWithDrawStatus(bwOrder.getStatusId()));
// if (LoanWalletUtils.convertWithDrawStatus(bwOrder.getStatusId()) != null) {
// result.setCode(LoanWalletUtils.convertWithDrawStatus(bwOrder.getStatusId()));
// } else {
// result.setCode("error");
// }
// result.setUserId(reqData.getOrderNo());
// } catch (Exception e) {
// logger.error(methodName + " 异常", e);
// resp.setCode("ERROR");
// resp.setMessage("系统异常，请稍后再试");
// }
// methodEnd(stopWatch, methodName, "", resp);
// return resp;
// }
//
// @ResponseBody
// @RequestMapping("loanWallet/pullApprove.do")
// public LoanWalletResp<PullApproveResData> pullApprove(HttpServletRequest request) {
// logger.set("PULLAPPROVE");
// StopWatch stopWatch = new StopWatch();
// stopWatch.start();
// LoanWalletResp<PullApproveResData> resp = new LoanWalletResp<PullApproveResData>();
// PullApproveResData approveResData = new PullApproveResData();
// resp.setResult(approveResData);
//
// String methodName = "LoanWalletController.pullApprove";
// logger.info(methodName + " start");
//
// try {
// logger.info("开始验证请求参数");
// String check = LoanWalletUtils.commonCheck(request);
//
// if (StringUtils.isNotBlank(check)) {
// resp.setCode("ERROR");
// resp.setMessage(check);
// methodEnd(stopWatch, methodName, check, resp);
// return resp;
// }
//
// String data = request.getParameter("data");
// String code = request.getParameter("code");
// String token = request.getParameter("token");
//
// PullApproveReqData approveData = JSONObject.parseObject(data, PullApproveReqData.class);
//
// if (CommUtils.isNull(approveData)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "repayData is null", resp);
// return resp;
// }
// logger.set("PULLAPPROVE-" + approveData.getOrderNo());
// // logger.info("请求参数验证通过");
// // logger.info("data=" + data + ",code=" + code + ",token=" + token);
//
// BwOrderRong bwOrderRong = new BwOrderRong();
// bwOrderRong.setThirdOrderNo(approveData.getOrderNo());
// bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
//
// if (CommUtils.isNull(bwOrderRong)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "贷款钱包订单不存在", resp);
// return resp;
// }
//
// BwOrder bwOrder = findBwOrderByIdProxy(bwOrderRong.getOrderId());
// if (CommUtils.isNull(bwOrder)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "订单不存在", resp);
// return resp;
// }
//
// String approveStatus = LoanWalletUtils.convertApproveStatus(bwOrder.getStatusId());
// // logger.info("原状态:" + bwOrder.getStatusId() + "---》目标状态:" + approveStatus);
//
// if (StringUtils.isBlank(approveStatus)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "订单状态[" + bwOrder.getStatusId() + "]无效", resp);
// return resp;
// }
//
// if ("REJECT".equals(approveStatus)) {
// approveResData.setOrderNo(approveData.getOrderNo());
// approveResData.setConclusion(approveStatus);
// } else if ("THROUGH".equals(approveStatus)) {
// String amount = "0";
// BwOverdueRecord overdueRecord = new BwOverdueRecord();
// overdueRecord.setOrderId(bwOrder.getId());
// overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord);
// if (!CommUtils.isNull(overdueRecord)) {
// logger.info("有逾期记录,累加逾期金额");
// amount = new BigDecimal(amount).add(new BigDecimal(overdueRecord.getOverdueAccrualMoney()))
// .setScale(2, BigDecimal.ROUND_HALF_UP).toString();
// }
// approveResData.setConclusion(approveStatus);
// approveResData.setOrderNo(approveData.getOrderNo());
// approveResData.setAmount(String.valueOf(bwOrder.getBorrowAmount()));
// approveResData.setPoundage("0");
// approveResData.setRate("0.0006");
// approveResData.setOverdueFine("0.1");
// approveResData.setPoundageType("1");
// approveResData.setIsEditAmount("2");
// approveResData.setIsEditLoanDate("2");
// approveResData.setLoanDate("30");
// }
// } catch (Exception e) {
// logger.error(methodName + " 异常", e);
// resp.setCode("ERROR");
// resp.setMessage("系统异常，请稍后再试");
// }
// methodEnd(stopWatch, methodName, "", resp);
// return resp;
// }
//
// /**
// * 推送用户信息
// *
// * @param request
// * @return
// */
// @ResponseBody
// @RequestMapping("loanWallet/pushUserInfo.do")
// public LoanWalletResp<Object> pushUserInfo(HttpServletRequest request) {
// logger.set("PUSHUSERINFO");
// StopWatch stopWatch = new StopWatch();
// stopWatch.start();
// LoanWalletResp<Object> resp = new LoanWalletResp<Object>();
// String methodName = "LoanWalletController.pushUserInfo";
// logger.info(methodName + " start");
//
// try {
// logger.info("开始验证请求参数");
// String check = LoanWalletUtils.commonCheck(request);
//
// if (StringUtils.isNotBlank(check)) {
// resp.setCode("ERROR");
// resp.setMessage(check);
// methodEnd(stopWatch, methodName, check, resp);
// return resp;
// }
//
// String data = request.getParameter("data");
// String code = request.getParameter("code");
// String token = request.getParameter("token");
//
// logger.info("开始验证token,token=" + token + "");
// logger.info("key:" + LoanWalletConstant.KEY);
// boolean isEqual = LoanWalletUtils.tokenIsEqual(data, request.getParameter("token"));
// if (!isEqual) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "token验证失败", resp);
// return resp;
// }
//
// // logger.info("请求参数验证通过");
//
// LoanWalletUserData userData = JSONObject.parseObject(data, LoanWalletUserData.class);
//
// if (CommUtils.isNull(userData)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "repayData is null", resp);
// return resp;
// }
//
// String cardFront = userData.getCardFront();
// String cardVerso = userData.getCardVerso();
// List<String> cards = userData.getCardList();
// List<String> cardList = new ArrayList<String>();
// userData.setCardFront(cardFront.replaceAll(" ", "\\+"));
// userData.setCardVerso(cardVerso.replaceAll(" ", "\\+"));
// userData.setCardList(cardList);
//
// for (String card : cards) {
// cardList.add(card.replaceAll(" ", "\\+"));
// }
//
// // logger.info("token验证通过");
//
// logger.set("PUSHUSERINFO-" + userData.getOrderNo());
// // logger.info("请求参数验证通过");
//
// // logger.info("data=" + data + ",code=" + code + ",token=" + token);
//
// // 放入REDIS
// logger.info("准备放入redis-list[" + USER_INFO_REDIS + "]");
// Long resultNum = RedisUtils.rpush(USER_INFO_REDIS, data);
// if (!CommUtils.isNull(resultNum)) {
// logger.info("订单基本信息存入redis-list[" + USER_INFO_REDIS + "]成功");
// resp.setCode("SUCCESS");
// resp.setMessage("成功");
// } else {
// logger.info("订单基本信息存入redis-list[" + USER_INFO_REDIS + "]失败");
// resp.setCode("ERROR");
// resp.setMessage("系统异常，请稍后再试");
// }
// } catch (Exception e) {
// logger.error(methodName + " 异常", e);
// resp.setCode("ERROR");
// resp.setMessage("系统异常，请稍后再试");
// }
// logger.info(methodName + " end");
// logger.remove();
// return resp;
// }
//
// /**
// * 还款接口
// *
// * @param request
// * @return
// */
// @ResponseBody
// @RequestMapping("loanWallet/repayment.do")
// public LoanWalletResp<Object> repayment(HttpServletRequest request) {
// logger.set("REPAYMENT");
// StopWatch stopWatch = new StopWatch();
// stopWatch.start();
// LoanWalletResp<Object> resp = new LoanWalletResp<Object>();
// String methodName = "LoanWalletController.repayment";
// // logger.info(methodName + " start");
//
// try {
// // logger.info("开始验证请求参数");
// String check = LoanWalletUtils.commonCheck(request);
//
// if (StringUtils.isNotBlank(check)) {
// resp.setCode("ERROR");
// resp.setMessage(check);
// methodEnd(stopWatch, methodName, check, resp);
// return resp;
// }
//
// String data = request.getParameter("data");
// String code = request.getParameter("code");
// String token = request.getParameter("token");
//
// RepayData repayData = JSONObject.parseObject(data, RepayData.class);
//
// if (CommUtils.isNull(repayData)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "repayData is null", resp);
// return resp;
// }
//
// logger.set("REPAYMENT-" + repayData.getOrderNo());
// // logger.info("请求参数验证通过");
// // logger.info("data=" + data + ",code=" + code + ",token=" + token);
//
// BwOrderRong bwOrderRong = new BwOrderRong();
// bwOrderRong.setThirdOrderNo(repayData.getOrderNo());
// bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
//
// if (CommUtils.isNull(bwOrderRong)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "贷款钱包工单为空", resp);
// return resp;
// }
//
// BwOrder bwOrder = findBwOrderByIdProxy(bwOrderRong.getOrderId());
//
// if (CommUtils.isNull(bwOrder)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "工单为空", resp);
// return resp;
// }
//
// Long borrowerId = bwOrder.getBorrowerId();
// BwBankCard bwBankCard = findBwBankCardByBoorwerIdProxy(borrowerId);
//
// if (CommUtils.isNull(bwBankCard)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// methodEnd(stopWatch, methodName, "银行卡信息为空", resp);
// return resp;
// }
//
// resp = commonRepay(bwOrder, bwBankCard, "repay");
//
// if ("200".equals(resp.getCode())) {
// RedisUtils.lpush("notify:orderState", String.valueOf(bwOrder.getId()));
// }
// } catch (Exception e) {
// logger.error(methodName + " 异常", e);
// resp.setCode("ERROR");
// resp.setMessage("系统异常，请稍后再试");
// }
//
// methodEnd(stopWatch, methodName, "", resp);
// return resp;
// }
//
// private LoanWalletResp<Object> commonRepay(BwOrder bwOrder, BwBankCard bwBankCard, String type) throws Exception {
// LoanWalletResp<Object> resp = new LoanWalletResp<Object>();
//
// String orderId = String.valueOf(bwOrder.getId());
//
// if (RedisUtils.hexists(LOAN_WALLET_XUDAI, orderId)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// logger.info("此工单正在续贷中");
// return resp;
// }
//
// if (RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// logger.info("此工单正在宝付支付中");
// return resp;
// }
//
// if (RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// logger.info("此工单正在连连支付中");
// return resp;
// }
//
// Long statusId = bwOrder.getStatusId();// 工单状态
// logger.info("工单状态:" + statusId);
// if (!(statusId.intValue() == 9 || statusId.intValue() == 13)) {
// resp.setCode("ERROR");
// resp.setMessage("工单只有还款中或逾期中才可还款");
// logger.info("工单只有还款中或逾期中才可还款");
// return resp;
// }
//
// BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
// bwRepaymentPlan.setOrderId(bwOrder.getId());
// bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
// if (CommUtils.isNull(bwRepaymentPlan)) {
// resp.setCode("ERROR");
// resp.setMessage("系统异常");
// logger.info("没有符合条件的还款计划");
// return resp;
// }
//
// // 判断该用户是否签约
// Long borrowerId = bwOrder.getBorrowerId();
// logger.info("开始调用连连签约查询接口,borrowerId=" + borrowerId + ",cardNo=" + bwBankCard.getCardNo());
// CardQueryResult cardQueryResult = LianLianPayService.cardBindQuery(borrowerId.toString(),
// bwBankCard.getCardNo());
//
// logger.info("结束调用连连签约查询接口,cardQueryResult=" + JSONObject.toJSONString(cardQueryResult));
//
// if (CommUtils.isNull(cardQueryResult)) {
// resp.setCode("ERROR");
// resp.setMessage("未签约");
// return resp;
// }
//
// if (!"0000".equals(cardQueryResult.getRet_code())) {
// resp.setCode("ERROR");
// resp.setMessage(LoanWalletUtils.convertLian2Msg(cardQueryResult.getRet_code()));
// logger.info("调用连连签约查询接口返回结果失败，ret_code != 0000");
// return resp;
// }
//
// List<Agreement> agreements = cardQueryResult.getAgreement_list();
// if (CommUtils.isNull(agreements)) {
// resp.setCode("ERROR");
// resp.setMessage("未签约");
// logger.info("调用连连签约查询接口返回结果失败,Agreement_list is null");
// return resp;
// }
//
// String agreeNo = agreements.get(0).getNo_agree();
//
// logger.info("调用连连签约查询接口返回结果成功，连连支付协议号:" + agreeNo);
//
// List<RepaymentPlan> repays = new ArrayList<RepaymentPlan>();
// SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//
// String amount = String.valueOf(bwRepaymentPlan.getRealityRepayMoney());
// BwOverdueRecord overdueRecord = new BwOverdueRecord();
// overdueRecord.setOrderId(bwOrder.getId());
// overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord);
// if (!CommUtils.isNull(overdueRecord)) {
// amount = new BigDecimal(amount).add(new BigDecimal(overdueRecord.getOverdueAccrualMoney()))
// .setScale(2, BigDecimal.ROUND_HALF_UP).toString();
// }
// logger.info("amount=" + amount);
//
// RepaymentPlan repay = new RepaymentPlan();
// repay.setAmount(amount);
// repay.setDate(dateFormat2.format(bwRepaymentPlan.getRepayTime()));
// repays.add(repay);
//
// BwBorrower bwBorrower = findBwBorrowerByIdProxy(bwOrder.getBorrowerId());
// SignalLess signalLess = new SignalLess();
// signalLess.setAcct_name(bwBorrower.getName());
// // signalLess.setApp_request(app_request);
// signalLess.setCard_no(bwBankCard.getCardNo());
// signalLess.setId_no(bwBorrower.getIdCard());
// signalLess.setNo_agree(agreeNo);
// signalLess.setUser_id(borrowerId.toString());
//
// logger.info("开始调用连连授权接口,signalLess=" + JSONObject.toJSONString(signalLess) + ",orderNo=" + bwOrder.getOrderNo()
// + ",repays=" + JSONObject.toJSONString(repays));
// PlanResult planResult = LianLianPayService.sigalAccreditPay(signalLess, bwOrder.getOrderNo(), repays);
// logger.info("结束调用连连授权接口，planResult=" + JSONObject.toJSONString(planResult));
//
// if (CommUtils.isNull(planResult)) {
// resp.setCode("ERROR");
// resp.setMessage("支付授权失败");
// logger.info("调用连连授权接口返回结果为空");
// return resp;
// }
//
// if (!"0000".equals(planResult.getRet_code())) {
// resp.setCode("ERROR");
// resp.setMessage(LoanWalletUtils.convertLian2Msg(planResult.getRet_code()));
// logger.info("调用连连授权接口返回结果失败,ret_code != 0000");
// return resp;
// }
//
// logger.info("调用连连授权接口成功");
//
// // 支付
// RepayRequest repayRequest = new RepayRequest();
// repayRequest.setNo_order(GenerateSerialNumber.getSerialNumber().substring(8) + bwOrder.getId());
// repayRequest.setUser_id(borrowerId.toString());
// repayRequest.setNo_agree(agreeNo);
// repayRequest.setDt_order(dateFormat.format(new Date()));
// repayRequest.setName_goods("易秒贷");
// repayRequest.setMoney_order(amount);
// // 测试
// // repayRequest.setMoney_order("0.01");
// repayRequest.setSchedule_repayment_date(repays.get(0).getDate());
// repayRequest.setRepayment_no(bwOrder.getOrderNo());
// repayRequest.setUser_info_bind_phone(bwBorrower.getPhone());
// repayRequest.setUser_info_dt_register(dateFormat.format(bwBorrower.getCreateTime()));
// repayRequest.setUser_info_full_name(bwBorrower.getName());
// repayRequest.setUser_info_id_no(bwBorrower.getIdCard());
// repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/loanWallet/repaymentNotify.do");
//
// // 存入连连redis中，有效时间15分钟
// if (!RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId())) {
// RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId(), bwOrder.getId().toString(), 900);
// logger.info("存redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId().toString() + "]");
// }
//
// logger.info("开始调用连连支付接口，repayRequest=" + JSONObject.toJSONString(repayRequest));
// RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);
// logger.info("结束调用连连支付接口，repaymentResult=" + JSONObject.toJSONString(repaymentResult));
//
// if (CommUtils.isNull(repaymentResult)) {
// resp.setCode("ERROR");
// resp.setMessage("支付失败");
// logger.info("调用连连支付接口返回结果为空");
// return resp;
// }
//
// if (!"0000".equals(repaymentResult.getRet_code()) && !"1003".equals(repaymentResult.getRet_code())) {
// resp.setCode("ERROR");
// resp.setMessage(LoanWalletUtils.convertLian2Msg(repaymentResult.getRet_code()) == null
// ? repaymentResult.getRet_msg() : LoanWalletUtils.convertLian2Msg(repaymentResult.getRet_code()));
// // 删除连连REDIS
// logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]");
// RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId());
// logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]成功");
// logger.info("调用连连支付接口返回结果失败,ret_code != 0000");
// return resp;
// }
// resp.setCode("SUCCESS");
// resp.setMessage("支付成功");
//
// return resp;
// }
//
// /**
// * 还款回调
// *
// * @param request
// */
// @ResponseBody
// @RequestMapping(value = "loanWallet/repaymentNotify.do")
// public NotifyNotice repaymentNotify(HttpServletRequest request) {
// logger.set("REPAYNOTIFY");
// String methodName = "LoanWalletController.repaymentNotify";
// logger.info(methodName + " start");
// NotifyNotice notice = new NotifyNotice();
//
// try {
// NotifyResult notifyResult = getNotifyResult(request);
// logger.info("notifyResult=" + JSONObject.toJSONString(notifyResult));
//
// if (CommUtils.isNull(notifyResult)) {
// notice.setRet_code("101");
// notice.setRet_msg("异步还款通知为空");
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// return notice;
// }
//
// if (StringUtils.isBlank(notifyResult.getSign())) {
// notice.setRet_code("101");
// notice.setRet_msg("异步还款通知签名为空");
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// return notice;
// }
//
// if (StringUtils.isBlank(notifyResult.getNo_order())) {
// notice.setRet_code("101");
// notice.setRet_msg("工单id为空");
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// return notice;
// }
//
// logger.info("开始验证签名...");
//
// boolean checkSign = checkLianLianSign(notifyResult);
// if (!checkSign) {
// notice.setRet_code("101");
// notice.setRet_msg("验签未通过");
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// return notice;
// }
// String orderId = notifyResult.getNo_order().substring(20);
//
// // 验证是否成功
// if (!"SUCCESS".equals(notifyResult.getResult_pay())) {
// notice.setRet_code("102");
// notice.setRet_msg("交易失败");
//
// try {
// // TODO
// // 还款状态反馈
// } catch (Exception e) {
// logger.error("调用贷款钱包还款状态反馈接口异常", e);
// }
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// return notice;
// }
//
// BwOrderRong bwOrderRong = new BwOrderRong();
// bwOrderRong.setOrderId(Long.parseLong(orderId));
// bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
// if (CommUtils.isNull(bwOrderRong)) {
// notice.setRet_code("101");
// notice.setRet_msg("贷款钱包工单不存在");
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// return notice;
// }
//
// logger.set("REPAYNOTIFY-" + bwOrderRong.getThirdOrderNo());
//
// BwOrder order = findBwOrderByIdProxy(Long.parseLong(orderId));
//
// if (CommUtils.isNull(order)) {
// notice.setRet_code("101");
// notice.setRet_msg("工单不存在");
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// logger.remove();
// return notice;
// }
//
// if (order.getStatusId().intValue() == 6) {
// notice.setRet_code("0000");
// notice.setRet_msg("交易成功");
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// logger.remove();
// return notice;
// }
//
// // 查询还款人信息
// BwBorrower borrower = findBwBorrowerByIdProxy(order.getBorrowerId());
// if (CommUtils.isNull(borrower)) {
// notice.setRet_code("101");
// notice.setRet_msg("借款人为空");
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// logger.remove();
// return notice;
// }
//
// // 查询银行卡信息
// BwBankCard card = findBwBankCardByBoorwerIdProxy(borrower.getId());
// if (CommUtils.isNull(card)) {
// notice.setRet_code("101");
// notice.setRet_msg("银行卡信息为空");
// logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
// logger.remove();
// return notice;
// }
//
// // 记录流水
// logger.info("记录交易流水");
// int platFormInt = savePlatformRecord(notifyResult, card, borrower, order);
// logger.info("记录交易流水结束,新增条数：" + platFormInt);
//
// // 删除连连REDIS
// logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]");
// RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId());
// logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]成功");
//
// // 修改订单状态
// bwRepaymentService.updateOrderStatus(order.getId());
//
// try {
// RedisUtils.lpush("notify:orderState", String.valueOf(order.getId()));
// } catch (Exception e) {
// logger.error("调用贷款钱包反馈接口异常", e);
// }
//
// notice.setRet_code("0000");
// notice.setRet_msg("交易成功");
// } catch (Exception e) {
// logger.error("还款回调异常", e);
// notice.setRet_code("103");
// notice.setRet_msg("交易失败");
// }
//
// logger.info(methodName + " end,resp=" + JSONObject.toJSONString(notice));
// logger.remove();
// return notice;
// }
//
// private boolean checkLianLianSign(NotifyResult notifyResult) {
// Map<String, String> map = new HashMap<>();
// map.put("oid_partner", notifyResult.getOid_partner());
// map.put("sign_type", notifyResult.getSign_type());
// map.put("dt_order", notifyResult.getDt_order());
// map.put("no_order", notifyResult.getNo_order());
// map.put("oid_paybill", notifyResult.getOid_paybill());
// map.put("money_order", notifyResult.getMoney_order());
// map.put("result_pay", notifyResult.getResult_pay());
// map.put("settle_date", notifyResult.getSettle_date());
// map.put("info_order", notifyResult.getInfo_order());
// map.put("pay_type", notifyResult.getPay_type());
// map.put("bank_code", notifyResult.getBank_code());
// map.put("no_agree", notifyResult.getNo_agree());
// map.put("id_type", notifyResult.getId_type());
// map.put("id_no", notifyResult.getId_no());
// map.put("acct_name", notifyResult.getAcct_name());
// map.put("card_no", notifyResult.getCard_no());
// String osign = RSAUtil.sortParams(map);
//
// return RSAUtil.checksign(SystemConstant.YT_PUB_KEY, osign, notifyResult.getSign());
// }
//
// private NotifyResult getNotifyResult(HttpServletRequest request) throws Exception {
// NotifyResult notifyResult = null;
//
// if (CommUtils.isNull(request)) {
// return null;
// }
//
// request.setCharacterEncoding("utf-8");
//
// BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
// StringBuilder sbuff = new StringBuilder();
// String tmp = "";
// while ((tmp = br.readLine()) != null) {
// sbuff.append(tmp);
// }
//
// br.close();
//
// String data = sbuff.toString();
//
// if (StringUtils.isBlank(data)) {
// return null;
// }
//
// notifyResult = JSONObject.parseObject(data, NotifyResult.class);
//
// return notifyResult;
// }
//
// private int savePlatformRecord(NotifyResult notifyResult, BwBankCard card, BwBorrower borrower, BwOrder order) {
// BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
// bwPlatformRecord.setTradeNo(notifyResult.getOid_paybill());
// bwPlatformRecord.setTradeAmount(Double.valueOf(notifyResult.getMoney_order()));// 交易金额
// bwPlatformRecord.setTradeType(1);// 1划拨2转账
// bwPlatformRecord.setOutAccount(card.getCardNo());
// bwPlatformRecord.setOutName(borrower.getName());
// bwPlatformRecord.setInAccount("上海水象金融信息服务有限公司-连连支付");
// bwPlatformRecord.setInName("上海水象金融信息服务有限公司-连连支付");
// bwPlatformRecord.setOrderId(order.getId());
// bwPlatformRecord.setTradeTime(new Date());
// bwPlatformRecord.setTradeRemark("连连还款扣款");
// bwPlatformRecord.setTradeChannel(3);// 连连支付
// int platFormInt = bwPlatformRecordService.saveBwPlatFormRecord(bwPlatformRecord);
// return platFormInt;
// }
//
// private void methodEnd(StopWatch stopWatch, String methodName, String message, LoanWalletResp<?> resp) {
// stopWatch.stop();
// logger.info(methodName + " end,costTime=" + stopWatch.getTotalTimeMillis() + "," + message + ",resp=" + resp);
// logger.remove();
// }
//
// private BwOrderRong findBwOrderRongByAttrProxy(BwOrderRong bwOrderRong) {
// // logger.info("开始查询贷款钱包工单,bwOrderRong=" + JSONObject.toJSONString(bwOrderRong));
// bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
// // logger.info("结束查询贷款钱包工单,bwOrderRong=" + JSONObject.toJSONString(bwOrderRong));
// return bwOrderRong;
// }
//
// private BwOrder findBwOrderByIdProxy(Long orderId) {
// // logger.info("开始查询工单,orderId=" + orderId);
// BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
// // logger.info("结束查询工单,bwOrder=" + JSONObject.toJSONString(bwOrder));
// return bwOrder;
// }
//
// private BwBankCard findBwBankCardByBoorwerIdProxy(Long borrowerId) {
// // logger.info("开始查询银行卡信息,borrowerId=" + borrowerId);
// BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
// // logger.info("结束查询银行卡信息,bwBankCard=" + JSONObject.toJSONString(bwBankCard));
// return bwBankCard;
// }
//
// private BwRepaymentPlan findBwRepaymentPlanByAttrProxy(BwRepaymentPlan bwRepaymentPlan) {
// // logger.info("开始查询还款计划,bwRepaymentPlan=" + JSONObject.toJSONString(bwRepaymentPlan));
// bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
// // logger.info("结束查询还款计划,bwRepaymentPlan=" + JSONObject.toJSONString(bwRepaymentPlan));
// return bwRepaymentPlan;
// }
//
// private BwOverdueRecord findBwOverdueRecordByAttrProxy(BwOverdueRecord overdueRecord) {
// // logger.info("开始查询逾期记录,overdueRecord=" + JSONObject.toJSONString(overdueRecord));
// overdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(overdueRecord);
// // logger.info("结束查询逾期记录,overdueRecord=" + JSONObject.toJSONString(overdueRecord));
// return overdueRecord;
// }
//
// private BwBorrower findBwBorrowerByIdProxy(Long borrowerId) {
// // logger.info("开始查询借款人信息,borrowerId=" + borrowerId);
// BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
// // logger.info("结束查询借款人信息,borrower=" + JSONObject.toJSONString(bwBorrower));
// return bwBorrower;
// }
//
// private BwBankCard findBwBankCardByAttrProxy(BwBankCard bwBankCard) {
// // logger.info("开始查询银行卡信息:bwBankCard=" + JSONObject.toJSONString(bwBankCard));
// bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
// // logger.info("银行卡信息查询结果：bwBankCard=" + JSONObject.toJSONString(bwBankCard));
// return bwBankCard;
// }
//
// private BwBorrower findBwBorrowerByAttrProxy(BwBorrower borrower) {
// // logger.info("开始查询借款人信息,borrower=" + JSONObject.toJSONString(borrower));
// borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
// // logger.info("结束查询借款人信息,borrower=" + JSONObject.toJSONString(borrower));
// return borrower;
// }
//
// private void sendPwdMsg(String password, String phone) {
// // 发送短信
// try {
// String message = LoanWalletUtils.getMsg(password);
// // MsgReqData msg = new MsgReqData();
// // msg.setPhone(phone);
// // msg.setMsg(message);
// // msg.setType("0");
// // logger.info("开始发送密码短信,phone=" + phone);
// // Response<Object> response = BeadWalletSendMsgService.sendMsg(msg);
// // logger.info("发送完成,发送结果：" + JSONObject.toJSONString(response));
// // boolean bo = sendMessageCommonService.commonSendMessage(phone, message);
// // if (bo) {
// // logger.info("发送成功");
// // }else {
// // logger.info("发送失败");
// // }
// MessageDto messageDto = new MessageDto();
// messageDto.setBusinessScenario("1");
// messageDto.setPhone(phone);
// messageDto.setMsg(message);
// messageDto.setType("1");
// RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
// } catch (Exception e) {
// logger.error("发送短信异常:", e);
// }
// }
//
// public static void main(String[] args) {
// String string = "421127199012195614";
// System.out.println(BankUtil.getname(string));
// }
// }