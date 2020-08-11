//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spectrum.mall.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.spectrum.mall.utils.text.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author oe_qinzuopu
 */
public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static Gson gson = null;
    private static Gson gsonTmp = null;
    private static final ObjectMapper mapper;

    private JsonUtils() {
    }

    public static JavaType getJavaType(Type type) {
        return mapper.getTypeFactory().constructType(type);
    }

    public static <T> T readValue(String json, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return mapper.readValue(json, valueType);
    }

    public static String jacksonObjectToJson(Object o) {
        String labelString = null;

        try {
            labelString = mapper.writeValueAsString(o);
        } catch (JsonProcessingException var3) {
            log.error("转换异常", var3);
        }

        return labelString;
    }

    public static <T> T jacksonJsonToObject(String json, Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (IOException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> jacksonJsonToMap(String json) throws IOException, JsonParseException, JsonMappingException {
        return (Map)mapper.readValue(json, Map.class);
    }

    public static String objectToJson(Object obj) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(obj);
        }

        return gsonString;
    }

    public static <T> T jsonToObject(String json, Class<T> c) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, c);
        }

        return t;
    }

    public static <T> T jsonToObject(String json, Type type) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, type);
        }

        return t;
    }

    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = null;
        if (gson != null) {
            map = (Map)gson.fromJson(json, (new TypeToken<TreeMap<String, Object>>() {
            }).getType());
        }

        return map;
    }

    public static Map<String, Object> jsonToMap(String json, boolean numberFlag) {
        Map<String, Object> map = null;
        if (gsonTmp != null) {
            map = (Map)gsonTmp.fromJson(json, (new TypeToken<TreeMap<String, Object>>() {
            }).getType());
        }

        return map;
    }

    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList();
        JsonArray array = (new JsonParser()).parse(json).getAsJsonArray();
        Iterator var5 = array.iterator();

        while(var5.hasNext()) {
            JsonElement elem = (JsonElement)var5.next();
            list.add(gson.fromJson(elem, cls));
        }

        return list;
    }

    public static <T> Set<T> jsonToSet(String json, Class<T> cls) {
        Gson gson = new Gson();
        Set<T> set = new HashSet();
        JsonArray array = (new JsonParser()).parse(json).getAsJsonArray();
        Iterator var5 = array.iterator();

        while(var5.hasNext()) {
            JsonElement elem = (JsonElement)var5.next();
            set.add(gson.fromJson(elem, cls));
        }

        return set;
    }

    public static <T> List<Map<String, T>> jsonToListMap(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = (List)gson.fromJson(gsonString, (new TypeToken<List<Map<String, T>>>() {
            }).getType());
        }

        return list;
    }

    public static boolean validate(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = (new JsonParser()).parse(jsonStr);
        } catch (Exception var3) {
            return false;
        }

        if (jsonElement == null) {
            return false;
        } else {
            return jsonElement.isJsonObject();
        }
    }

    public static <T> T jsonToObjectCaseUnderLineToHump(String json, Class<T> c) {
        Map<String, Object> jsonMap = jsonToMap(json);
        Map<String, Object> tempMap = new HashMap();
        Set<String> keys = jsonMap.keySet();
        String[] array = (String[])keys.toArray(new String[keys.size()]);
        String[] var6 = array;
        int var7 = array.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String key = var6[var8];
            Object value = jsonMap.get(key);
            String humpKey = StringUtils.underlineToCamel(key);
            tempMap.put(humpKey, value);
        }

        json = gson.toJson(tempMap);
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, c);
        }

        return t;
    }

    static {
        if (gson == null) {
            gson = (new GsonBuilder()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).registerTypeAdapter(GsonErrCdEnum.class, new GsonErrCdEnumTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }

        if (gsonTmp == null) {
            gsonTmp = (new GsonBuilder()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).registerTypeAdapter((new TypeToken<TreeMap<String, Object>>() {
            }).getType(), new MapTypeAdapter()).registerTypeAdapter(GsonErrCdEnum.class, new GsonErrCdEnumTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }

        mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
