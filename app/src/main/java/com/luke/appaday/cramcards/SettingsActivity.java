package com.luke.appaday.cramcards;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;

public class SettingsActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define the xml file used for preferences
        addPreferencesFromResource(R.xml.preferences);
        setContentView(R.layout.preferences);
    }

    public void finishActivity(View view) {
        Log.d("SETTINGS", "FINISHED??");
        finish();
    }
}