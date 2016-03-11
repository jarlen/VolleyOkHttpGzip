package com.android.volley.okhttp.gzip;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

/**
 * JsonArray for gzip
 * 
 * @author :jarlen
 * @date : 2016-3-10
 */
public class GzipJsonArrayRequest extends JsonArrayRequest {

	private boolean mGzipEnabled = true;

	/**
	 * Creates a new request.
	 * 
	 * @param url
	 *            URL to fetch the JSON from
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public GzipJsonArrayRequest(String url, Listener<JSONArray> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);
	}

	@Override
	protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
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

			return Response.success(new JSONArray(parsed),
					HttpHeaderParser.parseCacheHeaders(response));

		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException e) {
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
