package com.shu.apache.http.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @ClassName: StreamTool
 * @Description: 跟流相关的工具类方法
 * @author haoran.shu
 * @date 2016-3-14 下午3:16:12
 */
public class StreamTool {

	/**
	 * @Title: getInputStream
	 * @Description: 读取输入流中的数据
	 * @param in
	 * @return String
	 * @throws IOException
	 */
	public static String getInputStream(InputStream in) throws IOException {
		// 将字节流向字符流的转换。
		InputStreamReader isr = new InputStreamReader(in, "UTF-8");// 读取
		// 创建字符流缓冲区
		BufferedReader bufr = new BufferedReader(isr);// 缓冲
		StringBuilder builder = new StringBuilder(); // 保存读取到的数据
		String line; // 读取每一行数据
		while ((line = bufr.readLine()) != null) {
			builder.append(line); // 保存读取到的每一行数据
		}
		// 关闭流
		isr.close();
		bufr.close();
		return builder.toString();
	}

	/**
	 * @Title: getInputStream1
	 * @Description: 读取InputSteam中的数据(第2种方法)
	 * @param in
	 * @return String
	 * @throws IOException
	 */
	public static String getInputStream1(InputStream in) throws IOException {
		byte[] b = new byte[1024]; // 构建字节缓存数组
		int length = 0; // 保存每一次读取的字节数
		StringBuilder sb = new StringBuilder();
		// 循环读取
		while ((length = in.read(b)) != -1) {
			// 将每一次读取的字节转换为字符,避免乱码
			sb.append(new String(b, 0, length, "utf-8"));
		}
		in.close();// 关闭流
		return sb.toString();
	}
}
