package com.hhh.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.hhh.core.util.FundPage;

public interface ISysUcenterPermissionService {

	public FundPage<Map<String, Object>> listSysUcenterPermission(Map<String, Object> params, PageRequest page);
	
	public List<Map<String, Object>> listSysUcenterPermission(Map<String, Object> params);
}
