package com.hhh.core.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.core.util.FundPage;
import com.hhh.core.util.StringUtil;

@Repository
@Transactional
public class BaseDao  {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public <T> T saveOrUpdate (T entity) {
		return entityManager.merge(entity);
	}
	
	public <T> List<T> saveOrUpdates(List<T> list) {
		List<T> results = new ArrayList<>();
		for (T t : list) {
			results.add(entityManager.merge(t));
		}
		return results;
	}
	
	
	public void delete (Object entity) {
		entityManager.remove(entity);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findListByParams(Class<T> clz, Object... params) {
		StringBuilder sql = new StringBuilder("SELECT t FROM " + clz.getSimpleName() + " t WHERE 1=1");
		for (int i = 0; i < params.length; i = i + 2) {
			sql.append(" and " + params[i] + " = :" + params[i]);
		}
		Query query = entityManager.createQuery(sql.toString());
		for (int i = 0; i < params.length; i = i + 2) {
			query.setParameter(params[i].toString(), params[i + 1]);
		}
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param clz 实体
	 * @param fieldNames 要取的字段
	 * @param orderField 要排序的字段
	 * @param flashback 是否倒叙，true为倒叙
	 * @param params 过滤条件
	 * @return
	 */
	public <T> List<Map<String, Object>> findListByParamsOrderBy(Class<T> clz,List<String> fieldNames, String orderField, boolean flashback, Object... params) {
		StringBuilder sql = new StringBuilder("SELECT");
		if (fieldNames != null && fieldNames.size() > 0) {
			for (int i = 0; i < fieldNames.size(); i++) {
				String fieldName = fieldNames.get(i);
				if (i == 0) {
					sql.append(" t." + fieldName);
				} else {
					sql.append(", t." + fieldName);
				}
			}
			
		} else {
			sql.append(" t");
		}
		sql.append(" FROM " + clz.getSimpleName() + " t WHERE 1=1");
		for (int i = 0; i < params.length; i = i + 2) {
			sql.append(" and " + params[i] + " = :" + params[i]);
		}
		if (!StringUtil.isEmpty(orderField)) {
			sql.append(" ORDER BY t." + orderField);
			if (flashback) {
				sql.append(" DESC");
			}
		}
		Query query = entityManager.createQuery(sql.toString());
		for (int i = 0; i < params.length; i = i + 2) {
			query.setParameter(params[i].toString(), params[i + 1]);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		if (fieldNames != null && fieldNames.size() > 0) {
			List<Object[]> objList = query.getResultList();
			for (int i = 0; i < objList.size(); i++) {
				Object[] objArr = objList.get(i);
				Map<String, Object> map = new HashMap<>();
				for (int j = 0; j < objArr.length; j++) {
					map.put(fieldNames.get(j), objArr[j]);
				}
				list.add(map);
			}
		} else {
			List<T> objList = query.getResultList();
			for (T obj : objList) {
				Map<String, Object> map = StringUtil.object2Map(obj);
				list.add(map);
			}
		}
		return list;
	}
	
	public Boolean isExistsByParams(Class<?> clz, Object... params) {
		StringBuilder sql = new StringBuilder("SELECT 1 FROM " + clz.getSimpleName() + " t WHERE 1=1");
		for (int i = 0; i < params.length; i = i + 2) {
			sql.append(" and " + params[i] + " = :" + params[i]);
		}
		Query query = entityManager.createQuery(sql.toString());
		for (int i = 0; i < params.length; i = i + 2) {
			query.setParameter(params[i].toString(), params[i + 1]);
		}
		
		int result = query.getFirstResult();
		return result == 1;
	}

	
	public Boolean existsByParams(Class<?> clz, Object... params) {
		StringBuilder sql = new StringBuilder("SELECT 1 FROM " + clz.getSimpleName() + " t WHERE 1=1");
		for (int i = 0; i < params.length; i = i + 2) {
			sql.append(" and " + params[i] + " = :" + params[i]);
		}
		Query query = entityManager.createQuery(sql.toString());
		for (int i = 0; i < params.length; i = i + 2) {
			query.setParameter(params[i].toString(), params[i + 1]);
		}
		List<?> result = query.getResultList();
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}

	
	public <T> T findById(Class<T> entityClass, Object id) {
		return entityManager.find(entityClass, id);
	}

	public <T> T findById(Class<T> entityClass, Integer id) {
		return entityManager.find(entityClass, id);
	}
	
	public <T> List<T> findByIds(Class<T> entityClass, List<String> ids) {
		String priKey = getPriFieldName(entityClass);
		if (priKey == null) return null;
		
		if (ids == null || ids.isEmpty()) {
			return new ArrayList<T>();
		}
		int n = 1;
		List<Object> paramList = new ArrayList<>();
		String hql = "FROM " + entityClass.getSimpleName() + " WHERE 1=1 ";
		if (ids.size() <= 1000) {
			hql += " AND " + priKey + " IN (?" + (n++) + ")";
			paramList.add(ids);
		} else {
			int num = ids.size() / 1000;
			int remain = ids.size() % 1000;
			hql += " AND (";
			for (int i = 0; i <= num; i++) {
				List<String> newids = new ArrayList<>();
				if (i != num) {
					newids = ids.subList(i * 1000, (i + 1) * 1000);
				} else {
					newids = ids.subList(i * 1000, i * 1000 + remain);
				}
				if (i == 0) {
					hql += priKey + " IN (?" + (n++) + ")";
				} else {
					hql += " OR " + priKey + " IN (?" + (n++) + ")";
				}
				paramList.add(newids);
			}
			hql += " )";
		}
		FundPage<T> result = queryHqlWithPaging(hql, paramList, null);
		return result.getContent();
	}

	private <T> String getPriFieldName(Class<T> entityClass) {
		for (Field field : entityClass.getDeclaredFields()) {
			if (field.getAnnotation(Id.class) != null) {
				return field.getName();
			}
		}
		return null;
	}

	
	public <T> Map<String, Set<T>> findMapByFields(Class<T> entityClass, String fieldName, Set<Object> fieldValues) {
		if (entityClass == null || fieldValues == null || fieldValues.size() == 0) {
			return null;
		}
		
		int n = 1;
		List<Object> paramList = new ArrayList<>();
		String hql = "FROM " + entityClass.getSimpleName() + " WHERE 1=1 ";
		if (fieldValues.size() <= 1000) {
			hql += " AND " + fieldName + " IN (?" + (n++) + ")";
			paramList.add(fieldValues);
		} else {
			int num = fieldValues.size() / 1000;
			int remain = fieldValues.size() % 1000;
			List<Object> list = new ArrayList<>(fieldValues);
			hql += " AND (";
			for (int i = 0; i <= num; i++) {
				List<Object> newids = new ArrayList<>();
				if (i != num) {
					newids = list.subList(i * 1000, (i + 1) * 1000);
				} else {
					newids = list.subList(i * 1000, i * 1000 + remain);
				}
				if (i == 0) {
					hql += fieldName + " IN (?" + (n++) + ")";
				} else {
					hql += " OR " + fieldName + " IN (?" + (n++) + ")";
				}
				paramList.add(newids);
			}
			hql += " )";
		}
		FundPage<T> result = queryHqlWithPaging(hql, paramList, null);
		List<T> list = result.getContent();
		Map<String, Set<T>> resultMap = new HashMap<>();
		try {
			Field priField = entityClass.getDeclaredField(fieldName);
			priField.setAccessible(true);
			for (T t : list) {
				String keyFieldValue = priField.get(t).toString();
				if (!resultMap.containsKey(keyFieldValue)) {
					// needSort? LinkedHashSet?
					resultMap.put(keyFieldValue, new HashSet<>());
				}
				resultMap.get(keyFieldValue).add(t);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	
	public <T> Map<String, T> findMapByIds(Class<T> entityClass, List<String> ids) {
		if (entityClass == null || ids == null || ids.size() == 0) {
			return new HashMap<>();
		}
		String priKey = getPriFieldName(entityClass);
		if (priKey == null) return null;
		
		int n = 1;
		List<Object> paramList = new ArrayList<>();
		String hql = "FROM " + entityClass.getSimpleName() + " WHERE 1=1 ";
		if (ids.size() <= 1000) {
			hql += " AND " + priKey + " IN (?" + (n++) + ")";
			paramList.add(ids);
		} else {
			int num = ids.size() / 1000;
			int remain = ids.size() % 1000;
			hql += " AND (";
			for (int i = 0; i <= num; i++) {
				List<String> newids = new ArrayList<>();
				if (i != num) {
					newids = ids.subList(i * 1000, (i + 1) * 1000);
				} else {
					newids = ids.subList(i * 1000, i * 1000 + remain);
				}
				if (i == 0) {
					hql += priKey + " IN (?" + (n++) + ")";
				} else {
					hql += " OR " + priKey + " IN (?" + (n++) + ")";
				}
				paramList.add(newids);
			}
			hql += " )";
		}
		FundPage<T> result = queryHqlWithPaging(hql, paramList, null);
		List<T> list = result.getContent();
		Map<String, T> resultMap = new HashMap<>();
		try {
			Field priField = entityClass.getDeclaredField(priKey);
			priField.setAccessible(true);
			for (T t : list) {
				resultMap.put(priField.get(t).toString(), t);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	public <T> Map<String, T> findMapByFields(Class<T> entityClass, String fieldName, List<String> pks) {
		if (entityClass == null || pks == null || pks.size() == 0) {
			return null;
		}
		int n = 1;
		List<Object> paramList = new ArrayList<>();
		String hql = "FROM " + entityClass.getSimpleName() + " WHERE 1=1 ";
		if (pks.size() <= 1000) {
			hql += " AND " + fieldName + " IN (?" + (n++) + ")";
			paramList.add(pks);
		} else {
			int num = pks.size() / 1000;
			int remain = pks.size() % 1000;
			hql += " AND (";
			for (int i = 0; i <= num; i++) {
				List<String> newids = new ArrayList<>();
				if (i != num) {
					newids = pks.subList(i * 1000, (i + 1) * 1000);
				} else {
					newids = pks.subList(i * 1000, i * 1000 + remain);
				}
				if (i == 0) {
					hql += fieldName + " IN (?" + (n++) + ")";
				} else {
					hql += " OR " + fieldName + " IN (?" + (n++) + ")";
				}
				paramList.add(newids);
			}
			hql += " )";
		}
		FundPage<T> result = queryHqlWithPaging(hql, paramList, null);
		List<T> list = result.getContent();
		Map<String, T> resultMap = new HashMap<>();
		try {
			Field priField = entityClass.getDeclaredField(fieldName);
			priField.setAccessible(true);
			for (T t : list) {
				resultMap.put(priField.get(t).toString(), t);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	
	public <T> List<T> findByFields(Class<T> entityClass, String fieldName, Collection<Object> values) {
		if (values.size() == 0) {
			return new ArrayList<T>();
		}
		int n = 1;
		List<Object> paramList = new ArrayList<>();
		String hql = "FROM " + entityClass.getSimpleName() + " WHERE 1=1 ";
		if (values.size() <= 1000) {
			hql += " AND " + fieldName + " IN (?" + (n++) + ")";
			paramList.add(values);
		} else {
			int num = values.size() / 1000;
			int remain = values.size() % 1000;
			List<Object> list = new ArrayList<>(values);
			hql += " AND (";
			for (int i = 0; i <= num; i++) {
				List<Object> newids = new ArrayList<>();
				if (i != num) {
					newids = list.subList(i * 1000, (i + 1) * 1000);
				} else {
					newids = list.subList(i * 1000, i * 1000 + remain);
				}
				if (i == 0) {
					hql += fieldName + " IN (?" + (n++) + ")";
				} else {
					hql += " OR " + fieldName + " IN (?" + (n++) + ")";
				}
				paramList.add(newids);
			}
			hql += " )";
		}
		
		FundPage<T> result = queryHqlWithPaging(hql, paramList, null);
		return result.getContent();
	}

	
	public <T> List<T> findByField(Class<T> entityClass, String fieldName, Object value) {
		String hql = "FROM " + entityClass.getSimpleName() + " WHERE " + fieldName + " = ?1";
		List<Object> paramList = new ArrayList<>();
		paramList.add(value);
		FundPage<T> result = queryHqlWithPaging(hql, paramList, null);
		return result.getContent();
	}
	
	public <T> FundPage<T> queryHqlWithPaging(String hql, List<Object> paramList, Pageable page) {
		return queryWithPaging(hql, paramList, page, false);
	}

	
	public <T> FundPage<T> querySqlWithPaging(String sql, List<Object> paramList, Pageable page, Class<T> clz) {
		int start = 0;
		int end = 0;
		Integer len = 0;
		Integer totalPages = 0;
		String pageSql = sql;
		if (page != null) {
			len = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM (" + sql + ")", paramList.toArray(), Integer.class);
			int pageSize = page.getPageSize();
			totalPages = Long.valueOf((len + pageSize) / pageSize).intValue();
			start = page.getPageNumber() * pageSize;
			end = (page.getPageNumber() + 1) * pageSize;
			pageSql = "SELECT t2.* FROM (SELECT ROWNUM AS rowno, t.* FROM (" + sql + ") t WHERE ROWNUM <= " + end + ") t2 WHERE t2.rowno > " + start;
		}
		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(pageSql, paramList.toArray());
		List<T> list = new ArrayList<>();
		Map<String, String> fieldMap = new HashMap<>();
		for (Field field : clz.getDeclaredFields()) {
			Column column = field.getAnnotation(Column.class);
			if (column != null) {
				fieldMap.put(column.name().replaceAll("\"", ""), field.getName());
			} else {
				fieldMap.put(field.getName().toUpperCase(), field.getName());
			}
		}
		for (Map<String, Object> result : results) {
			try {
				T obj = clz.newInstance();
				for (java.util.Map.Entry<String, Object> entry : result.entrySet()) {
					String fieldName = fieldMap.get(entry.getKey());
					if (!StringUtil.isEmpty(fieldName)) {
						Field field = clz.getDeclaredField(fieldName);
						field.setAccessible(true);
						field.set(obj, entry.getValue());
					}
				}
				list.add(obj);
			} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return new FundPage<T>(totalPages, len, list);
	}

	
	public <T> FundPage<T> querySqlWithPaging(String sql, List<Object> paramList, Pageable page) {
		return queryWithPaging(sql, paramList, page, true);
	}
	
	@SuppressWarnings("unchecked")
	private <T> FundPage<T> queryWithPaging(String script, List<Object> paramList, Pageable page, boolean isSql) {
		Query query = null;
		if (isSql) {
			query = entityManager.createNativeQuery(script);
		} else {
			query = entityManager.createQuery(script);
		}
		int i = 1;
		for (Object param : paramList) {
			query.setParameter(i++, param);
		}
		int totalPages = 0;
		long len = 0;
		if (page != null) {
			Query pageQuery = null;
			if (isSql) {
				pageQuery = entityManager.createNativeQuery("SELECT COUNT(1) FROM (" + script + ")");
			} else {
				String pageHql = null;
				if (script.trim().toUpperCase().startsWith("SELECT")) {
					String selectFields = script.substring(7, script.toUpperCase().indexOf("FROM") - 1);
					pageHql = script.replaceFirst(selectFields, "COUNT(1)");
				} else if (script.trim().toUpperCase().startsWith("FROM")) {
					pageHql = "SELECT COUNT(1) " + script;
				}
				pageQuery = entityManager.createQuery(pageHql);
			}
			i = 1;
			for (Object param : paramList) {
				pageQuery.setParameter(i++, param);
			}
			len = ((Number) pageQuery.getSingleResult()).longValue();
			int pageSize = page.getPageSize();
			totalPages = Long.valueOf((len + pageSize) / pageSize).intValue();

			int start = page.getPageNumber() * pageSize;
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		List<T> resultList = query.getResultList();
		return new FundPage<T>(totalPages, len, resultList);
	}
	
	@SuppressWarnings("unchecked")
	
	public <T> T findByParams(Class<T> clz, Object... params) {
		StringBuilder sql = new StringBuilder("SELECT t FROM " + clz.getSimpleName() + " t WHERE 1=1");
		for (int i = 0; i < params.length; i = i + 2) {
			sql.append(" and " + params[i] + " = :" + params[i].toString().replace(".", ""));
		}
		Query query = entityManager.createQuery(sql.toString());
		for (int i = 0; i < params.length; i = i + 2) {
			query.setParameter(params[i].toString().replace(".", ""), params[i + 1]);
		}
		List<T> result = query.getResultList();
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
}