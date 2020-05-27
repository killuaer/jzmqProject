package com.hhh.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.hhh.auth.entity.JcXmGrou;

public interface JcXmGrouService {
	public List<JcXmGrou> listJcxm(Map<String, Object> paramMap, PageRequest page);

	public JcXmGrou save(JcXmGrou entity);
	
	public JcXmGrou getById(String id);

	public Map<String, Object> del(String id);
	
}
