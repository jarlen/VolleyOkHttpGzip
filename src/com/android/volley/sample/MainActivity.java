package com.android.volley.sample;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.R;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView resultTv = null;
	private EditText httpContent = null;

	private RequestQueue requestQueue = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		httpContent = (EditText) findViewById(R.id.httpContent);
		httpContent.setText("www.baidu.com");
		resultTv = (TextView) findViewById(R.id.result);

		findViewById(R.id.go).setOnClickListener(this);

		requestQueue = Volley.newRequestQueue(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.go:
			String request = httpContent.getText().toString();

			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject();
				jsonObject.put("id", "1");
				jsonObject.put("jsonrpc", "2.0");
				jsonObject.put("method", "applicationAdvertiseQuery");
				JSONObject params = new JSONObject();
				params.put("method", "applicationAdvertiseQuery");
				params.put("what", -1);
				jsonObject.put("params", params);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// "http://11.12.110.225:8200/mdms/api"
			JsonObjectRequest objectRequest = new JsonObjectRequest(
					Method.POST, "http://11.12.110.225:8200/mdms/api",
					jsonObject, new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {

							Log.i("===", "onResponse  " + response.toString());
							resultTv.setText(response.toString());
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub
							Log.i("===", "onErrorResponse  " + error.toString());
						}
					});

			objectRequest.setShouldCache(true);

			requestQueue.add(objectRequest);

			break;

		default:
			break;
		}
	}
}
