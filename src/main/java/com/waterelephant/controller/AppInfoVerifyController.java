package com.waterelephant.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.service.entity.request.InsureLoginReqData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entiyt.middle.LoginResponse;
import com.waterelephant.constants.OrderStatusConstant;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwInsureAccout;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwInsureAccoutService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.service.InsureService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.Base64Utils;
import com.waterelephant.utils.CdnUploadTools;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;

/**
 * app信息验证控制器
 *
 * @author huwei
 */
@Controller
@RequestMapping("/app/infoVerify")
public class AppInfoVerifyController {
	private Logger logger = Logger.getLogger(AppMyController.class);

	@Autowired
	private IBwBorrowerService bwBorrowerService;

	@Autowired
	private IBwWorkInfoService bwWorkInfoService;

	@Autowired
	private IBwBankCardService bwBankCardService;

	@Autowired
	private IBwInsureAccoutService bwInsureAccoutService;

	@Autowired
	private InsureService insureService;

	@Autowired
	private IBwPersonInfoService bwPersonInfoService;

	@Autowired
	private IBwAdjunctService bwAdjunctService;

	@Autowired
	private IBwOrderService bwOrderService;

	@Autowired
	private BwOrderAuthService bwOrderAuthService;

	/**
	 * 借款人个人信息录入
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/saveOrUpdatePInfo.do")
	public AppResponseResult savePersonInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, Object> map = new HashMap<>();
			// 获取当前登录人id
			String bId = request.getParameter("bId");
			// 获取当前工单id
			String orderId = request.getParameter("orderId");
			// 历史个人信息Id
			String hisPId = request.getParameter("hisPId");
			// 个人信息
			String name = request.getParameter("name");
			String idCard = request.getParameter("idCard");
			String cityName = request.getParameter("cityName");
			String addr = request.getParameter("addr");
			String relationName = request.getParameter("relationName");
			String relationPhone = request.getParameter("relationPhone");
			String unrelationName = request.getParameter("unrelationName");
			String unrelationPhone = request.getParameter("unrelationPhone");
			String haveCar = request.getParameter("haveCar");
			String haveMarry = request.getParameter("haveMarry");
			String haveHouse = request.getParameter("haveHouse");
			logger.info("借款人id:" + bId);
			logger.info("工单id:" + orderId);
			logger.info(" 历史个人信息Id:" + hisPId);
			logger.info("身份证号:" + idCard);
			logger.info("所在城市:" + cityName);
			logger.info("亲属联系人电话:" + relationPhone);
			logger.info("非亲属联系人电话:" + unrelationPhone);
			if (CommUtils.isNull(bId)) {
				result.setCode("502");
				result.setMsg("借款人id为空");
				return result;
			}
			if (CommUtils.isNull(orderId)) {
				result.setCode("503");
				result.setMsg("当前工单id为空");
				return result;
			}
			if (CommUtils.isNull(name)) {
				result.setCode("504");
				result.setMsg("真实姓名为空");
				return result;
			}
			if (CommUtils.isNull(idCard)) {
				result.setCode("505");
				result.setMsg("身份证号为空");
				return result;
			}
			if (CommUtils.isNull(addr)) {
				result.setCode("506");
				result.setMsg("居住地址为空");
				return result;
			}
			if (CommUtils.isNull(relationName)) {
				result.setCode("507");
				result.setMsg("亲属联系人姓名为空");
				return result;
			}
			if (CommUtils.isNull(relationPhone)) {
				result.setCode("508");
				result.setMsg("亲属联系人电话为空");
				return result;
			}
			if (CommUtils.isNull(unrelationName)) {
				result.setCode("509");
				result.setMsg("非亲属联系人姓名为空");
				return result;
			}
			if (CommUtils.isNull(unrelationPhone)) {
				result.setCode("510");
				result.setMsg("非亲属联系人电话为空");
				return result;
			}
			if (CommUtils.isNull(cityName)) {
				result.setCode("511");
				result.setMsg("当前城市名称为空");
				return result;
			}
			if (CommUtils.isNull(haveCar)) {
				haveCar = "-1";
			}
			if (CommUtils.isNull(haveMarry)) {
				haveMarry = "-1";
			}
			if (CommUtils.isNull(haveHouse)) {
				haveHouse = "-1";
			}
			cityName = cityName.replaceAll("您当前所在城市：", "");
			relationPhone = relationPhone.replaceAll(" ", "");
			unrelationPhone = unrelationPhone.replaceAll(" ", "");
			/**
			 * 更改机构匹配
			 */
			String city = cityName.substring(cityName.length() - 3, cityName.length());
			// Map<String, Object> org = bwPersonInfoService.getOrgId(city);
			Map<String, Object> org = bwPersonInfoService.getCityId(city);
			BwOrder bo = new BwOrder();
			if (org != null) {
				String orgId = org.get("id").toString();
				bo.setOrgId(Long.valueOf(orgId));
				bo.setId(Long.valueOf(orderId));
				bwOrderService.updateOrg(bo);
			}
			// 根据借款人id查询身份证信息
			BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
			logger.info("借款人基本信息:" + bb);
			if (CommUtils.isNull(bb)) {
				result.setCode("512");
				result.setMsg("该借款人不存在或已被删除");
				return result;
			}
			// 修改借款人基本信息
			bb.setIdCard(idCard);
			bb.setName(name);
			bb.setUpdateTime(new Date());
			int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(idCard.substring(6, 10));
			bb.setAge(age);
			int sex = Integer.parseInt(idCard.substring(idCard.length() - 2, idCard.length() - 1)) % 2 == 0 ? 0 : 1;
			bb.setSex(sex);
			int bNum = bwBorrowerService.updateBwBorrower(bb);
			if (bNum <= 0) {
				result.setCode("513");
				result.setMsg("借款人基本信息录入失败");
				return result;
			}
			// 录入借款人personInfo 信息
			// 判断是否有历史个人信息
			if (CommUtils.isNull(hisPId)) {
				BwPersonInfo bpInfo = bwPersonInfoService.findBwPersonInfoByOrderId(Long.parseLong(orderId));
				if (CommUtils.isNull(bpInfo)) {
					bb.setAuthStep(2);
					bNum = bwBorrowerService.updateBwBorrower(bb);
					if (bNum <= 0) {
						result.setCode("513");
						result.setMsg("借款人基本信息录入失败");
						return result;
					}
					// 添加
					BwPersonInfo bpi = new BwPersonInfo();
					bpi.setOrderId(Long.parseLong(orderId));
					bpi.setCityName(cityName);
					bpi.setAddress(addr);
					bpi.setRelationName(relationName);
					bpi.setRelationPhone(relationPhone);
					bpi.setUnrelationName(unrelationName);
					bpi.setUnrelationPhone(unrelationPhone);
					bpi.setHouseStatus(Integer.parseInt(haveHouse));
					bpi.setMarryStatus(Integer.parseInt(haveMarry));
					bpi.setCarStatus(Integer.parseInt(haveCar));
					bpi.setCreateTime(new Date());
					Long pid = bwPersonInfoService.save(bpi);
					if (pid == 0) {
						result.setCode("501");
						result.setMsg("借款人个人信息录入失败");
						return result;
					}
					result.setCode("000");
					result.setMsg("借款人个人信息录入成功");
					map.put("pid", pid);
					map.put("orderId", orderId);
					map.put("bId", bId);
					result.setResult(map);
					return result;
				} else {
					// 修改
					bpInfo.setCityName(cityName);
					bpInfo.setAddress(addr);
					bpInfo.setRelationName(relationName);
					bpInfo.setRelationPhone(relationPhone);
					bpInfo.setUnrelationName(unrelationName);
					bpInfo.setUnrelationPhone(unrelationPhone);
					bpInfo.setHouseStatus(Integer.parseInt(haveHouse));
					bpInfo.setMarryStatus(Integer.parseInt(haveMarry));
					bpInfo.setCarStatus(Integer.parseInt(haveCar));
					bpInfo.setUpdateTime(new Date());
					int pNum = bwPersonInfoService.update(bpInfo);
					if (pNum > 0) {
						result.setCode("000");
						result.setMsg("借款人个人信息录入成功");
						map.put("pid", hisPId);
						map.put("orderId", orderId);
						map.put("bId", bId);
						result.setResult(map);
					} else {
						result.setCode("501");
						result.setMsg("借款人个人信息录入失败");
					}
				}
			} else {
				// 判断历史个人信息是否与当前工单绑定
				BwPersonInfo bpi = bwPersonInfoService.getByIdAndOrderId(Long.parseLong(hisPId),
						Long.parseLong(orderId));
				if (CommUtils.isNull(bpi)) {
					logger.info("历史个人信息id与当前工单id没有绑定,历史个人信息id为:" + hisPId);
					logger.info("工单id为:" + orderId);
					bb.setAuthStep(2);
					bNum = bwBorrowerService.updateBwBorrower(bb);
					if (bNum <= 0) {
						result.setCode("513");
						result.setMsg("借款人基本信息录入失败");
						return result;
					}
					// 添加
					BwPersonInfo bpInfo = new BwPersonInfo();
					bpInfo.setOrderId(Long.parseLong(orderId));
					bpInfo.setCityName(cityName);
					bpInfo.setAddress(addr);
					bpInfo.setRelationName(relationName);
					bpInfo.setRelationPhone(relationPhone);
					bpInfo.setUnrelationName(unrelationName);
					bpInfo.setUnrelationPhone(unrelationPhone);
					bpInfo.setHouseStatus(Integer.parseInt(haveHouse));
					bpInfo.setMarryStatus(Integer.parseInt(haveMarry));
					bpInfo.setCarStatus(Integer.parseInt(haveCar));
					bpInfo.setCreateTime(new Date());
					Long pid = bwPersonInfoService.save(bpInfo);
					if (pid != 0) {
						result.setCode("000");
						result.setMsg("借款人个人信息录入成功");
						map.put("pid", pid);
						map.put("orderId", orderId);
						map.put("bId", bId);
						result.setResult(map);
						return result;
					}
				} else {
					logger.info("历史个人信息id与当前工单id绑定了,历史个人信息id为:" + bpi.getId());
					logger.info("工单id为:" + bpi.getOrderId());
					// 修改工单个人信息
					bpi.setCityName(cityName);
					bpi.setAddress(addr);
					bpi.setRelationName(relationName);
					bpi.setRelationPhone(relationPhone);
					bpi.setUnrelationName(unrelationName);
					bpi.setUnrelationPhone(unrelationPhone);
					bpi.setHouseStatus(Integer.parseInt(haveHouse));
					bpi.setMarryStatus(Integer.parseInt(haveMarry));
					bpi.setCarStatus(Integer.parseInt(haveCar));
					bpi.setUpdateTime(new Date());
					int pNum = bwPersonInfoService.update(bpi);
					if (pNum > 0) {
						result.setCode("000");
						result.setMsg("借款人个人信息录入成功");
						map.put("pid", hisPId);
						map.put("orderId", orderId);
						map.put("bId", bId);
						result.setResult(map);
					} else {
						result.setCode("501");
						result.setMsg("借款人个人信息录入失败");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.setCode("502");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		return result;

	}

	/**
	 * 借款人工作信息录入
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/saveOrUpdateWorkInfo.do")
	public AppResponseResult saveWorkInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, Object> map = new HashMap<>();
			// 获取历史工作信息id
			String hisWId = request.getParameter("hisWId");
			// 获取历史银行卡信息id
			String hisBCId = request.getParameter("hisBCId");
			// 获取历史社保账号信息id
			String hisIAId = request.getParameter("hisIAId");
			// 获取当前工单id
			String orderId = request.getParameter("orderId");
			// 当前借款人id
			String bId = request.getParameter("bId");
			// 工作信息
			String comName = request.getParameter("comName");
			String callTime = request.getParameter("callTime");
			String workYears = request.getParameter("workYears");

			// 银行卡信息
			String cardNo = request.getParameter("cardNo");
			String cityCode = request.getParameter("cityCode");
			String provinceCode = request.getParameter("provinceCode");
			String bankCode = request.getParameter("bankCode");

			// 社保账号信息
			String cityId = request.getParameter("cityId");
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			String picCode = request.getParameter("picCode");
			String ruleId = request.getParameter("ruleId");
			String checkInsure = request.getParameter("checkInsure");
			logger.info("借款人id:" + bId);
			logger.info("当前工单id:" + orderId);
			logger.info("历史工作信息id:" + hisWId);
			logger.info("历史银行卡信息id:" + hisBCId);
			logger.info("历史社保账号信息id:" + hisIAId);
			logger.info("bankCode:" + bankCode);
			logger.info("cityCode:" + cityCode);
			logger.info("provinceCode:" + provinceCode);
			logger.info("checkInsure的值为:" + checkInsure);
			logger.info("图片验证码:" + picCode);
			if (CommUtils.isNull(bId)) {
				result.setCode("302");
				result.setMsg("借款人id为空");
				return result;
			}
			if (CommUtils.isNull(comName)) {
				result.setCode("303");
				result.setMsg("公司名称为空");
				return result;
			}
			if (CommUtils.isNull(callTime)) {
				result.setCode("304");
				result.setMsg("方便接听电话时间为空");
				return result;
			}
			if (CommUtils.isNull(workYears)) {
				result.setCode("305");
				result.setMsg("工龄为空");
				return result;
			}
			if (CommUtils.isNull(cardNo)) {
				result.setCode("307");
				result.setMsg("银行卡号为空");
				return result;
			}
			if (CommUtils.isNull(provinceCode)) {
				result.setCode("308");
				result.setMsg("富友省份code为空");
				return result;
			}
			if (CommUtils.isNull(bankCode)) {
				result.setCode("309");
				result.setMsg("富友银行code为空");
				return result;
			}
			if (CommUtils.isNull(cityCode)) {
				result.setCode("310");
				result.setMsg("富友城市code为空");
				return result;
			}
			if (CommUtils.isNull(orderId)) {
				result.setCode("311");
				result.setMsg("工单id为空");
				return result;
			}
			// 根据bankCode 从redis获取数据
			String bankName = RedisUtils.hget("fuiou:bank", bankCode);
			if (CommUtils.isNull(bankName)) {
				result.setCode("312");
				result.setMsg("银行名称获取为空");
				return result;
			}
			// 校验工作年限
			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(Long.valueOf(orderId));
			logger.info("获取到的借款人年龄为：" + borrower.getAge());
			logger.info("借款人选择的工作年限为：" + workYears);
			workYears = bwOrderAuthService.checkWorkYears(borrower.getAge(), workYears);
			logger.info("校验后的工作年限为：" + workYears);
			if ("UNKNOWN".equals(workYears)) {
				result.setCode("1002");
				result.setMsg("工作年限选项不正确");
				return result;
			}
			// 工作信息
			if (CommUtils.isNull(hisWId)) {
				// 添加工作信息
				BwWorkInfo bwi = new BwWorkInfo();
				bwi.setOrderId(Long.parseLong(orderId));
				bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
				if (CommUtils.isNull(bwi)) {
					BwWorkInfo bWorkInfo = new BwWorkInfo();
					bWorkInfo.setOrderId(Long.parseLong(orderId));
					bWorkInfo.setComName(comName);
					bWorkInfo.setCallTime(callTime);
					bWorkInfo.setWorkYears(workYears);
					bWorkInfo.setCreateTime(new Date());
					int num = bwWorkInfoService.save(bWorkInfo, Long.parseLong(bId));
					if (num <= 0) {
						result.setCode("301");
						result.setMsg("借款人工作信息录入失败");
						return result;
					}
				} else {
					// 修改
					bwi.setComName(comName);
					bwi.setCallTime(callTime);
					bwi.setWorkYears(workYears);
					bwi.setUpdateTime(new Date());
					int wiNum = bwWorkInfoService.update(bwi);
					if (wiNum <= 0) {
						result.setCode("301");
						result.setMsg("借款人工作信息修改失败");
						return result;
					}
					map.put("wId", bwi.getId());
				}

			} else {

				// 判断当前工单是否和个人工作信息绑定
				BwWorkInfo workInfo = new BwWorkInfo();
				workInfo.setId(Long.parseLong(hisWId));
				workInfo.setOrderId(Long.parseLong(orderId));
				workInfo = bwWorkInfoService.findBwWorkInfoByAttr(workInfo);
				if (CommUtils.isNull(workInfo)) {
					// 添加
					BwWorkInfo bwi = new BwWorkInfo();
					bwi.setOrderId(Long.parseLong(orderId));
					bwi.setComName(comName);
					bwi.setCallTime(callTime);
					bwi.setWorkYears(workYears);
					bwi.setCreateTime(new Date());
					int num = bwWorkInfoService.save(bwi, Long.parseLong(bId));
					if (num <= 0) {
						result.setCode("301");
						result.setMsg("借款人工作信息录入失败");
						return result;
					}
				} else {
					// 修改
					workInfo.setComName(comName);
					workInfo.setCallTime(callTime);
					workInfo.setWorkYears(workYears);
					workInfo.setUpdateTime(new Date());
					int wiNum = bwWorkInfoService.update(workInfo);
					if (wiNum <= 0) {
						result.setCode("301");
						result.setMsg("借款人工作信息修改失败");
						return result;
					}
					map.put("wId", hisWId);
				}
			}
			// 银行卡信息
			if (CommUtils.isNull(hisBCId)) {
				BwBankCard bCard = new BwBankCard();
				bCard.setBorrowerId(Long.parseLong(bId));
				bCard = bwBankCardService.findBwBankCardByAttr(bCard);
				if (CommUtils.isNull(bCard)) {
					// 添加银行卡信息
					BwBankCard bbc = new BwBankCard();
					bbc.setBorrowerId(Long.parseLong(bId));
					bbc.setCardNo(cardNo);
					bbc.setProvinceCode(provinceCode);
					bbc.setCityCode(cityCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setSignStatus(0);
					bbc.setCreateTime(new Date());
					int num = bwBankCardService.saveBwBankCard(bbc, Long.parseLong(bId));
					if (num <= 0) {
						result.setCode("301");
						result.setMsg("借款人银行卡信息录入失败");
						return result;
					}
				} else {
					bCard.setCardNo(cardNo);
					bCard.setProvinceCode(provinceCode);
					bCard.setCityCode(cityCode);
					bCard.setBankCode(bankCode);
					bCard.setBankName(bankName);
					bCard.setUpdateTime(new Date());
					int bbcNum = bwBankCardService.update(bCard);
					if (bbcNum == 0) {
						result.setCode("301");
						result.setMsg("借款人银行卡信息录入失败");
						return result;
					}
					map.put("bcId", bCard.getId());
				}

			} else {
				// 判断当前工单是否和银行卡信息
				BwBankCard bbc = new BwBankCard();
				bbc.setId(Long.parseLong(hisBCId));
				bbc.setBorrowerId(Long.parseLong(bId));
				bbc = bwBankCardService.findBwBankCardByAttr(bbc);
				if (CommUtils.isNull(bbc)) {
					// 添加
					bbc = new BwBankCard();
					bbc.setBorrowerId(Long.parseLong(bId));
					bbc.setCardNo(cardNo);
					bbc.setProvinceCode(provinceCode);
					bbc.setCityCode(cityCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setSignStatus(0);
					bbc.setCreateTime(new Date());
					int num = bwBankCardService.saveBwBankCard(bbc, Long.parseLong(bId));
					if (num <= 0) {
						result.setCode("301");
						result.setMsg("借款人银行卡信息录入失败");
						return result;
					}
				} else {
					// 修改
					bbc.setCardNo(cardNo);
					bbc.setProvinceCode(provinceCode);
					bbc.setCityCode(cityCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setUpdateTime(new Date());
					int bbcNum = bwBankCardService.update(bbc);
					if (bbcNum == 0) {
						result.setCode("301");
						result.setMsg("借款人银行卡信息录入失败");
						return result;
					}
					map.put("bcId", bbc.getId());
				}
			}
			// 社保输入不为空
			if (!CommUtils.isNull(checkInsure) && checkInsure.equals("1")) {
				if (CommUtils.isNull(cityId)) {
					result.setCode("313");
					result.setMsg("社保城市id为空");
					return result;
				}
				if (CommUtils.isNull(account)) {
					result.setCode("314");
					result.setMsg("社保账号为空");
					return result;
				}
				if (CommUtils.isNull(password)) {
					result.setCode("315");
					result.setMsg("社保密码为空");
					return result;
				}
				if (CommUtils.isNull(picCode)) {
					result.setCode("316");
					result.setMsg("社保登录验证图片验证码为空");
					return result;
				}
				if (CommUtils.isNull(ruleId)) {
					result.setCode("317");
					result.setMsg("社保登录规则id为空");
					return result;
				}
				logger.info("社保城市id:" + cityId);
				logger.info("社保账号:" + account);
				logger.info("密码:" + password);
				logger.info("图片验证码:" + picCode);
				logger.info("社保登录规则id:" + ruleId);
				logger.info("社保环境配置:" + SystemConstant.INSURE_CIRCUMSTANCE);
				// 登录
				// 判断社保环境配置，真是环境需要验证，测试环境不需要验证信息
				if (SystemConstant.INSURE_CIRCUMSTANCE.equals("0")) {
					InsureLoginReqData insureLoginReqData = new InsureLoginReqData();
					insureLoginReqData.setId_card(account);
					insureLoginReqData.setPassword(password);
					insureLoginReqData.setPic_code(picCode);
					insureLoginReqData.setRule_id(ruleId);
					insureLoginReqData.setUserId(bId);
					Response<LoginResponse> loginRes = insureService.login(insureLoginReqData);
					logger.info("社保登录结果" + loginRes.getRequestMsg());
					if (!loginRes.getRequestCode().equals("200")) {
						if (loginRes.getRequestCode().equals("210101")) {
							result.setCode("301");
							result.setMsg("图片验证码错误");
							result.setResult(loginRes.getObj());
							return result;
						} else {
							result.setCode("301");
							result.setMsg("社保账号或密码错误");
							result.setResult(loginRes.getObj());
							return result;
						}
					}
				}
				// 录入社保信息
				if (CommUtils.isNull(hisIAId)) {
					BwInsureAccout bia = new BwInsureAccout();
					bia.setOrderId(Long.parseLong(orderId));
					bia = bwInsureAccoutService.findBwInsureAccoutByAttr(bia);
					if (CommUtils.isNull(bia)) {
						// 添加
						// 社保账号录入
						BwInsureAccout biAccout = new BwInsureAccout();
						biAccout.setOrderId(Long.parseLong(orderId));
						biAccout.setAccount(account);
						biAccout.setPassword(password);
						biAccout.setCityCode(Long.parseLong(cityId));
						biAccout.setCreateTime(new Date());
						int num = bwInsureAccoutService.save(biAccout, Long.parseLong(bId));
						if (num <= 0) {
							result.setCode("301");
							result.setMsg("借款人社保账号录入失败");
							return result;
						}
					} else {
						// 修改
						bia.setAccount(account);
						bia.setPassword(password);
						bia.setCityCode(Long.parseLong(cityId));
						bia.setUpdateTime(new Date());
						int iaNum = bwInsureAccoutService.update(bia);
						if (iaNum <= 0) {
							result.setCode("301");
							result.setMsg("借款人社保账号修改失败");
							return result;
						}
						map.put("iaId", bia.getId());
					}
				} else {
					// 判断当前社保账号信息是否与当前工单绑定
					BwInsureAccout insureAccout = new BwInsureAccout();
					insureAccout.setId(Long.parseLong(hisIAId));
					insureAccout.setOrderId(Long.parseLong(orderId));
					insureAccout = bwInsureAccoutService.findBwInsureAccoutByAttr(insureAccout);
					if (CommUtils.isNull(insureAccout)) {
						// 添加
						insureAccout = new BwInsureAccout();
						insureAccout.setOrderId(Long.parseLong(orderId));
						insureAccout.setAccount(account);
						insureAccout.setPassword(password);
						insureAccout.setCityCode(Long.parseLong(cityId));
						insureAccout.setCreateTime(new Date());
						int num = bwInsureAccoutService.save(insureAccout, Long.parseLong(bId));
						if (num <= 0) {
							result.setCode("301");
							result.setMsg("借款人社保账号录入失败");
							return result;
						}
					} else {
						// 修改
						insureAccout.setOrderId(Long.parseLong(orderId));
						insureAccout.setAccount(account);
						insureAccout.setPassword(password);
						insureAccout.setCityCode(Long.parseLong(cityId));
						insureAccout.setUpdateTime(new Date());
						int iaNum = bwInsureAccoutService.update(insureAccout);
						if (iaNum <= 0) {
							result.setCode("301");
							result.setMsg("借款人社保账号修改失败");
							return result;
						}
						map.put("iaId", hisIAId);
					}
				}
			}
			map.put("bId", bId);
			map.put("orderId", orderId);
			result.setCode("000");
			result.setMsg("工作信息录入成功");
			result.setResult(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.setCode("302");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}

		return result;
	}

	/**
	 * 借款人工作信息录入（新增行业）
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/saveOrUpdateWorkInfoNew.do")
	public AppResponseResult saveOrUpdateWorkInfoNew(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, Object> map = new HashMap<>();
			// 获取历史工作信息id
			String hisWId = request.getParameter("hisWId");
			// 获取历史银行卡信息id
			String hisBCId = request.getParameter("hisBCId");
			// 获取历史社保账号信息id
			String hisIAId = request.getParameter("hisIAId");
			// 获取当前工单id
			String orderId = request.getParameter("orderId");
			// 当前借款人id
			String bId = request.getParameter("bId");
			// 工作信息
			String comName = request.getParameter("comName");
			String industry = request.getParameter("industry");
			String callTime = request.getParameter("callTime");
			String workYears = request.getParameter("workYears");
			// 银行卡信息
			String cardNo = request.getParameter("cardNo");
			String cityCode = request.getParameter("cityCode");
			String provinceCode = request.getParameter("provinceCode");
			String bankCode = request.getParameter("bankCode");

			// 社保账号信息
			String cityId = request.getParameter("cityId");
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			String picCode = request.getParameter("picCode");
			String ruleId = request.getParameter("ruleId");
			String checkInsure = request.getParameter("checkInsure");
			logger.info("借款人id:" + bId);
			logger.info("当前工单id:" + orderId);
			logger.info("历史工作信息id:" + hisWId);
			logger.info("历史银行卡信息id:" + hisBCId);
			logger.info("历史社保账号信息id:" + hisIAId);
			logger.info("bankCode:" + bankCode);
			logger.info("cityCode:" + cityCode);
			logger.info("provinceCode:" + provinceCode);
			logger.info("checkInsure的值为:" + checkInsure);
			logger.info("图片验证码:" + picCode);
			if (CommUtils.isNull(bId)) {
				result.setCode("302");
				result.setMsg("借款人id为空");
				return result;
			}
			if (CommUtils.isNull(comName)) {
				result.setCode("303");
				result.setMsg("公司名称为空");
				return result;
			}
			if (CommUtils.isNull(industry)) {
				result.setCode("318");
				result.setMsg("工作行业为空");
				return result;
			}
			if (CommUtils.isNull(callTime)) {
				result.setCode("304");
				result.setMsg("方便接听电话时间为空");
				return result;
			}
			if (CommUtils.isNull(workYears)) {
				result.setCode("305");
				result.setMsg("工龄为空");
				return result;
			}
			if (CommUtils.isNull(cardNo)) {
				result.setCode("307");
				result.setMsg("银行卡号为空");
				return result;
			}
			if (CommUtils.isNull(provinceCode)) {
				result.setCode("308");
				result.setMsg("富友省份code为空");
				return result;
			}
			if (CommUtils.isNull(bankCode)) {
				result.setCode("309");
				result.setMsg("富友银行code为空");
				return result;
			}
			if (CommUtils.isNull(cityCode)) {
				result.setCode("310");
				result.setMsg("富友城市code为空");
				return result;
			}
			if (CommUtils.isNull(orderId)) {
				result.setCode("311");
				result.setMsg("工单id为空");
				return result;
			}
			// 根据bankCode 从redis获取数据
			String bankName = RedisUtils.hget("fuiou:bank", bankCode);
			if (CommUtils.isNull(bankName)) {
				result.setCode("312");
				result.setMsg("银行名称获取为空");
				return result;
			}
			// 校验工作年限
			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(Long.valueOf(orderId));
			logger.info("获取到的借款人年龄为：" + borrower.getAge());
			logger.info("借款人选择的工作年限为：" + workYears);
			workYears = bwOrderAuthService.checkWorkYears(borrower.getAge(), workYears);
			logger.info("校验后的工作年限为：" + workYears);
			if ("UNKNOWN".equals(workYears)) {
				result.setCode("1002");
				result.setMsg("工作年限选项不正确");
				return result;
			}
			// 工作信息
			if (CommUtils.isNull(hisWId)) {
				// 添加工作信息
				BwWorkInfo bwi = new BwWorkInfo();
				bwi.setOrderId(Long.parseLong(orderId));
				bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
				if (CommUtils.isNull(bwi)) {
					BwWorkInfo bWorkInfo = new BwWorkInfo();
					bWorkInfo.setOrderId(Long.parseLong(orderId));
					bWorkInfo.setComName(comName);
					bWorkInfo.setIndustry(industry);
					bWorkInfo.setCallTime(callTime);
					bWorkInfo.setWorkYears(workYears);
					bWorkInfo.setCreateTime(new Date());
					int num = bwWorkInfoService.save(bWorkInfo, Long.parseLong(bId));
					if (num <= 0) {
						result.setCode("301");
						result.setMsg("借款人工作信息录入失败");
						return result;
					}
				} else {
					// 修改
					bwi.setComName(comName);
					bwi.setIndustry(industry);
					bwi.setCallTime(callTime);
					bwi.setWorkYears(workYears);
					bwi.setUpdateTime(new Date());
					int wiNum = bwWorkInfoService.update(bwi);
					if (wiNum <= 0) {
						result.setCode("301");
						result.setMsg("借款人工作信息修改失败");
						return result;
					}
					map.put("wId", bwi.getId());
				}

			} else {

				// 判断当前工单是否和个人工作信息绑定
				BwWorkInfo workInfo = new BwWorkInfo();
				workInfo.setId(Long.parseLong(hisWId));
				workInfo.setOrderId(Long.parseLong(orderId));
				workInfo = bwWorkInfoService.findBwWorkInfoByAttr(workInfo);
				if (CommUtils.isNull(workInfo)) {
					// 添加
					BwWorkInfo bwi = new BwWorkInfo();
					bwi.setOrderId(Long.parseLong(orderId));
					bwi.setComName(comName);
					bwi.setIndustry(industry);
					bwi.setCallTime(callTime);
					bwi.setWorkYears(workYears);
					bwi.setCreateTime(new Date());
					int num = bwWorkInfoService.save(bwi, Long.parseLong(bId));
					if (num <= 0) {
						result.setCode("301");
						result.setMsg("借款人工作信息录入失败");
						return result;
					}
				} else {
					// 修改
					workInfo.setComName(comName);
					workInfo.setIndustry(industry);
					workInfo.setCallTime(callTime);
					workInfo.setWorkYears(workYears);
					workInfo.setUpdateTime(new Date());
					int wiNum = bwWorkInfoService.update(workInfo);
					if (wiNum <= 0) {
						result.setCode("301");
						result.setMsg("借款人工作信息修改失败");
						return result;
					}
					map.put("wId", hisWId);
				}
			}
			// 银行卡信息
			if (CommUtils.isNull(hisBCId)) {
				BwBankCard bCard = new BwBankCard();
				bCard.setBorrowerId(Long.parseLong(bId));
				bCard = bwBankCardService.findBwBankCardByAttr(bCard);
				if (CommUtils.isNull(bCard)) {
					// 添加银行卡信息
					BwBankCard bbc = new BwBankCard();
					bbc.setBorrowerId(Long.parseLong(bId));
					bbc.setCardNo(cardNo);
					bbc.setProvinceCode(provinceCode);
					bbc.setCityCode(cityCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setSignStatus(0);
					bbc.setCreateTime(new Date());
					int num = bwBankCardService.saveBwBankCard(bbc, Long.parseLong(bId));
					if (num <= 0) {
						result.setCode("301");
						result.setMsg("借款人银行卡信息录入失败");
						return result;
					}
				} else {
					bCard.setCardNo(cardNo);
					bCard.setProvinceCode(provinceCode);
					bCard.setCityCode(cityCode);
					bCard.setBankCode(bankCode);
					bCard.setBankName(bankName);
					bCard.setUpdateTime(new Date());
					int bbcNum = bwBankCardService.update(bCard);
					if (bbcNum == 0) {
						result.setCode("301");
						result.setMsg("借款人银行卡信息录入失败");
						return result;
					}
					map.put("bcId", bCard.getId());
				}

			} else {
				// 判断当前工单是否和银行卡信息
				BwBankCard bbc = new BwBankCard();
				bbc.setId(Long.parseLong(hisBCId));
				bbc.setBorrowerId(Long.parseLong(bId));
				bbc = bwBankCardService.findBwBankCardByAttr(bbc);
				if (CommUtils.isNull(bbc)) {
					// 添加
					bbc = new BwBankCard();
					bbc.setBorrowerId(Long.parseLong(bId));
					bbc.setCardNo(cardNo);
					bbc.setProvinceCode(provinceCode);
					bbc.setCityCode(cityCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setSignStatus(0);
					bbc.setCreateTime(new Date());
					int num = bwBankCardService.saveBwBankCard(bbc, Long.parseLong(bId));
					if (num <= 0) {
						result.setCode("301");
						result.setMsg("借款人银行卡信息录入失败");
						return result;
					}
				} else {
					// 修改
					bbc.setCardNo(cardNo);
					bbc.setProvinceCode(provinceCode);
					bbc.setCityCode(cityCode);
					bbc.setBankCode(bankCode);
					bbc.setBankName(bankName);
					bbc.setUpdateTime(new Date());
					int bbcNum = bwBankCardService.update(bbc);
					if (bbcNum == 0) {
						result.setCode("301");
						result.setMsg("借款人银行卡信息录入失败");
						return result;
					}
					map.put("bcId", bbc.getId());
				}
			}
			// 社保输入不为空
			if (!CommUtils.isNull(checkInsure) && checkInsure.equals("1")) {
				if (CommUtils.isNull(cityId)) {
					result.setCode("313");
					result.setMsg("社保城市id为空");
					return result;
				}
				if (CommUtils.isNull(account)) {
					result.setCode("314");
					result.setMsg("社保账号为空");
					return result;
				}
				if (CommUtils.isNull(password)) {
					result.setCode("315");
					result.setMsg("社保密码为空");
					return result;
				}
				if (CommUtils.isNull(picCode)) {
					result.setCode("316");
					result.setMsg("社保登录验证图片验证码为空");
					return result;
				}
				if (CommUtils.isNull(ruleId)) {
					result.setCode("317");
					result.setMsg("社保登录规则id为空");
					return result;
				}
				logger.info("社保城市id:" + cityId);
				logger.info("社保账号:" + account);
				logger.info("密码:" + password);
				logger.info("图片验证码:" + picCode);
				logger.info("社保登录规则id:" + ruleId);
				logger.info("社保环境配置:" + SystemConstant.INSURE_CIRCUMSTANCE);
				// 登录
				// 判断社保环境配置，真是环境需要验证，测试环境不需要验证信息
				if (SystemConstant.INSURE_CIRCUMSTANCE.equals("0")) {
					InsureLoginReqData insureLoginReqData = new InsureLoginReqData();
					insureLoginReqData.setId_card(account);
					insureLoginReqData.setPassword(password);
					insureLoginReqData.setPic_code(picCode);
					insureLoginReqData.setRule_id(ruleId);
					insureLoginReqData.setUserId(bId);
					Response<LoginResponse> loginRes = insureService.login(insureLoginReqData);
					logger.info("社保登录结果" + loginRes.getRequestMsg());
					if (!loginRes.getRequestCode().equals("200")) {
						if (loginRes.getRequestCode().equals("210101")) {
							result.setCode("301");
							result.setMsg("图片验证码错误");
							result.setResult(loginRes.getObj());
							return result;
						} else {
							result.setCode("301");
							result.setMsg("社保账号或密码错误");
							result.setResult(loginRes.getObj());
							return result;
						}
					}
				}
				// 录入社保信息
				if (CommUtils.isNull(hisIAId)) {
					BwInsureAccout bia = new BwInsureAccout();
					bia.setOrderId(Long.parseLong(orderId));
					bia = bwInsureAccoutService.findBwInsureAccoutByAttr(bia);
					if (CommUtils.isNull(bia)) {
						// 添加
						// 社保账号录入
						BwInsureAccout biAccout = new BwInsureAccout();
						biAccout.setOrderId(Long.parseLong(orderId));
						biAccout.setAccount(account);
						biAccout.setPassword(password);
						biAccout.setCityCode(Long.parseLong(cityId));
						biAccout.setCreateTime(new Date());
						int num = bwInsureAccoutService.save(biAccout, Long.parseLong(bId));
						if (num <= 0) {
							result.setCode("301");
							result.setMsg("借款人社保账号录入失败");
							return result;
						}
					} else {
						// 修改
						bia.setAccount(account);
						bia.setPassword(password);
						bia.setCityCode(Long.parseLong(cityId));
						bia.setUpdateTime(new Date());
						int iaNum = bwInsureAccoutService.update(bia);
						if (iaNum <= 0) {
							result.setCode("301");
							result.setMsg("借款人社保账号修改失败");
							return result;
						}
						map.put("iaId", bia.getId());
					}
				} else {
					// 判断当前社保账号信息是否与当前工单绑定
					BwInsureAccout insureAccout = new BwInsureAccout();
					insureAccout.setId(Long.parseLong(hisIAId));
					insureAccout.setOrderId(Long.parseLong(orderId));
					insureAccout = bwInsureAccoutService.findBwInsureAccoutByAttr(insureAccout);
					if (CommUtils.isNull(insureAccout)) {
						// 添加
						insureAccout = new BwInsureAccout();
						insureAccout.setOrderId(Long.parseLong(orderId));
						insureAccout.setAccount(account);
						insureAccout.setPassword(password);
						insureAccout.setCityCode(Long.parseLong(cityId));
						insureAccout.setCreateTime(new Date());
						int num = bwInsureAccoutService.save(insureAccout, Long.parseLong(bId));
						if (num <= 0) {
							result.setCode("301");
							result.setMsg("借款人社保账号录入失败");
							return result;
						}
					} else {
						// 修改
						insureAccout.setOrderId(Long.parseLong(orderId));
						insureAccout.setAccount(account);
						insureAccout.setPassword(password);
						insureAccout.setCityCode(Long.parseLong(cityId));
						insureAccout.setUpdateTime(new Date());
						int iaNum = bwInsureAccoutService.update(insureAccout);
						if (iaNum <= 0) {
							result.setCode("301");
							result.setMsg("借款人社保账号修改失败");
							return result;
						}
						map.put("iaId", hisIAId);
					}
				}
			}
			map.put("bId", bId);
			map.put("orderId", orderId);
			result.setCode("000");
			result.setMsg("工作信息录入成功");
			result.setResult(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.setCode("302");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}

		return result;
	}

	/**
	 * 图片附件在线提交
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/saveOrUpdateAdjunct.do")
	public AppResponseResult saveOrUpdateAdjunct(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, Object> map = new HashMap<>();
			String hisgpId = request.getParameter("hisgpId");
			String hissfzzmId = request.getParameter("hissfzzmId");
			String hissfzfmId = request.getParameter("hissfzfmId");
			String orderId = request.getParameter("orderId");
			String bId = request.getParameter("bId");
			// 图片上传地址
			String gpUrl = request.getParameter("gpUrl");
			String sfzzmUrl = request.getParameter("sfzzmUrl");
			String sfzfmUrl = request.getParameter("sfzfmUrl");
			if (CommUtils.isNull(bId)) {
				result.setCode("302");
				result.setMsg("借款人id为空");
				return result;
			}
			if (CommUtils.isNull(orderId)) {
				result.setCode("303");
				result.setMsg("当前工单id为空");
				return result;
			}
			if (CommUtils.isNull(gpUrl)) {
				result.setCode("304");
				result.setMsg("工牌图片url为空");
				return result;
			}
			if (CommUtils.isNull(sfzzmUrl)) {
				result.setCode("305");
				result.setMsg("身份证正面图片url为空");
				return result;
			}
			if (CommUtils.isNull(sfzfmUrl)) {
				result.setCode("306");
				result.setMsg("身份证反面图片url为空");
				return result;
			}
			// 工牌图片
			if (CommUtils.isNull(hisgpId)) {
				BwAdjunct bwAdjunct = new BwAdjunct();
				bwAdjunct.setOrderId(Long.parseLong(orderId));
				bwAdjunct.setAdjunctType(4);
				bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
				if (CommUtils.isNull(bwAdjunct)) {
					// 添加
					BwAdjunct gpbaj = new BwAdjunct();
					gpbaj.setAdjunctType(4);
					gpbaj.setAdjunctPath(gpUrl);
					gpbaj.setOrderId(Long.parseLong(orderId));
					gpbaj.setBorrowerId(Long.parseLong(bId));
					gpbaj.setCreateTime(new Date());
					Long gpId = bwAdjunctService.save(gpbaj);
					if (CommUtils.isNull(gpId)) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", gpId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					bwAdjunct.setAdjunctPath(gpUrl);
					bwAdjunct.setUpdateTime(new Date());
					int gpNum = bwAdjunctService.update(bwAdjunct);
					if (gpNum <= 0) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", bwAdjunct.getId());
				}

			} else {
				// 判断工牌图片附件是否和当前工单绑定
				BwAdjunct bwAdjunct = new BwAdjunct();
				bwAdjunct.setId(Long.parseLong(hisgpId));
				bwAdjunct.setOrderId(Long.parseLong(orderId));
				bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
				if (CommUtils.isNull(bwAdjunct)) {
					// 添加
					bwAdjunct = new BwAdjunct();
					bwAdjunct.setAdjunctType(4);
					bwAdjunct.setAdjunctPath(gpUrl);
					bwAdjunct.setOrderId(Long.parseLong(orderId));
					bwAdjunct.setBorrowerId(Long.parseLong(bId));
					bwAdjunct.setCreateTime(new Date());
					Long gpId = bwAdjunctService.save(bwAdjunct);
					if (CommUtils.isNull(gpId)) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", gpId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					bwAdjunct.setAdjunctType(4);
					bwAdjunct.setAdjunctPath(gpUrl);
					bwAdjunct.setUpdateTime(new Date());
					int gpNum = bwAdjunctService.update(bwAdjunct);
					if (gpNum <= 0) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", hisgpId);
				}
			}
			// 身份证正面
			if (CommUtils.isNull(hissfzzmId)) {
				BwAdjunct sfzzmbaj = new BwAdjunct();
				sfzzmbaj.setOrderId(Long.parseLong(orderId));
				sfzzmbaj.setAdjunctType(1);
				sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);
				if (CommUtils.isNull(sfzzmbaj)) {
					// 添加
					sfzzmbaj = new BwAdjunct();
					sfzzmbaj.setAdjunctType(1);
					sfzzmbaj.setAdjunctPath(sfzzmUrl);
					sfzzmbaj.setOrderId(Long.parseLong(orderId));
					sfzzmbaj.setBorrowerId(Long.parseLong(bId));
					sfzzmbaj.setCreateTime(new Date());
					Long sfzzmId = bwAdjunctService.save(sfzzmbaj);
					if (CommUtils.isNull(sfzzmId)) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					} else {
						map.put("sfzzmId", sfzzmId);
						// 修改借款人状态
						BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
						bb.setAuthStep(4);
						bb.setUpdateTime(new Date());
						// 将个人基本信息录入到borrower表中
						int bbNum = bwBorrowerService.updateBwBorrower(bb);
						logger.info("修改借款人状态结果：" + bbNum);
					}
				} else {
					sfzzmbaj.setAdjunctPath(sfzzmUrl);
					sfzzmbaj.setUpdateTime(new Date());
					int sfzzmNum = bwAdjunctService.update(sfzzmbaj);
					if (sfzzmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", sfzzmbaj.getId());
				}

			} else {
				// 判断身份证正面图片附件是否和当前工单绑定
				BwAdjunct zmbaj = new BwAdjunct();
				zmbaj.setId(Long.parseLong(hissfzzmId));
				zmbaj.setOrderId(Long.parseLong(orderId));
				zmbaj = bwAdjunctService.findBwAdjunctByAttr(zmbaj);
				if (CommUtils.isNull(zmbaj)) {
					// 添加
					zmbaj = new BwAdjunct();
					zmbaj.setAdjunctType(1);
					zmbaj.setAdjunctPath(sfzzmUrl);
					zmbaj.setOrderId(Long.parseLong(orderId));
					zmbaj.setBorrowerId(Long.parseLong(bId));
					zmbaj.setCreateTime(new Date());
					Long zmId = bwAdjunctService.save(zmbaj);
					if (CommUtils.isNull(zmId)) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", zmId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					bb.setUpdateTime(new Date());
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					zmbaj.setAdjunctType(1);
					zmbaj.setAdjunctPath(sfzzmUrl);
					zmbaj.setUpdateTime(new Date());
					int sfzzmNum = bwAdjunctService.update(zmbaj);
					if (sfzzmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", hissfzzmId);
				}
			}
			// 身份证反面
			if (CommUtils.isNull(hissfzfmId)) {
				BwAdjunct sfzfmbaj = new BwAdjunct();
				sfzfmbaj.setOrderId(Long.parseLong(orderId));
				sfzfmbaj.setAdjunctType(2);
				sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);
				if (CommUtils.isNull(sfzfmbaj)) {
					// 添加
					sfzfmbaj = new BwAdjunct();
					sfzfmbaj.setAdjunctType(2);
					sfzfmbaj.setAdjunctPath(sfzfmUrl);
					sfzfmbaj.setOrderId(Long.parseLong(orderId));
					sfzfmbaj.setBorrowerId(Long.parseLong(bId));
					sfzfmbaj.setCreateTime(new Date());
					Long sfzfmId = bwAdjunctService.save(sfzfmbaj);
					if (CommUtils.isNull(sfzfmId)) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					} else {
						map.put("sfzfmId", sfzfmId);
						// 修改借款人状态
						BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
						bb.setAuthStep(4);
						bb.setUpdateTime(new Date());
						// 将个人基本信息录入到borrower表中
						int bbNum = bwBorrowerService.updateBwBorrower(bb);
						logger.info("修改借款人状态结果：" + bbNum);
					}
				} else {
					sfzfmbaj.setAdjunctPath(sfzfmUrl);
					sfzfmbaj.setUpdateTime(new Date());
					int sfzfmNum = bwAdjunctService.update(sfzfmbaj);
					if (sfzfmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", sfzfmbaj.getId());
				}

			} else {
				// 判断身份证正面图片附件是否和当前工单绑定
				BwAdjunct fmbaj = new BwAdjunct();
				fmbaj.setId(Long.parseLong(hissfzfmId));
				fmbaj.setOrderId(Long.parseLong(orderId));
				fmbaj = bwAdjunctService.findBwAdjunctByAttr(fmbaj);
				if (CommUtils.isNull(fmbaj)) {
					// 添加
					fmbaj = new BwAdjunct();
					fmbaj.setAdjunctType(2);
					fmbaj.setAdjunctPath(sfzfmUrl);
					fmbaj.setOrderId(Long.parseLong(orderId));
					fmbaj.setBorrowerId(Long.parseLong(bId));
					fmbaj.setCreateTime(new Date());
					Long fmId = bwAdjunctService.save(fmbaj);
					if (CommUtils.isNull(fmId)) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", fmId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					bb.setUpdateTime(new Date());
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					fmbaj.setAdjunctType(2);
					fmbaj.setAdjunctPath(sfzfmUrl);
					fmbaj.setUpdateTime(new Date());
					int sfzfmNum = bwAdjunctService.update(fmbaj);
					if (sfzfmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", hissfzfmId);
				}
			}
			// 禁用接口
			result.setCode("999");
			result.setMsg("您当前版本过旧，请升级到最新版本。若您当前使用微信借款，请清理缓存后再申请。");
			return result;
			// BwOrder bo = bwOrderService.findBwOrderById(orderId);
			// bo.setStatusId(2l);
			// bo.setUpdateTime(new Date());
			// int oNum = bwOrderService.updateBwOrder(bo);
			// if (oNum > 0) {
			// result.setCode("000");
			// result.setMsg("图片附件上传成功");
			// result.setResult(map);
			// } else {
			// result.setCode("322");
			// result.setMsg("修改工单状态失败");
			// result.setResult(map);
			// }
			// // 将待审核的信息放入Redis中
			// BwBorrower borrower = new BwBorrower();
			// borrower.setId(bo.getBorrowerId());
			// borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			// SystemAuditDto systemAuditDto = new SystemAuditDto();
			// systemAuditDto.setOrderId(bo.getId());
			// systemAuditDto.setBorrowerId(bo.getBorrowerId());
			// systemAuditDto.setName(borrower.getName());
			// systemAuditDto.setPhone(borrower.getPhone());
			// systemAuditDto.setIdCard(borrower.getIdCard());
			// systemAuditDto.setCreateTime(new Date());
			// systemAuditDto.setIncludeAddressBook(1);// 包含通讯录
			// RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
			// JsonUtils.toJson(systemAuditDto));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.setCode("324");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		// result.setCode("000");
		// result.setMsg("成功");
		// return result;
	}

	/**
	 * 图片附件在线提交
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/saveOrUpdateAdjunctNewMoxie.do")
	public AppResponseResult saveOrUpdateAdjunctNewMoxie(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, Object> map = new HashMap<>();
			String hisgpId = request.getParameter("hisgpId");
			String hissfzzmId = request.getParameter("hissfzzmId");
			String hissfzfmId = request.getParameter("hissfzfmId");
			String orderId = request.getParameter("orderId");
			String bId = request.getParameter("bId");
			// 图片上传地址
			String gpUrl = request.getParameter("gpUrl");
			String sfzzmUrl = request.getParameter("sfzzmUrl");
			String sfzfmUrl = request.getParameter("sfzfmUrl");
			if (CommUtils.isNull(bId)) {
				result.setCode("302");
				result.setMsg("借款人id为空");
				return result;
			}
			if (CommUtils.isNull(orderId)) {
				result.setCode("303");
				result.setMsg("当前工单id为空");
				return result;
			}
			if (CommUtils.isNull(gpUrl)) {
				result.setCode("304");
				result.setMsg("工牌图片url为空");
				return result;
			}
			if (CommUtils.isNull(sfzzmUrl)) {
				result.setCode("305");
				result.setMsg("身份证正面图片url为空");
				return result;
			}
			if (CommUtils.isNull(sfzfmUrl)) {
				result.setCode("306");
				result.setMsg("身份证反面图片url为空");
				return result;
			}
			// 工牌图片
			if (CommUtils.isNull(hisgpId)) {
				BwAdjunct bwAdjunct = new BwAdjunct();
				bwAdjunct.setOrderId(Long.parseLong(orderId));
				bwAdjunct.setAdjunctType(4);
				bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
				if (CommUtils.isNull(bwAdjunct)) {
					// 添加
					BwAdjunct gpbaj = new BwAdjunct();
					gpbaj.setAdjunctType(4);
					gpbaj.setAdjunctPath(gpUrl);
					gpbaj.setOrderId(Long.parseLong(orderId));
					gpbaj.setBorrowerId(Long.parseLong(bId));
					gpbaj.setCreateTime(new Date());
					Long gpId = bwAdjunctService.save(gpbaj);
					if (CommUtils.isNull(gpId)) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", gpId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					bwAdjunct.setAdjunctPath(gpUrl);
					bwAdjunct.setUpdateTime(new Date());
					int gpNum = bwAdjunctService.update(bwAdjunct);
					if (gpNum <= 0) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", bwAdjunct.getId());
				}

			} else {
				// 判断工牌图片附件是否和当前工单绑定
				BwAdjunct bwAdjunct = new BwAdjunct();
				bwAdjunct.setId(Long.parseLong(hisgpId));
				bwAdjunct.setOrderId(Long.parseLong(orderId));
				bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
				if (CommUtils.isNull(bwAdjunct)) {
					// 添加
					bwAdjunct = new BwAdjunct();
					bwAdjunct.setAdjunctType(4);
					bwAdjunct.setAdjunctPath(gpUrl);
					bwAdjunct.setOrderId(Long.parseLong(orderId));
					bwAdjunct.setBorrowerId(Long.parseLong(bId));
					bwAdjunct.setCreateTime(new Date());
					Long gpId = bwAdjunctService.save(bwAdjunct);
					if (CommUtils.isNull(gpId)) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", gpId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					bwAdjunct.setAdjunctType(4);
					bwAdjunct.setAdjunctPath(gpUrl);
					bwAdjunct.setUpdateTime(new Date());
					int gpNum = bwAdjunctService.update(bwAdjunct);
					if (gpNum <= 0) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", hisgpId);
				}
			}
			// 身份证正面
			if (CommUtils.isNull(hissfzzmId)) {
				BwAdjunct sfzzmbaj = new BwAdjunct();
				sfzzmbaj.setOrderId(Long.parseLong(orderId));
				sfzzmbaj.setAdjunctType(1);
				sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);
				if (CommUtils.isNull(sfzzmbaj)) {
					// 添加
					sfzzmbaj = new BwAdjunct();
					sfzzmbaj.setAdjunctType(1);
					sfzzmbaj.setAdjunctPath(sfzzmUrl);
					sfzzmbaj.setOrderId(Long.parseLong(orderId));
					sfzzmbaj.setBorrowerId(Long.parseLong(bId));
					sfzzmbaj.setCreateTime(new Date());
					Long sfzzmId = bwAdjunctService.save(sfzzmbaj);
					if (CommUtils.isNull(sfzzmId)) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					} else {
						map.put("sfzzmId", sfzzmId);
						// 修改借款人状态
						BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
						bb.setAuthStep(4);
						bb.setUpdateTime(new Date());
						// 将个人基本信息录入到borrower表中
						int bbNum = bwBorrowerService.updateBwBorrower(bb);
						logger.info("修改借款人状态结果：" + bbNum);
					}
				} else {
					sfzzmbaj.setAdjunctPath(sfzzmUrl);
					sfzzmbaj.setUpdateTime(new Date());
					int sfzzmNum = bwAdjunctService.update(sfzzmbaj);
					if (sfzzmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", sfzzmbaj.getId());
				}

			} else {
				// 判断身份证正面图片附件是否和当前工单绑定
				BwAdjunct zmbaj = new BwAdjunct();
				zmbaj.setId(Long.parseLong(hissfzzmId));
				zmbaj.setOrderId(Long.parseLong(orderId));
				zmbaj = bwAdjunctService.findBwAdjunctByAttr(zmbaj);
				if (CommUtils.isNull(zmbaj)) {
					// 添加
					zmbaj = new BwAdjunct();
					zmbaj.setAdjunctType(1);
					zmbaj.setAdjunctPath(sfzzmUrl);
					zmbaj.setOrderId(Long.parseLong(orderId));
					zmbaj.setBorrowerId(Long.parseLong(bId));
					zmbaj.setCreateTime(new Date());
					Long zmId = bwAdjunctService.save(zmbaj);
					if (CommUtils.isNull(zmId)) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", zmId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					bb.setUpdateTime(new Date());
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					zmbaj.setAdjunctType(1);
					zmbaj.setAdjunctPath(sfzzmUrl);
					zmbaj.setUpdateTime(new Date());
					int sfzzmNum = bwAdjunctService.update(zmbaj);
					if (sfzzmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", hissfzzmId);
				}
			}
			// 身份证反面
			if (CommUtils.isNull(hissfzfmId)) {
				BwAdjunct sfzfmbaj = new BwAdjunct();
				sfzfmbaj.setOrderId(Long.parseLong(orderId));
				sfzfmbaj.setAdjunctType(2);
				sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);
				if (CommUtils.isNull(sfzfmbaj)) {
					// 添加
					sfzfmbaj = new BwAdjunct();
					sfzfmbaj.setAdjunctType(2);
					sfzfmbaj.setAdjunctPath(sfzfmUrl);
					sfzfmbaj.setOrderId(Long.parseLong(orderId));
					sfzfmbaj.setBorrowerId(Long.parseLong(bId));
					sfzfmbaj.setCreateTime(new Date());
					Long sfzfmId = bwAdjunctService.save(sfzfmbaj);
					if (CommUtils.isNull(sfzfmId)) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					} else {
						map.put("sfzfmId", sfzfmId);
						// 修改借款人状态
						BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
						bb.setAuthStep(4);
						bb.setUpdateTime(new Date());
						// 将个人基本信息录入到borrower表中
						int bbNum = bwBorrowerService.updateBwBorrower(bb);
						logger.info("修改借款人状态结果：" + bbNum);
					}
				} else {
					sfzfmbaj.setAdjunctPath(sfzfmUrl);
					sfzfmbaj.setUpdateTime(new Date());
					int sfzfmNum = bwAdjunctService.update(sfzfmbaj);
					if (sfzfmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", sfzfmbaj.getId());
				}

			} else {
				// 判断身份证正面图片附件是否和当前工单绑定
				BwAdjunct fmbaj = new BwAdjunct();
				fmbaj.setId(Long.parseLong(hissfzfmId));
				fmbaj.setOrderId(Long.parseLong(orderId));
				fmbaj = bwAdjunctService.findBwAdjunctByAttr(fmbaj);
				if (CommUtils.isNull(fmbaj)) {
					// 添加
					fmbaj = new BwAdjunct();
					fmbaj.setAdjunctType(2);
					fmbaj.setAdjunctPath(sfzfmUrl);
					fmbaj.setOrderId(Long.parseLong(orderId));
					fmbaj.setBorrowerId(Long.parseLong(bId));
					fmbaj.setCreateTime(new Date());
					Long fmId = bwAdjunctService.save(fmbaj);
					if (CommUtils.isNull(fmId)) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", fmId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					bb.setUpdateTime(new Date());
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					fmbaj.setAdjunctType(2);
					fmbaj.setAdjunctPath(sfzfmUrl);
					fmbaj.setUpdateTime(new Date());
					int sfzfmNum = bwAdjunctService.update(fmbaj);
					if (sfzfmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", hissfzfmId);
				}
			}
			// 禁用接口
			result.setCode("999");
			result.setMsg("您当前版本过旧，请升级到最新版本。若您当前使用微信借款，请清理缓存后再申请。");
			return result;
			// // 修改状态
			// BwOrder bo = bwOrderService.findBwOrderById(orderId);
			// bo.setStatusId(2l);
			// bo.setUpdateTime(new Date());
			// int oNum = bwOrderService.updateBwOrder(bo);
			// if (oNum > 0) {
			// result.setCode("000");
			// result.setMsg("图片附件上传成功");
			// result.setResult(map);
			// } else {
			// result.setCode("322");
			// result.setMsg("修改工单状态失败");
			// result.setResult(map);
			// }
			// // 将待审核的信息放入Redis中
			// BwBorrower borrower = new BwBorrower();
			// borrower.setId(bo.getBorrowerId());
			// borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			// SystemAuditDto systemAuditDto = new SystemAuditDto();
			// systemAuditDto.setOrderId(bo.getId());
			// systemAuditDto.setBorrowerId(bo.getBorrowerId());
			// systemAuditDto.setName(borrower.getName());
			// systemAuditDto.setPhone(borrower.getPhone());
			// systemAuditDto.setIdCard(borrower.getIdCard());
			// systemAuditDto.setCreateTime(new Date());
			// systemAuditDto.setIncludeAddressBook(1);// 包含通讯录
			// RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
			// JsonUtils.toJson(systemAuditDto));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.setCode("324");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		// result.setCode("000");
		// result.setMsg("成功");
		// return result;
	}

	/**
	 * 图片附件在线提交
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/saveOrUpdateAdjunctNewRong.do")
	public AppResponseResult saveOrUpdateAdjunctNewRong(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, Object> map = new HashMap<>();
			String hisgpId = request.getParameter("hisgpId");
			String hissfzzmId = request.getParameter("hissfzzmId");
			String hissfzfmId = request.getParameter("hissfzfmId");
			String hisczzId = request.getParameter("hisczzId ");
			String orderId = request.getParameter("orderId");
			String bId = request.getParameter("bId");
			// 图片上传地址
			String gpUrl = request.getParameter("gpUrl");
			String sfzzmUrl = request.getParameter("sfzzmUrl");
			String sfzfmUrl = request.getParameter("sfzfmUrl");
			String czzUrl = request.getParameter("czzUrl");
			// 是否从微信提交
			// String wechatFlag = request.getParameter("wechatFlag");
			if (CommUtils.isNull(bId)) {
				result.setCode("302");
				result.setMsg("借款人id为空");
				return result;
			}
			if (CommUtils.isNull(orderId)) {
				result.setCode("303");
				result.setMsg("当前工单id为空");
				return result;
			}
			if (CommUtils.isNull(gpUrl)) {
				result.setCode("304");
				result.setMsg("工牌图片url为空");
				return result;
			}
			if (CommUtils.isNull(sfzzmUrl)) {
				result.setCode("305");
				result.setMsg("身份证正面图片url为空");
				return result;
			}
			if (CommUtils.isNull(sfzfmUrl)) {
				result.setCode("306");
				result.setMsg("身份证反面图片url为空");
				return result;
			}
			if (CommUtils.isNull(czzUrl)) {
				result.setCode("307");
				result.setMsg("持证照url为空");
				return result;
			}
			logger.info("工单id为：" + orderId);
			// 持证照
			if (CommUtils.isNull(hisczzId)) {
				BwAdjunct czzbaj = new BwAdjunct();
				czzbaj.setOrderId(Long.parseLong(orderId));
				czzbaj.setAdjunctType(3);
				czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);
				if (CommUtils.isNull(czzbaj)) {
					// 添加
					czzbaj = new BwAdjunct();
					czzbaj.setAdjunctType(3);
					czzbaj.setAdjunctPath(czzUrl);
					czzbaj.setOrderId(Long.parseLong(orderId));
					czzbaj.setBorrowerId(Long.parseLong(bId));
					czzbaj.setCreateTime(new Date());
					Long czzId = bwAdjunctService.save(czzbaj);
					if (CommUtils.isNull(czzId)) {
						result.setCode("301");
						result.setMsg("持证照录入失败");
						return result;
					} else {
						map.put("czzId", czzId);
					}
				} else {
					czzbaj.setAdjunctPath(czzUrl);
					czzbaj.setUpdateTime(new Date());
					int czzNum = bwAdjunctService.update(czzbaj);
					if (czzNum == 0) {
						result.setCode("301");
						result.setMsg("持证照录入失败");
						return result;
					}
					map.put("czzId", czzbaj.getId());
				}

			} else {
				// 判断持证照附件是否和当前工单绑定
				BwAdjunct czzbaj = new BwAdjunct();
				czzbaj.setId(Long.parseLong(hisczzId));
				czzbaj.setOrderId(Long.parseLong(orderId));
				czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);
				if (CommUtils.isNull(czzbaj)) {
					// 添加
					czzbaj = new BwAdjunct();
					czzbaj.setAdjunctType(3);
					czzbaj.setAdjunctPath(czzUrl);
					czzbaj.setOrderId(Long.parseLong(orderId));
					czzbaj.setBorrowerId(Long.parseLong(bId));
					czzbaj.setCreateTime(new Date());
					Long czzId = bwAdjunctService.save(czzbaj);
					if (CommUtils.isNull(czzId)) {
						result.setCode("301");
						result.setMsg("持证照录入失败");
						return result;
					}
					map.put("czzId", czzId);
				} else {
					// 修改
					czzbaj.setAdjunctType(3);
					czzbaj.setAdjunctPath(czzUrl);
					czzbaj.setUpdateTime(new Date());
					int czzNum = bwAdjunctService.update(czzbaj);
					if (czzNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("czzId", hisczzId);
				}
			}
			// 工牌图片
			if (CommUtils.isNull(hisgpId)) {
				BwAdjunct bwAdjunct = new BwAdjunct();
				bwAdjunct.setOrderId(Long.parseLong(orderId));
				bwAdjunct.setAdjunctType(4);
				bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
				if (CommUtils.isNull(bwAdjunct)) {
					// 添加
					BwAdjunct gpbaj = new BwAdjunct();
					gpbaj.setAdjunctType(4);
					gpbaj.setAdjunctPath(gpUrl);
					gpbaj.setOrderId(Long.parseLong(orderId));
					gpbaj.setBorrowerId(Long.parseLong(bId));
					gpbaj.setCreateTime(new Date());
					Long gpId = bwAdjunctService.save(gpbaj);
					if (CommUtils.isNull(gpId)) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", gpId);
				} else {
					bwAdjunct.setAdjunctPath(gpUrl);
					bwAdjunct.setUpdateTime(new Date());
					int gpNum = bwAdjunctService.update(bwAdjunct);
					if (gpNum <= 0) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", bwAdjunct.getId());
				}

			} else {
				// 判断工牌图片附件是否和当前工单绑定
				BwAdjunct bwAdjunct = new BwAdjunct();
				bwAdjunct.setId(Long.parseLong(hisgpId));
				bwAdjunct.setOrderId(Long.parseLong(orderId));
				bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
				if (CommUtils.isNull(bwAdjunct)) {
					// 添加
					bwAdjunct = new BwAdjunct();
					bwAdjunct.setAdjunctType(4);
					bwAdjunct.setAdjunctPath(gpUrl);
					bwAdjunct.setOrderId(Long.parseLong(orderId));
					bwAdjunct.setBorrowerId(Long.parseLong(bId));
					bwAdjunct.setCreateTime(new Date());
					Long gpId = bwAdjunctService.save(bwAdjunct);
					if (CommUtils.isNull(gpId)) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", gpId);
				} else {
					// 修改
					bwAdjunct.setAdjunctType(4);
					bwAdjunct.setAdjunctPath(gpUrl);
					bwAdjunct.setUpdateTime(new Date());
					int gpNum = bwAdjunctService.update(bwAdjunct);
					if (gpNum <= 0) {
						result.setCode("301");
						result.setMsg("工牌图片录入失败");
						return result;
					}
					map.put("gpId", hisgpId);
				}
			}
			// 身份证正面
			if (CommUtils.isNull(hissfzzmId)) {
				BwAdjunct sfzzmbaj = new BwAdjunct();
				sfzzmbaj.setOrderId(Long.parseLong(orderId));
				sfzzmbaj.setAdjunctType(1);
				sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);
				if (CommUtils.isNull(sfzzmbaj)) {
					// 添加
					sfzzmbaj = new BwAdjunct();
					sfzzmbaj.setAdjunctType(1);
					sfzzmbaj.setAdjunctPath(sfzzmUrl);
					sfzzmbaj.setOrderId(Long.parseLong(orderId));
					sfzzmbaj.setBorrowerId(Long.parseLong(bId));
					sfzzmbaj.setCreateTime(new Date());
					Long sfzzmId = bwAdjunctService.save(sfzzmbaj);
					if (CommUtils.isNull(sfzzmId)) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					} else {
						map.put("sfzzmId", sfzzmId);
						// 修改借款人状态
						BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
						bb.setAuthStep(4);
						bb.setUpdateTime(new Date());
						// 将个人基本信息录入到borrower表中
						int bbNum = bwBorrowerService.updateBwBorrower(bb);
						logger.info("修改借款人状态结果：" + bbNum);
					}
				} else {
					sfzzmbaj.setAdjunctPath(sfzzmUrl);
					sfzzmbaj.setUpdateTime(new Date());
					int sfzzmNum = bwAdjunctService.update(sfzzmbaj);
					if (sfzzmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", sfzzmbaj.getId());
				}

			} else {
				// 判断身份证正面图片附件是否和当前工单绑定
				BwAdjunct zmbaj = new BwAdjunct();
				zmbaj.setId(Long.parseLong(hissfzzmId));
				zmbaj.setOrderId(Long.parseLong(orderId));
				zmbaj = bwAdjunctService.findBwAdjunctByAttr(zmbaj);
				if (CommUtils.isNull(zmbaj)) {
					// 添加
					zmbaj = new BwAdjunct();
					zmbaj.setAdjunctType(1);
					zmbaj.setAdjunctPath(sfzzmUrl);
					zmbaj.setOrderId(Long.parseLong(orderId));
					zmbaj.setBorrowerId(Long.parseLong(bId));
					zmbaj.setCreateTime(new Date());
					Long zmId = bwAdjunctService.save(zmbaj);
					if (CommUtils.isNull(zmId)) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", zmId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					bb.setUpdateTime(new Date());
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					zmbaj.setAdjunctType(1);
					zmbaj.setAdjunctPath(sfzzmUrl);
					zmbaj.setUpdateTime(new Date());
					int sfzzmNum = bwAdjunctService.update(zmbaj);
					if (sfzzmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", hissfzzmId);
				}
			}
			// 身份证反面
			if (CommUtils.isNull(hissfzfmId)) {
				BwAdjunct sfzfmbaj = new BwAdjunct();
				sfzfmbaj.setOrderId(Long.parseLong(orderId));
				sfzfmbaj.setAdjunctType(2);
				sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);
				if (CommUtils.isNull(sfzfmbaj)) {
					// 添加
					sfzfmbaj = new BwAdjunct();
					sfzfmbaj.setAdjunctType(2);
					sfzfmbaj.setAdjunctPath(sfzfmUrl);
					sfzfmbaj.setOrderId(Long.parseLong(orderId));
					sfzfmbaj.setBorrowerId(Long.parseLong(bId));
					sfzfmbaj.setCreateTime(new Date());
					Long sfzfmId = bwAdjunctService.save(sfzfmbaj);
					if (CommUtils.isNull(sfzfmId)) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					} else {
						map.put("sfzfmId", sfzfmId);
						// 修改借款人状态
						BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
						bb.setAuthStep(4);
						bb.setUpdateTime(new Date());
						// 将个人基本信息录入到borrower表中
						int bbNum = bwBorrowerService.updateBwBorrower(bb);
						logger.info("修改借款人状态结果：" + bbNum);
					}
				} else {
					sfzfmbaj.setAdjunctPath(sfzfmUrl);
					sfzfmbaj.setUpdateTime(new Date());
					int sfzfmNum = bwAdjunctService.update(sfzfmbaj);
					if (sfzfmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", sfzfmbaj.getId());
				}

			} else {
				// 判断身份证正面图片附件是否和当前工单绑定
				BwAdjunct fmbaj = new BwAdjunct();
				fmbaj.setId(Long.parseLong(hissfzfmId));
				fmbaj.setOrderId(Long.parseLong(orderId));
				fmbaj = bwAdjunctService.findBwAdjunctByAttr(fmbaj);
				if (CommUtils.isNull(fmbaj)) {
					// 添加
					fmbaj = new BwAdjunct();
					fmbaj.setAdjunctType(2);
					fmbaj.setAdjunctPath(sfzfmUrl);
					fmbaj.setOrderId(Long.parseLong(orderId));
					fmbaj.setBorrowerId(Long.parseLong(bId));
					fmbaj.setCreateTime(new Date());
					Long fmId = bwAdjunctService.save(fmbaj);
					if (CommUtils.isNull(fmId)) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", fmId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					bb.setUpdateTime(new Date());
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					fmbaj.setAdjunctType(2);
					fmbaj.setAdjunctPath(sfzfmUrl);
					fmbaj.setUpdateTime(new Date());
					int sfzfmNum = bwAdjunctService.update(fmbaj);
					if (sfzfmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", hissfzfmId);
				}
			}
			// 禁用接口
			result.setCode("999");
			result.setMsg("您当前版本过旧，请升级到最新版本。若您当前使用微信借款，请清理缓存后再申请。");
			return result;
			// // 修改状态
			// BwOrder bo = bwOrderService.findBwOrderById(orderId);
			// bo.setStatusId(2l);
			// bo.setUpdateTime(new Date());
			// int oNum = bwOrderService.updateBwOrder(bo);
			// if (oNum > 0) {
			// result.setCode("000");
			// result.setMsg("图片附件上传成功");
			// result.setResult(map);
			// } else {
			// result.setCode("322");
			// result.setMsg("修改工单状态失败");
			// result.setResult(map);
			// }
			// // 将待审核的信息放入Redis中
			// BwBorrower borrower = new BwBorrower();
			// borrower.setId(bo.getBorrowerId());
			// borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			// SystemAuditDto systemAuditDto = new SystemAuditDto();
			// systemAuditDto.setOrderId(bo.getId());
			// systemAuditDto.setBorrowerId(bo.getBorrowerId());
			// systemAuditDto.setName(borrower.getName());
			// systemAuditDto.setPhone(borrower.getPhone());
			// systemAuditDto.setIdCard(borrower.getIdCard());
			// systemAuditDto.setCreateTime(new Date());
			// if ("WX".equals(wechatFlag)) {
			// systemAuditDto.setIncludeAddressBook(0);// 不包含通讯录
			// } else {
			// systemAuditDto.setIncludeAddressBook(1);// 包含通讯录
			// }
			// RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
			// JsonUtils.toJson(systemAuditDto));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.setCode("324");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}
		// result.setCode("000");
		// result.setMsg("成功");
		// return result;
	}

	/**
	 * 图片附件在线提交（移除工牌）
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/saveOrUpdateAdjunctNewRongRemoveGongpai.do")
	public AppResponseResult saveOrUpdateAdjunctNewRongRemoveGongpai(HttpServletRequest request,
			HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			// Date now = new Date();
			Map<String, Object> map = new HashMap<>();
			// String hisgpId = request.getParameter("hisgpId");
			String hissfzzmId = request.getParameter("hissfzzmId");
			String hissfzfmId = request.getParameter("hissfzfmId");
			String hisczzId = request.getParameter("hisczzId ");
			String orderId = request.getParameter("orderId");
			String bId = request.getParameter("bId");
			// 图片上传地址
			// String gpUrl = request.getParameter("gpUrl");
			String sfzzmUrl = request.getParameter("sfzzmUrl");
			String sfzfmUrl = request.getParameter("sfzfmUrl");
			String czzUrl = request.getParameter("czzUrl");
			// 是否从微信提交
			// String wechatFlag = request.getParameter("wechatFlag");
			if (CommUtils.isNull(bId)) {
				result.setCode("302");
				result.setMsg("借款人id为空");
				return result;
			}
			if (CommUtils.isNull(orderId)) {
				result.setCode("303");
				result.setMsg("当前工单id为空");
				return result;
			}
			/*
			 * if (CommUtils.isNull(gpUrl)) { result.setCode("304"); result.setMsg("工牌图片url为空"); return result; }
			 */
			if (CommUtils.isNull(sfzzmUrl)) {
				result.setCode("305");
				result.setMsg("身份证正面图片url为空");
				return result;
			}
			if (CommUtils.isNull(sfzfmUrl)) {
				result.setCode("306");
				result.setMsg("身份证反面图片url为空");
				return result;
			}
			if (CommUtils.isNull(czzUrl)) {
				result.setCode("307");
				result.setMsg("持证照url为空");
				return result;
			}
			logger.info("工单id为：" + orderId);
			// 持证照
			if (CommUtils.isNull(hisczzId)) {
				BwAdjunct czzbaj = new BwAdjunct();
				czzbaj.setOrderId(Long.parseLong(orderId));
				czzbaj.setAdjunctType(3);
				czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);
				if (CommUtils.isNull(czzbaj)) {
					// 添加
					czzbaj = new BwAdjunct();
					czzbaj.setAdjunctType(3);
					czzbaj.setAdjunctPath(czzUrl);
					czzbaj.setOrderId(Long.parseLong(orderId));
					czzbaj.setBorrowerId(Long.parseLong(bId));
					czzbaj.setCreateTime(new Date());
					Long czzId = bwAdjunctService.save(czzbaj);
					if (CommUtils.isNull(czzId)) {
						result.setCode("301");
						result.setMsg("持证照录入失败");
						return result;
					} else {
						map.put("czzId", czzId);
					}
				} else {
					czzbaj.setAdjunctPath(czzUrl);
					czzbaj.setUpdateTime(new Date());
					int czzNum = bwAdjunctService.update(czzbaj);
					if (czzNum == 0) {
						result.setCode("301");
						result.setMsg("持证照录入失败");
						return result;
					}
					map.put("czzId", czzbaj.getId());
				}

			} else {
				// 判断持证照附件是否和当前工单绑定
				BwAdjunct czzbaj = new BwAdjunct();
				czzbaj.setId(Long.parseLong(hisczzId));
				czzbaj.setOrderId(Long.parseLong(orderId));
				czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);
				if (CommUtils.isNull(czzbaj)) {
					// 添加
					czzbaj = new BwAdjunct();
					czzbaj.setAdjunctType(3);
					czzbaj.setAdjunctPath(czzUrl);
					czzbaj.setOrderId(Long.parseLong(orderId));
					czzbaj.setBorrowerId(Long.parseLong(bId));
					czzbaj.setCreateTime(new Date());
					Long czzId = bwAdjunctService.save(czzbaj);
					if (CommUtils.isNull(czzId)) {
						result.setCode("301");
						result.setMsg("持证照录入失败");
						return result;
					}
					map.put("czzId", czzId);
				} else {
					// 修改
					czzbaj.setAdjunctType(3);
					czzbaj.setAdjunctPath(czzUrl);
					czzbaj.setUpdateTime(new Date());
					int czzNum = bwAdjunctService.update(czzbaj);
					if (czzNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("czzId", hisczzId);
				}
			}
			// 身份证正面
			if (CommUtils.isNull(hissfzzmId)) {
				BwAdjunct sfzzmbaj = new BwAdjunct();
				sfzzmbaj.setOrderId(Long.parseLong(orderId));
				sfzzmbaj.setAdjunctType(1);
				sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);
				if (CommUtils.isNull(sfzzmbaj)) {
					// 添加
					sfzzmbaj = new BwAdjunct();
					sfzzmbaj.setAdjunctType(1);
					sfzzmbaj.setAdjunctPath(sfzzmUrl);
					sfzzmbaj.setOrderId(Long.parseLong(orderId));
					sfzzmbaj.setBorrowerId(Long.parseLong(bId));
					sfzzmbaj.setCreateTime(new Date());
					Long sfzzmId = bwAdjunctService.save(sfzzmbaj);
					if (CommUtils.isNull(sfzzmId)) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					} else {
						map.put("sfzzmId", sfzzmId);
						// 修改借款人状态
						BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
						bb.setAuthStep(4);
						bb.setUpdateTime(new Date());
						// 将个人基本信息录入到borrower表中
						int bbNum = bwBorrowerService.updateBwBorrower(bb);
						logger.info("修改借款人状态结果：" + bbNum);
					}
				} else {
					sfzzmbaj.setAdjunctPath(sfzzmUrl);
					sfzzmbaj.setUpdateTime(new Date());
					int sfzzmNum = bwAdjunctService.update(sfzzmbaj);
					if (sfzzmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", sfzzmbaj.getId());
				}

			} else {
				// 判断身份证正面图片附件是否和当前工单绑定
				BwAdjunct zmbaj = new BwAdjunct();
				zmbaj.setId(Long.parseLong(hissfzzmId));
				zmbaj.setOrderId(Long.parseLong(orderId));
				zmbaj = bwAdjunctService.findBwAdjunctByAttr(zmbaj);
				if (CommUtils.isNull(zmbaj)) {
					// 添加
					zmbaj = new BwAdjunct();
					zmbaj.setAdjunctType(1);
					zmbaj.setAdjunctPath(sfzzmUrl);
					zmbaj.setOrderId(Long.parseLong(orderId));
					zmbaj.setBorrowerId(Long.parseLong(bId));
					zmbaj.setCreateTime(new Date());
					Long zmId = bwAdjunctService.save(zmbaj);
					if (CommUtils.isNull(zmId)) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", zmId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					bb.setUpdateTime(new Date());
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					zmbaj.setAdjunctType(1);
					zmbaj.setAdjunctPath(sfzzmUrl);
					zmbaj.setUpdateTime(new Date());
					int sfzzmNum = bwAdjunctService.update(zmbaj);
					if (sfzzmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证正面图片录入失败");
						return result;
					}
					map.put("sfzzmId", hissfzzmId);
				}
			}
			// 身份证反面
			if (CommUtils.isNull(hissfzfmId)) {
				BwAdjunct sfzfmbaj = new BwAdjunct();
				sfzfmbaj.setOrderId(Long.parseLong(orderId));
				sfzfmbaj.setAdjunctType(2);
				sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);
				if (CommUtils.isNull(sfzfmbaj)) {
					// 添加
					sfzfmbaj = new BwAdjunct();
					sfzfmbaj.setAdjunctType(2);
					sfzfmbaj.setAdjunctPath(sfzfmUrl);
					sfzfmbaj.setOrderId(Long.parseLong(orderId));
					sfzfmbaj.setBorrowerId(Long.parseLong(bId));
					sfzfmbaj.setCreateTime(new Date());
					Long sfzfmId = bwAdjunctService.save(sfzfmbaj);
					if (CommUtils.isNull(sfzfmId)) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					} else {
						map.put("sfzfmId", sfzfmId);
						// 修改借款人状态
						BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
						bb.setAuthStep(4);
						bb.setUpdateTime(new Date());
						// 将个人基本信息录入到borrower表中
						int bbNum = bwBorrowerService.updateBwBorrower(bb);
						logger.info("修改借款人状态结果：" + bbNum);
					}
				} else {
					sfzfmbaj.setAdjunctPath(sfzfmUrl);
					sfzfmbaj.setUpdateTime(new Date());
					int sfzfmNum = bwAdjunctService.update(sfzfmbaj);
					if (sfzfmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", sfzfmbaj.getId());
				}

			} else {
				// 判断身份证正面图片附件是否和当前工单绑定
				BwAdjunct fmbaj = new BwAdjunct();
				fmbaj.setId(Long.parseLong(hissfzfmId));
				fmbaj.setOrderId(Long.parseLong(orderId));
				fmbaj = bwAdjunctService.findBwAdjunctByAttr(fmbaj);
				if (CommUtils.isNull(fmbaj)) {
					// 添加
					fmbaj = new BwAdjunct();
					fmbaj.setAdjunctType(2);
					fmbaj.setAdjunctPath(sfzfmUrl);
					fmbaj.setOrderId(Long.parseLong(orderId));
					fmbaj.setBorrowerId(Long.parseLong(bId));
					fmbaj.setCreateTime(new Date());
					Long fmId = bwAdjunctService.save(fmbaj);
					if (CommUtils.isNull(fmId)) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", fmId);
					// 修改借款人状态
					BwBorrower bb = bwBorrowerService.findBwBorrowerById(Long.parseLong(bId));
					bb.setAuthStep(4);
					bb.setUpdateTime(new Date());
					// 将个人基本信息录入到borrower表中
					int bbNum = bwBorrowerService.updateBwBorrower(bb);
					logger.info("修改借款人状态结果：" + bbNum);
				} else {
					// 修改
					fmbaj.setAdjunctType(2);
					fmbaj.setAdjunctPath(sfzfmUrl);
					fmbaj.setUpdateTime(new Date());
					int sfzfmNum = bwAdjunctService.update(fmbaj);
					if (sfzfmNum == 0) {
						result.setCode("301");
						result.setMsg("身份证反面图片录入失败");
						return result;
					}
					map.put("sfzfmId", hissfzfmId);
				}
			}
			// 禁用接口
			result.setCode("999");
			result.setMsg("您当前版本过旧，请升级到最新版本。若您当前使用微信借款，请清理缓存后再申请。");
			return result;
			// // 修改状态
			// BwOrder bo = bwOrderService.findBwOrderById(orderId);
			// bo.setStatusId(2l);
			// bo.setUpdateTime(new Date());
			// int oNum = bwOrderService.updateBwOrder(bo);
			// if (oNum > 0) {
			// result.setCode("000");
			// result.setMsg("图片附件上传成功");
			// result.setResult(map);
			// } else {
			// result.setCode("322");
			// result.setMsg("修改工单状态失败");
			// result.setResult(map);
			// }
			// // 将待审核的信息放入Redis中
			// BwBorrower borrower = new BwBorrower();
			// borrower.setId(bo.getBorrowerId());
			// borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			// SystemAuditDto systemAuditDto = new SystemAuditDto();
			// systemAuditDto.setOrderId(bo.getId());
			// systemAuditDto.setBorrowerId(bo.getBorrowerId());
			// systemAuditDto.setName(borrower.getName());
			// systemAuditDto.setPhone(borrower.getPhone());
			// systemAuditDto.setIdCard(borrower.getIdCard());
			// systemAuditDto.setCreateTime(now);
			// if ("WX".equals(wechatFlag)) {
			// systemAuditDto.setIncludeAddressBook(0);// 不包含通讯录
			// } else {
			// systemAuditDto.setIncludeAddressBook(1);// 包含通讯录
			// }
			// RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
			// JsonUtils.toJson(systemAuditDto));

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			result.setCode("327");
			result.setMsg("系统异常，请稍后再试");
			return result;
		}

		// result.setCode("000");
		// result.setMsg("成功");
		// return result;
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
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("301");
			result.setMsg("工单id为空");
			return result;
		}
		if (CommUtils.isNull(bwId)) {
			result.setCode("302");
			result.setMsg("借款人id为空");
			return result;
		}
		// 判断当前工单是否有对应个人信息
		BwPersonInfo personInfo = bwPersonInfoService.findBwPersonInfoByOrderId(Long.parseLong(orderId));
		if (personInfo != null) {
			logger.info("根据当前工单" + orderId + "查找到个人信息。");
			result.setCode("000");
			result.setMsg("查询个人信息成功");
			result.setResult(personInfo);
		} else {
			logger.info("当前工单" + orderId + "未查找到个人信息，根据当前借款人" + bwId + "查找历史工单个人信息。");
			// 查找当前借款人最近一期非当前工单对应的个人信息
			Example example = new Example(BwOrder.class);
			example.createCriteria().andEqualTo("borrowerId", Long.parseLong(bwId))
					.andNotEqualTo("id", Long.parseLong(orderId))
					.andEqualTo("productType", OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//			example.setOrderByClause("createTime desc");
			example.orderBy("createTime").desc();
			List<BwOrder> list = bwOrderService.findBwOrderByExample(example);
			if (list != null && list.size() > 0) {
				logger.info("根据当前借款人" + bwId + "查找到历史工单" + list.get(0).getId());
				BwPersonInfo info = bwPersonInfoService.findBwPersonInfoByOrderId(list.get(0).getId());
				if (info != null) {
					logger.info("根据当前借款人历史工单" + list.get(0).getId() + "查找到个人信息。");
					result.setCode("000");
					result.setMsg("查询个人信息成功");
					result.setResult(info);
				} else {
					logger.info("当前借款人历史工单" + list.get(0).getId() + "没有关联个人信息。");
					result.setCode("303");
					result.setMsg("没有找到个人信息");
					return result;
				}
			} else {
				// 没有工单
				logger.info("当前借款人" + bwId + "没有历史工单。");
				result.setCode("303");
				result.setMsg("没有找到个人信息");
				return result;
			}

		}
		return result;
	}

	/**
	 * 查询工作信息
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findWorkInfo.do")
	public AppResponseResult findWorkInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("301");
			result.setMsg("工单id为空");
			return result;
		}
		if (CommUtils.isNull(bwId)) {
			result.setCode("302");
			result.setMsg("借款人id为空");
			return result;
		}
		BwWorkInfo bwWorkInfo = null; // 单位信息
		BwBankCard bwBankCard = null; // 银行卡
		BwInsureAccout bwInsureAccout = null; // 社保账户
		// 判断当前工单是否有对应单位信息
		BwWorkInfo workInfo = new BwWorkInfo();
		workInfo.setOrderId(Long.parseLong(orderId));
		workInfo = bwWorkInfoService.findBwWorkInfoByAttr(workInfo);
		if (workInfo != null) {
			logger.info("根据当前工单" + orderId + "查找到单位信息。");
			bwWorkInfo = workInfo;
		} else {
			logger.info("当前工单" + orderId + "未查找到单位信息，根据当前借款人" + bwId + "查找历史工单单位信息。");
			// 查找当前借款人最近一期非当前工单对应的单位信息
			Example example = new Example(BwOrder.class);
			example.createCriteria().andEqualTo("borrowerId", Long.parseLong(bwId))
					.andEqualTo("productType", OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE)
					.andNotEqualTo(" id", Long.parseLong(orderId));
//			example.setOrderByClause("createTime desc");
			example.orderBy("createTime").desc();
			List<BwOrder> list = bwOrderService.findBwOrderByExample(example);
			if (list != null && list.size() > 0) {
				logger.info("根据当前借款人" + bwId + "查找到历史工单" + list.get(0).getId());
				workInfo = new BwWorkInfo();
				workInfo.setOrderId(list.get(0).getId());
				bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(workInfo);
				if (bwWorkInfo != null) {
					logger.info("根据当前借款人历史工单" + list.get(0).getId() + "查找到单位信息。");
				} else {
					logger.info("当前借款人历史工单" + list.get(0).getId() + "没有关联单位信息。");
				}
			} else {
				logger.info("当前借款人" + bwId + "没有历史工单。");
			}
		}

		// 判断当前借款人是否有对应银行卡信息
		Example bankCardExample = new Example(BwBankCard.class);
		bankCardExample.createCriteria().andEqualTo("borrowerId", Long.parseLong(bwId));
//		bankCardExample.setOrderByClause("createTime desc");
		bankCardExample.orderBy("createTime").desc();
		List<BwBankCard> bankCardList = bwBankCardService.findBwBankCardByExample(bankCardExample);
		if (bankCardList != null && bankCardList.size() > 0) {
			logger.info("根据当前借款人" + bwId + "查找到银行卡信息。");
			bwBankCard = bankCardList.get(0);
		} else {
			logger.info("当前借款人" + bwId + "没有关联银行卡信息。");
		}

		// 判断当前工单是否有对应社保信息
		BwInsureAccout insureAccout = new BwInsureAccout();
		insureAccout.setOrderId(Long.parseLong(orderId));
		insureAccout = bwInsureAccoutService.findBwInsureAccoutByAttr(insureAccout);
		if (insureAccout != null) {
			logger.info("根据当前工单" + orderId + "查找到社保信息。");
			bwInsureAccout = insureAccout;
		} else {
			logger.info("当前工单" + orderId + "未查找到社保信息，根据当前借款人" + bwId + "查找历史工单社保信息。");
			// 查找当前借款人最近一期非当前工单对应的社保信息
			Example example = new Example(BwOrder.class);
			example.createCriteria().andEqualTo("borrowerId", Long.parseLong(bwId)).andNotEqualTo("id",
					Long.parseLong(orderId));
//			example.setOrderByClause("createTime desc");
			example.orderBy("createTime").desc();
			List<BwOrder> list = bwOrderService.findBwOrderByExample(example);
			if (list != null && list.size() > 0) {
				logger.info("根据当前借款人" + bwId + "查找到历史工单" + list.get(0).getId());
				insureAccout = new BwInsureAccout();
				insureAccout.setOrderId(list.get(0).getId());
				bwInsureAccout = bwInsureAccoutService.findBwInsureAccoutByAttr(insureAccout);
				if (bwInsureAccout != null) {
					logger.info("根据当前借款人历史工单" + list.get(0).getId() + "查找到社保信息。");
				} else {
					logger.info("当前借款人历史工单" + list.get(0).getId() + "没有关联社保信息。");
				}
			} else {
				logger.info("当前借款人" + bwId + "没有历史工单。");
			}
		}
		result.setCode("000");
		result.setMsg("查询工作信息成功");
		Map<String, Object> map = new HashMap<>();
		map.put("bwi", bwWorkInfo);
		map.put("bbc", bwBankCard);
		map.put("bia", bwInsureAccout);
		result.setResult(map);
		return result;
	}

	/**
	 * 查询图片信息
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findAdjunct.do")
	public AppResponseResult findAdjunct(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		String bwId = request.getParameter("bwId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("301");
			result.setMsg("工单id为空");
			return result;
		}
		if (CommUtils.isNull(bwId)) {
			result.setCode("302");
			result.setMsg("借款人id为空");
			return result;
		}
		List<BwAdjunct> list = null;
		// 判断当前工单是否有对应附件信息
		list = bwAdjunctService.findBwAdjunctPhotoByOrderId(Long.parseLong(orderId));
		if (list != null && list.size() > 0) {
			logger.info("根据当前工单" + orderId + "查找到图片附件信息。");
			result.setCode("000");
			result.setMsg("查询附件信息成功");
			result.setResult(list);
		} else {
			logger.info("当前工单" + orderId + "未查找到图片附件信息，根据当前借款人" + bwId + "查找历史工单图片附件信息");
			// 判断当前借款人是否有最近一期非当前工单
			Example orderExample = new Example(BwOrder.class);
			orderExample.createCriteria().andEqualTo("borrowerId", Long.parseLong(bwId))
					.andNotEqualTo("id", Long.parseLong(orderId))
					.andEqualTo("productType", OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//			orderExample.setOrderByClause("createTime desc");
			orderExample.orderBy("createTime").desc();
			List<BwOrder> orderList = bwOrderService.findBwOrderByExample(orderExample);
			if (orderList != null && orderList.size() > 0) {
				logger.info("根据当前借款人" + bwId + "查找到历史工单" + orderList.get(0).getId());
				list = bwAdjunctService.findBwAdjunctPhotoByOrderId(orderList.get(0).getId());
				if (list != null && list.size() > 0) {
					logger.info("根据当前借款人历史工单" + orderList.get(0).getId() + "查找到图片附件信息。");
					result.setCode("000");
					result.setMsg("查询附件信息成功");
					result.setResult(list);
				} else {
					logger.info("当前借款人历史工单" + orderList.get(0).getId() + "没有关联图片附件信息。");
					result.setCode("303");
					result.setMsg("没有对应附件信息");
				}
			} else {
				logger.info("当前借款人" + bwId + "没有历史工单。");
				result.setCode("303");
				result.setMsg("没有对应附件信息");
			}
		}
		return result;
	}

	/**
	 * 查询合同
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findPdf.do")
	public AppResponseResult findPdf(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("1021");
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
	 * 查询合同(新)
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/findPdfNew.do")
	public AppResponseResult findPdfNew(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("302");
			result.setMsg("工单id为空");
			return result;
		}
		logger.info("工单id：" + orderId);
		// BwAdjunct bwAdjunct = new BwAdjunct();
		// bwAdjunct.setOrderId(Long.parseLong(orderId));
		// bwAdjunct.setAdjunctType(13); //合同
		// bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
		List<BwAdjunct> bwAdjuncts = bwAdjunctService.findBwAdjunctByOrderIdNew(Long.parseLong(orderId));
		HashMap<String, Object> map = new HashMap<>();
		if (bwAdjuncts.size() > 0) {
			result.setCode("000");
			result.setMsg("成功");
			for (BwAdjunct bwAdjunct2 : bwAdjuncts) {
				logger.info("查询到的合同地址是：" + SystemConstant.PDF_URL + bwAdjunct2.getAdjunctPath());
				if (bwAdjunct2.getAdjunctType().equals(11)) {
					// 借款协议
					map.put("jk", SystemConstant.PDF_URL + bwAdjunct2.getAdjunctPath());
				} else {
					// 小微金融信息咨询及信用管理服务合同 （13）
					map.put("xw", SystemConstant.PDF_URL + bwAdjunct2.getAdjunctPath());
				}
			}
			result.setResult(map);
			return result;
		} else {
			result.setCode("301");
			result.setMsg("未找到合同");
			return result;
		}
	}

	/**
	 * 上传图片
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/uploadPic.do")
	public AppResponseResult uploadPic(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String base64 = request.getParameter("base64");
		if (CommUtils.isNull(base64)) {
			result.setCode("301");
			result.setMsg("base64为空");
			return result;
		}
		try {
			base64 = base64.substring(base64.lastIndexOf(",") + 1);
			byte[] cache = Base64Utils.decode(base64);
			InputStream in = new ByteArrayInputStream(cache);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String uploadPath = "upload/backend/" + sdf.format(new Date()) + "/" + sdf2.format(new Date())
					+ CommUtils.getRandomNumber(3) + ".jpg";
			logger.info("上传文件路径：" + uploadPath);
			CdnUploadTools.uploadPic(in, uploadPath);
			in.close();
			result.setCode("000");
			result.setMsg("图片上传成功");
			result.setResult(uploadPath);
			return result;
		} catch (Exception e) {
			logger.error("上传图片出错，传入参数[base64=" + base64 + "]，异常信息：" + e.getMessage());
			e.printStackTrace();
			result.setCode("500");
			result.setMsg("系统错误");
			return result;
		}
	}

}
