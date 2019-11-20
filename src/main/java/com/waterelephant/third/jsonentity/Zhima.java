package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 芝麻信用分
 * @author dengyan
 *
 */
public class Zhima {

	@JSONField(name = "ali_trust_score")
	private AliTrustScore aliTrustScore;

	public AliTrustScore getAliTrustScore() {
		return aliTrustScore;
	}

	public void setAliTrustScore(AliTrustScore aliTrustScore) {
		this.aliTrustScore = aliTrustScore;
	}
}
