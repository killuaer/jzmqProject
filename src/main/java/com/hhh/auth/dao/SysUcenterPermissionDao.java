package com.hhh.auth.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.entity.SysUcenterPermission;
import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Repository
@Transactional
public class SysUcenterPermissionDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;

	
	public FundPage<SysUcenterPermission> listPermission(Map<String, Object> param, PageRequest page) {
		StringBuilder hql = new StringBuilder();
		int i = 1;
		List<Object> paramList = new ArrayList<>();
		hql.append("FROM SysUcenterPermission p WHERE 1=1");
		if (!StringUtil.isEmpty(param.get("roleId"))) {
			hql.append(" and p.roleId = ?" + i++);
			paramList.add(param.get("roleId"));
		}
		if (!StringUtil.isEmpty(param.get("resType"))) {
			hql.append(" and p.resType = ?" + i++);
			paramList.add(param.get("resType"));
		}
		if (!StringUtil.isEmpty(param.get("roleIds"))) {
			hql.append(" and p.roleId in (?"+(i++)+")");
			paramList.add(param.get("roleIds"));
		}

		FundPage<SysUcenterPermission> results = queryHqlWithPaging(hql.toString(), paramList, page);
		return results;
	}
}
