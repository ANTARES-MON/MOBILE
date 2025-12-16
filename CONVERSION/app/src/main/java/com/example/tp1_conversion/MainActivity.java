package com.example.tp1_conversion;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText inputvaleur;
    private TextView usdOutput, euroOutput, madOutput;
    private Button BTN1, BTN2;

    private final double EUR_TO_MAD = 10.69;
    private final double USD_TO_MAD = 9.85;
    private final double USD_TO_EUR = 0.92;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inputvaleur = findViewById(R.id.inputvaleur);
        usdOutput = findViewById(R.id.usdOutput);
        euroOutput = findViewById(R.id.euroOutput);
        madOutput = findViewById(R.id.madOutput);
        BTN1 = findViewById(R.id.BTN1);
        BTN2 = findViewById(R.id.BTN2);

        BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert();
            }
        });
        BTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }
    private void convert() {
        String inputValeurStr = inputvaleur.getText().toString();

            double inputvaleur = Double.parseDouble(inputValeurStr);
            double conUSD = inputvaleur / USD_TO_MAD;
            double conEUR = inputvaleur / EUR_TO_MAD;

            usdOutput.setText(String.format("USD: %.2f", conUSD));
            euroOutput.setText(String.format("EUR: %.2f", conEUR));
            madOutput.setText(String.format("MAD: %.2f", inputvaleur));

    }
    private void clearFields() {
        inputvaleur.setText("");
        usdOutput.setText("USD:");
        euroOutput.setText("EUR:");
        madOutput.setText("MAD:");
        Toast.makeText(this, "Fields cleared", Toast.LENGTH_SHORT).show();
    }
}
