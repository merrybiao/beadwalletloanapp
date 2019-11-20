package com.waterelephant.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 获取动态数据源
 * @author dinglinhao
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	 
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHolderManager.get();
	}
}
   