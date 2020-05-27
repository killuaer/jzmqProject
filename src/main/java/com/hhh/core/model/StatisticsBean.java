package com.hhh.core.model;

import com.hhh.core.util.StringUtil;


/**
 * 统计报表的Bean
 *
 */
public class StatisticsBean {
	// 总数量
	private String noteIpTotal;
	// 合格数量
	private String noteIpYTotal;
	// 合格率
	private String noteIpYPercent;
	// 不合格数量
	private String noteIpNTotal;
	// 不合格率
	private String noteIpNPercent;
	// 复检数量
	private String noteIpFTotal;
	// 复检率
	private String noteIpFPercent;
	// 其他数量
	private String noteIpTTotal;
	// 其他率
	private String noteIpTPercent;
	// 监督抽检数量
	private String sgJcTotal;
	// 监督抽检率
	private String sgJcPercent;
	// 设备超期数量
	private String sbcqReportNum;
	// 自动采集数量
	private String zdcjNum;
	// 自动采集率
	private String zdcjPercent;
	// 未自动采集数量
	private String wzdcjNum;
	// 未自动采集率
	private String wzdcjPercent;
	// 修改数量
	private String reportModifyNum;
	// 修改率
	private String reportModifyPercent;
	// 异常修改数量
	private String exceptionReportModifyNum;
	// 异常修改率
	private String exceptionReportModifyPercent;
	// 超资质数量
	private String cxmzzReportNum;
	// 无证上岗数量
	private String wzsgReportNum;
	// 设备数量
	private String sbNum;
	// 人员数量
	private String ryNum;
	// 资质数量
	private String zzNum;
	// 工程数量
	private String gcNum;

	private String gcId;
	private String gcCode;
	private String zljdbm;
	private String gcName;

	private String etpId;
	private String etpName;
	private String customerId;
	private String etpCustomerId;
	private String unitcode;

	private String xmId;
	private String xmCode;
	private String xmName;
	private String xmNum;

	private String province;
	private String city;
	private String town;
	private String type;
	
	// 用于查询
	private String customerId4Search;
	private String year;
	private String month;
	
	private String rankType;
	
	public void merge(StatisticsBean statisticsBean) {
		if (statisticsBean == null) return;
		this.noteIpTotal = (Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getNoteIpTotal(), "0"))) + "";
		this.noteIpYTotal = (Integer.parseInt(StringUtil.nvl(this.noteIpYTotal, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getNoteIpYTotal(), "0"))) + "";
		this.noteIpYPercent = StringUtil.decimalFormat(Integer.parseInt(StringUtil.nvl(this.noteIpYTotal, "0")) / Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) * 100) + "%";
		this.noteIpNTotal = (Integer.parseInt(StringUtil.nvl(this.noteIpNTotal, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getNoteIpNTotal(), "0"))) + "";
		this.noteIpNPercent = StringUtil.decimalFormat(Integer.parseInt(StringUtil.nvl(this.noteIpNTotal, "0")) / Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) * 100) + "%";
		this.noteIpFTotal = (Integer.parseInt(StringUtil.nvl(this.noteIpFTotal, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getNoteIpFTotal(), "0"))) + "";
		this.noteIpFPercent = StringUtil.decimalFormat(Integer.parseInt(StringUtil.nvl(this.noteIpFTotal, "0")) / Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) * 100) + "%";
		this.noteIpTTotal = (Integer.parseInt(StringUtil.nvl(this.noteIpTTotal, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getNoteIpTTotal(), "0"))) + "";
		this.noteIpTPercent = StringUtil.decimalFormat(Integer.parseInt(StringUtil.nvl(this.noteIpTTotal, "0")) / Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) * 100) + "%";
		this.sgJcTotal = (Integer.parseInt(StringUtil.nvl(this.sgJcTotal, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getSgJcTotal(), "0"))) + "";
		this.sgJcPercent = StringUtil.decimalFormat(Integer.parseInt(StringUtil.nvl(this.sgJcTotal, "0")) / Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) * 100) + "%";
		this.sbcqReportNum = (Integer.parseInt(StringUtil.nvl(this.sbcqReportNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getSbcqReportNum(), "0"))) + "";
		this.zdcjNum = (Integer.parseInt(StringUtil.nvl(this.zdcjNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getZdcjNum(), "0"))) + "";
		this.zdcjPercent = StringUtil.decimalFormat(Integer.parseInt(StringUtil.nvl(this.zdcjNum, "0")) / Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) * 100) + "%";
		this.wzdcjNum = (Integer.parseInt(StringUtil.nvl(this.wzdcjNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getWzdcjNum(), "0"))) + "";
		this.wzdcjPercent = StringUtil.decimalFormat(Integer.parseInt(StringUtil.nvl(this.wzdcjNum, "0")) / Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) * 100) + "%";
		this.reportModifyNum = (Integer.parseInt(StringUtil.nvl(this.reportModifyNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getReportModifyNum(), "0"))) + "";
		this.reportModifyPercent = StringUtil.decimalFormat(Integer.parseInt(StringUtil.nvl(this.reportModifyNum, "0")) / Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) * 100) + "%";
		this.exceptionReportModifyNum = (Integer.parseInt(StringUtil.nvl(this.exceptionReportModifyNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getExceptionReportModifyNum(), "0"))) + "";
		this.exceptionReportModifyPercent = StringUtil.decimalFormat(Integer.parseInt(StringUtil.nvl(this.exceptionReportModifyNum, "0")) / Integer.parseInt(StringUtil.nvl(this.noteIpTotal, "0")) * 100) + "%";
		this.cxmzzReportNum = (Integer.parseInt(StringUtil.nvl(this.cxmzzReportNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getCxmzzReportNum(), "0"))) + "";
		this.wzsgReportNum = (Integer.parseInt(StringUtil.nvl(this.wzsgReportNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getWzsgReportNum(), "0"))) + "";
		this.sbNum = (Integer.parseInt(StringUtil.nvl(this.sbNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getSbNum(), "0"))) + "";
		this.ryNum = (Integer.parseInt(StringUtil.nvl(this.ryNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getRyNum(), "0"))) + "";
		this.zzNum = (Integer.parseInt(StringUtil.nvl(this.zzNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getZzNum(), "0"))) + "";
		this.gcNum = (Integer.parseInt(StringUtil.nvl(this.gcNum, "0")) + Integer.parseInt(StringUtil.nvl(statisticsBean.getGcNum(), "0"))) + "";
	}
	
	public String getNoteIpTotal() {
		return noteIpTotal;
	}

	public void setNoteIpTotal(String noteIpTotal) {
		this.noteIpTotal = noteIpTotal;
	}

	public String getNoteIpYPercent() {
		return noteIpYPercent;
	}

	public void setNoteIpYPercent(String noteIpYPercent) {
		this.noteIpYPercent = noteIpYPercent;
	}

	public String getNoteIpNPercent() {
		return noteIpNPercent;
	}

	public void setNoteIpNPercent(String noteIpNPercent) {
		this.noteIpNPercent = noteIpNPercent;
	}

	public String getNoteIpFPercent() {
		return noteIpFPercent;
	}

	public void setNoteIpFPercent(String noteIpFPercent) {
		this.noteIpFPercent = noteIpFPercent;
	}

	public String getNoteIpTPercent() {
		return noteIpTPercent;
	}

	public void setNoteIpTPercent(String noteIpTPercent) {
		this.noteIpTPercent = noteIpTPercent;
	}

	public String getSgJcPercent() {
		return sgJcPercent;
	}

	public void setSgJcPercent(String sgJcPercent) {
		this.sgJcPercent = sgJcPercent;
	}

	public String getNoteIpYTotal() {
		return noteIpYTotal;
	}

	public void setNoteIpYTotal(String noteIpYTotal) {
		this.noteIpYTotal = noteIpYTotal;
	}

	public String getNoteIpNTotal() {
		return noteIpNTotal;
	}

	public void setNoteIpNTotal(String noteIpNTotal) {
		this.noteIpNTotal = noteIpNTotal;
	}

	public String getNoteIpFTotal() {
		return noteIpFTotal;
	}

	public void setNoteIpFTotal(String noteIpFTotal) {
		this.noteIpFTotal = noteIpFTotal;
	}

	public String getNoteIpTTotal() {
		return noteIpTTotal;
	}

	public void setNoteIpTTotal(String noteIpTTotal) {
		this.noteIpTTotal = noteIpTTotal;
	}

	public String getSgJcTotal() {
		return sgJcTotal;
	}

	public void setSgJcTotal(String sgJcTotal) {
		this.sgJcTotal = sgJcTotal;
	}

	public String getGcId() {
		return gcId;
	}

	public void setGcId(String gcId) {
		this.gcId = gcId;
	}

	public String getGcCode() {
		return gcCode;
	}

	public void setGcCode(String gcCode) {
		this.gcCode = gcCode;
	}

	public String getZljdbm() {
		return zljdbm;
	}

	public void setZljdbm(String zljdbm) {
		this.zljdbm = zljdbm;
	}

	public String getGcName() {
		return gcName;
	}

	public void setGcName(String gcName) {
		this.gcName = gcName;
	}

	public String getEtpId() {
		return etpId;
	}

	public void setEtpId(String etpId) {
		this.etpId = etpId;
	}

	public String getEtpName() {
		return etpName;
	}

	public void setEtpName(String etpName) {
		this.etpName = etpName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getEtpCustomerId() {
		return etpCustomerId;
	}

	public void setEtpCustomerId(String etpCustomerId) {
		this.etpCustomerId = etpCustomerId;
	}

	public String getXmId() {
		return xmId;
	}

	public void setXmId(String xmId) {
		this.xmId = xmId;
	}

	public String getXmCode() {
		return xmCode;
	}

	public void setXmCode(String xmCode) {
		this.xmCode = xmCode;
	}

	public String getXmName() {
		return xmName;
	}

	public void setXmName(String xmName) {
		this.xmName = xmName;
	}

	public String getXmNum() {
		return xmNum;
	}

	public void setXmNum(String xmNum) {
		this.xmNum = xmNum;
	}

	public String getUnitcode() {
		return unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSbcqReportNum() {
		return sbcqReportNum;
	}

	public void setSbcqReportNum(String sbcqReportNum) {
		this.sbcqReportNum = sbcqReportNum;
	}

	public String getWzdcjNum() {
		return wzdcjNum;
	}

	public void setWzdcjNum(String wzdcjNum) {
		this.wzdcjNum = wzdcjNum;
	}

	public String getExceptionReportModifyNum() {
		return exceptionReportModifyNum;
	}

	public void setExceptionReportModifyNum(String exceptionReportModifyNum) {
		this.exceptionReportModifyNum = exceptionReportModifyNum;
	}

	public String getCxmzzReportNum() {
		return cxmzzReportNum;
	}

	public void setCxmzzReportNum(String cxmzzReportNum) {
		this.cxmzzReportNum = cxmzzReportNum;
	}

	public String getWzsgReportNum() {
		return wzsgReportNum;
	}

	public void setWzsgReportNum(String wzsgReportNum) {
		this.wzsgReportNum = wzsgReportNum;
	}

	public String getSbNum() {
		return sbNum;
	}

	public void setSbNum(String sbNum) {
		this.sbNum = sbNum;
	}

	public String getRyNum() {
		return ryNum;
	}

	public void setRyNum(String ryNum) {
		this.ryNum = ryNum;
	}

	public String getZzNum() {
		return zzNum;
	}

	public void setZzNum(String zzNum) {
		this.zzNum = zzNum;
	}

	public String getCustomerId4Search() {
		return customerId4Search;
	}

	public void setCustomerId4Search(String customerId4Search) {
		this.customerId4Search = customerId4Search;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWzdcjPercent() {
		return wzdcjPercent;
	}

	public void setWzdcjPercent(String wzdcjPercent) {
		this.wzdcjPercent = wzdcjPercent;
	}

	public String getExceptionReportModifyPercent() {
		return exceptionReportModifyPercent;
	}

	public void setExceptionReportModifyPercent(String exceptionReportModifyPercent) {
		this.exceptionReportModifyPercent = exceptionReportModifyPercent;
	}

	public String getRankType() {
		return rankType;
	}

	public void setRankType(String rankType) {
		this.rankType = rankType;
	}

	public String getZdcjNum() {
		return zdcjNum;
	}

	public void setZdcjNum(String zdcjNum) {
		this.zdcjNum = zdcjNum;
	}

	public String getZdcjPercent() {
		return zdcjPercent;
	}

	public void setZdcjPercent(String zdcjPercent) {
		this.zdcjPercent = zdcjPercent;
	}

	public String getReportModifyNum() {
		return reportModifyNum;
	}

	public void setReportModifyNum(String reportModifyNum) {
		this.reportModifyNum = reportModifyNum;
	}

	public String getReportModifyPercent() {
		return reportModifyPercent;
	}

	public void setReportModifyPercent(String reportModifyPercent) {
		this.reportModifyPercent = reportModifyPercent;
	}

	public String getGcNum() {
		return gcNum;
	}

	public void setGcNum(String gcNum) {
		this.gcNum = gcNum;
	}
}
