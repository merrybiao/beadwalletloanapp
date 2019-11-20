package com.waterelephant.haoDai.service;

import java.util.Map;

import com.beadwallet.service.entity.request.PostLoanInfo;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entiyt.middle.HaoDaiResponseData;
import com.waterelephant.dto.RepayDto;

/**
 * 
 * @ClassName: HaoDaiService
 * @Description: TODO(提供好贷网业务接口)
 * @author SongYaJun
 * @date 2016年11月23日 下午3:33:59
 *
 */
public interface HaoDaiService {

	/**
	 * 添加借款人信息
	 * 
	 * @return 借款人id
	 */
	Map<String, Long> addLoanUserInfo(Response<HaoDaiResponseData> haoDaiResponseData, String phone);

	/**
	 * 添加附件以及上传至oss
	 */
	// int addBwBwAdjunctAndOssUpload(BwAdjunct bwAdjunct);

	/**
	 * 添加用户设备信息
	 * 
	 * @param orderId
	 * @return
	 */
	int addBwUserEquipment(Long orderId);

	/**
	 * 获取运营商数据
	 * 
	 * @param borrowerId
	 * @return
	 */
	String getDataBack(Long borrowerId);

	/**
	 * 获取贷款订单状态接口
	 * 
	 * @param orderId 工单id
	 * @return
	 */
	Map<String, String> getLoanStatusData(Long orderId);

	/**
	 * 获取贷后信息
	 * 
	 * @param orderId 我方工单id
	 * @return
	 */
	PostLoanInfo getLoanInfo(Long orderId);

	/**
	 * 获取还款信息,提供好贷作为展示
	 * 
	 * @param orderId 工单ID
	 * @return
	 */
	RepayDto findRepayDto(Long orderId);
}
