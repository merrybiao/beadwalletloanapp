//package com.waterelephant.sxyDrainage.sina.service.impl;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwThirdOperateBasic;
//import com.waterelephant.entity.BwThirdOperateVoice;
//import com.waterelephant.entity.BwXlApplicationCheck;
//import com.waterelephant.entity.BwXlBasic;
//import com.waterelephant.entity.BwXlBehaviorCheck;
//import com.waterelephant.entity.BwXlCalls;
//import com.waterelephant.entity.BwXlCellBehavior;
//import com.waterelephant.entity.BwXlCheckBlackInfo;
//import com.waterelephant.entity.BwXlCheckSearchInfo;
//import com.waterelephant.entity.BwXlCollectionContact;
//import com.waterelephant.entity.BwXlContactList;
//import com.waterelephant.entity.BwXlContactRegion;
//import com.waterelephant.entity.BwXlDeliverAddress;
//import com.waterelephant.entity.BwXlEbusinessExpense;
//import com.waterelephant.entity.BwXlMainService;
//import com.waterelephant.entity.BwXlNets;
//import com.waterelephant.entity.BwXlReport;
//import com.waterelephant.entity.BwXlSmses;
//import com.waterelephant.entity.BwXlTransactions;
//import com.waterelephant.entity.BwXlTripInfo;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwThirdOperateBasicService;
//import com.waterelephant.service.BwThirdOperateVoiceService;
//import com.waterelephant.service.BwXlApplicationCheckService;
//import com.waterelephant.service.BwXlBasicService;
//import com.waterelephant.service.BwXlBehaviorCheckService;
//import com.waterelephant.service.BwXlCallsService;
//import com.waterelephant.service.BwXlCellBehaviorService;
//import com.waterelephant.service.BwXlCheckBlackInfoService;
//import com.waterelephant.service.BwXlCheckSearchInfoService;
//import com.waterelephant.service.BwXlCollectionContactService;
//import com.waterelephant.service.BwXlContactListService;
//import com.waterelephant.service.BwXlContactRegionService;
//import com.waterelephant.service.BwXlDeliverAddressService;
//import com.waterelephant.service.BwXlEbusinessExpenseService;
//import com.waterelephant.service.BwXlMainServiceService;
//import com.waterelephant.service.BwXlNetsService;
//import com.waterelephant.service.BwXlReportService;
//import com.waterelephant.service.BwXlSmsesService;
//import com.waterelephant.service.BwXlTransactionsService;
//import com.waterelephant.service.BwXlTripInfoService;
//import com.waterelephant.service.impl.BwOrderService;
//import com.waterelephant.sxyDrainage.service.BqsCheckService;
//import com.waterelephant.sxyDrainage.sina.entity.ApplicationCheck;
//import com.waterelephant.sxyDrainage.sina.entity.Basic;
//import com.waterelephant.sxyDrainage.sina.entity.BasicInfo;
//import com.waterelephant.sxyDrainage.sina.entity.Behavior;
//import com.waterelephant.sxyDrainage.sina.entity.BehaviorCheck;
//import com.waterelephant.sxyDrainage.sina.entity.Calls;
//import com.waterelephant.sxyDrainage.sina.entity.CellBehavior;
//import com.waterelephant.sxyDrainage.sina.entity.CheckBlackInfo;
//import com.waterelephant.sxyDrainage.sina.entity.CheckSearchInfo;
//import com.waterelephant.sxyDrainage.sina.entity.CollectionContact;
//import com.waterelephant.sxyDrainage.sina.entity.ContactDetails;
//import com.waterelephant.sxyDrainage.sina.entity.ContactList;
//import com.waterelephant.sxyDrainage.sina.entity.ContactRegion;
//import com.waterelephant.sxyDrainage.sina.entity.DeliverAddress;
//import com.waterelephant.sxyDrainage.sina.entity.EbusinessExpense;
//import com.waterelephant.sxyDrainage.sina.entity.MainService;
//import com.waterelephant.sxyDrainage.sina.entity.Nets;
//import com.waterelephant.sxyDrainage.sina.entity.Report;
//import com.waterelephant.sxyDrainage.sina.entity.Report2;
//import com.waterelephant.sxyDrainage.sina.entity.Service_details;
//import com.waterelephant.sxyDrainage.sina.entity.SinaOperator;
//import com.waterelephant.sxyDrainage.sina.entity.Smses;
//import com.waterelephant.sxyDrainage.sina.entity.Transactions;
//import com.waterelephant.sxyDrainage.sina.entity.TripInfo;
//import com.waterelephant.sxyDrainage.sina.entity.UserInfoCheck;
//import com.waterelephant.sxyDrainage.sina.service.AsyncSinaTaskService;
//import com.waterelephant.sxyDrainage.sina.utils.SinaConstant;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DateUtil;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
///**
// * 
// * Module:
// * 
// * AsyncSinaTaskServiceImpl.java
// * 
// * @author YANHUI
// * @since JDK 1.8
// * @version 1.0
// * @description: <异步处理运营商数据>
// */
//@Service
//public class AsyncSinaTaskServiceImpl implements AsyncSinaTaskService {
//
//	private Logger logger = LoggerFactory.getLogger(AsyncSinaTaskServiceImpl.class);
//	
//
//	@Autowired
//	private BqsCheckService bqsCheckService;
//	@Autowired
//	private BwOrderService bwOrderService;
//	@Autowired
//	private BwOrderProcessRecordService bwOrderProcessRecordService;
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	@Autowired
//	private BwXlContactRegionService bwXlContactRegionService;
//	@Autowired
//	private BwXlApplicationCheckService bwXlApplicationCheckService;
//	@Autowired
//	private BwXlBehaviorCheckService bwXlBehaviorCheckService;
//	@Autowired
//	private BwXlCellBehaviorService bwXlCellBehaviorService;
//	@Autowired
//	private BwXlCollectionContactService bwXlCollectionContactService;
//	@Autowired
//	private BwXlContactListService bwXlContactListService;
//	@Autowired
//	private BwXlDeliverAddressService bwXlDeliverAddressService;
//	@Autowired
//	private BwXlEbusinessExpenseService bwXlEbusinessExpenseService;
//	@Autowired
//	private BwXlMainServiceService bwXlMainServiceService;
//	@Autowired
//	private BwXlReportService bwXlReport2Service;
//	@Autowired
//	private BwXlTripInfoService bwXlTripInfoService;
//	@Autowired
//	private BwXlCallsService bwXlCallsService;
//	@Autowired
//	private BwXlBasicService bwXlBasicService;
//	@Autowired
//	private BwXlNetsService bwXlNetsService;
//	@Autowired
//	private BwXlSmsesService bwXlSmsesService;
//	@Autowired
//	private BwXlTransactionsService bwXlTransactionsService;
//	@Autowired
//	private BwXlCheckBlackInfoService bwXlCheckBlackInfoService;
//	@Autowired
//	private BwXlCheckSearchInfoService bwXlCheckSearchInfoService;
//	@Autowired
//	private BwThirdOperateBasicService bwThirdOperateBasicService;
//	@Autowired
//	private BwThirdOperateVoiceService bwThirdOperateVoiceService;
//
//	@Async("asyncTaskExecutor")
//	@Override
//	public void addOperator(long sessionId, BwOrder bwOrder, BwBorrower borrower, SinaOperator operator) throws ParseException {
//		logger.info("<<<<<【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪运营商数据>>>>>");
//		Long orderId = bwOrder.getId();
//		if (null != operator) {
//			Report report = operator.getReport();
//			if (null != report) {
//				List<ContactRegion> contact_region = report.getContact_region();
//				bwXlContactRegionService.deleteByOrderId(orderId);
//				for (ContactRegion contactRegion : contact_region) {
//					BwXlContactRegion bwXlContactRegion = new BwXlContactRegion();
//					bwXlContactRegion.setRegionAvgCallInTime(contactRegion.getRegion_avg_call_in_time());
//					bwXlContactRegion.setRegionAvgCallOutTime(contactRegion.getRegion_avg_call_out_time());
//					bwXlContactRegion.setRegionCallInCnt(contactRegion.getRegion_call_in_cnt());
//					bwXlContactRegion.setRegionCallInCntPct(contactRegion.getRegion_call_in_cnt_pct());
//					bwXlContactRegion.setRegionCallInTime(contactRegion.getRegion_call_in_time());
//					bwXlContactRegion.setRegionCallInTimePct(contactRegion.getRegion_call_in_time_pct());
//					bwXlContactRegion.setRegionCallOutCnt(contactRegion.getRegion_call_out_cnt());
//					bwXlContactRegion.setRegionCallOutCntPct(contactRegion.getRegion_call_out_cnt_pct());
//					bwXlContactRegion.setRegionCallOutTime(contactRegion.getRegion_avg_call_out_time());
//					bwXlContactRegion.setRegionCallOutTimePct(contactRegion.getRegion_call_out_time_pct());
//					bwXlContactRegion.setRegionLoc(contactRegion.getRegion_loc());
//					bwXlContactRegion.setRegionUniqNumCnt(contactRegion.getRegion_uniq_num_cnt());
//					bwXlContactRegion.setOrderId(orderId);
//					bwXlContactRegion.setCreateTime(new Date());
//					bwXlContactRegion.setUpdateTime(new Date());
//					try {
//						bwXlContactRegionService.save(bwXlContactRegion);
//					} catch (Exception e) {
//						logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_contact_region表数据异常**********************");
//					}
//				}
//			}
//		} else {
//			logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空**********************");
//		}
//		// 嵌套
//
//		logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪ApplicationCheck**********************");
//
//		logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪ApplicationCheck**********************");
//
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					List<ApplicationCheck> application_check = report.getApplication_check();
//					bwXlApplicationCheckService.deleteByOrderId(orderId);
//					for (ApplicationCheck applicationCheck : application_check) {
//						try {
//							String text = JSON.toJSONString(applicationCheck.getCheck_points());
//							BwXlApplicationCheck bwXlApplicationCheck = JSON.parseObject(text,BwXlApplicationCheck.class);
//							if (null != bwXlApplicationCheck) {
//								bwXlApplicationCheck.setAppPoint(applicationCheck.getApp_point());
//								bwXlApplicationCheck.setCreateTime(new Date());
//								bwXlApplicationCheck.setOrderId(orderId);
//								bwXlApplicationCheck.setUpdateTime(new Date());
//								bwXlApplicationCheckService.save(bwXlApplicationCheck);
//							}
//						} catch (Exception e) {
//							logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_application_check表数据异常**********************",e);
//						}
//					}
//				}
//			} else {
//				logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空**********************");
//			}
//		} catch (Exception e) {
//			logger.info("**********************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//		logger.info("*****************************************************█████████******************************");
//		logger.info("*********************************███████░░░░░░░░░░███▒▒▒▒▒▒▒▒███****************************");
//		logger.info("*********************************█▒▒▒▒▒▒█░░░░░░░███▒▒▒▒▒▒▒▒▒▒▒▒▒███*************************");
//		logger.info("**********************************█▒▒▒▒▒▒█░░░░██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██***********************");
//		logger.info("***********************************█▒▒▒▒▒█░░░██▒▒▒▒▒██▒▒▒▒▒▒██▒▒▒▒▒███**********************");
//		logger.info("************************************█▒▒▒█░░░█▒▒▒▒▒▒████▒▒▒▒████▒▒▒▒▒▒██*********************");
//		logger.info("**********************************█████████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██*********************");
//		logger.info("**********************************█▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒▒▒▒▒▒██*********************");
//		logger.info("********************************██▒▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒██▒▒▒▒▒▒▒▒▒▒██▒▒▒▒██*********************");
//		logger.info("*******************************██▒▒▒███████████▒▒▒▒▒██▒▒▒▒▒▒▒▒██▒▒▒▒▒██*********************");
//		logger.info("*******************************█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒████████▒▒▒▒▒▒▒██*********************");
//		logger.info("*******************************██▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██**********************");
//		logger.info("********************************█▒▒▒███████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██************************");
//		logger.info("********************************██▒▒▒▒▒▒▒▒▒▒████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█**************************");
//		logger.info("*********************************████████████░░░█████████████████***************************");
//		// 无list 无嵌套
//		logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪BehaviorCheck**********************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					List<BehaviorCheck> behavior_check = report.getBehavior_check();
//					bwXlBehaviorCheckService.deleteByOrderId(orderId);
//					for (BehaviorCheck behaviorCheck : behavior_check) {
//						try {
//							String text = JSON.toJSONString(behaviorCheck);
//							BwXlBehaviorCheck bwXlBehaviorCheck = JSON.parseObject(text, BwXlBehaviorCheck.class);
//							if (null != bwXlBehaviorCheck) {
//								bwXlBehaviorCheck.setOrderId(orderId);
//								bwXlBehaviorCheck.setCreateTime(new Date());
//								bwXlBehaviorCheck.setUpdateTime(new Date());
//								bwXlBehaviorCheckService.save(bwXlBehaviorCheck);
//							}
//						} catch (Exception e) {
//							logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_behavior_check表数据异常**********************");
//						}
//					}
//				}
//			} else {
//				logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空**********************");
//			}
//		} catch (Exception e) {
//			logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//		// 有list嵌套
//		logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪CellBehavior**********************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					List<CellBehavior> cell_behavior = report.getCell_behavior();
//					for (CellBehavior cellBehavior : cell_behavior) {
//						bwXlCellBehaviorService.deleteByOrderId(orderId);
//						try {
//							if (null != cellBehavior.getBehavior() && !cellBehavior.getBehavior().isEmpty()) {
//								List<Behavior> behaviorList = cellBehavior.getBehavior();
//								for (Behavior behavior : behaviorList) {
//									String text = JSON.toJSONString(behavior);
//									BwXlCellBehavior bwXlCellBehavior = JSON.parseObject(text, BwXlCellBehavior.class);
//									bwXlCellBehavior.setPhoneNum(cellBehavior.getPhone_num());
//									bwXlCellBehavior.setOrderId(orderId);
//									bwXlCellBehavior.setCreateTime(new Date());
//									bwXlCellBehavior.setUpdateTime(new Date());
//									bwXlCellBehaviorService.save(bwXlCellBehavior);
//								}
//							}
//
//						} catch (Exception e) {
//							logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_cell_behavior表数据异常**********************");
//						}
//					}
//				}
//			} else {
//				logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空**********************");
//			}
//		} catch (Exception e) {
//			logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//		// 有list嵌套，多个单参数
//		logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪CollectionContact**********************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					List<CollectionContact> collection_contact = report.getCollection_contact();
//					bwXlCollectionContactService.deleteByOrderId(orderId);
//					for (CollectionContact collectionContact : collection_contact) {
//						try {
//							if (null != collectionContact.getContact_details()
//									&& !collectionContact.getContact_details().isEmpty()) {
//								List<ContactDetails> contactDetailsList = collectionContact.getContact_details();
//								for (ContactDetails contactDetails : contactDetailsList) {
//									String text = JSON.toJSONString(contactDetails);
//									BwXlCollectionContact bwXlCollectionContact = JSON.parseObject(text,BwXlCollectionContact.class);
//									bwXlCollectionContact.setBeginDate(collectionContact.getBegin_date());
//									bwXlCollectionContact.setContactName(collectionContact.getContact_name());
//									bwXlCollectionContact.setEndDate(collectionContact.getEnd_date());
//									bwXlCollectionContact.setTotalAmount(collectionContact.getTotal_amount());
//									bwXlCollectionContact.setTotalCount(collectionContact.getTotal_count());
//									bwXlCollectionContact.setOrderId(orderId);
//									bwXlCollectionContact.setCreateTime(new Date());
//									bwXlCollectionContact.setUpdateTime(new Date());
//									bwXlCollectionContactService.save(bwXlCollectionContact);
//								}
//							}
//						} catch (Exception e) {
//							logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_collection_contact表数据异常**********************");
//						}
//					}
//				}
//			} else {
//				logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空**********************");
//			}
//		} catch (Exception e) {
//			logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//
//		// 无list 无嵌套
//		logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪ContactList**********************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					List<ContactList> contact_list = report.getContact_list();
//					bwXlContactListService.deleteByOrderId(orderId);
//					for (ContactList contactList : contact_list) {
//						try {
//							String text = JSON.toJSONString(contactList);
//							BwXlContactList bwXlContactList = JSON.parseObject(text, BwXlContactList.class);
//							if (null != bwXlContactList) {
//								bwXlContactList.setOrderId(orderId);
//								bwXlContactList.setCreateTime(new Date());
//								bwXlContactList.setUpdateTime(new Date());
//								bwXlContactListService.save(bwXlContactList);
//							}
//						} catch (Exception e) {
//							logger.info("***【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_contact_list表数据异常***");
//						}
//					}
//				}
//			} else {
//				logger.info("***【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空***");
//			}
//		} catch (Exception e) {
//			logger.info("***【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//
//		// 有list嵌套
//		logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪DeliverAddress*******************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					List<DeliverAddress> deliver_address = report.getDeliver_address();
//					BwXlDeliverAddress bwXlDeliverAddress = new BwXlDeliverAddress();
//					bwXlDeliverAddressService.deleteByOrderId(orderId);
//					for (DeliverAddress deliverAddress : deliver_address) {
//						try {
//							bwXlDeliverAddress = new BwXlDeliverAddress();
//							bwXlDeliverAddress.setOrderId(orderId);
//							bwXlDeliverAddress.setAddress(deliverAddress.getAddress());
//							bwXlDeliverAddress.setLng(deliverAddress.getLng());
//							bwXlDeliverAddress.setLat(deliverAddress.getLat());
//							bwXlDeliverAddress.setPredictAddrType(deliverAddress.getPredict_addr_type());
//							bwXlDeliverAddress.setBeginDate(deliverAddress.getBegin_date());
//							bwXlDeliverAddress.setEndDate(deliverAddress.getEnd_date());
//							bwXlDeliverAddress.setTotalAmount(deliverAddress.getTotal_amount());
//							bwXlDeliverAddress.setTotalCount(deliverAddress.getTotal_count());
//							bwXlDeliverAddress.setCreateTime(new Date());
//							List<Object> receiver = deliverAddress.getReceiver();
//							if (receiver != null) {
//								bwXlDeliverAddress.setReceiver(JSON.toJSONString(receiver));
//							}
//							bwXlDeliverAddress.setCount(deliverAddress.getCount());
//							bwXlDeliverAddress.setAmount(deliverAddress.getAmount());
//							bwXlDeliverAddress.setName(deliverAddress.getName());
//							List<String> phoneNumList = deliverAddress.getPhone_num_list();
//							if (phoneNumList != null) {
//								bwXlDeliverAddress.setPhoneNumList(JSON.toJSONString(phoneNumList));
//							}
//							
//							bwXlDeliverAddressService.save(bwXlDeliverAddress);
//						} catch (Exception e) {
//							logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_deliver_address表数据异常*******************");
//						}
//					}
//				}
//			} else {
//				logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空*******************");
//			}
//		} catch (Exception e) {
//			logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//
//		// 有list嵌套
//		logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪EbusinessExpense*******************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					List<EbusinessExpense> ebusiness_expense = report.getEbusiness_expense();
//					BwXlEbusinessExpense bwXlEbusinessExpense = new BwXlEbusinessExpense();
//					bwXlEbusinessExpenseService.deleteByOrderId(orderId);
//					for (EbusinessExpense ebusinessExpense : ebusiness_expense) {
//						try {
//							bwXlEbusinessExpense = new BwXlEbusinessExpense();
//							bwXlEbusinessExpense.setOrderId(orderId);
//							bwXlEbusinessExpense.setTransMth(ebusinessExpense.getTrans_mth());
//							bwXlEbusinessExpense.setAllAmount(ebusinessExpense.getAll_amount());
//							bwXlEbusinessExpense.setAllCount(ebusinessExpense.getAll_count());
//							List<String> all_category = ebusinessExpense.getAll_category();
//							bwXlEbusinessExpense.setCreateTime(new Date());
//							bwXlEbusinessExpense.setUpdateTime(new Date());
//							if (all_category != null) {
//								bwXlEbusinessExpense.setAllCategory(JSON.toJSONString(all_category));
//							}
//							bwXlEbusinessExpenseService.save(bwXlEbusinessExpense);
//						} catch (Exception e) {
//							logger.info("****************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_ebusiness_expense表数据异常*******************");
//						}
//					}
//				}
//			} else {
//				logger.info("***【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空*******************");
//			}
//		} catch (Exception e) {
//			logger.info("***【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//
//		// 有list嵌套，多个单参数
//		logger.info("***【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪MainService*******************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					List<MainService> main_service = report.getMain_service();
//					bwXlMainServiceService.deleteByOrderId(orderId);
//					for (MainService mainService : main_service) {
//						try {
//							if (null != mainService.getService_details()
//									&& !mainService.getService_details().isEmpty()) {
//								List<Service_details> service_detailsList = mainService.getService_details();
//								for (Service_details service_details : service_detailsList) {
//									String text = JSON.toJSONString(service_details);
//									BwXlMainService bwXlMainService = JSON.parseObject(text, BwXlMainService.class);
//									bwXlMainService.setCompanyName(mainService.getCompany_name());
//									bwXlMainService.setCompanyType(mainService.getCompany_type());
//									bwXlMainService.setTotalServiceCnt(mainService.getTotal_service_cnt());
//									bwXlMainService.setOrderId(orderId);
//									bwXlMainService.setCreateTime(new Date());
//									bwXlMainService.setUpdateTime(new Date());
//									bwXlMainServiceService.save(bwXlMainService);
//								}
//							}
//
//						} catch (Exception e) {
//							logger.info("***【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_main_service表数据异常*******************");
//						}
//					}
//				}
//			} else {
//				logger.info("***【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空*******************");
//			}
//		} catch (Exception e) {
//			logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//
//		logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪Report*******************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					Report2 report2 = report.getReport();
//					bwXlReport2Service.deleteByOrderId(orderId);
//					try {
//						String text = JSON.toJSONString(report2);
//						BwXlReport bwXlReport = JSON.parseObject(text, BwXlReport.class);
//						if (null != bwXlReport) {
//							bwXlReport.setOrderId(orderId);
//							bwXlReport.setCreateTime(new Date());
//							bwXlReport.setReportTime(report2.getUpdate_time());
//							bwXlReport2Service.save(bwXlReport);
//						}
//
//					} catch (Exception e) {
//						logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_report表数据异常*******************");
//					}
//				}
//			} else {
//				logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空*******************");
//			}
//		} catch (Exception e) {
//			logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//
//		// 无list 无嵌套
//		logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪TripInfo*******************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					List<TripInfo> tripInfo1 = report.getTrip_info();
//					bwXlTripInfoService.deleteByOrderId(orderId);
//					for (TripInfo tripInfo : tripInfo1) {
//						try {
//							String text = JSON.toJSONString(tripInfo);
//							BwXlTripInfo bwXlTripInfo = JSON.parseObject(text, BwXlTripInfo.class);
//							if (null != bwXlTripInfo) {
//								bwXlTripInfo.setOrderId(orderId);
//								bwXlTripInfo.setCreateTime(new Date());
//								bwXlTripInfo.setUpdateTime(new Date());
//								bwXlTripInfoService.save(bwXlTripInfo);
//							}
//
//						} catch (Exception e) {
//							logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_trip_info表数据异常*******************");
//						}
//					}
//				}
//			} else {
//				logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空*******************");
//			}
//		} catch (Exception e) {
//			logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//
//		logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪CheckBlackInfo*******************");
//		try {
//			if (null != operator) {
//				Report report = operator.getReport();
//				if (null != report) {
//					bwXlCheckBlackInfoService.deleteByOrderId(orderId);
//					UserInfoCheck userInfoCheck = report.getUser_info_check();
//					CheckBlackInfo checkBlackInfo = userInfoCheck.getCheck_black_info();
//					CheckSearchInfo checkSearchInfo = userInfoCheck.getCheck_search_info();
//					try {
//						String text = JSON.toJSONString(checkBlackInfo);
//						BwXlCheckBlackInfo bwXlCheckBlackInfo = JSON.parseObject(text, BwXlCheckBlackInfo.class);
//						if (null != bwXlCheckBlackInfo) {
//							bwXlCheckBlackInfo.setOrderId(orderId);
//							bwXlCheckBlackInfo.setCreateTime(new Date());
//							bwXlCheckBlackInfo.setUpdateTime(new Date());
//							bwXlCheckBlackInfoService.save(bwXlCheckBlackInfo);
//						}
//
//					} catch (Exception e) {
//						logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_check_black_info表数据异常*******************");
//					}
//					bwXlCheckSearchInfoService.deleteByOrderId(orderId);
//					try {
//						String text1 = JSON.toJSONString(checkSearchInfo);
//						BwXlCheckSearchInfo bwXlCheckSearchInfo = JSON.parseObject(text1, BwXlCheckSearchInfo.class);
//						if (null != bwXlCheckSearchInfo) {
//							bwXlCheckSearchInfo.setRegisterOrgCnt(Long.valueOf(checkSearchInfo.getRegister_org_cnt()));
//							bwXlCheckSearchInfo.setSearchedOrgCnt(checkSearchInfo.getSearched_org_cnt());
//							List<String> arised_open_web = checkSearchInfo.getArised_open_web();
//							if (arised_open_web != null) {
//								bwXlCheckSearchInfo.setArisedOpenWeb(JSON.toJSONString(arised_open_web));
//							}
//							List<String> idcard_with_other_names = checkSearchInfo.getIdcard_with_other_names();
//							if (idcard_with_other_names != null) {
//								bwXlCheckSearchInfo.setIdcardWithOtherNames(JSON.toJSONString(idcard_with_other_names));
//							}
//							List<String> idcard_with_other_phones = checkSearchInfo.getIdcard_with_other_names();
//							if (idcard_with_other_phones != null) {
//								bwXlCheckSearchInfo.setIdcardWithOtherPhones(JSON.toJSONString(idcard_with_other_phones));
//							}
//							List<String> phone_with_other_idcards = checkSearchInfo.getPhone_with_other_idcards();
//							if (phone_with_other_idcards != null) {
//								bwXlCheckSearchInfo.setPhoneWithOtherIdcards(JSON.toJSONString(phone_with_other_idcards));
//							}
//							List<String> phone_with_other_names = checkSearchInfo.getPhone_with_other_names();
//							if (phone_with_other_names != null) {
//								bwXlCheckSearchInfo.setPhoneWithOtherNames(JSON.toJSONString(phone_with_other_names));
//							}
//							List<String> register_org_type = checkSearchInfo.getRegister_org_type();
//							if (register_org_type != null) {
//								bwXlCheckSearchInfo.setRegisterOrgType(JSON.toJSONString(register_org_type));
//							}
//							List<String> searched_org_type = checkSearchInfo.getSearched_org_type();
//							if (searched_org_type != null) {
//								bwXlCheckSearchInfo.setSearchedOrgType(JSON.toJSONString(searched_org_type));
//							}
//							bwXlCheckSearchInfo.setOrderId(orderId);
//							bwXlCheckSearchInfo.setCreateTime(new Date());
//							bwXlCheckSearchInfo.setUpdateTime(new Date());
//							bwXlCheckSearchInfoService.save(bwXlCheckSearchInfo);
//						}
//					} catch (Exception e) {
//						logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_check_search_info表数据异常*******************");
//					}
//
//				}
//			} else {
//				logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空*******************");
//			}
//		} catch (Exception e) {
//			logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//
//		// BasicInfo 无list嵌套
//		logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】开始异步处理新浪Basic*******************");
//		logger.info("*************************************█▓▓█▒██▓▓▓██▒█▓▓█**************************************");
//		logger.info("************************************█▓▒▒▓█▓▓▓▓▓▓▓█▓▒▒▓█*************************************");
//		logger.info("************************************█▓▒▒▓▓▓▓▓▓▓▓▓▓▓▒▒▓█*************************************");
//		logger.info("*************************************█▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓█**************************************");
//		logger.info("**************************************█▓▓▓▓▓▓▓▓▓▓▓▓▓█***************************************");
//		logger.info("**************************************█▓▓█▓▓▓▓▓▓█▓▓▓█***************************************");
//		logger.info("**************************************█▓▓██▓▓▓▓▓██▓▓█***************************************");
//		logger.info("*************************************█▓▓▓▓▒▒█▓█▒▒▓▓▓▓█**************************************");
//		logger.info("************************************█▓▓▒▒▓▒▒███▒▒▓▒▒▓▓█*************************************");
//		logger.info("************************************█▓▓▒▒▓▒▒▒█▒▒▒▓▒▒▓▓█*************************************");
//		logger.info("************************************█▓▓▓▓▓▓▒▒▒▒▒▓▓▓▓▓▓█*************************************");
//		logger.info("*************************************█▓▓▓▓▓▓███▓▓▓▓▓▓█**************************************");
//		logger.info("**************************************█▓▓▓▓▓▓▓▓▓▓▓▓▓█***************************************");
//		logger.info("*************************************█▓▓▓▓▒▒▒▒▒▒▒▓▓▓▓█**************************************");
//		logger.info("************************************█▓▓▓▓▒▒▒▒▒▒▒▒▒▓▓▓▓█*************************************");
//		logger.info("***********************************█▓▓▓█▓▒▒▒▒▒▒▒▒▒▓█▓▓▓█************************************");
//		logger.info("*********************************██▓▓▓█▓▒▒▒██▒██▒▒▒▓█▓▓▓██**********************************");
//		logger.info("********************************█▓▓▓▓█▓▒▒▒▒█▓█▓█▒▒▒▓█▓▓▓▓█**********************************");
//		logger.info("*******************************█▓██▓▓█▓▒▒▒▒█▒▓▒█▒▒▒▒▓█▓▓██▓█********************************");
//		logger.info("*******************************█▓▓▓▓█▓▓▒▒▒▒█▓▒▓█▒▒▒▒▓▓█▓▓▓▓█********************************");
//		logger.info("********************************█▓▓▓█▓▓▒▒▒▒▒█▒█▒▒▒▒▒▓▓█▓▓▓█*********************************");
//		logger.info("*********************************████▓▓▒▒▒▒▒▒█▒▒▒▒▒▒▓▓████**********************************");
//		logger.info("************************************█▓▓▓▒▒▒▒▒▒▒▒▒▒▒▓▓▓█*************************************");
//		logger.info("*************************************█▓▓▓▒▒▒▒▒▒▒▒▒▓▓▓█**************************************");
//		logger.info("*************************************█▓▓▓▓▒▒▒▒▒▒▒▓▓▓▓█**************************************");
//		logger.info("**************************************█▓▓▓▓▓█▓█▓▓▓▓▓█***************************************");
//		logger.info("***************************************█▓▓▓▓▓█▓▓▓▓▓█****************************************");
//		logger.info("************************************████▓▓▓▓▓█▓▓▓▓▓████*************************************");
//		logger.info("***********************************█▓▓▓▓▓▓▓▓▓█▓▓▓▓▓▓▓▓▓█************************************");
//		try {
//			if (null != operator) {
//				BasicInfo basicInfo = operator.getBasicInfo();
//				bwXlBasicService.deleteByOrderId(orderId);
//				if (null != basicInfo) {
//					String text = JSON.toJSONString(basicInfo.getBasic());
//					BwXlBasic bwXlBasic = JSON.parseObject(text, BwXlBasic.class);
//					if (null != bwXlBasic) {
//						bwXlBasic.setOrderId(orderId);
//						bwXlBasic.setCreateTime(new Date());
//						bwXlBasic.setCreateTime(new Date());
//						bwXlBasicService.save(bwXlBasic);
//					}
//					List<Calls> calls = basicInfo.getCalls();
//					bwXlCallsService.deleteByOrderId(orderId);
//					for (Calls callsList : calls) {
//						try {
//							String text1 = JSON.toJSONString(callsList);
//							BwXlCalls bwXlCalls = JSON.parseObject(text1, BwXlCalls.class);
//							if (null != bwXlCalls) {
//								bwXlCalls.setOrderId(orderId);
//								bwXlCalls.setCreateTime(new Date());
//								bwXlCallsService.save(bwXlCalls);
//							}
//						} catch (Exception e) {
//							logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_basic表数据异常*******************");
//						}
//					}
//					List<Nets> nets = basicInfo.getNets();
//					bwXlNetsService.deleteByOrderId(orderId);
//					for (Nets netsList : nets) {
//						try {
//							String text2 = JSON.toJSONString(netsList);
//							BwXlNets bwXlNets = JSON.parseObject(text2, BwXlNets.class);
//							if (null != bwXlNets) {
//								bwXlNets.setOrderId(orderId);
//								bwXlNets.setCreateTime(new Date());
//								bwXlNetsService.save(bwXlNets);
//
//							}
//						} catch (Exception e) {
//							logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_nets表数据异常*******************");
//						}
//					}
//					List<Smses> smses = basicInfo.getSmses();
//					bwXlSmsesService.deleteByOrderId(orderId);
//					for (Smses smsesList : smses) {
//						try {
//							String text3 = JSON.toJSONString(smsesList);
//							BwXlSmses bwXlSmses = JSON.parseObject(text3, BwXlSmses.class);
//							if (null != bwXlSmses) {
//								bwXlSmses.setOrderId(orderId);
//								bwXlSmses.setCreateTime(new Date());
//								bwXlSmsesService.save(bwXlSmses);
//							}
//						} catch (Exception e) {
//							logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_smses表数据异常*******************");
//						}
//					}
//					List<Transactions> transactions = basicInfo.getTransactions();
//					bwXlTransactionsService.deleteByOrderId(orderId);
//					for (Transactions transactionsList : transactions) {
//						try {
//							String text4 = JSON.toJSONString(transactionsList);
//							BwXlTransactions bwXlTransactions = JSON.parseObject(text4, BwXlTransactions.class);
//							if (null != bwXlTransactions) {
//								bwXlTransactions.setOrderId(orderId);
//								bwXlTransactions.setCreateTime(new Date());
//								bwXlTransactionsService.save(bwXlTransactions);
//							}
//						} catch (Exception e) {
//							logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】添加bw_xl_transactions表数据异常*******************",e);
//						}
//					}
//				}
//			} else {
//				logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】运营商数据为空*******************");
//			}
//		} catch (Exception e) {
//			logger.info("*******************【AsyncSinaTaskServiceImpl.addOperator】异步处理新浪运营商数据异常：", e);
//		}
//		bwOrder.setStatusId(2L);
//		bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//		bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//		bwOrderService.updateBwOrder(bwOrder);
//		try {
//			String res = bqsCheckService.doBqsCheck(sessionId, orderId + "");
//			logger.info(sessionId + "白骑士校验>>orderId" + orderId + ">>>res" + ("0".equals(res) ? "成功" : "失败"));
//		} catch (Exception e) {
//			logger.error(sessionId + "调用白骑士校验异常" + e);
//		}
//		HashMap<String, String> hm = new HashMap<>();
//		hm.put("channelId", bwOrder.getChannel() + "");
//		hm.put("orderId", String.valueOf(bwOrder.getId()));
//		hm.put("orderStatus", 2 + "");
//		hm.put("result", "");
//		String hmData = JSON.toJSONString(hm);
//		RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//
//		BwOrderRong bwOrderRong = new BwOrderRong();
//		bwOrderRong.setOrderId(orderId);
//		bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//
//		// 放入redis
//		SystemAuditDto systemAuditDto = new SystemAuditDto();
//		systemAuditDto.setIncludeAddressBook(0);
//		systemAuditDto.setOrderId(orderId);
//		systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//		systemAuditDto.setName(borrower.getName());
//		systemAuditDto.setPhone(borrower.getPhone());
//		systemAuditDto.setIdCard(borrower.getIdCard());
//		systemAuditDto.setChannel(bwOrder.getChannel());
//		systemAuditDto.setThirdOrderId(bwOrderRong.getThirdOrderNo());
//		systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//		RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//		logger.info(sessionId + ">>> 修改订单状态，并放入redis");
//		// 更改订单进行时间
//		BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//		bwOrderProcessRecord.setSubmitTime(new Date());
//		bwOrderProcessRecord.setOrderId(bwOrder.getId());
//		bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//		logger.info(sessionId + ">>> 更改订单进行时间");
//		logger.info(sessionId + ">>>结束处理新浪运营商数据:orderId=" + orderId);
//		
//		
//		
//	
//	
//		BasicInfo basicInfo = operator.getBasicInfo(); // 用户基本信息
//		Report report = operator.getReport();		
//		
//		List<Calls> callList = null;
//		String cell_operator = null;
//		String cell_loc = null;
//		if (null != report) {
//			List<CellBehavior> cell_behaviors = report.getCell_behavior();
//			for (CellBehavior cell_behavior : cell_behaviors) {
//				List<Behavior> behaviors = cell_behavior.getBehavior();
//				for (Behavior behavior : behaviors) {
//					cell_operator = behavior.getCell_operator(); // 号码类型
//					cell_loc = behavior.getCell_loc();			 // 归属地
//				}
//			}
//		}
//
//		if (null != basicInfo) {
//			callList = basicInfo.getCalls();	//获取通话记录
//			logger.info(sessionId + ":新浪>>>【AsyncSinaTaskImpl.addOperator】开始添加运营商基础信息到bw_third_operate_basic表中");
//			BwThirdOperateBasic sinaBasic = new BwThirdOperateBasic();
//			sinaBasic.setOrderId(orderId);
//			// 基本的运营商数据
//			BwThirdOperateBasic bwThirdOperateBasic = bwThirdOperateBasicService.findByAttr(sinaBasic);
//			Basic basic = basicInfo.getBasic();
//			if (bwThirdOperateBasic == null) {
//				bwThirdOperateBasic = new BwThirdOperateBasic();
//				bwThirdOperateBasic.setOrderId(orderId);
//				bwThirdOperateBasic.setChannel(Integer.valueOf(SinaConstant.CHANNELID));
//				bwThirdOperateBasic.setUserSource(cell_operator);
//				bwThirdOperateBasic.setIdCard(basic.getIdcard());
//				bwThirdOperateBasic.setAddr(cell_loc);
//				bwThirdOperateBasic.setRealName(basic.getReal_name());
//				bwThirdOperateBasic.setPhone(basic.getCell_phone());
//				bwThirdOperateBasic.setRegTime(DateUtil.stringToDate(basic.getReg_time(), DateUtil.yyyy_MM_dd_HHmmss));
//				bwThirdOperateBasic.setCreateTime(new Date());
//				bwThirdOperateBasic.setUpdateTime(new Date());
//				bwThirdOperateBasicService.save(bwThirdOperateBasic);
//			} else {
//				bwThirdOperateBasic.setOrderId(orderId);
//				bwThirdOperateBasic.setChannel(Integer.valueOf(SinaConstant.CHANNELID));
//				bwThirdOperateBasic.setUserSource(cell_operator);
//				bwThirdOperateBasic.setIdCard(basic.getIdcard());
//				bwThirdOperateBasic.setAddr(cell_loc);
//				bwThirdOperateBasic.setRealName(basic.getReal_name());
//				bwThirdOperateBasic.setPhone(basic.getCell_phone());
//				bwThirdOperateBasic.setRegTime(DateUtil.stringToDate(basic.getReg_time(), DateUtil.yyyy_MM_dd_HHmmss));
//				bwThirdOperateBasic.setUpdateTime(new Date());
//				bwThirdOperateBasicService.update(bwThirdOperateBasic);
//			}
//			logger.info(sessionId + ":新浪>>>结束处理运营商基础信息");
//		}
//
//		//处理通话记录
//		if (CollectionUtils.isNotEmpty(callList)) {
//			logger.info(sessionId + ":新浪>>>开始更新通话记录......");
//			SimpleDateFormat sdf_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date callDate = bwThirdOperateVoiceService.getCallTimeByborrowerIdEs(orderId);
//			for (Calls call : callList) {
//				Date jsonCallDate = sdf_hms.parse(call.getStart_time());
//				try {
//					if (null == callDate || jsonCallDate.after(callDate)) { // 通话记录采取最新追加的方式
//						BwThirdOperateVoice bwThirdOperateVoice = new BwThirdOperateVoice();
//						bwThirdOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
//						bwThirdOperateVoice.setOrderId(orderId);
//						// 检验日期格式
//						String callTime = sdf_hms.format(sdf_hms.parse(call.getStart_time()));
//						bwThirdOperateVoice.setCallTime(callTime); // 通话时间
//						bwThirdOperateVoice.setCallType("主叫".equals(call.getInit_type()) ? 1 : 2); // 呼叫类型
//						bwThirdOperateVoice.setReceivePhone(call.getOther_cell_phone()); // 对方号码
//						bwThirdOperateVoice.setTradeAddr(CommUtils.isNull(call.getPlace()) ? "" : call.getPlace()); // 通话地点
//						bwThirdOperateVoice.setTradeTime(call.getUse_time()+""); // 通话时长
//						bwThirdOperateVoice.setTradeType("本地通话".equals(call.getCall_type()) ? 1 : 2); // 通信类型 1.本地通话,国内长途
//						bwThirdOperateVoice.setChannel(Integer.valueOf(SinaConstant.CHANNELID));
//						bwThirdOperateVoiceService.save(bwThirdOperateVoice);
//					}
//				} catch (Exception e) {
//					logger.error(sessionId + ":新浪>>>保存通话记录异常,忽略此条通话记录...", e);
//					continue;
//				}
//			}
//			logger.info(sessionId + ":新浪>>>更新通话记录结束......");
//		}else{
//			logger.info(sessionId + ":新浪>>>通话记录为空......");
//		}
//	}
//}
