package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 芝麻信用分
 * @author dengyan
 *
 */
public class AliTrustScore {

	@JSONField(name = "score")
	private int score;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
