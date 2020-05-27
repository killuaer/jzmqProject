package com.hhh.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterNumManageDao;
import com.hhh.auth.entity.SysUcenterNumManage;

@Service
@Transactional
public class SysUcenterNumManageServiceImpl  implements SysUcenterNumManageService{

	@Autowired
	private SysUcenterNumManageDao dao;
    
	@Override
	public SysUcenterNumManage save(SysUcenterNumManage entity) {
		return this.dao.save(entity);
	}
	
	public SysUcenterNumManage getById(String id) {
		SysUcenterNumManage entity =  new SysUcenterNumManage();
		entity = this.dao.findOne(id);
		return entity;
	}
	
	@Override
    public SysUcenterNumManage findBybhId(String bhId){
		SysUcenterNumManage entity = new SysUcenterNumManage();
		entity = this.dao.findBybhId(bhId);
		return entity;
	}
	
}
