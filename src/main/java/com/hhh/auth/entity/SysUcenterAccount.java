package com.hhh.auth.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.hhh.core.model.State;
import com.hhh.core.model.Whether;



/**
 * The persistent class for the SYS_UCENTER_ACCOUNT database table.
 * 
 */
@Entity
@Table(name="SYS_UCENTER_ACCOUNT")
@NamedQuery(name="SysUcenterAccount.findAll", query="SELECT s FROM SysUcenterAccount s")
public class SysUcenterAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;
	
	@Column(name="CUSTOMER_ID")
	private String customerId;

	private String email;

	@Enumerated(EnumType.ORDINAL)
	@Column(name="IS_ADMIN")
	private Whether isAdmin;

	@Column(name="KEY_ID")
	private String keyId;

	@Column(name="LOGIN_NAME")
	private String loginName;

	private String name;

	private String password;

	private String phone;

	private String salt;

	@Enumerated(EnumType.ORDINAL)
	private State status;
	
	private Integer ord;
	
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

	@ManyToOne
	@JoinColumn(name="COMPANY_ID")
	private SysUcenterCompany sysUcenterCompany;

	@OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="USERINFO_ID")
	private SysUcenterUserinfo sysUcenterUserinfo;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "sys_ucenter_userrole", joinColumns = {@JoinColumn(name = "ACCOUNT_ID")}, inverseJoinColumns = {
	@JoinColumn(name = "ROLE_ID")})
	private Set<SysUcenterRole> sysUcenterRoles;
	
	

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

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

	public Whether getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Whether isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public State getStatus() {
		return status;
	}

	public void setStatus(State status) {
		this.status = status;
	}

	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
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

	public SysUcenterCompany getSysUcenterCompany() {
		return this.sysUcenterCompany;
	}

	public void setSysUcenterCompany(SysUcenterCompany sysUcenterCompany) {
		this.sysUcenterCompany = sysUcenterCompany;
	}

	public SysUcenterUserinfo getSysUcenterUserinfo() {
		return this.sysUcenterUserinfo;
	}

	public void setSysUcenterUserinfo(SysUcenterUserinfo sysUcenterUserinfo) {
		this.sysUcenterUserinfo = sysUcenterUserinfo;
	}

	public Set<SysUcenterRole> getSysUcenterRoles() {
		return sysUcenterRoles;
	}

	public void setSysUcenterRoles(Set<SysUcenterRole> sysUcenterRoles) {
		this.sysUcenterRoles = sysUcenterRoles;
	}

}