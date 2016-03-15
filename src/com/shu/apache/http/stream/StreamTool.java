package com.shu.apache.http.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @ClassName: StreamTool
 * @Description: ������صĹ����෽��
 * @author haoran.shu
 * @date 2016-3-14 ����3:16:12
 */
public class StreamTool {

	/**
	 * @Title: getInputStream
	 * @Description: ��ȡ�������е�����
	 * @param in
	 * @return String
	 * @throws IOException
	 */
	public static String getInputStream(InputStream in) throws IOException {
		// ���ֽ������ַ�����ת����
		InputStreamReader isr = new InputStreamReader(in, "UTF-8");// ��ȡ
		// �����ַ���������
		BufferedReader bufr = new BufferedReader(isr);// ����
		StringBuilder builder = new StringBuilder(); // �����ȡ��������
		String line; // ��ȡÿһ������
		while ((line = bufr.readLine()) != null) {
			builder.append(line); // �����ȡ����ÿһ������
		}
		// �ر���
		isr.close();
		bufr.close();
		return builder.toString();
	}

	/**
	 * @Title: getInputStream1
	 * @Description: ��ȡInputSteam�е�����(��2�ַ���)
	 * @param in
	 * @return String
	 * @throws IOException
	 */
	public static String getInputStream1(InputStream in) throws IOException {
		byte[] b = new byte[1024]; // �����ֽڻ�������
		int length = 0; // ����ÿһ�ζ�ȡ���ֽ���
		StringBuilder sb = new StringBuilder();
		// ѭ����ȡ
		while ((length = in.read(b)) != -1) {
			// ��ÿһ�ζ�ȡ���ֽ�ת��Ϊ�ַ�,��������
			sb.append(new String(b, 0, length, "utf-8"));
		}
		in.close();// �ر���
		return sb.toString();
	}
}
