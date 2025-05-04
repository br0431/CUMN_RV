package com.example.cumn_rv.interfaces;

import com.example.cumn_rv.Caracteristica;
import com.example.cumn_rv.Pala;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FirestoreAPI {

    @GET("api/palas")
    Single<List<Pala>> obtenerPalas();

    @GET("api/palas/{palaId}/caracteristicas/{nombrePala}")
    Single<Caracteristica> obtenerCaracteristicas(
            @Path("palaId") String palaId,
            @Path("nombrePala") String nombrePala
    );
}





