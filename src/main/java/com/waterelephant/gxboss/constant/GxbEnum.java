package com.waterelephant.gxboss.constant;

public enum GxbEnum {
	
	SHEBAO(5,"SHEBAO"),
	
	SURPLUS(6,"SURPLUS"),
	
	MEITUAN(14,"MEITUAN"),
	
	DIDI(13,"DIDI"),
	
	ALIPAY(12,"ALIPAY");
	
	private Integer key;
	
	private String name;
	
	private GxbEnum(int key, String name) {
		this.key = key;
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static Integer verifyChannel(String name) {
	    for (GxbEnum val : GxbEnum.values()) {
		     if(val.getName().equals(name)) {
				return val.getKey();
		     }
	    }
	         return -1;
	}
}
