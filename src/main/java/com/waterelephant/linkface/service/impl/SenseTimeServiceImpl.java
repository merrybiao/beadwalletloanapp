package com.waterelephant.linkface.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.linkface.SenseTimeConstant;
import com.beadwallet.service.linkface.SenseTimeSDKService;
import com.waterelephant.entity.BwExternalIdentityCard;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.linkface.service.SenseTimeService;
import com.waterelephant.service.BwExternalIdentityCardService;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.StringUtil;
/**
 * 商汤业务处理
 * @author dinglinhao
 * @date 2018年8月30日18:13:35
 *
 */
@Service
public class SenseTimeServiceImpl implements SenseTimeService {
	
	private Logger logger = LoggerFactory.getLogger(SenseTimeServiceImpl.class);
	
	@Autowired
	private BwExternalIdentityCardService bwExternalIdentityCardService;
	
	@Override
	public Map<String,Object> ocrIdcard(String imageUrl,String imageData,String side) throws Exception {
		String result = SenseTimeSDKService.ocrIdcard(imageUrl,imageData);
		logger.info("----调用SenseTimeSDKService.ocrIdcard方法，imageUrl:{},返回结果为:{}--",imageUrl,result);
		if(StringUtils.isEmpty(result)) {
			throw new BusinessException("接口返回结果为空~");
		}
		
		if(!StringUtil.isJson(result)){
			throw new BusinessException("三方接口返回结果无法正常解析~");
		}
		
		JSONObject jsonResult = JSON.parseObject(result);
		Integer code = jsonResult.getInteger("code");
		if(1000 != code.intValue()) {
			throw new BusinessException(String.valueOf(code),SenseTimeConstant.responseCode.get(code));
		}
		if(!side.equals(jsonResult.getString("side"))) {
			throw new BusinessException("请上传身份证"+("front".equals(side)?"正面":"反面")+"照片~");
		} 
		jsonResult.remove("code");
		return jsonResult;
	}
	
	@Override
	public Map<String,Object> ocrBankcard(String imageUrl,String imageData) throws Exception {
		String result = SenseTimeSDKService.ocrBankcard(imageUrl,imageData);
		logger.info(result);
		return null;
	}
	
	@Override
	public Map<String,Object> imageVerification(String firstImg,String secondImg) throws Exception {
		String result = SenseTimeSDKService.imageVerification(firstImg, secondImg);
		logger.info("----调用SenseTimeSDKService.imageVerification方法，参数firstImg:{},secondImg:{}返回结果为:{}--",firstImg,secondImg,result);
		if(StringUtils.isEmpty(result)) {
			throw new BusinessException("接口返回结果为空~");
		}
		if(!StringUtil.isJson(result)){
			throw new BusinessException("三方接口返回结果无法正常解析~");
		}
		JSONObject jsonResult = JSON.parseObject(result);
		Integer code = jsonResult.getInteger("code");
		if(1000 != code.intValue()) {
			throw new BusinessException(String.valueOf(code),SenseTimeConstant.responseCode.get(code));
		}
		jsonResult.remove("code");
		return jsonResult;
	}
	
	@Override
	public Map<String,Object> imageVerificationUrl(String firstImgUrl,String secondImgUrl) throws Exception {
		String result = SenseTimeSDKService.imageVerificationUrl(firstImgUrl, secondImgUrl);
		logger.info("----调用SenseTimeSDKService.imageVerificationUrl方法，参数：firstImgUrl:{},secondImgUrl:{},返回结果为：{}--",firstImgUrl,secondImgUrl,result);
		if(StringUtils.isEmpty(result)) {
			throw new BusinessException("接口返回结果为空~");
		}
		if(!StringUtil.isJson(result)){
			throw new BusinessException("三方接口返回结果无法正常解析~");
		}
		JSONObject jsonResult = JSON.parseObject(result);
		Integer code = jsonResult.getInteger("code");
		if(1000 != code.intValue()) {
			throw new BusinessException(String.valueOf(code),SenseTimeConstant.responseCode.get(code));
		}
		jsonResult.remove("code");
		return jsonResult;
	}
	
	@Override
	public Map<String, Object> idnumberVerification(String name, String number, String imageUrl,String imageData)
			throws Exception {
		String result = SenseTimeSDKService.idnumberVerification(name, number, imageUrl, imageData);
		logger.info("----调用SenseTimeSDKService.idnumberVerification方法，参数：name:{},number:{},imageUrl:{},返回结果为：{}--",name,number,imageUrl,result);
		if(StringUtils.isEmpty(result)) {
			throw new BusinessException("接口返回结果为空~");
		}
		if(!StringUtil.isJson(result)){
			throw new BusinessException("三方接口返回结果无法正常解析~");
		}
		JSONObject jsonResult = JSON.parseObject(result);
		Integer code = jsonResult.getInteger("code");
		if(1000 != code.intValue()) {
			throw new BusinessException(String.valueOf(code),SenseTimeConstant.responseCode.get(code));
		}
		jsonResult.remove("code");
		return jsonResult;
	}

	@Override
	public Map<String, Object> livenessIdnumberVerification(String name, String number, String livenessUrl,String livenessData)
			throws Exception {
		String result = SenseTimeSDKService.livenessIdnumberVerification(name, number, livenessUrl, livenessData);
		logger.info("----调用SenseTimeSDKService.idnumberVerification方法，参数：name:{},number:{},livenessUrl:{},返回结果为：{}--",name,number,livenessUrl,result);
		if(StringUtils.isEmpty(result)) {
			throw new BusinessException("接口返回结果为空~");
		}
		if(!StringUtil.isJson(result)){
			throw new BusinessException("三方接口返回结果无法正常解析~");
		}
		JSONObject jsonResult = JSON.parseObject(result);
		Integer code = jsonResult.getInteger("code");
		if(1000 != code.intValue()) {
			throw new BusinessException(String.valueOf(code),SenseTimeConstant.responseCode.get(code));
		}
		jsonResult.remove("code");
		return jsonResult;
	}

	@Override
	public boolean saveIdentityCardInfo(Map<String, Object> resultMap) throws Exception {
		boolean flag = false;
		JSONObject idcardInfo = new JSONObject(resultMap);
		JSONObject info = idcardInfo.getJSONObject("info");
		String side = idcardInfo.getString("side");
		String idcardNumber = info.getString("number");
		String birthday = info.getString("year") + "-" + info.getString("month") + "-" + info.getString("day");
		if(StringUtils.isEmpty(idcardNumber)) {
			idcardNumber = idcardInfo.getString("idcardNumber");
		}
		try {
			// 第一步：判断用户是否存在（根据身份证号）
			BwExternalIdentityCard identityCard = bwExternalIdentityCardService.queryIdentityCardByIdcardNo(idcardNumber);
			if (null == identityCard && "front".equals(side)) {
				// 第二步：实例化身份证正面信息
				identityCard = new BwExternalIdentityCard();
				identityCard.setIdCardNumber(idcardNumber);
				identityCard.setAddress(info.getString("address"));
				identityCard.setBirthday(birthday);
				identityCard.setGender(info.getString("gender"));
				identityCard.setName(info.getString("name"));
				identityCard.setRace(info.getString("nation"));
//				identityCard.setIssuedBy(paramMap.get("issued_by"));
//				identityCard.setValidDate(paramMap.get("valid_date"));
				identityCard.setCreateTime(new Date());
				identityCard.setUpdateTime(new Date());
				identityCard.setVerifySource(2);//商湯
				
				// 第三步：保存身份证正面信息
				flag = bwExternalIdentityCardService.saveBwIdentityCard(identityCard)>0;
			} else if(null != identityCard) {
				// 第二步：更新身份证正面信息
				if ("front".equals(side)) {
					identityCard.setIdCardNumber(info.getString("number"));//身份证号
					identityCard.setAddress(info.getString("address"));//地址
					identityCard.setBirthday(birthday);//生日
					identityCard.setGender(info.getString("gender"));//性别
					identityCard.setName(info.getString("name"));//姓名
					identityCard.setRace(info.getString("nation"));//名族
					identityCard.setUpdateTime(new Date());
					identityCard.setVerifySource(2);//商湯
					flag = bwExternalIdentityCardService.updateBwIdentityCard(identityCard)>0;
				}else if("back".equals(side)) {
					// 第三步：更新身份证反面信息
					identityCard.setIssuedBy(info.getString("authority"));
					identityCard.setValidDate(info.getString("timelimit"));
					identityCard.setUpdateTime(new Date());
					flag = bwExternalIdentityCardService.updateBwIdentityCard(identityCard)>0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存身份证信息异常,入参信息：{}，异常信息：{}",idcardInfo.toJSONString(),e.getMessage());
		}
		return flag;
	}
	
	@Deprecated
	public long formatId(String idcardNumber) {
		idcardNumber = idcardNumber.toLowerCase().replaceAll("x", "0");
		String id = idcardNumber.substring(idcardNumber.length()-4)+DateUtil.getDateString(new Date(), "ssSSS");
		return -Long.valueOf(id);
	}
	
//	public static void main(String[] args) {
//		SenseTimeServiceImpl impl = new SenseTimeServiceImpl();
//		
//		//照片对比
//		String firstImage = "D:\\first_20180828131319.jpg";
//		String secondImage = "D:\\second_20180828131304.jpg";
//		try {
//			String firstImg = Base64Utils.encodeFile(firstImage);
//			String secondImg = Base64Utils.encodeFile(secondImage);
//			Map<String, Object> result = impl.imageVerification(firstImg, secondImg);
//			System.out.println(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	public static void main(String[] args) {
//		String imageUrl = "http://img.beadwallet.com/upload/backend/2018-11-27/17980899_03.jpg";
//		String result =SenseTimeSDKService.uploadImageByUrl(imageUrl);
//		JSONObject object = JSON.parseObject(result);
//		
//		String imageId = object.getString("result");
//		String name = "张三";
//		String idcard = "420821199003283531";
//		result = SenseTimeSDKService.idnumberVerificationByImageId(name, idcard, imageId);
//		System.out.println(result);
//	}

	
}
