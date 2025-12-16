package com.example.wifi;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.List;

public class MainActivity extends Activity {
    TextView resultText;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiScanReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.resultText);
        resultText.setMovementMethod(new ScrollingMovementMethod());

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (checkPermissions()) {
            startWifiScan();
        }
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, PERMISSION_REQUEST_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startWifiScan();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startWifiScan() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            Toast.makeText(this, "WiFi turned ON", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> {
                scanNow();
            }, 2000);
        } else {
            scanNow();
        }
    }

    private void scanNow() {
        wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    List<ScanResult> results = wifiManager.getScanResults();
                    showResults(results);
                } else {
                    List<ScanResult> results = wifiManager.getScanResults();
                    showResults(results);
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiScanReceiver, intentFilter);

        boolean success = wifiManager.startScan();
        if (success) {
            Toast.makeText(this, "Scan started...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Scan failed", Toast.LENGTH_SHORT).show();
            resultText.setText("Scan failed. Make sure:\n1. WiFi is ON\n2. Location is ON\n3. Permissions granted");
        }
    }

    private void showResults(List<ScanResult> results) {
        if (results == null || results.isEmpty()) {
            resultText.setText("No WiFi networks found");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Found ").append(results.size()).append(" networks:\n\n");

        for (int i = 0; i < results.size(); i++) {
            ScanResult result = results.get(i);
            sb.append(i+1).append(". ").append(result.SSID).append("\n");
            sb.append("   Signal: ").append(result.level).append(" dBm\n");
            sb.append("   BSSID: ").append(result.BSSID).append("\n");
            sb.append("   Frequency: ").append(result.frequency).append(" MHz\n");
            sb.append("   Security: ").append(result.capabilities).append("\n");
            sb.append("-----------------\n");
        }

        resultText.setText(sb.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wifiScanReceiver != null) {
            unregisterReceiver(wifiScanReceiver);
        }
    }
}