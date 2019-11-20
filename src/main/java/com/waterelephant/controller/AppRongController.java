package com.waterelephant.controller;

import com.beadwallet.entity.request.AppSignCardReqData;
import com.beadwallet.entity.request.RegReqData;
import com.beadwallet.entity.response.AppSignCardRspData;
import com.beadwallet.entity.response.CommonRspData;
import com.beadwallet.entity.response.ResponsePayResult;
import com.beadwallet.servcie.BeadwalletService;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.serve.BeadWalletRongOrderService;
import com.google.gson.reflect.TypeToken;
import com.waterelephant.dto.AppResponseRongResult;
import com.waterelephant.dto.OrderInfo;
import com.waterelephant.dto.RongOrderDetail;
import com.waterelephant.dto.RongOrderDto;
import com.waterelephant.entity.*;
import com.waterelephant.service.*;
import com.waterelephant.utils.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.*;

@Controller
@RequestMapping("/app/rong")
public class AppRongController {
	private Logger logger = Logger.getLogger(AppRongController.class);
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private CheckService checkService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwRongOrderDetailService bwRongOrderDetailService;
	@Autowired
	private BwRongOrderAddInfoService bwRongOrderAddInfoService;
	@Autowired
	private BwFuiouBankProvinceService bwFuiouBankProvinceService;
	@Autowired
	private BwFuiouBankCityService bwFuiouBankCityService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;

	@ResponseBody
	@RequestMapping("/orderPush.do")
	public AppResponseResult orderPush(@RequestBody RongOrderDto rongOrderDto) {
		AppResponseResult result = new AppResponseResult();
		try {
			if (CommUtils.isNull(rongOrderDto)) {
				result.setCode("101");
				result.setMsg("系统参数为空");
				return result;
			}
			String biz_data = rongOrderDto.getBiz_data();
			JSONObject bizData = JSONObject.fromObject(biz_data);
			Type type = new TypeToken<RongOrderDetail>() {
			}.getType();
			// 根据引流的订单判断
			RongOrderDetail rongOrderDetail = JsonUtils.fromJson(bizData.toString(), type);
			OrderInfo orderInfo = rongOrderDetail.getOrderInfo();
			logger.info("融360推单的订单号为："+orderInfo.getOrder_No());
			Long resultNum = RedisUtils.rpush("rong360:orderPush", biz_data);
			if (!CommUtils.isNull(resultNum)) {
				logger.info(orderInfo.getOrder_No()+"的订单基本信息存入redis成功");
				result.setCode("200");
				result.setMsg("成功");
			}else {
				logger.info(orderInfo.getOrder_No()+"的订单基本信息存入redis失败");
				result.setCode("103");
				result.setMsg("系统异常，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.setCode("102");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/orderAddInfoPush.do")
	public AppResponseResult orderAddInfoPush(@RequestBody RongOrderDto rongOrderDto) {
		AppResponseResult result = new AppResponseResult();
		try {
			if (CommUtils.isNull(rongOrderDto)) {
				result.setCode("101");
				result.setMsg("系统参数为空");
				return result;
			}
			String biz_data = rongOrderDto.getBiz_data();
			JSONObject bizData = JSONObject.fromObject(biz_data);
			logger.info("融360订单补充信息订单号为："+bizData.getString("order_no"));
			Long resultNum = RedisUtils.hset("rong360:addInfo",bizData.getString("order_no"),biz_data);
			if (!CommUtils.isNull(resultNum)) {
				logger.info(bizData.getString("order_no")+"的订单补充信息存入redis成功");
				result.setCode("200");
				result.setMsg("成功");
			}else {
				logger.info(bizData.getString("order_no")+"订单补充信息存入redis失败");
				result.setCode("103");
				result.setMsg("系统异常，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.setCode("102");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/oldUser.do")
	public AppResponseResult oldUser(@RequestBody RongOrderDto rongOrderDto) {
		AppResponseResult result = new AppResponseResult();
		try {
			String biz_data = rongOrderDto.getBiz_data();
			String sign = rongOrderDto.getSign();
			logger.info("biz_data的值为：" + biz_data);
			logger.info("sign的值为：" + sign);
			JSONObject bizData = JSONObject.fromObject(biz_data);
			String idCard = bizData.optString("id_card");
			String phone = bizData.optString("user_mobile");
			BwBorrower borrower = bwBorrowerService.oldUserFilter(idCard.substring(0, idCard.length() - 5),
					phone.substring(0, phone.length() - 4));
			if (CommUtils.isNull(borrower)) {
				// 200 表示不是老用户可以申请借款
				logger.info("可以借款");
				result.setCode("200");
				result.setMsg("成功");
			} else {
				BwOrder order = new BwOrder();
				order.setBorrowerId(borrower.getId());
				order = bwOrderService.findBwOrderByAttr(order);
				if (!CommUtils.isNull(order)) {
					BwRejectRecord record = new BwRejectRecord();
					record.setOrderId(order.getId());
					record = bwRejectRecordService.findBwRejectRecordByAtta(record);
					if (!CommUtils.isNull(record)) {
						logger.info("不可以借款");
						result.setCode("102");
						result.setMsg("该用户暂不能借款");
						return result;
					}
				}
				if (borrower.getChannel().equals(11)) {
					result.setCode("200");
					result.setMsg("成功");
				} else {
					logger.info("不可以借款");
					result.setCode("101");
					result.setMsg("该用户在系统中已存在");
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.setCode("102");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/bindCard.do")
	public AppResponseRongResult bindCard(@RequestBody RongOrderDto rongOrderDto, HttpServletResponse response) {
		AppResponseRongResult result = new AppResponseRongResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Date now = new Date();
			String biz_data = rongOrderDto.getBiz_data();
			String sign = rongOrderDto.getSign();
			logger.info("biz_data的值为：" + biz_data);
			logger.info("sign的值为：" + sign);
			JSONObject bizData = JSONObject.fromObject(biz_data);
			String contract_return_url = bizData.getString("contract_return_url");
			// 保存至redis
			RedisUtils.hset("third:url", "rong360", contract_return_url);
			String order_no = bizData.getString("order_no");
			String bank_card = bizData.getString("bank_card");
			String open_bank = bizData.getString("open_bank");
			String user_name = bizData.getString("user_name");
			String id_number = bizData.getString("id_number");
			String user_mobile = bizData.getString("user_mobile");
			String bank_address = bizData.getString("bank_address");
			String bankCode = this.convertToBankCode(open_bank);
			logger.info("contract_return_url:" + contract_return_url);
			logger.info("order_no:" + order_no);
			logger.info("bankCode:" + bankCode);
			logger.info("user_mobile:" + user_mobile);
			logger.info("id_number:" + id_number);
			if (bankCode.equals("0000")) {
				result.setCode("102");
				result.setMsg("该银行卡所在地区暂未开通服务");
				return result;
			}
			String[] address = bank_address.split(" ");
			String province = address[0];
			logger.info("省份：" + province);
			if (province.contains("市")) {
				BwFuiouBankCity bankCity = bwFuiouBankCityService.findCityByName(province);
				if (CommUtils.isNull(bankCity)) {
					result.setCode("102");
					result.setMsg("该银行卡所在地区暂未开通服务");
					return result;
				}
				String pCode = bankCity.getPcode();
				String cCode = bankCity.getCode();
				logger.info("省份code:" + pCode);
				logger.info("城市code:" + cCode);
				String bankName = RedisUtils.hget("fuiou:bank", bankCode);
				// 根据订单号查询关联的工单
				BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(order_no);
				BwOrder order = new BwOrder();
				order.setId(bwOrderRong.getOrderId());
				order = bwOrderService.findBwOrderByAttr(order);
				logger.info("查询到的借款人id为：" + order.getBorrowerId());
				// 获取借款人信息
				BwBorrower borrower = new BwBorrower();
				borrower.setId(order.getBorrowerId());
				borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
				// 保存银行卡信息
				BwBankCard bbc = new BwBankCard();
				bbc.setBorrowerId(order.getBorrowerId());
				bbc = bwBankCardService.findBwBankCardByAttr(bbc);
				if (CommUtils.isNull(bbc)) {
					bbc = new BwBankCard();
					bbc.setBorrowerId(order.getBorrowerId());
					bbc.setCardNo(bank_card);
					bbc.setProvinceCode(pCode);
					bbc.setCityCode(cCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setPhone(user_mobile);
					bbc.setSignStatus(0);
					bbc.setCreateTime(now);
					bwBankCardService.saveBwBankCard(bbc, order.getBorrowerId());
				} else {
					if (bbc.getSignStatus().equals(1)) {
						result.setCode("200");
						result.setMsg("成功");
						map.put("bind_card_url", contract_return_url);
						result.setData(map);
						return result;
					}
					bbc.setBorrowerId(order.getBorrowerId());
					bbc.setCardNo(bank_card);
					bbc.setProvinceCode(pCode);
					bbc.setCityCode(cCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setPhone(user_mobile);
					bbc.setSignStatus(0);
					bbc.setUpdateTime(now);
					bwBankCardService.update(bbc);
				}
				// 开户
				ResponsePayResult<CommonRspData> bw = AppRongController.createFuiouUser(bbc, borrower.getName(),
						borrower.getIdCard());
				if (bw.getBeadwalletCode().equals("0000") || bw.getBeadwalletCode().equals("5343")) {
					logger.info("更新富有账户");
					// 更新富有账户
					borrower.setFuiouAcct(user_mobile);
					borrower.setAuthStep(4);
					bwBorrowerService.updateBwBorrower(borrower);
					// 返回回调地址
					logger.info("富友账号：" + borrower.getFuiouAcct());
					result.setCode("200");
					result.setMsg("成功");
					map.put("bind_card_url",
							SystemConstant.CALLBACK_URL + "/app/rong/signCard.do?phone=" + borrower.getFuiouAcct()
									+ "&mobile=" + borrower.getPhone() + "&orderId=" + order.getId() + "");
					result.setData(map);
				} else {
					result.setCode("200");
					result.setMsg("绑卡操作失败");
					// 反馈绑卡失败
					Response<Object> response2 = BeadWalletRongOrderService.bindcardfeedback(order_no, 2);
					logger.info("反馈绑卡失败结果：" + response2.getRequestCode());
					map.put("bind_card_url",
							SystemConstant.CALLBACK_URL + "/app/my/signCallBackRong.do?orderId=" + "-1");
					result.setData(map);
				}

			} else {
				BwFuiouBankProvince bankProvince = bwFuiouBankProvinceService.findProvinceByName(address[0]);
				if (CommUtils.isNull(bankProvince)) {
					result.setCode("102");
					result.setMsg("该银行卡所在地区暂未开通服务");
				}
				BwFuiouBankCity bankCity = bwFuiouBankCityService.findCityByName(address[1]);
				if (CommUtils.isNull(bankCity)) {
					result.setCode("102");
					result.setMsg("该银行卡所在地区暂未开通服务");
				}
				String pCode = bankProvince.getCode();
				String cCode = bankCity.getCode();
				logger.info("省份code:" + pCode);
				logger.info("城市code:" + cCode);
				String bankName = RedisUtils.hget("fuiou:bank", bankCode);
				// 根据订单号查询关联的工单
				BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(order_no);
				BwOrder order = new BwOrder();
				order.setId(bwOrderRong.getOrderId());
				order = bwOrderService.findBwOrderByAttr(order);
				logger.info("查询到的借款人id为：" + order.getBorrowerId());
				// 保存银行卡信息
				BwBankCard bbc = new BwBankCard();
				bbc.setBorrowerId(order.getBorrowerId());
				bbc = bwBankCardService.findBwBankCardByAttr(bbc);
				if (CommUtils.isNull(bbc)) {
					bbc = new BwBankCard();
					bbc.setBorrowerId(order.getBorrowerId());
					bbc.setCardNo(bank_card);
					bbc.setProvinceCode(pCode);
					bbc.setCityCode(cCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setPhone(user_mobile);
					bbc.setSignStatus(0);
					bbc.setCreateTime(now);
					bwBankCardService.saveBwBankCard(bbc, order.getBorrowerId());
				} else {
					if (bbc.getSignStatus().equals(1)) {
						result.setCode("200");
						result.setMsg("成功");
						map.put("bind_card_url", contract_return_url);
						result.setData(map);
						return result;
					}
					bbc.setBorrowerId(order.getBorrowerId());
					bbc.setCardNo(bank_card);
					bbc.setProvinceCode(pCode);
					bbc.setCityCode(cCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setPhone(user_mobile);
					bbc.setSignStatus(0);
					bbc.setUpdateTime(now);
					bwBankCardService.update(bbc);
				}
				// 开户
				ResponsePayResult<CommonRspData> bw = AppRongController.createFuiouUser(bbc, user_name, id_number);
				if (bw.getBeadwalletCode().equals("0000") || bw.getBeadwalletCode().equals("5343")) {
					logger.info("更新富有账户");
					// 更新富有账户
					BwBorrower borrower = new BwBorrower();
					borrower.setId(order.getBorrowerId());
					borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
					borrower.setFuiouAcct(user_mobile);
					borrower.setAuthStep(4);
					bwBorrowerService.updateBwBorrower(borrower);
					// 返回回调地址
					logger.info("富友账号：" + borrower.getFuiouAcct());
					result.setCode("200");
					result.setMsg("成功");
					map.put("bind_card_url",
							SystemConstant.CALLBACK_URL + "/app/rong/signCard.do?phone=" + borrower.getFuiouAcct()
									+ "&mobile=" + borrower.getPhone() + "&orderId=" + order.getId() + "");
					result.setData(map);
				} else {
					result.setCode("200");
					result.setMsg("绑卡操作失败");
					// 反馈绑卡失败
					BeadWalletRongOrderService.bindcardfeedback(order_no, 2);
					map.put("bind_card_url",
							SystemConstant.CALLBACK_URL + "/app/my/signCallBackRong.do?orderId=" + "-1");
					result.setData(map);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.setCode("103");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		return result;
	}

	// 签约
	@ResponseBody
	@RequestMapping("/signCard.do")
	public AppResponseResult signCard(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String phone = request.getParameter("phone");// 登录金账户名
			String mobile = request.getParameter("mobile");// 银行预留手机号
			String orderId = request.getParameter("orderId");
			;// 订单号
			logger.info("免登陆签约获取金账户号:" + orderId);
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
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.setCode("103");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		result.setCode("200");
		result.setMsg("成功");
		return result;
	}

	// 查看合同url
	@ResponseBody
	@RequestMapping("/findContract.do")
	public AppResponseRongResult findContract(@RequestBody RongOrderDto rongOrderDto) {
		AppResponseRongResult result = new AppResponseRongResult();
		try {
			String biz_data = rongOrderDto.getBiz_data();
			String sign = rongOrderDto.getSign();
			logger.info("biz_data的值为：" + biz_data);
			logger.info("sign的值为：" + sign);
			JSONObject bizData = JSONObject.fromObject(biz_data);
			String orderNo = bizData.getString("order_no");
			if (CommUtils.isNull(orderNo)) {
				result.setCode("302");
				result.setMsg("工单号为空");
				return result;
			}
			logger.info("订单号：" + orderNo);
			// 根据订单号查询到工单号
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
			Long orderId = bwOrderRong.getOrderId();
			logger.info("工单号" + orderId);

			// map.put("xw",
			// "http://waterelephant.oss-cn-shanghai.aliyuncs.com/upload/backend/2016-12-23/20161223025827.jpg");
			List<BwAdjunct> bwAdjuncts = bwAdjunctService.findBwAdjunctByOrderIdNew(orderId);
			HashMap<String, Object> map = new HashMap<>();
			if (bwAdjuncts.size() > 0) {
				result.setCode("200");
				result.setMsg("成功");
				for (BwAdjunct bwAdjunct2 : bwAdjuncts) {
					if (bwAdjunct2.getAdjunctType().equals(13)) {
						logger.info("查询到的合同地址是：" + SystemConstant.PDF_URL + bwAdjunct2.getAdjunctPath());
						map.put("contract_url", SystemConstant.PDF_URL + bwAdjunct2.getAdjunctPath());
					}
				}
				result.setData(map);
			} else {
				result.setCode("301");
				result.setMsg("未找到合同");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.setCode("103");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		return result;
	}

	// 开户操作
	private static ResponsePayResult<CommonRspData> createFuiouUser(BwBankCard bwBankCard, String name, String idCard) {
		RegReqData regReqData = new RegReqData();
		regReqData.setBank_nm(CommUtils.isNull(bwBankCard.getBankName()) ? "" : bwBankCard.getBankName());// 开户支行
		regReqData.setCapAcntNo(CommUtils.isNull(bwBankCard.getCardNo()) ? "" : bwBankCard.getCardNo());// 开户支行
		// regReqData.setCapAcntNo(bwBankCard.getCardNo());// 银行卡号
		regReqData.setCertif_id(CommUtils.isNull(idCard) ? "" : idCard);// 身份证号
		regReqData.setCertif_tp("0");// 证件类型
		regReqData.setCity_id(CommUtils.isNull(bwBankCard.getCityCode()) ? "" : bwBankCard.getCityCode());// 开户地区
		regReqData.setCust_nm(CommUtils.isNull(name) ? "" : name);// 真实姓名
		regReqData.setMchnt_cd(ResourceBundle.getBundle("fuiou").getString("mchnt_cd"));// 富友生产商户代码
		regReqData.setMchnt_txn_ssn(GenerateSerialNumber.getSerialNumber());
		regReqData.setMobile_no(CommUtils.isNull(bwBankCard.getPhone()) ? "" : bwBankCard.getPhone());// 手机号(账号)
		regReqData.setParent_bank_id(CommUtils.isNull(bwBankCard.getBankCode()) ? "" : bwBankCard.getBankCode());// 开户行);
		return BeadwalletService.bwReg(regReqData);
	}

	// 根据rong360的银行code转成平台自己的code
	private String convertToBankCode(String open_bank) {
		String bankCode = "";
		switch (open_bank) {
		case "CCB":
			// 建设银行
			bankCode = "0105";
			break;
		case "CEB":
			// 光大银行
			bankCode = "0303";
			break;
		case "PSBC":
			// 邮政银行
			bankCode = "0403";
			break;
		case "HXB":
			// 华夏银行
			bankCode = "0304";
			break;
		case "ABC":
			// 农业银行
			bankCode = "0103";
			break;
		case "BOCOM":
			// 交通银行
			bankCode = "0301";
			break;
		case "COMM":
			// 交通银行
			bankCode = "0301";
			break;
		case "BOC":
			// 中国银行
			bankCode = "0104";
			break;
		case "CITIC":
			// 中信实业银行
			bankCode = "0302";
			break;
		case "SPDB":
			// 上海浦东发展银行
			bankCode = "0310";
			break;
		case "ICBC":
			// 中国工商银行
			bankCode = "0102";
			break;
		case "CIB":
			// 兴业银行
			bankCode = "0309";
			break;
		case "PINGAN":
			// 平安银行股份有限公司
			bankCode = "0307";
			break;
		case "CMB":
			// 招商银行
			bankCode = "0308";
			break;
		case "CMBC":
			// 中国民生银行
			bankCode = "0305";
			break;
		case "GDB":
			// 广东发展银行
			bankCode = "0306";
			break;
		default:
			bankCode = "0000";
			break;
		}
		return bankCode;
	}

}
