//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.waterelephant.entity.*;
//import com.waterelephant.mapper.BwOperateVoiceMapper;
//import com.waterelephant.service.*;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.*;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.UserInfo;
//import com.waterelephant.sxyDrainage.service.AsyncXjbkTask;
//import com.waterelephant.sxyDrainage.service.XjbkOperatorReportService;
//import com.waterelephant.sxyDrainage.utils.xianJinBaiKa.XianJinBaiKaConstant;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.*;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.log4j.Logger;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * (code:xjbk001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 14:24 2018/6/25
// * @Modified By:
// */
//@Component
//public class AsyncXjbkTaskImpl implements AsyncXjbkTask {
//    private Logger logger = Logger.getLogger(AsyncXjbkTaskImpl.class);
//    private String channelStr = XianJinBaiKaConstant.CHANNELID;
//
//    @Autowired
//    private BwOperateBasicService bwOperateBasicService;
//    @Autowired
//    private BwOperateVoiceService bwOperateVoiceService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//    @Autowired
//    private XjbkOperatorReportService xjbkOperatorReportService;
//    @Autowired
//    private IBwIdentityCardService bwIdentityCardService;
//    @Autowired
//    private BwTripartiteMarkService bwTripartiteMarkService;
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//
//    @Async("xjbkTaskExecutor")
//    @Override
//    public void saveOperatorData(Long sessionId, UserVerify userVerify, UserInfo userInfo, BwOrder bwOrder,
//                                 BwBorrower bwBorrower, String thirdOrderNo) {
//        logger.info(sessionId + ":开始AsyncXjbkTaskImpl saveOperatorData method: orderId=" + bwOrder.getId());
//        try {
//            // 运营商基础信息
//            OperatorVerify operatorVerify = userVerify.getOperator_verify();
//            String dataSource = "", idCard = "", cellPhone = "", realName = "", regTime = "";
//            if (null != operatorVerify) {
//                dataSource = operatorVerify.getDatasource();
//
//                BasicVo basicVo = operatorVerify.getBasic();
//                if (null != basicVo) {
//                    idCard = basicVo.getIdcard();
//                    cellPhone = basicVo.getCell_phone();
//                    realName = basicVo.getReal_name();
//                    regTime = basicVo.getReg_time();
//                }
//            }
//            BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(bwBorrower.getId());
//            if (bwOperateBasic == null) {
//                bwOperateBasic = new BwOperateBasic();
//                bwOperateBasic.setBorrowerId(bwBorrower.getId());
//                bwOperateBasic.setUserSource(dataSource);
//                bwOperateBasic.setIdCard(idCard);
//                bwOperateBasic.setPhone(cellPhone);
//                bwOperateBasic.setRealName(realName);
//                if (StringUtils.isNotBlank(regTime)) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    bwOperateBasic.setRegTime(sdf.parse(regTime));
//                }
//                bwOperateBasic.setCreateTime(new Date());
//
//                bwOperateBasicService.save(bwOperateBasic);
//            } else {
//                bwOperateBasic.setUpdateTime(new Date());
//                bwOperateBasic.setUserSource(dataSource);
//                bwOperateBasic.setIdCard(idCard);
//                bwOperateBasic.setPhone(cellPhone);
//                bwOperateBasic.setRealName(realName);
//                if (StringUtils.isNotBlank(regTime)) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    bwOperateBasic.setRegTime(sdf.parse(regTime));
//                }
//
//                bwOperateBasicService.update(bwOperateBasic);
//            }
//            logger.info(sessionId + ">>> 处理运营商基础信息");
//
//            // 通话记录
//            Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(bwBorrower.getId());
//            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//            //批处理
//            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//                BwOperateVoiceMapper bwOperateVoiceMapper = sqlSession.getMapper(BwOperateVoiceMapper.class);
//                if (null != operatorVerify) {
//                    List<CallsVo> callList = operatorVerify.getCalls();
//                    if (CollectionUtils.isNotEmpty(callList)) {
//                        for (CallsVo callsVo : callList) {
//                            try {
//                                Date jsonCallData = sdf.parse(callsVo.getStart_time());
//                                // 通话记录采取最新追加的方式
//                                if (callDate == null || jsonCallData.after(callDate)) {
//                                    BwOperateVoice bwOperateVoice = new BwOperateVoice();
//                                    bwOperateVoice.setBorrower_id(bwBorrower.getId());
//                                    bwOperateVoice.setUpdateTime(new Date());
//                                    bwOperateVoice.setCall_time(callsVo.getStart_time());
//                                    bwOperateVoice.setCall_type("主叫".equals(callsVo.getInit_type()) ? 1 : 2);
//                                    String otherCellPhone = callsVo.getOther_cell_phone();
//                                    if (otherCellPhone != null && otherCellPhone.length() > 20) {
//                                        otherCellPhone = otherCellPhone.substring(0, 19);
//                                    }
//                                    bwOperateVoice.setReceive_phone(otherCellPhone);
//                                    bwOperateVoice.setTrade_addr(callsVo.getPlace());
//                                    bwOperateVoice.setTrade_time(callsVo.getUse_time());
//
//                                    bwOperateVoiceMapper.insert(bwOperateVoice);
//                                }
//                            } catch (Exception e) {
//                                logger.error(sessionId + ":插入单条通话记录异常，忽略该条记录", e);
//                            }
//                        }
//                    }
//                }
//                sqlSession.commit();
//            }
//            logger.info(sessionId + ">>> 处理通话记录信息 ");
//
//            // 插入运营商认证记录
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 1, Integer.parseInt(channelStr));
//
//            // 处理运营商报告数据
//            try {
//                Object object = userVerify.getOperator_report_verify();
//                if (!CommUtils.isNull(object)) {
//                    String operaterData = JSON.toJSONString(object);
//                    xjbkOperatorReportService.saveOperatorReport(sessionId, bwOrder.getId(), operaterData);
//                }
//            } catch (Exception e) {
//                logger.error(sessionId + "运营商报告数据解析异常", e);
//            }
//            logger.info(sessionId + ">>> 解析运营商报告数据");
//
//            //添加获取白骑士数据
//            try {
//                logger.info(sessionId + ":开始白骑士校验，orderId=" + bwOrder.getId());
//                String url = "https://www.sxfq.com/loanapp-api-web/v3/app/order/a10/getSanFangBqs.do";
//                Map<String, String> params = new HashMap<>(16);
//                params.put("orderId", String.valueOf(bwOrder.getId()));
//                String string = HttpClientHelper.post(url, "utf-8", params);
//                logger.info(sessionId + ":结束白骑士校验，orderId=" + bwOrder.getId() + ",返回结果：" + string);
//            } catch (Exception e) {
//                logger.error(sessionId + ":获取白骑士数据异常 ");
//            }
//
//            Object idcardInfos = userVerify.getIdcard_info();
//            String s = JSON.toJSONString(idcardInfos);
//            IdCardInfos idCardInfos = JSON.parseObject(s, IdCardInfos.class);
//            IdCardInfo idcardInfo = idCardInfos.getIdcard_info();
//            // 认证图片
//            String frontFile = idcardInfo.getId_number_z_picture();
//            String backFile = idcardInfo.getId_number_f_picture();
//            String natureFile = idcardInfo.getFace_recognition_picture();
//
//            // 身份证正面照
//            String frontImage = UploadToCssUtils.urlUpload(frontFile, bwOrder.getId() + "_01");
//            logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//            // 保存身份证正面照
//            thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, bwOrder.getId(), bwBorrower.getId(), 0);
//
//            // 身份证反面照
//            String backImage = UploadToCssUtils.urlUpload(backFile, bwOrder.getId() + "_02");
//            logger.info(sessionId + ">>> 身份证反面 " + backImage);
//            // 保存身份证反面照
//            thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, bwOrder.getId(), bwBorrower.getId(), 0);
//
//            // 手持照
//            String handerImage = UploadToCssUtils.urlUpload(natureFile, bwOrder.getId() + "_03");
//            logger.info(sessionId + ">>> 手持照/人脸 " + handerImage);
//            // 保存手持照
//            thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, bwOrder.getId(), bwBorrower.getId(), 0);
//
//            logger.info(sessionId + ">>> 处理认证图片 ");
//
//            // 保存身份证信息
//            BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//            bwIdentityCard.setBorrowerId(bwBorrower.getId());
//            bwIdentityCard = bwIdentityCardService.findBwIdentityCardByAttr(bwIdentityCard);
//            String ocrStartTime = idcardInfo.getOcr_start_time();
//            String ocrEndTime = idcardInfo.getOcr_end_time();
//            if (ocrEndTime != null && ocrStartTime != null) {
//                ocrEndTime = ocrEndTime.replaceAll("-", ".");
//                ocrStartTime = ocrStartTime.replaceAll("-", ".");
//            }
//            if (bwIdentityCard == null) {
//                bwIdentityCard = new BwIdentityCard2();
//                bwIdentityCard.setBorrowerId(bwBorrower.getId());
//                bwIdentityCard.setCreateTime(new Date());
//                bwIdentityCard.setAddress(idcardInfo.getOcr_address());
//                bwIdentityCard.setGender(idcardInfo.getOcr_sex());
//                bwIdentityCard.setBirthday(idcardInfo.getOcr_birthday());
//                bwIdentityCard.setIdCardNumber(idcardInfo.getOcr_id_number());
//                bwIdentityCard.setIssuedBy(idcardInfo.getOcr_issued_by());
//                bwIdentityCard.setName(idcardInfo.getOcr_name());
//                bwIdentityCard.setRace(idcardInfo.getOcr_race());
//                bwIdentityCard.setValidDate(ocrStartTime + "-" + ocrEndTime);
//                bwIdentityCardService.saveBwIdentityCard(bwIdentityCard);
//            } else {
//                bwIdentityCard.setAddress(idcardInfo.getOcr_address());
//                bwIdentityCard.setGender(idcardInfo.getOcr_sex());
//                bwIdentityCard.setBirthday(idcardInfo.getOcr_birthday());
//                bwIdentityCard.setIdCardNumber(idcardInfo.getOcr_id_number());
//                bwIdentityCard.setIssuedBy(idcardInfo.getOcr_issued_by());
//                bwIdentityCard.setName(idcardInfo.getOcr_name());
//                bwIdentityCard.setRace(idcardInfo.getOcr_race());
//                bwIdentityCard.setValidDate(ocrStartTime + "-" + ocrEndTime);
//                bwIdentityCard.setUpdateTime(new Date());
//                bwIdentityCardService.updateBwIdentityCard(bwIdentityCard);
//            }
//            // 插入身份认证记录
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 3, Integer.parseInt(channelStr));
//            logger.info(sessionId + ">>> 新增/更新身份证信息");
//
//            logger.info(sessionId + ":结束AsyncXjbkTaskImpl saveOperatorData method: orderId=" + bwOrder.getId());
//        } catch (Exception e) {
//            BwTripartiteMark bwTripartiteMark = new BwTripartiteMark();
//            bwTripartiteMark.setChannelId(Integer.valueOf(channelStr));
//            bwTripartiteMark.setIndexKey(bwOrder.getId() + "");
//            String message = e.getMessage();
//            if (message.length() > 250) {
//                message = message.substring(0, 250);
//            }
//            bwTripartiteMark.setAddr(message);
//            bwTripartiteMark.setCreateTime(new Date());
//            bwTripartiteMarkService.save(bwTripartiteMark);
//            logger.info(sessionId + "：保存bwTripartiteMark成功");
//            logger.error(sessionId + ":执行AsyncXjbkTaskImpl saveOperatorData method异常：", e);
//        }
//    }
//
//}
