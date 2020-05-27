package com.hhh.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterPermissionDao;
import com.hhh.auth.entity.SysUcenterPermission;
import com.hhh.core.service.ISysUcenterPermissionService;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Service
@Transactional
public class SysUcenterPermissionServiceImpl implements ISysUcenterPermissionService {
	@Autowired
	private SysUcenterPermissionDao sysUcenterPermissionDao;
	
	@Override
	public FundPage<Map<String, Object>> listSysUcenterPermission(Map<String, Object> params, PageRequest page) {
		FundPage<SysUcenterPermission> fund = sysUcenterPermissionDao.listPermission(params, page);
		List<Map<String, Object>> list = new ArrayList<>();
		for (SysUcenterPermission entity : fund.getContent()) {
			list.add(StringUtil.object2Map(entity));
		}
		return new FundPage<>(fund.getTotalPages(), fund.getTotalElements(), list);
	}

	@Override
	public List<Map<String, Object>> listSysUcenterPermission(Map<String, Object> params) {
		return listSysUcenterPermission(params, null).getContent();
	}

}
