package com.hhh.core.service;

import java.util.Map;

public interface IJcXtszCodeStatusService {

	/**
	 * 参数说明:
	 * name 编号名称*
	 * customerId 客户id*
	 * userId 用户id*
	 * xmId 检测项目id
	 * deptId 部门id
	 * gclx 工程类型
	 * sjlb 送检类别
	 * busyId 业务id 用于日志表关联
	 * num 一次取号数，默认1
	 */
	public String[] getNewCode(Map<String, Object> params);
}
