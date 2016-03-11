# VolleyOkHttpGzip

This library includes two open source library code : 

volley(Mirror https://github.com/mcxiaoke/android-volley) ,

okhttp(Mirror : https://github.com/square/okhttp),

okio(Mirror : https://github.com/square/okio).


1.  modify volley source code for the cachekey of jsonRequest ,like this mark(@github.com/jarlen) in JsonRequest.java

2.  add MapRequest

3.  OkHttpClient as the transport layer for Volley 

4.	Add gzip response for volley  response
