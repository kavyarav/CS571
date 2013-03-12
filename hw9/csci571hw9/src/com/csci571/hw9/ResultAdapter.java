package com.csci571.hw9;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
// Custom adapter!
class ResultAdapter extends ArrayAdapter<Result> {
	
	private ArrayList<Result> resultList;
	private int textViewRid;
	private Activity context;
	
	static class ViewHolder {
		public ImageView poster;
		public TextView title;
		public TextView rating;
	}

	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public ResultAdapter(Context context, int textViewResourceId, 
			List<Result> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		StrictMode.ThreadPolicy policy = new StrictMode.
		ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		resultList = new ArrayList<Result>();
		resultList.addAll(objects);
		textViewRid = textViewResourceId;
		this.context = (Activity) context;
	}
	
	@Override
	public void add(Result r) {
		resultList.add(r);
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		//View rowView = convertView;
		//View rowView;
		ViewHolder vh;
		
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) context.getLayoutInflater();
			convertView = vi.inflate(textViewRid, null);
			vh = new ViewHolder();
			vh.poster = (ImageView) convertView.findViewById(R.id.poster);
			vh.title = (TextView) convertView.findViewById(R.id.title);
			vh.rating = (TextView) convertView.findViewById(R.id.rating);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		
		
		Result res = resultList.get(pos);
		if (res != null) {
			// set image
	//		Bitmap bmap;
			// TODO: LOAD THE IMAGE
//			try {
//				bmap = BitmapFactory.decodeStream((InputStream) new URL(res.getPoster()).openStream());
//				vh.poster.setImageBitmap(bmap);
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			Drawable img;
			try {
				img = getPosterFromURL(res.getPoster());
				vh.poster.setImageDrawable(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// set Title
			vh.title.setText(res.getTitle() + "(" + res.getYear() + ")");
			vh.rating.setText("Rating:" + res.getRating());
		}
		return convertView;
	}
	
	private Drawable getPosterFromURL(String poster_url) throws IOException {
		URL url = new URL(poster_url);
		Object content = url.getContent();
		InputStream is = (InputStream) content;
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}
	
}
