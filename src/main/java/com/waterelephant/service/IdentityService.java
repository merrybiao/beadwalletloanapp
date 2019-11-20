package com.waterelephant.service;

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

/**
 * 身份认证
 * @author huwwei
 *
 */
public interface IdentityService {

	//国政通 身份认证
	public Response<String> queryGbossMessage(GbossReqData gbossReqData);
	//国政通不良信息验证
	public Response<String> blackList(GbossReqData gbossReqData);
	//前海身份认证
	public Response<VerifyData> verify(QhzxReqData qhzxReqData);
	//前海风险提示度
	public Response<QueryData> query(QhzxReqData qhzxReqData);
	//宜信阿福风险名单
	public Response<QueryBlackListRspData> queryBlackList(YxafReqData yxafReqData);
	//宜信阿福致诚分
	public Response<QueryCreditScoreRspData> queryCreditScore(YxafReqData yxafReqData);
	//芝麻信用反欺诈
	public Response<ivsRspData> ivs(ZmxyReqData zmxyReqData);
}
