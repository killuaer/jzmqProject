package com.hhh.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.auth.entity.JcXmCoreSample;
import com.hhh.auth.entity.JcXmCoreSampleBean;
import com.hhh.auth.entity.JcXmGrou;
import com.hhh.auth.entity.JcXmGrouBean;
import com.hhh.auth.entity.JcXmZcgjljGrou;
import com.hhh.auth.entity.JcXmZcgjljGrouBean;
import com.hhh.auth.entity.JcXmcsInfo;
import com.hhh.auth.service.JcXmCoreSampleService;
import com.hhh.auth.service.JcXmGrouService;
import com.hhh.auth.service.JcXmZcgjljGrouService;
import com.hhh.auth.service.JcXmcsInfoService;
import com.hhh.auth.service.SysUcenterRoleService;
import com.hhh.core.controller.BaseController;
import com.hhh.core.model.WebServiceConstants;
import com.hhh.core.util.LayPageParam;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.log.HHHLog;

@Controller
@RequestMapping("/wtgl")
public class WtglController extends BaseController {
	@Autowired
	private SysUcenterRoleService roleService;
    @Autowired
    private JcXmcsInfoService jcXmService;
    
    @Autowired
    private JcXmZcgjljGrouService jcXmZcgjljService;
    @Autowired
    private JcXmGrouService jcXmGrouService;
    @Autowired
    private JcXmCoreSampleService jcXmCoreServive;

	@HHHLog(moduleFirst = "系统管理",moduleSecond = "检测项目设置", info = "项目树")
	@RequestMapping(value = "/moveJcxm")
	@ResponseBody
	public List<Map<String, Object>> moveJcxm(String id, String type) {
		Map<String, Object> map = new HashMap<>();	
		List<Map<String, Object>> nodelist = new ArrayList<>();
		List<Map<String, Object>> list = new ArrayList<>();
		List<JcXmcsInfo> jcxmlist =		this.jcXmService.findAll();
		if(jcxmlist.size()>0){
			for(JcXmcsInfo jcxm: jcxmlist){
				Map<String, Object> subnode = new HashMap<>();
				if("1".equals(jcxm.getType())){
					map.put("id", jcxm.getId());
					map.put("name", jcxm.getXmName());
					map.put("spread", true);
				}
				if("2".equals(jcxm.getType())){
					subnode.put("id", jcxm.getId());
					subnode.put("name", jcxm.getXmName());
					nodelist.add(subnode);
				}
			}
			map.put("children", nodelist);
		}
		list.add(map);
		return list;
	}
	
	@HHHLog(moduleFirst = "系统管理",moduleSecond = "检测项目设置", info = "子单元分项")
	@RequestMapping(value = "/listJcxmcs")
	@ResponseBody
	public Map<String, Object> listJcxmcs(HttpServletRequest req, LayPageParam pageParam) {
		Map<String, Object> map = new HashMap<>();
        String pid = req.getParameter("pid");
		List<JcXmcsInfo> jcxmList = new ArrayList<JcXmcsInfo>();
		List<JcXmcsInfo> list = jcXmService.findByParentId(pid);
		if(list.size()>0){
			for(JcXmcsInfo entity: list){
				if("3".equals(entity.getType())){
					jcxmList.add(entity);
				}
			}
		}
		map.put("count", 1);			
		map.put("code", 0);
		map.put("msg", "");
		map.put("data", jcxmList);
		return map;
	}
	
	@HHHLog(moduleFirst = "委托管理",moduleSecond = "委托管理", info = "项目记录数据列表")
	@RequestMapping(value = "/listJcxmCore")
	@ResponseBody
	public Map<String, Object> listJcxmCore(HttpServletRequest req, LayPageParam pageParam) {
		String customerId = WebServiceConstants.CUSTOMERID;	
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("wtNum", req.getParameter("wtNum"));
		paramMap.put("gcName", req.getParameter("gcName"));
		paramMap.put("wtUnit", req.getParameter("wtUnit"));
		paramMap.put("gcAddress", req.getParameter("gcAddress"));
		paramMap.put("qdfw", req.getParameter("qdfw"));
		paramMap.put("customerId", req.getParameter(customerId));
		List<JcXmCoreSample> list = jcXmCoreServive.listJcxm(paramMap, pageReq);
		List<JcXmCoreSampleBean> listBean = new ArrayList<JcXmCoreSampleBean>();
		
		if (list.size()>0) {
			for(JcXmCoreSample entity :list){
				JcXmCoreSampleBean bean = new JcXmCoreSampleBean();
				bean.Converster(entity);
				listBean.add(bean);
			}
			map.put("count", pageParam.getPage());			
		} else {
			map.put("count", 0);
		}
		map.put("code", 0);
		map.put("msg", "");
		map.put("data", listBean);
		return map;
	}
	
	@HHHLog(moduleFirst = "委托管理",moduleSecond = "委托管理", info = "保存记录")
	@RequestMapping(value = "/jcXmCoreSave")
	@ResponseBody
	public Map<String, Object> jcXmCoreSave(JcXmCoreSampleBean bean,HttpServletRequest req){
		String names = ShiroUtils.getFullname();
		String logName = ShiroUtils.getUsername();
		JcXmCoreSample entity = new JcXmCoreSample();
		String customerId = WebServiceConstants.CUSTOMERID;
		entity.Converster(bean);
		entity.setCustomerId(customerId);
		entity.setLoginName(logName);
		entity.setNames(names);
		Map<String, Object> map = new HashMap<>();
		JcXmCoreSample entityJcxm  = jcXmCoreServive.save(entity);
		if(entityJcxm!=null){
			map.put("status", "success");
		}else{
			map.put("status", "fail");
		}
		return map;
	}
	
	@HHHLog(moduleFirst = "委托管理",moduleSecond = "新增委托", info = "子单元项目记录数据列表")
	@RequestMapping(value = "/listJcxmZcgjljGrou")
	@ResponseBody
	public Map<String, Object> listJcxmZcgjljGrou(HttpServletRequest req, LayPageParam pageParam) {
		String customerId = WebServiceConstants.CUSTOMERID;
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("wtId", req.getParameter("id"));
		paramMap.put("customerId", req.getParameter(customerId));
		List<JcXmZcgjljGrou> list = jcXmZcgjljService.listJcxm(paramMap, pageReq);
		if (list.size()>0) {
			map.put("count", pageParam.getPage());			
		} else {
			map.put("count", 0);
		}
		map.put("code", 0);
		map.put("msg", "");
		map.put("data", list);
		return map;
	}
	
	@HHHLog(moduleFirst = "委托管理",moduleSecond = "新增委托", info = "保存子单元项目记录")
	@RequestMapping(value = "/jcXmZcgjljGrouSave")
	@ResponseBody
	public Map<String, Object> jcXmZcgjljGrouSave(JcXmZcgjljGrouBean bean,HttpServletRequest req){
		String names = ShiroUtils.getFullname();
		JcXmZcgjljGrou entity = new JcXmZcgjljGrou();
		String customerId = WebServiceConstants.CUSTOMERID;
		entity.Converster(bean);
		entity.setCustomerId(customerId);
		entity.setSyMan(names);
		Map<String, Object> map = new HashMap<>();
		JcXmZcgjljGrou entityJcxm  = jcXmZcgjljService.save(entity);
		if(entityJcxm!=null){
			map.put("status", "success");
		}else{
			map.put("status", "fail");
		}
		return map;
	}
	
	@HHHLog(moduleFirst = "委托管理",moduleSecond = "委托管理", info = "项目记录数据列表")
	@RequestMapping(value = "/listJcxmGrou")
	@ResponseBody
	public Map<String, Object> listJcxmGrou(HttpServletRequest req, LayPageParam pageParam) {
		String customerId = WebServiceConstants.CUSTOMERID;	
		PageRequest pageReq = pageParam.genPageParem().getPageRequest();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("wtNum", req.getParameter("wtNum"));
		paramMap.put("gcName", req.getParameter("gcName"));
		paramMap.put("wtUnit", req.getParameter("wtUnit"));
		paramMap.put("gcAddress", req.getParameter("gcAddress"));
		paramMap.put("qdfw", req.getParameter("qdfw"));
		paramMap.put("customerId", req.getParameter(customerId));
		List<JcXmGrou> list = jcXmGrouService.listJcxm(paramMap, pageReq);
		List<JcXmGrouBean> listBean = new ArrayList<JcXmGrouBean>();
		
		if (list.size()>0) {
			for(JcXmGrou entity :list){
				JcXmGrouBean bean = new JcXmGrouBean();
				bean.Converster(entity);
				listBean.add(bean);
			}
			map.put("count", pageParam.getPage());			
		} else {
			map.put("count", 0);
		}
		map.put("code", 0);
		map.put("msg", "");
		map.put("data", listBean);
		return map;
	}
}
