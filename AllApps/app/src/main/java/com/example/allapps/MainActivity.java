package com.example.allapps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] appNames = {"CALCULATOR", "PÉRIMÈTRE D'UN CERCLE", "CONVERTISSEUR", "SMS", "OFFICE APPS", "MENU", "GPS LOCATION", "GOOGLE MAPS", "WIFI", "BLUETOOTH", "Gestion des Étudiants"};
    String[] packageNames = {"com.example.atelier1", "com.example.myapplication", "com.example.tp1_conversion", "com.example.tp6", "com.example.listview", "com.example.api", "com.example.gpslocation", "com.example.mapsapp", "com.example.wifi", "com.example.bluetooth", "com.example.sqllite" };
    int[] icons = {R.drawable.calculator,
            R.drawable.circled,
            R.drawable.conv,
            R.drawable.smss,
            R.drawable.microsoft,
            R.drawable.menu,
            R.drawable.gps,
            R.drawable.mapp,
            R.drawable.wifi,
            R.drawable.blue,
            R.drawable.etu};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.app_item, R.id.appName, appNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                ImageView icon = view.findViewById(R.id.icon);
                TextView text = view.findViewById(R.id.appName);

                icon.setImageResource(icons[position]);
                text.setText(appNames[position]);

                return view;
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String packageName = packageNames[position];

            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);

            if (launchIntent != null) {
                startActivity(launchIntent);
            } else {
                try {
                    Intent explicitIntent = new Intent();

                    if (packageName.equals("com.example.mapsapp")) {
                        explicitIntent.setClassName(packageName, packageName + ".MapsActivity");
                    } else {
                        explicitIntent.setClassName(packageName, packageName + ".MainActivity");
                    }

                    startActivity(explicitIntent);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,
                            "App not found: " + appNames[position] +
                                    "\nInstall the app first",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}