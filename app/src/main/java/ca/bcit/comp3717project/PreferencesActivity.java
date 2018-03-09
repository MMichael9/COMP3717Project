package ca.bcit.comp3717project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class PreferencesActivity extends AppCompatActivity {

    private ProgressBar schools, shopping, parks, culture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);

        schools = findViewById(R.id.seekBar);
        shopping = findViewById(R.id.seekBar2);
        culture = findViewById(R.id.seekBar3);
        parks = findViewById(R.id.seekBar4);
    }

    public void savePreferences(final View view) {
        int schoolValue = schools.getProgress();
        int shoppingValue = shopping.getProgress();
        int cultureValue = culture.getProgress();
        int parkValue = parks.getProgress();
        Preferences newPreference = new Preferences(schoolValue, shoppingValue, cultureValue, parkValue);
        finish();
    }
}
