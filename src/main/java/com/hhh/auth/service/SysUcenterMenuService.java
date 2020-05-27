package com.hhh.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterMenuDao;
import com.hhh.auth.entity.SysUcenterMenu;
import com.hhh.core.service.BaseService;

@Service
@Transactional
public class SysUcenterMenuService extends BaseService {

	@Autowired
	private SysUcenterMenuDao sysMenuDao;

	public List<SysUcenterMenu> listMenu(Map<String, Object> param) {
		return sysMenuDao.listMenu(param);
	}

}
