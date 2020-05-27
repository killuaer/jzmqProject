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
 * The persistent class for the SYS_UCENTER_PROJECT database table.
 * 
 */
@Entity
@Table(name="SYS_UCENTER_PROJECT")
@NamedQuery(name="SysUcenterProject.findAll", query="SELECT s FROM SysUcenterProject s")
public class SysUcenterProject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;
	
	@Column(name="CUSTOMER_ID")
	private String customerId;
	
	@Column(name="gc_code")
	private String gcCode;		//工程编号
	
	@Column(name="gc_name")		
	private String gcName;		//工程名称
	
	@Column(name="gc_address")
	private String gcAddress;		//工程地址
	
	@Column(name="wt_unit")
	private String wtUnit;		//委托单位
	
	@Column(name="jc_unit")
	private String jcUnit;
	
	@Column(name="jz_unit")
	private String jzUnit;		//监督单位
	
	@Column(name="jz_man")
	private String jzMan;		//业务负责人
	
	@Column(name="wt_man")
	private String wtMan;		//委托人
	
	@Column(name="wt_man_tel")
	private String wtManTel;
	
	@Column(name="js_unit")
	private String jsUnit;
	
	@Column(name="sj_unit")
	private String sjUnit;
	
	@Column(name="jz_num",columnDefinition="varchar(50) COMMENT '监督登记号'")
	private String jzNum;

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

	public String getGcCode() {
		return gcCode;
	}

	public void setGcCode(String gcCode) {
		this.gcCode = gcCode;
	}

	public String getGcName() {
		return gcName;
	}

	public void setGcName(String gcName) {
		this.gcName = gcName;
	}

	public String getGcAddress() {
		return gcAddress;
	}

	public void setGcAddress(String gcAddress) {
		this.gcAddress = gcAddress;
	}

	public String getWtUnit() {
		return wtUnit;
	}

	public void setWtUnit(String wtUnit) {
		this.wtUnit = wtUnit;
	}

	public String getJcUnit() {
		return jcUnit;
	}

	public void setJcUnit(String jcUnit) {
		this.jcUnit = jcUnit;
	}

	public String getJzUnit() {
		return jzUnit;
	}

	public void setJzUnit(String jzUnit) {
		this.jzUnit = jzUnit;
	}

	public String getJzMan() {
		return jzMan;
	}

	public void setJzMan(String jzMan) {
		this.jzMan = jzMan;
	}

	public String getWtMan() {
		return wtMan;
	}

	public void setWtMan(String wtMan) {
		this.wtMan = wtMan;
	}

	public String getWtManTel() {
		return wtManTel;
	}

	public void setWtManTel(String wtManTel) {
		this.wtManTel = wtManTel;
	}

	public String getJsUnit() {
		return jsUnit;
	}

	public void setJsUnit(String jsUnit) {
		this.jsUnit = jsUnit;
	}

	public String getSjUnit() {
		return sjUnit;
	}

	public void setSjUnit(String sjUnit) {
		this.sjUnit = sjUnit;
	}

	public String getJzNum() {
		return jzNum;
	}

	public void setJzNum(String jzNum) {
		this.jzNum = jzNum;
	}

}