package com.waterelephant.dto;

import java.io.Serializable;

public class BjuiDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2241641141526532310L;
	private String statusCode;
	private String message;
	private String tabid;
	private Boolean closeCurrent;
	private String forward;
	private String forwardConfirm;
	private String filename;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTabid() {
		return tabid;
	}

	public void setTabid(String tabid) {
		this.tabid = tabid;
	}

	public Boolean getCloseCurrent() {
		return closeCurrent;
	}

	public void setCloseCurrent(Boolean closeCurrent) {
		this.closeCurrent = closeCurrent;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getForwardConfirm() {
		return forwardConfirm;
	}

	public void setForwardConfirm(String forwardConfirm) {
		this.forwardConfirm = forwardConfirm;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}