package com.hhh.auth.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JcXmZcgjljGrouBean  {
	private String id;
	private String wtId;
	private String customerId;
	private String gcCode;
	private String gcName;
	private String wtUnit;
	private String wtMan;
	private String wtManTel;
	private String syMan;
	private String shMan;
	private String chMan;
	private String syDate;
	private String shDate;
	private String beizhu;
	private String wtNum;
	private String phNum;
	private String prtNum;
	
	private String mqlx;
	private String jddy;
	private String jdnum;
	private String jdzdy;
	private String mqType;	

	private String note;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWtId() {
		return wtId;
	}

	public void setWtId(String wtId) {
		this.wtId = wtId;
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

	public String getWtUnit() {
		return wtUnit;
	}

	public void setWtUnit(String wtUnit) {
		this.wtUnit = wtUnit;
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

	public String getSyMan() {
		return syMan;
	}

	public void setSyMan(String syMan) {
		this.syMan = syMan;
	}

	public String getShMan() {
		return shMan;
	}

	public void setShMan(String shMan) {
		this.shMan = shMan;
	}

	public String getChMan() {
		return chMan;
	}

	public void setChMan(String chMan) {
		this.chMan = chMan;
	}

	public String getSyDate() {
		return syDate;
	}

	public void setSyDate(String syDate) {
		this.syDate = syDate;
	}

	public String getShDate() {
		return shDate;
	}

	public void setShDate(String shDate) {
		this.shDate = shDate;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getWtNum() {
		return wtNum;
	}

	public void setWtNum(String wtNum) {
		this.wtNum = wtNum;
	}

	public String getPhNum() {
		return phNum;
	}

	public void setPhNum(String phNum) {
		this.phNum = phNum;
	}

	public String getPrtNum() {
		return prtNum;
	}

	public void setPrtNum(String prtNum) {
		this.prtNum = prtNum;
	}
	
	public String getMqlx() {
		return mqlx;
	}

	public void setMqlx(String mqlx) {
		this.mqlx = mqlx;
	}

	public String getJddy() {
		return jddy;
	}

	public void setJddy(String jddy) {
		this.jddy = jddy;
	}

	public String getJdnum() {
		return jdnum;
	}

	public void setJdnum(String jdnum) {
		this.jdnum = jdnum;
	}

	public String getJdzdy() {
		return jdzdy;
	}

	public void setJdzdy(String jdzdy) {
		this.jdzdy = jdzdy;
	}

	public String getMqType() {
		return mqType;
	}

	public void setMqType(String mqType) {
		this.mqType = mqType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void Converster(JcXmZcgjljGrou w){		
		if (w.getShDate() != null){
			Date ldtDate = w.getShDate();
			DateFormat s_date = new SimpleDateFormat("yyyy-MM-dd");
			setShDate(s_date.format(ldtDate));
		}
		if (w.getSyDate() != null){
			Date ldtDate = w.getSyDate();
			DateFormat s_date = new SimpleDateFormat("yyyy-MM-dd");
			setSyDate(s_date.format(ldtDate));
		}

		setId(w.getId());
		setShMan(w.getShMan());
		setSyMan(w.getSyMan());
		setWtManTel(w.getWtManTel());
		setWtMan(w.getWtMan());
		setWtUnit(w.getWtUnit());
		setGcName(w.getGcName());
		setGcCode(w.getGcCode());
		setCustomerId(w.getCustomerId());
		setChMan(w.getChMan());
		setBeizhu(w.getBeizhu());
		setWtNum(w.getWtNum());
		setPhNum(w.getPhNum());
		setPrtNum(w.getPrtNum());		
		setJdzdy(w.getJdzdy());
		setMqType(w.getMqType());
		setJdnum(w.getJdnum());
		setNote(w.getNote());
		setJddy(w.getJddy());
		setMqlx(w.getMqlx());
	}
}