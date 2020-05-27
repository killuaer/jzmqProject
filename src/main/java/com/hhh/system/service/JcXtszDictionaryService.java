package com.hhh.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.core.service.BaseService;
import com.hhh.core.util.DateUtil;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;
import com.hhh.system.dao.JcXtszDictionaryDao;
import com.hhh.system.entity.JcXtszDictionary;

@Service
@Transactional
public class JcXtszDictionaryService extends BaseService {

	@Autowired
	private JcXtszDictionaryDao jcXtcsBaseDao;

	
	public Map<String, Object> save(Map<String, Object> bean) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(bean.get("type"))) {
			map.put("status", "fail");
			map.put("msg", "type不能为空");
			return map;
		}
		JcXtszDictionary dict = null;
		if (StringUtil.isEmpty(bean.get("id"))) {
			dict = new JcXtszDictionary();
			dict.setType((String)bean.get("type"));
			dict.setCustomerId((String)bean.get("customerId"));
			dict.setDescr((String)bean.get("descr"));
			dict.setCode((String)bean.get("code"));
			dict.setOrd(Integer.valueOf((String)bean.get("ord")));
			dict.setParentId((String)bean.get("parentId"));
			dict.setName((String)bean.get("name"));
			dict.setCreatorId((String)bean.get("userId"));
			dict.setCreateTime(DateUtil.getCurrentDateTime());
		} else {
			dict = this.jcXtcsBaseDao.findById(JcXtszDictionary.class, bean.get("id"));
			dict.setName((String)bean.get("name"));
			dict.setOrd(Integer.valueOf((String)bean.get("ord")));
			dict.setUpdateTime(DateUtil.getCurrentDateTime());
			dict.setUpdateUserId((String)bean.get("userId"));
		}
		this.jcXtcsBaseDao.saveOrUpdate(dict);
		map.put("status", "success");
		return map;
	}

	public Map<String, Object> del(String id) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(id)) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		//有子分类不能删除
		List<JcXtszDictionary> list = this.jcXtcsBaseDao.findListByParams(JcXtszDictionary.class, "parentId", id);
		if (list != null && list.size() > 0) {
			map.put("status", "fail");
			map.put("msg", "请先删除子类数据");
			return map;
		}
		JcXtszDictionary dict = this.jcXtcsBaseDao.findById(JcXtszDictionary.class, id);
		this.jcXtcsBaseDao.delete(dict);
		map.put("status", "success");
		return map;
	}
	
	public FundPage<Map<String, Object>> listDict(Map<String, Object> paramMap, PageRequest page) {
		FundPage<JcXtszDictionary> fund = this.jcXtcsBaseDao.listDict(paramMap, page);
		List<Map<String, Object>> jsonObjs = new ArrayList<>();
		for (JcXtszDictionary entity : fund.getContent()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", entity.getId());
			map.put("name", entity.getName());
			map.put("type", entity.getType());
			map.put("code", entity.getCode());
			map.put("descr", entity.getDescr());
			map.put("parentId", entity.getParentId());
			jsonObjs.add(map);
		}
		return new FundPage<Map<String, Object>>(fund.getTotalPages(), fund.getTotalElements(), jsonObjs);
	}

	public Map<String, Object> move(String id, String type) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(id)) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		JcXtszDictionary jxb = this.jcXtcsBaseDao.findById(JcXtszDictionary.class, id);
		int ord = jxb.getOrd();
		String parentId = jxb.getParentId();
		List<Map<String, Object>> list = this.jcXtcsBaseDao.findListByParamsOrderBy(JcXtszDictionary.class, null, "ord", false, "parentId", parentId);
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
				JcXtszDictionary jxb2 = (JcXtszDictionary)StringUtil.map2Object(val, JcXtszDictionary.class);
				jxb.setOrd(jxb2.getOrd());
				jxb2.setOrd(ord);
				this.jcXtcsBaseDao.saveOrUpdate(jxb);
				this.jcXtcsBaseDao.saveOrUpdate(jxb2);
			}
		} catch (Exception e) {
			map.put("status", "fail");
			map.put("msg", e.getMessage());
			e.printStackTrace();
		}
		map.put("status", "success");
		return map;
	}
}
