package ca.bcit.comp3717project;

/**
 * Created by dylan on 3/20/2018.
 */

public class Shopping extends Amenity
{
    @Override
    public int getValue(Preferences pref) {
        return (int) (pref.shopping * 0.15);
    }

    @Override
    public String getCategory() { return "Shopping"; }
}
