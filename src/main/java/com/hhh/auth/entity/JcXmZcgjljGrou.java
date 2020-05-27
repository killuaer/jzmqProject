package com.hhh.auth.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

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
@Table(name="JC_XM_ZCGJLJ_GROU")
@NamedQuery(name="JcXmZcgjljGrou.findAll", query="SELECT s FROM JcXmZcgjljGrou s")
public class JcXmZcgjljGrou implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;
	
	@Column(name="WT_ID")
	private String wtId;
	
	@Column(name="CUSTOMER_ID")
	private String customerId;
	
	@Column(name="gc_code")
	private String gcCode;
	
	@Column(name="gc_name")
	private String gcName;
	
	@Column(name="wt_unit")
	private String wtUnit;
	
	@Column(name="wt_man")
	private String wtMan;
	
	@Column(name="wt_man_tel")
	private String wtManTel;
	
	@Column(name="SY_MAN")
	private String syMan;
	
	@Column(name="SH_MAN")
	private String shMan;
	
	@Column(name="CH_MAN")
	private String chMan;
	
	@Column(name="SY_DATE") 
	private Date syDate;
	
	@Column(name="SH_DATE")
	private Date shDate;
	
	@Column(name="BEIZHU") 
	private String beizhu;
	
	@Column(name="WT_NUM") 
	private String wtNum;
	
	@Column(name="PH_NUM") 
	private String phNum;
	
	@Column(name="PRT_NUM") 
	private String prtNum;
	
    
	private String mqlx;
	private String jddy;
	private String jdnum;
	private String jdzdy;
	@Column(name="MQ_TYPE")
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

	public Date getSyDate() {
		return syDate;
	}

	public void setSyDate(Date syDate) {
		this.syDate = syDate;
	}

	public Date getShDate() {
		return shDate;
	}

	public void setShDate(Date shDate) {
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

	

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getJdnum() {
		return jdnum;
	}

	public void setJdnum(String jdnum) {
		this.jdnum = jdnum;
	}
	
	public String getMqType() {
		return mqType;
	}

	public void setMqType(String mqType) {
		this.mqType = mqType;
	}
	
	public String getJdzdy() {
		return jdzdy;
	}

	public void setJdzdy(String jdzdy) {
		this.jdzdy = jdzdy;
	}

	public void Converster(JcXmZcgjljGrouBean w){
		if (w.getShDate() != null && w.getShDate().length()>0){
			Timestamp qr_date = Timestamp.valueOf(w.getShDate()+" 00:00:00");
			setShDate(qr_date);
		}
		if (w.getSyDate() != null && w.getSyDate().length()>0){
			Timestamp qr_date = Timestamp.valueOf(w.getSyDate()+" 00:00:00");
			setSyDate(qr_date);
		}
		setId(w.getId());
		setWtId(w.getWtId());
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