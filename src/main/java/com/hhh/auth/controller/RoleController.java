package com.hhh.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.auth.entity.SysUcenterAccount;
import com.hhh.auth.entity.SysUcenterRole;
import com.hhh.auth.service.SysUcenterRoleService;
import com.hhh.core.controller.BaseController;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.LayPageParam;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.StringUtil;
import com.hhh.core.util.log.HHHLog;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	@Autowired
	private SysUcenterRoleService roleService;

	@HHHLog(moduleFirst = "系统管理",moduleSecond = "角色管理", info = "保存角色")
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.roleService.save(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "角色管理", info = "删除角色")
	@RequestMapping(value = "/del")
	@ResponseBody
	public Map<String, Object> del() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.roleService.del(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "给用户授权角色")
	@RequestMapping(value = "/grantAccounts")
	@ResponseBody
	public Map<String, Object> grantRoles() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.roleService.grantAccounts(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "角色管理", info = "给角色授权菜单")
	@RequestMapping(value = "/grantMenus")
	@ResponseBody
	public Map<String, Object> grantMenus() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.roleService.grantMenus(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "角色管理", info = "获取角色列表")
	@RequestMapping(value = "/listRole")
	@ResponseBody
	public Map<String, Object> listRole(HttpServletRequest req, LayPageParam pageParam) {
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", req.getParameter("name"));
		FundPage<Map<String, Object>> fundPage = this.roleService.listRole(paramMap, pageReq);
		List<Map<String, Object>> list = new ArrayList<>();
		if (fundPage != null && fundPage.getContent() != null) {
			map.put("count", (int) fundPage.getTotalElements());
			list = fundPage.getContent();
		} else {
			map.put("count", 0);
		}
		map.put("code", 0);
		map.put("msg", "");
		map.put("data", list);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "角色管理", info = "获取菜单树")
	@RequestMapping(value = "/treeMenu")
	@ResponseBody
	public List<Map<String, Object>> treeMenu() {
		Map<String, Object> param = this.getParamMap();
		param.put("customerId", ShiroUtils.getCustomerId());
		Map<String, Object> map = this.roleService.treeMenu(param);
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(map);
		return list;
	}
	
	@RequestMapping(value = "/getRole")
	@ResponseBody
	public Map<String, Object> getRole(String id) {
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		SysUcenterRole role = this.roleService.findById(SysUcenterRole.class, id);
		if (role != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", role.getId());
			map.put("name", role.getName());
			map.put("descr", role.getDescr());
			Set<SysUcenterAccount> users = role.getSysUcenterAccounts();
			if (users != null && users.size() > 0) {
				List<String> ids = new ArrayList<>();
				List<String> names = new ArrayList<>();
				for (SysUcenterAccount user : users) {
					ids.add(user.getId());
					names.add(user.getName());
				}
				map.put("accountIds", StringUtil.join(ids));
				map.put("accounutNames", StringUtil.join(names));
			}
			return map;
		}
		return null;
	}
	
}
