package com.hhh.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.dao.SysUcenterAccountDao;
import com.hhh.auth.entity.SysUcenterAccount;
import com.hhh.auth.entity.SysUcenterCompany;
import com.hhh.auth.entity.SysUcenterDepartment;
import com.hhh.auth.entity.SysUcenterRole;
import com.hhh.auth.entity.SysUcenterUserDepartment;
import com.hhh.auth.entity.SysUcenterUserrole;
import com.hhh.core.model.Whether;
import com.hhh.core.service.BaseService;
import com.hhh.core.util.DateUtil;
import com.hhh.core.util.FundPage;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.StringUtil;

@Service
@Transactional
public class SysUcenterAccountService extends BaseService {

	@Autowired
	private SysUcenterAccountDao sysAccountDao;

	public FundPage<Map<String, Object>> listAccount(Map<String, Object> paramMap, PageRequest page) {
		FundPage<SysUcenterAccount> fund = this.sysAccountDao.listAccount(paramMap, page);
		List<Map<String, Object>> jsonObjs = new ArrayList<>();
		for (SysUcenterAccount entity : fund.getContent()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", entity.getId());
			map.put("name", entity.getName());
			map.put("loginName", entity.getLoginName());
			map.put("email", entity.getEmail());
			map.put("phone", entity.getPhone());
			jsonObjs.add(map);
		}
		return new FundPage<Map<String, Object>>(fund.getTotalPages(), fund.getTotalElements(), jsonObjs);
	}

	public Map<String, Object> save(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		SysUcenterAccount account = null;
		if (StringUtil.isEmpty(paramMap.get("deptId"))) {
			map.put("status", "fail");
			map.put("msg", "deptId不能为空");
			return map;
		}
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			account = new SysUcenterAccount();
			account.setName((String)paramMap.get("name"));
			account.setLoginName((String)paramMap.get("loginName"));
			String encPsd = StringUtil.string2MD5((String)paramMap.get("password")).toUpperCase();
			account.setPassword(encPsd);
			account.setPhone((String)paramMap.get("phone"));
			account.setEmail((String)paramMap.get("email"));
			account.setCustomerId(ShiroUtils.getCustomerId());
			account.setCreateTime(DateUtil.getCurrentDateTime());
			account.setCreatorId(ShiroUtils.getUserId());
			account.setIsAdmin(Whether.No);
			SysUcenterDepartment department = sysAccountDao.findById(SysUcenterDepartment.class, (String)paramMap.get("deptId"));
			SysUcenterCompany company = sysAccountDao.findByParams(SysUcenterCompany.class, "customerId", department.getCustomerId());
			account.setSysUcenterCompany(company);
			account = this.sysAccountDao.saveOrUpdate(account);
			
			SysUcenterUserDepartment ud = new SysUcenterUserDepartment();
			ud.setAccountId(account.getId());
			ud.setDepartId((String)paramMap.get("deptId"));
			this.sysAccountDao.saveOrUpdate(ud);
		} else {
			String id = (String)paramMap.get("id");
			account = this.sysAccountDao.findById(SysUcenterAccount.class, id);
			account.setName((String)paramMap.get("name"));
			account.setLoginName((String)paramMap.get("loginName"));
			String encPsd = StringUtil.string2MD5((String)paramMap.get("password")).toUpperCase();
			account.setPassword(encPsd);
			account.setPhone((String)paramMap.get("phone"));
			account.setEmail((String)paramMap.get("email"));
			account.setUpdateTime(DateUtil.getCurrentDateTime());
			account.setUpdateUserId(ShiroUtils.getUserId());
			account = this.sysAccountDao.saveOrUpdate(account);
		}
		this.giveRole(account.getId(), (String)paramMap.get("roleIds"));
		map.put("status", "success");
		return map;
	}
	
	private void giveRole(String accountId, String roleIds) {
		List<SysUcenterUserrole> urlist = this.sysAccountDao.findListByParams(SysUcenterUserrole.class, "accountId", accountId);
		if (urlist != null && urlist.size() > 0) {
			for (SysUcenterUserrole ur : urlist) {
				this.sysAccountDao.delete(ur);
			}
		}
		if (!StringUtil.isEmpty(roleIds)) {
			String[] rIds = roleIds.split(",");
			for (String roleId : rIds) {
				SysUcenterUserrole ur = new SysUcenterUserrole();
				ur.setAccountId(accountId);
				ur.setRoleId(roleId);
				this.sysAccountDao.saveOrUpdate(ur);
			}
		}
	}
	
	public Map<String, Object> del(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		String accountId = (String)paramMap.get("id");
		SysUcenterAccount account = this.sysAccountDao.findById(SysUcenterAccount.class, accountId);
		List<SysUcenterUserrole> urlist = this.sysAccountDao.findListByParams(SysUcenterUserrole.class, "accountId", accountId);
		if (urlist != null && urlist.size() > 0) {
			for (SysUcenterUserrole ur : urlist) {
				this.sysAccountDao.delete(ur);
			}
		}
		this.sysAccountDao.delete(account);
		map.put("status", "success");
		return map;
	}
	
	public Map<String, Object> resetPsd(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		SysUcenterAccount account = this.sysAccountDao.findById(SysUcenterAccount.class, (String)paramMap.get("id"));
		String password = StringUtil.string2MD5("HHHabc123!@#").toUpperCase();
		account.setPassword(password);
		this.sysAccountDao.saveOrUpdate(account);
		map.put("status", "success");
		return map;
	}
	
	public Map<String, Object> grantRoles(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isEmpty(paramMap.get("id"))) {
			map.put("status", "fail");
			map.put("msg", "id不能为空");
			return map;
		}
		if (StringUtil.isEmpty(paramMap.get("roleIds"))) {
			map.put("status", "fail");
			map.put("msg", "roleIds不能为空");
			return map;
		}
		SysUcenterAccount account = this.sysAccountDao.findById(SysUcenterAccount.class, (String)paramMap.get("id"));
		String roleIds = (String)paramMap.get("roleIds");
		String[] rIds = roleIds.split(",");
		List<SysUcenterRole> roleList = this.sysAccountDao.findByIds(SysUcenterRole.class, Arrays.asList(rIds));
		account.setSysUcenterRoles(new HashSet<>(roleList));
		account = this.sysAccountDao.saveOrUpdate(account);
		map.put("status", "success");
		return map;
	}
}
