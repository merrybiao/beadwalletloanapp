package com.waterelephant.dto;

import java.io.Serializable;

public class HistorOrderDto implements Serializable{

	private static final long serialVersionUID = 8653420256203500426L;
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String statusId;
	/**
	 * 
	 */
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
