package com.waterelephant.authentication;

import java.security.KeyFactory;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.waterelephant.utils.Base64Utils;
import com.waterelephant.utils.MD5Util;

public class ApiSignUtils {
	
	public static final String DEFAULT_CHARSET = "utf-8";
	
	public static final String REQUEST_PARAM_APPKEY ="appKey";
	
	public static final String REQUEST_PARAM_TOKEN = "token";
	
	public static final String REQUEST_PARAM_SIGN = "sign";
	
	public static final String REQUEST_PARAM_ENCRYPT = "encrypt";
	
	public static final String REQUEST_PARAM_TIMESTAMP = "timestamp";
	
	public static final long _5_MINS = 5 * 50 * 1000; //请求验签时间不得超过5分钟
	//签名时需要忽略的字段名称
	public static Set<String> ignoreSignSet = new HashSet<>();
	
	static {
		ignoreSignSet.add("key");
		ignoreSignSet.add("imageData");
		ignoreSignSet.add("imgData");
		ignoreSignSet.add("imageBase64");
		ignoreSignSet.add("imgBase64");
		ignoreSignSet.add("appToken");
		ignoreSignSet.add(REQUEST_PARAM_SIGN);
		ignoreSignSet.add(REQUEST_PARAM_TOKEN);
		ignoreSignSet.add(REQUEST_PARAM_ENCRYPT);
		ignoreSignSet.add(REQUEST_PARAM_APPKEY);
		
	}
	
	public static Map<String, String> getRequestParamMap(HttpServletRequest request) {
		Map<String,String> paramsMap = new HashMap<>();
		if (request != null) {
			Enumeration<?> parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String name = String.valueOf(parameterNames.nextElement());
				String value = "";
				if (!StringUtils.isEmpty(name))
					value = request.getParameter(name);
				paramsMap.put(name, value);
			}
		}
		return paramsMap;
	}
	
	/**
     * 按key进行正序排列
     * <功能描述>
     * @param params
     * @return
     */
    private static SortedMap<String, String> getSortMap(Map<String, String> params) {
        SortedMap<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        for (String key: params.keySet()) {
            map.put(key, params.get(key) == null ? null : String.valueOf(params.get(key)));
        }
        return map;
    }
    
    //定义签名，根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
    public static String createSign(Map<String,String>  paramsMap,String signKey){
    	SortedMap<String, String>  sortedMap= getSortMap(paramsMap);
        StringBuffer sb = new StringBuffer();
        Set<Entry<String, String>>  es = sortedMap.entrySet();
        Iterator<Entry<String, String>> it = es.iterator();
        while(it.hasNext()) {
        	Entry<String, String> entry = it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            //参数值为空或空字符串 忽略
            if(null == v || "".equals(v)) continue;
            //参数名在set集合中 忽略
            if(ignoreSignSet.contains(k)) continue;
            //拼接
            sb.append(k + "=" + v + "&");
        }
        if(!StringUtils.isEmpty(signKey))
        	sb.append("signKey" + "=" + signKey);
        String str = sb.toString();
        String sign = MD5Util.getMd5Value(str);
        return sign;
    }
    
    /**
	 * 私钥签名 <功能描述>
	 * @param src
	 * @param priKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] generateSHA1withRSASigature(String src, String priKey, String charset) throws Exception {
		Signature sigEng = Signature.getInstance("SHA1withRSA");
		byte[] pribyte = Base64Utils.decode(priKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
		KeyFactory fac = KeyFactory.getInstance("RSA");
		RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
		sigEng.initSign(privateKey);
		sigEng.update(src.getBytes(charset));
		byte[] signature = sigEng.sign();
		return signature;
	}

}
