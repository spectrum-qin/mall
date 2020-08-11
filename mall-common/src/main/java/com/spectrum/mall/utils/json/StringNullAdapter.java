package com.spectrum.mall.utils.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author oe_qinzuopu
 */
public class StringNullAdapter extends TypeAdapter<String> {
    public StringNullAdapter() {
    }

    public String read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return "";
        } else {
            return reader.nextString();
        }
    }

    public void write(JsonWriter writer, String value) throws IOException {
        if (value == null) {
            writer.value("");
        } else {
            writer.value(value);
        }
    }
}
