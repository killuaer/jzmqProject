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
import com.hhh.system.dao.JcXtszSystemSettingDao;
import com.hhh.system.entity.JcXtszSystemSetting;

@Service
@Transactional
public class JcXtszSystemSettingService extends BaseService {

	@Autowired
	private JcXtszSystemSettingDao ssSystemSettingDao;

	public FundPage<Map<String, Object>> listJcXtszSystemSetting(Map<String, Object> paramMap, PageRequest page) {
		FundPage<JcXtszSystemSetting> fund = this.ssSystemSettingDao.listJcXtszSystemSetting(paramMap, page);
		List<Map<String, Object>> jsonObjs = new ArrayList<>();
		for (JcXtszSystemSetting entity : fund.getContent()) {
			Map<String, Object> map = StringUtil.object2Map(entity);
			jsonObjs.add(map);
		}
		return new FundPage<Map<String, Object>>(fund.getTotalPages(), fund.getTotalElements(), jsonObjs);
	}

	public Map<String, Object> save(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		String customerId = (String)paramMap.get("customerId");
		paramMap.remove("customerId");
		List<JcXtszSystemSetting> sssList = this.ssSystemSettingDao.findListByParams(JcXtszSystemSetting.class, "customerId", customerId);
		List<String> sssIds = new ArrayList<>();
		for (JcXtszSystemSetting sss : sssList) {
			sssIds.add(sss.getParameterId());
		}
		Map<String, JcXtszSystemSetting> sssMap = this.ssSystemSettingDao.findMapByFields(JcXtszSystemSetting.class, "parameterId", sssIds);
		if (!paramMap.containsKey("is_show_commission_wtcol")) {
			JcXtszSystemSetting isc = sssMap.get("is_show_commission_wtcol");
			isc.setValue("n");
			this.ssSystemSettingDao.saveOrUpdate(isc);
		}
		if (!paramMap.containsKey("is_show_commission_wt")) {
			JcXtszSystemSetting isc = sssMap.get("is_show_commission_wt");
			isc.setValue("n");
			this.ssSystemSettingDao.saveOrUpdate(isc);
		}
		if (!paramMap.containsKey("is_show_commission_task")) {
			JcXtszSystemSetting isc = sssMap.get("is_show_commission_task");
			isc.setValue("n");
			this.ssSystemSettingDao.saveOrUpdate(isc);
		}
		if (!paramMap.containsKey("is_show_commission_biaoqian")) {
			JcXtszSystemSetting isc = sssMap.get("is_show_commission_biaoqian");
			isc.setValue("n");
			this.ssSystemSettingDao.saveOrUpdate(isc);
		}
		if (!paramMap.containsKey("is_show_commission_bgpz")) {
			JcXtszSystemSetting isc = sssMap.get("is_show_commission_bgpz");
			isc.setValue("n");
			this.ssSystemSettingDao.saveOrUpdate(isc);
		}
		if (!paramMap.containsKey("is_show_commission_original")) {
			JcXtszSystemSetting isc = sssMap.get("is_show_commission_original");
			isc.setValue("n");
			this.ssSystemSettingDao.saveOrUpdate(isc);
		}
		if (!paramMap.containsKey("is_show_commission_liuyang")) {
			JcXtszSystemSetting isc = sssMap.get("is_show_commission_liuyang");
			isc.setValue("n");
			this.ssSystemSettingDao.saveOrUpdate(isc);
		}
		if (!paramMap.containsKey("is_show_commission_jianding")) {
			JcXtszSystemSetting isc = sssMap.get("is_show_commission_jianding");
			isc.setValue("n");
			this.ssSystemSettingDao.saveOrUpdate(isc);
		}
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			String parameterId = entry.getKey();
			String value = (String)entry.getValue();
			//创建用户时，即有了默认数据。这里只涉及修改操作
			JcXtszSystemSetting sss = null;
			if (sssMap != null && sssMap.get(parameterId) != null) {
				sss = sssMap.get(parameterId);
				sss.setValue(value);
				sss.setUpdateTime(DateUtil.getCurrentDateTime());
				sss.setUpdateUserId((String)paramMap.get("customerId"));
			} else {
				sss = new JcXtszSystemSetting();
				sss.setCustomerId(customerId);
				sss.setParameterId(parameterId);
				sss.setValue(value);
				sss.setCreateTime(DateUtil.getCurrentDateTime());
				sss.setCreatorId((String)paramMap.get("customerId"));
			}
			this.ssSystemSettingDao.saveOrUpdate(sss);
		}
		map.put("status", "success");
		return map;
	}
	
}
