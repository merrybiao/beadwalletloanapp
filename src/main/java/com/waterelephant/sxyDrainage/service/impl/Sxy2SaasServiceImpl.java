//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.log4j.Logger;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.entity.BwAdjunct;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwBorrowerPosition;
//import com.waterelephant.entity.BwBqsDecision;
//import com.waterelephant.entity.BwBqsHitRule;
//import com.waterelephant.entity.BwBqsStrategy;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwFundAuth;
//import com.waterelephant.entity.BwFundInfo;
//import com.waterelephant.entity.BwFundRecord;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwInsureAccout;
//import com.waterelephant.entity.BwInsureInfo;
//import com.waterelephant.entity.BwInsureRecord;
//import com.waterelephant.entity.BwJdBankcards;
//import com.waterelephant.entity.BwJdOrderList;
//import com.waterelephant.entity.BwJdShippingAddrs;
//import com.waterelephant.entity.BwJdUserInfo;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwTbDeliverAddre;
//import com.waterelephant.entity.BwTbOrder;
//import com.waterelephant.entity.BwTbUser;
//import com.waterelephant.entity.BwTbZhifubaoBinding;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.entity.BwXgAreaAnalysis;
//import com.waterelephant.entity.BwXgAreaAnalysisDetail;
//import com.waterelephant.entity.BwXgCallLog;
//import com.waterelephant.entity.BwXgCallLogDetail;
//import com.waterelephant.entity.BwXgEmergencyAnalysis;
//import com.waterelephant.entity.BwXgMidScore;
//import com.waterelephant.entity.BwXgMonthlyConsumption;
//import com.waterelephant.entity.BwXgOverall;
//import com.waterelephant.entity.BwXgSpecialCate;
//import com.waterelephant.entity.BwXgSpecialCatePhoneDetail;
//import com.waterelephant.entity.BwXgSpecialMonthDetail;
//import com.waterelephant.entity.BwXgTripAnalysis;
//import com.waterelephant.entity.BwXgTripAnalysisDetail;
//import com.waterelephant.entity.BwXgUserSpecialCallInfo;
//import com.waterelephant.service.BwBorrowerPositionService;
//import com.waterelephant.service.BwBqsDecisionService;
//import com.waterelephant.service.BwBqsHitRuleService;
//import com.waterelephant.service.BwBqsStrategyService;
//import com.waterelephant.service.BwFundAuthService;
//import com.waterelephant.service.BwFundInfo2Service;
//import com.waterelephant.service.BwFundRecordService;
//import com.waterelephant.service.BwInsureInfoService;
//import com.waterelephant.service.BwInsureRecordService;
//import com.waterelephant.service.BwJdBankcardsService;
//import com.waterelephant.service.BwJdOrderListService;
//import com.waterelephant.service.BwJdShippingAddrsService;
//import com.waterelephant.service.BwJdUserInfoService;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwTbDeliverAddreService;
//import com.waterelephant.service.BwTbOrderService;
//import com.waterelephant.service.BwTbUserService;
//import com.waterelephant.service.BwTbZhifubaoBindingService;
//import com.waterelephant.service.BwXgAreaAnalysisDetailService;
//import com.waterelephant.service.BwXgAreaAnalysisService;
//import com.waterelephant.service.BwXgCallLogDetailService;
//import com.waterelephant.service.BwXgCallLogService;
//import com.waterelephant.service.BwXgEmergencyAnalysisService;
//import com.waterelephant.service.BwXgMidScoreService;
//import com.waterelephant.service.BwXgMonthlyConsumptionService;
//import com.waterelephant.service.BwXgOverallService;
//import com.waterelephant.service.BwXgSpecialCatePhoneDetailService;
//import com.waterelephant.service.BwXgSpecialCateService;
//import com.waterelephant.service.BwXgSpecialMonthDetailService;
//import com.waterelephant.service.BwXgTripAnalysisDetailService;
//import com.waterelephant.service.BwXgTripAnalysisService;
//import com.waterelephant.service.BwXgUserSpecialCallInfoService;
//import com.waterelephant.service.IBwAdjunctService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwBorrowerService;
//import com.waterelephant.service.IBwContactListService;
//import com.waterelephant.service.IBwIdentityCardService;
//import com.waterelephant.service.IBwInsureAccoutService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.sxyDrainage.entity.sxy2saas.RequestParams;
//import com.waterelephant.sxyDrainage.entity.sxy2saas.S2sRequest;
//import com.waterelephant.sxyDrainage.entity.sxy2saas.S2sResponse;
//import com.waterelephant.sxyDrainage.entity.sxy2saas.UserBasicInfo;
//import com.waterelephant.sxyDrainage.entity.sxy2saas.UserOperatorInfo;
//import com.waterelephant.sxyDrainage.service.Sxy2SaasService;
//import com.waterelephant.sxyDrainage.utils.sxy2saas.Sxy2SaasUtil;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.ElasticSearchUtils;
//import com.waterelephant.utils.StringUtil;
//import com.waterelephant.utils.SystemConstant;
//
///**
// * Module: (code:s2s)
// * <p>
// * Sxy2SaasServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//@Service
//public class Sxy2SaasServiceImpl implements Sxy2SaasService {
//    private Logger logger = Logger.getLogger(Sxy2SaasServiceImpl.class);
//    @Autowired
//    private IBwBorrowerService bwBorrowerService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private IBwBankCardService bwBankCardService;
//    @Autowired
//    private IBwAdjunctService bwAdjunctService;
//    @Autowired
//    private IBwWorkInfoService bwWorkInfoServiceImpl;
//    @Autowired
//    private IBwIdentityCardService bwIdentityCardServiceImpl;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoServiceImpl;
//    @Autowired
//    private IBwContactListService bwContactListService;
//    @Autowired
//    private BwOperateBasicService bwOperateBasicService;
//    @Autowired
//    private BwXgAreaAnalysisService bwXgAreaAnalysisService;
//    @Autowired
//    private BwXgAreaAnalysisDetailService bwXgAreaAnalysisDetailService;
//    @Autowired
//    private BwXgCallLogService bwXgCallLogService;
//    @Autowired
//    private BwXgCallLogDetailService bwXgCallLogDetailService;
//    @Autowired
//    private BwXgEmergencyAnalysisService bwXgEmergencyAnalysisService;
//    @Autowired
//    private BwXgMidScoreService bwXgMidScoreService;
//    @Autowired
//    private BwXgMonthlyConsumptionService bwXgMonthlyConsumptionService;
//    @Autowired
//    private BwXgOverallService bwXgOverallService;
//    @Autowired
//    private BwXgSpecialCatePhoneDetailService bwXgSpecialCatePhoneDetailService;
//    @Autowired
//    private BwXgSpecialCateService bwXgSpecialCateService;
//    @Autowired
//    private BwXgSpecialMonthDetailService bwXgSpecialMonthDetailService;
//    @Autowired
//    private BwXgTripAnalysisDetailService bwXgTripAnalysisDetailService;
//    @Autowired
//    private BwXgTripAnalysisService bwXgTripAnalysisService;
//    @Autowired
//    private BwXgUserSpecialCallInfoService bwXgUserSpecialCallInfoService;
//    @Autowired
//    private BwTbDeliverAddreService bwTbDeliverAddreService;
//    @Autowired
//    private BwTbOrderService bwTbOrderService;
//    @Autowired
//    private BwTbUserService bwTbUserService;
//    @Autowired
//    private BwTbZhifubaoBindingService bwTbZhifubaoBindingService;
//    @Autowired
//    private BwJdBankcardsService bwJdBankcardsService;
//    @Autowired
//    private BwJdOrderListService bwJdOrderListService;
//    @Autowired
//    private BwJdShippingAddrsService bwJdShippingAddrsService;
//    @Autowired
//    private BwJdUserInfoService bwJdUserInfoService;
//    @Autowired
//    private BwBqsDecisionService bwBqsDecisionService;
//    @Autowired
//    private BwBqsHitRuleService bwBqsHitRuleService;
//    @Autowired
//    private BwBqsStrategyService bwBqsStrategyService;
//    @Autowired
//    private BwFundAuthService bwFundAuthService;
//    @Autowired
//    private BwFundInfo2Service bwFundInfo2Service;
//    @Autowired
//    private BwFundRecordService bwFundRecordService;
//    @Autowired
//    private IBwInsureAccoutService bwInsureAccoutService;
//    @Autowired
//    private BwInsureInfoService bwInsureInfoService;
//    @Autowired
//    private BwInsureRecordService bwInsureRecordService;
//    @Autowired
//    private BwBorrowerPositionService bwBorrowerPositionService;
//
//
//    @Override
//    public S2sResponse getBasicInfo(Long sessionId, S2sRequest s2sRequest) {
//        logger.info(sessionId + ":开始Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sRequest));
//        S2sResponse s2sResponse = new S2sResponse();
//        try {
//            String sign = s2sRequest.getSign();
//            String request = s2sRequest.getRequest();
//            if (CommUtils.isNull(sign) || CommUtils.isNull(request)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("请求参数为空");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//
//            // AES解密请求参数
//            String signStr = Sxy2SaasUtil.Decrypt(sign, Sxy2SaasUtil.cKey);
//            boolean flag = request.equals(signStr);
//            if (!flag) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("签名错误");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//
//            RequestParams requestParams = JSON.parseObject(request, RequestParams.class);
//
//            String phone = requestParams.getPhone();
//            if (StringUtil.isEmpty(phone)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("手机号为空");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：手机号：phone=" + phone);
//
//            BwBorrower borrower = new BwBorrower();
//            borrower.setPhone(phone);
//            borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//            if (CommUtils.isNull(borrower)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("用户表为空");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            Long borrowerId = borrower.getId();
//            logger.info(sessionId + "：存在用户信息，borrowerId=" + borrowerId);
//
//            // 根据用户id和订单状态，以订单id倒序查询一条数据
//            Long orderId = bwOrderService.findOrderId(borrowerId, 7);
//            if (CommUtils.isNull(orderId)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("订单表为空");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：存在订单信息，orderId=" + orderId);
//
//            BwBankCard bwBankCard = new BwBankCard();
//            bwBankCard.setBorrowerId(borrowerId);
//            bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//            if (CommUtils.isNull(borrower)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("银行卡表为空");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：存在银行卡信息，bankCardId=" + bwBankCard.getId());
//
//            BwWorkInfo workInfo = new BwWorkInfo();
//            workInfo.setOrderId(orderId);
//            workInfo = bwWorkInfoServiceImpl.findBwWorkInfoByAttr(workInfo);
//            if (CommUtils.isNull(borrower)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("工作信息表为空");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：存在工作信息，workInfoId=" + workInfo.getId());
//
//            List<BwAdjunct> bwAdjuncts = bwAdjunctService.findBwAdjunctByOrderId(orderId);
//            if (CollectionUtils.isEmpty(bwAdjuncts)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("附件信息表为空");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：存在附件信息");
//            String url = "https://we-loan.oss-cn-shanghai.aliyuncs.com/";
//            for (BwAdjunct bwAdjunct : bwAdjuncts) {
//                bwAdjunct.setAdjunctPath(url + bwAdjunct.getAdjunctPath());
//            }
//
//            BwIdentityCard2 bwIdentityCard2 = new BwIdentityCard2();
//            bwIdentityCard2.setBorrowerId(borrowerId);
//            bwIdentityCard2 = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard2);
//            if (CommUtils.isNull(bwIdentityCard2)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("身份证表为空");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：存在身份证信息，identityCardId=" + bwIdentityCard2.getId());
//
//            BwPersonInfo bwPersonInfo = bwPersonInfoServiceImpl.findBwPersonInfoByOrderId(orderId);
//            if (CommUtils.isNull(bwPersonInfo)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("个人信息表为空");
//                logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：存在个人信息，personInfoId=" + bwPersonInfo.getId());
//
//            UserBasicInfo userBasicInfo = new UserBasicInfo();
//            userBasicInfo.setBwBorrower(borrower);
//            userBasicInfo.setBwBankCard(bwBankCard);
//            userBasicInfo.setBwWorkInfo(workInfo);
//            userBasicInfo.setBwAdjuncts(bwAdjuncts);
//            userBasicInfo.setBwIdentityCard2(bwIdentityCard2);
//            userBasicInfo.setBwPersonInfo(bwPersonInfo);
//
//            s2sResponse.setCode(S2sResponse.CODE_SUCCESS);
//            s2sResponse.setMsg("请求成功");
//            s2sResponse.setData(userBasicInfo);
//        } catch (Exception e) {
//            s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//            s2sResponse.setMsg("请求失败！");
//            logger.error(sessionId + "执行Sxy2SaasServiceImpl getBasicInfo method异常:" + e);
//        }
//        logger.info(sessionId + "结束Sxy2SaasServiceImpl getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//        return s2sResponse;
//    }
//
//
//    @Override
//    public S2sResponse getOperatorInfo(Long sessionId, S2sRequest s2sRequest) {
//        logger.info(sessionId + ":开始Sxy2SaasServiceImpl getOperatorInfo method:" + JSON.toJSONString(s2sRequest));
//        S2sResponse s2sResponse = new S2sResponse();
//        try {
//            String sign = s2sRequest.getSign();
//            String request = s2sRequest.getRequest();
//            if (CommUtils.isNull(sign) || CommUtils.isNull(request)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("请求参数为空");
//                logger.info(
//                    sessionId + "结束Sxy2SaasServiceImpl getOperatorInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//
//            // AES解密请求参数
//            String signStr = Sxy2SaasUtil.Decrypt(sign, Sxy2SaasUtil.cKey);
//            boolean flag = request.equals(signStr);
//            if (!flag) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("签名错误");
//                logger.info(
//                    sessionId + "结束Sxy2SaasServiceImpl getOperatorInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//
//            RequestParams requestParams = JSON.parseObject(request, RequestParams.class);
//
//            String phone = requestParams.getPhone();
//            if (StringUtil.isEmpty(phone)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("手机号为空");
//                logger.info(
//                    sessionId + "结束Sxy2SaasServiceImpl getOperatorInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：手机号：phone=" + phone);
//
//            BwBorrower borrower = new BwBorrower();
//            borrower.setPhone(phone);
//            borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//            if (CommUtils.isNull(borrower)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("用户表为空");
//                logger.info(
//                    sessionId + "结束Sxy2SaasServiceImpl getOperatorInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            Long borrowerId = borrower.getId();
//            logger.info(sessionId + "：存在用户信息，borrowerId=" + borrowerId);
//
//            // 根据用户id和订单状态，以订单id倒序查询一条数据
//            Long orderId = bwOrderService.findOrderId(borrowerId, 7);
//            if (CommUtils.isNull(orderId)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("订单表为空");
//                logger.info(
//                    sessionId + "结束Sxy2SaasServiceImpl getOperatorInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：存在订单信息，orderId=" + orderId);
//
//            List<BwContactList> bwContactLists = bwContactListService.findBwContactListByBorrowerId(borrowerId);
//            logger.info(sessionId + "：获取通讯录信息");
//
//            BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//            if (CommUtils.isNull(bwOperateBasic)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("运营商基础信息表为空");
//                logger.info(
//                    sessionId + "结束Sxy2SaasServiceImpl getOperatorInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//            logger.info(sessionId + "：存在运营商基础信息，operateBasicId=" + bwOperateBasic.getId());
//
//            // 获取ES中的通话记录
//            List<BwOperateVoice> bwOperateVoices = new ArrayList<>();
//            Client client = ElasticSearchUtils.getInstance().getClient();
//            SearchResponse searchResponse = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
//                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("borrower_id", borrowerId)))
//                .setFrom(1).setSize(5000).addSort("call_time", SortOrder.DESC).execute().actionGet();
//            SearchHits searchHits = searchResponse.getHits();
//            long totalHits = searchHits.getTotalHits();
//            System.out.println(totalHits);
//
//            for (SearchHit searchHit : searchHits) {
//                BwOperateVoice bwOper = new BwOperateVoice();
//                Map<String, Object> map1 = searchHit.getSource();
//                SimpleDateFormat tim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                bwOper.setTrade_time(map1.get("trade_time") == null ? "" : map1.get("trade_time").toString());
//                bwOper.setCall_time(tim.format(map1.get("call_time") == null ? "" : map1.get("call_time")));
//                bwOper.setTrade_addr(map1.get("trade_addr") == null ? "" : map1.get("trade_addr").toString());
//                bwOper.setCall_type(map1.get("call_type") == null ? null : Integer.valueOf(map1.get("call_type")
//                    .toString()));
//                bwOper.setReceive_phone(map1.get("receive_phone") == null ? "" : map1.get("receive_phone").toString());
//                bwOper.setTrade_type(map1.get("trade_type") == null ? null : Integer.valueOf(map1.get("trade_type")
//                    .toString()));
//
//                bwOperateVoices.add(bwOper);
//            }
//            logger.info(sessionId + "：获取通话记录");
//
//            // xg
//            BwXgAreaAnalysis areaAnalysis = new BwXgAreaAnalysis();
//            areaAnalysis.setBorrowerId(borrowerId);
//            List<BwXgAreaAnalysis> bwXgAreaAnalysiss = bwXgAreaAnalysisService.findListByAttr(areaAnalysis);
//
//            BwXgAreaAnalysisDetail analysisDetail = new BwXgAreaAnalysisDetail();
//            analysisDetail.setBorrowerId(borrowerId);
//            List<BwXgAreaAnalysisDetail> bwXgAreaAnalysisDetails = bwXgAreaAnalysisDetailService
//                .findListByAttr(analysisDetail);
//
//            BwXgCallLog callLog = new BwXgCallLog();
//            callLog.setBorrowerId(borrowerId);
//            List<BwXgCallLog> bwXgCallLogs = bwXgCallLogService.findListByAttr(callLog);
//
//            BwXgCallLogDetail callLogDetail = new BwXgCallLogDetail();
//            callLogDetail.setBorrowerId(borrowerId);
//            List<BwXgCallLogDetail> bwXgCallLogDetails = bwXgCallLogDetailService.findListByAttr(callLogDetail);
//
//            BwXgEmergencyAnalysis bwXgEmergencyAnalysis = new BwXgEmergencyAnalysis();
//            bwXgEmergencyAnalysis.setBorrowerId(borrowerId);
//            List<BwXgEmergencyAnalysis> bwXgEmergencyAnalysiss = bwXgEmergencyAnalysisService
//                .findListByAttr(bwXgEmergencyAnalysis);
//
//            BwXgMidScore bwXgMidScore = new BwXgMidScore();
//            bwXgMidScore.setBorrowerId(borrowerId);
//            List<BwXgMidScore> bwXgMidScores = bwXgMidScoreService.findListByAttr(bwXgMidScore);
//
//            BwXgMonthlyConsumption bwXgMonthlyConsumption = new BwXgMonthlyConsumption();
//            bwXgMonthlyConsumption.setBorrowerId(borrowerId);
//            List<BwXgMonthlyConsumption> bwXgMonthlyConsumptions = bwXgMonthlyConsumptionService
//                .findListByAttr(bwXgMonthlyConsumption);
//
//            BwXgOverall bwXgOverall = new BwXgOverall();
//            bwXgOverall.setBorrowerId(borrowerId);
//            List<BwXgOverall> bwXgOveralls = bwXgOverallService.findListByAttr(bwXgOverall);
//
//            BwXgSpecialCate bwXgSpecialCate = new BwXgSpecialCate();
//            bwXgSpecialCate.setBorrowerId(borrowerId);
//            List<BwXgSpecialCate> bwXgSpecialCates = bwXgSpecialCateService.findListByAttr(bwXgSpecialCate);
//
//            BwXgSpecialMonthDetail bwXgSpecialCateMonthDetail = new BwXgSpecialMonthDetail();
//            bwXgSpecialCateMonthDetail.setBorrowerId(borrowerId);
//            List<BwXgSpecialMonthDetail> bwXgSpecialCateMonthDetails = bwXgSpecialMonthDetailService
//                .findListByAttr(bwXgSpecialCateMonthDetail);
//
//            BwXgSpecialCatePhoneDetail bwXgSpecialCatePhoneDetail = new BwXgSpecialCatePhoneDetail();
//            bwXgSpecialCatePhoneDetail.setBorrowerId(borrowerId);
//            List<BwXgSpecialCatePhoneDetail> bwXgSpecialCatePhoneDetails = bwXgSpecialCatePhoneDetailService
//                .findListByAttr(bwXgSpecialCatePhoneDetail);
//
//            BwXgTripAnalysis bwXgTripAnalysis = new BwXgTripAnalysis();
//            bwXgTripAnalysis.setBorrowerId(borrowerId);
//            List<BwXgTripAnalysis> bwXgTripAnalysiss = bwXgTripAnalysisService.findListByAttr(bwXgTripAnalysis);
//
//            BwXgTripAnalysisDetail bwXgTripAnalysisDetail = new BwXgTripAnalysisDetail();
//            bwXgTripAnalysisDetail.setBorrowerId(borrowerId);
//            List<BwXgTripAnalysisDetail> bwXgTripAnalysisDetails = bwXgTripAnalysisDetailService
//                .findListByAttr(bwXgTripAnalysisDetail);
//
//            BwXgUserSpecialCallInfo bwXgUserSpecialCallInfo = new BwXgUserSpecialCallInfo();
//            bwXgUserSpecialCallInfo.setBorrowerId(borrowerId);
//            List<BwXgUserSpecialCallInfo> bwXgUserSpecialCallInfos = bwXgUserSpecialCallInfoService
//                .findListByAttr(bwXgUserSpecialCallInfo);
//            logger.info(sessionId + "：获取西瓜数据");
//
//            // credit
//            // BwCreditDhbBindingIdcards bwCreditDhbBindingIdcards = new BwCreditDhbBindingIdcards();
//            // BwCreditDhbBindingPhones bwCreditDhbBindingPhones = new BwCreditDhbBindingPhones();
//            // BwCreditDhbHistoryOrg bwCreditDhbHistoryOrg = new BwCreditDhbHistoryOrg();
//            // BwCreditDhbHistorySearch bwCreditDhbHistorySearch = new BwCreditDhbHistorySearch();
//            // BwCreditDhbInfo bwCreditDhbInfo = new BwCreditDhbInfo();
//            // BwCreditDhbRiskBlacklist bwCreditDhbRiskBlacklist = new BwCreditDhbRiskBlacklist();
//            // BwCreditDhbRiskSocialNetwork bwCreditDhbRiskSocialNetwork = new BwCreditDhbRiskSocialNetwork();
//            // BwCreditDhbUserBasic bwCreditDhbUserBasic = new BwCreditDhbUserBasic();
//            // BwCreditInformation bwCreditInformation = new BwCreditInformation();
//            // logger.info(sessionId + "：获取信用卡数据");
//
//            // tb
//            BwTbDeliverAddre deliverAddre = new BwTbDeliverAddre();
//            deliverAddre.setOrderId(orderId);
//            List<BwTbDeliverAddre> bwTbDeliverAddres = bwTbDeliverAddreService.findListByAttr(deliverAddre);
//
//            BwTbOrder tbOrder = new BwTbOrder();
//            tbOrder.setBwOrderId(orderId);
//            List<BwTbOrder> bwTbOrders = bwTbOrderService.findListByAttr(tbOrder);
//
//            BwTbUser tbUser = new BwTbUser();
//            tbUser.setOrderId(orderId);
//            List<BwTbUser> bwTbUsers = bwTbUserService.findListByAttr(tbUser);
//
//            BwTbZhifubaoBinding zhifubaoBinding = new BwTbZhifubaoBinding();
//            zhifubaoBinding.setOrderId(orderId);
//            List<BwTbZhifubaoBinding> bwTbZhifubaoBindings = bwTbZhifubaoBindingService.findListByAttr(zhifubaoBinding);
//            logger.info(sessionId + "：获取淘宝数据");
//
//            // jd
//            BwJdBankcards bankcards = new BwJdBankcards();
//            bankcards.setBorrowerId(borrowerId);
//            List<BwJdBankcards> bwJdBankcardss = bwJdBankcardsService.findListByAttr(bankcards);
//
//            BwJdOrderList orderList = new BwJdOrderList();
//            orderList.setBorrowerId(borrowerId);
//            List<BwJdOrderList> bwJdOrderLists = bwJdOrderListService.findListByAttr(orderList);
//
//            BwJdShippingAddrs shippingAddrs = new BwJdShippingAddrs();
//            shippingAddrs.setBorrowerId(borrowerId);
//            List<BwJdShippingAddrs> bwJdShippingAddrss = bwJdShippingAddrsService.findListByAttr(shippingAddrs);
//
//            BwJdUserInfo userInfo = new BwJdUserInfo();
//            userInfo.setBorrowerId(borrowerId);
//            List<BwJdUserInfo> bwJdUserInfos = bwJdUserInfoService.findListByAttr(userInfo);
//            logger.info(sessionId + "：获取京东数据");
//
//            // bqs
//            BwBqsDecision bwBqsDecision = new BwBqsDecision();
//            bwBqsDecision.setOrderId(orderId);
//            List<BwBqsDecision> bwBqsDecisions = bwBqsDecisionService.findListByAttr(bwBqsDecision);
//
//            List<BwBqsStrategy> bwBqsStrategies = new ArrayList<>();
//            List<BwBqsHitRule> bwBqsHitRules = new ArrayList<>();
//            if (CollectionUtils.isNotEmpty(bwBqsDecisions)) {
//                for (BwBqsDecision bwBqsDecision2 : bwBqsDecisions) {
//                    Long decisionId = bwBqsDecision2.getId();
//
//                    BwBqsStrategy bwBqsStrategy = new BwBqsStrategy();
//                    bwBqsStrategy.setDecisionId(decisionId);
//                    List<BwBqsStrategy> strategies = bwBqsStrategyService.findListByAttr(bwBqsStrategy);
//                    bwBqsStrategies.addAll(strategies);
//
//                    if (CollectionUtils.isNotEmpty(strategies)) {
//                        for (BwBqsStrategy bwBqsStrategy2 : strategies) {
//                            Long strategyId = bwBqsStrategy2.getId();
//
//                            BwBqsHitRule bwBqsHitRule = new BwBqsHitRule();
//                            bwBqsHitRule.setStrategyId(strategyId);
//                            List<BwBqsHitRule> hitRules = bwBqsHitRuleService.findListByAttr(bwBqsHitRule);
//                            bwBqsHitRules.addAll(hitRules);
//                        }
//                    }
//                }
//            }
//            logger.info(sessionId + "：获取白骑士数据" + bwBqsDecisions + ";;;" + bwBqsStrategies + ":::" + bwBqsHitRules);
//
//            // fund
//            BwFundAuth bwFundAuth = new BwFundAuth();
//            bwFundAuth.setBorrowerId(borrowerId);
//            List<BwFundAuth> bwFundAuths = bwFundAuthService.findListByAttr(bwFundAuth);
//
//            BwFundInfo bwFundInfo = new BwFundInfo();
//            bwFundInfo.setOrderId(orderId);
//            bwFundInfo = bwFundInfo2Service.findByAttr(bwFundInfo);
//
//            BwFundRecord bwFundRecord = new BwFundRecord();
//            bwFundRecord.setOrderId(orderId);
//            List<BwFundRecord> bwFundRecords = bwFundRecordService.findListByAttr(bwFundRecord);
//            logger.info(sessionId + "：获取公积金数据");
//
//            // insure
//            BwInsureAccout bwInsureAccout = new BwInsureAccout();
//            bwInsureAccout.setOrderId(orderId);
//            bwInsureAccout = bwInsureAccoutService.findBwInsureAccoutByAttr(bwInsureAccout);
//
//            BwInsureInfo bwInsureInfo = new BwInsureInfo();
//            bwInsureInfo.setOrderId(orderId);
//            List<BwInsureInfo> bwInsureInfos = bwInsureInfoService.queryInfo(orderId);
//
//            BwInsureRecord bwInsureRecord = new BwInsureRecord();
//            bwInsureRecord.setOrderId(orderId);
//            List<BwInsureRecord> bwInsureRecords = bwInsureRecordService.findListByAttr(bwInsureRecord);
//
//            logger.info(sessionId + "：获取社保数据");
//
//            // position
//            BwBorrowerPosition borrowerPosition = new BwBorrowerPosition();
//            borrowerPosition.setBorrowerId(borrowerId);
//            List<BwBorrowerPosition> bwBorrowerPositions = bwBorrowerPositionService.findListByAttr(borrowerPosition);
//            logger.info(sessionId + "：获取定位数据");
//
//            UserOperatorInfo userOperatorInfo = new UserOperatorInfo();
//            userOperatorInfo.setBwContactLists(bwContactLists);
//            userOperatorInfo.setBwOperateBasic(bwOperateBasic);
//            userOperatorInfo.setBwOperateVoices(bwOperateVoices);
//
//            userOperatorInfo.setBwXgAreaAnalysiss(bwXgAreaAnalysiss);
//            userOperatorInfo.setBwXgAreaAnalysisDetails(bwXgAreaAnalysisDetails);
//            userOperatorInfo.setBwXgCallLogs(bwXgCallLogs);
//            userOperatorInfo.setBwXgCallLogDetails(bwXgCallLogDetails);
//            userOperatorInfo.setBwXgEmergencyAnalysiss(bwXgEmergencyAnalysiss);
//            userOperatorInfo.setBwXgMidScores(bwXgMidScores);
//            userOperatorInfo.setBwXgMonthlyConsumptions(bwXgMonthlyConsumptions);
//            userOperatorInfo.setBwXgOveralls(bwXgOveralls);
//            userOperatorInfo.setBwXgSpecialCatePhoneDetails(bwXgSpecialCatePhoneDetails);
//            userOperatorInfo.setBwXgSpecialCates(bwXgSpecialCates);
//            userOperatorInfo.setBwXgSpecialCateMonthDetails(bwXgSpecialCateMonthDetails);
//            userOperatorInfo.setBwXgTripAnalysisDetails(bwXgTripAnalysisDetails);
//            userOperatorInfo.setBwXgTripAnalysiss(bwXgTripAnalysiss);
//            userOperatorInfo.setBwXgUserSpecialCallInfos(bwXgUserSpecialCallInfos);
//
//            userOperatorInfo.setBwTbDeliverAddres(bwTbDeliverAddres);
//            userOperatorInfo.setBwTbOrders(bwTbOrders);
//            userOperatorInfo.setBwTbUsers(bwTbUsers);
//            userOperatorInfo.setBwTbZhifubaoBindings(bwTbZhifubaoBindings);
//
//            userOperatorInfo.setBwJdBankcardss(bwJdBankcardss);
//            userOperatorInfo.setBwJdOrderLists(bwJdOrderLists);
//            userOperatorInfo.setBwJdShippingAddrss(bwJdShippingAddrss);
//            userOperatorInfo.setBwJdUserInfos(bwJdUserInfos);
//
//            userOperatorInfo.setBwBqsDecisions(bwBqsDecisions);
//            userOperatorInfo.setBwBqsHitRules(bwBqsHitRules);
//            userOperatorInfo.setBwBqsStrategies(bwBqsStrategies);
//
//            userOperatorInfo.setBwFundAuths(bwFundAuths);
//            userOperatorInfo.setBwFundInfo(bwFundInfo);
//            userOperatorInfo.setBwFundRecords(bwFundRecords);
//
//            userOperatorInfo.setBwInsureAccout(bwInsureAccout);
//            userOperatorInfo.setBwInsureInfos(bwInsureInfos);
//            userOperatorInfo.setBwInsureRecords(bwInsureRecords);
//
//            userOperatorInfo.setBwBorrowerPositions(bwBorrowerPositions);
//
//            s2sResponse.setCode(S2sResponse.CODE_SUCCESS);
//            s2sResponse.setMsg("请求成功");
//            s2sResponse.setData(userOperatorInfo);
//        } catch (Exception e) {
//            s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//            s2sResponse.setMsg("请求失败！");
//            logger.error(sessionId + "执行Sxy2SaasServiceImpl getOperatorInfo method异常:" + e);
//        }
//        logger.info(sessionId + "结束Sxy2SaasServiceImpl getOperatorInfo method");
//        return s2sResponse;
//    }
//
//}
