package ca.bcit.comp3717project;

/**
 * Created by dylan on 3/20/2018.
 */

public abstract class Amenity {
    private double lat, lng;
    private String name = "";

    Amenity() {};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Amenity(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public abstract int getValue(Preferences pref);

    public abstract String getCategory();

}
