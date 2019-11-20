//package com.waterelephant.sxyDrainage.controller;
//
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.sxyDrainage.entity.Borrower;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.exception.BorrowerException;
//import com.waterelephant.sxyDrainage.utils.AesEcbUtil;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @author xanthuim@gmail.com
// * @since 2018-07-23
// */
//@RequestMapping("/sxyDrainage/user")
//@RestController
//public class BorrowerController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowerController.class);
//
//    @Autowired
//    private BwBorrowerService bwBorrowerService;
//
//
//    @Value("${KEY_YHWX}")
//    private String KEY_YHWX;
//
//    @ExceptionHandler
//    private DrainageRsp exception(Exception e) {
//        DrainageRsp rsp = new DrainageRsp();
//        if (e instanceof BorrowerException) {
//            BorrowerException exception = (BorrowerException) e;
//            rsp.setCode(exception.getCode() + "");
//            rsp.setMessage(exception.getMessage());
//        } else {
//            rsp.setCode("500");
//            rsp.setMessage("系统异常");
//            LOGGER.error("系统异常：", e);
//        }
//        return rsp;
//    }
//
//    @RequestMapping(value = "/check", method = RequestMethod.POST)
//    public DrainageRsp checkUser(@RequestBody Borrower borrower) throws Exception {
//        LOGGER.info("撞库参数：{}", borrower);
//        DrainageRsp rsp = new DrainageRsp();
//        if (StringUtils.isBlank(borrower.getPhone()) || StringUtils.isBlank(borrower.getIdCard())) {
//            throw new BorrowerException("请传入手机号和身份证", -1);
//        }
//
//        String decryptPhone = AesEcbUtil.decrypt(borrower.getPhone(), KEY_YHWX);
//        String decryptIdCard = AesEcbUtil.decrypt(borrower.getIdCard(), KEY_YHWX);
//
//        List<BwBorrower> borrowers = bwBorrowerService.findByPhoneOrIdcard(decryptPhone, decryptIdCard);
//        if (borrowers.size() > 0) {
//            rsp.setCode("0");
//            rsp.setMessage("该用户已存在");
//        } else {
//            rsp.setCode("1");
//            rsp.setMessage("不存在该用户");
//        }
//        return rsp;
//    }
//}
