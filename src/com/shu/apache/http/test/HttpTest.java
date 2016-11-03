package com.shu.apache.http.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.message.BasicNameValuePair;

import com.shu.apache.http.HttpRequest;
import com.shu.apache.http.exception.CodeErrorException;
import com.shu.apache.http.mime.content.LocaleFileBody;

/**
 * @ClassName: HttpTest 
 * @Description: 封装的请求测试类
 * @author haoran.shu
 * @date 2016-3-14 下午3:27:12
 */
public class HttpTest {
	private static final String SERVER = "http://192.173.1.17:8080/TestServer";
	private static final String FILE_SERVER = "http://127.0.0.1:8080/AppVersionService";
	
	public static void main(String[] args) {
		try {
			
			// System.out.println("查询订单信息：" + searchOrder());
			System.out.println("上传文件：" + uploadFile());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String searchOrder() throws ClientProtocolException, IOException, CodeErrorException{
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("oid", "100000"));
		return HttpRequest.exec(SERVER + "/queryOrder.do", nvps);
	}
	
	public static String searchTest() throws Exception {
		String params = "{'oid': 'dasdfdaasdf'}";
		return HttpRequest.exec(SERVER + "/queryOrder.do", params);
	}
	
	public static String uploadFile() throws IOException {
		File file = new File("D:\\日志系统.txt");
		LocaleFileBody fileBody = new LocaleFileBody(file.getName(), file.length(), new FileInputStream(file), ContentType.DEFAULT_BINARY);
		// FileBody fileBody = new FileBody(new File("D:\\日志系统.txt"), ContentType.MULTIPART_FORM_DATA);
		HttpEntity reqEntity = MultipartEntityBuilder.create()
			// .addBinaryBody("file", new FileInputStream("D:\\日志系统.txt"), ContentType.MULTIPART_FORM_DATA, "日志系统")
			.addPart("file", fileBody)
			.addTextBody("flag", "image")
			.build();
		return HttpRequest.exec(FILE_SERVER + "/file/uploadFileAjax.do", reqEntity);
	}
}
