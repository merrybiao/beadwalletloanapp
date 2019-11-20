package com.waterelephant.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.activity.service.ActivityService;
import com.waterelephant.annotation.LockAndSyncRequest;
import com.waterelephant.baiqishi.service.BaiQiShiService;
import com.waterelephant.dto.OrderAuthDto;
import com.waterelephant.dto.PersonWorkInfoDto;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.installment.service.OrderService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * 查询四要素认证记录
 * 
 * @Title: AppBwNewVerController.java
 * @Description:
 * @version V1.0
 */

@Controller
@RequestMapping("/app/new/ver")
public class AppBwNewVerController {

	private Logger logger = Logger.getLogger(AppBwNewVerController.class);

	@Autowired
	private BaiQiShiService baiQiShiService;

	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;

	/**
	 * 查询工单认证状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findOrderAuthStatus.do")
	public AppResponseResult findOrderAuthStatus(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String orderId = request.getParameter("orderId");
			String bwId = request.getParameter("bwId");
			if (CommUtils.isNull(orderId) || CommUtils.isNull(bwId)) {
				respResult.setCode("1001");
				respResult.setMsg("工单ID或借款人ID不能为空");
				return respResult;
			}
			String authChannel = request.getParameter("authChannel");
			if (CommUtils.isNull(authChannel)) {
				respResult.setCode("1001");
				respResult.setMsg("来源渠道不能为空");
				return respResult;
			}
			Long order_id = Long.parseLong(orderId);
			Long bw_id = Long.parseLong(bwId);
			Integer auth_channel = Integer.parseInt(authChannel);
			BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(bw_id);
			if (bwZmxyGrade != null) {
				Date updateTime = bwZmxyGrade.getUpdateTime();
				Date now = new Date();
				int interval = MyDateUtils.getDaySpace(updateTime, now);
				BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(order_id, 4);
				if (interval > 30) {
					if (bwOrderAuth != null) {
						bwOrderAuthService.deleteBwOrderAuth(bwOrderAuth);
					}
				} else {
					if (bwOrderAuth == null) {
						bwOrderAuth = new BwOrderAuth();
						bwOrderAuth.setAuth_channel(auth_channel);
						bwOrderAuth.setAuth_type(4);
						bwOrderAuth.setCreateTime(now);
						bwOrderAuth.setOrderId(order_id);
						bwOrderAuth.setUpdateTime(now);
						bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
					}
				}
			}
			List<OrderAuthDto> dtoList = new ArrayList<OrderAuthDto>();
			dtoList.add(new OrderAuthDto(1, 0));
			dtoList.add(new OrderAuthDto(2, 0));
			dtoList.add(new OrderAuthDto(3, 0));
			dtoList.add(new OrderAuthDto(4, 0));
			List<Integer> list = bwOrderAuthService.findBwOrderAuth(order_id);
			if (CommUtils.isNull(list)) {
				respResult.setResult(dtoList);
			} else {
				for (OrderAuthDto orderAuthDto : dtoList) {
					if (list.contains(orderAuthDto.getAuthType())) {
						orderAuthDto.setAuthStatus(1);
					}
				}
				respResult.setResult(dtoList);
			}
			respResult.setCode("0000");
			respResult.setMsg("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			respResult.setCode("1001");
			respResult.setMsg("系统异常");
		}
		return respResult;
	}

	/**
	 * 查询个人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findPersonInfo.do")
	public AppResponseResult findPersonInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String bwId = request.getParameter("bwId");
			String productType = request.getParameter("productType");// 产品类型
			if (CommUtils.isNull(bwId)) {
				respResult.setCode("1001");
				respResult.setMsg("借款人ID不能为空");
				return respResult;
			}
			if (CommUtils.isNull(productType)) {
				productType = "1";
			}
			Long borrowerId = Long.parseLong(bwId);
			// 查询个人信息
			BwPersonInfo personInfo = bwOrderAuthService.findBwPersonInfo(borrowerId, productType);
			// 查询工作信息
			BwWorkInfo workInfo = bwOrderAuthService.findBwWorkInfo(borrowerId, productType);
			PersonWorkInfoDto dto = new PersonWorkInfoDto();
			if (personInfo != null) {
				dto.setCityName(personInfo.getCityName());
				dto.setRelationName(personInfo.getRelationName());
				dto.setRelationPhone(personInfo.getRelationPhone());
				dto.setUnrelationName(personInfo.getUnrelationName());
				dto.setUnrelationPhone(personInfo.getUnrelationPhone());
				dto.setEmail(personInfo.getEmail());
				dto.setAddress(StringUtil.toString(personInfo.getAddress()));
			}
			if (workInfo != null) {
				dto.setComName(workInfo.getComName());
				dto.setIndustry(workInfo.getIndustry());
				dto.setWorkYears(workInfo.getWorkYears());
			}
			logger.info("个人信息为 ： " + dto);
			respResult.setCode("0000");
			respResult.setMsg("查询成功");
			respResult.setResult(dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			respResult.setCode("1001");
			respResult.setMsg("系统异常");
		}
		return respResult;
	}

	/**
	 * 保存个人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/savePersonInfo.do")
	@LockAndSyncRequest
	public AppResponseResult savePersonInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		// try {
			respResult.setCode("1001");
			respResult.setMsg("请更新最新版本");
			return respResult;
//			String orderId = request.getParameter("orderId");
//			if (CommUtils.isNull(orderId)) {
//				respResult.setCode("1001");
//				respResult.setMsg("工单ID不能为空");
//				return respResult;
//			}
//			logger.info("savePersonInfo传入参数:orderId=" + orderId);
//			String cityName = request.getParameter("cityName");
//			String relationName = request.getParameter("relationName");
//			String relationPhone = request.getParameter("relationPhone");
//			String unrelationName = request.getParameter("unrelationName");
//			String unrelationPhone = request.getParameter("unrelationPhone");
//			String comName = request.getParameter("comName");
//			String industry = request.getParameter("industry");
//			String workYears = request.getParameter("workYears");
//			String authChannel = request.getParameter("authChannel");
//			String email = request.getParameter("email");
//			String address = request.getParameter("address");
//			address = CommUtils.isNull(address) ? "" : address;
//
//			if (CommUtils.isNull(cityName) || CommUtils.isNull(relationName) || CommUtils.isNull(relationPhone)
//					|| CommUtils.isNull(unrelationName) || CommUtils.isNull(unrelationPhone)
//					|| CommUtils.isNull(comName) || CommUtils.isNull(industry) || CommUtils.isNull(workYears)
//					|| CommUtils.isNull(authChannel)) {
//				respResult.setCode("1001");
//				respResult.setMsg("必填信息不能为空");
//				return respResult;
//			}
//			logger.info("当前定位的城市名为：" + cityName);
//			// 校验工作年限
//			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(Long.valueOf(orderId));
//			if (!CommUtils.isNull(borrower)) {
//				if (StringUtil.toString(borrower.getPhone()).equals(relationPhone)
//						|| StringUtil.toString(borrower.getPhone()).equals(unrelationPhone)
//						|| unrelationPhone.equals(relationPhone)) {
//					respResult.setCode("1003");
//					respResult.setMsg("不能提交相同的手机号码，请重新提交");
//					return respResult;
//				}
//				if (StringUtil.toString(borrower.getName()).equals(relationName)
//						|| StringUtil.toString(borrower.getName()).equals(unrelationName)
//						|| relationName.equals(unrelationName)) {
//					respResult.setCode("1004");
//					respResult.setMsg("紧急联系人不能为同一个人，请重新提交");
//					return respResult;
//				}
//			}
//			logger.info("savePersonInfo传入参数:orderId=" + orderId + ",借款人信息" + borrower.toString());
//			logger.info("借款人选择的工作年限为：" + workYears);
//			workYears = bwOrderAuthService.checkWorkYears(borrower.getAge(), workYears);
//			logger.info("校验后的工作年限为：" + workYears);
//			if ("UNKNOWN".equals(workYears)) {
//				respResult.setCode("1002");
//				respResult.setMsg("工作年限选项不正确");
//				return respResult;
//			}
//			bwOrderAuthService.savePensonInfo(orderId, cityName, relationName, relationPhone, unrelationName,
//					unrelationPhone, comName, industry, workYears, authChannel, email, address);
//			respResult.setCode("0000");
//			respResult.setMsg("操作成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//			respResult.setCode("1001");
//			respResult.setMsg("系统异常");
//		}
//		return respResult;
	}

	/**
	 * 保存拍照信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/savePicInfo.do")
	@LockAndSyncRequest
	public AppResponseResult savePicInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		respResult.setCode("1001");
		respResult.setMsg("请更新最新版本");
		return respResult;
	}

	/**
	 * 提交认证信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/submitOrderAuthInfo.do")
	@LockAndSyncRequest
	public AppResponseResult submitOrderAuthInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
		String orderId = request.getParameter("orderId");
		logger.info("【AppBwNewVerController.submitOrderAuthInfo】orderId:" + orderId + ",paramMap=" + paramMap);
		AppResponseResult respResult = new AppResponseResult();
		respResult.setCode("1001");
		respResult.setMsg("请更新最新版本");
		return respResult;
	}

}
