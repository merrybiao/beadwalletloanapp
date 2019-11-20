package com.waterelephant.drainage.util.youyu;

import org.apache.commons.codec.binary.Base64;

import com.waterelephant.utils.StringUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.apache.commons.lang3.ArrayUtils.subarray;

/**
 * SignUtil RSA 签名工具类
 *
 * @author wuxiaoxing
 *
 */
public class SignUtil {
	
	private static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgW2dDqDpGbE6t7MaVWw7z35ha"
			+ "\r" + "0geRDSEgJkn8W2HHWzgf2qTW0VylviYlY+R7+TM9w85V1JjPGO22zw6WI8bDQ0K2" + "\r"
			+ "3dQxVa3HxUrPSSwec5Q+tnyCyrko2VfPTioHcIOxhqqfL3DWRLhILvQC7k1jQjUD" + "\r" + "A0+FvDkLww+S2k60GQIDAQAB"
			+ "\r";

	private static final String DEFAULT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKBbZ0OoOkZsTq3s"
			+ "\r" + "xpVbDvPfmFrSB5ENISAmSfxbYcdbOB/apNbRXKW+JiVj5Hv5Mz3DzlXUmM8Y7bbP" + "\r"
			+ "DpYjxsNDQrbd1DFVrcfFSs9JLB5zlD62fILKuSjZV89OKgdwg7GGqp8vcNZEuEgu" + "\r"
			+ "9ALuTWNCNQMDT4W8OQvDD5LaTrQZAgMBAAECgYAWLE1dF5fnQPaoKgNTh6HLqvFA" + "\r"
			+ "LaaKMgyQi3rTgDdG/6AFF5CPe6eZ628O4H8pfU3OjpKrX5g5mrLUAlF8BTpocYLY" + "\r"
			+ "Kpy9Oy2eGBI9ca9zaTup1aItGMiw9o4KnEzVb+KSy1lHsXY6SW1VigysotZunxYU" + "\r"
			+ "ZvC2KCCBnwcdXEUh2QJBANLXpycddBCY415mpgUqUy7txkGeMrjp8/FOLP1KbRkE" + "\r"
			+ "C8WjI54EX4AjXc2cSclIShAezMK8Na6F8jlTrGW7T7MCQQDCs6wtOXvm7d8ZiKU6" + "\r"
			+ "YHTcYMa6ecd7lTBLctwpc88XmOI1+z/TszVoVBVH6WqftP9GogGtwgHHHN/O+1af" + "\r"
			+ "5acDAkEAifbbRdkcDZA9l5QLpu2fKOImDOH7xswv+AJzpfqBkRD4swahU9EAvNRn" + "\r"
			+ "mRdfoPpQnGPLENIfPmgfrCt4b8k1yQJAGZjVgfyUtX+AXTMBxfL4aiCu/8US3MR4" + "\r"
			+ "XPL0zt5S059d3gryETr2QokLYzDku6poBTk3T0i6QxsgsW2JrevbUQJBAMAk32Z2" + "\r"
			+ "RfmVIeMl73fY0JRzkVv0uWqPShfP0qrIKNdkDXmUrImN2G4klkF8oD/4Aza+AGe2" + "\r" + "ERnMnyFZOLfhqQU=" + "\r";

	/**
	 * 生成签名-集合
	 *
	 * @param params
	 *            签名参数集合
	 * @param privateKey
	 *            秘钥
	 * @param charset
	 *            字符集
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsaSign(Map<String, String> params, String privateKey, String charset)
			throws RuntimeException {
		String signContent = getSignContent(params);
		return rsaSign(signContent, privateKey, charset);
	}

	/**
	 * SHA1WithRSA加密
	 *
	 * @param content
	 *            签名字符串
	 * @param privateKeyStr
	 *            秘钥
	 * @param charset
	 *            字符集
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsaSign(String content, String privateKeyStr, String charset) {
		try {
			PrivateKey privateKey = getPrivateKeyFromPKCS8("RSA", privateKeyStr);
			if (privateKey == null) {
				return null;
			}
			return rsaSign(content, privateKey, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String rsaSign(String content, String privateUrl, String charset, String priKeyPass) {
		try {
			PrivateKey privateKey = getPrivateKeyFromFile(privateUrl, priKeyPass);
			if (privateKey == null) {
				return null;
			}
			return rsaSign(content, privateKey, charset);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static String rsaSign(String content, PrivateKey privateKey, String charset) {
		try {
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initSign(privateKey);
			if (StringUtil.isEmpty(charset)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(charset));
			}
			byte[] signed = signature.sign();
			return new String(Base64.encodeBase64(signed));
		} catch (Exception var7) {
			throw new RuntimeException("RSAcontent = " + content + "; charset = " + charset, var7);
		}
	}

	/**
	 * SHA256WithRSA加密
	 *
	 * @param content
	 *            签名字符串
	 * @param privateKey
	 *            秘钥
	 * @param charset
	 *            字符集
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsa256Sign(String content, String privateKey, String charset) throws RuntimeException {
		try {
			PrivateKey e = getPrivateKeyFromPKCS8("RSA", privateKey);
			Signature signature = Signature.getInstance("SHA256WithRSA");
			signature.initSign(e);
			if (StringUtil.isEmpty(charset)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(charset));
			}

			byte[] signed = signature.sign();
			return new String(Base64.encodeBase64(signed));
		} catch (Exception var6) {
			throw new RuntimeException("RSAcontent = " + content + "; charset = " + charset, var6);
		}
	}

	/**
	 * 公钥签名校验
	 *
	 * @param content
	 *            签名原始字符串
	 * @param sign
	 *            签名
	 * @param publicKey
	 *            公钥
	 * @param charset
	 *            字符集
	 * @return
	 * @throws RuntimeException
	 */
	public static boolean rsaCheckContent(String content, String sign, String publicKey, String charset)
			throws RuntimeException {
		try {
			PublicKey e = getPublicKeyFromX509("RSA", publicKey);
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initVerify(e);
			if (StringUtil.isEmpty(charset)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(charset));
			}

			return signature.verify(Base64.decodeBase64(sign.getBytes()));
		} catch (Exception var6) {
			throw new RuntimeException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, var6);
		}
	}

	/**
	 * 公钥签名校验
	 *
	 * @param content
	 *            签名原始字符串
	 * @param sign
	 *            签名
	 * @param publicKey
	 *            公钥
	 * @param charset
	 *            字符集
	 * @return
	 * @throws RuntimeException
	 */
	public static boolean rsa256CheckContent(String content, String sign, String publicKey, String charset)
			throws RuntimeException {
		try {
			PublicKey e = getPublicKeyFromX509("RSA", publicKey);
			Signature signature = Signature.getInstance("SHA256WithRSA");
			signature.initVerify(e);
			if (StringUtil.isEmpty(charset)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(charset));
			}

			return signature.verify(Base64.decodeBase64(sign.getBytes()));
		} catch (Exception var6) {
			throw new RuntimeException("RSA256 content = " + content + ",sign=" + sign + ",charset = " + charset, var6);
		}
	}

	/**
	 * 公钥加密
	 *
	 * @param content
	 *            加密字符串
	 * @param publicKey
	 *            公钥
	 * @param charset
	 *            字符集
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsaEncrypt(String content, String publicKey, String charset) throws RuntimeException {
		try {
			PublicKey e = getPublicKeyFromX509("RSA", publicKey);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(1, e);
			byte[] data = StringUtil.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;

			for (int i = 0; inputLen - offSet > 0; offSet = i * 117) {
				byte[] cache;
				if (inputLen - offSet > 117) {
					cache = cipher.doFinal(data, offSet, 117);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}

				out.write(cache, 0, cache.length);
				++i;
			}

			byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
			out.close();
			return StringUtil.isEmpty(charset) ? new String(encryptedData) : new String(encryptedData, charset);
		} catch (Exception var12) {
			throw new RuntimeException("EncryptContent = " + content + ",charset = " + charset, var12);
		}
	}

	/**
	 * 私钥加密
	 *
	 * @param content
	 * @param privateUrl
	 * @param charset
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsaEncryptByPrivate(String content, String privateUrl, String charset, String priKeyPass)
			throws RuntimeException {
		PrivateKey privateKey = getPrivateKeyFromFile(privateUrl, priKeyPass);
		if (privateKey == null) {
			return null;
		}
		try {
			byte[] destBytes = rsaByKey(content.getBytes(), privateKey, Cipher.ENCRYPT_MODE);
			if (destBytes == null) {
				return null;
			}
			StringBuilder hexRetSB = new StringBuilder();
			for (byte b : destBytes) {
				String hexString = Integer.toHexString(0x00ff & b);
				hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
			}
			return hexRetSB.toString();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 公钥文件解密内容
	 *
	 * @param content
	 * @param cerUrl
	 * @param charset
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsaDecryptByPublic(String content, String cerUrl, String charset) throws RuntimeException {
		PublicKey publicKey = getPublicKeyFromFile(cerUrl);
		if (publicKey == null) {
			return null;
		}
		try {
			byte[] destBytes = rsaByKey(hex2Bytes(content), publicKey, Cipher.DECRYPT_MODE);
			if (destBytes == null) {
				return null;
			}
			return new String(destBytes, charset);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	/**
	 * 私钥解密
	 *
	 * @param content
	 *            需要解密字符串
	 * @param privateKey
	 *            私钥
	 * @param charset
	 *            字符集
	 * @return
	 * @throws RuntimeException
	 */
	public static String rsaDecrypt(String content, String privateKey, String charset) throws RuntimeException {
		try {
			PrivateKey e = getPrivateKeyFromPKCS8("RSA", privateKey);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(2, e);
			byte[] encryptedData = StringUtil.isEmpty(charset) ? Base64.decodeBase64(content.getBytes())
					: Base64.decodeBase64(content.getBytes(charset));
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;

			for (int i = 0; inputLen - offSet > 0; offSet = i * 128) {
				byte[] cache;
				if (inputLen - offSet > 128) {
					cache = cipher.doFinal(encryptedData, offSet, 128);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}

				out.write(cache, 0, cache.length);
				++i;
			}

			byte[] decryptedData = out.toByteArray();
			out.close();
			return StringUtil.isEmpty(charset) ? new String(decryptedData) : new String(decryptedData, charset);
		} catch (Exception var12) {
			throw new RuntimeException("EncodeContent = " + content + ",charset = " + charset, var12);
		}
	}

	/**
	 * 获取私钥对象
	 *
	 * @param algorithm
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePrivate(keySpec);
	}

	/**
	 * 获取公钥对象
	 *
	 * @param algorithm
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKeyFromX509(String algorithm, String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * 根据key值排序后的签名字符串
	 *
	 * @param sortedParams
	 * @return
	 */
	public static String getSignContent(Map<String, String> sortedParams) {
		StringBuilder content = new StringBuilder();
		List keys = new ArrayList<>(sortedParams.keySet());
		Collections.sort(keys);
		int index = 0;

		for (Object key1 : keys) {
			String key = (String) key1;
			String value = sortedParams.get(key);
			if (!StringUtil.isEmpty(key) && !StringUtil.isEmpty(value)) {
				content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
				++index;
			}
		}
		return content.toString();
	}

	/**
	 * 根据私钥路径读取私钥
	 *
	 * @param pfxPath
	 * @param priKeyPass
	 * @return
	 */
	public static PrivateKey getPrivateKeyFromFile(String pfxPath, String priKeyPass) {
		InputStream priKeyStream = null;
		try {
			priKeyStream = new FileInputStream(pfxPath);
			byte[] reads = new byte[priKeyStream.available()];
			priKeyStream.read(reads);
			return getPrivateKeyByStream(reads, priKeyPass);
		} catch (Exception e) {
		} finally {
			if (priKeyStream != null) {
				try {
					priKeyStream.close();
				} catch (Exception e) {

				}
			}
		}
		return null;
	}

	/**
	 * 根据PFX私钥字节流读取私钥
	 *
	 * @param pfxBytes
	 * @param priKeyPass
	 * @return
	 */
	public static PrivateKey getPrivateKeyByStream(byte[] pfxBytes, String priKeyPass) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			char[] charPriKeyPass = priKeyPass.toCharArray();
			ks.load(new ByteArrayInputStream(pfxBytes), charPriKeyPass);
			Enumeration<String> aliasEnum = ks.aliases();
			String keyAlias = null;
			if (aliasEnum.hasMoreElements()) {
				keyAlias = aliasEnum.nextElement();
			}
			return (PrivateKey) ks.getKey(keyAlias, charPriKeyPass);
		} catch (IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException
				| CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 公钥算法
	 *
	 * @param srcData
	 *            源字节
	 * @param key
	 *            公钥
	 * @param mode
	 *            加密 OR 解密
	 * @return
	 */
	public static byte[] rsaByKey(byte[] srcData, Key key, int mode) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(mode, key);
			// 分段加密
			int blockSize = (mode == Cipher.ENCRYPT_MODE) ? 117 : 128;
			byte[] encryptedData = null;
			for (int i = 0; i < srcData.length; i += blockSize) {
				// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
				byte[] doFinal = cipher.doFinal(subarray(srcData, i, i + blockSize));
				encryptedData = addAll(encryptedData, doFinal);
			}
			return encryptedData;

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException
				| InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据Cer文件读取公钥
	 *
	 * @param pubCerPath
	 * @return
	 */
	public static PublicKey getPublicKeyFromFile(String pubCerPath) {
		FileInputStream pubKeyStream = null;
		try {
			pubKeyStream = new FileInputStream(pubCerPath);
			byte[] reads = new byte[pubKeyStream.available()];
			pubKeyStream.read(reads);
			return getPublicKeyByText(new String(reads));
		} catch (IOException e) {
			// log.error("公钥文件读取失败:", e);
		} finally {
			if (pubKeyStream != null) {
				try {
					pubKeyStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 根据公钥Cer文本串读取公钥
	 *
	 * @param pubKeyText
	 * @return
	 */
	public static PublicKey getPublicKeyByText(String pubKeyText) {
		try {
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
			BufferedReader br = new BufferedReader(new StringReader(pubKeyText));
			String line;
			StringBuilder keyBuffer = new StringBuilder();
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("-")) {
					keyBuffer.append(line);
				}
			}
			Certificate certificate = certificateFactory
					.generateCertificate(new ByteArrayInputStream(Base64.decodeBase64(keyBuffer.toString())));
			return certificate.getPublicKey();
		} catch (Exception e) {
			// log.error("解析公钥内容失败:", e);
		}
		return null;
	}

	/**
	 * 将16进制字符串转为转换成字符串
	 */
	public static byte[] hex2Bytes(String source) {
		byte[] sourceBytes = new byte[source.length() / 2];
		for (int i = 0; i < sourceBytes.length; i++) {
			sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
		}
		return sourceBytes;
	}
}
