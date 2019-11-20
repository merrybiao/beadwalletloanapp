//package com.waterelephant.jiedianqian.test;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.StopWatch;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.jiedianqian.controller.JieDianQianController;
//import com.waterelephant.jiedianqian.entity.AddressBook;
//import com.waterelephant.jiedianqian.entity.BankInfo;
//import com.waterelephant.jiedianqian.entity.JieDianQianResponse;
//import com.waterelephant.jiedianqian.entity.LoanInfo;
//import com.waterelephant.jiedianqian.entity.Operator;
//import com.waterelephant.jiedianqian.entity.OrderInfoRequest;
//import com.waterelephant.jiedianqian.entity.UserInfo;
//import com.waterelephant.jiedianqian.entity.UserLoginUploadLog;
//import com.waterelephant.jiedianqian.service.JieDianQianService;
//import com.waterelephant.jiedianqian.util.Base64;
//import com.waterelephant.jiedianqian.util.GzipUtil;
//import com.waterelephant.jiedianqian.util.JieDianQianContext;
//import com.waterelephant.jiedianqian.util.JieDianQianLogUtil;
//import com.waterelephant.jiedianqian.util.JieDianQianUtils;
//import com.waterelephant.jiedianqian.util.RSAUtil;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwOrderService;
//import com.waterelephant.service.impl.BwRepaymentPlanService;
//import com.waterelephant.third.entity.ThirdResponse;
//import com.waterelephant.third.entity.request.CallRecord;
//import com.waterelephant.third.entity.request.CompanyInfo;
//import com.waterelephant.third.entity.request.Contact;
//import com.waterelephant.third.entity.request.IdentifyInfo;
////import com.waterelephant.third.entity.request.Operator;
//
//import com.waterelephant.third.entity.request.RequestPush;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.AESUtil;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DateUtil;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.OrderUtil;
//
//@Controller
//public class TestJieDianQianController {
//	private JieDianQianLogUtil logger = new JieDianQianLogUtil(JieDianQianController.class);
//
//	@Autowired
//	private JieDianQianService jieDianQianService;
//
//	@Autowired
//	private BwOrderService bwOrderService;
//
//	@Autowired
//	private BwRepaymentPlanService bwRepaymentPlanService;
//
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//
//	@Autowired
//	private BwBorrowerService borrowerService;
//	
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//
//	//private static String USER_INFO_REDIS = "jiedianqian:userInfo";
//	//private static String ORDER_NO_REDIS = "jiedianqian:orderno";
//	private static String JIEDIANQIAN_XUDAI = "xudai:order_id";
//	private static String partnerPrivateKey = JieDianQianContext.get("priKey"); // 我方私钥
//	private static String jdqPublicKey = JieDianQianContext.get("jdqPubKey"); // 借点钱1024位公钥
//	private static String channelCode = JieDianQianContext.get("channelCode");//渠道编码
//	public static void main(String[] args) {
//		//测试接收订单
//		//testPushOrder();
//		
//		//解析运营商数据
////		String data="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";
////		String operatorDataStr = GzipUtil.uncompress(Base64.decode(data), "utf-8");
////		System.out.println(operatorDataStr);
////		Operator operator = JSONObject.parseObject(operatorDataStr, Operator.class);
////		
////		System.out.println(operator);
//		
//		//JSONObject operatorData="";
//		
//		
//		String data="{\\\"data\\\":{         \\\"address_book\\\":Array[2],         \\\"device_info\\\":{             \\\"ip\\\":\\\"58.48.184.206\\\"         },         \\\"loan_info\\\":{             \\\"day\\\":\\\"30\\\",             \\\"money\\\":\\\"1000.00\\\"         },         \\\"operator\\\":\\\"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\\\",         \\\"source_order_id\\\":\\\"R150883304374557372\\\",         \\\"user_contact\\\":{             \\\"mobile\\\":\\\"15156027632\\\",             \\\"mobile_spare\\\":\\\"13858858888\\\",             \\\"name\\\":\\\"杜文军\\\",             \\\"name_spare\\\":\\\"测试\\\",             \\\"relation\\\":\\\"亲属/配偶姓名\\\",             \\\"relation_spare\\\":\\\"同学/同事\\\"         },         \\\"user_info\\\":{             \\\"\\\":\\\"测试|13858858888\\\",             \\\"city\\\":\\\"南京市\\\",             \\\"company_address\\\":\\\"上海,上海市,崇明县|波司登国际大厦\\\",             \\\"company_city\\\":\\\"重庆市\\\",             \\\"company_name\\\":\\\"上海融家金融信息司\\\",             \\\"hand_id_photo\\\":\\\"http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/45755/1499405652.png\\\",             \\\"id_card\\\":\\\"620522198908140319\\\",             \\\"id_negative\\\":\\\"http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/45755/1503024844649.png\\\",             \\\"id_positive\\\":\\\"http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/45755/1503024831808.png\\\",             \\\"industry\\\":\\\"计算机/互联网\\\",             \\\"living_address\\\":\\\"福建省,福州市,鼓楼区|政府路18号波司登国际大厦14楼\\\",             \\\"name\\\":\\\"宋文华\\\",             \\\"phone\\\":\\\"13122053988\\\",             \\\"telecom_auth\\\":\\\"1\\\"         }     } }";
//		
//		
//		
//		
//		
//		
//	}
//	/**
//	 * 2.1 检测用户接口
//	 */
//	@RequestMapping("/app/test/jiedianqian/checkUser.do")
//	@ResponseBody
//	public JieDianQianResponse checkUser(@RequestBody JSONObject json) {
//		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
//		String sessionId = DateUtil.getSessionId();
//
//		// 第一步：验证请求中参数
//		try {
//			logger.info(sessionId + "  进入用户检验接口,开始接收参数");
//			//String check = JieDianQianUtils.checkOrderPush(json);
//
//			if (CommUtils.isNull(json)) { // 判断是否为空
//				jieDianQianResponse.setCode(-1); // 状态码
//				jieDianQianResponse.setDesc("接受用户信息为空"); // 异常信息
//				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//				return jieDianQianResponse; // 响应pojo
//			}
//
//
//			// 第二步:取参数
//			// String name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "utf-8"); // 姓名 测试转码
//			String name = json.getString("user_name"); // 姓名
//			String phone = json.getString("phone"); // 手机号码
//			String id_number = json.getString("id_number"); // 身份证
//
//			// 第三步：用户检验
//			jieDianQianResponse = jieDianQianService.checkUserInfo(sessionId, name, phone.replace("****", "%"),
//					id_number.replace("****", "%"));
//
//		} catch (Exception e) {
//			logger.error(sessionId + "  执行用户检验接口异常", e);
//			jieDianQianResponse.setCode(-1);
//			jieDianQianResponse.setDesc("系统异常，请稍后再试");
//		}
//
//		logger.info(sessionId + "  结束存量用户检验接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//		return jieDianQianResponse;
//	}
//	
//	/**
//	 * 2.2.测试接收订单信息接口
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/app/test/jiedianqian/createOrder.do")
//	public JieDianQianResponse getOrderInfo(@RequestBody JSONObject json) {
//		String sessionId = DateUtil.getSessionId();
//		logger.info(sessionId + " 进入接收订单信息接口");
//		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
//		String data = "";
//		try {
//			logger.info(sessionId + " 开始验证请求参数");
//			String check = JieDianQianUtils.checkOrderPush(json);
//			//String data=json.getString("data");
//			String id_number="";
//	
//
//			try {
//				JSONObject parseObject = json.getJSONObject("data");
//				
//				JSONObject operatorData  = parseObject.getJSONObject("operator");
//				//byte[] operatorDataByte =  Base64.decode(JSON.toJSONString(operatorData));
//				System.out.println("41111"+operatorData);
//				
//				String operatorDataStr = GzipUtil.uncompress(Base64.decode(JSON.toJSONString(operatorData)), "utf-8"); // 解压运营商数据
//				Operator operator = JSONObject.parseObject(operatorDataStr, Operator.class);
//				String operatorStr=JSON.toJSONString(operator);
//				parseObject.put("operator", operatorStr);
//				String desDataStr=parseObject.toJSONString();
//				OrderInfoRequest orderInfoRequest = JSONObject.parseObject(desDataStr, OrderInfoRequest.class);
//				data = JSON.toJSONString(parseObject);
//				//System.out.println(data);
//				logger.info(sessionId + " 接收的运营商数据：" + JSON.toJSONString(operator));
//				logger.info(sessionId + " 接收的订单信息：" + JSON.toJSONString(orderInfoRequest));
//				
//				UserInfo user = orderInfoRequest.getUser_info();
//				String name =user.getName(); // 姓名
//				String phone = user.getPhone(); // 手机号码
//				 id_number = user.getId_card(); // 身份证
//
//				// 第三步：用户检验
//				jieDianQianResponse = jieDianQianService.checkUserInfo(sessionId, name, phone.replace("****", "%"),
//						id_number.replace("****", "%"));
//				
//				
//				
//				
//			} catch (Exception e) {
//				logger.error(sessionId + "  订单信息异常", e);
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
//			}
//
//			// 创建工单
////			BwOrder order = new BwOrder();
////			String orderNo = OrderUtil.generateOrderNo();
////			order.setOrderNo(orderNo);
////			System.out.println("新订单："+orderNo);
////			order=bwOrderService.findBwOrderByAttr(order);
//			//获取用户检查信息，判断用户是否具有可贷性
//			logger.info("开始检验用户信息");
//			String checkInformation=jieDianQianResponse.getDesc();
//			if(checkInformation.equals("命中黑名单规则")||checkInformation.equals("命中在贷规则")||checkInformation.equals("命中被拒规则")){
//				return jieDianQianResponse;
//			}
//
//			boolean creatOrder=jieDianQianService.savePushOrder(data);//返回创建订单的结果 true:创建成功
//			BwOrder order = new BwOrder();
//			
//			BwBorrower bw = new BwBorrower();
//			bw.setIdCard(id_number);
//			bw = borrowerService.findBwBorrowerByAttr(bw);
//			order=bwOrderService.findOrderIdByBorrwerId(bw.getId());//找出对应的订单
//			String orderNo=order.getOrderNo();//找出订单编号
//			//是否加入存储数据成功的判断，返回信息
//			Map<String, String> hm = new HashMap<String, String>();
//			hm.put("orderId", orderNo);
//			
//			if(creatOrder==false){
//				logger.info(sessionId + "  订单信息异常:该订单号"+orderNo+" 已存在");
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setData(hm);
//				jieDianQianResponse.setDesc("订单创建失败，订单已存在或订单信息为空");
//				logger.info(sessionId + "结束订单信息接收接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//				return jieDianQianResponse;
//			}
//			jieDianQianResponse.setCode(0);
//			jieDianQianResponse.setData(hm);
//			jieDianQianResponse.setDesc("创建成功");
//			
//			
//		} catch (Exception e) {
//			logger.error(sessionId + " 接收订单信息接口异常", e);
//			jieDianQianResponse.setCode(-1);
//			jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
//		}
//
//		logger.info(sessionId + "结束订单信息接收接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//		return jieDianQianResponse;
//
//}
//	/**
//	 * 2.3.提现试算接口
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/app/test/jiedianqian/calculate.do")
//	@ResponseBody
//	public JieDianQianResponse calculateMethod(@RequestBody JSONObject json) {
//		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
//		String sessionId = DateUtil.getSessionId();
//		logger.info(sessionId + " 开始提现试算接口方法");
//		try {
//			logger.info(sessionId + " 开始验证请求参数");
//			// 第一步：效验
//			if (CommUtils.isNull(json)) { // 判断是否为空
//				jieDianQianResponse.setCode(-1); // 状态码
//				jieDianQianResponse.setDesc("接收到的数据为空"); // 异常信息
//				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//				return jieDianQianResponse; // 响应pojo
//			}
//			
//
//			// 第二步：取参数
//			String orderNo = json.getString("order_id");
//			String amonut = json.getString("amount");
//
//			// 第三步：计算
//			jieDianQianResponse = jieDianQianService.loanCalculate(sessionId, amonut);
//
//		} catch (Exception e) {
//			logger.error(sessionId + "执行 贷款试算期接口异常", e);
//			jieDianQianResponse.setCode(-1);
//			jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
//		}
//		logger.info(sessionId + " 结束提现试算接口方法，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//		return jieDianQianResponse;
//	}
//	
//	/**
//	 * 2.5 订单状态查询接口
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/app/test/jiedianqian/orderInfo.do")
//	public JieDianQianResponse pushOrderInfo(@RequestBody JSONObject json) {
//		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
//		String sessionId = DateUtil.getSessionId();
//		logger.info(sessionId + " 开始进入订单查询接口方法");
//
//		try {
//			Map<String, Object> result = new HashMap<String, Object>();
//			logger.info(sessionId + " 开始验证请求参数");
//			// 第一步：效验
//			if (CommUtils.isNull(json)) { // 判断是否为空
//				jieDianQianResponse.setCode(-1); // 状态码
//				jieDianQianResponse.setDesc("接收的订单为空"); // 异常信息
//				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//				return jieDianQianResponse; // 响应pojo
//			}
//			
// 
//			
//
//			String orderId = json.getString("order_id"); // 获取参数
//
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setOrderNo(orderId);
//			bwOrder = this.bwOrderService.findBwOrderByAttr(bwOrder);
//
//			if (CommUtils.isNull(bwOrder)) { // 如果没查到,返回
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("没有查询到订单信息");
//				logger.info(sessionId + " 查询订单信息，返回结果：" + JSON.toJSONString(bwOrder));
//				return jieDianQianResponse;
//			}
//
//			jieDianQianResponse.setCode(0);
//			Integer rejectType = bwOrder.getRejectType(); // 拒绝类型 0:系统拒绝 1:人工拒绝
//			Long id = bwOrder.getId();
//
//			String status = JieDianQianUtils.convertStatus(bwOrder.getStatusId()); // 状态转换
//			result.put("order_id", bwOrder.getOrderNo());
//			result.put("status", status);
//
//			if (!CommUtils.isNull(rejectType)) { // 被拒
//				result.put("approval_amount", "0");
//				result.put("approval_periods", "0");
//			} else { // 通过
//				result.put("approval_periods", "1");
//				result.put("approval_amount", String.valueOf(bwOrder.getBorrowAmount()));
//				result.put("sign_loan_amount", bwOrder.getCreditLimit() == null ? "0" : bwOrder.getCreditLimit() + ""); // 额度
//			}
//
//			result.put("sign_loan_periods", "1"); // 贷款期数
//			result.put("approval_period_days", "30"); // 审批每期天数
//			result.put("approval_days", "30"); // 审批总天数
//			result.put("interest_rate", "0.18"); // 贷款利息率
//			result.put("overdue_rate", "0.01"); // 逾期费率
//
//			Map<String, String> payResult = new HashMap<String, String>(); // repayment_plan 还款计划数据
//			List<Map<String, String>> planMaps = new ArrayList<Map<String, String>>();
//			if ("9".equals(String.valueOf(bwOrder.getStatusId()))
//					|| "13".equals(String.valueOf(bwOrder.getStatusId()))) { // 状态：还款中,逾期
//				BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
//				bwRepaymentPlan.setOrderId(id);
//				bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
//				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//				if (CommUtils.isNull(bwRepaymentPlan)) {
//					result.put("repayment_plan", "");
//				} else {
//					String plan_repayment_time = simpleDateFormat.format(bwRepaymentPlan.getUpdateTime());
//
//					payResult.put("plan_repayment_time", plan_repayment_time); // 计划还款日期
//					payResult.put("amount", bwRepaymentPlan.getRepayCorpusMoney() + ""); // 本期还款本金，单位元 repayCorpusMoney
//					payResult.put("period_fee", "0.18"); // 本期手续（利息）费，单位元
//					payResult.put("period", "1"); // 本期期数
//
//					if ("9".equals(String.valueOf(bwOrder.getStatusId()))) {
//						payResult.put("status", "1");
//					} else if ("6".equals(String.valueOf(bwOrder.getStatusId()))) {
//						payResult.put("status", "2");
//					} else if ("13".equals(String.valueOf(bwOrder.getStatusId()))) {
//						payResult.put("status", "4");
//					}
//
//					if ("4".equals(String.valueOf(bwRepaymentPlan.getRepayStatus()))) { // 展期，续期
//						result.put("status", "14");
//					}
//
//					BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//					bwOverdueRecord.setOrderId(id);
//					bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//
//					if (!CommUtils.isNull(bwOverdueRecord)) { // 如果逾期记录存在
//						double planMoney = DoubleUtil.sub(bwOverdueRecord.getOverdueAccrualMoney(),
//								bwOverdueRecord.getAdvance());
//						payResult.put("overdue_fee", planMoney < 0 ? "0" : planMoney + ""); // 逾期罚款，单位元
//						payResult.put("overdue_day", bwOverdueRecord.getOverdueDay() + ""); // 逾期天数
//						payResult.put("overdue", "1"); // 是否逾期，0未逾期，1逾期
//					} else {
//						payResult.put("overdue", "0");
//					}
//				}
//			} else {
//				result.put("repayment_plan", "");
//			}
//
//			planMaps.add(payResult);
//			result.put("repayment_plan", planMaps);
//			jieDianQianResponse.setData(result);
//			jieDianQianResponse.setDesc("订单状态信息查询成功");
//
//			jieDianQianResponse.setCode(0);
//			jieDianQianResponse.setDesc("成功");
//
//		} catch (Exception e) {
//			logger.error(sessionId + " 订单状态查询接口异常", e);
//			jieDianQianResponse.setCode(-1);
//			jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
//		}
//
//		logger.info(sessionId + " 结束订单状态查询接口方法，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//		return jieDianQianResponse;
//	}
//	/**
//	 * 2.4. 确认要款（提现）接口
//	 * 
//	 * @param jsonObject
//	 * @return
//	 */
//	@RequestMapping("/app/test/jiedianqian/confirm.do")
//	@ResponseBody
//	public JieDianQianResponse confirm(@RequestBody JSONObject json) {
//		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
//		String sessionId = DateUtil.getSessionId();
//		String methodName = "JieDianQianController.confirm";
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		logger.info(sessionId + "   {" + methodName + " }start");
//		try {
//			logger.info(sessionId + " 开始验证请求参数");
//			if (CommUtils.isNull(json)) { // 判断是否为空
//				jieDianQianResponse.setCode(-1); // 状态码
//				jieDianQianResponse.setDesc("接收的订单为空"); // 异常信息
//				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//				return jieDianQianResponse; // 响应pojo
//			}
//
//			// 获取参数
//			String orderId = json.getString("order_id"); // 订单id
//			String loan_amount = json.getString("loan_amount"); // 贷款金额
//			String loan_periods = json.getString("loan_periods"); // 贷款期数
//			
//			//String orderId=json.getString("order_id");
//			//String loan_amount = json.getString("loan_amount");
//			//String loan_periods = json.getString("loan_periods");
//			
//
//			BwOrder bwOrder = new BwOrder(); // 订单
//			bwOrder.setOrderNo(orderId);
//			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder); // 通过第三方传递的订单号,查询我方订单
//
//			if (CommUtils.isNull(bwOrder)) { // 如果没查到,返回
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("没有查询到订单信息");
//				logger.info(sessionId + " 查询订单信息，返回结果：" + JSON.toJSONString(bwOrder));
//				return jieDianQianResponse;
//			}
//			if ("4".equals(bwOrder.getStatusId())) { // 如果没查到,返回
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("请重新确定订单状态处于待签约状态");
//				logger.info(sessionId + " 查询订单信息，返回结果：" + JSON.toJSONString(bwOrder));
//				return jieDianQianResponse;
//			}
//			
//
//			Long borrowerId = bwOrder.getBorrowerId();
//			
//			//判断审核是否通过，是否能借款逻辑
//			
//			// 第一步：比对金额
//			
//			if(CommUtils.isNull(bwOrder.getBorrowAmount())){
//				logger.info("借款人贷款金额为："+bwOrder.getBorrowAmount());
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("借款人贷款金额为空");
//				methodEnd(stopWatch, methodName, "借款人贷款金额为空", jieDianQianResponse);
//				return jieDianQianResponse;
//			}
//			if(CommUtils.isNull(loan_amount)){
//				logger.info("预贷款金额为："+loan_amount);
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("预贷款金额为空");
//				methodEnd(stopWatch, methodName, "预贷款金额为空", jieDianQianResponse);
//				return jieDianQianResponse;
//			}
//			
//			
//			
//			double loan_amountBD =Double.parseDouble(loan_amount);
//			double borrow_amountBD=bwOrder.getBorrowAmount().doubleValue();
//			
//			if(loan_amountBD-borrow_amountBD>0){
//				logger.info("开始验证金额：预借款为"+loan_amountBD+",可借款额度为"+borrow_amountBD);
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("贷款金额超出范围");
//				methodEnd(stopWatch, methodName, "贷款金额超出范围", jieDianQianResponse);
//				return jieDianQianResponse;
//			}
//			
//			// 第三步：判断订单状态是否是待签约（直接调用接口）
//			Integer channelId=bwOrder.getChannel();
//			
//			//获取订单id，查询第三方订单编号
//			String order_id=String.valueOf(bwOrder.getId());
//			String thirdOrderNo=bwOrderRongService.findThirdOrderNoByOrderId(order_id);
//			
//			ThirdResponse tResponse=thirdCommonService.updateSignContract(thirdOrderNo, channelId);//获取调用签约接口的返回值
//			if(tResponse.getCode()==200){
//				jieDianQianResponse.setCode(0);
//				jieDianQianResponse.setDesc("操作成功");
//				jieDianQianResponse.setData("");
//			}else{
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("操作失败");
//				jieDianQianResponse.setData("");
//			}
//
//		} catch (Exception e) {
//			logger.error(sessionId + "{  " + methodName + "  } 异常", e);
//			jieDianQianResponse.setCode(-1);
//			jieDianQianResponse.setDesc("系统异常，请稍后再试");
//		}
//
//		return jieDianQianResponse;
//	}
//	
//	/**
//	 * 2.9. 查询银行卡信息 在合作方页面绑卡时，需要合作方提供银行卡信息查询接口
//	 * 
//	 * @param json
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/app/test/jiedianqian/card-info.do")
//	public JieDianQianResponse cardInfo(@RequestBody JSONObject json) {
//		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
//		String sessionId = DateUtil.getSessionId();
//		Map<String, String> hm = new HashMap<String, String>();
//		logger.info(sessionId + " 开始查询银行卡信息接口方法");
//		try {
//			logger.info(sessionId + " 开始验证请求参数");
//			if (CommUtils.isNull(json)) { // 判断是否为空
//				jieDianQianResponse.setCode(-1); // 状态码
//				jieDianQianResponse.setDesc("接收的订单为空"); // 异常信息
//				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
//				return jieDianQianResponse; // 响应pojo
//			}
//
//
//			// 取参数
//			String orderId = json.getString("order_id"); // B20170905034012489444
//
//			// 通过订单id查询银行卡信息
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setOrderNo(orderId);
//			bwOrder = this.bwOrderService.findBwOrderByAttr(bwOrder);
//
//			if (CommUtils.isNull(bwOrder)) { // 如果没查到,返回
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("没有查询到订单信息");
//				logger.info(sessionId + " 查询订单信息，返回结果：" + JSON.toJSONString(bwOrder));
//				return jieDianQianResponse;
//			}
//
//			// 获取订单中的借款人id，查询银行信息
//			BwBankCard bankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//			if (CommUtils.isNull(bankCard)) { // 如果没查到,返回
//				jieDianQianResponse.setCode(-1);
//				jieDianQianResponse.setDesc("没有查询到银行卡信息");
//				logger.info(sessionId + " 查询银行卡信息，返回结果：" + JSON.toJSONString(bankCard));
//				return jieDianQianResponse;
//			}
//			hm.put("bank_name", bankCard.getBankName()); // 银行名称
//			hm.put("card_no", bankCard.getCardNo()); // 银行卡号
//
//			jieDianQianResponse.setCode(0);
//			jieDianQianResponse.setData(hm);
//			jieDianQianResponse.setDesc("成功");
//
//		} catch (Exception e) {
//			logger.error(sessionId + "执行提现跳转合作方页面绑卡接口", e);
//			jieDianQianResponse.setCode(-1);
//			jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
//		}
//
//		return jieDianQianResponse;
//
//	}
//	
//	
//	
//	
//	
//	
//	
//	/**
//	 * 测试接受用户数据格式是否能接受
//	 * */
//	@ResponseBody
//	@RequestMapping("/app/test/jiedianqian/test.do")
//	public void wa1(HttpServletRequest request){
//		System.out.println("111");
//		String da=request.getParameter("data");
//		//String data="{\"data\":{\"source_order_id\":\"R123\",\"loan_info\":{\"money\":\"1000.00\",\"day\":\"7\"},\"zm\":{\"score\":748,\"openId\":\"268810086958205648146358904\",\"watch_info\":{\"is_matched\":false,\"biz_no\":\"ZM201703143000000289000722167799\",\"success\":true}},\"bank_info\":{\"code\":\"BOC\",\"card_no\":\"6013820800081078139\",\"phone\":\"13564446729\",\"bank_name\":\"中国银行\"},\"user_info\":{\"id_negative\":\"http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/53227427/1489400154668.png\",\"face\":\"http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/53227427/1489992279102.png\",\"address\":\"上海,上海市,杨浦区|11\",\"device_id\":\"000000000000000\",\"phone\":\"13564446729\",\"address_distinct\":\"上海市\",\"id_card\":\"430224198611161822\",\"id_positive\":\"http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/53227427/1489628588.png\",\"marry\":\"未婚\",\"name\":\"谭晶\",\"educate\":\"本科\"},\"user_login_upload_log\":{\"address\":\"上海,上海市,杨浦区|11\",\"latitude\":\"31.30935900816946\",\"longitude\":\"121.51833974762269\"},\"user_contact\":{\"name_spare\":\"张三\",\"mobile\":\"13826508699\",\"name\":\"李四\",\"relation_spare\":\"同事\",\"mobile_spare\":\"13826508699\",\"relation\":\"父母\"},\"address_book\":[{\"mobile\":\"13856239856\",\"name\":\"测试\"},{\"mobile\":\"021801234510086\",\"name\":\"陈旭\"}],\"operator\":{\"smses\":[{\"start_time\":\"2017-03-01 09:42:12\",\"update_time\":\"2017-03-13 16:44:38\",\"subtotal\":\"0.0\",\"place\":\"\",\"init_type\":\"接收\",\"other_cell_phone\":\"8610690017810181\",\"cell_phone\":\"13564446729\"},{\"start_time\":\"2016-12-13 01:00:02\",\"update_time\":\"2017-03-13 16:44:38\",\"subtotal\":\"0.0\",\"place\":\"\",\"init_type\":\"接收\",\"other_cell_phone\":\"861069031324251\",\"cell_phone\":\"13564446729\"}],\"calls\":{\"start_time\":\"2016-07-17 21:55:37\",\"update_time\":\"2016-07-18 10:16:31\",\"use_time\":125,\"subtotal\":0.6,\"place\":\"上海\",\"init_type\":\"主叫\",\"call_type\":\"国内长途\",\"other_cell_phone\":\"134***2182\",\"cell_phone\":\"131***9681\"},\"nets\":[],\"datasource\":\"chinamobilesh\",\"juid\":\"\",\"basic\":{\"update_time\":\"2017-03-13 16:44:38\",\"idcard\":\"430224********182*\",\"reg_time\":\"2010-12-13 00:00:00\",\"real_name\":\"*晶\",\"cell_phone\":\"13564446729\"},\"transactions\":[{\"total_amt\":\"14.0\",\"update_time\":\"2017-03-13 16:44:38\",\"pay_amt\":\"14.0\",\"plan_amt\":\"14.0\",\"bill_cycle\":\"2017-02-01 00:00:00\",\"cell_phone\":\"13564446729\"},{\"total_amt\":\"14.0\",\"update_time\":\"2017-03-13 16:44:38\",\"pay_amt\":\"14.0\",\"plan_amt\":\"14.0\",\"bill_cycle\":\"2017-01-01 00:00:00\",\"cell_phone\":\"13564446729\"},{\"total_amt\":\"14.87\",\"update_time\":\"2017-03-13 16:44:38\",\"pay_amt\":\"14.87\",\"plan_amt\":\"14.0\",\"bill_cycle\":\"2016-12-01 00:00:00\",\"cell_phone\":\"13564446729\"},{\"total_amt\":\"14.0\",\"update_time\":\"2017-03-13 16:44:38\",\"pay_amt\":\"14.0\",\"plan_amt\":\"14.0\",\"bill_cycle\":\"2016-11-01 00:00:00\",\"cell_phone\":\"13564446729\"},{\"total_amt\":\"14.0\",\"update_time\":\"2017-03-13 16:44:38\",\"pay_amt\":\"14.0\",\"plan_amt\":\"14.0\",\"bill_cycle\":\"2016-10-01 00:00:00\",\"cell_phone\":\"13564446729\"},{\"total_amt\":\"14.0\",\"update_time\":\"2017-03-13 16:44:38\",\"pay_amt\":\"14.0\",\"plan_amt\":\"14.0\",\"bill_cycle\":\"2016-09-01 00:00:00\",\"cell_phone\":\"13564446729\"}],\"version\":\"1\",\"token\":\"d451e68e48a54655af023a2e4a4a4bcc\"}}}";
//		//OrderInfoRequest orderInfoRequest = JSONObject.parseObject(da, OrderInfoRequest.class);
//		//System.out.println(orderInfoRequest);
//		System.out.println(da);
//	}
//	
//	
//	private static void testPushOrder() {
//		try {
//			String url = "http://localhost:8080/beadwalletloanapp/app/testjiedianqian/testCreate-order.do";
//			Map<String, String> paramMap = new HashMap<>();
//			paramMap.put("appId", "6FH841BGIH51D6C");
//			OrderInfoRequest orderInfoRequest=new OrderInfoRequest();
//			orderInfoRequest.setSource_order_id("248448244517478");
//			UserInfo userInfo=new UserInfo();
//			userInfo.setId_card("421127199510211517");
//			userInfo.setName("王飞11");
//			userInfo.setCity("武汉市");
//			userInfo.setId_card_address("上海,上海市,杨浦区");
//			userInfo.setId_negative("http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/53227427/1489400154668.png");
//			userInfo.setId_positive("http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/53227427/1489628588.png");
//			userInfo.setHand_id_photo("http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/53227427/1489400154668.png");
//			userInfo.setFace("http://bktest-10010.oss-cn-hangzhou.aliyuncs.com/53227427/1489992279102.png");
//			userInfo.setCompany_name("上海水象金融公司");
//			userInfo.setCompany_address("上海市，杨浦区");
//			userInfo.setIndustry("计算机/互联网");
//			userInfo.setLiving_address("福建省,福州市,鼓楼区|政府路18号波司登国际大厦14楼");
//			userInfo.setPhone("15727158711");
//			userInfo.setMarry("未婚");
//			userInfo.setEducate("本科");
//			userInfo.setRole("工薪族");
//			userInfo.setSalary_range("6000");
//			userInfo.setLoan_usage("国家建设");
//			userInfo.setCompany_city("上海");
//			userInfo.setHiredate("2017年7月12日");
//			userInfo.setCompany_work_year("1");
//			orderInfoRequest.setUser_info(userInfo);
//			
//			BankInfo bankInfo=new BankInfo();
//			bankInfo.setCard_no("6215581818002985950");
//			bankInfo.setPhone("15727158711");
//			bankInfo.setBank_name("工商银行");
//			bankInfo.setCode("ICBKCNBJ");
//			orderInfoRequest.setBank_info(bankInfo);
//			
//			LoanInfo loanInfo =new LoanInfo();
//			loanInfo.setDay("30");
//			loanInfo.setMoney("1000");
//			orderInfoRequest.setLoan_info(loanInfo);
//			
//			UserLoginUploadLog userLoginUploadLog=new UserLoginUploadLog();
//			userLoginUploadLog.setAddress("上海,上海市,杨浦区|11");
//			userLoginUploadLog.setLatitude("31.30935900816946");
//			userLoginUploadLog.setLongitude("121.51833974762269");
//			orderInfoRequest.setUser_login_upload_log(userLoginUploadLog);
//			
//			List<AddressBook> addressBooks=new ArrayList<>();
//			for(int i=0;i<5;i++){
//				AddressBook addressBook=new AddressBook();
//				addressBook.setMobile("1590000000"+i);
//				addressBook.setName("admin"+i);
//				addressBooks.add(addressBook);
//			}
//			orderInfoRequest.setAddress_book(addressBooks);
//			String data = JSON.toJSONString(orderInfoRequest);
//			paramMap.put("request", data);
//			
//			HttpClientHelper.post(url, "utf-8", paramMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private BwRepaymentPlan findBwRepaymentPlanByAttrProxy(BwRepaymentPlan bwRepaymentPlan) {
//		bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
//		return bwRepaymentPlan;
//	}
//	private void methodEnd(StopWatch stopWatch, String methodName, String message, JieDianQianResponse resp) {
//		stopWatch.stop();
//		logger.info(methodName + " end,costTime=" + stopWatch.getTotalTimeMillis() + "," + message + ",resp=" + resp);
//		logger.remove();
//	}
//	
//	
//}