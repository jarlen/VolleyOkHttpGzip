# VolleyOkHttpGzip

This library includes two open source library code : 

volley(Mirror https://github.com/mcxiaoke/android-volley) ,

okhttp(Mirror : https://github.com/square/okhttp),

okio(Mirror : https://github.com/square/okio).


1.  modify volley source code for the cachekey of jsonRequest ,like this mark(@github.com/jarlen) in JsonRequest.java
    (origin："Method + url" as cacheKey,now : "mehod + url + params" as cacheKey)

2.  add MapRequest

3.  OkHttpClient as the transport layer for Volley (@link https://gist.github.com/bryanstern/4e8f1cb5a8e14c202750)

	private RequestQueue requestQueue = Volley.newRequestQueueWithOkHttp(this);

4.	Add gzip response for volley  response
	
	GzipJsonObjectRequest objectRequest = new GzipJsonObjectRequest(?,?,?,?);
	
5.	Modify newRequestQueue function for changing cache dir

	private RequestQueue requestQueue = Volley.newRequestQueue(this, "mnt/sdcard/");


这个库包括三个开源项目代码，Volley OkHttp,Gzip

主要修改了以下几点:

1.  优化了JsonRequest请求缓存处理Key，原始使用Method + Url 的方式，不能标示唯一的网络请求数据缓存Key,现调整为Method + url + params as cachekey (个人理解这样)

2.  添加了网络请求参数为Map的请求方式

3.  引入OkHttpClient作为Volley的网络传输层，具体参考与:(https://gist.github.com/bryanstern/4e8f1cb5a8e14c202750)

	private RequestQueue requestQueue = Volley.newRequestQueueWithOkHttp(this);
	
4.  添加了网络请求返回数据Gzip数据解压处理。

	GzipJsonObjectRequest objectRequest = new GzipJsonObjectRequest(?,?,?,?);

5.	改变volley请求队列创建接口，可以自定义缓存路径
	
	private RequestQueue requestQueue = Volley.newRequestQueue(this, "mnt/sdcard/");
	

小弟不才，仅仅在原有开源库基础上做了一些调整，欢迎大家检视代码并指正，有兴趣的同学可以一起优化。

