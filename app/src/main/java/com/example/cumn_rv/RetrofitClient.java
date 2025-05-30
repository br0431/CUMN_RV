package com.example.cumn_rv;

import com.example.cumn_rv.interfaces.FirestoreAPI;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL_PUBLICA = "https://cumn-api.onrender.com/";
    //private static final String BASE_URL_PRIVADA = "http://192.168.1.110:5000/";

    private static Retrofit retrofitPublica;
    private static Retrofit retrofitPrivada;

    public static FirestoreAPI getPublicFirestoreAPI() {
        if (retrofitPublica == null) {
            retrofitPublica = new Retrofit.Builder()
                    .baseUrl(BASE_URL_PUBLICA)
                    .client(new OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitPublica.create(FirestoreAPI.class);
    }

    /* public static FirestoreAPI getPrivateFirestoreAPI() {
        if (retrofitPrivada == null) {
            retrofitPrivada = new Retrofit.Builder()
                    .baseUrl(BASE_URL_PRIVADA)
                    .client(new OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitPrivada.create(FirestoreAPI.class);
    } */
}



