package com.yun.album.bean;

import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 */
public class PageInfo<T> {
	/** 总记录数 */
	private long total;
	/** 当前页 */
    private int pageNum;
    /** 每页的数量 */
    private int pageSize;
    /** 当前页的数量 */
    private int size;
    /** 总页数 */
    private int pages;
    /** 结果集 */
	private List<T> list;

    public PageInfo(Page<T> page) {
		this.list = new ArrayList<>(page);
		this.total = page.getTotal();
		this.pageNum = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.size = page.size();
		this.pages = page.getPages();
	}
    
    public PageInfo(Page<?> page, List<T> list) {
    	this.list = new ArrayList<>(list);
		this.total = page.getTotal();
		this.pageNum = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.size = page.size();
		this.pages = page.getPages();
    }

	public long getTotal() {
		return total;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getSize() {
		return size;
	}

	public int getPages() {
		return pages;
	}

	public List<T> getList() {
		return list;
	}

}
