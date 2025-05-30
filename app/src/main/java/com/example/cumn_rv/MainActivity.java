package com.example.cumn_rv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView contadorClicksText, palasDesbloqueadasText;
    private int contadorClicks;
    private SharedPreferences prefs;
    private BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iconPala = findViewById(R.id.icon_pala);
        ImageView iconMapa = findViewById(R.id.icon_mapa);
        iconPala.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CollectionPalas.class)));
        iconMapa.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapsActivity.class)));

        prefs = getSharedPreferences("PalaPrefs", MODE_PRIVATE);
        contadorClicks = prefs.getInt("clickCount", 0);

        contadorClicksText = findViewById(R.id.contadorClicksText);
        palasDesbloqueadasText = findViewById(R.id.palasDesbloqueadasText);
        Button btnClick = findViewById(R.id.btnClick);

        setSupportActionBar(bottomAppBar);

        actualizarUI();

        btnClick.setOnClickListener(v -> {
            contadorClicks++;
            prefs.edit().putInt("clickCount", contadorClicks).apply();
            actualizarUI();

            desbloquearPalaSiEsNecesario();
        });
    }

    private void actualizarUI() {
        contadorClicksText.setText("Clicks: " + contadorClicks);
        Set<String> palasDesbloqueadas = prefs.getStringSet("collectedPalas", new HashSet<>());
        palasDesbloqueadasText.setText("Palas desbloqueadas: " + palasDesbloqueadas.size());
    }

    @SuppressLint("CheckResult")
    private void desbloquearPalaSiEsNecesario() {
        RetrofitClient.getPublicFirestoreAPI().obtenerPalas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Set<String> palasDesbloqueadas = prefs.getStringSet("collectedPalas", new HashSet<>());
                    Set<String> nuevasPalas = new HashSet<>(palasDesbloqueadas);

                    for (Pala pala : response) {
                        if (pala != null && pala.getNombre() != null) {
                            long clicksNecesarios = pala.getClicksNecesarios() != null ? pala.getClicksNecesarios() : Long.MAX_VALUE;

                            if (contadorClicks >= clicksNecesarios && !nuevasPalas.contains(pala.getNombre())) {
                                nuevasPalas.add(pala.getNombre());
                                Log.d("MainActivity", "✅ Desbloqueada: " + pala.getNombre());

                                mostrarToastDesbloqueo(pala.getNombre());
                            }
                        }
                    }

                    prefs.edit().putStringSet("collectedPalas", nuevasPalas).apply();
                    actualizarUI();
                }, throwable -> Log.e("MainActivity", "❌ Error al obtener palas desde Firestore", throwable));
    }

    private void mostrarToastDesbloqueo(String nombrePala) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom, findViewById(R.id.toast_text));

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText("¡Has desbloqueado la pala: " + nombrePala + "!");

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}



