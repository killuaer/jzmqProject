package com.hhh.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/**
 * The persistent class for the SYS_UCENTER_NUMMANAGE database table.
 * 编号管理表
 */
@Entity
@Table(name="SYS_UCENTER_NUMMANAGE")
@NamedQuery(name="SysUcenterNumManage.findAll", query="SELECT s FROM SysUcenterNumManage s")
public class SysUcenterNumManage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;
	
	@Column(name="CUSTOMER_ID",columnDefinition="varchar(80) COMMENT '单位ID'")
	private String customerId;
	@Column(name="BH_NAME",columnDefinition="varchar(80) COMMENT '编码名称'")
	private String bhName;
	@Column(name="BH_ID",columnDefinition="varchar(80) COMMENT '编码ID'")
	private String bhId;
	@Column(name="BH_FORM",columnDefinition="varchar(80) COMMENT '编号开头标识'")
	private String bhForm;
	@Column(name="BH_MONTH",columnDefinition="varchar(20) COMMENT '月份'")
	private String bhMonth;
	@Column(name="BH_YEAR",columnDefinition="varchar(20) COMMENT '年份'")
	private String bhYear;
	@Column(name="BH_DAY",columnDefinition="varchar(20) COMMENT '天'")
	private String bhDay;
	@Column(name="BH_PIPMODE",columnDefinition="varchar(20) COMMENT '流水方式'")
	private String bhPipmode;
	@Column(name="BH_NUMBER",columnDefinition="int COMMENT '流水号'")
	private int bhNumber;
	@Column(name="BH_LONG",columnDefinition="int COMMENT '流水号长度'")
	private int bhLong;
	@Column(name="BH_BS",columnDefinition="int COMMENT '流水标识'")
	private int bhBs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBhName() {
		return bhName;
	}
	public void setBhName(String bhName) {
		this.bhName = bhName;
	}
	public String getBhId() {
		return bhId;
	}
	public void setBhId(String bhId) {
		this.bhId = bhId;
	}
	public String getBhForm() {
		return bhForm;
	}
	public void setBhForm(String bhForm) {
		this.bhForm = bhForm;
	}
	public String getBhMonth() {
		return bhMonth;
	}
	public void setBhMonth(String bhMonth) {
		this.bhMonth = bhMonth;
	}
	public String getBhYear() {
		return bhYear;
	}
	public void setBhYear(String bhYear) {
		this.bhYear = bhYear;
	}
	public String getBhDay() {
		return bhDay;
	}
	public void setBhDay(String bhDay) {
		this.bhDay = bhDay;
	}
	public String getBhPipmode() {
		return bhPipmode;
	}
	public void setBhPipmode(String bhPipmode) {
		this.bhPipmode = bhPipmode;
	}
	public int getBhNumber() {
		return bhNumber;
	}
	public void setBhNumber(int bhNumber) {
		this.bhNumber = bhNumber;
	}
	public int getBhLong() {
		return bhLong;
	}
	public void setBhLong(int bhLong) {
		this.bhLong = bhLong;
	}
	public int getBhBs() {
		return bhBs;
	}
	public void setBhBs(int bhBs) {
		this.bhBs = bhBs;
	}
}
