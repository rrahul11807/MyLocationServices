package com.example.mylocationservices;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtLatitude = (TextView)findViewById(R.id.txtLatitude);
        final TextView txtLongitude = (TextView)findViewById(R.id.txtLongitude);

        final Button btnGetLocation = (Button) findViewById(R.id.btnLocation);

        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Task<Location> location = client.getLastLocation();

                    //TODO don't do this!
                    //double lat = location.getResult().getLatitude();
                    location.addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {

                            txtLatitude.setText(Double.toString(task.getResult().getLatitude()));
                            txtLongitude.setText(Double.toString(task.getResult().getLongitude()));
                            System.err.println(task.getResult().getLatitude());
                        }
                    });
                }
                catch(SecurityException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

    }
}
