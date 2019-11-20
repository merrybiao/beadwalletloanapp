package com.waterelephant.faceID.service;

import java.util.Map;

import com.beadwallet.service.faceID.entity.OCRIDCard;
import com.beadwallet.service.faceID.entity.Verify;
import com.waterelephant.utils.AppResponseResult;

/**
 * faceID - Service接口
 * 
 * @author dengyan
 *
 */
public interface FaceIDService {

	/**
	 * faceID - 身份证识别正面
	 * 
	 * @param sessionId
	 * @param params
	 * @return
	 */
	public AppResponseResult ocrIDCardFront(Map<String, String> params);

	/**
	 * faceID - 身份证识别反面
	 * 
	 * @param sessionId
	 * @param params
	 * @return
	 */
	public AppResponseResult ocrIDCardBack(Map<String, String> params);

	/**
	 * faceID - 身份证认证
	 * 
	 * @param sessionId
	 * @param prams
	 * @return
	 */
	public Map<String, String> saveVerifyIDCard(Map<String, String> params);

	/**
	 * faceID - 活体认证
	 * 
	 * @param sessionId
	 * @param params
	 * @return
	 */
	public AppResponseResult saveVerifyFace(Map<String, String> params, String delta);

//	/**
//	 * faceID - H5 - 获取朗读随机字符
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public AppResponseResult getRandomNumber(Map<String, String> params);
//
//	/**
//	 * faceID - H5 - 活体验证
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Map<String, String> validateVideo(Map<String, String> params);

//	/**
//	 * faceID - H5 - 活体结果比对
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public AppResponseResult verify(Map<String, String> params);
//	
//	/**
//	 * faceID - H5 - 正脸验证
//	 * @param params
//	 * @return
//	 */
//	public AppResponseResult validateFrontFace(Map<String, String> params);
//	
//	/**
//	 * faceID - H5 - 侧脸验证
//	 * @param params
//	 * @return
//	 */
//	public AppResponseResult validateSideFace(Map<String, String> params);

	/**
	 * faceID 征信黑名单
	 */
	public void saveZxBlack(Map<String, String> params);
	

	/**
	 * 保存身份证信息
	 * @param paramMap
	 * @return
	 */
	public void saveIdCard(Map<String, String> paramMap);
	
	OCRIDCard ocrIdcard(String sessionId,String userId,String imgBase64,String side) throws Exception;
	
	//Map<String,String> verifyIdcard(String userId,String sessionId,String imgBase64,String idcardName,String idcardNum) throws Exception;
	
	Verify verifyFace(String sessionId,String userId,String idcardName,String idcardNum,String imgBase64,String delta) throws Exception;
	//保存身份证信息
	boolean saveIdcardInfo(Map<String, String> paramMap) throws Exception;

	boolean saveVerifyFaceInfo(String idcardName, String idcardNum, float confidence);
	
}
