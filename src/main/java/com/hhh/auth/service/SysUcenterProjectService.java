package com.hhh.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.hhh.auth.entity.SysUcenterProject;

public interface SysUcenterProjectService {
	public List<SysUcenterProject> listAccount(Map<String, Object> paramMap, PageRequest page);

	public SysUcenterProject save(SysUcenterProject entity);
	
	public SysUcenterProject getById(String id);

	public Map<String, Object> del(String id);
	
}
