//package com.waterelephant.sxyDrainage.utils.fenling;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.TreeMap;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.commons.codec.binary.Base64;
//
//import com.google.gson.Gson;
//import com.waterelephant.sxyDrainage.entity.fenling.FenLingUserStatus;
//
//public class CodecUtils {
//
//	private static final String	ALGORITHM_CIPHER_AES	= "AES/ECB/PKCS5Padding";
//	public static final String	ALGORITHM_AES			= "AES";
//	public static final String	CHARSET					= "UTF-8";
//
//
//
//	/**
//	 * AES算法加密数据
//	 * 
//	 * @param data
//	 *            待加密的明文数据
//	 * @param key
//	 *            AES密钥字符串
//	 * @return AES加密后的经过Base64编码的密文字符串,加密过程中遇到异常则抛出RuntimeException
//	 */
//	public static String buildAESEncrypt(String data, String key) {
//		try {
//			// 实例化Cipher对象,它用于完成实际的加密操作
//			Cipher cipher = Cipher.getInstance(ALGORITHM_CIPHER_AES);
//			// 还原密钥,并初始化Cipher对象,设置为加密模式
//			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), ALGORITHM_AES));
//			// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
//			// 将Base64中的URL非法字符如'+','/','='转为其他字符,详见RFC3548
//			return Base64.encodeBase64URLSafeString(cipher.doFinal(data.getBytes(CHARSET)));
//		} catch (Exception e) {
//			throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
//		}
//	}
//
//
//
//	/**
//	 * AES算法解密数据
//	 * 
//	 * @param data
//	 *            待解密的经过Base64编码的密文字符串
//	 * @param key
//	 *            AES密钥字符串
//	 * @return AES解密后的明文字符串,解密过程中遇到异常则抛出RuntimeException
//	 */
//	public static String buildAESDecrypt(String data, String key) {
//		try {
//			Cipher cipher = Cipher.getInstance(ALGORITHM_CIPHER_AES);
//			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), ALGORITHM_AES));
//			return new String(cipher.doFinal(Base64.decodeBase64(data)), CHARSET);
//		} catch (Exception e) {
//			throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
//		}
//	}
//	
//	/**
//	 * MD5加密
//	 * @param str
//	 * @return
//	 */
//	public static String getMD5(String str) {  
//        try {  
//            // 生成一个MD5加密计算摘要  
//            MessageDigest md = MessageDigest.getInstance("MD5");  
//            // 计算md5函数  
//            md.update(str.getBytes());  
//            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
//            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
//            return new BigInteger(1, md.digest()).toString(16);  
//        } catch (Exception e) {  
//           e.printStackTrace();  
//           return null;  
//        }  
//    } 
//	
//	public static void main(String[] args) {
//		CodecUtils.buildAESDecrypt("ir1_IqLLP975-ckHLs3WVD-H1iHOL46I32jfPj4RJzCr-Sm4Wv_jNbWDFtwxhS3w", "qwertyuiopasdfghjklzxc");
//	}
//	
//	public static String sortParam(String jsonData){
//		StringBuffer sb = new StringBuffer();
//		FenLingUserStatus fenLingUser = new Gson().fromJson(jsonData, FenLingUserStatus.class);
//		TreeMap<String, String> treemap = new TreeMap<String, String>();
//		treemap.put("UserId", String.valueOf(fenLingUser.getUserId()));
//		treemap.put("MblNo", fenLingUser.getMblNo());
//		treemap.put("OrderNo", String.valueOf(fenLingUser.getOrderNo()));
//		treemap.put("Status", String.valueOf(fenLingUser.getStatus()));
//		
//		if(null!=treemap){
//		try{
//			Iterator titer=treemap.entrySet().iterator();  
//	        while(titer.hasNext()){  
//	            Map.Entry ent=(Map.Entry)titer.next();  
//	            //String key=ent.getKey().toString();  
//	            String value=ent.getValue().toString();  
//	            sb.append(value);  
//	        }  
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return sb.toString();
//		}
//		return null;
//	} 
//
//	
//}
//
