package com.hhh.core.util;

import java.math.BigDecimal;
import java.util.List;

/**
 * MathExtend 提供精确的加法、减法、乘法和除法运算
 * 
 * @author 3hcdp
 */
public class MathExtend {
	// 默认除法运算精度
	private static final int DEFAULT_DIV_SCALE = 10;

	/**
	 * 计算绝对值
	 * 
	 * @param v1 字符串数值
	 * @return 返回绝对值，以字符串格式返回
	 */
	public static String abs(String v1) {
		BigDecimal b1 = new BigDecimal(v1);
		return b1.abs().toPlainString();
	}

	/**
	 * 计算标准差
	 * 
	 * @param zhi 一组数
	 * @param pj 平均值
	 * @return
	 */
	public static double calculateStandardDeviation(List<Double> zhi, double pj) {
		double allSquare = 0.0;
		for (Double shu : zhi) {
			allSquare += (shu - pj) * (shu - pj);
		}
		// (xi - x(平均分)的平方 的和计算完毕
		double denominator = zhi.size() - 1.0;// double denominator =zhi.size()
												// * (zhi.size() - 1);
		return Math.sqrt(allSquare / denominator);
	}

	/**
	 * 计算标准差
	 * 
	 * @param zhi 一组数
	 * @param pj 平均值
	 * @return
	 */
	public static String calculateStandardDeviation(String[] zhi, String pj) {
		double allSquare = 0.0;
		for (String shu : zhi) {
			allSquare += (Double.parseDouble(shu) - Double.parseDouble(pj)) * (Double.parseDouble(shu) - Double.parseDouble(pj));
		}
		// (xi - x(平均分)的平方 的和计算完毕
		double denominator = zhi.length - 1.0;// double denominator =zhi.size()
												// * (zhi.size() - 1);
		if (Double.isNaN(Math.sqrt(allSquare / denominator))) {
			return "0";
		}
		return Double.toString(Math.sqrt(allSquare / denominator));
	}

	/**
	 * 计算平方
	 * 
	 * @param v1 字符串数值
	 * @return 返回平方，以字符串格式返回
	 */
	public static String pow(String v1) {
		BigDecimal b1 = new BigDecimal(v1);
		return b1.pow(2).toPlainString();
	}

	/**
	 * 计算开方
	 * 
	 * @param v1 字符串数值
	 * @return 返回开方，以字符串格式返回
	 */
	public static String sqrt(String v1) {
		return String.valueOf(Math.sqrt(Double.valueOf(v1)));
	}

	/**
	 * 估算样本的标准偏差。标准偏差反映相对于平均值 (mean)的离散程度。（具体计算过程参见EXCEL的STDEV函数）
	 * 
	 * @param xList 参数列表
	 * @return 估算样本的标准偏差
	 * @throws Exception
	 */
	public static String stdev(String... xList) throws Exception {
		String stdev = "";
		if (null != xList && 0 < xList.length) { // 参数不为空
			String sum = "0"; // N个数的和
			String sumPow = "0"; // N个数的和的平方
			String powSum = "0"; // N个数的平均和，每个数先平方再求和
			String nPowSum = "0"; // N倍的 N个数的平均和
			String nPowSum_sumPow = "0"; // N倍的 N个数的平均和 - N个数的和的平方
			// 开始计算标准差
			for (int i = 0; i < xList.length; i++) {
				sum = MathExtend.add(sum, xList[i]);
				powSum = MathExtend.add(powSum, MathExtend.pow(xList[i]));
			}
			sumPow = MathExtend.pow(sum);
			nPowSum = MathExtend.multiply(String.valueOf(xList.length), powSum);
			nPowSum_sumPow = MathExtend.subtract(nPowSum, sumPow);
			stdev = MathExtend.sqrt(MathExtend.divide(nPowSum_sumPow, MathExtend.multiply(String.valueOf(xList.length), String.valueOf(xList.length - 1))));
		} // if (null != xList && 0 < xList.length)
		return stdev;
	}

	/**
	 * 提供精确的加法运算
	 * 
	 * @param v1 第一个参数
	 * @param v2 第二个参数
	 * @return 两个参数数学加和，以字符串格式返回
	 */
	public static String add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).toPlainString();
	}

	/**
	 * 提供精确的减法运算
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数数学差，以字符串格式返回
	 */
	public static String subtract(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).toPlainString();
	}

	/**
	 * 提供精确的乘法运算
	 * 
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的数学积，以字符串格式返回
	 */
	public static String multiply(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).toPlainString();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍六入,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商，以字符串格式返回
	 */
	public static String divide(String v1, String v2) {
		return divide(v1, v2, DEFAULT_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍六入。舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示需要精确到小数点以后几位
	 * @return 两个参数的商，以字符串格式返回
	 */
	public static String divide(String v1, String v2, int scale) {
		return divide(v1, v2, scale, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍六入。舍入模式采用用户指定舍入模式
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示需要精确到小数点以后几位
	 * @param round_mode 表示用户指定的舍入模式
	 * @return 两个参数的商，以字符串格式返回
	 */
	public static String divide(String v1, String v2, int scale, int round_mode) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (Double.parseDouble(v1) == 0) {
			return "0";
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, round_mode).toPlainString();
	}
	/**3hcxy20141022
	 * 提供精确的内插法运算
	 * X=（D3*（C4-B4）+B3*（D4-C4））/（D4-B4）
	 * @return 
	 */
	public static String neichafa(String b3, String d3,String b4,String c4,String d4) {
		BigDecimal xb4 = new BigDecimal(b4);
		BigDecimal xd4 = new BigDecimal(d4);
		BigDecimal xb3 = new BigDecimal(b3);
		BigDecimal xd3 = new BigDecimal(d3);
		BigDecimal xc4 = new BigDecimal(c4);
		BigDecimal xc3,temp1,temp2,temp3,temp4;
		if (xd4.subtract(xb4)==BigDecimal.ZERO){
			return "";
		}
		if (StringUtil.isEmpty(b3) || StringUtil.isEmpty(d3) || StringUtil.isEmpty(b4) || StringUtil.isEmpty(c4) || StringUtil.isEmpty(d4)){
			return "";
		}
		temp1 = xd3.multiply(xc4.subtract(xb4));
		temp2 = xb3.multiply(xd4.subtract(xc4));
		temp3 = temp1.add(temp2);
		temp4 = xd4.subtract(xb4);
		xc3 = temp3.divide(temp4,3);
		return xc3.toPlainString();
	}
}
