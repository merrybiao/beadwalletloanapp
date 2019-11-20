//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.waterelephant.entity.*;
//import com.waterelephant.service.*;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import com.waterelephant.utils.*;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.service.baofu.entity.BindCardRequest;
//import com.beadwallet.service.baofu.entity.BindCardResult;
//import com.beadwallet.service.baofu.service.BaofuServiceSDK;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaCommonRequest;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaResponse;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.XianJinBaiKaService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.xianJinBaiKa.XianJinBaiKaConstant;
//import com.waterelephant.sxyDrainage.utils.xianJinBaiKa.XianJinBaiKaUtil;
//import com.waterelephant.third.service.ThirdCommonService;
//
///**
// * Module: (code:xjbk)
// * <p>
// * XianJinBaiKaServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//@Service
//public class XianJinBaiKaServiceImpl implements XianJinBaiKaService {
//    private Logger logger = LoggerFactory.getLogger(XianJinBaiKaServiceImpl.class);
//    private String channelIdStr = XianJinBaiKaConstant.CHANNELID;
//
//    @Autowired
//    private IBwBorrowerService bwBorrowerService;
//    @Autowired
//    private BwOrderRongService bwOrderRongService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private IBwBankCardService bwBankCardService;
//    @Autowired
//    private IBwRepaymentPlanService bwRepaymentPlanService;
//    @Autowired
//    private BwProductDictionaryService bwProductDictionaryService;
//    @Autowired
//    private BwOverdueRecordService bwOverdueRecordService;
//    @Autowired
//    private IBwAdjunctService bwAdjunctService;
//    @Autowired
//    private CommonService commonService;
//    @Autowired
//    private BwRejectRecordService bwRejectRecordService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//    @Autowired
//    private BwBankCardChangeService bwBankCardChangeService;
//    @Autowired
//    private BwOrderAuthService bwOrderAuthService;
//
//    @Override
//    public XianJinBaiKaResponse checkUser(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        logger.info(sessionId + ":开始checkUser method:{}", JSON.toJSONString(xianJinBaiKaCommonRequest));
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        try {
//            Map<String, Object> map = new HashMap<>(16);
//            String userName = xianJinBaiKaCommonRequest.getUser_name();
//            String userPhone = xianJinBaiKaCommonRequest.getUser_phone();
//            String userIdCard = xianJinBaiKaCommonRequest.getUser_idcard();
//
//            DrainageRsp drainageRsp = commonService.checkUser(sessionId, userName, userPhone, userIdCard);
//            String code = drainageRsp.getCode();
//            // 验证数据是否为空
//            if ("1002".equals(code)) {
//                return failReturn(sessionId, map, drainageRsp.getMessage());
//            }
//            // 验证是否符合申请要求
//            if ("2001".equals(code)) {
//                map.put("result", 403);
//                return failReturn(sessionId, map, drainageRsp.getMessage());
//            }
//            if ("2002".equals(code)) {
//                map.put("result", 401);
//                return failReturn(sessionId, map, drainageRsp.getMessage());
//            }
//            if ("2004".equals(code)) {
//                map.put("result", 301);
//                return failReturn(sessionId, map, drainageRsp.getMessage());
//            }
//
//            if (userPhone.startsWith("170")) {
//                map.put("result", 505);
//                return failReturn(sessionId, map, userPhone + "为虚拟运营商号段！");
//            }
//
//            userIdCard = userIdCard.replace("*", "%");
//            userPhone = userPhone.replace("*", "%");
//            if ("2003".equals(code)) {
//                BwBorrower borrower = bwBorrowerService.oldUserFilter2(userName, userPhone, userIdCard);
//                BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());
//                Date rejectDate = record.getCreateTime();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String canLoanTime = sdf.format(rejectDate.getTime() + 24 * 60 * 60 * 1000 * 7);
//                map.put("result", 505);
//                map.put("can_loan_time", canLoanTime);
//                return failReturn(sessionId, map, drainageRsp.getMessage());
//            }
//            if ("1000".equals(code)) {
//                return failReturn(sessionId, map, "请求失败");
//            }
//
//            // 设置默认产品
//            BwProductDictionary dictionary = bwProductDictionaryService.findById(Long.valueOf(XianJinBaiKaConstant.PRODUCTID));
//            Integer maxAmount = dictionary.getMaxAmount();
//
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//            xianJinBaiKaResponse.setMessage("success");
//            // 借款权限
//            map.put("result", 200);
//            // 可贷额度，单位: 分
//            map.put("amount", maxAmount * 100);
//            List<Integer> list = new ArrayList<>();
//            list.add(28);
//            // 可贷期限。如: [7,14,30]
//            map.put("terms", list);
//            // 贷款期限单位。1:按天; 2：按月; 3：按年
//            map.put("term_type", 1);
//            // 0：标准流程；1：简化流程
//            map.put("loan_mode", 0);
//            xianJinBaiKaResponse.setResponse(map);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行checkUser method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束checkUser：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    /**
//     * 直接返回失败信息
//     *
//     * @param sessionId 时间戳
//     * @param map       返回参数
//     * @param msg       错误原因
//     * @return XianJinBaiKaResponse
//     */
//    private XianJinBaiKaResponse failReturn(long sessionId, Map<String, Object> map, String msg) {
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//        xianJinBaiKaResponse.setMessage(msg);
//        if (null != map && map.size() > 0) {
//            xianJinBaiKaResponse.setResponse(map);
//        }
//        logger.info(sessionId + "-结束XianJinBaiKaServiceImpl-" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse getValidBankList(long sessionId) {
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        logger.info(sessionId + ":开始getValidBankList method");
//        try {
//            List<Map<String, String>> list = new ArrayList<>();
//            Map<String, String> map;
//            for (String string : XianJinBaiKaUtil.baoFuBankCard) {
//                map = new HashMap<>(16);
//                map.put("bank_name", string);
//                map.put("bank_code", XianJinBaiKaUtil.convertToBankCode(string));
//                map.put("bank_title", XianJinBaiKaUtil.bfMap.get(string));
//                list.add(map);
//            }
//
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//            xianJinBaiKaResponse.setMessage("success");
//            xianJinBaiKaResponse.setResponse(list);
//
//        } catch (Exception e) {
//            logger.error(sessionId + ":执行getValidBankList method:{}", e.getMessage());
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求异常");
//        }
//        logger.info(sessionId + ":结束getValidBankList method:{}", JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse updateApplyBindCard(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        logger.info(sessionId + ":开始applyBindCard method:{}", JSON.toJSONString(xianJinBaiKaCommonRequest));
//        Map<String, Object> map = new HashMap<>(16);
//        try {
//            String name = xianJinBaiKaCommonRequest.getUser_name();
//            String idCard = xianJinBaiKaCommonRequest.getUser_idcard();
//            String bankCard = xianJinBaiKaCommonRequest.getCard_number();
//            String cardPhone = xianJinBaiKaCommonRequest.getCard_phone();
//            String regPhone = xianJinBaiKaCommonRequest.getUser_phone();
//            String xjbkBankCode = xianJinBaiKaCommonRequest.getBank_code();
//
//            // 验证参数
//            if (StringUtils.isBlank(name)) {
//                return failReturn(sessionId, map, "姓名为空");
//            }
//            if (StringUtils.isBlank(idCard)) {
//                return failReturn(sessionId, map, "手机号码为空");
//            }
//            if (StringUtils.isBlank(bankCard)) {
//                return failReturn(sessionId, map, "银行卡号码为空");
//            }
//            if (StringUtils.isBlank(cardPhone)) {
//                return failReturn(sessionId, map, "银行预留手机号为空");
//            }
//            if (StringUtils.isBlank(regPhone)) {
//                return failReturn(sessionId, map, "用户准入手机号为空");
//            }
//
//            // 新增或更新借款人
//            BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, name, idCard, regPhone,
//                    NumberUtils.toInt(channelIdStr));
//            long borrowerId = borrower.getId();
//            logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//
//            boolean flag = false;
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
//            // 2018.05.03 修改code和name未存(1.0.1)
//            String cardCode = XianJinBaiKaUtil.convertXjbkBankCodeToFuYou(xjbkBankCode);
//            String cardName = RedisUtils.hget("baofoo_withhold_support_bank", cardCode);
//            logger.info("cardCode=" + cardCode + ",cardName=" + cardName);
//            if (bwBankCard != null && bwBankCard.getSignStatus() == 1) {
//                if (bankCard.equals(bwBankCard.getCardNo())) {
//                    bwBankCard.setUpdateTime(new Date());
//                    bwBankCardService.updateBwBankCard(bwBankCard);
//
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage("success");
//                    map.put("bind_status", "200");
//                    map.put("remark", "success");
//                    xianJinBaiKaResponse.setResponse(map);
//                    logger.info(sessionId + ":结束applyBindCard method:{}", JSON.toJSONString(xianJinBaiKaResponse));
//                    return xianJinBaiKaResponse;
//                } else {
//                    logger.info("进入换卡接口,旧卡：" + bwBankCard.getCardNo() + ";新卡：" + bankCard);
//                    flag = true;
//                }
//            } else if (bwBankCard != null) {
//                bwBankCard.setBankName(cardName);
//                bwBankCard.setBankCode(cardCode);
//                bwBankCard.setCardNo(bankCard);
//                bwBankCard.setPhone(cardPhone);
//                bwBankCard.setSignStatus(0);
//                bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//                bwBankCardService.updateBwBankCard(bwBankCard);
//            } else {
//                bwBankCard = new BwBankCard();
//                bwBankCard.setBankName(cardName);
//                bwBankCard.setBankCode(cardCode);
//                bwBankCard.setCardNo(bankCard);
//                bwBankCard.setPhone(cardPhone);
//                bwBankCard.setBorrowerId(borrowerId);
//                bwBankCard.setSignStatus(0);
//                bwBankCard.setCreateTime(Calendar.getInstance().getTime());
//                bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//                bwBankCardService.saveBwBankCard(bwBankCard, borrowerId);
//            }
//
//            // 开始绑卡
//            // 2018.05.03 修改绑卡编码转换(1.0.0)
//            String bankCode = XianJinBaiKaUtil.convertXjbkBankCodeToBaofu(xjbkBankCode);
//            BindCardRequest bcr = new BindCardRequest();
//            bcr.setPay_code(bankCode);
//            bcr.setBorrowerId(borrowerId + "");
//            bcr.setId_card(idCard);
//            bcr.setMobile(cardPhone);
//            bcr.setId_holder(name);
//            bcr.setAcc_no(bankCard);
//            BindCardResult bindCardResult = BaofuServiceSDK.directBind(bcr);
//            if (null != bindCardResult) {
//                if ("0000".equals(bindCardResult.getResp_code())) {
//                    bwBankCard.setSignStatus(1);
//                    if (flag) {
//                        BwBankCardChange bwBankCardChange = new BwBankCardChange();
//                        logger.info(bwBankCard.getCardNo());
//                        bwBankCardChange.setBorrowerId(borrowerId);
//                        bwBankCardChange.setBankName(bwBankCard.getBankName());
//                        bwBankCardChange.setBankCode(bwBankCard.getBankCode());
//                        bwBankCardChange.setPhone(bwBankCard.getPhone());
//                        bwBankCardChange.setCardNo(bwBankCard.getCardNo());
//                        bwBankCardChange.setCreatedTime(new Date());
//                        Integer insertNumber = bwBankCardChangeService.insertByAtt(bwBankCardChange);
//                        if (insertNumber > 0) {
//                            logger.info("成功插入" + insertNumber + "借款人" + borrowerId + "换卡信息成功,卡号为：" + bwBankCard.getCardNo());
//                        }
//                    }
//                    bwBankCard.setBankCode(cardCode);
//                    bwBankCard.setCardNo(bankCard);
//                    bwBankCard.setBankName(cardName);
//                    bwBankCard.setPhone(cardPhone);
//                    bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//                    bwBankCardService.updateBwBankCard(bwBankCard);
//
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage("success");
//                    map.put("bind_status", "200");
//                    map.put("remark", "success");
//                    xianJinBaiKaResponse.setResponse(map);
//
//                } else {
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage("success");
//                    map.put("bind_status", "505");
//                    // 2018.05.03 修改宝付返回为null的(1.0.0)
//                    map.put("remark", bindCardResult.getResp_msg() == null ? "绑卡失败,请稍后重试" : bindCardResult.getResp_msg());
//                    xianJinBaiKaResponse.setResponse(map);
//
//                }
//            } else {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                xianJinBaiKaResponse.setMessage("success");
//                map.put("bind_status", "505");
//                map.put("remark", "绑卡失败");
//                xianJinBaiKaResponse.setResponse(map);
//
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + ":执行applyBindCard method:{}", e.getMessage());
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求异常");
//        }
//        logger.info(sessionId + ":结束applyBindCard method:{}", JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse getUserBindBankCardList(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        logger.info(sessionId + "：开始getBindCard method：" + JSON.toJSONString(xianJinBaiKaCommonRequest));
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        try {
//            Map<String, Object> map = new HashMap<>(16);
//            String userIdcard = xianJinBaiKaCommonRequest.getUser_idcard();
//            String userName = xianJinBaiKaCommonRequest.getUser_name();
//            String userPhone = xianJinBaiKaCommonRequest.getUser_phone();
//
//            if (StringUtils.isBlank(userIdcard) || StringUtils.isBlank(userName) || StringUtils.isBlank(userPhone)) {
//                return failReturn(sessionId, map, "传入参数为空！");
//            }
//            BwBorrower bwBorrower = new BwBorrower();
//            bwBorrower.setPhone(userPhone);
//            bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//            if (null == bwBorrower) {
//                return failReturn(sessionId, map, "用户不存在！");
//            }
//
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwBorrower.getId());
//
//            if (null != bwBankCard && 4 == bwBankCard.getSignStatus()) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                xianJinBaiKaResponse.setMessage("success");
//                List<Map<String, Object>> list = new ArrayList<>();
//                map.put("bank_name", bwBankCard.getBankName());
//                map.put("bank_code", XianJinBaiKaUtil.convertToBankCode(bwBankCard.getBankName()));
//                // 银行类型 1 信用卡 2 借记卡
//                map.put("type", "2");
//                map.put("card_no", bwBankCard.getCardNo());
//                map.put("bank_mobile", bwBankCard.getPhone());
//                map.put("name", userName);
//                map.put("main_card", 1);
//                list.add(map);
//                xianJinBaiKaResponse.setResponse(list);
//            } else {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                xianJinBaiKaResponse.setMessage("没有绑定的银行卡");
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行getBindCard method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束getBindCard：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse getContracts(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        logger.info(sessionId + "：开始getContracts method：" + JSON.toJSONString(xianJinBaiKaCommonRequest));
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        try {
//            String thirdOrderNo = xianJinBaiKaCommonRequest.getOrder_sn();
//
//            Map<String, String> contractUrls;
//            List<Map<String, String>> list = new ArrayList<>();
//            if (StringUtils.isNotBlank(thirdOrderNo)) {
//                BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//                if (!CommUtils.isNull(bwOrderRong)) {
//                    Long orderId = bwOrderRong.getOrderId();
//                    List<BwAdjunct> bwAdjunctList = bwAdjunctService.findBwAdjunctByOrderId(orderId);
//                    if (!CommUtils.isNull(bwAdjunctList)) {
//                        for (BwAdjunct bwAdjunct : bwAdjunctList) {
//                            if (bwAdjunct.getAdjunctType() == 29) {
//                                contractUrls = new HashMap<>(16);
//                                String adjunctPath = bwAdjunct.getAdjunctPath();
//                                String conUrl = SystemConstant.PDF_URL + adjunctPath;
//                                contractUrls.put("name", "小微金融水象分期信息咨询及信用管理服务合同");
//                                contractUrls.put("link", conUrl);
//                                list.add(contractUrls);
//                            }
//
//                            if (bwAdjunct.getAdjunctType() == 30) {
//                                contractUrls = new HashMap<>(16);
//                                String adjunctPath = bwAdjunct.getAdjunctPath();
//                                String conUrl = SystemConstant.PDF_URL + adjunctPath;
//                                contractUrls.put("name", "征信及信息披露授权书");
//                                contractUrls.put("link", conUrl);
//                                list.add(contractUrls);
//                            }
//                        }
//                    }
//                }
//            }
//            if (CollectionUtils.isEmpty(list)) {
//                contractUrls = new HashMap<>(16);
//                String conUrl = "https://www.sxfq.com/weixinApp3.0/html/Agreement/manageAssociation.html";
//                contractUrls.put("name", "服务协议");
//                contractUrls.put("link", conUrl);
//                list.add(contractUrls);
//            }
//
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//            xianJinBaiKaResponse.setMessage("success");
//            xianJinBaiKaResponse.setResponse(list);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行getContracts method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束getContracts：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse getRepayplan(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        logger.info(sessionId + "：开始getRepayplan method：" + JSON.toJSONString(xianJinBaiKaCommonRequest));
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        try {
//            Map<String, Object> map = new HashMap<>(16);
//            String thirdOrderNo = xianJinBaiKaCommonRequest.getOrder_sn();
//
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                return failReturn(sessionId, map, "第三方订单号为空！");
//            }
//            // 2.根据第三方订单编号获取订单id
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (CommUtils.isNull(bwOrderRong)) {
//                return failReturn(sessionId, map, "第三方订单不存在！");
//            }
//            Long orderId = bwOrderRong.getOrderId();
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//            if (CommUtils.isNull(bwOrder)) {
//                return failReturn(sessionId, map, "订单为空！");
//            }
//            // 获取银行卡信息
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//            if (CommUtils.isNull(bwBankCard)) {
//                return failReturn(sessionId, map, "银行卡信息为空！");
//            }
//            // 获取还款计划
//            List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//            if (CommUtils.isNull(bwRepaymentPlans)) {
//                return failReturn(sessionId, map, "还款计划为空！");
//            }
//
//            List<Map<String, Object>> list = new ArrayList<>();
//            // 订单唯一编号
//            map.put("order_sn", thirdOrderNo);
//            // 总服务费; 单位: 分
//            map.put("total_svc_fee", 0);
//            // 总期数
//            map.put("total_period", bwOrder.getBorrowNumber());
//
//            int finishPeriod = 0;
//            Double totalAmount = 0.0D;
//            Double alreadyPaid = 0.0D;
//            Double overdueFees = 0.0D;
//            for (BwRepaymentPlan bwRepaymentPlan : bwRepaymentPlans) {
//                Map<String, Object> mapNew = new HashMap<>(16);
//                // 还款期号
//                mapNew.put("period_no", bwRepaymentPlan.getNumber());
//                // 本期还款本金 单位: 分
//                mapNew.put("principle", new Double(bwRepaymentPlan.getRepayCorpusMoney() * 100).intValue());
//                // 本期还款利息;单位: 分
//                mapNew.put("interest", new Double(bwRepaymentPlan.getRepayAccrualMoney() * 100).intValue());
//                // 本期服务费用; 单位: 分
//                mapNew.put("service_fee", 0);
//                // 本期账单状态; -1:未出账;0:待还款; 1:已还款
//                mapNew.put("bill_status", bwRepaymentPlan.getRepayStatus() == 2 ? 1 : 0);
//                // 本期已还金额;单位: 分
//                mapNew.put("already_paid", new Double(bwRepaymentPlan.getAlreadyRepayMoney() * 100).intValue());
//                // 实际起息时间
//                mapNew.put("loan_time", bwRepaymentPlan.getCreateTime().getTime() / 1000);
//                // 最迟还款时间（精确到秒超过该时间就算逾期）
//                mapNew.put("due_time", bwRepaymentPlan.getRepayTime().getTime() / 1000);
//                // 可以还款时间
//                mapNew.put("can_pay_time", bwRepaymentPlan.getCreateTime().getTime() / 1000);
//
//                mapNew.put("pay_type", 0);
//                mapNew.put("finish_pay_time", 0);
//                if (bwRepaymentPlan.getRepayStatus() == 2) {
//                    // 还款支付方式; 如: 0.未还款 1. 主动还款 2.系统扣款 3. 支付宝转账 4. 银行转账或其它方式
//                    mapNew.put("pay_type", 1);
//                    // 实际完成还款时间
//                    mapNew.put("finish_pay_time", bwOrder.getUpdateTime().getTime() / 1000);
//                    finishPeriod++;
//                }
//
//                BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//                bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//                bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//                Double overdueFee = 0.0D;
//                Integer overdueDay = 0;
//                if (bwOverdueRecord != null) {
//                    Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D
//                            : bwOverdueRecord.getOverdueAccrualMoney();
//                    Double advance = bwOverdueRecord.getAdvance() == null ? 0.0D : bwOverdueRecord.getAdvance();
//                    overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//                    overdueDay = bwOverdueRecord.getOverdueDay();
//                }
//                // 逾期天数
//                mapNew.put("overdue_day", overdueDay);
//                // 逾期费用; 单位: 分
//                mapNew.put("overdue_fee", new Double(overdueFee * 100).intValue());
//                // 本期还款总额;
//                mapNew.put("total_amount", new Double((bwRepaymentPlan.getRealityRepayMoney() + overdueFee) * 100).intValue());
//                // 本期费用描述
//                mapNew.put("period_fee_desc",
//                        "还款本金:" + bwRepaymentPlan.getRepayCorpusMoney().intValue() + ",还款利息:"
//                                + bwRepaymentPlan.getRepayAccrualMoney().intValue() + ",逾期费用:"
//                                + overdueFee.intValue());
//
//                list.add(mapNew);
//
//                totalAmount += bwRepaymentPlan.getRealityRepayMoney();
//                alreadyPaid += bwRepaymentPlan.getAlreadyRepayMoney();
//                overdueFees += overdueFee;
//            }
//            // 还款总额 单位:分
//            map.put("total_amount", new Double((totalAmount + overdueFees) * 100).intValue());
//            // 已还金额; 单位: 分
//            map.put("already_paid", new Double(alreadyPaid * 100).intValue());
//            // 已还期数
//            map.put("finish_period", finishPeriod);
//            // 具体还款计划数组
//            map.put("repayment_plan", list);
//
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//            xianJinBaiKaResponse.setMessage("success");
//            xianJinBaiKaResponse.setResponse(map);
//            logger.info(sessionId + "：结束getRepayplan：" + JSON.toJSONString(xianJinBaiKaResponse));
//            return xianJinBaiKaResponse;
//
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行getRepayplan method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束getRepayplan：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse getOrderStatus(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        logger.info(sessionId + "：开始getOrderStatus method：" + JSON.toJSONString(xianJinBaiKaCommonRequest));
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        try {
//            Map<String, Object> map = new HashMap<>(16);
//            String thirdOrderNo = xianJinBaiKaCommonRequest.getOrder_sn();
//            // 1: 拉取订单审批结果，2：拉取订单签约状态，3：拉取订单放款状态
//            int actType = xianJinBaiKaCommonRequest.getAct_type();
//
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                return failReturn(sessionId, map, "第三方订单号为空！");
//            }
//            // 根据第三方订单编号获取订单id
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (CommUtils.isNull(bwOrderRong)) {
//                return failReturn(sessionId, map, "第三方订单不存在！");
//            }
//            Long orderId = bwOrderRong.getOrderId();
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//            if (CommUtils.isNull(bwOrder)) {
//                return failReturn(sessionId, map, "订单为空！");
//            }
//            // 订单唯一编号
//            map.put("order_sn", thirdOrderNo);
//            // 结果类型，要求和请求参数中的act_type一致。
//            map.put("result_type", actType);
//
//            long orderStatus = bwOrder.getStatusId();
//            // 审批状态响应
//            if (1 == actType) {
//                if (7 == orderStatus || 8 == orderStatus) {
//                    // 审批状态 403 审批拒绝
//                    map.put("approve_status", "403");
//                    // 审批时间（十位标准时时间戳））
//                    map.put("approve_time", bwOrder.getUpdateTime().getTime() / 1000 + "");
//                    // 审批状态备注
//                    map.put("approve_remark", "评分不足");
//                    // 审批后的可借金额单位（分）
//                    map.put("approve_amount", new Double(bwOrder.getExpectMoney() * 100).intValue() + "");
//                    // 审批后的可借周期
//                    map.put("approve_term", "28");
//                    // 1:按天; 2：按月; 3：按年
//                    map.put("term_type", "1");
//                } else if (4 <= orderStatus) {
//                    // 审批状态 200 审批通过
//                    map.put("approve_status", "200");
//                    map.put("approve_time", bwOrder.getUpdateTime().getTime() / 1000 + "");
//                    map.put("approve_remark", "ok");
//                    map.put("approve_amount", new Double(bwOrder.getBorrowAmount() * 100).intValue() + "");
//                    map.put("approve_term", "28");
//                    map.put("term_type", "1");
//                } else {
//                    // 审批状态 100 审批中
//                    map.put("approve_status", "100");
//                    map.put("approve_remark", "审批中");
//                    map.put("approve_time", "");
//                    map.put("approve_amount", "");
//                    map.put("approve_term", "28");
//                    map.put("term_type", "1");
//                }
//                // 签约状态响应
//            } else if (2 == actType) {
//                if (7 == orderStatus || 8 == orderStatus) {
//                    // 签约状态 402 签约拒绝
//                    map.put("confirm_status", "402");
//                    // 签约状态备注
//                    map.put("confirm_remark", "签约失败");
//                    // 签约后时间（十位标准时时间戳）（待签约为空）
//                    map.put("confirm_time", "");
//                    // 签约后的可借金额单位（分）
//                    map.put("confirm_amount", new Double(bwOrder.getBorrowAmount() * 100).intValue() + "");
//                    // 否 签约后的可借周期
//                    map.put("confirm_term", "28");
//                    // 1:按天; 2：按月; 3：按年
//                    map.put("term_type", "1");
//                } else if (5 <= orderStatus) {
//                    // 签约状态 200 签约成功
//                    map.put("confirm_status", "200");
//                    map.put("confirm_remark", "ok");
//                    map.put("confirm_time", bwOrder.getUpdateTime().getTime() / 1000 + "");
//                    map.put("confirm_amount", new Double(bwOrder.getBorrowAmount() * 100).intValue() + "");
//                    map.put("confirm_term", "28");
//                    map.put("term_type", "1");
//                } else {
//                    // 签约状态 100 待签约
//                    map.put("confirm_status", "100");
//                    map.put("confirm_remark", "待签约");
//                    map.put("confirm_time", "");
//                    map.put("confirm_amount", new Double(bwOrder.getBorrowAmount() * 100).intValue() + "");
//                    map.put("confirm_term", "28");
//                    map.put("term_type", "1");
//                }
//                // 放款状态响应
//            } else if (3 == actType) {
//                if (7 == orderStatus || 8 == orderStatus) {
//                    // 放款状态 401 放款拒绝
//                    map.put("lending_status", "401");
//                    // 放款状态备注
//                    map.put("lending_remark", "放款失败");
//                    // 放款时间（十位标准时时间戳）
//                    map.put("lending_time", "");
//                    // 放款后的可借金额单位（分）
//                    map.put("lending_amount", new Double(bwOrder.getBorrowAmount() * 100).intValue() + "");
//                    // 否 放款后的可借周期
//                    map.put("lending_term", "28");
//                    // 1:按天; 2：按月; 3：按年
//                    map.put("term_type", "1");
//                } else if (9 == orderStatus || 13 == orderStatus) {
//                    // 放款状态 200 放款成功
//                    map.put("lending_status", "200");
//                    map.put("lending_remark", "ok");
//                    map.put("lending_time", bwOrder.getUpdateTime().getTime() / 1000 + "");
//                    map.put("lending_amount", new Double(bwOrder.getBorrowAmount() * 100).intValue() + "");
//                    map.put("lending_term", "28");
//                    map.put("term_type", "1");
//                } else {
//                    // 放款状态 100 待放款
//                    map.put("lending_status", "100");
//                    map.put("lending_remark", "待放款");
//                    map.put("lending_time", "");
//                    map.put("lending_amount", new Double(bwOrder.getBorrowAmount() * 100).intValue() + "");
//                    map.put("lending_term", "28");
//                    map.put("term_type", "1");
//                }
//            }
//
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//            xianJinBaiKaResponse.setMessage("success");
//            xianJinBaiKaResponse.setResponse(map);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行getOrderStatus method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束getOrderStatus：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse loanCalculate(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        logger.info(sessionId + "：开始loanCalculate method：" + JSON.toJSONString(xianJinBaiKaCommonRequest));
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        try {
//            Map<String, Object> map = new HashMap<>(16);
//            Integer loanAmount = xianJinBaiKaCommonRequest.getLoan_amount();
//
//            // 第一步， 查询水象云产品
//            BwProductDictionary bwProductDictionary = bwProductDictionaryService
//                    .findBwProductDictionaryById(Integer.valueOf(XianJinBaiKaConstant.PRODUCTID));
//
//            // 第二步，获取放款金额限制
//            Integer maxLoanAmount = bwProductDictionary.getMaxAmount() * 100;
//            // 分期利息率
//            Double interestRate = bwProductDictionary.getInterestRate();
//
//            if (loanAmount > maxLoanAmount) {
//                logger.info(sessionId + "：本次借款金额：" + loanAmount + "，大于最大借款金额：" + maxLoanAmount);
//                return failReturn(sessionId, map, "本次借款金额：" + loanAmount + "，大于最大借款金额：" + maxLoanAmount);
//            } else if (loanAmount < 100000) {
//                logger.info(sessionId + "：本次借款金额：" + loanAmount + "，小于最小借款金额：" + 100000);
//                return failReturn(sessionId, map, "本次借款金额：" + loanAmount + "，小于最小借款金额：" + maxLoanAmount);
//            }
//
//            // 第三步，计算每期还款金额
//            // 服务费
//            map.put("service_fee", 0);
//            map.put("service_fee_desc", "无服务费");
//            map.put("receive_amount", loanAmount);
//
//            // 每期应还本金
//            Double eachAmount = Double.valueOf(loanAmount) / 400;
//            // 每期应还利息
//            Double periodOne = DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount) / 100, 1, interestRate);
//            Double periodTwo = DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount) / 100, 2, interestRate);
//            Double periodThree = DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount) / 100, 3, interestRate);
//            Double periodFour = DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount) / 100, 4, interestRate);
//
//            Double interestFee = periodOne + periodTwo + periodThree + periodFour;
//            Double amount = Double.valueOf(loanAmount) / 100 + interestFee;
//
//            List<Map<String, Object>> repayPlan = new ArrayList<>();
//            Map<String, Object> plan1 = new HashMap<>(16);
//            // 期号
//            plan1.put("period_no", 1);
//            // 本期应还金额
//            plan1.put("repay_amount", new Double((eachAmount + periodOne) * 100).intValue());
//            plan1.put("repay_amount_desc",
//                    "本期应还本金：" + eachAmount.intValue() + "元" + "，本期应还利息" + periodOne.intValue() + "元");
//            repayPlan.add(plan1);
//
//            Map<String, Object> plan2 = new HashMap<>(16);
//            plan2.put("period_no", 2);
//            plan2.put("repay_amount", new Double((eachAmount + periodTwo) * 100).intValue());
//            plan2.put("repay_amount_desc",
//                    "本期应还本金：" + eachAmount.intValue() + "元" + "，本期应还利息" + periodTwo.intValue() + "元");
//            repayPlan.add(plan2);
//
//            Map<String, Object> plan3 = new HashMap<>(16);
//            plan3.put("period_no", 3);
//            plan3.put("repay_amount", new Double((eachAmount + periodThree) * 100).intValue());
//            plan3.put("repay_amount_desc",
//                    "本期应还本金：" + eachAmount.intValue() + "元" + "，本期应还利息" + periodThree.intValue() + "元");
//            repayPlan.add(plan3);
//
//            Map<String, Object> plan4 = new HashMap<>(16);
//            plan4.put("period_no", 4);
//            plan4.put("repay_amount", new Double((eachAmount + periodFour) * 100).intValue());
//            plan4.put("repay_amount_desc",
//                    "本期应还本金：" + eachAmount.intValue() + "元" + "，本期应还利息" + periodFour.intValue() + "元");
//            repayPlan.add(plan4);
//
//            map.put("interest_fee", new Double(interestFee * 100).intValue());
//            map.put("repay_amount", new Double(amount * 100).intValue());
//            map.put("repay_plan", repayPlan);
//
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//            xianJinBaiKaResponse.setMessage("success");
//            xianJinBaiKaResponse.setResponse(map);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行loanCalculate method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束loanCalculate：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse updateApplyRepay(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        logger.info(sessionId + "：开始updateApplyRepay method：" + JSON.toJSONString(xianJinBaiKaCommonRequest));
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        Map<String, Object> map = new HashMap<>(16);
//        try {
//            String thirdOrderNo = xianJinBaiKaCommonRequest.getOrder_sn();
//            DrainageRsp drainageRsp = commonService.updateRepayment(sessionId, thirdOrderNo);
//            if (drainageRsp != null) {
//                if ("000".equals(drainageRsp.getCode())) {
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage("success");
//                    map.put("repay_result", "100");
//                    xianJinBaiKaResponse.setResponse(map);
//                } else {
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage(drainageRsp.getMessage());
//                    map.put("repay_result", "505");
//                    xianJinBaiKaResponse.setResponse(map);
//                }
//            } else {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                xianJinBaiKaResponse.setMessage("操作失败");
//                map.put("repay_result", "505");
//                xianJinBaiKaResponse.setResponse(map);
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行updateApplyRepay method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束updateApplyRepay：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse authStatus(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        logger.info(sessionId + "：开始authStatus method：" + JSON.toJSONString(xianJinBaiKaCommonRequest));
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        Map<String, Object> map = new HashMap<>(16);
//        try {
//            String thirdOrderNo = xianJinBaiKaCommonRequest.getOrder_sn();
//            String userIdcard = xianJinBaiKaCommonRequest.getUser_idcard();
//            String userName = xianJinBaiKaCommonRequest.getUser_name();
//            String userPhone = xianJinBaiKaCommonRequest.getUser_phone();
//            String authType = xianJinBaiKaCommonRequest.getAuth_type();
//
//            // 验证参数
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                return failReturn(sessionId, map, "订单为空");
//            }
//            if (StringUtils.isBlank(userName)) {
//                return failReturn(sessionId, map, "姓名为空");
//            }
//            if (StringUtils.isBlank(userIdcard)) {
//                return failReturn(sessionId, map, "身份证号码为空");
//            }
//            if (StringUtils.isBlank(userPhone)) {
//                return failReturn(sessionId, map, "手机号码为空");
//            }
//            if (StringUtils.isBlank(authType)) {
//                return failReturn(sessionId, map, "认证类型为空");
//            }
//
//            // 根据第三方订单编号获取订单id
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (CommUtils.isNull(bwOrderRong)) {
//                return failReturn(sessionId, map, "第三方订单不存在！");
//            }
//            Long orderId = bwOrderRong.getOrderId();
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//            if (CommUtils.isNull(bwOrder)) {
//                return failReturn(sessionId, map, "订单为空！");
//            }
//
//            BwBorrower borrower = new BwBorrower();
//            borrower.setPhone(userPhone);
//            borrower.setName(userName);
//            borrower.setIdCard(userIdcard);
//            borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//            if (borrower == null) {
//                return failReturn(sessionId, map, "该用户不存在");
//            }
//
//            BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(orderId, 11);
//            if (bwOrderAuth == null) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                xianJinBaiKaResponse.setMessage("success");
//                map.put("auth_result", "401");
//                xianJinBaiKaResponse.setResponse(map);
//            } else {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                xianJinBaiKaResponse.setMessage("success");
//                map.put("auth_result", "200");
//                map.put("success_time", bwOrderAuth.getCreateTime().getTime() / 1000);
//                xianJinBaiKaResponse.setResponse(map);
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行authStatus method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束authStatus：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse updateApplyBindCardNew(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        logger.info(sessionId + ":开始applyBindCardNew method:{}", JSON.toJSONString(xianJinBaiKaCommonRequest));
//        Map<String, Object> map = new HashMap<>(16);
//        try {
//            String thirdOrderNo = xianJinBaiKaCommonRequest.getOrder_sn();
//            String name = xianJinBaiKaCommonRequest.getUser_name();
//            String idCard = xianJinBaiKaCommonRequest.getUser_idcard();
//            String bankCard = xianJinBaiKaCommonRequest.getCard_number();
//            String cardPhone = xianJinBaiKaCommonRequest.getCard_phone();
//            String regPhone = xianJinBaiKaCommonRequest.getUser_phone();
//            String xjbkBankCode = xianJinBaiKaCommonRequest.getBank_code();
//            String verifyCode = xianJinBaiKaCommonRequest.getVerify_code();
//
//            DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//            drainageBindCardVO.setThirdOrderNo(thirdOrderNo);
//            drainageBindCardVO.setName(name);
//            drainageBindCardVO.setIdCardNo(idCard);
//            drainageBindCardVO.setBankCardNo(bankCard);
//            drainageBindCardVO.setRegPhone(cardPhone);
//            drainageBindCardVO.setPhone(regPhone);
//            drainageBindCardVO.setBankCode(XianJinBaiKaUtil.convertXjbkBankCodeToFuYou(xjbkBankCode));
//            drainageBindCardVO.setChannelId(NumberUtils.toInt(channelIdStr));
//            drainageBindCardVO.setNotify(false);
//
//            String code = "0000";
//            if (CommUtils.isNull(verifyCode)) {
//                DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//                if (code.equals(drainageRsp.getCode())) {
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage("success");
//                    map.put("bind_status", "100");
//                    map.put("remark", "success");
//                    xianJinBaiKaResponse.setResponse(map);
//                } else {
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage("success");
//                    map.put("bind_status", "505");
//                    map.put("remark", drainageRsp.getMessage());
//                    xianJinBaiKaResponse.setResponse(map);
//                }
//                SxyThirdInterfaceLogUtils.setSxyLog(channelIdStr, thirdOrderNo, "", xianJinBaiKaResponse.getResponse().toString(), "预绑卡");
//            } else {
//                drainageBindCardVO.setVerifyCode(verifyCode);
//                DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//                if (code.equals(drainageRsp.getCode())) {
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage("success");
//                    map.put("bind_status", "200");
//                    map.put("remark", "success");
//                    xianJinBaiKaResponse.setResponse(map);
//                } else {
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage("success");
//                    map.put("bind_status", "505");
//                    map.put("remark", drainageRsp.getMessage());
//                    xianJinBaiKaResponse.setResponse(map);
//                }
//                SxyThirdInterfaceLogUtils.setSxyLog(channelIdStr, thirdOrderNo, "", xianJinBaiKaResponse.getResponse().toString(), "确认绑卡");
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行updateApplyBindCardNew method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束updateApplyBindCardNew：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//
//    }
//
//    @Override
//    public XianJinBaiKaResponse updateApplyRepayNew(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest) {
//        logger.info(sessionId + "：开始updateApplyRepayNew method：" + JSON.toJSONString(xianJinBaiKaCommonRequest));
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        Map<String, Object> map = new HashMap<>(16);
//        String thirdOrderNo = null;
//        try {
//            thirdOrderNo = xianJinBaiKaCommonRequest.getOrder_sn();
//            DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo);
//            String code = "000";
//            if (drainageRsp != null) {
//                if (code.equals(drainageRsp.getCode())) {
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage("success");
//                    map.put("repay_result", "100");
//                    xianJinBaiKaResponse.setResponse(map);
//                } else {
//                    xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                    xianJinBaiKaResponse.setMessage(drainageRsp.getMessage());
//                    map.put("repay_result", "505");
//                    xianJinBaiKaResponse.setResponse(map);
//                }
//            } else {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//                xianJinBaiKaResponse.setMessage("操作失败");
//                map.put("repay_result", "505");
//                xianJinBaiKaResponse.setResponse(map);
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行updateApplyRepayNew method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        } finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(channelIdStr, thirdOrderNo, xianJinBaiKaResponse.getStatus() + "", xianJinBaiKaResponse.getMessage(), "applyRepay");
//        }
//        logger.info(sessionId + "：结束updateApplyRepayNew：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//
//}
