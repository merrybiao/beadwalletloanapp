package com.waterelephant.service.impl;


import org.springframework.stereotype.Service;

import com.beadwallet.service.entity.request.InsureLoginReqData;
import com.beadwallet.service.entity.request.InsurePicReqData;
import com.beadwallet.service.entity.response.CityRspData;
import com.beadwallet.service.entity.response.InsureRspData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entiyt.middle.LoginInfoResponse;
import com.beadwallet.service.entiyt.middle.LoginResponse;
import com.beadwallet.service.entiyt.middle.PicResponse;
import com.beadwallet.service.serve.BeadWalletInsureService;
import com.waterelephant.service.InsureService;

@Service
public class InsureServiceImpl implements InsureService {


	
	@Override
	public Response<CityRspData> getValidCity(){
		return BeadWalletInsureService.getValidCity();
	}

	@Override
	public Response<PicResponse> getPicCode(InsurePicReqData insurePicReqData){
		return BeadWalletInsureService.getPicCode(insurePicReqData);
	}

	@Override
	public Response<LoginInfoResponse> getLoginInfo(Long cityId,Long userId) {
		return BeadWalletInsureService.getLoginInfo(cityId,userId);
	}

	@Override
	public Response<LoginResponse> login(InsureLoginReqData insureLoginReqData) {
		return BeadWalletInsureService.login(insureLoginReqData);
	}

	@Override
	public Response<InsureRspData> getData(String userId) {
		Response<InsureRspData> response = BeadWalletInsureService.getData(userId);
		
		return response;
	}

	
}
