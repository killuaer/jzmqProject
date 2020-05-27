package com.hhh.auth.service;

import java.util.List;
import java.util.Map;

import com.hhh.auth.entity.JcXmcsInfo;

public interface JcXmcsInfoService {

	public JcXmcsInfo save(JcXmcsInfo entity);
	
	public JcXmcsInfo getById(String id);

	public Map<String, Object> del(String id);
	
	public List<JcXmcsInfo> findAll();
	
	public List<JcXmcsInfo> findByParentId(String pid);
}
