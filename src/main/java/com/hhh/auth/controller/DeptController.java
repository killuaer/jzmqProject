package com.hhh.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.auth.entity.SysUcenterDepartment;
import com.hhh.auth.service.SysUcenterDepartmentService;
import com.hhh.core.controller.BaseController;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.LayPageParam;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.StringUtil;
import com.hhh.core.util.log.HHHLog;

@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {
	@Autowired
	private SysUcenterDepartmentService deptService;

	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "保存部门/站点")
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.deptService.save(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "删除部门/站点")
	@RequestMapping(value = "/del")
	@ResponseBody
	public Map<String, Object> del() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.deptService.del(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "获取部门树")
	@RequestMapping(value = "/treeDept")
	@ResponseBody
	public List<Map<String, Object>> treeDept(HttpServletRequest req) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("customerId", ShiroUtils.getCustomerId());
		paramMap.put("userId", ShiroUtils.getUserId());
		Map<String, Object> map = this.deptService.treeDept(paramMap);
		list.add(map);
		return list;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "获取部门列表")
	@RequestMapping(value = "/listDept")
	@ResponseBody
	public Map<String, Object> listDept(HttpServletRequest req, LayPageParam pageParam) {
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("deptId", req.getParameter("deptId"));
		FundPage<Map<String, Object>> fundPage = this.deptService.listDept(paramMap, pageReq);
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
	
	@RequestMapping(value = "/getDept")
	@ResponseBody
	public Map<String, Object> getDept(String id) {
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		SysUcenterDepartment dept = this.deptService.findById(SysUcenterDepartment.class, id);
		if (dept != null) {
			Map<String, Object> map = StringUtil.object2Map(dept);
			return map;
		}
		return null;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "获取部门树")
	@RequestMapping(value = "/xtreeDept")
	@ResponseBody
	public List<Map<String, Object>> xtreeDept() {
		Map<String, Object> param = this.getParamMap();
		param.put("customerId", ShiroUtils.getCustomerId());
		Map<String, Object> map = this.deptService.xtreeDepart(param);
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(map);
		return list;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "组织架构", info = "移动部门")
	@RequestMapping(value = "/moveDept")
	@ResponseBody
	public Map<String, Object> moveDept(String id, String type) {
		Map<String, Object> map = this.deptService.move(id, type);
		return map;
	}
}
