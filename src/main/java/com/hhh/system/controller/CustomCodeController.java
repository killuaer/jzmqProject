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
import com.hhh.core.util.StringUtil;
import com.hhh.core.util.log.HHHLog;
import com.hhh.system.entity.JcXtszCodeDefine;
import com.hhh.system.service.JcXtszCodeDefineService;

@Controller
@RequestMapping("/customCode")
public class CustomCodeController extends BaseController {
	@Autowired
	private JcXtszCodeDefineService jcXtcsCodingdefService;

	@HHHLog(moduleFirst = "系统管理",moduleSecond = "自定编号", info = "保存编号")
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save() {
		Map<String, Object> param = this.getParamMap();
		param.put("customerId", ShiroUtils.getCustomerId());
		param.put("userId", ShiroUtils.getUserId());
		Map<String, Object> map = this.jcXtcsCodingdefService.save(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "自定编号", info = "删除编号")
	@RequestMapping(value = "/del")
	@ResponseBody
	public Map<String, Object> del() {
		Map<String, Object> param = this.getParamMap();
		Map<String, Object> map = this.jcXtcsCodingdefService.del(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "自定编号", info = "获取编号列表")
	@RequestMapping(value = "/listJcXtszCodeDefine")
	@ResponseBody
	public Map<String, Object> listJcXtszCodeDefine(HttpServletRequest req, LayPageParam pageParam) {
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("customerId", ShiroUtils.getCustomerId());
		paramMap.put("name", req.getParameter("name"));
		paramMap.put("type", req.getParameter("type"));
		FundPage<Map<String, Object>> fundPage = this.jcXtcsCodingdefService.listJcXtszCodeDefine(paramMap, pageReq);
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
	
	@RequestMapping(value = "/getJcXtszCodeDefine")
	@ResponseBody
	public Map<String, Object> getJcXtszCodeDefine(String id) {
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		JcXtszCodeDefine entity = this.jcXtcsCodingdefService.findById(JcXtszCodeDefine.class, id);
		Map<String, Object> map = new HashMap<>();
		if (entity != null) {
			map = StringUtil.object2Map(entity);
		}
		return map;
	}
	
	/**
	 * 拿到编码下拉列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getCodeList")
	@ResponseBody
	public Map<String, Object> getCodeList(String name) {
		if (StringUtil.isEmpty(name)) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		List<JcXtszCodeDefine> list = this.jcXtcsCodingdefService.findListByParams(JcXtszCodeDefine.class, "customerId", ShiroUtils.getCustomerId(), "type", name);
		if (list != null) {
			map.put("list", list);
		}
		return map;
	}
}
