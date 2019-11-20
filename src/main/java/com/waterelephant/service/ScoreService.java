package com.waterelephant.service;

import com.waterelephant.dto.IndentityScoreDto;
import com.waterelephant.dto.InsureScoreDto;
import com.waterelephant.dto.PersonInfoScoreDto;
import com.waterelephant.dto.UnderLineDto;

public interface ScoreService {

	//身份信息打分
	public int indentityScore(IndentityScoreDto indentityScoreDto,Long orderId);
	
	//运营商信息打分
	public int operatorScore();
	
	//社保信息打分
	public int insureScore(InsureScoreDto insureScoreDto,Long orderId);
	
	//个人信息打分
	public int personInfoScore(PersonInfoScoreDto personInfoDto,Long orderId);
	
	//线下验证打分
	public int underLineScore(UnderLineDto underLineDto,Long orderId);
	
	
}
