package com.waterelephant.yixin.dto.result;

/**
 * 返回宜信的返回值
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月15日 下午4:57:08
 */
public class YixinDto {

	private String tx;
	
	private String version;
	
	private YixinDataDto data;

	public String getTx() {
		return tx;
	}

	public void setTx(String tx) {
		this.tx = tx;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public YixinDataDto getData() {
		return data;
	}

	public void setData(YixinDataDto data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "YixinDto [tx=" + tx + ", version=" + version + ", data=" + data + "]";
	}
	
	
}
