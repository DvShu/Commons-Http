package com.shu.apache.http.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import com.shu.apache.http.HttpRequest;
import com.shu.apache.http.exception.CodeErrorException;

/**
 * @ClassName: HttpTest 
 * @Description: 封装的请求测试类
 * @author haoran.shu
 * @date 2016-3-14 下午3:27:12
 */
public class HttpTest {
	private static final String SERVER = "http://192.173.1.17:8080/TestServer";
	
	
	public static void main(String[] args) {
		try {
			
			System.out.println("查询订单信息：" + searchOrder());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CodeErrorException e) {
			e.printStackTrace();
		}
	}
	
	public static String searchOrder() throws ClientProtocolException, IOException, CodeErrorException{
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("oid", "100000"));
		return HttpRequest.exec(SERVER + "/queryOrder.do", nvps);
	}
	
	
}
