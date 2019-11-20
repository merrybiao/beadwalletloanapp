package com.waterelephant.metlife.service.impl;

import static java.util.Arrays.asList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.waterelephant.entity.BwMetlifeInsuranceApplyRecord;
import com.waterelephant.entity.BwMetlifeInsuranceDetail;
import com.waterelephant.entity.BwMetlifeInsuranceOrder;
import com.waterelephant.metlife.dto.MetlifeInsuranceOrderDto;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.metlife.service.BwMetlifeInsuranceApplyRecordService;
import com.waterelephant.metlife.service.BwMetlifeInsuranceDetailService;
import com.waterelephant.metlife.service.BwMetlifeInsuranceOrderService;
import com.waterelephant.metlife.service.MetlifeBusiService;
import com.waterelephant.metlife.vo.MetLifeInsuredVo;

@Service
public class MetlifeBusiServiceImpl implements MetlifeBusiService {
	
	private Logger logger = LoggerFactory.getLogger(MetlifeBusiServiceImpl.class);
	
//	//大都会-保险申请信息
//	private static final String REDIS_INSURE_APPLYINFO = "metlife:applyInsurance";
//	//大都会-保险-工单号(一个保险工单号对应一条申请信息)
//	private static final String REDIS_INSURE_ORDERNO ="metlife:orderNo";
	
	@Autowired
	private BwMetlifeInsuranceApplyRecordService applyRecordService;
	
	@Autowired
	private BwMetlifeInsuranceOrderService insuranceOrderService;
	
	@Autowired
	private BwMetlifeInsuranceDetailService insuranceDetailService;

//	@Override
//	public boolean checkApplyInsurance(MetLifeInsuredVo vo) throws Exception {
//		
//		Assert.notNull(vo,"传入参数不能为空，请检查参数vo~");
//		String orderNo = vo.getOrderNo();
//		String strJson = JSON.toJSONString(vo);
//		
//		//在集合中存在~相同的orderNo，则是重复申请
//		if(RedisUtils.hexists(REDIS_INSURE_ORDERNO, orderNo)){
//			logger.warn("-------【大都会保险】redis中已存在相同的工单编号，orderNo:{}-----",orderNo);
//			throw new BusinessException("重复的保险申请记录【"+orderNo+"】~");
//		}
//		//在mysql数据库表中存在~相同orderNo,且申请状态为0：默认状态；1:申请成功的记录
//		int recordNum= applyRecordService.queryApplyRecordCount(orderNo, asList(0,1));//0默认状态（已存在）,1申请成功
//		if(recordNum >0) {
//			logger.warn("-------【大都会保险】数据库中已存在相同的保险申请记录或已生成保险单，orderNo:{}-----",orderNo);
//			throw new BusinessException("重复的保险申请记录【"+orderNo+"】~");
//		}else {
//			//添加保险申请记录到mysql
//			Long id = applyRecordService.save(vo,0);//applyState=0初始化状态
//			if(null != id) {//将保险申请记录缓存至redis
//				logger.info(">>>>>>【大都会保险】-写redis Key {},field:{}<<<<<<<",REDIS_INSURE_ORDERNO,orderNo);
//				RedisUtils.hset(REDIS_INSURE_ORDERNO, vo.getOrderNo(), String.valueOf(id));
//				logger.info(">>>>>>【大都会保险】-写redis Key {},value:{}<<<<<<<",REDIS_INSURE_APPLYINFO,strJson);
//				RedisUtils.rpush(REDIS_INSURE_APPLYINFO, strJson);
//			}
//		}
//		return true;
//	}
	
	
	@Override
	public boolean checkApplyInsurance(MetLifeInsuredVo vo) throws Exception {
		
		Assert.notNull(vo,"传入参数不能为空，请检查参数vo~");
		String orderNo = vo.getOrderNo();
		
		//在mysql数据库表中存在~相同orderNo,且申请状态为0：默认状态；1:申请成功的记录
		int recordNum= applyRecordService.queryApplyRecordCount(orderNo, asList(0,1));//0默认状态（已存在）,1申请成功
		if(recordNum >0) {
			logger.warn("-------【大都会保险】数据库中已存在相同的保险申请记录或已生成保险单，orderNo:{}-----",orderNo);
			throw new BusinessException("重复的保险申请记录【"+orderNo+"】~");
		}else {
			try {
				//添加保险申请记录到mysql
				Long recordId = applyRecordService.save(vo,0);//applyState=0初始化状态
				//将申请记录生成工单
				insuranceOrderService.createOrder(vo,recordId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("-------【大都会保险】生成工单 异常，异常信息：{}",e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	@Override
	public BwMetlifeInsuranceApplyRecord queryApplyState(String orderNo,String productNo){
		
		return applyRecordService.queryApplyRecordByOrderNo(orderNo,productNo);
	}
//	@Override
//	public BwMetlifeInsuranceDetail queryInsuranceDetail(String orderNo){
//		
//		return insuranceOrderService.queryInsuranceDetail(orderNo);
//	}
	@Override
	public List<String> queryPolicyNoByTrimDate(String productNo, String trimDate){
		
		return insuranceOrderService.queryInsurancePolicyNo(productNo,trimDate,null);
	}
	
	@Override
	public List<String> queryPolicyNoByOrderNo(String productNo, String orderNo) throws Exception {
		
		return insuranceOrderService.queryInsurancePolicyNo(productNo,null,orderNo);
	}

	@Override
	public List<MetlifeInsuranceOrderDto> queryInsuranceOrderList(String orderNo, String productNo) throws Exception {
		BwMetlifeInsuranceApplyRecord applyRecord = applyRecordService.queryApplyRecordByOrderNo(orderNo,productNo);
		if(null == applyRecord)
			return null;
		List<MetlifeInsuranceOrderDto> orderList = insuranceOrderService.queryInsuranceOrderList(applyRecord.getId(),orderNo);
		return orderList;
	}
	
	@Override
	public List<MetlifeInsuranceOrderDto> queryInsuranceListByPolicyNo(String policyNo,String productNo) throws Exception {
		List<MetlifeInsuranceOrderDto> orderList = insuranceOrderService.queryInsuranceListByPolicyNo(policyNo,productNo);
		return orderList;
	}



	@Override
	public boolean checkCancelInsurance(String productNo,String orderNo,String remark) throws Exception {
		boolean flag = false;
		BwMetlifeInsuranceApplyRecord applyRecord = this.applyRecordService.queryApplyRecordByOrderNo(orderNo,productNo);
		if(null == applyRecord) throw new BusinessException("查无此记录[orderNo="+orderNo+"]");
		
		applyRecordService.updateApplyRecord(applyRecord.getId(), -1, "内部取消");
		
		flag = insuranceOrderService.updateStateByRid(applyRecord.getId(), -1);
//		
//		//在集合中是否存在orderNo，存在则删除
//		if(RedisUtils.hexists(REDIS_INSURE_ORDERNO, orderNo)){ 
//			//删除保险申请记录
//			RedisUtils.hdel(REDIS_INSURE_ORDERNO, orderNo);
//			MetLifeInsuredVo vo = new MetLifeInsuredVo();
//			BeanUtils.copyProperties(vo, applyRecord);
//			Long count = RedisUtils.lrem(REDIS_INSURE_APPLYINFO, 1, JSON.toJSONString(vo));//删除一条记录
//			if(count != null &&  count.longValue() ==1) {
//				flag = applyRecordService.updateApplyRecord(applyRecord.getId(),-1,"请求方主动取消申请记录，取消时间："+DateUtil.getDateString(new Date(), DateUtil.yyyy_MM_dd_HHmmss));//-1为无效的记录
//				logger.info("----【大都会保险】----请求方主动取消保险申请，orderNo:{},redis中删除{}条记录，数据库中删除：{}------",count,flag);
//				return flag;
//			}
//		}
//		
//		//上面如果没有从redis中删成功，则修改数据库
//		applyRecord = this.applyRecordService.queryApplyRecordByOrderNo(orderNo,productNo);
//		if(applyRecord.getApplyState() == 0) {
//			flag = applyRecordService.updateApplyRecord(applyRecord.getId(),-1,"请求方主动取消申请记录，取消时间："+DateUtil.getDateString(new Date(), DateUtil.yyyy_MM_dd_HHmmss));//-1为无效的记录
//			logger.info("----【大都会保险】----请求方主动取消保险申请，orderNo:{}，数据库中删除：{}------",flag);
//		} else if (applyRecord.getApplyState() == 1) {
//			//保险已生成，取消保险
//			flag = applyRecordService.updateApplyRecord(applyRecord.getId(),-1,"请求方主动取消申请记录，取消时间："+DateUtil.getDateString(new Date(), DateUtil.yyyy_MM_dd_HHmmss));//-1为无效的记录
//			flag = insuranceOrderService.updateInsuranceDetail(orderNo,-1);//取消
//			logger.info("----【大都会保险】----请求方主动取消保险申请，orderNo:{}，数据库中删除：{}------",flag);
//		} else {
//			throw new BusinessException("该记录无法取消，请查询申请状态~");
//		}
		return flag;
	}

	@Override
	public List<MetlifeInsuranceOrderDto> queryOrderApplyState(String orderNo) {
		
		return insuranceOrderService.queryOrderApplyState(orderNo);
	}

	@Override
	public int updatePolicyNo(String batchNo, String policyNo) {
		List<BwMetlifeInsuranceDetail> list = insuranceDetailService.queryInsuranceDetailListByBatchNo(batchNo);
		if(null == list || list.isEmpty()) return 0;
		int count = 0;
		for(BwMetlifeInsuranceDetail detail : list ) {
			if(!StringUtils.isEmpty(detail.getPolicyNumber())) continue;
			insuranceDetailService.updatePolicyNo(detail.getId(), policyNo);
			insuranceOrderService.updatePolicyNoByUuid(detail.getUuid(),policyNo);
			count ++;
		}
		return count;
	}

}
