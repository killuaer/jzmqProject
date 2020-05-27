package com.hhh.auth.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.entity.JcXmGrou;
import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Repository
@Transactional
public class JcXmGrouDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FundPage<JcXmGrou> listJcxm(Map paramMap, PageRequest page) {
		StringBuilder hql = new StringBuilder();
		List paramList = new ArrayList();
		hql.append(" from JcXmGrou a ");
		hql.append(" where 1=1 ");
		if (!StringUtil.isEmpty(paramMap.get("gcCode"))){
			hql.append(" and a.gcCode like ? ");
			paramList.add("%" + paramMap.get("gcCode") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("gcName"))) {
			hql.append(" and a.gcName like ? ");
			paramList.add("%" + paramMap.get("gcName") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("wtUnit"))) {
			hql.append(" and a.wtUnit like ? ");
			paramList.add("%" + paramMap.get("wtUnit") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("wtNum"))) {
			hql.append(" and a.wtNum like ? ");
			paramList.add("%" + paramMap.get("wtNum") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("phNum"))) {
			hql.append(" and a.phNum like ? ");
			paramList.add("%" + paramMap.get("phNum") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("prtNum"))) {
			hql.append(" and a.prtNum like ? ");
			paramList.add("%" + paramMap.get("prtNum") + "%");
		}

		if (!StringUtil.isEmpty(paramMap.get("wtId"))) {
			hql.append(" and a.wtId = ? ");
			paramList.add(paramMap.get("wtId"));
		}
		if (!StringUtil.isEmpty(paramMap.get("customerId"))) {
			hql.append(" and a.customerId = ? ");
			paramList.add(paramMap.get("customerId"));
		}

		return super.queryHqlWithPaging(hql.toString(), paramList, page);
	}
}
