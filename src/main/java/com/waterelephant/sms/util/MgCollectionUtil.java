package com.waterelephant.sms.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * MongoDB 链接池
 * @author dengyan
 *
 */
public class MgCollectionUtil {
	
	// 1、初始化时创建链接
	public static MongoClient mongoClient;
	public static DBCollection dbCollection;
	
	// 2、链接池
	public static MongoClient getMongoClient() {
		try {
			if(StringUtils.isEmpty(MgdbConstant.NAME) && StringUtils.isEmpty(MgdbConstant.PASSWORD)) {
				mongoClient = new MongoClient(MgdbConstant.IP, Integer.parseInt(MgdbConstant.PORT));
			}else {
				String hostStr = MgdbConstant.IP;
				ServerAddress serverAddrs = new ServerAddress(hostStr,Integer.parseInt(MgdbConstant.PORT));
				
				MongoCredential credential = MongoCredential.createScramSha1Credential(MgdbConstant.NAME, MgdbConstant.DATABASE, MgdbConstant.PASSWORD.toCharArray());
				List<MongoCredential> credentails = new ArrayList<MongoCredential>();
				credentails.add(credential);
				mongoClient = new MongoClient(serverAddrs, credentails);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mongoClient;
	}
	
	// 3、获取MongoDB数据库集合
	@SuppressWarnings({ "deprecation" })
	public static DBCollection dbCollection() {
		if (dbCollection == null) {
			if (mongoClient == null) {
				mongoClient = getMongoClient();
			}
			DB db = mongoClient.getDB(MgdbConstant.DATABASE);
			dbCollection = db.getCollection(MgdbConstant.TABLE);
		}
		return dbCollection;
	}
	
//	public static void main(String[] args) {
//		String hostStr = "139.196.254.181";
//		ServerAddress serverAddrs = new ServerAddress(hostStr,Integer.parseInt("3717"));
//		
//		MongoCredential credential = MongoCredential.createScramSha1Credential("root", "admin", "WaterElephant#2016".toCharArray());
//		List<MongoCredential> credentails = new ArrayList<MongoCredential>();
//		credentails.add(credential);
//		MongoClient mongoClient = new MongoClient(serverAddrs, credentails);
//		DB db = mongoClient.getDB("admin");
//		DBCollection dbCollection = db.getCollection("mg_message_info");
//		System.out.println(dbCollection);
//		
//	}
	

	
}
