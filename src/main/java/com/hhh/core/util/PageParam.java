package com.hhh.core.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class PageParam {

	private int page = 1;// 页数

	private int rows = 10;// 每页记录数

	private String sort;// 排序字段

	private String order = "asc";// 排序类型

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Sort getPageSort() {
		if (sort != null && !sort.equals("")) {
			if (order.equals("desc")) {
				return new Sort(new Order(Direction.DESC, "id"));
			} else {
				return new Sort(new Order(Direction.ASC, "id"));
			}
		} else {
			return null;
		}
	}

	public PageRequest getPageRequest() {
		if (this.sort != null && !this.sort.equals("")) {
			if (this.order.equals("desc")) {
				return new PageRequest(this.page - 1, this.rows, new Sort(new Order(Direction.DESC, this.sort)));
			} else {
				return new PageRequest(this.page - 1, this.rows, new Sort(new Order(Direction.ASC, this.sort)));
			}
		} else {
			return new PageRequest(this.page - 1, this.rows);
		}
	}

}
