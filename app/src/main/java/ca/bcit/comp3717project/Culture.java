package ca.bcit.comp3717project;

/**
 * Created by sangwoorai on 2018-03-23.
 */

public class Culture extends Amenity {
    @Override
    public int getValue(Preferences pref) {
        return (int) (pref.culture * 0.05);
    }

    @Override
    public String getCategory() { return "Culture"; }
}
