package com.hhh.auth.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.entity.SysUcenterAccount;
import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Repository
@Transactional
public class SysUcenterAccountDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FundPage<SysUcenterAccount> listAccount(Map paramMap, PageRequest page) {
		StringBuilder hql = new StringBuilder();
		List paramList = new ArrayList();
		hql.append(" from SysUcenterAccount a ");
		hql.append(" where 1=1 ");
		if (!StringUtil.isEmpty(paramMap.get("name"))) {
			hql.append(" and a.name like ? ");
			paramList.add("%" + paramMap.get("name") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("loginName"))) {
			hql.append(" and a.loginName like ? ");
			paramList.add("%" + paramMap.get("loginName") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("deptId"))) {
			hql.append("and exists (select d.accountId from SysUcenterUserDepartment d where a.id = d.accountId and d.departId=?) ");
			paramList.add(paramMap.get("deptId"));
		}
		if (!StringUtil.isEmpty(paramMap.get("companyId"))) {
			hql.append(" and a.sysUcenterCompany.id = ? ");
			paramList.add(paramMap.get("companyId"));
		}
		if (!StringUtil.isEmpty(paramMap.get("deptName"))) {
			hql.append(" and exists (select 1 from SysUcenterUserDepartment ud,SysUcenterDepartment d where a.id = ud.accountId and ud.departId = d.id and d.name like ?) ");
			paramList.add("%" + paramMap.get("deptName") + "%");
		}
		return super.queryHqlWithPaging(hql.toString(), paramList, page);
	}

	
}
