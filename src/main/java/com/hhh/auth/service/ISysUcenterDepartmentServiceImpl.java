package com.hhh.auth.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterDepartmentDao;
import com.hhh.auth.entity.SysUcenterDepartment;
import com.hhh.core.service.BaseService;
import com.hhh.core.service.ISysUcenterDepartmentService;
import com.hhh.core.util.StringUtil;

@Service
@Transactional
public class ISysUcenterDepartmentServiceImpl extends BaseService  implements ISysUcenterDepartmentService{

	@Autowired
	private SysUcenterDepartmentDao sysDepartmentDao;

	@Override
	public Map<String, Object> getById(String id) {
		SysUcenterDepartment entity = this.sysDepartmentDao.findById(SysUcenterDepartment.class, id);
		if (entity != null) {
			Map<String, Object> map = StringUtil.object2Map(entity);
			return map;
		}
		return null;
	}

	
}
