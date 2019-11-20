/**
 * 
 */
package com.waterelephant.utils;

/**
 * @author dinglinaho
 *
 */
public enum ProductType {
	
	BJ_QUFENQI("1001"),//北京-去分期
	WH_SXFQ("1002"),//武汉-水象分期
	SAAS("1003"),//SAAS-陈洋
	BJ_SXYP("1004"),//北京-水象优品-崔红亮
	WH_CHANNEL("1005"),//武汉-渠道-李鹏
	SAAS_SZQB("1006");//SAAS-水珠钱包 刘道道 甘立思
	;
	
	private String productNo;
	
	private ProductType(String productNo){
		this.productNo = productNo;
	}

	public String getProductNo() {
		return productNo;
	}
	
	public static ProductType get(String productNo) {
		ProductType[] array = ProductType.values();
		for(ProductType type : array) {
			if(type.productNo.equals(productNo)) return type;
		}
		return null;
	}
	
}
