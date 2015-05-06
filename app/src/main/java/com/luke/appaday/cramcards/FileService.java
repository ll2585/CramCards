package com.luke.appaday.cramcards;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.FilenameUtils;

// We use an IntentService to handle long running processes off the UI thread
// Any new requests will wait for the 1st to end and it can't be interrupted

public class FileService extends IntentService {

    // Used to identify when the IntentService finishes
    public static final String TRANSACTION_DONE = "com.luke.appaday.cramcards.TRANSACTION_DONE";

    // Validates resource references inside Android XML files
    public FileService() {
        super(FileService.class.getName());
    }


    public FileService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e("FileService", "Service Started");

        // Get the URL for the file to download
        String passedURL = intent.getStringExtra("url");

        Log.d("FileService", "Getting " + FilenameUtils.getName(passedURL));

        downloadFileExternal(passedURL);

        Log.e("FileService", "Service Stopped");

        // Broadcast an intent back to MainActivity when file is downloaded
        Intent i = new Intent(TRANSACTION_DONE);
        FileService.this.sendBroadcast(i);

    }

    protected void downloadFile(String theURL) {

        // The name for the file we will save data to
        String fileName = FilenameUtils.getName(theURL);

        try{

            // Create an output stream to write data to a file (private to everyone except our app)
            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

            // Get File
            URL fileURL = new URL(theURL);

            // Create a connection we can use to read data from a url
            HttpURLConnection urlConnection = (HttpURLConnection) fileURL.openConnection();

            // We are using the GET method
            urlConnection.setRequestMethod("GET");

            // Set that we want to allow output for this connection
            urlConnection.setDoOutput(true);

            // Connect to the url
            urlConnection.connect();

            // Gets an input stream for reading data
            InputStream inputStream = urlConnection.getInputStream();

            // Define the size of the buffer
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            // read reads a byte of data from the stream until there is nothing more
            while ( (bufferLength = inputStream.read(buffer)) > 0 ){

                // Write the data received to our file
                outputStream.write(buffer, 0, bufferLength);

            }

            // Close our connection to our file
            outputStream.close();

            // Get File Done

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getStorageDir() {
        String folder = "FlashCardCSV";
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), folder);
        if (!file.mkdirs()) {
            Log.e("FileService", "Directory not created");
        }
        return file;
    }

    protected void downloadFileExternal(String theURL) {

        // The name for the file we will save data to
        if(isExternalStorageWritable()) {

            String fileName = FilenameUtils.getName(theURL);

            try {

                // Create an output stream to write data to a file (private to everyone except our app)
                FileOutputStream outputStream = new FileOutputStream(new File(getStorageDir(), fileName));

                // Get File
                URL fileURL = new URL(theURL);

                // Create a connection we can use to read data from a url
                HttpURLConnection urlConnection = (HttpURLConnection) fileURL.openConnection();

                // We are using the GET method
                urlConnection.setRequestMethod("GET");

                // Set that we want to allow output for this connection
                urlConnection.setDoOutput(true);

                // Connect to the url
                urlConnection.connect();

                // Gets an input stream for reading data
                InputStream inputStream = urlConnection.getInputStream();

                // Define the size of the buffer
                byte[] buffer = new byte[1024];
                int bufferLength = 0;

                // read reads a byte of data from the stream until there is nothing more
                while ((bufferLength = inputStream.read(buffer)) > 0) {

                    // Write the data received to our file
                    outputStream.write(buffer, 0, bufferLength);

                }

                // Close our connection to our file
                outputStream.close();

                // Get File Done

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}