package com.example.mylocationservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtLatitude = (TextView)findViewById(R.id.txtLatitude);
        final TextView txtLongitude = (TextView)findViewById(R.id.txtLongitude);

        final Button btnGetLocation = (Button) findViewById(R.id.btnLocation);

        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
    }
}
