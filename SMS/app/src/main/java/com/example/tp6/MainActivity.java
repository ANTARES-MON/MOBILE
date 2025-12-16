package com.example.tp6;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.envoiesms);

        Button btnEnvoie = findViewById(R.id.envoyer);
        final EditText numero = findViewById(R.id.numero);
        final EditText message = findViewById(R.id.message);

        btnEnvoie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = numero.getText().toString();
                String msg = message.getText().toString();

                if (num.length() >= 4 && !msg.isEmpty()) {
                    SmsManager.getDefault().sendTextMessage(num, null, msg, null, null);
                    numero.setText("");
                    message.setText("");
                    Toast.makeText(MainActivity.this, "SMS envoyé avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Veuillez entrer un numéro et un message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}