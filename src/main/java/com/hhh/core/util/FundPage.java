package com.hhh.core.util;

import java.util.List;

/**
 * 分页
 * @author 3hhjj
 *
 * @param <T>
 */
public class FundPage<T> {

	
	private int totalPages;
	
	private long totalElements;
	
	private List<T> content;
	
	public FundPage(int pages, long total, List<T> list){
		this.totalPages = pages;
		this.totalElements = total;
		this.content = list;
	}
	
	/**
	 * 总记录条数
	 * @return
	 */
	public long getTotalElements(){
		return this.totalElements;
	}
	
	/**
	 * 总页数
	 * @return
	 */
	public int getTotalPages(){
		return this.totalPages;
	}
	
	public List<T> getContent(){
		return content;
	}

}
