package ca.bcit.comp3717project;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

public class PreferencesActivity extends AppCompatActivity {
    private static final String TAG = "PreferencesActivity";
    private SharedPreferences mPrefs;
    private ProgressBar schools,
            shopping,
            //parks,
            culture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);

        schools = findViewById(R.id.seekBar);
        shopping = findViewById(R.id.seekBar2);
        culture = findViewById(R.id.seekBar3);
        //parks = findViewById(R.id.seekBar4);

        mPrefs = getSharedPreferences("nwm.pref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("preferences", "");
        Preferences preferences = gson.fromJson(json, Preferences.class);
        Log.d(TAG, "onCreate: preferences found: " + preferences);
        if (preferences != null) {
            schools.setProgress(preferences.school);
            shopping.setProgress(preferences.shopping);
            culture.setProgress(preferences.culture);
            //parks.setProgress(preferences.parks);
        } else {
            schools.setProgress(50);
            shopping.setProgress(50);
            culture.setProgress(50);
            //parks.setProgress(50);
        }
    }

    public void savePreferences(final View view) {
        int schoolValue = schools.getProgress();
        int shoppingValue = shopping.getProgress();
        int cultureValue = culture.getProgress();
        //int parkValue = parks.getProgress();
        Preferences newPreference = new Preferences(schoolValue, shoppingValue, cultureValue/*, parkValue*/);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(newPreference);
        prefsEditor.putString("preferences", json);
        prefsEditor.commit();

        finish();
    }
}
