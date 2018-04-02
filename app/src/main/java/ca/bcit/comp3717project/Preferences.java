package ca.bcit.comp3717project;

import android.os.Parcelable;

/**
 * Created by dylan on 3/9/2018.
 */

public class Preferences {
    int school,
            shopping,
            culture;
            //parks;

    public Preferences() {
        this.school = 50;
        this.shopping = 50;
        this.culture = 50;
        //this.parks = 50;
    }

    public Preferences(int school, int shopping, int culture/*, int parks*/) {
        this.school = school;
        this.shopping = shopping;
        this.culture = culture;
        //this.parks = parks;
    }

    public String toString() {
        return "Shopping : " + shopping;
    }
}
