package ca.bcit.comp3717project;

/**
 * Created by sangwoorai on 2018-03-23.
 */

public class School extends Amenity {
    @Override
    public int getValue(Preferences pref) {
        return (int) (pref.school * 0.1);
    }
}