package com.hhh.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.hhh.auth.entity.JcXmCoreSample;

public interface JcXmCoreSampleService {
	public List<JcXmCoreSample> listJcxm(Map<String, Object> paramMap, PageRequest page);

	public JcXmCoreSample save(JcXmCoreSample entity);
	
	public JcXmCoreSample getById(String id);

	public Map<String, Object> del(String id);
	
}
