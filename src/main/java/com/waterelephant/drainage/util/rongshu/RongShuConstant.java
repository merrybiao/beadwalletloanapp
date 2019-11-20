package com.waterelephant.drainage.util.rongshu;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/***
 * xiaoxingWu
 */
public class RongShuConstant {

    public static  String PRI_KEY="";
    public static  String PUB_KEY="";
    public static  String appId="";
    public static  String DEFAULT_PRIVATE_KEY="";
    public static  String DEFAULT_PUBLIC_KEY="";
    static {
        ResourceBundle rongshu_Bundle=ResourceBundle.getBundle("rongshu");
        if (rongshu_Bundle==null){
            throw new IllegalArgumentException("[rongshu_Bundle.properties] is not found!");
        }
        RongShuConstant.PRI_KEY=rongshu_Bundle.getString("RS_PRI_KEY");
        RongShuConstant.PUB_KEY=rongshu_Bundle.getString("RS_PUB_KEY");
        RongShuConstant.appId=rongshu_Bundle.getString("appId");
        RongShuConstant.DEFAULT_PRIVATE_KEY=rongshu_Bundle.getString("DEFAULT_PRIVATE_KEY");
        RongShuConstant.DEFAULT_PUBLIC_KEY=rongshu_Bundle.getString("DEFAULT_PUBLIC_KEY");
    }

    public static void main(String[] args) {
    	
        System.out.println(RongShuConstant.DEFAULT_PUBLIC_KEY);
        
        
        
    }

}
