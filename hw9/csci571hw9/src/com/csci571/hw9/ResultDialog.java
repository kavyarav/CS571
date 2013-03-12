package com.csci571.hw9;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ResultDialog extends DialogFragment {
	
	Result result;
	ViewHolder vh;
	
	// Facebook stuff
//	Facebook fb = new Facebook("454449914592993");
//	AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(fb);
	
	static class ViewHolder {
		public ImageView poster;
		public TextView title;
		public TextView year;
		public TextView director;
		public TextView rating;
		public TextView fb;
	}

	
	public ResultDialog(Result result) {
		super();
		this.result = result;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		StrictMode.ThreadPolicy policy = new StrictMode.
//		ThreadPolicy.Builder().permitAll().build();
//		StrictMode.setThreadPolicy(policy); 
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater li = getActivity().getLayoutInflater();
		View resultView = li.inflate(R.layout.result_dialog, null);
	//	if (resultView != null) {
			vh = new ViewHolder();
			vh.poster = (ImageView) resultView.findViewById(R.id.d_poster);
			vh.title = (TextView) resultView.findViewById(R.id.d_title);
			vh.year = (TextView) resultView.findViewById(R.id.d_year);
			vh.director = (TextView) resultView.findViewById(R.id.d_director);
			vh.rating = (TextView) resultView.findViewById(R.id.d_rating);
			vh.fb = (TextView) resultView.findViewById(R.id.d_fb);
			resultView.setTag(vh);
		//}
		
		builder.setTitle("Details").setView(resultView)
			.setCancelable(false)
			.setPositiveButton("spam", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO FB stuff
					Intent fbIntent = new Intent(getActivity(), FBLoginActivity.class);
					fbIntent.putExtra("title", result.getTitle());
					fbIntent.putExtra("year", result.getYear());
					fbIntent.putExtra("director", result.getDirector());
					fbIntent.putExtra("rating", result.getRating());
					fbIntent.putExtra("poster", result.getPoster());
					fbIntent.putExtra("deets", result.getLink());
					startActivity(fbIntent);
				}
			});
		
		if (result != null) {
			Drawable img;
			try {
				img = getPosterFromURL(result.getPoster());
				vh.poster.setImageDrawable(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			vh.title.setText("Name: " + result.getTitle());
			vh.year.setText("Year: " + result.getYear());
			vh.director.setText("Director: " + result.getDirector());
			if (result.getRating().equals("N/A")) {
				vh.rating.setText("Rating: " + result.getRating());
			} else {
			vh.rating.setText("Rating: " + result.getRating() + "/10");
			}
		}
		
		return builder.create();
	}
	
	private Drawable getPosterFromURL(String poster_url) throws IOException {
		URL url = new URL(poster_url);
		Object content = url.getContent();
		InputStream is = (InputStream) content;
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}

}
