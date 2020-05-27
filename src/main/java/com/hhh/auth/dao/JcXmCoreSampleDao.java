package com.hhh.auth.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.entity.JcXmCoreSample;
import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Repository
@Transactional
public class JcXmCoreSampleDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FundPage<JcXmCoreSample> listJcxm(Map paramMap, PageRequest page) {
		StringBuilder hql = new StringBuilder();
		List paramList = new ArrayList();
		hql.append(" from JcXmCoreSample a ");
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
		if (!StringUtil.isEmpty(paramMap.get("gcAddress"))) {
			hql.append(" and a.gcAddress like ? ");
			paramList.add("%" + paramMap.get("gcAddress") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("customerId"))) {
			hql.append(" and a.customerId = ? ");
			paramList.add(paramMap.get("customerId"));
		}

		return super.queryHqlWithPaging(hql.toString(), paramList, page);
	}
}
