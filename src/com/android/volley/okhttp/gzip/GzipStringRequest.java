package com.android.volley.okhttp.gzip;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

/**
 * String for gzip
 * 
 * @author :jarlen
 * @date : 2016-3-10
 */
public class GzipStringRequest extends StringRequest {

	private boolean mGzipEnabled = true;

	public GzipStringRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}

	public GzipStringRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		this(Method.GET, url, listener, errorListener);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String parsed = null;
		try {

			if (mGzipEnabled && GzipUtil.isGzipped(response)) {
				byte[] data = GzipUtil.decompressResponse(response.data);
				parsed = new String(data,
						HttpHeaderParser.parseCharset(response.headers));
			}

			if (parsed == null) {
				parsed = new String(response.data,
						HttpHeaderParser.parseCharset(response.headers));
			}
			return Response.success(parsed,
					HttpHeaderParser.parseCacheHeaders(response));

		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (IOException e) {
			return Response.error(new ParseError(e));
		}

	}

	/** Disables GZIP compressing (enabled by default) */
	public void disableGzip() {
		mGzipEnabled = false;
	}

}
