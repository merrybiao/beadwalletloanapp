package com.waterelephant.channel.service;

import java.util.Date;
import java.util.List;

import com.waterelephant.dto.LoanInfo;
import com.waterelephant.dto.PaymentRespDto;
import com.waterelephant.dto.ProductFeeDto;
import com.waterelephant.dto.QueryRepayInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwProductTerm;
import com.waterelephant.entity.ExtraConfig;
import com.waterelephant.utils.AppResponseResult;

import tk.mybatis.mapper.entity.Example;

public interface ProductService {

	/**
	 * 根据条件您查询产品集合
	 * 
	 * @param example
	 * @return
	 */
	List<BwProductDictionary> selectByExample(Example example);

	// 根据产品编号查询产品信息
	BwProductDictionary getProductInfo(String pNo);

	/**
	 * 根据工单ID查对应产品
	 * 
	 * @param orderId
	 * @return
	 */
	BwProductDictionary queryByOrderId(Long orderId);

	// 根据产品id和期数返回产品配置信息
	BwProductTerm getProductTermInfo(int id, int termNum);

	/**
	 * 根据产品编号和期数返回产品配置信息
	 * 
	 * @param pNo 产品编号
	 * @param termNum
	 * @return
	 */
	BwProductTerm getProductTermInfo(String pNo, int termNum);

	/**
	 * 根据产品编号获取最新配置信息，即期数最大
	 * 
	 * @param pNo
	 * @param termNum
	 * @return
	 */
	public BwProductTerm getMaxProductTermInfo(String pNo);

	/**
	 * 根据产品编号获取最大期数
	 * 
	 * @param pNo
	 * @param termNum
	 * @return
	 */
	Integer selectMaxTermNumByNo(String pNo);

	/**
	 * 根据产品编号获取最大期数
	 * 
	 * @param pNo
	 * @param termNum
	 * @return
	 */
	Integer selectMaxTermNumById(Long id);

	// 查询配置信息列表
	List<ExtraConfig> getExtraConfigList();

	/**
	 * 获取当前使用产品的编号
	 * 
	 * @return
	 */
	String queryCurrentProductNo();

	/**
	 * 获取当前使用的产品
	 * 
	 * @return
	 */
	BwProductDictionary queryCurrentProduct();

	/**
	 * 计算借款费用明细(快速信审费、平台使用费、账户管理费、代收通道费、资金使用费)
	 * 
	 * @param borrowAmount 金额
	 * @param product
	 * @return
	 */
	QueryRepayInfo calcBorrowCost(double borrowAmount, BwProductDictionary product);

	/**
	 * 根据产品类型获取总工本费率
	 * 
	 * @param productType
	 * @return
	 */
	double calcTotalCostRateByType(Integer productType);

	/**
	 * 根据产品类型获取总利息费率
	 * 
	 * @param productType
	 * @return
	 */
	double calcTotalInterestRateByType(Integer productType);

	/**
	 * 计算展期金额，借款费用(可配置，18%)和服务费(可配置，第一次5%，第2次7%，第3次以后9%)
	 * 
	 * @param borrowAmount 计算金额
	 * @param orderId 工单ID
	 * @param repayId 还款计划ID
	 * @param product
	 * @param termNum 续贷期数
	 * @param loanInfo 续贷各项费用，传入loanInfo，会自动补齐各项费用
	 * @return
	 */
	Double calcXudaiCost(double borrowAmount, Long orderId, Long repayId, BwProductDictionary product, int termNum,
			LoanInfo loanInfo);

	/**
	 * 计算还款费用（本金+逾期），逾期计算减免罚息
	 * 
	 * @param borrowAmount
	 * @param orderId
	 * @param repayId
	 * @param product
	 * @param loanInfo
	 * @return
	 */
	Double calcRepaymentCost(double borrowAmount, Long orderId, Long repayId, BwProductDictionary product,
			LoanInfo loanInfo);

	/**
	 * 计算逾期金额<br />
	 * 1.正在还款中的工单：直接从逾期记录获取逾期罚息金额<br />
	 * 2.已还款的工单(查询历史记录)：新数据从BwRepaymentDetail支付明细获取，老数据通过逾期记录的逾期天数计算
	 * 
	 * @param orderId
	 * @param repayId 还款计划ID
	 * @param loanInfo
	 * @return
	 */
	Double calcOverdueCost(Long orderId, Long repayId, LoanInfo loanInfo);

	/**
	 * 计算还款日期
	 * 
	 * @param date
	 *            开始计算的日期
	 * @param term
	 *            期限
	 * @param termType
	 *            期限类型(1.月 2.天)
	 * @return
	 */
	Date calcRepayTime(Date date, String term, String termType);

	/**
	 * 计算展期后还款日期
	 *
	 * @param date
	 *            开始计算的日期
	 * @param term
	 *            期限
	 * @param termType
	 *            期限类型(1.月 2.天)
	 * @return
	 */
    Date calcExtendRepayTime(Date date, String term, String termType);

    /**
	 * 根据参数编号查询参数信息
	 */
	ExtraConfig getParameterInfo(String code);

	/**
	 * 是否可以展期，code=000时可以续贷，否则不能续贷，并返回错误信息msg
	 * 
	 * @param orderId
	 * @return
	 */
	AppResponseResult canXuDai(Long orderId);

	/**
	 * 根据产品类型获取当前使用的产品
	 * 
	 * @param productType
	 * @return
	 */
	BwProductDictionary selectCurrentByProductType(Integer productType);

	/**
	 * 计算展期金额，先判断是否满足展期条件，如果满足返回code=000，且result的值为{@code LoanInfo}对象(amt为展期总金额)
	 * 
	 * @param orderId
	 * @return
	 */
	AppResponseResult calcZhanQiCost(Long orderId);

	/**
	 * 计算还款金额
	 * 
	 * @param orderId
	 * @param useCoupons
	 * @param batchMoney
	 * @return
	 */
	AppResponseResult calcRepaymentCost(Long orderId, boolean useCoupons, Double batchMoney);

    AppResponseResult calcRepaymentCost(Long orderId);

    /**
	 * 计算湛江委扣除金额
	 * 
	 * @param calcAmount
	 * @param product
	 * @return
	 */
	double calcZjwAmount(double calcAmount, BwProductDictionary product);

	/**
	 * 根据产品计算产品费用明细
	 *
	 * @param borrowAmount
	 * @param product
	 * @return
	 */
    ProductFeeDto queryProductFee(Double borrowAmount, BwProductDictionary product);

	/**
	 * 获取付款信息
	 *
	 * @param orderId
	 * @return
	 */
	PaymentRespDto getNeedPaymentInfo(Long orderId);
}
