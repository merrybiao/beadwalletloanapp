//package com.waterelephant.sxyDrainage.utils.fenqiguanjia;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
///**
// * Hello world!
// */
//public class App {
//
//	/**
//	 * 应用时替换自己的私钥
//	 */
//	private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKvUeLjBbEaU2fuR\n"
//			+ "+bRoSfYukN8rBmSrSUKTy/fFsRriiW9izcGEHqxoLvEDtq+du5rkHSDY5uopL1az\n"
//			+ "oxBUFhHVQxlSNxWN8qt4DyzBeJNMUJfXcNNGCMcvdvFco+b89MrPFwlHoam7W8C3\n"
//			+ "npoyXHsVSurU3hvNVq9/qoQEcHQpAgMBAAECgYB69UsB4p+JjmDCLb5DA9HRUZcn\n"
//			+ "q1Ei3pNJChwODLBkbbEtPMXB8bpFYzQcEa071CJ4fz380FnFPH18jIVmXNWA9xWF\n"
//			+ "m00tUSVdPdsnU355YkTrb87/oG0bbIrtglmaSh2Jo5ozIYJOL5eLnnmnjUYoZSig\n"
//			+ "3EffLvSGRh51pQNSUQJBANdlTQV42QpPR/aj7Fg5NEQhaVbTQ8k1KVnt7auxATqx\n"
//			+ "iRDFkSo6T9WYznRc3cr1HZWCjSgGhTySk5Q7njnGZNUCQQDMOL+SJakMmFfuhtbY\n"
//			+ "isS4KDT6XZNryo+uctA1QKB9EuCHBCyo7AzTX1P8S/XIDYr/DGBr4lx5ZKfePaT4\n"
//			+ "vIwFAkEAuQOdffbIzy88TCGPsFQqjd75IYhEcH8GbuWNQe2/dY/rgQmC4HfH/VvM\n"
//			+ "8myWYm/bIoJKBzhal6X7t9bh2RSNpQJAFO/wfAxOY5Mu2K6MUlEP0A2XON/lIFT5\n"
//			+ "HBp7TCpfCdOon08HQR5KRlVsp94lyafRy0o6jHT9pBh0uW06f6Hc/QJAGFhOdAUv\n"
//			+ "qc/RkQ2eZiYz/5totZBdDGAvFMbM2W9o7r+cjNMKHHRqfYfXRDq7wSE/WYVc00sJ\n" + "PlBxo/PJ72b+rw==";
//
//	/**
//	 * 应用时替换需要的地址
//	 */
//	private static final String url = "http://test.api.fqgj.net:9099/openapi/open/gateway/v1.api.order.orderfeedback";
//	// private static final String url = "http://localhost:8080/open/gateway/v1.api.order.orderfeedback";
//
//	/**
//	 * 应用时替换自己的appId
//	 */
//	private static final String appId = "shuixiangfenqi_m90orkb8b";
//
//	private static ObjectMapper mapper = new ObjectMapper();
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
//
//	public static void main(String[] args) {
//
//		// 订单状态反馈例子
//
//		RequestParams requestParams = new RequestParams(appId);
//		String bizDataStr = null;
//
//		Map<String, Object> param = new LinkedHashMap<String, Object>();
//		param.put("order_no", "20170615102135262");
//		param.put("order_status", 100);
//		param.put("update_time", System.currentTimeMillis());
//
//		try {
//			bizDataStr = mapper.writeValueAsString(param);
//		} catch (JsonProcessingException e) {
//			LOGGER.error(e.getMessage(), e);
//			e.printStackTrace();
//		}
//		requestParams.put("biz_data", bizDataStr);
//
//		CallerResponse callerResponse = new HttpPost().synchronousPost(url, privateKey, requestParams);
//
//		System.out.print(callerResponse);
//	}
//}
