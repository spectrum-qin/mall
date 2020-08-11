package com.spectrum.mall.utils.text;

import java.util.UUID;

/**
 * @author oe_qinzuopu
 */
public class UuidUtils {
    public UuidUtils() {
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getSnowId() {
        return SnowflakeKeyWorker.nextId();
    }
}
