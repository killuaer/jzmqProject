package com.hhh.system.controller;

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

import com.hhh.core.controller.BaseController;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.LayPageParam;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.log.HHHLog;
import com.hhh.system.service.LogService;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
	@Autowired
	private LogService logService;

	@HHHLog(moduleFirst = "系统管理",moduleSecond = "日志", info = "获取运行日志列表")
	@RequestMapping(value = "/listJcLogRun")
	@ResponseBody
	public Map<String, Object> listJcLogRun(HttpServletRequest req, LayPageParam pageParam) {
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("customerId", ShiroUtils.getCustomerId());
		paramMap.put("type", req.getParameter("type"));
		paramMap.put("starttime", req.getParameter("starttime"));
		paramMap.put("endtime", req.getParameter("endtime"));
		FundPage<Map<String, Object>> fundPage = this.logService.listJcLogRun(paramMap, pageReq);
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
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "日志", info = "获取操作日志列表")
	@RequestMapping(value = "/listJcLogOperation")
	@ResponseBody
	public Map<String, Object> listJcLogOperation(HttpServletRequest req, LayPageParam pageParam) {
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("customerId", ShiroUtils.getCustomerId());
		paramMap.put("starttime", req.getParameter("starttime"));
		paramMap.put("endtime", req.getParameter("endtime"));
		FundPage<Map<String, Object>> fundPage = this.logService.listJcLogOperation(paramMap, pageReq);
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
}
