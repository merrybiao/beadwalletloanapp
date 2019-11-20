package com.waterelephant.capital.weishenma;

import java.util.ResourceBundle;



public class WeiShenMaConstant {
	public static String SIGNKEY = "";
	public static int CAPITALID;
	static{
		ResourceBundle weiShenMa_bundle = ResourceBundle.getBundle("weishenma");
		if (weiShenMa_bundle == null) {
			throw new IllegalArgumentException("[weishenma.properties] is not found!");
		}
		WeiShenMaConstant.SIGNKEY = weiShenMa_bundle.getString("signKey");
		WeiShenMaConstant.CAPITALID = Integer.valueOf(weiShenMa_bundle.getString("capitalId"));
	}
}
