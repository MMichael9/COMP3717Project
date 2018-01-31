package ca.bcit.comp3717project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
    }

    public void toPreferences(final View view) {
        Intent toPreferencesIntent = new Intent(this, PreferencesActivity.class);
        startActivity(toPreferencesIntent);
    }

    public void toRating(final View view) {
        Intent toRatingIntent = new Intent(this, LocationRatingActivity.class);
        startActivity(toRatingIntent);
    }
}
