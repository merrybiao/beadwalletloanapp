package com.waterelephant.capital.nuoyuan;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.waterelephant.utils.Base64Utils;

public class ThreeDes {

	private static final String ALGORITHM_KEY = "desede";// "DESede";
	private static final String ALGORITHM_INSTANCE = "desede/CBC/PKCS5Padding";// "DESede";
	// //定义加密算法,可用
	// DES,DESede,Blowfish
	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）

	public static byte[] encryptMode(String iv, byte[] keybyte, byte[] src) {
		try {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(keybyte);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM_KEY);
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance(ALGORITHM_INSTANCE);
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(src);
			return encryptData;
		} catch (java.security.NoSuchAlgorithmException e1) {
		} catch (javax.crypto.NoSuchPaddingException e2) {
		} catch (Throwable e3) {
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static byte[] decryptMode(String iv, byte[] keybyte, byte[] src) {
		try {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(keybyte);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM_KEY);
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance(ALGORITHM_INSTANCE);
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			byte[] decryptData = cipher.doFinal(src);

			return decryptData;
		} catch (Throwable t) {
		}
		return null;
	}

	// 转换成十六进制字符串
	public static String byte2Hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) throws Exception {
		String key = "d36e9d2555414ee0182e55x2ae055b4p3f7a4122273d5b4x34896d5b434pd4d7"; // 24字节
		// String params =
		// "{\"appId\":\"17\",\"bid_id\":6318283892195333,\"bid_no\":\"B20170904101853383751\",\"bid_remark\":\"审核通过\",\"bid_status\":1}";
		//
		// byte[] bs_encode = encryptMode("01234567", key.getBytes("UTF-8"), params.getBytes("UTF-8"));
		// String src_base64 = Base64.base64Encode(bs_encode);
		// System.out.println(src_base64);
		// byte[] bytes = decryptMode("01234567", "A!d234%~eruBVso$^.456258".getBytes("UTF-8"),
		// Base64.base64Decode(src_base64));
		// System.out.println(new String(bytes));

		String str = "cdlnZenTjGAg/xGAA1zCBw0Ou7fHzzh2/cgFcI3nYKUkSQmBMv9GD8uFW6F0agtsGI0iw1c9tNp5\ne5rbjaE6s9BXop+buhk9DYndKECm33BBhcAfdNjyqm0uQYWMVfsc1/46lt6//jRVxTM9dmZUcMq1\nmVX/CjY3/Eey3MZw/pzVuz7xBl6yH6AXcGYeoeMG9rNxL3MN++DA3tvGpFThpPWUa5XtmjCW0Olc\no2IFmxoAcyO/+y8GoFBWUhDX35VR8x61jranc4rrZj7gmYfKf54OZogouo6K";
		byte[] bytes = decryptMode("01234567",
				"d36e9d2555414ee0182e55x2ae055b4p3f7a4122273d5b4x34896d5b434pd4d7".getBytes("UTF-8"),
				Base64Utils.decode(str));

		System.out.println(new String(bytes));

	}
}
