package com.luke.appaday.cramcards;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainScreenActivity extends AppCompatActivity {
	private static final String TAG = "FLASHCARDS";
	public static final String FILES = "LIST_OF_FILES";
	public static final String DL = "DOWNLOAD";
	private static final int TABLE_OF_CONTENTS_INFO = 2;
	private final Context context = this;
	private ProgressDialog pd;
	private ArrayList<String> selectedFiles;
	private ArrayAdapter<String> adapter;
	private ListView listView;
	private static final int SETTINGS_INFO = 1;

	private boolean receiverRegistered = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_main_screen);
		Button downloadButton = (Button) findViewById(R.id.downloadFromWebsiteButton);
		showFiles();
		// add button listener
		downloadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				download("https://raw.githubusercontent.com/ll2585/ll2585.github.io/master/korean/test.csv");
			}
		});
		selectedFiles = new ArrayList<>();
		Button startButton = (Button) findViewById(R.id.startTableOfContentsButton);
		// add button listener
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startTableOfContents();

			}
		});

		// Allows use to track when an intent with the id TRANSACTION_DONE is executed
		// We can call for an intent to execute something and then tell use when it finishes
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(FileService.TRANSACTION_DONE);

		// Prepare the main thread to receive a broadcast and act on it
		registerReceiver(downloadReceiver, intentFilter);
		receiverRegistered = true;

		boolean preferencesSaved = getPreferences(Context.MODE_PRIVATE).getBoolean("PREFERENCES_SAVED", false);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {


		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			Intent intentPreferences = new Intent(getApplicationContext(),
					SettingsActivity.class);

			// 3. Start the activity and then pass results to onActivityResult
			startActivityForResult(intentPreferences, SETTINGS_INFO);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	private void saveSettings(){


	}

	// 2. Called if the app is forced to close
	@Override
	protected void onStop() {
		if(receiverRegistered){
			unregisterReceiver(downloadReceiver);
			receiverRegistered = false;
		}

		saveSettings();

		super.onStop();
	}

	private void startTableOfContents() {
		getCheckedItems();
		if(itemsAreChecked()){
			startTableOfContentsActivity();
		}else{
			showNothingSelectedAlert();
		}
	}

	private void showNothingSelectedAlert() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
			// set title
			alertDialogBuilder.setTitle("Your Title");
			// set dialog message 
			alertDialogBuilder
				.setMessage("Please select a file")
				.setCancelable(false)
				.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
	}

	private void startTableOfContentsActivity() {
		String[] outputStrArr = new String[selectedFiles.size()];
		 
        for (int i = 0; i < selectedFiles.size(); i++) {
            outputStrArr[i] = selectedFiles.get(i);
			Log.d("Main Screen", "We are getting " + selectedFiles.get(i));
        }
        Intent intent = new Intent(getApplicationContext(),
        		TableOfContents.class);
 
        // Create a bundle object
        Bundle b = new Bundle();
		b.putStringArray("selectedItems", outputStrArr);
 
        // Add the bundle to the intent.
        intent.putExtras(b);
 
        // start the ResultActivity
		startActivityForResult(intent, TABLE_OF_CONTENTS_INFO);
	}

	private void updateWithSettings(){

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){

		super.onActivityResult(requestCode, resultCode, data);

		// 3. Check that the intent with the id SETTINGS_INFO called here
		if(requestCode == SETTINGS_INFO){

			updateWithSettings();

		}
		else if(requestCode == TABLE_OF_CONTENTS_INFO){
			resetSelectedItems();
		}

	}

	private void resetSelectedItems() {
		selectedFiles = new ArrayList<>();
	}

	private boolean itemsAreChecked() {
		return selectedFiles.size() > 0;
	}

	private void getCheckedItems() {
		SparseBooleanArray checked = listView.getCheckedItemPositions();
		Log.d("Main Screen", "There are " + checked.size() + " items...?");
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i))
            	selectedFiles.add(adapter.getItem(position));
        }
 
        
	}



	private void showFiles() {
		final ArrayList<String> list = ExternalSearcher.getListOfFilesInStorageDir();
		// Get a handle to the list view
		if(list.size()>0){
			Log.d(TAG, "We have files in our list it is " + list.get(0));
		}
		listView = (ListView)findViewById(R.id.listView);
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, list);
		listView.setAdapter(adapter);
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			//do your stuff in here! 
			}});
		MyUtilites.setListViewHeightBasedOnChildren(listView);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}

	private void download(String url){

		Log.d("OK", url);

		// Create an intent to run the IntentService in the background
		Intent intent = new Intent(this, FileService.class);

		// Pass the URL that the IntentService will download from
		intent.putExtra("url", url);

		// Start the intent service
		this.startService(intent);

	}

	private final BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

		// Called when the broadcast is received
		@Override
		public void onReceive(Context context, Intent intent) {

			Log.e("FileService", "Service Received");


			showFile();

		}
	};
	private void showFile(){
		StringBuilder sb;
		try {
			// Opens a stream so we can read from our local file
			FileInputStream fis = this.openFileInput("myFile");

			// Gets an input stream for reading data
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

			// Used to read the data in small bytes to minimize system load
			BufferedReader bufferedReader = new BufferedReader(isr);

			// Read the data in bytes until nothing is left to read
			sb = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line).append("\n");
			}

			// Put downloaded text into the EditText
			Log.d("FILE",sb.toString());
			refresh();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void preExecuteDialog(){
		pd = new ProgressDialog(context);
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
	}
	
	public void postExecuteDialog(){
		pd.dismiss();
		refresh();
	}
	
	private void refresh(){
		showFiles();
	}


	
	
	
}


