package com.hhh.system.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;
import com.hhh.system.entity.JcXtszCodeDefine;

@Repository
@Transactional
public class JcXtszCodeDefineDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;

	
	public FundPage<JcXtszCodeDefine> listJcXtszCodeDefine(Map<String, Object> paramMap, PageRequest page) {
		StringBuilder hql = new StringBuilder();
		List<Object> paramList = new ArrayList<>();
		int i = 1;
		hql.append(" from JcXtszCodeDefine a where  1=1 ");
		
		if (!StringUtil.isEmpty(paramMap.get("customerId"))) {
			hql.append(" and a.customerId = ?" + (i++));
			paramList.add(paramMap.get("customerId"));
		}
		if (!StringUtil.isEmpty(paramMap.get("name"))) {
			hql.append(" and a.name like ?" + (i++));
			paramList.add("%" + paramMap.get("name") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("type"))) {
			hql.append(" and a.type = ?" + (i++));
			paramList.add(paramMap.get("type"));
		}
		return queryHqlWithPaging(hql.toString(), paramList, page);
	}
	
	
}
