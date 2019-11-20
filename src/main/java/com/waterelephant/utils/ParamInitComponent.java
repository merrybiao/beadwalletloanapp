package com.waterelephant.utils;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class ParamInitComponent {

	static {
		ResourceBundle config_bundle = ResourceBundle.getBundle("config");
		if (config_bundle == null) {
			throw new IllegalArgumentException("[config.properties] is not found!");
		}
		SystemConstant.CMS_BORROW_QUESTION = config_bundle.getString("cms_borrowQuestion");
		SystemConstant.CMS_REPAYMENT_QUESTION = config_bundle.getString("cms_repaymentQuestion");
		SystemConstant.CMS_COST_QUESTION = config_bundle.getString("cms_costQuestion");
		SystemConstant.CMS_ACCOUNT_QUESTION = config_bundle.getString("cms_accountQuestion");
		SystemConstant.CMS_BANNARIMG = config_bundle.getString("cms_bannarImg");
		SystemConstant.CMS_SCROLL = config_bundle.getString("cms_scroll");
		SystemConstant.CMS_MESSAGE = config_bundle.getString("cms_message");
		SystemConstant.CMS_URGENTMESSAGE = config_bundle.getString("cms_urgentmessage");

		SystemConstant.MESSAGE_CIRCUMSTANCE = config_bundle.getString("message_circumstance");
		SystemConstant.INSURE_CIRCUMSTANCE = config_bundle.getString("insure_circumstance");
		SystemConstant.DEFAULT_AVOID_FINE_DATE = config_bundle.getString("avoid_fine_date");
		SystemConstant.PDF_URL = config_bundle.getString("PDF_URL");
		SystemConstant.ACCESSKEY_ID = config_bundle.getString("ACCESSKEY_ID");
		SystemConstant.ACCESS_KEY_SECRET = config_bundle.getString("ACCESS_KEY_SECRET");
		SystemConstant.ENDPOINT = config_bundle.getString("ENDPOINT");
		SystemConstant.BUCKET_NAME = config_bundle.getString("BUCKET_NAME");
		SystemConstant.IMG_URL = config_bundle.getString("IMG_URL");
		SystemConstant.APK_Down_Path = config_bundle.getString("APK_Down_Path");
		SystemConstant.APPID = config_bundle.getString("appid");
		SystemConstant.APP_SECRET = config_bundle.getString("appsecret");
		SystemConstant.YIQIHAO_FILE_PATH = config_bundle.getString("yiqihao_file_path");
		// 支付代扣是否测试
		try {
			SystemConstant.WITHHOLD_TEST_BOOL = Boolean.parseBoolean(config_bundle.getString("withhold_test_bool"));
		} catch (Exception e) {
		}

		ResourceBundle redis_bundle = ResourceBundle.getBundle("redis");
		if (redis_bundle == null) {
			throw new IllegalArgumentException("[redis.properties] is not found!");
		}
		SystemConstant.REDIS_IP = redis_bundle.getString("redis_ip");
		SystemConstant.REDIS_PORT = Integer.parseInt(redis_bundle.getString("redis_port"));
		SystemConstant.REDIS_MAXWAIT = Integer.parseInt(redis_bundle.getString("redis_maxWait"));
		SystemConstant.REDIS_MAXIDLE = Integer.parseInt(redis_bundle.getString("redis_maxIdle"));
		SystemConstant.REDIS_MAXACTIVE = Integer.parseInt(redis_bundle.getString("redis_maxActive"));
		SystemConstant.REDIS_TIMEOUT = Integer.parseInt(redis_bundle.getString("redis_timeout"));
		SystemConstant.AUDIT_KEY = redis_bundle.getString("audit");
		SystemConstant.MOXIE_CARRIER_KEY = redis_bundle.getString("moxieCarrier");
		SystemConstant.MOXIE_CARRIER_TEM = redis_bundle.getString("moxieCarrierTem");
		SystemConstant.ZHENG_XIN_91 = redis_bundle.getString("zhengXin91");
		SystemConstant.REDIS_PASSWORD = redis_bundle.getString("redis_password");
		ResourceBundle fuiou_bundle = ResourceBundle.getBundle("fuiou");
		if (fuiou_bundle == null) {
			throw new IllegalArgumentException("[fuiou.properties] is not found!");
		}
		SystemConstant.CALLBACK_URL = fuiou_bundle.getString("callback_url");
		SystemConstant.FUIOU_MCHNT_CD = fuiou_bundle.getString("mchnt_cd");
		SystemConstant.FUIOU_MCHNT_NAME = fuiou_bundle.getString("fuiou_mchnt_name");
		SystemConstant.FUIOU_MCHNT_BACKUP = fuiou_bundle.getString("fuiou_mchnt_backup");

		ResourceBundle es_bundle = ResourceBundle.getBundle("es");
		if (es_bundle == null) {
			throw new IllegalArgumentException("[es.properties] is not found!");
		}
		SystemConstant.ES_IP = es_bundle.getString("es.host");
		SystemConstant.ES_PORT = Integer.parseInt(es_bundle.getString("es.port"));
		SystemConstant.ES_CLUSTER_NAME = es_bundle.getString("es.cluster.name");
		SystemConstant.ES_INDEX = es_bundle.getString("ES_INDEX");

		ResourceBundle lianlian_bundle = ResourceBundle.getBundle("lianlian");
		if (lianlian_bundle == null) {
			throw new IllegalArgumentException("[lianlian.properties] is not found!");
		}
		SystemConstant.NOTIRY_URL = lianlian_bundle.getString("NOTIFY_URL");
		SystemConstant.YT_PUB_KEY = lianlian_bundle.getString("YT_PUB_KEY");

		ResourceBundle rong360_bundle = ResourceBundle.getBundle("rong360");
		if (rong360_bundle == null) {
			throw new IllegalArgumentException("[rong360.properties] is not found!");
		}
		SystemConstant.RONG360_PUB_KEY = rong360_bundle.getString("pubKey");

		SystemConstant.NEW_GUIDELINES = config_bundle.getString("new_guidelines");

		SystemConstant.XUDAI_AFTER_DATE = config_bundle.getString("xudai_after_date");

//		ResourceBundle config_fqgj = ResourceBundle.getBundle("fqgj");
//		SystemConstant.FQGJ_PUB_KEY = config_fqgj.getString("FQGJ_PUB_KEY");
//
//		ResourceBundle config_ybj = ResourceBundle.getBundle("ybj");
//		SystemConstant.YBJ_PUB_KEY = config_ybj.getString("YBJ_PUB_KEY");
//		SystemConstant.YBJ_MER_ID = config_ybj.getString("YBJ_MER_ID");
//		SystemConstant.YBJ_KEY = config_ybj.getString("YBJ_KEY");
//		SystemConstant.YBJ_CHARGE = config_ybj.getString("YBJ_CHARGE");

//		ResourceBundle config_koudai = ResourceBundle.getBundle("koudai");
//		if (config_koudai == null) {
//			throw new IllegalArgumentException("[koudai.properties] is not found!");
//		}
//		SystemConstant.CAPITAL_KOUDAI_REQUEST_URL = config_koudai.getString("request_url");
//		SystemConstant.CAPITAL_KOUDAI_ACCOUNT = config_koudai.getString("account");
//		SystemConstant.CAPITAL_KOUDAI_PWD = config_koudai.getString("pwd");
//		SystemConstant.CAPITAL_KOUDAI_KEY = config_koudai.getString("key");
//		SystemConstant.CAPITAL_KOUDAI_VERIFY_KEY = config_koudai.getString("verify_key");
//		SystemConstant.CAPITAL_KOUDAI_VERIFY_KEY_D = config_koudai.getString("verify_key_d");

		ResourceBundle config_yiqile = ResourceBundle.getBundle("yiqile");
		if (config_yiqile == null) {
			throw new IllegalArgumentException("[yiqile.properties] is not found!");
		}
		SystemConstant.CAPITAL_YIQILE_KEY = config_yiqile.getString("signKey");

		ResourceBundle config_nuoyuan = ResourceBundle.getBundle("nuoyuan");
		if (config_nuoyuan == null) {
			throw new IllegalArgumentException("[nuoyuan.properties] is not found!");
		}
		SystemConstant.CAPITAL_NUOYUAN_KEY = config_nuoyuan.getString("key");
		SystemConstant.CAPITAL_NUOYUAN_IV = config_nuoyuan.getString("iv");

		ResourceBundle config_baofoo = ResourceBundle.getBundle("baofoo");
		if (config_baofoo == null) {
			throw new IllegalArgumentException("[baofoo.properties] is not found!");
		}
		SystemConstant.CAPITAL_BAOFOO_PUBKEYPATH = config_baofoo.getString("pubKeyPath");

		ResourceBundle config_baofoo_qfq = ResourceBundle.getBundle("baofooQfq");
		if (config_baofoo_qfq == null) {
			throw new IllegalArgumentException("[baofooQfq.properties] is not found!");
		}
		SystemConstant.CAPITAL_BAOFOO_QFQ_PUBKEYPATH = config_baofoo_qfq.getString("pubKeyPath");

		ResourceBundle config_uline = ResourceBundle.getBundle("uline");
		if (config_uline == null) {
			throw new IllegalArgumentException("[uline.properties] is not found!");
		}
		SystemConstant.ULINE_APP_ID = config_uline.getString("app_id");
		SystemConstant.ULINE_SECRET = config_uline.getString("secret");
		SystemConstant.ULINE_REDIRECT_URL = config_uline.getString("redirect_url");
		SystemConstant.ULINE_TRANSFER_URL = config_uline.getString("transfer_url");
		SystemConstant.ULINE_RESULT_URL = config_uline.getString("result_url");
		SystemConstant.ULINE_CHARGE = config_uline.getString("uline_charge");
		SystemConstant.ULINE_FAIL_HK = config_uline.getString("fail_url_hk");
		SystemConstant.ULINE_FAIL_ZQ = config_uline.getString("fail_url_zq");
		SystemConstant.ULINE_SIGN_KEY = config_uline.getString("key");
		
		ResourceBundle config_fqy = ResourceBundle.getBundle("beadloanapp_fqy");
		if (config_fqy == null) {
			throw new IllegalArgumentException("[beadloanapp_fqy.properties] is not found!");
		}
		SystemConstant.FQY_REQUEST_PATH = config_fqy.getString("request_path");
		SystemConstant.FQY_OPERATOR_DATA_URL = config_fqy.getString("operator_data_url");
	}

}
