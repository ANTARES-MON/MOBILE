package com.example.sqllitef;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqllitef.AppDatabase;
import com.example.sqllitef.R;
import com.example.sqllitef.Student;
import com.example.sqllitef.StudentAdapter;
import com.example.sqllitef.StudentDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editNom, editPrenom, editNiveau;
    private Button btnAjouter;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNom = findViewById(R.id.edit_nom);
        editPrenom = findViewById(R.id.edit_prenom);
        editNiveau = findViewById(R.id.edit_niveau);
        btnAjouter = findViewById(R.id.btnA);
        recyclerView = findViewById(R.id.recyViewS);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getInstance(this);
        studentDao = db.studentDao();

        adapter = new StudentAdapter();
        recyclerView.setAdapter(adapter);

        chargerListe();

        btnAjouter.setOnClickListener(v -> {
            String nom = editNom.getText().toString().trim();
            String prenom = editPrenom.getText().toString().trim();
            String niveau = editNiveau.getText().toString().trim();

            if (nom.isEmpty() || prenom.isEmpty() || niveau.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            Student nouveau = new Student(nom, prenom, niveau);

            new Thread(() -> {
                studentDao.insert(nouveau);
                runOnUiThread(() -> {
                    chargerListe();
                    editNom.setText("");
                    editPrenom.setText("");
                    editNiveau.setText("");
                    Toast.makeText(MainActivity.this, "Étudiant ajouté", Toast.LENGTH_SHORT).show();
                });
            }).start();
        });
    }

    private void chargerListe() {
        new Thread(() -> {
            List<Student> students = studentDao.getAll();
            runOnUiThread(() -> adapter.setStudents(students));
        }).start();
    }
}