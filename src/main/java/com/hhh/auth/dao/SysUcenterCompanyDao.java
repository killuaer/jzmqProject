package com.hhh.auth.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.entity.SysUcenterCompany;
import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Repository
@Transactional
public class SysUcenterCompanyDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings({ "rawtypes" })
	public FundPage<SysUcenterCompany> listCompany(Map paramMap, Pageable page) {
		StringBuilder hql = new StringBuilder();
		List<Object> paramList = new ArrayList<>();
		hql.append("FROM SysUcenterCompany a WHERE 1=1");
		if (!StringUtil.isEmpty(paramMap.get("name"))) {
			hql.append(" and a.name like ? ");
			paramList.add("%" + paramMap.get("name") + "%");
		}
		
		FundPage<SysUcenterCompany> results = queryHqlWithPaging(hql.toString(), paramList, page);

		return results;
	}
	
}
