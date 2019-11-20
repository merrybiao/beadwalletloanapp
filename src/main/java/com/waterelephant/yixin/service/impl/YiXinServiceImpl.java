package com.waterelephant.yixin.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.service.BaseService;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.yixin.dto.BackYiXinDto;
import com.waterelephant.yixin.dto.param.YiXinParamDto;
import com.waterelephant.yixin.dto.result.BwOrderDto;
import com.waterelephant.yixin.dto.result.BwRejectRecordDto;
import com.waterelephant.yixin.dto.result.YixinDataDto;
import com.waterelephant.yixin.dto.result.YixinDto;
import com.waterelephant.yixin.service.YiXinService;
import com.waterelephant.yixin.util.YiXinCommonUtil;
import com.waterelephant.yixin.util.YiXinConstant;

@Service
public class YiXinServiceImpl extends BaseService<BackYiXinDto, String> implements YiXinService {

	/**
	 * 查询相关的借款和逾期的内容
	 */
	@Override
	public List<Map<String, Object>> getBorrowingOrder(YiXinParamDto yiXinParamDto) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(
				" SELECT borrower.id, borrower.name,borrower.id_card,bw.create_time,bw.id as 'orderId',bw.repay_term,bw.borrow_amount,bw.status_id,plan.repay_type,plan.repay_status,bw.product_id,overdue.overdue_corpus as 'total',overdue.id  as 'overdueId'");
		sqlBuffer.append(
				",p.default_number,bw.product_type,plan.`repay_corpus_money`,bw.expect_money,plan.`repay_time`");
		sqlBuffer.append(" FROM bw_borrower borrower");
		sqlBuffer.append(" LEFT JOIN bw_order bw ON borrower.id = bw.borrower_id");
		sqlBuffer.append(" LEFT JOIN `bw_product_dictionary` p on bw.`product_id` = p.`id`");
		sqlBuffer.append(" LEFT JOIN bw_overdue_record overdue ON bw.id = overdue.order_id");
		sqlBuffer.append(" LEFT JOIN  bw_repayment_plan  plan on bw.id = plan.order_id");
		sqlBuffer.append(" where 1=1");
		sqlBuffer.append(" AND borrower.id_card ='" + yiXinParamDto.getData().getIdNo() + "'");
		sqlBuffer.append(" AND borrower.name ='" + yiXinParamDto.getData().getName().trim() + "'");
		sqlBuffer.append(" GROUP BY plan.`id`");
		sqlBuffer.append(" order by plan.`repay_time`");
		return sqlMapper.selectList(sqlBuffer.toString());
	}

	/**
	 * 查询风险名单
	 */
	@Override
	public List<Map<String, Object>> getRiskOrder(YiXinParamDto yiXinParamDto) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select  borrower.id_card,record.reject_info,record.create_time");
		sqlBuffer.append(" from bw_reject_record record left join bw_order bw");
		sqlBuffer.append(" on record.order_id  = bw.id");
		sqlBuffer.append(" LEFT JOIN bw_borrower borrower");
		sqlBuffer.append(" on bw.borrower_id = borrower.id");
		sqlBuffer.append(" where 1=1");
		sqlBuffer.append(" AND borrower.id_card ='" + yiXinParamDto.getData().getIdNo() + "'");
		return sqlMapper.selectList(sqlBuffer.toString());
	}

	/**
	 * 查询单期和多期的情况 code:18010
	 */
	@Override
	public List<Map<String, Object>> findOrderInfo(String orderId) {
		String sql = "SELECT bw.id as 'orderId',bw.product_type,plan.`repay_corpus_money`,"
				+ " plan.`repay_type` ,plan.`repay_status`" + " FROM bw_order bw"
				+ " LEFT JOIN bw_repayment_plan plan on bw.id = plan.order_id" + " WHERE bw.id = " + orderId;
		return sqlMapper.selectList(sql);
	}

	/**
	 * 处理逻辑拼接字符串 private List<BwOrderDto> loanRecords; //被拒的内容
	 * 同一个订单可能有多个逾期记录。这里是要去重的，将一个订单里的多次逾期金额，次数要相加。返回的是一个订单一条记录。 private List
	 * <BwRejectRecordDto> riskResults;
	 */
	@Override
	public BackYiXinDto backYiXinDto(YiXinParamDto yiXinParamDto, String rc4key) {
		BackYiXinDto backYiXinDto = new BackYiXinDto();
		List<BwOrderDto> bwOrderDtoList = new ArrayList<BwOrderDto>();
		List<BwRejectRecordDto> bwRejectRecordDtoList = new ArrayList<BwRejectRecordDto>();
		try {
			// 查询相关的借款和逾期的内容
			List<Map<String, Object>> list = getBorrowingOrder(yiXinParamDto);
			System.out.println("getBorrowingOrder方法查询的list长度为:" + list.size());
			List<Map<String, Object>> to_param_list = new ArrayList<Map<String, Object>>();
			if (!CommUtils.isNull(list) && list.size() > 0) {
				// 组装钱等数据
				Map<String, Map<Object, Object>> resultMap = new HashMap<String, Map<Object, Object>>();
				Map<String, Object> paramMap = new HashMap<String, Object>();
				for (Map<String, Object> map : list) {
					if (!CommUtils.isNull(map.get("orderId"))) {
						paramMap.put(map.get("orderId").toString(), map.get("orderId"));
					}
				}
				for (Map.Entry entry : paramMap.entrySet()) {
					String orderId = entry.getKey().toString();
					int num = 0;
					double total = 0.0;
					Map<Object, Object> reuslt = new HashMap<Object, Object>();
					for (Map<String, Object> map : list) {
						String param_orderId = map.get("orderId").toString();
						if (!CommUtils.isNull(param_orderId) && param_orderId.equals(orderId)) {
							// 修改 老版本只有单期 现在存在多期的情况 code:18010
							// List<Map<String, Object>> list2 = findOrderInfo(param_orderId);
							// 单期
							if (map.get("product_type").toString().equals("1")) {
								// 钱是否存在
								String money = map.get("total") == null ? "" : map.get("total").toString();
								if (!CommUtils.isNull(money)) {
									total += Double.parseDouble(money);
									
									//修改源代码---开始
									if(map.get("repay_status").toString().equals("3")){  //逾期才开始记录逾期数
										num++;
									}
									//修改源代码---结束
									
								}
							} else {
								// 多期 repay_type为2则为逾期还款 repay_status为1则为未还款 3为已垫付
								if (!CommUtils.isNull(map.get("repay_type"))
										&& !CommUtils.isNull(map.get("repay_status"))) {
									if (map.get("repay_type").toString().equals("2")
											&& (map.get("repay_status").toString().equals("1")
													|| map.get("repay_status").toString().equals("3"))) {
										String money = map.get("repay_corpus_money") == null ? ""
												: map.get("repay_corpus_money").toString();
										if (!CommUtils.isNull(money)) {
											total += Double.parseDouble(money);
											num++;
										}
									}
								}
							}
						}
					}
					System.out.println("num为:" + num);
					reuslt.put(total, num);
					resultMap.put(orderId, reuslt);
//					System.out.println(reuslt.toString());
//					System.out.println(resultMap.toString());
					// 去重
					for (Map<String, Object> param_map : list) {
						// 添加分期逻辑
						String param_orderId = param_map.get("orderId").toString();
						// String repayStatus = param_map.get("repay_status").toString();
						// 单期
						if (param_map.get("product_type").toString().equals("1")) {
							// String repayType = param_map.get("repay_type").toString();
							if (!CommUtils.isNull(param_orderId) && param_orderId.equals(orderId)) {
								
								//修改源代码---开始
								if(Objects.equals("7",param_map.get("status_id").toString())||Objects.equals("6",param_map.get("status_id").toString())){
									to_param_list.add(param_map);
								}else if(param_map.containsKey("repay_status")&&StringUtils.isNotBlank(param_map.get("repay_status").toString())&&Objects.equals("3",param_map.get("repay_status").toString())){
									to_param_list.add(param_map);
								}
								//修改源代码---结束
								
							}
						} else {
							if (!CommUtils.isNull(param_orderId) && param_orderId.equals(orderId)) {
								to_param_list.add(param_map);
							}
						}
					}
				}
				int count = 0;
				// M3
				Integer M3_total = 0;
				// M6
				Integer M6_total = 0;
				// 逾期
				String status_m = "";
				//
				int ms = 0;
				for (Map<String, Object> map : to_param_list) {
					count++;
					// 获取ordreId
					String orderId = YiXinCommonUtil.MapUtil.getKeyByMap(map, "orderId");

					BwOrderDto bwOrderDto = new BwOrderDto();
					String name = YiXinCommonUtil.MapUtil.getKeyByMap(map, "name");
					if (!CommUtils.isNull(name)) {
						// 借款人的姓名
						bwOrderDto.setName(name);
					}
					String certNo = YiXinCommonUtil.MapUtil.getKeyByMap(map, "id_card");
					if (!CommUtils.isNull(certNo)) {
						// 被查询借款人的身份证号码
						bwOrderDto.setCertNo(certNo);
					}
					String loanDate = YiXinCommonUtil.MapUtil.getKeyByMap(map, "create_time");
					if (!CommUtils.isNull(loanDate)) {
						// 借款时间
						bwOrderDto.setLoanDate(CommUtils
								.convertDateToString(CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd"), "yyyyMM"));
					}
					String periods = YiXinCommonUtil.MapUtil.getKeyByMap(map, "default_number");
					if (!CommUtils.isNull(periods)) {
						// 借款期数
						bwOrderDto.setPeriods(Integer.parseInt(periods));
					}else {
						// 为null默认为1期
						bwOrderDto.setPeriods(1);
					}
					String loanAmount = YiXinCommonUtil.MapUtil.getKeyByMap(map, "borrow_amount");
					if (!CommUtils.isNull(loanAmount)) {
						// 借款金额
						bwOrderDto.setLoanAmount(initMoneyRange(loanAmount));
					}

					String approvalStatusCode = YiXinCommonUtil.MapUtil.getKeyByMap(map, "status_id");
					if (!CommUtils.isNull(approvalStatusCode)) {
						/**
						 * 审批结果码      201 审核中       202 批贷已放款      203 拒贷      204 客户放弃
						 */
						// 审核中
						if (approvalStatusCode.equals("2") || approvalStatusCode.equals("3")
								|| approvalStatusCode.equals("4") || approvalStatusCode.equals("5")
								|| approvalStatusCode.equals("11") || approvalStatusCode.equals("12")
								|| approvalStatusCode.equals("14")) {
							approvalStatusCode = YiXinConstant.REVIEWING;
						}
						// 批贷已放款
						if (approvalStatusCode.equals("9") || approvalStatusCode.equals("13")
								|| approvalStatusCode.equals("6")) {
							approvalStatusCode = YiXinConstant.BATCHLOAN;
						}
						// 拒贷
						if (approvalStatusCode.equals("7") || "8".equals(approvalStatusCode)) {
							approvalStatusCode = YiXinConstant.DENIEDLOANS;
							String expectMoney = YiXinCommonUtil.MapUtil.getKeyByMap(map, "expect_money");
							bwOrderDto.setLoanAmount(initMoneyRange(expectMoney));
						}
						// 客户放弃
						if (approvalStatusCode.equals("1")) {
							approvalStatusCode = YiXinConstant.CUSTOMERUP;
							// bwOrderDto.setLoanAmount("(0,1000]");
							String expectMoney = YiXinCommonUtil.MapUtil.getKeyByMap(map, "expect_money");
							bwOrderDto.setLoanAmount(initMoneyRange(expectMoney));
						}
						bwOrderDto.setApprovalStatusCode(approvalStatusCode);
					}
					String loanStatusCode = YiXinCommonUtil.MapUtil.getKeyByMap(map, "repay_status");
					String loanStatusCode2 = loanStatusCode;
					// 还款的类别
					String repay_type = YiXinCommonUtil.MapUtil.getKeyByMap(map, "repay_type");
					if (!CommUtils.isNull(loanStatusCode) && !CommUtils.isNull(repay_type)) {
						/**
						 * 还款状态码 301 正常 302 逾期 303 结清
						 * 
						 */
						String statusId = YiXinCommonUtil.MapUtil.getKeyByMap(map, "status_id");
						// 逾期还款
						// (repay_type.equals("2") && loanStatusCode.equals("1"))||
						// (repay_type.equals("2") && loanStatusCode.equals("3"))&&
						if ("13".equals(statusId)) {
							loanStatusCode2 = YiXinConstant.BACK_MONEY_WITHIN;
						} else
						// 6为结束，9还款中，13为逾期
						// 正常还款
						// if ((repay_type.equals("1") && loanStatusCode.equals("1"))) {
						// loanStatusCode2 = YiXinConstant.BACK_MONEY_NORAMT;
						// }
						if ("9".equals(statusId)) {
							loanStatusCode2 = YiXinConstant.BACK_MONEY_NORAMT;
						} else

						// 结清
						// ((repay_type.equals("1") || repay_type.equals("3")) &&
						// loanStatusCode.equals("2"))|| (repay_type.equals("2") &&
						// loanStatusCode.equals("2"))&&
						if ("6".equals(statusId)) {
							loanStatusCode2 = YiXinConstant.BACK_MONEY_END;
						}

						bwOrderDto.setLoanStatusCode(loanStatusCode2);
					}

					/**
					 * 借款类型码 21 信用 22 抵押 23 担保
					 * 
					 */
					bwOrderDto.setLoanTypeCode(YiXinConstant.BORROWING_TYPE_CREDIT);

					String overdueAmount = getVal(orderId, resultMap, 1);

					// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					/*	if (!CommUtils.isNull(overdueAmount)) {
							// 逾期还款
							if ((repay_type.equals("2") && loanStatusCode.equals("1"))
									|| (repay_type.equals("2") && loanStatusCode.equals("3"))) {
								// 逾期金额
								bwOrderDto.setOverdueAmount(initMoneyRange(overdueAmount));
							}
						}*/

					/**
					 * 逾期情况
					 * 
					 */
					String overdueStatus = getVal(orderId, resultMap, 2);
					Integer overdueTotal = 0;

					// 单期 code:18010
					if (map.get("product_type").toString().equals("1")) {
						if (!CommUtils.isNull(overdueStatus) && !overdueAmount.equals("0.0")) {
							// 逾期还款
							if ((repay_type.equals("2") && loanStatusCode.equals("1"))
									|| (repay_type.equals("2") && loanStatusCode.equals("3"))) {
								Integer status = Integer.parseInt(overdueStatus);

								if (status > 0 && status < 6) {
									// 逾期总次数
									overdueTotal++;
									status_m = "M" + status;
									if (status > 3) {
										M3_total++;
									}
								}
								if (status >= 6) {
									overdueTotal++;
									status_m = "M6+";
									M6_total++;
								}
							}
						}
						bwOrderDto.setOverdueStatus(status_m);
					} else {
						if (count != 1 && (count - 1) % 4 == 0) {
							ms = 0;
						}
						// 多期 TO DO
						if (!CommUtils.isNull(overdueStatus) && !overdueAmount.equals("0.0")) {
							// 逾期还款
							if ((repay_type.equals("2") && loanStatusCode.equals("1"))
									|| (repay_type.equals("2") && loanStatusCode.equals("3"))) {
								Integer status = Integer.parseInt(overdueStatus);

								if (status > 0 && status < 6) {
									// 逾期总次数 =====================================需修改
									overdueTotal++;
									// Date repayTime = (Date) map.get("repay_time"); // code:18010
									// long rt = repayTime.getTime();
									// long nt = new Date().getTime();
									// long a = (nt - rt) / (1000 * 60 * 60 * 24);
									// ms = (int) Math.ceil(a / 31.00);
									// status_m = "M" + status;
									ms = status;
									if (status > 3) {
										M3_total++;
									}
								}
								if (status >= 6) {
									overdueTotal++;
									status_m = "M6+";
									M6_total++;
								}

								M3_total++;
							}
						}
					}

					status_m = "M" + ms;
					if (ms != 0) {
						bwOrderDto.setOverdueStatus(status_m);
					}

					/**
					 * 历史逾期总次数 无逾期可以不填写
					 * 
					 * 
					 */
					// if (!CommUtils.isNull(overdueStatus) && !overdueStatus.equals("0")) {
					// bwOrderDto.setOverdueTotal(overdueStatus.equals("0") ? "" : overdueStatus);
					// }
					if (!CommUtils.isNull(overdueStatus)) {
						bwOrderDto.setOverdueTotal(0 == Integer.parseInt(overdueStatus) ? "" : overdueStatus);
					}
					/**
					 * 历史逾期 出现过的M3的次数之和
					 */
					if (M3_total > 0) {
						bwOrderDto.setOverdueM3(M3_total.toString());
					}

					/**
					 * 历史逾期 出现过的M6的次数之和
					 */
					if (M6_total > 0) {
						bwOrderDto.setOverdueM6(M6_total.toString());
					}

					// 工单状态
					// String overdueId = YiXinCommonUtil.MapUtil.getKeyByMap(map, "overdueId");

					// 如果借款金额或者借款人借款时间为空则并且去重复不添加信息
//					System.out.println("========开始保存！");
					String statusId = YiXinCommonUtil.MapUtil.getKeyByMap(map, "status_id");
//					System.out.println("========statusId为:"+statusId);
//					System.out.println("========loanAmount为:"+loanAmount);
//					System.out.println("========loanDate为:"+loanDate);
//					System.out.println("========name为:"+name);
					if (!CommUtils.isNull(loanDate) && !CommUtils.isNull(name)) {
						//!CommUtils.isNull(loanAmount) && 
						if (map.get("product_type").toString().equals("1")) {
//							System.out.println("单期："+ JSON.toJSONString(bwOrderDto));
							if(!CommUtils.isNull(overdueAmount)&& !"0.0".equals(overdueAmount)) {
								bwOrderDto.setOverdueAmount(initMoneyRange(overdueAmount));
							}
							bwOrderDtoList.add(bwOrderDto);
						} else {
							if (count % 4 == 0
									&& ("13".equals(statusId) || "9".equals(statusId) || "6".equals(statusId))) {
								String overdueAmount1 = getVal(orderId, resultMap, 1);
								// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
								if (!CommUtils.isNull(overdueAmount1) && !"0.0".equals(overdueAmount1)) {
									// 逾期金额
									bwOrderDto.setOverdueAmount(initMoneyRange(overdueAmount1));
								}
//								System.out.println("分期：" + JSON.toJSONString(bwOrderDto));
								bwOrderDtoList.add(bwOrderDto);
							} else if ((!"13".equals(statusId) && !"9".equals(statusId) && !"6".equals(statusId))) {
//								System.out.println( JSON.toJSONString("拒绝："+bwOrderDto));
								bwOrderDtoList.add(bwOrderDto);
							}
						}
//						System.out.println("========保存结束！");
					}
//					System.out.println("========保存结束后bwOrderDtoList长度为："+bwOrderDtoList.size());
//					System.out.println("========保存结束后bwOrderDtoList值为："+JSON.toJSONString(bwOrderDtoList));
				}
			}
			// 查询风险名单
			List<Map<String, Object>> riskOrderList = getRiskOrder(yiXinParamDto);
			if (!CommUtils.isNull(riskOrderList) && riskOrderList.size() > 0) {
				for (Map<String, Object> map : riskOrderList) {
					BwRejectRecordDto bwRejectRecordDto = new BwRejectRecordDto();
					/**
					 * 命中项码 101010
					 */
					bwRejectRecordDto.setRiskItemTypeCode("101010");

					String riskItemValue = YiXinCommonUtil.MapUtil.getKeyByMap(map, "id_card");
					if (!CommUtils.isNull(riskItemValue)) {
						/**
						 * 命中内容 --身份证号码
						 */
						bwRejectRecordDto.setRiskItemValue(riskItemValue);
					}

					String rishDetail = YiXinCommonUtil.MapUtil.getKeyByMap(map, "reject_info");
					if (!CommUtils.isNull(rishDetail)) {
						/**
						 * 风险明细 描述
						 * 
						 */
						bwRejectRecordDto.setRiskDetail(rishDetail);
					}

					String riskTime = YiXinCommonUtil.MapUtil.getKeyByMap(map, "create_time");
					if (!CommUtils.isNull(rishDetail)) {
						/**
						 * 风险最近的时间 yyyy
						 */
						SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY");
						bwRejectRecordDto.setRiskTime(dateFormat.format(dateFormat.parse(riskTime)));
					}
					bwRejectRecordDtoList.add(bwRejectRecordDto);
				}
			}

			// 拼接字符串
			backYiXinDto.setErrorCode("0000");
			backYiXinDto.setMessage("数据查询成功");
//			System.out.println("=======开始装配所有参数！");
			YixinDto yixinDto = initYixinDto(bwOrderDtoList, bwRejectRecordDtoList);

			// 测试===================
			YixinDataDto data = yixinDto.getData();
			List<BwOrderDto> loanRecords = data.getLoanRecords();
			List<BwRejectRecordDto> riskResults = data.getRiskResults();
			System.out.println("=========loanRecords的相关信息为：" + JSON.toJSONString(loanRecords));
			System.out.println("=========riskResults的相关信息为：" + JSON.toJSONString(riskResults));
			/*for (BwRejectRecordDto bwRejectRecordDto : riskResults) {
				System.out.println(JSON.toJSONString(bwRejectRecordDto));
			}
			System.out.println("================================================");
			for (BwOrderDto bwOrderDto : loanRecords) {
				System.out.println(JSON.toJSONString(bwOrderDto));
			}*/

			backYiXinDto.setParams(URLEncoder.encode(YiXinCommonUtil.Rc4Util.encodeStr(yixinDto, rc4key), "utf-8"));
			return backYiXinDto;
		} catch (Exception e) {
			e.printStackTrace();
			// 拼接字符串
			backYiXinDto.setErrorCode("0000");
			backYiXinDto.setMessage("数据查询");
			YixinDto yixinDto = initYixinDto(bwOrderDtoList, bwRejectRecordDtoList);
			try {
				backYiXinDto.setParams(URLEncoder.encode(YiXinCommonUtil.Rc4Util.encodeStr(yixinDto, rc4key), "utf-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			return backYiXinDto;
		}
	}

	/**
	 * 组装对象
	 * 
	 * @return
	 */
	public YixinDto initYixinDto(List<BwOrderDto> bwOrderDtoList, List<BwRejectRecordDto> bwRejectRecordDtoList) {
		// 拼接参数
		YixinDto yixinDto = new YixinDto();
		YixinDataDto yixinDataDto = new YixinDataDto();
		yixinDataDto.setLoanRecords(bwOrderDtoList);
		yixinDataDto.setRiskResults(bwRejectRecordDtoList);
		yixinDto.setData(yixinDataDto);
		yixinDto.setTx("202");
		yixinDto.setVersion("v3");
		return yixinDto;
	}

	/**
	 * 组装相关钱的范围
	 */
	public String initMoneyRange(String money) {
		if (CommUtils.isNull(money)) {
			return "";
		}
		String result = "";
		Double amount = Double.parseDouble(money);
		if (0 < amount && amount <= 1000) {
			result = "(0,1000]";
		}
		if (1000 < amount && amount <= 5000) {
			result = "(1000,5000]";
		}
		if (5000 < amount && amount <= 10000) {
			result = "(5000,10000]";
		}
		if (10000 < amount && amount <= 20000) {
			result = "(10000,20000]";
		}
		if (20000 < amount && amount <= 50000) {
			result = "(20000,50000]";
		}
		if (50000 < amount && amount <= 100000) {
			result = "(50000,100000]";
		}
		if (100000 < amount) {
			result = "(100000,+)";
		}
		return result;
	}

	/**
	 * 获取total和num
	 * 
	 */
	public String getVal(String orderId, Map<String, Map<Object, Object>> resultMap, Integer type) {
		String result = "";
		if (CommUtils.isNull(orderId)) {
			return null;
		}
		Map<Object, Object> map = resultMap.get(orderId);
		for (Map.Entry entry : map.entrySet()) {
			// 获取total
			if (type == 1) {
				result = entry.getKey().toString();
			}
			// 获取num
			if (type == 2) {
				result = entry.getValue().toString();
			}
		}
		return result;
	}

}
