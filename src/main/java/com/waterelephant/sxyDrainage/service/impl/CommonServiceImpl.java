//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.service.baofu.entity.BindCardRequest;
//import com.beadwallet.service.baofu.entity.BindCardResult;
//import com.beadwallet.service.baofu.service.BaofuServiceSDK;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.drainage.entity.common.PushOrderRequest;
//import com.waterelephant.drainage.service.DrainageService;
//import com.waterelephant.dto.PaySignDto;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwAdjunct;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBankCardChange;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.entity.BwTripartiteMark;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.entity.BwZmxyGrade;
//import com.waterelephant.jiedianqian.entity.UserCheckResp;
//import com.waterelephant.service.BwBankCardChangeService;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.BwTripartiteMarkService;
//import com.waterelephant.service.BwZmxyGradeService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwRepaymentService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwAdjunctServiceImpl;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.utils.BankUtils;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.MD5Util;
//import com.waterelephant.sxyDrainage.utils.SxyDrainageConstant;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.AppResponseResult;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DateUtil;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//import com.waterelephant.utils.UploadToCssUtils;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import tk.mybatis.mapper.common.Mapper;
//
///**
// * 水象云 - 公共方法
// * <p>
// * <p>
// * Module:
// * <p>
// * CommonService.java
// *
// * @author liuDaodao
// * @version 1.0
// * @description: <三方引流公共方法>
// * @since JDK 1.8
// */
//@Service
//public class CommonServiceImpl implements CommonService {
//
//    private Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
//
//    @Autowired
//    private DrainageService drainageService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//    @Autowired
//    private BwAdjunctServiceImpl bwAdjunctService;
//    @Autowired
//    private IBwRepaymentPlanService bwRepaymentPlanService;
//    @Autowired
//    private BwOverdueRecordService bwOverdueRecordService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private BwOrderRongService bwOrderRongService;
//    @Autowired
//    private BwOrderProcessRecordService bwOrderProcessRecordService;
//    @Autowired
//    private IBwWorkInfoService bwWorkInfoService;
//    @Autowired
//    private BwContactListService bwContactListService;
//    @Autowired
//    private BwOperateBasicService bwOperateBasicService;
//    @Autowired
//    private BwOperateVoiceService bwOperateVoiceService;
//    @Autowired
//    private BwZmxyGradeService bwZmxyGradeService;
//    @Autowired
//    private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//    @Autowired
//    private IBwBankCardService bwBankCardService;
//    @Autowired
//    private BwProductDictionaryService bwProductDictionaryService;
//    @Autowired
//    private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//    @Autowired
//    private IBwRepaymentService bwRepaymentService;
//    @Autowired
//    private BwTripartiteMarkService bwTripartiteMarkService;
//
//    @Autowired
//    private BwBankCardChangeService bwBankCardChangeService;
//    @Autowired
//    private BwBorrowerService bwBorrowerService;
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//
//    @Override
//    public DrainageRsp checkUser(Long sessionId, String name, String phone, String idCard) {
//        try {
//            logger.info(sessionId + " 进入CommonServiceImpl checkUser()用户检验接口..");
//            if (StringUtils.isBlank(name)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：姓名为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "姓名为空");
//            }
//            if (StringUtils.isBlank(idCard)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：身份证号码为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "身份证号码为空");
//            }
//            if (StringUtils.isBlank(phone)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：手机号为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "手机号为空");
//            }
//            idCard = idCard.replace("*", "%");
//            phone = phone.replace("*", "%");
//            // 第一步：是否黑名单
//            boolean isBlackUser = drainageService.isBlackUser2(name, phone, idCard);
//            if (isBlackUser) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：命中黑名单规则");
//                return new DrainageRsp(DrainageEnum.CODE_RULE_BLACKLIST);
//            }
//            // 第二步：是否进行中的订单
//            boolean isProcessingOrder = drainageService.isPocessingOrder2(name, phone, idCard);
//            if (isProcessingOrder) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：存在进行中的订单");
//                return new DrainageRsp(DrainageEnum.CODE_RULE_ISPROCESSING);
//            }
//            // 第三步：是否有被拒记录
//            UserCheckResp isRejectRecord = drainageService.isRejectRecord2(name, phone, idCard);
//            logger.info("被拒信息返回参数：" + name + JSON.toJSONString(isRejectRecord));
//            if (isRejectRecord.getIf_can_loan().equals("0")) {
//                // if_can_loan 0-否；1-是
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：命中被拒规则");
//                return new DrainageRsp(DrainageEnum.CODE_RULE_ISREJECT);
//            }
//            // 第四步：判断是否年龄超限
//            Calendar cal = Calendar.getInstance();
//            String year = idCard.substring(6, 10);
//            if (year.indexOf("%") == -1) {
//                int iCurrYear = cal.get(Calendar.YEAR);
//                int age = iCurrYear - Integer.valueOf(year);
//                if (age > 55 || age <= 21) {
//                    logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：用户年龄超限");
//                    return new DrainageRsp(DrainageEnum.CODE_RULE_AGE_UNMATCH);
//                }
//            }
//            // 第四步：查询借款信息（规则已过，可以借款）
//            return new DrainageRsp(DrainageEnum.CODE_SUCCESS);
//        } catch (Exception e) {
//            logger.error(sessionId + " 执行CommonServiceImpl.checkUser()方法异常", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    @Override
//    public DrainageRsp saveOrder(Long sessionId, PushOrderRequest pushOrderRequest) {
//        logger.info(sessionId + " 进入CommonServiceImpl saveOrder() 工单进件方法");
//        try {
//            String userName = pushOrderRequest.getUserName();
//            String idCard = pushOrderRequest.getIdCard();
//            String phone = pushOrderRequest.getPhone();
//            Integer channelId = pushOrderRequest.getChannelId();
//            String thirdOrderNo = pushOrderRequest.getThirdOrderNo();
//            if (StringUtils.isBlank(userName)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.saveOrder()方法，返回结果：姓名为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "姓名为空");
//            }
//            if (StringUtils.isBlank(idCard)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.saveOrder()方法，返回结果：身份证号码为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "身份证号码为空");
//            }
//            if (StringUtils.isBlank(phone)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.saveOrder()方法，返回结果：手机号为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "手机号为空");
//            }
//            if (CommUtils.isNull(channelId)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.saveOrder()方法，返回结果：渠道编码为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "渠道编码为空");
//            }
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.saveOrder()方法，返回结果:三方订单号为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "三方订单号为空");
//            }
//
//            // 新增或更新借款人
//            BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId);
//            long borrowerId = borrower.getId();
//            logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//
//            // 判断该渠道是否有撤回的订单
//            BwOrder order = new BwOrder();
//            order.setBorrowerId(borrowerId);
//            order.setStatusId(8L);
//            order.setChannel(channelId);
//            order = bwOrderService.findBwOrderByAttr(order);
//            if (order == null) {
//                // 查询是否有进行中的订单
//                long count = bwOrderService.findProOrder(borrowerId + "");
//                logger.info(sessionId + ">>> 进行中的订单校验：" + count);
//                if (count > 0) {
//                    logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：存在进行中的订单，请勿重复推送");
//                    return new DrainageRsp(DrainageEnum.CODE_RULE_ISPROCESSING);
//                }
//            }
//            Integer productId = Integer.valueOf(SxyDrainageConstant.productId);
//            // 判断是否有草稿状态的订单
//            BwOrder bwOrder = new BwOrder();
//            bwOrder.setBorrowerId(borrowerId);
//            bwOrder.setStatusId(1L);
//            bwOrder.setProductType(2);
//            bwOrder.setChannel(channelId);
//            bwOrder.setProductId(productId);
//            List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
//            bwOrder.setStatusId(8L);
//            List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
//            boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
//            if (boList != null && boList.size() > 0) {
//                bwOrder = boList.get(boList.size() - 1);
//                // bwOrder.setChannel(channelId);
//                bwOrder.setStatusId(1L);
//                bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwOrder.setProductType(2);
//                bwOrder.setExpectMoney(pushOrderRequest.getExpectMoney());//
//                bwOrder.setExpectNumber(pushOrderRequest.getExpectNumber());
//                // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                // bwOrder.setSubmitTime(simpleDateFormat.parse(basicInfo.getApplyDate()));
//                // bwOrder.setBorrowUse(basicInfo.getDesc());
//                bwOrderService.updateBwOrder(bwOrder);
//            } else {
//                bwOrder = new BwOrder();
//                bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//                bwOrder.setBorrowerId(borrower.getId());
//                bwOrder.setStatusId(1L);
//                bwOrder.setCreateTime(Calendar.getInstance().getTime());
//                bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwOrder.setChannel(channelId);
//                bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//                bwOrder.setApplyPayStatus(0);
//                bwOrder.setProductId(productId);
//                bwOrder.setProductType(2);
//                bwOrder.setExpectMoney(pushOrderRequest.getExpectMoney());
//                bwOrder.setExpectNumber(pushOrderRequest.getExpectNumber());
//                // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                // bwOrder.setSubmitTime(simpleDateFormat.parse(basicInfo.getApplyDate()));
//                // bwOrder.setBorrowUse(basicInfo.getDesc());
//                bwOrderService.addBwOrder(bwOrder);
//            }
//            long orderId = bwOrder.getId();
//            logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);
//
//            // 判断是否有融360订单
//            if (StringUtils.isNotBlank(thirdOrderNo)) {
//                BwOrderRong bwOrderRong = new BwOrderRong();
//                bwOrderRong.setOrderId(orderId);
//                bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//                if (bwOrderRong == null) {
//                    bwOrderRong = new BwOrderRong();
//                    bwOrderRong.setOrderId(orderId);
//                    bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                    bwOrderRong.setChannelId(Long.valueOf(channelId));
//                    bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//                    bwOrderRongService.save(bwOrderRong);
//                } else {
//                    if (null == bwOrderRong.getChannelId()) {
//                        bwOrderRong.setChannelId(Long.valueOf(channelId));
//                    }
//                    bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                    bwOrderRongService.update(bwOrderRong);
//                }
//                logger.info(sessionId + ">>> 判断是否有融360订单");
//            }
//
//            // 判断是否有商户订单信息
//            BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrder = bwMerchantOrderServiceImpl.selectByAtt(bwMerchantOrder);
//            if (bwMerchantOrder == null) {
//                bwMerchantOrder = new BwMerchantOrder();
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setCreateTime(Calendar.getInstance().getTime());
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwMerchantOrderServiceImpl.insertByAtt(bwMerchantOrder);
//            } else {
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwMerchantOrderServiceImpl.updateByAtt(bwMerchantOrder);
//            }
//
//            // 判断是否有工作信息
//            BwWorkInfo bwWorkInfo = new BwWorkInfo();
//            bwWorkInfo.setOrderId(orderId);
//            bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//            BwWorkInfo bwWorkInfo_ = pushOrderRequest.getBwWorkInfo();
//            if (null == bwWorkInfo) {
//                if (null == bwWorkInfo_) {
//                    bwWorkInfo_ = new BwWorkInfo();
//                }
//                bwWorkInfo_.setOrderId(orderId);
//                bwWorkInfo_.setCallTime("10:00 - 12:00");// 默认值
//                bwWorkInfo_.setUpdateTime(Calendar.getInstance().getTime());
//                bwWorkInfo_.setCreateTime(Calendar.getInstance().getTime());
//                bwWorkInfoService.save(bwWorkInfo_, borrowerId);
//                thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId); // 插入个人认证记录
//                logger.info(sessionId + ">>> 判断是否有工作信息");
//            } else if (null != bwWorkInfo_ && null != bwWorkInfo) {
//                bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//                bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//                bwWorkInfo.setWorkYears(bwWorkInfo_.getWorkYears());
//                bwWorkInfo.setComName(bwWorkInfo_.getComName());
//                bwWorkInfo.setIncome(bwWorkInfo_.getIncome());
//                bwWorkInfo.setIndustry(bwWorkInfo_.getIndustry());
//                bwWorkInfoService.update(bwWorkInfo);
//                thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId); // 插入个人认证记录
//                logger.info(sessionId + ">>> 判断是否有工作信息");
//            }
//
//            // 通讯录
//            List<BwContactList> contactList_ = pushOrderRequest.getBwContactList();
//            List<BwContactList> listConS = new ArrayList<>();
//            if (contactList_ != null && contactList_.size() > 0) {
//                for (BwContactList contact : contactList_) {
//                    if (CommUtils.isNull(contact.getName())) {
//                        continue;
//                    }
//                    if (CommUtils.isNull(contact.getPhone())) {
//                        continue;
//                    }
//                    BwContactList bwContactList = new BwContactList();
//                    bwContactList.setBorrowerId(borrowerId);
//                    bwContactList.setPhone(contact.getPhone());
//                    bwContactList.setName(contact.getName());
//                    listConS.add(bwContactList);
//                }
//                bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
//            }
//            logger.info(sessionId + ">>> 处理通讯录信息 ");
//
//            // 运营商
//            BwOperateBasic bwOperateBasic_ = pushOrderRequest.getBwOperateBasic();
//            BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//            if (bwOperateBasic == null) {
//                if (null == bwOperateBasic_) {
//                    bwOperateBasic_ = new BwOperateBasic();
//                }
//                bwOperateBasic_.setBorrowerId(borrowerId);
//                bwOperateBasic_.setCreateTime(new Date());
//                bwOperateBasic_.setUpdateTime(new Date());
//                bwOperateBasicService.save(bwOperateBasic_);
//            } else if (null != bwOperateBasic_ && null != bwOperateBasic) {
//                bwOperateBasic.setBorrowerId(borrowerId);
//                bwOperateBasic.setUpdateTime(new Date());
//                bwOperateBasic.setBorrowerId(borrower.getId());
//                bwOperateBasic.setUserSource(bwOperateBasic_.getUserSource());
//                bwOperateBasic.setIdCard(bwOperateBasic_.getIdCard());
//                bwOperateBasic.setAddr(bwOperateBasic_.getAddr());
//                bwOperateBasic.setPhone(bwOperateBasic_.getPhone());
//                bwOperateBasic.setPhoneRemain(bwOperateBasic_.getPhoneRemain());
//                bwOperateBasic.setRealName(bwOperateBasic_.getRealName());
//                bwOperateBasic.setUpdateTime(new Date());
//                bwOperateBasic.setRegTime(bwOperateBasic_.getRegTime());
//                bwOperateBasicService.update(bwOperateBasic);
//            }
//            logger.info(sessionId + ">>> 处理运营商信息");
//
//            // 通话记录
//            List<BwOperateVoice> bwOperateVoice_ = pushOrderRequest.getBwOperateVoiceList();
//            Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//            if (CollectionUtils.isNotEmpty(bwOperateVoice_)) {
//                SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//                for (BwOperateVoice bwOperateVoice : bwOperateVoice_) {
//                    try {
//                        Date jsonCallData = sdf_hms.parse(bwOperateVoice.getCall_time());
//                        if (callDate == null || jsonCallData.after(callDate)) { // 通话记录采取最新追加的方式
//                            bwOperateVoice.setUpdateTime(new Date());
//                            bwOperateVoice.setBorrower_id(borrowerId);
//                            bwOperateVoiceService.save(bwOperateVoice);
//                        }
//                    } catch (Exception e) {
//                        logger.error(sessionId + ">>> 插入单条通话记录异常，忽略该条记录" + e.getMessage());
//                    }
//                }
//            }
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, channelId);// 插入运营商认证记录
//            logger.info(sessionId + ">>> 处理通话记录信息 ");
//
//            // 芝麻信用
//            Integer sesameScore_ = pushOrderRequest.getSesameScore();
//            if (null != sesameScore_) {
//                BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrowerId);
//                if (bwZmxyGrade == null) {
//                    bwZmxyGrade = new BwZmxyGrade();
//                    bwZmxyGrade.setBorrowerId(borrowerId);
//                    bwZmxyGrade.setZmScore(sesameScore_);
//                    bwZmxyGrade.setCreateTime(Calendar.getInstance().getTime());
//                    bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
//                    bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
//                } else {
//                    bwZmxyGrade.setZmScore(sesameScore_);
//                    bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
//                    bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
//                }
//                thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 4, channelId);// 插入芝麻认证记录
//            }
//            logger.info(sessionId + ">>> 处理芝麻信用信息 ");
//
//            // 认证图片
//            String frontFile = pushOrderRequest.getIdCardFrontImage();
//            String backFile = pushOrderRequest.getIdCardBackImage();
//            String natureFile = pushOrderRequest.getIdCardHanderImage();
//            if (StringUtils.isNotBlank(frontFile)) {
//                String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01"); // 身份证正面照
//                logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//                thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0); // 保存身份证正面照
//            }
//            if (StringUtils.isNotBlank(backFile)) {
//                String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02"); // 身份证反面照
//                logger.info(sessionId + ">>> 身份证反面 " + backImage);
//                thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0); // 保存身份证反面照
//            }
//            if (StringUtils.isNotBlank(natureFile)) {
//                String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03"); // 手持照
//                logger.info(sessionId + ">>> 手持照/人脸 " + handerImage);
//                thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId, borrowerId, 0); // 保存手持照
//            }
//            logger.info(sessionId + ">>> 处理认证图片 ");
//
//            // 保存身份证信息
//            BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//            bwIdentityCard.setBorrowerId(borrowerId);
//            bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//            BwIdentityCard2 bwIdentityCard_ = pushOrderRequest.getBwIdentityCard();
//            if (bwIdentityCard == null) {
//                if (null == bwIdentityCard_) {
//                    bwIdentityCard_ = new BwIdentityCard2();
//                }
//                bwIdentityCard_.setBorrowerId(borrowerId);
//                bwIdentityCard_.setCreateTime(new Date());
//                bwIdentityCard_.setUpdateTime(new Date());
//                bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard_);
//            } else if (null != bwIdentityCard_ && null != bwIdentityCard) {
//                bwIdentityCard.setAddress(bwIdentityCard_.getAddress());
//                bwIdentityCard.setBirthday(bwIdentityCard_.getBirthday());
//                bwIdentityCard.setGender(bwIdentityCard_.getGender());
//                bwIdentityCard.setIdCardNumber(bwIdentityCard_.getIdCardNumber());
//                bwIdentityCard.setIssuedBy(bwIdentityCard_.getIssuedBy());
//                bwIdentityCard.setName(bwIdentityCard_.getName());
//                bwIdentityCard.setRace(bwIdentityCard_.getRace());
//                bwIdentityCard.setUpdateTime(new Date());
//                bwIdentityCard.setValidDate(bwIdentityCard_.getValidDate());
//                bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//            }
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);// 插入身份认证记录
//            logger.info(sessionId + ">>> 处理身份证信息");
//
//            // 亲属联系人
//            BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//            BwPersonInfo bwPersonInfo_ = pushOrderRequest.getBwPersonInfo();
//            if (bwPersonInfo == null) {
//                if (null == bwPersonInfo_) {
//                    bwPersonInfo_ = new BwPersonInfo();
//                }
//                bwPersonInfo_.setCreateTime(new Date());
//                bwPersonInfo_.setOrderId(orderId);
//                bwPersonInfo_.setUpdateTime(new Date());
//                bwPersonInfoService.add(bwPersonInfo_);
//            } else if (null != bwPersonInfo_ && null != bwPersonInfo) {
//                bwPersonInfo.setAddress(bwPersonInfo_.getAddress());
//                bwPersonInfo.setCarStatus(bwPersonInfo_.getCarStatus());
//                bwPersonInfo.setCityName(bwPersonInfo_.getCityName());
//                bwPersonInfo.setEmail(bwPersonInfo_.getEmail());
//                bwPersonInfo.setHouseStatus(bwPersonInfo_.getHouseStatus());
//                bwPersonInfo.setMarryStatus(bwPersonInfo_.getMarryStatus());
//                // bwPersonInfo.setOrderId(orderId);
//                bwPersonInfo.setRelationName(bwPersonInfo_.getRelationName());
//                bwPersonInfo.setRelationPhone(bwPersonInfo_.getRelationPhone());
//                bwPersonInfo.setUnrelationName(bwPersonInfo_.getUnrelationName());
//                bwPersonInfo.setUnrelationPhone(bwPersonInfo_.getUnrelationPhone());
//                bwPersonInfo.setUpdateTime(new Date());
//                bwPersonInfoService.update(bwPersonInfo);
//            }
//            logger.info(sessionId + ">>> 处理亲属联系人信息");
//
//            // 银行卡
//            BwBankCard bwBankCard_ = pushOrderRequest.getBwBankCard();
//            if (null != bwBankCard_) {
//                BwBankCard bwBankCard = new BwBankCard();
//                bwBankCard.setBorrowerId(borrowerId);
//                bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//                if (bwBankCard != null && bwBankCard.getSignStatus() == 1) {
//                    // 绑卡通知
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("channelId", channelId);
//                    map.put("orderId", orderId);
//                    map.put("result", "已经绑卡");
//                    String json = JSON.toJSONString(map);
//                    RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
//                    logger.info(sessionId + ">>> 已经绑卡,放入绑卡通知");
//                } else if (bwBankCard != null) {
//                    bwBankCard.setBankName(bwBankCard_.getBankName());
//                    bwBankCard.setBankCode(bwBankCard_.getBankCode());
//                    bwBankCard.setCardNo(bwBankCard_.getCardNo());
//                    bwBankCard.setPhone(bwBankCard_.getPhone());
//                    bwBankCard.setProvinceCode(bwBankCard_.getProvinceCode());
//                    bwBankCard.setCityCode(bwBankCard_.getCityCode());
//                    bwBankCard.setSignStatus(0);
//                    bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//                    bwBankCardService.updateBwBankCard(bwBankCard);
//                } else {
//                    bwBankCard_.setBorrowerId(borrowerId);
//                    bwBankCard_.setSignStatus(0);
//                    bwBankCard_.setCreateTime(Calendar.getInstance().getTime());
//                    bwBankCard_.setUpdateTime(Calendar.getInstance().getTime());
//                    bwBankCardService.saveBwBankCard(bwBankCard_, borrowerId);
//                }
//                logger.info(sessionId + ">>> 保存银行卡");
//            }
//
//            if (null != pushOrderRequest.getOrderStatus()) {
//                bwOrder.setStatusId(Long.valueOf(pushOrderRequest.getOrderStatus()));
//                bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//                bwOrderService.updateBwOrder(bwOrder);
//
//                logger.info(sessionId + ">>> 修改工单状态为" + pushOrderRequest.getOrderStatus());
//                HashMap<String, String> hm = new HashMap<>();
//                hm.put("channelId", channelId + "");
//                hm.put("orderId", String.valueOf(bwOrder.getId()));
//                hm.put("orderStatus", pushOrderRequest.getOrderStatus() + "");
//                hm.put("result", "");
//                String hmData = JSON.toJSONString(hm);
//                RedisUtils.rpush("tripartite:orderStatusNotify:" + channelId, hmData);
//                // 放入redis
//                SystemAuditDto systemAuditDto = new SystemAuditDto();
//                systemAuditDto.setIncludeAddressBook(0);
//                systemAuditDto.setOrderId(orderId);
//                systemAuditDto.setBorrowerId(borrowerId);
//                systemAuditDto.setName(userName);
//                systemAuditDto.setPhone(phone);
//                systemAuditDto.setIdCard(idCard);
//                systemAuditDto.setChannel(channelId);
//                systemAuditDto.setThirdOrderId(thirdOrderNo);
//                systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//                RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//                logger.info(sessionId + ">>> 修改订单状态，并放入redis");
//            }
//
//            // 更改订单进行时间
//            BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//            bwOrderProcessRecord.setSubmitTime(new Date());
//            bwOrderProcessRecord.setOrderId(bwOrder.getId());
//            bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//            logger.info(sessionId + ">>> 更改订单进行时间");
//
//            logger.info(sessionId + " 结束CommonServiceImpl.saveOrder()方法，返回结果:进件成功");
//            return new DrainageRsp(DrainageEnum.CODE_SUCCESS);
//        } catch (Exception e) {
//            logger.error(sessionId + " 执行CommonServiceImpl. saveOrder()方法异常", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 公共方法 - 绑卡
//     */
//    @Override
//    public DrainageRsp saveBindCard(Long sessionId, String idCardNo, String bankCardNo, String name, String regPhone,
//                                    String thirdOrderNo) {
//        logger.info(sessionId + "：开始公共方法 - 绑卡");
//        try {
//            if (StringUtils.isBlank(idCardNo)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> idCardNo：" + idCardNo);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "证件号码为空");
//            }
//            if (StringUtils.isBlank(bankCardNo)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> bankCardNo：" + bankCardNo);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "银行卡号为空");
//            }
//            if (StringUtils.isBlank(name)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> name：" + name);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "姓名为空");
//            }
//            if (StringUtils.isBlank(regPhone)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> regPhone：" + regPhone);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "手机号码为空");
//            }
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> thirdNo：" + thirdOrderNo);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "三方订单编号为空");
//            }
//
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (bwOrderRong == null) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>>" + DrainageEnum.CODE_THIRD_ORDER_NOT_FOUND.getMsg());
//                return new DrainageRsp(DrainageEnum.CODE_THIRD_ORDER_NOT_FOUND);
//            }
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//            if (bwOrder == null) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>>" + DrainageEnum.CODE_ORDER_NOT_FOUND.getMsg());
//                return new DrainageRsp(DrainageEnum.CODE_ORDER_NOT_FOUND);
//            }
//
//            boolean flag = false;
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//            String cardName = BankUtils.getname(bankCardNo);
//            String cardCode = DrainageUtils.convertToBankCode(cardName);
//            if (bwBankCard != null && bwBankCard.getSignStatus() == 1) {
//                if (bankCardNo.equals(bwBankCard.getCardNo())) {
//                    // 绑卡通知
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("channelId", bwOrder.getChannel());
//                    map.put("orderId", bwOrder.getId());
//                    map.put("result", "绑卡成功");
//                    String json = JSON.toJSONString(map);
//                    RedisUtils.lpush("tripartite:bindCardNotify:" + bwOrder.getChannel(), json);
//
//                    bwBankCard.setUpdateTime(new Date());
//                    bwBankCardService.updateBwBankCard(bwBankCard);
//                    logger.info(sessionId + ">>> 已经绑卡,放入绑卡通知");
//                    return new DrainageRsp(DrainageEnum.CODE_SUCCESS);
//                } else {
//                    logger.info("进入换卡接口,旧卡：" + bwBankCard.getCardNo() + ";新卡：" + bankCardNo);
//                    flag = true;
//                }
//            } else if (bwBankCard != null) {
//                bwBankCard.setBankName(cardName);
//                bwBankCard.setBankCode(cardCode);
//                bwBankCard.setCardNo(bankCardNo);
//                bwBankCard.setPhone(regPhone);
//                bwBankCard.setSignStatus(0);
//                bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//                bwBankCardService.updateBwBankCard(bwBankCard);
//            } else {
//                bwBankCard = new BwBankCard();
//                bwBankCard.setBankName(cardName);
//                bwBankCard.setBankCode(cardCode);
//                bwBankCard.setCardNo(bankCardNo);
//                bwBankCard.setPhone(regPhone);
//                bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
//                bwBankCard.setSignStatus(0);
//                bwBankCard.setCreateTime(Calendar.getInstance().getTime());
//                bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//                bwBankCardService.saveBwBankCard(bwBankCard, bwOrder.getBorrowerId());
//            }
//
//            // 开始绑卡
//            String bankCode = DrainageUtils.convertFuiouBankCodeToBaofu(cardCode);
//            BindCardRequest bcr = new BindCardRequest();
//            bcr.setPay_code(bankCode);
//            bcr.setBorrowerId(bwOrder.getBorrowerId() + "");
//            bcr.setId_card(idCardNo);
//            bcr.setMobile(regPhone);
//            bcr.setId_holder(name);
//            bcr.setAcc_no(bankCardNo);
//            BindCardResult bindCardResult = BaofuServiceSDK.directBind(bcr);
//            logger.info("宝付返回信息:" + JSON.toJSONString(bindCardResult));
//            if (null != bindCardResult) {
//                if ("0000".equals(bindCardResult.getResp_code())) {
//                    bwBankCard.setSignStatus(1);
//                    if (flag) {
//                        BwBankCardChange bwBankCardChange = new BwBankCardChange();
//                        logger.info(bwBankCard.getCardNo());
//                        bwBankCardChange.setBorrowerId(bwOrder.getBorrowerId());
//                        bwBankCardChange.setBankName(bwBankCard.getBankName());
//                        bwBankCardChange.setBankCode(bwBankCard.getBankCode());
//                        bwBankCardChange.setPhone(bwBankCard.getPhone());
//                        bwBankCardChange.setCardNo(bwBankCard.getCardNo());
//                        bwBankCardChange.setCreatedTime(new Date());
//                        Integer insertNumber = bwBankCardChangeService.insertByAtt(bwBankCardChange);
//                        if (insertNumber > 0) {
//                            logger.info("成功插入" + insertNumber + "借款人" + bwOrder.getBorrowerId() + "换卡信息成功,卡号为："
//                                    + bwBankCard.getCardNo());
//                        }
//                    }
//                    bwBankCard.setBankCode(cardCode);
//                    bwBankCard.setCardNo(bankCardNo);
//                    bwBankCard.setBankName(cardName);
//                    bwBankCard.setPhone(regPhone);
//                    bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//                    bwBankCardService.updateBwBankCard(bwBankCard);
//                    // 回调
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("orderId", bwOrder.getId());
//                    map.put("channelId", bwOrder.getChannel());
//                    map.put("result", bindCardResult.getResp_msg());
//                    String json = JSON.toJSONString(map);
//                    RedisUtils.rpush("tripartite:bindCardNotify:" + bwOrder.getChannel(), json);
//                    logger.info(sessionId + "结束绑卡接口 >>>绑卡成功");
//                    return new DrainageRsp(DrainageEnum.CODE_SUCCESS);
//                } else {
//                    // 回调
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("orderId", bwOrder.getId());
//                    map.put("channelId", bwOrder.getChannel());
//                    map.put("result", bindCardResult.getResp_msg());
//                    String json = JSON.toJSONString(map);
//                    RedisUtils.rpush("tripartite:bindCardNotify:" + bwOrder.getChannel(), json);
//                    logger.info(sessionId + "结束绑卡接口 >>> 绑卡失败:" + bindCardResult.getResp_msg());
//                    return new DrainageRsp(DrainageEnum.CODE_FAIL_MSG, bindCardResult.getResp_msg());
//                }
//            } else {
//                logger.info(sessionId + "结束绑卡接口 >>> 绑卡失败");
//                return new DrainageRsp(DrainageEnum.CODE_FAIL);
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "结束绑卡接口>>> 系统异常", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 公共方法 - 签约
//     */
//    @Override
//    public DrainageRsp updateSignContract(Long sessionId, String thirdOrderNo) {
//        logger.info(sessionId + "：开始公共方法 - 签约 >>> 三方订单号：" + thirdOrderNo);
//        try {
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                logger.info(sessionId + "：结束公共方法 - 签约 >>>" + DrainageEnum.CODE_PARAMSETER.getMsg());
//                return new DrainageRsp(DrainageEnum.CODE_PARAMSETER);
//            }
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (CommUtils.isNull(bwOrderRong)) {
//                logger.info(sessionId + "：结束公共方法 - 签约 >>>" + DrainageEnum.CODE_THIRD_ORDER_NOT_FOUND.getMsg());
//                return new DrainageRsp(DrainageEnum.CODE_THIRD_ORDER_NOT_FOUND);
//            }
//            Long orderId = bwOrderRong.getOrderId();
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//            if (CommUtils.isNull(bwOrder)) {
//                logger.info(sessionId + "：结束公共方法 - 签约 >>>" + DrainageEnum.CODE_ORDER_NOT_FOUND.getMsg());
//                return new DrainageRsp(DrainageEnum.CODE_ORDER_NOT_FOUND);
//            }
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//            if (null == bwBankCard || bwBankCard.getSignStatus() == null || bwBankCard.getSignStatus() < 1) {
//                logger.info(sessionId + "：结束公共方法 - 签约 >>>" + DrainageEnum.CODE_NO_BINDCARD.getMsg());
//                return new DrainageRsp(DrainageEnum.CODE_NO_BINDCARD);
//            }
//            if (4 != bwOrder.getStatusId()) {// 待签约
//                return new DrainageRsp(DrainageEnum.CODE_ORDERSTATUS_ERR);
//            }
//            // 获取利率字典表信息
//            BwProductDictionary bwProductDictionary = bwProductDictionaryService
//                    .findBwProductDictionaryById(bwOrder.getProductId());
//
//            // ******************发放优惠券******************
//            grantMallCoupon(sessionId, bwOrder.getId(), bwOrder.getChannel());
//
//            bwOrder.setRepayTerm(Integer.valueOf(bwProductDictionary.getpTerm()));
//            bwOrder.setRepayType(2);
//            bwOrder.setBorrowRate(bwProductDictionary.getpInvestRateMonth());
//            bwOrder.setContractRate(bwProductDictionary.getpInvesstRateYear());
//            bwOrder.setContractMonthRate(bwProductDictionary.getpBorrowRateMonth());
//            bwOrder.setStatusId(11L);
//            bwOrder.setUpdateTime(new Date());
//            bwOrderService.updateBwOrder(bwOrder);
//
//            // 第三方通知
//            logger.info("签约成功===" + bwOrder.getId());
//            HashMap<String, String> hm = new HashMap<>();
//            hm.put("channelId", bwOrder.getChannel() + "");
//            hm.put("orderId", bwOrder.getId() + "");
//            hm.put("orderStatus", "11");
//            hm.put("result", "签约成功");
//            String hmData = JSON.toJSONString(hm);
//            RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//            // 生成合同
//            RedisUtils.rpush("system:contract", String.valueOf(orderId));
//
//            BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//            bwOrderProcessRecord.setOrderId(bwOrder.getId());
//            bwOrderProcessRecord.setSignTime(new Date());
//            bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//            logger.info(sessionId + "：结束公共方法 - 签约 >>> 成功");
//            return new DrainageRsp(DrainageEnum.CODE_SUCCESS);
//        } catch (Exception e) {
//            logger.error(sessionId + "：结束公共方法 - 签约 >>> 系统异常：", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * @param sessionId
//     * @param orderId
//     * @param channelId
//     * @return
//     */
//    private void grantMallCoupon(Long sessionId, Long orderId, Integer channelId) {
//        boolean flag = false;
//        String msg = "";
//        try {
//            logger.info(sessionId + " >>开始优惠券发放 " + orderId);
//            String url = SxyDrainageConstant.MALLCOUPON_URL + "/v3/app/order/a56/grantMallCoupon.do";
//            Map<String, String> map = new HashMap<>();
//            map.put("orderId", orderId + "");
//            map.put("thirdNo", orderId + "-0");
//            map.put("billNo", orderId + "-0");
//            map.put("tradAmount", "0");
//            map.put("tradCreateTime", DateUtil.getDateString(new Date(), DateUtil.yyyyMMddHHmmss));
//            map.put("tradFinishTime", DateUtil.getDateString(new Date(), DateUtil.yyyyMMddHHmmss));
//            String mallCouponKey = SxyDrainageConstant.MALLCOUPON_KEY;
//            String currentSecretKey = MD5Util.getMd5Sign(map, mallCouponKey, Arrays.asList("sign"), true);
//            map.put("sign", currentSecretKey);
//            String json = HttpClientHelper.post(url, "UTF-8", map);
//            logger.info(sessionId + " >>优惠券发放 json>>>" + json);
//            if (StringUtils.isNoneBlank(json)) {
//                JSONObject resJson = JSONObject.parseObject(json);
//                if (null != resJson) {
//                    if ("000".equals(resJson.getString("code"))) {
//                        flag = true;
//                    }
//                    msg = resJson.getString("msg");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (!flag) {
//                try {
//                    BwTripartiteMark bwTripartiteMark = new BwTripartiteMark();
//                    bwTripartiteMark.setChannelId(channelId);
//                    bwTripartiteMark.setIndexKey(orderId + "");
//                    bwTripartiteMark.setCreateTime(new Date());
//                    bwTripartiteMark.setRemark1("发放优惠券失败");
//                    bwTripartiteMark.setRemark2(msg);
//                    bwTripartiteMarkService.save(bwTripartiteMark);
//                } catch (Exception e2) {
//                    e2.printStackTrace();
//                }
//            }
//        }
//        logger.info(sessionId + " >>结束优惠券发放 " + orderId);
//    }
//
//    /**
//     * 公共方法 - 获取合同
//     */
//    @Override
//    public DrainageRsp getContactUrl(Long sessionId, String thirdOrderNo) {
//        logger.info(sessionId + "：开始公共方法 - 获取合同 >>> 三方订单号：" + thirdOrderNo);
//        DrainageRsp drainageRsp = new DrainageRsp();
//        try {
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                logger.info(sessionId + "：结束公共方法 - 获取合同 >>>" + DrainageEnum.CODE_PARAMSETER.getMsg());
//                return new DrainageRsp(DrainageEnum.CODE_PARAMSETER);
//            }
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (CommUtils.isNull(bwOrderRong)) {
//                logger.info(sessionId + "：结束公共方法 - 获取合同 >>>" + DrainageEnum.CODE_THIRD_ORDER_NOT_FOUND.getMsg());
//                return new DrainageRsp(DrainageEnum.CODE_THIRD_ORDER_NOT_FOUND);
//            }
//            Long orderId = bwOrderRong.getOrderId();
//
//            List<Map<String, String>> list = new ArrayList<>();
//            Map<String, String> map = new HashMap<>();
//
//            BwAdjunct bwAdjunct = new BwAdjunct();
//            bwAdjunct.setOrderId(orderId);
//            bwAdjunct.setAdjunctType(28);
//            bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
//            // if (!CommUtils.isNull(bwAdjunct)) {
//            // String contractUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
//            // map.put("name", "借款协议");
//            // map.put("link", contractUrl);
//            // // list.add(map);
//            // }
//
//            bwAdjunct.setAdjunctType(29);
//            bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
//            if (!CommUtils.isNull(bwAdjunct)) {
//                String contractUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
//                map.put("name", "小微金融水象分期信息咨询及信用管理服务合同");
//                map.put("link", contractUrl);
//                list.add(map);
//            }
//            bwAdjunct.setAdjunctType(30);
//            bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
//            if (!CommUtils.isNull(bwAdjunct)) {
//                String contractUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
//                map.put("name", "征信及信息披露授权书");
//                map.put("link", contractUrl);
//                list.add(map);
//            }
//
//            drainageRsp.setCode(DrainageEnum.CODE_SUCCESS);
//            drainageRsp.setObject(map);
//            logger.info(sessionId + "：结束 公共  获取合同 >>>" + JSON.toJSONString(drainageRsp));
//            return drainageRsp;
//        } catch (Exception e) {
//            logger.error(sessionId + "：结束公共方法 - 获取合同 >>> 系统异常：", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 获取订单状态
//     */
//    @Override
//    public DrainageRsp queryOrder(Long sessionId, String orderNo) {
//        Map<String, Object> map = new HashMap<>();
//        try {
//            logger.info(sessionId + " 进入CommonServiceImpl queryOrder()订单查询接口..orderNo=" + orderNo);
//            // 第一步，查询我方订单信息
//            BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNo);
//            if (CommUtils.isNull(bwOrder)) {
//                logger.info(sessionId + "结束获取订单状态 >>>未查询到orderNo  [" + orderNo + "]的订单");
//                // return new DrainageRsp(DrainageEnum.CODE_ORDER_NOT_FOUND);
//            }
//
//            // 第二步，查询还款计划
//            Long orderId = bwOrder.getId();
//            List<Map<String, Object>> bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByOrderId(orderId);
//            if (bwRepaymentPlan.size() == 0) {
//                logger.info(sessionId + "结束获取订单状态 >>>未查询到orderNo  [" + orderNo + "]的还款计划");
//                // return new DrainageRsp(DrainageEnum.CODE_REPAYPLAN_NOT_FOUND);
//            }
//
//            // 第三步，查询逾期记录
//            BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//            bwOverdueRecord.setOrderId(orderId);
//            bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//            if (CommUtils.isNull(bwOverdueRecord)) {
//                logger.info(sessionId + "结束获取订单状态 >>>未查询到orderNo  [" + orderNo + "]的逾期记录");
//                // return new DrainageRsp(DrainageEnum.CODE_OVERDUE_NOT_FOUND);
//            }
//            map.put("bwOrder", bwOrder);
//            map.put("bwRepaymentPlan", bwRepaymentPlan);
//            map.put("bwOverdueRecord", bwOverdueRecord);
//            DrainageRsp drainageRsp = new DrainageRsp();
//            drainageRsp.setCode(DrainageEnum.CODE_SUCCESS);
//            drainageRsp.setObject(map);
//            return drainageRsp;
//        } catch (Exception e) {
//            logger.error(sessionId + " 执行CommonServiceImpl. queryOrder()方法异常", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 公共方法 - 试算
//     */
//    @Override
//    public DrainageRsp calculate(Long sessionId, int loanAmount, int loanTerm) {
//        logger.info(sessionId + "：开始公共方法 - 获取合同");
//        DrainageRsp drainageRsp = new DrainageRsp();
//        try {
//            // 第一步， 查询水象云产品
//            BwProductDictionary bwProductDictionary = bwProductDictionaryService
//                    .findBwProductDictionaryById(Integer.valueOf(SxyDrainageConstant.productId));
//
//            // 第二步，获取放款金额限制
//            Integer maxLoanAmount = bwProductDictionary.getMaxAmount();
//            Integer minLoanAmount = bwProductDictionary.getMinAmount();
//            Double interestRate = bwProductDictionary.getInterestRate();// 分期利息率
//
//            if (loanAmount > maxLoanAmount) {
//                logger.info(sessionId + "：本次借款金额：" + loanAmount + "，大于最大借款金额：" + maxLoanAmount);
//                return new DrainageRsp(DrainageEnum.CODE_LOANAMOUNT_GT, loanAmount, maxLoanAmount);
//            } else if (loanAmount < minLoanAmount) {
//                logger.info(sessionId + "：本次借款金额：" + loanAmount + "，小于最小借款金额：" + minLoanAmount);
//                return new DrainageRsp(DrainageEnum.CODE_LOANAMOUNT_LT, loanAmount, minLoanAmount);
//            }
//
//            // 第三步，计算每期还款金额
//            HashMap<String, Object> hm = new HashMap<>();
//            hm.put("accountMoney", loanAmount);// 到账金额
//            hm.put("serviceRate", 0);// 服务费
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("1",
//                    loanAmount / 4 + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 1, interestRate));
//            map.put("2",
//                    loanAmount / 4 + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 2, interestRate));
//            map.put("3",
//                    loanAmount / 4 + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 3, interestRate));
//            map.put("4",
//                    loanAmount / 4 + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 4, interestRate));
//
//            Double amount = Double.valueOf(loanAmount)
//                    + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 1, interestRate)
//                    + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 2, interestRate)
//                    + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 3, interestRate)
//                    + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 4, interestRate);
//
//            hm.put("repayMoney", map);// 每期还款金额
//            hm.put("amount", amount);// 总还款金额
//
//            drainageRsp.setCode(DrainageEnum.CODE_SUCCESS);
//            drainageRsp.setObject(hm);
//            logger.info(sessionId + "：结束公共方法 - 试算 >>>" + JSON.toJSONString(drainageRsp));
//            return drainageRsp;
//        } catch (Exception e) {
//            logger.error(sessionId + "：结束公共方法 - 试算 >>> 系统异常：", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 主动还款
//     */
//    @Override
//    public DrainageRsp updateRepayment(Long sessionId, String thirdOrderNo) {
//        logger.info(sessionId + "：开始公共方法 - 主动还款");
//        DrainageRsp drainageRsp = new DrainageRsp();
//        try {
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.updateRepayment()方法，返回结果:三方订单号为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "三方订单号为空");
//            }
//            BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//            if (null == bwOrder) {
//                return new DrainageRsp(DrainageEnum.CODE_ORDER_NOT_FOUND);
//            }
//            AppResponseResult appResponseResult = bwRepaymentService.updateAndPaymentThirdByOrderId(bwOrder.getId());
//            if (null != appResponseResult) {
//                drainageRsp.setCode(appResponseResult.getCode());// 成功 000
//                drainageRsp.setMessage(appResponseResult.getMsg());
//                drainageRsp.setObject(appResponseResult.getResult());
//            } else {
//                drainageRsp = new DrainageRsp(DrainageEnum.CODE_FAIL);
//            }
//            logger.info(sessionId + "：结束公共方法 - 主动还款>>>" + JSON.toJSONString(drainageRsp));
//            return drainageRsp;
//        } catch (Exception e) {
//            logger.error(sessionId + "：结束公共方法 - 主动还款>>> 系统异常：", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 公共方法 - 预绑卡 borrowerId、phone、cardNo，新绑卡必传name、idCard bindType 绑卡类型 前置传1 其他为后置 前置绑卡 传 phone 后置绑卡 传 thirdOrderNo
//     */
//    @Override
//    public DrainageRsp saveBindCard_NewReady(Long sessionId, DrainageBindCardVO drainageBindCardVO) {
//        logger.info(sessionId + "：开始公共方法 - 预绑卡");
//        try {
//            // bindType 绑卡类型 前置传1 其他为后置
//            // 前置绑卡 传 phone 后置绑卡 传 thirdOrderNo
//            if (null == drainageBindCardVO) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> drainageBindCardVO：" + drainageBindCardVO);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMSETER);
//            }
//            String idCardNo = drainageBindCardVO.getIdCardNo();
//            if (StringUtils.isBlank(idCardNo)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> idCardNo：" + idCardNo);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "证件号码为空");
//            }
//            String bankCardNo = drainageBindCardVO.getBankCardNo();
//            if (StringUtils.isBlank(bankCardNo)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> bankCardNo：" + bankCardNo);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "银行卡号为空");
//            }
//            String bankCode = drainageBindCardVO.getBankCode();
//            if (StringUtils.isBlank(bankCode)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> bankCode：" + bankCode);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "银行编码为空");
//            }
//            String name = drainageBindCardVO.getName();
//            if (StringUtils.isBlank(name)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> name：" + name);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "姓名为空");
//            }
//            String regPhone = drainageBindCardVO.getRegPhone();
//            if (StringUtils.isBlank(regPhone)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> regPhone：" + regPhone);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "预留手机号码为空");
//            }
//            String bindType = drainageBindCardVO.getBindType();
//            String phone = drainageBindCardVO.getPhone();
//            String thirdOrderNo = drainageBindCardVO.getThirdOrderNo();
//            int channelId = drainageBindCardVO.getChannelId();
//            Long borrowerId = null;
//            if ("1".equals(bindType)) {// 前置绑卡
//                if (StringUtils.isBlank(phone)) {
//                    logger.info(sessionId + "：结束公共方法 - 绑卡 >>> Phone：" + phone);
//                    return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "手机号码为空");
//                }
//                BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, name, idCardNo, phone,
//                        channelId);
//                borrowerId = borrower.getId();
//            } else {// 后置绑卡
//                if (StringUtils.isBlank(thirdOrderNo)) {
//                    logger.info(sessionId + "：结束公共方法 - 绑卡 >>> thirdOrderNo：" + thirdOrderNo);
//                    return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "订单号为空");
//                }
//                BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//
//                if (bwOrder == null) {
//                    logger.info(sessionId + "：结束公共方法 - 绑卡 >>>" + DrainageEnum.CODE_ORDER_NOT_FOUND.getMsg());
//                    return new DrainageRsp(DrainageEnum.CODE_ORDER_NOT_FOUND);
//                }
//
//                borrowerId = bwOrder.getBorrowerId();
//            }
//            if (null == borrowerId) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> 用户不存在");
//                return new DrainageRsp(DrainageEnum.CODE_USER_NOT_FOUND);
//            }
//            // 开始绑卡 预绑卡 borrowerId、phone、cardNo
//            PaySignDto paySignDto = new PaySignDto();
//            paySignDto.setBorrowerId(borrowerId);
//            paySignDto.setPhone(regPhone);
//            paySignDto.setCardNo(bankCardNo);
//            paySignDto.setIdCard(idCardNo);
//            paySignDto.setName(name);
//            paySignDto.setBankCode(bankCode);
//            paySignDto.setSameCardNoValidate(false);
//
//            AppResponseResult appResponseResult = bwBankCardService.updateAndReadyBindBankCard(paySignDto);
//            if (null != appResponseResult) {
//                logger.info("返回绑卡信息:" + JSON.toJSONString(appResponseResult));
//                if ("000".equals(appResponseResult.getCode())) {
//                    logger.info(sessionId + "结束绑卡接口 >>>预绑卡成功");
//                    return new DrainageRsp(DrainageEnum.CODE_SUCCESS);
//                } else {
//                    logger.info(sessionId + "结束绑卡接口 >>> 预绑卡失败:" + appResponseResult.getMsg());
//                    return new DrainageRsp(DrainageEnum.CODE_FAIL_MSG, appResponseResult.getMsg());
//                }
//            } else {
//                logger.info(sessionId + "结束绑卡接口 >>> 绑卡失败");
//                return new DrainageRsp(DrainageEnum.CODE_FAIL_MSG, "绑卡失败，请重试");
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "结束绑卡接口>>> 系统异常", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 公共方法 - 确认绑卡 * bindType 绑卡类型 前置传1 其他为后置 前置绑卡 传 phone 后置绑卡 传 thirdOrderNo 必传 ： 用户注册手机号，验证码
//     */
//    @Override
//    public DrainageRsp saveBindCard_NewSure(Long sessionId, DrainageBindCardVO drainageBindCardVO) {
//        logger.info(sessionId + "：开始公共方法 - 确认绑卡");
//        try {
//            if (null == drainageBindCardVO) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> drainageBindCardVO：" + drainageBindCardVO);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMSETER);
//            }
//            String verifyCode = drainageBindCardVO.getVerifyCode();
//            if (StringUtils.isBlank(verifyCode)) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> verifyCode：" + verifyCode);
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "验证码为空");
//            }
//            String bindType = drainageBindCardVO.getBindType();
//            String phone = drainageBindCardVO.getPhone();
//            String thirdOrderNo = drainageBindCardVO.getThirdOrderNo();
//            int channelId = drainageBindCardVO.getChannelId();
//            boolean isNofity = drainageBindCardVO.isNotify();
//            Long borrowerId = null, orderId = null;
//            if ("1".equals(bindType)) {// 前置绑卡
//                if (StringUtils.isBlank(phone)) {
//                    logger.info(sessionId + "：结束公共方法 - 绑卡 >>> Phone：" + phone);
//                    return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "手机号码为空");
//                }
//                BwBorrower borrower = new BwBorrower();
//                borrower.setPhone(phone);
//                borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//                if (null == borrower) {
//                    logger.info(sessionId + "：结束公共方法 - 绑卡 >>> 用户不存在");
//                    return new DrainageRsp(DrainageEnum.CODE_USER_NOT_FOUND);
//                }
//                borrowerId = borrower.getId();
//            } else {// 后置绑卡
//                if (StringUtils.isBlank(thirdOrderNo)) {
//                    logger.info(sessionId + "：结束公共方法 - 绑卡 >>> thirdOrderNo：" + thirdOrderNo);
//                    return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "订单号为空");
//                }
//                BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//                if (bwOrder == null) {
//                    logger.info(sessionId + "：结束公共方法 - 绑卡 >>>" + DrainageEnum.CODE_ORDER_NOT_FOUND.getMsg());
//                    return new DrainageRsp(DrainageEnum.CODE_ORDER_NOT_FOUND);
//                }
//                borrowerId = bwOrder.getBorrowerId();
//                orderId = bwOrder.getId();
//            }
//            if (null == borrowerId) {
//                logger.info(sessionId + "：结束公共方法 - 绑卡 >>> 用户不存在");
//                return new DrainageRsp(DrainageEnum.CODE_USER_NOT_FOUND);
//            }
//            PaySignDto paySignDto = new PaySignDto();
//            paySignDto.setBorrowerId(borrowerId);
//            paySignDto.setVerifyCode(verifyCode);
//            AppResponseResult appResponseResult = bwBankCardService.updateAndSureBindCard(paySignDto);
//            if (null != appResponseResult) {
//                logger.info("返回绑卡信息:" + JSON.toJSONString(appResponseResult));
//                if ("000".equals(appResponseResult.getCode())) {
//                    if (null != orderId && isNofity) {
//                        // 绑卡通知
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("channelId", channelId);
//                        map.put("orderId", orderId);
//                        map.put("result", "绑卡成功");
//                        String json = JSON.toJSONString(map);
//                        RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
//                    }
//                    logger.info(sessionId + "结束绑卡接口 >>>绑卡成功");
//                    return new DrainageRsp(DrainageEnum.CODE_SUCCESS);
//                } else {
//                    if (null != orderId && isNofity) {
//                        // 绑卡通知
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("channelId", channelId);
//                        map.put("orderId", orderId);
//                        map.put("result", appResponseResult.getMsg());
//                        String json = JSON.toJSONString(map);
//                        RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
//                    }
//                    logger.info(sessionId + "结束绑卡接口 >>> 绑卡失败:" + appResponseResult.getMsg());
//                    return new DrainageRsp(DrainageEnum.CODE_FAIL_MSG, appResponseResult.getMsg());
//                }
//            } else {
//                if (null != orderId && isNofity) {
//                    // 绑卡通知
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("channelId", channelId);
//                    map.put("orderId", orderId);
//                    map.put("result", "绑卡失败，请重试");
//                    String json = JSON.toJSONString(map);
//                    RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
//                }
//                logger.info(sessionId + "结束绑卡接口 >>> 绑卡失败");
//                return new DrainageRsp(DrainageEnum.CODE_FAIL_MSG, "绑卡失败，请重试");
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "结束绑卡接口>>> 系统异常", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 主动还款
//     */
//    @Override
//    public DrainageRsp updateRepayment_New(Long sessionId, String thirdOrderNo) {
//        logger.info(sessionId + "：开始公共方法 - 主动还款");
//        DrainageRsp drainageRsp = new DrainageRsp();
//        try {
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.updateRepayment()方法，返回结果:三方订单号为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "三方订单号为空");
//            }
//            BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//            if (null == bwOrder) {
//                return new DrainageRsp(DrainageEnum.CODE_ORDER_NOT_FOUND);
//            }
//            AppResponseResult appResponseResult = bwRepaymentService.updateAndPaymentThirdByOrderIdNew(bwOrder.getId());
//            if (null != appResponseResult) {
//                // 成功 000
//                drainageRsp.setCode(appResponseResult.getCode());
//                drainageRsp.setMessage(appResponseResult.getMsg());
//                drainageRsp.setObject(appResponseResult.getResult());
//            } else {
//                drainageRsp = new DrainageRsp(DrainageEnum.CODE_FAIL);
//            }
//            logger.info(sessionId + "：结束公共方法 - 主动还款>>>" + JSON.toJSONString(drainageRsp));
//            return drainageRsp;
//        } catch (Exception e) {
//            logger.error(sessionId + "：结束公共方法 - 主动还款>>> 系统异常：", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    @Override
//    public DrainageRsp checkUserNew(Long sessionId, String name, String phone, String idCard, String channelId) {
//        try {
//            logger.info(sessionId + " 进入CommonServiceImpl checkUser()用户检验接口..");
//            if (StringUtils.isBlank(name)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：姓名为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "姓名为空");
//            }
//            if (StringUtils.isBlank(idCard)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：身份证号码为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "身份证号码为空");
//            }
//            if (StringUtils.isBlank(phone)) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：手机号为空");
//                return new DrainageRsp(DrainageEnum.CODE_PARAMETER, "手机号为空");
//            }
//            idCard = idCard.replace("*", "%");
//            phone = phone.replace("*", "%");
//            // 第一步：是否黑名单
//            boolean isBlackUser = drainageService.isBlackUser2(name, phone, idCard);
//            if (isBlackUser) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：命中黑名单规则");
//                return new DrainageRsp(DrainageEnum.CODE_RULE_BLACKLIST);
//            }
//            // 第二步：是否进行中的订单
//            boolean isProcessingOrder = drainageService.isPocessingOrder2(name, phone, idCard);
//            if (isProcessingOrder) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：存在进行中的订单");
//                return new DrainageRsp(DrainageEnum.CODE_RULE_ISPROCESSING);
//            }
//            // 第三步：是否有被拒记录
//            UserCheckResp isRejectRecord = drainageService.isRejectRecord2(name, phone, idCard);
//            logger.info("被拒信息返回参数：" + name + JSON.toJSONString(isRejectRecord));
//            if (isRejectRecord.getIf_can_loan().equals("0")) {
//                // if_can_loan 0-否；1-是
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：命中被拒规则");
//                return new DrainageRsp(DrainageEnum.CODE_RULE_ISREJECT);
//            }
//            // 第四步：判断手机是否170开头手机号码
//            if (phone.startsWith("170")) {
//                logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：命中170开头手机");
//                return new DrainageRsp(DrainageEnum.CODE_PHONE_NOT_EXCEPTION);
//            }
//            // 第四步：判断是否年龄超限
//            Calendar cal = Calendar.getInstance();
//            String year = idCard.substring(6, 10);
//            if (year.indexOf("%") == -1) {
//                int iCurrYear = cal.get(Calendar.YEAR);
//                int age = iCurrYear - Integer.valueOf(year);
//                int maxAge = 0, minAge = 0;
//                if (StringUtils.isNotBlank(channelId)) {
//                    String ageStr = RedisUtils.hget("tripartite:ageFilter", channelId);
//                    if (null != ageStr) {
//                        String[] ages = ageStr.split(",");
//                        if (2 == ages.length) {
//                            minAge = NumberUtils.toInt(ages[0]);
//                            maxAge = NumberUtils.toInt(ages[1]);
//                        }
//                    }
//                }
//                if (0 == minAge || 0 == maxAge) {
//                    minAge = 21;
//                    maxAge = 55;
//                }
//                if (age > maxAge || age <= minAge) {
//                    logger.info(sessionId + " 结束CommonServiceImpl.checkUser()方法，返回结果：用户年龄超限");
//                    return new DrainageRsp(DrainageEnum.CODE_RULE_AGE_UNMATCH);
//                }
//            }
//            // 第四步：查询借款信息（规则已过，可以借款）
//            return new DrainageRsp(DrainageEnum.CODE_SUCCESS);
//        } catch (Exception e) {
//            logger.error(sessionId + " 执行CommonServiceImpl.checkUser()方法异常", e);
//            return new DrainageRsp(DrainageEnum.CODE_EXCEPTION);
//        }
//    }
//
//    /**
//     * 提交机审、人工智能
//     *
//     * @param bwBorrower
//     * @param bwOrder
//     * @param channelId
//     * @param thirdOrderNo
//     */
//    @Override
//    public void sumbitAI(BwBorrower bwBorrower, BwOrder bwOrder, Integer channelId, String thirdOrderNo) {
//        //运营商数据解析完毕后修改订单状态
//        SystemAuditDto systemAuditDto = new SystemAuditDto();
//        systemAuditDto.setIncludeAddressBook(0);
//        systemAuditDto.setOrderId(bwOrder.getId());
//        systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//        systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//        systemAuditDto.setName(bwBorrower.getName());
//        systemAuditDto.setPhone(bwBorrower.getPhone());
//        systemAuditDto.setIdCard(bwBorrower.getIdCard());
//        systemAuditDto.setChannel(channelId);
//        systemAuditDto.setThirdOrderId(thirdOrderNo);
//
//        Long reLong = RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
//                JsonUtils.toJson(systemAuditDto));
//        logger.info("修改订单状态，并放入" + reLong + "条redis");
//        bwOrder.setStatusId(2L);
//        bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//        bwOrderService.updateBwOrder(bwOrder);
//        logger.info("开始修改订单提交时间");
//        // 修改工单进程表
//        BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//        bwOrderProcessRecord.setOrderId(bwOrder.getId());
//        bwOrderProcessRecord.setSubmitTime(new Date());
//        bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//    }
//
//    /**
//     * 批处理插入
//     * <p>
//     * 但需要注意的是主键生成策略需要这样改”@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")“，
//     * 否则批处理不生效，原因是通用Mapper的问题。具体可参考{@link BwContactList#id}
//     *
//     * @param collection
//     * @param mapper
//     */
//    @Override
//    public void batch(Collection<?> collection, Mapper mapper) {
//        if (collection.isEmpty()) {
//            return;
//        }
//        long start = System.currentTimeMillis();
//        //批处理执行
//        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory()
//                .openSession(ExecutorType.BATCH, false)) {
//            Iterator<?> iterator = collection.iterator();
//            while (iterator.hasNext()) {
//                mapper.insert(iterator.next());
//            }
//            sqlSession.commit();
//        }
//        long end = System.currentTimeMillis();
//        logger.info("批处理：{}条，消耗时间：{}毫秒", collection.size(), end - start);
//    }
//}
