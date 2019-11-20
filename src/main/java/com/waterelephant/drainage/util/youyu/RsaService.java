package com.waterelephant.drainage.util.youyu;

public interface RsaService {

    String PKCS1="PKCS1";
    String PKCS8="PKCS8";

    void init(String privateKey, String publicKey,String paddingmode);//初始化

    String encrypt(String plain_data);//加密

    String decrypt(String encry_data);//解密

    boolean verifySign(String sign,String param);//校验签名

    String generateSign(String params);//生成签名

    String generateSignWithPrivateKey(String params,String privateKey);//签名私钥生成


}
