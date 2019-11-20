/**
 * 
 */
package com.waterelephant.utils;

/**
 * @author dinglinhao
 *
 */
public enum VerifySource {
	FaceID(1L),
	SenseTime(2L);
	
	private Long source;
	
	private VerifySource(Long source){
		this.source = source;
	}

	public Long getSource() {
		return source;
	}
}
