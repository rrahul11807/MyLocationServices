package com.example.mylocationservices;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity
{

    private static final int REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtLatitude = (TextView)findViewById(R.id.txtLatitude);
        final TextView txtLongitude = (TextView)findViewById(R.id.txtLongitude);

        final Button btnGetLocation = (Button) findViewById(R.id.btnLocation);

        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);

        btnGetLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    Task<Location> location = client.getLastLocation();

                    //TODO don't do this!
                    //double lat = location.getResult().getLatitude();
                    location.addOnCompleteListener(new OnCompleteListener<Location>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Location> task)
                        {

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

        LocationRequest req = new LocationRequest();
        req.setInterval(60000); // 1 min seconds

        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION))
            {


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            else
                {
                    //No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION);

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
        }
        else
            {
            // Permission has already been granted

                    client.requestLocationUpdates(req,new LocationCallback()
                    {
                        @Override
                        public void onLocationResult(LocationResult locationResult)
                        {
                            Log.e("location:",locationResult.getLastLocation().toString());

                            Toast.makeText(MainActivity.this, locationResult.getLastLocation().toString(), Toast.LENGTH_LONG).show();
                        }
                    },null);
            }

    }
}
