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

change logs:
----
>v1.0:  
>>-- 这是第一次上传的工程版本<br>
>>-- 目前只做了对于提交post form数据的时候做了封装

>v1.1
>>-- 新增了一个通过POST提交字符串数据的方法
    
