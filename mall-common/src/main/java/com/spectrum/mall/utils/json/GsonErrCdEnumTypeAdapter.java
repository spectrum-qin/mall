package com.spectrum.mall.utils.json;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author oe_qinzuopu
 */
public class GsonErrCdEnumTypeAdapter implements JsonSerializer<GsonErrCdEnum>, JsonDeserializer<GsonErrCdEnum> {
    public GsonErrCdEnumTypeAdapter() {
    }

    public JsonElement serialize(GsonErrCdEnum src, Type typeOfSrc, JsonSerializationContext context) {
        return src != null ? new JsonPrimitive(src.getValue()) : null;
    }

    public GsonErrCdEnum deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}