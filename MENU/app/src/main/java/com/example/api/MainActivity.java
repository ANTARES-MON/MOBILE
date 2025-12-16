package com.example.api;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private NumberPicker np;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        Drawable overflowIcon = ContextCompat.getDrawable(this, android.R.drawable.ic_menu_more);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("MenuPopup");
        }

        setupNumberPicker();

        Button btnPopup = findViewById(R.id.btn_popup);
        btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void setupNumberPicker() {
        tv = findViewById(R.id.tv);
        np = findViewById(R.id.np);

        np.setMinValue(0);
        np.setMaxValue(10);
        np.setWrapSelectorWheel(true);

        Button button = findViewById(R.id.bouton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv.setText("Votre choix " + np.getValue());
            }
        });
    }

    private void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.popup_afficher) {
                    Toast.makeText(MainActivity.this, "Afficher clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.popup_settings) {
                    Toast.makeText(MainActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_aide) {
            Toast.makeText(this, "Aide", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_apropos) {
            Toast.makeText(this, "A Propos", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_parametres) {
            Toast.makeText(this, "Paramètres", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_rafraichir) {
            Toast.makeText(this, "Rafraichir", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_rechercher) {
            Toast.makeText(this, "Rechercher", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_quitter) {
            Toast.makeText(this, "Au revoir!", Toast.LENGTH_LONG).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}