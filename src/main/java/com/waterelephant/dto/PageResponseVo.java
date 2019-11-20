package com.waterelephant.dto;

import java.io.Serializable;
import java.util.List;

public class PageResponseVo<T> implements Serializable {
    
	private static final long serialVersionUID = 1L;

    public List<T> rows;

    public long total;
    
    private int pageNum;

    private int pageSize;

    public PageResponseVo(long total, List<T> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
    
}
