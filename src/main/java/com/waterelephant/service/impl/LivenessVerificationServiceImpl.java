package com.waterelephant.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
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
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwBorrowerCredit;
import com.waterelephant.entity.BwCreditAdjunct;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.service.BwBorrowerCreditService;
import com.waterelephant.service.BwCreditAdjunctService;
import com.waterelephant.service.BwCreditIdentityVerifyResultService;
import com.waterelephant.service.BwCreditVerifySourceService;
import com.waterelephant.service.BwIdentityLivenessRecordService;
import com.waterelephant.service.BwIdentityVerifyResultService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwVerifySourceService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.LivenessVerificationService;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.VerifySource;

@Service
public class LivenessVerificationServiceImpl implements LivenessVerificationService {
	
	private Logger logger = LoggerFactory.getLogger(LivenessVerificationServiceImpl.class);
	
//	private static final String SIDE_FRONT = "front";
//	
//	private static final String SIDE_BACK = "back";
	
	private static final String HTTP_URL = "http://img.beadwallet.com/";
	
//	private static final String HTTP_URL = "http://waterelephant.oss-cn-shanghai.aliyuncs.com/";
	
	private static Float Threshold = 0.7f;
	
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	
	@Autowired
	private BwVerifySourceService bwVerifySourceService;
	
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	
	@Autowired
	private BwIdentityVerifyResultService bwIdentityVerifyResultService;
	
	@Autowired
	private IBwOrderService bwOrderService;
	
	@Autowired
	private IBwBorrowerService bwBorrowerService; 
	
	@Autowired
	private BwIdentityLivenessRecordService bwIdentityLivenessRecordService;

	@Override
	public boolean savelivenessVerificationByOrder(String orderNo) {
		//根据工单号查询工单
		BwOrder order = this.bwOrderService.findBwOrderByOrderNo(orderNo);
		
		if(null == order) throw new BusinessException("查无此工单："+orderNo);
		
		//借款人ID
		Long borrowerId = order.getBorrowerId();
		//获取借款人信息
		BwBorrower borrower = this.bwBorrowerService.findBwBorrowerById(order.getBorrowerId());
		
		if(null == borrower) throw new BusinessException("查无此借款人信息，根据工单："+orderNo);
		
		//工单ID
		Long orderId = order.getId();
		
		logger.info("----活体检测--orderNo:{},orderId:{},borrowerId:{}------",orderNo,orderId,borrowerId);
		
		//根据工单ID获取附件信息，按照附件类型返回
		Map<Integer,BwAdjunct> adjunctMap = this.bwAdjunctService.queryBwAdjunctByOrderId(orderId); 
		
		//附件列表为空
		if(null == adjunctMap || adjunctMap.isEmpty()) return true;
		
		//工单渠道编号
		Integer channel = order.getChannel();
		
//		//1、身份证正面照检测
//		BwAdjunct adjunct_1 = adjunctMap.get(1);
//		this.OCRFront(borrowerId,orderId,adjunct_1);
//		//2、身份证反面照检测
//		BwAdjunct adjunct_2 = adjunctMap.get(2);
//		this.OCRBack(borrowerId,orderId,adjunct_2);
		//借款人姓名
		String name = borrower.getName();
		//借款人身份证号
		String idcard = borrower.getIdCard();
		
		//手持照
		BwAdjunct adjunct_3 = adjunctMap.get(3);
		//根据姓名+身份证号到三方库查询照片和附件3中的照片比对
		boolean flag = idnumberVerification(borrowerId,orderId,name, idcard, adjunct_3);
		
		if(flag) {
			//3、照片对比
			//根据渠道做活体检测（安卓、IOS做活体文件检测，渠道单做活体照片检测）
			switch (channel) {
				case 1://安卓
				case 2://IOS
					BwAdjunct adjunct_15 = adjunctMap.get(15);
					//4、IOS、安卓 活体文件检测(手持照和五分照比对)
					livenessVerificationByData(orderId,adjunct_3,adjunct_15);
					break;
				default:
					BwAdjunct adjunct_1 = adjunctMap.get(1);
					//5、渠道工单活体照片检测(身份证正面照和手持照比对)
					livenessVerificationByImage(orderId,adjunct_1,adjunct_3);
					break;
			}
		}
		return flag;
	}
	
//	public void OCRFront(Long borrowerId,Long orderId,BwAdjunct adjunct){
//		try {
//			String imageUrl = HTTP_URL + adjunct.getAdjunctPath();
//			Map<String,Object> resultMap = this.senseTimeService.ocrIdcard(imageUrl, null, SIDE_FRONT);
//			boolean flag = this.senseTimeService.saveIdentityCardInfo(resultMap);
//			if(flag) {
//				this.bwVerifySourceService.saveBwVerifySource(adjunct.getId(), borrowerId, orderId, VerifySource.SenseTime.getSource());//商汤认证
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//		}
//	}
//	
//	public void OCRBack(Long borrowerId,Long orderId,BwAdjunct adjunct){
//		try {
//			String imageUrl = HTTP_URL + adjunct.getAdjunctPath();
//			Map<String,Object> resultMap = SenseTimeSDKService.ocrIdcard(imageUrl, null, SIDE_BACK);
//			boolean flag = this.senseTimeService.saveIdentityCardInfo(resultMap);
//			if(flag) {
//				adjunct.setPhotoState(1);
//				adjunct.setUpdateTime(new Date());
//				this.bwAdjunctService.update(adjunct);
//				this.bwVerifySourceService.saveBwVerifySource(adjunct.getId(), borrowerId, orderId, VerifySource.SenseTime.getSource());//商汤认证
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			
//		}
//	}
//	
	/**
	 *	 根据姓名+身份证号到三方库查询照片和附件3中的照片做对比
	 * @param borrowerId 借款人ID
	 * @param orderId 工单ID
	 * @param name 姓名
	 * @param idcard 身份证号
	 * @param adjunct 附件3
	 * @return
	 */
	public boolean idnumberVerification(Long borrowerId,Long orderId,String name,String idcard,BwAdjunct adjunct) {
		//判断附件或附件照片路径是否为空
		if(null == adjunct || StringUtils.isEmpty(adjunct.getAdjunctPath())) return true;
		//返回标记
		boolean flag = false;
		//sdk返回结果
		String result = null;
		//附件中照片URL
		String imageUrl = HTTP_URL + adjunct.getAdjunctPath();
		try {
			//姓名+身份证号+照片url
			result = SenseTimeSDKService.idnumberVerification(name, idcard, imageUrl, null);
			//返回结果为空
			if(StringUtils.isEmpty(result)) return flag;
			//判断是否是JSON字符串
			if(!StringUtil.isJson(result)) return flag;
			//解析json字符串
			JSONObject jsonResult = JSON.parseObject(result);
			//
			Map<String,String> resultMap = new HashMap<>();
			//返回结果代码
			Integer code = jsonResult.getInteger("code");
			//检验分值
			float verifyScore = 0;
			//返回结果代码不为空且为1000（正常）
			if(null != code && 1000 == code.intValue()) {
				//获取校验的分值
				verifyScore = jsonResult.getFloatValue("verification_score");
				//分值大于等于阈值，则认证通过
				if(verifyScore >= Threshold) {
					resultMap.put("code", "000");
					resultMap.put("msg", "认证成功");
					resultMap.put("verifyScore", String.valueOf(verifyScore));
					adjunct.setPhotoState(1);//认证通过
				}else {//分值小于阈值，则认证失败
					resultMap.put("code", "204");
					resultMap.put("msg", "分数过低");
					resultMap.put("verifyScore", String.valueOf(verifyScore));
					resultMap.put("threshold", String.valueOf(Threshold));
					adjunct.setPhotoState(0);//认证失败，低于阈值threshold
				}
			} else {//返回结果代码为空或不等于1000，认证失败
				resultMap.put("code", "999");
				resultMap.put("msg", "认证失败");
				resultMap.put("status", String.valueOf(code));
				resultMap.put("statusMsg", SenseTimeConstant.responseCode.get(code));
				adjunct.setPhotoState(0);//认证失败，返回结果不正确
			}
			//将结果封装到描述字段里
			adjunct.setAdjunctDesc(JSON.toJSONString(resultMap));
			adjunct.setUpdateTime(new Date());
			//更新附件认证结果
			flag = this.bwAdjunctService.update(adjunct)>0;
			if(flag) {
				if(1 == adjunct.getPhotoState()) {
					//更改工代授权表中该工单手持照的认证状态
					flag = this.bwOrderAuthService.updatePhotoStateByOrderId(orderId, 3, adjunct.getPhotoState());
				}
				//保存校验记录
				flag = this.bwVerifySourceService.saveOrUpdateBwVerifySource(adjunct.getId(), borrowerId, orderId, VerifySource.SenseTime.getSource());
			}
			
			if(flag) {
				//结果描述代码
				String resultCode = resultMap.get("code");
				//三方照片和活体照比对
				Integer verifytype = 2;
				//保存校验结果
				 flag = saveIdentityVerifyResult(resultCode, orderId, verifytype, verifyScore,VerifySource.SenseTime);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("----照片活体检测失败orderId:{},name:{},idcard:{},imageUrl:{},检测结果：{}异常--",orderId,name,idcard,imageUrl,result);
		} finally {
			try {
				bwIdentityLivenessRecordService.saveLivenessRecord("0000", VerifySource.SenseTime.getSource().toString(), idcard,name, imageUrl, null, JSON.parseObject(result));
			} catch (Exception e) {
				logger.error("----保存活体请求记录失败------");
			}
		}
		return flag;
	}
	
	
	/**
	 * 身份证正面照和手持照做对比
	 * @param adjunct1
	 * @param adjunct3
	 */
	public boolean livenessVerificationByImage(Long orderId,BwAdjunct adjunct1,BwAdjunct adjunct3) {
		
		//判断附件或附件照片路径是否为空
		if(null == adjunct1 || StringUtils.isEmpty(adjunct1.getAdjunctPath())) return true;
		if(null == adjunct3 || StringUtils.isEmpty(adjunct3.getAdjunctPath())) return true;
		
		boolean flag = false;
		//身份证正面照
		String firstImgUrl = HTTP_URL + adjunct1.getAdjunctPath();
		//手持照
		String secondImgUrl = HTTP_URL + adjunct3.getAdjunctPath();
		//SDK返回结果
		String result = null;
		try {
			//两张照片做对比
			result = SenseTimeSDKService.imageVerificationUrl(firstImgUrl, secondImgUrl);
			if(StringUtils.isEmpty(result)) return flag;
			JSONObject jsonResult = JSON.parseObject(result);
			//返回结果代码
			Integer code = jsonResult.getInteger("code");
			//检验分值
			float verifyScore = 0;
			//结果描述代码
			String resultCode = null;
			//返回结果代码不为空且为1000（正常）
			if(null != code && 1000 == code.intValue()) {
				//获取校验的分值
				verifyScore = jsonResult.getFloatValue("verification_score");
				//分值大于等于阈值，则认证通过
				if(verifyScore >= Threshold) {
					resultCode = "000";
				}else {//分值小于阈值，则认证失败
					resultCode = "204";
				}
			} else {//返回结果代码为空或不等于1000，认证失败
				resultCode = "999";
			}
			//身份证正面照和手持照对比
			Integer verifytype = 1;
			//保存校验结果
			flag = saveIdentityVerifyResult(resultCode, orderId, verifytype, verifyScore,VerifySource.SenseTime);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("----照片活体检测失败orderId:{},imageUrl:{},imageUrl:{},检测结果：{}异常--",orderId,firstImgUrl,secondImgUrl,result);
		} finally {
			try {
				bwIdentityLivenessRecordService.saveLivenessRecord("0000", VerifySource.SenseTime.getSource().toString(), null, null, firstImgUrl + ","+ secondImgUrl, null, JSON.parseObject(result));
			} catch (Exception e) {
				logger.error("----保存活体请求记录失败------");
			}
		}
		return flag;
	}

	/**
	 * 身份证五分照和手持照做对比 
	 * @param adjunct3
	 * @param adjunct15
	 */
	public boolean livenessVerificationByData(Long orderId,BwAdjunct adjunct3,BwAdjunct adjunct15) {
		//判断附件或附件照片路径是否为空
		if(null == adjunct3 || StringUtils.isEmpty(adjunct3.getAdjunctPath())) return true;
		if(null == adjunct15 || StringUtils.isEmpty(adjunct15.getAdjunctPath())) return true;
		boolean flag = false;
		//申明SDK返回结果
		String result = null;
		//五分照
		String firstImgUrl = HTTP_URL + adjunct15.getAdjunctPath();
		//手持照
		String secondImgUrl = HTTP_URL + adjunct3.getAdjunctPath();
		try {
			//两张照片做对比
			result = SenseTimeSDKService.imageVerificationUrl(firstImgUrl, secondImgUrl);
			//返回结果为空
			if(StringUtils.isEmpty(result)) return flag;
			//解析json字符串
			JSONObject jsonResult = JSON.parseObject(result);
			//返回结果代码
			Integer code = jsonResult.getInteger("code");
			//检验分值
			float verifyScore = 0;
			//结果描述代码
			String resultCode = null;
			//返回结果代码不为空且为1000（正常）
			if(null != code && 1000 == code.intValue()) {
				//获取校验的分值
				verifyScore = jsonResult.getFloatValue("verification_score");
				//分值大于等于阈值，则认证通过
				if(verifyScore >= Threshold) {
					resultCode = "000";
				}else {//分值小于阈值，则认证失败
					resultCode = "204";
				}
			} else {//返回结果代码为空或不等于1000，认证失败
				resultCode = "999";
			}
			//五分照和手持照做对比
			Integer verifytype = 1;
			//保存校验结果
			flag = saveIdentityVerifyResult(resultCode, orderId, verifytype, verifyScore,VerifySource.SenseTime);
			
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("----照片活体检测失败orderId,imageUrl:{},imageUrl:{},检测结果：{}异常--",orderId,firstImgUrl,secondImgUrl,result);
		} finally {
			try {
				bwIdentityLivenessRecordService.saveLivenessRecord("0000", VerifySource.SenseTime.getSource().toString(), null, null, firstImgUrl + ","+ secondImgUrl, null, JSON.parseObject(result));
			} catch (Exception e) {
				logger.error("----保存活体请求记录失败------");
			}
		}
		return flag;
	}
	
	private boolean saveIdentityVerifyResult(String resultCode,Long orderId,Integer verifytype,Float score,VerifySource source) {
		
		//校验结果（根据结果描述代码设定）
		Integer verifyResult = null;
		if(!StringUtils.isEmpty(resultCode)) {
			switch (resultCode) {
				case "000":
					verifyResult = 1;
					break;
				case "204":
					verifyResult = 2;
					break;
				case "999":
					verifyResult = 3;
					break;
			}
		}
		
		if(null == verifyResult) {
			logger.error("----保存身份校验结果参数异常，verifyResult:{},resultCode:{}----",verifyResult,resultCode);
			return false;
		}
		//校验分数
		String verifyScore = null == score ? "" : String.valueOf(score);
		//校验来源
		Integer verifySource = source.getSource().intValue();
		//保存校验结果
		return this.bwIdentityVerifyResultService.saveIdentityVerifyResult(orderId,verifySource, verifytype, verifyResult, verifyScore);
	}

	
	//////////////////////////////////////////以下内容为授信单活体检测/////////////////////////////////////////////////////////////////////
	
	@Autowired
	private BwBorrowerCreditService bwBorrowerCreditService;
	
	@Autowired
	private BwCreditAdjunctService bwCreditAdjunctService;
	
	@Autowired
	private BwCreditVerifySourceService bwCreditVerifySourceService;
	
	@Autowired
	private BwCreditIdentityVerifyResultService bwCreditIdentityVerifyResultService;
	
	@Override
	public boolean savelivenessVerificationBycreditNo(String creditNo) {
		
		BwBorrowerCredit credit = bwBorrowerCreditService.queryCreditOrderByCreditNo(creditNo);
		
		if(null == credit) throw new BusinessException("查无此授信单："+creditNo);
		//授信单ID
		Long creditId = credit.getId();
		//借款人ID
		Long borrowerId = credit.getBorrowerId();
		//获取借款人信息
		BwBorrower borrower = this.bwBorrowerService.findBwBorrowerById(borrowerId);
		
		if(null == borrower) throw new BusinessException("查无此借款人信息，根据授信单："+creditNo);
		
		
		logger.info("----活体检测--creditNo:{},creditId:{},borrowerId:{}------",creditNo,creditId,borrowerId);
		
		//根据授信单ID获取附件信息，按照附件类型返回
		Map<Integer,BwCreditAdjunct> adjunctMap = this.bwCreditAdjunctService.queryAdjunctByCreditId(creditId); 
		
		//附件列表为空
		if(null == adjunctMap || adjunctMap.isEmpty()) return true;
		
		//借款人姓名
		String name = borrower.getName();
		//借款人身份证号
		String idcard = borrower.getIdCard();
		
		//手持照
		BwCreditAdjunct adjunct_3 = adjunctMap.get(3);
		//根据姓名+身份证号到三方库查询照片和附件3中的照片比对
		boolean flag = idnumberVerification(borrowerId,creditId,name, idcard, adjunct_3);
		
		if(flag) {
			//3、照片对比
			Integer channel = credit.getChannel();
			if(null == channel) return false;
			//根据渠道做活体检测（安卓、IOS做活体文件检测，渠道单做活体照片检测）
			switch (channel) {
				case 1://安卓
				case 2://IOS
					BwCreditAdjunct adjunct_15 = adjunctMap.get(15);
					//4、IOS、安卓 活体文件检测(手持照和五分照比对)
					livenessVerificationByData(creditId,adjunct_3,adjunct_15);
					break;
				default:
					BwCreditAdjunct adjunct_1 = adjunctMap.get(1);
					//5、渠道工单活体照片检测(身份证正面照和手持照比对)
					livenessVerificationByImage(creditId,adjunct_1,adjunct_3);
					break;
			}
		}
		
		return false;
	}
	
	/**
	 *	 根据姓名+身份证号到三方库查询照片和附件3中的照片做对比
	 * @param borrowerId 借款人ID
	 * @param orderId 工单ID
	 * @param name 姓名
	 * @param idcard 身份证号
	 * @param adjunct 附件3
	 * @return
	 */
	public boolean idnumberVerification(Long borrowerId,Long creditId,String name,String idcard,BwCreditAdjunct adjunct) {
		//判断附件或附件照片路径是否为空
		if(null == adjunct || StringUtils.isEmpty(adjunct.getAdjunctPath())) return true;
		//返回标记
		boolean flag = false;
		//sdk返回结果
		String result = null;
		//附件中照片URL
		String imageUrl = HTTP_URL + adjunct.getAdjunctPath();
		try {
			//姓名+身份证号+照片url
			result = SenseTimeSDKService.idnumberVerification(name, idcard, imageUrl, null);
			//返回结果为空
			if(StringUtils.isEmpty(result)) return flag;
			//判断是否是JSON字符串
			if(!StringUtil.isJson(result)) return flag;
			//解析json字符串
			JSONObject jsonResult = JSON.parseObject(result);
			//
			Map<String,String> resultMap = new HashMap<>();
			//返回结果代码
			Integer code = jsonResult.getInteger("code");
			//检验分值
			float verifyScore = 0;
			//返回结果代码不为空且为1000（正常）
			if(null != code && 1000 == code.intValue()) {
				//获取校验的分值
				verifyScore = jsonResult.getFloatValue("verification_score");
				//分值大于等于阈值，则认证通过
				if(verifyScore >= Threshold) {
					resultMap.put("code", "000");
					resultMap.put("msg", "认证成功");
					resultMap.put("verifyScore", String.valueOf(verifyScore));
//					adjunct.setPhotoState(1);//认证通过
				}else {//分值小于阈值，则认证失败
					resultMap.put("code", "204");
					resultMap.put("msg", "分数过低");
					resultMap.put("verifyScore", String.valueOf(verifyScore));
					resultMap.put("threshold", String.valueOf(Threshold));
//					adjunct.setPhotoState(0);//认证失败，低于阈值threshold
				}
			} else {//返回结果代码为空或不等于1000，认证失败
				resultMap.put("code", "999");
				resultMap.put("msg", "认证失败");
				resultMap.put("status", String.valueOf(code));
				resultMap.put("statusMsg", SenseTimeConstant.responseCode.get(code));
//				adjunct.setPhotoState(0);//认证失败，返回结果不正确
			}
			//将结果封装到描述字段里
			adjunct.setAdjunctDesc(JSON.toJSONString(resultMap));
			adjunct.setThird(VerifySource.SenseTime.getSource().intValue());
			adjunct.setUpdateTime(LocalDateTime.now());
			//更新附件认证结果
			flag = this.bwCreditAdjunctService.update(adjunct)>0;
			if(flag) {
				//保存校验记录
				flag = this.bwCreditVerifySourceService.saveOrUpdateBwVerifySource(adjunct.getId(), borrowerId, creditId, VerifySource.SenseTime.getSource().intValue());
			}
			
			if(flag) {
				//结果描述代码
				String resultCode = resultMap.get("code");
				//三方照片和活体照比对
				Integer verifytype = 2;
				//保存校验结果
				 flag = saveCreditIdentityVerifyResult(resultCode, borrowerId,creditId, verifytype, verifyScore,VerifySource.SenseTime);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("----照片活体检测失败creditId:{},name:{},idcard:{},imageUrl:{},检测结果：{}异常--",creditId,name,idcard,imageUrl,result);
		} finally {
			try {
				bwIdentityLivenessRecordService.saveLivenessRecord("0000", VerifySource.SenseTime.getSource().toString(), idcard,name, imageUrl, null, JSON.parseObject(result));
			} catch (Exception e) {
				logger.error("----保存活体请求记录失败------");
			}
		}
		return flag;
	}
	
	private boolean saveCreditIdentityVerifyResult(String resultCode,Long borrowerId,Long creditId,Integer verifytype,Float score,VerifySource source) {
		
		//校验结果（根据结果描述代码设定）
		Integer verifyResult = null;
		if(!StringUtils.isEmpty(resultCode)) {
			switch (resultCode) {
				case "000":
					verifyResult = 1;
					break;
				case "204":
					verifyResult = 2;
					break;
				case "999":
					verifyResult = 3;
					break;
			}
		}
		
		if(null == verifyResult) {
			logger.error("----保存身份校验结果参数异常，verifyResult:{},resultCode:{}----",verifyResult,resultCode);
			return false;
		}
		//校验分数
		String verifyScore = null == score ? "" : String.valueOf(score);
		//校验来源
		Integer verifySource = source.getSource().intValue();
		//保存校验结果
		return this.bwCreditIdentityVerifyResultService.saveIdentityVerifyResult(borrowerId,creditId,verifySource, verifytype, verifyResult, verifyScore);
	}
	/**
	 * 身份证正面照和手持照做对比
	 * @param adjunct1
	 * @param adjunct3
	 */
	public boolean livenessVerificationByImage(Long creditId,BwCreditAdjunct adjunct1,BwCreditAdjunct adjunct3) {
		
		//判断附件或附件照片路径是否为空
		if(null == adjunct1 || StringUtils.isEmpty(adjunct1.getAdjunctPath())) return true;
		if(null == adjunct3 || StringUtils.isEmpty(adjunct3.getAdjunctPath())) return true;
		
		boolean flag = false;
		//身份证正面照
		String firstImgUrl = HTTP_URL + adjunct1.getAdjunctPath();
		//手持照
		String secondImgUrl = HTTP_URL + adjunct3.getAdjunctPath();
		//SDK返回结果
		String result = null;
		try {
			//两张照片做对比
			result = SenseTimeSDKService.imageVerificationUrl(firstImgUrl, secondImgUrl);
			if(StringUtils.isEmpty(result)) return flag;
			JSONObject jsonResult = JSON.parseObject(result);
			//返回结果代码
			Integer code = jsonResult.getInteger("code");
			//检验分值
			float verifyScore = 0;
			//结果描述代码
			String resultCode = null;
			//返回结果代码不为空且为1000（正常）
			if(null != code && 1000 == code.intValue()) {
				//获取校验的分值
				verifyScore = jsonResult.getFloatValue("verification_score");
				//分值大于等于阈值，则认证通过
				if(verifyScore >= Threshold) {
					resultCode = "000";
				}else {//分值小于阈值，则认证失败
					resultCode = "204";
				}
			} else {//返回结果代码为空或不等于1000，认证失败
				resultCode = "999";
			}
			//身份证正面照和手持照对比
			Integer verifytype = 1;
			//保存校验结果
			flag = saveCreditIdentityVerifyResult(resultCode, adjunct3.getBorrowerId(),creditId, verifytype, verifyScore,VerifySource.SenseTime);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("----照片活体检测失败creditId:{},imageUrl:{},imageUrl:{},检测结果：{}异常--",creditId,firstImgUrl,secondImgUrl,result);
		} finally {
			try {
				bwIdentityLivenessRecordService.saveLivenessRecord("0000", VerifySource.SenseTime.getSource().toString(), null, null, firstImgUrl + ","+ secondImgUrl, null, JSON.parseObject(result));
			} catch (Exception e) {
				logger.error("----保存活体请求记录失败------");
			}
		}
		return flag;
	}

	/**
	 * 身份证五分照和手持照做对比 
	 * @param adjunct3
	 * @param adjunct15
	 */
	public boolean livenessVerificationByData(Long creditId,BwCreditAdjunct adjunct3,BwCreditAdjunct adjunct15) {
		//判断附件或附件照片路径是否为空
		if(null == adjunct3 || StringUtils.isEmpty(adjunct3.getAdjunctPath())) return true;
		if(null == adjunct15 || StringUtils.isEmpty(adjunct15.getAdjunctPath())) return true;
		boolean flag = false;
		//申明SDK返回结果
		String result = null;
		//五分照
		String firstImgUrl = HTTP_URL + adjunct15.getAdjunctPath();
		//手持照
		String secondImgUrl = HTTP_URL + adjunct3.getAdjunctPath();
		try {
			//两张照片做对比
			result = SenseTimeSDKService.imageVerificationUrl(firstImgUrl, secondImgUrl);
			//返回结果为空
			if(StringUtils.isEmpty(result)) return flag;
			//解析json字符串
			JSONObject jsonResult = JSON.parseObject(result);
			//返回结果代码
			Integer code = jsonResult.getInteger("code");
			//检验分值
			float verifyScore = 0;
			//结果描述代码
			String resultCode = null;
			//返回结果代码不为空且为1000（正常）
			if(null != code && 1000 == code.intValue()) {
				//获取校验的分值
				verifyScore = jsonResult.getFloatValue("verification_score");
				//分值大于等于阈值，则认证通过
				if(verifyScore >= Threshold) {
					resultCode = "000";
				}else {//分值小于阈值，则认证失败
					resultCode = "204";
				}
			} else {//返回结果代码为空或不等于1000，认证失败
				resultCode = "999";
			}
			//五分照和手持照做对比
			Integer verifytype = 1;
			//保存校验结果
			flag = saveCreditIdentityVerifyResult(resultCode, adjunct3.getBorrowerId(),creditId, verifytype, verifyScore,VerifySource.SenseTime);
			
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("----照片活体检测失败creditId:{},imageUrl:{},imageUrl:{},检测结果：{}异常--",creditId,firstImgUrl,secondImgUrl,result);
		} finally {
			try {
				bwIdentityLivenessRecordService.saveLivenessRecord("0000", VerifySource.SenseTime.getSource().toString(), null, null, firstImgUrl + ","+ secondImgUrl, null, JSON.parseObject(result));
			} catch (Exception e) {
				logger.error("----保存活体请求记录失败------");
			}
		}
		return flag;
	}
	
}
