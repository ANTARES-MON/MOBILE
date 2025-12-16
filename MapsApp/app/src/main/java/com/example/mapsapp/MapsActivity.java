package com.example.mapsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            try {
                mMap.setMyLocationEnabled(true);
                getCurrentLocation();
            } catch (SecurityException e) {
                Toast.makeText(this, "Location permission error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getCurrentLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {

                                LatLng currentLatLng = new LatLng(
                                        location.getLatitude(),
                                        location.getLongitude());


                                mMap.addMarker(new MarkerOptions()
                                        .position(currentLatLng)
                                        .title("You are here")
                                        .snippet("Lat: " + location.getLatitude() +
                                                ", Lng: " + location.getLongitude()));


                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));

                                Toast.makeText(MapsActivity.this,
                                        "Location found: " + location.getLatitude() + ", " + location.getLongitude(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MapsActivity.this,
                                        "Location not available. Enable GPS or set location in emulator.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } catch (SecurityException e) {
            Toast.makeText(this, "SecurityException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (mMap != null) {
                    try {
                        mMap.setMyLocationEnabled(true);
                        getCurrentLocation();
                    } catch (SecurityException e) {
                        Toast.makeText(this, "Error enabling location: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Location permission denied. Map will work without location features.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}