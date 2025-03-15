package com.example.cumn_rv;

import com.google.gson.annotations.SerializedName;

public class FirestoreCaracteristica {
    @SerializedName("fields")
    private FirestoreFields fields;

    public FirestoreFields getFields() {
        return fields;
    }

    public String getMarca() {
        return fields != null && fields.marca != null ? fields.marca.getStringValue() : "No disponible";
    }

    public String getMaterial() {
        return fields != null && fields.material != null ? fields.material.getStringValue() : "No disponible";
    }

    public static class FirestoreFields {
        @SerializedName("marca")
        public FirestoreStringValue marca;

        @SerializedName("material")
        public FirestoreStringValue material;
    }
}



