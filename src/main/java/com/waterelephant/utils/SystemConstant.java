package com.waterelephant.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统常量
 *
 * @author Hui Wang
 */
public class SystemConstant {

	/********************************** 系统常量 *****************************************/
	// yyyy-MM-dd HH:mm:ss
	public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
	// yyyy-MM-dd
	public static final String YMD = "yyyy-MM-dd";

	// APP手机验证码
	public static final String APP_PHONE_CODE = "appPhoneCode";
	// APP图形验证码
	public static final String APP_VERIFY_CODE = "appVerifyCode";
	/**
	 * 代扣支付是否测试
	 */
	public static boolean WITHHOLD_TEST_BOOL = false;

	// 图片路径
	public static String IMG_PATH = "";
	public static String PDF_URL = "";

	// 短信的环境 0 真是环境 1 测试环境
	public static String MESSAGE_CIRCUMSTANCE = "";
	// 社保环境 0 真是环境 1 测试环境
	public static String INSURE_CIRCUMSTANCE = "";

	// 回调URL
	public static String CALLBACK_URL = "";
	// 富友生产商户代码
	public static String FUIOU_MCHNT_CD = "";
	// 平台商户登录账号
	public static String FUIOU_MCHNT_NAME = "";
	// 平台风险备用金账户
	public static String FUIOU_MCHNT_BACKUP = "";
	// 默认免罚息天数
	public static String DEFAULT_AVOID_FINE_DATE = "";

	/************************************* APP *************************************/
	// app会话token
	public static final String APP_SESSION_LOGIN_TOKEN = "appLoginToken";
	// app页面请token
	public static final String APP_PAGE_TOKEN = "pageToken";

	// APP接口token
	public static final Map<String, String> SESSION_APP_TOKEN = new HashMap<String, String>();

	/************************************* 国政通 *******************************************/
	// 国政通身份验证结果
	public static final String GBOSS_MSG_OK = "一致";
	public static final String GBOSS_MSG_NO = "不一致";

	// 前海征信查询原因
	// 01--贷款审批
	// 02--贷中管理
	// 03—贷后管理
	// 04--本人查询
	// 05--异议查询
	public static final String QHZX_REASONCODE_DQ = "01";
	public static final String QHZX_REASONCODE_DZ = "02";
	public static final String QHZX_REASONCODE_DH = "03";
	public static final String QHZX_REASONCODE_BR = "04";
	public static final String QHZX_REASONCODE_YY = "05";

	/*********************** redis pool ************************/
	// redis服务ip
	public static String REDIS_IP = "";

	public static int REDIS_PORT = 0;

	// redis最大的等待时间
	public static int REDIS_MAXWAIT = 0;

	// pool最大空闲数
	public static int REDIS_MAXIDLE = 0;

	// pool最大连接数
	public static int REDIS_MAXACTIVE = 0;

	// 连接超时时间
	public static int REDIS_TIMEOUT = 0;
	// auth密码
	public static String REDIS_PASSWORD = "";
	/*********************** 前海征信 ************************/
	// 机构代码
	public static String QHZX_ORGCODE = "";
	// 渠道、系统ID
	public static String QHZX_CHNLID = "";
	// 机构授权码
	public static String QHZX_AUTHCODE = "";
	// 授权时间(yyyy-MM-dd)
	public static String QHZX_AUTHDATE = "";
	// 签名
	public static String QHZX_SIGN = "";
	// 用户
	public static String QHZX_USER_NAME = "";
	// 用户密码
	public static String QHZX_USER_PWD = "";

	/**
	 * 微信
	 */
	// APPID
	public static String APPID = "";
	// 微信密钥
	public static String APP_SECRET = "";
	// 授权接口地址
	public static final String WEIXIN_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// 获得用户信息
	public static final String WEIXIN_USER_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 微信openId保存在session中的token
	public static final String WEIXIN_OPENID_TOKEN = "weixinOpenid";
	// 微信用户定位保存在session中的token
	public static final String WEIXIN_LOCATION_TOKEN = "weixinLocation";
	// 工单渠道号保存在会话中的token
	public static final String ORDER_CHENNEL_TOKEN = "orderChannel";

	/**
	 * 阿里云ACCESS_ID
	 */
	public static String ACCESSKEY_ID = "";
	/**
	 * 阿里云ACCESS_KEY
	 */
	public static String ACCESS_KEY_SECRET = "";
	/**
	 * 阿里云OSS_ENDPOINT 终端地址
	 */
	public static String ENDPOINT = "";

	/**
	 * 阿里云BUCKET_NAME OSS
	 */
	public static String BUCKET_NAME = "";
	/**
	 * 阿里云图片访问前路径
	 */
	public static String IMG_URL = "";
	/**
	 * apk默认下载路径
	 */
	public static String APK_Down_Path = "";

	/*** ElasticSearch配置 ***/
	public static String ES_IP = null;
	public static int ES_PORT = 0;
	public static String ES_CLUSTER_NAME = null;
	public static String ES_INDEX = null;
	public static String ES_ORDER_STATUS = "esOrderStatus";

	/******************************* 自定义redsiKEY ************************************/
	public static String AUDIT_KEY = null;
	public static String MOXIE_CARRIER_KEY = null;
	public static String MOXIE_CARRIER_TEM = null;

	public static String ZHENG_XIN_91 = null;

	/******** 一起好文件保存路径 *******/
	public static String YIQIHAO_FILE_PATH;
	// 存入redsikey
	public static final String WEIXIN_ORDER_ID = "xudai:order_id";

	/**
	 * baofu notify
	 */
	public static String NOTIFY_BAOFU = "notify_baofu:order_id";
	public static List<String> baofuCode = new ArrayList<>();

	static {
		baofuCode.add("BF00100");
		baofuCode.add("BF00112");
		baofuCode.add("BF00113");
		baofuCode.add("BF00115");
		baofuCode.add("BF00144");
		baofuCode.add("BF00202");
	}

	/**
	 * 身份证号
	 * 
	 * CERTNO_Match_Trust_Self_Sharing_Bad 身份证号与其他信息匹配，但匹配后的信息被多个用户使用(中风险) CERTNO_History_NegativeList 身份证号存在欺诈记录
	 * CERTNO_Mismatch 身份证号与其他信息不匹配 CERTNO_Match_Trust_Other_Recency_Bad 身份证号与其他信息不匹配
	 * CERTNO_Match_Trust_Other_Sharing_Bad 身份证号与其他信息不匹配 CERTNO_Match_Trust_Other_Reliability_Bad 身份证号与其他信息不匹配
	 **/

	/**
	 * 姓名 NAME_Mismatch 姓名与其他信息不匹配
	 *
	 **/

	/**
	 * 手机号 PHONE_Mismatch 电话号码与其他信息不匹配 PHONE_History_NegativeList 电话号码存在欺诈记录
	 *
	 */
	/**
	 * 银行卡 BANKCARD_Mismatch 银行卡号与其他信息不匹配 BANKCARD_History_NegativeList 银行卡号存在欺诈记录
	 */
	public static final String[] ZMXY_CODE_ARRAY = { "CERTNO_Match_Trust_Self_Sharing_Bad",
			"CERTNO_History_NegativeList", "CERTNO_Mismatch", "CERTNO_Match_Trust_Other_Recency_Bad",
			"CERTNO_Match_Trust_Other_Sharing_Bad", "CERTNO_Match_Trust_Other_Reliability_Bad", "NAME_Mismatch",
			"PHONE_Mismatch", "PHONE_History_NegativeList", "BANKCARD_Mismatch", "BANKCARD_History_NegativeList" };

	/*********************************** lianlianpay *********************************************/

	// 银行卡还款扣款异步回调地址
	public static String NOTIRY_URL = "";
	public static String YT_PUB_KEY = "";
	public static String NOTIFY_LIANLIAN_PRE = "lianlian:";

	/**
	 * yiqihao notify
	 */
	public static String YIQIHAO_ZHANQI = "yiqihao:zhanqi";

	public static String RONG360_PUB_KEY = "";

	/*********************** CMS常量 ************************/
	/** 借款问题 */
	public static String CMS_BORROW_QUESTION = "";
	/** 还款问题 */
	public static String CMS_REPAYMENT_QUESTION = "";
	/** 费用问题 */
	public static String CMS_COST_QUESTION = "";
	/** 账户问题 */
	public static String CMS_ACCOUNT_QUESTION = "";
	/** 首页bannar图 */
	public static String CMS_BANNARIMG = "";
	/** 首页滚动条 */
	public static String CMS_SCROLL = "";
	/** 首页消息 */
	public static String CMS_MESSAGE = "";
	/** 首页紧急通知 */
	public static String CMS_URGENTMESSAGE = "";
	/** 新手指引链接 */
	public static String NEW_GUIDELINES = "";
	/** 计算续贷次数开始时间 */
	public static String XUDAI_AFTER_DATE = "";

	// 分期管家公钥
	public static String FQGJ_PUB_KEY = "";
	// 益倍嘉 水象公钥
	public static String YBJ_PUB_KEY = "";
	public static String YBJ_KEY = "";
	public static String YBJ_MER_ID = "";
	public static String YBJ_CHARGE = "";

	// 畅优聚合支付
	public static String ULINE_APP_ID = "";
	public static String ULINE_SECRET = "";
	public static String ULINE_REDIRECT_URL = "";
	public static String ULINE_TRANSFER_URL = "";
	public static String ULINE_RESULT_URL = "";
	public static String ULINE_CHARGE = "";
	public static String ULINE_FAIL_HK = "";
	public static String ULINE_FAIL_ZQ = "";
	public static String ULINE_SIGN_KEY = "";

	// 资方 - 口袋
	public static String CAPITAL_KOUDAI_REQUEST_URL = "";
	public static String CAPITAL_KOUDAI_ACCOUNT = "";
	public static String CAPITAL_KOUDAI_PWD = "";
	public static String CAPITAL_KOUDAI_KEY = "";
	public static String CAPITAL_KOUDAI_VERIFY_KEY = "";
	public static String CAPITAL_KOUDAI_VERIFY_KEY_D = "";

	public static String CAPITAL_NUOYUAN_KEY = "";
	public static String CAPITAL_NUOYUAN_IV = "";

	public static String CAPITAL_BAOFOO_PUBKEYPATH = "";

	public static String CAPITAL_BAOFOO_QFQ_PUBKEYPATH = "";

	// 资方 - 亿奇乐
	public static String CAPITAL_YIQILE_KEY = "";

	// 百融
	public static String APICODE = "";

	public static String SANDBOXLOGINAPI = "";
	//分七云
	public static String FQY_REQUEST_PATH = "";
	//分七云-运营商数据
	public static String FQY_OPERATOR_DATA_URL = "";

}
