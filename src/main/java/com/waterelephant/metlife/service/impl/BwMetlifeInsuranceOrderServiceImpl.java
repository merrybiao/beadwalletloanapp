package com.waterelephant.metlife.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.metlife.MetLifeInsureSDKService;
import com.beadwallet.service.metlife.common.MetLifeConstant;
import com.waterelephant.entity.BwMetlifeInsuranceOrder;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.mapper.BwMetlifeInsuranceOrderMapper;
import com.waterelephant.metlife.comm.MetLifeUtils;
import com.waterelephant.metlife.dto.MetlifeInsuranceOrderDto;
import com.waterelephant.metlife.service.BwMetlifeInsuranceOrderService;
import com.waterelephant.metlife.vo.MetLifeInsuredVo;
import com.waterelephant.service.BaseService;
import com.waterelephant.utils.DateUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
/**
 * 对保险请求记录进行分单
 * <p> 由于大都会
 * @author dinglinhao
 *
 */
@Service
public class BwMetlifeInsuranceOrderServiceImpl extends BaseService<BwMetlifeInsuranceOrder,Long> implements BwMetlifeInsuranceOrderService {
	
	private Logger logger = LoggerFactory.getLogger(BwMetlifeInsuranceOrderServiceImpl.class);
	
	@Autowired
	private BwMetlifeInsuranceOrderMapper orderMapper;

	@Override
	public boolean createOrder(MetLifeInsuredVo vo, Long rId) throws Exception {
		
		double polTermYears = 1;//默认保险保1年
		int polTermMonths = 12;//默认1年12个月
		//放款天数
		int loanDays = null == vo.getLoanDays() ? 0 : vo.getLoanDays().intValue();
		//放款月数
		int loanMonths = null == vo.getLoanMonths() ? 0 : vo.getLoanMonths().intValue();
		//放款金额
		double loanAmount = vo.getLoanAmount();
		//费率
		double rate = 0.0;
		//保险套餐编号
		String grpContPlancode = null;
		//放款时间
		String grantLoansDate = vo.getLoanDate();
		//根据放款天数选择保险套餐
		if(loanDays>0) {
			switch(loanDays) {
			case 28:
				grpContPlancode = MetLifeConstant.GRP_CONT_PLAN_CODE_01;
				rate = MetLifeInsureSDKService.getRate(grpContPlancode);
				break;
			case 29:
				grpContPlancode = MetLifeConstant.GRP_CONT_PLAN_CODE_02;
				rate = MetLifeInsureSDKService.getRate(grpContPlancode);
				break;
			default:
				logger.error("申请大都会保险失败~找不到对应的保险套餐~请检查bo:{}放款天数 loanDays:{}是否有对应的保险套餐",JSON.toJSONString(vo),loanDays);
				throw new BusinessException("根据放款天数找不到对应的保险套餐~");
			}
		} else if(loanMonths > 0) {//根据放款月数选择保险套餐
			//将放款月数转化成天数（放款日+自然月=还款日，放款天数=还款日-放款日）
			loanDays = MetLifeUtils.getLoanDays(grantLoansDate, loanMonths);
			grpContPlancode = MetLifeConstant.GRP_CONT_PLAN_CODE_03;
			rate = MetLifeInsureSDKService.getRate(grpContPlancode);
		}else {
			logger.error("申请大都会保险失败~找不到对应的保险套餐~请检查bo:{}放款天数 loanDays:{}或loanMonths:{}是否有对应的保险套餐",JSON.toJSONString(vo),loanDays,loanMonths);
			throw new BusinessException("找不到对应的保险套餐~");
		}
		//到期还款日
		String grantLoansEndDate = MetLifeUtils.getLoanEndDate(grantLoansDate, loanDays);
		//通过身份证号计算出生年月
		String insuredBirthday = MetLifeUtils.getBirthday(vo.getInsuredIdNo());
		//证件类型 默认身份证 0
		Integer insuredIdType = StringUtils.isEmpty(vo.getInsuredIdType()) ? 0 : vo.getInsuredIdType();
		//合同分单处理
		List<BwMetlifeInsuranceOrder> orderList = this.splitOrder(vo.getOrderNo(), loanAmount, grpContPlancode, rate, grantLoansDate, grantLoansEndDate, polTermMonths, polTermYears, 
				vo.getInsuredName(), insuredIdType, vo.getInsuredIdNo(), vo.getInsuredGender(), insuredBirthday, vo.getInsuredMobile(),
				vo.getIsSick(), vo.getIsAbsenteeism(), vo.getIsSeriousIllness(), vo.getIsOccupationalDisease(), 
				vo.getIsDisability(), vo.getIsPregnancy(), vo.getGoAbroad());
		
		if(null == orderList || orderList.isEmpty()) return false;
		
		for(BwMetlifeInsuranceOrder order : orderList) {
			order.setrId(rId);//apply_record表Id
			order.setInsureState(0);
			order.setCreateTime(new Date());//创建时间
			order.setUpdateTime(new Date());//更新时间 
			insert(order);//入库
		}
		return true;
	}
	
	/**
	 * 分单（保费大于1万元分单处理)
	 * @param orderNo 工单号
	 * @param loanAmount 合同金额
	 * @param grpContPlancode 保险套餐编号
	 * @param rate 保险费率
	 * @param grantLoansDate 放款时间
	 * @param grantLoansEndDate 到期还款时间
	 * @param polTermMonths 保险月
	 * @param polTermYears 保险年
	 * @param insuredName 被保人姓名
	 * @param insuredIdType 被保人证件类型
	 * @param insuredIdNo 被保人证件号
	 * @param insuredGender 被保人性别
	 * @param insuredBirthday 被保人出生年月
	 * @param insuredMobile 被保人手机号
	 * @param isSick
	 * @param isAbsenteeism
	 * @param isSeriousIllness
	 * @param isOccupationalDisease
	 * @param isDisability
	 * @param isPregnancy
	 * @param goAbroad
	 * @return
	 */
	private List<BwMetlifeInsuranceOrder> splitOrder(String orderNo,double loanAmount,String grpContPlancode,double rate,
			String grantLoansDate,String grantLoansEndDate,int polTermMonths,double polTermYears,
			String insuredName,Integer insuredIdType,String insuredIdNo,Integer insuredGender,String insuredBirthday,String insuredMobile,
			String isSick,String isAbsenteeism,String isSeriousIllness,String isOccupationalDisease,String isDisability,
			String isPregnancy,String goAbroad){
		
		List<BwMetlifeInsuranceOrder> list = new ArrayList<>();
		//合同支持的最大金额(大都会保费支持最大金额为1万元，根据套餐和套餐保费比例推算出合同支持的最大金额)
		double loanMaxAmount = MetLifeUtils.getLoanMaxAmount(grpContPlancode, loanAmount);
		//至少执行一次
		int index = 0;
		Calendar calendar = null;
		do {
			index ++;
			double loanContractAmount = 0;
			//计算分单金额 （合同金额大于最大金额，取最大金额为此次购买金额，循环减去此次购买金额，当合同金额小于最大金额时，取合同金额为此次购买金额）
			if(loanAmount>=loanMaxAmount) {
				loanContractAmount = loanMaxAmount;
				loanAmount = loanAmount - loanMaxAmount;
			}else {
				loanContractAmount = loanAmount;
				loanAmount = 0;
			}
			//保险金额
			double amount = MetLifeInsureSDKService.getAmount(grpContPlancode, loanContractAmount);
			//保费
			double premium = MetLifeInsureSDKService.getPremium(amount, rate);
			
			BwMetlifeInsuranceOrder order = new BwMetlifeInsuranceOrder();
			order.setOrderNo(orderNo);//工单编号bw_order表
			order.setRequestType(MetLifeConstant.ADD_REQUEST_TYPE);//请求类型：添加投保人
			//日期在当前天基础上+1天
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, index -1); //index初始值为0，第一次的值为1，依次累加；故此处是index-1从当前天算起
			//日期处理
			order.setBatchNo(DateUtil.getDateString(calendar.getTime(), DateUtil.yyyyMMdd));//批次号
			order.setTrimDate(DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd));//整理日期
			order.setGrpContPlancode(grpContPlancode);//保险套餐编号
			order.setGrantLoansDate(grantLoansDate);//放款日
			order.setGrantLoansEndDate(grantLoansEndDate);//到期还款日期
			order.setPolTermMonths(polTermMonths);//12个月 保险期限月数
			order.setPolTermYears(polTermYears);//1年 保险期限年限 1年、2年、3年、1.5年
			order.setRate(rate);//保险费率
			order.setAmount(MetLifeConstant.roundDown(amount));//保险金额   不得大于1万元（大都会）
			order.setPremium(MetLifeConstant.roundDown(premium));//保费
			order.setLoanContractAmount(loanContractAmount);//合同借款金额
			order.setLoanContractCode(orderNo+"_"+index);
			//被保人信息
			order.setInsuredName(insuredName);//姓名
			order.setInsuredIdType(insuredIdType);//证件类型 默认身份证 0
			order.setInsuredIdNo(insuredIdNo);//证件号
			order.setInsuredGender(insuredGender);//性别
			order.setInsuredBirthday(insuredBirthday);//生日
			order.setInsuredMobile(insuredMobile);//手机号
			//问询项
			order.setIsSick(isSick.toUpperCase());
			order.setIsAbsenteeism(isAbsenteeism.toUpperCase());
			order.setIsSeriousIllness(isSeriousIllness.toUpperCase());
			order.setIsOccupationalDisease(isOccupationalDisease.toUpperCase());
			order.setIsDisability(isDisability.toUpperCase());
			order.setIsPregnancy(isPregnancy.toUpperCase());
			order.setGoAbroad(goAbroad.toUpperCase());
			list.add(order);
			
		}while(loanAmount >0);
		return list;
	}

	@Override
	public List<MetlifeInsuranceOrderDto> queryInsuranceOrderList(Long rId, String orderNo) throws Exception {
		return orderMapper.queryInsuranceOrderList(rId,orderNo);
	}

	@Override
	public List<String> queryInsurancePolicyNo(String productNo, String trimDate, String orderNo) {
		
		if(!StringUtils.isEmpty(trimDate)) return orderMapper.queryPolicyNoByTrimDate(trimDate, productNo);
		
		if(!StringUtils.isEmpty(orderNo)) return orderMapper.queryPolicyNoByOrderNo(orderNo, productNo);
		
		return null;
	}

	@Override
	public List<MetlifeInsuranceOrderDto> queryInsuranceListByPolicyNo(String policyNo, String productNo)
			throws Exception {
		return orderMapper.queryInsuranceDetailList(policyNo,productNo);
	}

	@Override
	public List<MetlifeInsuranceOrderDto> queryOrderApplyState(String orderNo) {
		return orderMapper.queryOrderListByOrderNo(orderNo);
	}

	@Override
	public boolean updateStateByRid(Long rId, int insureState) {
		BwMetlifeInsuranceOrder record = new BwMetlifeInsuranceOrder();
		
		record.setInsureState(insureState);
		record.setUpdateTime(new Date());
		
		Example example = new Example(BwMetlifeInsuranceOrder.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("rId", rId);
		
		return orderMapper.updateByExampleSelective(record, example)>0;
	}

	@Override
	public boolean updatePolicyNoByUuid(String uuid, String policyNo) {
		Example example = new Example(BwMetlifeInsuranceOrder.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("uuid", uuid);
		example.setOrderByClause("create_time desc");
		List<BwMetlifeInsuranceOrder> list = selectByExample(example);
		if(null == list || list.isEmpty()) return false;
		BwMetlifeInsuranceOrder record = list.get(0);
		record.setPolicyNumber(policyNo);
		record.setUpdateTime(new Date());
		return updateByPrimaryKeySelective(record)>0;
	}
	
}
