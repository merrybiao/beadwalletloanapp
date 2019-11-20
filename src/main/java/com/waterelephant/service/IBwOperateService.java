package com.waterelephant.service;

import java.util.List;

import com.beadwallet.service.entity.request.RongOperateReqData;
import com.beadwallet.service.entity.response.OperateRsp;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entity.response.RongOperateLogin;
import com.beadwallet.service.entity.response.RongOprerateMsgCodeRsqData;
import com.beadwallet.service.entiyt.middle.RongOperateDataList;
import com.waterelephant.dto.OperateCheck;

public interface IBwOperateService {

	// //创建任务
	// public Response<Object> task(MoxieReqData moxieReqData);
	// //轮询状态
	// public Response<Object> getStatus(String taskId);
	// //输入短信验证码
	// public Response<Object> inputCode(String taskId,String code);

	// 运营商认证
	// public Response<Object> operate(String mobile,String password,String name,String idCard,String userId);

	// 根据手机号查询通话记录
	public List<OperateCheck> getVoiceCallByMobile(String mobile);

	// 获取登录规则
	public Response<Object> getLoginRule(String phone);

	// 登录
	public Response<Object> login(RongOperateReqData rongOperateDto);

	// 提交短信验证码
	public Response<Object> submitMsgCode(String phone, String code, String name, String idCard, String userId,
			String picCode);

	// 刷新短信验证码
	public Response<Object> refreshMsgCode(String phone, String messageCodeType, String userId);

	// 请求短信验证码
	public Response<RongOprerateMsgCodeRsqData> getMsgCode(String phone, String name, String idCard,
			String messagecodeType);

	// 请求图片验证码
	public Response<RongOperateLogin> getPicCode(RongOperateReqData rongOperateReqData, String picCodeType);

	// //提交图片验证码
	// public Response<Object> submitPicCode(String phone,String code,String name,String idCard,String userId);

	// 刷新图片验证码
	public Response<Object> refreshPicCode(String phone, String picCodeType, String userId);

	// 拉取数据
	public Response<Object> saveData(String userId, String search_id) throws Exception;

	// 根据通话记录最多的top10 与通讯录进行比较
	public int count(List<String> list, Long borrowerId);

	/**
	 * @author 崔雄健
	 * @date 2017年3月31日
	 * @description
	 * @param
	 * @return
	 */
	public Response<Object> getData(String userId);
	
	/**
	 * 保存数据到速秒分库
	 * @param borrowerId
	 * @param result
	 * @return
	 */
	public void saveDataV2(Long borrowerId, String searchId,List<RongOperateDataList> dataLists);

	// 电话号码的实名认证信息是否与借款人相同

	// 运营商授权时间-运营商注册时间＜30天

	// 3个月内呼入与呼出前10的重叠个数≤1

	// 3个月内呼入与呼出前10的重叠个数≤1

}
