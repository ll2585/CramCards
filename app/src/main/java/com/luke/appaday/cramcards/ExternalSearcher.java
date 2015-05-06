package com.luke.appaday.cramcards;

import android.app.Activity;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class ExternalSearcher {
	private static final String TAG = "FLASHCARDS";

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
}
