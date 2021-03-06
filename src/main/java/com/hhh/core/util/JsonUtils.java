package com.hhh.core.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json、bean、集合转换实用类
 * 
 * @author shijw
 * 
 */
public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	private static ObjectMapper objectMapper = new ObjectMapper();

	private JsonUtils() {
	};

	public static <T> String bean2Json(T bean) {
		try {
			return objectMapper.writeValueAsString(bean);
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("bean转换json失败");
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	public static String map2Json(Map map) {
		try {
			return objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("map转换json失败");
			e.printStackTrace();
		}
		return "";
	}

	public static <T> String list2Json(List<T> list) {
		try {
			return objectMapper.writeValueAsString(list);
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("list转换json失败");
			e.printStackTrace();
		}
		return "";
	}

	public static <T> T json2Bean(String json, Class<T> beanClass) {
		try {
			return objectMapper.readValue(json, beanClass);
		} catch (Exception e) {
			logger.info("json转换bean失败");
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> json2List(String json, Class<T> beanClass) {
		try {

			return (List<T>) objectMapper.readValue(json, getCollectionType(List.class, beanClass));
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("json转换list失败");
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Map json2Map(String json) {
		try {

			return (Map) objectMapper.readValue(json, Map.class);
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("json转换map失败");
			e.printStackTrace();
		}
		return null;
	}

	private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametrizedType(collectionClass, collectionClass, elementClasses);
	}

	public static void setDateFormat(String pattern) {
		if (!StringUtil.isEmpty(pattern)) {
			objectMapper.setDateFormat(new SimpleDateFormat(pattern));
		}
	}

	public static int getNodeValue(String json, String node) {
		JsonNode jsonNode = null;
		int value = 0;
		try {
			jsonNode = objectMapper.readTree(json);
			value = jsonNode.get(node).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获取节点值失败");
		}
		return value;
	}

	public static JsonNode getNodeValue(String json) {
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(json);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获取节点值失败");
		}
		return jsonNode;
	}
}
