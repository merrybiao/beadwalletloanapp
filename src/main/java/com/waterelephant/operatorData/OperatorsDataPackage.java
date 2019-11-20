package com.waterelephant.operatorData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.rong360.service.BeadWalletRongExternalService;
import com.waterelephant.dataSource.DataSource;
import com.waterelephant.dataSource.DataSourceHolderManager;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth2;
import com.waterelephant.operatorData.Fqgj.servier.impl.FqgjOperatorDataServiceImpl;
import com.waterelephant.operatorData.bld.service.impl.BldOperatorDataImpl;
import com.waterelephant.operatorData.dkgj.service.impl.DkgjOperatorDataServiceImpl;
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
import com.waterelephant.operatorData.mgj.service.impl.MgjOperatorDataImpl;
import com.waterelephant.operatorData.rongshu.service.impl.RongsShuOperatorDataImpl;
import com.waterelephant.operatorData.ryt.servier.impl.RytOperatorDataImpl;
import com.waterelephant.operatorData.sdd.service.impl.SddOperatorDataImpl;
import com.waterelephant.operatorData.service.impl.OperatorsDataAbstractService;
import com.waterelephant.operatorData.suMiao.service.impl.SuMaioOperatorDataImpl;
import com.waterelephant.operatorData.xg.service.impl.XgOperatorDataServiceImpl;
import com.waterelephant.operatorData.xjbk.service.impl.XjbkOperatorDataServiceImpl;
import com.waterelephant.operatorData.xl.service.impl.XlOperatorDataServiceImpl;
import com.waterelephant.operatorData.xygj.service.impl.XygjOperatorDataImpl;
import com.waterelephant.operatorData.zlqb.service.impl.ZlqbOperatorDataImpl;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.utils.GzipUtil;
/**
 * 打包运营商数据 
 * @author dinglinhao
 *
 */
@Service
public class OperatorsDataPackage {
	
	private Logger logger = LoggerFactory.getLogger(OperatorsDataPackage.class);
	
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	
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
	
	@Autowired
	private BldOperatorDataImpl bldOperatorDataImpl;
	
	@Autowired
	private DkgjOperatorDataServiceImpl dkgjOperatorDataServiceImpl;
	
	@Resource(name = "xmdOperatorDataImpl")
	private OperatorsDataAbstractService xmd;
	
	@Resource(name = "yhwxOperatorDataServiceImpl")
	private OperatorsDataAbstractService yhwx;
	
	@Resource(name = "lfqKaBaoOperatorDataImpl")
	private OperatorsDataAbstractService lfqKaBao;
	
	@Autowired
	private MgjOperatorDataImpl mgjOperatorDataImpl;
	
	public List<Object> packageOperatorsDataV2(BwBorrower bwBorrower, BwOrder bwOrder,
			BwOrderAuth2 bwOrderAuth,boolean gzip) {
		try {
			//借款人ID
			Long borrowerId = bwBorrower.getId();
			//工单ID
			Long orderId = bwOrder.getId();
			//渠道(如果工单授权渠道为空，则取工单渠道)
			Integer channel = null == bwOrderAuth ? bwOrder.getChannel() : bwOrderAuth.getAuthChannel();
			//注册产品
			Integer appid = bwBorrower.getAppId();
			
			List<Object> dataList = null;
			
			DataSourceHolderManager.set(DataSource.MASTER_NEW);
			String xgSearchId = xgOperatorDataService.getSearchId(borrowerId);
			dataList = getOperatorData(borrowerId,orderId,channel,appid,xgSearchId);
			if(null == dataList) {
				DataSourceHolderManager.set(DataSource.MASTER);
				xgSearchId = xgOperatorDataService.getSearchId(borrowerId);
				dataList = getOperatorData(borrowerId,orderId,channel,appid,xgSearchId);
			}
			
			return null == dataList ? defaultDataList() : dataList;
		} finally {
			DataSourceHolderManager.reset();
		}
	}
	
	public Map<String,Object> packageOperatorsDataV3(BwOrder bwOrder,boolean gzip) {
		try {
			
			//工单ID
			Long orderId = bwOrder.getId();
			//借款人ID
			Long borrowerId = bwOrder.getBorrowerId();
			//借款人信息
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
			//工单运营商授权记录
			BwOrderAuth2 bwOrderAuth = bwOrderAuthService.selectBwOrderAuth2(orderId, 1);
			//渠道(如果工单授权渠道为空，则取工单渠道)
			Integer channel = null == bwOrderAuth ? bwOrder.getChannel() : bwOrderAuth.getAuthChannel();
			//注册产品
			Integer appid = bwBorrower.getAppId();
			
			List<Object> dataList = null;
			
			DataSourceHolderManager.set(DataSource.MASTER_NEW);
			String xgSearchId = xgOperatorDataService.getSearchId(borrowerId);
			dataList = getOperatorData(borrowerId,orderId,channel,appid,xgSearchId);
			if(null == dataList) {
				DataSourceHolderManager.set(DataSource.MASTER);
				xgSearchId = xgOperatorDataService.getSearchId(borrowerId);
				dataList = getOperatorData(borrowerId,orderId,channel,appid,xgSearchId);
			}
		
			//渠道运营商数据封装成融360数据格式返回 type==mobile 2018年8月20日11:08:56 dinglinhao
			Map<String,Object> data = new HashMap<>();
			//运营商数据列表
			data.put("data_list", null ==  dataList ? defaultDataList() : dataList);
			JSONObject mobileResponse = new JSONObject();
			mobileResponse.put("data", data);
			//是否选择压缩运营商相应结果
			Object result = gzip ? GzipUtil.gzip(mobileResponse.toJSONString()) : mobileResponse;
			
			Map<String,Object> response = new HashMap<>();
			response.put("error", 200);
			response.put("msg", "处理成功");
			response.put("wd_api_mobilephone_getdatav2_response", result);
			return response;
			
		} finally {
			DataSourceHolderManager.reset();
		}
	}
		
	private List<Object> getOperatorData(Long borrowerId,Long orderId,Integer channel,Integer appid,String xgSearchId){
		logger.info("----【运营商数据】----根据borrowerId：{}，orderId：{},channel:{}获取运营商数据----",borrowerId,orderId,channel);

		long start = System.currentTimeMillis();

		List<Object> dataList = new ArrayList<>();
		//1、根据渠道号，查询运营商数据
		switch(channel) {
			case 833://现金白卡 833
//			case 1043://现金白卡 1043 在default中通过appid判断查询运营商 
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
			case 938:
				dataList = bldOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
//			case 1052:  //借钱快手 通过default中appid判断取运营商
//				dataList = jqksOperatorDataServiceImpl.getOperatorDataList(borrowerId, orderId);
//				break;
			case 1124: //贷款管家
				dataList = dkgjOperatorDataServiceImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 1163: //星美贷
				dataList = xmd.getOperatorDataList(borrowerId, orderId);
				break;
			case 1169: // 云禾网信
				dataList = yhwx.getOperatorDataList(borrowerId, orderId);
				break;
			case 1125: // 优米管家
				dataList = mgjOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 1060: // 乐分期51卡宝
				dataList = lfqKaBao.getOperatorDataList(borrowerId, orderId);
				break;
			default:
				logger.info("----[查询运营商数据]channel:{}未匹配到相应渠道----",channel);
				if(StringUtils.isEmpty(xgSearchId)) {
					dataList = getOperatorDataListByAppid(borrowerId, orderId, appid);
				}else if("-1".equals(xgSearchId)) { //>30Days
					dataList = xgOperatorDataService.getOperatorDataList(borrowerId, orderId);
				}else { // <= 30Days
					dataList = getOperatorDataList(xgSearchId);
				}
				break;
		}

		long end = System.currentTimeMillis();
		logger.info("----【运营商数据】----根据borrowerId：{}，orderId：{},channel:{}获取运营商数据,耗时：{}ms----",borrowerId,orderId,channel,(end-start));
		return dataList;
	}
	
	public List<Object> getOperatorDataListByAppid(Long borrowerId, Long orderId,Integer appid){
		//根据appId查询运营商数据 
		List<Object> dataList = new ArrayList<>();
		switch(appid){
			case 1: //1为速秒
				dataList = suMaioOperatorDataImpl.getOperatorDataList(borrowerId, orderId);
				break;
			case 2: //七七钱包
			case 3: //乐分期
			case 4: //速秒分期
			case 5: //及花分期
				dataList = mfOperatorDataServiceImpl.getOperatorDataList(borrowerId, orderId);
				break;
		}
		return dataList;
	}
	
//	public String queryXgSearchId(Long borrowerId) {
//		String searchId = null;
//		
//		DataSourceHolderManager.set(DataSource.MASTER_NEW);
//		searchId = xgOperatorDataService.getSearchId(borrowerId);
//		if(StringUtils.isEmpty(searchId)) {
//			DataSourceHolderManager.set(DataSource.MASTER);
//			searchId = xgOperatorDataService.getSearchId(borrowerId);
//		}
//		return searchId;
//	}
	
	private List<Object> getOperatorDataList(String searchId){
		String result = BeadWalletRongExternalService.getData(searchId,"mobile",false);
		JSONObject jsonResult = JSON.parseObject(result);
		JSONObject mobileData = jsonResult.getJSONObject("wd_api_mobilephone_getdatav2_response");
		if(!StringUtils.isEmpty(mobileData)){
			return mobileData.getJSONObject("data").getJSONArray("data_list");
		}
		return new ArrayList<>();
	}
	
	private List<Object> defaultDataList(){
		Map<String,Object> data = new HashMap<>();
		data.put("userdata", new HashMap<>());
		data.put("rechargedata", new ArrayList<>());
		data.put("msgdata", new ArrayList<>());
		data.put("teldata", new ArrayList<>());
		data.put("billdata", new ArrayList<>());
		data.put("familydata", new ArrayList<>());
		data.put("netlogdata", new ArrayList<>());
		data.put("monthinfo", new HashMap<>());
		data.put("netdata", new ArrayList<>());
		List<Object> dataList = new ArrayList<>();
		dataList.add(data);
		return dataList;
	}
}
