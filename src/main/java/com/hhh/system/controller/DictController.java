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
import com.hhh.system.entity.JcXtszDictionary;
import com.hhh.system.service.JcXtszDictionaryService;

@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {
	@Autowired
	private JcXtszDictionaryService jcXtcsBaseService;
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "数据字典", info = "获取数据字典列表")
	@RequestMapping(value = "/listDict")
	@ResponseBody
	public Map<String, Object> listDict(HttpServletRequest req, LayPageParam pageParam) {
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("type", req.getParameter("type"));
		paramMap.put("name", req.getParameter("name"));
		paramMap.put("parentId", req.getParameter("parentId"));
		FundPage<Map<String, Object>> fundPage = this.jcXtcsBaseService.listDict(paramMap, pageReq);
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
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "数据字典", info = "保存数据字典")
	@RequestMapping(value = "/saveDict")
	@ResponseBody
	public Map<String, Object> saveDict() {
		Map<String, Object> param = this.getParamMap();
		param.put("customerId", ShiroUtils.getCustomerId());
		param.put("userId", ShiroUtils.getUserId());
		Map<String, Object> map = this.jcXtcsBaseService.save(param);
		return map;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "数据字典", info = "删除数据字典")
	@RequestMapping(value = "/delDict")
	@ResponseBody
	public Map<String, Object> delDict(String id) {
		Map<String, Object> map = this.jcXtcsBaseService.del(id);
		return map;
	}
	
	@RequestMapping(value = "/getDict")
	@ResponseBody
	public Map<String, Object> getDict(String id) {
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		JcXtszDictionary dict = this.jcXtcsBaseService.findById(JcXtszDictionary.class, id);
		if (dict != null) {
			map = StringUtil.object2Map(dict);
		}
		return map;
	}
	
	//获取第一层数据字典
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "数据字典", info = "获取数据字典树")
	@RequestMapping(value = "/getDictTree")
	@ResponseBody
	public List<Map<String, Object>> getDictTree() {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = this.jcXtcsBaseService.findListByParamsOrderBy(JcXtszDictionary.class, null, "ord", false, "customerId",ShiroUtils.getCustomerId(),"parentId","0");
		map.put("name", "数据字典");
		map.put("id", "0");
		map.put("spread", true);
		List<Map<String, Object>> chilList = new ArrayList<>();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> jxb : list) {
				Map<String, Object> val = new HashMap<>();
				val.put("name", jxb.get("name"));
				val.put("id", jxb.get("id"));
				val.put("spread", true);
				chilList.add(val);
			}
		}
		map.put("children", chilList);
		List<Map<String, Object>> resList = new ArrayList<>();
		resList.add(map);
		return resList;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "数据字典", info = "移动数据字典")
	@RequestMapping(value = "/moveDict")
	@ResponseBody
	public Map<String, Object> moveDict(String id, String type) {
		Map<String, Object> map = this.jcXtcsBaseService.move(id, type);
		return map;
	}
	
	/**
	 * 获取数据字典下拉值
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/getDictList")
	@ResponseBody
	public Map<String, Object> getDictList(String type) {
		if (StringUtil.isEmpty(type)) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		List<JcXtszDictionary> list = this.jcXtcsBaseService.findListByParams(JcXtszDictionary.class, "customerId", ShiroUtils.getCustomerId(), "type", type);
		if (list != null) {
			map.put("list", list);
		}
		return map;
	}
}
