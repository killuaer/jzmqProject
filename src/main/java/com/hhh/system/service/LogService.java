package com.hhh.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.core.service.ISysUcenterAccountService;
import com.hhh.core.util.DateUtil;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;
import com.hhh.system.dao.JcLogOperationDao;
import com.hhh.system.dao.JcLogRunDao;
import com.hhh.system.entity.JcLogOperation;
import com.hhh.system.entity.JcLogRun;

@Service
@Transactional
public class LogService {

	@Autowired
	private JcLogOperationDao jcLogOperationDao;
	@Autowired
	private JcLogRunDao jcLogRunDao;
	@Autowired
	private ISysUcenterAccountService iSysUcenterAccountService;
	
	public Map<String, Object> saveRunLog(Map<String, Object> bean) {
		JcLogRun log = null;
		try {
			log = (JcLogRun)StringUtil.map2Object(bean, JcLogRun.class);
			log.setCreateTime(DateUtil.getCurrentDateTime());
			log = this.jcLogRunDao.saveOrUpdate(log);
			bean.put("id", log.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public void saveOperationLog(JcLogOperation log) {
		this.jcLogRunDao.saveOrUpdate(log);
	}
	
	public FundPage<Map<String, Object>> listJcLogRun(Map<String, Object> paramMap, PageRequest page) {
		FundPage<JcLogRun> fund = this.jcLogRunDao.listJcLogRun(paramMap, page);
		List<Map<String, Object>> jsonObjs = new ArrayList<>();
		List<String> userIds = new ArrayList<>();
		for (JcLogRun entity : fund.getContent()) {
			userIds.add(entity.getCreatorId());
		}
		Map<String, Map<String, Object>> accountMap = this.iSysUcenterAccountService.findMapByIds(userIds);
		for (JcLogRun entity : fund.getContent()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", entity.getId());
			map.put("createTime", DateUtil.formatDateTime(entity.getCreateTime()));
			map.put("systemUrl", entity.getSystemUrl());
			map.put("logInfo", entity.getLogInfo());
			Map<String, Object> account = accountMap.get(entity.getCreatorId());
			map.put("creator", account.get("name"));
			jsonObjs.add(map);
		}
		return new FundPage<Map<String, Object>>(fund.getTotalPages(), fund.getTotalElements(), jsonObjs);
	}
	
	public FundPage<Map<String, Object>> listJcLogOperation(Map<String, Object> paramMap, PageRequest page) {
		FundPage<JcLogOperation> fund = this.jcLogOperationDao.listJcLogOperation(paramMap, page);
		List<Map<String, Object>> jsonObjs = new ArrayList<>();
		List<String> userIds = new ArrayList<>();
		for (JcLogOperation entity : fund.getContent()) {
			userIds.add(entity.getCreatorId());
		}
		Map<String, Map<String, Object>> accountMap = this.iSysUcenterAccountService.findMapByIds(userIds);
		for (JcLogOperation entity : fund.getContent()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", entity.getId());
			map.put("createTime", DateUtil.formatDateTime(entity.getCreateTime()));
			map.put("content", entity.getModuleFirst()+("无".equals(entity.getModuleSecond()) ? "" : "-"+entity.getModuleSecond()) + ("无".equals(entity.getModuleThird()) ? "" : "-"+entity.getModuleThird())+"-"+entity.getOpContent());
			map.put("opId", entity.getOpId());
			Map<String, Object> account = accountMap.get(entity.getCreatorId());
			map.put("creator", account.get("name"));
			jsonObjs.add(map);
		}
		return new FundPage<Map<String, Object>>(fund.getTotalPages(), fund.getTotalElements(), jsonObjs);
	}
	
}
