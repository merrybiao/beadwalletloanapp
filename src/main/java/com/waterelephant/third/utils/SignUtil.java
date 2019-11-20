/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.third.utils;

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

/**
 * 统一对外接口 -code0091
 * 
 * Module:
 * 
 * SignUtil.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class SignUtil {
	// 公钥还原：
	// 1: 对公钥字符串Base64解密得到byte数组
	// 2：把公钥的byte数组放入规范
	// 3：实例化KeyFactory，
	// 4：从KeyFactory中获得公钥
	public static PublicKey getPublicKey(String public_key) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(public_key);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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

	// 私钥还原：
	// 1: 对私钥字符串Base64解密得到byte数组
	// 2：把私钥的byte数组放入规范
	// 3：实例化KeyFactory，
	// 4：从KeyFactory中获得私钥
	public static PrivateKey getPrivateKey(String private_key) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(private_key);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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

	// 加密：
	// 1：获得要加密明文的byte数组
	// 2：实例化Cipher
	// 3：初始化Cipher
	// 4：调Cipher的doFinal方法(明文的byte数组作为参数),获得一byte数组
	// 5：对上一步的byte数组进行Base64加密得到密文
	public static String encrypt(PrivateKey privateKey, byte[] byteArray) throws Exception {
		String base64String = null;
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(byteArray);
			base64String = new BASE64Encoder().encodeBuffer(output);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
		return base64String;
	}

	// 解密：
	// 1：获得要解密明文的byte数组
	// 2：实例化Cipher
	// 3：初始化Cipher
	// 4：调Cipher的doFinal方法(明文的byte数组作为参数),获得一byte数组
	// 5：对上一步的byte数组进行Base64解密得到内容
	public static String decrypt(PublicKey publicKey, String ciphertext) throws Exception {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] buffer = base64Decoder.decodeBuffer(ciphertext);

		String base64String = null;
		if (publicKey == null) {
			throw new Exception("解密公钥为空, 请设置");
		}
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(buffer);
			base64String = new String(output);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			throw new Exception("加密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
		return base64String;
	}

}
