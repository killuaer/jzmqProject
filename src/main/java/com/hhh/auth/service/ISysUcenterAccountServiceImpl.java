package com.hhh.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterDepartmentDao;
import com.hhh.auth.entity.SysUcenterAccount;
import com.hhh.core.service.BaseService;
import com.hhh.core.service.ISysUcenterAccountService;
import com.hhh.core.util.StringUtil;

@Service
@Transactional
public class ISysUcenterAccountServiceImpl extends BaseService  implements ISysUcenterAccountService{

	@Autowired
	private SysUcenterDepartmentDao sysDepartmentDao;

	@Override
	public Map<String, Object> getById(String id) {
		SysUcenterAccount entity = this.sysDepartmentDao.findById(SysUcenterAccount.class, id);
		if (entity != null) {
			Map<String, Object> map = StringUtil.object2Map(entity);
			return map;
		}
		return null;
	}

	@Override
	public Map<String, Map<String, Object>> findMapByIds(List<String> ids) {
		Map<String, SysUcenterAccount> accountMap = this.sysDepartmentDao.findMapByIds(SysUcenterAccount.class, ids);
		Map<String, Map<String, Object>> map = new HashMap<>();
		for (Map.Entry<String, SysUcenterAccount> entry : accountMap.entrySet()) { 
			map.put(entry.getKey(), StringUtil.object2Map(entry.getValue()));
		}
		return map;
	}

	
}
