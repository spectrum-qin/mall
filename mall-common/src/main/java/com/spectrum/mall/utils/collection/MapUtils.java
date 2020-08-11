package com.spectrum.mall.utils.collection;

import com.spectrum.mall.utils.bean.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author oe_qinzuopu
 */
public class MapUtils {
    private static final Logger log = LoggerFactory.getLogger(MapUtils.class);

    public MapUtils() {
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap();
        List<Field> fields = BeanUtils.getAllFields(obj.getClass());
        Iterator var3 = fields.iterator();

        while(var3.hasNext()) {
            Field field = (Field)var3.next();
            field.setAccessible(true);

            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException | IllegalArgumentException var6) {
                log.error("转换异常", var6);
            }
        }

        return map;
    }
}

