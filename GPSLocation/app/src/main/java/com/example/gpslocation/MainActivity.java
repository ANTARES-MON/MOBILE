package com.example.gpslocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private TextView myLocationText;
    private String provider;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocationText = findViewById(R.id.txtLocation);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(getApplicationContext(),
                        "GPS permission needed for location",
                        Toast.LENGTH_LONG).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        } else {
            startGeocodage();
        }
    }

    private void startGeocodage() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                updateWithNewLocation(location);
            }
            public void onProviderDisabled(String provider) {
                updateWithNewLocation(null);
            }
            public void onProviderEnabled(String provider) { }
            public void onStatusChanged(String provider, int status, Bundle extras) { }
        };

        try {
            Location location = locationManager.getLastKnownLocation(provider);
            updateWithNewLocation(location);
            locationManager.requestLocationUpdates(provider, 2000, 5, locationListener);
        } catch (SecurityException e) {
        }
    }

    private void updateWithNewLocation(Location location) {
        String latLongString = "\nAucune position trouvée";
        String addressString = "Aucune adresse trouvée";

        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            double alt = location.getAltitude();
            double spd = location.getSpeed();

            latLongString = "\n Latitude: " + lat +
                    "\n Longitude: " + lng +
                    "\n Altitude: " + alt + "m" +
                    "\n Vitesse: " + spd + "km/h" +
                    "\n Origine: " + provider;

            Log.i("coordonnees: ", lat + " " + lng + " " + alt + " " + provider);

            Geocoder gc = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = gc.getFromLocation(lat, lng, 1);
                StringBuilder sb = new StringBuilder();

                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i)).append("\n");
                    }
                    sb.append(address.getLocality()).append("\n");
                    sb.append(address.getPostalCode()).append("\n");
                    sb.append(address.getCountryName());
                }
                addressString = sb.toString();
                Log.i("Adresse: ", addressString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        myLocationText.setText("Votre position:\n" + latLongString + "\nAdresse:\n" + addressString);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(),
                            "Permission granted", Toast.LENGTH_LONG).show();
                    startGeocodage();
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}