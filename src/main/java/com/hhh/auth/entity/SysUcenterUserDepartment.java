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
 * The persistent class for the SYS_UCENTER_USER_DEPARTMENT database table.
 * 
 */
@Entity
@Table(name="SYS_UCENTER_USER_DEPARTMENT")
@NamedQuery(name="SysUcenterUserDepartment.findAll", query="SELECT s FROM SysUcenterUserDepartment s")
public class SysUcenterUserDepartment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;

	@Column(name="ACCOUNT_ID")
	private String accountId;

	@Column(name="DEPART_ID")
	private String departId;

	public SysUcenterUserDepartment() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

}