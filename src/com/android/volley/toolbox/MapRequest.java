package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;


/**
 * 重写缓存key： 如果请求带有参数，以url+params为CacheKey
 * @author :jarlen
 * @date : 2016-3-11
 */
public class MapRequest extends Request<Map<String, Object>> {

	private final Listener<Map<String, Object>> mListener;

	/**
	 * Creates a new request with the given method.
	 * 
	 * @param method
	 *            the request {@link Method} to use
	 * @param url
	 *            URL to fetch the string at
	 * @param listener
	 *            Listener to receive the String response
	 * @param errorListener
	 *            Error listener, or null to ignore errors
	 */
	public MapRequest(int method, String url,
			Listener<Map<String, Object>> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	/**
	 * Creates a new GET request.
	 * 
	 * @param url
	 *            URL to fetch the string at
	 * @param listener
	 *            Listener to receive the String response
	 * @param errorListener
	 *            Error listener, or null to ignore errors
	 */
	public MapRequest(String url, Listener<Map<String, Object>> listener,
			ErrorListener errorListener) {
		this(Method.GET, url, listener, errorListener);
	}

	@Override
	protected void deliverResponse(Map<String, Object> response) {
		mListener.onResponse(response);
	}

	@Override
	public String getCacheKey() {
		try {
			if (getParams() == null) {
				return super.getCacheKey();
			}
			return super.getCacheKey() + getParams().toString();
		} catch (AuthFailureError e) {
			e.printStackTrace();
			return super.getCacheKey();
		}
	}

	@Override
	protected Response<Map<String, Object>> parseNetworkResponse(
			NetworkResponse response) {
		String parsed = null;
		try {
			parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		Map<String, Object> map = null;

		return Response.success(map,
				HttpHeaderParser.parseCacheHeaders(response));
	}
}
