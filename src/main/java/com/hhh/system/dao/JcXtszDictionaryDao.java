package com.hhh.system.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;
import com.hhh.system.entity.JcXtszDictionary;

@Repository
@Transactional
public class JcXtszDictionaryDao extends BaseDao {

	public FundPage<JcXtszDictionary> listDict(Map<String, Object> paramMap, PageRequest pageReq) {
		List<Object> paramList = new ArrayList<>();
		StringBuilder hql = new StringBuilder();
		hql.append("FROM JcXtszDictionary a WHERE 1=1");
		if (!StringUtil.isEmpty(paramMap.get("type"))) {
			hql.append(" and a.type like ? ");
			paramList.add("%" + paramMap.get("type") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("name"))) {
			hql.append(" and a.name like ? ");
			paramList.add("%" + paramMap.get("name") + "%");
		}
		if (!StringUtil.isEmpty(paramMap.get("parentId"))) {
			hql.append(" and a.parentId = ? ");
			paramList.add(paramMap.get("parentId"));
		}
		hql.append(" order by a.ord");
		return queryHqlWithPaging(hql.toString(), paramList, pageReq);
	}
}
