package com.example.cumn_rv;

import com.google.gson.annotations.SerializedName;

public class Pala {
    @SerializedName("id")
    private String id;
    @SerializedName("nombre")
    private String nombre;

    @SerializedName("imagenUrl")
    private String imagenUrl;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("clicksNecesarios")
    private Long clicksNecesarios;

    public Pala() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getClicksNecesarios() { return clicksNecesarios; }
    public void setClicksNecesarios(Long clicksNecesarios) { this.clicksNecesarios = clicksNecesarios; }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


