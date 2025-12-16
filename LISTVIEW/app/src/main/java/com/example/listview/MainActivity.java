package com.example.listview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView maListViewPerso;
    private List<HashMap<String, String>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maListViewPerso = findViewById(R.id.listviewperso);

        listItem = new ArrayList<>();
        HashMap<String, String> map;

        map = new HashMap<>();
        map.put("img", String.valueOf(R.drawable.word));
        map.put("titre", "Word");
        map.put("description", "Traitement de texte");
        listItem.add(map);

        map = new HashMap<>();
        map.put("img", String.valueOf(R.drawable.excel));
        map.put("titre", "Excel");
        map.put("description", "Tableur");
        listItem.add(map);

        map = new HashMap<>();
        map.put("img", String.valueOf(R.drawable.powerpoint));
        map.put("titre", "PowerPoint");
        map.put("description", "Logiciel de présentation");
        listItem.add(map);

        map = new HashMap<>();
        map.put("img", String.valueOf(R.drawable.outlook));
        map.put("titre", "Outlook");
        map.put("description", "Client de courrier électronique");
        listItem.add(map);

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listItem,
                R.layout.affichageitem,
                new String[]{"img", "titre", "description"},
                new int[]{R.id.img, R.id.titre, R.id.description}
        );

        maListViewPerso.setAdapter(adapter);
        maListViewPerso.setOnItemClickListener((parent, view, position, id) -> {
            HashMap<String, String> selectedMap = (HashMap<String, String>) parent.getItemAtPosition(position);

            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
            intent.putExtra("titre", selectedMap.get("titre"));
            intent.putExtra("description", selectedMap.get("description"));
            intent.putExtra("img", selectedMap.get("img"));
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Votre réponse");
            adb.setPositiveButton("OK", null);

            switch (resultCode) {
                case 1: adb.setMessage("Vous utilisez Word."); break;
                case 2: adb.setMessage("Vous utilisez Excel."); break;
                case 3: adb.setMessage("Vous utilisez PowerPoint."); break;
                case 4: adb.setMessage("Vous utilisez Outlook."); break;
                case 5: adb.setMessage("Vous n'utilisez pas Word."); break;
                case 6: adb.setMessage("Vous n'utilisez pas Excel."); break;
                case 7: adb.setMessage("Vous n'utilisez pas PowerPoint."); break;
                case 8: adb.setMessage("Vous n'utilisez pas Outlook."); break;
                default: adb.setMessage("Aucune réponse reçue."); break;
            }
            adb.show();
        }
    }
}
