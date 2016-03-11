package com.android.volley.okhttp.gzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import com.android.volley.NetworkResponse;

/**
 * @author :jarlen
 * @date : 2016-3-11
 */
public class GzipUtil {

	public static final String HEADER_ENCODING = "Content-Encoding";
	public static final String ENCODING_GZIP = "gzip";
	public static final String HEADER_USER_GZIP = "Gzip";

	/**
	 * gzip compress
	 * @param str
	 * @param charset
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] compress(String str, String charset)
			throws IOException, UnsupportedEncodingException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		try {
			gzip.write(str.getBytes(charset));
			gzip.close();
			return out.toByteArray();
		} finally {

			if (gzip != null) {
				gzip.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * check it is gzip or not
	 * @param response
	 * @return
	 */
	public static boolean isGzipped(NetworkResponse response) {
		Map<String, String> headers = response.headers;
		return headers != null
				&& !headers.isEmpty()
				&& (headers.containsKey(HEADER_USER_GZIP) || (headers
						.containsKey(HEADER_ENCODING) && headers.get(
						HEADER_ENCODING).equalsIgnoreCase(ENCODING_GZIP)));
	}

	/**
	 * gzip decompress
	 * @param compressed
	 * @return
	 * @throws IOException
	 */
	public static byte[] decompressResponse(byte[] compressed)
			throws IOException {
		ByteArrayOutputStream baos = null;
		try {
			int size;
			ByteArrayInputStream memstream = new ByteArrayInputStream(
					compressed);
			GZIPInputStream gzip = new GZIPInputStream(memstream);
			final int buffSize = 256;
			byte[] tempBuffer = new byte[buffSize];
			baos = new ByteArrayOutputStream();
			while ((size = gzip.read(tempBuffer, 0, buffSize)) != -1) {
				baos.write(tempBuffer, 0, size);
			}
			return baos.toByteArray();
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}
}