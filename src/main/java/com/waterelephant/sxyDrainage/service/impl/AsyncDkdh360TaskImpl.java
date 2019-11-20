//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.waterelephant.entity.*;
//import com.waterelephant.mapper.*;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwThirdOperateBasicService;
//import com.waterelephant.sxyDrainage.entity.dkdh360.*;
//import com.waterelephant.sxyDrainage.service.AsyncDkdh360Task;
//import com.waterelephant.sxyDrainage.utils.dkdh360util.Dkdh360Constant;
//import com.waterelephant.utils.CommUtils;
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
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/26 17:11
// * @Description: 异步处理实现类
// */
//@Component
//public class AsyncDkdh360TaskImpl implements AsyncDkdh360Task {
//    private Logger logger = Logger.getLogger(AsyncDkdh360TaskImpl.class);
//    private String channelId = Dkdh360Constant.get("channel_id");
//    @Autowired
//    private BwThirdOperateBasicService bwThirdOperateBasicService;
//    @Autowired
//    private BwOperateBasicService bwOperateBasicService;
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//    @Autowired
//    private BwOperateBillMapper bwOperateBillMapper;
//    @Autowired
//    private BwOperateVoiceService bwOperateVoiceService;
//    @Autowired
//    private BwThirdOperateVoiceMapper bwThirdOperateVoiceMapper;
//    @Autowired
//    private BwOperateMsgMapper bwOperateMsgMapper;
//    @Autowired
//    private BwOperateNetMapper bwOperateNetMapper;
//    @Autowired
//    private BwOperateRechargeMapper bwOperateRechargeMapper;
//
//    @Async("dkdh360TaskExecutor")
//    @Override
//    public void saveOperatorData(long sessionId, AddInfoMobile addInfoMobile, Long orderId, Long borrowerId) {
//        logger.info(sessionId + ":开始AsyncDkdh360TaskImpl.saveOperatorData method");
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//            SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd");
//
//            // 获取白骑士数据
//            try {
//                logger.info(sessionId + ":开始白骑士校验，orderId=" + orderId);
//                String url = "https://www.sxfq.com/loanapp-api-web/v3/app/order/a10/getSanFangBqs.do";
//                Map<String, String> params = new HashMap<>(16);
//                params.put("orderId", String.valueOf(orderId));
//                String string = HttpClientHelper.post(url, "utf-8", params);
//                logger.info(sessionId + ":结束白骑士校验，orderId=" + orderId + ",返回结果：" + string);
//            } catch (Exception e) {
//                logger.error(sessionId + ":获取白骑士数据异常 ");
//            }
//
//            List<AddInfoMobileBill> addInfoMobileBills = addInfoMobile.getBill();
//            AddInfoMobileMsg addInfoMobileMsg = addInfoMobile.getMsg();
//            List<AddInfoMobileNet> addInfoMobileNets = addInfoMobile.getNet();
//            List<AddInfoMobileRecharge> addInfoMobileRecharges = addInfoMobile.getRecharge();
//            AddInfoMobileTel addInfoMobileTel = addInfoMobile.getTel();
//            AddInfoMobileUser addInfoMobileUser = addInfoMobile.getUser();
//
//            //用户运营商基础信息
//            BwThirdOperateBasic bwThirdOperateBasic = new BwThirdOperateBasic();
//            bwThirdOperateBasic.setOrderId(orderId);
//            bwThirdOperateBasic = bwThirdOperateBasicService.findByAttr(bwThirdOperateBasic);
//            if (CommUtils.isNull(bwThirdOperateBasic)) {
//                if (!CommUtils.isNull(addInfoMobileUser)) {
//                    bwThirdOperateBasic = new BwThirdOperateBasic();
//                    bwThirdOperateBasic.setOrderId(orderId);
//                    bwThirdOperateBasic.setCreateTime(new Date());
//                    bwThirdOperateBasic.setChannel(Integer.valueOf(channelId));
//                    bwThirdOperateBasic.setUserSource(addInfoMobileUser.getUser_source());
//                    bwThirdOperateBasic.setAddr(addInfoMobileUser.getAddr());
//                    bwThirdOperateBasic.setIdCard(addInfoMobileUser.getId_card());
//                    bwThirdOperateBasic.setRealName(addInfoMobileUser.getReal_name());
//                    bwThirdOperateBasic.setPackageName(addInfoMobileUser.getPackage_name());
//                    bwThirdOperateBasic.setPhone(addInfoMobileUser.getPhone());
//                    bwThirdOperateBasic.setPhoneRemain(addInfoMobileUser.getPhone_remain());
//                    bwThirdOperateBasic.setRegTime(sdf.parse(addInfoMobileUser.getReg_time()));
//                    bwThirdOperateBasic.setPhoneStatus(addInfoMobileUser.getPhone_status());
//                    if (StringUtils.isNotBlank(addInfoMobileUser.getReg_time())) {
//                        bwThirdOperateBasic.setRegTime(sdft.parse(addInfoMobileUser.getReg_time()));
//                    }
//                    bwThirdOperateBasic.setStarLevel(addInfoMobileUser.getStar_level());
//                    bwThirdOperateBasic.setContactPhone(addInfoMobileUser.getContact_phone());
//                    bwThirdOperateBasic.setAuthentication(addInfoMobileUser.getAuthentication());
//                    bwThirdOperateBasicService.save(bwThirdOperateBasic);
//                }
//            }
//            logger.info(sessionId + ":save bwThirdOperateBasic success");
//
//            BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//            if (CommUtils.isNull(bwOperateBasic)) {
//                bwOperateBasic = new BwOperateBasic();
//                bwOperateBasic.setBorrowerId(borrowerId);
//                bwOperateBasic.setUserSource(addInfoMobileUser.getUser_source());
//                bwOperateBasic.setAddr(addInfoMobileUser.getAddr());
//                bwOperateBasic.setIdCard(addInfoMobileUser.getId_card());
//                bwOperateBasic.setRealName(addInfoMobileUser.getReal_name());
//                bwOperateBasic.setPackageName(addInfoMobileUser.getPackage_name());
//                bwOperateBasic.setPhone(addInfoMobileUser.getPhone());
//                bwOperateBasic.setPhoneRemain(addInfoMobileUser.getPhone_remain());
//                bwOperateBasic.setRegTime(sdf.parse(addInfoMobileUser.getReg_time()));
//                bwOperateBasic.setPhoneStatus(addInfoMobileUser.getPhone_status());
//                if (StringUtils.isNotBlank(addInfoMobileUser.getReg_time())) {
//                    bwOperateBasic.setRegTime(sdft.parse(addInfoMobileUser.getReg_time()));
//                }
//                bwOperateBasic.setStarLevel(addInfoMobileUser.getStar_level());
//                bwOperateBasic.setContactPhone(addInfoMobileUser.getContact_phone());
//                bwOperateBasic.setAuthentication(addInfoMobileUser.getAuthentication());
//                bwOperateBasicService.save(bwOperateBasic);
//            } else {
//                bwOperateBasic.setUpdateTime(new Date());
//                bwOperateBasic.setUserSource(addInfoMobileUser.getUser_source());
//                bwOperateBasic.setAddr(addInfoMobileUser.getAddr());
//                bwOperateBasic.setIdCard(addInfoMobileUser.getId_card());
//                bwOperateBasic.setRealName(addInfoMobileUser.getReal_name());
//                bwOperateBasic.setPackageName(addInfoMobileUser.getPackage_name());
//                bwOperateBasic.setPhone(addInfoMobileUser.getPhone());
//                bwOperateBasic.setPhoneRemain(addInfoMobileUser.getPhone_remain());
//                bwOperateBasic.setRegTime(sdf.parse(addInfoMobileUser.getReg_time()));
//                bwOperateBasic.setPhoneStatus(addInfoMobileUser.getPhone_status());
//                if (StringUtils.isNotBlank(addInfoMobileUser.getReg_time())) {
//                    bwOperateBasic.setRegTime(sdft.parse(addInfoMobileUser.getReg_time()));
//                }
//                bwOperateBasic.setStarLevel(addInfoMobileUser.getStar_level());
//                bwOperateBasic.setContactPhone(addInfoMobileUser.getContact_phone());
//                bwOperateBasic.setAuthentication(addInfoMobileUser.getAuthentication());
//                bwOperateBasicService.update(bwOperateBasic);
//            }
//            logger.info(sessionId + ":save bwOperateBasic success");
//
//            //通话记录
//            List<BwThirdOperateVoice> bwThirdOperateVoices = bwThirdOperateVoiceMapper.findListByOrderId(orderId);
//            if (CollectionUtils.isEmpty(bwThirdOperateVoices)) {
//                try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,
//                    false)) {
//                    BwThirdOperateVoiceMapper mapper = sqlSession.getMapper(BwThirdOperateVoiceMapper.class);
//                    if (!CommUtils.isNull(addInfoMobileTel)) {
//                        List<AddInfoMobileTelTeldata> teldataList = addInfoMobileTel.getTeldata();
//                        if (CollectionUtils.isNotEmpty(teldataList)) {
//                            teldataList.forEach(teldata -> {
//                                try {
//                                    BwThirdOperateVoice bwThirdOperateVoice = new BwThirdOperateVoice();
//                                    bwThirdOperateVoice.setCreateTime(new Date());
//                                    bwThirdOperateVoice.setOrderId(orderId);
//                                    bwThirdOperateVoice.setChannel(Integer.valueOf(channelId));
//                                    // 通话时间
//                                    bwThirdOperateVoice.setCallTime(teldata.getCall_time());
//                                    // 呼叫类型
//                                    bwThirdOperateVoice.setCallType(Integer.valueOf(teldata.getCall_type()));
//                                    // 对方号码
//                                    bwThirdOperateVoice.setReceivePhone(teldata.getReceive_phone());
//                                    // 通话地点
//                                    bwThirdOperateVoice.setTradeAddr(teldata.getTrade_addr());
//                                    // 通话时长
//                                    bwThirdOperateVoice.setTradeTime(teldata.getTrade_time());
//                                    // 通信类型：1.本地市话,国内呼转
//                                    bwThirdOperateVoice.setTradeType(Integer.valueOf(teldata.getTrade_type()));
//                                    mapper.insert(bwThirdOperateVoice);
//
//                                } catch (Exception e) {
//                                    logger.error("保存通话记录异常,忽略此条通话记录", e);
//                                }
//                            });
//                        }
//                    }
//                    sqlSession.commit();
//
//                } catch (Exception e) {
//                    logger.error("通话记录批处理异常：{}", e);
//                }
//            }
//            logger.info(sessionId + ":save bwThirdOperateVoice success");
//
//            Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//            //批处理
//            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,
//                false)) {
//                BwOperateVoiceMapper bwOperateVoiceMapper = sqlSession.getMapper(BwOperateVoiceMapper.class);
//                if (!CommUtils.isNull(addInfoMobileTel)) {
//                    List<AddInfoMobileTelTeldata> teldataList = addInfoMobileTel.getTeldata();
//                    if (CollectionUtils.isNotEmpty(teldataList)) {
//                        teldataList.forEach(teldata -> {
//                            try {
//                                Date jsonCallData = sdf.parse(teldata.getCall_time());
//                                // 通话记录采取最新追加的方式
//                                if (callDate == null || jsonCallData.after(callDate)) {
//                                    BwOperateVoice bwOperateVoice = new BwOperateVoice();
//                                    bwOperateVoice.setBorrower_id(borrowerId);
//                                    bwOperateVoice.setUpdateTime(new Date());
//                                    bwOperateVoice.setCall_time(teldata.getCall_time());
//                                    bwOperateVoice.setCall_type(Integer.valueOf(teldata.getCall_type()));
//                                    bwOperateVoice.setReceive_phone(teldata.getReceive_phone());
//                                    bwOperateVoice.setTrade_addr(teldata.getTrade_addr());
//                                    bwOperateVoice.setTrade_time(teldata.getTrade_time());
//
//                                    bwOperateVoiceMapper.insert(bwOperateVoice);
//                                }
//                            } catch (Exception e) {
//                                logger.error(sessionId + ":插入单条通话记录异常，忽略该条记录", e);
//                            }
//                        });
//                    }
//                }
//                sqlSession.commit();
//
//            } catch (Exception e) {
//                logger.error("通话记录批处理异常：{}", e);
//            }
//            logger.info(sessionId + ":save bwOperateVoice success");
//
//            // 月账单记录
//            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType
//                .BATCH, false)) {
//                BwOperateBillMapper mapper = sqlSession.getMapper(BwOperateBillMapper.class);
//
//                if (CollectionUtils.isNotEmpty(addInfoMobileBills)) {
//                    bwOperateBillMapper.deleteByBorrowerId(borrowerId);
//
//                    addInfoMobileBills.forEach(addInfoMobileBill -> {
//                        try {
//                            BwOperateBill bwOperateBill = new BwOperateBill();
//                            bwOperateBill.setMonth(addInfoMobileBill.getMonth());
//                            bwOperateBill.setCallPay(addInfoMobileBill.getCall_pay());
//                            bwOperateBill.setPackageFee(addInfoMobileBill.getPackage_fee());
//                            bwOperateBill.setMsgFee(addInfoMobileBill.getMsg_fee());
//                            bwOperateBill.setTelFee(addInfoMobileBill.getTel_fee());
//                            bwOperateBill.setNetFee(addInfoMobileBill.getNet_fee());
//                            bwOperateBill.setAddtionalFee(addInfoMobileBill.getAddtional_fee());
//                            bwOperateBill.setPreferentialFee(addInfoMobileBill.getPreferential_fee());
//                            bwOperateBill.setGenerationFee(addInfoMobileBill.getGeneration_fee());
//                            bwOperateBill.setOtherFee(addInfoMobileBill.getOther_fee());
//                            bwOperateBill.setScore(addInfoMobileBill.getScore());
//                            bwOperateBill.setBorrowerId(borrowerId);
//                            bwOperateBill.setCreateTime(new Date());
//
//                            mapper.insert(bwOperateBill);
//                        } catch (Exception e) {
//                            logger.error(sessionId + ":插入单条月账单记录异常，忽略该条记录", e);
//                        }
//                    });
//                }
//                sqlSession.commit();
//
//            } catch (Exception e) {
//                logger.error("月账单记录批处理异常：{}", e);
//            }
//            logger.info(sessionId + ":save bwOperateBill success");
//
//            // 短信信息
//            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType
//                .BATCH, false)) {
//                BwOperateMsgMapper mapper = sqlSession.getMapper(BwOperateMsgMapper.class);
//
//                if (!CommUtils.isNull(addInfoMobileMsg)) {
//                    List<AddInfoMobileMsgMsgdata> msgdata = addInfoMobileMsg.getMsgdata();
//                    if (CollectionUtils.isNotEmpty(msgdata)) {
//                        bwOperateMsgMapper.deleteByBorrowerId(borrowerId);
//
//                        msgdata.forEach(addInfoMobileMsgMsgdata -> {
//                            try {
//                                BwOperateMsg bwOperateMsg = new BwOperateMsg();
//                                bwOperateMsg.setBorrowerId(borrowerId);
//                                bwOperateMsg.setSendTime(addInfoMobileMsgMsgdata.getSend_time());
//                                bwOperateMsg.setTradeWay(addInfoMobileMsgMsgdata.getTrade_way());
//                                bwOperateMsg.setReceiverPhone(addInfoMobileMsgMsgdata.getReceiver_phone());
//                                bwOperateMsg.setBusinessName(addInfoMobileMsgMsgdata.getBusiness_name());
//                                bwOperateMsg.setFee(addInfoMobileMsgMsgdata.getFee());
//                                bwOperateMsg.setTradeAddr(addInfoMobileMsgMsgdata.getTrade_addr());
//                                bwOperateMsg.setTradeType(Integer.valueOf(addInfoMobileMsgMsgdata.getTrade_type()));
//                                bwOperateMsg.setCreateTime(new Date());
//
//                                mapper.insert(bwOperateMsg);
//                            } catch (Exception e) {
//                                logger.error(sessionId + ":插入单条短信信息异常，忽略该条记录", e);
//                            }
//                        });
//                    }
//                }
//
//                sqlSession.commit();
//
//            } catch (Exception e) {
//                logger.error("短信信息批处理异常：{}", e);
//            }
//            logger.info(sessionId + ":save bwOperateMsg success");
//
//            // 流量值记录
//            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType
//                .BATCH, false)) {
//                BwOperateNetMapper mapper = sqlSession.getMapper(BwOperateNetMapper.class);
//
//                if (CollectionUtils.isNotEmpty(addInfoMobileNets)) {
//                    bwOperateNetMapper.deleteByBorrowerId(borrowerId);
//
//                    addInfoMobileNets.forEach(addInfoMobileNet -> {
//                        try {
//                            BwOperateNet bwOperateNet = new BwOperateNet();
//                            bwOperateNet.setBorrowerId(borrowerId);
//                            bwOperateNet.setFee(addInfoMobileNet.getFee());
//                            bwOperateNet.setNetType(addInfoMobileNet.getNet_type());
//                            bwOperateNet.setNetWay(addInfoMobileNet.getNet_way());
//                            bwOperateNet.setPreferentialFee(addInfoMobileNet.getPreferential_fee());
//                            bwOperateNet.setStartTime(addInfoMobileNet.getStart_time());
//                            bwOperateNet.setTotalTime(addInfoMobileNet.getTotal_time());
//                            bwOperateNet.setTotalTraffic(addInfoMobileNet.getTotal_traffic());
//                            bwOperateNet.setTradeAddr(addInfoMobileNet.getTrade_addr());
//                            bwOperateNet.setCreateTime(new Date());
//
//                            mapper.insert(bwOperateNet);
//                        } catch (Exception e) {
//                            logger.error(sessionId + ":插入单条流量值记录异常，忽略该条记录", e);
//                        }
//                    });
//                }
//                sqlSession.commit();
//
//            } catch (Exception e) {
//                logger.error("流量值记录批处理异常：{}", e);
//            }
//            logger.info(sessionId + ":save bwOperateNet success");
//
//            // 充值记录
//            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType
//                .BATCH, false)) {
//                BwOperateRechargeMapper mapper = sqlSession.getMapper(BwOperateRechargeMapper.class);
//
//                if (CollectionUtils.isNotEmpty(addInfoMobileRecharges)) {
//                    bwOperateRechargeMapper.deleteByBorrowerId(borrowerId);
//
//                    addInfoMobileRecharges.forEach(addInfoMobileRecharge -> {
//                        try {
//                            BwOperateRecharge bwOperateRecharge = new BwOperateRecharge();
//                            bwOperateRecharge.setBorrowerId(borrowerId);
//                            bwOperateRecharge.setFee(addInfoMobileRecharge.getFee());
//                            bwOperateRecharge.setRechargeTime(addInfoMobileRecharge.getRecharge_time());
//                            bwOperateRecharge.setRechargeWay(addInfoMobileRecharge.getRecharge_way());
//                            bwOperateRecharge.setCreateTime(new Date());
//
//                            mapper.insert(bwOperateRecharge);
//                        } catch (Exception e) {
//                            logger.error(sessionId + ":插入单条充值记录异常，忽略该条记录", e);
//                        }
//                    });
//                }
//                sqlSession.commit();
//
//            } catch (Exception e) {
//                logger.error("充值记录批处理异常：{}", e);
//            }
//            logger.info(sessionId + ":save bwOperateRecharge success");
//
//            logger.info(sessionId + ":结束AsyncDkdh360TaskImpl.saveOperatorData method");
//        } catch (Exception e) {
//            logger.error(sessionId + ":执行AsyncDkdh360TaskImpl.saveOperatorData method异常", e);
//        }
//    }
//}
