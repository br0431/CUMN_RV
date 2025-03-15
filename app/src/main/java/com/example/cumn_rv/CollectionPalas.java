package com.example.cumn_rv;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectionPalas extends AppCompatActivity {
    private List<Pala> listaPalas;
    private RecyclerView recyclerView;
    private PalaAdapter adapter;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_palas);

        prefs = getSharedPreferences("PalaPrefs", MODE_PRIVATE);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaPalas = new ArrayList<>();
        adapter = new PalaAdapter(this, listaPalas);
        recyclerView.setAdapter(adapter);

        cargarPalasDesbloqueadas();
    }

    @SuppressLint("CheckResult")
    private void cargarPalasDesbloqueadas() {
        Set<String> palasDesbloqueadas = prefs.getStringSet("collectedPalas", new HashSet<>());

        RetrofitClient.getFirestoreAPI().obtenerPalas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    listaPalas.clear();

                    if (response.getDocuments() != null) {
                        for (Pala pala : response.getDocuments()) {
                            if (pala != null && pala.getNombre() != null && palasDesbloqueadas.contains(pala.getNombre())) {
                                listaPalas.add(pala);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                }, throwable -> Log.e("Firestore", "❌ Error al obtener palas desbloqueadas", throwable));
    }
}


