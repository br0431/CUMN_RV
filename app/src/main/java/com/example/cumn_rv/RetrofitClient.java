package com.example.cumn_rv;

import android.util.Log;

import com.example.cumn_rv.interfaces.FirestoreAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api-pala-cumn.onrender.com";

    private static Retrofit retrofit;

    public static FirestoreAPI getFirestoreAPI() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Pala.class, new PalaDeserializer())
                    .registerTypeAdapter(Caracteristica.class, new CaracteristicaDeserializer())
                    .registerTypeAdapter(FirestoreCaracteristica.class, new JsonDeserializer<FirestoreCaracteristica>() {
                        @Override
                        public FirestoreCaracteristica deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            JsonObject jsonObject = json.getAsJsonObject();
                            FirestoreCaracteristica result = new FirestoreCaracteristica();

                            if (jsonObject.has("fields")) {
                                result = new Gson().fromJson(jsonObject, FirestoreCaracteristica.class);
                            } else {
                                Log.e("Firestore", "❌ Error: El JSON no contiene 'fields'");
                            }
                            return result;
                        }
                    })
                    .create();


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


