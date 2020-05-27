package com.hhh.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterPermissionDao;
import com.hhh.auth.entity.SysUcenterPermission;
import com.hhh.core.service.BaseService;
import com.hhh.core.util.FundPage;

@Service
@Transactional
public class SysUcenterPermissionService extends BaseService {

	@Autowired
	private SysUcenterPermissionDao sysPermissionDao;

	public List<SysUcenterPermission> listPermission(Map<String, Object> param) {
		FundPage<SysUcenterPermission> fund = sysPermissionDao.listPermission(param, null);
		List<SysUcenterPermission> list = fund.getContent();
		return list;
	}

}
