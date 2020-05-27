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
import com.hhh.system.entity.JcXtszSystemSetting;

@Repository
@Transactional
public class JcXtszSystemSettingDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FundPage<JcXtszSystemSetting> listJcXtszSystemSetting(Map paramMap, PageRequest page) {
		StringBuilder hql = new StringBuilder();
		List paramList = new ArrayList();
		hql.append(" from JcXtszSystemSetting a ");
		hql.append(" where 1=1 ");
		if (!StringUtil.isEmpty(paramMap.get("customerId"))) {
			hql.append(" and a.customerId = ? ");
			paramList.add(paramMap.get("customerId"));
		}
		
		return super.queryHqlWithPaging(hql.toString(), paramList, page);
	}
	
}
