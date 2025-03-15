package com.example.cumn_rv;

import com.google.gson.annotations.SerializedName;

public class Caracteristica {
    @SerializedName("marca")
    private String marca;

    @SerializedName("material")
    private String material;

    public Caracteristica() {}

    public String getMarca() { return marca; }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMaterial() { return material; }

    public void setMaterial(String material) {
        this.material = material;
    }
}

