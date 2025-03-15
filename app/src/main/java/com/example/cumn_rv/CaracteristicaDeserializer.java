package com.example.cumn_rv;

import android.util.Log;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class CaracteristicaDeserializer implements JsonDeserializer<Caracteristica> {
    @Override
    public Caracteristica deserialize(JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) throws JsonParseException {
        Caracteristica caracteristica = new Caracteristica();
        JsonObject obj = json.getAsJsonObject();

        if (!obj.has("fields")) {
            Log.e("Firestore", "❌ Error: JSON sin 'fields'. Respuesta: " + obj.toString());
            throw new JsonParseException("El JSON no tiene la clave 'fields'");
        }

        JsonObject fields = obj.getAsJsonObject("fields");

        caracteristica.setMarca(getStringValue(fields, "marca"));
        caracteristica.setMaterial(getStringValue(fields, "material"));

        return caracteristica;
    }

    private String getStringValue(JsonObject obj, String key) {
        if (obj.has(key) && obj.getAsJsonObject(key).has("stringValue")) {
            return obj.getAsJsonObject(key).get("stringValue").getAsString();
        }
        return "No disponible";
    }
}

