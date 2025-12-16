package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    Button bt;
    EditText edtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtxt = (EditText) findViewById(R.id.inputRayon);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        bt = (Button)findViewById(R.id.BTN);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String str = edtxt.getText().toString();
                double rayon = Double.parseDouble(str);
                double perimetre = 2 * 3.14 * rayon;
                Toast.makeText(getApplicationContext(), "le Périmètre du Cercle est: " +perimetre, Toast.LENGTH_LONG).show();
            }
        });
    }


}
