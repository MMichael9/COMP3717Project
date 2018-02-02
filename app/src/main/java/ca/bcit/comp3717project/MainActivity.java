package ca.bcit.comp3717project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toMap(final View view) {
        Intent goToMap = new Intent(this, MapActivity.class);
        startActivity(goToMap);
    }

    //added comments
}
