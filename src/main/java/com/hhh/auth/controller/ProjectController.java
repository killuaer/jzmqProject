package com.hhh.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.auth.entity.SysUcenterProject;
import com.hhh.auth.service.SysUcenterProjectService;
import com.hhh.core.controller.BaseController;
import com.hhh.core.model.WebServiceConstants;
import com.hhh.core.util.LayPageParam;
import com.hhh.core.util.log.HHHLog;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
	@Autowired
	private SysUcenterProjectService sysProjectService;

	@HHHLog(moduleFirst = "综合管理",moduleSecond = "工程管理", info = "保存工程")
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest req){
		String customerId = WebServiceConstants.CUSTOMERID;
		SysUcenterProject entity = new SysUcenterProject();
		entity.setId(req.getParameter("id"));
		entity.setGcCode(req.getParameter("gcCode"));
		entity.setGcName(req.getParameter("gcName"));
		entity.setGcAddress(req.getParameter("gcAddress"));
		entity.setWtUnit(req.getParameter("wtUnit"));
		entity.setWtMan(req.getParameter("wtMan"));
		entity.setWtManTel(req.getParameter("wtManTel"));
		entity.setJsUnit(req.getParameter("jsUnit"));
		entity.setSjUnit(req.getParameter("sjUnit"));
		entity.setJzUnit(req.getParameter("jzUnit"));
		entity.setJzMan(req.getParameter("jzMan"));
		entity.setJzNum(req.getParameter("jzNum"));
		entity.setCustomerId(customerId);
		Map<String, Object> map = new HashMap<>();
		SysUcenterProject entityProject  = sysProjectService.save(entity);
		if(entityProject!=null){
			map.put("status", "success");
		}else{
			map.put("status", "fail");
		}
		return map;
	}
	
	@HHHLog(moduleFirst = "综合管理",moduleSecond = "工程管理", info = "删除角色")
	@RequestMapping(value = "/del")
	@ResponseBody
	public Map<String, Object> del(String id) {
		Map<String, Object> map = sysProjectService.del(id);
		return map;
	}
	
	@HHHLog(moduleFirst = "综合管理",moduleSecond = "工程管理", info = "编辑单个工程")
	@RequestMapping(value = "/getProject")
	@ResponseBody
	public SysUcenterProject getProject(String id) {
		SysUcenterProject entity = this.sysProjectService.getById(id);
		return entity;
	}
	
	@HHHLog(moduleFirst = "综合管理",moduleSecond = "工程管理", info = "数据列表")
	@RequestMapping(value = "/listProject")
	@ResponseBody
	public Map<String, Object> listRole(HttpServletRequest req, LayPageParam pageParam) {
		String customerId = WebServiceConstants.CUSTOMERID;
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("gcCode", req.getParameter("gcCode"));
		paramMap.put("gcName", req.getParameter("gcName"));
		paramMap.put("wtUnit", req.getParameter("wtUnit"));
		paramMap.put("customerId", req.getParameter(customerId));
		List<SysUcenterProject> list = sysProjectService.listAccount(paramMap, pageReq);
		if (list.size()>0) {
			map.put("count", pageParam.getPage());			
		} else {
			map.put("count", 0);
		}
		map.put("code", 0);
		map.put("msg", "");
		map.put("data", list);
		return map;
	}
	
}
