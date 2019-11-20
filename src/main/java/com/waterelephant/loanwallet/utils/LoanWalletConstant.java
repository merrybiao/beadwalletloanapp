package com.waterelephant.loanwallet.utils;

import java.util.ResourceBundle;

/**
 * 
 * 
 * 
 * Module:
 * 
 * LoanWalletConstant.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <贷款钱包 - 常量>
 */
public class LoanWalletConstant {

	public static String KEY = "";// 秘钥
	public static String CODE = "";
	static {
		ResourceBundle loanWallet_bundle = ResourceBundle.getBundle("com/waterelephant/loanwallet/loanWallet");
		if (loanWallet_bundle == null) {
			throw new IllegalArgumentException("[loanWallet_bundle.properties] is not found!");
		}

		LoanWalletConstant.KEY = loanWallet_bundle.getString("loanWallet.key");
		LoanWalletConstant.CODE = loanWallet_bundle.getString("loanWallet.code");
	}
}
