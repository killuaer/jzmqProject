package com.hhh.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.hhh.auth.entity.JcXmZcgjljGrou;

public interface JcXmZcgjljGrouService {
	public List<JcXmZcgjljGrou> listJcxm(Map<String, Object> paramMap, PageRequest page);

	public JcXmZcgjljGrou save(JcXmZcgjljGrou entity);
	
	public JcXmZcgjljGrou getById(String id);

	public Map<String, Object> del(String id);
	
}
