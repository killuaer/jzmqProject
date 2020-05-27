package com.hhh.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhh.auth.dao.JcXmcsInfoDao;
import com.hhh.auth.entity.JcXmcsInfo;
import com.hhh.core.util.StringUtil;

@Service
public class JcXmcsInfoServiceImpl  implements JcXmcsInfoService{

	@Autowired
	private JcXmcsInfoDao jcXmcsDao;
    
	@Override
	public JcXmcsInfo save(JcXmcsInfo entity) {
		return this.jcXmcsDao.save(entity);
	}
	
	public JcXmcsInfo getById(String id) {
		JcXmcsInfo entity =  new JcXmcsInfo();
		entity = this.jcXmcsDao.findOne(id);
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
		JcXmcsInfo account = this.jcXmcsDao.findOne(Id);

		this.jcXmcsDao.delete(account);
		map.put("status", "success");
		return map;
	}
	
	@Override
    public List<JcXmcsInfo> findAll(){
		List<JcXmcsInfo> list = new ArrayList<JcXmcsInfo>();
		list = this.jcXmcsDao.findAlllist();
		return list;
	}
	
	@Override
    public List<JcXmcsInfo> findByParentId(String pid){
		List<JcXmcsInfo> list = new ArrayList<JcXmcsInfo>();
		list = this.jcXmcsDao.findByParentId(pid);
		return list;
	}
	
}
