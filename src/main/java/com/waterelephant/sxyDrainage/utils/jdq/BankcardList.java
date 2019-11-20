//package com.waterelephant.sxyDrainage.utils.jdq;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author xanthuim@gmail.com
// * @since 2018-07-24
// */
//
//public final class BankcardList {
//
//    static class Bank {
//        @JsonProperty("bank_name")
//        private String bankName;
//        @JsonProperty("bank_code")
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
//     * @param cardType 1:借记卡、2:信用卡
//     * @return
//     */
//    public static List<Bank> banks(String cardType) {
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
//    public static String convertToBaoFuCode(String bankcode) {
//        if (StringUtils.isBlank(bankcode)) {
//            return null;
//        }
//        String res = null;
//        switch (bankcode) {
//
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
//                res = "0304";
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
//                break;
//        }
//        return res;
//    }
//}
