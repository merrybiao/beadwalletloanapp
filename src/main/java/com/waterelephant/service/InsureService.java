package com.waterelephant.service;

import com.beadwallet.service.entity.request.InsureLoginReqData;
import com.beadwallet.service.entity.request.InsurePicReqData;
import com.beadwallet.service.entity.response.CityRspData;
import com.beadwallet.service.entity.response.InsureRspData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entiyt.middle.LoginInfoResponse;
import com.beadwallet.service.entiyt.middle.LoginResponse;
import com.beadwallet.service.entiyt.middle.PicResponse;

public interface InsureService {

	//获取登录规则信息  BeadWalletInsureService.getValidCity();
	public Response<LoginInfoResponse> getLoginInfo(Long cityId,Long userId);
	//图片验证码
	public Response<PicResponse> getPicCode(InsurePicReqData insurePicReqData);
	//登录
	public Response<LoginResponse> login(InsureLoginReqData insureLoginReqData);
	//轮询抓取状态
	//抓取数据
	public Response<InsureRspData> getData(String userId);
	//可支持城市查询
	public Response<CityRspData> getValidCity();
}
