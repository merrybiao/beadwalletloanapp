package com.waterelephant.utils.mongo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MgCollectionUtil {
	// 1、初始化时创建链接
	public static MongoClient mongoClient;
	public static DBCollection dbCollection;
	
	private static Logger logger = LoggerFactory.getLogger(MgCollectionUtil.class);
	
	// 2、链接池
	public static MongoClient getMongoClient() {
		try {
			if(!StringUtils.isEmpty(MongDBConstant.URI)) {
				logger.info("----mongoUrl:{}---",MongDBConstant.URI);
				mongoClient = new MongoClient(new MongoClientURI(MongDBConstant.URI));
			}else {
				if(StringUtils.isEmpty(MongDBConstant.NAME) && StringUtils.isEmpty(MongDBConstant.PASSWORD)) {
					mongoClient = new MongoClient(MongDBConstant.IP, Integer.parseInt(MongDBConstant.PORT));
				}else {
					String hostStr = MongDBConstant.IP;
					ServerAddress serverAddrs = new ServerAddress(hostStr,Integer.parseInt(MongDBConstant.PORT));
					
					MongoCredential credential = MongoCredential.createScramSha1Credential(MongDBConstant.NAME, MongDBConstant.DATABASE, MongDBConstant.PASSWORD.toCharArray());
					List<MongoCredential> credentails = new ArrayList<MongoCredential>();
					credentails.add(credential);
					mongoClient = new MongoClient(serverAddrs, credentails);
				}
			}
		}catch (Exception e) {
			logger.error("--------------创建mongo客户端失败------------------");
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
			DB db = mongoClient.getDB(MongDBConstant.DATABASE);
			dbCollection = db.getCollection(MongDBConstant.TABLE);
		}
		return dbCollection;
	}
}
