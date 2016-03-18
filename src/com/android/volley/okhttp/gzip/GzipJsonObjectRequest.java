package com.android.volley.okhttp.gzip;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

/**
 * JsonObject for gzip
 * 
 * @author :jarlen
 * @date : 2016-3-10
 */
public class GzipJsonObjectRequest extends JsonRequest<JSONObject> {

	private boolean mGzipEnabled = true;

	/**
	 * Creates a new request.
	 * 
	 * @param method
	 *            the HTTP method to use
	 * @param url
	 *            URL to fetch the JSON from
	 * @param requestBody
	 *            A {@link String} to post with the request. Null is allowed and
	 *            indicates no parameters will be posted along with request.
	 * @param listener
	 *            Listener to receive the String response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public GzipJsonObjectRequest(int method, String url, String requestBody,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, requestBody, listener, errorListener);
	}

	/**
	 * Creates a new request.
	 * 
	 * @param url
	 *            URL to fetch the JSON from
	 * @param listener
	 *            Listener to receive the String response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public GzipJsonObjectRequest(String url, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		super(Method.GET, url, null, listener, errorListener);
	}

	/**
	 * Creates a new request.
	 * 
	 * @param method
	 *            the HTTP method to use
	 * @param url
	 *            URL to fetch the JSON from
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public GzipJsonObjectRequest(int method, String url,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, null, listener, errorListener);
	}

	/**
	 * Creates a new request.
	 * 
	 * @param method
	 *            the HTTP method to use
	 * @param url
	 *            URL to fetch the JSON from
	 * @param jsonRequest
	 *            A {@link JSONObject} to post with the request. Null is allowed
	 *            and indicates no parameters will be posted along with request.
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public GzipJsonObjectRequest(int method, String url,
			JSONObject jsonRequest, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		super(method, url, (jsonRequest == null) ? null : jsonRequest
				.toString(), listener, errorListener);
	}

	/**
	 * Constructor which defaults to <code>GET</code> if
	 * <code>jsonRequest</code> is <code>null</code>, <code>POST</code>
	 * otherwise.
	 * 
	 * @see #JsonObjectRequest(int, String, JSONObject, Listener, ErrorListener)
	 */
	public GzipJsonObjectRequest(String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest,
				listener, errorListener);
	}
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headers = new HashMap<String, String>();
		if(mGzipEnabled){
			headers.put(GzipUtil.HEADER_ACCEPT_ENCODING, GzipUtil.ENCODING_GZIP);
		}
		
		return headers;
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		String parsed = null;
		try {
			if (mGzipEnabled && GzipUtil.isGzipped(response)) {
				byte[] data = GzipUtil.decompressResponse(response.data);
				parsed = new String(data, HttpHeaderParser.parseCharset(
						response.headers, PROTOCOL_CHARSET));
			}

			if (parsed == null) {
				parsed = new String(response.data,
						HttpHeaderParser.parseCharset(response.headers,
								PROTOCOL_CHARSET));
			}
			return Response.success(new JSONObject(parsed),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (Exception je) {
			return Response.error(new ParseError(je));
		}
	}

	/** Disables GZIP compressing (enabled by default) */
	public void disableGzip() {
		mGzipEnabled = false;
	}
}
