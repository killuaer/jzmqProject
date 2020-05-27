package com.hhh.core.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.hhh.core.util.StringUtil;

public class BaseController {

	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	
	private Map<String, Object> map;
	
	protected PageRequest getPageRequest() {
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		if (StringUtil.isEmpty(pageNum)) {
			pageNum = "0";
		}
		if (StringUtil.isEmpty(pageSize)) {
			pageSize = "10";
		}
		return new PageRequest(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<String, Object> getParamMap () {
		map = new HashMap<>();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	}
}
