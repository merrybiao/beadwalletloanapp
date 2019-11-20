package com.waterelephant.service.impl;

import com.alibaba.fastjson.JSON;
import com.beadwallet.entity.baofoo.BindCardRequest;
import com.beadwallet.entity.baofoo.ProtocolBindCardResult;
import com.beadwallet.servcie.BaoFooService;
import com.waterelephant.constants.BaofuConstant;
import com.waterelephant.constants.ParameterConstant;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.PaySignDto;
import com.waterelephant.entity.*;
import com.waterelephant.mapper.BwBankCardChangeMapper;
import com.waterelephant.mapper.BwOrderMapper;
import com.waterelephant.service.*;
import com.waterelephant.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BwBankCardServiceImpl extends BaseService<BwBankCard, Long> implements IBwBankCardService {
	private Logger logger = Logger.getLogger(BwBankCardServiceImpl.class);
	@Autowired
	private BwBorrowerService bwBorrowerService;
	@Autowired
	private BwOrderMapper bwOrderMapper;
	@Autowired
	private ExtraConfigService extraConfigService;
	@Autowired
	private BwBankCardChangeService bwBankCardChangeService;
	@Autowired
	private BwBankCardChangeMapper bwBankCardChangeMapper;
	@Autowired
	private BwBankCardBindInfoService bwBankCardBindInfoService;

	@Override
	public BwBankCard findBwBankCardByAttr(BwBankCard bwBankCard) {

		return mapper.selectOne(bwBankCard);
	}

	@Override
	public int saveBwBankCard(BwBankCard bwBankCard, Long bId) {
		int num = mapper.insert(bwBankCard);
		if (num > 0) {
			BwBorrower borrower = bwBorrowerService.findBwBorrowerById(bId);
			if (CommUtils.isNull(borrower)) {
				borrower.setState(3);
				num = bwBorrowerService.updateBwBorrower(borrower);
			}
		}
		return num;
	}

	@Override
	public BwBankCard findBwBankCardByBorrowerId(Long borrowerId) {
		BwBankCard bbc = new BwBankCard();
		bbc.setBorrowerId(borrowerId);
		return mapper.selectOne(bbc);
	}

	@Override
	public int update(BwBankCard bwBankCard) {
		return mapper.updateByPrimaryKey(bwBankCard);
	}

	@Override
	public BwBankCard findBwBankCardById(Long id) {
		BwBankCard bbc = new BwBankCard();
		bbc.setId(id);
		return mapper.selectOne(bbc);
	}

	@Override
	public List<BwBankCard> findBwBankCardByExample(Example example) {
		return mapper.selectByExample(example);
	}

	@Override
	public int addBwBankCard(BwBankCard bwBankCard) {
		return mapper.insert(bwBankCard);
	}

	@Override
	public BwBankCard findBwBankCardByCityNameAndPName(String provinceName, String cityName) {
		String sql = "select p.`code` as province_code,c.`code` as city_code from fuiou_bank_province p LEFT JOIN fuiou_bank_city c ON p.`code`=c.pcode " + "where p.province_name='" + provinceName
				+ "' and c.city_name='" + cityName + "'";
		return sqlMapper.selectOne(sql, BwBankCard.class);
	}

	@Override
	public Map<String, Object> query(String userId) {
		String sql = "select b.bank_name,c.city_name cityName ,b.city_code, b.bank_code ,b.card_no ,b.borrower_id ,c.pcode "
				+ " from bw_bank_card b LEFT JOIN fuiou_bank_city c ON c.code=b.city_code  where b.borrower_id='" + userId + "'";
		return sqlMapper.selectOne(sql);
	}

	@Override
	public Map<String, Object> toSigned(String orderId) {
		String sql = "select b.card_no ,b.bank_name,c.city_name cityName ,b.city_code, b.bank_code  , (SELECT name from bw_borrower where id=b.borrower_id  )name ,(SELECT fuiou_acct from bw_borrower where id=b.borrower_id  ) fuiou ,(SELECT phone from bw_borrower  where  id=b.borrower_id ) phone from bw_bank_card b LEFT JOIN fuiou_bank_city c ON c.code=b.city_code  where b.borrower_id=(select borrower_id from bw_order where id='"
				+ orderId + "')";
		return sqlMapper.selectOne(sql);
	}

	@Override
	public int updateBwBankCard(BwBankCard bwBankCard) {
		return mapper.updateByPrimaryKey(bwBankCard);
	}

	@Override
	public BwBankCard findBwBankCardByBoorwerId(Long boorwerId) {
		String sql = "select * from bw_bank_card b where b.borrower_id = " + boorwerId + "";
		return sqlMapper.selectOne(sql, BwBankCard.class);
	}

	@Override
	public void updateSign(String login_id) {
		String sql = "update bw_bank_card set sign_status='1' where borrower_id=(select id from bw_borrower where  phone ='" + login_id + "') ";
		sqlMapper.update(sql);
	}

	@Override
	public Integer findSignStatusByBorrowerId(Long borrowerId) {
		String sql = "select b.sign_status from bw_bank_card b where b.borrower_id=#{borrowerId} LIMIT 1";
		return sqlMapper.selectOne(sql, borrowerId, Integer.class);
	}

	@Override
	public int updateSignStatusByBorrowerId(Long borrowerId) {
		String sql = "UPDATE bw_bank_card set sign_status=2 where borrower_id=#{borrowerId}";
		return sqlMapper.update(sql, borrowerId);
	}

	@Override
	public void saveOrUpdBorrowerAndBankCard(BwBorrower bw, String bankCode, String bankName, String cardNo) {
		logger.info("保存到数据库=银行卡编码======" + bankCode);
		logger.info("保存到数据库=银行名字======" + bankName);
		logger.info("保存到数据库=银行卡号=====" + cardNo);

		int upd = bwBorrowerService.updateBwBorrower(bw);// 修改用户信息
		logger.info("修改用户记录条数=======" + upd);
		BwBankCard bbc = new BwBankCard();
		bbc.setBorrowerId(bw.getId());
		BwBankCard bbc1 = mapper.selectOne(bbc);
		if (bbc1 != null) {
			bbc1.setCardNo(cardNo);
			bbc1.setUpdateTime(new Date());
			bbc1.setBankName(bankName);
			bbc1.setBankCode(bankCode);
			int interBank = mapper.updateByPrimaryKey(bbc1);
			logger.info("修改银行卡条数=======" + interBank);
		} else {

			bbc.setId(null);
			bbc.setCardNo(cardNo);
			bbc.setBankName(bankName);
			bbc.setBankCode(bankCode);
			bbc.setCreateTime(new Date());
			bbc.setUpdateTime(new Date());
			bbc.setSignStatus(0);
			int interBank = mapper.insert(bbc);
			logger.info("添加银行卡条数=======" + interBank);
		}

	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwBankCardService#deleteBankCard(java.lang.Long)
	 */
	@Override
	public void deleteBankCard(Long borrowerId) {
		BwBankCard bankCard = findBwBankCardByBoorwerId(borrowerId);
		if (null != bankCard && !StringUtil.isEmpty(bankCard.getId())) {
			mapper.delete(bankCard);
		}

	}

	@Override
	public BwBankCard selectByBorrowerId(Long borrowerId) {
		if (borrowerId == null || borrowerId <= 0L) {
			return null;
		}
		BwBankCard queryBankCard = new BwBankCard();
		queryBankCard.setBorrowerId(borrowerId);
		List<BwBankCard> list = select(queryBankCard);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 是否重新绑卡
	 *
	 * @param bwBankCard
	 * @return
	 */
	@Override
	public boolean isBindCardAgain(BwBankCard bwBankCard) {
		boolean hasSign = false;
		if (bwBankCard != null && bwBankCard.getSignStatus() != null && bwBankCard.getSignStatus() >= 1) {// 已绑卡，宝付、连连、易宝
			hasSign = true;
		}
		return hasSign;
	}

	@Override
	@Deprecated
	public boolean hasUseIdCard(String idCard, String phone) {
		List<BwBorrower> borrowerList = bwBorrowerService.selectSignListByIdCard(idCard);
		boolean check = false;
		if (!CommUtils.isNull(borrowerList)) {
			for (BwBorrower cBorrower : borrowerList) {
				if (phone != null && !phone.equals(cBorrower.getPhone())) {
					check = true;
				}
			}
		}
		return check;
	}

	@Override
	public boolean hasUseIdCard(String idCard, String phone, Integer appId) {
		if (appId == null || appId == 0) {
			appId = 1;
		}
		List<BwBorrower> borrowerList = bwBorrowerService.selectSignListByIdCard(idCard);
		boolean check = false;
		if (!CommUtils.isNull(borrowerList)) {
			for (BwBorrower cBorrower : borrowerList) {
				Integer queryAppId = cBorrower.getAppId();
				if (queryAppId == null || queryAppId == 0) {
					queryAppId = 1;
				}
				if (phone != null && !phone.equals(cBorrower.getPhone()) && appId.equals(queryAppId)) {
					check = true;
					break;
				}
			}
		}
		return check;
	}

	/**
	 * 根据身份证计算年龄
	 *
	 * @param idCard
	 * @return
	 */
	@Override
	public int clacAgeByIdCard(String idCard) {
		if (StringUtils.isEmpty(idCard) || idCard.trim().length() < 10) {
			return 0;
		}
		int bronDate = Integer.parseInt(idCard.trim().substring(6, 10));
		int nowYear = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyy"));
		return nowYear - bronDate;
	}

	@Override
	public AppResponseResult canBindCard(PaySignDto paySignDto) {
		Long borrowerId = paySignDto.getBorrowerId();
		String name = paySignDto.getName();// 真实姓名
		String phone = paySignDto.getPhone();
		String idCard = paySignDto.getIdCard();// 身份证
		String cardNo = paySignDto.getCardNo();// 银行卡号
		Boolean sameCardNoValidate = paySignDto.getSameCardNoValidate();
		AppResponseResult result = new AppResponseResult();
		result.setResult(paySignDto);
		if (!CommUtils.isNull(phone) && !phone.matches("1[1-9]\\d{9}")) {
			result.setCode("157");
			result.setMsg("手机号格式错误");
			return result;
		}
		if (CommUtils.isNull(borrowerId)) {
			result.setCode("408");
			result.setMsg("借款人id为空");
			return result;
		}
		BwBorrower queryBorrower = bwBorrowerService.selectByPrimaryKey(borrowerId);
		if (queryBorrower == null) {
			result.setCode("410");
			result.setMsg("借款人不存在");
			return result;
		}
		String queryIdCard = queryBorrower.getIdCard();
		String queryName = queryBorrower.getName();
		BwBankCard queryBankCard = selectByBorrowerId(borrowerId);
		boolean isRebind = isBindCardAgain(queryBankCard);// 是否重新绑卡
		if (!isRebind && (StringUtils.isEmpty(idCard) || !ValidateUtil.checkIdentityCard(idCard))) {// 重新绑卡不校验身份证，直接用数据库已保存
			result.setCode("113");
			result.setMsg("身份证不合法");
			return result;
		}
		if (StringUtils.isEmpty(cardNo) || !ValidateUtil.checkBankCard(cardNo)) {
			result.setCode("114");
			result.setMsg("银行卡不合法");
			return result;
		}
		if (!isRebind && (StringUtils.isEmpty(name) || !ValidateUtil.checkTrueName(name))) {// 重新绑卡不校验姓名，直接用数据库已保存
			result.setCode("115");
			result.setMsg("姓名不合法");
			return result;
		}

		// 判断重新绑卡卡号是否一样
//		if (sameCardNoValidate != null && sameCardNoValidate && isRebind && cardNo.equals(queryBankCard.getCardNo())) {
//			result.setCode("116");
//			result.setMsg("请绑定不同的储蓄卡号");
//			return result;
//		}
		if (isRebind) {// 重新绑卡
			idCard = queryIdCard;
			name = queryName;
		} else {
			// 验证身份证是否被使用
			boolean hasUseIdCard = hasUseIdCard(idCard, queryBorrower.getPhone(), queryBorrower.getAppId());
			if (hasUseIdCard) {
				result.setCode("117");
				result.setMsg("该身份证号已绑定");
				return result;
			}
		}
		String bankNameUtil = BankInfoUtils.getNameOfBank(cardNo.substring(0, 6));
		logger.info("【BwBankCardService.canBindCard】borrowerId:" + borrowerId + ",工具类获取银行卡名称:" + bankNameUtil);
		String bankCode = paySignDto.getBankCode();
		if (StringUtils.isEmpty(bankNameUtil)) {
			if (StringUtils.isNotEmpty(paySignDto.getBankCode())) {
				bankNameUtil = RedisUtils.hget(RedisKeyConstant.FUIOU_BANK, bankCode);
			}
			if (StringUtils.isEmpty(bankNameUtil)) {
				result.setCode("118");
				result.setMsg("该银行卡不支持");
				return result;
			}
		}
		bankCode = ValidateUtil.getBankCode(bankNameUtil);
		logger.info("【BwBankCardService.canBindCard】borrowerId:" + borrowerId + ",工具类获取银行卡编码:" + bankCode);
		// 只对应到银行大分类
		String[] split = bankNameUtil.split("·");
		String bankName = split[0];
		// 判断银行卡是否支持
		if (StringUtils.isEmpty(bankCode) || !RedisUtils.hexists(RedisKeyConstant.FUIOU_BANK, bankCode)) {
			result.setCode("118");
			result.setMsg("该银行卡不支持");
			return result;
		}
		bankName = RedisUtils.hget(RedisKeyConstant.FUIOU_BANK, bankCode);
		paySignDto.setBankCode(bankCode);
		paySignDto.setBankName(bankName);
		paySignDto.setBankCardChange(isRebind);
		if (isRebind) {// 已绑卡
			// 重新绑卡不能修改姓名和身份证号码
			paySignDto.setName(queryName);
			paySignDto.setIdCard(queryIdCard);
			boolean canBool = canBindBankAuditByBorrowerId(borrowerId);
			if (!canBool) {
				result.setCode("147");
				result.setMsg("您的借款正在核实中，不能重新绑定银行卡！");
				return result;
			}
			// 从配置表中获得银行卡的绑定次数
			String code = ParameterConstant.BANK_CARD_BINDING_TIME;// 绑定银行的code
			Integer count = paySignDto.getMonthBindCountLimit();
			if (count == null || count <= 0) {
				count = Integer.parseInt(extraConfigService.findCountExtraConfigByCode(code));// 银行绑定次数
			}
			int changeCount = bwBankCardChangeService.findBwBankCardChangeByBorrowid(borrowerId);// 通过借款人id查询出银行卡绑定记录
			if (changeCount >= count) {// 修改次数小于等于银行卡绑定次数
				result.setCode("119");
				result.setMsg(StringUtils.join("每月只能重新绑定", count, "次银行卡！"));
				return result;
			}
		} else {// 未绑卡
			paySignDto.setName(name);
			paySignDto.setIdCard(StringUtils.isNotEmpty(queryIdCard) ? queryIdCard : idCard);
			// 第一步ORC非人工上传时身份证信息必须一致
			if (StringUtils.isNotEmpty(queryIdCard) && !queryIdCard.equals(idCard)) {
				result.setCode("146");
				result.setMsg("请绑定本人身份证");
				return result;
			}
			int age = clacAgeByIdCard(paySignDto.getIdCard());
			if (age <= 0 || age > 120) {
				result.setCode("113");
				result.setMsg("身份证不合法");
				logger.info(StringUtils.join("【BwBankCardService.canBindCard】borrowerId:", borrowerId, "年龄不合理,age=", age));
				return result;
			}
		}
		result.setCode("000");
		result.setMsg("可以绑卡");
		return result;
	}

	/**
	 * 根据用户ID、工单状态查看用户是否可以绑卡
	 *
	 * @param borrowerId
	 * @return
	 */
	private boolean canBindBankAuditByBorrowerId(Long borrowerId) {
		Example example = new Example(BwOrder.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("borrowerId", borrowerId);
		criteria.andIn("statusId", Arrays.asList(2, 3, 5, 11, 12, 14));
		int count = bwOrderMapper.selectCountByExample(example);
		boolean canBool = true;
		if (count > 0) {
			canBool = false;
		}
		return canBool;
	}

	@Override
	public AppResponseResult updateAndReadyBindBankCard(PaySignDto paySignDto) {
		Long borrowerId = paySignDto.getBorrowerId();
		AppResponseResult result = new AppResponseResult();
		AppResponseResult canBindResult = canBindCard(paySignDto);
		if (!"000".equals(canBindResult.getCode())) {
			result.setCode(canBindResult.getCode());
			result.setMsg(canBindResult.getMsg());
			return result;
		}
		String bankCode = paySignDto.getBankCode();
		if (StringUtils.isEmpty(paySignDto.getBankName())) {
			paySignDto.setBankName(RedisUtils.hget(RedisKeyConstant.FUIOU_BANK, bankCode));
		}
		String phone = paySignDto.getPhone();
		String lockKey = StringUtils.join(RedisKeyConstant.LOCK_KEY_PAY_PRE, phone);
		boolean lockBool = ControllerUtil.lockRequest(lockKey, 40);
        if (!lockBool) {
            result.setCode("300");
            result.setCode("正在处理中，请稍后再试");
            return result;
        }
		try {
			// 开始调用预绑卡接口
			BindCardRequest bindCardRequest = new BindCardRequest();
			bindCardRequest.setUserId(CommUtils.toString(borrowerId));
			bindCardRequest.setCardNo(paySignDto.getCardNo());
			bindCardRequest.setIdCard(paySignDto.getIdCard());
			bindCardRequest.setMobile(paySignDto.getPhone());
			bindCardRequest.setName(paySignDto.getName());
			bindCardRequest.setBankCode(BaofuConstant.convertFuiouBankCodeToBaofu(bankCode));
			com.beadwallet.entity.response.AppResponseResult<String> readyBindCardResult = BaoFooService.protocolReadyBindCard(bindCardRequest);
			if ("000".equals(readyBindCardResult.getCode())) {
				result.setCode("000");
				result.setMsg("预绑卡成功");
				// 保存unique_code
				String unique_code = readyBindCardResult.getResult();
				logger.info(StringUtils.join("【ApiPaySignService.readyBindBankCard】borrowerId:", borrowerId, "unique_code=", unique_code));
				RedisUtils.setex(StringUtils.join(RedisKeyConstant.BIND_BANK_UNIQUE_CODE_PRE, borrowerId), unique_code, 10 * 60);
				Long resultCount = RedisUtils.hset(RedisKeyConstant.PAY_SIGN, borrowerId.toString(), JSON.toJSONString(paySignDto));
				if (resultCount == null) {
					result.setCode("111");
					result.setMsg("绑卡失败，请稍后再试");
					logger.error("【ApiPaySignService.readyBindBankCard】borrowerId=" + borrowerId + ",绑定银行卡，存入redis异常");
					return result;
				}
			} else {
				result.setCode(readyBindCardResult.getCode());
				result.setMsg(readyBindCardResult.getMsg());
			}
		} finally {
			if (lockBool) {
				RedisUtils.del(lockKey);
			}
		}
		return result;
	}

	@Override
	public AppResponseResult updateAndSureBindCard(PaySignDto paySignDto) {
		Long borrowerId = paySignDto.getBorrowerId();
		AppResponseResult result = new AppResponseResult();
		if (borrowerId == null || borrowerId <= 0) {
			result.setCode("408");
			result.setMsg("借款人id为空");
			return result;
		}
		String unique_code = RedisUtils.get(StringUtils.join(RedisKeyConstant.BIND_BANK_UNIQUE_CODE_PRE, borrowerId));
		if (StringUtils.isEmpty(unique_code)) {
			result.setCode("457");
			result.setMsg("验证码已过期，请重新发送验证码");
			return result;
		}
		PaySignDto redisPaySignDto = queryRedisPaySign(borrowerId);
		if (redisPaySignDto == null) {
			result.setCode("458");
			result.setMsg("请重新发送验证码");
			return result;
		}
		String verifyCode = paySignDto.getVerifyCode();
		if (StringUtils.isEmpty(verifyCode)) {
			result.setCode("406");
			result.setMsg("验证码为空");
			return result;
		}
		com.beadwallet.entity.response.AppResponseResult<ProtocolBindCardResult> bindResult = BaoFooService.protocolSureBindCard(unique_code, verifyCode);
		logger.info(StringUtils.join("【ApiPaySignService.sureBindCard】borrowerId:", borrowerId, "宝付确认绑卡返回:", JSON.toJSONString(bindResult)));
		if (bindResult != null && "000".equals(bindResult.getCode())) {// 绑定成功
			ProtocolBindCardResult protocolBindCardResult = bindResult.getResult();
			BwBorrower bwBorrower = bwBorrowerService.selectByPrimaryKey(borrowerId);
			BwBankCard bwBankCard = selectByBorrowerId(borrowerId);
			boolean hasSign = isBindCardAgain(bwBankCard);
			// 开始保存绑卡信息
			BwBorrower updateBorroer = new BwBorrower();
			updateBorroer.setId(borrowerId);
			if (!hasSign) {// 未绑卡
				updateBorroer.setName(redisPaySignDto.getName());
				Integer age = null;
				if (StringUtils.isEmpty(bwBorrower.getIdCard())) {
					updateBorroer.setIdCard(redisPaySignDto.getIdCard());
					updateBorroer.setUpdateTime(new Date());
					age = clacAgeByIdCard(redisPaySignDto.getIdCard());
				} else {
					age = clacAgeByIdCard(bwBorrower.getIdCard());
				}
				updateBorroer.setAge(age);
				bwBorrowerService.updateByPrimaryKeySelective(updateBorroer);
			}

			if (hasSign) {// 重新绑卡，绑卡记录
				BwBankCardChange cardChange = new BwBankCardChange();
				cardChange.setBankCode(bwBankCard.getBankCode());
				cardChange.setBankName(bwBankCard.getBankName());
				cardChange.setBorrowerId(borrowerId);
				cardChange.setCardNo(bwBankCard.getCardNo());
				cardChange.setCityCode(bwBankCard.getCityCode());
				cardChange.setCreateTimeOld(bwBankCard.getCreateTime());
				cardChange.setPhone(bwBankCard.getPhone());
				cardChange.setProvinceCode(bwBankCard.getProvinceCode());
				cardChange.setSignStatus(bwBankCard.getSignStatus());
				cardChange.setUpdateTimeOld(bwBankCard.getUpdateTime());
				cardChange.setCreatedTime(new Date());
				bwBankCardChangeMapper.insertSelective(cardChange);
			}

			// 保存银行卡信息
			BwBankCard updateBankCard = new BwBankCard();
			updateBankCard.setBorrowerId(borrowerId);
			updateBankCard.setSignStatus(4);
			updateBankCard.setCardNo(redisPaySignDto.getCardNo());
			updateBankCard.setPhone(redisPaySignDto.getPhone());
			updateBankCard.setBankName(redisPaySignDto.getBankName());
			updateBankCard.setBankCode(redisPaySignDto.getBankCode());
			updateBankCard.setUpdateTime(new Date());
			saveOrUpdateByBorrowerId(updateBankCard);

			// 保存绑卡信息
			BwBankCardBindInfo updateBankCardBindInfo = new BwBankCardBindInfo();
			updateBankCardBindInfo.setBorrowerId(borrowerId);
			updateBankCardBindInfo.setChannelBankCode(protocolBindCardResult.getBank_code());
			updateBankCardBindInfo.setBindNo(protocolBindCardResult.getProtocol_no());
			updateBankCardBindInfo.setBindChannel(4);
			updateBankCardBindInfo.setBankCardId(updateBankCard.getId());
			bwBankCardBindInfoService.saveOrUpdateByBorrowerId(updateBankCardBindInfo);

			RedisUtils.del(StringUtils.join(RedisKeyConstant.BIND_BANK_UNIQUE_CODE_PRE, borrowerId));
			RedisUtils.hdel(RedisKeyConstant.PAY_SIGN, borrowerId.toString());
			result.setMsg("绑卡成功");
			result.setCode("000");
		} else {
			result.setCode("459");
			result.setMsg(bindResult.getMsg());
		}
		return result;
	}

	@Override
	public PaySignDto queryRedisPaySign(Long borrowerId) {
		PaySignDto redisPaySign = null;
		if (borrowerId != null && borrowerId > 0L) {
			String paySignJson = RedisUtils.hget(RedisKeyConstant.PAY_SIGN, borrowerId.toString());
			if (StringUtils.isNotEmpty(paySignJson)) {
				redisPaySign = JSON.parseObject(paySignJson, PaySignDto.class);
			}
		}
		return redisPaySign;
	}

	public void saveOrUpdateByBorrowerId(BwBankCard bwBankCard) {
		if (bwBankCard == null || bwBankCard.getBorrowerId() == null) {
			return;
		}
		Long borrowerId = bwBankCard.getBorrowerId();
		BwBankCard queryCard = selectByBorrowerId(borrowerId);
		bwBankCard.setUpdateTime(new Date());
		if (queryCard != null) {
			Long bankCardId = queryCard.getId();
			bwBankCard.setId(bankCardId);
			updateByPrimaryKeySelective(bwBankCard);
		} else {
			bwBankCard.setBorrowerId(borrowerId);
			bwBankCard.setId(null);
			bwBankCard.setCreateTime(new Date());
			if (bwBankCard.getSignStatus() == null) {
				bwBankCard.setSignStatus(0);
			}
			insertSelective(bwBankCard);
		}
	}

	@Override
	public BwBankCard findBwBankCardByThirdOrderNoAndCardNo(String thirdOrderNo, String cardNo) {
		String sql = "SELECT bc.* FROM	bw_bank_card bc	LEFT JOIN bw_order o ON bc.borrower_id = o.borrower_id	LEFT JOIN bw_order_rong oro ON o.id = oro.order_id WHERE oro.third_order_no = "
				+ thirdOrderNo + " 	AND bc.card_no = " + cardNo;
		return sqlMapper.selectOne(sql, BwBankCard.class);
	}
}
