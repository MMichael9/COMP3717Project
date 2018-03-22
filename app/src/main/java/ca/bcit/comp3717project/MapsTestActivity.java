package ca.bcit.comp3717project;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsTestActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng mapLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_test);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng newWest = new LatLng(49.2057, -122.9110);
        mMap.addMarker(new MarkerOptions().position(newWest).title("Marker"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(newWest));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(49.2057,-122.9110), 13,13,0)));

        mMap.setOnMapClickListener(this);

    }



    public void setMapLatLng(LatLng latlng) {
        this.mapLatLng = latlng;
    }

    public void toPreferences(final View view) {
        Intent toPreferencesIntent = new Intent(this, PreferencesActivity.class);
        startActivity(toPreferencesIntent);
    }

    public void toRating(final View view) {
        Intent toRatingIntent = new Intent(this, LocationRatingActivity.class);
        toRatingIntent.putExtra("lat", mapLatLng.latitude);
        toRatingIntent.putExtra("lng", mapLatLng.longitude);
        startActivity(toRatingIntent);
    }

    @Override
    public void onMapClick(LatLng point) {

        mMap.clear();

        mapLatLng = point;

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(point.latitude, point.longitude)).title("New Marker");

        mMap.addMarker(marker);

        System.out.println(point.latitude+"---"+ point.longitude);

    }
}
