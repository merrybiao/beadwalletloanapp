package com.waterelephant.service.impl;

import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwBorrowerCredit;
import com.waterelephant.entity.BwCreditRecord;
import com.waterelephant.mohe.service.MoheBussinessService;
import com.waterelephant.rongCarrier.service.Rong360BussinessService;
import com.waterelephant.service.BwBorrowerCreditService;
import com.waterelephant.service.BwCreditRecordService;
import com.waterelephant.service.CreditBussinessService;
import com.waterelephant.utils.AliyunOSSUtil;
import com.waterelephant.utils.GzipUtil;
/**
 * 授信单业务实现类
 * @author dinglinhao
 *
 */
@Service
public class CreditBussinessServiceImpl implements CreditBussinessService {
	
	private Logger log = LoggerFactory.getLogger(CreditBussinessServiceImpl.class);
	
	@Autowired
	private BwCreditRecordService bwCreditRecordService;
	
	@Autowired
	private BwBorrowerCreditService bwBorrowerCreditService;
	
	@Autowired
	private Rong360BussinessService rong360BussinessService;
	
	@Autowired
	private MoheBussinessService moheBussinessService;
	
	@Override
	public Map<String, Object> queryOperatorData(@NotNull String creditNo, String appId, boolean gzip) throws Exception {
		
		BwBorrowerCredit credit = bwBorrowerCreditService.queryCreditOrderByCreditNo(creditNo);
		//非空判断
		if(null == credit) {
			log.error("根据creditNo：{}查询授信单不存在~",creditNo);
			throw new Exception("授信单信息不存在["+creditNo+"]");
		}

		//根据授信单Id查询运营商授权记录
		BwCreditRecord creditRecord = bwCreditRecordService.queryByCreditId(credit.getId(), 1);//1：运营商
		//授信单运营商授权记录不存在
		if(null == creditRecord) {
			log.error("授信工单："+creditNo+"运营商数据不存在~");
			throw new Exception("运营商数据不存在["+creditNo+"]") ;
		}
		//运营商数据在oss的地址信息
		String filePath = creditRecord.getOssFileUrl();
		//三方查询数据ID
		String queryId = creditRecord.getQueryId();
		
		//如果ossFilePath存在，则通过oss取运营商数据
		if(!StringUtils.isEmpty(filePath)) {
			Integer creditThird = creditRecord.getCreditThird();
			String data= getOssData(filePath);
			return parseDataOrGzip(data, creditThird, gzip);
		} 
		//如果queryId不为空，则通过queryId 和creditThird 从三方取数据
		else if(!StringUtils.isEmpty(queryId)) {
			Integer creditThird = creditRecord.getCreditThird();
			if(creditThird == 3) {
				return this.rong360BussinessService.queryData(queryId, "mobile",appId, gzip);
			}else if(creditThird ==4) {
				return this.moheBussinessService.queryOperatorData(queryId, appId, gzip);
			}
			//TODO creditThird 的其他运营商授权待定
			log.error("根据queryId:{},creditThird:{}查询运营商数据为空~",queryId,creditThird);
			return null;
		} 
		//最后通过数据库表取数据（暂时不开发）
		else {
			//TODO
		}
		return null;
	}

	@Override
	public Map<String, Object> queryJdData(@NotNull String creditNo, String appId, boolean gzip) throws Exception {
		
		BwBorrowerCredit credit = bwBorrowerCreditService.queryCreditOrderByCreditNo(creditNo);
		//非空判断
		if(null == credit) {
			log.error("根据creditNo：{}查询授信单不存在~",creditNo);
			throw new Exception("授信单信息不存在["+creditNo+"]");
		}
		//根据授信单Id查询运营商授权记录
		BwCreditRecord creditRecord = bwCreditRecordService.queryByCreditId(credit.getId(), 9);//9：京东
		//授信单运营商授权记录不存在
		if(null == creditRecord) {
			log.error("授信工单："+creditNo+"京东信息不存在~");
			throw new Exception("京东数据不存在["+creditNo+"]") ;
		}
		//运营商数据在oss的地址信息
		String filePath = creditRecord.getOssFileUrl();
		//三方查询数据ID
		String queryId = creditRecord.getQueryId();
		
		//如果ossFilePath存在，则通过oss取运营商数据
		if(!StringUtils.isEmpty(filePath)) {
			Integer creditThird = creditRecord.getCreditThird();
			String data = getOssData(filePath);
			return parseDataOrGzip(data, creditThird, gzip);
		} 
		//如果queryId不为空，则通过queryId 和creditThird 从三方取数据
		else if(!StringUtils.isEmpty(queryId)) {
			Integer creditThird = creditRecord.getCreditThird();
			if(creditThird == 3) {
				return this.rong360BussinessService.queryData(queryId, "jd",appId, gzip);
			}else if (creditThird == 4) {
				return this.moheBussinessService.queryTaskData(queryId, appId, gzip);
			}
			//TODO creditThird 的其他运营商授权待定
			log.error("根据queryId:{},creditThird:{}查询京东数据为空~",queryId,creditThird);
			return null;
		} 
		//最后通过数据库表取数据（暂时不开发）
		else {
			//TODO
		}
		return null;
	}

	@Override
	public Map<String, Object> queryTaobaoData(@NotNull String creditNo, String appId, boolean gzip) throws Exception {
		BwBorrowerCredit credit = bwBorrowerCreditService.queryCreditOrderByCreditNo(creditNo);
		//非空判断
		if(null == credit) {
			log.error("根据creditNo：{}查询授信单不存在~",creditNo);
			throw new Exception("授信单信息不存在["+creditNo+"]");
		}
		//根据授信单Id查询运营商授权记录
		BwCreditRecord creditRecord = bwCreditRecordService.queryByCreditId(credit.getId(), 8);//8：淘宝
		//授信单运营商授权记录不存在
		if(null == creditRecord) {
			log.error("授信工单："+creditNo+"淘宝数据不存在~");
			throw new Exception("淘宝数据不存在["+creditNo+"]") ;
		}
		//运营商数据在oss的地址信息
		String filePath = creditRecord.getOssFileUrl();
		//三方查询数据ID
		String queryId = creditRecord.getQueryId();
		
		//如果ossFilePath存在，则通过oss取运营商数据
		if(!StringUtils.isEmpty(filePath)) {
			Integer creditThird = creditRecord.getCreditThird();
			String data =  getOssData(filePath);
			return parseDataOrGzip(data, creditThird, gzip);
		} 
		//如果queryId不为空，则通过queryId 和creditThird 从三方取数据
		else if(!StringUtils.isEmpty(queryId)) {
			Integer creditThird = creditRecord.getCreditThird();
			if(creditThird == 3) {
				return this.rong360BussinessService.queryData(queryId, "taobao",appId, gzip);
			}else if (creditThird == 4) {
				return this.moheBussinessService.queryTaskData(queryId, appId, gzip);
			}
			//TODO creditThird 的其他运营商授权待定
			log.error("根据queryId:{},creditThird:{}查询淘宝数据为空~",queryId,creditThird);
			return null;
		} 
		//最后通过数据库表取数据（暂时不开发）
		else {
			//TODO
		}
		return null;
	}
	
	/**
	 * 
	 * @param filePath oss文件路径
	 * @return
	 */
	private String getOssData(String filePath) throws Exception {
		long star = System.currentTimeMillis();
		String content = AliyunOSSUtil.getObject(filePath);
		log.info("----根据filePath:{},获取oss数据耗时：{}ms",filePath,(System.currentTimeMillis() - star));
		
		if(StringUtils.isEmpty(content)) {
			log.error("根据filePath:{}获取oss数据为空~",filePath);
			throw new Exception("获取数据内容为空~") ;
		}
		
		return content;
	}
	
	private JSONObject parseDataOrGzip(String data,Integer third,boolean gzip) {
		JSONObject result = JSON.parseObject(data);
		switch (third) {
		case 3://融360
		
			Set<String> set = result.keySet();
			for(String key :  set) {
				if(key.matches("^wd.*response$")) {
					String content = result.getString(key);
					result.put(key, GzipUtil.gzip(content));
					break;
				}
			}
			break;
		case 4://魔方
			
			Integer code = result.getInteger("code");
			result.put("error", code == 0 ? 200 : code);
			result.put("msg", result.getString("message"));
			result.remove("code");
			result.remove("message");
			if(gzip) {
				String content = result.getString("data");
				result.put("data", GzipUtil.gzip(content));
			}
			break;
		case 1:
		case 2:	
		case 5:
		default:
			break;
		}
		return result;
	}

}
