package com.waterelephant.cashloan.util;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 校验签名工具
 * @author dengyan
 *
 */
public class CheckSign {

	/**
	 * 对data数据进行加密
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static String checkSignKey(String data) throws Exception {
		String sign = null;
        // 将data转换为数组
        String[] dataArray = data.split("&");
        // 将数组按字母的升序进行排列
        Arrays.sort(dataArray, String.CASE_INSENSITIVE_ORDER);
        // 将数组转化为字符串
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i<dataArray.length; i++) {
        	strB.append(dataArray[i] + "&");
        }
        String signData = strB.toString().substring(0, strB.length() - 1);
        // 对字符串进行加密
        sign = MD5Tool(signData);
        return sign.toLowerCase();
	}
	
	/** 
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] 
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程 
     *  
     * @param arrB 
     *            需要转换的byte数组 
     * @return 转换后的字符串 
     * @throws Exception 
     *             本方法不处理任何异常，所有异常全部抛出 
     */  
    private static String MD5Tool(String pwd) throws Exception {
		 //用于加密的字符  
       char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
               'A', 'B', 'C', 'D', 'E', 'F' };

       byte[] bytes = pwd.getBytes();
       // 信息摘要是安全的单向has函数，它接收任意大小的数据，并输出固定长度的哈希值
       MessageDigest messageDigest = MessageDigest.getInstance("MD5");
       // MessageDigest对象通过update处理数据
       messageDigest.update(bytes);
       byte[] mdBytes = messageDigest.digest();
        int j = mdBytes.length;  
        char str[] = new char[j * 2];  
        int k = 0;  
        for (int i = 0; i < j; i++) {   //  i = 0  
            byte byte0 = mdBytes[i];  //95  
            str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5   
            str[k++] = md5String[byte0 & 0xf];   //   F  
        }
        
        return new String(str);
    }  
	
	public static void main(String[] args) throws Exception {
		String data = "sid=asdcas&mobile=15811522567&period=3&realname=张三&scureid=1327918431238481364781&send_time=1498549469&amount=50";
		String jsonStr = "{\"" + data.replace("=", "\":\"").replace("&", "\",\"") + "\"}";
		Map<String, String> map = (Map<String, String>)JSONObject.parse(jsonStr);
		System.out.println(map.get("sid"));
	}
}
