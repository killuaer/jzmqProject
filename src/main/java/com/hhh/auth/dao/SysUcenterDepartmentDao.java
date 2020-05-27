package com.hhh.auth.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.entity.SysUcenterDepartment;
import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Repository
@Transactional
public class SysUcenterDepartmentDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	public FundPage<SysUcenterDepartment> listDepartment(Map paramMap,  PageRequest pageReq) {
		StringBuilder hql = new StringBuilder();
		List paramList = new ArrayList<>();
		hql.append("FROM SysUcenterDepartment r WHERE 1=1");
		if (!StringUtil.isEmpty(paramMap.get("name"))) {
			hql.append(" and r.name like ? ");
			paramList.add("%" + paramMap.get("name") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("deptId"))) {
			hql.append(" and (r.parentId = ? or r.id = ?)");
			paramList.add(paramMap.get("deptId"));
			paramList.add(paramMap.get("deptId"));
		}
		if (!StringUtil.isEmpty(paramMap.get("parentId"))) {
			hql.append(" and r.parentId = ? ");
			paramList.add(paramMap.get("parentId"));
		}
		return  queryHqlWithPaging(hql.toString(), paramList, pageReq);
	}
}
