package com.waterelephant.capital.bairongZhitou;

import java.net.URLDecoder;
import java.security.PrivateKey;
import java.util.Map;

import net.sf.json.JSONObject;

public class ApprovalUtil {

	protected static String password = "123456";// 密码

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ApprovalUtil.password = password;
	}

	/**
	 * 解密
	 * 
	 * @param post
	 * @return
	 * @throws Exception
	 */
	public static String pubkey(String post) throws Exception {

		System.out.println("密文================>" + post);
		post = URLDecoder.decode(post, "utf-8");
		System.out.println("密文================>" + post);
		// 将返回的json串转换为map

		Map<String, String> map = JSONObject.fromObject(post);
		String encryptkey = map.get("encryptkey");
		String data = map.get("data");

		System.out.println("输出data  = =" + data);

		// 获取自己私钥解密
		PrivateKey pvkformPfx = RSA.getPvkformPfx("bairong.pfx", ApprovalUtil.getPassword());
		String publicKeyString = new String(Base64.encode(pvkformPfx.getEncoded()));
		System.out.println("pvkformPfx=============>" + publicKeyString);
		String decryptData = RSA.decrypt(encryptkey, publicKeyString);

		post = AES.decryptFromBase64(data, decryptData);

		System.out.println("明文================>" + post);

		return post;
	}

	/**
	 * 解密
	 * 
	 * @param encryptkey
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String pubkey(String encryptkey, String data) throws Exception {
		String post;
		System.out.println("encryptkey================>" + encryptkey);
		System.out.println("密文================>" + data);
		// 获取自己私钥解密
		PrivateKey pvkformPfx = RSA.getPvkformPfx("bairong.pfx", ApprovalUtil.getPassword());
		String decryptData = RSA.decrypt(encryptkey, pvkformPfx);

		post = AES.decryptFromBase64(data, decryptData);

		System.out.println("明文================>" + post);

		return post;
	}

	public static void main(String[] args) {
		try {
			String str = pubkey(
					"%7B%22data%22%3A%22ihF3CETPUHym6DqRwvagH0XXLDGD3yQjq52%2Bte%2B1SUTwj%2BaBjhXWhALqQyel%2Fn%2B7EYQxmCIntAEr7M0cf7xrxwovh4LUb22MFVBwI4cyQ6H0EL7K9EhSNqRXRUwGlGe13UqQJbUWF9sgW7z92oDQvqmiZG0LNZVUv33YjM1ztFrwt2XEslN2jrwOLaSDJnqQ6Qg066CsoMniETSittptuN2oZuLxdDMqRKnMiw0E%2FvSXh0f4FRV0WZkgPx%2FAVfUg0j2%2Fx6fGeac4nr%2BZCRH4riDMmnV81phGNKCrq9mqZDo%3D%22%2C%22encryptkey%22%3A%22ExfcQ99A0qQSZvkVzpyDBtKQbvq2jugwSI1JNuxnG2XBk920I1xvgIOKe4RuitB98QCkA0c4XPMT3oqR9FvRPgioPdrAFCSQX8wC4QQ45B%2BFeIuuaqkYD8eRFbptHp3CdGQN5YwGkquIX5idj89FVR2P4aP78Orz8g2r0YBGes0%3D%22%7D");
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
