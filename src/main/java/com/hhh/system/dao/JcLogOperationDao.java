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
import com.hhh.core.util.DateUtil;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;
import com.hhh.system.entity.JcLogOperation;

@Repository
@Transactional
public class JcLogOperationDao extends BaseDao {

	@PersistenceContext
	private EntityManager entityManager;

	
	public FundPage<JcLogOperation> listJcLogOperation(Map<String, Object> paramMap, PageRequest page) {
		StringBuilder hql = new StringBuilder();
		List<Object> paramList = new ArrayList<>();
		int i = 1;
		hql.append(" from JcLogOperation a where  1=1 ");
		
		if (!StringUtil.isEmpty(paramMap.get("customerId"))) {
			hql.append(" and a.customerId = ?" + (i++));
			paramList.add(paramMap.get("customerId"));
		}
		if (!StringUtil.isEmpty(paramMap.get("type"))) {
			hql.append(" and a.type = ?" + (i++));
			paramList.add(Integer.valueOf(paramMap.get("type").toString()));
		}
		if (!StringUtil.isEmpty(paramMap.get("creatorId"))) {
			hql.append(" and a.creatorId = ?" + (i++));
			paramList.add(paramMap.get("creatorId"));
		}
		if (!StringUtil.isEmpty(paramMap.get("starttime"))) {
			hql.append(" and a.createTime >= ?" + (i++));
			paramList.add(DateUtil.parseDateTime(paramMap.get("starttime") + " 00:00:00"));
		}
		if (!StringUtil.isEmpty(paramMap.get("endtime"))) {
			hql.append(" and a.createTime <= ?" + (i++));
			paramList.add(DateUtil.parseDateTime(paramMap.get("endtime") + " 23:59:59"));
		}
		return queryHqlWithPaging(hql.toString(), paramList, page);
	}
	
	
}
