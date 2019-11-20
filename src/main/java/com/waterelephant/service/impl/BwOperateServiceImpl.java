package com.waterelephant.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beadwallet.service.entity.request.RongOperateReqData;
import com.beadwallet.service.entity.request.RongOperateSubMsgCode;
import com.beadwallet.service.entity.response.OperateRsp;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entity.response.RongOperateLogin;
import com.beadwallet.service.entity.response.RongOperateLoginRule;
import com.beadwallet.service.entity.response.RongOperateSubmitRsp;
import com.beadwallet.service.entity.response.RongOprerateMsgCodeRsqData;
import com.beadwallet.service.entiyt.middle.Next;
import com.beadwallet.service.entiyt.middle.Param;
import com.beadwallet.service.entiyt.middle.RefresParam;
import com.beadwallet.service.entiyt.middle.RongOperateDataList;
import com.beadwallet.service.entiyt.middle.RongOperateTelData;
import com.beadwallet.service.entiyt.middle.TelData;
import com.beadwallet.service.rong360.service.BeadWalletRongCarrierService;
import com.beadwallet.service.serve.BeadWalletRongOperateService;
import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.dto.OperateCheck;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOperateBasicService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.IBwOperateService;
import com.waterelephant.utils.MyDateUtils;

//import com.beadwallet.service.serve.BeadWalletOperatorService;

@Service
public class BwOperateServiceImpl extends BaseService<OperateCheck, Long> implements IBwOperateService {
	
	private Logger logger = LoggerFactory.getLogger(BwOperateServiceImpl.class);

	@Autowired
	private BwOperateBasicService bwOperateBasicService;

	@Autowired
	private BwOperateVoiceService bwOperateVoiceService;

	@Autowired
	private BwBorrowerService bwBorrowerService;

	// @Override
	// public Response<Object> task(MoxieReqData moxieReqData) {
	// return BeadWalletOperatorService.createTask(moxieReqData);
	// }
	//
	// @Override
	// public Response<Object> getStatus(String taskId) {
	// return BeadWalletOperatorService.getTaskStatus(taskId);
	// }
	//
	// @Override
	// public Response<Object> inputCode(String taskId, String code) {
	// return BeadWalletOperatorService.inputCode(taskId, code);
	// }

	// @Override
	// public Response<Object> operate(String mobile, String password, String name, String idCard, String userId) {
	// Response<Object> result = new Response<>();
	// HashMap<String, Object> map = new HashMap<>();
	// // 创建任务
	// MoxieReqData moxieReqData = new MoxieReqData();
	// moxieReqData.setMobile(mobile);
	// moxieReqData.setPassword(password);
	// moxieReqData.setName(name);
	// moxieReqData.setIdCard(idCard);
	// moxieReqData.setUserId(Long.parseLong(userId));
	// Response<Object> taskRes = this.task(moxieReqData);
	// if (taskRes.getRequestCode().equals("200")) {
	// // 获取taskid
	// JSONObject taskJson = JSONObject.fromObject(taskRes.getObj());
	// String taskId = taskJson.getString("task_id");
	// System.out.println("==================taskId：" + taskId);
	// // 轮询状态
	// boolean temp = true;
	// while (temp) {
	// Response<Object> statusRes = this.getStatus(taskId);
	// JSONObject statusJson = JSONObject.fromObject(statusRes.getObj());
	// String phaseStatus = statusJson.getString("phase_status");
	// String description = statusJson.getString("description");
	// if (statusRes.getRequestCode().equals("200")) {
	// if (phaseStatus.equals("WAIT_CODE")) {
	// // 输入验证码
	// result.setRequestCode("200");
	// result.setRequestMsg(taskRes.getRequestMsg());
	// map.put("taskId", taskId);
	// map.put("status", "WAIT_CODE");
	// result.setObj(map);
	// break;
	// }
	// if (phaseStatus.equals("DONE_FAIL")) {
	// // 失败
	// result.setRequestCode("103");
	// result.setRequestMsg(description);
	// break;
	// }
	// if (phaseStatus.equals("DONE_TIMEOUT")) {
	// // 超时
	// result.setRequestCode("104");
	// result.setRequestMsg("登录超时,请稍后再试");
	// }
	// if (phaseStatus.equals("DONE_SUCC")) {
	// // 不需要输入验证码
	// result.setRequestCode("200");
	// result.setRequestMsg(description);
	// break;
	// }
	//
	// } else {
	// // 轮询创建任务状态异常信息
	// result.setRequestCode("102");
	// result.setRequestMsg(statusRes.getRequestMsg());
	// }
	// }
	// } else {
	// // 创建任务异常信息
	// result.setRequestCode("101");
	// result.setRequestMsg(taskRes.getRequestMsg());
	// }
	// return result;
	// }

	@Override
	public Response<Object> getLoginRule(String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		Response<Object> result = new Response<>();
		Response<RongOperateLoginRule> response = BeadWalletRongOperateService.getLoginRule(phone);
		if (response.getRequestCode().equals("200")) {
			Next next = response.getObj().getNext();
			if (CommUtils.isNull(next)) {
				String method = next.getMethod();
				map.put("method", method);
			}
			List<Param> params = next.getParam();
			if (params.size() > 0) {
				for (Param param : params) {
					if (param.getKey().equals("cellphone")) {
						map.put("param1", "phone");
					}
					if (param.getKey().equals("password")) {
						map.put("param2", "pwd");
					}
				}
			}
			result.setRequestCode("000");
			result.setRequestMsg("成功");
			result.setObj(map);
		} else {
			result.setRequestCode("101");
			result.setRequestMsg(response.getRequestMsg());
		}
		return result;
	}

	@Override
	public Response<Object> login(RongOperateReqData rongOperateDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		Response<Object> result = new Response<>();
		Response<RongOperateLogin> response = BeadWalletRongOperateService.login(rongOperateDto);

		if (response.getRequestCode().equals("200")) {
			if (CommUtils.isNull(response.getObj())) {
				// System.out.println("运营商登录成功，obj为null");
				result.setRequestCode("000");
				result.setRequestMsg("成功");
			} else {
				Next next = response.getObj().getNext();
				if (!CommUtils.isNull(next)) {
					map.put("method", next.getMethod());
					List<Param> params = next.getParam();
					if (params.size() == 1) {
						for (Param param : params) {
							if (param.getKey().equals("message_code")) {
								map.put("param1", "msgCode");
								map.put("status", "1");
							}
							if (param.getKey().equals("pic_code")) {
								map.put("param2", "picCode");
								map.put("picCode", param.getValue());
								map.put("status", "2");
							}
							// refreshParam
							List<RefresParam> rParams = param.getRefresh_param();
							for (RefresParam refresParam : rParams) {
								if (refresParam.getKey().equals("piccode_type")) {
									map.put("piccodeType", refresParam.getValue());
								}
								if (refresParam.getKey().equals("messagecode_type")) {
									map.put("messagecodeType", refresParam.getValue());
								}

							}
						}
						result.setRequestCode("000");
						result.setRequestMsg("成功");
						result.setObj(map);
					}

					if (params.size() == 2) {
						for (Param param : params) {
							if (param.getKey().equals("message_code")) {
								map.put("param1", "msgCode");
								map.put("status", "1");
							}
							if (param.getKey().equals("pic_code")) {
								map.put("param2", "picCode");
								map.put("picCode", param.getValue());
								map.put("status", "2");
							}
							// refreshParam
							List<RefresParam> rParams = param.getRefresh_param();
							for (RefresParam refresParam : rParams) {
								if (refresParam.getKey().equals("piccode_type")) {
									map.put("piccodeType", refresParam.getValue());
								}
								if (refresParam.getKey().equals("messagecode_type")) {
									map.put("messagecodeType", refresParam.getValue());
								}

							}
						}
						result.setRequestCode("000");
						result.setRequestMsg("成功");
						result.setObj(map);
					}
					if (params.size() == 3) {
						for (Param param : params) {
							if (param.getKey().equals("message_code")) {
								map.put("param1", "msgCode");
								map.put("status", "1");
							}
							if (param.getKey().equals("pic_code")) {
								map.put("param2", "picCode");
								map.put("picCode", param.getValue());
								map.put("status", "2");
							}
							if (param.getKey().equals("id_card")) {
								map.put("param3", "id_card");
							}
							if (param.getKey().equals("real_name")) {
								map.put("param4", "real_name");
							}
							// refreshParam
							List<RefresParam> rParams = param.getRefresh_param();
							if (!CommUtils.isNull(rParams)) {
								for (RefresParam refresParam : rParams) {
									if (refresParam.getKey().equals("piccode_type")) {
										map.put("piccodeType", refresParam.getValue());
									}
									if (refresParam.getKey().equals("messagecode_type")) {
										map.put("messagecodeType", refresParam.getValue());
									}

								}
							}

						}
						result.setRequestCode("000");
						result.setRequestMsg("成功");
						result.setObj(map);
					}
				}
			}
		} else {
			result.setRequestCode("101");
			result.setRequestMsg(response.getRequestMsg());
		}
		return result;
	}

	@Override
	public Response<Object> submitMsgCode(String phone, String code, String name, String idCard, String userId,
			String picCode) {
		Response<Object> result = new Response<>();
		RongOperateSubMsgCode rMsgCode = new RongOperateSubMsgCode();
		rMsgCode.setPhone(phone);
		rMsgCode.setMsgCode(code);
		rMsgCode.setName(name);
		rMsgCode.setIdCard(idCard);
		rMsgCode.setUserId(userId);
		if (!CommUtils.isNull(picCode)) {
			rMsgCode.setPicCode(picCode);
		}
		Response<RongOperateSubmitRsp> response = BeadWalletRongOperateService.submitMsgCode(rMsgCode);
		if (response.getRequestCode().equals("200")) {
			result.setRequestCode("000");
			result.setRequestMsg("成功");
			result.setObj(response.getObj());
		} else {
			result.setRequestCode("101");
			result.setRequestMsg(response.getRequestMsg());
		}
		return result;
	}

	@Override
	public Response<Object> refreshMsgCode(String phone, String messageCodeType, String userId) {
		return BeadWalletRongOperateService.refreshMsgCode(phone, messageCodeType, userId);
	}

	@Override
	public Response<Object> refreshPicCode(String phone, String picCodeType, String userId) {
		return BeadWalletRongOperateService.refreshPicCode(phone, picCodeType, userId);
	}

	@Override
	public Response<Object> getData(String userId) {
		SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		Response<Object> result = new Response<>();
		Response<OperateRsp> response = BeadWalletRongOperateService.getData(userId);
		// Response<OperateRsp> response = BeadWalletRongCarrierService.getData(search_id);
		if (response.getRequestCode().equals("200")) {
			// 根据手机号查询借款人id
			BwBorrower borrower = new BwBorrower();
			borrower.setPhone(userId);
			borrower.setFlag(1);
			borrower.setState(1);
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			if (CommUtils.isNull(borrower)) {
				// System.out.println("根据该手机号查询到的借款人为空");
				result.setRequestCode("200");
				result.setRequestMsg("成功");
				return result;
			}
			// 保存至数据库
			OperateRsp operateRsp = response.getObj();
			List<RongOperateDataList> dataLists = operateRsp.getData().getData_list();
			if (dataLists.size() > 0) {
				for (RongOperateDataList rongOperateDataList : dataLists) {
					// 根据手机号查询是否已存在 如果存在则更新 不存在就添加
					String phone = rongOperateDataList.getUserdata().getPhone();
					// System.out.println("借款人的手机号是：" + phone);
					BwOperateBasic bwOperateBasic = bwOperateBasicService
							.getBwOperateBasicByBorrowerId(borrower.getId());
					if (CommUtils.isNull(bwOperateBasic)) {
						// 添加基本信息
						try {
							bwOperateBasic = new BwOperateBasic();
							bwOperateBasic.setUserSource(rongOperateDataList.getUserdata().getUser_source());
							bwOperateBasic.setIdCard(rongOperateDataList.getUserdata().getId_card());
							bwOperateBasic.setAddr(rongOperateDataList.getUserdata().getAddr());
							bwOperateBasic.setPhone(rongOperateDataList.getUserdata().getPhone());
							bwOperateBasic.setPhoneRemain(rongOperateDataList.getUserdata().getPhone_remain());
							bwOperateBasic.setRealName(rongOperateDataList.getUserdata().getReal_name());
							bwOperateBasic.setRegTime(sdf.parse(rongOperateDataList.getUserdata().getReg_time()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						bwOperateBasic.setBorrowerId(borrower.getId());
						bwOperateBasicService.save(bwOperateBasic);
					} else {
						// 修改基本信息
						// System.out.println("根据手机号查询的id是：" + bwOperateBasic.getId());
						bwOperateBasic.setUserSource(rongOperateDataList.getUserdata().getUser_source());
						bwOperateBasic.setIdCard(rongOperateDataList.getUserdata().getId_card());
						bwOperateBasic.setAddr(rongOperateDataList.getUserdata().getAddr());
						bwOperateBasic.setPhone(rongOperateDataList.getUserdata().getPhone());
						bwOperateBasic.setPhoneRemain(rongOperateDataList.getUserdata().getPhone_remain());
						bwOperateBasic.setRealName(rongOperateDataList.getUserdata().getReal_name());
						try {
							bwOperateBasic.setRegTime(sdf.parse(rongOperateDataList.getUserdata().getReg_time()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						bwOperateBasic.setBorrowerId(borrower.getId());
						bwOperateBasicService.update(bwOperateBasic);
					}

					// 通话记录
					// 根据手机号查询通话记录
					List<RongOperateTelData> telDatas = rongOperateDataList.getTeldata();
					Date updateTime = null;
					try {
						updateTime = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (CommUtils.isNull(updateTime)) {
						for (RongOperateTelData rongOperateTelData : telDatas) {
							// 获取通话记录
							List<TelData> lists = rongOperateTelData.getTeldata();
							if (!CommUtils.isNull(lists)) {
								for (TelData telData : lists) {
									try {

										if (CommUtils.isNull(telData.getCall_time())) {
											continue;
										}
										if (CommUtils.isNull(telData.getTrade_time())) {
											continue;
										}
										if (CommUtils.isNull(telData.getCall_type())) {
											continue;
										}
										// 添加
										BwOperateVoice bwOperateVoice = new BwOperateVoice();
										bwOperateVoice.setUpdateTime(new Date());
										bwOperateVoice.setBorrower_id(borrower.getId());
										// 检验日期格式
										String callTime = null;
										try {
											callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
										} catch (ParseException e) {
											e.printStackTrace();
											// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
											continue;
										}
										if (!CommUtils.isNull(callTime)) {
											Date beginDate;
											try {
												beginDate = sdf_hms.parse(callTime);
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												continue;
											}
											int s = MyDateUtils.getMonthSpace(beginDate, now);
											if (s <= 3) {
												bwOperateVoice.setCall_time(callTime);
												bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
												bwOperateVoice.setReceive_phone(telData.getReceive_phone());
												bwOperateVoice.setTrade_addr(telData.getTrade_addr());
												bwOperateVoice.setTrade_time(telData.getTrade_time());
												bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
												bwOperateVoiceService.save(bwOperateVoice);
											}
										}

									} catch (Exception e) {
										continue;
									}
								}
							}
						}
					} else {
						Date callDate = updateTime;
						try {
							callDate = sdf_hms.parse(sdf_hms.format(callDate));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						for (RongOperateTelData rongOperateTelData : telDatas) {
							// 获取通话记录
							List<TelData> lists = rongOperateTelData.getTeldata();
							if (!CommUtils.isNull(lists)) {
								Date jsonCallData = null;
								for (TelData telData : lists) {
									try {
										if (CommUtils.isNull(telData.getCall_time())) {
											continue;
										}
										if (CommUtils.isNull(telData.getTrade_time())) {
											continue;
										}
										if (CommUtils.isNull(telData.getCall_type())) {
											continue;
										}

										try {
											jsonCallData = sdf_hms.parse(telData.getCall_time());
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										if (jsonCallData.after(callDate)) {
											BwOperateVoice bwOperateVoice = new BwOperateVoice();
											bwOperateVoice.setUpdateTime(new Date());
											bwOperateVoice.setBorrower_id(borrower.getId());
											// 检验日期格式
											String callTime = null;
											try {
												callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
											} catch (ParseException e) {
												e.printStackTrace();
												// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
												continue;
											}
											bwOperateVoice.setCall_time(callTime);
											bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
											bwOperateVoice.setReceive_phone(telData.getReceive_phone());
											bwOperateVoice.setTrade_addr(telData.getTrade_addr());
											bwOperateVoice.setTrade_time(telData.getTrade_time());
											bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
											bwOperateVoiceService.save(bwOperateVoice);
										}
									} catch (Exception e) {
										continue;
									}
								}
							}
						}
					}
				}

			}
			result.setRequestCode("000");
			result.setRequestMsg("成功");
		} else {
			result.setRequestCode("101");
			result.setRequestMsg(response.getRequestMsg());
		}
		return result;
	}

	@Override
	public Response<Object> saveData(String userId, String search_id) {
		SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		Response<Object> result = new Response<>();
		// Response<OperateRsp> response = BeadWalletRongOperateService.getData(userId);
		Response<OperateRsp> response = BeadWalletRongCarrierService.getData(search_id);
		if (response.getRequestCode().equals("200")) {
			// 根据手机号查询借款人id
			BwBorrower borrower = new BwBorrower();
			borrower.setId(Long.parseLong(userId));
			borrower.setFlag(1);
			borrower.setState(1);
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			if (CommUtils.isNull(borrower)) {
				// System.out.println("根据该手机号查询到的借款人为空");
				result.setRequestCode("200");
				result.setRequestMsg("成功");
				return result;
			}
			// 保存至数据库
			OperateRsp operateRsp = response.getObj();
			List<RongOperateDataList> dataLists = operateRsp.getData().getData_list();
			if (dataLists.size() > 0) {
				for (RongOperateDataList rongOperateDataList : dataLists) {
					// 根据手机号查询是否已存在 如果存在则更新 不存在就添加
					// String phone = rongOperateDataList.getUserdata().getPhone();
					// System.out.println("借款人的手机号是：" + phone);
					BwOperateBasic bwOperateBasic = bwOperateBasicService
							.getBwOperateBasicByBorrowerId(borrower.getId());
					if (CommUtils.isNull(bwOperateBasic)) {
						// 添加基本信息
						try {
							bwOperateBasic = new BwOperateBasic();
							bwOperateBasic.setUserSource(rongOperateDataList.getUserdata().getUser_source());
							bwOperateBasic.setIdCard(rongOperateDataList.getUserdata().getId_card());
							bwOperateBasic.setAddr(rongOperateDataList.getUserdata().getAddr());
							bwOperateBasic.setPhone(rongOperateDataList.getUserdata().getPhone());
							bwOperateBasic.setPhoneRemain(rongOperateDataList.getUserdata().getPhone_remain());
							bwOperateBasic.setRealName(rongOperateDataList.getUserdata().getReal_name());
							bwOperateBasic.setRegTime(sdf.parse(rongOperateDataList.getUserdata().getReg_time()));
							bwOperateBasic.setSearchId(search_id);
							bwOperateBasic.setCreateTime(new Date());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						bwOperateBasic.setBorrowerId(borrower.getId());
						bwOperateBasicService.save(bwOperateBasic);
					} else {
						// 修改基本信息
						// System.out.println("根据手机号查询的id是：" + bwOperateBasic.getId());
						bwOperateBasic.setUserSource(rongOperateDataList.getUserdata().getUser_source());
						bwOperateBasic.setIdCard(rongOperateDataList.getUserdata().getId_card());
						bwOperateBasic.setAddr(rongOperateDataList.getUserdata().getAddr());
						bwOperateBasic.setPhone(rongOperateDataList.getUserdata().getPhone());
						bwOperateBasic.setPhoneRemain(rongOperateDataList.getUserdata().getPhone_remain());
						bwOperateBasic.setRealName(rongOperateDataList.getUserdata().getReal_name());
						bwOperateBasic.setSearchId(search_id);
						bwOperateBasic.setUpdateTime(new Date());
						try {
							bwOperateBasic.setRegTime(sdf.parse(rongOperateDataList.getUserdata().getReg_time()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						bwOperateBasic.setBorrowerId(borrower.getId());
						bwOperateBasicService.update(bwOperateBasic);
					}

					// 通话记录
					// 根据手机号查询通话记录
					List<RongOperateTelData> telDatas = rongOperateDataList.getTeldata();
					Date updateTime = null;
					try {
						updateTime = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (CommUtils.isNull(updateTime)) {
						for (RongOperateTelData rongOperateTelData : telDatas) {
							// 获取通话记录
							List<TelData> lists = rongOperateTelData.getTeldata();
							if (!CommUtils.isNull(lists)) {
								for (TelData telData : lists) {
									try {

										if (CommUtils.isNull(telData.getCall_time())) {
											continue;
										}
										if (CommUtils.isNull(telData.getTrade_time())) {
											continue;
										}
										if (CommUtils.isNull(telData.getCall_type())) {
											continue;
										}
										// 添加
										BwOperateVoice bwOperateVoice = new BwOperateVoice();
										bwOperateVoice.setUpdateTime(new Date());
										bwOperateVoice.setBorrower_id(borrower.getId());
										// 检验日期格式
										String callTime = null;
										try {
											callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
										} catch (ParseException e) {
											e.printStackTrace();
											// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
											continue;
										}
										if (!CommUtils.isNull(callTime)) {
											Date beginDate;
											try {
												beginDate = sdf_hms.parse(callTime);
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												continue;
											}
											int s = MyDateUtils.getMonthSpace(beginDate, now);
											if (s <= 3) {
												bwOperateVoice.setCall_time(callTime);
												bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
												bwOperateVoice.setReceive_phone(telData.getReceive_phone());
												bwOperateVoice.setTrade_addr(telData.getTrade_addr());
												bwOperateVoice.setTrade_time(telData.getTrade_time());
												bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
												bwOperateVoiceService.save(bwOperateVoice);
											}
										}

									} catch (Exception e) {
										continue;
									}
								}
							}
						}
					} else {
						Date callDate = updateTime;
						try {
							callDate = sdf_hms.parse(sdf_hms.format(callDate));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						for (RongOperateTelData rongOperateTelData : telDatas) {
							// 获取通话记录
							List<TelData> lists = rongOperateTelData.getTeldata();
							if (!CommUtils.isNull(lists)) {
								Date jsonCallData = null;
								for (TelData telData : lists) {
									try {
										if (CommUtils.isNull(telData.getCall_time())) {
											continue;
										}
										if (CommUtils.isNull(telData.getTrade_time())) {
											continue;
										}
										if (CommUtils.isNull(telData.getCall_type())) {
											continue;
										}

										try {
											jsonCallData = sdf_hms.parse(telData.getCall_time());
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										if (jsonCallData.after(callDate)) {
											BwOperateVoice bwOperateVoice = new BwOperateVoice();
											bwOperateVoice.setUpdateTime(new Date());
											bwOperateVoice.setBorrower_id(borrower.getId());
											// 检验日期格式
											String callTime = null;
											try {
												callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
											} catch (ParseException e) {
												e.printStackTrace();
												// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
												continue;
											}
											bwOperateVoice.setCall_time(callTime);
											bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
											bwOperateVoice.setReceive_phone(telData.getReceive_phone());
											bwOperateVoice.setTrade_addr(telData.getTrade_addr());
											bwOperateVoice.setTrade_time(telData.getTrade_time());
											bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
											bwOperateVoiceService.save(bwOperateVoice);
										}
									} catch (Exception e) {
										continue;
									}
								}
							}
						}
					}
				}

			}
			result.setRequestCode("000");
			result.setRequestMsg("成功");
		} else {
			result.setRequestCode("101");
			result.setRequestMsg(response.getRequestMsg());
		}
		return result;
	}
	

	@Override
	public void saveDataV2(Long borrowerId, String searchId,List<RongOperateDataList> dataLists) {
			
		SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		//循环保存数据
		for (RongOperateDataList rongOperateDataList : dataLists) {
			BwOperateBasic bwOperateBasic = bwOperateBasicService
					.getBwOperateBasicByBorrowerId(borrowerId);
			if (CommUtils.isNull(bwOperateBasic)) {
				// 添加基本信息
				try {
					bwOperateBasic = new BwOperateBasic();
					bwOperateBasic.setBorrowerId(borrowerId);
					bwOperateBasic.setUserSource(rongOperateDataList.getUserdata().getUser_source());
					bwOperateBasic.setIdCard(rongOperateDataList.getUserdata().getId_card());
					bwOperateBasic.setAddr(rongOperateDataList.getUserdata().getAddr());
					bwOperateBasic.setPhone(rongOperateDataList.getUserdata().getPhone());
					bwOperateBasic.setPhoneRemain(rongOperateDataList.getUserdata().getPhone_remain());
					bwOperateBasic.setRealName(rongOperateDataList.getUserdata().getReal_name());
					bwOperateBasic.setRegTime(sdf.parse(rongOperateDataList.getUserdata().getReg_time()));
					bwOperateBasic.setSearchId(searchId);
					bwOperateBasic.setCreateTime(new Date());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				bwOperateBasicService.save(bwOperateBasic);
			} else {
				// 修改基本信息
				try {
					bwOperateBasic.setBorrowerId(borrowerId);
					bwOperateBasic.setUserSource(rongOperateDataList.getUserdata().getUser_source());
					bwOperateBasic.setIdCard(rongOperateDataList.getUserdata().getId_card());
					bwOperateBasic.setAddr(rongOperateDataList.getUserdata().getAddr());
					bwOperateBasic.setPhone(rongOperateDataList.getUserdata().getPhone());
					bwOperateBasic.setPhoneRemain(rongOperateDataList.getUserdata().getPhone_remain());
					bwOperateBasic.setRealName(rongOperateDataList.getUserdata().getReal_name());
					bwOperateBasic.setSearchId(searchId);
					bwOperateBasic.setUpdateTime(new Date());
					bwOperateBasic.setRegTime(sdf.parse(rongOperateDataList.getUserdata().getReg_time()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				bwOperateBasicService.update(bwOperateBasic);
			}
			
			// 通话记录
			// 根据手机号查询通话记录
			List<RongOperateTelData> telDatas = rongOperateDataList.getTeldata();
			Date updateTime = null;
			try {
				updateTime = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (CommUtils.isNull(updateTime)) {
				for (RongOperateTelData rongOperateTelData : telDatas) {
					// 获取通话记录
					List<TelData> lists = rongOperateTelData.getTeldata();
					if (!CommUtils.isNull(lists)) {
						for (TelData telData : lists) {
							try {
								
								if (CommUtils.isNull(telData.getCall_time())) {
									continue;
								}
								if (CommUtils.isNull(telData.getTrade_time())) {
									continue;
								}
								if (CommUtils.isNull(telData.getCall_type())) {
									continue;
								}
								// 添加
								BwOperateVoice bwOperateVoice = new BwOperateVoice();
								bwOperateVoice.setUpdateTime(new Date());
								bwOperateVoice.setBorrower_id(borrowerId);
								// 检验日期格式
								String callTime = null;
								try {
									callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
								} catch (ParseException e) {
									e.printStackTrace();
									// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
									continue;
								}
								if (!CommUtils.isNull(callTime)) {
									Date beginDate;
									try {
										beginDate = sdf_hms.parse(callTime);
									} catch (ParseException e) {
										e.printStackTrace();
										continue;
									}
									int s = MyDateUtils.getMonthSpace(beginDate, now);
									if (s <= 3) {
										bwOperateVoice.setCall_time(callTime);
										bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
										bwOperateVoice.setReceive_phone(telData.getReceive_phone());
										bwOperateVoice.setTrade_addr(telData.getTrade_addr());
										bwOperateVoice.setTrade_time(telData.getTrade_time());
										bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
										bwOperateVoiceService.save(bwOperateVoice);
									}
								}
								
							} catch (Exception e) {
								continue;
							}
						}
					}
				}
			} else {
				Date callDate = updateTime;
				try {
					callDate = sdf_hms.parse(sdf_hms.format(callDate));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				for (RongOperateTelData rongOperateTelData : telDatas) {
					// 获取通话记录
					List<TelData> lists = rongOperateTelData.getTeldata();
					if (!CommUtils.isNull(lists)) {
						Date jsonCallData = null;
						for (TelData telData : lists) {
							try {
								if (CommUtils.isNull(telData.getCall_time())) {
									continue;
								}
								if (CommUtils.isNull(telData.getTrade_time())) {
									continue;
								}
								if (CommUtils.isNull(telData.getCall_type())) {
									continue;
								}
								
								try {
									jsonCallData = sdf_hms.parse(telData.getCall_time());
								} catch (ParseException e) {
									e.printStackTrace();
								}
								if (jsonCallData.after(callDate)) {
									BwOperateVoice bwOperateVoice = new BwOperateVoice();
									bwOperateVoice.setUpdateTime(new Date());
									bwOperateVoice.setBorrower_id(borrowerId);
									// 检验日期格式
									String callTime = null;
									try {
										callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
									} catch (ParseException e) {
										e.printStackTrace();
										// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
										continue;
									}
									bwOperateVoice.setCall_time(callTime);
									bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
									bwOperateVoice.setReceive_phone(telData.getReceive_phone());
									bwOperateVoice.setTrade_addr(telData.getTrade_addr());
									bwOperateVoice.setTrade_time(telData.getTrade_time());
									bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
									bwOperateVoiceService.save(bwOperateVoice);
								}
							} catch (Exception e) {
								continue;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<OperateCheck> getVoiceCallByMobile(String mobile) {
		String sql = "SELECT DISTINCT v.id,v.mobile,v.peerNumber,COUNT(peerNumber) AS num FROM t_voicecall v WHERE mobile = #{mobile} "
				+ "GROUP BY v.peerNumber " + "ORDER BY num DESC LIMIT 10 ";
		return sqlMapper.selectList(sql, mobile, OperateCheck.class);
	}

	@Override
	public int count(List<String> list, Long borrowerId) {
		StringBuffer sb = new StringBuffer(" SELECT COUNT(id) as num FROM bw_contact_list WHERE phone IN (");
		for (String mobile : list) {
			sb.append(mobile);
			sb.append(",");

		}
		sb.append(") AND borrower_id = #{id} ");
		// System.out.println("sql语句：" + sb.toString());
		String sql = sb.toString();
		// System.out.println(sql);
		sql = sql.replace(",)", ")");
		// System.out.println("修改完之后的sql：" + sql);
		return sqlMapper.selectOne(sql, borrowerId, Integer.class);
	}

	@Override
	public Response<RongOprerateMsgCodeRsqData> getMsgCode(String phone, String name, String idCard,
			String messagecodeType) {
		return BeadWalletRongOperateService.getMsgCode(phone, name, idCard, messagecodeType);
	}

	@Override
	public Response<RongOperateLogin> getPicCode(RongOperateReqData rongOperateReqData, String piccodeType) {

		return BeadWalletRongOperateService.getPicCode(rongOperateReqData, piccodeType);
	}

}
