package com.waterelephant.fulinfk.service;

import java.util.Map;

public interface IFulinfkAppService {

	public String getFullinkKey() throws Exception;

	// public String getFullinkReport(Map<String, String> map) throws Exception;

	String saveFullinkReport(Map<String, String> map) throws Exception;

}
