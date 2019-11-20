package com.waterelephant.utils.mongo;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 亿美常量工具
 * @author dengyan
 *
 */
public class MongDBConstant {
	
	private static Logger logger = LoggerFactory.getLogger(MongDBConstant.class);
	
	public static String URI = "";
	public static String IP = "";
	public static String PORT = "";
	public static String DATABASE = "";
	public static String TABLE = "";
	public static String CONNECTIONS_PER_HOST = "";
	public static String CONNECTION_MULTIPLIER = "";
	public static String NAME = "";
	public static String PASSWORD = "";
	
	
	static {
		try {
			ResourceBundle mgdb_config = ResourceBundle.getBundle("mongodb");
			MongDBConstant.URI = mgdb_config.getString("mongo.uri");
			MongDBConstant.IP = mgdb_config.getString("mongo.host");
			MongDBConstant.PORT = mgdb_config.getString("mongo.port");
			MongDBConstant.DATABASE = mgdb_config.getString("mongo.dbname");
			MongDBConstant.TABLE = mgdb_config.getString("mongo.table");
			MongDBConstant.CONNECTIONS_PER_HOST = mgdb_config.getString("mongo.connectionsPerHost");
			MongDBConstant.CONNECTION_MULTIPLIER = mgdb_config.getString("mongo.connectionMultiplier");
			MongDBConstant.NAME = mgdb_config.getString("mongo.name");
			MongDBConstant.PASSWORD = mgdb_config.getString("mongo.password");
		} catch (Exception e) {
			logger.error("----读取配置文件mongodb.properties失败，异常：{}--",e.getMessage());
			e.printStackTrace();
		}
	}
	
}
