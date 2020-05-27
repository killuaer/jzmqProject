package com.hhh.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hhh.auth.dao.JcXmGrouDao;
import com.hhh.auth.entity.JcXmGrou;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Service
public class JcXmGrouServiceImpl  implements JcXmGrouService{

	@Autowired
	private JcXmGrouDao jcXmGrouDao;
    
	@Override
	public List<JcXmGrou> listJcxm(Map<String, Object> paramMap, PageRequest page) {
		FundPage<JcXmGrou> fund = this.jcXmGrouDao.listJcxm(paramMap, page);
		List<JcXmGrou> jsonObjs = new ArrayList<JcXmGrou>();
		for (JcXmGrou entity : fund.getContent()) {
			jsonObjs.add(entity);
		}
		return jsonObjs;
	}
    
	@Override
	public JcXmGrou save(JcXmGrou entity) {
		return this.jcXmGrouDao.saveOrUpdate(entity);
	}
	
	public JcXmGrou getById(String id) {
		JcXmGrou entity =  new JcXmGrou();
		entity = this.jcXmGrouDao.findById(JcXmGrou.class, id);
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
		JcXmGrou account = this.jcXmGrouDao.findById(JcXmGrou.class, Id);

		this.jcXmGrouDao.delete(account);
		map.put("status", "success");
		return map;
	}

}
