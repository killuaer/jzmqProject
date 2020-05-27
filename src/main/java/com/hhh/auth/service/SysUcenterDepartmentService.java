package com.hhh.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterDepartmentDao;
import com.hhh.auth.entity.SysUcenterDepartment;
import com.hhh.auth.entity.SysUcenterPermission;
import com.hhh.auth.entity.SysUcenterUserDepartment;
import com.hhh.core.service.BaseService;
import com.hhh.core.util.DateUtil;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.StringUtil;

@Service
@Transactional
public class SysUcenterDepartmentService extends BaseService {

	@Autowired
	private SysUcenterDepartmentDao sysDepartmentDao;

	public FundPage<Map<String, Object>> listDept(Map<String, Object> paramMap, PageRequest page) {
		FundPage<SysUcenterDepartment> fund = this.sysDepartmentDao.listDepartment(paramMap, page);
		List<Map<String, Object>> jsonObjs = new ArrayList<>();
		for (SysUcenterDepartment entity : fund.getContent()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", entity.getId());
			map.put("name", entity.getName());
			jsonObjs.add(map);
		}
		return new FundPage<Map<String, Object>>(fund.getTotalPages(), fund.getTotalElements(), jsonObjs);
	}

	public Map<String, Object> save(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		SysUcenterDepartment dept = null;
		if (StringUtil.isEmpty(paramMap.get("name"))) {
			map.put("status", "fail");
			map.put("msg", "name不能为空");
			return map;
		}
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			if (StringUtil.isEmpty(paramMap.get("parentId"))) {
				map.put("status", "fail");
				map.put("msg", "parentId不能为空");
				return map;
			}
			dept = new SysUcenterDepartment();
			dept.setCustomerId(ShiroUtils.getCustomerId());
			dept.setName((String)paramMap.get("name"));
			dept.setParentId((String)paramMap.get("parentId"));
			dept.setOrd(Integer.valueOf((String)paramMap.get("ord")));
			dept.setCode((String)paramMap.get("code"));
			dept.setType(Integer.valueOf((String)paramMap.get("type")));
			dept.setCreateTime(DateUtil.getCurrentDateTime());
			dept.setCreatorId(ShiroUtils.getUserId());
		} else {
			
			String id = (String)paramMap.get("id");
			dept = this.sysDepartmentDao.findById(SysUcenterDepartment.class, id);
			dept.setName((String)paramMap.get("name"));
			dept.setOrd(Integer.valueOf((String)paramMap.get("ord")));
			dept.setCode((String)paramMap.get("code"));
			dept.setType(Integer.valueOf((String)paramMap.get("type")));
			dept.setUpdateTime(DateUtil.getCurrentDateTime());
			dept.setUpdateUserId(ShiroUtils.getUserId());
		}
		this.sysDepartmentDao.saveOrUpdate(dept);
		map.put("status", "success");
		return map;
	}
	
	public Map<String, Object> del(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		String id = (String)paramMap.get("id");
		if (StringUtil.isEmpty(id)) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		List<SysUcenterDepartment> dList = this.sysDepartmentDao.findListByParams(SysUcenterDepartment.class, "parentId", id);
		if (dList != null && dList.size() > 0) {
			map.put("status", "fail");
			map.put("msg", "目标部门还存在子部门，不能删除");
			return map;
		}
		List<SysUcenterUserDepartment> udList = this.sysDepartmentDao.findListByParams(SysUcenterUserDepartment.class, "departId", id);
		if (udList != null && udList.size() > 0) {
			map.put("status", "fail");
			map.put("msg", "目标部门还存在用户，不能删除");
			return map;
		}
		SysUcenterDepartment dept = this.sysDepartmentDao.findById(SysUcenterDepartment.class, id);
		this.sysDepartmentDao.delete(dept);
		map.put("status", "success");
		return map;
	}
	
	//xtree
	public Map<String, Object> xtreeDepart(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		String roleId = (String)paramMap.get("id");
		String customerId = (String)paramMap.get("customerId");
		SysUcenterDepartment topDepart = this.sysDepartmentDao.findByParams(SysUcenterDepartment.class, "customerId", customerId, "parentId", "0");
		map.put("value", topDepart.getId());
		map.put("title", topDepart.getName());
		map.put("checked", false);
		//站点
		List<SysUcenterPermission> myPermList = this.sysDepartmentDao.findListByParams(SysUcenterPermission.class, "roleId", roleId, "resType", 1);
		//部门
		List<SysUcenterPermission> myPermList2 = this.sysDepartmentDao.findListByParams(SysUcenterPermission.class, "roleId", roleId, "resType", 2);
		myPermList.addAll(myPermList2);
		for (SysUcenterPermission perm : myPermList) {
			if (topDepart.getId().equals(perm.getResourcesId())) {
				map.put("checked", true);
				break;
			}
		}
		List<SysUcenterDepartment> allDepartList = this.sysDepartmentDao.findListByParams(SysUcenterDepartment.class,"customerId", customerId);
		
		List<Map<String, Object>> child = treeCheckedDepart(topDepart.getId(), allDepartList, myPermList);
		if (child.size() > 0) {
			map.put("data", child);
		}
		map.put("status", "success");
		return map;
	}
	
	private List<Map<String, Object>> treeCheckedDepart(String menuId, List<SysUcenterDepartment> allDepartList, List<SysUcenterPermission> myPermList) {
		List<Map<String, Object>> nodelist = new ArrayList<>();
		for (int i = 0; i < allDepartList.size(); i++) {
			SysUcenterDepartment menu = allDepartList.get(i);
			if (menu.getParentId().equals(menuId)) {
				Map<String, Object> subnode = new HashMap<>();
				subnode.put("value", menu.getId());
				subnode.put("title", menu.getName());
				List<Map<String, Object>> childList = treeCheckedDepart(menu.getId(), allDepartList, myPermList);
				if (childList.size() > 0) {
					subnode.put("data", childList);
				} else {
					for (SysUcenterPermission ssr : myPermList) {
						if (menu.getId().equals(ssr.getResourcesId())) {
							subnode.put("checked", true);
							break;
						} else {
							subnode.put("checked", false);
						}
					}
				}
				nodelist.add(subnode);
			}
		}

		return nodelist;
	}
	
	public Map<String, Object> move(String id, String type) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(id)) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		SysUcenterDepartment jxb = this.sysDepartmentDao.findById(SysUcenterDepartment.class, id);
		int ord = jxb.getOrd();
		String parentId = jxb.getParentId();
		List<Map<String, Object>> list = this.sysDepartmentDao.findListByParamsOrderBy(SysUcenterDepartment.class, null, "ord", false, "parentId", parentId);
		int index = 0;
		for (int i = 0; i< list.size(); i++) {
			Map<String, Object> val = list.get(i);
			if (id.equals(val.get("id"))) {
				index = i;
				break;
			}
		}
		//上移
		Map<String, Object> val = null;
		if ("up".equals(type)) {
			if (index > 0) {
				val = list.get(index-1);
			}
		} else {
			if (index < list.size()-1) {
				val = list.get(index+1);
			}
		}
		
		try {
			if (val != null) {
				SysUcenterDepartment jxb2 = (SysUcenterDepartment)StringUtil.map2Object(val, SysUcenterDepartment.class);
				jxb.setOrd(jxb2.getOrd());
				jxb2.setOrd(ord);
				this.sysDepartmentDao.saveOrUpdate(jxb);
				this.sysDepartmentDao.saveOrUpdate(jxb2);
			}
		} catch (Exception e) {
			map.put("status", "fail");
			map.put("msg", e.getMessage());
			e.printStackTrace();
		}
		map.put("status", "success");
		return map;
	}
	
	public Map<String, Object> treeDept(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		String customerId = (String)paramMap.get("customerId");
		List<Map<String, Object>> departments = this.sysDepartmentDao.findListByParamsOrderBy(SysUcenterDepartment.class, null, "ord", false, "customerId", customerId);
		for (Map<String, Object> cate : departments) {
			if ("0".equals(cate.get("parentId"))) {
				map.put("id", cate.get("id"));
				map.put("name", cate.get("name"));
				map.put("spread", true);
			}
		}
		findNextDepartment(map, departments);
		return map;
	}
	
	private List<Map<String, Object>> findNextDepartment(Map<String, Object> node, List<Map<String, Object>> departments) {
		List<Map<String, Object>> sublist =  new ArrayList<>();
		for (Map<String, Object> cate : departments) {
			if (node.get("id").equals(cate.get("parentId"))) {
				sublist.add(cate);
			}
		}
		List<Map<String, Object>> nodelist = new ArrayList<>();
		if(sublist != null && sublist.size()>0){
			for(Map<String, Object> org : sublist){
				Map<String, Object> subnode = new HashMap<>();
				subnode.put("id", org.get("id"));
				subnode.put("name", org.get("name"));
				nodelist.add(subnode);
				findNextDepartment(subnode, departments);
			}
			node.put("children", nodelist);
		}
		return nodelist;
	}
}
