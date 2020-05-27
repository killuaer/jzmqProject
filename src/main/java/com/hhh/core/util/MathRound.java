package com.hhh.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * MathRound 数据修约（4舍6入，5看奇偶）
 * 
 * @author 3hcdp
 * 
 */
public class MathRound {
	private static final String CONST_NULL_STR = "";
	
	/**
	 * 4舍5入
	 * @param data 
	 * @param scale 
	 * @return 
	 */
	public static String commonRound(String data, int scale) {
		// 如果参数为NULL，则返回空串
		if (null == data)
			return CONST_NULL_STR;
		// 如果参数不为空串，则进行修约
		if (!CONST_NULL_STR.equals(data.trim())) {
			BigDecimal bigDecimalData = new BigDecimal(data);
			BigDecimal scaledData = bigDecimalData.setScale(scale, BigDecimal.ROUND_HALF_UP);
			return scaledData.toPlainString();
		}
		return CONST_NULL_STR;
	}
	
	/**
	 * 4舍6入，5看奇偶
	 * @param data 要转换的数据
	 * @param scale 表示精确到10E-scale.比如1，表示精确到0.1;-1表示精确到十位数
	 * @return 转换之后的数据
	 */
	public static String round(String data, int scale) {
		// 如果参数为NULL，则返回空串
		if (null == data)
			return CONST_NULL_STR;
		// 如果参数不为空串，则进行修约
		if (!CONST_NULL_STR.equals(data.trim())) {
			BigDecimal bigDecimalData = new BigDecimal(data);
			BigDecimal scaledData = bigDecimalData.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
			return scaledData.toPlainString();
		}
		return CONST_NULL_STR;
	}

	/**
	 * 0.5修约
	 * 
	 * @param inDate
	 * @param pos
	 * @return
	 */
	public static String round_05(String inDate, int pos) {
		// 如果参数为NULL，则返回空串
		if (null == inDate)
			return CONST_NULL_STR;
		// 如果参数不为空串，则进行修约
		if (!CONST_NULL_STR.equals(inDate.trim())) {
			String temp = round(MathExtend.multiply(inDate, "2.0"), pos - 1);
			return MathRound.round(MathExtend.divide(temp, "2.0"), pos);
		}
		return CONST_NULL_STR;
	}

	/**
	 * 0.2修约
	 * 
	 * @param inDate
	 * @param pos
	 * @return
	 */
	public static String round_02(String inDate, int pos) {
		// 如果参数为NULL，则返回空串
		if (null == inDate)
			return CONST_NULL_STR;
		// 如果参数不为空串，则进行修约
		if (!CONST_NULL_STR.equals(inDate.trim())) {
			String temp = round(MathExtend.multiply(inDate, "5.0"), pos - 1);
			return MathRound.round(MathExtend.divide(temp, "5.0"), pos);
		}
		return CONST_NULL_STR;
	}
	
	/**3hcxy20141230
	 * 0.25修约
	 * 
	 * @param inDate
	 * @return
	 */
	public static String round_025(String inDate) {
		// 如果参数为NULL，则返回空串
		if (null == inDate)
			return CONST_NULL_STR;
		// 如果参数不为空串，则进行修约
		if (!CONST_NULL_STR.equals(inDate.trim())) {
			String temp = round(MathExtend.multiply(inDate, "4.0"), 0);
			return MathRound.round(MathExtend.divide(temp, "4.0"), 2);
		}
		return CONST_NULL_STR;
	}
	
	/**
	 * 保留指定的有效位数(4舍6入，5看奇偶)
	 * 
	 * @param value 数值字符串
	 * @param precision 有效位数
	 * @return
	 */
	public static String doPrecision(String value, int precision) {
		// 如果参数为NULL，则返回空串
		if (null == value)
			return CONST_NULL_STR;
		// 如果参数不为空串，则进行修约
		if (!CONST_NULL_STR.equals(value.trim())) {
			MathContext mathContext = new MathContext(precision, RoundingMode.HALF_EVEN);
			int len = 0;
			if (value.indexOf(".") != -1) {
				len = value.length() - 1;
				if (value.indexOf("-") != -1) {
					--len;
				}
				if (len < precision) {
					for (int i = 0; i < precision - len; i++) {
						value += "0";
					}
				}
			} else {
				len = value.length();
				if (value.indexOf("-") != -1) {
					--len;
				}
				if (len < precision) {
					value += ".";
					for (int i = 0; i < precision - len; i++) {
						value += "0";
					}
				}
			}
			BigDecimal b = new BigDecimal(value);
			return b.round(mathContext).toPlainString();
		}
		return CONST_NULL_STR;				
	}
	
	/**
	 *  进一法
	 * @param scale 保留位数
	 * @param v 字符串
	 * 
	 * */
	public static String exectu(int scale, String val) {
		  BigDecimal decimal = new BigDecimal(val); 
		  //scale表示保留scale位小数， BigDecimal.ROUND_UP表示第scale位小数后，只要有值，就向前进1
		  decimal = decimal.setScale(scale, BigDecimal.ROUND_UP); 
		  String res = decimal.doubleValue() + "";
		  res = commonRound(res, scale);
		  return res;
	 }
	
	/*
	public static void main(String[] args) {
		System.out.println("数据：MathRound.round(null   , 2)；结果：" + MathRound.round(null, 2));
		System.out.println("数据：MathRound.round(       , 2)；结果：" + MathRound.round("", 2));
		System.out.println("数据：MathRound.round(12.0568, 2)；结果：" + MathRound.round("12.0568", 2));
		System.out.println();
		System.out.println("数据：MathRound.commonRound(null   , 2)；结果：" + MathRound.commonRound(null, 2));
		System.out.println("数据：MathRound.commonRound(       , 2)；结果：" + MathRound.commonRound("", 2));
		System.out.println("数据：MathRound.commonRound(12.0568, 2)；结果：" + MathRound.commonRound("12.0568", 2));
		System.out.println();
		System.out.println("数据：MathRound.round_05(null   , 2)；结果：" + MathRound.round_05(null, 2));
		System.out.println("数据：MathRound.round_05(       , 2)；结果：" + MathRound.round_05("", 2));
		System.out.println("数据：MathRound.round_05(12.0568, 2)；结果：" + MathRound.round_05("12.0568", 2));
		System.out.println();
		System.out.println("数据：MathRound.round_02(null   , 2)；结果：" + MathRound.round_02(null, 2));
		System.out.println("数据：MathRound.round_02(       , 2)；结果：" + MathRound.round_02("", 2));
		System.out.println("数据：MathRound.round_02(12.0528, 2)；结果：" + MathRound.round_02("12.0528", 2));
		System.out.println();
		System.out.println("数据：MathRound.doPrecision(null   , 2)；结果：" + MathRound.doPrecision(null, 2));
		System.out.println("数据：MathRound.doPrecision(       , 2)；结果：" + MathRound.doPrecision("", 2));
		System.out.println("数据：MathRound.doPrecision(12.0528, 3)；结果：" + MathRound.doPrecision("12.0528", 3));
	}
	*/
}
