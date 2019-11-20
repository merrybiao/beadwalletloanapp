//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.drainage.service.DrainageService;
//import com.waterelephant.entity.BwAdjunct;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwThirdOperateBasic;
//import com.waterelephant.entity.BwThirdOperateVoice;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.mapper.BwBankCardMapper;
//import com.waterelephant.mapper.BwContactListMapper;
//import com.waterelephant.mapper.BwOperateVoiceMapper;
//import com.waterelephant.mapper.BwOrderMapper;
//import com.waterelephant.mapper.BwOrderRongMapper;
//import com.waterelephant.mapper.BwRepaymentPlanMapper;
//import com.waterelephant.mapper.BwThirdOperateBasicMapper;
//import com.waterelephant.mapper.BwThirdOperateVoiceMapper;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.IBwAdjunctService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.geinihua.ApprovalConfirm;
//import com.waterelephant.sxyDrainage.entity.geinihua.BasicInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.BindingCard;
//import com.waterelephant.sxyDrainage.entity.geinihua.Contract;
//import com.waterelephant.sxyDrainage.entity.geinihua.DeviceInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.DeviceInfo.Phone;
//import com.waterelephant.sxyDrainage.entity.geinihua.GnhPlan;
//import com.waterelephant.sxyDrainage.entity.geinihua.GnhRepayMentReq;
//import com.waterelephant.sxyDrainage.entity.geinihua.Operator;
//import com.waterelephant.sxyDrainage.entity.geinihua.Operator.Base;
//import com.waterelephant.sxyDrainage.entity.geinihua.Operator.Call;
//import com.waterelephant.sxyDrainage.entity.geinihua.OrderInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.ResponseInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.Supplement;
//import com.waterelephant.sxyDrainage.entity.geinihua.TemplateInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.TemplateInfo.Relation;
//import com.waterelephant.sxyDrainage.entity.geinihua.UserInfo;
//import com.waterelephant.sxyDrainage.exception.GeinihuaException;
//import com.waterelephant.sxyDrainage.mapper.BwGnhAppMapper;
//import com.waterelephant.sxyDrainage.mapper.BwGnhBillMapper;
//import com.waterelephant.sxyDrainage.mapper.BwGnhDeviceMapper;
//import com.waterelephant.sxyDrainage.mapper.BwGnhGprsMapper;
//import com.waterelephant.sxyDrainage.mapper.BwGnhSmsMapper;
//import com.waterelephant.sxyDrainage.service.AsyncGnhTask;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.GeinihuaService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.OrderStatusEnum;
//import com.waterelephant.sxyDrainage.utils.fenqiguanjia.FenQiGuanJiaUtils;
//import com.waterelephant.sxyDrainage.utils.shandiandai.SddUtil;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.RsaUtil;
//import com.waterelephant.utils.SystemConstant;
//
//import org.apache.commons.lang3.StringUtils;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//@Service
//public class GeinihuaServiceImpl implements GeinihuaService {
//
//    private Logger LOGGER = LoggerFactory.getLogger(getClass());
//
//    /**
//     * 成功
//     */
//    private static final int CODE_SUCCESS = 200;
//    /**
//     * 复贷
//     */
//    private static final int CODE_REPEAT = 10001;
//    /**
//     * 不可申请
//     */
//    private static final int CODE_UNAPPROVABLE = 10004;
//
//    /**
//     * 命中黑名单规则
//     */
//    private static final String CODE_BACKLIST = "2001";
//    /**
//     * 存在进行中的订单
//     */
//    private static final String CODE_PROCESSING_ORDER = "2002";
//    /**
//     * 命中被拒规则
//     */
//    private static final String CODE_REJECT = "2003";
//    /**
//     * 用户年龄超限
//     */
//    private static final String CODE_OVER_AGE = "2004";
//
//    private static final String CALL_TYPE_POSITIVE = "主叫";
//    private static final String CALL_TYPE_CHINA = "国内通话";
//    private static final String REPAY_SUCCESS = "000";
//
//    /**
//     * 订单状态关系映射
//     */
//    public static Map<Long, Integer> orderStatusMap = new HashMap<>();
//    /**
//     * 账单状态关系映射
//     */
//    public static Map<Integer, String> billStatusMap = new HashMap<>();
//
//    /**
//     * 渠道
//     */
//    @Value("${GNH_CHANNEL_ID}")
//    private Integer channelId;
//    /**
//     * 产品
//     */
//    @Value("${GNH_PRODUCT_ID}")
//    private Integer productId;
//    /**
//     * 渠道签约地址
//     */
//    @Value("${url.sign}")
//    private String urlSign;
//
//    @Autowired
//    private CommonService commonService;
//    @Autowired
//    private DrainageService drainageService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private BwOrderRongService bwOrderRongService;
//    @Autowired
//    private IBwMerchantOrderService bwMerchantOrderService;
//    @Autowired
//    private IBwWorkInfoService bwWorkInfoService;
//    @Autowired
//    private BwOperateBasicService bwOperateBasicService;
//    @Autowired
//    private BwOperateVoiceService bwOperateVoiceService;
//    @Autowired
//    private BwBorrowerService bwBorrowerService;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//    @Autowired
//    private BwGnhBillMapper bwGnhBillMapper;
//    @Autowired
//    private BwGnhSmsMapper bwGnhSmsMapper;
//    @Autowired
//    private BwGnhGprsMapper bwGnhGprsMapper;
//    @Autowired
//    private BwOrderMapper bwOrderMapper;
//    @Autowired
//    private BwGnhDeviceMapper bwGnhDeviceMapper;
//    @Autowired
//    private BwGnhAppMapper bwGnhAppMapper;
//    @Autowired
//    private BwOperateVoiceMapper bwOperateVoiceMapper;
//    @Autowired
//    private BwContactListMapper bwContactListMapper;
//    @Autowired
//    private BwThirdOperateVoiceMapper bwThirdOperateVoiceMapper;
//    @Autowired
//    private BwThirdOperateBasicMapper bwThirdOperateBasicMapper;
//    @Autowired
//    private BwOrderRongMapper bwOrderRongMapper;
//    @Autowired
//    private BwBankCardMapper bwBankCardMapper;
//
//    @Autowired
//    private IBwAdjunctService bwAdjunctService;
//    @Autowired
//    private BwProductDictionaryService bwProductDictionaryService;
//    @Autowired
//    private IBwRepaymentPlanService bwRepaymentPlanService;
//    @Autowired
//    private BwRepaymentPlanMapper bwRepaymentPlanMapper;
//    @Autowired
//    private BwOverdueRecordService bwOverdueRecordService;
//
//    @Autowired
//    private AsyncGnhTask asyncGnhTask;
//
//    static {
//        // 审核通过
//        orderStatusMap.put(4L, 95);
//        // 审核不通过
//        orderStatusMap.put(7L, 96);
//        orderStatusMap.put(8L, 96);
//        // 放款成功
//        orderStatusMap.put(9L, 100);
//        // 逾期
//        orderStatusMap.put(13L, 155);
//        // 贷款结清
//        orderStatusMap.put(6L, 200);
//
//
//        // 账单状态关系映射
//        // 未还款
//        billStatusMap.put(1, "1");
//        // 已还款
//        billStatusMap.put(2, "2");
//        // 已逾期
//        billStatusMap.put(3, "4");
//    }
//
//    @Override
//    public ResponseInfo<?> checkUser(UserInfo userInfo) {
//        String userName = userInfo.getUserName();
//        String mobile = userInfo.getMobile().replace("*", "%");
//        String idCard = userInfo.getIdCard().replace("*", "%");
//
//        DrainageRsp checkUser = commonService.checkUser(System.currentTimeMillis(), userName, mobile, idCard);
//        // 判断不支持的情形
//        unapprovable(checkUser.getCode());
//
//        // 判断新老用户
//        boolean oldUserBoolean = drainageService.oldUserFilter2(userName, mobile, idCard);
//        ResponseInfo<String> responseInfo = new ResponseInfo<>();
//        if (oldUserBoolean) {
//            responseInfo.setCode(CODE_REPEAT);
//            responseInfo.setMessage("可以申请，复贷用户");
//        } else {
//            responseInfo.setCode(CODE_SUCCESS);
//            responseInfo.setMessage("可以申请，新用户");
//        }
//        return responseInfo;
//    }
//
//    /**
//     * 不支持申请的
//     *
//     * @param drainageRspCode
//     */
//    private void unapprovable(String drainageRspCode) {
//        ResponseInfo<?> responseInfo = new ResponseInfo<>();
//        responseInfo.setCode(CODE_UNAPPROVABLE);
//        switch (drainageRspCode) {
//            case CODE_PROCESSING_ORDER:
//                responseInfo.setResonCode("R001");
//                responseInfo.setMessage("已经在对方有进行中的贷款");
//                break;
//            case CODE_BACKLIST:
//                responseInfo.setResonCode("R002");
//                responseInfo.setMessage("在对方有不良贷款记录");
//                break;
//            case CODE_REJECT:
//                responseInfo.setResonCode("R003");
//                responseInfo.setMessage("30天内被机构审批拒绝过");
//                break;
//            case CODE_OVER_AGE:
//                responseInfo.setResonCode("R004");
//                responseInfo.setMessage("年龄超限");
//                break;
//            default:
//                return;
//        }
//        throw new GeinihuaException(responseInfo);
//    }
//
//    @Transactional(rollbackFor = RuntimeException.class)
//    @Override
//    public ResponseInfo<?> basicInfo(BasicInfo basicInfo) throws Exception {
//        ResponseInfo<String> responseInfo = new ResponseInfo<>();
//
//        // 根据身份证号查询进行中的工单
//        long sessionId = System.currentTimeMillis();
//        boolean progressOrder = thirdCommonService.checkUserAccountProgressOrder(sessionId,
//                basicInfo.getUserDetail().getIdCard());
//        if (progressOrder) {
//            throw new GeinihuaException(new ResponseInfo<>(CODE_UNAPPROVABLE, "R001",
//                    "已经在对方有进行中的贷款"));
//        }
//
//        // 新增或更新个人信息
//        BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(System.currentTimeMillis(),
//                basicInfo.getUserDetail().getUserName(), basicInfo.getUserDetail().getIdCard(),
//                basicInfo.getUserDetail().getMobile(), channelId);
//        Long borrowerId = borrower.getId();
//        LOGGER.info(sessionId + "---addOrUpdateBorrower：" + borrowerId);
//
//        //订单
//        Long orderId = saveOrUpdateOrder(borrower.getId());
//        LOGGER.info(sessionId + "---saveOrUpdateOrder：【{}】", orderId);
//
//        try {
//            //征信系统需要
//            Map<String, Object> params = new HashMap<>(16);
//            params.put("mobile", borrower.getPhone());
//            params.put("order_id", orderId);
//            params.put("borrower_id", borrower.getId());
//            String value = JSON.toJSONString(params);
//            RedisUtils.rpush("phone_apply", value);
//        } catch (Exception e) {
//            LOGGER.error("征信redis异常", e);
//        }
//        //第三方订单
//        saveOrUpdateOrderRong(orderId, basicInfo.getOrderInfo().getOrderNo());
//        LOGGER.info(sessionId + "---saveOrUpdateOrderRong：" + borrowerId);
//        //商户信息
//        saveOrUpdateMerchantOrder(orderId, borrowerId);
//        LOGGER.info(sessionId + "---saveOrUpdateMerchantOrder：" + borrowerId);
//
//        // 运营商基本信息
//        addOrUpdateOperate(orderId, basicInfo.getAddInfo().getOperator().getBase(),
//                borrower.getId(), orderId);
//        LOGGER.info(sessionId + "---addOrUpdateOperate：" + borrowerId);
//        // 通话记录
//        addOperateVoice(basicInfo.getAddInfo().getOperator().getCall(), borrower.getId(), orderId);
//        LOGGER.info(sessionId + "---addOperateVoice：" + borrowerId);
//        //流量
//        saveGprs(orderId, basicInfo.getAddInfo().getOperator().getGprs());
//        LOGGER.info(sessionId + "---saveBill：【{}】", orderId);
//        //账单
//        saveBill(orderId, basicInfo.getAddInfo().getOperator().getBill());
//        LOGGER.info(sessionId + "---saveBill：【{}】", orderId);
//        //短信
//        saveSms(orderId, basicInfo.getAddInfo().getOperator().getSms());
//        LOGGER.info(sessionId + "---saveSms：【{}】", orderId);
//
//        responseInfo.setCode(CODE_SUCCESS);
//        responseInfo.setMessage("基本信息推送成功！");
//        return responseInfo;
//    }
//
//    @Transactional(rollbackFor = RuntimeException.class)
//    @Override
//    public ResponseInfo<?> supplementInfo(Supplement supplement) {
//        long sessionId = System.currentTimeMillis();
//
//        String thirdOrderNo = supplement.getOrderInfo().getOrderNo();
//
//        BwOrder bwOrder = existsOrder(thirdOrderNo);
//        bwOrder.setBorrowNumber(supplement.getOrderInfo().getLoanTerm());
//        bwOrder.setBorrowAmount(supplement.getOrderInfo().getLoanAmount());
//        bwOrder.setBorrowUse(loadPurpose(supplement.getTemplateInfo().getLoanPurpose()));
//        bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//        bwOrderService.update(bwOrder);
//
//        BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(bwOrder.getBorrowerId());
//        if (bwBorrower == null) {
//            throw new GeinihuaException(ResponseInfo.error("借款人不存在"));
//        }
//
//        // 异步上传身份验证信息
//        asyncGnhTask.uploadPicture(supplement.getTemplateInfo().getIdentity(),
//                bwOrder.getId(), bwBorrower.getId(), channelId);
//
//        /*
//         * 模板信息（包含关联人信息（紧急联系人、直系亲属）、身份验证信息（身份证照片、活体验证）、公司信息等。）
//         */
//        // 工作信息
//        modifyWorkInfo(bwOrder.getId(), supplement.getTemplateInfo(), bwBorrower.getId());
//        LOGGER.info(sessionId + "---modifyWorkInfo：【{}】", bwBorrower.getId());
//        // 手机设备信息（通讯录）
//        modifyContactInfo(bwBorrower.getId(), supplement.getDeviceInfo().getPhoneList());
//        LOGGER.info(sessionId + "---modifyContactInfo：{}", bwBorrower.getId());
//        // 联系人信息：联系人、朋友、同事
//        modifyPersonInfo(supplement.getTemplateInfo(), bwOrder.getId());
//        LOGGER.info(sessionId + "---modifyPersonInfo：【{}】", bwOrder.getId());
//        //设备信息
//        saveDevice(supplement.getDeviceInfo(), bwOrder.getId());
//        LOGGER.info(sessionId + "---saveDevice：【{}】", bwOrder.getId());
//        saveApp(supplement.getDeviceInfo().getAppList(), bwOrder.getId());
//        LOGGER.info(sessionId + "---saveApp：【{}】", bwOrder.getId());
//
//        commonService.sumbitAI(bwBorrower, bwOrder, channelId, thirdOrderNo);
//        return ResponseInfo.success("补充信息推送成功！");
//    }
//
//    @Override
//    public ResponseInfo<?> bindingCards(String orderNo) {
//        BwOrder bwOrder = existsOrder(orderNo);
//
//        BwBankCard bwBankCard = new BwBankCard();
//        bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
//        bwBankCard = bwBankCardMapper.selectOne(bwBankCard);
//        if (bwBankCard == null) {
//            return ResponseInfo.success("没有绑卡信息");
//        }
//
//        BindingCard bindingCard = new BindingCard();
//        bindingCard.setBankCard(bwBankCard.getCardNo());
//        bindingCard.setBankCode(numToCode(bwBankCard.getBankCode()));
//        bindingCard.setRepayCard(1);
//        bindingCard.setIsDefault(1);
//        bindingCard.setCardInfo("银行卡信息");
//        return ResponseInfo.success(Arrays.asList(bindingCard));
//    }
//
//    @Override
//    public ResponseInfo<?> banks() {
//        return ResponseInfo.success(GeinihuaServiceImpl.supportBanks());
//    }
//
//    @Override
//    public ResponseInfo<?> bindCard(BindingCard bindingCard) {
//        //后置绑卡，判断订单是否存在
//        existsOrder(bindingCard.getOrderNo());
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setIdCardNo(bindingCard.getIdCard());
//        drainageBindCardVO.setBankCardNo(bindingCard.getBankCard());
//        drainageBindCardVO.setName(bindingCard.getUserName());
//        //预留手机号，后置绑卡注册手机号不用传
//        drainageBindCardVO.setRegPhone(bindingCard.getMobile());
//        drainageBindCardVO.setBankCode(SddUtil.getBankCode(codeToNum(bindingCard.getBankCode())));
//        drainageBindCardVO.setBindType("2");
//        drainageBindCardVO.setChannelId(Integer.valueOf(channelId));
//        drainageBindCardVO.setNotify(false);
//        //后置必传
//        drainageBindCardVO.setThirdOrderNo(bindingCard.getOrderNo());
//
//        // 开始预绑卡
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(System.currentTimeMillis(),
//                drainageBindCardVO);
//        if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//            return ResponseInfo.success("预绑卡成功");
//        } else {
//            return ResponseInfo.error(drainageRsp.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseInfo<?> verifyBinding(BindingCard bindingCard) {
//        //后置绑卡，判断订单是否存在
//        existsOrder(bindingCard.getOrderNo());
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setIdCardNo(bindingCard.getIdCard());
//        drainageBindCardVO.setName(bindingCard.getUserName());
//        drainageBindCardVO.setBindType("2");
//        drainageBindCardVO.setPhone(bindingCard.getMobile());
//        drainageBindCardVO.setChannelId(Integer.valueOf(channelId));
//        drainageBindCardVO.setNotify(false);
//        drainageBindCardVO.setVerifyCode(bindingCard.getSmsCode());
//
//        // 开始验证码绑卡
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(System.currentTimeMillis(), drainageBindCardVO);
//
//        if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//            return ResponseInfo.success("确认绑卡成功");
//        } else {
//            return ResponseInfo.error(drainageRsp.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseInfo<?> approvalResult(OrderInfo orderInfo) {
//        BwOrder bwOrder = existsOrder(orderInfo.getOrderNo());
//        long orderStatus = bwOrder.getStatusId();
//        if (orderStatus == OrderStatusEnum.REJECTED.getStatus()
//                || orderStatus == OrderStatusEnum.REVOKED.getStatus()
//                || orderStatus == OrderStatusEnum.SIGN.getStatus()) {
//            Map<String, Object> map = new HashMap<>(16);
//            map.put("orderNo", orderInfo.getOrderNo());
//
//            //审批结果，1：通过，2：未通过
//            int conclusion = orderStatus == OrderStatusEnum.SIGN.getStatus() ? 1 : 2;
//            map.put("conclusion", conclusion);
//            //结果说明
//            String instructions = orderStatus == OrderStatusEnum.SIGN.getStatus() ? "审核通过" : "系统评分不足";
//            map.put("instructions", instructions);
//            map.put("approvalTime", bwOrder.getUpdateTime().getTime());
//            //最大/小申请金额
//            map.put("maxAmount", bwOrder.getBorrowAmount());
//            map.put("minAmount", bwOrder.getBorrowAmount());
//            map.put("rangeAmount", 100);
//            //借款期限单位，1：日、2：月
//            map.put("loanUnit", 1);
//            map.put("loanTerm", "28");
//            // 利率 0.00098
//            BwProductDictionary bwProductDictionary = bwProductDictionaryService
//                    .findBwProductDictionaryById(Integer.valueOf(productId));
//            Double interestRate = bwProductDictionary.getInterestRate();
//            interestRate = DoubleUtil.div(interestRate, 7.0, 5);
//            DecimalFormat df = new DecimalFormat("0.00000");
//            map.put("loanRate", df.format(interestRate));
//            return ResponseInfo.success(map);
//        }
//        return ResponseInfo.success("不存在该订单的审批结论");
//    }
//
//    /**
//     * phone:用户手机号
//     * order_no：三方工单编号
//     * platform：平台信息(平台：1安卓，2ios，4微信)
//     * params：MD5加密信息(phone=13297035695&order_no=222222223794)
//     * returnUrl：三方回调地址
//     *
//     * @param orderInfo
//     * @return
//     */
//    @Override
//    public ResponseInfo<String> approvalConfirm(OrderInfo orderInfo) {
//        BwOrder bwOrder = existsOrder(orderInfo.getOrderNo());
//        BwBorrower borrower = bwBorrowerService.findBwBorrowerById(bwOrder.getBorrowerId());
//        String phone = borrower.getPhone();
//        String orderNo = orderInfo.getOrderNo();
//        String params = RsaUtil.md5("phone=" + phone + "&order_no=" + orderNo);
//        //todo 回调地址没有
//        String returnURL = "";
//
//        String signURL = urlSign + "phone=" + phone + "&order_no=" + orderNo + "&params=" + params
//                + "&platform=4" + "&returnUrl=" + returnURL;
//        return new ResponseInfo<>("签约地址", signURL);
//    }
//
//    @Override
//    public ResponseInfo<?> contract(Contract contract) {
//        BwOrder bwOrder = existsOrder(contract.getOrderNo());
//
//        // 服务合同
//        BwAdjunct bwAdjunct = new BwAdjunct();
//        bwAdjunct.setOrderId(bwOrder.getId());
//        bwAdjunct.setAdjunctType(29);
//        bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
//        if (bwAdjunct == null) {
//            return ResponseInfo.success("未查到相关合同");
//        }
//        String contractUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
//        return ResponseInfo.success(Arrays.asList(contractUrl));
//    }
//
//    @Override
//    public ResponseInfo<?> caculateOrder(ApprovalConfirm approvalConfirm) {
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService
//                .findBwProductDictionaryById(productId);
//        if (bwProductDictionary == null) {
//            throw new GeinihuaException(ResponseInfo.error("未查到贷款产品"));
//        }
//        // 利息
//        Double borrowAmount = approvalConfirm.getLoanAmount();
//        Double interestRate = bwProductDictionary.getInterestRate();
//        Double totalInterest = DrainageUtils.calculateRepayMoney(borrowAmount, 1, interestRate) + SddUtil.calculateRepayMoney(borrowAmount, 2, interestRate)
//                + SddUtil.calculateRepayMoney(borrowAmount, 3, interestRate) + SddUtil.calculateRepayMoney(borrowAmount, 4, interestRate);
//
//        Map<String, Object> map = new HashMap<>(16);
//        map.put("serviceFee", 0.00D);
//        map.put("actualAmount", borrowAmount);
//        map.put("repayAmount", borrowAmount + totalInterest);
//        map.put("remark", String.format("本金%.2f元，利息%.2f", borrowAmount, totalInterest));
//        return ResponseInfo.success(map);
//    }
//
//    @Override
//    public ResponseInfo<?> orderStatus(OrderInfo orderInfo) {
//        BwOrder bwOrder = existsOrder(orderInfo.getOrderNo());
//        Integer status = orderStatusMap.get(bwOrder.getStatusId());
//        if (status == null) {
//            return ResponseInfo.success("订单处于审核中");
//        }
//
//        Map<String, Object> map = new HashMap<>(16);
//        map.put("orderNo", orderInfo.getOrderNo());
//        map.put("orderStatus", status);
//        map.put("updateTime", bwOrder.getUpdateTime());
//
//        return ResponseInfo.success(map);
//    }
//
//    @Override
//    public ResponseInfo<?> postiveRepay(OrderInfo orderInfo) {
//        BwOrder bwOrder = existsOrder(orderInfo.getOrderNo());
//
//        // 查询还款计划
//        List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//        if (CommUtils.isNull(bwRepaymentPlans)) {
//            new ResponseInfo(510, "错误，未查到还款计划");
//        }
//
//        int period = Integer.parseInt(orderInfo.getPeriodNos());
//
//        for (BwRepaymentPlan plan : bwRepaymentPlans) {
//            if (period == plan.getNumber() && plan.getRepayStatus() == 2) {
//                return new ResponseInfo(510, "请勿重复还款");
//            }
//            if (period > plan.getNumber() && plan.getRepayStatus() != 2) {
//                return new ResponseInfo(510, "期数错误，请依次还款");
//            }
//        }
//
//        // 提交还款
//        DrainageRsp drainageRsp = commonService.updateRepayment_New(System.currentTimeMillis(), orderInfo.getOrderNo());
//        if (drainageRsp != null) {
//            if (REPAY_SUCCESS.equals(drainageRsp.getCode())) {
//                return ResponseInfo.success("主动还款成功");
//            } else {
//                return new ResponseInfo(510, drainageRsp.getMessage());
//            }
//        } else {
//            return ResponseInfo.error("支付接口返回信息为空");
//        }
//    }
//
//    @Override
//    public ResponseInfo<?> replanRepay(OrderInfo orderInfo) {
//        BwOrder bwOrder = existsOrder(orderInfo.getOrderNo());
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//
//        // 查询还款计划
//        List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanMapper.findRepaymentPlans(bwOrder.getId());
//        if (bwRepaymentPlans == null || bwRepaymentPlans.size() == 0) {
//            return ResponseInfo.success("没有还款计划");
//        }
//
//        // 初始还款总额
//        Double totalAmount = 0.0;
//        // 初始已还金额
//        Double alpaid = 0.0;
//
//        // 还款计划
//        List<GnhPlan> list = new ArrayList<>(4);
//        for (BwRepaymentPlan plan : bwRepaymentPlans) {
//            GnhPlan gnhPlan = new GnhPlan();
//            gnhPlan.setPeriodNo(plan.getNumber());
//            gnhPlan.setDueTime(sdfDate.format(plan.getRepayTime()));
//            gnhPlan.setAmount(plan.getRepayMoney() + "");
//
//            // 逾期费用
//            Double overdueFee = 0.0;
//            BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//            bwOverdueRecord.setRepayId(plan.getId());
//            bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//            if (bwOverdueRecord != null) {
//                overdueFee = DoubleUtil.sub(bwOverdueRecord.getOverdueAccrualMoney(), bwOverdueRecord.getAdvance());
//            }
//            gnhPlan.setOverdueFee(overdueFee + "");
//            //账单状态： 1. 未还款 2. 已还款、3.未出账、4. 已逾期、5.还款失败，6.还款中
//            gnhPlan.setBillStatus(billStatusMap.get(plan.getRepayStatus()));
//            gnhPlan.setPrinciple(plan.getRepayCorpusMoney() + "");
//            gnhPlan.setInterest(plan.getRepayAccrualMoney() + "");
//
//            //还款方式
//            gnhPlan.setPayType("");
//            gnhPlan.setSuccessTime("");
//            if (plan.getRepayStatus() == 2) {
//                gnhPlan.setPayType("主动还款");
//                gnhPlan.setSuccessTime(sdf.format(plan.getUpdateTime()));
//            }
//
//            // 累计 还款总额
//            totalAmount = DoubleUtil.add(totalAmount, plan.getRepayMoney());
//            totalAmount = DoubleUtil.add(totalAmount, overdueFee);
//
//            // 累计 已还金额
//            alpaid = DoubleUtil.add(alpaid, plan.getAlreadyRepayMoney());
//
//        }
//
//        // 订单状态请求类
//        GnhRepayMentReq gnhRepayMentReq = new GnhRepayMentReq();
//
//        gnhRepayMentReq.setOrderNo(orderInfo.getOrderNo());
//        gnhRepayMentReq.setTotalAmount(totalAmount);
//        gnhRepayMentReq.setCharge("0.0");
//        gnhRepayMentReq.setAlpaid(alpaid + "");
//        gnhRepayMentReq.setBepaid(DoubleUtil.sub(totalAmount, alpaid) + "");
//        gnhRepayMentReq.setTotalPeriod(4);
//
//        // 已还期数
//        gnhRepayMentReq.setFinishPeriod(getPlanNumber(bwRepaymentPlans));
//        // 添加还款计划
//        gnhRepayMentReq.setList(list);
//        return ResponseInfo.success(gnhRepayMentReq);
//    }
//
//    @Override
//    public ResponseInfo<?> detailRepay(OrderInfo orderInfo) {
//        BwOrder bwOrder = existsOrder(orderInfo.getOrderNo());
//        BwRepaymentPlan repaymentPlan = bwRepaymentPlanMapper.findRepaymentPlan(bwOrder.getId(),
//                Integer.parseInt(orderInfo.getPeriodNos()));
//
//        //包含利息的
//        Double repayMoney = repaymentPlan.getRepayMoney();
//        //利息
//        Double repayAccrualMoney = repaymentPlan.getRepayAccrualMoney();
//        //本金
//        Double repayCorpusMoney = repaymentPlan.getRepayCorpusMoney();
//        Map<String, Object> map = new HashMap<>(16);
//        map.put("amount", repayMoney);
//        map.put("remark", String.format("含本金%f元，利息&手续费%f元", repayCorpusMoney, repayAccrualMoney));
//
//        return ResponseInfo.success(map);
//    }
//
//    /**
//     * 已还款期数
//     *
//     * @param bwRepaymentPlans
//     * @return
//     */
//    private int getPlanNumber(List<BwRepaymentPlan> bwRepaymentPlans) {
//        int num = 0;
//        for (BwRepaymentPlan plan : bwRepaymentPlans) {
//            if (plan.getRepayStatus().intValue() == 2) {
//                num++;
//            }
//        }
//        return num;
//    }
//
//    /**
//     * APP列表
//     *
//     * @param appList
//     * @param orderId
//     */
//    private void saveApp(List<DeviceInfo.App> appList, Long orderId) {
//        if (appList == null || appList.size() == 0) {
//            return;
//        }
//
//        DeviceInfo.App app = new DeviceInfo.App();
//        app.setOrderId(orderId);
//        bwGnhAppMapper.delete(app);
//
//
//        List<DeviceInfo.App> apps = new ArrayList<>();
//        Iterator<DeviceInfo.App> iterator = appList.iterator();
//        while (iterator.hasNext()) {
//            DeviceInfo.App ap = iterator.next();
//            ap.setOrderId(orderId);
//            Date time = Calendar.getInstance().getTime();
//            ap.setGmtCreate(time);
//            ap.setGmtModified(time);
//            apps.add(ap);
//        }
//
//        //批处理执行
//        commonService.batch(apps, bwGnhAppMapper);
//    }
//
//    /**
//     * 保存设备信息
//     *
//     * @param deviceInfo 设备信息
//     */
//    private void saveDevice(DeviceInfo deviceInfo, Long orderId) {
//        deviceInfo.setOrderId(orderId);
//        bwGnhDeviceMapper.delete(deviceInfo);
//
//        Date time = Calendar.getInstance().getTime();
//        deviceInfo.setGmtCreate(time);
//        deviceInfo.setGmtModified(time);
//        bwGnhDeviceMapper.insert(deviceInfo);
//    }
//
//    /**
//     * 贷款用途
//     *
//     * @param purpose 贷款用途
//     * @return
//     */
//    private String loadPurpose(Integer purpose) {
//        if (purpose == null) {
//            purpose = 11;
//        }
//        switch (purpose) {
//            case 1:
//                return "家庭装修";
//            case 2:
//                return "旅游度假";
//            case 3:
//                return "租房";
//            case 4:
//                return "教育培训";
//            case 5:
//                return "婚庆";
//            case 6:
//                return "数码电器";
//            case 7:
//                return "健康医疗";
//            case 8:
//                return "购车";
//            case 9:
//                return "购房";
//            case 10:
//                return "经营周转";
//            case 11:
//                return "其他";
//            default:
//                return "其他";
//        }
//    }
//
//    /**
//     * 修改通讯录信息
//     *
//     * @param borrowerId
//     * @param phones
//     */
//    private void modifyContactInfo(Long borrowerId, List<Phone> phones) {
//        if (Objects.isNull(phones)) {
//            return;
//        }
//
//        bwContactListMapper.deleteByBorrowerId(borrowerId);
//
//        List<BwContactList> contactList = new ArrayList<>();
//        for (Phone phone : phones) {
//            if (StringUtils.isBlank(phone.getName())) {
//                continue;
//            }
//            if (StringUtils.isBlank(phone.getPhone())) {
//                continue;
//            }
//            BwContactList bwContact = new BwContactList();
//            bwContact.setBorrowerId(borrowerId);
//            bwContact.setName(phone.getName());
//            bwContact.setPhone(phone.getPhone());
//            Date now = new Date();
//            bwContact.setCreateTime(now);
//            bwContact.setUpdateTime(now);
//            contactList.add(bwContact);
//        }
//        //批处理
//        commonService.batch(contactList, bwContactListMapper);
//    }
//
//    /**
//     * 修改个人信息
//     *
//     * @param orderId
//     * @param templateInfo
//     * @param borrowerId
//     */
//    private void modifyWorkInfo(Long orderId, TemplateInfo templateInfo, Long borrowerId) {
//        BwWorkInfo bwWorkInfo = new BwWorkInfo();
//        bwWorkInfo.setOrderId(orderId);
//        bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//        if (CommUtils.isNull(bwWorkInfo)) {
//            BwWorkInfo bwi = new BwWorkInfo();
//            bwi.setOrderId(orderId);
//            Date time = Calendar.getInstance().getTime();
//            bwi.setCreateTime(time);
//            bwi.setUpdateTime(time);
//            bwi.setWorkYears(getWorkPeriod(templateInfo.getGraduationTermLevel()));
//            bwi.setIndustry(getIndustryType(templateInfo.getIndustryCategory()));
//            bwi.setComName(templateInfo.getCompany());
//            bwWorkInfoService.save(bwi, borrowerId);
//        } else {
//            // 更新公司信息
//            BwWorkInfo bwi = new BwWorkInfo();
//            bwi.setOrderId(orderId);
//            bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
//            bwi.setUpdateTime(Calendar.getInstance().getTime());
//            bwi.setWorkYears(getWorkPeriod(templateInfo.getGraduationTermLevel()));
//            bwi.setIndustry(getIndustryType(templateInfo.getIndustryCategory()));
//            bwi.setComName(templateInfo.getCompany());
//            bwWorkInfoService.update(bwi);
//        }
//        // 插入个人认证记录
//        thirdCommonService.addOrUpdateBwOrderAuth(System.currentTimeMillis(), orderId, 2, channelId);
//    }
//
//    /**
//     * 判断职业类别
//     *
//     * @param key
//     * @return
//     */
//    public static String getIndustryType(Integer key) {
//
//        /*
//         * 1=农、林、牧、渔业，2=采矿业，3=制造业，4=电力、热力、燃气及水生产和供应业，
//         * 5=建筑业，6=批发和零售业，7=交通运输、仓储和邮政业，8=住宿和餐饮业，9=信息传输、软件和信息技术服务业，
//         * 10=金融业，11=房地产业，12=租赁和商务服务业，13=科学研究和技术服务业，14=水利、环境和公共设施管理业，
//         * 15=居民服务、修理和其他服务业，16=教育，17=卫生和社会工作，18=文化、体育和娱乐业，
//         * 19=公共管理、社会保障和社会组织，20=国际组织，21=其他
//         */
//        String[] array = {"农、林、牧、渔业", "采矿业", "制造业", "电力、热力、燃气及水生产和供应业",
//                "建筑业", "批发和零售业", "交通运输、仓储和邮政业", "住宿和餐饮业", "信息传输、软件和信息技术服务业",
//                "金融业", "房地产业", "租赁和商务服务业", "科学研究和技术服务业", "水利、环境和公共设施管理业",
//                "居民服务、修理和其他服务业", "教育", "卫生和社会工作", "文化、体育和娱乐业",
//                "公共管理、社会保障和社会组织", "国际组织", "其他"};
//        return array[--key];
//    }
//
//    /**
//     * 判断工作年限
//     *
//     * @param key
//     * @return
//     */
//    public static String getWorkPeriod(Integer key) {
//        // 1：0-5个月 2：6-11个月 3：1-3年 4：3-7年 5： 7年以上
//        String workPeriod;
//        if (Objects.isNull(key)) {
//            workPeriod = "1年以内";
//        } else {
//            switch (key) {
//                case 1:
//                    workPeriod = "0-5个月";
//                    break;
//                case 2:
//                    workPeriod = "6-11个月";
//                    break;
//                case 3:
//                    workPeriod = "1-3年";
//                    break;
//                case 4:
//                    workPeriod = "3-7年";
//                    break;
//                case 5:
//                    workPeriod = "7年以上";
//                    break;
//                default:
//                    workPeriod = "1年以内";
//                    break;
//            }
//        }
//        return workPeriod;
//    }
//
//    /**
//     * 修改联系人信息
//     *
//     * @param templateInfo
//     * @param orderId
//     */
//    private void modifyPersonInfo(TemplateInfo templateInfo, Long orderId) {
//        BwPersonInfo bwPersonInfo_ = new BwPersonInfo();
//        // 给你花只传2个联系人
//        List<Relation> relations = templateInfo.getRelations();
//        for (Relation relation : relations) {
//            /*
//             * 关系：1=父母，2=配偶，3=同事，4=同学，5=其他，6=父亲，7=母亲，8=兄弟，9=姐妹，10=已成年子女，11=朋友
//             */
//            switch (relation.getRelationType()) {
//                // 父母
//                case "1":
//                    bwPersonInfo_.setRelationName(relation.getName());
//                    bwPersonInfo_.setRelationPhone(relation.getPhone());
//                    break;
//                case "2":
//                    break;
//                // 同事
//                case "3":
//                    bwPersonInfo_.setColleagueName(relation.getName());
//                    bwPersonInfo_.setColleaguePhone(relation.getPhone());
//                    break;
//                // 同学
//                case "4":
//                    bwPersonInfo_.setFriend2Name(relation.getName());
//                    bwPersonInfo_.setFriend2Phone(relation.getPhone());
//                    break;
//                // 非亲属
//                case "5":
//                    bwPersonInfo_.setUnrelationName(relation.getName());
//                    bwPersonInfo_.setUnrelationPhone(relation.getPhone());
//                    break;
//                // 朋友
//                case "11":
//                    bwPersonInfo_.setFriend1Name(relation.getName());
//                    bwPersonInfo_.setFriend1Phone(relation.getPhone());
//                    break;
//                default:
//                    break;
//            }
//        }
//        bwPersonInfo_.setQqchat(templateInfo.getQq());
//        bwPersonInfo_.setWechat(templateInfo.getWechat());
//
//        bwPersonInfo_.setAddress(templateInfo.getCurrentAddress().getAddress());
//        bwPersonInfo_.setEmail(templateInfo.getEmail());
//        bwPersonInfo_.setMarryStatus(templateInfo.getMaritalStatus());
//        bwPersonInfo_.setCityName(templateInfo.getCurrentAddress().getCity());
//
//        // 查询联系人信息
//        BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//        if (bwPersonInfo == null) {
//            bwPersonInfo_.setCreateTime(new Date());
//            bwPersonInfo_.setOrderId(orderId);
//            bwPersonInfo_.setUpdateTime(new Date());
//            bwPersonInfoService.add(bwPersonInfo_);
//        } else if (null != bwPersonInfo_) {
//            bwPersonInfo.setAddress(bwPersonInfo_.getAddress());
//            bwPersonInfo.setCityName(bwPersonInfo_.getCityName());
//            bwPersonInfo.setEmail(bwPersonInfo_.getEmail());
//            bwPersonInfo.setMarryStatus(bwPersonInfo_.getMarryStatus());
//            bwPersonInfo.setRelationName(bwPersonInfo_.getRelationName());
//            bwPersonInfo.setRelationPhone(bwPersonInfo_.getRelationPhone());
//            bwPersonInfo.setUnrelationName(bwPersonInfo_.getUnrelationName());
//            bwPersonInfo.setUnrelationPhone(bwPersonInfo_.getUnrelationPhone());
//            bwPersonInfo.setFriend1Name(bwPersonInfo_.getFriend1Name());
//            bwPersonInfo.setFriend1Phone(bwPersonInfo_.getFriend1Phone());
//            bwPersonInfo.setColleagueName(bwPersonInfo_.getColleagueName());
//            bwPersonInfo.setColleaguePhone(bwPersonInfo_.getColleaguePhone());
//            bwPersonInfo.setUpdateTime(new Date());
//            bwPersonInfoService.update(bwPersonInfo);
//        }
//    }
//
//    /**
//     * 流量
//     *
//     * @param orderId
//     * @param gprsMap
//     */
//    private void saveGprs(Long orderId, Map<String, Operator.Gprs> gprsMap) {
//        Operator.Gprs g = new Operator.Gprs();
//        g.setOrderId(orderId);
//        bwGnhGprsMapper.delete(g);
//
//        List<Operator.Gprs> gprss = new ArrayList<>();
//        gprsMap.forEach((key, gprs) -> {
//            Date now = new Date();
//            gprs.setGmtCreate(now);
//            gprs.setGmtModified(now);
//            gprs.setOrderId(orderId);
//            gprss.add(gprs);
//        });
//
//        //批处理执行
//        commonService.batch(gprss, bwGnhGprsMapper);
//    }
//
//    /**
//     * 保存账单
//     *
//     * @param orderId
//     * @param billMap
//     */
//    private void saveBill(Long orderId, Map<String, Operator.Bill> billMap) {
//        //先删除当前订单的
//        Operator.Bill bill = new Operator.Bill();
//        bill.setOrderId(orderId);
//        bwGnhBillMapper.delete(bill);
//
//        List<Operator.Bill> bills = new ArrayList<>();
//        billMap.forEach((key, bil) -> {
//            Date now = new Date();
//            bil.setGmtCreate(now);
//            bil.setGmtModified(now);
//            bil.setOrderId(orderId);
//            bills.add(bil);
//        });
//
//        //批处理执行
//        commonService.batch(bills, bwGnhBillMapper);
//    }
//
//    /**
//     * 保存短信
//     *
//     * @param orderId
//     * @param smsMap
//     */
//    private void saveSms(Long orderId, Map<String, List<Operator.Sms>> smsMap) {
//        Operator.Sms s = new Operator.Sms();
//        s.setOrderId(orderId);
//        bwGnhSmsMapper.delete(s);
//
//        List<Operator.Sms> smsList = new ArrayList<>();
//        smsMap.forEach((key, smss) ->
//                smss.forEach(sms -> {
//                    sms.setOrderId(orderId);
//                    Date now = new Date();
//                    sms.setGmtCreate(now);
//                    sms.setGmtModified(now);
//
//                    smsList.add(sms);
//                })
//        );
//
//        //批处理执行
//        commonService.batch(smsList, bwGnhSmsMapper);
//    }
//
//    /**
//     * 保存或更新订单
//     *
//     * @param borrowerId
//     * @return
//     */
//    private Long saveOrUpdateOrder(Long borrowerId) {
//        // 判断是否有草稿状态的订单，草稿会有多个
//        BwOrder bwOrder = new BwOrder();
//        bwOrder.setBorrowerId(borrowerId);
//        bwOrder.setStatusId(1L);
//        bwOrder.setProductType(2);
//        bwOrder.setChannel(channelId);
//        bwOrder.setProductId(productId);
//        // 先查询草稿状态的订单
//        List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//        if (boList != null && boList.size() > 0) {
//            bwOrder = boList.get(boList.size() - 1);
//            bwOrder.setStatusId(1L);
//            bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwOrder.setProductType(2);
//            bwOrder.setExpectNumber(4);
//            bwOrder.setRepayType(2);
//            bwOrderService.updateBwOrder(bwOrder);
//        } else {
//            bwOrder = new BwOrder();
//            bwOrder.setOrderNo(FenQiGuanJiaUtils.generateOrderNo());
//            bwOrder.setBorrowerId(borrowerId);
//            bwOrder.setStatusId(1L);
//            bwOrder.setCreateTime(Calendar.getInstance().getTime());
//            bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwOrder.setChannel(Integer.valueOf(channelId));
//            bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//            bwOrder.setApplyPayStatus(0);
//            bwOrder.setProductId(productId);
//            bwOrder.setProductType(2);
//            bwOrder.setRepayType(2);
//            bwOrder.setExpectNumber(4);
//            bwOrderService.addBwOrder(bwOrder);
//        }
//        return bwOrder.getId();
//    }
//
//    /**
//     * 保存或更新三方订单
//     *
//     * @param orderId
//     * @param orderNo
//     */
//    private void saveOrUpdateOrderRong(Long orderId, String orderNo) {
//        BwOrderRong bwOrderRong = new BwOrderRong();
//        bwOrderRong.setOrderId(orderId);
//        bwOrderRong.setChannelId(Long.valueOf(channelId));
//        bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//
//        if (bwOrderRong == null) {
//            bwOrderRong = new BwOrderRong();
//            bwOrderRong.setOrderId(orderId);
//            bwOrderRong.setChannelId(Long.valueOf(channelId));
//            bwOrderRong.setThirdOrderNo(orderNo);
//            bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//            bwOrderRongService.save(bwOrderRong);
//        } else {
//            bwOrderRong.setThirdOrderNo(orderNo);
//            bwOrderRongService.update(bwOrderRong);
//        }
//    }
//
//    /**
//     * 保存或更新商户信息
//     *
//     * @param orderId
//     */
//    private void saveOrUpdateMerchantOrder(Long orderId, Long borrowerId) {
//        BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//        bwMerchantOrder.setOrderId(orderId);
//        bwMerchantOrder = bwMerchantOrderService.selectByAtt(bwMerchantOrder);
//
//        //新商户信息
//        BwMerchantOrder bwMerchantOrderNew = new BwMerchantOrder();
//        bwMerchantOrderNew.setExt3("0");
//        bwMerchantOrderNew.setMerchantId(0L);
//        bwMerchantOrderNew.setOrderId(orderId);
//        bwMerchantOrderNew.setBorrowerId(borrowerId);
//        bwMerchantOrderNew.setUpdateTime(Calendar.getInstance().getTime());
//        if (bwMerchantOrder == null) {
//            bwMerchantOrderNew.setCreateTime(Calendar.getInstance().getTime());
//            bwMerchantOrderService.insertByAtt(bwMerchantOrderNew);
//        } else {
//            bwMerchantOrderService.updateByAtt(bwMerchantOrderNew);
//        }
//    }
//
//    /**
//     * 通话记录
//     *
//     * @param callMap
//     * @param borrowerId
//     * @throws Exception
//     */
//    private void addOperateVoice(Map<String, List<Call>> callMap, Long borrowerId, Long orderId) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        // 最后通话时间
//        Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//
//        List<BwOperateVoice> operateVoices = new ArrayList<>();
//        List<BwThirdOperateVoice> thirdOperateVoices = new ArrayList<>();
//
//        callMap.forEach((key, calls) ->
//                calls.forEach((call) -> {
//                    if (CommUtils.isNull(callDate) || call.getCalltime().after(callDate)) {
//                        BwOperateVoice bwOperateVoice = new BwOperateVoice();
//                        bwOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
//                        bwOperateVoice.setBorrower_id(borrowerId);
//                        String callTime = dateFormat.format(call.getCalltime());
//                        bwOperateVoice.setCall_time(callTime);
//                        bwOperateVoice.setCall_type(CALL_TYPE_POSITIVE.equals(call.getCalltype()) ? 1 : 2);
//                        bwOperateVoice.setReceive_phone(call.getCallphone());
//                        bwOperateVoice.setTrade_addr(call.getHomearea());
//                        bwOperateVoice.setTrade_time(call.getCalllong());
//                        bwOperateVoice.setTrade_type(CALL_TYPE_CHINA.equals(call.getLandtype()) ? 1 : 2);
//                        bwOperateVoice.setUpdateTime(new Date());
//                        operateVoices.add(bwOperateVoice);
//
//                        //新增表
//                        BwThirdOperateVoice thirdOperateVoice = new BwThirdOperateVoice();
//                        thirdOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
//                        thirdOperateVoice.setCallTime(callTime);
//                        thirdOperateVoice.setCallType(CALL_TYPE_POSITIVE.equals(call.getCalltype()) ? 1 : 2);
//                        thirdOperateVoice.setReceivePhone(call.getCallphone());
//                        thirdOperateVoice.setTradeAddr(call.getHomearea());
//                        thirdOperateVoice.setTradeTime(call.getCalllong());
//                        thirdOperateVoice.setTradeType(CALL_TYPE_CHINA.equals(call.getLandtype()) ? 1 : 2);
//                        thirdOperateVoice.setUpdateTime(new Date());
//                        thirdOperateVoice.setOrderId(orderId);
//                        thirdOperateVoice.setChannel(channelId);
//                        thirdOperateVoices.add(thirdOperateVoice);
//                    }
//                })
//        );
//        //批处理插入
//        commonService.batch(operateVoices, bwOperateVoiceMapper);
//        commonService.batch(thirdOperateVoices, bwThirdOperateVoiceMapper);
//    }
//
//    /**
//     * 添加运营商基本信息
//     *
//     * @param authOrderId
//     * @param base
//     * @param borrowerId
//     */
//    private void addOrUpdateOperate(Long authOrderId, Base base, Long borrowerId, Long orderId) {
//        BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//        if (CommUtils.isNull(bwOperateBasic)) {
//            // 添加基本信息
//            bwOperateBasic = new BwOperateBasic();
//            bwOperateBasic.setCreateTime(new Date());
//            modifyOperateBasic(bwOperateBasic, base, borrowerId);
//            bwOperateBasicService.save(bwOperateBasic);
//        } else {
//            // 修改基本信息
//            modifyOperateBasic(bwOperateBasic, base, borrowerId);
//            bwOperateBasicService.update(bwOperateBasic);
//        }
//
//        //新增表
//        BwThirdOperateBasic thirdOperateBasic = new BwThirdOperateBasic();
//        thirdOperateBasic.setOrderId(orderId);
//        thirdOperateBasic = bwThirdOperateBasicMapper.selectOne(thirdOperateBasic);
//        if (Objects.isNull(thirdOperateBasic)) {
//            thirdOperateBasic = new BwThirdOperateBasic();
//            thirdOperateBasic.setOrderId(orderId);
//            thirdOperateBasic.setCreateTime(Calendar.getInstance().getTime());
//            modifyThirdOperateBasic(thirdOperateBasic, base, orderId);
//            bwThirdOperateBasicMapper.insert(thirdOperateBasic);
//        } else {
//            modifyThirdOperateBasic(thirdOperateBasic, base, orderId);
//            bwThirdOperateBasicMapper.updateByPrimaryKeySelective(thirdOperateBasic);
//        }
//
//        //认证类型，1：运营商 2：个人信息 3：拍照
//        thirdCommonService.addOrUpdateBwOrderAuth(System.currentTimeMillis(), authOrderId, 1, channelId);
//    }
//
//    /**
//     * 新增表
//     *
//     * @param thirdOperateBasic
//     * @param base
//     * @param orderId
//     */
//    private void modifyThirdOperateBasic(BwThirdOperateBasic thirdOperateBasic,
//                                         Base base, Long orderId) {
//        thirdOperateBasic.setUserSource(base.getType());
//        thirdOperateBasic.setIdCard(base.getId_card());
//        thirdOperateBasic.setAddr(base.getAddress());
//        thirdOperateBasic.setPhone(base.getMobile());
//        thirdOperateBasic.setPhoneRemain(base.getBalance());
//        thirdOperateBasic.setRealName(base.getTruename());
//        thirdOperateBasic.setPackageName(base.getTelPackage());
//        thirdOperateBasic.setAuthentication(base.getCertify());
//        thirdOperateBasic.setUpdateTime(Calendar.getInstance().getTime());
//        thirdOperateBasic.setRegTime(base.getOpen_time());
//        thirdOperateBasic.setOrderId(orderId);
//        thirdOperateBasic.setChannel(channelId);
//    }
//
//    /**
//     * 修改运营商信息
//     *
//     * @param bwOperateBasic
//     * @param base
//     * @param borrowerId
//     */
//    private void modifyOperateBasic(BwOperateBasic bwOperateBasic, Base base, Long borrowerId) {
//        bwOperateBasic.setUserSource(base.getType());
//        bwOperateBasic.setIdCard(base.getId_card());
//        bwOperateBasic.setAddr(base.getAddress());
//        bwOperateBasic.setPhone(base.getMobile());
//        bwOperateBasic.setPhoneRemain(base.getBalance());
//        bwOperateBasic.setRealName(base.getTruename());
//        bwOperateBasic.setPackageName(base.getTelPackage());
//        bwOperateBasic.setAuthentication(base.getCertify());
//        bwOperateBasic.setUpdateTime(new Date());
//        bwOperateBasic.setRegTime(base.getOpen_time());
//        bwOperateBasic.setBorrowerId(borrowerId);
//    }
//
//    /**
//     * 判断是否存在订单号
//     *
//     * @param orderNo
//     */
//    private BwOrder existsOrder(String orderNo) {
//        BwOrderRong bwOrderRong = new BwOrderRong();
//        bwOrderRong.setThirdOrderNo(orderNo);
//        bwOrderRong.setChannelId(Long.valueOf(channelId));
//        bwOrderRong = bwOrderRongMapper.selectOne(bwOrderRong);
//        if (bwOrderRong == null) {
//            throw new GeinihuaException(ResponseInfo.error("第三方订单不存在：" + orderNo));
//        }
//
//        BwOrder bwOrder = bwOrderMapper.selectByPrimaryKey(bwOrderRong.getOrderId());
//        if (bwOrder == null) {
//            throw new GeinihuaException(ResponseInfo.error("错误，我方订单不存在"));
//        }
//        return bwOrder;
//    }
//
//    /**
//     * 支持的银行
//     */
//    public static class Bank {
//        private String bankName;
//        private String bankCode;
//
//        public String getBankName() {
//            return bankName;
//        }
//
//        public void setBankName(String bankName) {
//            this.bankName = bankName;
//        }
//
//        public String getBankCode() {
//            return bankCode;
//        }
//
//        public void setBankCode(String bankCode) {
//            this.bankCode = bankCode;
//        }
//
//        public Bank(String bankName, String bankCode) {
//            this.bankName = bankName;
//            this.bankCode = bankCode;
//        }
//
//        public Bank() {
//        }
//    }
//
//    /**
//     * 支持的银行列表
//     *
//     * @return
//     */
//    public static List<Bank> supportBanks() {
//        List<Bank> banks = new ArrayList<>();
//        Bank bank = new Bank("中国工商银行", "ICBC");
//        banks.add(bank);
//        bank = new Bank("中国农业银行", "ABC");
//        banks.add(bank);
//        bank = new Bank("中国银行", "BOC");
//        banks.add(bank);
//        bank = new Bank("中国建设银行", "CCB");
//        banks.add(bank);
//        bank = new Bank("上海浦东发展银行", "SPDB");
//        banks.add(bank);
//        bank = new Bank("中国邮政储蓄银行", "PSBC");
//        banks.add(bank);
//        bank = new Bank("中国民生银行", "CMBC");
//        banks.add(bank);
//        bank = new Bank("兴业银行", "CIB");
//        banks.add(bank);
//        bank = new Bank("广东发展银行", "GDB");
//        banks.add(bank);
//        bank = new Bank("中信银行", "CITIC");
//        banks.add(bank);
//        bank = new Bank("中国光大银行", "CEB");
//        banks.add(bank);
//        bank = new Bank("华夏银行", "HXB");
//        banks.add(bank);
//        bank = new Bank("平安银行", "PAB");
//        banks.add(bank);
//        return banks;
//    }
//
//    /**
//     * 宝付编码转银行缩写
//     *
//     * @param bankCode
//     * @return
//     */
//    public static String numToCode(String bankCode) {
//        if (StringUtils.isBlank(bankCode)) {
//            throw new GeinihuaException(ResponseInfo.error("绑卡信息错误，编码不存在"));
//        }
//        String res;
//        switch (bankCode) {
//
//            case "0102":
//                res = "ICBC";
//                break;
//            case "0103":
//                res = "ABC";
//                break;
//            case "0104":
//                res = "BOC";
//                break;
//            case "0105":
//                res = "CCB";
//                break;
//            case "0301":
//                res = "BCOM";
//                break;
//            case "0302":
//                res = "CITIC";
//                break;
//            case "0303":
//                res = "CEB";
//                break;
//            case "0304":
//                res = "HXB";
//                break;
//            case "0305":
//                res = "CMBC";
//                break;
//            case "0306":
//                res = "GDB";
//                break;
//            case "0307":
//                res = "PAB";
//                break;
//            case "0308":
//                res = "CMB";
//                break;
//            case "0309":
//                res = "CIB";
//                break;
//            case "0310":
//                res = "SPDB";
//                break;
//            case "0403":
//                res = "PSBC";
//                break;
//            default:
//                throw new GeinihuaException(ResponseInfo.error("绑卡信息错误，错误的编码"));
//        }
//        return res;
//    }
//
//    /**
//     * 银行缩写转宝付
//     *
//     * @param bankCode
//     * @return
//     */
//    public static String codeToNum(String bankCode) {
//        if (StringUtils.isBlank(bankCode)) {
//            throw new GeinihuaException(ResponseInfo.error("绑卡信息错误，编码不存在"));
//        }
//        String res;
//        switch (bankCode) {
//            case "ICBC":
//                res = "0102";
//                break;
//            case "ABC":
//                res = "0103";
//                break;
//            case "BOC":
//                res = "0104";
//                break;
//            case "CCB":
//                res = "0105";
//                break;
//            case "BCOM":
//                res = "0301";
//                break;
//            case "CITIC":
//                res = "0302";
//                break;
//            case "CEB":
//                res = "0303";
//                break;
//            case "HXB":
//                res = "";
//                break;
//            case "CMBC":
//                res = "0305";
//                break;
//            case "GDB":
//                res = "0306";
//                break;
//            case "PAB":
//                res = "0307";
//                break;
//            case "CMB":
//                res = "0308";
//                break;
//            case "CIB":
//                res = "0309";
//                break;
//            case "SPDB":
//                res = "0310";
//                break;
//            case "PSBC":
//                res = "0403";
//                break;
//            default:
//                throw new GeinihuaException(ResponseInfo.error("绑卡信息错误，错误的编码"));
//        }
//        return res;
//    }
//
//}
