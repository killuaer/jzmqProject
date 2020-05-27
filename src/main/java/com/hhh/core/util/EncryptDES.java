package com.hhh.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptDES {

	// SecretKey 负责保存对称密钥
	private SecretKey secretKey;
	// Cipher负责完成加密或解密工作
	private Cipher c;
	// 该字节数组负责保存加密的结果
	private byte[] cipherByte;

	public EncryptDES(String deskey) {
		// 生成密钥
		DESKeySpec desKeySpec;
		try {
			desKeySpec = new DESKeySpec(deskey.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			this.secretKey = secretKey;
			// 生成Cipher对象,指定其支持的DES算法
			c = Cipher.getInstance("DES");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] encrytor(String str) {
		try {
			SecureRandom random = new SecureRandom();
			// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
			c.init(Cipher.ENCRYPT_MODE, secretKey, random);
			byte[] src = str.getBytes();
			// 加密，结果保存进cipherByte
			cipherByte = c.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherByte;
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] decryptor(byte[] buff) {
		try {
			SecureRandom random = new SecureRandom();
			// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示解密模式
			c.init(Cipher.DECRYPT_MODE, secretKey, random);
			cipherByte = c.doFinal(buff);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cipherByte;
	}

	/**
	 * 加密文件生成文件
	 * 
	 * @param sourceFileName
	 *            需要加密的文件
	 * @param targetFileName
	 *            加密后的文件
	 * @throws IOException
	 * @throws InvalidKeyException
	 */
	public void encrytFile(String sourceFileName, String targetFileName) {
		try {
			c.init(Cipher.ENCRYPT_MODE, secretKey);
			InputStream is = new FileInputStream(sourceFileName);
			OutputStream out = new FileOutputStream(targetFileName);
			CipherInputStream cis = new CipherInputStream(is, c);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = cis.read(buffer)) > 0) {
				out.write(buffer, 0, r);
			}
			cis.close();
			is.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解密文件生成文件
	 * 
	 * @param sourceFileName
	 *            需要解密的文件
	 * @param targetFileName
	 *            解密后的文件
	 * @throws IOException
	 * @throws InvalidKeyException
	 */
	public void decryptFile(String sourceFileName, String targetFileName) {
		try {
			c.init(Cipher.DECRYPT_MODE, secretKey);
			InputStream is = new FileInputStream(sourceFileName);
			OutputStream out = new FileOutputStream(targetFileName);
			CipherOutputStream cos = new CipherOutputStream(out, c);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = is.read(buffer)) >= 0) {
				cos.write(buffer, 0, r);
			}
			cos.close();
			out.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解密文件生成字符串
	 * 
	 * @param sourceFileName
	 * @throws InvalidKeyException
	 * @throws IOException
	 */
	public String decryptFileToString(String sourceFileName) {
		StringBuffer str = null;
		try {
			c.init(Cipher.DECRYPT_MODE, secretKey);
			InputStream is = new FileInputStream(sourceFileName);
			CipherInputStream cis = new CipherInputStream(is, c);
			BufferedReader reader = new BufferedReader(new InputStreamReader(cis, "gbk"));
			String line = null;
			str = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				str.append(line);
				str.append("\n");
			}
			reader.close();
			cis.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(str);
	}

	/**
	 * 解密文件生成字符串
	 * 
	 * @param sourceFileName
	 * @throws InvalidKeyException
	 * @throws IOException
	 */
	public String decryptFileToString(InputStream is) {
		StringBuffer str = null;
		try {
			c.init(Cipher.DECRYPT_MODE, secretKey);
			CipherInputStream cis = new CipherInputStream(is, c);
			BufferedReader reader = new BufferedReader(new InputStreamReader(cis, "gbk"));
			String line = null;
			str = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				str.append(line);
				str.append("\n");
			}
			reader.close();
			cis.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(str);
	}

	/**
	 * 加密字符串生成文件
	 * 
	 * @param str
	 * @param fileName
	 */
	public void encryptStringToFile(String str, String fileName) {
		try {
			c.init(Cipher.ENCRYPT_MODE, secretKey);
			InputStream is = new ByteArrayInputStream(str.getBytes("gbk"));
			OutputStream out = new FileOutputStream(fileName);
			CipherInputStream cis = new CipherInputStream(is, c);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = cis.read(buffer)) > 0) {
				out.write(buffer, 0, r);
			}
			cis.close();
			is.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 */
	public static void main(String[] args) throws Exception {
		// 密钥必须大于等于8位
		String desKey = "12345678";
		// EncryptDES de1 = new EncryptDES(desKey);
		// EncryptDES de2 = new EncryptDES(desKey);
		// String msg ="你好,你们好";
		// byte[] encontent = de1.encrytor(msg);
		// byte[] decontent = de2.decryptor(encontent);
		// System.out.println("明文是:" + msg);
		// System.out.println("加密后:" + new String(encontent));
		// System.out.println("解密后:" + new String(decontent));
		EncryptDES de3 = new EncryptDES(desKey);
		EncryptDES de4 = new EncryptDES(desKey);
		// 改成自己的文件路径
		de3.encrytFile("C:\\Users\\3hygj\\Desktop\\信息.txt", "C:\\Users\\3hygj\\Desktop\\信息1.txt");
		de4.decryptFile("C:\\Users\\3hygj\\Desktop\\信息1.txt", "C:\\Users\\3hygj\\Desktop\\信息2.txt");
		EncryptDES de5 = new EncryptDES(desKey);
		// de5.encryptStringToFile("大家好", "C:\\Users\\3hygj\\Desktop\\信息1.txt");
		System.out.println(de5.decryptFileToString("C:\\Users\\3hygj\\Desktop\\信息1.txt"));
	}

}