/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Module:
 * <p>
 * BaofuConstant.java
 *
 * @author 程盼
 * @version 1.0
 * @description: <描述>
 * @since JDK 1.8
 */
public class BaofuConstant {
    private static Map<String, String> bankCodeMap = new HashMap<>();
    private static Map<String, String> baoFuCodeMap = new HashMap<>();

    static {
        bankCodeMap.put("0102", "ICBC");// 工商银行
        bankCodeMap.put("0103", "ABC");// 农业银行
        bankCodeMap.put("0104", "BOC");
        bankCodeMap.put("0105", "CCB");// 建设
        bankCodeMap.put("0301", "BCOM");
        bankCodeMap.put("0302", "CITIC");
        bankCodeMap.put("0303", "CEB");
        bankCodeMap.put("0304", "HXB");
        bankCodeMap.put("0305", "CMBC");
        bankCodeMap.put("0306", "GDB");
        bankCodeMap.put("0307", "PAB");
        bankCodeMap.put("0308", "CMB");
        bankCodeMap.put("0309", "CIB");
        bankCodeMap.put("0310", "SPDB");
        bankCodeMap.put("0401", "SHB");
        bankCodeMap.put("0403", "PSBC");
        for (Map.Entry<String, String> entry : bankCodeMap.entrySet()) {
            baoFuCodeMap.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * 转换富友银行编码为宝付，如：0102->ICBC
     *
     * @param bankcode 富友银行编码
     * @return 对应宝付银行编码
     */
    public static String convertFuiouBankCodeToBaofu(String bankcode) {
        return bankCodeMap.get(bankcode);
    }

    public static String convertBaoFuBankCodeToFuiou(String baofuBankCode) {
        return baoFuCodeMap.get(baofuBankCode);
    }
}
