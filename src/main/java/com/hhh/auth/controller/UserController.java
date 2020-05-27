package com.hhh.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.auth.entity.SysUcenterAccount;
import com.hhh.auth.entity.SysUcenterMenu;
import com.hhh.auth.entity.SysUcenterPermission;
import com.hhh.auth.entity.SysUcenterRole;
import com.hhh.auth.entity.SysUcenterUserrole;
import com.hhh.auth.service.SysUcenterAccountService;
import com.hhh.auth.service.SysUcenterMenuService;
import com.hhh.auth.service.SysUcenterPermissionService;
import com.hhh.core.controller.BaseController;
import com.hhh.core.model.Whether;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.LayPageParam;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.StringUtil;
import com.hhh.core.util.log.HHHLog;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private SysUcenterPermissionService permissionService;
	@Autowired
	private SysUcenterMenuService menuService;
	@Autowired
	private SysUcenterAccountService accountService;
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "保存用户")
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.accountService.save(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "删除用户")
	@RequestMapping(value = "/del")
	@ResponseBody
	public Map<String, Object> del() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.accountService.del(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "用户管理", info = "重置用户密码")
	@RequestMapping(value = "/resetPsd")
	@ResponseBody
	public Map<String, Object> resetPsd() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.accountService.resetPsd(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "给用户授权角色")
	@RequestMapping(value = "/grantRoles")
	@ResponseBody
	public Map<String, Object> grantRoles() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.accountService.grantRoles(param);
		return map;
	}

	@HHHLog(moduleFirst = "系统管理",moduleSecond = "用户管理", info = "获取用户列表")
	@RequestMapping(value = "/listAccount")
	@ResponseBody
	public Map<String, Object> listAccount(HttpServletRequest req, LayPageParam pageParam) {
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("deptId", req.getParameter("deptId"));
		paramMap.put("name", req.getParameter("name"));
		paramMap.put("loginName", req.getParameter("loginName"));
		FundPage<Map<String, Object>> fundPage = this.accountService.listAccount(paramMap, pageReq);
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
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getRestMenu", method = RequestMethod.GET)
	public Map<String, Object> getFirstLevelMenu(String parentId) {
		Map<String, Object> parentBean = new HashMap<>();
		if (StringUtil.isEmpty(parentId)) {
			return null;
		}
		SysUcenterMenu parent = this.accountService.findById(SysUcenterMenu.class, parentId);
		if (null == parent) {
			return null;
		}
		parentBean = StringUtil.object2Map(parent);
		List<SysUcenterMenu> list = null;
		if (ShiroUtils.getUser().get("isAdmin").equals(Whether.Yes)) {
			List<Map<String, Object>> list1 = accountService.findListByParamsOrderBy(SysUcenterMenu.class, null, "ord", false, "customerId", ShiroUtils.getCustomerId(),"menuDisplay", 0);
			list = new ArrayList<>();
			for (Map<String, Object> map : list1) {
				try {
					SysUcenterMenu sm = (SysUcenterMenu)StringUtil.map2Object(map, SysUcenterMenu.class);
					list.add(sm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			String accountId = ShiroUtils.getUserId();
			List<SysUcenterUserrole> roles = this.accountService.findListByParams(SysUcenterUserrole.class, "accountId", accountId);
			if (roles != null && roles.size() > 0) {
				Set<String> roleIds = new HashSet<>();
				for (SysUcenterUserrole ur : roles) {
					roleIds.add(ur.getRoleId());
				}
				Map<String, Object> param = new HashMap<>();
				param.put("roleIds", roleIds);
				param.put("resType", 0);
				List<SysUcenterPermission> permList = permissionService.listPermission(param);
				if (permList != null && permList.size() > 0) {
					Set<String> resoIds = new HashSet<>();
					for (SysUcenterPermission perm : permList) {
						resoIds.add(perm.getResourcesId());
					}
					param.clear();
					param.put("ids", resoIds);
					list = menuService.listMenu(param);
				}
			}
		}
		List<Map<String, Object>> results = new ArrayList<>();
		Map<String, Map<String, Object>> map = new HashMap<>();
		for (SysUcenterMenu menu : list) {
			if (menu.getMenuDisplay() == 0) {
				Map<String, Object> menuBean = StringUtil.object2Map(menu);
				map.put(menu.getId(), menuBean);
			}
		}
		Map<String, Object> menuBean = null;
		for (SysUcenterMenu menu : list) {
			if (menu.getMenuDisplay() == 0) {
				menuBean = map.get(menu.getId());
				if (menu.getMenuLevel() == 2 && menu.getParentId().equals(parentId)) {
					results.add(menuBean);
				} else if (menu.getMenuLevel() > 2) {
					Map<String, Object> parentMenu = map.get(menu.getParentId());
					if (parentMenu != null) {
						if ("1".equals((Integer)parentMenu.get("haschild"))) parentMenu.put("subMenu", new ArrayList<Map<String, Object>>());
						if (StringUtil.isEmpty(parentMenu.get("subMenu")))  parentMenu.put("subMenu", new ArrayList<Map<String, Object>>());
						((List<Map<String, Object>>)parentMenu.get("subMenu")).add(menuBean);
					}
				}
			}
		}
		parentBean.put("subMenu", results);
		return parentBean;
	}
	/**
	 * 取登录用户的菜单（只拿一级菜单）
	 * 
	 * @return
	 */
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "用户管理", info = "获取用户菜单")
	@ResponseBody
	@RequestMapping(value = "/usermenu", method = RequestMethod.GET)
	public List<Map<String, Object>> getUserMenu(HttpSession session) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("menuLevel", 1);
		param.put("menuDisplay", 0);
		if (ShiroUtils.getUser().get("isAdmin").equals(Whether.Yes)) {
			
		} else {
			String accountId = ShiroUtils.getUserId();
			List<SysUcenterUserrole> roles = this.accountService.findListByParams(SysUcenterUserrole.class, "accountId", accountId);
			if (roles != null && roles.size() > 0) {
				Set<String> roleIds = new HashSet<>();
				for (SysUcenterUserrole role : roles) {
					roleIds.add(role.getRoleId());
				}
				param.put("roleIds", roleIds);
				param.put("resType", 0);
				List<SysUcenterPermission> permList = permissionService.listPermission(param);
				if (permList != null && permList.size() > 0) {
					Set<String> resoIds = new HashSet<>();
					for (SysUcenterPermission perm : permList) {
						resoIds.add(perm.getResourcesId());
					}
					param.put("ids", resoIds);
				}
			}
		}
		List<SysUcenterMenu> list = menuService.listMenu(param);
		List<Map<String, Object>> results = new ArrayList<>();
		for (SysUcenterMenu menu : list) {
			results.add(StringUtil.object2Map(menu));
		}
		return results;
	}
	
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public Map<String, Object> getUser(String id) {
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		SysUcenterAccount user = this.accountService.findById(SysUcenterAccount.class, id);
		if (user != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", user.getId());
			map.put("name", user.getName());
			map.put("loginName", user.getLoginName());
			map.put("password", user.getPassword());
			map.put("phone", user.getPhone());
			map.put("email", user.getEmail());
			Set<SysUcenterRole> roles = user.getSysUcenterRoles();
			if (roles != null && roles.size() > 0) {
				List<String> ids = new ArrayList<>();
				List<String> names = new ArrayList<>();
				for (SysUcenterRole role : roles) {
					ids.add(role.getId());
					names.add(role.getName());
				}
				map.put("roleIds", StringUtil.join(ids));
				map.put("roleNames", StringUtil.join(names));
			}
			return map;
		}
		return null;
	}
}
