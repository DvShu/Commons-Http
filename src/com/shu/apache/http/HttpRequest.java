package com.shu.apache.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	public static String exec(String url, List<NameValuePair> params) throws ClientProtocolException, IOException, CodeErrorException{
		// 构造POST请求
		HttpPost request = new HttpPost(url);
		HttpClient client = new DefaultHttpClient(); // 构造默认的HTTP请求执行
		// 设置连接超时时间为1分钟
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		// 设置读取内容的等待时间为1分钟
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		request.setEntity(new UrlEncodedFormEntity(params, "UTF-8")); // 设置请求参数
		HttpResponse response = client.execute(request); // 执行POST请求
		// 判断请求返回码
		if(response.getStatusLine().getStatusCode() == 200){
			HttpEntity entity = response.getEntity(); // 获取请求实体
			// 取的请求返回数据
			String content = StreamTool.getInputStream1(entity.getContent());
			request.abort(); // 断开请求
			// 返回读取的请求返回数据
			return content;
		}else{
			throw new CodeErrorException(response.getStatusLine().getStatusCode(), 
					response.getStatusLine().getReasonPhrase());
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
	 * @throws Exception
	 *             异常
	 */
	public static String exec(String url, String string) throws Exception {
		// 需要上传的内容如果为空,重置为空字符串
		if (string == null) {
			string = "";
		}
		String responseContent = "";// 响应内容
		// 构造HttpClient连接
		HttpClient httpClient = new DefaultHttpClient();
		try {
			// 构造post请求
			HttpPost httpPost = new HttpPost(url);
			// 构建请求实体
			StringEntity entity = new StringEntity(string, "UTF-8");// 避免中文乱码
			entity.setContentEncoding("UTF-8");// 编码
			entity.setContentType("application/json");
			httpPost.setEntity(entity);// 将要上传的内容,放入post请求当中
			HttpResponse response = httpClient.execute(httpPost);// 执行post请求
			// 获取响应实体
			HttpEntity responseEntity = response.getEntity();
			// 获取请求头里的编码格式
			String charset = "UTF-8";
			// If the response does not enclose an entity, there is no need
			// to bother about connection release
			if (responseEntity != null) {
				// 获取输入流
				InputStream instream = responseEntity.getContent();
				// 读取输入流里面的内容
				try {
					StringBuffer sb = new StringBuffer();
					// 从输入流中读取数据
					BufferedReader in = new BufferedReader(new InputStreamReader(instream, charset));
					String inputLine;
					while((inputLine = in.readLine()) != null){
						sb.append(inputLine);
					}
					responseContent = sb.toString();
				} catch (IOException ex) {
					throw ex;
				} catch (RuntimeException ex) {
					httpPost.abort();// 中断请求
					throw ex;
				} finally {
					try {
						instream.close();
					} catch (Exception ignore) {
						throw ignore;
					}
				}
			}
		} catch (Exception e) {
			// handle exception
			throw e;
		} finally {
			// 断开连接
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
}
