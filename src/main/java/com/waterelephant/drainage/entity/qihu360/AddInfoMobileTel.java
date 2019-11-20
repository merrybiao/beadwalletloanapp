package com.waterelephant.drainage.entity.qihu360;

import java.util.List;

/**
 * 
 * 
 * 
 * Module:用户通话信息
 * 
 * AddInfoMobileTel.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class AddInfoMobileTel {
	private List<AddInfoMobileTelTeldata> teldata; // 通话记录

	public List<AddInfoMobileTelTeldata> getTeldata() {
		return teldata;
	}

	public void setTeldata(List<AddInfoMobileTelTeldata> teldata) {
		this.teldata = teldata;
	}

}
