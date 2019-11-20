package com.waterelephant.register.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.waterelephant.utils.CommUtils;

/**
 * RSA签名公共类
 * @author kristain
 */
public class RSAUtil{

    private static RSAUtil instance;

    private RSAUtil()
    {

    }

    public static RSAUtil getInstance()
    {
        if (null == instance)
            instance = new RSAUtil();
        return instance;
    }

    /**
     * 签名处理
     * @param prikeyvalue：私钥文件
     * @param sign_str：签名源内容
     * @return
     */
    public static String sign(String prikeyvalue, String sign_str)
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature
                    .getInstance("MD5withRSA");
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes("UTF-8"));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(Base64.encode(signed));
        } catch (java.lang.Exception e)
        {
        }
        return null;
    }

    /**
     * 签名验证
     * @param pubkeyvalue：公钥
     * @param oid_str：源串
     * @param signed_str：签名结果串
     * @return
     */
    public static boolean checksign(String pubkeyvalue, String oid_str,
            String signed_str)
    {
        try
        {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
                    Base64.decode(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            byte[] signed = Base64.decode(signed_str);// 这是SignatureData输出的数字签名
            java.security.Signature signetcheck = java.security.Signature
                    .getInstance("MD5withRSA");
            signetcheck.initVerify(pubKey);
            signetcheck.update(oid_str.getBytes("UTF-8"));
            return signetcheck.verify(signed);
        } catch (java.lang.Exception e)
        {
        }
        return false;
    }
    
    public static String sortParams(Map<String,String> map){
    	StringBuffer sign = new StringBuffer("");
    	List<String> list = new ArrayList<>(map.keySet());
    	Collections.sort(list);
    	Iterator<String> iterator = list.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			if(!CommUtils.isNull(map.get(key))&&!"null".equals(map.get(key))){
				sign.append(key).append("=").append(map.get(key)).append("&");
			}
		}
    	return sign.substring(0,sign.length()-1);
    }
}
