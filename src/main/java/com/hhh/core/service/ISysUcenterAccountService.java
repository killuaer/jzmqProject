package com.hhh.core.service;

import java.util.List;
import java.util.Map;

public interface ISysUcenterAccountService {

	public Map<String, Object> getById(String id);
	
	public Map<String, Map<String, Object>> findMapByIds(List<String> ids);
}
