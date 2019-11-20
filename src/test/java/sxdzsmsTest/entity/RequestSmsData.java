package sxdzsmsTest.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestSmsData {
	
	private String secretkey;
	
	private SmsContent data;
	
}
