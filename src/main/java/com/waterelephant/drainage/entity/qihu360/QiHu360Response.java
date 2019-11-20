package com.waterelephant.drainage.entity.qihu360;

public class QiHu360Response {
	public static final int CODE_SUCCESS = 200; // 接口调用成功
	public static final int CODE_FAILURE = 100; // 接口调用失败
	public static final int CODE_DUPLICATECALL = 101; // 重复调用
	public static final int CODE_PARAMETER = 103; // 参数错误
	public static final int CODE_NOTLOAN = 400; // 不可申请

	private int code;// 类型码
	private String msg;// 类型码对应信息
	private Object data;// 响应数据
	private String reason;// C001：已经在对方有进行中的贷款 , C002：在对方有不良贷款记录, C003：30 天内被机构审批拒绝过

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
