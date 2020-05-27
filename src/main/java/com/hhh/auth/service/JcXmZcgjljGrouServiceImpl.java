package com.hhh.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hhh.auth.dao.JcXmZcgjljGrouDao;
import com.hhh.auth.entity.JcXmZcgjljGrou;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Service
public class JcXmZcgjljGrouServiceImpl  implements JcXmZcgjljGrouService{

	@Autowired
	private JcXmZcgjljGrouDao jcXmGrouDao;
    
	@Override
	public List<JcXmZcgjljGrou> listJcxm(Map<String, Object> paramMap, PageRequest page) {
		FundPage<JcXmZcgjljGrou> fund = this.jcXmGrouDao.listJcxm(paramMap, page);
		List<JcXmZcgjljGrou> jsonObjs = new ArrayList<JcXmZcgjljGrou>();
		for (JcXmZcgjljGrou entity : fund.getContent()) {
			jsonObjs.add(entity);
		}
		return jsonObjs;
	}
    
	@Override
	public JcXmZcgjljGrou save(JcXmZcgjljGrou entity) {
		return this.jcXmGrouDao.saveOrUpdate(entity);
	}
	
	public JcXmZcgjljGrou getById(String id) {
		JcXmZcgjljGrou entity =  new JcXmZcgjljGrou();
		entity = this.jcXmGrouDao.findById(JcXmZcgjljGrou.class, id);
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
		JcXmZcgjljGrou account = this.jcXmGrouDao.findById(JcXmZcgjljGrou.class, Id);

		this.jcXmGrouDao.delete(account);
		map.put("status", "success");
		return map;
	}

}
