package ca.bcit.comp3717project;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class LocationRatingActivity extends AppCompatActivity {

    private List<Ammenities> ammenitiesList = new ArrayList<Ammenities>();
    private ListView ammenitiesListView;
    private ArrayAdapter ammenitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_rating);

        ammenitiesListView = findViewById(R.id.list);
        ammenitiesAdapter = new UsersAdapter(this, ammenitiesList);
        ammenitiesListView.setAdapter(ammenitiesAdapter);

        ammenitiesList.add(new Ammenities("Shopping Center", "A shopping center in New West Minster"));
        ammenitiesList.add(new Ammenities("New West Minster Secondary", "Secondary School in New West Minster"));
        ammenitiesList.add(new Ammenities("Queens Park", "A park in New West Minster"));
        ammenitiesAdapter.notifyDataSetChanged();

        ammenitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LocationRatingActivity.this, "Clicked: " + ((Ammenities) parent.getItemAtPosition(position)).name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
                intent.putExtra(SearchManager.QUERY, "New West Minster");
                startActivity(intent);
            }
        });
    }

    public class UsersAdapter extends ArrayAdapter<Ammenities> {
        public UsersAdapter(Context context, List<Ammenities> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Ammenities user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.name);
            TextView tvDesc = (TextView) convertView.findViewById(R.id.desc);
            // Populate the data into the template view using the data object
            tvName.setText(user.name);
            tvDesc.setText(user.description);
            // Return the completed view to render on screen
            return convertView;
        }
    }



    private class Ammenities {
        int xCoord, yCoord;
        String description = "";
        String name = "";

        Ammenities(String name, String description) {
            this.description = description;
            this.name = name;
        }

        public int getxCoord() {
            return xCoord;
        }

        public void setxCoord(int xCoord) {
            this.xCoord = xCoord;
        }

        public int getyCoord() {
            return yCoord;
        }

        public void setyCoord(int yCoord) {
            this.yCoord = yCoord;
        }
    }
}
