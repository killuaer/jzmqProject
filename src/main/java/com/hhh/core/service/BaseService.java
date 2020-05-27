package com.hhh.core.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhh.core.dao.BaseDao;
import com.hhh.core.util.FundPage;

@Transactional
@Service
public class BaseService {
	
	@Autowired
	private BaseDao baseDao;

	public List<String> genCustomerId(String customerId) {
		String sql = "select * from table(genCustomerId(?1))";
		List<Object> paramList = new ArrayList<>();
		paramList.add(customerId);
		FundPage<String> results = baseDao.querySqlWithPaging(sql.toString(), paramList, null);
		return results.getContent();
	}
	
	public <T> Map<String, T> findMapByIds(Class<T> entityClass, List<String> ids) {
		return baseDao.findMapByIds(entityClass, ids);
	}
	
	public <T> Map<String, T> findMapByField(Class<T> entityClass, String fieldName, List<String> pks) {
		return baseDao.findMapByFields(entityClass, fieldName, pks);
	}
	
	public <T> Map<String, Set<T>> findMapByField(Class<T> entityClass, String fieldName, Set<Object> fieldValues) {
		return baseDao.findMapByFields(entityClass, fieldName, fieldValues);
	}
	
	public <T> List<T> findByField(Class<T> entityClass, String fieldName, Object value) {
		return baseDao.findByField(entityClass, fieldName, value);
	}
	
	public <T> List<T> findByFields(Class<T> entityClass, String fieldName, Collection<Object> values) {
		return baseDao.findByFields(entityClass, fieldName, values);
	}
	
	public <T> T findById(Class<T> entityClass, Object id) {
		return baseDao.findById(entityClass, id);
	}
	
	public <T> T findByParams(Class<T> clz, Object... params) {
		return baseDao.findByParams(clz, params);
	}
	
	public <T> List<T> findListByParams(Class<T> clz, Object... params) {
		return baseDao.findListByParams(clz, params);
	}
	
	public <T> List<Map<String, Object>> findListByParamsOrderBy(Class<T> clz,List<String> fieldNames, String orderField, boolean flashback, Object... params) {
		return baseDao.findListByParamsOrderBy(clz, fieldNames, orderField, flashback, params);
	}
	
	
	public <T> T simpleSaveEntity(T entity) {
		return baseDao.saveOrUpdate(entity);
	}
	
	public <T> List<T> saveOrUpdates(List<T> list) {
		return baseDao.saveOrUpdates(list);
	}
	
	public <T> void simpleDeleteEntity(T entity) {
		 baseDao.delete(entity);
	}
}
