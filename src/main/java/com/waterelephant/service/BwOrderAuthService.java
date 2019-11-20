package com.waterelephant.service;

import java.util.Date;
import java.util.List;

import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderAuth2;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.exception.BusException;
import com.waterelephant.faceID.entity.FaceIDOrderAuthDto;

public interface BwOrderAuthService {

	public void saveBwOrderAuth(BwOrderAuth bwOrderAuth);

	public void updateBwOrderAuth(BwOrderAuth bwOrderAuth);

	public void deleteBwOrderAuth(BwOrderAuth bwOrderAuth);

	public List<Integer> findBwOrderAuth(Long orderId);

	public BwOrderAuth findBwOrderAuth(Long orderId, Integer authType);

	public BwAdjunct findBwAdjunct(Long orderId, Integer adjunctType);

	public BwPersonInfo findBwPersonInfo(Long borrowerId, String productType);

	public BwWorkInfo findBwWorkInfo(Long borrowerId, String productType);

	public void savePensonInfo(String orderId, String cityName, String relationName, String relationPhone,
			String unrelationName, String unrelationPhone, String comName, String industry, String workYears,
			String authChannel, String email, String address);

	public void savePicInfo(String orderId, String borrowerId, String sfzzmUrl, String sfzfmUrl, String czzUrl,
			String authChannel);

	// 主要用来保存身份证姓名和身份证号码
	public void savePicInfo(String orderId, String borrowerId, String sfzzmUrl, String sfzfmUrl, String czzUrl,
			String authChannel, String nameAndNumber, String photoState, String verifyFaceResult, String photoStatu);

	// FaceID专用接口
	public List<FaceIDOrderAuthDto> findBwOrderAuthAndPhotoState(Long orderId);

	// 身份证正面保存
	public void saveOcrIDFront(String orderId, String borrowerId, String sfzzmUrl, String nameAndNumber,
			String photoStatu);

	// 身份证反面保存
	public void saveOcrIDBack(String orderId, String borrowerId, String sfzfmUrl, String photoStatu);

	// 保存czz
	public void savePicInfoH5(String orderId, String borrowerId, String czzUrl, String authChannel, String photoState,
			String photoStatu);
	
	/**
	 * 根据借款人年龄校验工作年限是否正确
	 * 
	 * @param age 年龄
	 * @param workyear 工作年限
	 * @return
	 */
	public String checkWorkYears(int age, String workyear);

	/**
	 * 查询最近的认证
	 * 
	 * @param orderId
	 * @param auth_type
	 * @return
	 */
	BwOrderAuth findLastOrderAuth(Long orderId, Integer auth_type);

	/**
	 * 根据借款人ID查询最近的认证
	 * 
	 * @param borrowerId
	 * @param auth_type
	 * @return
	 */
	BwOrderAuth findLastOrderAuthByBorrowerId(Long borrowerId, Integer auth_type);

	/**
	 * 
	 * 查询所有的工单状态
	 * 
	 * @param orderId
	 * @return
	 */
	List<BwOrderAuth> getListBwOrderAuthByOrderId(Long orderId);

	/**
	 * 将旧工单的个人信息复制到新工单，并进行认证
	 * 
	 * @param newOrderId
	 * @param oldOrderId
	 * @param authChannel 认证渠道
	 */
	void saveCopyPensonInfoAndAuth(Long newOrderId, Long oldOrderId, Integer authChannel);

	/**
	 * 将旧工单的身份证照片复制到新工单，并进行认证
	 * 
	 * @param newOrderId
	 * @param oldOrderId
	 * @param borrowerId
	 * @param authChannel 认证渠道
	 * @throws BusException
	 */
	void saveCopyPicInfoAndAuth(Long newOrderId, Long oldOrderId, Long borrowerId, Integer authChannel)
			throws BusException;

	/**
	 * 保存认证
	 * 
	 * @param orderId
	 * @param borrowerId
	 * @param authType
	 * @param authChannel
	 * @param createTime
	 * @param updateTime
	 */
	void saveOrderAuth(Long orderId, Long borrowerId, Integer authType, Integer authChannel, Date createTime,
			Date updateTime);

	/** 判断是否已经认证过 */
	boolean checkOrderAuth(int orderId, String authType);
	
	BwOrderAuth selectBwOrderAuth(Long orderId, Integer authType);

	boolean updatePhotoStateByOrderId(Long orderId, Integer authType, Integer photoState);

	BwOrderAuth2 selectBwOrderAuth2(Long orderId, Integer authType);

}
