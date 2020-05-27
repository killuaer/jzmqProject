package com.hhh.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterRoleDao;
import com.hhh.auth.entity.SysUcenterAccount;
import com.hhh.auth.entity.SysUcenterMenu;
import com.hhh.auth.entity.SysUcenterPermission;
import com.hhh.auth.entity.SysUcenterRole;
import com.hhh.core.service.BaseService;
import com.hhh.core.util.DateUtil;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.StringUtil;

@Service
@Transactional
public class SysUcenterRoleService extends BaseService {

	@Autowired
	private SysUcenterRoleDao sysRoleDao;

	public FundPage<Map<String, Object>> listRole(Map<String, Object> paramMap, PageRequest page) {
		FundPage<SysUcenterRole> fund = this.sysRoleDao.listRole(paramMap, page);
		List<Map<String, Object>> jsonObjs = new ArrayList<>();
		for (SysUcenterRole entity : fund.getContent()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", entity.getId());
			map.put("name", entity.getName());
			map.put("descr", entity.getDescr());
			jsonObjs.add(map);
		}
		return new FundPage<Map<String, Object>>(fund.getTotalPages(), fund.getTotalElements(), jsonObjs);
	}

	public Map<String, Object> save(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		SysUcenterRole role = null;
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			role = new SysUcenterRole();
			role.setName((String)paramMap.get("name"));
			role.setDescr((String)paramMap.get("descr"));
			role.setCustomerId(ShiroUtils.getCustomerId());
			role.setCreateTime(DateUtil.getCurrentDateTime());
			role.setCreatorId(ShiroUtils.getUserId());
		} else {
			String id = (String)paramMap.get("id");
			role = this.sysRoleDao.findById(SysUcenterRole.class, id);
			role.setName((String)paramMap.get("name"));
			role.setDescr((String)paramMap.get("descr"));
			role.setUpdateTime(DateUtil.getCurrentDateTime());
			role.setUpdateUserId(ShiroUtils.getUserId());
		}
		this.sysRoleDao.saveOrUpdate(role);
		map.put("status", "success");
		return map;
	}
	
	public Map<String, Object> del(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		SysUcenterRole role = this.sysRoleDao.findById(SysUcenterRole.class, (String)paramMap.get("id"));
		Set<SysUcenterAccount> accountList = role.getSysUcenterAccounts();
		if (accountList != null && accountList.size() > 0) {
			map.put("status", "fail");
			map.put("msg", "已有用户绑定，不能删除");
			return map;
		}
		this.sysRoleDao.delete(role);
		
		//删除绑定的菜单
		List<SysUcenterPermission> list = this.sysRoleDao.findListByParams(SysUcenterPermission.class, "roleId", paramMap.get("id"));
		if (list != null && list.size() > 0) {
			for (SysUcenterPermission per : list) {
				this.sysRoleDao.delete(per);
			}
		}
		map.put("status", "success");
		return map;
	}
	
	public Map<String, Object> grantAccounts(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		if (StringUtil.isEmpty(paramMap.get("accountIds"))) {
			map.put("status", "fail");
			map.put("msg", "accountIds不能为空");
			return map;
		}
		SysUcenterRole role = this.sysRoleDao.findById(SysUcenterRole.class, (String)paramMap.get("id"));
		String accountIds = (String)paramMap.get("accountIds");
		String[] uids = accountIds.split(",");
		List<SysUcenterAccount> accountList = this.sysRoleDao.findByIds(SysUcenterAccount.class, Arrays.asList(uids));
		if (accountList != null && accountList.size() > 0) {
			for (SysUcenterAccount account : accountList) {
				account.getSysUcenterRoles().add(role);
			}
			this.sysRoleDao.saveOrUpdates(accountList);
		}
		map.put("status", "success");
		return map;
	}
	
	public Map<String, Object> grantMenus(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		if (StringUtil.isEmpty(paramMap.get("menuIds"))) {
			map.put("status", "fail");
			map.put("msg", "menuIds不能为空");
			return map;
		}
		String menuIds = (String)paramMap.get("menuIds");
		String[] uids = menuIds.split(",");
		Set<String> menuIdSets = new HashSet<>(Arrays.asList(uids));
		List<SysUcenterPermission> opList = this.sysRoleDao.findListByParams(SysUcenterPermission.class, "roleId", paramMap.get("id"), "resType", 0);
		if (opList != null && opList.size() > 0) {
			for (SysUcenterPermission op : opList) {
				this.sysRoleDao.delete(op);
			}
		}
		for (String menuId : menuIdSets) {
			if ("0".equals(menuId)) { // 顶级菜单
				menuId = "417de36265d94ee49191885602d17159";
			}
			SysUcenterPermission op = new SysUcenterPermission();
			op.setEnable(0);
			op.setResType(0);
			op.setRoleId((String)paramMap.get("id"));
			op.setResourcesId(menuId);
			this.sysRoleDao.saveOrUpdate(op);
		}
		map.put("status", "success");
		return map;
	}
	
	//xtree
	public Map<String, Object> treeMenu(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		String roleId = (String)paramMap.get("id");
		String customerId = (String)paramMap.get("customerId");
		map.put("value", "0");
		map.put("title", "菜单");
		map.put("checked", false);
		List<SysUcenterPermission> myPermList = this.sysRoleDao.findListByParams(SysUcenterPermission.class, "roleId", roleId, "resType", 0);
		String menuId = null;
		List<SysUcenterMenu> allMenuList = this.sysRoleDao.findListByParams(SysUcenterMenu.class,"customerId", customerId);
		for (SysUcenterMenu menu : allMenuList) {
			if ("0".equals(menu.getParentId())) {
				menuId = menu.getId();
				break;
			}
		}
		List<Map<String, Object>> child = treeCheckedMenu(menuId, allMenuList, myPermList);
		if (child.size() > 0) {
			map.put("data", child);
		}
		map.put("status", "success");
		return map;
	}
	
	private List<Map<String, Object>> treeCheckedMenu(String menuId, List<SysUcenterMenu> allMenuList, List<SysUcenterPermission> myPermList) {
		List<Map<String, Object>> nodelist = new ArrayList<>();
		for (int i = 0; i < allMenuList.size(); i++) {
			SysUcenterMenu menu = allMenuList.get(i);
			if (menu.getParentId().equals(menuId)) {
				Map<String, Object> subnode = new HashMap<>();
				subnode.put("value", menu.getId());
				subnode.put("title", menu.getName());
				List<Map<String, Object>> childList = treeCheckedMenu(menu.getId(), allMenuList, myPermList);
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
	
}
