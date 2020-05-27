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
 * The persistent class for the JC_XMCS_INFO database table.
 * 
 */
@Entity
@Table(name="JC_XMCS_INFO")
@NamedQuery(name="JcXmcsInfo.findAll", query="SELECT s FROM JcXmcsInfo s")
public class JcXmcsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;
	
	@Column(name="CUSTOMER_ID")
	private String customerId;
	
	@Column(name="XM_NAME")
	private String xmName;
	
	@Column(name="PARENT_ID")
	private String parentId;
	
	@Column(name="CS_NAME")
	private String csName;
	
	@Column(name="CS_NAME_R")
	private String csNameR;
	
	private String type;
	
	@Column(name="XMCS_ID")
	private String xmcsId;
	
	private String state;

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

	public String getXmName() {
		return xmName;
	}

	public void setXmName(String xmName) {
		this.xmName = xmName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCsName() {
		return csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

	public String getCsNameR() {
		return csNameR;
	}

	public void setCsNameR(String csNameR) {
		this.csNameR = csNameR;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getXmcsId() {
		return xmcsId;
	}

	public void setXmcsId(String xmcsId) {
		this.xmcsId = xmcsId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}