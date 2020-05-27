package com.hhh.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.auth.entity.SysUcenterAccount;
import com.hhh.core.controller.BaseController;
import com.hhh.core.service.BaseService;
import com.hhh.core.util.DESUtil;
import com.hhh.core.util.MD5Util;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.log.HHHLog;

@Controller
@RequestMapping(value = "/admin")
public class LoginController extends BaseController{

	@Autowired
	private BaseService baseService;
	

	@HHHLog(moduleFirst = "登录模块", info = "登录请求")
	@RequestMapping(value = "/login", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Map<String, Object> adminLogin(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> user = this.getParamMap();
		Subject subject = SecurityUtils.getSubject();
		//解密
		String userdecode = null;
		String pwdecode = null;
		try {
			userdecode = DESUtil.decryption((String)user.get("username"));
			pwdecode = DESUtil.decryption((String)user.get("password"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
				
		UsernamePasswordToken token = new UsernamePasswordToken(userdecode,pwdecode);
//		token.setRememberMe((boolean)user.get("isRememberMe"));
		/*session.removeAttribute("variable");
		//验证是否是弱密码
		String detection_pwd = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,30}$";
		if(!pwdecode.matches(detection_pwd)) {
			session.setAttribute("variable", "true");//variable用于前台判断获取不一样的修改密码框
		}
		//获取缓存中的验证码和请求保存过来的验证码
		String code = (String) session.getAttribute("code");
		String requestCode = request.getParameter("code");  
		
		//如果验证不成功，则返回登录界面
		session.removeAttribute("code");//去掉session
		if(!code.equals(requestCode)) {
			return "redirect:/admin/login";
		}*/
		try {
			subject.login(token);
//			LogLoginBean log = new LogLoginBean("", "", request);
//			logService.saveLogLogin(log);
			map.put("status", "success");
			return map;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			token.clear();
			session.setAttribute("logininfo", "error");
			map.put("status", "fail");
			return map;
		}
	}

	@RequestMapping(value = "/logout")
	@ResponseBody
	public Map<String, Object> logout() {
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.logout();
		} catch (Exception e) {
			
		}
		map.put("status", "success");
		return map;
	}

	/**
	 * 修改密码
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @param model
	 * @return flag 是否成功标志
	 */
	@HHHLog(moduleFirst = "登录模块", info = "编辑密码")
	@RequestMapping(value = "/updPassword", method = RequestMethod.POST)
	@ResponseBody
	public String updPassword(String oldPassword, String newPassword, Model model,HttpSession session) {
		Map<String, Object> user = ShiroUtils.getUser();
		//按照约定好的加密方法，加密上传的密码
		String oldPwd = MD5Util.string2MD5(oldPassword).toUpperCase();
		String newPwd = MD5Util.string2MD5(newPassword).toUpperCase();
//		session.removeAttribute("variable");//去除强制密码修改验证
		//校验密码以及在校验通过后修改密码
		boolean flag = true;
		SysUcenterAccount account = this.baseService.findByParams(SysUcenterAccount.class, "loginName", user.get("loginName"));
		//校验密码
		if(!oldPwd.equalsIgnoreCase(account.getPassword())){
			flag = false;
		}
		//设置salt为用户名
		account.setSalt("");
		account.setPassword(newPwd);
		this.baseService.simpleSaveEntity(account);
		return flag ? "success" : "fail";
	}
}
