package com.hhh.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the JC_XTSZ_CODE_STATUS database table.
 * 
 */
@Entity
@Table(name="JC_XTSZ_CODE_STATUS")
@NamedQuery(name="JcXtszCodeStatus.findAll", query="SELECT j FROM JcXtszCodeStatus j")
public class JcXtszCodeStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;

	@Column(name="CODE_MAX")
	private String codeMax;

	@Column(name="CODE_PREFIX")
	private String codePrefix;

	@Column(name="CODE_TYPE")
	private String codeType;

	@Column(name="CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="CUSTOMER_ID")
	private String customerId;

	@Column(name="IS_LOCKED")
	private Integer isLocked;

	@Column(name="UPDATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	public JcXtszCodeStatus() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeMax() {
		return this.codeMax;
	}

	public void setCodeMax(String codeMax) {
		this.codeMax = codeMax;
	}

	public String getCodePrefix() {
		return this.codePrefix;
	}

	public void setCodePrefix(String codePrefix) {
		this.codePrefix = codePrefix;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

}