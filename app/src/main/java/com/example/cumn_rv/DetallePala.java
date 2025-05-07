package com.example.cumn_rv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class DetallePala extends AppCompatActivity {
    private TextView nombrePala, descripcionPala, marcaPala, materialPala;
    private ImageView imagenPala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_pala);

        nombrePala = findViewById(R.id.nombrePala);
        descripcionPala = findViewById(R.id.descripcionPala);
        marcaPala = findViewById(R.id.marcaPala);
        materialPala = findViewById(R.id.materialPala);
        imagenPala = findViewById(R.id.imagenPala);

        String palaNombre = getIntent().getStringExtra("PALA_NOMBRE");
        if (palaNombre != null) {
            obtenerDetallesPala(palaNombre);
        } else {
            Log.e("Firestore", "❌ Nombre de Pala no válido.");
        }
    }

    @SuppressLint("CheckResult")
    private void obtenerDetallesPala(String palaNombre) {
        Log.d("Firestore", "🔍 Buscando detalles para la pala: " + palaNombre);

        RetrofitClient.getPrivateFirestoreAPI().obtenerPalas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && !response.isEmpty()) {
                        for (int i = 0; i < response.size(); i++) {
                            Pala pala = response.get(i);
                            if (pala != null && pala.getNombre() != null && pala.getNombre().equals(palaNombre)) {
                                Log.d("Firestore", "✅ Detalles encontrados para la pala: " + palaNombre);
                                nombrePala.setText(pala.getNombre());
                                descripcionPala.setText(pala.getDescripcion());

                                Glide.with(nombrePala.getContext())
                                        .load(pala.getImagenUrl())
                                        .placeholder(R.drawable.placeholder_pala)
                                        .error(R.drawable.error_pala)
                                        .into(imagenPala);

                                String palaId = pala.getId();


                                Log.d("Firestore", "📌 Llamando a obtenerCaracteristicasPala con ID: " + palaId);

                                obtenerCaracteristicasPala(palaId, palaNombre);
                                return;
                            }
                        }
                    }
                    Log.e("Firestore", "⚠️ No se encontraron detalles para la pala con nombre: " + palaNombre);
                }, throwable -> Log.e("Firestore", "❌ Error al obtener detalles de la pala", throwable));
    }

    @SuppressLint("CheckResult")
    private void obtenerCaracteristicasPala(String palaId, String nombrePala) {
        try {

            Log.d("Firestore", "📌 Llamando al endpoint Flask: /api/palas/" + palaId + "/caracteristicas/" + nombrePala);

            RetrofitClient.getPublicFirestoreAPI().obtenerCaracteristicas(palaId, nombrePala)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        Log.d("Firestore", "📌 Respuesta cruda de Flask: " + response.toString());

                        if (response == null) {
                            Log.e("Firestore", "⚠️ La API devolvió una respuesta vacía.");
                            return;
                        }

                        String marca = response.getMarca();
                        String material = response.getMaterial();

                        Log.d("Firestore", "✅ Características encontradas: Marca=" + marca + ", Material=" + material);

                        marcaPala.setText("Marca: " + (marca != null ? marca : "No disponible"));
                        materialPala.setText("Material: " + (material != null ? material : "No disponible"));

                    }, throwable -> {
                        if (throwable instanceof HttpException) {
                            HttpException httpException = (HttpException) throwable;
                            int errorCode = httpException.code();
                            String errorBody = httpException.response().errorBody().string();
                            Log.e("Firestore", "❌ Error HTTP " + errorCode + ": " + errorBody);
                        } else {
                            Log.e("Firestore", "❌ Error al obtener características de la pala", throwable);
                        }
                    });
        } catch (Exception e) {
            Log.e("Firestore", "❌ Error al codificar la URL", e);
        }
    }
}



