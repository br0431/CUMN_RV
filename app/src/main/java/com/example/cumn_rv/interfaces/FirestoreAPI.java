package com.example.cumn_rv.interfaces;

import com.example.cumn_rv.FirestoreCaracteristica;
import com.example.cumn_rv.FirestoreResponse;
import com.example.cumn_rv.Pala;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FirestoreAPI {
    @GET("projects/cumnrv/databases/(default)/documents/palas")
    Single<FirestoreResponse<Pala>> obtenerPalas();

    @GET("projects/cumnrv/databases/(default)/documents/palas/{palaId}/caracteristicas/{nombrePala}")
    Single<FirestoreCaracteristica> obtenerCaracteristicas(
            @Path(value = "palaId", encoded = true) String palaId,
            @Path(value = "nombrePala", encoded = true) String nombrePala
    );
}




