package com.waterelephant.third.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.lianlian.Agreement;
import com.beadwallet.entity.lianlian.CardQueryResult;
import com.beadwallet.entity.lianlian.PlanResult;
import com.beadwallet.entity.lianlian.RepayRequest;
import com.beadwallet.entity.lianlian.RepaymentPlan;
import com.beadwallet.entity.lianlian.RepaymentResult;
import com.beadwallet.entity.lianlian.SignLess;
import com.beadwallet.entity.lianlian.SignalLess;
import com.beadwallet.servcie.LianLianPayService;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwBorrowerDetail;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwBorrowerDetailService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.third.entity.MessageDto;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.third.utils.ThirdUtil;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * 统一对外接口 - 公共service（code0091）
 *
 *
 * Module:
 *
 * ThirdCommonServiceImpl.java
 *
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class ThirdCommonServiceImpl implements ThirdCommonService {
    private Logger logger = LoggerFactory.getLogger(ThirdCommonServiceImpl.class);

    @Autowired
    private BwBorrowerService bwBorrowerService;
    @Autowired
    private BwOrderAuthService bwOrderAuthService;
    @Autowired
    private IBwAdjunctService bwAdjunctService;
    @Autowired
    private IBwRepaymentPlanService bwRepaymentPlanService;
    @Autowired
    private BwOverdueRecordService bwOverdueRecordService;
    @Autowired
    private IBwOrderService bwOrderService;
    @Autowired
    private BwOrderRongService bwOrderRongService;
    @Autowired
    private BwProductDictionaryService bwProductDictionaryService;
    @Autowired
    private BwOrderProcessRecordService bwOrderProcessRecordService;
    @Autowired
    private BwBorrowerDetailService bwBorrowerDetailService;

    private static Map<String, String> NOTIFY_MAP = new HashMap<String, String>();
    static {
        NOTIFY_MAP.put("thirdRepay", "/third/inteface/repayNotify.do");
        NOTIFY_MAP.put("thirdDefer", "/third/inteface/deferNotify.do");
    }

    /**
     *
     * @see com.waterelephant.third.service.ThirdCommonService#checkUserAccountProgressOrder(long,
     *      java.lang.String)
     */
    @Override
    public boolean checkUserAccountProgressOrder(long sessionId, String idCard) throws Exception {
        boolean flag = false;
        // 根据身份证号查询
        BwBorrower borrower = new BwBorrower();
        borrower.setIdCard(idCard);
        List<BwBorrower> bwBorrowers = bwBorrowerService.findBwBorrowerListByIdCard(borrower);
        int num = bwBorrowers.size();
        if (num < 1) {
            logger.info(sessionId + ":该用户无账号，id_card=" + idCard);
            flag = false;
        } else {
            logger.info(sessionId + ":该用户id_card=" + idCard + ",存在" + num + "个账号");
            for (BwBorrower bwBorrower : bwBorrowers) {
                // 查询是否有进行中的订单
                long count = bwOrderService.findProOrder(String.valueOf(bwBorrower.getId()));
                logger.info(sessionId + ":用户id[" + bwBorrower.getId() + "] 进行中的订单校验：" + count);
                if (count > 0) {
                    logger.info(sessionId + ":该用户id[" + bwBorrower.getId() + "],id_card=" + idCard + ",phone=" + bwBorrower.getPhone() + "存在进行中的订单");
                    flag = true;
                }
            }
        }
        return flag;
    }


    /**
     *
     * @see com.waterelephant.third.service.ThirdCommonService#checkUserAccountProgressOrder(long,
     *      java.lang.String)
     */
    @Override
    public boolean checkUserAccountProgressOrder(long sessionId, String idCard, String userName) throws Exception {
        boolean flag = false;
        // 根据身份证号查询
        BwBorrower borrower = new BwBorrower();
        borrower.setIdCard(idCard);
        List<BwBorrower> bwBorrowers = bwBorrowerService.checkOldUserList(idCard, userName);
        int num = bwBorrowers.size();
        if (num < 1) {
            logger.info(sessionId + ":该用户无账号，id_card=" + idCard + ",userName=" + userName);
            flag = false;
        } else {
            logger.info(sessionId + ":该用户id_card=" + idCard + ",userName=" + userName + ",存在" + num + "个账号");
            for (BwBorrower bwBorrower : bwBorrowers) {
                // 查询是否有进行中的订单
                long count = bwOrderService.findProOrder(String.valueOf(bwBorrower.getId()));
                logger.info(sessionId + ":用户id[" + bwBorrower.getId() + "] 进行中的订单校验：" + count);
                if (count > 0) {
                    logger.info(sessionId + ":该用户id[" + bwBorrower.getId() + "],id_card=" + idCard + ",phone=" + bwBorrower.getPhone() + "存在进行中的订单");
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 公共方法 - 新增或修改borrower
     *
     * @see com.waterelephant.third.service.ThirdCommonService#addOrUpdateBorrower(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public BwBorrower addOrUpdateBorrower(long sessionId, String name, String idCard, String phone, int channelId) throws Exception {
        // 根据手机号查询
        BwBorrower borrower = new BwBorrower();
        borrower.setPhone(phone);
        borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
        if (!CommUtils.isNull(borrower)) {
            borrower.setPhone(phone);
            borrower.setAuthStep(1);
            borrower.setFlag(1);
            borrower.setState(1);
            // borrower.setChannel(channelId); // 表示该借款人来源
            borrower.setIdCard(idCard);
            borrower.setName(name);
            borrower.setAge(ThirdUtil.getAgeByIdCard(idCard));
            borrower.setSex(ThirdUtil.getSexByIdCard(idCard));
            borrower.setUpdateTime(Calendar.getInstance().getTime());
            bwBorrowerService.updateBwBorrower(borrower);
        } else {
            // 创建借款人
            String password = ThirdUtil.getRandomPwd();
            borrower = new BwBorrower();
            borrower.setPhone(phone);
            borrower.setPassword(CommUtils.getMD5(password.getBytes()));
            borrower.setAuthStep(1);
            borrower.setFlag(1);
            borrower.setState(1);
            borrower.setChannel(channelId); // 表示该借款人来源
            borrower.setIdCard(idCard);
            borrower.setName(name);
            borrower.setAge(ThirdUtil.getAgeByIdCard(idCard));
            borrower.setSex(ThirdUtil.getSexByIdCard(idCard));
            borrower.setCreateTime(Calendar.getInstance().getTime());
            borrower.setUpdateTime(Calendar.getInstance().getTime());
            bwBorrowerService.addBwBorrower(borrower);

            // 发送短信
            try {
                if (RedisUtils.exists("tripartite:smsFilter:registerPassword:" + channelId) == false) {
                    String message = ThirdUtil.getMsg(password);
                    MessageDto messageDto = new MessageDto();
                    messageDto.setBusinessScenario("2");
                    messageDto.setPhone(phone);
                    messageDto.setMsg(message);
                    messageDto.setType("1");
                    RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
                }
            } catch (Exception e) {
                logger.error(sessionId + "发送短信异常:", e);
            }
        }
        return borrower;
    }

    /**
     * 公共方法- 保存或修改BwOrderAuth
     *
     * @see com.waterelephant.third.service.ThirdCommonService#addOrUpdateBwOrderAuth(long, long, int,
     *      int)
     */
    @Override
    public boolean addOrUpdateBwOrderAuth(long sessionId, long orderId, int type, int channelId) {
        try {
            BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(orderId, type);
            if (bwOrderAuth == null) {
                bwOrderAuth = new BwOrderAuth();
                bwOrderAuth.setOrderId(orderId);
                bwOrderAuth.setAuth_type(type);
                bwOrderAuth.setAuth_channel(channelId);
                bwOrderAuth.setCreateTime(Calendar.getInstance().getTime());
                bwOrderAuth.setUpdateTime(Calendar.getInstance().getTime());
                bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
            }
        } catch (Exception e) {
            logger.error(sessionId + "保存认证信息异常(orderId=" + orderId + "&&type=" + type + "&&channelId=" + channelId + "):", e);
            return false;
        }
        return true;
    }

    /**
     * 公共方法 - 新增或修改BwAdjunct
     *
     * @see com.waterelephant.third.service.ThirdCommonService#addOrUpdateBwAdjunct(long, int,
     *      java.lang.String, java.lang.String, long, long, java.lang.Integer)
     */
    @Override
    public boolean addOrUpdateBwAdjunct(long sessionId, int adjunctType, String adjunctPath, String adjunctDesc, long orderId, long borrowerId, Integer photoState) {
        try {
            BwAdjunct bwAdjunct = new BwAdjunct();
            bwAdjunct.setOrderId(orderId);
            bwAdjunct.setBorrowerId(borrowerId);
            bwAdjunct.setAdjunctType(adjunctType);
            bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
            if (bwAdjunct == null) {
                bwAdjunct = new BwAdjunct();
                bwAdjunct.setAdjunctDesc(adjunctDesc);
                bwAdjunct.setAdjunctPath(adjunctPath);
                bwAdjunct.setAdjunctType(adjunctType);
                bwAdjunct.setBorrowerId(borrowerId);
                bwAdjunct.setCreateTime(new Date());
                bwAdjunct.setOrderId(orderId);
                bwAdjunct.setPhotoState(photoState);
                bwAdjunct.setUpdateTime(new Date());

                return bwAdjunctService.save(bwAdjunct) > 0;
            } else {
                bwAdjunct.setAdjunctDesc(adjunctDesc);
                bwAdjunct.setAdjunctPath(adjunctPath);
                bwAdjunct.setAdjunctType(adjunctType);
                bwAdjunct.setBorrowerId(borrowerId);
                bwAdjunct.setOrderId(orderId);
                bwAdjunct.setPhotoState(photoState);
                bwAdjunct.setUpdateTime(new Date());

                return bwAdjunctService.update(bwAdjunct) > 0;
            }
        } catch (Exception e) {
            logger.error(sessionId + "保存认证信息异常(orderId=" + orderId + "):", e);
            return false;
        }
    }

    /**
     * 公共方法 - 还款
     *
     * @see com.waterelephant.third.service.ThirdCommonService#thirdRepay(long,
     *      com.waterelephant.entity.BwOrder, com.waterelephant.entity.BwBankCard, java.lang.String)
     */
    @Override
    public ThirdResponse thirdRepay(long sessionId, BwOrder bwOrder, BwBankCard bwBankCard, String amount, String type) {
        ThirdResponse thirdResponse = new ThirdResponse();
        logger.info(sessionId + "-开始ThirdCommonService.thirdRepay method......");
        try {
            String orderId = String.valueOf(bwOrder.getId());

            if (RedisUtils.hexists("xudai:order_id", orderId)) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg("此工单正在续贷中!");
                logger.info(sessionId + "-结束ThirdCommonService.thirdRepay method-" + JSON.toJSONString(thirdResponse));
                return thirdResponse;
            }
            if (RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg("此工单正在连连支付中");
                logger.info(sessionId + "-结束ThirdCommonService.thirdRepay method-" + JSON.toJSONString(thirdResponse));
                return thirdResponse;
            }

            Long statusId = bwOrder.getStatusId();// 工单状态
            logger.info("工单状态:" + statusId);
            if (!(statusId.intValue() == 9 || statusId.intValue() == 13)) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg("工单只有还款中或逾期中才可还款");
                logger.info(sessionId + "-结束ThirdCommonService.thirdRepay method-" + JSON.toJSONString(thirdResponse));
                return thirdResponse;
            }

            BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
            bwRepaymentPlan.setOrderId(bwOrder.getId());
            bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
            if (CommUtils.isNull(bwRepaymentPlan)) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg("没有符合条件的还款计划");
                logger.info(sessionId + "-结束ThirdCommonService.thirdRepay method-" + JSON.toJSONString(thirdResponse));
                return thirdResponse;
            }

            if (statusId.intValue() == 9 && "thirdDefer".equals(type)) {
                // 获取下个还款日
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                String repayTime = sdf.format(bwRepaymentPlan.getRepayTime());
                logger.info("还款时间为：" + repayTime);
                Date repayDate = sdf.parse(repayTime);
                // 离还款日的天数
                int day = MyDateUtils.getDaySpace(new Date(), repayDate);// 间隔时间
                logger.info("离还款时间相隔：" + day + "天");
                if (day > 10) {
                    thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                    thirdResponse.setMsg("到期时间十天内方可续贷");
                    logger.info(sessionId + "-结束ThirdCommonService.thirdRepay method-" + JSON.toJSONString(thirdResponse));
                    return thirdResponse;
                }
            }

            // 判断该用户是否绑卡
            Long borrowerId = bwOrder.getBorrowerId();
            logger.info("开始调用连连签约查询接口,borrowerId=" + borrowerId + ",cardNo=" + bwBankCard.getCardNo());
            CardQueryResult cardQueryResult = LianLianPayService.cardBindQuery(borrowerId.toString(), bwBankCard.getCardNo());
            logger.info("结束调用连连签约查询接口,cardQueryResult=" + JSONObject.toJSONString(cardQueryResult));

            if (CommUtils.isNull(cardQueryResult)) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg("该用户未绑卡");
                logger.info("调用连连签约查询接口返回结果为空");
                return thirdResponse;
            }

            if (!"0000".equals(cardQueryResult.getRet_code())) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg(ThirdUtil.convertLian2Msg(cardQueryResult.getRet_code()));
                logger.info("调用连连签约查询接口返回结果失败，ret_code != 0000");
                return thirdResponse;
            }

            List<Agreement> agreements = cardQueryResult.getAgreement_list();
            if (CommUtils.isNull(agreements)) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg("未签约");
                logger.info("调用连连签约查询接口返回结果失败,Agreement_list is null");
                return thirdResponse;
            }

            String agreeNo = agreements.get(0).getNo_agree();
            logger.info("调用连连签约查询接口返回结果成功，连连支付协议号:" + agreeNo);

            // 设置实际支付金额
            if ("repay".equals(type)) {
                amount = String.valueOf(bwRepaymentPlan.getRealityRepayMoney());
                // 获取逾期记录
                BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
                bwOverdueRecord.setOrderId(bwOrder.getId());
                bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
                if (!CommUtils.isNull(bwOverdueRecord)) {
                    logger.info("有逾期记录,累加逾期金额");
                    amount = new BigDecimal(amount).add(new BigDecimal(bwOverdueRecord.getOverdueAccrualMoney())).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                }
                logger.info("实际支付金额amount=" + amount);
            }

            List<RepaymentPlan> repaymentPlanList = new ArrayList<>();
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

            RepaymentPlan repaymentPlan = new RepaymentPlan();
            repaymentPlan.setAmount(amount);
            repaymentPlan.setDate(dateFormat2.format(bwRepaymentPlan.getRepayTime()));
            repaymentPlanList.add(repaymentPlan);

            BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(bwOrder.getBorrowerId());
            SignalLess signalLess = new SignalLess();
            signalLess.setAcct_name(bwBorrower.getName());
            // signalLess.setApp_request(app_request);
            signalLess.setCard_no(bwBankCard.getCardNo());
            signalLess.setId_no(bwBorrower.getIdCard());
            signalLess.setNo_agree(agreeNo);
            signalLess.setUser_id(borrowerId.toString());

            logger.info("开始调用连连授权接口,signalLess=" + JSONObject.toJSONString(signalLess) + ",orderNo=" + bwOrder.getOrderNo() + ",repays=" + JSONObject.toJSONString(repaymentPlanList));
            PlanResult planResult = LianLianPayService.sigalAccreditPay(signalLess, bwOrder.getOrderNo(), repaymentPlanList);
            logger.info("结束调用连连授权接口，planResult=" + JSONObject.toJSONString(planResult));

            if (CommUtils.isNull(planResult)) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg("支付授权失败");
                logger.info("调用连连授权接口返回结果为空");
                return thirdResponse;
            }

            if (!"0000".equals(planResult.getRet_code())) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg(ThirdUtil.convertLian2Msg(planResult.getRet_code()));
                logger.info("调用连连授权接口返回结果失败,ret_code != 0000");
                return thirdResponse;
            }

            logger.info("调用连连授权接口成功");

            // 支付
            RepayRequest repayRequest = new RepayRequest();
            repayRequest.setNo_order(GenerateSerialNumber.getSerialNumber().substring(8) + bwOrder.getId());
            repayRequest.setUser_id(borrowerId.toString());
            repayRequest.setNo_agree(agreeNo);
            repayRequest.setDt_order(dateFormat.format(new Date()));
            // repayRequest.setName_goods("易秒贷");
            repayRequest.setMoney_order(amount);
            repayRequest.setSchedule_repayment_date(repaymentPlanList.get(0).getDate());
            repayRequest.setRepayment_no(bwOrder.getOrderNo());
            repayRequest.setUser_info_bind_phone(bwBorrower.getPhone());
            repayRequest.setUser_info_dt_register(dateFormat.format(bwBorrower.getCreateTime()));
            repayRequest.setUser_info_full_name(bwBorrower.getName());
            repayRequest.setUser_info_id_no(bwBorrower.getIdCard());
            repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + NOTIFY_MAP.get(type));

            // 存入连连redis中，有效时间15分钟
            synchronized (this) {
                if (RedisUtils.hexists("xudai:order_id", orderId)) {
                    thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                    thirdResponse.setMsg("此工单正在续贷中!");
                    logger.info(sessionId + "-结束ThirdCommonService.thirdRepay method-" + JSON.toJSONString(thirdResponse));
                    return thirdResponse;
                }
                if (RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
                    thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                    thirdResponse.setMsg("此工单正在连连支付中");
                    logger.info(sessionId + "-结束ThirdCommonService.thirdRepay method-" + JSON.toJSONString(thirdResponse));
                    return thirdResponse;
                }
                if (!RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId())) {
                    RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId(), bwOrder.getId().toString(), 900);
                    logger.info("存redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId().toString() + "]");
                }
            }

            logger.info("开始调用连连支付接口，repayRequest=" + JSONObject.toJSONString(repayRequest));
            RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);
            logger.info("结束调用连连支付接口，repaymentResult=" + JSONObject.toJSONString(repaymentResult));

            if (CommUtils.isNull(repaymentResult)) {
                thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
                thirdResponse.setMsg("调用连连支付接口返回结果为空");
                logger.info(sessionId + "-结束ThirdCommonService.thirdRepay method-" + JSON.toJSONString(thirdResponse));
                return thirdResponse;
            }

            if (!"0000".equals(repaymentResult.getRet_code()) && !"1003".equals(repaymentResult.getRet_code())) {
                thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
                thirdResponse.setMsg(ThirdUtil.convertLian2Msg(repaymentResult.getRet_code()));
                // 删除连连REDIS
                logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]");
                RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId());
                logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]成功");
                logger.info("调用连连支付接口返回结果失败,ret_code != 0000");
                return thirdResponse;
            }

            logger.info("调用连连支付接口成功");
            thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
            thirdResponse.setMsg("支付成功");
        } catch (Exception e) {
            logger.error(sessionId + "-结束ThirdCommonService.thirdRepay method-" + e);
            thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
            thirdResponse.setMsg("运行异常");
        }
        logger.info(sessionId + "-结束ThirdCommonService.thirdRepay method-" + JSON.toJSONString(thirdResponse));
        return thirdResponse;
    }

    /**
     *
     * @see com.waterelephant.third.service.ThirdCommonService#bindCard(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void bindCard(String borrowerID, String orderNO, String bankCardNO, HttpServletResponse response) {
        logger.info("开始ThirdCommonService.bindCard method >>");
        try {
            BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNO);
            BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerIdByOrderId(bwOrder.getId());

            RedisUtils.hset("third:bindCard", borrowerID, orderNO); // 放入redis用于回调使用

            // 第二步：连连签约
            String urlReturn = SystemConstant.NOTIRY_URL + "/third/other/interface/bindCardCallback.do";
            SignLess signLess = new SignLess();
            signLess.setUser_id(borrowerID);
            signLess.setId_no(bwBorrower.getIdCard());
            signLess.setAcct_name(bwBorrower.getName());
            signLess.setCard_no(bankCardNO);
            signLess.setUrl_return(urlReturn);

            logger.info("开始调用连连绑卡接口,signLess=" + JSONObject.toJSONString(signLess));
            LianLianPayService.signAccreditPay(signLess, response);
            logger.info("结束调用连连绑卡接口");
        } catch (Exception e) {
            logger.error("执行ThirdCommonService.bindCard method 异常", e);
        }
        logger.info("结束ThirdCommonService.bindCard method");
    }

    /**
     * 公共方法 - 签约
     *
     * @see com.waterelephant.third.service.ThirdCommonService#updateSignContract(String,Integer)
     */
    @Override
    public ThirdResponse updateSignContract(String thirdOrderNo, Integer channelId) {
        logger.info("-开始ThirdCommonService.signContract method-");
        ThirdResponse thirdResponse = new ThirdResponse();
        try {
            // 2.根据第三方订单编号获取订单
            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
            if (CommUtils.isNull(bwOrderRong)) {
                thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
                thirdResponse.setMsg("第三方订单为空！");
                logger.info("-结束ThirdCommonService.signContract method-" + JSON.toJSONString(thirdResponse));
                return thirdResponse;
            }
            Long orderId = bwOrderRong.getOrderId();

            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
            if (CommUtils.isNull(bwOrder)) {
                thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
                thirdResponse.setMsg("订单为空！");
                logger.info("-结束ThirdCommonService.signContract method-" + JSON.toJSONString(thirdResponse));
                return thirdResponse;
            }

            // 获取审批金额
            // Double borrowAmount = bwOrder.getBorrowAmount();

            // 获取利率字典表信息
            BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(bwOrder.getProductId().longValue());

            // 获取审批期限
            String pTerm = bwProductDictionary.getpTerm();

            // 等额本息
            // 合同月利率
            Double contractMonthRate = bwProductDictionary.getpBorrowRateMonth();
            // 借款月利率
            Double pInvestRateMonth = bwProductDictionary.getpInvestRateMonth();
            // // 计算还款金额
            // Double repayAmount = DoubleUtil
            // .round(((borrowAmount / Integer.parseInt(pTerm)) + (borrowAmount * pInvestRateMonth)), 2);
            // // 计算合同金额
            // Double contractAmount = DoubleUtil
            // .round((repayAmount * (Math.pow(1 + contractMonthRate, Integer.parseInt(pTerm)) - 1))
            // / (contractMonthRate * (Math.pow(1 + contractMonthRate, Integer.parseInt(pTerm)))), 2);

            if (bwOrder.getStatusId() == 4) {// 待签约
                bwOrder.setRepayTerm(Integer.parseInt(pTerm));
                bwOrder.setRepayType(Integer.parseInt("1"));
                bwOrder.setBorrowRate(pInvestRateMonth);
                bwOrder.setContractRate(bwProductDictionary.getpInvesstRateYear());
                bwOrder.setContractMonthRate(contractMonthRate);
                bwOrder.setStatusId(11L);
                // bwOrder.setContractAmount(contractAmount);
                bwOrder.setUpdateTime(new Date());
                int num = bwOrderService.updateBwOrder(bwOrder);
                logger.info("修改工单条数:" + num);

                if (num == 0) {
                    thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
                    thirdResponse.setMsg("修改工单失败");
                    logger.info("-结束ThirdCommonService.signContract method-" + JSON.toJSONString(thirdResponse));
                    return thirdResponse;
                }

                // 第三方通知-------------code0093
                logger.info("签约成功===" + bwOrder.getId());
                HashMap<String, String> hm = new HashMap<>();
                hm.put("channelId", CommUtils.toString(bwOrder.getChannel()));
                hm.put("orderId", String.valueOf(bwOrder.getId()));
                hm.put("orderStatus", "11");
                hm.put("result", "签约成功");
                String hmData = JSON.toJSONString(hm);
                RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);

                // 生成合同
                RedisUtils.rpush("system:contract", String.valueOf(orderId));
                BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
                bwOrderProcessRecord.setOrderId(bwOrder.getId());
                bwOrderProcessRecord.setContractTime(new Date());
                bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
            }
            thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
            thirdResponse.setMsg("操作成功");
        } catch (Exception e) {
            logger.error("-结束ThirdCommonService.signContract method-" + e);
            thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
            thirdResponse.setMsg("运行异常");
        }
        logger.info("-结束ThirdCommonService.signContract method-" + JSON.toJSONString(thirdResponse));
        return thirdResponse;
    }

    /**
     * 通过判断新老用户和产品期限获得产品ID
     *
     * @see com.waterelephant.third.service.ThirdCommonService#getProductId(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.Integer)
     */
    @Override
    public BwProductDictionary getProduct(String name, String idCard, String phone, Integer pTerm) {
        BwProductDictionary bwProductDictionary = null;
        try {
            BwBorrower bwBorrower = new BwBorrower();
            bwBorrower.setName(name);
            bwBorrower.setIdCard(idCard);
            bwBorrower.setPhone(phone);
            bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
            if (bwBorrower == null) {
                bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(5);
                return bwProductDictionary;
            }

            BwBorrowerDetail bwBorrowerDetail = new BwBorrowerDetail();
            bwBorrowerDetail.setBorrowerId(bwBorrower.getId());
            bwBorrowerDetail.setUserType(2);
            bwBorrowerDetail = bwBorrowerDetailService.selectOne(bwBorrowerDetail);

            if (bwBorrowerDetail != null) {
                if (pTerm == 30) {
                    bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(3);
                    return bwProductDictionary;
                } else {
                    bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(5);
                    return bwProductDictionary;
                }
            } else {
                bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(5);
                return bwProductDictionary;
            }
        } catch (Exception e) {
            logger.error("【" + phone + "】获取产品ID异常：" + e);
            bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(5);
            return bwProductDictionary;
        }
    }

    @Override
    public BwProductDictionary getProductByLike(String name, String idCard, String phone, Integer pTerm) {
        BwProductDictionary bwProductDictionary = null;
        try {
            idCard = idCard.replace("*", "%");
            phone = phone.replace("*", "%");
            BwBorrower bwBorrower = bwBorrowerService.oldUserFilter2(name, phone, idCard);
            if (bwBorrower == null) {
                bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(5);
                return bwProductDictionary;
            }

            BwBorrowerDetail bwBorrowerDetail = new BwBorrowerDetail();
            bwBorrowerDetail.setBorrowerId(bwBorrower.getId());
            bwBorrowerDetail.setUserType(2);
            bwBorrowerDetail = bwBorrowerDetailService.selectOne(bwBorrowerDetail);

            if (bwBorrowerDetail != null) {
                if (pTerm == 30) {
                    bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(3);
                    return bwProductDictionary;
                } else {
                    bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(5);
                    return bwProductDictionary;
                }
            } else {
                bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(5);
                return bwProductDictionary;
            }
        } catch (Exception e) {
            logger.error("【" + phone + "】获取产品ID异常：" + e);
            bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(5);
            return bwProductDictionary;
        }
    }

}
