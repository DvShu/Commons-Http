# Commons-Http
这是一个对于apache的http网络连接库的封装。

使用：
----
>1.提交post form数据:
>>```Java
public static String searchOrder() throws ClientProtocolException, IOException, CodeErrorException{
	// 构建请求参数
	List<NameValuePair> nvps = new ArrayList<>();
	nvps.add(new BasicNameValuePair("oid", "100000"));
	// 执行请求
	return HttpRequest.exec(SERVER + "/queryOrder.do", nvps);
}
```
>2.JSON请求
>>```Java
public static String searchTest() throws Exception {
	String params = "{'oid': 'dasdfdaasdf'}";
	return HttpRequest.exec(SERVER + "/queryOrder.do", params);
}
```
>3.通用HTTP请求
>>```Java
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
```
通用的HTTP请求的方法为
```Java
String exec(String url, HTTPEntity entity);
```

change logs:
----
>v1.0:  
>>-- 这是第一次上传的工程版本<br>
>>-- 目前只做了对于提交post form数据的时候做了封装

>v1.1
>>-- 新增了一个通过POST提交字符串数据的方法
    
>v1.2
>>-- 更新请求库为最新的Commons-http请求包
>>-- 新增了一个通用请求接口
>>-- 更新了内部请求是实现为最新的 commons-http 实现
