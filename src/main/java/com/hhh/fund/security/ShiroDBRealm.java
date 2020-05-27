package com.hhh.fund.security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hhh.auth.entity.SysUcenterAccount;
import com.hhh.auth.entity.SysUcenterRole;
import com.hhh.auth.entity.SysUcenterUserDepartment;
import com.hhh.core.service.BaseService;
import com.hhh.core.util.ShiroPrincipal;
import com.hhh.core.util.StringUtil;
public class ShiroDBRealm extends AuthorizingRealm {

	@Autowired
	private BaseService baseService;

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SysUcenterAccount user = this.baseService.findByParams(SysUcenterAccount.class, "loginName", token.getUsername());
		if (user == null) {
			throw new UnknownAccountException("用户名/密码错误");
		} else {
			Map<String, Object> umap = StringUtil.object2Map(user);
			SysUcenterUserDepartment ud = this.baseService.findByParams(SysUcenterUserDepartment.class, "accountId", user.getId());
			if (ud != null) {
				umap.put("deptId", ud.getDepartId());
			}
			ShiroPrincipal subject = new ShiroPrincipal(umap);
			subject.setAuthorized(true);
			String pass1 = String.valueOf(token.getPassword());
			String pd1 = "";
			System.out.println(pass1 + "pass" + pass1.indexOf(";;;"));
			if (pass1.indexOf(";;;") != -1) {
				String[] matcher = pass1.split(";;;");
				pd1 = matcher[1];
			} else if (!StringUtils.hasLength(user.getSalt())) {
				CustomCredentialsMatcher matcher3 = new CustomCredentialsMatcher();
				this.setCredentialsMatcher(matcher3);
				return new SimpleAuthenticationInfo(subject, user.getPassword(), this.getName());
			}

			if (pd1.equals("key")) {
				EmptyCredentialsMatcher matcher2 = new EmptyCredentialsMatcher();
				this.setCredentialsMatcher(matcher2);
				return new SimpleAuthenticationInfo(subject, token.getPassword(), this.getName());
			} else {
				HashedCredentialsMatcher matcher1 = new HashedCredentialsMatcher("md5");
				matcher1.setHashIterations(2);
				this.setCredentialsMatcher(matcher1);
				return new SimpleAuthenticationInfo(subject, user.getPassword(), Util.bytes(user.getSalt()),
						this.getName());
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		ShiroPrincipal subject = (ShiroPrincipal) super.getAvailablePrincipal(principals);
		String username = subject.getUsername();
		if (!subject.isAuthorized()) {
			SysUcenterAccount account = this.baseService.findByParams(SysUcenterAccount.class, "loginName", username);
			Set beans = account.getSysUcenterRoles();
			HashSet roles = new HashSet();
			if (beans != null) {
				Iterator arg8 = beans.iterator();

				while (arg8.hasNext()) {
					SysUcenterRole bean = (SysUcenterRole) arg8.next();
					roles.add(bean.getName());
				}
			}

			subject.setRoles(roles);
			subject.setAuthorized(true);
		}

		if (subject.getAuthorities() != null) {
			info.addStringPermissions(subject.getAuthorities());
		}

		if (subject.getRoles() != null) {
			info.addRoles(subject.getRoles());
		}

		return info;
	}

	@PostConstruct
	public void initCredentialsMatcher() {
	}
}
