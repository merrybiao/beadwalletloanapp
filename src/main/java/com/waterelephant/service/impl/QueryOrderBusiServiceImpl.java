package com.waterelephant.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.serve.SystemService;
import com.waterelephant.entity.BwCreditRecord;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.mapper.BwBorrowerMapper;
import com.waterelephant.mapper.BwOverdueRecordMapper;
import com.waterelephant.mohe.service.MoheBussinessService;
import com.waterelephant.rongCarrier.service.Rong360BussinessService;
import com.waterelephant.service.BwCreditRecordService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.QueryOrderBusiService;
import com.waterelephant.utils.AliyunOSSUtil;
import com.waterelephant.utils.GzipUtil;

/**
 * 查询工单结果
 * <p> 根据身份证号(idcardNo)、姓名(name)、手机号(phone) 查询用户命中结果 
 * <p> 1、历史逾期天数
 * <p> 2、SAAS标记为进件中工单
 * <p> 3、当前逾期及当前未结清
 * <p> 4、速秒标记为进件中的工单
 * @author dinglinhao
 *
 */
@Service
public class QueryOrderBusiServiceImpl implements QueryOrderBusiService {
	
	private Logger logger = LoggerFactory.getLogger(QueryOrderBusiServiceImpl.class);
	
	@Autowired
	private IBwOrderService bwOrderService;
	
	@Autowired
	private BwOverdueRecordMapper bwOverdueRecordMapper;
	
	@Autowired
	private BwBorrowerMapper bwBorrowerMapper;
	
	@Autowired
	private BwCreditRecordService bwCreditRecordService;
	
	@Autowired
	private Rong360BussinessService rong360BussinessService;
	
	@Autowired
	private MoheBussinessService moheBussinessService;

	@Override
	public Map<String, Object> queryOrder(String idcardNo,String name,String phone) throws Exception {
		Map<String,Object> resultMap = new HashMap<>();
		Long borrowerId = queryBwBorrowerList(idcardNo, phone, name);
		logger.info("--------根据idcardNo:{},name:{},phone:{},borrowerId:{}查询命中结果-------",idcardNo,name,phone,borrowerId);
		
		//命中状态	出参	是否命中： 0未命中 1命中
		boolean flag = false;
		
		if(null == borrowerId) {
			resultMap.put("flag", flag ? "0":"1");
			resultMap.put("overdue_days", "");
			resultMap.put("current_order", flag? "0":"1");
			resultMap.put("overdue_order", flag? "0":"1");
			resultMap.put("current_overdue_days", "");//当前逾期工单逾期最大天数
			resultMap.put("saas_order", flag? "0":"1");
			resultMap.put("sm_order", flag ? "0":"1");
		}else {
			//结果描述	出参	历史逾期天数
			Integer days = queryOverdueMaxDays(borrowerId);
			if(null != days && days.intValue() >0) flag = true;
			//结果描述	出参	SAAS标记为进件中工单
			boolean saasResult = querySaasOrderList(idcardNo, phone);
			if(saasResult) flag =true;
//			//结果描述	出参	当前逾期及当前未结清
//			boolean isOverdueAndUncleared = queryUnderwayOrderCount(borrowerId);
//			if(isOverdueAndUncleared) flag = true;
			
			//命中未结清和逾期工单区分开 2018年10月11日14:18:24 dinglinhao
			//未结清工单
			boolean unclearedOrderResult = queryUnclearedOrderList(borrowerId);
			if(unclearedOrderResult) flag = true;
			//逾期工单
			boolean overdueOrderResult = queryOverdueOrderList(borrowerId);
			if(overdueOrderResult) flag = true;
			//当前逾期工单最大逾期天数
			Integer currOverdueDays = queryCurrentOverdueDays(borrowerId);
			if(currOverdueDays != null && currOverdueDays.intValue() >0) flag = true;
			//结果描述	出参	速秒标记为进件中的工单
			boolean smResult = querySmOrderList(borrowerId);
			if(smResult) flag = true;
			
			resultMap.put("flag", flag ? "0":"1");
			resultMap.put("overdue_days", null == days ? "": String.valueOf(days));//历史逾期最大天数
			resultMap.put("current_order", unclearedOrderResult ? "0":"1");//未结清
			resultMap.put("overdue_order", overdueOrderResult ? "0":"1");//逾期
			resultMap.put("current_overdue_days", null == currOverdueDays ? "": String.valueOf(currOverdueDays));//当前逾期工单逾期最大天数
			resultMap.put("saas_order", saasResult ? "0":"1");
			resultMap.put("sm_order", smResult ? "0":"1");
		}
		logger.info("--------根据idcardNo:{},name:{},phone:{},borrowerId:{}查询命中结果为：{}",idcardNo,name,phone,borrowerId,resultMap.toString());
		return resultMap;
	}
	
	/**
	 * 
	 * @param idcard
	 * @param mobile
	 * @param name
	 * @return
	 */
	private Long queryBwBorrowerList(String idcard,String phone,String name){
		
//		StringBuffer sb = new  StringBuffer("SELECT id FROM bw_borrower");
//		sb.append(" WHERE phone = '").append(phone).append("'");
//		sb.append(" AND id_card ='").append(idcard).append("'");
//		if(!StringUtils.isEmpty(name)) {
//			sb.append(" AND name = '").append(name).append("'");
//		}
//		String sql = sb.toString();
//		return sqlMapper.selectOne(sql, Long.class);
		List<Long> list = bwBorrowerMapper.queryBorrowerId(phone, idcard, name);
		return list == null || list.isEmpty() ? null : list.get(0);
	}
	
	/**
	 * 查询历史逾期天数
	 * <p>查询历史逾期最大天数
	 * @param borrowerId 借款人ID
	 * @return 0 命中 1未命中
	 */
	private Integer queryOverdueMaxDays(Long borrowerId) {
		long star = System.currentTimeMillis();
		Integer days = null;
		try {
			// status_id =6 已结清
//			String sql = "SELECT MAX(t.overdue_day) FROM bw_overdue_record t "
//					+ " LEFT JOIN bw_order o ON t.order_id = o.id "
//					+ " WHERE o.borrower_id = " + borrowerId
//					+ " AND o.status_id = 6";
//			days = sqlMapper.selectOne(sql, Integer.class);
			List<Integer> inStatus = Arrays.asList(6);//已结清
			days = bwOverdueRecordMapper.queryMaxOverdueDays(borrowerId, inStatus);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(">>>>>>查询历史逾期天数 查询历史逾期最大天数 sql异常,borrowerId:{},异常信息：{}",borrowerId,e.getMessage());
		} finally {
			logger.info(">>>>>>根据borrowerId:{}查询历史逾期天数 查询历史逾期最大天数耗时:{}ms<<<<",borrowerId,(System.currentTimeMillis() - star));
		}
		return days;
	}
	
	/**
	 * 查询当前逾期天数
	 * <p>查询历史逾期最大天数
	 * @param borrowerId 借款人ID
	 * @return 0 命中 1未命中
	 */
	private Integer queryCurrentOverdueDays(Long borrowerId) {
		long star = System.currentTimeMillis();
		Integer days = null;
		try {
			List<Integer> inStatus = Arrays.asList(13);//逾期
			days = bwOverdueRecordMapper.queryMaxOverdueDays(borrowerId, inStatus);
			logger.info(">>>>>>查询当前逾期最大天数,borrowerId:{},逾期天数：{}",borrowerId,days);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(">>>>>>查询当前逾期最大天数sql异常,borrowerId:{},异常信息：{}",borrowerId,e.getMessage());
		} finally {
			logger.info(">>>>>>根据,borrowerId:{}查询当前逾期最大天数耗时:{}ms<<<<",borrowerId,(System.currentTimeMillis() - star));
		}
		
		return days;
	}
	
	/**
	 * SAAS标记为进件中工单
	 * @params idcardNo 身份证号
	 * @params phone 手机号
	 * @return 0 命中 1未命中
	 */
	private boolean querySaasOrderList(String idcardNo,String phone) {
		long star = System.currentTimeMillis();
		String result = null;
		try {
			logger.info(">>>>>> idcardNo:{},phone:{}查询SAAS系统黑名单库<<<<<<",idcardNo,phone);
			result = SystemService.queryBlackSaas(phone, idcardNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(">>>>>查询SAAS系统黑名单库异常，idcardNo:{},phone:{},异常信息：{}",idcardNo,phone,e.getMessage());
		} finally {
			logger.info(">>>>>> idcardNo:{},phone:{}查询SAAS系统黑名单库耗时:{}ms<<<<<<",idcardNo,phone,(System.currentTimeMillis()- star));
		}
		return "0".equals(result);
	}
	
	/**
	 * 查询速秒进件中的工单命中结果
	 * @param borrowerId 申请人Id
	 * @return 0 命中 1未命中
	 */
	private boolean querySmOrderList(Long borrowerId) {
		long star = System.currentTimeMillis();
		int count = 0;
		try {
			//未完结工单（进件中的）
//			String productIds = "7,8";//速秒、商城
//			int[] status = new int[] {2,3,4,8,11,12,14};//机审、人审、待签约、撤回、待生成合同、待放款、放款中
//			String statusIn = "2,3,4,8,11,12,14";
//			String inStatus = "2,3,4,9,10,11,12,13,14";
//			String sql = "SELECT COUNT(0) FROM bw_order "
//					+ " WHERE borrower_id = " + borrowerId 
//					+ " AND product_id IN ("+productIds+")"
//					+ " AND status_id IN ("+inStatus+")";
//			
//			count = sqlMapper.selectOne(sql, Integer.class);
			count = bwOrderService.querySmOrderIng(borrowerId);
			logger.info(">>>>>>>根据borrowerId:{}查询速秒进件中的工单，共计：{}单<<<<<<",borrowerId,count);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(">>>>>>根据borrowerId:{}查询速秒进件中的工单异常，异常信息：{}",borrowerId,e.getMessage());
		} finally {
			logger.info(">>>>>>>根据borrowerId:{}查询速秒进件中的工单耗时:{}ms<<<<<",borrowerId,(System.currentTimeMillis() - star));
		}
		return count > 0;
	}
	
	/**
	 * 查询当前未结清工单数
	 * @param borrowerId
	 * @return
	 */
	private boolean queryUnclearedOrderList(Long borrowerId) {
		long star = System.currentTimeMillis();
		int count = 0;
		try {
			count  = bwOrderService.queryUnclearedOrderCount(borrowerId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			logger.info(">>>>根据borrowerId:{}查询当前未结清工单数耗时:{}ms<<<<",borrowerId,(System.currentTimeMillis() - star));
		}
		return count > 0;
	}
	
	/**
	 * 查询逾期工单数
	 * @param borrowerId
	 * @return
	 */
	private boolean queryOverdueOrderList(Long borrowerId) {
		long star = System.currentTimeMillis();
		int count =0; 
		try {
			count = bwOrderService.queryOverdueOrderCount(borrowerId);
		} catch (Exception e) {
			logger.error(">>>>根据borrowerId:{}查询未结清的工单异常，异常信息：{}",borrowerId,e.getMessage());
			e.printStackTrace();
		} finally {
			logger.info(">>>>根据borrowerId:{}查询未结清的工单耗时:{}ms<<<<",borrowerId,(System.currentTimeMillis() - star));
		}
		return count >0;
	}
	
	/**
	 * 当前逾期及当前未结清
	 * @param borrowerId 借款人ID
	 * @return 0 命中 1未命中
	 */
	private boolean queryUnderwayOrderCount(Long borrowerId) {
		long star = System.currentTimeMillis();
		//未结清或逾期
		int count = 0;
		try {
			//待还款、逾期
//			count = bwOrderMapper.queryOrderByStates(borrowerId, Arrays.asList(9,13));
			count = bwOrderService.queryUnderwayOrderCount(borrowerId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(">>>>>根据borrowerId:{}查询逾期或未结清的工单异常，异常信息：{}",borrowerId,e.getMessage());
		} finally {
			logger.info(">>>>>根据borrowerId:{}查询逾期或未结清的工单耗时:{}ms<<<<<",(System.currentTimeMillis() - star));
		}
		return count > 0;
	}

	@Override
	public Map<String, Object> queryOperatorData(BwOrder bwOrder, String appId, boolean gzip) throws Exception {
		//根据授信单Id查询运营商授权记录
		BwCreditRecord creditRecord = bwCreditRecordService.queryByOrderId(bwOrder.getId(), 1);//1：运营商
		//授信单运营商授权记录不存在
		if(null == creditRecord) {
			logger.error("工单："+bwOrder.getOrderNo()+"运营商数据不存在~");
			throw new Exception("运营商数据不存在["+bwOrder.getOrderNo()+"]") ;
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
				return this.rong360BussinessService.queryData(queryId, "mobile",appId, gzip);
			}else if(creditThird ==4) {
				return this.moheBussinessService.queryOperatorData(queryId, appId, gzip);
			}
			//TODO creditThird 的其他运营商授权待定
			logger.error("根据queryId:{},creditThird:{}查询运营商数据为空~",queryId,creditThird);
			return null;
		} 
		//最后通过数据库表取数据（暂时不开发）
		else {
			//TODO
		}
		return null;
	}

	@Override
	public Map<String, Object> queryJdData(BwOrder bwOrder, String appId, boolean gzip) throws Exception {
		//根据授信单Id查询运营商授权记录
		BwCreditRecord creditRecord = bwCreditRecordService.queryByOrderId(bwOrder.getId(), 9);//9：京东
		//授信单运营商授权记录不存在
		if(null == creditRecord) {
			logger.error("工单："+bwOrder.getOrderNo()+"运营商数据不存在~");
			throw new Exception("运营商数据不存在["+bwOrder.getOrderNo()+"]") ;
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
			}else if(creditThird ==4) {
				return this.moheBussinessService.queryOperatorData(queryId, appId, gzip);
			}
			//TODO creditThird 的其他运营商授权待定
			logger.error("根据queryId:{},creditThird:{}查询运营商数据为空~",queryId,creditThird);
			return null;
		} 
		//最后通过数据库表取数据（暂时不开发）
		else {
			//TODO
		}
		return null;
	}

	@Override
	public Map<String, Object> queryTaobaoData(BwOrder bwOrder, String appId, boolean gzip) throws Exception {
		//根据授信单Id查询运营商授权记录
		BwCreditRecord creditRecord = bwCreditRecordService.queryByOrderId(bwOrder.getId(), 8);//8：京东
		//授信单运营商授权记录不存在
		if(null == creditRecord) {
			logger.error("工单："+bwOrder.getOrderNo()+"运营商数据不存在~");
			throw new Exception("运营商数据不存在["+bwOrder.getOrderNo()+"]") ;
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
			}else if(creditThird ==4) {
				return this.moheBussinessService.queryOperatorData(queryId, appId, gzip);
			}
			//TODO creditThird 的其他运营商授权待定
			logger.error("根据queryId:{},creditThird:{}查询运营商数据为空~",queryId,creditThird);
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
		logger.info("----根据filePath:{},获取oss数据耗时：{}ms",filePath,(System.currentTimeMillis() - star));
		
		if(StringUtils.isEmpty(content)) {
			logger.error("根据filePath:{}获取oss数据为空~",filePath);
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
