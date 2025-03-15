package com.example.cumn_rv;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class PalaDeserializer implements JsonDeserializer<Pala> {
    @Override
    public Pala deserialize(JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) throws JsonParseException {
        Pala pala = new Pala();
        JsonObject obj = json.getAsJsonObject();

        if (!obj.has("fields")) {
            throw new JsonParseException("El JSON no tiene la clave 'fields'");
        }

        JsonObject fields = obj.getAsJsonObject("fields");

        pala.setNombre(getStringValue(fields, "nombre"));
        pala.setImagenUrl(getStringValue(fields, "imagenUrl"));
        pala.setDescripcion(getStringValue(fields, "descripcion"));
        pala.setClicksNecesarios(getLongValue(fields, "clicksNecesarios"));

        return pala;
    }

    private String getStringValue(JsonObject obj, String key) {
        if (obj.has(key) && obj.getAsJsonObject(key).has("stringValue")) {
            return obj.getAsJsonObject(key).get("stringValue").getAsString();
        }
        return null;
    }

    private Long getLongValue(JsonObject obj, String key) {
        if (obj.has(key) && obj.getAsJsonObject(key).has("integerValue")) {
            return obj.getAsJsonObject(key).get("integerValue").getAsLong();
        }
        return 0L;
    }
}

