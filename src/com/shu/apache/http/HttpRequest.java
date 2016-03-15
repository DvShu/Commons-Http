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
}
