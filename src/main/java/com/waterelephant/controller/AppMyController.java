package com.waterelephant.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.entity.request.AppSignCardReqData;
import com.beadwallet.entity.response.AppSignCardRspData;
import com.beadwallet.entity.response.ResponsePayResult;
import com.beadwallet.servcie.BeadwalletService;
import com.beadwallet.service.serve.BeadWalletRongOrderService;
import com.beadwallet.service.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;
import com.waterelephant.activity.service.ActivityService;
import com.waterelephant.channel.service.PartnerService;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.constants.OrderStatusConstant;
import com.waterelephant.constants.ParameterConstant;
import com.waterelephant.dto.AccountInfoDto;
import com.waterelephant.dto.BwOrderInfoDto;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;
import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBankCardNew;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwCheckRecord;
import com.waterelephant.entity.BwFeedbackInfo;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.entity.CmsContent;
import com.waterelephant.entity.FuiouCity;
import com.waterelephant.faceID.entity.FaceIDOrderAuthDto;
import com.waterelephant.service.ActivityInfoService;
import com.waterelephant.service.BwBankCardChangeService;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwBorrowerWealthService;
import com.waterelephant.service.BwCheckRecordService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOrderTemService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.ExtraConfigService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwFeedbackInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwOrderXuDaiService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.BankInfoUtils;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.JsonDoubleProcessorImpl;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.MyJSONUtil;
import com.waterelephant.utils.PageUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.vo.orderVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import tk.mybatis.mapper.entity.Example;

/**
 * 我的页面
 * 
 * @author lujilong
 *
 */
@Controller
@RequestMapping("/app/my")
public class AppMyController {

	private Logger logger = Logger.getLogger(AppMyController.class);
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwOrderTemService bwOrderTemService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private IBwFeedbackInfoService bwFeedbackInfoService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwOrderXuDaiService bwOrderXuDaiService;
	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private BwBankCardChangeService bwBankCardChangeService;
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BwBlacklistService bwBlacklistService;
	@Autowired
	private ExtraConfigService extraConfigService;
	@Autowired
	private BwBorrowerWealthService bwBorrowerWealthService;
	@Autowired
	private BwCheckRecordService bwCheckRecordService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;

	/**
	 * 新APP：根据工单id查询展期
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRepaymentPlanAndDetailByOrderId.do")
	public AppResponseResult getRepaymentPlanAndDetailByOrderId(HttpServletRequest request,
			HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String orderId = request.getParameter("orderId");// 订单id
			if (CommUtils.isNull(orderId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("工单id为空");
				return respResult;
			}

			List<Map<String, Object>> list = bwRepaymentPlanService
					.findBwRepaymentPlanAndDetailByOrderId(Long.parseLong(orderId));
			respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
			respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			respResult.setResult(list);
			return respResult;

		} catch (Exception e) {
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			logger.error(ActivityConstant.ErrorMsg.CATCH_ERROR, e);
			return respResult;
		}
	}

	/**
	 * 新APP个人中心：上传个人头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadBorrowerHeadImg.do")
	public AppResponseResult uploadBorrowerHeadImg(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String bwId = request.getParameter("bwId");// 借款人Id
			String headImgUrl = request.getParameter("headImgUrl");// 我的头像保存在阿里云服务器地址
			if (CommUtils.isNull(headImgUrl)) {
				respResult.setCode("201");
				respResult.setMsg("上传个人头像失败，请重新上传图片");
				return respResult;
			}
			if (CommUtils.isNull(bwId)) {
				respResult.setCode("202");
				respResult.setMsg("bwId不能为空");
				logger.info("bwId为空");
				return respResult;
			}

			// 修改借款人头像url
			BwBorrower borrower = new BwBorrower();
			// borrower.setHeadImgUrl(headImgUrl);
			borrower.setId(Long.parseLong(bwId));
			borrower.setUpdateTime(new Date());
			int update = bwBorrowerService.updateBwBorrowerSelective(borrower);
			if (update < 0) {
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg("上传我的头像失败，请重新上传图片");
				respResult.setResult(false);
				logger.info("上传我的头像失败，请重新上传图片");
				return respResult;
			}
			respResult.setCode("000");
			respResult.setMsg("上传我的头像成功！");
			respResult.setResult(true);
			logger.info("上传我的头像成功");
			return respResult;
		} catch (Exception e) {
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			logger.error(ActivityConstant.ErrorMsg.CATCH_ERROR, e);
			return respResult;
		}
	}

	/**
	 * 新APP个人中心：查询用户名单状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBorrowSort.do")
	public AppResponseResult getBorrowSort(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String bwId = request.getParameter("bwId");// 借款人Id
			logger.info("接收到的参数:borrowId=" + bwId);
			if (CommUtils.isNull(bwId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("bwId不能为空");
				logger.info("bwId不能为空");
				return respResult;
			}
			// 获取借款人信息
			BwBorrower borrower = new BwBorrower();
			borrower.setId(Long.parseLong(bwId));
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			if (CommUtils.isNull(borrower)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("借款人不存在");
				logger.error("借款人不存在");
				return respResult;
			}
			/// 获取最新的工单
			BwOrder bwOrder = bwOrderService.findNewBwOrderByBwIdNotLimitStatusID(bwId);
			if (CommUtils.isNull(borrower)) {
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg("未生成工单");
				return respResult;
			}
			int sort = 0;
			sort = bwBlacklistService.findAdoptTypeByCard(borrower.getIdCard());// 查询是否黑名单 灰名单
			// 查询记录并返回
			BwRejectRecord record = new BwRejectRecord();
			logger.info("工单Id：" + bwOrder.getId());
			record.setOrderId(bwOrder.getId());
			record = bwRejectRecordService.findBwRejectRecordByAtta(record);
			if (!CommUtils.isNull(record)) {
				if (sort == 1) {
					sort = 1;
				}
				if (sort != 1) {
					Integer rejectType = record.getRejectType();
					if (rejectType == 1) {
						sort = 1;
					}
					if (rejectType == 0) {
						sort = 2;
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("sort", new Integer(sort));
			logger.info("借款人的状态 sort为：" + sort + "===0普通用户1：黑名单，2：灰名单拒，3：白名单");
			respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
			respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			respResult.setResult(map);
			return respResult;
		} catch (Exception e) {
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			logger.error(ActivityConstant.ErrorMsg.CATCH_ERROR, e);
			return respResult;
		}
	}

	/**
	 * 新APP个人中心：帮助中心
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getHelpCenter.do")
	public AppResponseResult getHelpCenter(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String channelId = request.getParameter("channelId");// 渠道id
			logger.info("接收到的参数:channelId=" + channelId);
			if (CommUtils.isNull(channelId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("channelId不能为空");
				logger.info("channelId不能为空");
				return respResult;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("channelId", channelId);
			List<Map<String, Object>> contentList = partnerService.getHelpContentList(map);
			if (CommUtils.isNull(contentList)) {
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg("为查询到任何记录");
				return respResult;
			}
			String[] excludes = {};
			JSONArray listToJson = MyJSONUtil.listToJson(contentList, excludes, true);
			respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
			respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			respResult.setResult(listToJson);
			return respResult;
		} catch (Exception e) {
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			logger.error(ActivityConstant.ErrorMsg.CATCH_ERROR, e);
			e.printStackTrace();
			return respResult;
		}
	}

	/**
	 * 新APP个人中心：查询用户所有劵
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getListCoupon.do")
	public AppResponseResult getListCoupon(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String bwId = request.getParameter("bwId");// 借款人Id
			logger.info("接收到的参数:borrowId=" + bwId);
			if (CommUtils.isNull(bwId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("bwId不能为空");
				result.setResult(false);// 处理失败
				logger.error("bwId不能为空");
				return result;
			}
			// 查询用户所有劵
			Object object = activityService.findListCoupon(Integer.parseInt(bwId));
			if (null == object) {
				result.setCode(ActivityConstant.ErrorCode.SUCCESS);
				result.setMsg("未查询到任何优惠券记录 ！");
				logger.info("未查询到任何优惠券记录 ！");
				return result;
			} else {
				logger.info("查询用户所有劵：" + object.toString());
				JSONObject objectToJson = MyJSONUtil.objectToJson(object, new String[0], true);
				result.setCode(ActivityConstant.ErrorCode.SUCCESS);
				result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
				result.setResult(true);// 处理成功
				result.setResult(objectToJson);
				logger.info("处理成功");
				return result;
			}

		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			result.setResult(false);// 处理失败
			logger.error("服务器异常", e);
			return result;
		}
	}

	/**
	 * 新APP个人中心：查看活动详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getActivityById.do")
	public AppResponseResult getActivityById(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String activityId = request.getParameter("activityId");
			logger.info("============获得活动id:" + activityId);
			if (CommUtils.isNull(activityId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("活动id为空");
				return respResult;
			}
			ActivityInfo activityInfo = new ActivityInfo();
			activityInfo.setActivityId(Integer.parseInt(activityId));
			activityInfo = activityInfoService.findActivityInfoByEntry(activityInfo);
			String[] excludes = {};
			net.sf.json.JSONObject objectToJson = MyJSONUtil.objectToJson(activityInfo, excludes, true);
			respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
			respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			respResult.setResult(objectToJson);
			return respResult;
		} catch (Exception e) {
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			logger.error(e, e);
			e.printStackTrace();
			return respResult;
		}
	}

	/**
	 * 新APP个人中心：查询所有活动时间按时间倒序排序
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getListActivityInfo.do")
	public AppResponseResult getListActivityInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			Object object = activityInfoService.findListActivityInfo();
			respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
			respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			respResult.setResult(object);
			return respResult;
		} catch (Exception e) {
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			logger.error(e, e);
			e.printStackTrace();
			return respResult;
		}
	}

	/**
	 * 新APP个人中心：跳转到重新绑定银行卡页面 （满足条件： 1：不处于借款状态2：一个月内最多更换一次银行卡）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goToBandingBwBankCard.do")
	public AppResponseResult goToBandingBwBankCard(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String bwId = request.getParameter("bwId");
			logger.info("============获取提现卡===借款人id:" + bwId);
			if (CommUtils.isNull(bwId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("借款人id为空");
				return respResult;
			}

			// 查询所有工单获取最近一期的工单
			// BwOrder bwOrder = bwOrderService.findNewBwOrderByBwIdNotLimitStatusID(bwId);
			// if (CommUtils.isNull(bwOrder)) {
			// respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			// respResult.setMsg("借款人未生成工单");
			// return respResult;
			// }

			// 查询所有工单，单期和分期
			List<BwOrder> list = bwOrderService.fandAllOrderBy(bwId);
			for (BwOrder bwOrder : list) {
				// 工单状态为2或者3的时候不许绑卡
				Long statusId = bwOrder.getStatusId();
				logger.info("借款人的工单状态为：" + statusId);
				if (statusId == 2 || statusId == 3) {
					respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
					respResult.setMsg("正在审核中，暂不支持重新绑卡!");
					respResult.setResult(false);
					return respResult;
				}
			}

			// 从配置表中获得银行卡的绑定次数
			String code = ParameterConstant.BANK_CARD_BINDING_TIME;// 绑定银行的code
			String count = extraConfigService.findCountExtraConfigByCode(code);// 银行绑定次数
			int changeCount = bwBankCardChangeService.findBwBankCardChangeByBorrowid(Long.parseLong(bwId));// 通过借款人id查询出银行卡绑定记录
			if (changeCount < Integer.parseInt(count)) {// 修改次数小于等于银行卡绑定次数
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg("可以进行银行卡重绑");
				respResult.setResult(true);
				return respResult;
			}

			respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
			respResult.setMsg("每月只能重新绑定" + count + "次银行卡 ！");
			respResult.setResult(false);
			return respResult;
		} catch (Exception e) {
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			logger.error(e, e);
			e.printStackTrace();
			return respResult;
		}
	}

	/**
	 * 新APP个人中心：获取银行卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBwBankCard.do")
	public AppResponseResult getBwBankCard(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		logger.info("============获取提现卡===借款人id:" + bwId);
		try {
			if (CommUtils.isNull(bwId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("借款人id为空");
				return respResult;
			}
			BwBorrower bwBorrower = new BwBorrower();
			bwBorrower.setId(Long.valueOf(bwId));
			BwBorrower bw = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
			if (CommUtils.isNull(bw)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("借款人不存在");
				return respResult;
			}

			BwBankCard card = bwBankCardService.findBwBankCardByBorrowerId(Long.valueOf(bwId));
			if (CommUtils.isNull(card)) {
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg("借款人未绑定银行卡 ！");
				respResult.setResult(null);
				return respResult;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("bankName", "");
			map.put("bankCardNo", "");
			map.put("bankCode", "");
			if (!CommUtils.isNull(card)) {
				logger.info("============获取提现卡信息====:" + card.toString());
				String bankName = card.getBankName();
				bankName = bankName.split("·")[0];
				map.put("bankName", bankName);// 银行名称
				String cardNo = card.getCardNo();
				String bankCardNo = cardNo.substring(cardNo.length() - 4, cardNo.length());
				bankCardNo = "***** ***** ***** " + bankCardNo;// 银行后四位
				map.put("bankCardNo", bankCardNo);
				map.put("bankCode", card.getBankCode());
			}
			map.put("bankImgUrl", getBankImgUrl(map.get("bankCode") + ""));
			respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
			respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			respResult.setResult(map);
			return respResult;
		} catch (Exception e) {
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			logger.error(e, e);
			e.printStackTrace();
			return respResult;
		}
	}

	private String getBankImgUrl(String bankCode) {
		if (bankCode != null) {
			bankCode = bankCode.trim();
		}
		String pageUrl = ResourceBundle.getBundle("config").getString("page_url");
		String pageProjectName = ResourceBundle.getBundle("config").getString("page_project_name");
		StringBuilder bankImgUrlSB = new StringBuilder();
		bankImgUrlSB.append(pageUrl);
		bankImgUrlSB.append(pageProjectName);
		bankImgUrlSB.append("/");
		Map<String, String> bankMap = new HashMap<String, String>();
		bankMap.put("0105", "img/bank/jiansheBank.png");
		bankMap.put("0303", "img/bank/guangdaBank.png");
		bankMap.put("0308", "img/bank/zhaoshangBank.png");
		bankMap.put("0103", "img/bank/nongyeBank.png");
		bankMap.put("0301", "img/bank/jiaotongBank.png");
		bankMap.put("0307", "img/bank/pinganBank.png");
		bankMap.put("0310", "img/bank/pudongBank.png");
		bankMap.put("0403", "img/bank/youzhengBank.png");
		bankMap.put("0304", "img/bank/huaxiaBank.png");
		bankMap.put("0305", "img/bank/minshengBank.png");
		bankMap.put("0104", "img/bank/zhongguoBank.png");
		bankMap.put("0102", "img/bank/gongshangBank.png");
		bankMap.put("0306", "img/bank/guangfaBank.png");
		bankMap.put("0309", "img/bank/xingyeBank.png");
		bankMap.put("0302", "img/bank/zhongxinBank.png");
		bankImgUrlSB.append(bankMap.get(bankCode));
		return bankImgUrlSB.toString();
	}

	/**
	 * 新APP个人中心：查看我的借款记录详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBwRepaymentPlansByOrderId.do")
	public AppResponseResult getBwRepaymentPlansByOrderId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String orderId = request.getParameter("orderId");// 工单id
			if (CommUtils.isNull(orderId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("工单id不能为空");
				return respResult;
			}
			logger.info("工单id为：" + orderId);
			BwOrder bwOrder = new BwOrder();
			bwOrder.setId(Long.parseLong(orderId));
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);// 获得工单
			if (CommUtils.isNull(bwOrder)) {
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg("订单不存在");
				return respResult;
			}
			respResult = bwOrderService.getBorrowRcprdVP(bwOrder);

			return respResult;
		} catch (Exception e) {
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			logger.error(e, e);
			e.printStackTrace();
			return respResult;
		}
	}

	/**
	 * 新APP个人中心：查看我的借款记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getListBwRepaymentPlansByBorrowId.do")
	public AppResponseResult getListBwRepaymentPlansByBorrowId(HttpServletRequest request,
			HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {

			String bwId = request.getParameter("bwId");
			logger.info("借款人bwId为：" + bwId);
			if (CommUtils.isNull(bwId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("借款人ID不能为空");
				return respResult;
			}
			// 获取借款人信息
			BwBorrower borrower = new BwBorrower();
			borrower.setId(Long.parseLong(bwId));
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			if (CommUtils.isNull(borrower)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("借款人不存在");
				return respResult;
			}
			BwOrder bwOrder = new BwOrder();
			bwOrder.setBorrowerId(borrower.getId());
			/// 查询出所有的工单
			List<orderVO> list = bwOrderService.getBwOrderByStatusId(bwId);
			if (CommUtils.isNull(list)) {
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg("用户不存在历史借款记录 ！");
				respResult.setResult(null);
				return respResult;
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.lang.Double.class, new JsonDoubleProcessorImpl());
			JSONArray fromObject = JSONArray.fromObject(list, jsonConfig);
			respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
			respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			logger.info(fromObject);
			respResult.setResult(fromObject);
			return respResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
			return respResult;
		}
	}

	/**
	 * 新APP个人中心：我的资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBwBorrowerByBorrowerId.do")
	public AppResponseResult getBwBorrowerByBorrowerId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String bwId = request.getParameter("bwId");
			logger.info("借款人bwId为：" + bwId);
			if (CommUtils.isNull(bwId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("借款人ID不能为空");
				return respResult;
			}
			// 获取借款人信息
			BwBorrower borrower = new BwBorrower();
			borrower.setId(Long.parseLong(bwId));
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			if (CommUtils.isNull(borrower)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("借款人不存在");
				return respResult;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("operators", 0);// 运行商认证
			map.put("borrowMsg", 0);// 个人信息认证
			map.put("borrowCart", 0);// 身份证认证
			map.put("zmxy", 0);// 芝麻信用认证
			map.put("sort", "");// 名单认证
			map.put("photoState", "");
			map.put("creditLimit", 0L);
			String checkMsg = "";// 审核消息
			boolean canUpdate = false;// 是否能修改
			boolean canShowDetail = false;// 是否显示详情
			String detailMsg = "";// 审核详情
			String checkStatus = "";// 审核状态
			map.put("checkMsg", OrderStatusConstant.ORDER_CHECK_BEFORE_MSG);
			map.put("canUpdate", false);
			map.put("canShowDetail", false);
			map.put("detailMsg", "");
			map.put("checkStatus", "");
			/// 获取最新的工单
			BwOrder bwOrder = bwOrderService.findNewBwOrderByBwIdNotLimitStatusID(bwId);
			if (CommUtils.isNull(bwOrder)) {
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
				respResult.setResult(map);
				return respResult;
			} else {
				Long statusId = bwOrder.getStatusId();
				Long order_id = bwOrder.getId();
				Long bw_id = Long.parseLong(bwId);
				Integer auth_channel = borrower.getChannel();

				// 获取芝麻信用 大于30天删除芝麻信用
				BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(bw_id);
				if (bwZmxyGrade != null) {
					Date updateTime = bwZmxyGrade.getUpdateTime();
					Date now = new Date();
					int interval = MyDateUtils.getDaySpace(updateTime, now);
					BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(order_id, 4);
					if (interval > 30) {
						logger.info("芝麻信用认证间隔时间为：" + interval);
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

				// 以前表未使用blacklist记录黑名单，先查询blacklist判断用户是否是黑名单
				int sort = bwBlacklistService.findAdoptTypeByCard(borrower.getIdCard());// 查询是否黑名单 灰名单
				// 查询记录并返回
				BwRejectRecord record = new BwRejectRecord();
				logger.info("工单Id：" + order_id);
				record.setOrderId(order_id);
				record = bwRejectRecordService.findBwRejectRecordByAtta(record);
				if (!CommUtils.isNull(record)) {
					if (sort != 1) {
						Integer rejectType = record.getRejectType();
						if (rejectType == 1) {
							sort = 2;
						}
						if (rejectType == 0) {
							sort = 1;
						}
					}
				}
				logger.info("借款人的状态 sort为：" + sort + "===0普通用户1：黑名单，2：灰名单拒，3：白名单");

				// 审核中
				if (statusId == 1) {
					checkStatus = "0";
					checkMsg = OrderStatusConstant.ORDER_CHECK_BEFORE_MSG;
				}
				// 审核中
				if (statusId == 2 || statusId == 3) {
					checkMsg = OrderStatusConstant.ORDER_CHECK_LODING_MSG;
					checkStatus = "1";
				}
				// 拒绝
				if (statusId == 7) {
					checkStatus = "2";
					if (sort == 1) {// 黑名单
						checkMsg = OrderStatusConstant.ORDER_CHECK_FAILURE_MSG;
					} else if (sort == 2) {// 灰名单
						Date whiteTime = bwOrderService.findBorrowWhiteTimeByBorrowId(bwId);// 获得灰名单的拒绝90天后的时间
						checkMsg = OrderStatusConstant.ORDER_CHECK_FAILURE_MSG;
						if (!CommUtils.isNull(whiteTime)) {
							if (MyDateUtils.getDaySpace(whiteTime, new Date()) >= 0) {
								canUpdate = true;
								detailMsg = "因系统评分不足，您的审核未通过！您可以在"
										+ DateUtil.getDateString(whiteTime, DateUtil.yyyy_MM_dd) + "以后重新认证！";
							} else {
								canShowDetail = true;
								detailMsg = "因系统评分不足，您的审核未通过！您可以在"
										+ DateUtil.getDateString(whiteTime, DateUtil.yyyy_MM_dd) + "以后重新认证！";
							}
						}
					}
				}
				// 撤回
				if (statusId == 8) {
					checkStatus = "3";
					checkMsg = OrderStatusConstant.ORDER_CHECK_BACK_MSG;
					canUpdate = true;
					canShowDetail = true;
					BwCheckRecord checkRecord = bwCheckRecordService
							.findNewWithdrawByOrderId(StringUtil.toString(bwOrder.getId()));
					if (null != checkRecord) {
						detailMsg = StringUtil.toString(checkRecord.getComment());
					}
				}
				// 成功
				if (statusId == 4 || statusId == 5 || statusId == 9 || statusId == 11 || statusId == 12
						|| statusId == 13 || statusId == 14) {
					checkStatus = "4";
					checkMsg = OrderStatusConstant.ORDER_CHECK_SUCCEED_MSG;
				}
				// 结束
				if (statusId == 6) {
					checkStatus = "5";
					checkMsg = OrderStatusConstant.ORDER_CHECK_OVER_MSG;
				}
				map.put("sort", sort);
				map.put("canUpdate", canUpdate);
				map.put("canShowDetail", canShowDetail);
				map.put("checkMsg", checkMsg);
				map.put("detailMsg", detailMsg);
				map.put("checkStatus", checkStatus);// 审核状态
				Long creditLimit = bwOrder.getCreditLimit();
				creditLimit = (creditLimit == null) ? 0L : creditLimit;
				map.put("creditLimit", creditLimit);// 设置额度
				List<FaceIDOrderAuthDto> list = bwOrderAuthService.findBwOrderAuthAndPhotoState(order_id);
				if (list.size() > 0) {
					for (FaceIDOrderAuthDto faceIDOrderAuthDto : list) {
						Integer integer = faceIDOrderAuthDto.getAuthType();
						if (integer.equals(1)) {
							map.put("operators", "1");
						}
						if (integer.equals(2)) {
							map.put("borrowMsg", "1");
						}
						if (integer.equals(3)) {
							map.put("borrowCart", "1");
							map.put("photoState", StringUtil.toString(faceIDOrderAuthDto.getPhotoState()));// 设置活体
						}
						if (integer.equals(4)) {
							map.put("zmxy", "1");
						}
					}
				}

				logger.info(" 运行商认证为  " + map.get("operators") + "    个人信息认证为  " + map.get("borrowMsg") + "    身份证认证为  "
						+ map.get("borrowCart") + "    芝麻信用认证为  " + map.get("zmxy") + "，1代表已认证，0代表未认证");
				respResult.setResult(map);
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
				return respResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
		}

		return respResult;
	}

	/**
	 * 获取借款人基本信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findBwBorrowerByBwId.do")
	public AppResponseResult findBwBorrowerByBwId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("601");
			result.setMsg("借款人id为空");
			return result;
		}
		BwBorrower bb = new BwBorrower();
		bb.setId(Long.parseLong(bwId));
		bb = bwBorrowerService.findBwBorrowerByAttr(bb);
		result.setCode("000");
		result.setMsg("获取借款人信息成功");
		result.setResult(bb);
		return result;
	}

	/**
	 * 根据借款查询借款人所有工单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findBwBorrowerRecordByBwId.do")
	public AppResponseResult findBwBorrowerRecordByBwId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("601");
			result.setMsg("借款人id为空");
			return result;
		}
		int[] pageParam = PageUtil.init(request);
		PageRequestVo reqVo = new PageRequestVo(pageParam[0], pageParam[1]);
		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", bwId).andNotEqualTo("statusId", 1).andEqualTo("productType",
				OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//		example.setOrderByClause("createTime  desc");
		example.orderBy("createTime").desc();
		reqVo.setExample(example);
		PageResponseVo<BwOrder> rspVo = bwOrderService.findBwOrderPage(reqVo);
		result.setCode("000");
		result.setMsg("请求处理成功");
		result.setResult(rspVo);
		return result;
	}

	/**
	 * 根据工单号查询最近一期还款计划
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findBwRepaymentPlanByOrderId.do")
	public AppResponseResult findBwRepaymentPlanByOrderId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("111");
			result.setMsg("请求参数工单id为空");
			return result;
		}
		List<Map<String, Object>> list = bwRepaymentPlanService.findBwRepaymentPlanByOrderId(Long.parseLong(orderId));
		logger.info("获取的还款计划为：" + list.size());
		if (list.size() > 0) {
			list.get(0).put("xudai_term", bwOrderXuDaiService.queryXudaiTerm(Long.valueOf(orderId)));
			result.setCode("000");
			result.setMsg("根据工单号查询最近一期还款计划");
			list.get(0).put("repay_money", list.get(0).get("reality_repay_money"));
			result.setResult(list.get(0));
		}
		return result;
	}

	/**
	 * 根据借款工单查询对应还款计划列表(包含已还记录)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findBwRepaymentPlanPage.do")
	public AppResponseResult findBwRepaymentPlanPage(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("602");
			result.setMsg("请求参数工单id为空");
			return result;
		}
		int[] pageParam = PageUtil.init(request);
		PageRequestVo reqVo = new PageRequestVo(pageParam[0], pageParam[1]);
		Example example = new Example(BwRepaymentPlan.class);
		example.createCriteria().andEqualTo("orderId", orderId);
		example.setOrderByClause("number desc");// 根据还款期数排序
		reqVo.setExample(example);
		PageResponseVo<BwRepaymentPlan> rspVo = bwRepaymentPlanService.findBwRepaymentPlanPage(reqVo);
		if (!CommUtils.isNull(rspVo.getRows())) {
			List<BwRepaymentPlan> list = rspVo.getRows();
			List<BwRepaymentPlan> newList = new ArrayList<BwRepaymentPlan>();
			for (BwRepaymentPlan br : list) {
				br.setRepayMoney(br.getRealityRepayMoney());
				newList.add(br);
			}
			rspVo.setRows(newList);
		}
		result.setCode("000");
		result.setMsg("请求处理成功");
		result.setResult(rspVo);
		return result;
	}

	/**
	 * 添加意见反馈
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addBwFeedbackInfo.do")
	public AppResponseResult addBwFeedbackInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String contactPhone = request.getParameter("contactPhone");
		String feedbackContent = request.getParameter("feedbackContent");
		String borrowerId = request.getParameter("bwId");
		if (CommUtils.isNull(contactPhone)) {
			result.setCode("603");
			result.setMsg("反馈联系方式不能为空");
			return result;
		}
		if (CommUtils.isNull(feedbackContent)) {
			result.setCode("604");
			result.setMsg("反馈内容不能为空");
			return result;
		}
		BwFeedbackInfo bf = new BwFeedbackInfo();
		if (!CommUtils.isNull(borrowerId)) {
			bf.setBorrowerId(Long.parseLong(borrowerId));
		}
		bf.setContactPhone(contactPhone);
		bf.setCreateTime(new Date());
		bf.setFeedbackContent(feedbackContent);
		int num = bwFeedbackInfoService.addBwFeedbackInfo(bf);
		if (num > 0) {
			result.setCode("000");
			result.setMsg("添加反馈意见成功");
			return result;
		} else {
			result.setCode("605");
			result.setMsg("添加反馈意见失败");
			return result;
		}
	}

	/**
	 * 查询 借款人银行卡信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findBwBankCardByBwId.do")
	public AppResponseResult findBwBankCardByBwId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("601");
			result.setMsg("借款人id为空");
			return result;
		}
		BwBankCard bb = new BwBankCard();
		bb.setBorrowerId(Long.parseLong(bwId));
		bb = bwBankCardService.findBwBankCardByAttr(bb);
		result.setCode("000");
		result.setMsg("请求处理成功");
		result.setResult(bb);
		return result;
	}

	@ResponseBody
	@RequestMapping({ "/appCheckLogin/findBwOrderByBwId.do" })
	public AppResponseResult findBwOrderByBwId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("601");
			result.setMsg("借款人id为空");
			return result;
		}
		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", bwId).andNotEqualTo("statusId", Integer.valueOf(1))
				.andEqualTo("productType", OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//		example.setOrderByClause(" createTime desc ");
		example.orderBy("createTime").desc();
		List<BwOrder> list = bwOrderService.findBwOrderByExample(example);
		if (list.size() > 0) {
			result.setCode("000");
			result.setMsg("根据借款人id获取到最近一期工单信息");
			result.setResult(list.get(0));
			return result;
		}
		result.setCode("000");
		result.setMsg("根据借款人id获取到最近一期工单信息");
		result.setResult(null);
		return result;
	}

	/**
	 * 根据借款人id获取到最近一期工单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findBwOrderByBwId2.do")
	public AppResponseResult findBwOrderByBwId2(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("601");
			result.setMsg("借款人id为空");
			return result;
		}
		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", bwId).andNotEqualTo("statusId", 1).andEqualTo("productType",
				OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//		example.setOrderByClause(" createTime desc ");
		example.orderBy("createTime").desc();
		List<BwOrder> list = bwOrderService.findBwOrderByExample(example);
		BwOrder bwOrder = null;
		boolean canBorrow = false;// 可以借钱
		if (list != null && !list.isEmpty()) {
			bwOrder = list.get(0);
			Long statusId = bwOrder.getStatusId();
			if (statusId == 6 || statusId == 8) {
				canBorrow = true;
			} else if (statusId == 7) {
				BwRejectRecord record = new BwRejectRecord();
				record.setOrderId(bwOrder.getId());
				record = bwRejectRecordService.findBwRejectRecordByAtta(record);
				if (record != null) {
					Integer rejectType = record.getRejectType();
					if (rejectType == 1) {// 非永久被拒
						Date whiteTime = bwOrderService.findBorrowWhiteTimeByBorrowId(bwId);
						if (whiteTime != null) {
							canBorrow = new Date().after(whiteTime);
						}
					}
				}
			}
		} else {
			canBorrow = true;
		}
		com.alibaba.fastjson.JSONObject resultJson = null;
		if (bwOrder != null) {
			resultJson = JSON.parseObject(JSON.toJSONString(bwOrder));
		} else {
			resultJson = new com.alibaba.fastjson.JSONObject();
		}
		resultJson.put("canBorrow", canBorrow);
		result.setCode("000");
		result.setMsg("根据借款人id获取到最近一期工单信息(屏蔽草稿)");
		result.setResult(resultJson);
		return result;
	}

	/**
	 * 根据借款人id获取到最近一期工单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findLastBwOrderByBwId.do")
	public AppResponseResult findLastBwOrderByBwId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("601");
			result.setMsg("借款人id为空");
			return result;
		}
		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", bwId).andEqualTo("productType",
				OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//		example.setOrderByClause(" createTime desc ");
		example.orderBy("createTime").desc();
		List<BwOrder> list = bwOrderService.findBwOrderByExample(example);
		BwOrder bwOrder = null;
		boolean canBorrow = false;// 可以借钱
		if (list != null && !list.isEmpty()) {
			bwOrder = list.get(0);
			Long statusId = bwOrder.getStatusId();
			bwOrder = list.get(0);
			if (statusId == 1 || statusId == 6 || statusId == 8) {
				canBorrow = true;
			} else if (statusId == 7) {
				BwRejectRecord record = new BwRejectRecord();
				record.setOrderId(bwOrder.getId());
				record = bwRejectRecordService.findBwRejectRecordByAtta(record);
				if (record != null) {
					Integer rejectType = record.getRejectType();
					if (rejectType == 1) {// 非永久被拒
						Date whiteTime = bwOrderService.findBorrowWhiteTimeByBorrowId(bwId);
						if (whiteTime != null) {
							canBorrow = new Date().after(whiteTime);
						}
					}
				}
			}
		} else {
			canBorrow = true;
		}
		com.alibaba.fastjson.JSONObject resultJson = null;
		if (bwOrder != null) {
			resultJson = JSON.parseObject(JSON.toJSONString(bwOrder));
		} else {
			resultJson = new com.alibaba.fastjson.JSONObject();
		}
		resultJson.put("canBorrow", canBorrow);
		result.setCode("000");
		result.setMsg("根据借款人id获取到最近一期工单信息");
		result.setResult(resultJson);
		return result;
	}

	/**
	 * 根据工单id查询工单信息（提前还款）
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findBwOrderInfoByOrderId.do")
	public AppResponseResult findBwOrderInfoByOrderId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("606");
			result.setMsg("工单id为空");
			return result;
		}
		BwOrderInfoDto orderInfo = bwOrderService.findBwOrderInfoByOrderId(Long.parseLong(orderId));
		if (orderInfo == null) {
			result.setCode("607");
			result.setMsg("该工单不存在");
			return result;
		} else {
			result.setCode("000");
			result.setMsg("获取工单信息成功");
			result.setResult(orderInfo);
			return result;
		}
	}

	/**
	 * 生成用于认证信息绑定的空白工单（带第三方）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/generateBwOrderForAuthThird.do")
	public AppResponseResult generateBwOrderForAuthThird(HttpServletRequest request, HttpServletResponse response) {
		logger.info("【AppMyController.generateBwOrderForAuthNew】borrowerId：");
		AppResponseResult result = new AppResponseResult();
		result.setCode("111");
		result.setMsg("请更新到最新版本");
		result.setResult("");
		return result;
	}

	/**
	 * 生成用于认证信息绑定的空白工单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/generateBwOrderForAuthNew.do")
	public AppResponseResult generateBwOrderForAuthNew(HttpServletRequest request, HttpServletResponse response) {
		logger.info("【AppMyController.generateBwOrderForAuthNew】borrowerId：");
		AppResponseResult result = new AppResponseResult();
		result.setCode("111");
		result.setMsg("请将App更新到最新版本");
		result.setResult("");
		return result;

	}

	/**
	 * 生成用于认证信息绑定的空白工单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/generateBwOrderForAuth.do")
	public AppResponseResult generateBwOrderForAuth(HttpServletRequest request, HttpServletResponse response) {
		logger.info("【AppMyController.generateBwOrderForAuth】borrowerId：");
		AppResponseResult result = new AppResponseResult();
		result.setCode("111");
		result.setMsg("请将App更新到最新版本");
		result.setResult("");
		return result;
	}

	/**
	 * 根据工单id查询工单信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findBwOrderByOrderId.do")
	public AppResponseResult findBwOrderByOrderId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("606");
			result.setMsg("工单id为空");
			return result;
		}
		BwOrder order = new BwOrder();
		order.setId(Long.parseLong(orderId));
		order = bwOrderService.findBwOrderByAttr(order);
		if (order == null) {
			result.setCode("607");
			result.setMsg("该工单不存在");
			return result;
		} else {
			result.setCode("000");
			result.setMsg("获取工单信息成功");
			result.setResult(order);
			return result;
		}
	}

	/**
	 * 根据工单id查询工单和工单最近一期还款日期信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findBwOrderAndRepayTimeByOrderId.do")
	public AppResponseResult findBwOrderAndRepayTimeByOrderId(HttpServletRequest request,
			HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("606");
			result.setMsg("工单id为空");
			return result;
		}
		BwOrder order = new BwOrder();
		order.setId(Long.parseLong(orderId));
		order = bwOrderService.findBwOrderByAttr(order);
		if (order == null) {
			result.setCode("607");
			result.setMsg("该工单不存在");
			return result;
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("order", order);
			// 查询工单最近一期未还款的还款计划
			Example example = new Example(BwRepaymentPlan.class);
			example.createCriteria().andEqualTo("orderId", order.getId()).andEqualTo("repayStatus", 1);
//			example.setOrderByClause("repayTime");
			example.orderBy("repayTime").asc();
			List<BwRepaymentPlan> list = bwRepaymentPlanService.findBwRepaymentPlanByExample(example);
			if (list != null && list.size() > 0) {
				map.put("repayTime", list.get(0).getRepayTime());
			}
			map.put("xudai_term", bwOrderXuDaiService.queryXudaiTerm(Long.valueOf(orderId)));
			result.setCode("000");
			result.setMsg("获取工单信息成功");
			result.setResult(map);
			return result;
		}
	}

	/**
	 * 根据工单id修改工单的还款方式借款时间
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/updateBwOrderAttrByOrderId.do")
	public AppResponseResult updateBwOrderAttrByOrderId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		String orderTerm = request.getParameter("orderTerm");
		String repayType = request.getParameter("repayType");
		logger.info("获取到orderId：" + orderId);
		logger.info("获取到orderTerm：" + orderTerm);
		logger.info("获取到repayType：" + repayType);
		if (CommUtils.isNull(orderId)) {
			result.setCode("1001");
			result.setMsg("工单ID为空");
			return result;
		}
		if (CommUtils.isNull(orderTerm)) {
			result.setCode("1002");
			result.setMsg("工单期限为空");
			return result;
		}
		if (CommUtils.isNull(repayType)) {
			result.setCode("1003");
			result.setMsg("工单还款方式为空");
			return result;
		}
		// 获取利率字典表信息
		BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(2l);// 固定查询产品id为2的合同利率
		Double contractMonthRate = 0.0;
		Double repayAmount = 0.0;
		Double contractAmount = 0.0;
		// 等额本息
		// 计算合同月利率
		contractMonthRate = bwProductDictionary.getpBorrowRateMonth();
		// 计算合同金额
		BwOrder order = new BwOrder();
		order.setId(Long.parseLong(orderId));
		order = bwOrderService.findBwOrderByAttr(order);
		if (CommUtils.isNull(order)) {
			result.setCode("1006");
			result.setMsg("根据工单id获取工单信息为空");
			return result;
		}
		if (CommUtils.isNull(order.getBorrowAmount())) {
			result.setCode("1007");
			result.setMsg("借款金额为空");
			return result;
		}
		// 计算还款金额
		repayAmount = DoubleUtil.round(((order.getBorrowAmount() / Integer.parseInt(orderTerm))
				+ (order.getBorrowAmount() * bwProductDictionary.getpInvestRateMonth())), 2);
		// 计算合同金额
		contractAmount = DoubleUtil
				.round((repayAmount * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)) - 1))
						/ (contractMonthRate * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)))), 2);
		// BwRateDictionary bwRateDictionary = new BwRateDictionary();
		// bwRateDictionary.setRateTerm(Integer.parseInt(orderTerm));
		// bwRateDictionary.setRateType(Integer.parseInt(repayType));
		// bwRateDictionary = bwRateDictionaryService.findBwRateDictionaryByAttr(bwRateDictionary);
		// if (CommUtils.isNull(bwRateDictionary)) {
		// result.setCode("1005");
		// result.setMsg("获取利率字典表信息为空");
		// return result;
		// }
		// Double contractMonthRate = 0.0;
		// Double repayAmount = 0.0;
		// Double contractAmount = 0.0;
		// if (repayType.equals("1")) {
		// // 等额本息
		// // 计算合同月利率
		// contractMonthRate = DoubleUtil
		// .round(Math.pow(1 + (bwRateDictionary.getContractRate() / 100), 1.0 / 12.0) - 1, 8);
		// // 计算合同金额
		// BwOrder order = new BwOrder();
		// order.setId(Long.parseLong(orderId));
		// order = bwOrderService.findBwOrderByAttr(order);
		// if (CommUtils.isNull(order)) {
		// result.setCode("1006");
		// result.setMsg("根据工单id获取工单信息为空");
		// return result;
		// }
		// if (CommUtils.isNull(order.getBorrowAmount())) {
		// result.setCode("1007");
		// result.setMsg("借款金额为空");
		// return result;
		// }
		// // 计算还款金额
		// repayAmount = DoubleUtil.round(((order.getBorrowAmount() / Integer.parseInt(orderTerm))
		// + (order.getBorrowAmount() * bwRateDictionary.getBorrowerRate() / 100)), 2);
		// // 计算合同金额
		// contractAmount = DoubleUtil
		// .round((repayAmount * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)) - 1))
		// / (contractMonthRate * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)))), 2);
		// } else {
		// // 先息后本
		// // 计算合同月利率
		// contractMonthRate = DoubleUtil
		// .round(Math.pow(1 + (bwRateDictionary.getContractRate() / 100), 1.0 / 12.0) - 1, 8);
		// // 计算合同金额
		// BwOrder order = new BwOrder();
		// order.setId(Long.parseLong(orderId));
		// order = bwOrderService.findBwOrderByAttr(order);
		// if (CommUtils.isNull(order)) {
		// result.setCode("1006");
		// result.setMsg("根据工单id获取工单信息为空");
		// return result;
		// }
		// // 计算还款金额
		// repayAmount = DoubleUtil.round(((order.getBorrowAmount() / Integer.parseInt(orderTerm))
		// + (order.getBorrowAmount() * bwRateDictionary.getBorrowerRate() / 100)), 2);
		// // 计算合同金额
		// contractAmount = DoubleUtil
		// .round((repayAmount * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)) - 1))
		// / (contractMonthRate * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)))), 2);
		// }
		BwOrder bo = new BwOrder();
		bo.setId(Long.parseLong(orderId));
		bo = bwOrderService.findBwOrderByAttr(bo);
		bo.setRepayTerm(Integer.parseInt(orderTerm));
		bo.setRepayType(Integer.parseInt(repayType));
		bo.setBorrowRate(bwProductDictionary.getpInvestRateMonth());
		bo.setContractRate(bwProductDictionary.getpInvesstRateYear());
		bo.setContractMonthRate(contractMonthRate);
		BwProductDictionary product = productService.queryCurrentProduct();
		// 更新产品
		bo.setProductId(Integer.parseInt(product.getId().toString()));
		BwBankCard bankCard = new BwBankCard();
		bankCard.setBorrowerId(bo.getBorrowerId());
		bankCard.setSignStatus(1);
		bankCard = bwBankCardService.findBwBankCardByAttr(bankCard);
		if (!CommUtils.isNull(bankCard) && bo.getStatusId().longValue() == 4L) {
			bo.setStatusId(11l);
		}
		bo.setContractAmount(contractAmount);
		// 工单修改时间
		bo.setUpdateTime(new Date());
		int num = bwOrderService.updateBwOrder(bo);
		if (num > 0) {
			result.setCode("000");
			result.setMsg("修改工单成功");
			result.setResult(bo);
			return result;
		} else {
			result.setCode("1004");
			result.setMsg("修改工单失败");
			return result;
		}
	}

	/**
	 * 根据借款人查询银行卡信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/checkBankCardSignByBwId.do")
	public AppResponseResult checkBankCardSignByBwId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("1011");
			result.setMsg("借款人id获取为空");
			return result;
		}
		BwBankCard bb = bwBankCardService.findBwBankCardByBorrowerId(Long.parseLong(bwId));
		result.setResult(bb);
		result.setCode("000");
		result.setMsg("根据借款人获取银行卡信息成功");
		return result;
	}

	/**
	 * 根据借款人查询银行卡信息 (新)wrh 20161104
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/checkBankCardSignByBwIdNew.do")
	public AppResponseResult checkBankCardSignByBwIdNew(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		BwBankCardNew bwBankCardNew = new BwBankCardNew();

		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("1011");
			result.setMsg("借款人id获取为空");
			return result;
		}
		// 获取借款人基本信息
		BwBorrower bb = new BwBorrower();
		bb.setId(Long.parseLong(bwId));
		bb = bwBorrowerService.findBwBorrowerByAttr(bb);
		// 获取借款人银行卡信息
		BwBankCard bbc = bwBankCardService.findBwBankCardByBorrowerId(Long.parseLong(bwId));

		Map<String, String> provinces = RedisUtils.hgetall("fuiou:province");// 获取省

		String provinceCode = bbc.getProvinceCode();
		String cityCode = bbc.getCityCode();

		if (provinces.size() > 0) {
			bwBankCardNew.setProvinceName(provinces.get(provinceCode));
		}

		String cityStr = RedisUtils.hget("fuiou:city", provinceCode);
		Type type = new TypeToken<List<FuiouCity>>() {
		}.getType();
		List<FuiouCity> citys = JsonUtils.fromJson(cityStr, type);
		// Map<String, Object> citys=JsonUtils.fromJson(cityStr);
		if (citys.size() > 0) {
			for (FuiouCity city : citys) {
				if (city.getCode().equals(cityCode)) {
					bwBankCardNew.setCityName(city.getCityName());
				}
			}

		}
		bwBankCardNew.setName(bb.getName());
		;
		bwBankCardNew.setFuiouAcct(bb.getFuiouAcct());
		bwBankCardNew.setPhone(bb.getPhone());
		bwBankCardNew.setCardNo(bbc.getCardNo());
		bwBankCardNew.setBankName(bbc.getBankName());

		result.setResult(bwBankCardNew);
		result.setCode("000");
		result.setMsg("根据借款人获取银行卡信息成功");
		return result;
	}

	/**
	 * 跳转免登陆签约
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/appCheckToken/forWardSign.do")
	public AppResponseResult forWardSign(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");// 登录金账户名
		String mobile = request.getParameter("mobile");// 银行预留手机号
		String orderId = request.getParameter("orderId");// 订单号
		if (CommUtils.isNull(phone)) {
			result.setCode("1001");
			result.setMsg("金账户名为空");
			return result;
		}
		if (CommUtils.isNull(mobile)) {
			result.setCode("1002");
			result.setMsg("银行预留手机号为空");
			return result;
		}
		if (CommUtils.isNull(orderId)) {
			result.setCode("1003");
			result.setMsg("工单id为空");
			return result;
		}
		logger.info("免登陆签约获取金账户号:" + phone);
		logger.info("免登陆签约获取银行预留手机号:" + mobile);
		AppSignCardReqData reqData = new AppSignCardReqData();
		reqData.setMchnt_cd(SystemConstant.FUIOU_MCHNT_CD);
		String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
		logger.info("免登陆签约流水号:" + mchnt_txn_ssn);
		reqData.setMchnt_txn_ssn(mchnt_txn_ssn);
		reqData.setPage_notify_url(SystemConstant.CALLBACK_URL + "/app/my/signCallBack.do?orderId=" + orderId);
		logger.info("免登陆签约回调地址:" + reqData.getPage_notify_url());
		reqData.setLogin_id(phone);
		reqData.setMobile(mobile);
		ResponsePayResult<AppSignCardRspData> rp = BeadwalletService.bwAppSignCard(reqData, response);
		logger.info("请求sdk后获取的返回code:" + rp.getBeadwalletCode());
		logger.info("请求sdk后获取的返回msg:" + rp.getBeadwalletMsg());
		result.setCode(rp.getBeadwalletCode());
		result.setMsg(rp.getBeadwalletMsg());
		return result;
	}

	/**
	 * 免登陆签约回调
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/signCallBack.do")
	public String signCallBack(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String orderId = request.getParameter("orderId");
		String resp_code = request.getParameter("resp_code");
		String resp_desc = request.getParameter("resp_desc");
		logger.info("免登陆签约回调获取orderId:" + orderId);
		logger.info("免登陆签约回调获取resp_code:" + resp_code);
		logger.info("免登陆签约回调获取resp_desc:" + resp_desc);
		if (!CommUtils.isNull(resp_code) && (resp_code.equals("0000") || resp_code.equals("5360"))) {

			BwOrder bo = new BwOrder();
			bo.setId(Long.parseLong(orderId));
			bo = bwOrderService.findBwOrderByAttr(bo);
			logger.info("免登陆签约从数据库获取的工单id:" + bo.getId());
			if (bo.getStatusId().longValue() == 4L) {
				bo.setStatusId(11l);
				bo.setUpdateTime(new Date());
				bwOrderService.updateBwOrder(bo);
				logger.info("免登陆签约从数据库获取的借款人id:" + bo.getBorrowerId());
				BwBankCard bb = bwBankCardService.findBwBankCardByBorrowerId(bo.getBorrowerId());
				logger.info("免登陆签约从数据库获取的银行卡id:" + bb.getId());
				bb.setSignStatus(1);
				bwBankCardService.update(bb);
			}
			return "sign_success";
		} else {
			modelMap.put("respDesc", resp_desc);
			return "sign_fail";
		}
	}

	/**
	 * 免登陆签约回调
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/signCallBackRong.do")
	public String signCallBackRong(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String orderId = request.getParameter("orderId");
		String resp_code = request.getParameter("resp_code");
		String resp_desc = request.getParameter("resp_desc");
		String url = RedisUtils.hget("third:url", "rong360");
		if (orderId.equals("-1")) {
			return "redirect:" + url;
		}
		logger.info("免登陆签约回调获取orderId:" + orderId);
		logger.info("免登陆签约回调获取resp_code:" + resp_code);
		logger.info("免登陆签约回调获取resp_desc:" + resp_desc);
		// 根据orderId查询融360的订单
		BwOrderRong bwOrderRong = new BwOrderRong();
		bwOrderRong.setOrderId(Long.parseLong(orderId));
		bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
		if ((!CommUtils.isNull(resp_code) && (resp_code.equals("0000")) || resp_code.equals("5360"))) {

			BwOrder bo = new BwOrder();
			bo.setId(Long.parseLong(orderId));
			bo = bwOrderService.findBwOrderByAttr(bo);
			if (bo.getStatusId().longValue() == 4L) {
				logger.info("免登陆签约从数据库获取的工单id:" + bo.getId());
				bo.setStatusId(11l);
				bo.setUpdateTime(new Date());
				bwOrderService.updateBwOrder(bo);
				logger.info("免登陆签约从数据库获取的借款人id:" + bo.getBorrowerId());
				BwBankCard bb = bwBankCardService.findBwBankCardByBorrowerId(bo.getBorrowerId());
				logger.info("免登陆签约从数据库获取的银行卡id:" + bb.getId());
				bb.setSignStatus(1);
				bwBankCardService.update(bb);
			}
			// 反馈绑卡结果
			BeadWalletRongOrderService.bindcardfeedback(bwOrderRong.getThirdOrderNo(), 1);// 1表示成功
			return "redirect:" + url;
		} else {
			modelMap.put("respDesc", resp_desc);
			// 反馈绑卡结果
			BeadWalletRongOrderService.bindcardfeedback(bwOrderRong.getThirdOrderNo(), 2);// 2表示失败
			return "redirect:" + url;
		}
	}

	/**
	 * 跳转免登陆签约
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/forWardSignWeixin.do")
	public AppResponseResult forWardSignWeixin(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String phone = request.getParameter("phone");// 登录金账户名
		String mobile = request.getParameter("mobile");// 银行预留手机号
		String orderId = request.getParameter("orderId");// 订单号
		if (CommUtils.isNull(phone)) {
			result.setCode("1001");
			result.setMsg("金账户名为空");
			return result;
		}
		if (CommUtils.isNull(mobile)) {
			result.setCode("1002");
			result.setMsg("银行预留手机号为空");
			return result;
		}
		if (CommUtils.isNull(orderId)) {
			result.setCode("1003");
			result.setMsg("工单id为空");
			return result;
		}
		logger.info("免登陆签约获取金账户号:" + phone);
		logger.info("免登陆签约获取银行预留手机号:" + mobile);
		AppSignCardReqData reqData = new AppSignCardReqData();
		reqData.setMchnt_cd(SystemConstant.FUIOU_MCHNT_CD);
		String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
		logger.info("免登陆签约流水号:" + mchnt_txn_ssn);
		reqData.setMchnt_txn_ssn(mchnt_txn_ssn);
		reqData.setPage_notify_url(SystemConstant.CALLBACK_URL + "/app/my/signCallBackWeixin.do?orderId=" + orderId);
		logger.info("免登陆签约回调地址:" + reqData.getPage_notify_url());
		reqData.setLogin_id(phone);
		reqData.setMobile(mobile);
		ResponsePayResult<AppSignCardRspData> rp = BeadwalletService.bwAppSignCard(reqData, response);
		logger.info("请求sdk后获取的返回code:" + rp.getBeadwalletCode());
		logger.info("请求sdk后获取的返回msg:" + rp.getBeadwalletMsg());
		result.setCode(rp.getBeadwalletCode());
		result.setMsg(rp.getBeadwalletMsg());
		return result;
	}

	/**
	 * 免登陆签约回调
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/signCallBackWeixin.do")
	public String signCallBackWeixin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String orderId = request.getParameter("orderId");
		String resp_code = request.getParameter("resp_code");
		String resp_desc = request.getParameter("resp_desc");
		logger.info("免登陆签约回调获取orderId:" + orderId);
		logger.info("免登陆签约回调获取resp_code:" + resp_code);
		logger.info("免登陆签约回调获取resp_desc:" + resp_desc);
		if (!CommUtils.isNull(resp_code) && (resp_code.equals("0000") || resp_code.equals("5360"))) {

			BwOrder bo = new BwOrder();
			bo.setId(Long.parseLong(orderId));
			bo = bwOrderService.findBwOrderByAttr(bo);
			if (bo.getStatusId().longValue() == 4L) {
				logger.info("免登陆签约从数据库获取的工单id:" + bo.getId());
				bo.setStatusId(11l);
				bo.setUpdateTime(new Date());
				bwOrderService.updateBwOrder(bo);
				logger.info("免登陆签约从数据库获取的借款人id:" + bo.getBorrowerId());
				BwBankCard bb = bwBankCardService.findBwBankCardByBorrowerId(bo.getBorrowerId());
				logger.info("免登陆签约从数据库获取的银行卡id:" + bb.getId());
				bb.setSignStatus(1);
				bwBankCardService.update(bb);
			}
			return "sign_success_weixin";
		} else {
			modelMap.put("respDesc", resp_desc);
			return "sign_fail_weixin";
		}
	}

	/**
	 * 根据工单和借款人id查询合同
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findAdjunctByOrderId.do")
	public AppResponseResult findAdjunctByOrderId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("1021");
			result.setMsg("工单id为空");
			return result;
		}
		if (CommUtils.isNull(bwId)) {
			result.setCode("1022");
			result.setMsg("工单id为空");
			return result;
		}
		BwAdjunct ba = bwAdjunctService.findBwAdjunct(Long.parseLong(orderId));
		if (CommUtils.isNull(ba)) {
			result.setCode("1023");
			result.setMsg("此工单暂未找到对应的服务合同");
			return result;
		} else {
			ba.setAdjunctPath(SystemConstant.PDF_URL + ba.getAdjunctPath());
			result.setCode("000");
			result.setMsg("服务合同查询成功");
			result.setResult(ba);
			return result;
		}
	}

	/**
	 * 查找历史工单（只包含已结清和撤回）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findHistoryOrder.do")
	public AppResponseResult findHistoryOrder(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("1101");
			result.setMsg("借款人Id为空");
			return result;
		}
		try {
			Example example = new Example(BwOrder.class);
			List<Object> statusList = new ArrayList<Object>();
			statusList.add(6);// 结束
			statusList.add(8);// 撤回
			example.createCriteria().andIn("statusId", statusList).andEqualTo("borrowerId", bwId)
					.andEqualTo("productType", OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//			example.setOrderByClause(" createTime desc ");
			example.orderBy("createTime").desc();
			List<BwOrder> list = bwOrderService.findBwOrderByExample(example);
			if (list != null && list.size() > 0) {
				result.setCode("000");
				result.setMsg("成功");
				result.setResult(list);
			} else {
				result.setCode("1102");
				result.setMsg("未查找到历史工单");
			}
		} catch (Exception e) {
			result.setCode("1003");
			result.setMsg("接口内部错误");
			logger.error("查找历史工单接口异常：" + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询账户信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/getAccountInfo.do")
	public AppResponseResult getAccountInfo(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(bwId)) {
			result.setCode("1101");
			result.setMsg("借款人Id为空");
			return result;
		}
		try {
			AccountInfoDto accountInfoDto = new AccountInfoDto();
			accountInfoDto.setBorrowAmount(0.0);
			accountInfoDto.setMark("0");
			BwBorrower borrower = bwBorrowerService.findBwBorrowerById(Long.parseLong(bwId));
			accountInfoDto.setId(borrower.getId());
			accountInfoDto.setPhone(borrower.getPhone());
			accountInfoDto.setName(borrower.getName());
			accountInfoDto.setInviteCode(borrower.getInviteCode());
			// 统计邀请人数
			Integer inviteCount = bwBorrowerService.countInvite(borrower.getId());
			accountInfoDto.setInviteCount(inviteCount);
			// 计算借款金额
			Example example = new Example(BwOrder.class);
			List<Object> statusList = new ArrayList<Object>();
			statusList.add(4);// 签约
			statusList.add(5);// 待放款
			statusList.add(9);// 还款中
			statusList.add(11);// 待生成合同
			statusList.add(12);// 待债匹
			statusList.add(13);// 逾期
			statusList.add(14);// 债匹中
			example.createCriteria().andIn("statusId", statusList).andEqualTo("borrowerId", bwId)
					.andEqualTo("productType", OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//			example.setOrderByClause(" create_time desc ");
			example.orderBy("createTime").desc();
			List<BwOrder> list = bwOrderService.findBwOrderByExample(example);
			if (list != null && list.size() > 0) {
				accountInfoDto
						.setBorrowAmount(list.get(0).getBorrowAmount() == null ? 0.0 : list.get(0).getBorrowAmount());
				accountInfoDto.setMark(list.get(0).getMark());
			} else {
				accountInfoDto.setBorrowAmount(0D);
			}
			result.setCode("000");
			result.setMsg("查询账户信息成功");
			result.setResult(accountInfoDto);
		} catch (NumberFormatException e) {
			result.setCode("1003");
			result.setMsg("接口内部错误");
			logger.error("查询账户信息异常：" + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 还款页面==》根据工单号查询最近一期还款计划
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findRepaymentPlanInfoByOrderId.do")
	public AppResponseResult findRepaymentPlanInfoByOrderId(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String orderId = request.getParameter("orderId");
			if (CommUtils.isNull(orderId)) {
				result.setCode("602");
				result.setMsg("请求参数工单id为空");
				return result;
			}
			com.alibaba.fastjson.JSONObject jsonObject = bwRepaymentPlanService
					.findRepaymentPlanInfoByOrderId(Long.parseLong(orderId), true);
			jsonObject.put("xudaiTerm", bwOrderXuDaiService.queryXudaiTerm(Long.valueOf(orderId)));
			result.setCode("000");
			result.setMsg("根据工单号查询最近一期还款信息");
			result.setResult(jsonObject);
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统异常");
			logger.error("", e);
		}
		return result;
	}

	/**
	 * 判断工单是否在还款或续贷处理中
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/planIsProcess.do")
	public AppResponseResult planIsProcess(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		try {
			String orderId = request.getParameter("orderId");
			result.setCode("000");
			if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId)
					| RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)
					| RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
				result.setMsg("此工单正在还款或续贷处理中");
				result.setResult(true);
				return result;
			}
			result.setMsg("工单没有在还款或续贷处理");
			result.setResult(false);
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统异常");
			logger.error("", e);
		}
		return result;
	}

	/**
	 * 我的页面==》获取用户信息 用户星级、是否可以一键拿钱、提示信息链接、工单状态
	 * 
	 * @date 2017-04-05 14:22:23
	 * @author maoenqi
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMyInfo.do")
	public AppResponseResult getMyInfo(HttpServletRequest request) {
		AppResponseResult appResponseResult = null;
		try {
			JSONObject result = new JSONObject();
			Long bwId = NumberUtils.toLong(request.getParameter("bwId"), 0L);
			appResponseResult = bwOrderService.canKeyMoney(bwId);
			BwBorrower queryBwBorrower = bwBorrowerService.findBwBorrowerById(bwId);
			if (queryBwBorrower == null) {
				appResponseResult.setCode("901");
				appResponseResult.setMsg("没有此借款人");
				return appResponseResult;
			}

			// 首页查询未读公告数量--start
			logger.info("首页查询未读公告数量,栏目ID:" + "69");
			int unreadCount = partnerService.getUnreadCount("69", bwId.toString());
			result.put("unreadCount", unreadCount);
			// --end

			if ("000".equals(appResponseResult.getCode())) {
				result.put("canKeyMoney", true);
			} else {
				result.put("canKeyMoney", false);
			}
			BwOrder bwOrder = bwOrderService.findNewBwOrderByBwIdNotLimitStatusID(bwId.toString());

			logger.info("查询消息栏目ID:" + SystemConstant.CMS_MESSAGE);
			List<CmsContent> MsgList = partnerService.getIndexContentList(SystemConstant.CMS_MESSAGE);
			String noticeUrl = MsgList.get(0).getUrl();
			result.put("statusId", bwOrder == null ? 0 : bwOrder.getStatusId());
			result.put("orderId", bwOrder == null ? 0 : bwOrder.getId());
			// result.put("keyMoneyMsg", appResponseResult.getMsg());
			String phone = queryBwBorrower.getPhone();
			appResponseResult.setCode("000");
			result.put("phone", StringUtil.hideStringMiddle(phone, "*", 3, 4));
			// 提示信息
			result.put("tipsMsg", "第一次来水象分期，快去看看新手指引吧");
			// 提示信息链接，待定
			result.put("tipsUrl", SystemConstant.NEW_GUIDELINES);

			result.put("noticeUrl", "http://" + noticeUrl);
			// 星级，待定
			result.put("starLevel", bwBorrowerWealthService.getUserRankByBorrowerId(bwId.toString()));
			result.put("sex", queryBwBorrower.getSex());
			result.put("headImgUrl", "img/activeimg/home_center.png");
			appResponseResult.setMsg("请求成功");
			appResponseResult.setResult(result);
		} catch (Exception e) {
			appResponseResult = new AppResponseResult();
			appResponseResult.setResult(false);
			appResponseResult.setCode("999");
			appResponseResult.setMsg("数据调用异常--" + e.getMessage());
			logger.error("我的页面==》获取用户信息异常", e);
		}
		return appResponseResult;
	}

	/**
	 * 一键拿钱<br />
	 * 1.未超过31天直接跳过认证到马上拿钱页面<br />
	 * 2.距离上次运营商认证超过31天，则跳转认证页面，运营商、芝麻信用重新认证，其他两个默认已认证
	 * 
	 * @date 2017-04-05 14:22:23
	 * @author maoenqi
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/keyMoney.do")
	public AppResponseResult keyMoney(HttpServletRequest request) {
		AppResponseResult appResponseResult = null;
		try {
			Long bwId = NumberUtils.toLong(request.getParameter("bwId"), 0L);
			Integer channelId = NumberUtils.toInt(request.getParameter("channel"), 0);
			if (channelId == null || channelId <= 0) {
				appResponseResult = new AppResponseResult();
				appResponseResult.setCode("801");
				appResponseResult.setMsg("渠道不能为空");
				appResponseResult.setResult(false);
				return appResponseResult;
			}
			appResponseResult = bwOrderService.updateAndKeyMoney(bwId, channelId);
		} catch (Exception e) {
			appResponseResult = new AppResponseResult();
			appResponseResult.setCode("999");
			appResponseResult.setMsg("系统异常");
			appResponseResult.setResult(false);
			logger.error("一键拿钱调用异常", e);
		}
		return appResponseResult;
	}

	/**
	 * 判断是否是银行卡号
	 * 
	 * @param cardId
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;

	}

	private char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	/***
	 * 真实姓名验证
	 *
	 * @param trueName
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkTrueName(String trueName) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String check = "^[\u4E00-\u9FA5]{2,8}(?:·[\u4E00-\u9FA5]{2,8})*$";
		p = Pattern.compile(check); // 验证真实姓名
		m = p.matcher(trueName);
		b = m.matches();
		return b;
	}

	/**
	 * 身份证号码验证
	 *
	 * @param card
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkIdentityCard(String card) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String check = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		p = Pattern.compile(check);
		m = p.matcher(card);
		b = m.matches();
		return b;
	}

	/**
	 * 验证银行卡名字和卡号是否一致
	 * 
	 * @param bankCode
	 * @param cardNo
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkBankNameAndCode(String bankCode, String cardNo) {
		String bankName = BankInfoUtils.getNameOfBank(cardNo.substring(0, 6));
		logger.info("银行卡名称==========" + bankName);
		if (bankCode.equals("0105")) {// 中国建设银行
			if (!bankName.contains("建设")) {
				return false;
			}
		}
		if (bankCode.equals("0303")) {// 中国光大银行
			if (!bankName.contains("光大")) {
				return false;
			}
		}
		if (bankCode.equals("0308")) {// 招商银行
			if (!bankName.contains("招商")) {
				return false;
			}
		}
		if (bankCode.equals("0103")) {// 中国农业银行
			if (!bankName.contains("农业")) {
				return false;
			}
		}
		if (bankCode.equals("0301")) {// 交通银行
			if (!bankName.contains("交通")) {
				return false;
			}
		}
		if (bankCode.equals("0307")) {// 平安银行股份有限公司
			if (!bankName.contains("平安")) {
				return false;
			}
		}
		if (bankCode.equals("0310")) {// 上海浦东发展银行
			if (!bankName.contains("浦东")) {
				return false;
			}
		}
		if (bankCode.equals("0403")) {// 中国邮政储蓄银行有限公司
			if (!bankName.contains("邮政储蓄")) {
				return false;
			}
		}
		if (bankCode.equals("0304")) {// 华夏银行
			if (!bankName.contains("华夏银行")) {
				return false;
			}
		}
		if (bankCode.equals("0305")) {// 中国民生银行
			if (!bankName.contains("民生银行")) {
				return false;
			}
		}
		if (bankCode.equals("0104")) {// 中国银行
			if (!bankName.contains("中国银行")) {
				return false;
			}
		}
		if (bankCode.equals("0102")) {// 中国工商银行
			if (!bankName.contains("工商银行")) {
				return false;
			}
		}
		if (bankCode.equals("0306")) {// 广东发展银行
			if (!bankName.contains("广发银行")) {
				return false;
			}
		}
		if (bankCode.equals("0309")) {// 兴业银行
			if (!bankName.contains("兴业")) {
				return false;
			}
		}
		if (bankCode.equals("0302")) {// 中信实业银行
			if (!bankName.contains("中信银行")) {
				return false;
			}
		}

		return true;
	}

	@SuppressWarnings("unused")
	private String getBankCode(String bankName) {
		String bankCode = "";

		// 中国建设银行
		if (bankName.contains("建设")) {
			bankCode = "0105";
		}

		// 中国光大银行
		if (bankName.contains("光大")) {
			bankCode = "0303";
		}

		// 招商银行
		if (bankName.contains("招商")) {
			bankCode = "0308";
		}

		// 中国农业银行
		if (bankName.contains("农业")) {
			bankCode = "0103";
		}

		// 交通银行
		if (bankName.contains("交通")) {
			bankCode = "0301";
		}

		// 平安银行股份有限公司
		if (bankName.contains("平安")) {
			bankCode = "0307";
		}

		// 上海浦东发展银行
		if (bankName.contains("浦东")) {
			bankCode = "0310";
		}

		// 中国邮政储蓄银行有限公司
		if (bankName.contains("邮政")) {
			bankCode = "0403";
		}

		// 华夏银行
		if (bankName.contains("华夏")) {
			bankCode = "0304";
		}

		// 中国民生银行
		if (bankName.contains("民生")) {
			bankCode = "0305";
		}

		// 中国银行
		if (bankName.contains("中国银行")) {
			bankCode = "0104";
		}

		// 中国工商银行
		if (bankName.contains("工商")) {
			bankCode = "0102";
		}

		// 广东发展银行
		if (bankName.contains("广发")) {
			bankCode = "0306";
		}

		// 兴业银行
		if (bankName.contains("兴业")) {
			bankCode = "0309";
		}

		// 中信实业银行
		if (bankName.contains("中信")) {
			bankCode = "0302";
		}

		return bankCode;
	}

}
