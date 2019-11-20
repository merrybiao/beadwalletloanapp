///**
// * 
// */
//package com.waterelephant.sxyDrainage.utils.lakala;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.security.KeyStore;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.Security;
//import java.security.Signature;
//
//import javax.annotation.PostConstruct;
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import javax.security.cert.X509Certificate;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.codec.binary.Base64;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.parser.Feature;
//
//@Service
//public class SignService {
//	
//	private static final Logger logger = LoggerFactory.getLogger(SignService.class);
//	
//
//	private static String cerFilePath = LakalaConstant.cerFilePath;
//
//
//	private static String keyFilePath = LakalaConstant.keyFilePath;
//
//
//	private static String keyPassWord = LakalaConstant.keyPassWord;
//	
//
//	private String alias = LakalaConstant.keyAlias;
//	
//
//	private String dESCORPKey = LakalaConstant.encryptKey;
//	
//	/**
//	 * 加密算法与填充方式
//	 */
//	public static final String AlGORITHM = "DESede/ECB/PKCS5Padding"; // 定义加密算法,可用
//	
//	@PostConstruct   
//    public void init(){  
//		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null){
//
//			System.out.println("security provider BC not found, will add provider");
//	
//			Security.addProvider(new BouncyCastleProvider());
//
//		}
//    }
//
//	/**
//	 * 加签并加密需要发送的请求报文
//	 * @param 接口报文
//	 * @return 加签后可以发送的json密文
//	 * @throws JSONException 
//	 */
//	public String signRequestJsonToSend(String jsonStr) throws JSONException {
//		JSONObject resultObj = null;
//		if(jsonStr.indexOf("\"head\"") >= 0)
//		{
//			CommReq req = JSON.parseObject(jsonStr, CommReq.class, Feature.OrderedField);
//			// 对报文体签名
//			String signData = signData(req.getRequest());
//			
//			logger.info("加签成功,原始报文：" + jsonStr + "，报文体签名：" + signData);
//			
//			req.getHead().setSignData(signData);
//	        resultObj = JSONObject.parseObject(JSON.toJSONString(req), Feature.OrderedField);
//		}
//		else if(jsonStr.indexOf("\"comm\"") >= 0)
//		{
//			CommRequest req = JSON.parseObject(jsonStr, CommRequest.class, Feature.OrderedField);
//			// 对报文体签名
//			String signData = signData(req.getData());
//			
//			logger.info("加签成功,原始报文：" + jsonStr + "，报文体签名：" + signData);
//			
//			req.getComm().setSigntx(signData);
//	        resultObj = JSONObject.parseObject(JSON.toJSONString(req), Feature.OrderedField);
//		}
//		
//		String signedReq = String.valueOf(JSONObject.toJSON(resultObj));
//		logger.info("加签后的报文：" + signedReq);
//		
//		//加密
//		byte[] key = Base64.decodeBase64(dESCORPKey);
//		byte[] encryptData = encrypt(key, signedReq.getBytes());
//		
//		String encryptStr = Base64.encodeBase64String(encryptData);
//		
//		logger.info("加密成功,密文：" + encryptStr);
//		
//		return encryptStr;
//	}
//	
//	/**
//	 * 加签并加密需要发送的响应报文
//	 * @param 接口报文
//	 * @return 加签后可以发送的json密文
//	 * @throws JSONException 
//	 */
//	public String signResponseJsonToSend(String jsonStr) throws JSONException {
//		JSONObject resultObj = null;
//		if(jsonStr.indexOf("\"head\"") >= 0)
//		{
//			CommRes response = JSON.parseObject(jsonStr, CommRes.class, Feature.OrderedField);
//	        resultObj = JSONObject.parseObject(JSON.toJSONString(response), Feature.OrderedField);
//		}
//		else if(jsonStr.indexOf("\"comm\"") >= 0)
//		{
//			CommResponse response = JSON.parseObject(jsonStr, CommResponse.class, Feature.OrderedField);
//			// 对报文体签名
//			String signData = signData(response.getData());
//			
//			logger.info("加签成功,原始报文：" + jsonStr + "，报文体签名：" + signData);
//			
//			response.getComm().setSigntx(signData);
//	        resultObj = JSONObject.parseObject(JSON.toJSONString(response), Feature.OrderedField);
//		}
//		
//		String signedReq = String.valueOf(JSONObject.toJSON(resultObj));
//		logger.info("加签后的响应报文：" + signedReq);
//		
//		//加密
//		byte[] key = Base64.decodeBase64(dESCORPKey);
//		byte[] encryptData = encrypt(key, signedReq.getBytes());
//		
//		String encryptStr = Base64.encodeBase64String(encryptData);
//		
//		logger.info("加密成功的响应报文,密文：" + encryptStr);
//		
//		return encryptStr;
//	}
//
//	/**
//	 * 从request提取json data，并解密，验签， 验签成功则返回data，否则返回null
//	 * @param request
//	 * @return
//	 * @throws IOException
//	 * @throws JSONException
//	 */
//	public String verifyReceivedRequest(HttpServletRequest request) throws IOException, JSONException {
//		String json = extractJson(request);
//		logger.info("接收报文密文:" + json);
//		
//		return verifyRequestJson(json);
//	}
//
//	/**
//	 * 对收到的请求json解密，验签， 验签成功则返回data，否则返回null
//	 * @param json
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 * @throws JSONException
//	 */
//	public String verifyRequestJson(String json) throws UnsupportedEncodingException, JSONException {
//		//解密
//		byte[] key = Base64.decodeBase64(dESCORPKey);
//		byte[] decryptBytes = decrypt(key, Base64.decodeBase64(json));
//		String orig = new String(decryptBytes, "UTF-8");
//		logger.info("【收到的报文请求】接收报文:" + orig);
//		
//		// 验签
//		JSONObject obj = JSONObject.parseObject(orig, Feature.OrderedField);
//        String reqStr = String.valueOf(JSONObject.toJSON(obj));
//        if(reqStr.indexOf("\"comm\"") >= 0)
//		{
//	        CommRequest req = JSON.parseObject(reqStr, CommRequest.class, Feature.OrderedField);
//	        String signtx = req.getComm().getSigntx();
//	        
//			logger.info("报文中的签名:" + signtx);
//			boolean nRet = verifyData(req.getData(), signtx);
//			if(nRet)
//			{
//				req.getComm().setSigntx("");
//				return JSON.toJSONString(req);
//			}
//			else
//			{
//				return null;
//			}
//		}
//        else if(reqStr.indexOf("\"head\"") >= 0)
//		{
//        	CommReq req = JSON.parseObject(reqStr, CommReq.class, Feature.OrderedField);
//	        String signData = req.getHead().getSignData();
//	        
//			logger.info("报文中的签名:" + signData);
//			boolean nRet = verifyData(req.getRequest(), signData);
//			if(nRet)
//			{
//				req.getHead().setSignData("");
//				return JSON.toJSONString(req);
//			}
//			else
//			{
//				return null;
//			}
//		}
//        else
//        {
//        	return null;
//        }
//	}
//	
//	/**
//	 * 对响应的报文json解密，验签， 验签成功则返回data，否则返回null
//	 * @param json
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 * @throws JSONException
//	 */
//	public String verifyResponseJson(String json) throws UnsupportedEncodingException, JSONException {
//		//解密
//		byte[] key = Base64.decodeBase64(dESCORPKey);
//		byte[] decryptBytes = decrypt(key, Base64.decodeBase64(json));
//		String orig = new String(decryptBytes, "UTF-8");
//		logger.info("【收到的响应报文】报文:" + orig);
//		
//		// 验签
//		JSONObject obj = JSONObject.parseObject(orig, Feature.OrderedField);
//        String reqStr = String.valueOf(JSONObject.toJSON(obj));
//        if(reqStr.indexOf("\"comm\"") >= 0)
//		{
//	        CommResponse response = JSON.parseObject(reqStr, CommResponse.class, Feature.OrderedField);
//	        String signtx = response.getComm().getSigntx();
//	        
//			logger.info("报文中的签名:" + signtx);
//			boolean nRet = verifyData(response.getData(), signtx);
//			if(nRet)
//			{
//				response.getComm().setSigntx("");
//				return JSON.toJSONString(response);
//			}
//			else
//			{
//				return null;
//			}
//		}
//        else if(reqStr.indexOf("\"head\"") >= 0)
//		{
//        	CommRes response = JSON.parseObject(reqStr, CommRes.class, Feature.OrderedField);
//			return JSON.toJSONString(response);
//		}
//        else
//        {
//        	return null;
//        }
//	}
//	
//	public String extractJson(HttpServletRequest request) throws IOException {
//		//用于接收对方的jsonString
//		StringBuilder jsonString = new StringBuilder();
//		BufferedReader reader = request.getReader();
//		try {
//		    String line;
//		    while ((line = reader.readLine()) != null) {
//		        jsonString.append(line);
//		    }
//		} finally {
//		    reader.close();
//		}
//		String data = jsonString.toString();
//		return data;
//	}
//	
//	/*  使用私钥签名,并返回密文
//	  * @param param  需要进行签名的数据
//	  * @return 签名
//	  */
//	private String signData(String param) 
//	{
//		InputStream inputStream = null;
//		try {
//
//			String store_password = keyPassWord;// 密钥库密码
//			String password = keyPassWord;// 私钥密码
//			String keyAlias = alias;// 别名
//			// a. 创建针对jks文件的输入流
//
//			inputStream = new FileInputStream(keyFilePath);// CA 文件名 如： D://tmp/encrypt.jks
//			// input = getClass().getClassLoader().getResourceAsStream(keyFile);
//			// 如果制定classpath下面的证书文件
//
//			// b. 创建KeyStore实例 （store_password密钥库密码）
//			KeyStore keyStore = KeyStore.getInstance("JKS");
//			keyStore.load(inputStream, store_password.toCharArray());
//
//			// c. 获取私钥 （keyAlias 为私钥别名，password为私钥密码）
//			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, password.toCharArray());
//
//			// 实例化一个用SHA算法进行散列，用RSA算法进行加密的Signature.
//			Signature dsa = Signature.getInstance("SHA1withRSA");
//			// 加载加密散列码用的私钥
//			dsa.initSign(privateKey);
//			// 进行散列，对产生的散列码进行加密并返回
//			dsa.update(param.getBytes());
//			
//			return Base64.encodeBase64String(dsa.sign());// 进行签名, 加密后的也是二进制的，但是返回给调用方是字符串，将byte[]转为base64编码
//
//		} catch (Exception gse) {
//
//			gse.printStackTrace();
//			return null;
//
//		} finally {
//
//			try {
//				if (inputStream != null)
//					inputStream.close();// 判断
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//	} 
//	    
//	/*  用公钥证书对字符串进行签名验证
//	  * @param urlParam  需要进行签名验证的数据
//	  * @param signParam 编码后的签名
//	  * @return 校验签名,true为正确 false为错误
//	  */
//	private boolean verifyData(String urlParam, String signParam) 
//	{
//		boolean verifies = false;
//
//		InputStream in = null;
//
//		try {
//
//			// a. 创建针对cer文件的输入流
//			InputStream inputStream = new FileInputStream(cerFilePath);// CA 文件名 如： D://tmp/cerfile.p7b
//			// input = getClass().getClassLoader().getResourceAsStream(keyFile);
//			// 如果制定classpath下面的证书文件
//
//			// b. 创建KeyStore实例 （store_password密钥库密码）
//			X509Certificate cert = X509Certificate.getInstance(inputStream);
//
//			// c. 获取公钥 （keyAlias 为公钥别名）
//			PublicKey pubKey = cert.getPublicKey();
//
//			if (pubKey != null) {
//				// d. 公钥进行验签
//				// 获取Signature实例，指定签名算法(与之前一致)
//				Signature dsa = Signature.getInstance("SHA1withRSA");
//				// 加载公钥
//				dsa.initVerify(pubKey);
//				// 更新原数据
//				dsa.update(urlParam.getBytes());
//
//				// 公钥验签（true-验签通过；false-验签失败）
//				verifies = dsa.verify(Base64.decodeBase64(signParam));// 将签名数据从base64编码字符串转回字节数组
//			}
//
//		} catch (Exception gse) {
//			gse.printStackTrace();
//		} finally {
//
//			try {
//				if (in != null)
//					in.close();// 判断
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return verifies;
//
//	}
//	
//	
//	// DES,DESede,Blowfish
//	/**
//	 * 使用3des加密明文
//	 * 
//	 * @param byte[] key: 密钥
//	 * @param byte[] src: 明文
//	 * 
//	 */
//	private byte[] encrypt(byte[] key, byte[] src) {
//		try {
//			
//			if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null){
//
//				System.out.println("security provider BC not found, will add provider");
//		
//				Security.addProvider(new BouncyCastleProvider());
//
//			}
//			
//			// 生成密钥
//			SecretKey deskey = new SecretKeySpec(key, AlGORITHM);
//			// 加密
//			Cipher c1 = Cipher.getInstance(AlGORITHM);
//			c1.init(Cipher.ENCRYPT_MODE, deskey);
//			return c1.doFinal(src);// 在单一方面的加密或解密
//		} catch (java.security.NoSuchAlgorithmException e1) {
//			e1.printStackTrace();
//		} catch (javax.crypto.NoSuchPaddingException e2) {
//			e2.printStackTrace();
//		} catch (java.lang.Exception e3) {
//			e3.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 使用3des解密密文
//	 * 
//	 * @param byte[] key: 密钥
//	 * @param byte[] src: 密文
//	 * 
//	 */
//	private byte[] decrypt(byte[] keybyte, byte[] src) {
//		try {
//			
//			if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null){
//
//				System.out.println("security provider BC not found, will add provider");
//		
//				Security.addProvider(new BouncyCastleProvider());
//
//			}
//			
//			// 生成密钥
//			SecretKey deskey = new SecretKeySpec(keybyte, AlGORITHM);
//			// 解密
//			Cipher c1 = Cipher.getInstance(AlGORITHM);
//			c1.init(Cipher.DECRYPT_MODE, deskey);
//			return c1.doFinal(src);
//		} catch (java.security.NoSuchAlgorithmException e1) {
//			e1.printStackTrace();
//		} catch (javax.crypto.NoSuchPaddingException e2) {
//			e2.printStackTrace();
//		} catch (java.lang.Exception e3) {
//			e3.printStackTrace();
//		}
//
//		return null;
//	}
//	
//	public static void main(String[] args) throws JSONException {
////		JSONObject decryptObj = new JSONObject("{\"a\":\"0\",\"b\":{\"c\":\"0\"}}") ;
////		System.out.println(decryptObj.get("b"));
//		
////		if(1<2)
////		{
////			JSONObject orig = new JSONObject("{\"a\":\"asdf/asdf\"}");
////			orig.setEscapeForwardSlashAlways(false);
////			System.out.println(orig.toString());
////		}
//		
//		InputStream inputStream = null;
//		try {
//
//			String store_password = "123456";// 密钥库密码
//			String password = "123456";// 私钥密码
//			String keyAlias = "www.lakala.com";// 别名
//			// a. 创建针对jks文件的输入流
//
//			inputStream = new FileInputStream(keyFilePath);// CA 文件名 如： D://tmp/encrypt.jks
//			// input = getClass().getClassLoader().getResourceAsStream(keyFile);
//			// 如果制定classpath下面的证书文件
//
//			// b. 创建KeyStore实例 （store_password密钥库密码）
//			KeyStore keyStore = KeyStore.getInstance("JKS");
//			keyStore.load(inputStream, store_password.toCharArray());
//
//			// c. 获取私钥 （keyAlias 为私钥别名，password为私钥密码）
//			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, password.toCharArray());
//
//			// 实例化一个用SHA算法进行散列，用RSA算法进行加密的Signature.
//			Signature dsa = Signature.getInstance("SHA1withRSA");
//			// 加载加密散列码用的私钥
//			dsa.initSign(privateKey);
//			String param = "{\"applyAmt\":400000,\"applyDate\":1490889600000,\"applyNo\":\"2017033100001084891\",\"applySer\":\"APS2017033100001084891SN\",\"auditOrgNum\":null,\"auditTime\":1526089973000,\"businessType\":\"TNH\",\"certpicUrlIc\":\"\",\"certpicUrlIf\":\"\",\"channel\":\"01\",\"contactMobile\":\"13113211234\",\"contactName\":\"莫小春\",\"contactRelation\":\"亲属\",\"contractNo\":\"\",\"custCertno\":\"500111197702227328\",\"custMobile\":\"13156299129\",\"custName\":\"解一诺\",\"email\":\"396553955@qq.com\",\"id\":10,\"idType\":\"\",\"idcard\":\"6222023602073008868\",\"intravitalUrl\":\"\",\"liveProvince\":\"\",\"liveProvinceCode\":\"\",\"livingAddr\":\"北京市朝阳区东三环中路39号西区15号楼3层303\",\"livingCity\":\"440000440300230404\",\"livingCityCode\":\"\",\"loanUse\":\"\",\"openingBank\":\"0102\",\"orderId\":\"\",\"orgNo\":\"\",\"overdueTime\":null,\"positionType\":\"\",\"preauditUrl\":\"\",\"productName\":\"\",\"productNo\":\"\",\"pullOrgNum\":1,\"receiveTime\":1526089973000,\"relativesMobile\":\"13212345678\",\"relativesName\":\"孔锦花\",\"relativesRelation\":\"同事\",\"totalCycle\":0,\"traProtocolFlag\":\"\",\"unitAddr\":\"上海北京市朝阳区东三环中路39号西区15号楼3层303\",\"unitCity\":\"440000440300230404\",\"unitCityCode\":\"\",\"unitName\":\"拉卡拉\",\"unitPhone\":\"0755-35445678\",\"unitProvince\":\"\",\"unitProvinceCode\":\"\",\"userid\":\"44000134676\"}";
//			// 进行散列，对产生的散列码进行加密并返回
//			dsa.update(param .getBytes());
//			String sign = Base64.encodeBase64String(dsa.sign());
//			System.out.println(sign);// 进行签名, 加密后的也是二进制的，但是返回给调用方是字符串，将byte[]转为base64编码
//			//signData
//			boolean b = new SignService().verifyData(param, sign);
//			System.out.println(b);
//		} catch (Exception gse) {
//
//			gse.printStackTrace();
//
//		} finally {
//
//			try {
//				if (inputStream != null)
//					inputStream.close();// 判断
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
