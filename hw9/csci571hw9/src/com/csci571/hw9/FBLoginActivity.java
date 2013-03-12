package com.csci571.hw9;

//import com.facebook.android.Facebook;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import com.facebook.FacebookActivity;
import com.facebook.FacebookException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

//import com.facebook.android.*;
//import com.facebook.*;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class FBLoginActivity extends FacebookActivity{
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fblogin);
		this.openSession();
	//	publishFeedDialog();
		if (this.isSessionOpen()) {
			publishFeedDialog();
		}
	}

	@Override
	protected void onSessionStateChange(SessionState state, Exception excep) {
		// user logged in or not?
		if (state.isOpened()) {
			publishFeedDialog();
			
		}
	}
	
	//@Override
/*	protected void onResumeFragments() {
		super.onResumeFragments();
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session.getState(), null);
		}
	}
	*/
	private void publishFeedDialog() {
		Bundle params = new Bundle();
		params.putString("name", getIntent().getStringExtra("title") + "(" +
								getIntent().getStringExtra("year") + ")");
		params.putString("caption", "I am interested in this thing");
		String description = getIntent().getStringExtra("title") + " released in " +
							getIntent().getStringExtra("year") + " has a rating of " + 
							getIntent().getStringExtra("rating");
		params.putString("description", description);
		params.putString("link", getIntent().getStringExtra("deets"));
		params.putString("picture", getIntent().getStringExtra("poster"));
		String reviewLink = getIntent().getStringExtra("deets") + "reviews";
		JSONObject properties = new JSONObject();
		JSONObject reviewHref = new JSONObject();
		try {
			reviewHref.put("text", "here");
			reviewHref.put("href", reviewLink);
			properties.put("Look at user reviews", reviewHref);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.putString("properties", properties.toString());
		
		
		
		//String rateString = "{\"Look at user reviews\" : { \"text\" : \"here\", \"href\": reviewLink  }}";
//		String rateString = "{\"Look at user reviews\" : { \"text\" : \"here\", \"href\":" + reviewLink + "}}";
		//params.putString("properties", rateString);
		// for "properties" part of the dialog? oh god this is so fucking hacky
		// man i hope it works and it doesn't BAHHH
//		Bundle props = new Bundle();
//		Bundle desc = new Bundle();
//		desc.putString("text", "here");
//		desc.putString("href", reviewLink);
//		props.putBundle("Look at these user reviews" , desc);
//		params.putBundle("properties", props);
		
	    WebDialog feedDialog = (
	            new WebDialog.FeedDialogBuilder(this,
	                Session.getActiveSession(),
	                params))
	            .setOnCompleteListener(new OnCompleteListener() {
	                @Override
	                public void onComplete(Bundle values,
	                    FacebookException error) {
	                    // When the story is posted, echo the success
	                    // and the post Id.
	                	// i'm guessing values is null for some stupid fucking reason, 
	                	//but this break point isn't ever reached so i can't fucking check
	                    final String postId = values.getString("post_id");
	                    if (postId != null) {
	                        Toast.makeText(getBaseContext(),
	                            "Posted story, id: "+ postId,
	                        Toast.LENGTH_SHORT).show();
	                    }
	                    finish();
	                }

	            })
	            .build();
	    feedDialog.show();
		//finishActivity(RESULT_OK);
	    // super.onBackPressed();
	   // finish();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_fblogin, menu);
		return true;
	}
	
}
