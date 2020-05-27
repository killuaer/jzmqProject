package com.hhh.core.util;

import java.util.Random;
/**
 * 生成六位随机的验证码
 * @author 3hwxk
 *
 */
public class Code {
	public static String createCode() {
		//定义一串字符串
		String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		//构造初始容量为6的字符串
    	StringBuilder sb=new StringBuilder(6);
    	for(int i=0;i<6;i++)
    	{
    		char ch=str.charAt(new Random().nextInt(str.length()));
    		sb.append(ch);
    	}
    	return sb.toString();
	}
}
