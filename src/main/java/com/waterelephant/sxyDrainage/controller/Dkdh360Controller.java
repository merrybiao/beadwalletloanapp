//package com.waterelephant.sxyDrainage.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.entity.BwOrderChannel;
//import com.waterelephant.service.IBwOrderChannelService;
//import com.waterelephant.sxyDrainage.entity.dkdh360.*;
//import com.waterelephant.sxyDrainage.exception.Dkdh360Exception;
//import com.waterelephant.sxyDrainage.service.Dkdh360Service;
//import com.waterelephant.sxyDrainage.utils.dkdh360util.Dkdh360Utils;
//import com.waterelephant.utils.CommUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/25 15:44
// * @Description: 360贷款导航控制器
// */
//@RestController
//@RequestMapping(value = "/sxyDrainage/dkdh360", method = RequestMethod.POST)
//public class Dkdh360Controller {
//    private Logger logger = Logger.getLogger(Dkdh360Controller.class);
//    @Autowired
//    private Dkdh360Service dkdh360Service;
//    @Autowired
//    private IBwOrderChannelService bwOrderChannelService;
//
//    @ExceptionHandler(value = {Exception.class})
//    public Dkdh360Response exception(Exception e) {
//        if (e instanceof Dkdh360Exception) {
//            logger.error("360贷款导航异常:" + ((Dkdh360Exception) e).getMsg());
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, ((Dkdh360Exception) e).getMsg());
//        }
//        logger.error("统一处理360贷款导航异常", e);
//        return new Dkdh360Response(Dkdh360Response.CODE_FAILURE, e.getMessage());
//    }
//
//    /**
//     * 统一处理每次请求的校验参数与验签、解密数据
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Request
//     */
//    @ModelAttribute
//    public Dkdh360Request modelAttribute(@RequestBody Dkdh360Request dkdh360Request) throws Exception {
//        logger.info("请求参数：" + JSON.toJSONString(dkdh360Request));
//        //校验参数与验签
//        Dkdh360Response dkdh360Response = Dkdh360Utils.checkParam(dkdh360Request);
//        if (!CommUtils.isNull(dkdh360Response)) {
//            throw new Dkdh360Exception(dkdh360Response.getMsg());
//        }
//
//        // 商户号 - 查询数据库中是否存在
//        BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(dkdh360Request.getMerchant_id());
//        if (CommUtils.isNull(orderChannel)) {
//            throw new Dkdh360Exception("merchant_id不存在");
//        }
//
//        //解密数据
//        String data = Dkdh360Utils.decodeBizData(dkdh360Request);
//        dkdh360Request.setBiz_data(data);
//        return dkdh360Request;
//    }
//
//
//    /**
//     * 1.查询复贷和黑名单信息
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/filter.do")
//    public Dkdh360Response checkUser(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        CheckInfo checkInfo = JSON.parseObject(dkdh360Request.getBiz_data(), CheckInfo.class);
//        return dkdh360Service.checkUser(sessionId, checkInfo);
//    }
//
//    /**
//     * 2.推送用户借款基本信息
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/push-baseInfo.do")
//    public Dkdh360Response pushLoanBaseInfo(Dkdh360Request dkdh360Request) throws Exception {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        LoanBaseInfo loanBaseInfo = JSON.parseObject(dkdh360Request.getBiz_data(), LoanBaseInfo.class);
//        return dkdh360Service.savePushLoanBaseInfo(sessionId, loanBaseInfo);
//    }
//
//    /**
//     * 3.推送用户借款补充信息
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/push-addInfo.do")
//    public Dkdh360Response pushLoanAddInfo(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        LoanAddInfo loanAddInfo = JSON.parseObject(dkdh360Request.getBiz_data(), LoanAddInfo.class);
//        return dkdh360Service.savePushLoanAddInfo(sessionId, loanAddInfo);
//    }
//
//    /**
//     * 4.获取银行卡列表
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/get-bankCard.do")
//    public Dkdh360Response getBankCardInfo(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        CommonQuery commonQuery = JSON.parseObject(dkdh360Request.getBiz_data(), CommonQuery.class);
//        return dkdh360Service.getBankCardInfo(sessionId, commonQuery);
//    }
//
//    /**
//     * 5.推送用户绑定银行卡
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/ready-bindCard.do")
//    public Dkdh360Response bindCardReady(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        BankCardInfo bankCardInfo = JSON.parseObject(dkdh360Request.getBiz_data(), BankCardInfo.class);
//        return dkdh360Service.updateBindCardReady(sessionId, bankCardInfo);
//    }
//
//    /**
//     * 6.推送用户验证银行卡
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/sure-bindCard.do")
//    public Dkdh360Response bindCardSure(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        BankCardInfo bankCardInfo = JSON.parseObject(dkdh360Request.getBiz_data(), BankCardInfo.class);
//        return dkdh360Service.updateBindCardSure(sessionId, bankCardInfo);
//    }
//
//    /**
//     * 7.查询审批结论
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/query-approvalStatus.do")
//    public Dkdh360Response queryApprovalStatus(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        CommonQuery commonQuery = JSON.parseObject(dkdh360Request.getBiz_data(), CommonQuery.class);
//        return dkdh360Service.queryApprovalStatus(sessionId, commonQuery);
//    }
//
//
//    /**
//     * 8.试算接口
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/trial.do")
//    public Dkdh360Response trial(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        CommonQuery commonQuery = JSON.parseObject(dkdh360Request.getBiz_data(), CommonQuery.class);
//        return dkdh360Service.trial(sessionId, commonQuery);
//    }
//
//    /**
//     * 9.推送用户确认收款信息
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/push-confirm.do")
//    public Dkdh360Response pushConfirm(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        ConfirmInfo confirmInfo = JSON.parseObject(dkdh360Request.getBiz_data(), ConfirmInfo.class);
//        return dkdh360Service.pushConfirm(sessionId, confirmInfo);
//    }
//
//    /**
//     * 10.查询借款合同
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/query-contractUrl.do")
//    public Dkdh360Response queryContractUrl(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        CommonQuery commonQuery = JSON.parseObject(dkdh360Request.getBiz_data(), CommonQuery.class);
//        return dkdh360Service.queryContractUrl(sessionId, commonQuery);
//    }
//
//    /**
//     * 11.查询还款计划
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/query-repaymentPlan.do")
//    public Dkdh360Response queryRepaymentPlan(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        CommonQuery commonQuery = JSON.parseObject(dkdh360Request.getBiz_data(), CommonQuery.class);
//        return dkdh360Service.queryRepaymentPlan(sessionId, commonQuery);
//    }
//
//    /**
//     * 12.查询还款详情
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/query-repayInfo.do")
//    public Dkdh360Response queryRepayInfo(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        RepayInfo repayInfo = JSON.parseObject(dkdh360Request.getBiz_data(), RepayInfo.class);
//        return dkdh360Service.queryRepayInfo(sessionId, repayInfo);
//    }
//
//    /**
//     * 13.推送用户还款信息
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/push-repayInfo.do")
//    public Dkdh360Response pushRepayInfo(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        RepayInfo repayInfo = JSON.parseObject(dkdh360Request.getBiz_data(), RepayInfo.class);
//        return dkdh360Service.updatePushRepayInfo(sessionId, repayInfo);
//    }
//
//    /**
//     * 14.查询订单状态
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    @RequestMapping("/query-orderStatus.do")
//    public Dkdh360Response queryOrderStatus(Dkdh360Request dkdh360Request) {
//        long sessionId = System.currentTimeMillis();
//
//        //处理业务
//        CommonQuery commonQuery = JSON.parseObject(dkdh360Request.getBiz_data(), CommonQuery.class);
//        return dkdh360Service.queryOrderStatus(sessionId, commonQuery);
//    }
//}
