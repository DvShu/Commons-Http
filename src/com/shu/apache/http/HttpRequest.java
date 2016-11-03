package com.shu.apache.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.shu.apache.http.exception.CodeErrorException;
import com.shu.apache.http.stream.StreamTool;

/**
 * @ClassName: HttpRequest
 * @Description: 封装的Commons-http请求
 * @author haoran.shu
 * @date 2016-3-14 下午3:23:55
 */
public class HttpRequest {

	/**
	 * @Title: exec
	 * @Description: HTTP请求(post form请求)
	 * @param url
	 * @param params
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws CodeErrorException
	 * @return String
	 */
	public static String exec(String url, List<NameValuePair> params)
			throws ClientProtocolException, IOException, CodeErrorException {
		// 构造POST请求
		HttpPost request = new HttpPost(url);
		HttpClient client = HttpClients.createDefault(); // 构造默认的HTTP请求执行
		request.setEntity(new UrlEncodedFormEntity(params, "UTF-8")); // 设置请求参数
		HttpResponse response = client.execute(request); // 执行POST请求
		// 判断请求返回码
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity(); // 获取请求实体
			// 取的请求返回数据
			String content = StreamTool.getInputStream1(entity.getContent());
			request.abort(); // 断开请求
			// 返回读取的请求返回数据
			return content;
		} else {
			throw new CodeErrorException(response.getStatusLine()
					.getStatusCode(), response.getStatusLine()
					.getReasonPhrase());
		}
	}

	/**
	 * @Title: httpClientPost2Str2
	 * @Description: 发送字符串对象(可以是JSON字符串)
	 * @param url
	 *            请求的URL
	 * @param string
	 *            需要上传(提交)的内容
	 * @return String 服务器返回的String类型的内容
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws CodeErrorException 
	 * @throws Exception
	 *             异常
	 */
	public static String exec(String url, String string) throws ClientProtocolException, 
		IOException, CodeErrorException {
		// 需要上传的内容如果为空,重置为空字符串
		if (string == null) {
			string = "";
		}
		// 构造HttpClient连接
		HttpClient httpClient = HttpClients.createDefault();
		// 构造post请求
		HttpPost httpPost = new HttpPost(url);
		// 构建请求实体
		StringEntity entity = new StringEntity(string, "UTF-8");// 避免中文乱码
		entity.setContentEncoding("UTF-8");// 编码
		entity.setContentType("application/json");
		httpPost.setEntity(entity);// 将要上传的内容,放入post请求当中
		HttpResponse response = httpClient.execute(httpPost);// 执行post请求
		// 判断请求返回码
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity responseEntity = response.getEntity(); // 获取请求实体
			// 取的请求返回数据
			String content = StreamTool.getInputStream1(responseEntity.getContent());
			httpPost.abort(); // 断开请求
			// 返回读取的请求返回数据
			return content;
		} else {
			throw new CodeErrorException(response.getStatusLine()
					.getStatusCode(), response.getStatusLine()
					.getReasonPhrase());
		}
	}
	
	/**
	 * 执行HTTP POST请求(ClientMultipartFormPost),通用请求接口
	 * HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("bin", bin)
                    .addPart("comment", comment)
                    .build();
	 * @param url			请求地址URI
	 * @param reqEntity		请求内容
	 * @return 请求响应返回结果
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws CodeErrorException
	 */
	public static String exec(String url, HttpEntity reqEntity) throws IOException {
		String result = "success";
		// 构造HttpClient连接
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url); // post请求
		httpPost.setEntity(reqEntity); // 设置请求内容
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost); // 执行请求
			HttpEntity resEntity = response.getEntity(); // 获取返回内容
			result = EntityUtils.toString(resEntity, "UTF-8");
		} finally {
			// 关闭请求
			response.close();
			httpClient.close();
		}
		return result;
	}
}
