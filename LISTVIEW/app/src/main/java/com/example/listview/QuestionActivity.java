package com.example.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends Activity {

    private String titre;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichagequestionactivity);

        TextView question = findViewById(R.id.question);
        Button buttonOui = findViewById(R.id.buttonOui);
        Button buttonNon = findViewById(R.id.buttonNon);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            titre = bundle.getString("titre");
            description = bundle.getString("description");
        }

        question.setText("Utilisez-vous " + titre + " comme " + description + " ?");

        View.OnClickListener listener = v -> {
            int result = 0;

            if (v.getId() == R.id.buttonOui) {
                switch (titre) {
                    case "Word": result = 1; break;
                    case "Excel": result = 2; break;
                    case "PowerPoint": result = 3; break;
                    case "Outlook": result = 4; break;
                }
            } else if (v.getId() == R.id.buttonNon) {
                switch (titre) {
                    case "Word": result = 5; break;
                    case "Excel": result = 6; break;
                    case "PowerPoint": result = 7; break;
                    case "Outlook": result = 8; break;
                }
            }

            setResult(result);
            finish();
        };

        buttonOui.setOnClickListener(listener);
        buttonNon.setOnClickListener(listener);
    }
}
