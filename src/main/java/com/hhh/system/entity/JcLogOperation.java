package com.hhh.system.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the JC_LOG_OPERATION database table.
 * 
 */
@Entity
@Table(name="JC_LOG_OPERATION")
@NamedQuery(name="JcLogOperation.findAll", query="SELECT o FROM JcLogOperation o")
public class JcLogOperation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;

	@Column(name="\"TYPE\"")
	private Integer type;
	
	@Column(name="MODULE_FIRST")
	private String moduleFirst;
	
	@Column(name="MODULE_SECOND")
	private String moduleSecond;
	
	@Column(name="MODULE_THIRD")
	private String moduleThird;
	
	@Column(name="OP_CONTENT")
	private String opContent;
	
	@Column(name="OP_RESULT")
	private String opResult;
	
	@Column(name="OP_IP")
	private String opId;
	
	private String appname;
	
	@Column(name="CUSTOMER_ID")
	private String customerId;
	
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

	public JcLogOperation() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getModuleFirst() {
		return moduleFirst;
	}

	public void setModuleFirst(String moduleFirst) {
		this.moduleFirst = moduleFirst;
	}

	public String getModuleSecond() {
		return moduleSecond;
	}

	public void setModuleSecond(String moduleSecond) {
		this.moduleSecond = moduleSecond;
	}

	public String getModuleThird() {
		return moduleThird;
	}

	public void setModuleThird(String moduleThird) {
		this.moduleThird = moduleThird;
	}

	public String getOpContent() {
		return opContent;
	}

	public void setOpContent(String opContent) {
		this.opContent = opContent;
	}

	public String getOpResult() {
		return opResult;
	}

	public void setOpResult(String opResult) {
		this.opResult = opResult;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

}