/**
 * @author heqiwen
 * @date 2016年12月18日
 */
package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beadwallet.service.entity.response.YiMeiApplicationResponse;
import com.beadwallet.service.entity.response.YiMeiOperateResponse;
import com.beadwallet.service.entity.response.YiMeiOverdueResponse;
import com.beadwallet.service.entity.response.YiMeiRegisterResponse;
import com.beadwallet.service.serve.BeadWalletYiMeiService;
import com.waterelephant.entity.YimeiApplicationinfo;
import com.waterelephant.entity.YimeiMaindata;
import com.waterelephant.entity.YimeiOverdueinfo;
import com.waterelephant.entity.YimeiRegisterinfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.YiMeiApplicationService;
import com.waterelephant.service.YiMeiOverdueService;
import com.waterelephant.service.YiMeiRegisterService;
import com.waterelephant.service.YimeiMaindataService;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;

/**
 * @author Administrator
 *
 */
@Service
public class YimeiMaindataServiceImpl extends BaseService<YimeiMaindata, Long> implements YimeiMaindataService {

	@Autowired
	private YiMeiApplicationService yiMeiApplicationService;

	@Autowired
	private YiMeiOverdueService yiMeiOverdueService;

	@Autowired
	private YiMeiRegisterService yiMeiRegisterService;

	private static Logger logger = Logger.getLogger(YimeiMaindataServiceImpl.class);

	/**
	 * @author heqiwen
	 * @date 2016年12月18日
	 */
	public int addYimeiMaindata(YimeiMaindata YiMeiOverdue) {
		return 0;
	}

	/**
	 * @author heqiwen
	 * @date 2016年12月18日
	 */
	@Override
	public int updateYimeiMaindata(YimeiMaindata YiMeiOverdue) {
		return 0;
	}

	/**
	 * @author heqiwen
	 * @date 2016年12月18日
	 */
	public int deleteYimeiMaindata(String phone) {
		return 0;
	}

	/**
	 * 根据手机号 查询亿美 信贷数据 业务是这样的：1先到数据库中查数据，如果没有数据，就直接去亿美查询数据并保存。
	 * 2：如果有数据，判断是否是在30内的，如果是30天内更新的，就直接从数据库里取数据。如果是30天外的，删除以前的数据，再查亿美数据并保存。
	 * 
	 * @author heqiwen
	 * @date 2016年12月20日
	 */
	public Map<String, Object> saveYiMeiDataByPhone(String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		YimeiMaindata maindata = new YimeiMaindata();
		maindata.setPhone(phone);

		int registerCount = 0;
		int applicationCount = 0;
		int overdueCount = 0;
		List<YimeiRegisterinfo> list1 = new ArrayList<YimeiRegisterinfo>();//// 用户手机注册平台
		List<YimeiApplicationinfo> list2 = new ArrayList<YimeiApplicationinfo>();//// 用户借贷平台
		List<YimeiOverdueinfo> list3 = new ArrayList<YimeiOverdueinfo>();//// 用户逾期的平台

		try {
			Date now = new Date();
			// int i=mapper.selectCount(maindata);//查看数据库中是否有数据
			List<YimeiMaindata> yimeilist = mapper.select(maindata);
			if (yimeilist != null && yimeilist.size() > 0) {// 如果有
				maindata = yimeilist.get(0);
				Date da = maindata.getUpdatetime();
				Date date1 = MyDateUtils.addMonths(da, 1);
				if (date1.after(now)) {// 如果在30天内有他的数据，就直接从库里取
					list1 = yiMeiRegisterService.getYiMeiRegisterByPhone(phone);
					list2 = yiMeiApplicationService.getYiMeiApplicationByPhone(phone);
					list3 = yiMeiOverdueService.getYiMeiOverdueByPhone(phone);
				} else {
					// 拿取新数据,调亿美接口,保存
					yiMeiRegisterService.deleteYiMeiRegister(phone);
					yiMeiApplicationService.deleteYiMeiApplication(phone);
					yiMeiOverdueService.deleteYiMeiOverdue(phone);
					long id = maindata.getId();
					maindata.setUpdatetime(new Date());
					mapper.updateByPrimaryKey(maindata);
					logger.info("saveYiMeiDataByPhone拿取新数据,调亿美接口");
					YiMeiOperateResponse yimeiRegister = BeadWalletYiMeiService.GetCreditRegistrationDetail(phone, "0",
							"6", RedisUtils.get("yimei:token"));
					if (yimeiRegister != null && yimeiRegister.getRegisterList() != null) {
						for (YiMeiRegisterResponse regis : yimeiRegister.getRegisterList()) {
							YimeiRegisterinfo registerVo = new YimeiRegisterinfo();
							registerVo.setPhone(phone);
							registerVo.setpType(regis.getpType());
							registerVo.setPlatformCode(regis.getPlatformCode());
							registerVo.setRegisterTime(regis.getRegisterTime());
							registerVo.setCreateTime(now);
							registerVo.setMainid(id);
							yiMeiRegisterService.addYiMeiRegister(registerVo);
							list1.add(registerVo);
						}
					}

					YiMeiOperateResponse yimeiApp = BeadWalletYiMeiService.GetLoanApplicationDetial(phone, "0", "6",
							RedisUtils.get("yimei:token"));
					if (yimeiApp != null && yimeiApp.getApplicationList() != null) {
						for (YiMeiApplicationResponse applicat : yimeiApp.getApplicationList()) {
							YimeiApplicationinfo applicatVo = new YimeiApplicationinfo();
							applicatVo.setPhone(phone);
							applicatVo.setpType(applicat.getpType());
							applicatVo.setPlatformCode(applicat.getPlatformCode());
							applicatVo.setApplicationTime(applicat.getApplicationTime());
							applicatVo.setApplicationMount(applicat.getApplicationMount());
							applicatVo.setApplicationResult(applicat.getApplicationResult());
							applicatVo.setCreateTime(now);
							applicatVo.setMainid(id);
							yiMeiApplicationService.addYiMeiApplication(applicatVo);
							list2.add(applicatVo);
						}
					}
					YiMeiOperateResponse yimeiOverdue = BeadWalletYiMeiService.GetBeOverduePlatformDetail(phone, "6",
							RedisUtils.get("yimei:token"));
					if (yimeiOverdue != null && yimeiOverdue.getOverdueList() != null) {
						for (YiMeiOverdueResponse over : yimeiOverdue.getOverdueList()) {
							YimeiOverdueinfo overdueVo = new YimeiOverdueinfo();
							overdueVo.setPhone(phone);
							overdueVo.setpType(over.getpType());
							overdueVo.setPlatformCode(over.getPlatformCode());
							overdueVo.setCounts(over.getCounts());
							overdueVo.setMoney(over.getMoney());
							overdueVo.setProvince(over.getProvince());
							overdueVo.setCity(over.getCity());
							overdueVo.setCreateTime(now);
							overdueVo.setMainid(id);
							yiMeiOverdueService.addYiMeiOverdue(overdueVo);
							list3.add(overdueVo);

						}
					}

				}

			} else {// 如果表里没有数据,就直接到亿美那调
				maindata.setCreatetime(new Date());
				maindata.setUpdatetime(new Date());
				mapper.insert(maindata);
				Long id = maindata.getId();
				logger.info("saveYiMeiDataByPhone表里没有数据,就直接到亿美那调接口");
				YiMeiOperateResponse yimeiRegister = BeadWalletYiMeiService.GetCreditRegistrationDetail(phone, "0", "6",
						RedisUtils.get("yimei:token"));
				if (yimeiRegister != null && yimeiRegister.getRegisterList() != null) {
					for (YiMeiRegisterResponse regis : yimeiRegister.getRegisterList()) {
						YimeiRegisterinfo registerVo = new YimeiRegisterinfo();
						registerVo.setPhone(phone);
						registerVo.setpType(regis.getpType());
						registerVo.setPlatformCode(regis.getPlatformCode());
						registerVo.setRegisterTime(regis.getRegisterTime());
						registerVo.setCreateTime(now);
						registerVo.setMainid(id);
						yiMeiRegisterService.addYiMeiRegister(registerVo);
						list1.add(registerVo);
					}
				}

				YiMeiOperateResponse yimeiApp = BeadWalletYiMeiService.GetLoanApplicationDetial(phone, "0", "6",
						RedisUtils.get("yimei:token"));
				if (yimeiApp != null && yimeiApp.getApplicationList() != null) {
					for (YiMeiApplicationResponse applicat : yimeiApp.getApplicationList()) {
						YimeiApplicationinfo applicatVo = new YimeiApplicationinfo();
						applicatVo.setPhone(phone);
						applicatVo.setpType(applicat.getpType());
						applicatVo.setPlatformCode(applicat.getPlatformCode());
						applicatVo.setApplicationTime(applicat.getApplicationTime());
						applicatVo.setApplicationMount(applicat.getApplicationMount());
						applicatVo.setApplicationResult(applicat.getApplicationResult());
						applicatVo.setCreateTime(now);
						applicatVo.setMainid(id);
						yiMeiApplicationService.addYiMeiApplication(applicatVo);
						list2.add(applicatVo);
					}
				}

				YiMeiOperateResponse yimeiOverdue = BeadWalletYiMeiService.GetBeOverduePlatformDetail(phone, "6",
						RedisUtils.get("yimei:token"));
				if (yimeiOverdue != null && yimeiOverdue.getOverdueList() != null) {
					for (YiMeiOverdueResponse over : yimeiOverdue.getOverdueList()) {
						YimeiOverdueinfo overdueVo = new YimeiOverdueinfo();
						overdueVo.setPhone(phone);
						overdueVo.setpType(over.getpType());
						overdueVo.setPlatformCode(over.getPlatformCode());
						overdueVo.setCounts(over.getCounts());
						overdueVo.setMoney(over.getMoney());
						overdueVo.setProvince(over.getProvince());
						overdueVo.setCity(over.getCity());
						overdueVo.setCreateTime(now);
						overdueVo.setMainid(id);
						yiMeiOverdueService.addYiMeiOverdue(overdueVo);
						list3.add(overdueVo);

					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveYiMeiDataByPhone方法报错:" + e);
		}

		// System.out.println(RedisUtils.get("yimei:token"));
		registerCount = list1.size();
		applicationCount = list2.size();
		overdueCount = list3.size();
		map.put("registerlist", list1);
		map.put("applicationlist", list2);
		map.put("overduelist", list3);
		map.put("registerCount", registerCount);// 用户手机注册平台的数量
		map.put("applicationCount", applicationCount);// 用户手机贷款平台的数量
		map.put("overdueCount", overdueCount);// 用户手机逾期平台的数量
		return map;

	}

}
