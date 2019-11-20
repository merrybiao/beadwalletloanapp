package com.waterelephant.sms.util;

import java.util.ResourceBundle;

public class MgdbConstant {
	public static String IP = "";
	public static String PORT = "";
	public static String DATABASE = "";
	public static String TABLE = "";
	public static String CONNECTIONS_PER_HOST = "";
	public static String CONNECTION_MULTIPLIER = "";
	public static String NAME = "";
	public static String PASSWORD = "";
	
	static {
		ResourceBundle mgdb_config = ResourceBundle.getBundle("mgdb");
		MgdbConstant.IP = mgdb_config.getString("ip");
		MgdbConstant.PORT = mgdb_config.getString("port");
		MgdbConstant.DATABASE = mgdb_config.getString("database");
		MgdbConstant.TABLE = mgdb_config.getString("table");
		MgdbConstant.CONNECTIONS_PER_HOST = mgdb_config.getString("connections_per_host");
		MgdbConstant.CONNECTION_MULTIPLIER = mgdb_config.getString("connection_multiplier");
		MgdbConstant.NAME = mgdb_config.getString("name");
		MgdbConstant.PASSWORD = mgdb_config.getString("password");
	}
}
