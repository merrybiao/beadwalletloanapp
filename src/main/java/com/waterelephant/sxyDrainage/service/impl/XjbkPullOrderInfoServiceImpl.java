//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.*;
//import com.waterelephant.service.*;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.*;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.UserInfo;
//import com.waterelephant.sxyDrainage.service.AsyncXjbkTask;
//import com.waterelephant.sxyDrainage.service.XjbkPullOrderInfoService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import com.waterelephant.sxyDrainage.utils.xianJinBaiKa.XianJinBaiKaConstant;
//import com.waterelephant.sxyDrainage.utils.xianJinBaiKa.XianJinBaiKaUtil;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.JsonUtils;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
///**
// * 现金白卡
// * <p>
// * Module: (code:xjbk)
// * <p>
// * XjbkPullOrderInfoServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//@Service
//public class XjbkPullOrderInfoServiceImpl implements XjbkPullOrderInfoService {
//    private Logger logger = Logger.getLogger(XjbkPullOrderInfoServiceImpl.class);
//    private String channelIdStr = XianJinBaiKaConstant.CHANNELID;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private BwOrderRongService bwOrderRongService;
//    @Autowired
//    private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//    @Autowired
//    private AsyncXjbkTask asyncXjbkTask;
//    @Autowired
//    private IBwBorrowerService iBwBorrowerService;
//    @Autowired
//    private BwXjbkDeviceInfoService bwXjbkDeviceInfoService;
//    @Autowired
//    private BwContactListService bwContactListService;
//    @Autowired
//    private IBwWorkInfoService bwWorkInfoService;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//    @Autowired
//    private BwOrderProcessRecordService bwOrderProcessRecordService;
//    @Autowired
//    private IBwAdjunctService bwAdjunctService;
//
//    @Override
//    public XianJinBaiKaResponse savePushUserBaseInfo(long sessionId, XianJinBaiKaCommonRequest
//        xianJinBaiKaCommonRequest) {
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        //logger.info(sessionId + ":开始savePushUserBaseInfo method：" + JSON.toJSONString(xianJinBaiKaCommonRequest));
//        String thirdOrderNo = null;
//        try {
//            OrderInfo orderInfo = xianJinBaiKaCommonRequest.getOrder_info();
//            UserInfo userInfo = xianJinBaiKaCommonRequest.getUser_info();
//            UserVerify userVerify = xianJinBaiKaCommonRequest.getUser_verify();
//            if (null == orderInfo || null == userInfo || null == userVerify) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("请求参数为空！");
//                logger.info(sessionId + "：结束savePushUserBaseInfo method：" + JSON.toJSONString(xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            thirdOrderNo = orderInfo.getOrder_sn();
//            String name = userInfo.getUser_name();
//            String idCard = userInfo.getUser_idcard();
//            String phone = userInfo.getUser_phone();
//            if (StringUtils.isBlank(thirdOrderNo) || StringUtils.isBlank(name) ||
//                StringUtils.isBlank(idCard) || StringUtils.isBlank(phone)) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("请求参数为空！");
//                logger.info(sessionId + "：结束savePushUserBaseInfo method：" + JSON.toJSONString(xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            logger.info(sessionId + "OrderInfo=" + JSON.toJSONString(orderInfo) + ";UserInfo=" + JSON.toJSONString
//                (userInfo));
//
//            // 借款金额 单位（分）
//            String loanAmountStr = orderInfo.getLoan_amount();
//            Double loanAmount = NumberUtils.toDouble(loanAmountStr) / 100D;
//
//            int channelId = NumberUtils.toInt(channelIdStr);
//
//            IdCardInfos idcardInfos = userVerify.getIdcard_info();
//            IdCardInfo idcardInfo = idcardInfos.getIdcard_info();
//
//            String frontFile = idcardInfo.getId_number_z_picture();
//            String backFile = idcardInfo.getId_number_f_picture();
//            String natureFile = idcardInfo.getFace_recognition_picture();
//            if (StringUtils.isBlank(frontFile) || StringUtils.isBlank(backFile) || StringUtils.isBlank(natureFile)) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("请求参数为空！");
//                logger.info(sessionId + "：结束savePushUserBaseInfo method：" + JSON.toJSONString(xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//            logger.info(sessionId + "IdCardInfo=" + JSON.toJSONString(idcardInfo));
//
//            // 判断用户是否有多个账号，及是否有进行中的订单
//            boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//            if (flag) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("存在进行中的订单，请勿重复推送");
//                logger.info(sessionId + "：结束savePushUserBaseInfo method：" + JSON.toJSONString(xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            // 新增或更新借款人
//            BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, name, idCard, phone, channelId);
//            long borrowerId = borrower.getId();
//            logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//
//            // 判断是否有草稿状态的订单
//            BwOrder bwOrder = new BwOrder();
//            bwOrder.setBorrowerId(borrowerId);
//            bwOrder.setStatusId(1L);
//            bwOrder.setProductType(2);
//            bwOrder.setChannel(channelId);
//            List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//            if (boList != null && boList.size() > 0) {
//                bwOrder = boList.get(0);
//
//                if (bwOrder.getOrderNo() == null) {
//                    bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//                }
//                bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//                bwOrder.setApplyPayStatus(0);
//                bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwOrder.setProductId(7);
//                bwOrder.setRepayType(2);
//                bwOrder.setExpectMoney(loanAmount);
//                bwOrder.setExpectNumber(4);
//
//                bwOrderService.updateBwOrder(bwOrder);
//            } else {
//                bwOrder = new BwOrder();
//                bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//                bwOrder.setBorrowerId(borrowerId);
//                bwOrder.setStatusId(1L);
//                bwOrder.setCreateTime(Calendar.getInstance().getTime());
//                bwOrder.setChannel(channelId);
//                bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//                bwOrder.setApplyPayStatus(0);
//                bwOrder.setProductId(7);
//                bwOrder.setProductType(2);
//                bwOrder.setRepayType(2);
//                bwOrder.setExpectMoney(loanAmount);
//                bwOrder.setExpectNumber(4);
//
//                bwOrderService.addBwOrder(bwOrder);
//            }
//            long orderId = bwOrder.getId();
//            logger.info(sessionId + ">>> 新增/更新订单ID = " + orderId);
//
//            //用于获取征信数据
//            try {
//                String key = "phone_apply";
//                Map<String, Object> params = new HashMap<>(16);
//                params.put("mobile", phone);
//                params.put("order_id", orderId);
//                params.put("borrower_id", borrowerId);
//                String value = JSON.toJSONString(params);
//                RedisUtils.rpush(key, value);
//
//                logger.info(sessionId + ">>> 获取征信数据：" + JSON.toJSONString(params));
//            } catch (Exception e) {
//                logger.info(sessionId + ">>> 获取征信数据异常：", e);
//            }
//
//            // 判断是否有三方订单
//            BwOrderRong bwOrderRong = new BwOrderRong();
//            bwOrderRong.setOrderId(orderId);
//            bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//            if (bwOrderRong == null) {
//                bwOrderRong = new BwOrderRong();
//                bwOrderRong.setOrderId(orderId);
//                bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                bwOrderRong.setChannelId(Long.valueOf(channelIdStr));
//                bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//                bwOrderRongService.save(bwOrderRong);
//            } else {
//                bwOrderRong.setChannelId(Long.valueOf(channelIdStr));
//                bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                bwOrderRongService.update(bwOrderRong);
//            }
//            logger.info(sessionId + ">>> 新增/更新三方订单");
//
//            // 判断是否有商户订单信息
//            BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrder = bwMerchantOrderServiceImpl.selectByAtt(bwMerchantOrder);
//            if (bwMerchantOrder == null) {
//                bwMerchantOrder = new BwMerchantOrder();
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setCreateTime(Calendar.getInstance().getTime());
//                bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrderServiceImpl.insertByAtt(bwMerchantOrder);
//            } else {
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwMerchantOrderServiceImpl.updateByAtt(bwMerchantOrder);
//            }
//            logger.info(sessionId + ">>> 新增/更新商户订单表");
//
//            //异步处理运营商数据
//            asyncXjbkTask.saveOperatorData(sessionId, userVerify, userInfo, bwOrder, borrower, thirdOrderNo);
//
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//            xianJinBaiKaResponse.setMessage("success");
//            xianJinBaiKaResponse.setResponse(true);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行savePushUserBaseInfo method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage(e.getMessage());
//        } finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(channelIdStr, thirdOrderNo, xianJinBaiKaResponse.getStatus() + "",
//                xianJinBaiKaResponse.getMessage(), "pushUserBaseInfo");
//        }
//        logger.info(sessionId + "：结束savePushUserBaseInfo method：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    @Override
//    public XianJinBaiKaResponse savePushUserAdditionalInfo(long sessionId, XianJinBaiKaCommonRequest
//        xianJinBaiKaCommonRequest) {
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        //logger.info(sessionId + ":开始savePushUserAdditionalInfo method：" + JSON.toJSONString
//        // (xianJinBaiKaCommonRequest));
//        String thirdOrderNo = null;
//        try {
//            OrderInfo orderInfo = xianJinBaiKaCommonRequest.getOrder_info();
//            UserAdditional userAdditional = xianJinBaiKaCommonRequest.getUser_additional();
//            if (null == orderInfo || null == userAdditional) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("请求参数为空！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            thirdOrderNo = orderInfo.getOrder_sn();
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("第三方订单号为空！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//            logger.info(sessionId + "thirdOrderNo=" + thirdOrderNo);
//
//            ContactInfo contactInfo = userAdditional.getContact_info();
//            Email emailVo = userAdditional.getEmail();
//            Qq qqVo = userAdditional.getQq();
//            Wechat wechatVo = userAdditional.getWechat();
//            if (contactInfo == null || emailVo == null || qqVo == null || wechatVo == null) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("参数为空！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            String firstName = contactInfo.getName();
//            String firstPhone = contactInfo.getMobile();
//            String secontName = contactInfo.getName_spare();
//            String secondPhone = contactInfo.getMobile_spare();
//            String threeName = contactInfo.getName_other_one();
//            String threePhone = contactInfo.getMobile_other_one();
//            String fourName = contactInfo.getName_other_two();
//            String fourPhone = contactInfo.getMobile_other_two();
//            String fireName = contactInfo.getName_other_three();
//            String firePhone = contactInfo.getMobile_other_three();
//            if (firstName == null || firstPhone == null || secontName == null || secondPhone == null || threeName ==
//                null ||
//                threePhone == null || fourName == null || fourPhone == null || fireName == null || firePhone == null) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("联系人信息不全！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (bwOrderRong == null) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("第三方订单不存在！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//            // 如果当前订单不存在表示订单基本信息未推送
//            if (CommUtils.isNull(bwOrder)) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("订单不存在！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            Long orderId = bwOrder.getId();
//            Long borrowerId = bwOrder.getBorrowerId();
//
//            // 查询是否有进行中的订单
//            long count = bwOrderService.findProOrder(borrowerId + "");
//            logger.info(sessionId + ">>> 进行中的订单校验：" + count);
//            if (count > 0) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("存在进行中的订单，请勿重复推送！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            BwBorrower bwBorrower = new BwBorrower();
//            bwBorrower.setId(borrowerId);
//            bwBorrower = iBwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//            if (CommUtils.isNull(bwBorrower)) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("用户不存在！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            BwAdjunct bwAdjunct = new BwAdjunct();
//            bwAdjunct.setOrderId(orderId);
//            bwAdjunct.setBorrowerId(borrowerId);
//            bwAdjunct.setAdjunctType(3);
//            bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
//            if (CommUtils.isNull(bwAdjunct)) {
//                xianJinBaiKaResponse.setStatus(500);
//                xianJinBaiKaResponse.setMessage("基本信息未存储完成，请稍后提交！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            DeviceInfo deviceInfo = userAdditional.getDevice_info();
//            if (CommUtils.isNull(deviceInfo)) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("设备信息为空！");
//                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//                    (xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//            BwXjbkDeviceInfo bwXjbkDeviceInfo = new BwXjbkDeviceInfo();
//            bwXjbkDeviceInfo.setOrderId(orderId);
//            bwXjbkDeviceInfo = bwXjbkDeviceInfoService.findByAttr(bwXjbkDeviceInfo);
//            if (CommUtils.isNull(bwXjbkDeviceInfo)) {
//                bwXjbkDeviceInfo = new BwXjbkDeviceInfo();
//                bwXjbkDeviceInfo.setOrderId(bwOrder.getId());
//                bwXjbkDeviceInfo.setAndroidId(deviceInfo.getAndroid_id());
//                bwXjbkDeviceInfo.setAppMarket(deviceInfo.getApp_market());
//                bwXjbkDeviceInfo.setBettary(deviceInfo.getBettary());
//                bwXjbkDeviceInfo.setCarrier(deviceInfo.getCarrier());
//                bwXjbkDeviceInfo.setCreateTime(new Date());
//                bwXjbkDeviceInfo.setDeviceId(deviceInfo.getDevice_id());
//                bwXjbkDeviceInfo.setDeviceInfo(deviceInfo.getDevice_info());
//                String dns = deviceInfo.getDns();
//                if (dns != null && dns.length() > 50) {
//                    dns = dns.substring(0, 45);
//                }
//                bwXjbkDeviceInfo.setDns(dns);
//                bwXjbkDeviceInfo.setGpsAddress(deviceInfo.getGps_address());
//                bwXjbkDeviceInfo.setGpsLatitude(deviceInfo.getGps_latitude());
//                bwXjbkDeviceInfo.setGpsLongitude(deviceInfo.getGps_longitude());
//                bwXjbkDeviceInfo.setIdfa(deviceInfo.getIdfa());
//                bwXjbkDeviceInfo.setIdfv(deviceInfo.getIdfv());
//                bwXjbkDeviceInfo.setImsi(deviceInfo.getImsi());
//                bwXjbkDeviceInfo.setIp(deviceInfo.getIp());
//                bwXjbkDeviceInfo.setIsRoot(deviceInfo.getIs_root());
//                bwXjbkDeviceInfo.setIsSimulator(deviceInfo.getIs_simulator());
//                bwXjbkDeviceInfo.setLastLoginTime(deviceInfo.getLast_login_time());
//                bwXjbkDeviceInfo.setMac(deviceInfo.getMac());
//                bwXjbkDeviceInfo.setMemory(deviceInfo.getMemory());
//                bwXjbkDeviceInfo.setOsType(deviceInfo.getOs_type());
//                bwXjbkDeviceInfo.setOsVersion(deviceInfo.getOs_version());
//                bwXjbkDeviceInfo.setPicCount(deviceInfo.getPic_count());
//                bwXjbkDeviceInfo.setSdcard(deviceInfo.getSdcard());
//                bwXjbkDeviceInfo.setStorage(deviceInfo.getStorage());
//                bwXjbkDeviceInfo.setTeleNum(deviceInfo.getTele_num());
//                bwXjbkDeviceInfo.setUdid(deviceInfo.getUdid());
//                bwXjbkDeviceInfo.setUnuseSdcard(deviceInfo.getUnuse_sdcard());
//                bwXjbkDeviceInfo.setUnuseStorage(deviceInfo.getUnuse_storage());
//                bwXjbkDeviceInfo.setUuid(deviceInfo.getUuid());
//                bwXjbkDeviceInfo.setWifi(deviceInfo.getWifi());
//                bwXjbkDeviceInfo.setWifiName(deviceInfo.getWifi_name());
//
//                bwXjbkDeviceInfoService.saveByAttr(bwXjbkDeviceInfo);
//            }
//            logger.info(sessionId + ">>> 处理设备信息 ");
//
//            //通讯录
//            AddressBook addressBook = userAdditional.getAddress_book();
//            List<BwContactList> listConS = new ArrayList<>(16);
//            if (null != addressBook) {
//                List<String> phoneList = addressBook.getPhone_list();
//                if (CollectionUtils.isNotEmpty(phoneList)) {
//                    for (String ph : phoneList) {
//                        if (StringUtils.isNotBlank(ph)) {
//                            String[] phoneArr = ph.split("_");
//                            if (phoneArr.length == 2) {
//                                try {
//                                    if (phoneArr[0].length() > 300) {
//                                        logger.info(sessionId + ":姓名长度超长：" + phoneArr[0]);
//                                        continue;
//                                    }
//                                    if (phoneArr[1].length() > 300) {
//                                        logger.info(sessionId + ":号码长度超长：" + phoneArr[1]);
//                                        continue;
//                                    }
//                                    BwContactList bwContactList;
//                                    bwContactList = new BwContactList();
//                                    bwContactList.setName(CommUtils.filterEmoji(phoneArr[0]));
//                                    bwContactList.setPhone(phoneArr[1]);
//                                    bwContactList.setBorrowerId(borrowerId);
//                                    listConS.add(bwContactList);
//                                } catch (Exception e) {
//                                    logger.warn("解析记录失败，忽略该条记录" + e.getMessage());
//                                }
//                            }
//                        }
//                    }
//                }
//                bwContactListService.batchAddContact(listConS, bwBorrower.getId());
//            }
//            logger.info(sessionId + ">>> 处理通讯录信息 ");
//
//            // 亲属联系人
//            BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//            if (bwPersonInfo == null) {
//                bwPersonInfo = new BwPersonInfo();
//                bwPersonInfo.setOrderId(orderId);
//                bwPersonInfo.setEmail(emailVo.getEmail());
//                bwPersonInfo.setQqchat(qqVo.getQq());
//                bwPersonInfo.setWechat(wechatVo.getWechat());
//                bwPersonInfo.setCreateTime(new Date());
//                bwPersonInfo.setUpdateTime(new Date());
//                bwPersonInfo.setRelationName(firstName);
//                bwPersonInfo.setRelationPhone(firstPhone);
//                bwPersonInfo.setUnrelationName(secontName);
//                bwPersonInfo.setUnrelationPhone(secondPhone);
//                bwPersonInfo.setColleagueName(threeName.length() > 30 ? threeName.substring(0, 30) : threeName);
//                bwPersonInfo.setColleaguePhone(threePhone.length() > 30 ? threePhone.substring(0, 30) : threePhone);
//                bwPersonInfo.setFriend1Name(fourName.length() > 30 ? fourName.substring(0, 30) : fourName);
//                bwPersonInfo.setFriend1Phone(fourPhone.length() > 30 ? fourPhone.substring(0, 30) : fourPhone);
//                bwPersonInfo.setFriend2Name(fireName.length() > 30 ? fireName.substring(0, 30) : fireName);
//                bwPersonInfo.setFriend2Phone(firePhone.length() > 30 ? firePhone.substring(0, 30) : firePhone);
//                bwPersonInfoService.add(bwPersonInfo);
//            }
//            logger.info(sessionId + ">>> 新增/更新个人信息");
//
//            ProfessionType professionType = userAdditional.getProfession_type();
//            String type = professionType.getProfession_type();
//            //            if ("4".equals(type) || "5".equals(type)) {
//            //                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            //                xianJinBaiKaResponse.setMessage("学生和自由职业无工作信息，不可贷！");
//            //                logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString
//            // (xianJinBaiKaResponse));
//            //                return xianJinBaiKaResponse;
//            //            }
//            String comName = "", workYears = "", income = "", industry = "";
//            // 职业-上班族
//            if ("1".equals(type)) {
//                WorkOfficeInfo workOfficeInfo = userAdditional.getWork_office_info();
//                if (null != workOfficeInfo) {
//                    comName = workOfficeInfo.getCompany_name();
//                    workYears = XianJinBaiKaUtil.getWorkAge(workOfficeInfo.getWork_age());
//                    income = workOfficeInfo.getRevenue();
//
//                }
//                // 职业-企业主
//            } else if ("2".equals(type)) {
//                WorkEnterpriseInfo workEnterpriseInfo = userAdditional.getWork_enterprise_info();
//                if (null != workEnterpriseInfo) {
//                    comName = workEnterpriseInfo.getOwn_company_name();
//                    industry = workEnterpriseInfo.getOwn_industry();
//                    workYears = XianJinBaiKaUtil.getWorkAge(workEnterpriseInfo.getOwn_manage_life_time());
//                    income = workEnterpriseInfo.getOwn_total_revenue();
//                }
//                // 职业-个体户
//            } else if ("3".equals(type)) {
//                WorkSoleInfo workSoleInfo = userAdditional.getWork_sole_info();
//                if (null != workSoleInfo) {
//                    industry = workSoleInfo.getSole_industry();
//                    workYears = XianJinBaiKaUtil.getWorkAge(workSoleInfo.getSole_manage_life_time());
//                    income = workSoleInfo.getSole_total_revenue();
//                }
//            }
//            BusinessType bType = userAdditional.getBusiness_type();
//            if (bType != null) {
//                industry = XianJinBaiKaUtil.getWorkType(bType.getBusinessType());
//            }
//
//            // 判断是否有工作信息
//            BwWorkInfo bwWorkInfo = new BwWorkInfo();
//            bwWorkInfo.setOrderId(orderId);
//            bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//            if (CommUtils.isNull(bwWorkInfo)) {
//                bwWorkInfo = new BwWorkInfo();
//                bwWorkInfo.setOrderId(orderId);
//                bwWorkInfo.setCallTime("10:00 - 12:00");
//                bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//                bwWorkInfo.setWorkYears(workYears);
//                bwWorkInfo.setComName(comName);
//                bwWorkInfo.setIncome(income);
//                bwWorkInfo.setIndustry(industry);
//                bwWorkInfoService.save(bwWorkInfo, borrowerId);
//            }
//            logger.info(sessionId + ">>> 新增/更新工作信息");
//
//            // 插入个人认证记录
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, Integer.parseInt(channelIdStr));
//
//            bwOrder.setStatusId(2L);
//            bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//            bwOrderService.updateBwOrder(bwOrder);
//
//            // 放入redis
//            SystemAuditDto systemAuditDto = new SystemAuditDto();
//            systemAuditDto.setIncludeAddressBook(0);
//            systemAuditDto.setOrderId(bwOrder.getId());
//            systemAuditDto.setBorrowerId(bwBorrower.getId());
//            systemAuditDto.setName(bwBorrower.getName());
//            systemAuditDto.setPhone(bwBorrower.getPhone());
//            systemAuditDto.setIdCard(bwBorrower.getIdCard());
//            systemAuditDto.setChannel(Integer.valueOf(channelIdStr));
//            systemAuditDto.setThirdOrderId(thirdOrderNo);
//            systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//            RedisUtils.hset(SystemConstant.AUDIT_KEY, bwOrder.getId() + "", JsonUtils.toJson(systemAuditDto));
//            logger.info(sessionId + ">>> 修改订单状态，并放入redis");
//
//            // 更改订单进行时间
//            BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//            bwOrderProcessRecord.setSubmitTime(new Date());
//            bwOrderProcessRecord.setOrderId(bwOrder.getId());
//            bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//            logger.info(sessionId + ">>> 更改订单进行时间");
//
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_SUCCESS);
//            xianJinBaiKaResponse.setMessage("success");
//            xianJinBaiKaResponse.setResponse(true);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行savePushUserAdditionalInfo method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage(e.getMessage());
//        } finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(channelIdStr, thirdOrderNo, xianJinBaiKaResponse.getStatus() + "",
//                xianJinBaiKaResponse.getMessage(), "pushUserAdditionalInfo");
//        }
//        logger.info(sessionId + "：结束savePushUserAdditionalInfo method：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//}
