package com.waterelephant.controller;

import java.sql.SQLException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.rong360.service.BeadWalletRongBjQufenqiService;
import com.beadwallet.service.rong360.service.BeadWalletRongExternalService;
import com.beadwallet.service.utils.HttpClientHelper;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.mohe.service.MoheBussinessService;
import com.waterelephant.rongCarrier.service.BwDataExternalService;
import com.waterelephant.rongCarrier.service.Rong360BussinessService;
import com.waterelephant.saas.service.FqyBussinessService;
import com.waterelephant.service.CreditBussinessService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.QueryOrderBusiService;

//import io.swagger.annotations.ApiOperation;

/**
 * 系统授权认证中心
 * 主要处理对外开放的授权认证接口（北京分公司通过此类调用融360接口等）
 * 先已开放的业务有：运营商授权、公积金授权、社保授权
 * @author dinglinhao
 *
 */
@Controller
@RequestMapping("/app/authenticationCenters")
public class SystemAuthenticationCentersController {

	private Logger logger = LoggerFactory.getLogger(SystemAuthenticationCentersController.class);
	
	@Autowired
	private BwDataExternalService bwDataExternalService;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private IBwOrderService bwOrderService;
	
//	@Autowired
//	private IBwBorrowerService bwBorrowerService;
	
//	@Autowired
//	private OperatorsDataPackage operatorsDataPackage;
	
//	@Autowired
//	private BwOrderAuthService bwOrderAuthService;
//	
	@Autowired
	private MoheBussinessService moheBussinessService;
	
	@Autowired
	private FqyBussinessService saasBussinessService;
	
	@Autowired
	private Rong360BussinessService rong360BussinessService;
	
	@Autowired
	private CreditBussinessService creditBussinessService;
	
	@Autowired
	private QueryOrderBusiService queryOrderBusiService;
	
	
	/**
	 * @param
	 * @author dinglinhao
	 * @since 1.0.0
	 * @description 对外接口 提供融360天机风控接口 H5
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/rong360/collectuser.do",method=RequestMethod.POST)
	public Map<String,Object> collectuser(HttpServletRequest request) {
		
		String methodName = getClass().getName().concat(".collectuser");

		Map<String,Object> response = new HashMap<>();
		
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String idNumber = request.getParameter("idNumber");
		String userId = request.getParameter("userId");
		String outUniqueId = request.getParameter("outUniqueId");
		String authChannel = request.getParameter("authChannel");
		String emergencyName1 = request.getParameter("emergencyName1");
		String emergencyPhone1 = request.getParameter("emergencyPhone1");
		String emergencyRelation1 = request.getParameter("emergencyRelation1");
		String emergencyName2 = request.getParameter("emergencyName2");
		String emergencyPhone2 = request.getParameter("emergencyPhone2");
		String emergencyRelation2 = request.getParameter("emergencyRelation2");
		String returnUrl = request.getParameter("returnUrl");
		String notifyUrl = request.getParameter("notifyUrl");
		String version = request.getParameter("version");
		String exts = request.getParameter("exts");
		String appId = request.getParameter("appId");//新增appId参数，用该参数区分调用融360的配置（北京去分期的配置信息不同） dinglinhao/2018年7月31日17:57:10
		
		
		logger.info("{}方法，入参信息type:{},name:{},phone:{},idNumber:{},userId:{},outUniqueId:{},authChannel:{},"
				+ "emergencyName1:{},emergencyRelation1:{},emergencyPhone1:{},emergencyName2:{},emergencyRelation2:{},"
				+ "emergencyPhone2:{},returnUrl:{},notifyUrl:{},version:{},exts:{},appId:{}",methodName,type,name,phone,idNumber,userId,outUniqueId,authChannel,
				emergencyName1,emergencyRelation1,emergencyPhone1,emergencyName2,emergencyRelation2,emergencyPhone2,returnUrl,notifyUrl,version,exts,appId);
		
		try {
			
			Assert.hasText(type, "传入参数不完整，缺少type");//运营商报告
			
			Assert.isTrue(BeadWalletRongExternalService.containsType(type),"传入参数不正确，type参数错误");
			
			Assert.hasText(name, "传入参数不完整，缺少name");//姓名
			
			Assert.hasText(phone, "传入参数不完整，缺少phone");//手机号
			
			Assert.hasText(idNumber,"传入参数不完整，缺少idNumber");//身份证号
			
			Assert.hasText(userId, "传入参数不完整，缺少userId");//用户Id
			
			Assert.hasText(outUniqueId, "传入参数不完整，缺少outUniqueId");//业务流水ID
			
//			Assert.hasText(emergencyName1, "传入参数不完整，缺少emergencyName1");//紧急联系人
//			
//			Assert.hasText(emergencyPhone1, "传入参数不完整，缺少emergencyPhone1");//紧急联系人电话
//
//			Assert.hasText(emergencyName2, "传入参数不完整，缺少emergencyName2");//紧急联系人
//		
//			Assert.hasText(emergencyPhone2, "传入参数不完整，缺少emergencyPhone2");//紧急联系人电话
//
//          Assert.hasText(version, "传入参数不完整，缺少version");
			
			Assert.hasText(returnUrl,"传入参数不完整，缺少returnUrl");//回调地址
			
			Assert.hasText(notifyUrl,"传入参数不完整，缺少notifyUrl");//通知地址
			
			Assert.hasText(authChannel,"传入参数不完整，缺少authChannel");//授权渠道
			
			//有紧急联系人没有联系电话的情况
			if(!StringUtils.isEmpty(emergencyName1) ) {
				
				Assert.hasText(emergencyPhone1, "传入参数不完整，缺少emergencyPhone1");//紧急联系人电话
				//如果为空则默认other
				emergencyRelation1 = StringUtils.isEmpty(emergencyRelation1) ? "other" : emergencyRelation1;
			}
			//有紧急联系人没有联系电话的情况
			if(!StringUtils.isEmpty(emergencyName2)) {
				Assert.hasText(emergencyPhone2, "传入参数不完整，缺少emergencyPhone2");//紧急联系人电话
				//如果为空则默认other
				emergencyRelation2 = StringUtils.isEmpty(emergencyRelation2) ? "other" : emergencyRelation2;
			}
			
			Map<String,String> params = new HashMap<String,String>();
			
			params.put("type", type);
			params.put("name",name);
			params.put("phone", phone);
			params.put("userId",userId);
			params.put("idNumber",idNumber);
			params.put("authChannel",authChannel);
			params.put("outUniqueId",outUniqueId);
			params.put("emergencyName1",emergencyName1);
			params.put("emergencyPhone1",emergencyPhone1);
			params.put("emergencyRelation1",emergencyRelation1);																
			params.put("emergencyName2",emergencyName2);
			params.put("emergencyPhone2",emergencyPhone2);
			params.put("emergencyRelation2",emergencyRelation2);	
			params.put("returnUrl",returnUrl);
			params.put("notifyUrl",notifyUrl);
			params.put("version", version);
			params.put("exts", exts);
			
			bwDataExternalService.save(params,StringUtils.isEmpty(appId)?"融360-天机":appId);

			//SDK调用service融360接口
			String result = null;
			if(StringUtils.isEmpty(appId)) {
				result = BeadWalletRongExternalService.collectuser(params);
				
			}else if("2010826".equals(appId)) {//去分期的appid
				result = BeadWalletRongBjQufenqiService.collectuser(params);
			}else {
				Assert.isNull(appId, "传入参数[appId]不合法~");
			}
			
			logger.debug("请求融360的tianji.api.tianjireport.collectuser方法,返回：{}",result);
			
			Assert.hasText(result,"调用API接口异常");
			
//			JSONObject jsonObject = JSONObject.parseObject(result);
//			
//			String code = jsonObject.getString("error");
//			
//			//error !=200 请求正常返回
//			Assert.isTrue("200".equals(code), jsonObject.getString("msg"));
//			//天机该接口响应结构
//			JSONObject jsonResponse = jsonObject.getJSONObject("tianji_api_tianjireport_collectuser_response");
//			//重定向地址
//			String redirectUrl = jsonResponse.getString("redirectUrl");
//			
//			response.put("code", code);
//			response.put("version", "1.0.0");
//			response.put("message", "请重定向redirectUrl");
//			response.put("redirectUrl", redirectUrl);
			
			response = JSONObject.parseObject(result,Map.class);
		
		} catch(IllegalArgumentException e) {
			
			response.put("error", "102");
			response.put("msg", e.getMessage());
			
		} catch(SQLException e) {
			logger.error("接口异常,字段outUniqueId:{}违反唯一约束", outUniqueId);
			
			response.put("error", "101");
			response.put("msg", "参数outUniqueId必须唯一");
		} catch (Exception e) {
			logger.error("接口异常：{}", e.getMessage());
			
			response.put("error", "101");
			response.put("msg", "系统繁忙，请稍后再试");
		}
		return response;
	}
	
	
	/**
	 * @param
	 * @author dinglinhao
	 * @since 1.0.0
	 * @description 对外接口 拉卡拉调用融360天机风控接口获取运营商数据 H5
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/channel/collectuser.do",method=RequestMethod.POST)
	public Map<String,Object> channelCollectuser(HttpServletRequest request) {
		
		String methodName = getClass().getName().concat(".lklCollectuser");

		Map<String,Object> response = new HashMap<>();
		
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String idNumber = request.getParameter("idNumber");
		String userId = request.getParameter("userId");
		String outUniqueId = request.getParameter("outUniqueId");
		String authChannel = request.getParameter("authChannel");
		String emergencyName1 = request.getParameter("emergencyName1");
		String emergencyPhone1 = request.getParameter("emergencyPhone1");
		String emergencyRelation1 = request.getParameter("emergencyRelation1");
		String emergencyName2 = request.getParameter("emergencyName2");
		String emergencyPhone2 = request.getParameter("emergencyPhone2");
		String emergencyRelation2 = request.getParameter("emergencyRelation2");
		String returnUrl = request.getParameter("returnUrl");
		String notifyUrl = request.getParameter("notifyUrl");
		String version = request.getParameter("version");
		String exts = request.getParameter("exts");
		String platform = request.getParameter("platform");
		
		
		logger.info("{}方法，入参信息type:{},name:{},phone:{},idNumber:{},userId:{},outUniqueId:{},authChannel:{},"
				+ "emergencyName1:{},emergencyRelation1:{},emergencyPhone1:{},emergencyName2:{},emergencyRelation2:{},"
				+ "emergencyPhone2:{},returnUrl:{},notifyUrl:{},version:{},exts:{}",methodName,type,name,phone,idNumber,userId,outUniqueId,authChannel,
				emergencyName1,emergencyRelation1,emergencyPhone1,emergencyName2,emergencyRelation2,emergencyPhone2,returnUrl,notifyUrl,version,exts);
		
		try {
			
			Assert.hasText(type, "传入参数不完整，缺少type");//运营商报告
			
			Assert.isTrue(BeadWalletRongExternalService.containsType(type),"传入参数不正确，type参数错误");
			
			Assert.hasText(name, "传入参数不完整，缺少name");//姓名
			
			Assert.hasText(phone, "传入参数不完整，缺少phone");//手机号
			
			Assert.hasText(idNumber,"传入参数不完整，缺少idNumber");//身份证号
			
			Assert.hasText(userId, "传入参数不完整，缺少userId");//用户Id
			
			Assert.hasText(outUniqueId, "传入参数不完整，缺少outUniqueId");//业务流水ID
			
//			Assert.hasText(emergencyName1, "传入参数不完整，缺少emergencyName1");//紧急联系人
//			
//			Assert.hasText(emergencyPhone1, "传入参数不完整，缺少emergencyPhone1");//紧急联系人电话
//
//			Assert.hasText(emergencyName2, "传入参数不完整，缺少emergencyName2");//紧急联系人
//		
//			Assert.hasText(emergencyPhone2, "传入参数不完整，缺少emergencyPhone2");//紧急联系人电话
//
//          Assert.hasText(version, "传入参数不完整，缺少version");
//			
			Assert.hasText(returnUrl,"传入参数不完整，缺少returnUrl");//回调地址
//			
//			Assert.hasText(notifyUrl,"传入参数不完整，缺少notifyUrl");//通知地址
			
			Assert.hasText(authChannel,"传入参数不完整，缺少authChannel");//授权渠道
			
			//有紧急联系人没有联系电话的情况
			if(!StringUtils.isEmpty(emergencyName1) ) {
				
				Assert.hasText(emergencyPhone1, "传入参数不完整，缺少emergencyPhone1");//紧急联系人电话
				//如果为空则默认other
				emergencyRelation1 = StringUtils.isEmpty(emergencyRelation1) ? "other" : emergencyRelation1;
			}
			//有紧急联系人没有联系电话的情况
			if(!StringUtils.isEmpty(emergencyName2)) {
				Assert.hasText(emergencyPhone2, "传入参数不完整，缺少emergencyPhone2");//紧急联系人电话
				//如果为空则默认other
				emergencyRelation2 = StringUtils.isEmpty(emergencyRelation2) ? "other" : emergencyRelation2;
			}
			
			//特殊处理-针对returnUrl过长：截取url中"?"以后的参数信息放入exts字段中
			if(!StringUtils.isEmpty(returnUrl) && returnUrl.length()>160) {
				//数据库returnUrl 最大500字节（一个汉字3个字节，500/3=166个汉字）
				int flag = returnUrl.indexOf("?");
				String str = returnUrl.substring(flag, returnUrl.length());
				exts = StringUtils.isEmpty(exts) ? str : exts.indexOf("orderId=")!=-1 ? str : str + "&orderId="+ exts;
				returnUrl = returnUrl.substring(0, flag);
			}
			
			Map<String,String> params = new HashMap<String,String>();
			
			params.put("type", type);
			params.put("name",name);
			params.put("phone", phone);
			params.put("userId",userId);
			params.put("idNumber",idNumber);
			params.put("authChannel",authChannel);
			params.put("outUniqueId",outUniqueId);
			params.put("emergencyName1",emergencyName1);
			params.put("emergencyPhone1",emergencyPhone1);
			params.put("emergencyRelation1",emergencyRelation1);																
			params.put("emergencyName2",emergencyName2);
			params.put("emergencyPhone2",emergencyPhone2);
			params.put("emergencyRelation2",emergencyRelation2);	
			params.put("returnUrl",returnUrl);
			params.put("notifyUrl",notifyUrl);
			params.put("version", version);
			params.put("exts", exts);
			
			bwDataExternalService.save(params,StringUtils.isEmpty(platform) ? authChannel : platform);//

			//SDK调用service融360接口
			String result = BeadWalletRongExternalService.collectuser(params);
			
			logger.debug("请求融360的tianji.api.tianjireport.collectuser方法,返回：{}",result);
			
			Assert.hasText(result,"调用API接口异常");
			
//			JSONObject jsonObject = JSONObject.parseObject(result);
//			
//			String code = jsonObject.getString("error");
//			
//			//error !=200 请求正常返回
//			Assert.isTrue("200".equals(code), jsonObject.getString("msg"));
//			//天机该接口响应结构
//			JSONObject jsonResponse = jsonObject.getJSONObject("tianji_api_tianjireport_collectuser_response");
//			//重定向地址
//			String redirectUrl = jsonResponse.getString("redirectUrl");
//			
//			response.put("code", code);
//			response.put("version", "1.0.0");
//			response.put("message", "请重定向redirectUrl");
//			response.put("redirectUrl", redirectUrl);
			
			response = JSONObject.parseObject(result,Map.class);
		
		} catch(IllegalArgumentException e) {
			
			response.put("error", "102");
			response.put("msg", e.getMessage());
			
		} catch(SQLException e) {
			logger.error("接口异常,字段outUniqueId:{}违反唯一约束", outUniqueId);
			
			response.put("error", "101");
			response.put("msg", "参数outUniqueId必须唯一");
		} catch (Exception e) {
			logger.error("接口异常：{}", e.getMessage());
			
			response.put("error", "101");
			response.put("msg", "系统繁忙，请稍后再试");
		}
		return response;
	}
	
    
    /**
     * 报告详情
     * @param userId 用户Id
     * @param outUniqueId 业务流水号 唯一
     * @reportType 报告类型  默认JSON
     * @author dinglinhao
     * @date 2018年5月10日11:54:06
     * @version 1.0.0
     * @return JSON
     */
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping("/rong360/detail.do")
    public Map<String, Object> getDetail(HttpServletRequest request) {
    	
    	String methodName = getClass().getName().concat(".getDetail");
    	
    	Map<String,Object> 	response =new HashMap<>();
    	
    	String userId = request.getParameter("userId");
    	
		String outUniqueId = request.getParameter("outUniqueId");
		
		String appId = request.getParameter("appId");//新增appId参数，用该参数区分调用融360的配置（北京去分期的配置信息不同） dinglinhao/2018年7月31日17:57:10
		
		String reportType = "json";//默认json返回
		
		String result = null;
		
		try {
			
			Assert.hasText(userId, "传入参数不完整，缺少userId");
			
			Assert.hasText(outUniqueId,"传入参数不完整，缺少outUniqueId");
			
//			BwDataExternal record = this.bwDataExternalService.selectRecord(userId, outUniqueId);
//			
//			Assert.notNull(record,"该请求记录不存在~");
//			
//			String type = record.getType();
//			
//			Assert.hasText(type, "该请求记录没有授权类型~");
			
			if(StringUtils.isEmpty(appId)) {
				result =BeadWalletRongExternalService.detail(userId, outUniqueId, reportType,false);
			
			}else if("2010826".equals(appId)) {//去分期的appid
				result =BeadWalletRongBjQufenqiService.detail(userId, outUniqueId, reportType);
			} else {
				Assert.isNull(appId, "传入参数[appId]不合法~");
			}
			
			logger.debug("{}方法，请求参数userId：{},outUniqueId:{},返回结果：{}",methodName,userId,outUniqueId,result);
			
			Assert.hasText(result,"系统繁忙，请稍后再试");
			
//			JSONObject jsonObject = JSONObject.parseObject(result);
//			
//			if(jsonObject.containsKey("error")) {
//				
//				String code = jsonObject.getString("error");
//				String message = jsonObject.getString("msg");
//				if("200".equals(code)) {
//					response.put("code", code);
//					response.put("version", "1.0.0");
//					response.put("message", message);
//					response.put("tianji_api_tianjireport_detail_response", jsonObject.get("tianji_api_tianjireport_detail_response"));
//				}else {
//					response.put("code", code);
//					response.put("version", "1.0.0");
//					response.put("message", message);
//				} 
//			}else {
//				response = JSONObject.toJavaObject(jsonObject, Map.class);
//			}
			
			response = JSONObject.parseObject(result, Map.class);
		} catch (IllegalArgumentException e) {
			
			response.put("error", "102");
			response.put("msg", e.getMessage());
			
		} catch (Exception e) {
			
			logger.error("{}方法，请求参数userId：{},outUniqueId:{},系统异常：{}",methodName,userId,outUniqueId,e.getMessage());
			
			response.put("error", "102");
			response.put("msg", "系统繁忙，请稍后再试");
			
		}
    	
    	return response;
    }
    
    /**
     * 获得基础数据
     * @param request
     * @return
     */
    @ResponseBody
	@RequestMapping("/rong360/get_data.do")
    public Map<String,Object> getData(HttpServletRequest request) {
    	long start = System.currentTimeMillis();
    	
    	String methodName = getClass().getName().concat("/rong360/get_data.do");
    	
    	logger.info("----/rong360/get_data.do接口请求入参：{}----",JSON.toJSONString(request.getParameterMap()));
    	
    	Map<String,Object> response = new HashMap<>();

    	String searchId = request.getParameter("searchId");
    	
    	String appId = request.getParameter("appId");//新增appId参数，用该参数区分调用融360的配置（北京去分期的配置信息不同） dinglinhao/2018年7月31日17:57:10
    	
    	String type = request.getParameter("type");//授权时的类型（运营商、公积金、社保、电商等）
    	
    	String isgzip = request.getParameter("isGzip");
    	
    	try {
			Assert.hasText(searchId,"传入参数不完整，缺少searchId");
			
 			type = StringUtils.isEmpty(type)?"mobile":type;//默认查询运营商

 			boolean gzip = StringUtils.isEmpty(isgzip) ? false : Boolean.valueOf(isgzip);
 			
 			int index = searchId.lastIndexOf("_");
 			
 			if(index > -1) {
 				String prefix = searchId.substring(0, index+1).trim();
 				String content = searchId.substring(index+1).trim();
 				
 				Assert.hasText(prefix, "传入参数searchId不正确~");
 				Assert.hasText(content, "传入参数searchId不正确~");
 				
 				//授信单
 				if(prefix.toUpperCase().indexOf("SXD_")>-1) {
 					String creditNo = content;
 					response = creditBussinessService.queryOperatorData(creditNo, appId,gzip);
 				} 
 				//魔方
 				else if(prefix.equalsIgnoreCase("MF_")) {
 					String taskId = content;
 					response = moheBussinessService.queryOperatorData(taskId, appId,gzip);
 				} 
 				//SAAS
 				else if(prefix.equalsIgnoreCase("SAAS_")) {
 					String saasOrderNo = content;
 					response = saasBussinessService.querySaaSOperatorData(saasOrderNo, appId,gzip);
 	 				response.put("error", 200);
 	 				response.put("msg", "请求成功~");
 				}
 				else {
 					logger.error("-----searchId:{}格式不正确~",searchId);
 					throw new IllegalArgumentException("传入参数searchId不正确~");
 				}
 			}else {
 				//判断searchId是否是本库工单ID
 				BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(searchId);
 				//存在工单记录 且为运营商类型（mobile）
 				if(null != bwOrder && "mobile".equals(type)) {
 					// 根据借款人,工单信息，授权记录查询运营商数据
 					response = queryOrderBusiService.queryOperatorData(bwOrder, appId, gzip);
 					
 				}else {
 					//直接查询rong360运营商报告
 					response = rong360BussinessService.queryData(searchId,type,appId,gzip);
 				}
 				
 			}
 			
// 			boolean isMoheId = searchId.startsWith("MF_");//魔方首字母大写加下划线
// 			
// 			boolean isSAAS = searchId.startsWith("SAAS_");//SAAS		
// 			//1、授信单
// 			if(searchId.startsWith("sxd_")) {
// 				String creditNo = searchId.replace("sxd_", "").trim();
// 				Assert.hasText(creditNo, "传入参数searchId不正确~");
// 				//根据授信单号查询运营商数据
// 				return creditBussinessService.queryOperatorData(creditNo, gzip);
// 			}
// 			//1、查询魔盒运营商数据
// 			if(isMoheId) {
// 				String taskId = searchId.replace("MF_", "");
// 				response = moheBussinessService.queryTaskData(taskId, appId,gzip);
// 				response.put("error", 200);
// 				response.put("msg", "请求成功~");
// 				return response;
// 			}
// 			//2、查询SAAS运营商数据
// 			if(isSAAS){
// 				String orderNo = searchId.replace("SAAS_", "");
// 				response = fqyBussinessService.querySaaSOperatorData(orderNo, appId,gzip);
// 				response.put("error", 200);
// 				response.put("msg", "请求成功~");
// 				return response;
// 			}
// 			
// 			//3、判断searchId是否是本库工单ID
//			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(searchId);
//			//存在工单记录 且为运营商类型（mobile）
//			if(null != bwOrder && "mobile".equals(type)) {
////				//查询该工单的借款人30天内是否有做过融360运营商授权
////				String xgSearchId = operatorsDataPackage.queryXgSearchId(bwOrder.getBorrowerId());
////				//如果xgSearchId为空 则根据自有渠道或三方渠道工单查询运营商数据
////				if(StringUtils.isEmpty(xgSearchId)) {
//				//借款人信息
//				BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(bwOrder.getBorrowerId());
//				//工单授权记录
//				BwOrderAuth2 bwOrderAuth = bwOrderAuthService.selectBwOrderAuth2(bwOrder.getId(), 1);
//				// 根据借款人,工单信息，授权记录查询运营商数据
//				Object dataList = operatorsDataPackage.packageOperatorsDataV2(bwBorrower,bwOrder,bwOrderAuth,gzip);
//				//渠道运营商数据封装成融360数据格式返回 type==mobile 2018年8月20日11:08:56 dinglinhao
//				Map<String,Object> data = new HashMap<>();
//				//运营商数据列表
//				data.put("data_list", dataList);
//				Map<String,Object> mobileResponse = new HashMap<>();
//				mobileResponse.put("data", data);
//				//是否选择压缩运营商相应结果
//				Object result = gzip ? GzipUtil.gzip(JSON.toJSONString(mobileResponse)) : JSON.toJSON(mobileResponse);
//				
//				response.put("error", 200);
//				response.put("msg", "处理成功");
//				response.put("wd_api_mobilephone_getdatav2_response", result);
//				return response;
////				}else {
////					//通过xgSearchId获取融360运营商数据 appId默认速秒appId
////					response = rong360BussinessService.queryData(xgSearchId, type, null, gzip);
////					return response;
////				}
//			}
//			//4、通过我们做的rong360授权，直接调用rong360接口查询数据
//			BwDataExternal record = this.bwDataExternalService.selectOneBySearchId(searchId);
//			if(null != record) {
//				type = record.getType();
//			}
//			//直接查询rong360运营商报告
//			response = rong360BussinessService.queryData(searchId,type, appId, gzip);
//			//5、不在以上范围内
////			response.put("error", "102");
////			response.put("msg", "searchId无效或未找到对应产品");
		} catch(IllegalArgumentException | BusinessException e) {
			response.put("error", "102");
			response.put("msg", e.getMessage());
		} catch (Exception e) {
			response.put("error", "101");
			response.put("msg", "系统繁忙，请稍后再试");
			logger.error("{}方法，请求参数searchId：{},系统异常：{}",methodName,searchId,e.getMessage());
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("----searchId:{}----请求耗时：{}ms----",searchId,(end-start));
		}
    	return response;
    }
    
    /**
     * 回调测试
     * 该方法提供给对外接入方测试他们的回调地址是否正常~
     * @param returnUrl 回调地址
     * @return
     */
    @ResponseBody
    @RequestMapping("/rong360/callback_test.do")
    public Map<String,Object> callbackTest(HttpServletRequest request) {
    	
    	String methodName = getClass().getName().concat(".callbackTest");
    	
    	String returnUrl = request.getParameter("returnUrl");
    	
    	Map<String,Object> response = new HashMap<String,Object>();
    	
    	try {
			Assert.hasText(returnUrl, "传入参数不完整，缺少returnUrl");
			
			Thread task = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					String result = HttpClientHelper.get(returnUrl, "utf-8", new HashMap<>(), new HashMap<>());
					
					logger.debug("{}方法发送测试请求Url：{},返回：{}",methodName,returnUrl,result);
				}
			});
			
			taskExecutor.execute(task);
			response.put("error", "200");
			response.put("msg", "请求成功~");
		} catch(IllegalArgumentException e) {
			
			response.put("error", "102");
			response.put("msg", e.getMessage());
			
		} catch (Exception e) {
			
			logger.error("{}方法发送测试请求Url：{},异常：{}",methodName,returnUrl,e.getMessage());
			
			response.put("error", "101");
			response.put("msg", "系统异常~");
		}
    	
    	return response;
    }
    
//    @ApiOperation(value="获取融360淘宝数据",notes="通过融360获取用户淘宝数据")
    @ResponseBody
    @RequestMapping(path = "/rong360/taobao_data.do",method =RequestMethod.POST)
    public Map<String,Object> getTaobaoData(HttpServletRequest request) {
    	long start = System.currentTimeMillis();
    	
    	String methodName = getClass().getName().concat("/rong360/taobao_data");
    	
    	logger.info("----/rong360/taobao_data接口请求入参：{}----",JSON.toJSONString(request.getParameterMap()));
    	
    	Map<String,Object> response = new HashMap<>();

    	String searchId = request.getParameter("searchId");
    	
    	String appId = request.getParameter("appId");
    	
    	String isgzip = request.getParameter("isGzip");
        	
    	try {
			Assert.hasText(searchId,"传入参数不完整，缺少searchId");
			
 			boolean gzip = StringUtils.isEmpty(isgzip) ? false : Boolean.valueOf(isgzip);
 			
 			int index = searchId.indexOf("_");
 			
 			if(index > 0) {
 				String prefix = searchId.substring(0, index+1).trim();
 				String content = searchId.substring(index+1).trim();
 				
 				Assert.hasText(prefix, "传入参数searchId不正确~");
 				Assert.hasText(content, "传入参数searchId不正确~");
 				
 				//授信单
 				if(prefix.equalsIgnoreCase("SXD_")) {
 					String creditNo = content;
 					response = creditBussinessService.queryTaobaoData(creditNo, appId,gzip);
 				} 
 			} else {
 				BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(searchId);
 				//存在工单记录
 				if(null != bwOrder) {
 					response = queryOrderBusiService.queryTaobaoData(bwOrder, appId,gzip);
 				}else {
 					response = rong360BussinessService.queryData(searchId,"taobao",appId,gzip);
 					
 				}
 				
 			}
 			
		} catch(IllegalArgumentException | BusinessException e) {
			response.put("error", "102");
			response.put("msg", e.getMessage());
		} catch (Exception e) {
			response.put("error", "101");
			response.put("msg", "系统繁忙，请稍后再试");
			logger.error("{}方法，请求参数searchId：{},系统异常：{}",methodName,searchId,e.getMessage());
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("----searchId:{}----请求耗时：{}ms----",searchId,(end-start));
		}
    	return response;
    }
    
//    @ApiOperation(value="获取融360京东数据",notes="通过融360获取用户京东数据")
    @ResponseBody
    @RequestMapping(path = "/rong360/jd_data.do",method =RequestMethod.POST)
    public Map<String,Object> getJdData(HttpServletRequest request) {
    	long start = System.currentTimeMillis();
    	
    	String methodName = getClass().getName().concat("/rong360/jd_data");
    	
    	logger.info("----/rong360/jd_data接口请求入参：{}----",JSON.toJSONString(request.getParameterMap()));
    	
    	Map<String,Object> response = new HashMap<>();

    	String searchId = request.getParameter("searchId");
    	
    	String appId = request.getParameter("appId");
    	
    	String isgzip = request.getParameter("isGzip");
        	
    	try {
			Assert.hasText(searchId,"传入参数不完整，缺少searchId");
			
 			boolean gzip = StringUtils.isEmpty(isgzip) ? false : Boolean.valueOf(isgzip);
 			
 			int index = searchId.indexOf("_");
 			
 			if(index > 0) {
 				String prefix = searchId.substring(0, index+1).trim();
 				String content = searchId.substring(index+1).trim();
 				
 				Assert.hasText(prefix, "传入参数searchId不正确~");
 				Assert.hasText(content, "传入参数searchId不正确~");
 				
 				//授信单
 				if(prefix.equalsIgnoreCase("SXD_")) {
 					String creditNo = content;
 					response = creditBussinessService.queryJdData(creditNo, appId,gzip);
 				} 
 			} else {
	 			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(searchId);
				//存在工单记录
				if(null != bwOrder) {
					response = queryOrderBusiService.queryJdData(bwOrder,appId,gzip);
				}else {
					response = rong360BussinessService.queryData(searchId,"jd",appId,gzip);
				}
 			}
		} catch(IllegalArgumentException | BusinessException e) {
			response.put("error", "102");
			response.put("msg", e.getMessage());
		} catch (Exception e) {
			response.put("error", "101");
			response.put("msg", "系统繁忙，请稍后再试");
			logger.error("{}方法，请求参数searchId：{},系统异常：{}",methodName,searchId,e.getMessage());
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("----searchId:{}----请求耗时：{}ms----",searchId,(end-start));
		}
    	return response;
    }
    
}
