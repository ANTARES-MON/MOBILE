package com.example.atelier1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonPlus;
    Button buttonMoins;
    Button buttonDiv;
    Button buttonMul;
    Button buttonC;
    Button buttonEgal;
    Button buttonPoint;
    EditText ecran;

    private double chiffre1;
    private boolean clicOperateur = false;
    private boolean update = false;
    private String operateur = "";

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

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMoins = findViewById(R.id.buttonMoins);
        buttonDiv = findViewById(R.id.buttonDivision);
        buttonMul = findViewById(R.id.buttonMultiplier);
        buttonC = findViewById(R.id.buttonC);
        buttonEgal = findViewById(R.id.buttonEgal);
        buttonPoint = findViewById(R.id.buttonPoint);
        ecran = findViewById(R.id.EditText01);

        button0.setOnClickListener(v -> chiffreClick("0"));
        button1.setOnClickListener(v -> chiffreClick("1"));
        button2.setOnClickListener(v -> chiffreClick("2"));
        button3.setOnClickListener(v -> chiffreClick("3"));
        button4.setOnClickListener(v -> chiffreClick("4"));
        button5.setOnClickListener(v -> chiffreClick("5"));
        button6.setOnClickListener(v -> chiffreClick("6"));
        button7.setOnClickListener(v -> chiffreClick("7"));
        button8.setOnClickListener(v -> chiffreClick("8"));
        button9.setOnClickListener(v -> chiffreClick("9"));
        buttonPoint.setOnClickListener(v -> chiffreClick("."));

        buttonC.setOnClickListener(v -> clear());
        buttonEgal.setOnClickListener(v -> calcul());

        buttonPlus.setOnClickListener(v -> operatorClick("+"));
        buttonMoins.setOnClickListener(v -> operatorClick("-"));
        buttonMul.setOnClickListener(v -> operatorClick("*"));
        buttonDiv.setOnClickListener(v -> operatorClick("/"));
    }

    private void chiffreClick(String number) {
        String currentText = ecran.getText().toString();
        if (currentText.equals("0") || update) {
            if (number.equals(".") && currentText.contains(".")) {
                return;
            }
            ecran.setText(number);
            update = false;
        } else {
            ecran.setText(ecran.getText() + number);
        }
    }

    private void clear() {
        ecran.setText("0");
        clicOperateur = false;
        update = false;
        chiffre1 = 0;
        operateur = "";
    }

    private void operatorClick(String op) {
        if (clicOperateur) {
            calcul();
            ecran.setText(String.valueOf(chiffre1));
        } else {
            chiffre1 = Double.valueOf(ecran.getText().toString()).doubleValue();
            clicOperateur = true;
        }
        operateur = op;
        update = true;
    }

    private void calcul() {
        if (operateur.equals("+")) {
            chiffre1 = chiffre1 + Double.valueOf(ecran.getText().toString()).doubleValue();
            ecran.setText(String.valueOf(chiffre1));
        }
        if (operateur.equals("-")) {
            chiffre1 = chiffre1 - Double.valueOf(ecran.getText().toString()).doubleValue();
            ecran.setText(String.valueOf(chiffre1));
        }
        if (operateur.equals("*")) {
            chiffre1 = chiffre1 * Double.valueOf(ecran.getText().toString()).doubleValue();
            ecran.setText(String.valueOf(chiffre1));
        }
        if (operateur.equals("/")) {
            try {
                chiffre1 = chiffre1 / Double.valueOf(ecran.getText().toString()).doubleValue();
                ecran.setText(String.valueOf(chiffre1));
            } catch (ArithmeticException e) {
                ecran.setText("0");
            }
        }
        clicOperateur = false;
        update = true;
    }
}