package com.spectrum.mall.utils.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class NullStringToEmptyAdapterFactory implements TypeAdapterFactory {
    public NullStringToEmptyAdapterFactory() {
    }

    public TypeAdapter create(Gson gson, TypeToken type) {
        Class rawType = type.getRawType();
        return rawType != String.class ? null : new StringNullAdapter();
    }
}