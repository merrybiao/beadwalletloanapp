//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.waterelephant.entity.BwJbApplist;
//import com.waterelephant.entity.BwJbBills;
//import com.waterelephant.entity.BwJbNets;
//import com.waterelephant.entity.BwJbRecharges;
//import com.waterelephant.entity.BwJbSmses;
//import com.waterelephant.entity.BwThirdOperateBasic;
//import com.waterelephant.entity.BwThirdOperateVoice;
//import com.waterelephant.mapper.*;
//import com.waterelephant.service.BwJbApplistService;
//import com.waterelephant.service.BwJbBillsService;
//import com.waterelephant.service.BwJbNetsService;
//import com.waterelephant.service.BwJbRechargesService;
//import com.waterelephant.service.BwJbSmsesService;
//import com.waterelephant.service.BwThirdOperateBasicService;
//import com.waterelephant.service.BwThirdOperateVoiceService;
//import com.waterelephant.sxyDrainage.entity.jieba.AddInfo;
//import com.waterelephant.sxyDrainage.entity.jieba.Applist;
//import com.waterelephant.sxyDrainage.entity.jieba.Bills;
//import com.waterelephant.sxyDrainage.entity.jieba.Calls;
//import com.waterelephant.sxyDrainage.entity.jieba.CallsItems;
//import com.waterelephant.sxyDrainage.entity.jieba.Mobile;
//import com.waterelephant.sxyDrainage.entity.jieba.Nets;
//import com.waterelephant.sxyDrainage.entity.jieba.NetsItems;
//import com.waterelephant.sxyDrainage.entity.jieba.Recharges;
//import com.waterelephant.sxyDrainage.entity.jieba.Smses;
//import com.waterelephant.sxyDrainage.entity.jieba.SmsesItems;
//import com.waterelephant.sxyDrainage.service.AsyncJbTask;
//import com.waterelephant.sxyDrainage.utils.jieba.JieBaConstant;
//import com.waterelephant.utils.CommUtils;
//
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
///**
// * (code:jb001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 10:37 2018/6/15
// * @Modified By:
// */
//@Component
//public class AsyncJbTaskImpl implements AsyncJbTask {
//    private Logger logger = Logger.getLogger(AsyncJbTaskImpl.class);
//    private String channelStr = JieBaConstant.channelId;
//    private BwJbApplistService bwJbApplistService;
//    private BwJbBillsService bwJbBillsService;
//    private BwThirdOperateBasicService bwThirdOperateBasicService;
//    private BwThirdOperateVoiceService bwThirdOperateVoiceService;
//    private SqlSessionTemplate sqlSessionTemplate;
//    private BwJbNetsService bwJbNetsService;
//    private BwJbRechargesService bwJbRechargesService;
//    private BwJbSmsesService bwJbSmsesService;
//
//    @Autowired
//    public AsyncJbTaskImpl(BwJbApplistService bwJbApplistService, BwJbBillsService bwJbBillsService,
//                           BwThirdOperateBasicService bwThirdOperateBasicService, BwThirdOperateVoiceService
//                               bwThirdOperateVoiceService,
//                           SqlSessionTemplate sqlSessionTemplate, BwJbNetsService bwJbNetsService,
//                           BwJbRechargesService bwJbRechargesService, BwJbSmsesService bwJbSmsesService) {
//        this.bwJbApplistService = bwJbApplistService;
//        this.bwJbBillsService = bwJbBillsService;
//        this.bwThirdOperateBasicService = bwThirdOperateBasicService;
//        this.bwThirdOperateVoiceService = bwThirdOperateVoiceService;
//        this.sqlSessionTemplate = sqlSessionTemplate;
//        this.bwJbNetsService = bwJbNetsService;
//        this.bwJbRechargesService = bwJbRechargesService;
//        this.bwJbSmsesService = bwJbSmsesService;
//    }
//
//    @Async("asyncTaskExecutor")
//    @Override
//    public void saveOperator(Long sessionId, AddInfo addInfo, Long orderId) {
//        logger.info(sessionId + ":开始AsyncJbTaskImpl saveOperator method: orderId=" + orderId);
//        try {
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
//            List<Applist> applists = addInfo.getApplist();
//            Mobile mobile = addInfo.getMobile();
//
//            List<Bills> bills = mobile.getBills();
//            List<Calls> calls = mobile.getCalls();
//            List<Nets> nets = mobile.getNets();
//            List<Recharges> recharges = mobile.getRecharges();
//            List<Smses> smses = mobile.getSmses();
//
//            //APP列表
//            if (CollectionUtils.isNotEmpty(applists)) {
//                bwJbApplistService.deleteBwJbAppListByOrderId(orderId);
//                try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType
//                    .BATCH, false)) {
//                    BwJbApplistMapper mapper = sqlSession.getMapper(BwJbApplistMapper.class);
//
//                    applists.forEach(applist -> {
//                        try {
//                            BwJbApplist bwJbApplist = new BwJbApplist();
//                            bwJbApplist.setOrderId(orderId);
//                            bwJbApplist.setAppName(applist.getApp_name());
//                            bwJbApplist.setCreateTime(new Date());
//                            mapper.insert(bwJbApplist);
//                        } catch (Exception e) {
//                            logger.error(sessionId + ":保存applist异常，忽略该条记录", e);
//                        }
//                    });
//                    sqlSession.commit();
//                } catch (Exception e) {
//                    logger.error("APP列表批处理异常：{}", e);
//                }
//            }
//            logger.info(sessionId + ":save applist success");
//
//            //运营商基本信息
//            BwThirdOperateBasic bwThirdOperateBasic = new BwThirdOperateBasic();
//            bwThirdOperateBasic.setOrderId(orderId);
//            bwThirdOperateBasic = bwThirdOperateBasicService.findByAttr(bwThirdOperateBasic);
//            if (CommUtils.isNull(bwThirdOperateBasic)) {
//                bwThirdOperateBasic = new BwThirdOperateBasic();
//                bwThirdOperateBasic.setOrderId(orderId);
//                bwThirdOperateBasic.setAddr((CommUtils.isNull(mobile.getProvince()) ? null : mobile.getProvince()) +
//                    (CommUtils.isNull(mobile.getCity()) ? null : mobile.getCity()));
//                bwThirdOperateBasic.setChannel(Integer.valueOf(channelStr));
//                bwThirdOperateBasic.setIdCard(CommUtils.isNull(mobile.getIdcard()) ? null : mobile.getIdcard());
//                bwThirdOperateBasic.setCreateTime(new Date());
//                String packageName = mobile.getPackage_name();
//                if (packageName != null && packageName.length() > 55) {
//                    packageName = packageName.substring(0, 50);
//                }
//                bwThirdOperateBasic.setPackageName(packageName);
//                bwThirdOperateBasic.setPhone(mobile.getMobile());
//                bwThirdOperateBasic.setPhoneRemain(CommUtils.isNull(mobile.getAvailable_balance()) ? null :
//                    String.valueOf(mobile.getAvailable_balance() / 100));
//                bwThirdOperateBasic.setPhoneStatus(mobile.getMessage());
//                bwThirdOperateBasic.setRealName(CommUtils.isNull(mobile.getName()) ? null : mobile.getName());
//                if (StringUtils.isNotBlank(mobile.getOpen_time())) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    bwThirdOperateBasic.setRegTime(sdf.parse(mobile.getOpen_time()));
//                }
//                bwThirdOperateBasic.setStarLevel(CommUtils.isNull(mobile.getLevel()) ? null : mobile.getLevel());
//                bwThirdOperateBasic.setUserSource(mobile.getCarrier());
//                bwThirdOperateBasicService.save(bwThirdOperateBasic);
//            } else {
//                bwThirdOperateBasic.setAddr((CommUtils.isNull(mobile.getProvince()) ? null : mobile.getProvince()) +
//                    (CommUtils.isNull(mobile.getCity()) ? null : mobile.getCity()));
//                bwThirdOperateBasic.setChannel(Integer.valueOf(channelStr));
//                bwThirdOperateBasic.setIdCard(CommUtils.isNull(mobile.getIdcard()) ? null : mobile.getIdcard());
//                bwThirdOperateBasic.setUpdateTime(new Date());
//                String packageName = mobile.getPackage_name();
//                if (packageName != null && packageName.length() > 55) {
//                    packageName = packageName.substring(0, 50);
//                }
//                bwThirdOperateBasic.setPackageName(packageName);
//                bwThirdOperateBasic.setPhone(mobile.getMobile());
//                bwThirdOperateBasic.setPhoneRemain(CommUtils.isNull(mobile.getAvailable_balance()) ? null :
//                    String.valueOf(mobile.getAvailable_balance() / 100));
//                bwThirdOperateBasic.setPhoneStatus(mobile.getMessage());
//                bwThirdOperateBasic.setRealName(CommUtils.isNull(mobile.getName()) ? null : mobile.getName());
//                if (StringUtils.isNotBlank(mobile.getOpen_time())) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    bwThirdOperateBasic.setRegTime(sdf.parse(mobile.getOpen_time()));
//                }
//                bwThirdOperateBasic.setStarLevel(CommUtils.isNull(mobile.getLevel()) ? null : mobile.getLevel());
//                bwThirdOperateBasic.setUserSource(mobile.getCarrier());
//                bwThirdOperateBasicService.update(bwThirdOperateBasic);
//            }
//            logger.info(sessionId + ":save operatebasic success");
//
//            //月账单记录
//            if (CollectionUtils.isNotEmpty(bills)) {
//                bwJbBillsService.deleteBwJbBillsByOrderId(orderId);
//                try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType
//                    .BATCH, false)) {
//                    BwJbBillsMapper mapper = sqlSession.getMapper(BwJbBillsMapper.class);
//
//                    bills.forEach(bill -> {
//                        try {
//                            BwJbBills bwJbBills = new BwJbBills();
//                            bwJbBills.setOrderId(orderId);
//                            bwJbBills.setActualFee(bill.getActualFee());
//                            bwJbBills.setBaseFee(bill.getBase_fee());
//                            bwJbBills.setBillEndDate(bill.getBill_end_date());
//                            bwJbBills.setBillMonth(bill.getBill_month());
//                            bwJbBills.setBillStartDate(bill.getBill_start_date());
//                            bwJbBills.setCreateTime(new Date());
//                            bwJbBills.setDiscount(CommUtils.isNull(bill.getDiscount()) ? null : bill.getDiscount());
//                            bwJbBills.setExtraDiscount(CommUtils.isNull(bill.getExtra_discount()) ? null : bill
//                                .getExtra_discount());
//                            bwJbBills.setExtraFee(CommUtils.isNull(bill.getExtra_fee()) ? null : bill.getExtra_fee());
//                            bwJbBills.setExtraServiceFee(bill.getExtra_service_fee());
//                            bwJbBills.setLastPoint(CommUtils.isNull(bill.getLast_point()) ? null : bill.getLast_point
//                                ());
//                            bwJbBills.setNotes(CommUtils.isNull(bill.getNotes()) ? null : bill.getNotes());
//                            bwJbBills.setPaidFee(CommUtils.isNull(bill.getPaid_fee()) ? null : bill.getPaid_fee());
//                            bwJbBills.setPoint(CommUtils.isNull(bill.getPoint()) ? null : bill.getPoint());
//                            bwJbBills.setRelatedMobiles(CommUtils.isNull(bill.getRelated_mobiles()) ? null : bill
//                                .getRelated_mobiles());
//                            bwJbBills.setSmsFee(bill.getSms_fee());
//                            bwJbBills.setTotalFee(bill.getTotal_fee());
//                            bwJbBills.setUnpaidFee(CommUtils.isNull(bill.getUnpaid_fee()) ? null : bill.getUnpaid_fee
//                                ());
//                            bwJbBills.setVoiceFee(bill.getVoice_fee());
//                            bwJbBills.setWebFee(bill.getWeb_fee());
//
//                            mapper.insert(bwJbBills);
//                        } catch (Exception e) {
//                            logger.error(sessionId + ":保存bills异常，忽略该条记录", e);
//                        }
//                    });
//                    sqlSession.commit();
//                } catch (Exception e) {
//                    logger.error("月账单记录批处理异常：{}", e);
//                }
//            }
//            logger.info(sessionId + ":save bills success");
//
//            //通话记录
//            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,
//                false)) {
//                BwThirdOperateVoiceMapper mapper = sqlSession.getMapper(BwThirdOperateVoiceMapper.class);
//                if (CollectionUtils.isNotEmpty(calls)) {
//                    bwThirdOperateVoiceService.deleteAllByOrderId(orderId);
//                    calls.forEach(call -> {
//                        List<CallsItems> callsItemsList = call.getItems();
//                        if (CollectionUtils.isNotEmpty(callsItemsList)) {
//                            callsItemsList.forEach(callsItems -> {
//                                try {
//                                    BwThirdOperateVoice bwThirdOperateVoice = new BwThirdOperateVoice();
//                                    bwThirdOperateVoice.setCreateTime(new Date());
//                                    bwThirdOperateVoice.setOrderId(orderId);
//                                    bwThirdOperateVoice.setChannel(Integer.valueOf(channelStr));
//                                    // 通话时间
//                                    bwThirdOperateVoice.setCallTime(callsItems.getTime());
//                                    // 呼叫类型
//                                    bwThirdOperateVoice.setCallType("DIAL".equals(callsItems.getDial_type()) ? 1 : 2);
//                                    String peerNumber = callsItems.getPeer_number();
//                                    if (peerNumber != null && peerNumber.length() > 20) {
//                                        peerNumber = peerNumber.substring(0, 19);
//                                    }
//                                    // 对方号码
//                                    bwThirdOperateVoice.setReceivePhone(peerNumber);
//                                    // 通话地点
//                                    bwThirdOperateVoice.setTradeAddr(callsItems.getLocation());
//                                    // 通话时长
//                                    bwThirdOperateVoice.setTradeTime(callsItems.getDuration());
//                                    // 通信类型：1.本地市话,国内呼转
//                                    bwThirdOperateVoice.setTradeType("本地市话".equals(callsItems.getLocation_type()) ? 1
//                                        : 2);
//                                    mapper.insert(bwThirdOperateVoice);
//
//                                } catch (Exception e) {
//                                    logger.error("保存通话记录异常,忽略此条通话记录...", e);
//                                }
//                            });
//                        }
//                    });
//                }
//                sqlSession.commit();
//            } catch (Exception e) {
//                logger.error("通话记录批处理异常：{}", e);
//            }
//            logger.info(sessionId + ":save operatevioce success");
//
//            //流量值记录
//            if (CollectionUtils.isNotEmpty(nets)) {
//                bwJbNetsService.deleteBwJbNetsByOrderId(orderId);
//                try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType
//                    .BATCH, false)) {
//                    BwJbNetsMapper mapper = sqlSession.getMapper(BwJbNetsMapper.class);
//
//                    nets.forEach(net -> {
//                        List<NetsItems> netsItems = net.getItems();
//                        if (CollectionUtils.isNotEmpty(netsItems)) {
//                            netsItems.forEach(netsItem -> {
//                                try {
//                                    BwJbNets bwJbNets = new BwJbNets();
//                                    bwJbNets.setDetailsId(netsItem.getDetails_id());
//                                    bwJbNets.setCreateTime(new Date());
//                                    bwJbNets.setOrderId(orderId);
//                                    bwJbNets.setDuration(CommUtils.isNull(netsItem.getDuration()) ? null : netsItem
//                                        .getDuration());
//                                    bwJbNets.setFee(netsItem.getFee());
//                                    bwJbNets.setLocation(CommUtils.isNull(netsItem.getLocation()) ? null : netsItem
//                                        .getLocation());
//                                    String netType = netsItem.getNet_type();
//                                    if (netType != null && netType.length() > 50) {
//                                        netType = netType.substring(0, 45);
//                                    }
//                                    bwJbNets.setNetType(netType);
//                                    String serviceName = netsItem.getService_name();
//                                    if (serviceName != null && serviceName.length() > 50) {
//                                        serviceName = serviceName.substring(0, 45);
//                                    }
//                                    bwJbNets.setServiceName(serviceName);
//                                    bwJbNets.setSubflow(netsItem.getSubflow());
//                                    bwJbNets.setTime(netsItem.getTime());
//                                    mapper.insert(bwJbNets);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":保存nets异常，忽略该条记录", e);
//                                }
//                            });
//                        }
//                    });
//                    sqlSession.commit();
//                } catch (Exception e) {
//                    logger.error("流量值记录批处理异常：{}", e);
//                }
//            }
//            logger.info(sessionId + ":save nets success");
//
//            //充值记录
//            if (CollectionUtils.isNotEmpty(recharges)) {
//                bwJbRechargesService.deleteBwJbRechargesByOrderId(orderId);
//                try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType
//                    .BATCH, false)) {
//                    BwJbRechargesMapper mapper = sqlSession.getMapper(BwJbRechargesMapper.class);
//
//                    recharges.forEach(recharge -> {
//                        try {
//                            BwJbRecharges bwJbRecharges = new BwJbRecharges();
//                            bwJbRecharges.setOrderId(orderId);
//                            bwJbRecharges.setAmount(recharge.getAmount());
//                            bwJbRecharges.setCreateTime(new Date());
//                            bwJbRecharges.setDetailsId(recharge.getDetails_id());
//                            bwJbRecharges.setRechargeTime(recharge.getRecharge_time());
//                            bwJbRecharges.setType(recharge.getType());
//                            mapper.insert(bwJbRecharges);
//                        } catch (Exception e) {
//                            logger.error(sessionId + ":保存recharges异常，忽略该条记录", e);
//                        }
//                    });
//                    sqlSession.commit();
//                } catch (Exception e) {
//                    logger.error("充值记录批处理异常：{}", e);
//                }
//            }
//            logger.info(sessionId + ":save recharges success");
//
//            //短信信息
//            if (CollectionUtils.isNotEmpty(smses)) {
//                bwJbSmsesService.deleteBwJbSmsesByOrderId(orderId);
//                try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType
//                    .BATCH, false)) {
//                    BwJbSmsesMapper mapper = sqlSession.getMapper(BwJbSmsesMapper.class);
//
//                    smses.forEach(smse -> {
//                        List<SmsesItems> smsesItemsList = smse.getItems();
//                        if (CollectionUtils.isNotEmpty(smsesItemsList)) {
//                            smsesItemsList.forEach(smsesItems -> {
//                                try {
//                                    BwJbSmses bwJbSmses = new BwJbSmses();
//                                    bwJbSmses.setOrderId(orderId);
//                                    bwJbSmses.setCreateTime(new Date());
//                                    bwJbSmses.setDetailsId(smsesItems.getDetails_id());
//                                    bwJbSmses.setLocation(CommUtils.isNull(smsesItems.getLocation()) ? null : smsesItems
//                                        .getLocation());
//                                    bwJbSmses.setFee(smsesItems.getFee());
//                                    bwJbSmses.setMsgType(smsesItems.getMsg_type());
//                                    bwJbSmses.setPeerNumber(smsesItems.getPeer_number());
//                                    bwJbSmses.setSendTpye(smsesItems.getSend_tpye());
//                                    bwJbSmses.setSerivceName(CommUtils.isNull(smsesItems.getService_name()) ? null :
//                                        smsesItems.getService_name());
//                                    bwJbSmses.setTime(smsesItems.getTime());
//                                    mapper.insert(bwJbSmses);
//                                } catch (Exception e) {
//                                    logger.error(sessionId + ":保存smses异常，忽略该条记录", e);
//                                }
//                            });
//                        }
//                    });
//                    sqlSession.commit();
//                } catch (Exception e) {
//                    logger.error("短信信息批处理异常：{}", e);
//                }
//            }
//            logger.info(sessionId + ":save smses success");
//
//            logger.info(sessionId + ":结束AsyncJbTaskImpl saveOperator method: orderId=" + orderId);
//        } catch (Exception e) {
//            logger.error(sessionId + ":执行AsyncJbTaskImpl saveOperator method异常：", e);
//        }
//    }
//}
