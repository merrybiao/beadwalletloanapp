/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.baidu.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.lingala.zip4j.core.ZipFile;

/**
 * 
 * 
 * Module: 解压解密工具类 ZipUtil.java
 * 
 * @author 甘立思
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ZipUtil {
	/**
	 * 解压
	 * 
	 * @param file 原地址
	 * @param url 解压后地址
	 * @param passWord 密码
	 * @throws Exception
	 */
	public static void aa(String file, String url, String passWord) throws Exception {
		ZipFile zipFile = new ZipFile(file);
		if (zipFile.isEncrypted()) {
			zipFile.setPassword(passWord);
		}
		zipFile.extractAll(url);
	}

	/**
	 * 通过http下载图片并将图片保存本地
	 * 
	 * @param fileUrl
	 * @param savePath
	 * @return
	 */
	public static boolean saveUrlAs(String fileUrl, String savePath, String fileName) {
		/* fileUrl网络资源地址 */
		try {
			File file = new File(savePath);
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			URL url = new URL(fileUrl);/* 将网络资源地址传给,即赋值给url */
			/* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			if (connection.getResponseCode() == 200) {
				DataInputStream in = new DataInputStream(connection.getInputStream());
				/* 此处也可用BufferedInputStream与BufferedOutputStream */
				DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath + "/" + fileName));
				/* 将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址 */
				byte[] buffer = new byte[4096];
				int count = 0;
				while ((count = in.read(buffer)) > 0)/* 将输入流以字节的形式读取并写入buffer中 */
				{
					out.write(buffer, 0, count);
				}
				out.close();/* 后面三行为关闭输入输出流以及网络资源的固定格式 */
				in.close();
				connection.disconnect();
				return true;/* 网络资源截取并存储本地成功返回true */
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取TXT文件内容转换成字符串
	 * 
	 * @param filePath
	 * @return
	 */
	// public static String readTxtFile(String filePath) {
	// String lineTxt = "";
	// StringBuffer buffer = new StringBuffer();
	// try {
	// String encoding = "UTF-8";
	// File file = new File(filePath);
	// if (file.isFile() && file.exists()) { // 判断文件是否存在
	// InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
	// BufferedReader bufferedReader = new BufferedReader(read);
	// while ((lineTxt = bufferedReader.readLine()) != null) {
	// buffer.append(lineTxt);
	// }
	// read.close();
	// } else {
	// return "找不到指定的文件";
	// }
	// } catch (Exception e) {
	// System.out.println("读取文件内容出错");
	// e.printStackTrace();
	// }
	// lineTxt = new String(buffer);
	// return lineTxt;
	// }

	/**
	 * 修改照片名
	 * 
	 * @param realPath
	 * @param newName
	 * @param fileName
	 */
	public static String urlName(String realPath, String newName, String fileName) {
		String folderPath = realPath.substring(0, realPath.lastIndexOf("\\"));
		File file = new File(folderPath);
		String fileNameNew = "";
		String dirPath = file.getAbsolutePath();// 目录路径
		if (file.isDirectory()) {
			File[] files = file.listFiles();// 获取此目录下的文件列表
			for (File fileFrom : files) {
				String fromFile = fileFrom.getName();// 得到单个文件名
				if (fromFile.endsWith(fileName)) {
					// fromFile = fromFile.substring(0, fromFile.lastIndexOf("front_img.png"));
					String toFileName = dirPath + "/" + newName;
					File toFile = new File(toFileName);
					if (fileFrom.exists() && !toFile.exists()) {
						// 开始更名
						fileFrom.renameTo(toFile);
						fileNameNew = fileFrom.getName();
					} else {
						return "当前照片不存在";
					}
				} else {
					return "没有当前文件";
				}
			}
		}
		return fileNameNew;
	}

	/**
	 * 解析通话记录
	 * 
	 * @param str
	 * @return
	 */
	// public static List<Map<String, Object>> split(String str, String id) {
	// String[] strings = str.split(",");
	// // StringBuffer buffer = new StringBuffer();
	// List<Map<String, Object>> maps = new ArrayList<>();
	// for (int i = 0; i < strings.length; i++) {
	// int k = (i - 13) % 12;
	// int j = i / 12 - 1;
	// if (k == 0 && j >= 0) {
	// if ("1".equals(strings[i])) {
	// Map<String, Object> map = new HashMap<>();
	// if (strings[i - 1].length() > 5) {
	// String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(new Date(Long.valueOf(strings[i + 10]) * 1000));
	// if (strings[i + 11].length() > 6) {
	// String str2 = strings[i - 1].substring(strings[i - 1].length() - 5,
	// strings[i - 1].length());
	// String str3 = strings[i + 11].substring(0, strings[i + 11].length() - 5);
	// // buffer.append(str2 + ",").append(strings[i] + ",").append(strings[i + 1] + ",")
	// // .append(strings[i + 2] + ",").append(strings[i + 3] + ",")
	// // .append(strings[i + 4] + ",").append(strings[i + 5] + ",")
	// // .append(strings[i + 6] + ",").append(strings[i + 7] + ",")
	// // .append(strings[i + 8] + ",").append(strings[i + 9] + ",")
	// // .append(strings[i + 10] + ",")
	// // .append(strings[i + 11].substring(0, strings[i + 11].length() - 5) + ",");
	// map.put("trade_type", "国内通话".equals(strings[i + 2]) ? 1 : 2);
	// map.put("trade_time", str3);
	// map.put("call_time", date);
	// map.put("trade_addr", strings[i + 4]);
	// map.put("receive_phone", str2);
	// map.put("call_type", "主叫".equals(strings[i + 1]) ? 1 : 2);
	// map.put("borrower_id", id);
	// map.put("updateTime", "");
	// } else {
	// String str2 = strings[i - 1].substring(strings[i - 1].length() - 5,
	// strings[i - 1].length());
	// // buffer.append(str2 + ",").append(strings[i] + ",").append(strings[i + 1] + ",")
	// // .append(strings[i + 2] + ",").append(strings[i + 3] + ",")
	// // .append(strings[i + 4] + ",").append(strings[i + 5] + ",")
	// // .append(strings[i + 6] + ",").append(strings[i + 7] + ",")
	// // .append(strings[i + 8] + ",").append(strings[i + 9] + ",")
	// // .append(strings[i + 10] + ",").append(strings[i + 11] + ",");
	// map.put("trade_type", "国内通话".equals(strings[i + 2]) ? 1 : 2);
	// map.put("trade_time", strings[i + 11]);
	// map.put("call_time", date);
	// map.put("trade_addr", strings[i + 4]);
	// map.put("receive_phone", str2);
	// map.put("call_type", "主叫".equals(strings[i + 1]) ? 1 : 2);
	// map.put("borrower_id", id);
	// map.put("updateTime", "");
	// }
	// maps.add(map);
	// }
	// }
	// }
	// }
	// // String newString = new String(buffer);
	// // System.out.println(newString);
	// return maps;
	// }

	/**
	 * 一行一行读取文件，解决读取中文字符时出现乱码
	 * 
	 * 流的关闭顺序：先打开的后关，后打开的先关， 否则有可能出现java.io.IOException: Stream closed异常
	 * 
	 * @throws IOException
	 */
	public static List<Map<String, Object>> readFile02(String id, String fileUrl) throws IOException {
		FileInputStream fis = new FileInputStream(fileUrl);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		// 简写如下
		// BufferedReader br = new BufferedReader(new InputStreamReader(
		// new FileInputStream("E:/phsftp/evdokey/evdokey_201103221556.txt"), "UTF-8"));
		String line = "";
		String[] arrs = null;
		List<Map<String, Object>> maps = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			arrs = line.split(",");
			// System.out.println(arrs[0] + " , " + arrs[1] + " , " + arrs[2] + " , " + arrs[3] + " ," + arrs[4] + " , "
			// + arrs[5] + " , " + arrs[6] + " , " + arrs[7] + " , " + arrs[8] + " , " + arrs[9] + " , " + arrs[10]
			// + " , " + arrs[11] + " , " + arrs[12]);
			if ("1".equals(arrs[1])) {
				Map<String, Object> map = new HashMap<>();
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date(Long.valueOf(arrs[11]) * 1000));
				// buffer.append(str2 + ",").append(strings[i] + ",").append(strings[i + 1] + ",")
				// .append(strings[i + 2] + ",").append(strings[i + 3] + ",")
				// .append(strings[i + 4] + ",").append(strings[i + 5] + ",")
				// .append(strings[i + 6] + ",").append(strings[i + 7] + ",")
				// .append(strings[i + 8] + ",").append(strings[i + 9] + ",")
				// .append(strings[i + 10] + ",")
				// .append(strings[i + 11].substring(0, strings[i + 11].length() - 5) + ",");
				map.put("trade_type", "国内通话".equals(arrs[3]) ? 1 : 2);
				map.put("trade_time", arrs[12]);
				map.put("call_time", date);
				map.put("trade_addr", arrs[5]);
				map.put("receive_phone", arrs[0]);
				map.put("call_type", "主叫".equals(arrs[2]) ? 1 : 2);
				map.put("borrower_id", id);
				map.put("updateTime", "");
				maps.add(map);
			}
		}
		br.close();
		isr.close();
		fis.close();
		return maps;
	}
}
