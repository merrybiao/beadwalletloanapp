package com.waterelephant.yixin.util;

import java.util.Map;
import com.beadwallet.utils.CommUtils;
import com.google.gson.Gson;

/**
 * 工具类
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月16日 下午2:13:32
 */
public class YiXinCommonUtil {

	
	/**
	 * Map工具类
	 */
	public static class MapUtil{
		
		/**
		 * 获取对应的map的值
		 */
		public static String getKeyByMap(Map<String,Object> map,String key){
			if(CommUtils.isNull(key)){
				return null;
			}
			return map.get(key)==null?"":map.get(key).toString();
		}
	}
	
	/**
	 * 加密工具类
	 * @Description:TODO
	 * @author:yanfuxing
	 * @time:2016年12月16日 下午2:14:35
	 */
	public static class Rc4Util{
		/**
		 * 加密
		 * @return
		 */
		public static <T> String encodeStr(T t,String  rc4key){
			try {
				return RC4_128_V2.encode(new Gson().toJson(t).toString(),  rc4key);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}
	
}
