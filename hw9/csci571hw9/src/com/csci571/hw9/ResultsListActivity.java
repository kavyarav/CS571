package com.csci571.hw9;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v4.app.NavUtils;

public class ResultsListActivity extends Activity {

	ArrayList<Result> resultsList;
	TextView tv;
	ListView rList;
	//Context c = this;
	ResultAdapter ra;
	
	private class ServletConnect extends AsyncTask<String, Void, String> {
//		private static final int REG_TIMEOUT = 3 * 1000;
//		private static final int WATT_TIMEOUT = 30 * 1000;
//		private final HttpClient httpC = new DefaultHttpClient();
//		final HttpClient params = httpC.getParams();
		
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// get the JSON results
			//BufferedInputStream buff;
			String result = "test";
			for (String param : params) {
				result = param;
				//			try {
					HttpClient httpC = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(param);
					//result = httpGet.toString();
					HttpResponse responseGet = null;
					try {
						responseGet = httpC.execute(httpGet);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					HttpEntity httpE = responseGet.getEntity();
					if (httpE != null) {
						try {
							result = EntityUtils.toString(httpE);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//URL url = new URL(param); // i should only need this one right?
					//HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
					//InputStream in = urlConn.getInputStream();
//					buff = new BufferedInputStream(in);
//					result = buff.toString();
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}	
			}
			return result;
		}
		
		// update the UI?
		@Override
		protected void onPostExecute(String result) {
			//tv.setText(result);
			// get JSON Object stick it in array
			JSONObject resultsJSON;
			try {
				resultsJSON = new JSONObject(result);
			//	tv.setText(resultsJSON.toString());
				JSONObject results = resultsJSON.getJSONObject("results");
				JSONArray resultsArray = results.getJSONArray("result");
			//	tv.setText(resultsArray.toString());
				//tv.setText(Integer.toString(resultsArray.length()));
				resultsList = new ArrayList<Result>();
				if (resultsArray.length() == 0) {
					CharSequence toastStr = "No results!";
					Toast toast = Toast.makeText(getApplicationContext(), toastStr, Toast.LENGTH_LONG);
					toast.show();
					finish();
				}
				
				for (int i = 0; i < resultsArray.length(); ++i) {
					JSONObject resultJSON = resultsArray.getJSONObject(i);
					//String test = resultJSON.getString("title");
					//tv.setText(test);
					Result r = new Result(resultJSON.getString("cover"),
											resultJSON.getString("title"),
											resultJSON.getString("year"),
											resultJSON.getString("director"),
											resultJSON.getString("rating"),
											resultJSON.getString("details"));
					//tv.setText(r.toString());
					resultsList.add(r);
					//ra.add(r);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			displayResults(resultsList);
							
		}
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_list);
		// construct query to Java servlet
		tv = new TextView(this);
		String title = getIntent().getStringExtra("title");
		try {
			title = URLEncoder.encode(title, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String type = getIntent().getStringExtra("type");	
		String query = "http://cs-server.usc.edu:10174/examples/servlet/MovieSearch?title=" + title + "&type=" + type;
		
		//resultsList = new ArrayList<Result>();
		
		

		ServletConnect sc = new ServletConnect();
		sc.execute(new String[] { query });
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void displayResults(ArrayList<Result> r) {
		rList = (ListView) findViewById(R.id.result_list);
		ra = new ResultAdapter(this, R.layout.result_layout, r);
		rList.setAdapter(ra);
		rList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				Result res = (Result) parent.getItemAtPosition(pos);
				DialogFragment resultFrag = new ResultDialog(res);
				resultFrag.show(getFragmentManager(), "deets");
			}
		});
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_results_list, menu);
		return true;
	}

}
