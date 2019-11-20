package com.waterelephant.drainage.entity.youyu;

/**
 * 
 * @ClassName: YouyuResponse 
 * @Description: 用于有鱼响应 
 * @author He Longyun 
 * @date 2017年12月8日 上午10:18:18 
 *
 */
public class YouyuResponse {
	
	public static String CODE_SUCCESS = "1"; // 1:成功 其他：失败
	public static String CODE_FAIL = "10001"; // 内部错误，接口调用失败
	private String  code;//状态码
	private String desc;//描述
	private Object data;//业务数据
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	

}
