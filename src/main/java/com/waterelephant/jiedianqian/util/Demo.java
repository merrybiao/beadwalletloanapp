package com.waterelephant.jiedianqian.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/4/7.
 */
public class Demo {
    //合作方1024位公钥
    public static String partnerPublicKey = "";
    //合作方1024位私钥
    public static String partnerPrivateKey = "";

    //借点钱1024位公钥
    public static String jdqPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjDj8W5/2pQGgJR2vKrXcmBU9ynsnwpWE0PUni4nChr7auKQYI8gmIzjDCgDiR7qANxF0nkoTqmb6to/LoXnhO4ofN8178sKVUYMKmy5k9qrM+N3IBMEFCm7YUWbTxqzfv5WbeE6wsNsv5DAtHYekWot9gOgKzJmrxzFlC/pUc6wIDAQAB";
    //借点钱1024位私钥
    public static String jdqPrivateKey = "";

    /**
     * 借点钱调用合作方接口时，按此方式加密加签
     * @param param
     * @return 
     */
    public static String jdqEncode(String param){
        String data = RSAUtil.buildRSAEncryptByPublicKey(param,partnerPublicKey);
        String sign = RSAUtil.buildRSASignByPrivateKey(param, jdqPrivateKey);
        JSONObject paramJson=new JSONObject();
        paramJson.put("data", data);
        paramJson.put("sign", sign);
        return paramJson.toJSONString();
    }


    /**
     * 合作方获取借点钱的加密参数后，按此方式解密验签
     * @param paramJson
     * @return
     */
    public static String partnerDecode(JSONObject paramJson){
        String data = RSAUtil.buildRSADecryptByPrivateKey(paramJson.getString("data"), partnerPrivateKey);		//解密
        boolean flag = RSAUtil.buildRSAverifyByPublicKey(data, jdqPublicKey, paramJson.getString("sign"));		//验签
        if(!flag){
            System.out.println("验签失败");
        }
        return data;
    }


    /**
     * 合作方推送数据至借点钱时，按此方式加密加签
     * @param param
     * @return
     */
    public static String partnerEncode(String param){
        String data = null;
        try {
            data = RSA.encryptByPublicKey(param, jdqPublicKey,"utf-8");			//数据加密
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密失败");
        }
        String sign = RSAUtil.buildRSASignByPrivateKey(param, partnerPrivateKey);	// 加签	
        JSONObject paramJson=new JSONObject();
        paramJson.put("data", data);
        paramJson.put("sign", sign);
        return paramJson.toJSONString();
    }

    /**
     * 借点钱压缩运营商数据
     * @param operator
     * @return
     */
    public static String zipOperator(String operator){
        return Base64.encode(GzipUtil.compress(operator,"utf-8"));
    }

    /**
     * 合作方解压运营商数据
     * @param zipOperator
     * @return
     */
    public static String unzipOperator(String zipOperator){
        return GzipUtil.uncompress(Base64.decode(zipOperator),"utf-8");
    }
    
    public static void main(String[] args) throws Exception {
    	String data = RSA.encryptByPublicKey("123456", JieDianQianContext.get("pubKey"),"utf-8"); // 加密
    	System.out.println("加密数据："+data);
    	String desData = RSAUtil.buildRSADecryptByPrivateKey(data, JieDianQianContext.get("priKey"));	//解密
    	System.out.println("解密数据:"+desData);
    	
    	
	}
}
