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
import com.hhh.system.entity.JcXtszSystemSetting;
import com.hhh.system.service.JcXtszSystemSettingService;

@Controller
@RequestMapping("/systemSetting")
public class SystemSettingController extends BaseController {
	@Autowired
	private JcXtszSystemSettingService ssSystemSettingService;

	@HHHLog(moduleFirst = "系统管理",moduleSecond = "系统设置", info = "保存系统设置")
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save() {
		Map<String, Object> param = this.getParamMap();
		param.put("customerId", ShiroUtils.getCustomerId());
		param.put("userId", ShiroUtils.getUserId());
		Map<String, Object> map = this.ssSystemSettingService.save(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "系统设置", info = "获取系统设置列表")
	@RequestMapping(value = "/listJcXtszSystemSetting")
	@ResponseBody
	public Map<String, Object> listJcXtszSystemSetting(HttpServletRequest req, LayPageParam pageParam) {
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("customerId", ShiroUtils.getCustomerId());
		FundPage<Map<String, Object>> fundPage = this.ssSystemSettingService.listJcXtszSystemSetting(paramMap, pageReq);
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
	
	@RequestMapping(value = "/getJcXtszSystemSetting")
	@ResponseBody
	public Map<String, Object> getJcXtszSystemSetting() {
		Map<String, Object> map = new HashMap<>();
		String customerId = ShiroUtils.getCustomerId();
		List<JcXtszSystemSetting> sssList = this.ssSystemSettingService.findListByParams(JcXtszSystemSetting.class, "customerId", customerId);
		for (JcXtszSystemSetting sss : sssList) {
			map.put(sss.getParameterId(), sss.getValue());
		}
		return map;
	}
	
}
