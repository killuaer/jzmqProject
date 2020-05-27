package com.hhh.core.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 */
public class PublicTool {
	static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	/**
	 * 获取上传文件所的保存目录
	 * 
	 * @return 保存目录
	 */
	public static String getUploadFileDir() {
		String path = null;
		try {
			path = getBaseDir();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return path;
	}

	/**
	 * 获取上传文件所的保存目录
	 * 
	 * @param path
	 *            指定保存子目录名
	 * @param fileName
	 *            文件名
	 * @return 保存目录
	 * @throws IOException
	 */
	public static File getTargetFileDir(String path, String fileName) throws Exception {
		File targetFile = null;
		try {
			targetFile = new File(new File(getBaseDir(path)), fileName);
//			targetFile = new File(new File(path), fileName);
			if (!targetFile.exists()) {
				System.out.println("文件【" + targetFile.getCanonicalPath() + "】不存在。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return targetFile;
	}

	/**
	 * 获取上传文件所的保存目录
	 * 
	 * @return 保存目录
	 * @throws IOException
	 */
	public static File getTargetFileDir(String fileName) throws Exception {
		return getTargetFileDir(null, fileName);
	}

	/**
	 * 获取文件上传目录
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String getBaseDir() throws Exception {
		return getBaseDir(null);
	}

	/**
	 * 获取文件上传目录
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private static String getBaseDir(String path) throws Exception {
		String dir = null;
		InputStream in = null;
		try {
			in = PublicTool.class.getClassLoader().getResourceAsStream("web.properties");
			Properties prop = new Properties();
			prop.load(in);
			dir = prop.getProperty("uploadTempDir");
			if (dir == null || "".equals(dir)) {
				throw new Exception("请检查！！！未配置web.properties文件的puploadTempDir参数或配置错误！");
			}
			if (path != null && !"".equals(path)) {
				// dir = dir + File.separator + path;
				dir = dir + path;
			}
			File f = new File(dir);
			if (!f.exists()) {
				System.out.println("Create directory \"" + dir + "\" is " + f.mkdirs() + ".");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dir;
	}

	/**
	 * 获取上传文件所的保存目录
	 * 
	 * @return 文件存在返回保存目录，不存在则返回空字符串
	 */
	public static String getQualifiedTargetFileDir(String fileName) {
		String path = null;
		try {
			File file = getTargetFileDir(fileName);
			if (file.exists()) {
				path = file.getCanonicalPath();
			} else {
				path = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return path;
	}

	/**
	 * obj 转换为 map
	 * 
	 * @param obj
	 * @return map
	 */
	public static Map<String, Object> objectToMap(Object obj) {
		if (obj == null) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			try {
				map.put(field.getName(), field.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				map.put(field.getName(), null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				map.put(field.getName(), null);
			}
		}
		return map;
	}

	/**
	 * obj list 转换为list map
	 * 
	 * @param objList
	 * @return list map
	 */
	public static List<Map<String, Object>> objectListToListMap(List<?> objList) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Object obj = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = objList.get(i);
			if (obj != null) {
				listMap.add(objectToMap(obj));
			}
		}
		return listMap;
	}

	public static String getImageBinary(String url, String fileType) {
		File f = new File(url);
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, fileType, baos);
			byte[] bytes = baos.toByteArray();

			return encoder.encodeBuffer(bytes).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void base64StringToImage(String base64String, String url) {
		try {
			byte[] bytes1 = decoder.decodeBuffer(base64String);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			BufferedImage bi1 = ImageIO.read(bais);
			File w2 = new File(url);// 可以是jpg,png,gif格式
			ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getInfoByProperties(String key, String properties) {
		String info = null;
		InputStream in = null;
		try {
			in = PublicTool.class.getClassLoader().getResourceAsStream(properties);
			Properties prop = new Properties();
			prop.load(in);
			info = prop.getProperty(key);
			if(info == null || "".equals(info)){
				throw new Exception("请检查！！！未配置web.properties文件的puploadTempDir参数或配置错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}
	
	public static byte[] transformBase64(String str) {
		byte[] b = null;
		try {
			b = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	/**
	 * 从网络Url中下载文件
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadFromUrl(String urlStr, String fileName, String savePath) {
		FileOutputStream fos = null;
		InputStream inputStream = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(3 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			// 得到输入流
			inputStream = conn.getInputStream();
			// 获取自己数组
			byte[] getData = readInputStream(inputStream);

			// 文件保存位置
			File saveDir = new File(savePath);
			if (!saveDir.exists()) {
				saveDir.mkdir();
			}
			File file = new File(saveDir + File.separator + fileName);
			fos = new FileOutputStream(file);
			fos.write(getData);

			System.out.println("info:" + url + " download success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 从输入流中获取字节数组
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
		byte[] buffer = new byte[1024];  
		int len = 0;  
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
		while((len = inputStream.read(buffer)) != -1) {  
			bos.write(buffer, 0, len);  
		}  
		bos.close();  
		return bos.toByteArray();  
	}  

	public static void main(String[] args) throws Exception {
		// getQualifiedTargetFileDir("5_检测技术管理表格.doc");
		// System.out.println(File.separator);

		// System.out.println("1->"+PublicTool.getBaseDir());
		// System.out.println("2->"+PublicTool.getBaseDir("aaa"));
		// System.out.println("3->"+PublicTool.getBaseDir("/aaa/bb"));
		// System.out.println("4->"+PublicTool.getTargetFileDir("b.txt"));
		// System.out.println("3->"+PublicTool.getTargetFileDir("a",
		// "a.txt").getCanonicalPath());
	}
}
