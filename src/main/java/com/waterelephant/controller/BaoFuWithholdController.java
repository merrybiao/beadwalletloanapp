package com.waterelephant.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.baofu.WithHodingResult;
import com.beadwallet.entity.baofu.WithLessRequest;
import com.beadwallet.servcie.BaoFuService;
import com.waterelephant.constants.BaofuConstant;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * 支付控制层
 * 
 * 
 * Module:
 * 
 * AppPayController.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/baofu")
public class BaoFuWithholdController {
	private Logger logger = Logger.getLogger(BaoFuWithholdController.class);
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;

	/**
	 * 宝付扣款接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("debit.do")
	@ResponseBody
	public String withHold(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String[] phones = { "15956695623xxx" };
		String[] amounts = { "8200000" };
		String[] orders = { "1231231231" };
		BwBorrower queryBorrower = new BwBorrower();
		String success = "";
		String processing = "";
		String fail = "";
		String error = "";
		for (int i = 0; i < phones.length; i++) {
			try {
				queryBorrower.setPhone(phones[i]);
				BwBorrower borrower = bwBorrowerService.findBwBorrowerByAttr(queryBorrower);
				String name = borrower.getName();
				String idCard = borrower.getIdCard();
				String phone = borrower.getPhone();
				BwBankCard card = bwBankCardService.findBwBankCardByBoorwerId(borrower.getId());
				String cardNo = card.getCardNo();
				String bankCode = card.getBankCode();
				logger.info("========================宝付付款入参====持卡人姓名=========" + name);
				logger.info("========================宝付付款入参====身份证号=============" + idCard);
				logger.info("========================宝付付款入参====银行预留手机号=============" + phone);
				logger.info("========================宝付付款入参====卡号=============" + cardNo);
				logger.info("========================宝付付款入参====银行编码=============" + bankCode);
				logger.info("========================宝付付款入参====扣款金额=============" + amounts[i]);
				String withholdRemark = "广群扣款," + name + ",工单ID" + orders[i] + ",amount:" + amounts[i];
				String mchnt_txn_ssn = "gqdk" + orders[i];
				logger.info("========================宝付付款入参====生成序号=============" + mchnt_txn_ssn);
				WithLessRequest withLess = new WithLessRequest();
				withLess.setAcc_no(cardNo);// 卡号
				withLess.setId_card(idCard);// 身份证号
				withLess.setId_holder(name);// 持卡人姓名
				withLess.setMobile(phone);// 银行预留手机号
				String bank_code = BaofuConstant.convertFuiouBankCodeToBaofu(bankCode);
				withLess.setPay_code(bank_code);// 银行编码
				// withLess.setTxn_amt("1");// 测试交易金额,分
				withLess.setTxn_amt(amounts[i]);// 交易金额,分
				withLess.setRepayId(mchnt_txn_ssn);// 还款计划id
				withLess.setOrderNo(mchnt_txn_ssn);
				WithHodingResult res = BaoFuService.withHold(withLess);
				String respCode = res.getResp_code();
				// 处理成功
				if ("0000".equals(respCode)) {
					logger.info(withholdRemark + ",广群宝付扣款成功");
					success += "广群扣款成功工单Id" + orders[i] + ",";
				} else if (!CommUtils.isNull(respCode) && SystemConstant.baofuCode.contains(respCode)) {
					logger.info(withholdRemark + ",广群宝付处理中，返回消息：[" + respCode + "]" + res.getResp_msg());
					processing += "广群扣款处理中工单Id" + orders[i] + ",";
				} else {
					logger.error(withholdRemark + ",广群宝付扣款失败，返回消息：[" + respCode + "]" + res.getResp_msg());
					fail += "广群扣款失败工单Id" + orders[i] + ",";
				}
			} catch (Exception e) {
				logger.error("广群扣款异常手机号" + phones[i], e);
				error += "广群扣款异常手机号" + phones[i] + ",";
			}
		}
		json.put("success", success);
		json.put("processing", processing);
		json.put("fail", fail);
		json.put("error", error);
		logger.info("宝付扣款呵呵哒" + json);
		return json.toString();
	}

	public static void main(String[] args) {
		String[] orders = { "3186668", "3188998", "3168156", "3169718", "3160763", "3174212", "1411776", "2921692",
				"3187489", "3163693", "3162457", "3082207", "3188665", "3167598", "3193878", "3024079", "3167817",
				"3168060", "3164267", "3106825", "3163990", "3041736", "3165805", "3054408", "3164992", "3155444",
				"3166457", "3160982", "3170095", "3121501", "3169249", "3179226", "3166913", "3112644", "3187499",
				"3166088", "2952528", "3108258", "3167468", "3170219", "3154394", "3162264", "3056047", "1954896",
				"3187848", "3149617", "3188176", "3164944", "3167375", "3161446", "1802577", "1635105", "3040975",
				"3186038", "3179947", "3166652", "3163978", "3160349", "3129475", "3127109", "3168017", "3168104",
				"3166206", "2744557", "3007396", "3165873", "2404421", "3157301", "3162405", "3180627", "3143524",
				"3167203", "3080432", "3134942", "3165468", "3167381", "3166604", "3071101", "2757308", "3169852",
				"3169311", "3177154", "3167063", "3163882", "3073747", "3146160", "3162660", "3169004", "3167297",
				"3093035", "3158761", "3159201", "3092364", "3188182", "1449372", "3160965", "3202665", "2935227",
				"3110278", "3163663", "3166486", "3172522", "3162591", "3167190", "3172479", "3168206", "3167242",
				"2161448", "3164256", "3106910", "3186813", "3168918", "3155348", "3160638", "3164476", "3088015",
				"3168359", "3162427", "3184882", "2952103", "3162111", "3169396", "2321018", "3174370", "3176529",
				"3031129", "3169358", "3188215", "3165018", "3161438", "3097116", "3192481", "3150108", "3161099",
				"3137158", "3163776", "3192284", "2010623", "3193704", "3168797", "2566315", "3188254", "3167838",
				"3187421", "3138429", "3167155", "3161595", "2956492", "3153803", "3106522", "3165808", "3186210",
				"3155426", "3187342", "3169371", "3166758", "3175067", "3162534", "3186737", "3177043", "2951201",
				"3186949", "3179457", "3162621", "3167315", "1679756", "3189003", "3142694", "3167388", "3089193",
				"3180616", "3186888", "3166680", "3155304", "3178515", "3161306", "3060271", "3169254", "3167433",
				"3133914", "2860752", "3189583", "3166623", "3123681", "3165993", "3148894", "3166332", "315194 ",
				"3169414", "3169233", "3168287", "3164367", "3166254", "2902697", "3169631", "3161238", "3161914",
				"3076551", "3000562", "3168601", "3187286", "2940323", "3149098", "3168652", "3164940", "3167721",
				"3151483", "3165839", "3168986", "2693655", "3092635", "3027798", "2435285", "3167160", "3153408",
				"3153965", "3161300", "446911 ", "3188872", "3168732", "242160 ", "3197171", "3161005", "3168241",
				"3169841", "3010021", "3168500", "3072477", "2636544", "3161619", "3024953", "3166412", "3187612",
				"3168516", "3105365", "2984217", "3168209", "1404358", "3186538", "3150096", "3169752", "2995246",
				"3183449", "3168307", "3168852", "3161337", "3169193", "3166768", "3007430", "3165725", "3178027",
				"3167065", "3162783", "3180338", "3186916", "2942608", "3189686", "1855739", "2853239", "3191196",
				"3187788", "3169581", "3162278", "3149437", "3192347", "2446894", "3149248", "3200812", "3182400",
				"3169879", "3186548", "3178187", "3194294", "3166611", "3167659", "3168927", "848875 ", "2983251",
				"3153671", "3075181", "3168961", "3167500", "3162493", "3160774", "3098496", "2819237", "3018707",
				"3188126", "3168518", "3160300", "3187636", "3167699", "3194919", "3162477", "3199589", "3164140",
				"3143724", "3167221", "3164103", "3160568", "3187228", "3163987", "3188482", "2994523", "3162826",
				"3172245", "3168520", "3186608", "3159994", "3167760", "2986388", "3160496", "3181812", "3167020",
				"3188396", "3156413", "3167748", "3167749", "3110527", "2643169", "3106574", "3153614", "3188192",
				"2850873", "3168687", "3087959", "3151695", "3154617", "3141046", "2769757", "2848447", "3167765",
				"3169709" };
		String success = "3186668,3188998,3168156,3169718,3160763,1411776,3163693,3162457,3193878,3024079,3041736,3155444,3170095,3169249,3166913,3112644,3167468,3170219,3154394,3162264,3187848,3149617,3164944,3167375,3161446,3040975,3186038,3179947,3166652,3163978,3160349,3129475,3168104,3166206,3180627,3167203,3167381,3177154,3167063,3163882,3073747,3146160,3167297,3188182,2935227,3163663,3162591,3172479,2161448,3106910,3155348,3160638,3164476,3168359,3162427,2952103,2321018,3174370,3169358,3161438,3097116,3192481,3161099,3163776,2010623,3188254,3167838,3167155,2956492,3165808,3155426,3169371,3175067,3186737,3177043,2951201,3162621,3189003,3142694,3089193,3186888,3161306,3169254,3167433,3133914,2860752,3189583,3123681,3165993,3166332,315194 ,3169233,3168287,3166254,2902697,3161238,3000562,3168601,3149098,3168652,3167721,3168986,2693655,3153965,3161005,3168241,3169841,2636544,3161619,3187612,3105365,2984217,3168209,3186538,3150096,3168307,3161337,3169193,3007430,3165725,3178027,3167065,3162783,2853239,3191196,3187788,3169581,3162278,3149437,3149248,3182400,3169879,3186548,3194294,3168927,848875 ,3075181,3168961,2819237,3018707,3168518,3160300,3187636,3167699,3194919,3143724,3187228,3168520,3160496,3181812,3167020,3156413,3167748,3167749,2643169,3106574,3151695,3154617,2848447,3167765,3169709 ";
		for (String s : orders) {
			if (success.contains(s)) {
				System.out.println("成功");
			} else {
				System.out.println("失败");
			}
		}
	}

}