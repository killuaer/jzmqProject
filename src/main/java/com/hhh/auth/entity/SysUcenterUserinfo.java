package com.hhh.auth.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the SYS_UCENTER_USERINFO database table.
 * 
 */
@Entity
@Table(name="SYS_UCENTER_USERINFO")
@NamedQuery(name="SysUcenterUserinfo.findAll", query="SELECT s FROM SysUcenterUserinfo s")
public class SysUcenterUserinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;
	
	private String idcard;

	private String address;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String gender;
	
	private String jobnumber;
	
	@Column(name="\"POSITION\"")
	private String position;
	
	private String busy;
	
	@Column(name="CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column(name="CREATOR_ID")
	private String creatorId;
	
	@Column(name="UPDATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Column(name="UPDATE_USER_ID")
	private String updateUserId;
	//bi-directional many-to-one association to SysUcenterAccount
	@OneToMany(mappedBy="sysUcenterUserinfo")
	private List<SysUcenterAccount> sysUcenterAccounts;

	public SysUcenterUserinfo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public String getBusy() {
		return busy;
	}

	public void setBusy(String busy) {
		this.busy = busy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public List<SysUcenterAccount> getSysUcenterAccounts() {
		return this.sysUcenterAccounts;
	}

	public void setSysUcenterAccounts(List<SysUcenterAccount> sysUcenterAccounts) {
		this.sysUcenterAccounts = sysUcenterAccounts;
	}

	public SysUcenterAccount addSysUcenterAccount(SysUcenterAccount sysUcenterAccount) {
		getSysUcenterAccounts().add(sysUcenterAccount);
		sysUcenterAccount.setSysUcenterUserinfo(this);

		return sysUcenterAccount;
	}

	public SysUcenterAccount removeSysUcenterAccount(SysUcenterAccount sysUcenterAccount) {
		getSysUcenterAccounts().remove(sysUcenterAccount);
		sysUcenterAccount.setSysUcenterUserinfo(null);

		return sysUcenterAccount;
	}

}