package com.hhh.auth.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.List;


/**
 * The persistent class for the SYS_UCENTER_COMPANY database table.
 * 
 */
@Entity
@Table(name="SYS_UCENTER_COMPANY")
@NamedQuery(name="SysUcenterCompany.findAll", query="SELECT s FROM SysUcenterCompany s")
public class SysUcenterCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;

	private String address;

	private String code;

	private String customerid;

	@Column(name="DELEGATION_MODEL")
	private String delegationModel;

	@Column(name="FIRST_REGION")
	private String firstRegion;

	private String header;

	@Column(name="HEADER_EMAIL")
	private String headerEmail;

	@Column(name="HEADER_PHONE")
	private String headerPhone;

	@Lob
	private byte[] icon;

	private String name;

	private String phone;

	@Column(name="SECOND_REGION")
	private String secondRegion;

	@Column(name="THIRD_REGION")
	private String thirdRegion;

	@Column(name="\"TYPE\"")
	private String type;

	@Column(name="TYPE_DESC")
	private String typeDesc;

	//bi-directional many-to-one association to SysUcenterAccount
	@OneToMany(mappedBy="sysUcenterCompany")
	private List<SysUcenterAccount> sysUcenterAccounts;

	public SysUcenterCompany() {
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getDelegationModel() {
		return this.delegationModel;
	}

	public void setDelegationModel(String delegationModel) {
		this.delegationModel = delegationModel;
	}

	public String getFirstRegion() {
		return this.firstRegion;
	}

	public void setFirstRegion(String firstRegion) {
		this.firstRegion = firstRegion;
	}

	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getHeaderEmail() {
		return this.headerEmail;
	}

	public void setHeaderEmail(String headerEmail) {
		this.headerEmail = headerEmail;
	}

	public String getHeaderPhone() {
		return this.headerPhone;
	}

	public void setHeaderPhone(String headerPhone) {
		this.headerPhone = headerPhone;
	}

	public byte[] getIcon() {
		return this.icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSecondRegion() {
		return this.secondRegion;
	}

	public void setSecondRegion(String secondRegion) {
		this.secondRegion = secondRegion;
	}

	public String getThirdRegion() {
		return this.thirdRegion;
	}

	public void setThirdRegion(String thirdRegion) {
		this.thirdRegion = thirdRegion;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return this.typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public List<SysUcenterAccount> getSysUcenterAccounts() {
		return this.sysUcenterAccounts;
	}

	public void setSysUcenterAccounts(List<SysUcenterAccount> sysUcenterAccounts) {
		this.sysUcenterAccounts = sysUcenterAccounts;
	}

	public SysUcenterAccount addSysUcenterAccount(SysUcenterAccount sysUcenterAccount) {
		getSysUcenterAccounts().add(sysUcenterAccount);
		sysUcenterAccount.setSysUcenterCompany(this);

		return sysUcenterAccount;
	}

	public SysUcenterAccount removeSysUcenterAccount(SysUcenterAccount sysUcenterAccount) {
		getSysUcenterAccounts().remove(sysUcenterAccount);
		sysUcenterAccount.setSysUcenterCompany(null);

		return sysUcenterAccount;
	}

}