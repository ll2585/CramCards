package com.luke.appaday.cramcards;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class ExternalSearcher {
	private static final String TAG = "FLASHCARDS";
	public static final String FOLDER = "FlashCardCSV";

	public static File getStorageDir() {
		// Get the directory for the user's public pictures directory.
		File file = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOCUMENTS), FOLDER);
		if (!file.mkdirs()) {
			Log.e("FileService", "Directory not created");
		}
		return file;
	}
	public static ArrayList<String> getListOfFilesInFolder(File folder) {
		ArrayList<String> temp = new ArrayList<String>();
		Log.d(TAG, "what is in " + folder.getPath() + "?");
	    if (folder != null) {
	        if (folder.listFiles() != null) {
	        	Log.d(TAG, "stuff!");
	            for (File file : folder.listFiles()) {
	                if (file.isFile()) {
	                    if(file.getName().contains(".csv")){
	                    	temp.add(file.getName());
	                    }
	                }
	                Log.d(TAG, "the file is " + file.getName());
	            }
	        }
	    }
	    return temp;
	}

	public static ArrayList<String> getListOfFilesInStorageDir(){
		return getListOfFilesInFolder(getStorageDir());
	}
}
