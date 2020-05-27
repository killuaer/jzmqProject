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
import com.hhh.system.dao.JcXtszCodeDefineDao;
import com.hhh.system.entity.JcXtszCodeDefine;

@Service
@Transactional
public class JcXtszCodeDefineService extends BaseService {

	@Autowired
	private JcXtszCodeDefineDao jcXtcsCodingdefDao;

	
	public Map<String, Object> save(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		JcXtszCodeDefine entity = null;
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			entity = new JcXtszCodeDefine();
			entity.setCustomerId((String)paramMap.get("customerId"));
			entity.setType((String)paramMap.get("type"));
			entity.setIsDefault(0);
			entity.setCreatorId((String)paramMap.get("userId"));
			entity.setCreateTime(DateUtil.getCurrentDateTime());
		} else {
			entity = this.jcXtcsCodingdefDao.findById(JcXtszCodeDefine.class, (String)paramMap.get("id"));
			entity.setUpdateUserId((String)paramMap.get("userId"));
			entity.setUpdateTime(DateUtil.getCurrentDateTime());
		}
		entity.setCodeFormat((String)paramMap.get("codeFormat"));
		entity.setCycle((String)paramMap.get("cycle"));
		entity.setName((String)paramMap.get("name"));
		this.jcXtcsCodingdefDao.saveOrUpdate(entity);
		map.put("status", "success");
		return map;
	}

	
	public FundPage<Map<String, Object>> listJcXtszCodeDefine(Map<String, Object> paramMap, PageRequest page) {
		FundPage<JcXtszCodeDefine> fund = this.jcXtcsCodingdefDao.listJcXtszCodeDefine(paramMap, page);
		List<Map<String, Object>> jsonObjs = new ArrayList<>();
		for (JcXtszCodeDefine entity : fund.getContent()) {
			Map<String, Object> map = StringUtil.object2Map(entity);
			jsonObjs.add(map);
		}
		return new FundPage<Map<String, Object>>(fund.getTotalPages(), fund.getTotalElements(), jsonObjs);
	}

	
	public Map<String, Object> del(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		JcXtszCodeDefine video = this.jcXtcsCodingdefDao.findById(JcXtszCodeDefine.class, (String)paramMap.get("id"));
		this.jcXtcsCodingdefDao.delete(video);
		map.put("status", "success");
		return map;
	}
	

}
