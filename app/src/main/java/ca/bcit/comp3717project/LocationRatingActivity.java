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

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class LocationRatingActivity extends AppCompatActivity {
    private static final String TAG = "LocationRatingActivity";
    private static final double R_EARTH = 6378137; // in meters

    private float MAX_RATING = 5.0f;
    private RatingBar ratingBarView;
    private float rating = 0;

    private List<Amenity> nearbyAmenitiesList = new ArrayList<>();
    private ListView amenitiesListView;
    private ArrayAdapter amenitiesAdapter;
    private Preferences preferences;
    private LatLng mapLatLng;
    private final double RANGE = 1; // in km

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_rating);

        setLatLng();
        setPreferences();
        initListView();
        populateList();
        setRating();
        setStarRating();
    }

    private void setLatLng() {
        double lat = getIntent().getExtras().getDouble("lat");
        double lng = getIntent().getExtras().getDouble("lng");
        mapLatLng = new LatLng(lat, lng);
    }

    private void setPreferences() {
        SharedPreferences mPrefs = getSharedPreferences("nwm.pref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("preferences", "");
        preferences = gson.fromJson(json, Preferences.class);
        if (preferences == null) {
            preferences = new Preferences();
            Log.d(TAG, "onCreate: preferences not found " + preferences.shopping);
        } else {
            Log.d(TAG, "onCreate: preferences found " + preferences.shopping);
        }
    }

    private void initListView() {
        amenitiesListView = findViewById(R.id.list);
        amenitiesAdapter = new AmenitiesAdapter(this, nearbyAmenitiesList);
        amenitiesListView.setAdapter(amenitiesAdapter);
        amenitiesAdapter.notifyDataSetChanged();

        amenitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LocationRatingActivity.this, "Clicked: " + ((Amenity) parent.getItemAtPosition(position)).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
                intent.putExtra(SearchManager.QUERY, "New Westminster " + ((Amenity) parent.getItemAtPosition(position)).getName());
                startActivity(intent);
            }
        });
    }

    private void populateList() {
        nearbyAmenitiesList.clear();
        for (Amenity amenity : MainActivity.AMENITIES) {
            if (isNearby(amenity)) {
                nearbyAmenitiesList.add(amenity);
            }
        }
        amenitiesAdapter.notifyDataSetChanged();
    }

    private void setRating() {
        int value = 0;
        for (Amenity am : nearbyAmenitiesList) {
            value += am.getValue(preferences);
        }
        float valuePercent = Math.min(1.0f, value / 100f);
        Log.d(TAG, "onCreate: value = " + value + ", percent = " + valuePercent );

        rating = MAX_RATING * valuePercent;
        Log.d(TAG, "onCreate: stars " + rating);
    }

    private void setStarRating() {
        ratingBarView = findViewById(R.id.ratingBar);
        ratingBarView.setNumStars((int)MAX_RATING);
        ratingBarView.setRating(rating);
        ratingBarView.setIsIndicator(true);
    }

    private boolean isNearby(Amenity amenity) {
        Log.d(TAG, "isNearby: " + amenity.getName() + " : " + getDistanceTo(amenity));
        return getDistanceTo(amenity) < RANGE;
    }

    private double rad(double x) {
        return x * Math.PI / 180;
    }

    private double getDistanceTo(Amenity amenity) {
        double lon2 = amenity.getLng();
        double lon1 = mapLatLng.longitude;
        double lat2 = amenity.getLat();
        double lat1 = mapLatLng.latitude;

        double dLat = rad(lat2 - lat1);
        double dLong = rad(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(rad(lat1)) * Math.cos(rad(lat2)) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R_EARTH * c;
        //Log.d(TAG, "getDistanceTo: d in km = " + d);
        return d / 1000;
    }

    private class AmenitiesAdapter extends ArrayAdapter<Amenity> {
        public AmenitiesAdapter(Context context, List<Amenity> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Amenity amenity = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter, parent, false);
            }
            // Lookup view for data population
            TextView tvName = convertView.findViewById(R.id.name);
            TextView tvCategory = convertView.findViewById(R.id.category);
            // Populate the data into the template view using the data object
            tvName.setText(amenity.getName());
            tvCategory.setText(amenity.getCategory());
            // Return the completed view to render on screen
            return convertView;
        }
    }

}
