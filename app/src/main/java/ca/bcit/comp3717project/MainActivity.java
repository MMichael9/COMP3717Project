package ca.bcit.comp3717project;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static List<Amenity> AMENITIES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadShoppingData();
        downloadSchoolData();
    }

    public void toMap(final View view) {
        Intent goToMap = new Intent(this, MapsTestActivity.class);
        startActivity(goToMap);
    }

    public void downloadShoppingData() {
        Log.d(TAG, "downloadShoppingData: DOWNLOADING");
        Ion.with(this).
                load("http://opendata.newwestcity.ca/downloads/major-shopping/MAJOR_SHOPPING.json").
                asJsonArray().
                setCallback(
                        new FutureCallback<JsonArray>() {
                            @Override
                            public void onCompleted(final Exception ex,
                                                    final JsonArray result) {
                                if (ex == null) {
                                    downloadShoppingSuccess(result);
                                } else {
                                    downloadError(ex);
                                }
                            }
                        }
                );
    }

    private void downloadShoppingSuccess(final JsonArray jsonArray) {
        for (final JsonElement element : jsonArray) {
            final JsonObject feature = element.getAsJsonObject();
            final String name = feature.get("BLDGNAM").getAsString();
            final double lat = feature.get("Y").getAsDouble();
            final double lng = feature.get("X").getAsDouble();
            final Amenity newAmenity = new Shopping();

            newAmenity.setName(name);
            newAmenity.setLat(lat);
            newAmenity.setLng(lng);

            AMENITIES.add(newAmenity);
            Log.d(TAG, "downloadSuccess: " + name);
        }

    }

    public void downloadSchoolData() {
        Log.d(TAG, "downloadSchoolData: DOWNLOADING");
        Ion.with(this).
                load("http://opendata.newwestcity.ca/downloads/significant-buildings-schools/SIGNIFICANT_BLDG_SCHOOLS.json").
                asJsonObject().
                setCallback(
                        new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(final Exception ex,
                                                    final JsonObject result) {
                                if (ex == null) {
                                    downloadSchoolSuccess(result);
                                } else {
                                    downloadError(ex);
                                }
                            }
                        }
                );
    }

    private void downloadSchoolSuccess(final JsonObject jsonObject) {
        JsonArray schoolsArray = jsonObject.getAsJsonArray("features");

        for (final JsonElement element : schoolsArray) {
            final JsonObject properties = element.getAsJsonObject().getAsJsonObject("properties");
            if (!properties.get("BLDGNAM").isJsonNull()) {
                final String name = properties.get("BLDGNAM").getAsString();
                final double lat = properties.get("Y").getAsDouble();
                final double lng = properties.get("X").getAsDouble();
                final Amenity newAmenity = new School();

                newAmenity.setName(name);
                newAmenity.setLat(lat);
                newAmenity.setLng(lng);

                AMENITIES.add(newAmenity);
                Log.d(TAG, "downloadSuccess: " + name);
            }
        }
    }

    /*public void downloadParkData() {
        Log.d(TAG, "downloadSchoolData: DOWNLOADING");
        Ion.with(this).
                load("http://opendata.newwestcity.ca/downloads/parks/PARKS.json").
                asJsonArray().
                setCallback(
                        new FutureCallback<JsonArray>() {
                            @Override
                            public void onCompleted(final Exception ex,
                                                    final JsonArray result) {
                                if (ex == null) {
                                    downloadParkSuccess(result);
                                } else {
                                    downloadError(ex);
                                }
                            }
                        }
                );
    }

    private void downloadParkSuccess(final JsonArray jsonArray) {
        for (final JsonElement element : jsonArray) {
            final JsonObject feature = element.getAsJsonObject();
            final String name = feature.get("Name").getAsString();
            final JsonArray polygonArray = feature.get("json_geometry").getAsJsonObject().get("coordinates").getAsJsonArray();
            final ArrayList<LatLng> parkCoords = new ArrayList<>();
            for (final JsonElement coords : polygonArray) {
                JsonArray coord = coords.getAsJsonArray();
            }
            final double lat = feature.get("Y").getAsDouble();
            final double lng = feature.get("X").getAsDouble();
            final Amenity newAmenity = new Shopping();

            newAmenity.setName(name);
            newAmenity.setLat(lat);
            newAmenity.setLng(lng);

            AMENITIES.add(newAmenity);
            Log.d(TAG, "downloadSuccess: " + name);
        }
    }*/

    private static void downloadError(final Exception ex) {
        ex.printStackTrace();
    }

}
