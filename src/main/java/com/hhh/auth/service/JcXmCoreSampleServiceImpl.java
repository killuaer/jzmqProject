package com.hhh.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hhh.auth.dao.JcXmCoreSampleDao;
import com.hhh.auth.entity.JcXmCoreSample;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Service
public class JcXmCoreSampleServiceImpl  implements JcXmCoreSampleService{

	@Autowired
	private JcXmCoreSampleDao jcXmCoreDao;
    
	@Override
	public List<JcXmCoreSample> listJcxm(Map<String, Object> paramMap, PageRequest page) {
		FundPage<JcXmCoreSample> fund = this.jcXmCoreDao.listJcxm(paramMap, page);
		List<JcXmCoreSample> jsonObjs = new ArrayList<JcXmCoreSample>();
		for (JcXmCoreSample entity : fund.getContent()) {
			jsonObjs.add(entity);
		}
		return jsonObjs;
	}
    
	@Override
	public JcXmCoreSample save(JcXmCoreSample entity) {
		return this.jcXmCoreDao.saveOrUpdate(entity);
	}
	
	public JcXmCoreSample getById(String id) {
		JcXmCoreSample entity =  new JcXmCoreSample();
		entity = this.jcXmCoreDao.findById(JcXmCoreSample.class, id);
		return entity;
	}
	
	@Override
	public Map<String, Object> del(String id) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(id)) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		String Id = id;
		JcXmCoreSample account = this.jcXmCoreDao.findById(JcXmCoreSample.class, Id);

		this.jcXmCoreDao.delete(account);
		map.put("status", "success");
		return map;
	}

}
