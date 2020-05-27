package com.hhh.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public final class StringUtil {

	public final static String CUSTOMER_ID = "customerId";
	private static final int decode_key = 5;
	
	public static String getNextUpEn(String en){
		if(en==null || en.equals(""))
			return "A";
	    char lastE = 'Z';
	    int lastEnglish = (int)lastE;    
	    char[] c = en.toCharArray();
	    if(c.length>1){
	    	return null;
	    }else{
	    	int now = (int)c[0];
	    	if(now >= lastEnglish)
	    		return null;
	    	char uppercase = (char)(now+1);
	    	return String.valueOf(uppercase);
	    }
	}
	/*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
	/**
	 * 数字格式化
	 * 
	 * @param num
	 * @return 0.00
	 */
	public static String decimalFormat(float num) {
		if (num == 0)
			return "0.00";
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(num);
	}

	public static String getUid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	public static float decimalParse(Object num) {
		if (StringUtil.isEmpty(num))
			return 0.0f;
		DecimalFormat format = new DecimalFormat("0.00");
		try {
			return format.parse(num.toString()).floatValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0.0f;
		}
	}
	
	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateFormat(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	public static Collection<Object> idList(Collection<?> coll) {
		if (coll == null || coll.size() <= 0) {
			return null;
		}
		Collection<Object> ids = new HashSet<>();
		Class<?> clz = coll.iterator().next().getClass();
		Field priField = null;
		for (Field field : clz.getDeclaredFields()) {
			if (field.getAnnotation(Id.class) != null) {
				priField = field;
				break;
			}
		}
		if (priField == null) {
			return null;
		}
		priField.setAccessible(true);
		for (Object obj : coll) {
			try {
				ids.add(priField.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return ids;
	}
	
	public static String join(Collection<?> coll) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : coll) {
			sb.append("," + obj.toString());
		}
		return sb.length() > 0 ? sb.substring(1) : "";
	}
	
	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String dateFormat1(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * 字符串转日期对象
	 * 
	 * @param str
	 *            字符串日期格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date parstDate(String str) {
		if (str == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 字符串转日期对象
	 * 
	 * @param str
	 *            字符串日期格式：yyyy-MM-dd
	 * @return
	 */
	public static Date parstDate1(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String nvl(Object obj, String defaultStr) {
		if (isEmpty(obj)) {
			return defaultStr;
		}
		return obj.toString();
	}

	public static Integer toInt(Object obj, Integer defaultInt) {
		if (obj != null) {
			if (obj instanceof Number) {
				return ((Number)obj).intValue();
			} else {
				try {
					return Integer.valueOf(obj.toString());
				} catch (Exception e) {}
			}
		}
		return defaultInt;
	}

	public static String toStr(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}
	
	public static boolean isEmpty(Object obj) {
		return obj == null || StringUtils.isEmpty(obj.toString()) || obj.toString().equalsIgnoreCase("null");
	}
	
	public static String displayBool(Object obj) {
		if (isEmpty(obj)) {
			return "否";
		}
		String str = obj.toString();
		return str.equals("true") || str.equals("1") ? "是" : "否";
	}

	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 数据加密
	 * 根据字符串进行循环，取得每个字符hashcode与5进行异或运算
	 * @param sourceData
	 * @return
	 */
	public static String XorEncrypt(String sourceData) {
		StringBuilder builder = new StringBuilder();
		String[] sourceArr = sourceData.split("");
		for (int j = 0; j < sourceArr.length; j = j + 1) {
			int num2 = (sourceArr[j].hashCode() ^ decode_key);
			builder.append((char)num2);
		}
		return builder.toString();
	}
	
	/**
	 * 数据解密
	 * 取得加密后的字符串进行循环，5与取得每个字符hashcode进行异或运算
	 * 
	 * @param encrypData
	 * @return
	 */
	public static String XorCrevasse(String encrypData) {
		StringBuilder builder = new StringBuilder();
		String[] sourceArr = encrypData.split("");
		for (int j = 0; j < encrypData.length(); j = j + 1) {
			int num2 = sourceArr[j].hashCode();
			builder.append((char)(decode_key ^ num2));
		}
		return builder.toString();
	}
	/**
	 * ep:   wtUnit  -   wt_unit
	 * @param sort
	 * @return
	 */
	public static String HtoS(String sort) {
		if (null == sort || sort.isEmpty()) {
			return "";
		}
        String s1 = sort.replaceAll("[A-Z]", "_$0");
        String s2 = s1.substring(1, s1.length());
		return s2;
	}
	/**
	 * String转Json
	 * @param json
	 * @return
	 */
	public static JSONArray strToJsonArray(String json) {
		JSONArray r = null;
		if (json != null && !"".equals(json)) {
			JSONObject jsonObj = new JSONObject();
			JSONObject items = jsonObj.accumulate("items", json);
			r = items.getJSONArray("items");
		}
		return r;
	}
	
	public static byte[] base64StrToByteArray(String str) {
	    if (str == null) {
	        return null;
	    }
	    return Base64.getDecoder().decode(str);
	}
	
	public static String byteArrayToBase64Str(byte[] byteArray) {
	    if (byteArray == null) {
	        return null;
	    }
	    return Base64.getEncoder().encodeToString(byteArray);
	}
	
	/**
	 * 检查字符串能否转换为double基本类型
	 * 
	 * @param value
	 *            要转换的字符串
	 * @return true/false
	 * @throws NumberFormatException
	 */
	public static boolean checkStringIsDouble(String value) throws NumberFormatException {
		boolean isSucess = false;
		if (value != null && value.trim().length() > 0) {
			Double.parseDouble(value);
			isSucess = true;
		}
		return isSucess;
	}
	
	/**
	 * 获取平均值
	 * 
	 * @param scale
	 *            保留位数
	 * @param xList
	 *            数据列表
	 * @return 平均值
	 */
	public static String getAverage(int scale, String... xList) {
		String result = "";
		if (null != xList && 0 < xList.length) {
			String total = "0";
			for (int i = 0; i < xList.length; i++) {
				total = MathExtend.add(total, xList[i]);
			}
			result = MathRound.round(MathExtend.divide(total, String.valueOf(xList.length)), scale);
		}
		return result;
	}
	
	/**
	 * 把Object转化为String，如果Object == null，则返回""
	 * 
	 * @param Obj
	 *            一个对象
	 * @return 字符串String
	 */
	public static String toString(Object Obj) {
		return Obj == null ? "" : String.valueOf(Obj);
	}
	
	public static String getBrowser(HttpServletRequest request) {  
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();  
        if (UserAgent != null) {  
            if (UserAgent.indexOf("msie") >= 0)  
                return "IE";  
            if (UserAgent.indexOf("firefox") >= 0)  
                return "FF";  
            if (UserAgent.indexOf("safari") >= 0)  
                return "SF";  
        }  
        return null;  
    }
	
	/** 
	* 复制单个文件 
	* @param oldPath String 原文件路径 如：c:/fqf.txt 
	* @param newPath String 复制后路径 如：f:/fqf.txt 
	* @return boolean 
	*/ 
	@SuppressWarnings("resource")
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
//				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}
	}
	
		/**
	     * 实体对象转成Map
	     * @param obj 实体对象
	     * @return
	     */
	    @SuppressWarnings("rawtypes")
		public static Map<String, Object> object2Map(Object obj) {
	        Map<String, Object> map = new HashMap<>();
	        if (obj == null) {
	            return map;
	        }
	        Class clazz = obj.getClass();
	        Field[] fields = clazz.getDeclaredFields();
	        try {
	            for (Field field : fields) {
	                field.setAccessible(true);
	                map.put(field.getName(), field.get(obj));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return map;
	    }
	    
	    public static Object map2Object(Map<String, Object> map, Class<?> beanClass) throws Exception {    
	        if (map == null)   
	            return null;    
	   
	        Object obj = beanClass.newInstance();  
	   
	        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
	        for (PropertyDescriptor property : propertyDescriptors) {  
	            Method setter = property.getWriteMethod();    
	            if (setter != null) {  
	                setter.invoke(obj, map.get(property.getName()));   
	            }  
	        }  
	   
	        return obj;  
	    }    
}
