//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.sxyDrainage.entity.jdq.BwJdqBasic;
//import com.waterelephant.sxyDrainage.entity.jdq.BwJdqCall;
//import com.waterelephant.sxyDrainage.entity.jdq.BwJdqInformation;
//import com.waterelephant.sxyDrainage.entity.jdq.BwJdqNet;
//import com.waterelephant.sxyDrainage.entity.jdq.BwJdqSms;
//import com.waterelephant.sxyDrainage.entity.jdq.BwJdqTransaction;
//import com.waterelephant.sxyDrainage.entity.jdq.Operator;
//import com.waterelephant.sxyDrainage.mapper.BwJdqBasicMapper;
//import com.waterelephant.sxyDrainage.mapper.BwJdqCallMapper;
//import com.waterelephant.sxyDrainage.mapper.BwJdqInformationMapper;
//import com.waterelephant.sxyDrainage.mapper.BwJdqNetMapper;
//import com.waterelephant.sxyDrainage.mapper.BwJdqSmsMapper;
//import com.waterelephant.sxyDrainage.mapper.BwJdqTransactionMapper;
//import com.waterelephant.sxyDrainage.service.AsyncJdqTask;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.OkHttpUtil;
//import com.waterelephant.utils.UploadToCssUtils;
//
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
///**
// * @author xanthuim@gmail.com
// * @since 2018-07-20
// */
//@Component
//public class AsyncJdqTaskImpl implements AsyncJdqTask {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//
//    @Autowired
//    private BwJdqBasicMapper bwJdqBasicMapper;
//    @Autowired
//    private BwJdqCallMapper bwJdqCallMapper;
//    @Autowired
//    private BwJdqInformationMapper bwJdqInformationMapper;
//    @Autowired
//    private BwJdqTransactionMapper bwJdqTransactionMapper;
//    @Autowired
//    private BwJdqNetMapper bwJdqNetMapper;
//    @Autowired
//    private BwJdqSmsMapper bwJdqSmsMapper;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//
//    /**
//     * 白骑士的项目URL
//     */
//    @Value("${url.loanapp-api-web}")
//    private String urlSxfq;
//
//    /**
//     * 异步处理运营商数据
//     *
//     * @param bwOrder
//     * @param borrower
//     * @param bwOrderRong
//     * @param channelId
//     * @param operator
//     */
//    @Transactional(rollbackFor = RuntimeException.class)
//    @Async("taskExecutor")
//    @Override
//    public void handleOperator(BwOrder bwOrder, BwBorrower borrower, BwOrderRong bwOrderRong,
//                               Integer channelId, Operator operator,
//                               String frontFile, String backFile, String natureFile) {
//        logger.info("---开始处理运营商数据---");
//        addPicture(frontFile, backFile, natureFile, bwOrder.getId(), borrower.getId());
//        addBasic(bwOrder.getId(), operator);
//        addCall(bwOrder.getId(), operator);
//        addInformation(bwOrder.getId(), operator);
//        addNet(bwOrder.getId(), operator);
//        addSms(bwOrder.getId(), operator);
//        addTransaction(bwOrder.getId(), operator);
//        logger.info("---完成处理运营商数据---");
//        //白骑士风控
//        postBaiqishi(bwOrder.getId());
//    }
//
//    private void addPicture(String frontFile, String backFile, String natureFile,
//                            long orderId, long borrowerId) {
//        long sessionId = System.currentTimeMillis();
//        // 身份证正面照
//        String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01");
//        logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//        // 保存身份证正面照
//        thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null,
//                orderId, borrowerId, 0);
//
//
//        // 身份证反面照
//        String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02");
//        logger.info(sessionId + ">>> 身份证反面 " + backImage);
//        // 保存身份证反面照
//        thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null,
//                orderId, borrowerId, 0);
//
//        // 活体照
//        String handlerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03");
//        logger.info(sessionId + ">>> 活体照/人脸 " + handlerImage);
//        // 保存活体照
//        thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handlerImage, null,
//                orderId, borrowerId, 1);
//        logger.info(sessionId + ">>> 处理认证图片 ");
//    }
//
//    /**
//     * 白骑士风控
//     *
//     * @param orderId 订单ID
//     */
//    private void postBaiqishi(Long orderId) {
//        try {
//            Map<String, Object> params = new HashMap<>(16);
//            params.put("orderId", orderId);
//            OkHttpUtil.asyncPost(urlSxfq + "v3/app/order/a10/getSanFangBqs.do",
//                    params, new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            logger.error("白骑士访问异常：{}", e);
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            logger.info("白骑士访问结果：{}", response.body().string());
//                        }
//                    });
//        } catch (RuntimeException e) {
//            logger.error("白骑士访问异常：{}", e);
//        }
//    }
//
//    /**
//     * @param orderId
//     * @param operator
//     */
//    private void addTransaction(Long orderId, Operator operator) {
//        if (Objects.isNull(operator.getTransactions())) {
//            return;
//        }
//
//        bwJdqTransactionMapper.deleteByOrderId(orderId);
//
//        operator.getTransactions().forEach(transaction -> {
//            try {
//                BwJdqTransaction data = new BwJdqTransaction();
//                data.setCellPhone(transaction.getCell_phone());
//                data.setGmtCreate(new Date());
//                data.setGmtModified(new Date());
//                data.setPayAmt(transaction.getPay_amt());
//                data.setPlanAmt(transaction.getPlan_amt());
//                data.setBillCycle(transaction.getBill_cycle());
//                data.setUpdateTime(transaction.getUpdate_time());
//                data.setOrderId(orderId);
//                bwJdqTransactionMapper.insert(data);
//            } catch (Exception e) {
//                //这种情况需要捕获异常，让流程继续往下走
//                logger.error("addTransaction处理异常：{}", e);
//            }
//        });
//        logger.info("addTransaction执行完毕");
//    }
//
//    /**
//     * 短信
//     *
//     * @param orderId
//     * @param operator
//     */
//    private void addSms(Long orderId, Operator operator) {
//        if (Objects.isNull(operator.getSmses())) {
//            return;
//        }
//        Instant start = Instant.now();
//        bwJdqSmsMapper.deleteByOrderId(orderId);
//
//        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//            BwJdqSmsMapper mapper = sqlSession.getMapper(BwJdqSmsMapper.class);
//            operator.getSmses().forEach(sms -> {
//
//                BwJdqSms data = new BwJdqSms();
//                data.setCellPhone(sms.getCell_phone());
//                data.setGmtCreate(new Date());
//                data.setGmtModified(new Date());
//                data.setOtherCellPhone(sms.getOther_cell_phone());
//                data.setPlace(sms.getPlace());
//                data.setSubtotal(sms.getSubtotal());
//                data.setUpdateTime(sms.getUpdate_time());
//                data.setOrderId(orderId);
//                data.setStartTime(sms.getStart_time());
//                data.setInitType(sms.getInit_type());
//                mapper.insert(data);
//
//            });
//            sqlSession.commit();
//        } catch (Exception e) {
//            logger.error("addSms执行异常：{}", e);
//        }
//        long between = ChronoUnit.MILLIS.between(start, Instant.now());
//        logger.info("addSms执行完毕，消耗时间：{}毫秒", between);
//    }
//
//    /**
//     * 网络
//     *
//     * @param orderId
//     * @param operator
//     */
//    private void addNet(Long orderId, Operator operator) {
//        if (Objects.isNull(operator.getNets())) {
//            return;
//        }
//        Instant start = Instant.now();
//        bwJdqNetMapper.deleteByOrderId(orderId);
//
//        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//            BwJdqNetMapper mapper = sqlSession.getMapper(BwJdqNetMapper.class);
//            operator.getNets().forEach(net -> {
//                BwJdqNet data = new BwJdqNet();
//                data.setCellPhone(net.getCell_phone());
//                data.setGmtCreate(new Date());
//                data.setGmtModified(new Date());
//                data.setNetType(net.getNet_type());
//                data.setPlace(net.getPlace());
//                data.setSubflow(net.getSubflow());
//                data.setSubtotal(net.getSubtotal());
//                data.setUpdateTime(net.getUpdate_time());
//                data.setUseTime(net.getUse_time());
//                data.setOrderId(orderId);
//                mapper.insert(data);
//            });
//            sqlSession.commit();
//        } catch (Exception e) {
//            logger.error("addNet执行异常：{}", e);
//        }
//
//        long between = ChronoUnit.MILLIS.between(start, Instant.now());
//        logger.info("addNet执行完毕，消耗时间：{}毫秒", between);
//    }
//
//    /**
//     * @param orderId
//     * @param operator
//     */
//    private void addInformation(Long orderId, Operator operator) {
//        bwJdqInformationMapper.deleteByOrderId(orderId);
//
//        BwJdqInformation data = new BwJdqInformation();
//        data.setDatasource(operator.getDatasource());
//        data.setGmtCreate(new Date());
//        data.setGmtModified(new Date());
//        data.setJuid(operator.getJuid());
//        data.setToken(operator.getToken());
//        data.setVersion(operator.getVersion());
//        data.setOrderId(orderId);
//        try {
//            bwJdqInformationMapper.insert(data);
//        } catch (Exception e) {
//            //这种情况需要捕获异常，让流程继续往下走
//            logger.error("addInformation处理异常：{}", e);
//        }
//        logger.info("addInformation执行完毕");
//    }
//
//    /**
//     * @param orderId
//     * @param operator
//     */
//    private void addCall(Long orderId, Operator operator) {
//        if (Objects.isNull(operator.getCalls())) {
//            return;
//        }
//        Instant start = Instant.now();
//        bwJdqCallMapper.deleteByOrderId(orderId);
//        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
//            BwJdqCallMapper mapper = sqlSession.getMapper(BwJdqCallMapper.class);
//            operator.getCalls().forEach(call -> {
//                BwJdqCall data = new BwJdqCall();
//                data.setCallType(call.getCall_type());
//                data.setCellPhone(call.getCell_phone());
//                data.setGmtCreate(new Date());
//                data.setGmtModified(new Date());
//                data.setInitType(call.getInit_type());
//                data.setOrderId(orderId);
//                data.setOtherCellPhone(call.getOther_cell_phone());
//                data.setPlace(call.getPlace());
//                data.setStartTime(call.getStart_time());
//                data.setSubtotal(call.getSubtotal());
//                data.setUseTime(call.getUse_time());
//                mapper.insert(data);
//            });
//            sqlSession.commit();
//        } catch (Exception e) {
//            logger.error("addCall执行异常：{}", e);
//        }
//        long between = ChronoUnit.MILLIS.between(start, Instant.now());
//        logger.info("addCall执行完毕，消耗时间：{}毫秒", between);
//    }
//
//    /**
//     * @param orderId
//     * @param operator
//     */
//    private void addBasic(Long orderId, Operator operator) {
//        //删除以前的数据
//        bwJdqBasicMapper.deleteByOrderId(orderId);
//
//        BwJdqBasic data = new BwJdqBasic();
//        data.setCellPhone(operator.getBasic().getCell_phone());
//        data.setGmtCreate(new Date());
//        data.setGmtModified(new Date());
//        data.setIdcard(operator.getBasic().getIdcard());
//        data.setOrderId(orderId);
//        data.setRealName(operator.getBasic().getReal_name());
//        try {
//            data.setRegTime(operator.getBasic().getReg_time());
//            data.setUpdateTime(operator.getBasic().getUpdate_time());
//            bwJdqBasicMapper.insert(data);
//        } catch (Exception e) {
//            //这种情况需要捕获异常，让流程继续往下走
//            logger.error("addBasic处理异常：{}", e);
//        }
//        logger.info("addBasic执行完毕");
//    }
//
//
//}
