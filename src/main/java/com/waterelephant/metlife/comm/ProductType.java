/**
 * 
 */
package com.waterelephant.metlife.comm;

/**
 * @author dinglinaho
 *
 */
public enum ProductType {
	
	BJ_QUFENQI("1001"),//北京-去分期
	WH_SXFQ("1002"),//武汉-水象分期
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
