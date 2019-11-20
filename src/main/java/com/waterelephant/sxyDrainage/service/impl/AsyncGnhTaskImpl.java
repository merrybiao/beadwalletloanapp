//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.service.IBwIdentityCardService;
//import com.waterelephant.sxyDrainage.entity.geinihua.TemplateInfo;
//import com.waterelephant.sxyDrainage.service.AsyncGnhTask;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.UploadToCssUtils;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//
///**
// * 宜未雨而绸缪，毋临渴而掘井
// *
// * @author xanthuim
// * @since 2018-07-31
// */
//@Service
//public class AsyncGnhTaskImpl implements AsyncGnhTask {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncGnhTaskImpl.class);
//
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//    @Autowired
//    private IBwIdentityCardService bwIdentityCardService;
//
//    @Transactional(rollbackFor = RuntimeException.class)
//    @Async("taskExecutor")
//    @Override
//    public void uploadPicture(TemplateInfo.Identity identity, Long orderId, Long borrowerId,
//                              Integer channelId) {
//        String frontImage = UploadToCssUtils.urlUpload(identity.getIdCardFront(), orderId + "_01");
//        LOGGER.info(">>> 身份证正面 " + frontImage);
//        // 保存身份证正面照
//        thirdCommonService.addOrUpdateBwAdjunct(System.currentTimeMillis(), 1, frontImage, null,
//                orderId, borrowerId, 0);
//
//        // 身份证反面照
//        String backImage = UploadToCssUtils.urlUpload(identity.getIdCardBack(), orderId + "_02");
//        LOGGER.info(">>> 身份证反面 " + backImage);
//        // 保存身份证反面照
//        thirdCommonService.addOrUpdateBwAdjunct(System.currentTimeMillis(), 2, backImage, null,
//                orderId, borrowerId, 0);
//
//        // 手持照
//        String handerImage = UploadToCssUtils.urlUpload(identity.getFaceDetectImageList().get(0),
//                orderId + "_03");
//        LOGGER.info(">>> 手持照/人脸 " + handerImage);
//        // 保存手持照
//        thirdCommonService.addOrUpdateBwAdjunct(System.currentTimeMillis(), 3, handerImage, null,
//                orderId, borrowerId, 0);
//
//
//        LOGGER.info(">>> 处理认证图片 ");
//        // 保存身份证信息
//        BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//        bwIdentityCard.setBorrowerId(borrowerId);
//        bwIdentityCard = bwIdentityCardService.findBwIdentityCardByAttr(bwIdentityCard);
//        BwIdentityCard2 bwIdentityCard_ = new BwIdentityCard2();
//        bwIdentityCard_.setAddress(identity.getAddress());
//        bwIdentityCard_.setGender(identity.getGender());
//        bwIdentityCard_.setIdCardNumber(identity.getIdCardNo());
//        bwIdentityCard_.setName(identity.getName());
//        bwIdentityCard_.setRace(identity.getNation());
//        bwIdentityCard_.setValidDate(identity.getValidDateEnd());
//        bwIdentityCard_.setIssuedBy(identity.getAgency());
//        if (bwIdentityCard == null) {
//            bwIdentityCard_.setBorrowerId(borrowerId);
//            bwIdentityCard_.setCreateTime(new Date());
//            bwIdentityCard_.setUpdateTime(new Date());
//            bwIdentityCardService.saveBwIdentityCard(bwIdentityCard_);
//        } else if (null != bwIdentityCard_) {
//            bwIdentityCard.setAddress(bwIdentityCard_.getAddress());
//            bwIdentityCard.setBirthday(bwIdentityCard_.getBirthday());
//            bwIdentityCard.setGender(bwIdentityCard_.getGender());
//            bwIdentityCard.setIdCardNumber(bwIdentityCard_.getIdCardNumber());
//            bwIdentityCard.setIssuedBy(bwIdentityCard_.getIssuedBy());
//            bwIdentityCard.setName(bwIdentityCard_.getName());
//            bwIdentityCard.setRace(bwIdentityCard_.getRace());
//            bwIdentityCard.setUpdateTime(new Date());
//            bwIdentityCard.setValidDate(bwIdentityCard_.getValidDate());
//            bwIdentityCardService.updateBwIdentityCard(bwIdentityCard);
//        }
//        thirdCommonService.addOrUpdateBwOrderAuth(System.currentTimeMillis(), orderId, 3, channelId);
//    }
//}
