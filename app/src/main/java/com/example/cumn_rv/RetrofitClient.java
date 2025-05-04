package com.example.cumn_rv;

import com.example.cumn_rv.interfaces.FirestoreAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api-pala-cumn.onrender.com/";

    private static Retrofit retrofit;

    public static FirestoreAPI getFirestoreAPI() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().create();

            OkHttpClient client = new OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(FirestoreAPI.class);
    }
}



