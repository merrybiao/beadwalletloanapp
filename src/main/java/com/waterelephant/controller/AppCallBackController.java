package com.waterelephant.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.request.AppSignCardReqData;
import com.beadwallet.entity.request.RegReqData;
import com.beadwallet.entity.response.AppSignCardRspData;
import com.beadwallet.entity.response.CommonRspData;
import com.beadwallet.entity.response.ResponsePayResult;
import com.beadwallet.servcie.BeadwalletService;
import com.beadwallet.service.entity.request.QhzxReqData;
import com.beadwallet.service.entity.response.InsureRspData;
import com.beadwallet.service.entity.response.OperateRsp;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entity.response.RongAddInfo;
import com.beadwallet.service.entity.response.RongOrder;
import com.beadwallet.service.entity.response.RongOrderDetail;
import com.beadwallet.service.entiyt.middle.Data_list;
import com.beadwallet.service.entiyt.middle.Flow;
import com.beadwallet.service.entiyt.middle.QueryData;
import com.beadwallet.service.entiyt.middle.Record2;
import com.beadwallet.service.entiyt.middle.RongOperateDataList;
import com.beadwallet.service.rong360.service.BeadWalletRongCarrierService;
import com.beadwallet.service.serve.BeadWalletQhzxService;
import com.beadwallet.service.serve.BeadWalletRongOrderService;
import com.waterelephant.constants.OrderStatusConstant;
import com.waterelephant.dataSource.DataSource;
import com.waterelephant.dataSource.DataSourceHolderManager;
import com.waterelephant.dto.MoxieTaskDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwInsureInfo;
import com.waterelephant.entity.BwInsureRecord;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwQhzxBlack;
import com.waterelephant.entity.BwRateDictionary;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.moxie.entity.CarrierBillTask;
//import com.waterelephant.rongCarrier.JSonEntity.AreaAnalysis;
//import com.waterelephant.rongCarrier.JSonEntity.AreaAnalysisDetail;
//import com.waterelephant.rongCarrier.JSonEntity.BasicInfo;
//import com.waterelephant.rongCarrier.JSonEntity.CallLog;
//import com.waterelephant.rongCarrier.JSonEntity.Detail;
//import com.waterelephant.rongCarrier.JSonEntity.EmergencyAnalysis;
//import com.waterelephant.rongCarrier.JSonEntity.HeadInfo;
//import com.waterelephant.rongCarrier.JSonEntity.InputInfo;
//import com.waterelephant.rongCarrier.JSonEntity.MidScore;
//import com.waterelephant.rongCarrier.JSonEntity.MonthlyConsumption;
import com.waterelephant.rongCarrier.JSonEntity.OpReport;
//import com.waterelephant.rongCarrier.JSonEntity.RiskAnalysis;
//import com.waterelephant.rongCarrier.JSonEntity.SpecialCate;
//import com.waterelephant.rongCarrier.JSonEntity.SpecialCateMonthDetail;
//import com.waterelephant.rongCarrier.JSonEntity.SpecialCatePhoneDetail;
//import com.waterelephant.rongCarrier.JSonEntity.TripAnalysis;
//import com.waterelephant.rongCarrier.JSonEntity.UserPortrait;
//import com.waterelephant.rongCarrier.JSonEntity.UserPortraitActiveDays;
//import com.waterelephant.rongCarrier.JSonEntity.UserPortraitSpecialCallInfo;
//import com.waterelephant.rongCarrier.JSonEntity.XGReturn;
//import com.waterelephant.rongCarrier.entity.XgAreaAnalysis;
//import com.waterelephant.rongCarrier.entity.XgAreaAnalysisDetail;
//import com.waterelephant.rongCarrier.entity.XgCallLog;
//import com.waterelephant.rongCarrier.entity.XgCallLogDetail;
//import com.waterelephant.rongCarrier.entity.XgEmergencyAnalysis;
//import com.waterelephant.rongCarrier.entity.XgMidScore;
//import com.waterelephant.rongCarrier.entity.XgMonthlyConsumption;
//import com.waterelephant.rongCarrier.entity.XgOverall;
//import com.waterelephant.rongCarrier.entity.XgSpecialCate;
//import com.waterelephant.rongCarrier.entity.XgSpecialCateMonthDetail;
//import com.waterelephant.rongCarrier.entity.XgSpecialCatePhoneDetail;
//import com.waterelephant.rongCarrier.entity.XgTripAnalysis;
//import com.waterelephant.rongCarrier.entity.XgTripAnalysisDetail;
//import com.waterelephant.rongCarrier.entity.XgUserSpecialCallInfo;
//import com.waterelephant.rongCarrier.service.XgAreaAnalysisDetailService;
//import com.waterelephant.rongCarrier.service.XgAreaAnalysisService;
//import com.waterelephant.rongCarrier.service.XgCallLogDetailService;
//import com.waterelephant.rongCarrier.service.XgCallLogService;
//import com.waterelephant.rongCarrier.service.XgEmergencyAnalysisService;
//import com.waterelephant.rongCarrier.service.XgMidScoreService;
//import com.waterelephant.rongCarrier.service.XgMonthlyConsumptionService;
//import com.waterelephant.rongCarrier.service.XgOverallService;
//import com.waterelephant.rongCarrier.service.XgSpecialCateMonthDetailService;
//import com.waterelephant.rongCarrier.service.XgSpecialCatePhoneDetailService;
//import com.waterelephant.rongCarrier.service.XgSpecialCateService;
//import com.waterelephant.rongCarrier.service.XgTripAnalysisDetailService;
//import com.waterelephant.rongCarrier.service.XgTripAnalysisService;
//import com.waterelephant.rongCarrier.service.XgUserSpecialCallInfoService;
import com.waterelephant.service.BwInsureInfoService;
import com.waterelephant.service.BwInsureRecordService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.BwQhzxBlackService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.BwRongOrderService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwOperateService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.InsureService;
import com.waterelephant.service.XgBusiService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.service.impl.BwRateDictionaryService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;

/**
 * app第三方回调地址
 *
 * @author huwei
 */
@Controller
@RequestMapping("/app/callBack")
public class AppCallBackController {
	
	private Logger logger = LoggerFactory.getLogger(AppCallBackController.class);

	@Autowired
	private InsureService insureService;
	@Autowired
	private BwInsureRecordService bwInsureRecordService;
	@Autowired
	private BwInsureInfoService bwInsureInfoService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private IBwOperateService bwOperateService;
	@Autowired
	private BwBorrowerService bwBorrowerService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private BwRongOrderService bwRongOrderService;
	@Autowired
	private BwQhzxBlackService bwQhzxBlackService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private BwRateDictionaryService bwRateDictionaryService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;

//	@Autowired
//	private XgOverallService xgOverallService;
//	@Autowired
//	private XgAreaAnalysisService xgAreaAnalysisService;
//	@Autowired
//	private XgAreaAnalysisDetailService xgAreaAnalysisDetailService;
//	@Autowired
//	private XgSpecialCateService xgSpecialCateService;
//	@Autowired
//	private XgSpecialCatePhoneDetailService xgSpecialCatePhoneDetailService;
//	@Autowired
//	private XgSpecialCateMonthDetailService xgSpecialCateMonthDetailService;
//	@Autowired
//	private XgCallLogService xgCallLogService;
//	@Autowired
//	private XgCallLogDetailService xgCallLogDetailService;
//	@Autowired
//	private XgMidScoreService xgMidScoreService;
//	@Autowired
//	private XgTripAnalysisService xgTripAnalysisService;
//	@Autowired
//	private XgTripAnalysisDetailService xgTripAnalysisDetailService;
//	@Autowired
//	private XgMonthlyConsumptionService xgMonthlyConsumptionService;
//	@Autowired
//	private XgUserSpecialCallInfoService xgUserSpecialCallInfoService;
//	@Autowired
//	private XgEmergencyAnalysisService xgEmergencyAnalysisService;
	
	@Autowired
	private XgBusiService xgBusiService;

	@ResponseBody
	@RequestMapping("/insureBack.do")
	public AppResponseResult insureBack(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String module = request.getParameter("module");
		String user_id = request.getParameter("user_id");
		String status = request.getParameter("status");
		String stauts_id = request.getParameter("stauts_id");
		Long num = null;
		if (status.equals("2")) {
			// 根据userId获取工单信息
			Example example = new Example(BwOrder.class);
			example.createCriteria().andEqualTo("borrowerId", user_id).andNotEqualTo("statusId", 1)
					.andEqualTo("productType", OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//			example.setOrderByClause(" createTime desc ");
			example.orderBy("createTime").desc();
			List<BwOrder> orders = bwOrderService.findBwOrderByExample(example);
			logger.info("社保回调成功，根据借款人id获取最近一次工单信息：" + orders.size());
			logger.info("社保回调成功，根据借款人id获取最近一次工单信息的id：" + orders.get(0).getId());
			// 抓取成功
			Response<InsureRspData> insureRes = insureService.getData(user_id);
			if (insureRes.getRequestCode().equals("200")) {
				// 将数据保存到数据库中
				List<Data_list> list = insureRes.getObj().getData().getData_list();
				Long temp = 0l;
				for (Data_list data_list : list) {
					List<Flow> flows = data_list.getFlow();
					temp++;
					for (Flow flow : flows) {
						BwInsureRecord bwInsureRecord = new BwInsureRecord();
						bwInsureRecord.setIdCard(flow.getId_card());
						bwInsureRecord.setPayDate(flow.getPay_date());
						bwInsureRecord.setStartDate(flow.getStart_date());
						bwInsureRecord.setEndDate(flow.getEnd_date());
						bwInsureRecord.setBaseRmb(flow.getBase_rmb());
						bwInsureRecord.setComRmb(flow.getCom_rmb());
						bwInsureRecord.setPerRmb(flow.getPer_rmb());
						bwInsureRecord.setBalanceRmb(flow.getBalance_rmb());
						bwInsureRecord.setMonthRmb(flow.getMonth_rmb());
						bwInsureRecord.setComName(flow.getCom_name());
						bwInsureRecord.setPayType(flow.getPay_type());
						// bwInsureRecord.setFlowType(flow.getFlow_type());
						bwInsureRecord.setInsureInfoId(temp);// 社保信息
						bwInsureRecord.setOrderId(orders.get(0).getId());
						num = bwInsureRecordService.save(bwInsureRecord);
					}

					BwInsureInfo bwInsureInfo = new BwInsureInfo();
					bwInsureInfo.setRealName(data_list.getUser().getReal_name());
					bwInsureInfo.setIdCard(data_list.getUser().getId_card());
					bwInsureInfo.setSex(data_list.getUser().getSex());
					bwInsureInfo.setBirthday(data_list.getUser().getBirthday());
					bwInsureInfo.setWorkStartDay(data_list.getUser().getWork_start_day());
					bwInsureInfo.setAccProp(data_list.getUser().getAcc_prop());
					bwInsureInfo.setAccAddr(data_list.getUser().getAcc_addr());
					bwInsureInfo.setDegree(data_list.getUser().getDegree());
					bwInsureInfo.setCellphone(data_list.getUser().getCellphone());
					bwInsureInfo.setPhone(data_list.getUser().getPhone());
					bwInsureInfo.setEmail(data_list.getUser().getEmail());
					bwInsureInfo.setInsureCode(data_list.getUser().getInsure_code());
					bwInsureInfo.setInsureCity(data_list.getUser().getInsure_city());
					bwInsureInfo.setInsureStatus(data_list.getUser().getInsure_status());
					bwInsureInfo.setInsureMonthMoney(data_list.getUser().getInsure_month_money());
					bwInsureInfo.setComName(data_list.getUser().getCom_name());
					bwInsureInfo.setComCode(data_list.getUser().getCom_code());
					bwInsureInfo.setNation(data_list.getUser().getNation());
					bwInsureInfo.setLiveAddr(data_list.getUser().getLive_addr());
					bwInsureInfo.setLivePostcode(data_list.getUser().getLive_postcode());
					bwInsureInfo.setMaritalStatus(data_list.getUser().getMarital_status());
					bwInsureInfo.setWorkerNation(data_list.getUser().getWorker_nation());
					bwInsureInfo.setStartInsureDay(data_list.getUser().getStart_insure_day());
					bwInsureInfo.setFormatAccProp(data_list.getUser().getFormat_acc_prop());
					bwInsureInfo.setFormatDegree(data_list.getUser().getFormat_degree());
					bwInsureInfo.setOrderId(orders.get(0).getId());
					num = bwInsureInfoService.save(bwInsureInfo);
				}
			} else {
				result.setCode("301");
				result.setMsg("融360社保抓取失败");
				return result;
			}
		}
		result.setCode("000");
		result.setMsg("成功");
		result.setResult(num);
		return result;
	}

	@ResponseBody
	@RequestMapping("/operateBack.do")
	public AppResponseResult operateBack(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		final String user_id = request.getParameter("user_id");
		try {
			Thread task = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						logger.info("====融360运营商开始借款人" + user_id + "抓取数据====");
						bwOperateService.getData(user_id);
						logger.info("====融360运营商借款人" + user_id + "抓取数据结束====");
					} catch (Exception e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					}
				}
			});
			taskExecutor.execute(task);
			rDto.setCode("200");
			rDto.setMsg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			rDto.setCode("101");
			rDto.setMsg("系统异常");
		}
		return rDto;
	}

	@ResponseBody
	@RequestMapping("/operateBackH5.do")
	public AppResponseResult operateBackH5(HttpServletRequest request, HttpServletResponse response) {

		AppResponseResult rDto = new AppResponseResult();
		final String user_id = request.getParameter("userId");
		final String order_id = request.getParameter("outUniqueId");
		final String authChannel = request.getParameter("authChannel");
		final String state = request.getParameter("state");
		final String search_id = request.getParameter("search_id");

		// logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~融360添加认证运营商认证回调：state：" + state);
		logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~融360添加认证运营商认证记录：state：" + state + ",order_id：" + order_id + ",search_id:"
				+ search_id + ",user_id:  " + user_id);
		try {
			if (CommUtils.isNull(state)) {
				rDto.setCode("101");
				rDto.setMsg("state不可为空");
				return rDto;
			}

			if ("login".equals(state)) {

				rDto.setCode("200");
				rDto.setMsg("成功");
				return rDto;
			} else if ("login_fail".equals(state)) {
				rDto.setCode("200");
				rDto.setMsg("成功");
				return rDto;
			} else if ("crawl".equals(state)) {
				Thread task = new Thread(new Runnable() {
					@Override
					public void run() {
						
						Response<OperateRsp> result = null;
						try {
							logger.error("----根据融360的search_id:" + search_id + ",user_id:"+user_id+",state:"+state+",抓取运营商数据 STAR-----");
							//获取运营商数据
							result = BeadWalletRongCarrierService.getData(search_id);
							//返回结果
							if(null == result) {
								logger.error("----根据融360的search_id:{},user_id:{},state:{},抓取运营商失败，结果为空-----",search_id,user_id,state);
								return;
							} else if (!"200".equals(result.getRequestCode())) {
								logger.error("----根据融360的search_id:{},user_id:{},state:{},抓取运营商失败，结果为：{}",search_id,user_id,state,JSON.toJSONString(result));
								return;
							}else {
								//返回正常状态码 继续解析
								//解析运营商
								OperateRsp operateRsp = result.getObj();
								//运营商信息非空判断
								if (null == operateRsp || null == operateRsp.getData()) {
									logger.error("----根据融360的search_id:{},user_id:{},state:{},获取到的融360运营商结果为：{}",search_id,user_id,state,JSON.toJSONString(result));
									return;
								}
								
								//解析运营商数据集合
								List<RongOperateDataList> dataLists = operateRsp.getData().getData_list();
								//运营商数据集合非空判断
								if(null == dataLists || dataLists.isEmpty()) {
									logger.error("----根据融360的search_id:{},user_id:{},state:{},获取到的融360运营商结果为：{}",search_id,user_id,state,JSON.toJSONString(result));
									return;
								}
								
								//user_id 对应bw_borrower表的Id
								Long borrowerId = Long.valueOf(user_id);
								//根据borrowerId查询借款人信息是否存在
								BwBorrower borrower = bwBorrowerService.findBwBorrowerById(borrowerId);
								//借款人信息非空判断
								if (null == borrower) {
									logger.error("----借款人信息不存在 borrowerId:{},searchId:{}-----",borrowerId,search_id);
									return;
								} else if(borrower.getState() == 0 || borrower.getFlag() == 0) {
									//借款人state和flag有问题
									logger.error("----借款人borrowerId:{},已禁用或已删除，state:{},flag:{}-----",borrowerId,borrower.getState(),borrower.getFlag());
									return;
								}
								
								// 保存至数据库
								logger.info("-----根据融360的search_id:{},user_id:{},state:{},保存运营商数据 START----",search_id,user_id,state);
								try {
									//切换数据源,运营商信息保存至速秒分库
									DataSourceHolderManager.set(DataSource.MASTER_NEW);
									//保存运营商数据
									bwOperateService.saveDataV2(borrowerId, search_id, dataLists);
									logger.info("-----根据融360的search_id:{},user_id:{},state:{},保存运营商数据完成  END----",search_id,user_id,state);
								} finally {
									DataSourceHolderManager.reset();
								}
							}
						} catch (Exception e1) {
							logger.error("----根据融360的search_id:{},user_id:{},state:{},抓取运营商信息失败 FAIL-----",search_id,user_id,state);
							e1.printStackTrace();
							return;
						}
						
					}
				});
				taskExecutor.execute(task);
				rDto.setCode("200");
				rDto.setMsg("成功");
			} else if ("report".equals(state)) {

				Thread task = new Thread(new Runnable() {
					@Override
					public void run() {
						
						String detail = null;
						OpReport opReport = null;
						try {
							logger.info("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告 -----",search_id,user_id,order_id,state);
							//调用融360接口抓取西瓜运营商报告
							detail = BeadWalletRongCarrierService.detail(user_id, order_id);
							//返回结果非空判断
							if(StringUtils.isEmpty(detail)) {
								logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告失败，返回结果为空 -----",search_id,user_id,order_id,state);
								return;
							}
							//解析JSON
							JSONObject xgJson = JSON.parseObject(detail);
							if(null == xgJson || xgJson.isEmpty()) {
								logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告解析JSON失败，detail:{}----",search_id,user_id,order_id,state,detail);
								return;
							}
							//解析报告详情
							JSONObject xgResponse = xgJson.getJSONObject("tianji_api_tianjireport_detail_response");
							if(null == xgResponse || xgResponse.isEmpty()) {
								logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告解析tianji_api_tianjireport_detail_response失败，detail:{}----",search_id,user_id,order_id,state,detail);
								return;
							}
							//将报告转为JavaBean
							opReport = JSONObject.toJavaObject(xgResponse, OpReport.class);
							if(null == opReport) {
								logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告解析tianji_api_tianjireport_detail_response转JavaBean失败，detail:{}----",search_id,user_id,order_id,state,detail);
								return;
							}
						} catch (Exception e) {
							logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告解析JSON失败，detail:{}----",search_id,user_id,order_id,state,detail);
							e.printStackTrace();
							return;
						}
						
						try {
							// 保存运营商基础数据
//							saveXgData1(opReport, Long.parseLong(user_id));
//							xgBusiService.saveXgData(opReport, Long.parseLong(user_id));
							logger.info("----根据search_id:{},user_id:{},order_id:{},state:{},保存xg西瓜运营商报告 START----",search_id,user_id,order_id,state);
							Long borrowerId = Long.parseLong(user_id);
							xgBusiService.saveXgDataToNewTab(opReport, borrowerId);
							logger.info("----根据search_id:{},user_id:{},order_id:{},state:{},保存xg西瓜运营商报告完成  END----",search_id,user_id,order_id,state);
						} catch (Exception e) {
							logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},保存xg西瓜运营商报告失败 FAIL ----",search_id,user_id,order_id,state);
							e.printStackTrace();
						}
					}
				});
				// 关闭线程
				taskExecutor.execute(task);
				rDto.setCode("200");
				rDto.setMsg("成功");
				// if ("200".equals(xgJson1.getString("error"))) {
				// XgOverall xgOverall = xgOverallService.findXgOverall(Long.parseLong(user_id));
				// if (xgOverall == null || xgOverall.getScore() == null
				// || DateUtil.intervalDay(xgOverall.getCreateDate(), new Date()) > 30) {
				// BwBorrower q = new BwBorrower();
				// q.setId(Long.valueOf(user_id));
				// BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByAttr(q);
				// BeadWalletRongCarrierService.xiGuaSubmitinfo(user_id, order_id, authChannel,
				// bwBorrower.getPhone(), bwBorrower.getName(), bwBorrower.getIdCard(), search_id);
				// }
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			rDto.setCode("101");
			rDto.setMsg("系统异常");
		}
		return rDto;
	}

	@ResponseBody
	@RequestMapping("/operateBackRedirectUrlAPI.do")
	public AppResponseResult operateBackRAPI(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		rDto.setCode("200");
		rDto.setMsg("成功");
		return rDto;
	}

	@ResponseBody
	@RequestMapping("/operateBackAPI.do")
	public AppResponseResult operateBackAPI(HttpServletRequest request, HttpServletResponse response) {

		AppResponseResult rDto = new AppResponseResult();
		final String user_id = request.getParameter("userId");
		final String order_id = request.getParameter("outUniqueId");
		final String authChannel = request.getParameter("authChannel");
		final String state = request.getParameter("state");
		final String search_id = request.getParameter("search_id");
		final String borrowerIdStr = request.getParameter("borrowerId");

		logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~融360添加认证运营商认证API记录：state：" + state + ",order_id：" + order_id
				+ ",search_id:" + search_id + ",user_id:  " + user_id);
		try {
			if (CommUtils.isNull(state)) {
				rDto.setCode("101");
				rDto.setMsg("state不可为空");
				return rDto;
			}
			if ("login".equals(state)) {
				rDto.setCode("200");
				rDto.setMsg("成功");
				return rDto;
			} else if ("login_fail".equals(state)) {
				rDto.setCode("200");
				rDto.setMsg("成功");
				return rDto;
			} else if ("report".equals(state)) {
				Thread task1 = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							// 西瓜分抓取
							Long borrowerId = null;
							try {
								borrowerId = Long.valueOf(borrowerIdStr);
							} catch (Exception e) {
								logger.error("~~~~~~~~~~~~~~~ 西瓜回调userId 参数 转换为Long异常", e);
							}
							String returnStr = BeadWalletRongCarrierService.collectuserXg(search_id);
							// logger.info("~~~~~~~~~~~~~~~~~ 获取西瓜分出参：" + returnStr);
							if (CommUtils.isNull(returnStr)) {
								rDto.setCode("101");
								rDto.setMsg("西瓜返回参数为空");
							} else {
								JSONObject jsonObject = JSONObject.parseObject(returnStr);
								String reCode = jsonObject.getString("requestCode");
								if ("200".equals(reCode)) {
									// logger.info("~~~~~~~~~~~~~~~~~ 开始存储西瓜分：" + returnStr);
//									saveXgDate2(jsonObject, borrowerId);
//									xgBusiService.saveXgData2(jsonObject, borrowerId);
									xgBusiService.saveXgData2ToNewTab(jsonObject, borrowerId);
									// {"requestCode":"200","requestMsg":"成功","obj":"{\"score\":\"0.10825061509405\",\"mid_score\":{\"danger_score\":-1,\"aprv_score\":-1,\"installment_loan_score\":-1,\"qa_score\":-1,\"op_score\":0.13907357,\"loan_score\":530,\"zhima_score\":-1,\"payday_loan_score\":-1}}"}
									// saveXgDate(jsonObject, borrowerId);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(e.getMessage());
						}
					}
				});
				taskExecutor.execute(task1);
				rDto.setCode("200");
				rDto.setMsg("成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rDto.setCode("101");
			rDto.setMsg("系统异常");
		}
		return rDto;
	}

	// private void saveXgDate(JSONObject jsonObject, Long borrowerId) {
	// XGReturn xgReturn = new XGReturn();
	// try {
	// String objStr = jsonObject.getString("obj");
	//
	// JSONObject xgJson = JSON.parseObject(objStr);
	// xgReturn = JSONObject.toJavaObject(xgJson, XGReturn.class);
	// } catch (Exception e) {
	// logger.error("~~~~~~~~~~~~~~~ 西瓜分解析异常：", e);
	// }
	// OpReport opReport = xgReturn.getOp_report();
	// HeadInfo headInfo = opReport.getHead_info();
	// InputInfo inputInfo = opReport.getInput_info();
	// BasicInfo basicInfo = opReport.getBasic_info();
	// RiskAnalysis riskAnalysis = opReport.getRisk_analysis();
	// UserPortrait userPortrait = opReport.getUser_portrait();
	//
	// try {
	// // 保存主表
	// XgOverall xgOverall = new XgOverall();
	// xgOverall.setScore(xgReturn.getScore());
	// xgOverall.setBorrowerId(borrowerId);
	// xgOverallService.deleteZ(xgOverall);
	// // 设置HeadInfo
	// if (headInfo != null) {
	// xgOverall.setSearchId(headInfo.getSearch_id());
	// xgOverall.setReportTime(headInfo.getReport_time());
	// xgOverall.setUserType(headInfo.getUser_type());
	// }
	// if (inputInfo != null) {
	// xgOverall.setUserName(inputInfo.getUser_name());
	// xgOverall.setIdCard(inputInfo.getId_card());
	// xgOverall.setPhone(inputInfo.getPhone());
	// xgOverall.setEmergencyName1(inputInfo.getEmergency_name1());
	// xgOverall.setEmergencyName2(inputInfo.getEmergency_name2());
	// xgOverall.setEmergencyPhone1(inputInfo.getEmergency_phone1());
	// xgOverall.setEmergencyPhone2(inputInfo.getEmergency_phone2());
	// xgOverall.setEmergencyRelation1(inputInfo.getEmergency_relation1());
	// xgOverall.setEmergencyRelation2(inputInfo.getEmergency_relation2());
	// }
	// if (basicInfo != null) {
	// xgOverall.setOperator(basicInfo.getOperator());
	// xgOverall.setOperatorZh(basicInfo.getOperator_zh());
	// xgOverall.setPhoneLocation(basicInfo.getPhone_location());
	// xgOverall.setIdCardCheck(basicInfo.getId_card_check());
	// xgOverall.setNameCheck(basicInfo.getName_check());
	// xgOverall.setBasicInfoIdCard(basicInfo.getId_card());
	// xgOverall.setBasicInfoRealName(basicInfo.getReal_name());
	// xgOverall.setAveMonthlyConsumption(basicInfo.getAve_monthly_consumption());
	// xgOverall.setCurrentBalance(basicInfo.getCurrent_balance());
	// xgOverall.setRegTime(basicInfo.getReg_time());
	// xgOverall.setIfCallEmergency1(basicInfo.getIf_call_emergency1());
	// xgOverall.setIfCallEmergency2(basicInfo.getIf_call_emergency2());
	// }
	// if (riskAnalysis != null) {
	// xgOverall.setBlacklistCnt(riskAnalysis.getBlacklist_cnt());
	// xgOverall.setSearchedCnt(riskAnalysis.getSearched_cnt());
	// xgOverall.setLoanRecordCnt(riskAnalysis.getLoan_record_cnt());
	// }
	// if (userPortrait != null) {
	// if (userPortrait.getContact_distribution() != null) {
	// xgOverall.setContactDistributionLocation(userPortrait.getContact_distribution().getLocation());
	// xgOverall.setContactDistributionRatio(userPortrait.getContact_distribution().getRatio());
	// }
	// xgOverall.setNightActivityRatio(userPortrait.getNight_activity_ratio());
	// xgOverall.setBothCallCnt(userPortrait.getBoth_call_cnt());
	// xgOverall.setNightMsgRatio(userPortrait.getNight_msg_ratio());
	// if (userPortrait.getActive_days() != null) {
	// UserPortraitActiveDays activeDays = userPortrait.getActive_days();
	// xgOverall.setActiveDaysStartDay(activeDays.getStart_day());
	// xgOverall.setActiveDaysEndDay(activeDays.getEnd_day());
	// xgOverall.setActiveDaysTotalDays(activeDays.getTotal_days());
	// xgOverall.setActiveDaysStopDays(activeDays.getStop_days());
	// xgOverall.setActiveDaysStop3Days(activeDays.getStop_3_days());
	// xgOverall.setActiveDaysStop3DaysDetail(activeDays.getStop_3_days_detail());
	// xgOverall.setActiveDaysStopDaysDetail(activeDays.getStop_days_detail());
	//
	// }
	// }
	// xgOverallService.save(xgOverall);
	// XgUserSpecialCallInfo ddd = new XgUserSpecialCallInfo();
	// ddd.setBorrowerId(borrowerId);
	// xgUserSpecialCallInfoService.deleteZ(ddd);
	// if (userPortrait != null) {
	// List<UserPortraitSpecialCallInfo> s = userPortrait.getSpecial_call_info();
	// if (s != null && s.size() > 0) {
	// for (UserPortraitSpecialCallInfo userPortraitSpecialCallInfo : s) {
	// XgUserSpecialCallInfo xgUserSpecialCallInfo = new XgUserSpecialCallInfo();
	// xgUserSpecialCallInfo.setBorrowerId(borrowerId);
	// xgUserSpecialCallInfo.setCallCnt(userPortraitSpecialCallInfo.getCalled_seconds());
	// xgUserSpecialCallInfo.setTalkSeconds(userPortraitSpecialCallInfo.getTalk_seconds());
	// xgUserSpecialCallInfo.setTalkCnt(userPortraitSpecialCallInfo.getTalk_cnt());
	// xgUserSpecialCallInfo.setMsgCnt(userPortraitSpecialCallInfo.getMsg_cnt());
	// xgUserSpecialCallInfo.setCalledSeconds(userPortraitSpecialCallInfo.getCalled_seconds());
	// xgUserSpecialCallInfo.setReceiveCnt(userPortraitSpecialCallInfo.getReceive_cnt());
	// // xgUserSpecialCallInfo.setDetail(userPortraitSpecialCallInfo.getDetail());
	// xgUserSpecialCallInfo.setCallCnt(userPortraitSpecialCallInfo.getCall_cnt());
	// xgUserSpecialCallInfo.setUnknown_cnt(userPortraitSpecialCallInfo.getUnknown_cnt());
	// xgUserSpecialCallInfo.setSendCnt(userPortraitSpecialCallInfo.getSend_cnt());
	// xgUserSpecialCallInfo.setCallSeconds(userPortraitSpecialCallInfo.getCall_seconds());
	// xgUserSpecialCallInfo.setPhoneList(userPortraitSpecialCallInfo.getPhone_list());
	// xgUserSpecialCallInfo.setCalledCnt(userPortraitSpecialCallInfo.getCalled_cnt());
	//
	// xgUserSpecialCallInfoService.save(xgUserSpecialCallInfo);
	// }
	// }
	// }
	//
	// // 保存 中间分
	// MidScore midScore = xgReturn.getMid_score();
	// if (midScore != null) {
	// XgMidScore xgMidScore = new XgMidScore();
	// xgMidScore.setBorrowerId(borrowerId);
	// xgMidScoreService.deleteZ(xgMidScore);
	//
	// xgMidScore.setAprvScore(midScore.getAprv_score());
	// xgMidScore.setDangerScore(midScore.getDanger_score());
	// xgMidScore.setInstallmentLoanScore(midScore.getInstallment_loan_score());
	// xgMidScore.setLoanScore(midScore.getLoan_score());
	// xgMidScore.setOpScore(midScore.getOp_score());
	// xgMidScore.setPaydayLoanScore(midScore.getPayday_loan_score());
	// xgMidScore.setQaScore(midScore.getQa_score());
	// xgMidScore.setZhimaScore(midScore.getZhima_score());
	// xgMidScoreService.save(xgMidScore);
	// }
	//
	// // 保存EmergencyAnalysis
	// List<EmergencyAnalysis> emergencyAnalysisList = opReport.getEmergency_analysis();
	// XgEmergencyAnalysis dd3 = new XgEmergencyAnalysis();
	// dd3.setBorrowerId(borrowerId);
	// xgEmergencyAnalysisService.deleteZ(dd3);
	// if (emergencyAnalysisList != null && emergencyAnalysisList.size() > 0) {
	// for (EmergencyAnalysis emergencyAnalysis : emergencyAnalysisList) {
	// XgEmergencyAnalysis xgEmergencyAnalysis = new XgEmergencyAnalysis();
	// xgEmergencyAnalysis.setBorrowerId(borrowerId);
	// xgEmergencyAnalysis.setPhone(emergencyAnalysis.getPhone());
	// xgEmergencyAnalysis.setName(emergencyAnalysis.getName());
	// xgEmergencyAnalysis.setFirstContactDate(emergencyAnalysis.getFirst_contact_date());
	// xgEmergencyAnalysis.setLastContactDate(emergencyAnalysis.getLast_contact_date());
	// xgEmergencyAnalysis.setTalkSeconds(emergencyAnalysis.getTalk_seconds());
	// xgEmergencyAnalysis.setTalkCnt(emergencyAnalysis.getTalk_cnt());
	// xgEmergencyAnalysis.setCallCnt(emergencyAnalysis.getCall_cnt());
	// xgEmergencyAnalysis.setCallSeconds(emergencyAnalysis.getCall_seconds());
	// xgEmergencyAnalysis.setCalledSeconds(emergencyAnalysis.getCalled_seconds());
	// xgEmergencyAnalysis.setCalledCnt(emergencyAnalysis.getCalled_cnt());
	// xgEmergencyAnalysis.setMsgCnt(emergencyAnalysis.getMsg_cnt());
	// xgEmergencyAnalysis.setSendCnt(emergencyAnalysis.getSend_cnt());
	// xgEmergencyAnalysis.setReceiveCnt(emergencyAnalysis.getReceive_cnt());
	// xgEmergencyAnalysis.setUnknownCnt(emergencyAnalysis.getUnknown_cnt());
	// xgEmergencyAnalysisService.save(xgEmergencyAnalysis);
	// }
	// }
	//
	// // 保存SpecialCate
	// List<SpecialCate> specialCateList = opReport.getSpecial_cate();
	// XgSpecialCate d = new XgSpecialCate();
	// d.setBorrowerId(borrowerId);
	// xgSpecialCateService.deleteZ(d);
	// XgSpecialCatePhoneDetail d1 = new XgSpecialCatePhoneDetail();
	// d1.setBorrowerId(borrowerId);
	// xgSpecialCatePhoneDetailService.deleteZ(d1);
	// XgSpecialCateMonthDetail d2 = new XgSpecialCateMonthDetail();
	// d2.setBorrowerId(borrowerId);
	// xgSpecialCateMonthDetailService.deleteZ(d2);
	// if (specialCateList != null && specialCateList.size() > 0) {
	// for (SpecialCate specialCate : specialCateList) {
	// XgSpecialCate xgSpecialCate = new XgSpecialCate();
	// xgSpecialCate.setBorrowerId(borrowerId);
	// xgSpecialCate.setCallCnt(specialCate.getCall_cnt());
	// xgSpecialCate.setCalledCnt(specialCate.getCalled_cnt());
	// xgSpecialCate.setCalledSeconds(specialCate.getCalled_seconds());
	// xgSpecialCate.setCallSeconds(specialCate.getCall_seconds());
	// xgSpecialCate.setTalkCnt(specialCate.getTalk_cnt());
	// xgSpecialCate.setTalkSeconds(specialCate.getTalk_seconds());
	// xgSpecialCate.setMsgCnt(specialCate.getMsg_cnt());
	// xgSpecialCate.setReceiveCnt(specialCate.getReceive_cnt());
	// xgSpecialCate.setUnknownCnt(specialCate.getUnknown_cnt());
	// xgSpecialCate.setSendCnt(specialCate.getSend_cnt());
	// xgSpecialCate.setCate(specialCate.getCate());
	//
	// xgSpecialCateService.save(xgSpecialCate);
	//
	// // 保存SpecialCate中的 PhoneDetail
	// List<SpecialCatePhoneDetail> specialCatePhoneDetailList = specialCate.getPhone_detail();
	// if (specialCatePhoneDetailList != null && specialCatePhoneDetailList.size() > 0) {
	// for (SpecialCatePhoneDetail specialCatePhoneDetail : specialCatePhoneDetailList) {
	// XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail = new XgSpecialCatePhoneDetail();
	// xgSpecialCatePhoneDetail.setBorrowerId(borrowerId);
	// xgSpecialCatePhoneDetail.setSpecialCateId(xgSpecialCate.getId());
	// xgSpecialCatePhoneDetail.setCalledCnt(specialCatePhoneDetail.getCalled_cnt());
	// xgSpecialCatePhoneDetail.setTalkSeconds(specialCatePhoneDetail.getTalk_seconds());
	// xgSpecialCatePhoneDetail.setTalkCnt(specialCatePhoneDetail.getTalk_cnt());
	// xgSpecialCatePhoneDetail.setMsgCnt(specialCatePhoneDetail.getMsg_cnt());
	// xgSpecialCatePhoneDetail.setCalledSeconds(specialCatePhoneDetail.getCalled_seconds());
	// xgSpecialCatePhoneDetail.setReceiveCnt(specialCatePhoneDetail.getReceive_cnt());
	// xgSpecialCatePhoneDetail.setCallCnt(specialCatePhoneDetail.getCall_cnt());
	// xgSpecialCatePhoneDetail.setUnknownCnt(specialCatePhoneDetail.getUnknown_cnt());
	// xgSpecialCatePhoneDetail.setCalledSeconds(specialCatePhoneDetail.getCalled_seconds());
	// xgSpecialCatePhoneDetail.setPhone(specialCatePhoneDetail.getPhone());
	// xgSpecialCatePhoneDetail.setUnknownCnt(specialCatePhoneDetail.getUnknown_cnt());
	// xgSpecialCatePhoneDetail.setPhoneInfo(specialCatePhoneDetail.getPhone_info());
	// xgSpecialCatePhoneDetail.setCallSeconds(specialCatePhoneDetail.getCall_seconds());
	// xgSpecialCatePhoneDetail.setSendCnt(specialCatePhoneDetail.getSend_cnt());
	// xgSpecialCatePhoneDetailService.save(xgSpecialCatePhoneDetail);
	// }
	// }
	// List<SpecialCateMonthDetail> specialCateMonthDetails = specialCate.getMonth_detail();
	// if (specialCateMonthDetails != null && specialCateMonthDetails.size() > 0) {
	// for (SpecialCateMonthDetail specialCateMonthDetail : specialCateMonthDetails) {
	// XgSpecialCateMonthDetail xgSpecialCateMonthDetail = new XgSpecialCateMonthDetail();
	// xgSpecialCateMonthDetail.setBorrowerId(borrowerId);
	// xgSpecialCateMonthDetail.setSpecialCateId(xgSpecialCate.getId());
	//
	// xgSpecialCateMonthDetail.setCalledCnt(specialCateMonthDetail.getCalled_cnt());
	// xgSpecialCateMonthDetail.setTalkSeconds(specialCateMonthDetail.getTalk_seconds());
	// xgSpecialCateMonthDetail.setTalkCnt(specialCateMonthDetail.getTalk_cnt());
	// xgSpecialCateMonthDetail.setMsgCnt(specialCateMonthDetail.getMsg_cnt());
	// xgSpecialCateMonthDetail.setCalledSeconds(specialCateMonthDetail.getCalled_seconds());
	// xgSpecialCateMonthDetail.setReceiveCnt(specialCateMonthDetail.getReceive_cnt());
	// xgSpecialCateMonthDetail.setCallCnt(specialCateMonthDetail.getCall_cnt());
	// xgSpecialCateMonthDetail.setUnknownCnt(specialCateMonthDetail.getUnknown_cnt());
	// xgSpecialCateMonthDetail.setCalledSeconds(specialCateMonthDetail.getCalled_seconds());
	// xgSpecialCateMonthDetail.setMonth(specialCateMonthDetail.getMonth());
	// xgSpecialCateMonthDetail.setCallSeconds(specialCateMonthDetail.getCall_seconds());
	// xgSpecialCateMonthDetail.setSendCnt(specialCateMonthDetail.getSend_cnt());
	// xgSpecialCateMonthDetailService.save(xgSpecialCateMonthDetail);
	// }
	// }
	// }
	// }
	//
	// // 保存 monthly_consumption
	// List<MonthlyConsumption> monthlyConsumption = opReport.getMonthly_consumption();
	// XgMonthlyConsumption d3 = new XgMonthlyConsumption();
	// d3.setBorrowerId(borrowerId);
	// xgMonthlyConsumptionService.deleteZ(d3);
	// if (monthlyConsumption != null && monthlyConsumption.size() > 0) {
	// for (MonthlyConsumption consumption : monthlyConsumption) {
	// XgMonthlyConsumption xgMonthlyConsumption = new XgMonthlyConsumption();
	// xgMonthlyConsumption.setBorrowerId(borrowerId);
	// xgMonthlyConsumption.setCalledCnt(consumption.getCalled_cnt());
	// xgMonthlyConsumption.setTalkSeconds(consumption.getTalk_seconds());
	// xgMonthlyConsumption.setTalkCnt(consumption.getTalk_cnt());
	// xgMonthlyConsumption.setMsgCnt(consumption.getMsg_cnt());
	// xgMonthlyConsumption.setCalledSeconds(consumption.getCalled_seconds());
	// xgMonthlyConsumption.setReceiveCnt(consumption.getReceive_cnt());
	// xgMonthlyConsumption.setMonth(consumption.getMonth());
	// xgMonthlyConsumption.setCallCnt(consumption.getCall_cnt());
	// xgMonthlyConsumption.setUnknownCnt(consumption.getUnknown_cnt());
	// xgMonthlyConsumption.setCallConsumption(consumption.getCall_consumption());
	// xgMonthlyConsumption.setSendCnt(consumption.getSend_cnt());
	// xgMonthlyConsumption.setCallSeconds(consumption.getCall_seconds());
	// xgMonthlyConsumptionService.save(xgMonthlyConsumption);
	// }
	// }
	//
	// // 保存 call log
	// List<CallLog> callLogList = opReport.getCall_log();
	// XgCallLog d4 = new XgCallLog();
	// d4.setBorrowerId(borrowerId);
	// xgCallLogService.deleteZ(d4);
	// XgCallLogDetail d12 = new XgCallLogDetail();
	// d12.setBorrowerId(borrowerId);
	// xgCallLogDetailService.deleteZ(d12);
	// if (callLogList != null && callLogList.size() > 0) {
	// for (CallLog callLog : callLogList) {
	// XgCallLog xgCallLog = new XgCallLog();
	// xgCallLog.setBorrowerId(borrowerId);
	// xgCallLog.setContactNonn(callLog.getContact_noon());
	// xgCallLog.setTalkSeconds(callLog.getTalk_seconds());
	// xgCallLog.setTalkCnt(callLog.getTalk_cnt());
	// xgCallLog.setContact3m(callLog.getContact_3m());
	// xgCallLog.setMsgCnt(callLog.getMsg_cnt());
	// xgCallLog.setContact1m(callLog.getContact_1m());
	// xgCallLog.setUnknownCnt(callLog.getUnknown_cnt());
	// xgCallLog.setContactEveing(callLog.getContact_eveing());
	// xgCallLog.setContact1w(callLog.getContact_1w());
	// xgCallLog.setPhoneInfo(callLog.getPhone_info());
	// xgCallLog.setCallSeconds(callLog.getCall_seconds());
	// xgCallLog.setCallCnt(callLog.getCall_cnt());
	// xgCallLog.setCalledCnt(callLog.getCalled_cnt());
	// xgCallLog.setContactWeekday(callLog.getContact_weekday());
	// xgCallLog.setReceiveCnt(callLog.getReceive_cnt());
	// xgCallLog.setPhone(callLog.getPhone());
	// xgCallLog.setCallSeconds(callLog.getCall_seconds());
	// xgCallLog.setFirstContactDate(callLog.getFirst_contact_date());
	// xgCallLog.setContactAfternoon(callLog.getContact_afternoon());
	// xgCallLog.setContactEarlyMorning(callLog.getContact_early_morning());
	// xgCallLog.setLastContactDate(callLog.getLast_contact_date());
	// xgCallLog.setContactNight(callLog.getContact_night());
	// xgCallLog.setPhoneLabel(callLog.getPhone_label());
	// xgCallLog.setSendCnt(callLog.getSend_cnt());
	// xgCallLog.setPhoneLocation(callLog.getPhone_location());
	// xgCallLog.setContactMorning(callLog.getContact_morning());
	// xgCallLog.setContactWeekend(callLog.getContact_weekend());
	// xgCallLog.setCalledSeconds(callLog.getCalled_seconds());
	// xgCallLogService.save(xgCallLog);
	//
	// List<Detail> s = callLog.getDetail();
	// for (Detail detail : s) {
	// XgCallLogDetail xgCallLogDetail = new XgCallLogDetail();
	// xgCallLogDetail.setBorrowerId(borrowerId);
	// xgCallLogDetail.setCallLogId(xgCallLog.getId());
	// xgCallLogDetail.setCalledCnt(detail.getCalled_cnt());
	// xgCallLogDetail.setTalkSeconds(detail.getTalk_seconds());
	// xgCallLogDetail.setTalkCnt(detail.getTalk_cnt());
	// xgCallLogDetail.setMsgCnt(detail.getMsg_cnt());
	// xgCallLogDetail.setCalledSeconds(detail.getCalled_seconds());
	// xgCallLogDetail.setReceiveCnt(detail.getReceive_cnt());
	// xgCallLogDetail.setMonth(detail.getMonth());
	// xgCallLogDetail.setCallCnt(detail.getCall_cnt());
	// xgCallLogDetail.setUnknownCnt(detail.getUnknown_cnt());
	// xgCallLogDetail.setSendCnt(detail.getSend_cnt());
	// xgCallLogDetail.setCallSeconds(detail.getCall_seconds());
	// xgCallLogDetailService.save(xgCallLogDetail);
	// }
	// }
	// }
	//
	// // 保存 trip_analysis
	// List<TripAnalysis> tripAnalysisList = opReport.getTrip_analysis();
	// XgTripAnalysis d6 = new XgTripAnalysis();
	// d6.setBorrowerId(borrowerId);
	// xgTripAnalysisService.deleteZ(d6);
	// XgTripAnalysisDetail d15 = new XgTripAnalysisDetail();
	// d15.setBorrowerId(borrowerId);
	// xgTripAnalysisDetailService.deleteZ(d15);
	// if (tripAnalysisList != null && tripAnalysisList.size() > 0) {
	// for (TripAnalysis tripAnalysis : tripAnalysisList) {
	// XgTripAnalysis xgtripAnalysis = new XgTripAnalysis();
	// xgtripAnalysis.setBorrowerId(borrowerId);
	//
	// xgtripAnalysis.setCalledCnt(tripAnalysis.getCalled_cnt());
	// xgtripAnalysis.setTalkCnt(tripAnalysis.getTalk_cnt());
	// xgtripAnalysis.setTalkSeconds(tripAnalysis.getTalk_seconds());
	// xgtripAnalysis.setMsgCnt(tripAnalysis.getMsg_cnt());
	// xgtripAnalysis.setCallSeconds(tripAnalysis.getCall_seconds());
	// xgtripAnalysis.setReceiveCnt(tripAnalysis.getReceive_cnt());
	// xgtripAnalysis.setDateDistribution(tripAnalysis.getDate_distribution());
	// xgtripAnalysis.setCallCnt(tripAnalysis.getCall_cnt());
	// xgtripAnalysis.setUnknownCnt(tripAnalysis.getUnknown_cnt());
	// xgtripAnalysis.setLocation(tripAnalysis.getLocation());
	// xgtripAnalysis.setSendCnt(tripAnalysis.getSend_cnt());
	// xgtripAnalysis.setCalledSeconds(tripAnalysis.getCalled_seconds());
	// xgTripAnalysisService.save(xgtripAnalysis);
	//
	// List<Detail> s = tripAnalysis.getDetail();
	// for (Detail detail : s) {
	// XgTripAnalysisDetail xgTripAnalysisDetail = new XgTripAnalysisDetail();
	// xgTripAnalysisDetail.setBorrowerId(borrowerId);
	// xgTripAnalysisDetail.setTripAnalysisId(xgtripAnalysis.getId());
	// xgTripAnalysisDetail.setCalledCnt(detail.getCalled_cnt());
	// xgTripAnalysisDetail.setTalkSeconds(detail.getTalk_seconds());
	// xgTripAnalysisDetail.setTalkCnt(detail.getTalk_cnt());
	// xgTripAnalysisDetail.setMsgCnt(detail.getMsg_cnt());
	// xgTripAnalysisDetail.setCalledSeconds(detail.getCalled_seconds());
	// xgTripAnalysisDetail.setReceiveCnt(detail.getReceive_cnt());
	// xgTripAnalysisDetail.setMonth(detail.getMonth());
	// xgTripAnalysisDetail.setCallCnt(detail.getCall_cnt());
	// xgTripAnalysisDetail.setUnknownCnt(detail.getUnknown_cnt());
	// xgTripAnalysisDetail.setSendCnt(detail.getSend_cnt());
	// xgTripAnalysisDetail.setCallSeconds(detail.getCall_seconds());
	// xgTripAnalysisDetailService.save(xgTripAnalysisDetail);
	// }
	// }
	// }
	//
	// // 保存 area_analysis
	// List<AreaAnalysis> areaAnalysisList = opReport.getArea_analysis();
	// XgAreaAnalysisDetail d112 = new XgAreaAnalysisDetail();
	// d112.setBorrowerId(borrowerId);
	// xgAreaAnalysisDetailService.deleteZ(d112);
	// XgAreaAnalysis d8 = new XgAreaAnalysis();
	// d8.setBorrowerId(borrowerId);
	// xgAreaAnalysisService.deleteZ(d8);
	//
	// if (areaAnalysisList != null && areaAnalysisList.size() > 0) {
	// for (AreaAnalysis areaAnalysis : areaAnalysisList) {
	// XgAreaAnalysis xgAreaAnalysis = new XgAreaAnalysis();
	// xgAreaAnalysis.setBorrowerId(borrowerId);
	//
	// xgAreaAnalysis.setCalledCnt(areaAnalysis.getCalled_cnt());
	// xgAreaAnalysis.setTalkSeconds(areaAnalysis.getTalk_seconds());
	// xgAreaAnalysis.setTalkCnt(areaAnalysis.getTalk_cnt());
	// xgAreaAnalysis.setArea(areaAnalysis.getArea());
	// xgAreaAnalysis.setReceiveCnt(areaAnalysis.getReceive_cnt());
	// xgAreaAnalysis.setCallSeconds(areaAnalysis.getCall_seconds());
	// xgAreaAnalysis.setMsgCnt(areaAnalysis.getMsg_cnt());
	// xgAreaAnalysis.setCallCnt(areaAnalysis.getCall_cnt());
	// xgAreaAnalysis.setUnknownCnt(areaAnalysis.getUnknown_cnt());
	// xgAreaAnalysis.setContact_phoneCnt(areaAnalysis.getContact_phone_cnt());
	// xgAreaAnalysis.setSendCnt(areaAnalysis.getSend_cnt());
	// xgAreaAnalysis.setCalledSeconds(areaAnalysis.getCalled_seconds());
	// xgAreaAnalysisService.save(xgAreaAnalysis);
	//
	// List<AreaAnalysisDetail> s = areaAnalysis.getDetail();
	// for (AreaAnalysisDetail detail : s) {
	// XgAreaAnalysisDetail xgTripAnalysisDetail = new XgAreaAnalysisDetail();
	// xgTripAnalysisDetail.setBorrowerId(borrowerId);
	// xgTripAnalysisDetail.setAreaAnalysisId(xgAreaAnalysis.getId());
	//
	// xgTripAnalysisDetail.setCalledCnt(detail.getCalled_cnt());
	// xgTripAnalysisDetail.setTalkSeconds(detail.getTalk_seconds());
	// xgTripAnalysisDetail.setTalkCnt(detail.getTalk_cnt());
	// xgTripAnalysisDetail.setMsgCnt(detail.getMsg_cnt());
	// xgTripAnalysisDetail.setCalledSeconds(detail.getCalled_seconds());
	// xgTripAnalysisDetail.setReceiveCnt(detail.getReceive_cnt());
	// xgTripAnalysisDetail.setMonth(detail.getMonth());
	// xgTripAnalysisDetail.setCallCnt(detail.getCall_cnt());
	// xgTripAnalysisDetail.setUnknownCnt(detail.getUnknown_cnt());
	// xgTripAnalysisDetail.setContactPhoneCnt(detail.getContact_phone_cnt());
	// xgTripAnalysisDetail.setSendCnt(detail.getSend_cnt());
	// xgTripAnalysisDetail.setCallSeconds(detail.getCall_seconds());
	// xgAreaAnalysisDetailService.save(xgTripAnalysisDetail);
	// }
	// }
	// }
	// } catch (Exception e) {
	// logger.error("~~~~~~~~~~~~~~~ 西瓜分保存异常：", e);
	// }
	// }

//	private void saveXgDate1(OpReport opReport, Long borrowerId) throws Exception {
//		HeadInfo headInfo = opReport.getHead_info();
//		InputInfo inputInfo = opReport.getInput_info();
//		BasicInfo basicInfo = opReport.getBasic_info();
//		RiskAnalysis riskAnalysis = opReport.getRisk_analysis();
//		UserPortrait userPortrait = opReport.getUser_portrait();
//
//		try {
//			// 保存主表
//			XgOverall xgOverall = new XgOverall();
//			xgOverall.setBorrowerId(borrowerId);
//			xgOverallService.deleteZ(xgOverall);
//			xgOverall.setCreateDate(new Date());
//
//			// 设置HeadInfo
//			if (headInfo != null) {
//				xgOverall.setSearchId(headInfo.getSearch_id());
//				xgOverall.setReportTime(headInfo.getReport_time());
//				xgOverall.setUserType(headInfo.getUser_type());
//			}
//			if (inputInfo != null) {
//				xgOverall.setUserName(inputInfo.getUser_name());
//				xgOverall.setIdCard(inputInfo.getId_card());
//				xgOverall.setPhone(inputInfo.getPhone());
//				xgOverall.setEmergencyName1(inputInfo.getEmergency_name1());
//				xgOverall.setEmergencyName2(inputInfo.getEmergency_name2());
//				xgOverall.setEmergencyPhone1(inputInfo.getEmergency_phone1());
//				xgOverall.setEmergencyPhone2(inputInfo.getEmergency_phone2());
//				xgOverall.setEmergencyRelation1(inputInfo.getEmergency_relation1());
//				xgOverall.setEmergencyRelation2(inputInfo.getEmergency_relation2());
//			}
//			if (basicInfo != null) {
//				xgOverall.setOperator(basicInfo.getOperator());
//				xgOverall.setOperatorZh(basicInfo.getOperator_zh());
//				xgOverall.setPhoneLocation(basicInfo.getPhone_location());
//				xgOverall.setIdCardCheck(basicInfo.getId_card_check());
//				xgOverall.setNameCheck(basicInfo.getName_check());
//				xgOverall.setBasicInfoIdCard(basicInfo.getId_card());
//				xgOverall.setBasicInfoRealName(basicInfo.getReal_name());
//				xgOverall.setAveMonthlyConsumption(basicInfo.getAve_monthly_consumption());
//				xgOverall.setCurrentBalance(basicInfo.getCurrent_balance());
//				xgOverall.setRegTime(basicInfo.getReg_time());
//				xgOverall.setIfCallEmergency1(basicInfo.getIf_call_emergency1());
//				xgOverall.setIfCallEmergency2(basicInfo.getIf_call_emergency2());
//			}
//			if (riskAnalysis != null) {
//				xgOverall.setBlacklistCnt(riskAnalysis.getBlacklist_cnt());
//				xgOverall.setSearchedCnt(riskAnalysis.getSearched_cnt());
//				xgOverall.setLoanRecordCnt(riskAnalysis.getLoan_record_cnt());
//			}
//			if (userPortrait != null) {
//				if (userPortrait.getContact_distribution() != null) {
//					xgOverall.setContactDistributionLocation(userPortrait.getContact_distribution().getLocation());
//					xgOverall.setContactDistributionRatio(userPortrait.getContact_distribution().getRatio());
//				}
//				xgOverall.setNightActivityRatio(userPortrait.getNight_activity_ratio());
//				xgOverall.setBothCallCnt(userPortrait.getBoth_call_cnt());
//				xgOverall.setNightMsgRatio(userPortrait.getNight_msg_ratio());
//				if (userPortrait.getActive_days() != null) {
//					UserPortraitActiveDays activeDays = userPortrait.getActive_days();
//					xgOverall.setActiveDaysStartDay(activeDays.getStart_day());
//					xgOverall.setActiveDaysEndDay(activeDays.getEnd_day());
//					xgOverall.setActiveDaysTotalDays(activeDays.getTotal_days());
//					xgOverall.setActiveDaysStopDays(activeDays.getStop_days());
//					xgOverall.setActiveDaysStop3Days(activeDays.getStop_3_days());
//					xgOverall.setActiveDaysStop3DaysDetail(activeDays.getStop_3_days_detail());
//					xgOverall.setActiveDaysStopDaysDetail(activeDays.getStop_days_detail());
//
//				}
//			}
//			xgOverallService.save(xgOverall);
//			XgUserSpecialCallInfo ddd = new XgUserSpecialCallInfo();
//			ddd.setBorrowerId(borrowerId);
//			xgUserSpecialCallInfoService.deleteZ(ddd);
//			if (userPortrait != null) {
//				List<UserPortraitSpecialCallInfo> s = userPortrait.getSpecial_call_info();
//				if (s != null && s.size() > 0) {
//					for (UserPortraitSpecialCallInfo userPortraitSpecialCallInfo : s) {
//						XgUserSpecialCallInfo xgUserSpecialCallInfo = new XgUserSpecialCallInfo();
//						xgUserSpecialCallInfo.setBorrowerId(borrowerId);
//						xgUserSpecialCallInfo.setCallCnt(userPortraitSpecialCallInfo.getCalled_seconds());
//						xgUserSpecialCallInfo.setTalkSeconds(userPortraitSpecialCallInfo.getTalk_seconds());
//						xgUserSpecialCallInfo.setTalkCnt(userPortraitSpecialCallInfo.getTalk_cnt());
//						xgUserSpecialCallInfo.setMsgCnt(userPortraitSpecialCallInfo.getMsg_cnt());
//						xgUserSpecialCallInfo.setCalledSeconds(userPortraitSpecialCallInfo.getCalled_seconds());
//						xgUserSpecialCallInfo.setReceiveCnt(userPortraitSpecialCallInfo.getReceive_cnt());
//						xgUserSpecialCallInfo.setDetail(userPortraitSpecialCallInfo.getDetail());
//						xgUserSpecialCallInfo.setCallCnt(userPortraitSpecialCallInfo.getCall_cnt());
//						xgUserSpecialCallInfo.setUnknown_cnt(userPortraitSpecialCallInfo.getUnknown_cnt());
//						xgUserSpecialCallInfo.setSendCnt(userPortraitSpecialCallInfo.getSend_cnt());
//						xgUserSpecialCallInfo.setCallSeconds(userPortraitSpecialCallInfo.getCall_seconds());
//						// xgUserSpecialCallInfo.setPhoneList(userPortraitSpecialCallInfo.getPhone_list());
//						xgUserSpecialCallInfo.setCalledCnt(userPortraitSpecialCallInfo.getCalled_cnt());
//
//						xgUserSpecialCallInfoService.save(xgUserSpecialCallInfo);
//					}
//				}
//			}
//
//			// 保存EmergencyAnalysis
//			List<EmergencyAnalysis> emergencyAnalysisList = opReport.getEmergency_analysis();
//			XgEmergencyAnalysis dd3 = new XgEmergencyAnalysis();
//			dd3.setBorrowerId(borrowerId);
//			xgEmergencyAnalysisService.deleteZ(dd3);
//			if (emergencyAnalysisList != null && emergencyAnalysisList.size() > 0) {
//				for (EmergencyAnalysis emergencyAnalysis : emergencyAnalysisList) {
//					XgEmergencyAnalysis xgEmergencyAnalysis = new XgEmergencyAnalysis();
//					xgEmergencyAnalysis.setBorrowerId(borrowerId);
//					xgEmergencyAnalysis.setPhone(emergencyAnalysis.getPhone());
//					xgEmergencyAnalysis.setName(emergencyAnalysis.getName());
//					xgEmergencyAnalysis.setFirstContactDate(emergencyAnalysis.getFirst_contact_date());
//					xgEmergencyAnalysis.setLastContactDate(emergencyAnalysis.getLast_contact_date());
//					xgEmergencyAnalysis.setTalkSeconds(emergencyAnalysis.getTalk_seconds());
//					xgEmergencyAnalysis.setTalkCnt(emergencyAnalysis.getTalk_cnt());
//					xgEmergencyAnalysis.setCallCnt(emergencyAnalysis.getCall_cnt());
//					xgEmergencyAnalysis.setCallSeconds(emergencyAnalysis.getCall_seconds());
//					xgEmergencyAnalysis.setCalledSeconds(emergencyAnalysis.getCalled_seconds());
//					xgEmergencyAnalysis.setCalledCnt(emergencyAnalysis.getCalled_cnt());
//					xgEmergencyAnalysis.setMsgCnt(emergencyAnalysis.getMsg_cnt());
//					xgEmergencyAnalysis.setSendCnt(emergencyAnalysis.getSend_cnt());
//					xgEmergencyAnalysis.setReceiveCnt(emergencyAnalysis.getReceive_cnt());
//					xgEmergencyAnalysis.setUnknownCnt(emergencyAnalysis.getUnknown_cnt());
//					xgEmergencyAnalysisService.save(xgEmergencyAnalysis);
//				}
//			}
//
//			// 保存SpecialCate
//			List<SpecialCate> specialCateList = opReport.getSpecial_cate();
//			XgSpecialCate d = new XgSpecialCate();
//			d.setBorrowerId(borrowerId);
//			xgSpecialCateService.deleteZ(d);
//			XgSpecialCatePhoneDetail d1 = new XgSpecialCatePhoneDetail();
//			d1.setBorrowerId(borrowerId);
//			xgSpecialCatePhoneDetailService.deleteZ(d1);
//			XgSpecialCateMonthDetail d2 = new XgSpecialCateMonthDetail();
//			d2.setBorrowerId(borrowerId);
//			xgSpecialCateMonthDetailService.deleteZ(d2);
//			if (specialCateList != null && specialCateList.size() > 0) {
//				for (SpecialCate specialCate : specialCateList) {
//					XgSpecialCate xgSpecialCate = new XgSpecialCate();
//					xgSpecialCate.setBorrowerId(borrowerId);
//					xgSpecialCate.setCallCnt(specialCate.getCall_cnt());
//					xgSpecialCate.setCalledCnt(specialCate.getCalled_cnt());
//					xgSpecialCate.setCalledSeconds(specialCate.getCalled_seconds());
//					xgSpecialCate.setCallSeconds(specialCate.getCall_seconds());
//					xgSpecialCate.setTalkCnt(specialCate.getTalk_cnt());
//					xgSpecialCate.setTalkSeconds(specialCate.getTalk_seconds());
//					xgSpecialCate.setMsgCnt(specialCate.getMsg_cnt());
//					xgSpecialCate.setReceiveCnt(specialCate.getReceive_cnt());
//					xgSpecialCate.setUnknownCnt(specialCate.getUnknown_cnt());
//					xgSpecialCate.setSendCnt(specialCate.getSend_cnt());
//					xgSpecialCate.setCate(specialCate.getCate());
//
//					xgSpecialCateService.save(xgSpecialCate);
//
//					// 保存SpecialCate中的 PhoneDetail
//					List<SpecialCatePhoneDetail> specialCatePhoneDetailList = specialCate.getPhone_detail();
//					if (specialCatePhoneDetailList != null && specialCatePhoneDetailList.size() > 0) {
//						for (SpecialCatePhoneDetail specialCatePhoneDetail : specialCatePhoneDetailList) {
//							XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail = new XgSpecialCatePhoneDetail();
//							xgSpecialCatePhoneDetail.setBorrowerId(borrowerId);
//							xgSpecialCatePhoneDetail.setSpecialCateId(xgSpecialCate.getId());
//							xgSpecialCatePhoneDetail.setCalledCnt(specialCatePhoneDetail.getCalled_cnt());
//							xgSpecialCatePhoneDetail.setTalkSeconds(specialCatePhoneDetail.getTalk_seconds());
//							xgSpecialCatePhoneDetail.setTalkCnt(specialCatePhoneDetail.getTalk_cnt());
//							xgSpecialCatePhoneDetail.setMsgCnt(specialCatePhoneDetail.getMsg_cnt());
//							xgSpecialCatePhoneDetail.setCalledSeconds(specialCatePhoneDetail.getCalled_seconds());
//							xgSpecialCatePhoneDetail.setReceiveCnt(specialCatePhoneDetail.getReceive_cnt());
//							xgSpecialCatePhoneDetail.setCallCnt(specialCatePhoneDetail.getCall_cnt());
//							xgSpecialCatePhoneDetail.setUnknownCnt(specialCatePhoneDetail.getUnknown_cnt());
//							xgSpecialCatePhoneDetail.setCalledSeconds(specialCatePhoneDetail.getCalled_seconds());
//							xgSpecialCatePhoneDetail.setPhone(specialCatePhoneDetail.getPhone());
//							xgSpecialCatePhoneDetail.setUnknownCnt(specialCatePhoneDetail.getUnknown_cnt());
//							xgSpecialCatePhoneDetail.setPhoneInfo(specialCatePhoneDetail.getPhone_info());
//							xgSpecialCatePhoneDetail.setCallSeconds(specialCatePhoneDetail.getCall_seconds());
//							xgSpecialCatePhoneDetail.setSendCnt(specialCatePhoneDetail.getSend_cnt());
//							xgSpecialCatePhoneDetailService.save(xgSpecialCatePhoneDetail);
//						}
//					}
//					List<SpecialCateMonthDetail> specialCateMonthDetails = specialCate.getMonth_detail();
//					if (specialCateMonthDetails != null && specialCateMonthDetails.size() > 0) {
//						for (SpecialCateMonthDetail specialCateMonthDetail : specialCateMonthDetails) {
//							XgSpecialCateMonthDetail xgSpecialCateMonthDetail = new XgSpecialCateMonthDetail();
//							xgSpecialCateMonthDetail.setBorrowerId(borrowerId);
//							xgSpecialCateMonthDetail.setSpecialCateId(xgSpecialCate.getId());
//
//							xgSpecialCateMonthDetail.setCalledCnt(specialCateMonthDetail.getCalled_cnt());
//							xgSpecialCateMonthDetail.setTalkSeconds(specialCateMonthDetail.getTalk_seconds());
//							xgSpecialCateMonthDetail.setTalkCnt(specialCateMonthDetail.getTalk_cnt());
//							xgSpecialCateMonthDetail.setMsgCnt(specialCateMonthDetail.getMsg_cnt());
//							xgSpecialCateMonthDetail.setCalledSeconds(specialCateMonthDetail.getCalled_seconds());
//							xgSpecialCateMonthDetail.setReceiveCnt(specialCateMonthDetail.getReceive_cnt());
//							xgSpecialCateMonthDetail.setCallCnt(specialCateMonthDetail.getCall_cnt());
//							xgSpecialCateMonthDetail.setUnknownCnt(specialCateMonthDetail.getUnknown_cnt());
//							xgSpecialCateMonthDetail.setCalledSeconds(specialCateMonthDetail.getCalled_seconds());
//							xgSpecialCateMonthDetail.setMonth(specialCateMonthDetail.getMonth());
//							xgSpecialCateMonthDetail.setCallSeconds(specialCateMonthDetail.getCall_seconds());
//							xgSpecialCateMonthDetail.setSendCnt(specialCateMonthDetail.getSend_cnt());
//							xgSpecialCateMonthDetailService.save(xgSpecialCateMonthDetail);
//						}
//					}
//				}
//			}
//
//			// 保存 monthly_consumption
//			List<MonthlyConsumption> monthlyConsumption = opReport.getMonthly_consumption();
//			XgMonthlyConsumption d3 = new XgMonthlyConsumption();
//			d3.setBorrowerId(borrowerId);
//			xgMonthlyConsumptionService.deleteZ(d3);
//			if (monthlyConsumption != null && monthlyConsumption.size() > 0) {
//				for (MonthlyConsumption consumption : monthlyConsumption) {
//					XgMonthlyConsumption xgMonthlyConsumption = new XgMonthlyConsumption();
//					xgMonthlyConsumption.setBorrowerId(borrowerId);
//					xgMonthlyConsumption.setCalledCnt(consumption.getCalled_cnt());
//					xgMonthlyConsumption.setTalkSeconds(consumption.getTalk_seconds());
//					xgMonthlyConsumption.setTalkCnt(consumption.getTalk_cnt());
//					xgMonthlyConsumption.setMsgCnt(consumption.getMsg_cnt());
//					xgMonthlyConsumption.setCalledSeconds(consumption.getCalled_seconds());
//					xgMonthlyConsumption.setReceiveCnt(consumption.getReceive_cnt());
//					xgMonthlyConsumption.setMonth(consumption.getMonth());
//					xgMonthlyConsumption.setCallCnt(consumption.getCall_cnt());
//					xgMonthlyConsumption.setUnknownCnt(consumption.getUnknown_cnt());
//					xgMonthlyConsumption.setCallConsumption(consumption.getCall_consumption());
//					xgMonthlyConsumption.setSendCnt(consumption.getSend_cnt());
//					xgMonthlyConsumption.setCallSeconds(consumption.getCall_seconds());
//					xgMonthlyConsumptionService.save(xgMonthlyConsumption);
//				}
//			}
//
//			// 保存 call log
//			List<CallLog> callLogList = opReport.getCall_log();
//			XgCallLog d4 = new XgCallLog();
//			d4.setBorrowerId(borrowerId);
//			xgCallLogService.deleteZ(d4);
//			XgCallLogDetail d12 = new XgCallLogDetail();
//			d12.setBorrowerId(borrowerId);
//			xgCallLogDetailService.deleteZ(d12);
//			if (callLogList != null && callLogList.size() > 0) {
//				for (CallLog callLog : callLogList) {
//					XgCallLog xgCallLog = new XgCallLog();
//					xgCallLog.setBorrowerId(borrowerId);
//					xgCallLog.setContactNonn(callLog.getContact_noon());
//					xgCallLog.setTalkSeconds(callLog.getTalk_seconds());
//					xgCallLog.setTalkCnt(callLog.getTalk_cnt());
//					xgCallLog.setContact3m(callLog.getContact_3m());
//					xgCallLog.setMsgCnt(callLog.getMsg_cnt());
//					xgCallLog.setContact1m(callLog.getContact_1m());
//					xgCallLog.setUnknownCnt(callLog.getUnknown_cnt());
//					xgCallLog.setContactEveing(callLog.getContact_eveing());
//					xgCallLog.setContact1w(callLog.getContact_1w());
//					xgCallLog.setPhoneInfo(callLog.getPhone_info());
//					xgCallLog.setCallSeconds(callLog.getCall_seconds());
//					xgCallLog.setCallCnt(callLog.getCall_cnt());
//					xgCallLog.setCalledCnt(callLog.getCalled_cnt());
//					xgCallLog.setContactWeekday(callLog.getContact_weekday());
//					xgCallLog.setReceiveCnt(callLog.getReceive_cnt());
//					xgCallLog.setPhone(callLog.getPhone());
//					xgCallLog.setCallSeconds(callLog.getCall_seconds());
//					xgCallLog.setFirstContactDate(callLog.getFirst_contact_date());
//					xgCallLog.setContactAfternoon(callLog.getContact_afternoon());
//					xgCallLog.setContactEarlyMorning(callLog.getContact_early_morning());
//					xgCallLog.setLastContactDate(callLog.getLast_contact_date());
//					xgCallLog.setContactNight(callLog.getContact_night());
//					xgCallLog.setPhoneLabel(callLog.getPhone_label());
//					xgCallLog.setSendCnt(callLog.getSend_cnt());
//					xgCallLog.setPhoneLocation(callLog.getPhone_location());
//					xgCallLog.setContactMorning(callLog.getContact_morning());
//					xgCallLog.setContactWeekend(callLog.getContact_weekend());
//					xgCallLog.setCalledSeconds(callLog.getCalled_seconds());
//					xgCallLogService.save(xgCallLog);
//
//					List<Detail> s = callLog.getDetail();
//					for (Detail detail : s) {
//						XgCallLogDetail xgCallLogDetail = new XgCallLogDetail();
//						xgCallLogDetail.setBorrowerId(borrowerId);
//						xgCallLogDetail.setCallLogId(xgCallLog.getId());
//						xgCallLogDetail.setCalledCnt(detail.getCalled_cnt());
//						xgCallLogDetail.setTalkSeconds(detail.getTalk_seconds());
//						xgCallLogDetail.setTalkCnt(detail.getTalk_cnt());
//						xgCallLogDetail.setMsgCnt(detail.getMsg_cnt());
//						xgCallLogDetail.setCalledSeconds(detail.getCalled_seconds());
//						xgCallLogDetail.setReceiveCnt(detail.getReceive_cnt());
//						xgCallLogDetail.setMonth(detail.getMonth());
//						xgCallLogDetail.setCallCnt(detail.getCall_cnt());
//						xgCallLogDetail.setUnknownCnt(detail.getUnknown_cnt());
//						xgCallLogDetail.setSendCnt(detail.getSend_cnt());
//						xgCallLogDetail.setCallSeconds(detail.getCall_seconds());
//						xgCallLogDetailService.save(xgCallLogDetail);
//					}
//				}
//			}
//
//			// 保存 trip_analysis
//			List<TripAnalysis> tripAnalysisList = opReport.getTrip_analysis();
//			XgTripAnalysis d6 = new XgTripAnalysis();
//			d6.setBorrowerId(borrowerId);
//			xgTripAnalysisService.deleteZ(d6);
//			XgTripAnalysisDetail d15 = new XgTripAnalysisDetail();
//			d15.setBorrowerId(borrowerId);
//			xgTripAnalysisDetailService.deleteZ(d15);
//			if (tripAnalysisList != null && tripAnalysisList.size() > 0) {
//				for (TripAnalysis tripAnalysis : tripAnalysisList) {
//					XgTripAnalysis xgtripAnalysis = new XgTripAnalysis();
//					xgtripAnalysis.setBorrowerId(borrowerId);
//
//					xgtripAnalysis.setCalledCnt(tripAnalysis.getCalled_cnt());
//					xgtripAnalysis.setTalkCnt(tripAnalysis.getTalk_cnt());
//					xgtripAnalysis.setTalkSeconds(tripAnalysis.getTalk_seconds());
//					xgtripAnalysis.setMsgCnt(tripAnalysis.getMsg_cnt());
//					xgtripAnalysis.setCallSeconds(tripAnalysis.getCall_seconds());
//					xgtripAnalysis.setReceiveCnt(tripAnalysis.getReceive_cnt());
//					xgtripAnalysis.setDateDistribution(tripAnalysis.getDate_distribution());
//					xgtripAnalysis.setCallCnt(tripAnalysis.getCall_cnt());
//					xgtripAnalysis.setUnknownCnt(tripAnalysis.getUnknown_cnt());
//					xgtripAnalysis.setLocation(tripAnalysis.getLocation());
//					xgtripAnalysis.setSendCnt(tripAnalysis.getSend_cnt());
//					xgtripAnalysis.setCalledSeconds(tripAnalysis.getCalled_seconds());
//					xgTripAnalysisService.save(xgtripAnalysis);
//
//					List<Detail> s = tripAnalysis.getDetail();
//					for (Detail detail : s) {
//						XgTripAnalysisDetail xgTripAnalysisDetail = new XgTripAnalysisDetail();
//						xgTripAnalysisDetail.setBorrowerId(borrowerId);
//						xgTripAnalysisDetail.setTripAnalysisId(xgtripAnalysis.getId());
//						xgTripAnalysisDetail.setCalledCnt(detail.getCalled_cnt());
//						xgTripAnalysisDetail.setTalkSeconds(detail.getTalk_seconds());
//						xgTripAnalysisDetail.setTalkCnt(detail.getTalk_cnt());
//						xgTripAnalysisDetail.setMsgCnt(detail.getMsg_cnt());
//						xgTripAnalysisDetail.setCalledSeconds(detail.getCalled_seconds());
//						xgTripAnalysisDetail.setReceiveCnt(detail.getReceive_cnt());
//						xgTripAnalysisDetail.setMonth(detail.getMonth());
//						xgTripAnalysisDetail.setCallCnt(detail.getCall_cnt());
//						xgTripAnalysisDetail.setUnknownCnt(detail.getUnknown_cnt());
//						xgTripAnalysisDetail.setSendCnt(detail.getSend_cnt());
//						xgTripAnalysisDetail.setCallSeconds(detail.getCall_seconds());
//						xgTripAnalysisDetailService.save(xgTripAnalysisDetail);
//					}
//				}
//			}
//
//			// 保存 area_analysis
//			List<AreaAnalysis> areaAnalysisList = opReport.getArea_analysis();
//			XgAreaAnalysisDetail d112 = new XgAreaAnalysisDetail();
//			d112.setBorrowerId(borrowerId);
//			xgAreaAnalysisDetailService.deleteZ(d112);
//			XgAreaAnalysis d8 = new XgAreaAnalysis();
//			d8.setBorrowerId(borrowerId);
//			xgAreaAnalysisService.deleteZ(d8);
//
//			if (areaAnalysisList != null && areaAnalysisList.size() > 0) {
//				for (AreaAnalysis areaAnalysis : areaAnalysisList) {
//					XgAreaAnalysis xgAreaAnalysis = new XgAreaAnalysis();
//					xgAreaAnalysis.setBorrowerId(borrowerId);
//
//					xgAreaAnalysis.setCalledCnt(areaAnalysis.getCalled_cnt());
//					xgAreaAnalysis.setTalkSeconds(areaAnalysis.getTalk_seconds());
//					xgAreaAnalysis.setTalkCnt(areaAnalysis.getTalk_cnt());
//					xgAreaAnalysis.setArea(areaAnalysis.getArea());
//					xgAreaAnalysis.setReceiveCnt(areaAnalysis.getReceive_cnt());
//					xgAreaAnalysis.setCallSeconds(areaAnalysis.getCall_seconds());
//					xgAreaAnalysis.setMsgCnt(areaAnalysis.getMsg_cnt());
//					xgAreaAnalysis.setCallCnt(areaAnalysis.getCall_cnt());
//					xgAreaAnalysis.setUnknownCnt(areaAnalysis.getUnknown_cnt());
//					xgAreaAnalysis.setContact_phoneCnt(areaAnalysis.getContact_phone_cnt());
//					xgAreaAnalysis.setSendCnt(areaAnalysis.getSend_cnt());
//					xgAreaAnalysis.setCalledSeconds(areaAnalysis.getCalled_seconds());
//					xgAreaAnalysisService.save(xgAreaAnalysis);
//
//					List<AreaAnalysisDetail> s = areaAnalysis.getDetail();
//					for (AreaAnalysisDetail detail : s) {
//						XgAreaAnalysisDetail xgTripAnalysisDetail = new XgAreaAnalysisDetail();
//						xgTripAnalysisDetail.setBorrowerId(borrowerId);
//						xgTripAnalysisDetail.setAreaAnalysisId(xgAreaAnalysis.getId());
//
//						xgTripAnalysisDetail.setCalledCnt(detail.getCalled_cnt());
//						xgTripAnalysisDetail.setTalkSeconds(detail.getTalk_seconds());
//						xgTripAnalysisDetail.setTalkCnt(detail.getTalk_cnt());
//						xgTripAnalysisDetail.setMsgCnt(detail.getMsg_cnt());
//						xgTripAnalysisDetail.setCalledSeconds(detail.getCalled_seconds());
//						xgTripAnalysisDetail.setReceiveCnt(detail.getReceive_cnt());
//						xgTripAnalysisDetail.setMonth(detail.getMonth());
//						xgTripAnalysisDetail.setCallCnt(detail.getCall_cnt());
//						xgTripAnalysisDetail.setUnknownCnt(detail.getUnknown_cnt());
//						xgTripAnalysisDetail.setContactPhoneCnt(detail.getContact_phone_cnt());
//						xgTripAnalysisDetail.setSendCnt(detail.getSend_cnt());
//						xgTripAnalysisDetail.setCallSeconds(detail.getCall_seconds());
//						xgAreaAnalysisDetailService.save(xgTripAnalysisDetail);
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("~~~~~~~~~~~~~~~ 运营商报表保存异常：", e);
//		}
//	}
//
//	private void saveXgDate2(JSONObject jsonObject, Long borrowerId) {
//		try {
//			String objStr = jsonObject.getString("obj");
//
//			JSONObject xgJson = JSON.parseObject(objStr);
//			XGReturn xgReturn = JSONObject.toJavaObject(xgJson, XGReturn.class);
//
//			if (xgReturn != null && !CommUtils.isNull(xgReturn.getScore())) {
//				// 更新主表
//				XgOverall xgOverall = xgOverallService.findXgOverall(borrowerId);
//				if (xgOverall != null) {
//					xgOverall.setScore(xgReturn.getScore());
//					xgOverallService.updateXgOverall(xgOverall);
//				}
//
//				MidScore midScore = xgReturn.getMid_score();
//				if (midScore != null) {
//					XgMidScore xgMidScore = new XgMidScore();
//					xgMidScore.setBorrowerId(borrowerId);
//					xgMidScoreService.deleteZ(xgMidScore);
//
//					xgMidScore.setAprvScore(midScore.getAprv_score());
//					xgMidScore.setDangerScore(midScore.getDanger_score());
//					xgMidScore.setInstallmentLoanScore(midScore.getInstallment_loan_score());
//					xgMidScore.setLoanScore(midScore.getLoan_score());
//					xgMidScore.setOpScore(midScore.getOp_score());
//					xgMidScore.setPaydayLoanScore(midScore.getPayday_loan_score());
//					xgMidScore.setQaScore(midScore.getQa_score());
//					xgMidScore.setZhimaScore(midScore.getZhima_score());
//					xgMidScoreService.save(xgMidScore);
//				}
//			}
//
//		} catch (Exception e) {
//			logger.error("~~~~~~~~~~~~~~~ 西瓜分数据异常：", e);
//		}
//
//	}

	@RequestMapping(value = "/operateReturnH5.do")
	public String operateReturnH5(HttpServletRequest request, HttpServletResponse response) {
		try {
			String state = request.getParameter("state");
			String authChannel = request.getParameter("authChannel");
			String pageUrl = ResourceBundle.getBundle("config").getString("page_url");
			String pageProjectName = ResourceBundle.getBundle("config").getString("page_project_name");// 项目名
			String userId = request.getParameter("userId");
			String order_id = request.getParameter("outUniqueId");
			logger.info(" 出参~~~~~~~" + state + "  authChannel: " + authChannel + "  pageUrl: " + pageUrl
					+ " pageProjectName:" + pageProjectName + " userId: " + userId + " order_id: " + order_id);
			if (CommUtils.isNull(userId) || CommUtils.isNull(order_id) || "null".equals(order_id)) {
				logger.info("operateReturnH5===>>userId:" + userId + ",order_id:" + order_id);
				return "auth_fail";
			}

			if ("login".equals(state)) {
				// 修改认证状态
				BwBorrower borrower = new BwBorrower();
				borrower.setId(Long.parseLong(userId));
				borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
				borrower.setAuthStep(5);
				borrower.setUpdateTime(new Date());
				int bNum = bwBorrowerService.updateBwBorrower(borrower);
				logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 修改结果：" + bNum);
				if (bNum < 0) {
					return "auth_fail";
				}

				// 添加运营商认证记录
				BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.valueOf(order_id), 1);
				if (CommUtils.isNull(bwOrderAuth)) {
					bwOrderAuth = new BwOrderAuth();
					bwOrderAuth.setCreateTime(new Date());
					bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
					bwOrderAuth.setOrderId(Long.parseLong(order_id));
					bwOrderAuth.setAuth_type(1);
					bwOrderAuth.setUpdateTime(new Date());
					bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
				} else {
					// logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~融360修改认证运营商认证记录：order_id：" + order_id);
					bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
					bwOrderAuth.setUpdateTime(new Date());
					bwOrderAuthService.updateBwOrderAuth(bwOrderAuth);
				}

				if ("4".equals(authChannel)) {
					return "redirect:" + pageUrl + pageProjectName + "/html/CreditCertification/index.html";
				} else {
					return "auth_success";
				}

			} else {
				if ("4".equals(authChannel)) {
					return "redirect:" + pageUrl + pageProjectName
							+ "/html/CreditCertification/failure.html?category=5";
				} else {
					return "auth_fail";
				}
			}
		} catch (Exception e) {
			logger.error("异常:", e);
			return "auth_fail";
		}

	}

	/**
	 * 魔蝎 - 运营商 - 导入通知URL
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/importNotice.do")
	public AppResponseResult importNotice(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		response.setStatus(201);
		rDto.setCode("200");
		rDto.setMsg("成功");
		return rDto;
	}

	/**
	 * 魔蝎 - 运营商 - 登录状态通知URL
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loginStatusNotice.do")
	public AppResponseResult loginStatusNotice(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		String mobile = request.getParameter("mobile");
		String result = request.getParameter("result");
		response.setStatus(201);
		rDto.setCode("200");
		rDto.setMsg("成功");
		return rDto;
	}

	/**
	 * 魔蝎 - 运营商 - 账单通知URL
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/billNotice.do", method = RequestMethod.POST)
	public AppResponseResult billNotice(@RequestBody CarrierBillTask body, HttpServletRequest request,
			HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		// String body = request.getParameter("body");
		try {
			if (!CommUtils.isNull(body)) {
				logger.info("魔蝎回调TaskId：" + body.getTaskId() + ",userId" + body.getUserId());
				// Map<String, Object> map = (Map<String, Object>) JSONObject.parse(body);
				String task_id = body.getTaskId();

				if (!CommUtils.isNull(task_id)) {
					String value = RedisUtils.hget(SystemConstant.MOXIE_CARRIER_TEM, task_id);
					MoxieTaskDto moxieTaskDto = null;
					if (!CommUtils.isNull(value)) {
						moxieTaskDto = JsonUtils.fromJson(value, MoxieTaskDto.class);
						// 魔蝎回调 通话记录
						logger.info("魔蝎回调 通话记录:" + moxieTaskDto.getUser_id());
						RedisUtils.rpush(SystemConstant.MOXIE_CARRIER_KEY, JsonUtils.toJson(moxieTaskDto));

						RedisUtils.hdel(SystemConstant.MOXIE_CARRIER_TEM, task_id);
						// response.setStatus(201);

					} else {
						moxieTaskDto = new MoxieTaskDto();
						moxieTaskDto.setMobile(body.getMobile());
						moxieTaskDto.setOrder_id("0");
						moxieTaskDto.setTask_id(body.getTaskId());
						moxieTaskDto.setUser_id(body.getUserId());
						moxieTaskDto.setAccount(3);
						RedisUtils.rpush(SystemConstant.MOXIE_CARRIER_KEY, JsonUtils.toJson(moxieTaskDto));
						logger.info(
								"魔蝎回调 存入redis:" + moxieTaskDto.getUser_id() + ",task_id:" + moxieTaskDto.getTask_id());
					}
					response.setStatus(201);
					rDto.setCode("201");
					rDto.setMsg("成功");
				} else {
					response.setStatus(101);
					rDto.setCode("100");
					rDto.setMsg("task_id不可为空");
				}
			} else {
				logger.info("请求失败：body不可为空");
				response.setStatus(101);
				rDto.setCode("100");
				rDto.setMsg("body不可为空");
			}
		} catch (Exception e) {
			logger.error("请求失败：", e);
			response.setStatus(101);
			rDto.setCode("101");
			rDto.setMsg("请求失败");
			return rDto;
		}
		return rDto;
	}

	/**
	 * 魔蝎 - 运营商 - 失败原因通知URL
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/failureNotice.do")
	public AppResponseResult failureNotice(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		response.setStatus(201);
		rDto.setCode("200");
		rDto.setMsg("成功");
		return rDto;
	}

	/**
	 * 魔蝎 - 运营商 - 分析报告通知URL
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/analysisReportNotice.do")
	public AppResponseResult analysisReportNotice(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		response.setStatus(201);
		rDto.setCode("200");
		rDto.setMsg("成功");
		return rDto;
	}

	@ResponseBody
	@RequestMapping("/test.do")
	public AppResponseResult test(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		List<BwContactList> list = new ArrayList<BwContactList>();
		try {
			// String reString =
			// bwOperateVoiceService.getCallTimeByborrowerIdEs(18l);
			// List<String> list2 =
			// bwOperateVoiceService.outCountVoicetopTenEs(18l);
			// List<String> list2 =
			// bwOperateVoiceService.inCountVoicetopTenEs(18l);
			// int i = bwOperateVoiceService.SumVoiceTopTenEs(18l);
			// bwOperateVoiceService.countVoiceOutTimesEs(18l);
			// bwOperateVoiceService.countVoiceInTimesEs(18l);

			// Map<String,Object> map =
			// bwOperateVoiceService.countVoiceTimesEs(18l);
			// int num = bwOperateVoiceService.sumContactEs(18l);
			// List<String> list2 = bwOperateVoiceService.sumShortPhoneEs(18l);
			// BwContactList bwContactList = new BwContactList();
			// bwContactList.setBorrowerId(3L);
			// bwContactList.setCreateTime(new Date());
			// bwContactList.setName("妈妈123");
			// bwContactList.setPhone("15827214439");
			// bwContactList.setUpdateTime(new Date());
			// bwContactListService.updateToEs(bwContactList);
			List<BwContactList> list2 = new ArrayList<BwContactList>();
			BwContactList bwContactList = new BwContactList();
			bwContactList.setBorrowerId(3l);
			bwContactList.setName("妈妈2");
			bwContactList.setPhone("15827214439");
			list2.add(bwContactList);
			BwContactList bwContactList2 = new BwContactList();
			bwContactList2.setBorrowerId(3l);
			bwContactList2.setName("吴慧2");
			bwContactList2.setPhone("15006870215");
			list2.add(bwContactList2);
			Map<String, Object> map = new HashMap<String, Object>();
			// bwContactListService.addOrUpdateBwContactLists(list2);
			// map = bwOperateVoiceService.countVoiceTimesEs(18l);
			// int l = bwOperateVoiceService.sumContactEs(18l); //呼出
			// for (int i = 0; i < 10; i++) {
			// BwContactList bwContactList = new BwContactList();
			// bwContactList.setBorrowerId(3l);
			// bwContactList.setName("HW"+i);
			// bwContactList.setPhone("1816352600"+i);
			// bwContactList.setId((long)i);
			// list.add(bwContactList);
			// bwContactListService.saveToEs(bwContactList);
			// }
			// bwContactListService.addOrUpdateBwContactLists(list);

			// bwContactListService.findBwContactListByBorrowerIdEs(18l);
			rDto.setCode("200");
			rDto.setMsg("成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rDto;
	}

	@ResponseBody
	@RequestMapping("/test2.do")
	public AppResponseResult test2(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		Map<String, Object> map;
		try {
			// start_time=1478659555&end_time=1478832355
			Response<List<RongOrder>> resOrder = BeadWalletRongOrderService.getOrderList(1478659555L, 1478832355L);
			// 判断该借款人是否在本平台已存在
			List<RongOrder> list = resOrder.getObj();
			if (!CommUtils.isNull(list)) {
				for (RongOrder rongOrder : list) {
					BwBorrower borrower = new BwBorrower();
					borrower.setPhone(rongOrder.getUser_mobile());
					borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
					if (CommUtils.isNull(borrower)) {
						// 创建新的借款人
						// 创建借款人
						borrower = new BwBorrower();
						borrower.setName(rongOrder.getUser_name());
						borrower.setIdCard(rongOrder.getUser_id());
						borrower.setPhone(rongOrder.getUser_mobile());
						String pwd = "123456a";// 设置默认密码
						borrower.setPassword(CommUtils.getMD5(pwd.getBytes()));
						borrower.setCreateTime(new Date());
						borrower.setAuthStep(1);
						borrower.setFlag(1);
						borrower.setState(1);
						int num = bwBorrowerService.addBwBorrower(borrower);
						if (num <= 0) {
							logger.info("引流rong360订单时，添加新的借款人失败,手机号为：" + rongOrder.getUser_mobile());
						}
						// 创建工单
						BwOrder bwOrder = new BwOrder();
						String orderNo = OrderUtil.generateOrderNo();
						bwOrder.setOrderNo(orderNo);
						bwOrder.setBorrowerId(borrower.getId());
						bwOrder.setStatusId(1L);
						bwOrder.setCreateTime(new Date());
						bwOrder.setChannel(7);// 表示从360 引流
						bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
						bwOrder.setApplyPayStatus(0);
						bwOrderService.addBwOrder(bwOrder);
						// 工单id 和融360引流的订单绑定
						BwOrderRong bwOrderRong = new BwOrderRong();
						logger.info("工单id：" + bwOrder.getId());
						logger.info("360引流的订单号为：" + rongOrder.getOrder_No());
						bwOrderRong.setOrderId(bwOrder.getId());
						// bwOrderRong.setThirdOrdeNo(rongOrder.getOrder_No());
						bwOrderRongService.save(bwOrderRong);
						// 获取订单详细信息
						Response<RongOrderDetail> detailRes = BeadWalletRongOrderService.getOrderDetail(orderNo);
						// 获取订单补充信息
						Response<RongAddInfo> addInfoRes = BeadWalletRongOrderService.getOrderAddInfo(orderNo);
						RongOrderDetail rongOrderDetail = detailRes.getObj();
						RongAddInfo rongAddInfo = addInfoRes.getObj();
						// 录入身份证号和姓名
						BwBorrower borrowerUpdate = new BwBorrower();
						logger.info("借款人id：" + borrower.getId());
						borrowerUpdate.setId(borrower.getId());
						borrowerUpdate.setIdCard(rongOrderDetail.getApplyDetail().getUser_id());
						borrowerUpdate.setUpdateTime(new Date());

						// 录入personInfo
						BwPersonInfo bpi = new BwPersonInfo();
						bpi.setOrderId(bwOrder.getId());
						bpi.setCityName(rongOrder.getCity());
						bpi.setAddress(rongAddInfo.getAddr_detail());
						bpi.setRelationName(rongAddInfo.getEmergency_contact_personA_name());
						bpi.setRelationPhone(rongAddInfo.getEmergency_contact_personA_phone());
						bpi.setUnrelationName(rongAddInfo.getEmergency_contact_personB_phone());
						bpi.setUnrelationPhone(rongAddInfo.getEmergency_contact_personB_name());
						bpi.setHouseStatus(0);
						bpi.setMarryStatus(0);
						bpi.setCarStatus(0);
						bpi.setCreateTime(new Date());
						Long pid = bwPersonInfoService.save(bpi);
						// BwPersonInfo bwPersonInfo = new BwPersonInfo();
						// bwPersonInfo.setAddress(address);
						// bwPersonInfo.setCarStatus(0);
						// bwPersonInfo.setCityName(cityName);
						// bwPersonInfo.setCreateTime(new Date());
						// bwPersonInfo.setHouseStatus(0);
						// bwPersonInfo.setMarryStatus(0);
						// bwPersonInfo.setOrderId(orderId);
						// bwPersonInfo.setRelationName(relationName);
						// bwPersonInfo.setRelationPhone(relationPhone);
						// bwPersonInfo.setUnrelationName(unrelationName);
						// bwPersonInfo.setUnrelationPhone(unrelationPhone);
						// 录入工作信息
						BwWorkInfo bwWorkInfo = new BwWorkInfo();
						bwWorkInfo.setCallTime("10:00 - 12:00");
						bwWorkInfo.setComName(rongAddInfo.getCompany_name_open());
						bwWorkInfo.setCreateTime(new Date());
						bwWorkInfo.setOrderId(bwOrder.getId());
						bwWorkInfo.setWorkYears("一年以内");
						// 录入运营商信息
						// 录入附件信息
						// 身份证正面
						BwAdjunct sfzzmbaj = new BwAdjunct();
						sfzzmbaj = new BwAdjunct();
						sfzzmbaj.setAdjunctType(1);
						sfzzmbaj.setAdjunctPath(rongAddInfo.getID_Positive());
						sfzzmbaj.setOrderId(bwOrder.getId());
						sfzzmbaj.setBorrowerId(borrower.getId());
						sfzzmbaj.setCreateTime(new Date());
						Long sfzzmId = bwAdjunctService.save(sfzzmbaj);
						// 身份证反面
						BwAdjunct sfzfmbaj = new BwAdjunct();
						sfzfmbaj = new BwAdjunct();
						sfzfmbaj.setAdjunctType(2);
						// sfzfmbaj.setAdjunctPath(rongAddInfo.getPhoto_hand_ID());
						sfzfmbaj.setOrderId(bwOrder.getId());
						sfzfmbaj.setBorrowerId(borrower.getId());
						sfzfmbaj.setCreateTime(new Date());
						Long sfzfmId = bwAdjunctService.save(sfzfmbaj);
						// 修改状态
						BwBorrower bwBorrower = new BwBorrower();
						bwBorrower.setAuthStep(4);
						bwBorrower.setBorrowerId(borrower.getId());
						bwBorrowerService.updateBwBorrower(bwBorrower);
						BwOrder order = new BwOrder();
						order.setStatusId(2l);
						order.setId(bwOrder.getId());
						bwOrderService.updateBwOrder(order);
					} else {
						// 不符合条件
						bwRongOrderService.orderfeedback(rongOrder.getOrder_No(), 40);
					}
				}
			}
		} catch (Exception e) {
			rDto.setCode("101");
			rDto.setMsg("系统异常");
			e.printStackTrace();
			return rDto;
		}
		rDto.setCode("000");
		rDto.setMsg("成功");
		return rDto;
	}

	@ResponseBody
	@RequestMapping("/test3.do")
	public AppResponseResult test3(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		// 开户
		RegReqData regReqData = new RegReqData();
		regReqData.setBank_nm("招商银行");// 开户支行
		regReqData.setCapAcntNo("6225882709161826");// 银行卡号
		regReqData.setCertif_id("110101199801014639");// 身份证号
		regReqData.setCertif_tp("0");// 证件类型
		regReqData.setCity_id("1000");// 开户地区
		regReqData.setCust_nm("测试");// 真实姓名
		regReqData.setMchnt_cd(ResourceBundle.getBundle("fuiou").getString("mchnt_cd"));// 富友生产商户代码
		regReqData.setMchnt_txn_ssn(GenerateSerialNumber.getSerialNumber());
		regReqData.setMobile_no("15609889295");// 手机号(账号)
		regReqData.setParent_bank_id("0308");// 开户行);
		ResponsePayResult<CommonRspData> payResult = BeadwalletService.bwReg(regReqData);
		System.out.println(payResult.getBeadwalletCode());
		System.out.println(payResult.getBeadwalletMsg());
		// if (payResult.getBeadwalletCode().equals("0000") ||
		// payResult.getBeadwalletCode().equals("5343")) {
		// logger.info("更新富有账户");
		// // 更新富有账户
		// BwBorrower borrower = new BwBorrower();
		// borrower.setId(802l);
		// borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		// borrower.setFuiouAcct("13924885309");
		// bwBorrowerService.updateBwBorrower(borrower);
		// }
		// BwBankCard bbc = new BwBankCard();
		// bbc.setBorrowerId(678l);
		// bbc.setCardNo("");
		// bbc.setProvinceCode("420");
		// bbc.setCityCode("5210");
		// bbc.setBankCode("0308");
		// bbc.setBankName("招商银行");
		// bbc.setSignStatus(0);
		// bbc.setCreateTime(new Date());
		// ResponsePayResult<CommonRspData> bw =
		// 签约
		String phone = "15609889295";// 登录金账户名
		String mobile = "15609889295";// 银行预留手机号
		String orderId = "871";// 订单号
		logger.info("免登陆签约获取金账户号:" + phone);
		logger.info("免登陆签约获取银行预留手机号:" + mobile);
		AppSignCardReqData reqData = new AppSignCardReqData();
		reqData.setMchnt_cd(SystemConstant.FUIOU_MCHNT_CD);
		String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
		logger.info("免登陆签约流水号:" + mchnt_txn_ssn);
		reqData.setMchnt_txn_ssn(mchnt_txn_ssn);
		reqData.setPage_notify_url(SystemConstant.CALLBACK_URL + "/app/my/signCallBackRong.do?orderId=" + orderId);
		logger.info("免登陆签约回调地址:" + reqData.getPage_notify_url());
		reqData.setLogin_id(phone);
		reqData.setMobile(mobile);
		ResponsePayResult<AppSignCardRspData> rp = BeadwalletService.bwAppSignCard(reqData, response);
		logger.info("请求sdk后获取的返回code:" + rp.getBeadwalletCode());
		logger.info("请求sdk后获取的返回msg:" + rp.getBeadwalletMsg());
		rDto.setCode("000");
		rDto.setMsg("成功");
		return rDto;
	}

	@ResponseBody
	@RequestMapping("/test4.do")
	public AppResponseResult test4(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		Date now = new Date();
		// 芝麻信用
		// ZmxyReqData zmxyReqData = new ZmxyReqData();
		// zmxyReqData.setIdNo("张裙");
		// zmxyReqData.setName("421081199204215520");
		// zmxyReqData.setMobile("18163862665");
		// Response<ivsRspData> res =
		// BeadWalletZmxyService.ivsDetail(zmxyReqData);
		// if (res.getRequestCode().equals("200")) {
		// ivsRspData ivsRspData = res.getObj();
		// List<IvsDetail> list = ivsRspData.getIvsDetail();
		// for (IvsDetail ivsDetail : list) {
		// BwZmxyIvs bwZmxyIvs = new BwZmxyIvs();
		// System.out.println(Integer.parseInt(ivsRspData.getIvsScore()));
		// bwZmxyIvs.setIvsScore(Integer.parseInt(ivsRspData.getIvsScore()));
		// bwZmxyIvs.setName("张裙");
		// bwZmxyIvs.setIdCard("421081199204215520");
		// bwZmxyIvs.setPhone("18163862665");
		// System.out.println(ivsDetail.getCode());
		// System.out.println(ivsDetail.getDescription());
		// bwZmxyIvs.setCode(ivsDetail.getCode());
		// bwZmxyIvs.setDescription(ivsDetail.getDescription());
		// bwZmxyIvs.setCreateTime(now);
		// bwZmxyIvs.setUpdateTime(now);
		// bwZmxyIvsService.save(bwZmxyIvs);
		// }
		// }
		// 宜信至诚分
		// YxafReqData yxafReqData = new YxafReqData();
		// yxafReqData.setName("刘军");
		// yxafReqData.setIdNo("430726198610090815");
		// yxafReqData.setIdType("101");
		// yxafReqData.setQueryReason("10");
		// Response<QueryCreditScoreRspData> res =
		// BeadWalletYxafService.queryCreditScore(yxafReqData);
		// if (res.getRequestCode().equals("200")) {
		// BwYxafScore bwYxafScore = new BwYxafScore();
		// bwYxafScore.setName(yxafReqData.getName());
		// bwYxafScore.setIdCard(yxafReqData.getIdNo());
		// bwYxafScore.setRate(res.getObj().getRate());
		// bwYxafScore.setCreditScore(Integer.parseInt(res.getObj().getCreditScore()));
		// bwYxafScore.setCreateTime(now);
		// bwYxafScore.setUpdateTime(now);
		// bwYxafScoreService.save(bwYxafScore);
		// }
		// 前海
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HHmmss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QhzxReqData qhzxReqData = new QhzxReqData();
		qhzxReqData.setTransNo("bbbbb" + System.currentTimeMillis());
		qhzxReqData.setTransDate(sdf3.format(now));
		qhzxReqData.setBatchNo("ccccc" + System.currentTimeMillis());
		qhzxReqData.setIdNo("421081199304235510");
		qhzxReqData.setName("胡威");
		qhzxReqData.setReasonCode("01");
		qhzxReqData.setEntityAuthCode("AAAA" + System.currentTimeMillis());
		qhzxReqData.setEntityAuthDate(sdf.format(now));
		qhzxReqData.setSeqNo("lldxl" + sdf2.format(now).replaceAll(" ", ""));
		Response<QueryData> res = BeadWalletQhzxService.query(qhzxReqData);
		System.out.println("返回code" + res.getRequestCode());
		System.out.println("返回msg" + res.getRequestMsg());
		System.out.println("返回msg" + res.getObj());
		if (res.getRequestCode().equals("200")) {
			QueryData queryData = res.getObj();
			List<Record2> list = queryData.getRecords();
			for (Record2 record2 : list) {
				// 保存
				BwQhzxBlack bwQhzxBlack = new BwQhzxBlack();
				bwQhzxBlack.setIdCard(record2.getIdNo());
				bwQhzxBlack.setName(record2.getName());
				bwQhzxBlack.setRskMark(record2.getRskMark());
				bwQhzxBlack.setRskScore(record2.getRskScore());
				bwQhzxBlack.setSourceId(record2.getSourceId());
				bwQhzxBlack.setDataBuildTime(record2.getDataBuildTime());
				bwQhzxBlack.setCreateTime(now);
				bwQhzxBlack.setUpdateTime(now);
				bwQhzxBlackService.save(bwQhzxBlack);
			}
		}
		rDto.setCode("000");
		rDto.setMsg("成功");
		return rDto;
	}

	@ResponseBody
	@RequestMapping("/test5.do")
	public AppResponseResult test5(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(2l);
		System.out.println("合同年利率" + bwProductDictionary.getpInvesstRateYear());
		System.out.println("借款月利率" + bwProductDictionary.getpInvestRateMonth());
		System.out.println("合同月利率" + bwProductDictionary.getpBorrowRateMonth());
		BwRateDictionary bwRateDictionary = new BwRateDictionary();
		bwRateDictionary.setRateTerm(1);
		bwRateDictionary.setRateType(1);
		bwRateDictionary = bwRateDictionaryService.findBwRateDictionaryByAttr(bwRateDictionary);
		Double contractMonthRate = 0.0;
		Double repayAmount = 0.0;
		Double contractAmount = 0.0;
		// 先息后本
		// 计算合同月利率
		contractMonthRate = DoubleUtil.round(Math.pow(1 + (bwRateDictionary.getContractRate() / 100), 1.0 / 12.0) - 1,
				8);
		// 计算合同金额
		BwOrder order = new BwOrder();
		order.setId(50l);
		order = bwOrderService.findBwOrderByAttr(order);
		// 计算还款金额
		repayAmount = DoubleUtil.round(
				((order.getBorrowAmount() / 1) + (order.getBorrowAmount() * bwRateDictionary.getBorrowerRate() / 100)),
				2);
		// 计算合同金额
		contractAmount = DoubleUtil.round((repayAmount * (Math.pow(1 + contractMonthRate, 1) - 1))
				/ (contractMonthRate * (Math.pow(1 + contractMonthRate, 1))), 2);
		System.out.println("==============:借款月利率" + bwRateDictionary.getBorrowerRate() / 100);
		System.out.println("==============:合同月利率" + contractMonthRate);
		rDto.setCode("000");
		rDto.setMsg("成功");
		return rDto;
	}

	@ResponseBody
	@RequestMapping("/test6.do")
	public AppResponseResult test6(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		String idCard = "3302041988070";
		String phone = "18500023207";
		BwBorrower borrower = bwBorrowerService.oldUserFilter(idCard.substring(0, idCard.length() - 5),
				phone.substring(0, phone.length() - 4));
		if (CommUtils.isNull(borrower)) {
			// 200 表示不是老用户可以申请借款
			System.out.println("可以借款");
		} else {
			BwOrder order = new BwOrder();
			order.setBorrowerId(borrower.getId());
			order = bwOrderService.findBwOrderByAttr(order);
			if (!CommUtils.isNull(order)) {
				BwRejectRecord record = new BwRejectRecord();
				record.setOrderId(order.getId());
				record = bwRejectRecordService.findBwRejectRecordByAtta(record);
				if (!CommUtils.isNull(record)) {
					System.out.println("不可以借款");
				}
			}
			if (borrower.getChannel().equals(11)) {
				System.out.println("可以借款");
			} else {
				System.out.println("不可以借款");
			}
		}
		rDto.setCode("000");
		rDto.setMsg("成功");
		return rDto;
	}
}
