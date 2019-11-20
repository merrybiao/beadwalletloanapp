package com.waterelephant.gxboss.service;

public interface GxbOssServicce {
	
	public Long queryBwBorrowerCreditByCreditNo(String creditNo);
	
	public String queryBwCreditURLByRelationId(Long relationId,Integer relationType,Integer creditType);
	
	public Long queryBwOrderIdByCreditNo(String creditNo);
	

}
