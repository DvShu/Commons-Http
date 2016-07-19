package com.shu.apache.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.shu.apache.http.exception.CodeErrorException;
import com.shu.apache.http.stream.StreamTool;

/**
 * @ClassName: HttpRequest
 * @Description: ��װ��Commons-http����
 * @author haoran.shu
 * @date 2016-3-14 ����3:23:55
 */
public class HttpRequest {

	/**
	 * @Title: exec
	 * @Description: HTTP����(post form����)
	 * @param url
	 * @param params
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws CodeErrorException
	 * @return String
	 */
	public static String exec(String url, List<NameValuePair> params)
			throws ClientProtocolException, IOException, CodeErrorException {
		// ����POST����
		HttpPost request = new HttpPost(url);
		HttpClient client = new DefaultHttpClient(); // ����Ĭ�ϵ�HTTP����ִ��
		// �������ӳ�ʱʱ��Ϊ1����
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		// ���ö�ȡ���ݵĵȴ�ʱ��Ϊ1����
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		request.setEntity(new UrlEncodedFormEntity(params, "UTF-8")); // �����������
		HttpResponse response = client.execute(request); // ִ��POST����
		// �ж����󷵻���
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity(); // ��ȡ����ʵ��
			// ȡ�����󷵻�����
			String content = StreamTool.getInputStream1(entity.getContent());
			request.abort(); // �Ͽ�����
			// ���ض�ȡ�����󷵻�����
			return content;
		} else {
			throw new CodeErrorException(response.getStatusLine()
					.getStatusCode(), response.getStatusLine()
					.getReasonPhrase());
		}
	}

	/**
	 * @Title: httpClientPost2Str2
	 * @Description: �����ַ�������(������JSON�ַ���)
	 * @param url
	 *            �����URL
	 * @param string
	 *            ��Ҫ�ϴ�(�ύ)������
	 * @return String ���������ص�String���͵�����
	 * @throws Exception
	 *             �쳣
	 */
	public static String exec(String url, String string) throws Exception {
		// ��Ҫ�ϴ����������Ϊ��,����Ϊ���ַ���
		if (string == null) {
			string = "";
		}
		// ����HttpClient����
		HttpClient httpClient = new DefaultHttpClient();
		// ����post����
		HttpPost httpPost = new HttpPost(url);
		// ��������ʵ��
		StringEntity entity = new StringEntity(string, "UTF-8");// ������������
		entity.setContentEncoding("UTF-8");// ����
		entity.setContentType("application/json");
		httpPost.setEntity(entity);// ��Ҫ�ϴ�������,����post������
		HttpResponse response = httpClient.execute(httpPost);// ִ��post����
		// �ж����󷵻���
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity responseEntity = response.getEntity(); // ��ȡ����ʵ��
			// ȡ�����󷵻�����
			String content = StreamTool.getInputStream1(responseEntity.getContent());
			httpPost.abort(); // �Ͽ�����
			// ���ض�ȡ�����󷵻�����
			return content;
		} else {
			throw new CodeErrorException(response.getStatusLine()
					.getStatusCode(), response.getStatusLine()
					.getReasonPhrase());
		}
	}
}
