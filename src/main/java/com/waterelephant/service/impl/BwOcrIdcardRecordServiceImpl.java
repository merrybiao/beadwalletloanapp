package com.waterelephant.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwOcrIdcardRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOcrIdcardRecordService;

/**
 * OCR身份识别记录
 * @author dinglinhao
 *
 */
@Service
public class BwOcrIdcardRecordServiceImpl extends BaseService<BwOcrIdcardRecord, Long>
		implements BwOcrIdcardRecordService {

	@Override
	public boolean saveFront(String productNo, String ocrSource, Map<String, Object> resultMap,Object result,String imageUrl,String imageData) throws Exception {
		BwOcrIdcardRecord record = new BwOcrIdcardRecord();
		if(null == resultMap || resultMap.isEmpty()) {
			JSONObject object = new JSONObject();
			object.put("result", result);
			object.put("image_url", imageUrl);
			record.setOcrResult(object.toJSONString());
		}else {
			JSONObject jsonObject = new JSONObject(resultMap);
			switch(ocrSource) {
			case "1"://face++
				
				break;
			case "2"://商汤
				JSONObject info = jsonObject.getJSONObject("info");
				record.setSide(jsonObject.getString("side"));
				record.setAddress(info.getString("address"));//地址
				record.setIdcardNumber(info.getString("number"));//身份证号
				record.setName(info.getString("name"));//姓名
				record.setGender(info.getString("gender"));//性别
				record.setRace(info.getString("nation"));//民族
				record.setYear(info.getString("year"));//出生年
				record.setMonth(info.getString("month"));//出生月
				record.setDay(info.getString("day"));//出生日
				break;
			}
			record.setOcrResult(jsonObject.toJSONString());
		}
		record.setProductNo(productNo);
		record.setOcrSource(ocrSource);
		record.setCreateTime(new Date());
		return insert(record)>0;
	}
	
	@Override
	public boolean saveBack(String productNo,String ocrSource,String idcardNumber,Map<String,Object> resultMap,Object result,String imageUrl,String imageData) throws Exception {
		BwOcrIdcardRecord record = new BwOcrIdcardRecord();
		if(null == resultMap || resultMap.isEmpty()) {
			JSONObject object = new JSONObject();
			object.put("result", result);
			object.put("image_url", imageUrl);
			record.setOcrResult(object.toJSONString());
		}else {
			JSONObject jsonObject = new JSONObject(resultMap);
			switch(ocrSource) {
				case "1"://face++
					
					break;
				case "2"://商汤
					JSONObject info = jsonObject.getJSONObject("info");
					record.setSide(jsonObject.getString("side"));
					record.setIdcardNumber(idcardNumber);//身份证号
					String validDate = info.getString("timelimit");
					if(!StringUtils.isEmpty(validDate)) {
						String[] strDates = validDate.split("-");
						if(null != strDates && strDates.length==2) {
							String startDate = strDates[0];
							String endDate = strDates[1];
							if(null != startDate && startDate.length() ==8) {
								String year = startDate.substring(0, 4);
								String month = startDate.substring(4,6);
								String day = startDate.substring(6);
								startDate = year + "." + month + "." + day;
							}
							if(null != endDate && endDate.length() ==8) {
								String year = endDate.substring(0, 4);
								String month = endDate.substring(4,6);
								String day = endDate.substring(6);
								endDate = year + "." + month + "." + day;
							}
							validDate = startDate + "-" + endDate;
						}
					}
					record.setValidDate(validDate);
					record.setIssuedBy(info.getString("authority"));
					break;
			}
			record.setOcrResult(jsonObject.toJSONString());
		}
		record.setProductNo(productNo);
		record.setOcrSource(ocrSource);
		record.setCreateTime(new Date());
		return insert(record)>0;
	}

}
