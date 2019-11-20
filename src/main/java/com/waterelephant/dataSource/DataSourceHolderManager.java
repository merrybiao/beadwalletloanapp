package com.waterelephant.dataSource;
/**
 * 数据源持有者管理
 * 默认master
 * @author dinglinhao
 *
 */
public class DataSourceHolderManager {

	 private static final ThreadLocal<DataSource> dataSourceHolder = new ThreadLocal<DataSource>(){
         @Override
         protected DataSource initialValue(){
             return DataSource.MASTER;
         }
     };
     
     public static DataSource get(){
         return dataSourceHolder.get();
     }
     
     public static void set(DataSource dataSource){
    	 dataSourceHolder.set(dataSource);
     }
     
     public static void reset(){
    	 dataSourceHolder.set(DataSource.MASTER);
     }
     
     public static void remove(){
    	 dataSourceHolder.remove();
     }
}
