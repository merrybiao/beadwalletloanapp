package com.waterelephant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.entity.response.OperateRsp;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entiyt.middle.RongOperateDataList;
import com.beadwallet.service.rong360.service.BeadWalletRongCarrierService;
import com.beadwallet.service.utils.HttpClientHelper;
import com.waterelephant.dataSource.DataSource;
import com.waterelephant.dataSource.DataSourceHolderManager;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.rongCarrier.JSonEntity.OpReport;
import com.waterelephant.rongCarrier.entity.BwDataExternal;
import com.waterelephant.rongCarrier.service.BwDataExternalService;
import com.waterelephant.service.IBwOperateService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.xg.service.XgBusinessService;

/**
 * app第三方回调地址
 * 由于AppCallBackController类中业务代码太多，避免麻烦，新增该类处理rong360新增业务的回调
 * @author dinglinhao
 * @date 2018年5月11日10:30:49
 */
@Controller
@RequestMapping("/app/callBack/rong360")
public class AppCallBackRongCarrierController {
	
	private final Logger logger = LoggerFactory.getLogger(AppCallBackRongCarrierController.class);

	@Autowired
	private BwDataExternalService bwDataExternalService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IBwOperateService bwOperateService;
	@Autowired
	private XgBusinessService xgBusinessService;
	@Autowired
	private BwBorrowerService bwBorrowerService;

	/**
     * rong360 回调接口
     * 回调地址IP(124.251.102.8，124.251.102.9，111.204.113.197)
     * @param request request
     * @return 回调成功信息，默认返回success
     */
    @RequestMapping(value = "/h5/etl/return.do")
    public String callback(HttpServletRequest request) {
		String methodName = getClass().getName().concat(".callback");
		
		String authChannel = request.getParameter("authChannel");
		String userId = request.getParameter("userId");
		String outUniqueId = request.getParameter("outUniqueId");
		String state = request.getParameter("state");
		String searchId = request.getParameter("searchId");
		
    	logger.info("融360回调请求，{}方法入参authChannel:{},search_id:{},outUniqueId:{},userId:{},state:{}",
    			methodName,authChannel,searchId,outUniqueId,userId,state);
    	
    	BwDataExternal bwDataExternal = null;
    	
    	try {
			bwDataExternal = this.bwDataExternalService.updateState(userId, outUniqueId, state);
		
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error("------融360回调--同步接口,更新数据异常~,参数信息userId:{},outUniqueId:{},state:{},异常信息：{}",userId,outUniqueId,state,e.getMessage());
		}
    	
    	if(null == bwDataExternal || StringUtils.isEmpty(bwDataExternal.getReturnUrl())) {
    		logger.info("------融360回调--同步接口，数据对象bwDataExternal为空或returnUrl为空,参数信息userId:{},outUniqueId:{},state:{}-----",userId,outUniqueId,state);
    		return "rong360_auth_fail";
    	}
    	
    	//下面对参数进行拼接
		StringBuffer sb = new StringBuffer(bwDataExternal.getReturnUrl());
		
		//扩展字段exts不为空
		if(!StringUtils.isEmpty(bwDataExternal.getExts())) {
			String exts = bwDataExternal.getExts();
			//判断扩展字段中是否有需要拼接的参数
			if(exts.indexOf("?")!=-1) {
				sb.append(exts);
			}
		}
		
		String platform = StringUtils.isEmpty(bwDataExternal.getPlatform())?"":bwDataExternal.getPlatform().trim();
		//根据platform做判断
		switch (platform) {
		case "LKL":
			//拉卡拉渠道过来的returnUrl不需要 default中的参数
			break;
		default:
			//如果StringBuilder中没有“？”，则拼接以“？”开始，如果有则以“&开始”
			sb.append(sb.toString().indexOf("?") ==-1 ? "?" : "&");
			sb.append("authChannel=").append(authChannel);
			sb.append("&outUniqueId=").append(outUniqueId);
			sb.append("&userId=").append(userId);
			sb.append("&state=").append(state);
			sb.append("&searchId=").append(searchId);
			break;
		}
		
		String url = sb.toString();
		logger.info("------融360回调--同步接口，url重定向：{}",url);
		return "redirect:" + url;
		
    }
    
    @ResponseBody
    @RequestMapping(value ="/h5/etl/notify.do")
    public AppResponseResult notify(HttpServletRequest request) {
    	String methodName = getClass().getName().concat(".notify");
    	AppResponseResult result = new AppResponseResult();
    	
    	String authChannel = request.getParameter("authChannel");
    	String searchId = request.getParameter("search_id");
    	String outUniqueId = request.getParameter("outUniqueId");
    	String userId = request.getParameter("userId");
    	String state = request.getParameter("state");
    	String account = request.getParameter("account");
    	String accountType = request.getParameter("accountType");
    	String errorReasonDetail = request.getParameter("errorReasonDetail");
    	
		logger.info("融360通知请求，{}方法入参authChannel:{},search_id:{},outUniqueId:{},userId:{},state:{},account:{},accountType:{},errorReasonDetail:{}",
    			methodName,authChannel,searchId,outUniqueId,userId,state,account,accountType,errorReasonDetail);
		
		BwDataExternal entity = null;
		
		try {
			Assert.hasText(userId, "缺少参数[userId]字段不能为空~");
			Assert.hasText(outUniqueId, "缺少参数[outUniqueId]字段不能为空~");
			Assert.hasText(state, "缺少参数[state]字段不能为空~");
			entity = this.bwDataExternalService.updateSearchInfo(userId, outUniqueId, searchId, state, account, accountType,errorReasonDetail);
			
			if(null == entity) {
				result.setCode("0");
				result.setMsg("接收失败~未查到授权记录~");
			} else if(!StringUtils.isEmpty(entity.getNotifyUrl())) {
				notifyFoward(entity.getNotifyUrl(), authChannel, userId, outUniqueId, state, account, accountType, searchId, errorReasonDetail);
			}else {
				String orderId = null;
				if(!StringUtils.isEmpty(entity.getExts())) {
					//见SystemAuthenticationCentersController.java类的298行代码,存储orderId的方式
					//从exts字段中取orderId
					String exts = entity.getExts();
					int index = exts.indexOf("orderId=");
					if(index == -1) {
						orderId = exts;
					}else {
						String tmp = exts.substring(index +"orderId=".length());
						orderId = tmp.substring(0, tmp.indexOf("&"));
					}
				}
				//没有回调则保存至数据库
				return notifyExecute(entity.getPlatform(),outUniqueId, searchId, userId, state,orderId);
			}
			result.setCode("200");
			result.setMsg("接收成功");
		} catch (IllegalArgumentException e) {
			logger.error("-----融360异步通知，处理异常：{}",e.getMessage());
			result.setCode("101");
			result.setMsg("接收失败~"+e.getMessage());
		}catch (Exception e) {
			logger.error("-----融360异步通知，处理异常：{}",e.getMessage());
			e.printStackTrace();
			result.setCode("0");
			result.setMsg("接收失败~");
		}
		return result;
    }
    
    
    private void notifyFoward(String notifyUrl,String authChannel,String userId,
    		String outUniqueId,String state,String account,String accountType,
    		String searchId,String errorReasonDetail) {
			
		Thread task = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				Map<String, String> params = new HashMap<>();
				
				params.put("authChannel", authChannel);
				params.put("userId", userId);
				params.put("outUniqueId", outUniqueId);
				params.put("state", state);
				params.put("search_id", searchId);
				params.put("account", account);
				params.put("accountType", accountType);
				params.put("errorReasonDetail", errorReasonDetail);
				
				//最多通知3次~
				for(int i =0;i<3;i++) {
					//通知第三方
					logger.info("-----融360异步通知--转发通知给：{}，参数：{}",notifyUrl,params);
					String result = HttpClientHelper.post(notifyUrl, "utf-8", params);
					logger.info("-----融360异步通知--第{}次--转发通知给：{}，返回结果：{}",(i+1),notifyUrl,result);
					if(!StringUtils.isEmpty(result)) return;
				}
			}
		});
		
		taskExecutor.execute(task);
    }
    
    
    private AppResponseResult notifyExecute(String platform,String outUniqueId,String searchId,String userId,String state,String orderId) {
    	AppResponseResult rDto = new AppResponseResult();

		// logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~融360添加认证运营商认证回调：state：" + state);
		logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~融360添加认证运营商认证记录：state：" + state + ",orderId：" + orderId + ",searchId:"
				+ searchId + ",userId:  " + userId);
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
							logger.error("----根据融360的search_id:" + searchId + ",user_id:"+userId+",state:"+state+",抓取运营商数据 STAR-----");
							//获取运营商数据
							result = BeadWalletRongCarrierService.getData(searchId);
							//返回结果
							if(null == result) {
								logger.error("----根据融360的search_id:{},user_id:{},state:{},抓取运营商失败，结果为空-----",searchId,userId,state);
								return;
							} else if (!"200".equals(result.getRequestCode())) {
								logger.error("----根据融360的search_id:{},user_id:{},state:{},抓取运营商失败，结果为：{}",searchId,userId,state,JSON.toJSONString(result));
								return;
							}else {
								//返回正常状态码 继续解析
								//解析运营商
								OperateRsp operateRsp = result.getObj();
								//运营商信息非空判断
								if (null == operateRsp || null == operateRsp.getData()) {
									logger.error("----根据融360的search_id:{},user_id:{},state:{},获取到的融360运营商结果为：{}",searchId,userId,state,JSON.toJSONString(result));
									return;
								}
								
								//解析运营商数据集合
								List<RongOperateDataList> dataLists = operateRsp.getData().getData_list();
								//运营商数据集合非空判断
								if(null == dataLists || dataLists.isEmpty()) {
									logger.error("----根据融360的search_id:{},user_id:{},state:{},获取到的融360运营商结果为：{}",searchId,userId,state,JSON.toJSONString(result));
									return;
								}
								
								//user_id 对应bw_borrower表的Id
								Long borrowerId = Long.valueOf(userId);
								//根据borrowerId查询借款人信息是否存在
								BwBorrower borrower = bwBorrowerService.findBwBorrowerById(borrowerId);
								//借款人信息非空判断
								if (null == borrower) {
									logger.error("----借款人信息不存在 borrowerId:{},searchId:{}-----",borrowerId,searchId);
									return;
								} else if(borrower.getState() == 0 || borrower.getFlag() == 0) {
									//借款人state和flag有问题
									logger.error("----借款人borrowerId:{},已禁用或已删除，state:{},flag:{}-----",borrowerId,borrower.getState(),borrower.getFlag());
									return;
								}
								
								// 保存至数据库
								logger.info("-----根据融360的search_id:{},user_id:{},state:{},保存运营商数据 START----",searchId,userId,state);
								try {
									//切换数据源,运营商信息保存至速秒分库
									DataSourceHolderManager.set(DataSource.MASTER_NEW);
									//保存运营商数据
									bwOperateService.saveDataV2(borrowerId, searchId, dataLists);
									logger.info("-----根据融360的search_id:{},user_id:{},state:{},保存运营商数据完成  END----",searchId,userId,state);
								} finally {
									DataSourceHolderManager.reset();
								}
							}
						} catch (Exception e1) {
							logger.error("----根据融360的search_id:{},user_id:{},state:{},抓取运营商信息失败 FAIL-----",searchId,userId,state);
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
						JSONObject jsonResult = new JSONObject();
						try {
							logger.info("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告 -----",searchId,userId,orderId,state);
							//调用融360接口抓取西瓜运营商报告
							detail = BeadWalletRongCarrierService.detail(userId, outUniqueId);
							//返回结果非空判断
							if(StringUtils.isEmpty(detail)) {
								logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告失败，返回结果为空 -----",searchId,userId,orderId,state);
								jsonResult.put("status", "1");
								jsonResult.put("msg", "解析运营商数据失败~");
								return;
							}
							//解析JSON
							JSONObject xgJson = JSON.parseObject(detail);
							if(null == xgJson || xgJson.isEmpty()) {
								logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告解析JSON失败，detail:{}----",searchId,userId,orderId,state,detail);
								jsonResult.put("status", "1");
								jsonResult.put("msg", "解析运营商数据失败~");
								return;
							}
							//解析报告详情
							JSONObject xgResponse = xgJson.getJSONObject("tianji_api_tianjireport_detail_response");
							if(null == xgResponse || xgResponse.isEmpty()) {
								logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告解析tianji_api_tianjireport_detail_response失败，detail:{}----",searchId,userId,orderId,state,detail);
								jsonResult.put("status", "1");
								jsonResult.put("msg", "解析运营商数据失败~");
								return;
							}
							//将报告转为JavaBean
							opReport = JSONObject.toJavaObject(xgResponse, OpReport.class);
							if(null == opReport) {
								logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},抓取xg西瓜运营商报告解析tianji_api_tianjireport_detail_response转JavaBean失败，detail:{}----",searchId,userId,orderId,state,detail);
								jsonResult.put("status", "1");
								jsonResult.put("msg", "解析运营商数据失败~");
								return;
							}
							// 保存运营商基础数据
//							saveXgData1(opReport, Long.parseLong(user_id));
//							xgBusiService.saveXgData(opReport, Long.parseLong(user_id));
							logger.info("----根据search_id:{},user_id:{},order_id:{},state:{},保存xg西瓜运营商报告 START----",searchId,userId,orderId,state);
							Long borrowerId = Long.parseLong(userId);
							xgBusinessService.saveXgDataToNewTab(opReport, borrowerId);
							logger.info("----根据search_id:{},user_id:{},order_id:{},state:{},保存xg西瓜运营商报告完成  END----",searchId,userId,orderId,state);
							jsonResult.put("status","0");
							jsonResult.put("msg","保存运营商数据成功~");
						} catch (Exception e) {
							logger.error("----根据search_id:{},user_id:{},order_id:{},state:{},保存xg西瓜运营商报告失败 FAIL ----",searchId,userId,orderId,state);
							jsonResult.put("status", "1");
							jsonResult.put("msg", "保存运营商数据失败~");
						} finally {
							if(!StringUtils.isEmpty(orderId)) {
								//示例：{"orderId":"180411000012","status":"0"}
								jsonResult.put("orderId", orderId);
								String value = jsonResult.toJSONString();
								RedisUtils.lpush("system:channel_order", value);
								logger.info("拉取运营商数据成功，存reids Key:{}标记：{}","system:channel_order",value);
							}else {
								logger.info("----融360异步回调通知，参数outUniqueId：{} ,searchId: {} ,userId:{},既没有回调地址，也没有orderId:{}---",outUniqueId,searchId,userId,orderId);
							}
							
						}
					}
				});
				// 关闭线程
				taskExecutor.execute(task);
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
    
}
