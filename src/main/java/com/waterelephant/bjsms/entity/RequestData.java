package com.waterelephant.bjsms.entity;

import lombok.Data;

@Data
public class RequestData<T> {
	
	private String secretkey;
	
	private T param;
	
}
