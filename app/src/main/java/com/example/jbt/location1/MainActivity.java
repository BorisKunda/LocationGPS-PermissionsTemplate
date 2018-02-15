package com.example.jbt.location1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //Google stores last know location.
        //check if null before.. it may be null
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            //request permission at runtime
            //1.context
            //2.permissions array
            //3. request code..
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 15);

        } else {
            Location lastKnowLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }


        (findViewById(R.id.gpsBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 15);
                }
                else {
                    //1. LocationManager.GPS_PROVIDER or LocationManager.NETWORK_PROVIDER or LocationManager.PASSIVE_PROVIDER (uses other apps for location)
                    //2. min time in milliseconds
                    //3. min distance in meters
                    //4. some object that implements locationListener (new LocationListener() or this if the Activity implements)

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, MainActivity.this);
                }


            }
        });



    }


    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==15)
        {
            if(grantResults[0] ==PackageManager.PERMISSION_GRANTED )
            {
             //get location
              @SuppressLint("MissingPermission")
              Location loc=   ((LocationManager) getSystemService(LOCATION_SERVICE)).getLastKnownLocation(LocationManager.GPS_PROVIDER);

            }
            else
            {
                Toast.makeText(this, "please allow location...", Toast.LENGTH_SHORT).show();
            }
        }

    }





    @Override
    public void onLocationChanged(Location location) {

        //Will be called every time location gets updated
        Log.d("location","lat: "+location.getLatitude()+" lon:"+location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        //when accuracy changes
    }

    @Override
    public void onProviderEnabled(String provider) {

        //if the user turns on the provider (GPS)
    }

    @Override
    public void onProviderDisabled(String provider) {
      //if the user turns off the provider (GPS)

    }




}
