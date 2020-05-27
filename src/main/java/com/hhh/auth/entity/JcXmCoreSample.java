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
@Table(name="JC_XM_CORE_SAMPLE")
@NamedQuery(name="JcXmCoreSample.findAll", query="SELECT s FROM JcXmCoreSample s")
public class JcXmCoreSample implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;
	
	@Column(name="CUSTOMER_ID")
	private String customerId;
	
	@Column(name="gc_code")
	private String gcCode;
	
	@Column(name="gc_name")
	private String gcName;
	
	@Column(name="gc_address")
	private String gcAddress;
	
	@Column(name="wt_unit")
	private String wtUnit;
	
	@Column(name="jc_unit")
	private String jcUnit;
	
	@Column(name="jz_unit")
	private String jzUnit;
	
	@Column(name="jz_man")
	private String jzMan;
	
	@Column(name="wt_man")
	private String wtMan;
	
	@Column(name="wt_man_tel")
	private String wtManTel;
	
	@Column(name="js_unit")
	private String jsUnit;
	
	@Column(name="sj_unit")
	private String sjUnit;
	
	@Column(name="QD_DATE")
	private Date qdDate;
	
	@Column(name="SJ_DATE") 
	private Date sjDate;
	
	@Column(name="JG_DATE")
	private Date jgDate;
	
	private String mqmj;
	@Column(name="MQ_TYPE")
	private String mqType;
	
	@Column(name="WT_MONEY")
	private String wtMoney;
	
	private String qdfw;
	
	private String fy;
	
	private String jybz;

	private String maxbg;
	
	private String kzqd;
	
	@Column(name="DMCC_TYPE") 
	private String dmccType;
	
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
	
	@Column(name="JCXM_ID")
	private String jcxmId;
	
	@Column(name="WT_NUM") 
	private String wtNum;
	
	@Column(name="PH_NUM") 
	private String phNum;
	
	@Column(name="PRT_NUM") 
	private String prtNum;
	
	@Column(name="login_name") 
	private String loginName;
	private String names;
	
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

	public Date getQdDate() {
		return qdDate;
	}

	public void setQdDate(Date qdDate) {
		this.qdDate = qdDate;
	}

	public Date getSjDate() {
		return sjDate;
	}

	public void setSjDate(Date sjDate) {
		this.sjDate = sjDate;
	}

	public Date getJgDate() {
		return jgDate;
	}

	public void setJgDate(Date jgDate) {
		this.jgDate = jgDate;
	}

	public String getMqmj() {
		return mqmj;
	}

	public void setMqmj(String mqmj) {
		this.mqmj = mqmj;
	}

	public String getMqType() {
		return mqType;
	}

	public void setMqType(String mqType) {
		this.mqType = mqType;
	}

	public String getWtMoney() {
		return wtMoney;
	}

	public void setWtMoney(String wtMoney) {
		this.wtMoney = wtMoney;
	}

	public String getQdfw() {
		return qdfw;
	}

	public void setQdfw(String qdfw) {
		this.qdfw = qdfw;
	}

	public String getFy() {
		return fy;
	}

	public void setFy(String fy) {
		this.fy = fy;
	}

	public String getJybz() {
		return jybz;
	}

	public void setJybz(String jybz) {
		this.jybz = jybz;
	}

	public String getMaxbg() {
		return maxbg;
	}

	public void setMaxbg(String maxbg) {
		this.maxbg = maxbg;
	}

	public String getKzqd() {
		return kzqd;
	}

	public void setKzqd(String kzqd) {
		this.kzqd = kzqd;
	}

	public String getDmccType() {
		return dmccType;
	}

	public void setDmccType(String dmccType) {
		this.dmccType = dmccType;
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

	public String getJcxmId() {
		return jcxmId;
	}

	public void setJcxmId(String jcxmId) {
		this.jcxmId = jcxmId;
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
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public void Converster(JcXmCoreSampleBean w){
		if (w.getQdDate() != null && w.getQdDate().length()>0){
			Timestamp qr_date = Timestamp.valueOf(w.getQdDate()+" 00:00:00");
			setQdDate(qr_date);
		}
		if (w.getShDate() != null && w.getShDate().length()>0){
			Timestamp qr_date = Timestamp.valueOf(w.getShDate()+" 00:00:00");
			setShDate(qr_date);
		}
		if (w.getSyDate() != null && w.getSyDate().length()>0){
			Timestamp qr_date = Timestamp.valueOf(w.getSyDate()+" 00:00:00");
			setSyDate(qr_date);
		}
		if (w.getJgDate() != null && w.getJgDate().length()>0){
			Timestamp qr_date = Timestamp.valueOf(w.getJgDate()+" 00:00:00");
			setJgDate(qr_date);
		}
		if (w.getSjDate() != null && w.getSjDate().length()>0){
			Timestamp qr_date = Timestamp.valueOf(w.getSjDate()+" 00:00:00");
			setSjDate(qr_date);
		}
		if (w.getQdDate() != null && w.getQdDate().length()>0){
			Timestamp qr_date = Timestamp.valueOf(w.getQdDate()+" 00:00:00");
			setQdDate(qr_date);
		}
		
		setId(w.getId());
		setShMan(w.getShMan());
		setSyMan(w.getSyMan());
		setDmccType(w.getDmccType());
		setKzqd(w.getKzqd());
		setMaxbg(w.getMaxbg());
		setJybz(w.getJybz());
		setFy(w.getFy());
		setQdfw(w.getQdfw());
		setWtMoney(w.getWtMoney());
		setMqType(w.getMqType());
		setMqmj(w.getMqmj());
		setSjUnit(w.getSjUnit());
		setJsUnit(w.getJsUnit());
		setWtManTel(w.getWtManTel());
		setWtMan(w.getWtMan());
		setJzMan(w.getJzMan());
		setJzUnit(w.getJzUnit());
		setJcUnit(w.getJcUnit());
		setWtUnit(w.getWtUnit());
		setGcAddress(w.getGcAddress());
		setGcName(w.getGcName());
		setGcCode(w.getGcCode());
		setCustomerId(w.getCustomerId());
		setChMan(w.getChMan());
		setBeizhu(w.getBeizhu());
		setJcxmId(w.getJcxmId());
		setWtNum(w.getWtNum());
		setPhNum(w.getPhNum());
		setPrtNum(w.getPrtNum());
		setNames(w.getNames());
		setLoginName(w.getLoginName());
	}
}