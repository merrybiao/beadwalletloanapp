package com.waterelephant.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.serve.BeadWalletDistributionService;
import com.waterelephant.entity.BwShouxinCashmoney;
import com.waterelephant.entity.BwShouxinProduct;
import com.waterelephant.mapper.BwShouxinCashmoneyMapper;
import com.waterelephant.mapper.BwShouxinProductMapper;
import com.waterelephant.service.BwShouxinInfoService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BwShouxinInfoServiceImpl implements BwShouxinInfoService{
	
	private Logger logger = LoggerFactory.getLogger(BwShouxinInfoServiceImpl.class);
	
	@Autowired
	private BwShouxinProductMapper bwShouxinProductMapper;
	
	@Autowired
	private BwShouxinCashmoneyMapper bwShouxinCashmoneyMapper;
	

	@Override
	public boolean saveProductData(String app_term, String apply_state, String apply_time,
			String bank_card_no, String bank_code, String channel_info, String code_bus, JSONObject device, String id_card,
			String id_type, String mobile, String name_custc, String no_busb, String order_info, String prod_type,
			String re_loan) {
		BwShouxinProduct shouxinProduct = new BwShouxinProduct();
		try {
			shouxinProduct.setAppTerm(app_term);
			shouxinProduct.setApplyState(apply_state);
			shouxinProduct.setApplyTime(DateUtil.stringToDate(apply_time, DateUtil.yyyy_MM_dd_HHmmss));
			shouxinProduct.setBankCardNo(bank_card_no);
			shouxinProduct.setBankCode(bank_code);
			shouxinProduct.setChannelInfo(channel_info);
			shouxinProduct.setCodeBus(code_bus);
			shouxinProduct.setDevice(CommUtils.isNull(device) ? "".getBytes() :device.toString().getBytes());
			shouxinProduct.setIdCard(id_card);
			shouxinProduct.setIdType(id_type);
			shouxinProduct.setMobile(mobile);
			shouxinProduct.setNameCustc(name_custc);
			shouxinProduct.setNoBusb(no_busb);
			shouxinProduct.setOrderInfo(order_info);
			shouxinProduct.setProdType(prod_type);
			shouxinProduct.setReLoan(re_loan);
			shouxinProduct.setCreateTime(new Date());
			int count = bwShouxinProductMapper.insert(shouxinProduct);
			return count > 0 ? true :false;
			
		} catch (Exception e) {
			logger.error("~【商品分期】~存储基础数据到数据库异常，异常信息:{}",e.getMessage());
			e.printStackTrace();
			return false;
		}
   }
	
	@Override
	public JSONObject returnSxResult(JSONObject jsondata) {
		 JSONObject res = null;
		 String deviceStr = CommUtils.isNull(jsondata.getJSONObject("device")) ? "" : jsondata.getJSONObject("device").toString();
		 String jsonStr = CommUtils.isNull(jsondata.getJSONObject("device")) ? jsondata.toString() : jsondata.fluentRemove("device").toString();
		 String result = BeadWalletDistributionService.pushDistributionTxd(jsonStr,deviceStr);
		 if(null != result) {
			 JSONObject bjResult = JSONObject.parseObject(result);
			 String resCode = bjResult.getString("resCode");
			 if("0000".equals(resCode)) {
				 res = JSONObject.parseObject(bjResult.getString("handlerData"));
			 } else {
				 logger.error("~请求北京审核接口成功，返回数据异常，异常信息为：{}",bjResult);
			 }
		 }
		return res;
	}

	@Override
	public boolean updateProductResult(String no_busb,String result) {
		try {
			Example example = new Example(BwShouxinProduct.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("noBusb", no_busb);
			BwShouxinProduct shjg = new BwShouxinProduct();
			shjg.setReturnResult(result);
			shjg.setUpdateTime(new Date());
			//bwShouxinProductMapper.updateByExample(shjg, example); //此种修改方法会导致其他的字段全部恢复成默认值
			bwShouxinProductMapper.updateByExampleSelective(shjg, example);
			return true;
		} catch (Exception e) {
			logger.error("~【商品分期】~根据唯一标识no_busb:{},申请环节{},修改对应数据审核结果出现异常，异常信息为：{}",no_busb,e.getMessage());
			e.printStackTrace();
		}
		return false;
	}


	
	@Override
	public boolean updateCashMoneyResult(String no_busb, String result) {
		try {
			Example example = new Example(BwShouxinProduct.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("noBusb", no_busb);
			BwShouxinCashmoney chashjg = new BwShouxinCashmoney();
			chashjg.setReturnResult(result);
			chashjg.setUpdateTime(new Date());
			bwShouxinCashmoneyMapper.updateByExampleSelective(chashjg, example);
			return true;
		} catch (Exception e) {
			logger.error("~【商品分期】~根据唯一标识no_busb:{},修改对应数据审核结果出现异常，异常信息为：{}",no_busb,e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean saveCashMoneyData(BigDecimal app_limit, Short app_term, String apply_state,
			String apply_time, String bank_card_no, String bank_code, String channel_info, String code_bus,
			JSONObject device, String id_card, String id_type, String mobile, String name_custc, String no_busb,
			String prod_type, String re_loan) {
		BwShouxinCashmoney cashmoney = new BwShouxinCashmoney();
		try {
			cashmoney.setAppLimit(app_limit);
			cashmoney.setAppTerm(app_term);
			cashmoney.setApplyState(apply_state);
			cashmoney.setApplyTime(DateUtil.stringToDate(apply_time, DateUtil.yyyy_MM_dd_HHmmss));
			cashmoney.setBankCardNo(bank_card_no);
			cashmoney.setBankCode(bank_code);
			cashmoney.setChannelInfo(channel_info);
			cashmoney.setCodeBus(code_bus);
			cashmoney.setDevice(CommUtils.isNull(device) ? "".getBytes() :device.toString().getBytes());
			cashmoney.setIdCard(id_card);
			cashmoney.setIdType(id_type);
			cashmoney.setMobile(mobile);
			cashmoney.setNameCustc(name_custc);
			cashmoney.setNoBusb(no_busb);
			cashmoney.setProdType(prod_type);
			cashmoney.setReLoan(re_loan);
			cashmoney.setCreateTime(new Date());
			int count = bwShouxinCashmoneyMapper.insert(cashmoney);
			return count > 0 ? true :false;
		} catch (Exception e) {
			logger.error("~【提现环节】~存储基础数据到数据库异常，异常信息:{}",e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
