package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.dto.IndentityScoreDto;
import com.waterelephant.dto.InsureScoreDto;
import com.waterelephant.dto.PersonInfoScoreDto;
import com.waterelephant.dto.UnderLineDto;
import com.waterelephant.entity.BwCardCity;
import com.waterelephant.entity.BwMarkRecord;
import com.waterelephant.entity.BwInsureCity;
import com.waterelephant.service.BwMarkRecordService;
import com.waterelephant.service.IBwInsureCityService;
import com.waterelephant.service.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService{

	@Autowired
	private BwMarkRecordService bwMarkRecordService;
	
	@Override
	public int indentityScore(IndentityScoreDto indentityScoreDto,Long orderId) {
		int result = 0;
		BwMarkRecord bwMarkRecord = new BwMarkRecord();
		//性别得分
		if (indentityScoreDto.getSex().equals("1")) {
			//0分
			bwMarkRecord.setAnswerId(1l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}else {
			//1分
			bwMarkRecord.setAnswerId(2l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}
		//年龄得分
		if (indentityScoreDto.getAge() >= 18 && indentityScoreDto.getAge() <= 29) {
			//2分
			bwMarkRecord.setAnswerId(9l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}else if (indentityScoreDto.getAge() >= 30 && indentityScoreDto.getAge() <= 35) {
			//3分
			bwMarkRecord.setAnswerId(10l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}else if (indentityScoreDto.getAge() >= 36 && indentityScoreDto.getAge() <= 45) {
			//2分
			bwMarkRecord.setAnswerId(11l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}else if (indentityScoreDto.getAge() >= 46 && indentityScoreDto.getAge() <= 55){
			//1分
			bwMarkRecord.setAnswerId(12l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}
		//出生地行政等级得分
		BwCardCity bcc = indentityScoreDto.getBwCardCity();
		if (bcc.getLevel() == 1) {
			//3分
			bwMarkRecord.setAnswerId(13l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}else if (bcc.getLevel() == 2) {
			//2分
			bwMarkRecord.setAnswerId(14l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}else {
			//1分
			bwMarkRecord.setAnswerId(15l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}
		//出生地与申请城市匹配得分
		if (bcc.getCityName().equals(indentityScoreDto.getSqCity())) {
			//1分
			bwMarkRecord.setAnswerId(16l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}else {
			//0分
			bwMarkRecord.setAnswerId(17l);
			bwMarkRecord.setOrderId(orderId);
			bwMarkRecord.setCreateTime(new Date());
			result = bwMarkRecordService.add(bwMarkRecord);
		}
		return result;
	}

	@Override
	public int operatorScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insureScore(InsureScoreDto insureScoreDto,Long orderId) {
		int result = 0;
		BwMarkRecord bmr = new BwMarkRecord();
		//户口所在城市得分
		if (insureScoreDto.getAccCity().equals(insureScoreDto.getSqCity())) {
			//1分
			bmr.setAnswerId(3l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//0分
			bmr.setAnswerId(4l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//文化程度得分
		if (insureScoreDto.getDegree().equals("大学本科")) {
			//3分
			bmr.setAnswerId(21l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else if (insureScoreDto.getDegree().equals("大学专科")) {
			//2分
			bmr.setAnswerId(20l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else if (insureScoreDto.getDegree().equals("高中")) {
			//1分
			bmr.setAnswerId(19l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//0分
			bmr.setAnswerId(18l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//参保人电话得分
		if (insureScoreDto.getInsurePhone().equals(insureScoreDto.getPhone())) {
			//1分
			bmr.setAnswerId(22l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//0分
			bmr.setAnswerId(23l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//医疗参保人员类别得分
		if (insureScoreDto.getWorkNation().equals("在职职工")) {
			//1分
			bmr.setAnswerId(24l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//0分
			bmr.setAnswerId(25l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//在本单位社保连续缴纳月数
		if (insureScoreDto.getContinuityMonthInCom() <= 6) {
			// 0分
			bmr.setAnswerId(26l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else if (insureScoreDto.getContinuityMonthInCom() >= 7 && insureScoreDto.getContinuityMonthInCom() <= 12) {
			//1分
			bmr.setAnswerId(27l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else if (insureScoreDto.getContinuityMonthInCom() >= 13 && insureScoreDto.getContinuityMonthInCom() <= 36) {
			//2分
			bmr.setAnswerId(28l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else if (insureScoreDto.getContinuityMonthInCom() >= 37 && insureScoreDto.getContinuityMonthInCom() <= 60) {
			//3分
			bmr.setAnswerId(29l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//5分
			bmr.setAnswerId(30l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//社保连续缴纳月数
		if (insureScoreDto.getContinuityMonth() <= 12) {
			//1分
			bmr.setAnswerId(31l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else if (insureScoreDto.getContinuityMonth() >= 13 && insureScoreDto.getContinuityMonth() <= 36) {
			//2分
			bmr.setAnswerId(32l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else if (insureScoreDto.getContinuityMonth() >= 37 && insureScoreDto.getContinuityMonth() <= 60) {
			//3分
			bmr.setAnswerId(33l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//5分
			bmr.setAnswerId(34l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		return result;
	}

	@Override
	public int personInfoScore(PersonInfoScoreDto personInfoDto, Long orderId) {
		int result = 0;
		BwMarkRecord bmr = new BwMarkRecord();
		//婚姻状况得分
		if (personInfoDto.getMarryStatus() == 0) {
			//0分
			bmr.setAnswerId(5l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//1分
			bmr.setAnswerId(6l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//子女数量得分
		if (personInfoDto.getChildNum() > 0) {
			//1分
			bmr.setAnswerId(36l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//0分
			bmr.setAnswerId(35l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}		
		//工龄得分
		if (personInfoDto.getWorkYears() <= 1) {
			//0分
			bmr.setAnswerId(37l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else if (personInfoDto.getWorkYears() > 1 && personInfoDto.getWorkYears() <= 3) {
			//2分
			bmr.setAnswerId(38l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else if (personInfoDto.getWorkYears() > 3 && personInfoDto.getWorkYears() <= 5) {
			//3分
			bmr.setAnswerId(39l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//5分
			bmr.setAnswerId(40l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		return result;
	}

	@Override
	public int underLineScore(UnderLineDto underLineDto, Long orderId) {
		int result = 0;
		BwMarkRecord bmr = new BwMarkRecord();
		//名下是否有车得分
		if (underLineDto.getHaveCar() == 0) {
			//0分
			bmr.setAnswerId(41l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//3分
			bmr.setAnswerId(42l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//名下是否有房得分
		if (underLineDto.getHaveHouse() == 0) {
			//0分
			bmr.setAnswerId(43l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//2分
			bmr.setAnswerId(44l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//有无直系亲属在同一公司得分
		if (underLineDto.getHaveZxqs() == 0) {
			//0分
			bmr.setAnswerId(45l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//3分
			bmr.setAnswerId(46l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//有无旁系亲属在同一公司得分
		if (underLineDto.getHavePxqs() == 0) {
			//0分
			bmr.setAnswerId(47l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//2分
			bmr.setAnswerId(48l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		//是否有信用卡得分
		if (underLineDto.getHaveCreditCard() == 0) {
			//0分
			bmr.setAnswerId(49l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}else {
			//1分
			bmr.setAnswerId(50l);
			bmr.setOrderId(orderId);
			bmr.setCreateTime(new Date());
			result = bwMarkRecordService.add(bmr);
		}
		return result;
	}

}
