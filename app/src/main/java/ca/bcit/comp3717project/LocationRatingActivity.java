package ca.bcit.comp3717project;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LocationRatingActivity extends AppCompatActivity {
    private static final String TAG = "LocationRatingActivity";

    private float MAX_RATING = 5.0f;
    private RatingBar ratingBarView;

    private List<Amenity> nearbyAmenitiesList = new ArrayList<>();
    private ListView amenitiesListView;
    private ArrayAdapter amenitiesAdapter;
    private Preferences preferences;
    private LatLng mapLatLng;
    private final double RANGE = 0.007;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_rating);

        // get map latlng
        double lat = getIntent().getExtras().getDouble("lat");
        double lng = getIntent().getExtras().getDouble("lng");
        mapLatLng = new LatLng(lat, lng);

        // get preferences
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("preferences", "");
        preferences = gson.fromJson(json, Preferences.class);
        if (preferences == null) {
            preferences = new Preferences();
            Log.d(TAG, "onCreate: " + preferences.school);
        }

        // populate nearby amenities list
        populateList();
        Log.d(TAG, "onCreate: " + nearbyAmenitiesList.size());

        // update listview
        amenitiesListView = findViewById(R.id.list);
        amenitiesAdapter = new AmenitiesAdapter(this, nearbyAmenitiesList);
        amenitiesListView.setAdapter(amenitiesAdapter);
        amenitiesAdapter.notifyDataSetChanged();

        amenitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LocationRatingActivity.this, "Clicked: " + ((Amenity) parent.getItemAtPosition(position)).name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
                intent.putExtra(SearchManager.QUERY, "New West Minster");
                startActivity(intent);
            }
        });

        // get rating
        int value = 0;
        for (Amenity am : nearbyAmenitiesList) {
            value += am.getValue(preferences);
        }
        float valuePercent = Math.min(1.0f, value / 100f);
        Log.d(TAG, "onCreate: value = " + value + ", percent = " + valuePercent );

        float valueStars = MAX_RATING * valuePercent;
        Log.d(TAG, "onCreate: stars " + valueStars);

        // rating bar
        ratingBarView = findViewById(R.id.ratingBar);
        ratingBarView.setNumStars((int)MAX_RATING);
        ratingBarView.setRating(valueStars);
        ratingBarView.setIsIndicator(true);
    }

    private void populateList() {
        for (Amenity amenity : MainActivity.AMENITIES) {
            if (isNearby(amenity)) {
                nearbyAmenitiesList.add(amenity);
            }
        }
    }

    private boolean isNearby(Amenity amenity) {
        Log.d(TAG, "isNearby: " + amenity.name + " : " + (amenity.lat - mapLatLng.latitude));
        return Math.abs(amenity.lat - mapLatLng.latitude) < RANGE &&
                Math.abs(amenity.lng - mapLatLng.longitude) < RANGE;
    }

    private class AmenitiesAdapter extends ArrayAdapter<Amenity> {
        public AmenitiesAdapter(Context context, List<Amenity> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Amenity user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter, parent, false);
            }
            // Lookup view for data population
            TextView tvName = convertView.findViewById(R.id.name);
            TextView tvDesc = convertView.findViewById(R.id.desc);
            // Populate the data into the template view using the data object
            tvName.setText(user.name);
            // Return the completed view to render on screen
            return convertView;
        }
    }

}
