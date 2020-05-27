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
 * The persistent class for the SYS_UCENTER_PERMISSION database table.
 * 
 */
@Entity
@Table(name="SYS_UCENTER_PERMISSION")
@NamedQuery(name="SysUcenterPermission.findAll", query="SELECT s FROM SysUcenterPermission s")
public class SysUcenterPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	private String id;

	@Column(name="\"ENABLE\"")
	private Integer enable;

	@Column(name="RESOURCES_ID")
	private String resourcesId;

	@Column(name="RES_TYPE")
	private Integer resType;//0菜单，1站点，2部门，3检测项目，4按钮

	@Column(name="ROLE_ID")
	private String roleId;

	public SysUcenterPermission() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getResourcesId() {
		return this.resourcesId;
	}

	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}

	public Integer getResType() {
		return this.resType;
	}

	public void setResType(Integer resType) {
		this.resType = resType;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}