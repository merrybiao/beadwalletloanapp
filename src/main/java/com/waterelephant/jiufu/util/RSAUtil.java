package com.waterelephant.jiufu.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 玖富RSA加密解密工具类
 * @author Administrator
 *
 */
public class RSAUtil {
	/**
	 * 公钥还原：
	 * 1: 对公钥字符串Base64解密得到byte数组
	 * 2：把公钥的byte数组放入规范
	 * 3：实例化KeyFactory，
	 * 4：从KeyFactory中获得公钥
	 * @param public_key
	 * @return PublicKey
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String public_key) throws Exception{  
		try {  
			BASE64Decoder base64Decoder= new BASE64Decoder();  
			byte[] buffer= base64Decoder.decodeBuffer(public_key);
			X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);  
			
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
			PublicKey publicKey = keyFactory.generatePublic(keySpec);  

			return publicKey;  
		} catch (NoSuchAlgorithmException e) {  
			throw new Exception("无此算法");  
		} catch (InvalidKeySpecException e) {  
			throw new Exception("公钥非法");  
		} catch (IOException e) {  
			throw new Exception("公钥数据内容读取错误");  
		} catch (NullPointerException e) {  
			throw new Exception("公钥数据为空");  
		}
	}  

	/**
	 * RSA加密
	 * @param publicKey 通过getPublicKey公钥还原方法获得
	 * @param mobilebyte mobile.getBytes()获得
	 * @return base64String 手机号密文
	 * @throws Exception
	 */
	public static String encrypt(PublicKey publicKey, byte[] mobilebyte) throws Exception{ 
	    String base64String=null;
        if(publicKey== null){  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
            cipher= Cipher.getInstance("RSA"); 
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output= cipher.doFinal(mobilebyte);  
            base64String = new BASE64Encoder().encodeBuffer(output);
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        }catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        } 
        return base64String;//为最终加密的手机号  		
    }
	
	/**
	 * 私钥还原：
	 * 1: 对私钥字符串Base64解密得到byte数组
	 * 2：把私钥的byte数组放入规范
	 * 3：实例化KeyFactory，
	 * 4：从KeyFactory中获得私钥
	 * @param private_key
	 * @return PrivateKey
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String private_key) throws Exception{  
		try {  
			BASE64Decoder base64Decoder= new BASE64Decoder();  
			byte[] buffer= base64Decoder.decodeBuffer(private_key);
			PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);  
			
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  

			return privateKey;  
		} catch (NoSuchAlgorithmException e) {  
			throw new Exception("无此算法");  
		} catch (InvalidKeySpecException e) {  
			throw new Exception("私钥非法");  
		} catch (IOException e) {  
			throw new Exception("私钥数据内容读取错误");  
		} catch (NullPointerException e) {  
			throw new Exception("私钥数据为空");  
		}
	}
	
	/**
	 * 解密：
	 * 1：获得要解密明文的byte数组
	 * 2：实例化Cipher
	 * 3：初始化Cipher
	 * 4：调Cipher的doFinal方法(明文的byte数组作为参数),获得一byte数组
	 * 5：对上一步的byte数组进行Base64解密得到内容
	 * @param privateKey
	 * @param mobile 手机号密文
	 * @return base64String 手机号明文
	 * @throws Exception
	 */
	public static String decrypt(PrivateKey privateKey, String  mobile) throws Exception{ 
	
	byte[] buffer=new BASE64Decoder().decodeBuffer(mobile);
	
	    String base64String=null;
        if(privateKey== null){  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
            cipher= Cipher.getInstance("RSA"); 
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output= cipher.doFinal(buffer);  
           base64String = new String(output);
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        }catch (InvalidKeyException e) {  
            throw new Exception("加密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        } 
        return base64String; //最后获得的手机号 		
    }
	//测试手机号码加密解密
	public static void main(String[] args) {
		String privatekey = JiufuConstant.KEY_MAP.get("private_key");
		String publickey = JiufuConstant.KEY_MAP.get("public_key");
		try {
			String phone = "007";
			byte[] mobilebyte = phone.getBytes();
			PrivateKey privateKey = RSAUtil.getPrivateKey(privatekey);
			PublicKey publicKey = RSAUtil.getPublicKey(publickey);
			String miwen = RSAUtil.encrypt(publicKey, mobilebyte);
			System.out.println(miwen);
			miwen = "MdVl60xgfQEtGDBefC+4IHZalzCke1iHgr7LbwDFOPXBbNdcrLSLqkwJAFmHGg6SuJIP8RGAc1cz\r\nKNhkv3QhYEu5817qbafeqTFhApnIU0n5k7LVVNZvKnGKEf9l6ssxdJFjy0WQ6n2fB08wHsT7fh5W\r\nPTGbI+yjf/gAd3y1SFM=\r\n";
			miwen = miwen.replaceAll(" ", "+");
			String mingwen = RSAUtil.decrypt(privateKey, miwen);
			System.out.println(mingwen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
