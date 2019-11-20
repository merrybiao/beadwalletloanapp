package com.waterelephant.saas.service;

import java.util.Map;

public interface FqyBussinessService {

	Map<String, Object> querySaaSOperatorData(String orderNo, String partnerCode, boolean gzip);

}
