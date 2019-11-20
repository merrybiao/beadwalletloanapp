package com.waterelephant.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.sms.chuanglan.service.ClSendMessageService;
import com.waterelephant.sms.dahansantong.service.DhstSendMessageService;
import com.waterelephant.sms.entity.SmsConfig;
import com.waterelephant.sms.service.SendMessageCommonService;
import com.waterelephant.sms.service.SmsConfigService;
import com.waterelephant.sms.yimei.service.YimeiSendMessageService;
import com.waterelephant.utils.RedisUtils;

/**
 * 短信发送通用事物处理层
 * 
 * @author dengyan
 *
 */
@Service
public class SendMessageCommonServiceImpl implements SendMessageCommonService {

	private Logger logger = LoggerFactory.getLogger(SendMessageCommonServiceImpl.class);

	@Autowired
	private SmsConfigService smsConfigService;

	@Autowired
	private YimeiSendMessageService yimeiSendMessageService;

	// @Autowired
	// private MgMessageInfoService mgMessageInfoService;

	@Autowired
	private DhstSendMessageService dhstSendMessageService;

	@Autowired
	private ClSendMessageService clSendMessageService;

	/**
	 * 通用文字短信发送
	 */
	@Override
	public boolean commonSendMessage(String phones, String msg) {
		try {
			// 第一步：查询配置文件中哪个发送短信的渠道在使用中
			int id = Integer.parseInt(RedisUtils.hget("system:sms", "1"));
			String seqid = String.valueOf(System.currentTimeMillis());
			SmsConfig smsConfig = smsConfigService.querySmsConfig(id);
			String sign = RedisUtils.hget("system:sms", "3");
			msg = msg.replace("水象借点花", sign.replace("【", "").replace("】", "")).replace("【水象分期】", "").replace("【", "")
					.replace("】", "").replace(" ", "");
			if (smsConfig != null) {
				if (smsConfig.getId() == 1) { // 如果状态为chenal为1则通过亿美发送短信
					boolean bo = yimeiSendMessageService.sendMsg(seqid, phones, sign + msg);
					if (bo) {
						// mgMessageInfoService.save(MgCollectionUtil.dbCollection(), phones, smsConfig.getSign() + msg,
						// seqid, smsConfig.getChenal(),1);
						return true;
					}
				}
				if (smsConfig.getId() == 2) {
					boolean bo = dhstSendMessageService.sendMsg(seqid, phones, msg, sign);
					if (bo) {
						// mgMessageInfoService.save(MgCollectionUtil.dbCollection(), phones, msg, seqid,
						// smsConfig.getChenal(),1);
						return true;
					}
				}
				if (smsConfig.getId() == 5) {
					boolean bo = clSendMessageService.sendMsg(seqid, phones, sign + msg);
					if (bo) {
						// mgMessageInfoService.save(MgCollectionUtil.dbCollection(), phones, smsConfig.getSign() + msg,
						// seqid, smsConfig.getChenal(), 1);
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("发送短信异常：", e);
			return false;
		}

		return false;
	}

	/**
	 * 语音发送短信
	 */
	@Override
	public boolean commonSendMessageVoice(String phones, String msg) {
		try {
			// 第一步：查询配置文件中哪个发送短信的渠道在使用中
			int id = Integer.parseInt(RedisUtils.hget("system:sms", "2"));
			String seqid = String.valueOf(System.currentTimeMillis());
			SmsConfig smsConfig = smsConfigService.querySmsConfig(id);
			if (smsConfig != null) {
				if (smsConfig.getId() == 3) {
					boolean bo = yimeiSendMessageService.sendMsgVoice(seqid, phones, msg);
					if (bo) {
						// mgMessageInfoService.save(MgCollectionUtil.dbCollection(), phones, msg, seqid,
						// smsConfig.getChenal(), 2);
						return true;
					}
				}
				if (smsConfig.getId() == 4) {
					boolean bo = clSendMessageService.sendMsgVoice(seqid, phones, msg);
					if (bo) {
						// mgMessageInfoService.save(MgCollectionUtil.dbCollection(), phones, msg, seqid,
						// smsConfig.getChenal(), 2);
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("发送短信异常：", e);
			return false;
		}

		return false;
	}

	/**
	 * 调用大汉三通的短信发送
	 * 
	 * @param phones
	 * @param msg
	 * @return
	 */
	@Override
	public boolean dhstSendMessage(String phones, String msg) {
		try {
			// 第一步：查询配置文件中哪个发送短信的渠道在使用中
			int id = Integer.parseInt(RedisUtils.hget("system:sms", "4"));
			String seqid = String.valueOf(System.currentTimeMillis());
			SmsConfig smsConfig = smsConfigService.querySmsConfig(id);
			String sign = RedisUtils.hget("system:sms", "3");
			msg = msg.replace("水象借点花", sign.replace("【", "").replace("】", "")).replace("【水象分期】", "").replace("【", "")
					.replace("】", "").replace(" ", "");
			if (smsConfig != null) {
				if (smsConfig.getId() == 1) { // 如果状态为chenal为1则通过亿美发送短信
					boolean bo = yimeiSendMessageService.sendMsg(seqid, phones, sign + msg);
					if (bo) {
						// mgMessageInfoService.save(MgCollectionUtil.dbCollection(), phones, smsConfig.getSign() + msg,
						// seqid, smsConfig.getChenal(), 1);
						return true;
					}
				}
				if (smsConfig.getId() == 2) {
					boolean bo = dhstSendMessageService.sendMsg(seqid, phones, msg, sign);
					if (bo) {
						// mgMessageInfoService.save(MgCollectionUtil.dbCollection(), phones, msg, seqid,
						// smsConfig.getChenal(), 1);
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("发送短信异常：", e);
			return false;
		}

		return false;
	}

}
