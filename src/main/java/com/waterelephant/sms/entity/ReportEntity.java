package com.waterelephant.sms.entity;

public class ReportEntity {
	
	private String srctermid;
	private String submitDate;
	private String receiveDate;
	private String addSerial;
	private String addSerialRev;
	private String state;
	private String seqid;
	
	public ReportEntity(){
		
	}
	
	public ReportEntity(String srctermid, String submitDate, String receiveDate, String addSerial, String addSerialRev,
			String state, String seqid) {
		this.srctermid = srctermid;
		this.submitDate = submitDate;
		this.receiveDate = receiveDate;
		this.addSerial = addSerial;
		this.addSerialRev = addSerialRev;
		this.state = state;
		this.seqid = seqid;
	}

	public String getSrctermid() {
		return srctermid;
	}

	public void setSrctermid(String srctermid) {
		this.srctermid = srctermid;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getAddSerial() {
		return addSerial;
	}

	public void setAddSerial(String addSerial) {
		this.addSerial = addSerial;
	}

	public String getAddSerialRev() {
		return addSerialRev;
	}

	public void setAddSerialRev(String addSerialRev) {
		this.addSerialRev = addSerialRev;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSeqid() {
		return seqid;
	}

	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}
	
	
}
