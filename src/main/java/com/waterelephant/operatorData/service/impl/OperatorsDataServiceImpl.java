package com.waterelephant.operatorData.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.rong360.service.BeadWalletRongExternalService;
import com.waterelephant.entity.BwOrderAuth2;
import com.waterelephant.mapper.BwOrderMapper;
import com.waterelephant.operatorData.Fqgj.servier.impl.FqgjOperatorDataServiceImpl;
import com.waterelephant.operatorData.gnh.service.impl.GnhOperatorDataServiceImpl;
import com.waterelephant.operatorData.haodai.service.impl.HaoDaiOperatorDataImpl;
import com.waterelephant.operatorData.jb.service.impl.JbOperatorDataServiceImpl;
import com.waterelephant.operatorData.jdq.servier.impl.JdqOperatorDataServiceImpl;
import com.waterelephant.operatorData.jqks.service.impl.JqksOperatorDataServiceImpl;
import com.waterelephant.operatorData.kabao.service.impl.KaBaoOperatorDataImpl;
import com.waterelephant.operatorData.kaniu.service.impl.KaNiuOperatorDataServiceImpl;
import com.waterelephant.operatorData.kuainiu.service.impl.KuaNiuOperatorDataServiceImpl;
import com.waterelephant.operatorData.lfq.service.impl.LfqOperatorDataServiceImpl;
import com.waterelephant.operatorData.mf.service.impl.MfOperatorDataServiceImpl;
import com.waterelephant.operatorData.rongshu.service.impl.RongsShuOperatorDataImpl;
import com.waterelephant.operatorData.ryt.servier.impl.RytOperatorDataImpl;
import com.waterelephant.operatorData.sdd.service.impl.SddOperatorDataImpl;
import com.waterelephant.operatorData.service.OperatorsDataService;
import com.waterelephant.operatorData.suMiao.service.impl.SuMaioOperatorDataImpl;
import com.waterelephant.operatorData.xg.service.impl.XgOperatorDataServiceImpl;
import com.waterelephant.operatorData.xjbk.service.impl.XjbkOperatorDataServiceImpl;
import com.waterelephant.operatorData.xl.service.impl.XlOperatorDataServiceImpl;
import com.waterelephant.operatorData.xygj.service.impl.XygjOperatorDataImpl;
import com.waterelephant.operatorData.zlqb.service.impl.ZlqbOperatorDataImpl;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.impl.BwBorrowerService;

/**
 * 该类不在做维护 请使用新类 OperatorsDataPackage.java
 * <p>新类中包含多数据源查询
 * @date 2019年1月2日17:24:09
 * @author dinglinhao
 *
 */
@Deprecated
@Service
public class OperatorsDataServiceImpl implements OperatorsDataService {
	
	private Logger logger = LoggerFactory.getLogger(OperatorsDataServiceImpl.class);
	
	@Autowired
	private BwOrderMapper orderMapper;
	
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	
	@Autowired
	private BwBorrowerService bwBorrowerService;
	
	@Autowired
	private XgOperatorDataServiceImpl xgOperatorDataService;
	
	@Autowired
	private XjbkOperatorDataServiceImpl xjbkOperatorDataService;
	
	@Autowired
	private FqgjOperatorDataServiceImpl fqgjoperatordataserviceimpl;
	
	@Autowired
	private JdqOperatorDataServiceImpl jdqoperatordataserviceimpl;
	
	@Autowired
	private JbOperatorDataServiceImpl jboperatordataserviceimpl;

	@Autowired
	private LfqOperatorDataServiceImpl lfqoperatordataserviceimpl;
	
	@Autowired
	private KaNiuOperatorDataServiceImpl kaniuoperatordataserviceimpl;
	
	@Autowired
	private GnhOperatorDataServiceImpl gnhoperatordataserviceimpl;
	
	@Autowired
	private XlOperatorDataServiceImpl xloperatordataserviceimpl;
	
	@Autowired
	private KuaNiuOperatorDataServiceImpl kuaniuoperatordataserviceimpl;
	
	@Autowired
	private SddOperatorDataImpl sddOperatorDataImpl;
	
	@Autowired
	private KaBaoOperatorDataImpl kaBaoOperatorDataImpl;
	
	@Autowired
	private HaoDaiOperatorDataImpl haoDaiOperatorDataImpl;
	
	@Autowired
	private RongsShuOperatorDataImpl rongsShuOperatorDataImpl;

	@Autowired
	private XygjOperatorDataImpl xygjOperatorDataImpl;
	
	@Autowired
	private RytOperatorDataImpl rytOperatorDataImpl;
	
	@Autowired
	private ZlqbOperatorDataImpl zlqbOperatorDataImpl;
	
	@Autowired
	private JqksOperatorDataServiceImpl jqksOperatorDataServiceImpl;
	
	@Autowired
	private SuMaioOperatorDataImpl suMaioOperatorDataImpl;
	
	@Autowired
	private MfOperatorDataServiceImpl mfOperatorDataServiceImpl;
	
	@Override
	public Map<String, Object> packageOperatorsData(Long borrowerId,Long orderId) {
		Long channel = orderMapper.getOrderChannel(orderId);
		if(null == channel) return null;
		
		return packageOperatorsData(borrowerId,orderId, channel.intValue());
	}
	
	@Override
	public Map<String, Object> packageOperatorsData(Long borrowerId,Long orderId,int channel) {
		Map<String,Object> data = new HashMap<>();
		//根据工单号找授权渠道号，bw_order_auth auth_type=1运营商授权 2018年9月19日14:30:06 dinglinhao
		BwOrderAuth2 bwOrderAuth = bwOrderAuthService.selectBwOrderAuth2(orderId, 1);
		if(null != bwOrderAuth && null != bwOrderAuth.getAuthChannel()) {
			logger.info("----【运营商数据】----borrowerId：{}，orderId：{},channel:{}的运营商授权渠道：{}----",borrowerId,orderId,channel,bwOrderAuth.getAuthChannel());
			channel = bwOrderAuth.getAuthChannel();
		}
		List<Object> dataList = new ArrayList<>();
		logger.info("----【运营商数据】----根据borrowerId：{}，orderId：{},channel:{}获取运营商数据----",borrowerId,orderId,channel);
		long start = System.currentTimeMillis();
		switch(channel) {
			case 833://现金白卡 833
			case 1043://现金白卡 1043
				dataList = xjbkOperatorDataService.getOperatorDataList(borrowerId,orderId);
				break;
			case 820:
				dataList = fqgjoperatordataserviceimpl.getOperatorDataList(borrowerId,orderId);
				break;
			case 862:
				dataList = jdqoperatordataserviceimpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 876:
				dataList = jboperatordataserviceimpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 965://正式环境渠道号【龙分期】
				dataList = lfqoperatordataserviceimpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 837://正式环境渠道号【卡牛】
				dataList = kaniuoperatordataserviceimpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 900://正式环境渠道号【给你花】
				dataList = gnhoperatordataserviceimpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 910://正式环境渠道号【新浪】
				dataList = xloperatordataserviceimpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 966://正式环境渠道号【快牛】
				dataList = kuaniuoperatordataserviceimpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 887://正式环境渠道号【闪电贷】
				dataList = sddOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 881://正式环境渠道号【51卡包】  126部署测试OK,没部署上线
				dataList = kaBaoOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 850://正式环境渠道号【好贷】   126部署测试OK,没部署上线  //没数据
				dataList = haoDaiOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 840://正式环境渠道号【榕树】   126部署测试OK,没部署上线
				dataList = rongsShuOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 883://正式环境渠道号【信用管家】 126部署测试OK,没部署上线
				dataList = xygjOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 809://正式环境渠道号【容易推】   126部署测试OK,没部署上线,正式环境渠道号需更改,正式809
				dataList = rytOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 1018://正式环境渠道号【助力钱包】  正式环境渠道号需更改,正式1018
				dataList = zlqbOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 1052:
				dataList = jqksOperatorDataServiceImpl.getOperatorDataList(borrowerId, orderId);
				break;
			default:
				String searchId = xgOperatorDataService.getSearchId(borrowerId);
				if(StringUtils.isEmpty(searchId)) {
					//根据bw_borrower表的appid来区分速秒、七七、乐分期  
					Integer appid = bwBorrowerService.getAppid(borrowerId);
					logger.info("searchId为空，依据appid：{}查询。",appid);
					switch(appid){
					case 1: //1为速秒
						dataList = suMaioOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
						break;
					case 2: //2七七钱包
					case 3: //3乐分期
						dataList = mfOperatorDataServiceImpl.getOperatorDataList(borrowerId, orderId);
						break;
					}
				} else
					return getOperatorDataList(searchId);
				break;
		}
		long end = System.currentTimeMillis();
		logger.info("----【运营商数据】----根据borrowerId：{}，orderId：{},channel:{}获取运营商数据,耗时：{}ms----",borrowerId,orderId,channel,(end-start));
		//认证用户列表
		data.put("data_list", dataList);
		return data;
	}
	
	private Map<String, Object> getOperatorDataList(String searchId){
		String result = BeadWalletRongExternalService.getData(searchId,"mobile",false);
		JSONObject jsonResult = JSON.parseObject(result);
		JSONObject mobileData = jsonResult.getJSONObject("wd_api_mobilephone_getdatav2_response");
		if(!StringUtils.isEmpty(mobileData)){
			return mobileData.getJSONObject("data");
		}
		return new HashMap<>();
	}
}
