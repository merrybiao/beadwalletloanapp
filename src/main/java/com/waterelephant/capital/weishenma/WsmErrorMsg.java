package com.waterelephant.capital.weishenma;

import java.util.HashMap;
import java.util.Map;

public class WsmErrorMsg {
	public static Map<String, String> map = new HashMap<>();
	static{
		map.put("1000","商户秘钥串校验未通过");
		map.put("1001", "数字签名校验未通过");
		map.put("1002","绑定校验未通过");
		map.put("1003", "支付明细校验未通过");
		map.put("1004", "绑定范围校验未通过");
		map.put("1005","对公开户行相关信息非空校验未通过");
		map.put("1006", "解密校验未通过");
		map.put("1007","必填校验未通过");
		map.put("1008", "字段规则校验未通过");
		map.put("1009", "日期校验未通过");
		map.put("1010","类型校验未通过");
		map.put("1011", "依赖必填校验未通过");
		map.put("1012","字段长度校验未通过");
		map.put("1013", "营业时间校验未通过");
		
		map.put("1014", "A类黑名单校验未通过");
		map.put("1015","银行卡支持校验未通过(暂时停用)");
		map.put("1016", "商户订单号重复校验未通过");
		map.put("1017","单量校验未通过");
		map.put("1018", "资金方匹配失败");
		map.put("1019", "还款计划生成失败");
		map.put("1020", "签约失败");
		
		map.put("1021","支付失败");
		map.put("1022", "商户订单号不存在");
		map.put("1023","处理中");
		map.put("1024", "借贷人年龄校验未通过");
		map.put("1025", "资产ip地址不符合设置的ip地址");
		map.put("1026", "开户行编号不在系统可用银行列表中");
		map.put("1027","第三方支付成功后银行支付处理失败");
		map.put("1028", "订单查询过频");
		map.put("1029","保证金金额校验未通过");
		map.put("1030", "当日金额校验未通过");
	}
}
