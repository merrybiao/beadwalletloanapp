package com.waterelephant.service.impl;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beadwallet.entity.request.FreezeReqData;
import com.beadwallet.entity.request.QueryBalanceReqData;
import com.beadwallet.entity.request.TransferBmuReqData;
import com.beadwallet.entity.request.WithdrawReqData;
import com.beadwallet.entity.request.WtrechargeReqData;
import com.beadwallet.entity.response.CommonRspData;
import com.beadwallet.entity.response.QueryBalanceResultData;
import com.beadwallet.entity.response.QueryBalanceRspData;
import com.beadwallet.entity.response.ResponsePayResult;
import com.beadwallet.entity.response.UnFreezeRspData;
import com.beadwallet.entity.response.WithdrawRspData;
import com.beadwallet.entity.response.WtrechargeRspData;
import com.beadwallet.servcie.BeadwalletService;
import com.beadwallet.utils.CommUtils;
import com.beadwallet.utils.GenerateSerialNumber;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.service.FuYouService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.SystemConstant;

import cn.jpush.api.utils.StringUtils;
/**
* @ClassName: FuYouServiceImpl 
* @Description: TODO(和富有之间查询余额、冻结、解冻、卡扣接口实现) 
* @author SongYaJun
* @date 2016年11月15日 上午10:28:36 
 */
@Service
public class FuYouServiceImpl implements FuYouService {
	
	private Logger logger = Logger.getLogger(FuYouServiceImpl.class);
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Override
	public ResponsePayResult<QueryBalanceRspData> getAccountBalance(String fuYouAccount) {
		ResponsePayResult<QueryBalanceRspData> result=null;
		if(StringUtils.isNotEmpty(fuYouAccount)){
		QueryBalanceReqData reqData = new QueryBalanceReqData();
		reqData.setCust_no(fuYouAccount);  //富有账号
		reqData.setMchnt_cd(ResourceBundle.getBundle("fuiou").getString("mchnt_cd"));  // 富友生产商户代码
		reqData.setMchnt_txn_dt(CommUtils.convertDateToString(new Date(), "yyyyMMdd"));
		reqData.setMchnt_txn_ssn(GenerateSerialNumber.getSerialNumber());  // 流水号
			try {
				result = BeadwalletService.bwBalanceAction(reqData);
				logger.info(result.getBeadwalletCode());
			} catch (Exception e) {
				logger.error(result.getBeadwalletCode(),e);
			}
		}
		return result;
	}
    /**
     * 冻结金额
     */
	@Override
	public   AppResponseResult  freezeAccount(String fuYouAccount, Double monery) {
		
		AppResponseResult appResponseResult= new AppResponseResult();
		FreezeReqData freezeReqData =new FreezeReqData();
		freezeReqData.setMchnt_cd(ResourceBundle.getBundle("fuiou").getString("mchnt_cd"));// 商户代码
		freezeReqData.setMchnt_txn_ssn(GenerateSerialNumber.getSerialNumber());// 流水号
		freezeReqData.setAmt(doubleToString(monery));// 冻结金额
		freezeReqData.setCust_no(fuYouAccount);// 冻结账户
		
		ResponsePayResult<CommonRspData> payResult =BeadwalletService.bwFreeze(freezeReqData);
		String code=CommUtils.isNull(payResult) ? "999":CommUtils.isNull(payResult.getObj())?"999":payResult.getObj().getResp_code();
		String msg=CommUtils.isNull(payResult)?"系统报错":payResult.getBeadwalletMsg();
		appResponseResult.setCode(code);
		appResponseResult.setMsg(msg);

		return appResponseResult;
	}

	@Override
	public WtrechargeRspData Wtrecharge(String fuYouAccount,Double monery) throws Exception{
		WtrechargeReqData wtrechargeReqData = new WtrechargeReqData();
		wtrechargeReqData.setAmt(doubleToString(monery));
		wtrechargeReqData.setBack_notify_url("a");
		String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
		wtrechargeReqData.setMchnt_txn_ssn(mchnt_txn_ssn);
		wtrechargeReqData.setMchnt_cd(ResourceBundle.getBundle("fuiou").getString("mchnt_cd"));
		wtrechargeReqData.setLogin_id(fuYouAccount);
		WtrechargeRspData wtrechargeRspData = BeadwalletService.wtrecharge(wtrechargeReqData);
		String respCode = wtrechargeRspData.getResp_code();
		logger.info("委托充值code"+respCode);
		return wtrechargeRspData;
	}
	/**
	 * 解冻金额
	 */
	@Override
	public AppResponseResult removeFreeze(String userId,Double monery,String mchnt_txn_ssn) {
		AppResponseResult appResponseResult= new AppResponseResult();
		try {
			logger.info("借款人分期产品借款利息解冻流水号=====" + mchnt_txn_ssn);
			logger.info("解冻金额方法参数=====" +"monery==="+monery);
			logger.info("解冻金额方法参数=====" +"userId==="+userId);
			BwBorrower bw = bwBorrowerService.findBwBorrowerById(Long.parseLong(userId));	
			logger.info("解冻金额用户对象参数=====" +"bw==="+bw);
			FreezeReqData feReqData = new FreezeReqData();
			feReqData.setMchnt_cd(ResourceBundle.getBundle("fuiou").getString("mchnt_cd"));
			feReqData.setMchnt_txn_ssn(mchnt_txn_ssn);
			feReqData.setAmt(doubleToString(monery)); // 解冻金额
			feReqData.setCust_no(bw.getFuiouAcct());
			logger.info("解冻金额=====开始解冻");
			ResponsePayResult<UnFreezeRspData> resList=BeadwalletService.bwUnFreeze(feReqData);
			logger.info("解冻金额=====解冻完成");
			String code=CommUtils.isNull(resList) ? "999":CommUtils.isNull(resList.getObj())?"999":resList.getObj().getResp_code();
			logger.info("解冻金额方法code====="+code);
			String msg=CommUtils.isNull(resList)?"系统报错":resList.getBeadwalletMsg();
			logger.info("解冻金额方法msg====="+msg);
			appResponseResult.setCode(code);
			appResponseResult.setMsg(msg);
			return appResponseResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("解冻金额方法error====="+e.getMessage());
			appResponseResult.setCode("999");
			appResponseResult.setMsg("操作异常");
			return appResponseResult;
		}
	}
	
	
	private String doubleToString(double num) {
		num = num * 100;
		DecimalFormat decimalFormat = new DecimalFormat("0");
		return decimalFormat.format(num);
	}

	@Override
	public WithdrawRspData withdraw(String fuYouAccount, Double monery)
			throws Exception {
		// 抵押人委托提现
		WithdrawReqData withdrawReqData = new WithdrawReqData();
		withdrawReqData.setMchnt_cd(ResourceBundle.getBundle("fuiou").getString("mchnt_cd"));
		withdrawReqData.setAmt(doubleToString(monery)); //提现金额
		withdrawReqData.setBack_notify_url("a");
		withdrawReqData.setMchnt_txn_ssn(GenerateSerialNumber.getSerialNumber()); //流水号
		withdrawReqData.setLogin_id(fuYouAccount); //富有账户
		WithdrawRspData withdrawRspData = BeadwalletService.Withdraw(withdrawReqData);
		String respCode = withdrawRspData.getResp_code();
		logger.info("解冻code"+respCode);
		return withdrawRspData;
	}

	/**
	 * 获取借款人富友账号余额
	 * ct_balance账面总余额
	 * ca_balance 可用余额 
	 * cf_balance 冻结余额 
	 * cu_balance 未转接余额
	 * @param userId 用户id
	 * @return 
	 * @throws Exception
	 */

	public HashMap<String,Double> getFuiouAmount(String userId){
		HashMap<String,Double> map=new HashMap<>();
		BwBorrower bw = bwBorrowerService.findBwBorrowerById(Long.parseLong(userId));
		if (!CommUtils.isNull(bw)) {
			String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
			QueryBalanceReqData reqData = new QueryBalanceReqData();
			reqData.setCust_no(bw.getFuiouAcct());// 客户富友账号
			reqData.setMchnt_txn_dt(CommUtils.convertDateToString(new Date(), "yyyyMMdd"));
			reqData.setMchnt_cd(SystemConstant.FUIOU_MCHNT_CD);// 富友生产商户代码
			reqData.setMchnt_txn_ssn(mchnt_txn_ssn);
			logger.info("查询富友账号余额:=================富友账号====" + bw.getFuiouAcct() + "生产商代码==" + reqData.getMchnt_cd());
			ResponsePayResult<QueryBalanceRspData> list = BeadwalletService.bwBalanceAction(reqData);
			 if(list.getObj()!=null){
				 List<QueryBalanceResultData> resList = list.getObj().getResults();
					String ct_balance= CommUtils.isNull(resList) ? "0": CommUtils.isNull(resList.get(0).getCt_balance()) ? "0" : resList.get(0).getCt_balance();//账面总余额
					String ca_balance= CommUtils.isNull(resList) ? "0": CommUtils.isNull(resList.get(0).getCa_balance()) ? "0" : resList.get(0).getCa_balance();//可用余额
					String cf_balance= CommUtils.isNull(resList) ? "0": CommUtils.isNull(resList.get(0).getCf_balance()) ? "0" : resList.get(0).getCf_balance();//冻结余额
					String cu_balance= CommUtils.isNull(resList) ? "0": CommUtils.isNull(resList.get(0).getCu_balance()) ? "0" : resList.get(0).getCu_balance();//未转接余额
					map.put("ct_balance", Double.parseDouble(ct_balance));
					map.put("ca_balance", Double.parseDouble(ca_balance));
					map.put("cf_balance", Double.parseDouble(cf_balance));
					map.put("cu_balance", Double.parseDouble(cu_balance));
					logger.info("账面总额===ct_balance=" + ct_balance + "可用余额ca_balance==" + ca_balance+"冻结金额cf_balance====="+cf_balance);  		 
			 }
		}
		return map;

	}

	/**
	 * 转账
	 * 
	 * @param amt 还款金额
	 * @param userId 还款人Id
	 * @param mchnt_txn_ssnt流水号
	 * @throws Exception
	 */
	public AppResponseResult bwTransferBu(String userId, Double amt, String mchnt_txn_ssn) {
		AppResponseResult appResponseResult= new AppResponseResult();
		
		BwBorrower bw = bwBorrowerService.findBwBorrowerById(Long.parseLong(userId));
		TransferBmuReqData tr = new TransferBmuReqData();
		tr.setMchnt_cd(SystemConstant.FUIOU_MCHNT_CD);// 商户代码
		tr.setMchnt_txn_ssn(mchnt_txn_ssn);// 获取流水号
		logger.info("借款人打入公司平台账号的流水号=====" + mchnt_txn_ssn);
		tr.setOut_cust_no(bw.getFuiouAcct());// 借款人富友账号
		tr.setIn_cust_no(SystemConstant.FUIOU_MCHNT_BACKUP);// 公司平台风险备用金
		logger.info("借款人打入公司平台账号的金额=====" + amt);
		tr.setAmt(doubleToString(amt));// 交易金额
		ResponsePayResult<CommonRspData> resList = BeadwalletService.bwTransferBu(tr);
		String code=CommUtils.isNull(resList) ? "999":CommUtils.isNull(resList.getObj())?"999":resList.getObj().getResp_code();
		String msg=CommUtils.isNull(resList)?"系统报错":resList.getBeadwalletMsg();
		appResponseResult.setCode(code);
		appResponseResult.setMsg(msg);
		return appResponseResult;
	}

}
