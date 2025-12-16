package com.example.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Set;

public class MainActivity extends Activity {

    private static final int BLUETOOTH_ACTIVATION = 1;
    private static final int BLUETOOTH_DISCOVERABLE = 2;
    private static final int PERMISSION_REQUEST = 3;

    BluetoothAdapter bluetoothAdapter;
    BroadcastReceiver bluetoothReceiver;
    TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Pas de Bluetooth!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        try {
            if (!bluetoothAdapter.isEnabled()) {
                startActivityForResult(
                        new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
                        BLUETOOTH_ACTIVATION
                );
            } else {
                startBluetooth();
            }
        } catch (SecurityException e) {
            Toast.makeText(this, "Bluetooth permission error", Toast.LENGTH_SHORT).show();
        }
    }

    void startBluetooth() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.BLUETOOTH_CONNECT
                        },
                        PERMISSION_REQUEST
                );
                return;
            }
        }

        try {
            startActivityForResult(
                    new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE),
                    BLUETOOTH_DISCOVERABLE
            );
        } catch (SecurityException e) {
            Toast.makeText(this, "Permission error", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
            String s = "Appareils liés:\n";

            for (BluetoothDevice d : devices) {
                s += d.getName() + " - " + d.getAddress() + "\n";
            }

            statusText.setText(s);
        } catch (SecurityException e) {
            statusText.setText("Permission needed for paired devices");
        }

        bluetoothReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                    try {
                        BluetoothDevice device =
                                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        String info = "Trouvé: " + device.getName() + " - " + device.getAddress();
                        Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
                        Log.v("Bluetooth", info);
                    } catch (SecurityException e) {
                        Log.e("Bluetooth", "Permission error");
                    }
                }
            }
        };

        registerReceiver(
                bluetoothReceiver,
                new IntentFilter(BluetoothDevice.ACTION_FOUND)
        );

        try {
            bluetoothAdapter.startDiscovery();
        } catch (SecurityException e) {
            Toast.makeText(this, "Cannot scan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BLUETOOTH_ACTIVATION && resultCode == RESULT_OK) {
            startBluetooth();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startBluetooth();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bluetoothReceiver != null) {
            unregisterReceiver(bluetoothReceiver);
        }
        try {
            if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
        } catch (SecurityException e) {
        }
    }
}