package com.hhh.auth.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.entity.SysUcenterRole;
import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Repository
@Transactional
public class SysUcenterRoleDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FundPage<SysUcenterRole> listRole(Map paramMap, PageRequest page) {
		StringBuilder hql = new StringBuilder();
		List paramList = new ArrayList();
		hql.append(" from SysUcenterRole a ");
		hql.append(" where 1=1 ");
		if (!StringUtil.isEmpty(paramMap.get("name"))) {
			hql.append(" and a.name like ? ");
			paramList.add("%" + paramMap.get("name") + "%");
		}
		
		return super.queryHqlWithPaging(hql.toString(), paramList, page);
	}
	
}
