package com.hhh.system.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.core.service.BaseService;
import com.hhh.core.service.IJcXtszCodeStatusService;
import com.hhh.core.service.ISysUcenterDepartmentService;
import com.hhh.core.util.DateUtil;
import com.hhh.core.util.LockUtil;
import com.hhh.core.util.StringUtil;
import com.hhh.system.dao.JcXtszCodeDefineDao;
import com.hhh.system.entity.JcLogCode;
import com.hhh.system.entity.JcXtszCodeDefine;
import com.hhh.system.entity.JcXtszCodeStatus;

@Service
@Transactional
public class IJcXtszCodeStatusServiceImpl extends BaseService implements IJcXtszCodeStatusService {

	@Autowired
	private JcXtszCodeDefineDao jcXtszCodeDefineDao;
	@Autowired
	private ISysUcenterDepartmentService sysUcenterDepartmentService;

	/**
	 * 参数说明
	 * name 编号名称*
	 * customerId 客户id*
	 * userId 用户id*
	 * xm 替换[XM]的值
	 * deptId 部门id
	 * gclx 工程类型
	 * sjlb 送检类别
	 * busyId 业务id 用于日志表关联
	 * num 一次取号数，默认1
	 */
	@Override
	public String[] getNewCode(Map<String, Object> params) {
		if (StringUtil.isEmpty(params.get("name")) || StringUtil.isEmpty(params.get("customerId")) || StringUtil.isEmpty(params.get("userId"))) {
			return null;
		}
		String name = (String)params.get("name");
		String customerId = (String)params.get("customerId");
		JcXtszCodeDefine define = jcXtszCodeDefineDao.findByParams(JcXtszCodeDefine.class, "name", name, "customerId", customerId);
		if (define != null) {
			String fomart = define.getCodeFormat();
			Map<String, Object> rfMap = new HashMap<>();
			rfMap.put("xm", params.get("xm"));
			rfMap.put("deptId", params.get("deptId"));
			rfMap.put("gclx", params.get("gclx"));
			rfMap.put("sjlb", params.get("sjlb"));
			rfMap.put("codeformat", fomart);
			rfMap.put("name", params.get("name"));
			String[] val1 = this.replaceFormat(rfMap);
			if (val1 != null) {
				String prefix = val1[0];
				String suffix = val1[1];
				
				while (LockUtil.isLock(name)) {
				}
				try {
					LockUtil.lock(name);
					params.put("prefix", prefix);
					params.put("suffix", suffix);
					String[] newCode = this.replaceNum(params);
					return newCode;
				} catch (Exception e) {
					throw e;
				} finally {
					LockUtil.release(name);
				}
			}
			
		}
		return null;
	}

	private String[] replaceFormat(Map<String, Object> params) {
		String codeformat = (String)params.get("codeformat");
		if (!StringUtil.isEmpty(codeformat)) {
			if (codeformat.contains("[XM]") && !StringUtil.isEmpty(params.get("xm"))) {
				codeformat = codeformat.replace("[XM]", StringUtil.nvl(params.get("xm"), ""));
			}
			if ((codeformat.contains("[BM]") || codeformat.contains("[ZD]")) && !StringUtil.isEmpty(params.get("deptId"))) {
				Map<String, Object> map = this.sysUcenterDepartmentService.getById((String)params.get("deptId"));
				if (map != null) {
					if (1 == (Integer)map.get("type")) {
						codeformat = codeformat.replace("[BM]", StringUtil.nvl(map.get("code"), ""));
					} else {
						codeformat = codeformat.replace("[ZD]", StringUtil.nvl(map.get("code"), ""));
					}
				}
			}
			if (codeformat.contains("[GCLX]") && !StringUtil.isEmpty(params.get("gclx"))) {
				codeformat = codeformat.replace("[GCLX]", StringUtil.nvl(params.get("gclx"), ""));
			}
			if (codeformat.contains("[SJLB]") && !StringUtil.isEmpty(params.get("sjlb"))) {
				codeformat = codeformat.replace("[SJLB]", StringUtil.nvl(params.get("sjlb"), ""));
			}
			codeformat = this.replaceDate(codeformat);
			Pattern p = Pattern.compile("[x]{4,8}");
			Matcher m = p.matcher(codeformat);
			if (m.find()) {
				String prefix = codeformat.split(m.group())[0];
				String suffix = codeformat.split(prefix)[1];
				return new String[]{prefix, suffix};
			}
		}
		return null;
	}
	
	public String[] replaceNum(Map<String, Object> params) {
		String prefix = (String)params.get("prefix");
		String suffix = (String)params.get("suffix"); 
		String customerId = (String)params.get("customerId");
		String userId = (String)params.get("userId");
		String name = (String)params.get("name");
		Integer num = 1;
		if (!StringUtil.isEmpty(params.get("num"))) {
			num = Integer.valueOf((String)params.get("num"));
		}
		String[] newCode = new String[num];
		int len = suffix.length();
		String firstCode = "0001";
		if (len == 5) {
			firstCode = "00001";
		} else if (len == 6) {
			firstCode = "000001";
		}
		JcXtszCodeStatus codeStatus = this.jcXtszCodeDefineDao.findByParams(JcXtszCodeStatus.class, "customerId", customerId, "codePrefix", prefix);
		if (codeStatus != null) {
			String max = codeStatus.getCodeMax();
			String maxNum = max.substring(max.length() - len);
			Integer oldInteger = Integer.valueOf(maxNum);
			for (int i = 0; i < num; i++) {
				Integer newInteger = new Integer(oldInteger + i + 1);
				String s = newInteger.toString();
				int length = s.length();
				String newMaxNum = firstCode.substring(0, firstCode.length() - length) + s;
				codeStatus.setCodeMax(prefix + newMaxNum);
				newCode[i] = prefix + newMaxNum;
			}
			codeStatus.setUpdateTime(DateUtil.getCurrentDateTime());
			codeStatus.setUpdateUserId(userId);
		} else {
			codeStatus= new JcXtszCodeStatus();
			codeStatus.setCustomerId(customerId);
			Integer oldInteger = 0;
			for (int i = 0; i < num; i++) {
				Integer newInteger = new Integer(oldInteger + i + 1);
				String s = newInteger.toString();
				int length = s.length();
				String newMaxNum = firstCode.substring(0, firstCode.length() - length) + s;
				codeStatus.setCodeMax(prefix + newMaxNum);
				newCode[i] = prefix + newMaxNum;
			}
			codeStatus.setCodePrefix(prefix);
			codeStatus.setCreateTime(DateUtil.getCurrentDateTime());
			codeStatus.setCreatorId(userId);
			codeStatus.setIsLocked(0);
			codeStatus.setCodeType(name);
		}
		codeStatus = this.jcXtszCodeDefineDao.saveOrUpdate(codeStatus);
		
		//取号日志
		for (String code : newCode) {
			JcLogCode log = new JcLogCode();
			log.setCode(code);
			log.setCodePrefix(prefix);
			log.setCodeType(name);
			log.setCreateTime(DateUtil.getCurrentDateTime());
			log.setCreatorId(userId);
			log.setCustomerId(customerId);
			log.setBusyId((String)params.get("busyId"));
			log.setStatusId(codeStatus.getId());
			log.setIsNew(0);
			this.jcXtszCodeDefineDao.saveOrUpdate(log);
		}
		return newCode;
	}
	
	private String replaceDate(String code) {
		Calendar now = Calendar.getInstance();
		if (code.contains("yyyy")) {
			code = code.replace("yyyy", now.get(Calendar.YEAR) + "");
		} else if (code.contains("yy")) {
			code = code.replace("yy", (now.get(Calendar.YEAR) + "").substring(2));
		}
		if (code.contains("mm")) {
			code = code.replace("mm", (now.get(Calendar.MONTH) + 1) < 10 ? "0" + (now.get(Calendar.MONTH) + 1) : "" + (now.get(Calendar.MONTH) + 1));
		}
		if (code.contains("dd")) {
			code = code.replace("dd", now.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + (now.get(Calendar.DAY_OF_MONTH)) : "" + (now.get(Calendar.DAY_OF_MONTH)));
		}
		return code;
	}
}
