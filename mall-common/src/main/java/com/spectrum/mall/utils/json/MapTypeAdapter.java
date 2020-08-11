package com.spectrum.mall.utils.json;

import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author oe_qinzuopu
 */
public class MapTypeAdapter extends TypeAdapter<Object> {
    private static final Logger log = LoggerFactory.getLogger(MapTypeAdapter.class);

    public MapTypeAdapter() {
    }

    public Object read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        switch(token) {
            case BEGIN_ARRAY:
                List<Object> list = new ArrayList();
                in.beginArray();

                while(in.hasNext()) {
                    list.add(this.read(in));
                }

                in.endArray();
                return list;
            case BEGIN_OBJECT:
                Map<String, Object> map = new LinkedTreeMap();
                in.beginObject();

                while(in.hasNext()) {
                    map.put(in.nextName(), this.read(in));
                }

                in.endObject();
                return map;
            case STRING:
                return in.nextString();
            case NUMBER:
                double dbNum = in.nextDouble();
                if (dbNum > 9.223372036854776E18D) {
                    return dbNum;
                } else {
                    long lngNum = (long)dbNum;
                    if (dbNum == (double)lngNum) {
                        return lngNum;
                    }

                    return dbNum;
                }
            case BOOLEAN:
                return in.nextBoolean();
            case NULL:
                in.nextNull();
                return null;
            default:
                log.info("暂不支持类型");
                throw new IllegalStateException();
        }
    }

    public void write(JsonWriter out, Object value) throws IOException {
    }
}
