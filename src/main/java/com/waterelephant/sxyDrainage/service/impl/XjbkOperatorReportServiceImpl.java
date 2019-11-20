//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.entity.*;
//import com.waterelephant.mapper.*;
//import com.waterelephant.service.*;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.*;
//import com.waterelephant.sxyDrainage.service.XjbkOperatorReportService;
//import com.waterelephant.utils.CommUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.log4j.Logger;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * Module:(code:xjbk001)
// * <p>
// * XjbkOperatorReportServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//@Service
//public class XjbkOperatorReportServiceImpl implements XjbkOperatorReportService {
//    private Logger logger = Logger.getLogger(XjbkOperatorReportServiceImpl.class);
//
//    @Autowired
//    private BwXjbkApplicationCheckService bwXjbkApplicationCheckService;
//    @Autowired
//    private BwXjbkBehaviorCheckService bwXjbkBehaviorCheckService;
//    @Autowired
//    private BwXjbkCellBehaviorService bwXjbkCellBehaviorService;
//    @Autowired
//    private BwXjbkCollectionContactService bwXjbkCollectionContactService;
//    @Autowired
//    private BwXjbkContactListService bwXjbkContactListService;
//    @Autowired
//    private BwXjbkContactRegionService bwXjbkContactRegionService;
//    @Autowired
//    private BwXjbkDeliverAddressService bwXjbkDeliverAddressService;
//    @Autowired
//    private BwXjbkEbusinessExpenseService bwXjbkEbusinessExpenseService;
//    @Autowired
//    private BwXjbkMainServiceService bwXjbkMainServiceService;
//    @Autowired
//    private BwXjbkReportService bwXjbkReportService;
//    @Autowired
//    private BwXjbkTripInfoService bwXjbkTripInfoService;
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//
//
//    @Override
//    public void saveOperatorReport(Long sessionId, Long orderId, String operaterData) {
//        logger.info(sessionId + ": start saveOperatorReport method: orderId=" + orderId);
//        try {
//            if (!CommUtils.isNull(operaterData)) {
//                OperatorReportVerify operatorReportVerify = JSONObject.parseObject(operaterData, OperatorReportVerify.class);
//
//                Report report = operatorReportVerify.getReport();
//                if (!CommUtils.isNull(report)) {
//                    BwXjbkReport bwXjbkReport = new BwXjbkReport();
//                    bwXjbkReport.setOrderId(orderId);
//                    bwXjbkReport = bwXjbkReportService.findByAttr(bwXjbkReport);
//                    if (bwXjbkReport == null) {
//                        bwXjbkReport = new BwXjbkReport();
//                        bwXjbkReport.setOrderId(orderId);
//                        bwXjbkReport.setReportTime(report.getUpdate_time());
//                        bwXjbkReport.setCreateTime(new Date());
//
//                        bwXjbkReportService.save(bwXjbkReport);
//                    } else {
//                        bwXjbkReport.setOrderId(orderId);
//                        bwXjbkReport.setReportTime(report.getUpdate_time());
//                        bwXjbkReport.setUpdateTime(new Date());
//
//                        bwXjbkReportService.update(bwXjbkReport);
//                    }
//                }
//                logger.info(sessionId + ": save or update bwxjbkreport: orderId=" + orderId);
//
//                BwXjbkApplicationCheck bwXjbkApplicationCheck = new BwXjbkApplicationCheck();
//                bwXjbkApplicationCheck.setOrderId(orderId);
//                List<BwXjbkApplicationCheck> bwXjbkApplicationCheckList = bwXjbkApplicationCheckService.findByAttr(bwXjbkApplicationCheck);
//                if (CollectionUtils.isEmpty(bwXjbkApplicationCheckList)) {
//
//                    List<ApplicationCheck> applicationChecks = operatorReportVerify.getApplication_check();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkApplicationCheckMapper bwXjbkApplicationCheckMapper = sqlSession.getMapper(BwXjbkApplicationCheckMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(applicationChecks)) {
//                            for (ApplicationCheck applicationCheck : applicationChecks) {
//                                try {
//                                    String appPoint = applicationCheck.getApp_point();
//                                    CheckPoints checkPoints = applicationCheck.getCheck_points();
//
//                                    bwXjbkApplicationCheck = new BwXjbkApplicationCheck();
//                                    bwXjbkApplicationCheck.setOrderId(orderId);
//                                    bwXjbkApplicationCheck.setAppPoint(appPoint);
//                                    bwXjbkApplicationCheck.setCreateTime(new Date());
//
//                                    if (checkPoints != null) {
//                                        String keyValue = checkPoints.getKey_value();
//                                        if (keyValue != null && keyValue.length() > 50) {
//                                            keyValue = keyValue.substring(0, 49);
//                                        }
//                                        bwXjbkApplicationCheck.setKeyValue(keyValue);
//                                        bwXjbkApplicationCheck.setGender(checkPoints.getGender());
//                                        bwXjbkApplicationCheck.setAge(checkPoints.getAge());
//                                        bwXjbkApplicationCheck.setProvince(checkPoints.getProvince());
//                                        bwXjbkApplicationCheck.setCity(checkPoints.getCity());
//                                        bwXjbkApplicationCheck.setRegion(checkPoints.getRegion());
//                                        bwXjbkApplicationCheck.setRegTime(checkPoints.getReg_time());
//                                        bwXjbkApplicationCheck.setCheckName(checkPoints.getCheck_name());
//                                        bwXjbkApplicationCheck.setCheckIdcard(checkPoints.getCheck_idcard());
//                                        bwXjbkApplicationCheck.setCheckEbusiness(checkPoints.getCheck_ebusiness());
//                                        bwXjbkApplicationCheck.setCheckAddr(checkPoints.getCheck_addr());
//                                        bwXjbkApplicationCheck.setRelationship(checkPoints.getRelationship());
//                                        bwXjbkApplicationCheck.setContactName(checkPoints.getContact_name());
//                                        bwXjbkApplicationCheck.setCheckXiaohao(checkPoints.getCheck_xiaohao());
//                                        bwXjbkApplicationCheck.setCheckMobile(checkPoints.getCheck_mobile());
//
//                                        CourtBlacklist courtBlacklist = checkPoints.getCourt_blacklist();
//                                        FinancialBlacklist financialBlacklist = checkPoints.getFinancial_blacklist();
//
//                                        if (courtBlacklist != null) {
//                                            bwXjbkApplicationCheck.setCourtArised(courtBlacklist.isArised() ? 1 : 0);
//                                            List<Object> blackTypes = courtBlacklist.getBlack_type();
//                                            String courtBlackType = null;
//                                            if (blackTypes != null) {
//                                                courtBlackType = JSON.toJSONString(blackTypes);
//                                            }
//                                            bwXjbkApplicationCheck.setCourtBlackType(courtBlackType);
//                                        }
//                                        if (financialBlacklist != null) {
//                                            bwXjbkApplicationCheck.setFinancialArised(financialBlacklist.isArised() ? 1 : 0);
//                                            List<Object> blackTypes = financialBlacklist.getBlack_type();
//                                            String financialBlackType = null;
//                                            if (blackTypes != null) {
//                                                financialBlackType = JSON.toJSONString(blackTypes);
//                                            }
//                                            bwXjbkApplicationCheck.setCourtBlackType(financialBlackType);
//                                        }
//                                    }
//
//                                    bwXjbkApplicationCheckMapper.insert(bwXjbkApplicationCheck);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ":save bwXjbkApplicationCheck exception", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkApplicationCheck: orderId=" + orderId);
//
//                BwXjbkBehaviorCheck bwXjbkBehaviorCheck = new BwXjbkBehaviorCheck();
//                bwXjbkBehaviorCheck.setOrderId(orderId);
//                List<BwXjbkBehaviorCheck> bwXjbkBehaviorCheckList = bwXjbkBehaviorCheckService.findByAttr(bwXjbkBehaviorCheck);
//                if (CollectionUtils.isEmpty(bwXjbkBehaviorCheckList)) {
//
//                    List<BehaviorCheck> behaviorChecks = operatorReportVerify.getBehavior_check();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkBehaviorCheckMapper bwXjbkBehaviorCheckMapper = sqlSession.getMapper(BwXjbkBehaviorCheckMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(behaviorChecks)) {
//                            for (BehaviorCheck behaviorCheck : behaviorChecks) {
//                                try {
//                                    bwXjbkBehaviorCheck = new BwXjbkBehaviorCheck();
//                                    bwXjbkBehaviorCheck.setOrderId(orderId);
//                                    bwXjbkBehaviorCheck.setCheckPoint(behaviorCheck.getCheck_point());
//                                    bwXjbkBehaviorCheck.setCheckPointCn(behaviorCheck.getCheck_point_cn());
//                                    bwXjbkBehaviorCheck.setResult(behaviorCheck.getResult());
//                                    String evidence = behaviorCheck.getEvidence();
//                                    if (evidence != null && evidence.length() > 500) {
//                                        evidence = evidence.substring(0, 495);
//                                    }
//                                    bwXjbkBehaviorCheck.setEvidence(evidence);
//                                    bwXjbkBehaviorCheck.setScore(behaviorCheck.getScore());
//                                    bwXjbkBehaviorCheck.setCreateTime(new Date());
//
//                                    bwXjbkBehaviorCheckMapper.insert(bwXjbkBehaviorCheck);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ":save bwXjbkBehaviorCheck exception ", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkBehaviorCheck: orderId=" + orderId);
//
//                BwXjbkCellBehavior bwXjbkCellBehavior = new BwXjbkCellBehavior();
//                bwXjbkCellBehavior.setOrderId(orderId);
//                List<BwXjbkCellBehavior> bwXjbkCellBehaviorList = bwXjbkCellBehaviorService.findByAttr(bwXjbkCellBehavior);
//                if (CollectionUtils.isEmpty(bwXjbkCellBehaviorList)) {
//
//                    List<CellBehavior> cellBehaviors = operatorReportVerify.getCell_behavior();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkCellBehaviorMapper bwXjbkCellBehaviorMapper = sqlSession.getMapper(BwXjbkCellBehaviorMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(cellBehaviors)) {
//                            for (CellBehavior cellBehavior : cellBehaviors) {
//                                String phoneNum = cellBehavior.getPhone_num();
//                                if (phoneNum != null && phoneNum.length() > 20) {
//                                    phoneNum = phoneNum.substring(0, 19);
//                                }
//                                List<Behavior> behaviors = cellBehavior.getBehavior();
//                                if (CollectionUtils.isNotEmpty(behaviors)) {
//                                    for (Behavior behavior : behaviors) {
//                                        try {
//                                            bwXjbkCellBehavior = new BwXjbkCellBehavior();
//                                            bwXjbkCellBehavior.setOrderId(orderId);
//                                            bwXjbkCellBehavior.setPhoneNum(phoneNum);
//                                            bwXjbkCellBehavior.setSmsCnt(behavior.getSms_cnt());
//                                            bwXjbkCellBehavior.setCellPhoneNum(behavior.getCell_phone_num());
//                                            bwXjbkCellBehavior.setNetFlow(behavior.getNet_flow());
//                                            bwXjbkCellBehavior.setTotalAmount(behavior.getTotal_amount());
//                                            bwXjbkCellBehavior.setCallOutTime(behavior.getCall_out_time());
//                                            bwXjbkCellBehavior.setCellMth(behavior.getCell_mth());
//                                            bwXjbkCellBehavior.setCellLoc(behavior.getCell_loc());
//                                            bwXjbkCellBehavior.setCallCnt(behavior.getCall_cnt());
//                                            bwXjbkCellBehavior.setCellOperatorZh(behavior.getCell_operator_zh());
//                                            bwXjbkCellBehavior.setCallOutCnt(behavior.getCall_out_cnt());
//                                            bwXjbkCellBehavior.setCellOperator(behavior.getCell_operator());
//                                            bwXjbkCellBehavior.setCallInCnt(behavior.getCall_in_cnt());
//                                            bwXjbkCellBehavior.setCallInTime(behavior.getCall_in_time());
//                                            bwXjbkCellBehavior.setCreateTime(new Date());
//
//                                            bwXjbkCellBehaviorMapper.insert(bwXjbkCellBehavior);
//                                        } catch (Exception e) {
//                                            logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ":save bwXjbkCellBehavior exception", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkCellBehavior: orderId=" + orderId);
//
//                BwXjbkCollectionContact bwXjbkCollectionContact = new BwXjbkCollectionContact();
//                bwXjbkCollectionContact.setOrderId(orderId);
//                List<BwXjbkCollectionContact> bwXjbkCollectionContactList = bwXjbkCollectionContactService.findByAttr(bwXjbkCollectionContact);
//                if (CollectionUtils.isEmpty(bwXjbkCollectionContactList)) {
//
//                    List<CollectionContact> collectionContacts = operatorReportVerify.getCollection_contact();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkCollectionContactMapper bwXjbkCollectionContactMapper = sqlSession.getMapper(BwXjbkCollectionContactMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(collectionContacts)) {
//                            for (CollectionContact collectionContact : collectionContacts) {
//                                try {
//                                    bwXjbkCollectionContact = new BwXjbkCollectionContact();
//                                    bwXjbkCollectionContact.setOrderId(orderId);
//                                    bwXjbkCollectionContact.setContactName(collectionContact.getContact_name());
//                                    bwXjbkCollectionContact.setBeginDate(collectionContact.getBegin_date());
//                                    bwXjbkCollectionContact.setEndDate(collectionContact.getEnd_date());
//                                    bwXjbkCollectionContact.setTotalCount(collectionContact.getTotal_count());
//                                    bwXjbkCollectionContact.setTotalAmount(collectionContact.getTotal_amount());
//
//                                    List<Object> contactDetails = collectionContact.getContact_details();
//                                    if (contactDetails != null) {
//                                        bwXjbkCollectionContact.setContactDetails(JSON.toJSONString(contactDetails));
//                                    }
//                                    bwXjbkCollectionContact.setPhoneNum(collectionContact.getPhone_num());
//                                    bwXjbkCollectionContact.setPhoneNumLoc(collectionContact.getPhone_num_loc());
//                                    bwXjbkCollectionContact.setCallCnt(collectionContact.getCall_cnt());
//                                    bwXjbkCollectionContact.setCallLen(collectionContact.getCall_len());
//                                    bwXjbkCollectionContact.setCallOutCnt(collectionContact.getCall_out_cnt());
//                                    bwXjbkCollectionContact.setCallInCnt(collectionContact.getCall_in_cnt());
//                                    bwXjbkCollectionContact.setSmsCnt(collectionContact.getSms_cnt());
//                                    bwXjbkCollectionContact.setTransStart(collectionContact.getTrans_start());
//                                    bwXjbkCollectionContact.setTransEnd(collectionContact.getTrans_end());
//                                    bwXjbkCollectionContact.setCreateTime(new Date());
//
//                                    bwXjbkCollectionContactMapper.insert(bwXjbkCollectionContact);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ": save bwXjbkCollectionContact exception", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkCollectionContact: orderId=" + orderId);
//
//                BwXjbkContactList bwXjbkContactList = new BwXjbkContactList();
//                bwXjbkContactList.setOrderId(orderId);
//                List<BwXjbkContactList> bwXjbkContactListList = bwXjbkContactListService.findByAttr(bwXjbkContactList);
//                if (CollectionUtils.isEmpty(bwXjbkContactListList)) {
//
//                    List<ContactList> contactLists = operatorReportVerify.getContact_list();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkContactListMapper bwXjbkContactListMapper = sqlSession.getMapper(BwXjbkContactListMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(contactLists)) {
//                            for (ContactList contactList : contactLists) {
//                                try {
//                                    bwXjbkContactList = new BwXjbkContactList();
//                                    bwXjbkContactList.setOrderId(orderId);
//                                    String phoneNum = contactList.getPhone_num();
//                                    if (phoneNum != null && phoneNum.length() > 20) {
//                                        phoneNum = phoneNum.substring(0, 19);
//                                    }
//                                    bwXjbkContactList.setPhoneNum(phoneNum);
//                                    String phoneNumLoc = contactList.getPhone_num_loc();
//                                    if (phoneNumLoc != null && phoneNumLoc.length() > 10) {
//                                        phoneNumLoc = phoneNumLoc.substring(0, 9);
//                                    }
//                                    bwXjbkContactList.setPhoneNumLoc(phoneNumLoc);
//                                    String contactName = contactList.getContact_name();
//                                    if (contactName != null && contactName.length() > 50) {
//                                        contactName = contactName.substring(0, 49);
//                                    }
//                                    bwXjbkContactList.setContactName(contactName);
//                                    String needsType = contactList.getNeeds_type();
//                                    if (needsType != null && needsType.length() > 10) {
//                                        needsType = needsType.substring(0, 9);
//                                    }
//                                    bwXjbkContactList.setNeedsType(needsType);
//                                    bwXjbkContactList.setCallCnt(contactList.getCall_cnt());
//                                    bwXjbkContactList.setCallLen(contactList.getCall_len());
//                                    bwXjbkContactList.setCallOutCnt(contactList.getCall_out_cnt());
//                                    bwXjbkContactList.setCallOutLen(contactList.getCall_out_len());
//                                    bwXjbkContactList.setCallInCnt(contactList.getCall_in_cnt());
//                                    bwXjbkContactList.setCallInLen(contactList.getCall_in_len());
//                                    bwXjbkContactList.setpRelation(contactList.getP_relation());
//                                    bwXjbkContactList.setContactOnew(contactList.getContact_1w());
//                                    bwXjbkContactList.setContactOnem(contactList.getContact_1m());
//                                    bwXjbkContactList.setContactThreem(contactList.getContact_3m());
//                                    bwXjbkContactList.setContactThreemPlus(contactList.getContact_3m_plus());
//                                    bwXjbkContactList.setContactEarlyMorning(contactList.getContact_early_morning());
//                                    bwXjbkContactList.setContactMorning(contactList.getContact_morning());
//                                    bwXjbkContactList.setContactNoon(contactList.getContact_noon());
//                                    bwXjbkContactList.setContactAfternoon(contactList.getContact_afternoon());
//                                    bwXjbkContactList.setContactNight(contactList.getContact_night());
//                                    bwXjbkContactList.setContactAllDay(contactList.isContact_all_day() ? 1 : 0);
//                                    bwXjbkContactList.setContactWeekday(contactList.getContact_weekday());
//                                    bwXjbkContactList.setContactWeekend(contactList.getContact_weekend());
//                                    bwXjbkContactList.setContactHoliday(contactList.getContact_holiday());
//                                    bwXjbkContactList.setCreateTime(new Date());
//
//                                    bwXjbkContactListMapper.insert(bwXjbkContactList);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ": save bwXjbkContactList exception", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkContactList: orderId=" + orderId);
//
//                BwXjbkContactRegion bwXjbkContactRegion = new BwXjbkContactRegion();
//                bwXjbkContactRegion.setOrderId(orderId);
//                List<BwXjbkContactRegion> bwXjbkContactRegionList = bwXjbkContactRegionService.findByAttr(bwXjbkContactRegion);
//                if (CollectionUtils.isEmpty(bwXjbkContactRegionList)) {
//
//                    List<ContactRegion> contactRegions = operatorReportVerify.getContact_region();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkContactRegionMapper bwXjbkContactRegionMapper = sqlSession.getMapper(BwXjbkContactRegionMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(contactRegions)) {
//                            for (ContactRegion contactRegion : contactRegions) {
//                                try {
//                                    bwXjbkContactRegion = new BwXjbkContactRegion();
//                                    bwXjbkContactRegion.setOrderId(orderId);
//                                    bwXjbkContactRegion.setRegionLoc(contactRegion.getRegion_loc());
//                                    bwXjbkContactRegion.setRegionUniqNumCnt(contactRegion.getRegion_uniq_num_cnt());
//                                    bwXjbkContactRegion.setRegionCallInCnt(contactRegion.getRegion_call_in_cnt());
//                                    bwXjbkContactRegion.setRegionCallOutCnt(contactRegion.getRegion_call_out_cnt());
//                                    bwXjbkContactRegion.setRegionCallInTime(contactRegion.getRegion_call_in_time());
//                                    bwXjbkContactRegion.setRegionCallOutTime(contactRegion.getRegion_call_out_time());
//                                    bwXjbkContactRegion.setRegionAvgCallInTime(contactRegion.getRegion_avg_call_in_time());
//                                    bwXjbkContactRegion.setRegionAvgCallOutTime(contactRegion.getRegion_avg_call_out_time());
//                                    bwXjbkContactRegion.setRegionCallInCntPct(contactRegion.getRegion_call_in_cnt_pct());
//                                    bwXjbkContactRegion.setRegionCallInTimePct(contactRegion.getRegion_call_in_time_pct());
//                                    bwXjbkContactRegion.setRegionCallOutCntPct(contactRegion.getRegion_call_out_cnt_pct());
//                                    bwXjbkContactRegion.setRegionCallOutTimePct(contactRegion.getRegion_call_out_time_pct());
//                                    bwXjbkContactRegion.setCreateTime(new Date());
//
//                                    bwXjbkContactRegionMapper.insert(bwXjbkContactRegion);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ": save bwXjbkContactRegion exception", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkContactRegion: orderId=" + orderId);
//
//                BwXjbkDeliverAddress bwXjbkDeliverAddress = new BwXjbkDeliverAddress();
//                bwXjbkDeliverAddress.setOrderId(orderId);
//                List<BwXjbkDeliverAddress> bwXjbkDeliverAddressList = bwXjbkDeliverAddressService.findByAttr(bwXjbkDeliverAddress);
//                if (CollectionUtils.isEmpty(bwXjbkDeliverAddressList)) {
//
//                    List<DeliverAddress> deliverAddresses = operatorReportVerify.getDeliver_address();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkDeliverAddressMapper bwXjbkDeliverAddressMapper = sqlSession.getMapper(BwXjbkDeliverAddressMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(deliverAddresses)) {
//                            for (DeliverAddress deliverAddress : deliverAddresses) {
//                                try {
//                                    bwXjbkDeliverAddress = new BwXjbkDeliverAddress();
//                                    bwXjbkDeliverAddress.setOrderId(orderId);
//                                    bwXjbkDeliverAddress.setAddress(deliverAddress.getAddress());
//                                    bwXjbkDeliverAddress.setLng(deliverAddress.getLng());
//                                    bwXjbkDeliverAddress.setLat(deliverAddress.getLat());
//                                    bwXjbkDeliverAddress.setPredictAddrType(deliverAddress.getPredict_addr_type());
//                                    bwXjbkDeliverAddress.setBeginDate(deliverAddress.getBegin_date());
//                                    bwXjbkDeliverAddress.setEndDate(deliverAddress.getEnd_date());
//                                    bwXjbkDeliverAddress.setTotalAmount(deliverAddress.getTotal_amount());
//                                    bwXjbkDeliverAddress.setTotalCount(deliverAddress.getTotal_count());
//                                    bwXjbkDeliverAddress.setCreateTime(new Date());
//
//                                    List<Object> receiver = deliverAddress.getReceiver();
//                                    if (receiver != null) {
//                                        bwXjbkDeliverAddress.setReceiver(JSON.toJSONString(receiver));
//                                    }
//
//                                    bwXjbkDeliverAddress.setCount(deliverAddress.getCount());
//                                    bwXjbkDeliverAddress.setAmount(deliverAddress.getAmount());
//                                    bwXjbkDeliverAddress.setName(deliverAddress.getName());
//
//                                    List<String> phoneNumList = deliverAddress.getPhone_num_list();
//                                    if (phoneNumList != null) {
//                                        bwXjbkDeliverAddress.setPhoneNumList(JSON.toJSONString(phoneNumList));
//                                    }
//
//                                    bwXjbkDeliverAddressMapper.insert(bwXjbkDeliverAddress);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ": save bwXjbkDeliverAddress exception", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkDeliverAddress: orderId=" + orderId);
//
//                BwXjbkEbusinessExpense bwXjbkEbusinessExpense = new BwXjbkEbusinessExpense();
//                bwXjbkEbusinessExpense.setOrderId(orderId);
//                List<BwXjbkEbusinessExpense> bwXjbkEbusinessExpenseList = bwXjbkEbusinessExpenseService.findByAttr(bwXjbkEbusinessExpense);
//                if (CollectionUtils.isEmpty(bwXjbkEbusinessExpenseList)) {
//
//                    List<EbusinessExpense> ebusinessExpenses = operatorReportVerify.getEbusiness_expense();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkEbusinessExpenseMapper bwXjbkEbusinessExpenseMapper = sqlSession.getMapper(BwXjbkEbusinessExpenseMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(ebusinessExpenses)) {
//                            for (EbusinessExpense ebusinessExpense : ebusinessExpenses) {
//                                try {
//                                    bwXjbkEbusinessExpense = new BwXjbkEbusinessExpense();
//                                    bwXjbkEbusinessExpense.setOrderId(orderId);
//                                    bwXjbkEbusinessExpense.setTransMth(ebusinessExpense.getTrans_mth());
//                                    bwXjbkEbusinessExpense.setAllAmount(ebusinessExpense.getAll_amount());
//                                    bwXjbkEbusinessExpense.setAllCount(ebusinessExpense.getAll_count());
//                                    bwXjbkEbusinessExpense.setCreateTime(new Date());
//
//                                    List<String> allCategory = ebusinessExpense.getAll_category();
//                                    if (allCategory != null) {
//                                        bwXjbkEbusinessExpense.setAllCategory(JSON.toJSONString(allCategory));
//                                    }
//
//                                    bwXjbkEbusinessExpenseMapper.insert(bwXjbkEbusinessExpense);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ": save bwXjbkEbusinessExpense exception", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkEbusinessExpense: orderId=" + orderId);
//
//                BwXjbkMainService bwXjbkMainService = new BwXjbkMainService();
//                bwXjbkMainService.setOrderId(orderId);
//                List<BwXjbkMainService> bwXjbkMainServiceList = bwXjbkMainServiceService.findByAttr(bwXjbkMainService);
//                if (CollectionUtils.isEmpty(bwXjbkMainServiceList)) {
//
//                    List<MainService> mainServices = operatorReportVerify.getMain_service();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkMainServiceMapper bwXjbkMainServiceMapper = sqlSession.getMapper(BwXjbkMainServiceMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(mainServices)) {
//                            for (MainService mainService : mainServices) {
//                                String companyName = mainService.getCompany_name();
//                                if (companyName != null && companyName.length() > 30) {
//                                    companyName = companyName.substring(0, 29);
//                                }
//                                String companyType = mainService.getCompany_type();
//                                int total = mainService.getTotal_service_cnt();
//                                List<ServiceDetails> seList = mainService.getService_details();
//                                if (CollectionUtils.isNotEmpty(seList)) {
//                                    for (ServiceDetails serviceDetails : seList) {
//                                        try {
//                                            bwXjbkMainService = new BwXjbkMainService();
//                                            bwXjbkMainService.setOrderId(orderId);
//                                            bwXjbkMainService.setCompanyName(companyName);
//                                            bwXjbkMainService.setCompanyType(companyType);
//                                            bwXjbkMainService.setTotalServiceCnt(total);
//                                            bwXjbkMainService.setDetailsInteractCnt(serviceDetails.getInteract_cnt());
//                                            bwXjbkMainService.setDetailsInteractMth(serviceDetails.getInteract_mth());
//                                            bwXjbkMainService.setCreateTime(new Date());
//
//                                            bwXjbkMainServiceMapper.insert(bwXjbkMainService);
//                                        } catch (Exception e) {
//                                            logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ": save bwXjbkMainService exception", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkMainService: orderId=" + orderId);
//
//                BwXjbkTripInfo bwXjbkTripInfo = new BwXjbkTripInfo();
//                bwXjbkTripInfo.setOrderId(orderId);
//                List<BwXjbkTripInfo> bwXjbkTripInfoList = bwXjbkTripInfoService.findByAttr(bwXjbkTripInfo);
//                if (CollectionUtils.isEmpty(bwXjbkTripInfoList)) {
//
//                    List<TripInfo> tripInfos = operatorReportVerify.getTrip_info();
//                    try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                        BwXjbkTripInfoMapper bwXjbkTripInfoMapper = sqlSession.getMapper(BwXjbkTripInfoMapper.class);
//
//                        if (CollectionUtils.isNotEmpty(tripInfos)) {
//                            for (TripInfo tripInfo : tripInfos) {
//                                try {
//                                    bwXjbkTripInfo = new BwXjbkTripInfo();
//                                    bwXjbkTripInfo.setOrderId(orderId);
//                                    bwXjbkTripInfo.setTripDest(tripInfo.getTrip_dest());
//                                    bwXjbkTripInfo.setTripLeave(tripInfo.getTrip_leave());
//                                    bwXjbkTripInfo.setTripStartTime(tripInfo.getTrip_start_time());
//                                    bwXjbkTripInfo.setTripEndTime(tripInfo.getTrip_end_time());
//                                    bwXjbkTripInfo.setTripType(tripInfo.getTrip_type());
//                                    bwXjbkTripInfo.setCreateTime(new Date());
//
//                                    bwXjbkTripInfoMapper.insert(bwXjbkTripInfo);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":插入该条记录异常，忽略", e);
//                                }
//                            }
//                        }
//
//                        sqlSession.commit();
//                    } catch (Exception e) {
//                        logger.error(sessionId + ": save bwXjbkTripInfo exception ", e);
//                    }
//                }
//                logger.info(sessionId + ": save bwXjbkTripInfo: orderId=" + orderId);
//            }
//
//            logger.info(sessionId + ": end saveOperatorReport method: orderId=" + orderId);
//        } catch (Exception e) {
//            logger.error(sessionId + ": doing saveOperatorReport method exception: orderId=" + orderId, e);
//        }
//
//    }
//
//}
