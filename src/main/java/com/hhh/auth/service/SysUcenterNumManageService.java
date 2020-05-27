package com.hhh.auth.service;

import com.hhh.auth.entity.SysUcenterNumManage;

public interface SysUcenterNumManageService {

	public SysUcenterNumManage save(SysUcenterNumManage entity);
	
	public SysUcenterNumManage getById(String id);

	public SysUcenterNumManage findBybhId(String bhId);
}
