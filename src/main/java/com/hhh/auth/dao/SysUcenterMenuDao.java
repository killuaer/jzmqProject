package com.hhh.auth.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.entity.SysUcenterMenu;
import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Repository
@Transactional
public class SysUcenterMenuDao extends BaseDao {
	
	public List<SysUcenterMenu> listMenu(Map<String, Object> param) {
		int i = 1;
		StringBuilder hql = new StringBuilder();
		List<Object> paramList = new ArrayList<>();
		hql.append("FROM SysUcenterMenu m WHERE 1=1");
		if (!StringUtil.isEmpty(param.get("ids"))) {
			hql.append(" and m.id in (?"+(i++)+")");
			paramList.add(param.get("ids"));
		}
		if (!StringUtil.isEmpty(param.get("menuLevel"))) {
			hql.append(" and m.menuLevel = ?" + (i++));
			paramList.add(param.get("menuLevel"));
		}
		if (!StringUtil.isEmpty(param.get("menuDisplay"))) {
			hql.append(" and m.menuDisplay = ?" + (i++));
			paramList.add(param.get("menuDisplay"));
		}
		hql.append(" order by m.ord");

		FundPage<SysUcenterMenu> results = queryHqlWithPaging(hql.toString(), paramList, null);
		List<SysUcenterMenu> resultList = results.getContent();
		return resultList;
	}

	
}
