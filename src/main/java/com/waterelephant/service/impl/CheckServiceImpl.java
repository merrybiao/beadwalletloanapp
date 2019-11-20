package com.waterelephant.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beadwallet.service.entity.request.QhzxReqData;
import com.beadwallet.service.entity.request.ZmxyReqData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entity.response.ivsRspData;
import com.beadwallet.service.entiyt.middle.IvsDetail;
import com.beadwallet.service.entiyt.middle.QueryData;
import com.beadwallet.service.entiyt.middle.Record2;
import com.waterelephant.dto.CheckInfoDto;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBlacklist;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwCreditImformation;
import com.waterelephant.entity.BwInsureInfo;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwQhzxBlack;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwScoreCard;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.entity.BwZmxyIvs;
import com.waterelephant.entity.CheckResult;
import com.waterelephant.entity.YimeiOverdueinfo;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwCreditInformationService;
import com.waterelephant.service.BwInsureInfoService;
import com.waterelephant.service.BwInsureRecordService;
import com.waterelephant.service.BwOperateBasicService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.BwQhzxBlackService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.BwScoreCardService;
import com.waterelephant.service.BwZmxyIvsService;
import com.waterelephant.service.CheckService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwContactListService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.service.IdentityService;
import com.waterelephant.service.YimeiMaindataService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.yixin.dto.YixinMainData;
import com.waterelephant.yixin.service.YixinDataService;

import tk.mybatis.mapper.entity.Example;

@Service
public class CheckServiceImpl implements CheckService {
	private Logger logger = Logger.getLogger(CheckServiceImpl.class);

	@Autowired
	private YixinDataService yixinDataService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private BwInsureRecordService bwInsureRecordService;
	@Autowired
	private BwInsureInfoService bwInsureInfoService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	@Autowired
	private BwOperateBasicService bwOperateBasicService;
	@Autowired
	private BwOperateVoiceService bwOperateVoiceService;
	@Autowired
	private IBwContactListService bwContactListService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwCreditInformationService bwCreditInformationService;
	@Autowired
	private BwZmxyIvsService bwZmxyIvsService;
	@Autowired
	private BwQhzxBlackService bwQhzxBlackService;
	@Autowired
	private BwScoreCardService bwScoreCardService;
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	@Autowired
	private YimeiMaindataService yimeiMaindataService;
	@Autowired
	private BwBlacklistService bwBlacklistService;
	@Autowired
	private IBwBankCardService bwBankCardService;

	public CheckResult identity(String idNo, String name, String phone, String cardNo) {
		CheckResult checkResult = new CheckResult();
		boolean result = true;
		String code = "000";
		if ((idNo.length() != 18) && (idNo.length() != 15)) {
			checkResult.setResult(false);
			checkResult.setCode("A3");
			checkResult.setDesc("非法的身份证号");
			checkResult.setRejectType(Integer.valueOf(0));
			checkResult.setSoucre(7);
			return checkResult;
		}

		int bronDate = Integer.parseInt(idNo.substring(6, 10));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date now = new Date();
		if (Integer.parseInt(sdf.format(now)) - bronDate < 18) {
			checkResult.setResult(false);
			checkResult.setCode("A1");
			checkResult.setDesc("未满18岁");
			checkResult.setRejectType(Integer.valueOf(1));
			checkResult.setSoucre(7);
			return checkResult;
		}

		if (Integer.parseInt(sdf.format(now)) - bronDate > 55) {
			checkResult.setResult(false);
			checkResult.setCode("A2");
			checkResult.setDesc("年龄大于55岁");
			checkResult.setRejectType(Integer.valueOf(0));
			checkResult.setSoucre(7);
			return checkResult;
		}
		// ***********************宜信至诚分小于450分*
		YixinMainData yoixinMainData = yixinDataService.saveAndQueryYixinDatas(idNo, name, "10");
		if (!CommUtils.isNull(yoixinMainData.getZcCreditScore())) {
			if (Integer.valueOf(yoixinMainData.getZcCreditScore()) < 450) {
				checkResult.setSoucre(3);
				checkResult.setDesc("宜信至诚分小于450分");
				checkResult.setResult(false);
				checkResult.setRejectType(0);
				return checkResult;
			}
		}
		// ********************************************芝麻信用
		ZmxyReqData zmxyReqData = new ZmxyReqData();
		zmxyReqData.setIdNo(idNo);// 身份证
		zmxyReqData.setName(name);// 名字
		zmxyReqData.setMobile(phone);// 电话
		if (cardNo != null && !"".equals(cardNo)) {
			zmxyReqData.setBankCard(cardNo);// 银行卡号
		}
		Response<ivsRspData> ivsRes = this.identityService.ivs(zmxyReqData);
		if (ivsRes != null && ivsRes.getRequestCode().equals("200")) {
			List<IvsDetail> ivsDetails = ((ivsRspData) ivsRes.getObj()).getIvsDetail();
			if (ivsDetails.size() > 0) {
				// 保存数据
				for (IvsDetail ivsDetail : ivsDetails) {
					BwZmxyIvs bwZmxyIvs = bwZmxyIvsService.findByNameAndIdCard(name, idNo);
					if (CommUtils.isNull(bwZmxyIvs)) {
						bwZmxyIvs = new BwZmxyIvs();
						bwZmxyIvs.setIvsScore(Integer.parseInt(ivsRes.getObj().getIvsScore()));
						bwZmxyIvs.setName(name);
						bwZmxyIvs.setIdCard(idNo);
						bwZmxyIvs.setPhone(phone);
						bwZmxyIvs.setCode(ivsDetail.getCode());
						bwZmxyIvs.setDescription(ivsDetail.getDescription());
						bwZmxyIvs.setCreateTime(now);
						bwZmxyIvs.setUpdateTime(now);
						bwZmxyIvsService.save(bwZmxyIvs);
					} else {
						bwZmxyIvs.setIvsScore(Integer.parseInt(ivsRes.getObj().getIvsScore()));
						bwZmxyIvs.setName(name);
						bwZmxyIvs.setIdCard(idNo);
						bwZmxyIvs.setPhone(phone);
						bwZmxyIvs.setCode(ivsDetail.getCode());
						bwZmxyIvs.setDescription(ivsDetail.getDescription());
						bwZmxyIvs.setUpdateTime(now);
						bwZmxyIvsService.update(bwZmxyIvs);
					}

				}

				if (ivsRes.getObj() != null && !CommUtils.isNull(ivsRes.getObj().getIvsScore())) {
					int score = Integer.parseInt(ivsRes.getObj().getIvsScore());
					if (score != 0 && score < 60) {
						checkResult.setSoucre(6);
						checkResult.setResult(false);
						checkResult.setDesc("芝麻信用反欺诈分数小于60分");
						checkResult.setRejectType(0);
						return checkResult;
					}
				}
				// *************芝麻反欺诈 开始
				for (int i = 0; i < ivsDetails.size(); i++) {
					String zmxyCode = ivsDetails.get(i).getCode();
					for (String ss : SystemConstant.ZMXY_CODE_ARRAY) {
						if (ss.equals(zmxyCode)) {
							checkResult.setSoucre(6);
							checkResult.setResult(false);
							checkResult.setDesc(ivsDetails.get(i).getDescription());
							checkResult.setRejectType(0);
							return checkResult;
						}
					}

				}
				// *************芝麻反欺诈 结束

			}
		}
		// ****************************************************
		// 亿美逾期
		logger.info("亿美逾期======开始==============");
		Map<String, Object> mapOver = yimeiMaindataService.saveYiMeiDataByPhone(phone);
		if (!CommUtils.isNull(mapOver)) {
			List<YimeiOverdueinfo> list = (List<YimeiOverdueinfo>) mapOver.get("overduelist");
			if (!CommUtils.isNull(list)) {
				logger.info("亿美逾期======记录条数==============" + list.size());
				checkResult.setSoucre(10);
				checkResult.setResult(false);
				checkResult.setDesc("亿美平台逾期");
				checkResult.setRejectType(0);
				return checkResult;
			}
		}
		logger.info("亿美逾期======结束==============");
		checkResult.setCode(code);
		checkResult.setResult(result);
		return checkResult;
	}

	public CheckResult blackList(String idNo, String name) {
		CheckResult checkResult = new CheckResult();
		boolean result = true;
		String code = "000";
		Date now = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QhzxReqData qhzxReqData = new QhzxReqData();
		qhzxReqData.setTransNo("sxfqls" + System.currentTimeMillis());
		qhzxReqData.setTransDate(sdf2.format(new Date()));
		qhzxReqData.setBatchNo("sxfqpc" + System.currentTimeMillis());
		qhzxReqData.setIdNo(idNo);
		qhzxReqData.setName(name);
		qhzxReqData.setReasonCode("01");
		qhzxReqData.setEntityAuthCode("sxfqsqm" + System.currentTimeMillis());
		qhzxReqData.setEntityAuthDate(sdf.format(new Date()));
		qhzxReqData.setSeqNo("sxfqxl" + System.currentTimeMillis());
		Response<QueryData> qhRes = this.identityService.query(qhzxReqData);
		if (!qhRes.getRequestCode().equals("200") && qhRes.getRequestMsg().contains("校验不通过")) {
			checkResult.setSoucre(2);
			checkResult.setResult(false);
			checkResult.setRejectType(0);
			checkResult.setDesc(qhRes.getRequestMsg());
			return checkResult;
		}
		if ((!CommUtils.isNull(qhRes.getObj())) && (qhRes.getRequestCode().equals("200"))) {
			// System.out.println("come in");
			if (!CommUtils.isNull(((QueryData) qhRes.getObj()).getRecords())) {
				List<Record2> record2s = ((QueryData) qhRes.getObj()).getRecords();
				if (record2s.size() > 0) {
					for (Record2 record2 : record2s) {
						BwQhzxBlack bwQhzxBlack = bwQhzxBlackService.findByNameAndIdCard(name, idNo);
						if (CommUtils.isNull(bwQhzxBlack)) {
							// 保存
							bwQhzxBlack = new BwQhzxBlack();
							bwQhzxBlack.setIdCard(record2.getIdNo());
							bwQhzxBlack.setName(record2.getName());
							bwQhzxBlack.setRskMark(record2.getRskMark());
							bwQhzxBlack.setRskScore(record2.getRskScore());
							bwQhzxBlack.setSourceId(record2.getSourceId());
							bwQhzxBlack.setDataBuildTime(record2.getDataBuildTime());
							bwQhzxBlack.setCreateTime(now);
							bwQhzxBlack.setUpdateTime(now);
							bwQhzxBlackService.save(bwQhzxBlack);
						} else {
							bwQhzxBlack.setIdCard(record2.getIdNo());
							bwQhzxBlack.setName(record2.getName());
							bwQhzxBlack.setRskMark(record2.getRskMark());
							bwQhzxBlack.setRskScore(record2.getRskScore());
							bwQhzxBlack.setSourceId(record2.getSourceId());
							bwQhzxBlack.setDataBuildTime(record2.getDataBuildTime());
							bwQhzxBlack.setUpdateTime(now);
							bwQhzxBlackService.update(bwQhzxBlack);
						}

						if ((!record2.getSourceId().equals("A")) || (CommUtils.isNull(record2.getSourceId())))
							continue;
						if (!CommUtils.isNull(record2.getRskMark())) {
							if (record2.getRskMark().equals("B2")) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("执行人");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (record2.getRskMark().equals("B1")) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("失信被执行人");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (record2.getRskMark().equals("B3")) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("交通严重违章");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (record2.getRskMark().equals("C1")) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("手机号存在欺诈风险");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (record2.getRskMark().equals("C2")) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("卡号存在欺诈风险");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (record2.getRskMark().equals("C3")) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("身份证号存在欺诈风险");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
						}
						int qhScore = Integer.parseInt(record2.getRskScore());
						System.out.println("=====================前海分数:" + qhScore);
						int monthScore = qhScore / 10;
						System.out.println("=====================计算后的月分数:" + monthScore);
						int moneyScore = qhScore % 10;
						System.out.println("=====================计算后的金额分数:" + moneyScore);

						if ((10 <= qhScore) && (qhScore <= 15)) {
							if (moneyScore == 0) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期31-60天,逾期金额范围为：无");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 1) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期31-60天,逾期金额范围为：0-1000");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 2) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期31-60天,逾期金额范围为：1000-5000");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 3) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期31-60天,逾期金额范围为：5000-20000");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 4) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期31-60天,逾期金额范围为：2w-10w");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 5) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期31-60天,逾期金额范围为：10w以上");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
						}
						if ((20 <= qhScore) && (qhScore <= 25)) {
							if (moneyScore == 0) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期61-90天,逾期金额范围为：无");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 1) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期61-90天,逾期金额范围为：0-1000");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 2) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期61-90天,逾期金额范围为：1000-5000");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 3) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期61-90天,逾期金额范围为：5000-20000");
								checkResult.setResult(true);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 4) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期61-90天,逾期金额范围为：2w-10w");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 5) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期61-90天,逾期金额范围为：10w以上");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
						}
						if ((30 <= qhScore) && (qhScore <= 35)) {
							if (moneyScore == 0) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期91-180天,逾期金额范围为：无");
								checkResult.setResult(true);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 1) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期91-180天,逾期金额范围为：0-1000");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 2) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期91-180天,逾期金额范围为：1000-5000");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 3) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期91-180天,逾期金额范围为：5000-20000");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								return checkResult;
							}
							if (moneyScore == 4) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期91-180天,逾期金额范围为：2w-10w");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 5) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期91-180天,逾期金额范围为：10w以上");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
						}
						if ((40 <= qhScore) && (qhScore <= 45)) {
							if (moneyScore == 0) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期180天以上,逾期金额范围为：无");
								checkResult.setResult(true);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 1) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期180天以上,逾期金额范围为：0-1000");
								checkResult.setResult(true);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 2) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期180天以上,逾期金额范围为：1000-5000");
								checkResult.setResult(true);
								checkResult.setRejectType(Integer.valueOf(1));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 3) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期180天以上,逾期金额范围为：5000-20000");
								checkResult.setResult(true);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore == 4) {
								checkResult.setSoucre(Integer.valueOf(2));
								checkResult.setDesc("逾期180天以上,逾期金额范围为：2w-10w");
								checkResult.setResult(false);
								checkResult.setRejectType(Integer.valueOf(0));
								checkResult.setDataBuildTime(record2.getDataBuildTime());
								return checkResult;
							}
							if (moneyScore != 5)
								continue;
							checkResult.setSoucre(Integer.valueOf(2));
							checkResult.setDesc("逾期180天以上,逾期金额范围为：10w以上");
							checkResult.setResult(false);
							checkResult.setRejectType(Integer.valueOf(0));
							checkResult.setDataBuildTime(record2.getDataBuildTime());
							return checkResult;
						}

					}

				}

			}

		}
		// GbossReqData gbossReqData = new GbossReqData();
		// gbossReqData.setQueryParam(name + "," + idNo);
		// gbossReqData.setQueryType("1");
		// gbossReqData.setSignature("");
		// Response<String> gBossRes =
		// this.identityService.blackList(gbossReqData);
		// if (gBossRes.getRequestCode().equals("200")) {
		// System.out.println(gBossRes.getObj().toString());
		// if ((gBossRes.getObj()).equals("查询成功_有数据")) {
		// checkResult.setSoucre(Integer.valueOf(1));
		// checkResult.setResult(false);
		// checkResult.setDesc(gBossRes.getRequestMsg());
		// checkResult.setCode("B1");
		// checkResult.setRejectType(Integer.valueOf(0));
		// return checkResult;
		// }
		// }
		checkResult.setCode(code);
		checkResult.setResult(result);

		return checkResult;
	}

	public CheckResult inSure(CheckInfoDto cInfoDto) {
		CheckResult checkResult = new CheckResult();
		boolean result = false;
		String code = "";
		BwInsureInfo bii = this.bwInsureInfoService.getByIdCard(cInfoDto.getIdNo());
		if (CommUtils.isNull(bii)) {
			code = "D2";
			checkResult.setResult(result);
			checkResult.setCode(code);
			checkResult.setRejectType(Integer.valueOf(0));
			checkResult.setDesc("数据库中未能找到身份证号为：" + cInfoDto.getIdNo() + "的数据");
			return checkResult;
		}

		if (!bii.getRealName().equals(cInfoDto.getName())) {
			code = "D1";
			checkResult.setResult(result);
			checkResult.setCode(code);
			checkResult.setRejectType(Integer.valueOf(0));
			checkResult.setDesc("姓名未能与真实姓名保持一致");
			return checkResult;
		}
		result = true;

		if (this.bwInsureRecordService.getRecords().size() < 15) {
			code = "D3";
			checkResult.setResult(result);
			checkResult.setCode(code);
			checkResult.setDesc("近3个月在本单位未能连续缴纳社保");
			checkResult.setRejectType(Integer.valueOf(1));
			return checkResult;
		}
		result = true;

		int workStart = Integer.parseInt(bii.getWorkStartDay().substring(0, 4));
		int bron = Integer.parseInt(cInfoDto.getIdNo().substring(6, 10));
		if (workStart - bron < 16) {
			code = "D4";
			checkResult.setResult(result);
			checkResult.setCode(code);
			checkResult.setRejectType(Integer.valueOf(0));
			checkResult.setDesc("参加工作年份减去出生年份小于16年");
			return checkResult;
		}
		result = true;

		checkResult.setResult(result);
		checkResult.setCode(code);
		return checkResult;
	}

	public CheckResult historyOrder(CheckInfoDto cInfoDto) {
		CheckResult checkResult = new CheckResult();
		boolean result = true;
		String code = "";
		int relationNum = 0;
		int unrelationNum = 0;

		relationNum = this.bwPersonInfoService.countRelation(cInfoDto.getMobile(), cInfoDto.getBwId());
		unrelationNum = this.bwPersonInfoService.countUnRelation(cInfoDto.getMobile(), cInfoDto.getBwId());
		if (relationNum + unrelationNum > 4) {
			code = "E3";
			checkResult.setResult(false);
			checkResult.setCode(code);
			checkResult.setRejectType(Integer.valueOf(0));
			checkResult.setDesc("申请人号码被超过4个申请人作为联系人使用过");
			return checkResult;
		}

		relationNum = this.bwPersonInfoService.countRelation(cInfoDto.getKinshipMobile(), cInfoDto.getBwId());
		unrelationNum = this.bwPersonInfoService.countUnRelation(cInfoDto.getKinshipMobile(), cInfoDto.getBwId());
		if (relationNum + unrelationNum > 3) {
			code = "E5";
			checkResult.setResult(false);
			checkResult.setCode(code);
			checkResult.setRejectType(Integer.valueOf(0));
			checkResult.setDesc("亲属联系人号码被超过3个申请人作为联系人使用过");
			return checkResult;
		}

		relationNum = this.bwPersonInfoService.countRelation(cInfoDto.getUnKinshipMobile(), cInfoDto.getBwId());
		unrelationNum = this.bwPersonInfoService.countUnRelation(cInfoDto.getUnKinshipMobile(), cInfoDto.getBwId());
		if (relationNum + unrelationNum > 4) {
			code = "E7";
			checkResult.setResult(false);
			checkResult.setCode(code);
			checkResult.setRejectType(Integer.valueOf(0));
			checkResult.setDesc("联系人号码被超过4个申请人作为联系人使用过");
			return checkResult;
		}

		checkResult.setResult(result);
		checkResult.setCode(code);
		return checkResult;
	}

	private CheckResult operate(CheckInfoDto cInfoDto, BwBorrower borrower) throws Exception {
		CheckResult checkResult = new CheckResult();
		checkResult.setResult(true);
		// 办理号码的身份证姓名非本人
		BwOperateBasic operateBasic = bwOperateBasicService.getOperateBasicById(cInfoDto.getBwId());
		if (CommUtils.isNull(operateBasic)) {
			checkResult.setResult(false);
			checkResult.setRejectType(1);
			checkResult.setDesc("运营商通话记录抓取失败");
			// 融360运营商
			checkResult.setSoucre(9);
			return checkResult;
		}

		try {
			// ******************融360姓名与注册姓名一个字都无法匹配则拒绝
			logger.info("----------融360姓名与注册姓名一个字都无法匹配则拒绝------开始---------");
			int matchSum = 0;
			String realName = operateBasic.getRealName();
			logger.info("----------融360姓名---------------" + realName);
			// BwBorrower borrower =
			// bwBorrowerService.findBwBorrowerById(cInfoDto.getBwId());
			if (borrower != null) {
				String name = borrower.getName();
				logger.info("----------注册名字---------------" + name);
				if (!CommUtils.isNull(realName) && !CommUtils.isNull(name)) {
					for (int j = 0; j < realName.length(); j++) {
						if (j == realName.length() - 1) {
							if (name.contains(realName.substring(j))) {
								matchSum++;
							}
						} else {
							if (name.contains(realName.substring(j, j + 1))) {
								matchSum++;
							}
						}
					}
				}
			}
			if (matchSum == 0) {
				checkResult.setResult(false);
				checkResult.setRejectType(1);
				checkResult.setDesc("融360姓名与注册姓名一个字都无法匹配");
				// 融360运营商
				checkResult.setSoucre(9);
				return checkResult;
			}
			logger.info("----------融360姓名与注册姓名一个字都无法匹配则拒绝------结束---------");
			// ******************
			// 运营商授权时间-运营商注册时间＜60天
			Date regTime = operateBasic.getRegTime();
			if (!CommUtils.isNull(regTime)) {
				int daySpace = MyDateUtils.getDaySpace(regTime, new Date());
				System.out.println("运营商授权时间-运营商注册时间的值为：" + daySpace);
				if (daySpace < 60) {
					checkResult.setResult(false);
					checkResult.setRejectType(1);
					checkResult.setDesc("运营商授权时间-运营商注册时间小于30天");
					// 融360运营商
					checkResult.setSoucre(9);
					return checkResult;
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		// 3个月内呼入与呼出前10的重叠个数≤1
		int repateNum = 0;
		List<String> inList = null;
		List<String> outList = null;
		Set<String> totalList = new HashSet<String>();
		try {
			inList = bwOperateVoiceService.inCountVoicetopTenEs(cInfoDto.getBwId());
			outList = bwOperateVoiceService.outCountVoicetopTenEs(cInfoDto.getBwId());
			if (CommUtils.isNull(inList) && CommUtils.isNull(outList)) {
				checkResult.setResult(false);
				checkResult.setRejectType(1);
				checkResult.setDesc("运营商通话记录抓取失败");
				// 融360运营商
				checkResult.setSoucre(9);
				return checkResult;
			}
			if (inList.size() > 0 && outList.size() > 0) {
				for (String outStr : outList) {
					totalList.add(outStr);
					for (String inStr : inList) {
						totalList.add(inStr);
						if (outStr.equals(inStr)) {
							repateNum++;
						}
					}
				}
				if (repateNum <= 1) {
					checkResult.setResult(false);
					checkResult.setRejectType(1);
					checkResult.setDesc("3个月内呼入与呼出前10的重叠个数≤1");
					// 融360运营商
					checkResult.setSoucre(9);
					return checkResult;
				}
			}
			// ****************************************紧急联系人不在呼入呼出前10直接拒绝
			logger.info("----------紧急联系人不在呼入呼出前10直接拒绝------开始---------");
			Boolean isContain = false;// 默认不包含
			String relationPhone = cInfoDto.getKinshipMobile();// 亲属联系人电话
			String unrelationPhone = cInfoDto.getUnKinshipMobile();// 非亲属联系人电话
			logger.info("----------亲属电话---------" + relationPhone + "---------非亲属电话--" + unrelationPhone);
			if (!CommUtils.isNull(inList)) {
				for (int i = 0; i < inList.size(); i++) {
					if (inList.get(i).equals(relationPhone) || inList.get(i).equals(unrelationPhone)) {
						isContain = true;
						break;
					}
				}

			}

			if (!CommUtils.isNull(outList)) {
				if (!isContain) {
					for (int i = 0; i < outList.size(); i++) {
						if (outList.get(i).equals(relationPhone) || outList.get(i).equals(unrelationPhone)) {
							isContain = true;
							break;
						}
					}
				}

			}
			logger.info("----------是否包含---------------" + isContain);
			if (!isContain) {
				checkResult.setResult(false);
				checkResult.setRejectType(1);
				checkResult.setDesc("紧急联系人不在呼入呼出前10直接拒绝");
				// 融360运营商
				checkResult.setSoucre(9);
				return checkResult;
			}
			logger.info("----------紧急联系人不在呼入呼出前10直接拒绝------结束---------");
			// *******************************************
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

		// 近3个月内呼入最频繁电话联系总次数＜40次 且 近3个月内呼出最频繁电话联系总次数＜40次 时
		Map<String, Object> in_map = bwOperateVoiceService.countVoiceTimesEsInTotal(cInfoDto.getBwId());
		Map<String, Object> out_map = bwOperateVoiceService.countVoiceTimesEsOutTotal(cInfoDto.getBwId());
		if (CommUtils.isNull(in_map) && CommUtils.isNull(out_map)) {
			checkResult.setResult(false);
			checkResult.setRejectType(1);
			checkResult.setDesc("运营商通话记录抓取失败");
			// 融360运营商
			checkResult.setSoucre(9);
			return checkResult;
		}
		int in_num = Integer.parseInt(in_map.get("num").toString());
		int out_num = Integer.parseInt(out_map.get("num").toString());
		if (in_num <= 40 || out_num <= 40) {
			checkResult.setResult(false);
			checkResult.setRejectType(1);
			checkResult.setDesc("近3个月内呼入最频繁电话联系总次数<=40次 且 近3个月内呼出最频繁电话联系总次数<=40次");
			// 融360运营商
			checkResult.setSoucre(9);
			return checkResult;
		}

		// 3个月内呼入前10电话全部次数相加的平均值≤15次 且 呼入前10第一名电话通话次数≤40 时
		List<Map<String, Object>> inLists = bwOperateVoiceService.countVoiceInTimesEs(cInfoDto.getBwId());
		if (CommUtils.isNull(inLists)) {
			checkResult.setResult(false);
			checkResult.setRejectType(1);
			checkResult.setDesc("运营商通话记录抓取失败");
			// 融360运营商
			checkResult.setSoucre(9);
			return checkResult;
		}
		if (inLists.size() > 0) {
			int firstNum = Integer.parseInt(inLists.get(0).get("num").toString());
			// 计算通话记录top10的平均值
			// int sumNum =
			// bwOperateVoiceService.SumVoiceTopTen(cInfoDto.getBwId());
			int sumNum = bwOperateVoiceService.SumVoiceTopTenEs(cInfoDto.getBwId());
			if ((sumNum / inLists.size()) <= 15 && firstNum <= 40) {
				checkResult.setResult(false);
				checkResult.setRejectType(1);
				checkResult.setDesc("3个月内呼入前10电话全部次数相加的平均值≤15次 且 呼入前10第一名电话通话次数≤40");
				// 融360运营商
				checkResult.setSoucre(9);
				return checkResult;
			}
		}

		// 呼入呼出号码中短号（号码位数小于10位）在此20个电话中占比达到50%时，触发规则
		List<String> phones = bwOperateVoiceService.sumShortPhoneEs(cInfoDto.getBwId());
		if (!CommUtils.isNull(phones)) {
			int shortnum = 0;
			for (String phone : totalList) {
				for (String sphone : phones) {
					if (phone.equals(sphone)) {
						shortnum++;
					}
				}
			}
			if (shortnum >= 10) {
				checkResult.setResult(false);
				checkResult.setRejectType(1);
				checkResult.setDesc("呼入呼出号码中短号（号码位数小于10位）在此20个电话中占比达到50%");
				// 融360运营商
				checkResult.setSoucre(9);
				return checkResult;
			}
		}
		// 运营商号码总通讯个数不足30个
		int contactNum = bwOperateVoiceService.sumContactEs(cInfoDto.getBwId());
		if (CommUtils.isNull(contactNum)) {
			checkResult.setResult(false);
			checkResult.setRejectType(1);
			checkResult.setDesc("运营商通话记录抓取失败");
			// 融360运营商
			checkResult.setSoucre(9);
			return checkResult;
		}
		if (contactNum < 30) {
			checkResult.setResult(false);
			checkResult.setRejectType(1);
			checkResult.setDesc("运营商号码总通讯个数不足30个");
			// 融360运营商
			checkResult.setSoucre(9);
			return checkResult;
		}
		return checkResult;
	}

	@Override
	public boolean check(Long borrowerId, Long orderId, boolean includeAddressBook) throws Exception {
		try {
			Date now = new Date();
			CheckInfoDto cInfoDto = new CheckInfoDto();
			BwBorrower borrower = bwBorrowerService.findBwBorrowerById(borrowerId);
			// 判断身份证以福建省-身份证35开头、河南-身份证41开头、辽宁-21、吉林-22、黑龙江-23、山东东营-3705、山东临沂-3713、山东滨州-3716
			// 青岛-3702 新疆-65开头 西藏-54开头 常州-3204的不给予借钱
			String idCardTop2 = borrower.getIdCard().substring(0, 2); // 截取身份证前2位
			String idCardTop4 = borrower.getIdCard().substring(0, 4); // 截取身份证前4位
			if (idCardTop2.equals("35") || idCardTop2.equals("41") || idCardTop2.equals("21") || idCardTop2.equals("22")
					|| idCardTop2.equals("23") || idCardTop2.equals("65") || idCardTop2.equals("54")
					|| idCardTop4.equals("3705") || idCardTop4.equals("3713") || idCardTop4.equals("3716")
					|| idCardTop4.equals("3702") || idCardTop4.equals("3204")) {
				// 修改工单状态为被拒
				updateOrder(orderId);
				// 保存至被拒记录
				BwRejectRecord record = new BwRejectRecord();
				// 永久被拒
				record.setRejectType(0);
				record.setRejectInfo("身份证前两位或四位不在范围内：" + idCardTop4);
				record.setCreateTime(now);
				record.setSource(7);
				record.setOrderId(orderId);
				bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
				// 更新成功返回
				return false;
			}
			// ******************验证系统平台黑名单******************************
			logger.info("----------验证系统平台黑名单------开始---------");
			Example example = new Example(BwBlacklist.class);
			String idNo = borrower.getIdCard();
			example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card",
					idNo.toUpperCase());
			List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
			if (!CommUtils.isNull(desList)) {
				// 修改工单状态为被拒
				updateOrder(orderId);
				// 保存至被拒记录
				BwRejectRecord record = new BwRejectRecord();
				// 永久被拒
				record.setRejectType(0);
				record.setRejectInfo("系统黑名单库有此身份证信息");
				record.setCreateTime(now);
				record.setSource(7);
				record.setOrderId(orderId);
				bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
				// 更新成功返回
				return false;
			}
			logger.info("----------验证系统平台黑名单------结束---------");
			// ******************验证系统平台黑名单*****//idNo*************************

			cInfoDto.setBwId(borrower.getId());
			cInfoDto.setIdNo(borrower.getIdCard());
			cInfoDto.setName(borrower.getName());
			cInfoDto.setMobile(borrower.getPhone());
			BwPersonInfo bInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
			cInfoDto.setKinshipName(bInfo.getRelationName());
			cInfoDto.setKinshipMobile(bInfo.getRelationPhone());
			cInfoDto.setUnKinshipName(bInfo.getUnrelationName());
			cInfoDto.setUnKinshipMobile(bInfo.getUnrelationPhone());// 非亲属联系人电话
			// 平台黑名单认证
			// 平台黑名单列表认证
			BwCreditImformation bwCreditImformation = bwCreditInformationService.findByNameAndIdCard(borrower.getName(),
					borrower.getIdCard());
			if (!CommUtils.isNull(bwCreditImformation)) {
				// 直接拒绝
				if (!bwCreditImformation.getType().equals(3)) {
					// 修改工单状态为被拒
					logger.info("将id为：" + orderId + "的工单修改为拒绝状态");
					updateOrder(orderId);
					// 保存至被拒记录
					BwRejectRecord record = new BwRejectRecord();
					record.setOrderId(orderId);
					// record.setRejectCode(operateRes.getCode());
					if (bwCreditImformation.getRejectType() == 0) {
						// 永久被拒
						record.setRejectType(0);
						record.setRejectInfo("比中本平台的征信数据黑名单");
						record.setCreateTime(now);
						record.setSource(7);
						record.setOrderId(orderId);
					} else {
						// 非永久被拒
						record.setRejectType(1);
						record.setCreateTime(now);
						record.setRejectInfo("比中本平台的征信数据黑名单");
						record.setSource(7);
						record.setOrderId(orderId);
					}
					bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
					return false;
				}

			}
			// 通讯录认证
			if (includeAddressBook) {
				BwContactList bwContactList = new BwContactList();
				bwContactList.setBorrowerId(borrowerId);
				long contactListNum = bwContactListService.findBwContactListByBorrowerIdEs(borrowerId);
				this.logger.info("根据借款人" + borrowerId + "查询通讯录联系人数量为：" + contactListNum);
				if (contactListNum < 40) {
					updateOrder(orderId);
					// 添加拒绝记录
					BwRejectRecord record = new BwRejectRecord();
					record.setRejectType(Integer.valueOf(1));
					record.setCreateTime(now);
					record.setRejectInfo("通讯录联系人小于40");
					record.setSource(Integer.valueOf(7));
					record.setOrderId(orderId);
					int rNum = bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
					this.logger.info("添加拒绝记录结果：" + rNum);
					return false;
				}
			}
			// 运营商认证//////////////////////////////////////////////////////////////////////////
			CheckResult operateRes = this.operate(cInfoDto, borrower);
			logger.info("运营商认证结果" + operateRes.isResult());
			logger.info("运营商认证结果code：" + operateRes.getCode());
			logger.info("运营商认证结果描述：" + operateRes.getDesc());
			logger.info("拒绝类型：" + operateRes.getRejectType());
			logger.info("来源：" + operateRes.getSoucre());
			if (!operateRes.isResult()) {
				// 修改工单状态为被拒
				updateOrder(orderId);
				// 保存至被拒记录
				BwRejectRecord record = new BwRejectRecord();
				record.setOrderId(orderId);
				record.setRejectCode(operateRes.getCode());
				if (!CommUtils.isNull(operateRes.getRejectType())) {
					if (operateRes.getRejectType() == 0) {
						// 永久被拒
						logger.info("添加至永久被拒记录,被拒类型为:" + operateRes.getRejectType());
						record.setRejectType(operateRes.getRejectType());
						record.setRejectInfo(operateRes.getDesc());
						record.setCreateTime(now);
						record.setSource(operateRes.getSoucre());
					} else {
						// 非永久被拒
						logger.info("添加至非永久被拒记录,被拒类型为:" + operateRes.getRejectType());
						record.setRejectType(operateRes.getRejectType());
						record.setCreateTime(now);
						record.setRejectInfo(operateRes.getDesc());
						record.setSource(operateRes.getSoucre());
					}
				}
				int bjrNum = bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
				logger.info("保存至拒绝记录结果：" + bjrNum);
				return false;
			}
			// 历史工单认证///////////////////////////////////////////////////////////////////////////////////
			CheckResult hisRes = this.historyOrder(cInfoDto);
			logger.info("历史工单认证结果" + hisRes.isResult());
			logger.info("历史工单认证结果code：" + hisRes.getCode());
			logger.info("历史工单认证结果描述：" + hisRes.getDesc());
			logger.info("拒绝类型：" + hisRes.getRejectType());
			if (!hisRes.isResult()) {
				// 修改工单状态为被拒
				updateOrder(orderId);
				// 保存至被拒记录
				BwRejectRecord record = new BwRejectRecord();
				record.setOrderId(orderId);
				record.setRejectCode(hisRes.getCode());
				if (!CommUtils.isNull(hisRes.getRejectType())) {
					if (hisRes.getRejectType() == 0) {
						// 永久被拒
						logger.info("添加至永久被拒记录,被拒类型为:" + hisRes.getRejectType());
						record.setRejectType(hisRes.getRejectType());
						record.setRejectInfo(hisRes.getDesc());
						record.setCreateTime(now);
					} else {
						// 非永久被拒
						logger.info("添加至非永久被拒记录,被拒类型为:" + hisRes.getRejectType());
						record.setRejectType(hisRes.getRejectType());
						record.setCreateTime(now);
						record.setRejectInfo(hisRes.getDesc());
					}
				}
				int bjrNum = bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
				logger.info("保存至拒绝记录结果：" + bjrNum);
				return false;
			}
			///////////////////////////////////////////////////////////////////////////////////
			// 黑名单认证
			if (CommUtils.isNull(bwCreditImformation) || (bwCreditImformation.getType().equals(3)
					&& MyDateUtils.getDaySpace(bwCreditImformation.getUpdateTime(), now) >= 30)) {
				CheckResult blackRes = this.blackList(borrower.getIdCard(), borrower.getName());
				logger.info("黑名单认证结果code：" + blackRes.getCode());
				logger.info("黑名单认证结果：" + blackRes.isResult());
				logger.info("黑名单认证结果描述：" + blackRes.getDesc());
				if (!blackRes.isResult()) {
					// 保存数据至黑名单列表
					if (CommUtils.isNull(bwCreditImformation)) {
						// 添加
						bwCreditImformation = new BwCreditImformation();
						bwCreditImformation.setName(borrower.getName());
						bwCreditImformation.setIdCard(borrower.getIdCard());
						bwCreditImformation.setSource(blackRes.getSoucre());
						bwCreditImformation.setRejectInfo(blackRes.getDesc());
						bwCreditImformation.setRejectType(blackRes.getRejectType());
						if (blackRes.getSoucre() == 1) {
							// 1.不良信息
							bwCreditImformation.setType(1);
						} else {
							// 黑名单
							bwCreditImformation.setType(2);
						}
						bwCreditImformation.setRemark(blackRes.getDesc());
						bwCreditImformation.setCreateTime(now);
						bwCreditImformation.setUpdateTime(now);
						bwCreditInformationService.save(bwCreditImformation);
					} else {
						bwCreditImformation.setName(borrower.getName());
						bwCreditImformation.setIdCard(borrower.getIdCard());
						bwCreditImformation.setSource(blackRes.getSoucre());
						bwCreditImformation.setRejectInfo(blackRes.getDesc());
						bwCreditImformation.setRejectType(blackRes.getRejectType());
						if (blackRes.getSoucre() == 1) {
							// 1.不良信息
							bwCreditImformation.setType(1);
						} else {
							// 黑名单
							bwCreditImformation.setType(2);
						}
						bwCreditImformation.setRemark(blackRes.getDesc());
						bwCreditImformation.setUpdateTime(now);
						bwCreditInformationService.update(bwCreditImformation);
					}
					// 修改工单状态为被拒
					updateOrder(orderId);
					// 保存至被拒记录
					BwRejectRecord record = new BwRejectRecord();
					int source = blackRes.getSoucre();
					logger.info("黑名单来源：" + source);
					if (!CommUtils.isNull(source)) {
						record.setSource(source);
					}
					record.setOrderId(orderId);
					if (!CommUtils.isNull(blackRes.getRejectType())) {
						if (blackRes.getRejectType() == 0) {
							// 永久被拒
							record.setRejectType(blackRes.getRejectType());
							record.setRejectInfo(blackRes.getDesc());
							record.setCreateTime(now);
							record.setSource(blackRes.getSoucre());
						} else {
							// 非永久被拒
							record.setRejectType(blackRes.getRejectType());
							record.setCreateTime(now);
							// String time = sdf.format(MyDateUtils.addDays(now,
							// 91));
							// logger.info("转化之后的时间为：" + time);
							record.setRejectInfo(blackRes.getDesc());
							record.setSource(blackRes.getSoucre());
						}
					}
					int bjrNum = bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
					logger.info("保存至被拒记录结果：" + bjrNum);
					return false;
				}
			}
			// 身份证信息认证/////////////////////////////////////////////////////////////////////
			String cardNo = "";// 银行卡号
			BwBankCard bankCard = bwBankCardService.findBwBankCardByBoorwerId(borrower.getId());
			if (bankCard != null) {
				cardNo = bankCard.getCardNo();
			}
			CheckResult identityRes = this.identity(borrower.getIdCard(), borrower.getName(), borrower.getPhone(),
					cardNo);
			logger.info("身份认证结果code：" + identityRes.getCode());
			logger.info("身份认证结果描述：" + identityRes.getDesc());
			if (!identityRes.isResult()) {
				// 保存数据至黑名单列表
				if (CommUtils.isNull(bwCreditImformation)) {
					// 添加
					bwCreditImformation = new BwCreditImformation();
					bwCreditImformation.setName(borrower.getName());
					bwCreditImformation.setIdCard(borrower.getIdCard());
					bwCreditImformation.setSource(identityRes.getSoucre());
					bwCreditImformation.setRejectInfo(identityRes.getDesc());
					bwCreditImformation.setRejectType(identityRes.getRejectType());
					if (identityRes.getSoucre() == 1) {
						// 1.不良信息
						bwCreditImformation.setType(1);
					} else {
						// 黑名单
						bwCreditImformation.setType(2);
					}
					bwCreditImformation.setRemark(identityRes.getDesc());
					bwCreditImformation.setCreateTime(now);
					bwCreditImformation.setUpdateTime(now);
					bwCreditInformationService.save(bwCreditImformation);
				} else {
					bwCreditImformation.setName(borrower.getName());
					bwCreditImformation.setIdCard(borrower.getIdCard());
					bwCreditImformation.setSource(identityRes.getSoucre());
					bwCreditImformation.setRejectInfo(identityRes.getDesc());
					bwCreditImformation.setRejectType(identityRes.getRejectType());
					if (identityRes.getSoucre() == 1) {
						// 1.不良信息
						bwCreditImformation.setType(1);
					} else {
						// 黑名单
						bwCreditImformation.setType(2);
					}
					bwCreditImformation.setRemark(identityRes.getDesc());
					bwCreditImformation.setUpdateTime(now);
					bwCreditInformationService.update(bwCreditImformation);
				}
				// 修改工单状态为被拒
				updateOrder(orderId);
				BwRejectRecord record = new BwRejectRecord();
				int source = identityRes.getSoucre();
				logger.info("身份认证信息来源：" + source);
				if (!CommUtils.isNull(source)) {
					record.setSource(source);
				}
				record.setOrderId(orderId);
				record.setRejectCode(identityRes.getCode());
				if (!CommUtils.isNull(identityRes.getRejectType())) {
					if (identityRes.getRejectType() == 0) {
						// 永久被拒
						record.setRejectType(identityRes.getRejectType());
						// record.setRejectInfo("系统评分不足");
						record.setRejectInfo(identityRes.getDesc());
						record.setCreateTime(now);
						record.setSource(identityRes.getSoucre());
					} else {
						// 非永久被拒
						record.setRejectType(identityRes.getRejectType());
						record.setCreateTime(now);
						record.setSource(identityRes.getSoucre());
						record.setRejectInfo(identityRes.getDesc());
					}
				}
				int bjrNum = bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
				logger.info("保存至被拒记录结果：" + bjrNum);
				return false;
			}
			// 打分///////////////////////////////////////////////////////////////////////////////////
			int rank = commentScore(borrowerId, orderId, includeAddressBook);
			if (rank == 5) {
				// 修改工单状态为被拒
				updateOrder(orderId);
				BwRejectRecord record = new BwRejectRecord();
				record.setRejectType(1);
				record.setRejectInfo("系统评级级别为E级");
				record.setCreateTime(now);
				record.setSource(11);
				record.setOrderId(orderId);
				bwRejectRecordService.saveBwRejectRecord(record, borrowerId);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return false;
		}
		return true;
	}

	private void updateOrder(Long orderId) {
		BwOrder order = bwOrderService.findBwOrderById(String.valueOf(orderId));
		order.setStatusId(7l);
		order.setUpdateTime(new Date());
		order.setRejectType(0);
		bwOrderService.updateBwOrder(order);
	}

	/**
	 * 打分卡
	 * 
	 * @param borrowerId
	 * @param orderId
	 * @param includeAddressBook
	 * @return
	 * @throws Exception
	 */
	private int commentScore(Long borrowerId, Long orderId, boolean includeAddressBook) throws Exception {
		Date now = new Date();
		int totalScore = 0;// 总评分
		BwScoreCard bwScoreCard = new BwScoreCard();
		logger.info("评分开始**********用户id*****工单id*******" + borrowerId + "*********" + orderId);
		// *****************************************按性别评分
		BwBorrower borrower = bwBorrowerService.findBwBorrowerById(borrowerId);
		if (borrower != null) {
			//////////////////////////////////////////////////////////////////////////
			logger.info("按年龄评分开始");
			int yearAge = borrower.getAge();
			if (yearAge >= 18 && yearAge <= 22) {// 15
				bwScoreCard.setScore(15);
				bwScoreCard.setWeiduName("18-22");
				bwScoreCard.setWeiduType(1);
				totalScore = totalScore + 15;
			} else if (yearAge >= 23 && yearAge <= 26) {// 25
				bwScoreCard.setScore(25);
				bwScoreCard.setWeiduName("23-26");
				bwScoreCard.setWeiduType(2);
				totalScore = totalScore + 25;
			} else if (yearAge >= 27 && yearAge <= 30) {// 20
				bwScoreCard.setScore(20);
				bwScoreCard.setWeiduName("27-30");
				bwScoreCard.setWeiduType(3);
				totalScore = totalScore + 20;
			} else if (yearAge >= 31 && yearAge <= 33) {// 10
				bwScoreCard.setScore(10);
				bwScoreCard.setWeiduName("31-33");
				bwScoreCard.setWeiduType(4);
				totalScore = totalScore + 10;
			} else if (yearAge > 33) {// 6
				bwScoreCard.setScore(6);
				bwScoreCard.setWeiduName("33以上");
				bwScoreCard.setWeiduType(5);
				totalScore = totalScore + 6;
			}
			bwScoreCard.setId(null);
			bwScoreCard.setIndexName("年龄");
			bwScoreCard.setIndexType(1);
			bwScoreCard.setBorrowerId(borrowerId);
			bwScoreCard.setOrderId(orderId);
			bwScoreCard.setUpdateTime(now);
			bwScoreCard.setCreateTime(now);
			int ageInt = bwScoreCardService.saveScoreCard(bwScoreCard);
			logger.info("按年龄评分==新增记录条数====分数======：" + ageInt + "==" + bwScoreCard.getScore());
			////////////////////////////////////////////////////////////////////////////////
			logger.info("按性别评分开始");
			int sex = borrower.getSex();
			if (sex == 0) {// 女 8分
				totalScore = totalScore + 8;
				bwScoreCard.setScore(8);
				bwScoreCard.setWeiduName("女");
				bwScoreCard.setWeiduType(2);
			} else {// 男 4分
				bwScoreCard.setScore(4);
				bwScoreCard.setWeiduName("男");
				bwScoreCard.setWeiduType(1);
				totalScore = totalScore + 4;
			}
			// 记录评分
			bwScoreCard.setId(null);
			bwScoreCard.setIndexName("性别");
			bwScoreCard.setIndexType(2);
			bwScoreCard.setUpdateTime(now);
			bwScoreCard.setCreateTime(now);
			int sexInt = bwScoreCardService.saveScoreCard(bwScoreCard);
			logger.info("按性别评分结束======新增记录条数======分数==============" + sexInt + "==" + bwScoreCard.getScore());

		}

		// *************工作信息
		logger.info("工作年限 评分开始");
		BwWorkInfo bwWorkInfo = bwWorkInfoService.findBwWorkInfoByOrderId(orderId);
		if (bwWorkInfo != null) {
			String workYears = bwWorkInfo.getWorkYears();
			// *************工作年限
			if (!CommUtils.isNull(workYears)) {
				if (workYears.equals("一年以内")) {// 3
					bwScoreCard.setScore(3);
					bwScoreCard.setWeiduName("一年以内");
					bwScoreCard.setWeiduType(1);
					totalScore = totalScore + 3;
				} else if (workYears.equals("1-3年")) {// 5
					bwScoreCard.setScore(5);
					bwScoreCard.setWeiduName("1-3年");
					bwScoreCard.setWeiduType(2);
					totalScore = totalScore + 5;
				} else if (workYears.equals("3-5年")) {// 10
					bwScoreCard.setScore(10);
					bwScoreCard.setWeiduName("3-5年");
					bwScoreCard.setWeiduType(3);
					totalScore = totalScore + 10;
				} else if (workYears.equals("5-10年")) {// 7
					bwScoreCard.setScore(7);
					totalScore = totalScore + 7;
					bwScoreCard.setWeiduName("5-10年");
					bwScoreCard.setWeiduType(4);
				} else {
					bwScoreCard.setScore(3);
					totalScore = totalScore + 3;
					bwScoreCard.setWeiduName("一年以内");
					bwScoreCard.setWeiduType(1);
				}
			} else {
				bwScoreCard.setScore(3);
				totalScore = totalScore + 3;
				bwScoreCard.setWeiduName("一年以内");
				bwScoreCard.setWeiduType(1);
			}
			bwScoreCard.setId(null);
			bwScoreCard.setIndexName("工作年限");
			bwScoreCard.setIndexType(3);
			bwScoreCard.setUpdateTime(now);
			bwScoreCard.setCreateTime(now);
			int gznxInt = bwScoreCardService.saveScoreCard(bwScoreCard);
			logger.info("工作年限新增条数============分数==============" + gznxInt + "==" + bwScoreCard.getScore());
			////////// 行业评分/////////////////////
			logger.info("行业评分开始");
			String industry = bwWorkInfo.getIndustry();
			if (!CommUtils.isNull(industry)) {
				if (industry.equals("金融")) {// 7
					bwScoreCard.setScore(7);
					totalScore = totalScore + 7;
					bwScoreCard.setWeiduName("金融");
					bwScoreCard.setWeiduType(2);
				} else if (industry.equals("房地产/建筑")) {// 3
					bwScoreCard.setScore(3);
					totalScore = totalScore + 3;
					bwScoreCard.setWeiduName("房地产/建筑");
					bwScoreCard.setWeiduType(4);
				} else if (industry.equals("互联网/计算机")) {// 10
					bwScoreCard.setScore(10);
					totalScore = totalScore + 10;
					bwScoreCard.setWeiduName("互联网/计算机");
					bwScoreCard.setWeiduType(1);
				} else if (industry.equals("通讯")) {// 10
					bwScoreCard.setScore(10);
					totalScore = totalScore + 10;
					bwScoreCard.setWeiduName("通讯");
					bwScoreCard.setWeiduType(1);
				} else if (industry.equals("服务/教育培训")) {// 5
					bwScoreCard.setScore(5);
					totalScore = totalScore + 5;
					bwScoreCard.setWeiduName("服务/教育培训");
					bwScoreCard.setWeiduType(3);
				} else if (industry.equals("政府机关/非盈利机构")) {// 7
					bwScoreCard.setScore(7);
					totalScore = totalScore + 7;
					bwScoreCard.setWeiduName("政府机关/非盈利机构");
					bwScoreCard.setWeiduType(2);
				} else if (industry.equals("制造业")) {// 3
					bwScoreCard.setScore(3);
					totalScore = totalScore + 3;
					bwScoreCard.setWeiduName("制造业");
					bwScoreCard.setWeiduType(4);
				} else if (industry.equals("零售")) {// 3
					bwScoreCard.setScore(3);
					totalScore = totalScore + 3;
					bwScoreCard.setWeiduName("零售");
					bwScoreCard.setWeiduType(4);
				} else if (industry.equals("广告业")) {// 3
					bwScoreCard.setScore(3);
					totalScore = totalScore + 3;
					bwScoreCard.setWeiduName("广告业");
					bwScoreCard.setWeiduType(4);
				} else if (industry.equals("贸易")) {// 3
					bwScoreCard.setScore(3);
					totalScore = totalScore + 3;
					bwScoreCard.setWeiduName("贸易");
					bwScoreCard.setWeiduType(4);
				} else if (industry.equals("医疗")) {// 3
					bwScoreCard.setScore(3);
					totalScore = totalScore + 3;
					bwScoreCard.setWeiduName("医疗");
					bwScoreCard.setWeiduType(4);
				} else if (industry.equals("物流/运输")) {// 5
					bwScoreCard.setScore(5);
					totalScore = totalScore + 5;
					bwScoreCard.setWeiduName("物流/运输");
					bwScoreCard.setWeiduType(3);
				}
			} else {
				bwScoreCard.setScore(3);
				totalScore = totalScore + 3;
				bwScoreCard.setWeiduName("其他");
				bwScoreCard.setWeiduType(4);
			}
			bwScoreCard.setId(null);
			bwScoreCard.setIndexName("行业类别");
			bwScoreCard.setIndexType(4);
			bwScoreCard.setUpdateTime(now);
			bwScoreCard.setCreateTime(now);
			int hyInt = bwScoreCardService.saveScoreCard(bwScoreCard);
			logger.info("行业评分 结束 新增条数============分数==============" + hyInt + "==" + bwScoreCard.getScore());
		}

		// *************手机号注册地与工作城市（是否属于同一城市）
		logger.info("手机号注册地与工作城市（是否属于同一城市）开始");
		String address = "";
		String cityName = "";
		BwOperateBasic operateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
		if (operateBasic != null) {
			address = operateBasic.getAddr();// 运营商注册地址
		}
		cityName = bwOrderService.getBwOrderById(orderId);
		if (!CommUtils.isNull(address) && !CommUtils.isNull(cityName) && address.contains(cityName)) {
			bwScoreCard.setScore(30);
			totalScore = totalScore + 30;
			bwScoreCard.setWeiduName("是");
			bwScoreCard.setWeiduType(1);
		} else {// 15
			bwScoreCard.setScore(15);
			totalScore = totalScore + 15;
			bwScoreCard.setWeiduName("否");
			bwScoreCard.setWeiduType(2);
		}

		bwScoreCard.setId(null);
		bwScoreCard.setIndexName("手机号注册地与工作城市");
		bwScoreCard.setIndexType(5);
		bwScoreCard.setUpdateTime(now);
		bwScoreCard.setCreateTime(now);
		int cityInt = bwScoreCardService.saveScoreCard(bwScoreCard);
		logger.info(
				"手机号注册地与工作城市（是否属于同一城市）结束==========新增条数=======分数=========" + cityInt + "==" + bwScoreCard.getScore());

		// *************手机授权时长
		logger.info("手机授权时长 评分开始");
		int monthMobil = 0;
		if (operateBasic != null) {
			Date regTime = operateBasic.getRegTime();
			if (!CommUtils.isNull(regTime)) {
				int monthSpace = MyDateUtils.getMonthSpace(regTime, new Date());
				monthMobil = monthSpace;
			}
		}
		if (monthMobil >= 0 && monthMobil <= 6) {// 10
			bwScoreCard.setScore(10);
			totalScore = totalScore + 10;
			bwScoreCard.setWeiduName("0-6");
			bwScoreCard.setWeiduType(1);
		} else if (monthMobil >= 7 && monthMobil <= 12) {// 16
			bwScoreCard.setScore(16);
			totalScore = totalScore + 16;
			bwScoreCard.setWeiduName("7-12");
			bwScoreCard.setWeiduType(2);
		} else if (monthMobil >= 13 && monthMobil <= 24) {// 25
			bwScoreCard.setScore(25);
			totalScore = totalScore + 25;
			bwScoreCard.setWeiduName("13-24");
			bwScoreCard.setWeiduType(3);
		} else if (monthMobil >= 25) {// 35
			bwScoreCard.setScore(35);
			totalScore = totalScore + 35;
			bwScoreCard.setWeiduName("25+");
			bwScoreCard.setWeiduType(4);
		}
		bwScoreCard.setId(null);
		bwScoreCard.setIndexName("手机授权时长");
		bwScoreCard.setIndexType(6);
		bwScoreCard.setUpdateTime(now);
		bwScoreCard.setCreateTime(now);
		int sjscInt = bwScoreCardService.saveScoreCard(bwScoreCard);
		logger.info("手机授权时长 评分结束=======新增条数==========分数=========" + sjscInt + "==" + bwScoreCard.getScore());
		// *************有无通讯录
		logger.info("有无通讯录评分开始");
		if (includeAddressBook) {
			bwScoreCard.setScore(5);
			totalScore = totalScore + 5;
			bwScoreCard.setWeiduName("是");
			bwScoreCard.setWeiduType(1);
		} else {
			bwScoreCard.setScore(2);
			totalScore = totalScore + 2;
			bwScoreCard.setWeiduName("否");
			bwScoreCard.setWeiduType(2);
		}
		bwScoreCard.setId(null);
		bwScoreCard.setIndexName("有无通讯录");
		bwScoreCard.setIndexType(7);
		bwScoreCard.setUpdateTime(now);
		bwScoreCard.setCreateTime(now);
		int isExistInt = bwScoreCardService.saveScoreCard(bwScoreCard);
		logger.info("有无通讯录评分结束=====新增条数=======分数===" + isExistInt + "==" + bwScoreCard.getScore());
		// *************通讯录数量
		logger.info("通讯录数量评分开始");
		int addressNum = 0;/////////////////////////
		if (includeAddressBook) {
			BwContactList bwContactList = new BwContactList();
			bwContactList.setBorrowerId(borrowerId);
			long contactListNum = bwContactListService.findBwContactListByBorrowerIdEs(borrowerId);
			addressNum = (int) (addressNum + contactListNum);

		}
		if (addressNum >= 0 && addressNum <= 100) {// 2
			bwScoreCard.setScore(2);
			totalScore = totalScore + 2;
			bwScoreCard.setWeiduName("0-100");
			bwScoreCard.setWeiduType(1);
		} else if (addressNum >= 101 && addressNum <= 300) {// 4
			bwScoreCard.setScore(4);
			totalScore = totalScore + 4;
			bwScoreCard.setWeiduName("101-300");
			bwScoreCard.setWeiduType(2);
		} else if (addressNum >= 301) {// 5
			bwScoreCard.setScore(5);
			totalScore = totalScore + 5;
			bwScoreCard.setWeiduName("301+");
			bwScoreCard.setWeiduType(3);
		}
		bwScoreCard.setId(null);
		bwScoreCard.setIndexName("通讯录数量");
		bwScoreCard.setIndexType(8);
		bwScoreCard.setUpdateTime(now);
		bwScoreCard.setCreateTime(now);
		int txlInt = bwScoreCardService.saveScoreCard(bwScoreCard);
		logger.info("通讯录数量评分结束=========新增条数=======分数==========" + txlInt + "==" + bwScoreCard.getScore());

		// *************手运营商（三个月呼入呼出总次数）
		logger.info("*手运营商（三个月呼入呼出总次数）评分开始");
		long inOrOutNum = bwOperateVoiceService.countVoiceTimesEsTotalNum(borrowerId);
		logger.info("三个月呼入呼出总次数====" + inOrOutNum);
		if (inOrOutNum >= 0 && inOrOutNum <= 180) {// 8
			bwScoreCard.setScore(8);
			totalScore = totalScore + 8;
			bwScoreCard.setWeiduName("0-108");
			bwScoreCard.setWeiduType(1);
		} else if (inOrOutNum >= 181 && inOrOutNum <= 230) {// 13
			bwScoreCard.setScore(13);
			totalScore = totalScore + 13;
			bwScoreCard.setWeiduName("181-230");
			bwScoreCard.setWeiduType(2);
		} else if (inOrOutNum >= 231 && inOrOutNum <= 280) {// 18
			bwScoreCard.setScore(18);
			totalScore = totalScore + 18;
			bwScoreCard.setWeiduName("231-280");
			bwScoreCard.setWeiduType(3);
		} else if (inOrOutNum >= 281 && inOrOutNum <= 500) {// 22
			bwScoreCard.setScore(22);
			totalScore = totalScore + 22;
			bwScoreCard.setWeiduName("281-500");
			bwScoreCard.setWeiduType(4);
		} else if (inOrOutNum >= 501) {// 27
			bwScoreCard.setScore(27);
			totalScore = totalScore + 27;
			bwScoreCard.setWeiduName("501+");
			bwScoreCard.setWeiduType(5);
		}
		bwScoreCard.setId(null);
		bwScoreCard.setIndexName("手运营商（三个月呼入呼出总次数）");
		bwScoreCard.setIndexType(9);
		bwScoreCard.setUpdateTime(now);
		bwScoreCard.setCreateTime(now);
		int hrhcInt = bwScoreCardService.saveScoreCard(bwScoreCard);
		logger.info("*手运营商（三个月呼入呼出总次数）评分结束=====新增条数=========分数============" + hrhcInt + "==" + bwScoreCard.getScore());
		// ********************运营商 三个月呼入呼出总次数前10个和通讯录匹配度 评分开始
		logger.info("*运营商 三个月呼入呼出总次数前10个和通讯录匹配度  个数  评分开始");
		int matchInt = 0;//////////////////////////////////////////////////////////////////////////////////
		List<Map<String, Object>> inAndOutlist = new ArrayList<>();
		List<BwContactList> listContact = new ArrayList<BwContactList>();
		inAndOutlist = bwOperateVoiceService.countVoiceTimesEsTop10(borrowerId);// 呼入呼出总次数前10
		if (includeAddressBook) {
			listContact = bwOperateVoiceService.queryContact(borrowerId);// 通讯录
			if (!CommUtils.isNull(listContact) && !CommUtils.isNull(inAndOutlist)) {
				for (Map<String, Object> map2 : inAndOutlist) {
					for (BwContactList bwContactList : listContact) {
						if (map2.get("receive_phone").equals(bwContactList.getPhone())) {
							matchInt++;
						}
					}
				}
			}
		}
		if (matchInt >= 0 && matchInt < 3) {// 2
			bwScoreCard.setScore(2);
			totalScore = totalScore + 2;
			bwScoreCard.setWeiduName("0-3");
			bwScoreCard.setWeiduType(1);
		} else if (inOrOutNum >= 3 && inOrOutNum < 6) {// 3
			bwScoreCard.setScore(3);
			totalScore = totalScore + 3;
			bwScoreCard.setWeiduName("3-6");
			bwScoreCard.setWeiduType(2);
		} else if (inOrOutNum >= 6) {// 5
			bwScoreCard.setScore(5);
			totalScore = totalScore + 5;
			bwScoreCard.setWeiduName("6+");
			bwScoreCard.setWeiduType(3);
		}
		bwScoreCard.setId(null);
		bwScoreCard.setIndexName("运营商 三个月呼入呼出总次数前10个和通讯录匹配度 ");
		bwScoreCard.setIndexType(10);
		bwScoreCard.setUpdateTime(now);
		bwScoreCard.setCreateTime(now);
		int piPeiInt = bwScoreCardService.saveScoreCard(bwScoreCard);
		logger.info("*运营商 三个月呼入呼出总次数前10个和通讯录匹配度 个数  评分结束====新增条数==========分数=======" + piPeiInt + "=="
				+ bwScoreCard.getScore());
		// *****************亿美平台申请数量
		int yimei = 0;
		logger.info("*亿美平台申请数量 评分开始");
		if (borrower != null) {
			Map<String, Object> map = yimeiMaindataService.saveYiMeiDataByPhone(borrower.getPhone());
			if (!CommUtils.isNull(map)) {
				yimei = Integer.valueOf(map.get("applicationCount").toString());
			}
		}
		if (yimei >= 0 && yimei < 2) {// 15
			bwScoreCard.setScore(15);
			totalScore = totalScore + 15;
			bwScoreCard.setWeiduName("0-2");
			bwScoreCard.setWeiduType(1);
		} else if (yimei >= 2 && yimei <= 6) {// 12
			bwScoreCard.setScore(12);
			totalScore = totalScore + 12;
			bwScoreCard.setWeiduName("2-6");
			bwScoreCard.setWeiduType(2);
		} else if (inOrOutNum > 6) {// 9
			bwScoreCard.setScore(9);
			totalScore = totalScore + 9;
			bwScoreCard.setWeiduName("6+");
			bwScoreCard.setWeiduType(3);
		}
		bwScoreCard.setId(null);
		bwScoreCard.setIndexName("亿美平台贷款申请数量 ");
		bwScoreCard.setIndexType(11);
		bwScoreCard.setUpdateTime(now);
		bwScoreCard.setCreateTime(now);
		int yimeiInt = bwScoreCardService.saveScoreCard(bwScoreCard);
		logger.info("*亿美平台申请数量 评分结束=========新增条数====分数===" + yimeiInt + "==" + bwScoreCard.getScore());
		// *******************修改订单评分

		BwOrder order = bwOrderService.findBwOrderById(String.valueOf(orderId));
		int rank = 0;
		if (order != null) {
			logger.info("*修改工单评分开始");
			order.setMark(String.valueOf(totalScore));
			order.setUpdateTime(now);
			// 信用等级：1:A,2:B,3:C,4:D 5:E',
			if (totalScore >= 64 && totalScore <= 94) {// E
				order.setRank(5);
			} else if (totalScore >= 95 & totalScore <= 115) {// D
				order.setRank(4);
			} else if (totalScore >= 116 & totalScore <= 128) {// C
				order.setRank(3);
			} else if (totalScore >= 129 & totalScore <= 150) {// B
				order.setRank(2);
			} else if (totalScore >= 151 & totalScore <= 175) {// A
				order.setRank(1);
			}
			rank = order.getRank();
			bwOrderService.updateBwOrder(order);
		}
		logger.info("*总评分===" + totalScore + "==信用等级(1:A,2:B,3:C,4:D 5:E)===" + rank);
		logger.info("评分结束****************************");
		return rank;
	}
}