package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.beadwallet.service.entity.request.GbossReqData;
import com.beadwallet.service.entity.request.QhzxReqData;
import com.beadwallet.service.entity.request.YxafReqData;
import com.beadwallet.service.entity.request.ZmxyReqData;
import com.beadwallet.service.entity.response.QueryBlackListRspData;
import com.beadwallet.service.entity.response.QueryCreditScoreRspData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entity.response.ivsRspData;
import com.beadwallet.service.entiyt.middle.QueryData;
import com.beadwallet.service.entiyt.middle.VerifyData;
import com.beadwallet.service.serve.BeadWalletGbossService;
import com.beadwallet.service.serve.BeadWalletQhzxService;
import com.beadwallet.service.serve.BeadWalletYxafService;
import com.beadwallet.service.serve.BeadWalletZmxyService;
import com.waterelephant.service.IdentityService;

@Service
public class IdentityServiceImpl implements IdentityService {

	//前海身份认证
	@Override
	public Response<VerifyData> verify(QhzxReqData qhzxReqData) {
		return BeadWalletQhzxService.verify(qhzxReqData);
	}

	//芝麻信用反欺诈
	@Override
	public Response<ivsRspData> ivs(ZmxyReqData zmxyReqData){
		return BeadWalletZmxyService.ivsDetail(zmxyReqData);
	}

	//国政通身份验证
	@Override
	public Response<String> queryGbossMessage(GbossReqData gbossReqData) {
		return BeadWalletGbossService.queryGbossMessage(gbossReqData);
	}

	//国政通不良信息验证
	@Override
	public Response<String> blackList(GbossReqData gbossReqData) {
		return BeadWalletGbossService.blackList(gbossReqData);
	}

	//前海风险提示度
	@Override
	public Response<QueryData> query(QhzxReqData qhzxReqData) {
		return BeadWalletQhzxService.query(qhzxReqData);
	}

	//宜信阿福风险名单
	@Override
	public Response<QueryBlackListRspData> queryBlackList(YxafReqData yxafReqData) {
		return BeadWalletYxafService.queryBlackList(yxafReqData);
	}

	@Override
	public Response<QueryCreditScoreRspData> queryCreditScore(YxafReqData yxafReqData) {
		return BeadWalletYxafService.queryCreditScore(yxafReqData);
	}

}
