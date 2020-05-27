package com.hhh.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterProjectDao;
import com.hhh.auth.entity.SysUcenterProject;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Service
@Transactional
public class SysUcenterProjectServiceImpl  implements SysUcenterProjectService{

	@Autowired
	private SysUcenterProjectDao sysProjectDao;
    
	@Override
	public List<SysUcenterProject> listAccount(Map<String, Object> paramMap, PageRequest page) {
		FundPage<SysUcenterProject> fund = this.sysProjectDao.listAccount(paramMap, page);
		List<SysUcenterProject> jsonObjs = new ArrayList<SysUcenterProject>();
		for (SysUcenterProject entity : fund.getContent()) {
			jsonObjs.add(entity);
		}
		return jsonObjs;
	}
    
	@Override
	public SysUcenterProject save(SysUcenterProject entity) {
		return this.sysProjectDao.saveOrUpdate(entity);
	}
	
	public SysUcenterProject getById(String id) {
		SysUcenterProject entity =  new SysUcenterProject();
		entity = this.sysProjectDao.findById(SysUcenterProject.class, id);
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
		SysUcenterProject account = this.sysProjectDao.findById(SysUcenterProject.class, Id);

		this.sysProjectDao.delete(account);
		map.put("status", "success");
		return map;
	}

}
