package com.csci571.hw9;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.csci571.hw9.R;

public class MainActivity extends Activity {

	EditText titleBox;
	Spinner typeSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// title box, user enters this
		titleBox = (EditText) findViewById(R.id.title_box);
		//title = titleBox.getText().toString();
		//Log.v("searchTitle", title);
		
		
		
		// TODO test for empty input
		
		// Spinner
		typeSpinner = (Spinner) findViewById(R.id.type_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
											R.array.types_array, 
											android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(adapter);
		
		
		
		Button search = (Button) findViewById(R.id.search_button);
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// why does it not get titleBox & typeSpinner unless they're class vars
				// examples don't seem to need it
				String title = titleBox.getText().toString();				
				String type = typeSpinner.getSelectedItem().toString();
				if (type.equals("All Types")) {
					type = "feature,tv_series,game";
				} else if (type.equals("Film")) {
					type = "feature";
				} else if (type.equals("TV")) {
					type = "tv_series";
				} else if (type.equals("Video Games")) {
					type = "game";
				}
					
				// TEMP Debugging!!!
//				CharSequence toastStr = title + " of type " + type;
//				Toast toast = Toast.makeText(v.getContext(), toastStr, Toast.LENGTH_LONG);
//				toast.show()
				if (title.equals("")) {
					CharSequence toastStr = "Insert a title!";
					Toast toast = Toast.makeText(v.getContext(), toastStr, Toast.LENGTH_LONG);
					toast.show();			
				} else {
					Intent myIntent = new Intent(v.getContext(), ResultsListActivity.class);
					myIntent.putExtra("title", title);
					myIntent.putExtra("type", type);
					startActivity(myIntent);
				}

			}
		});
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		
		return true;
	}
	


}
