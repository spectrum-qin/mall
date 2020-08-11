package com.spectrum.mall.orm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author oe_qinzuopu
 */
@Data
@EqualsAndHashCode
@ToString
public class DataSourceConfig {
    private String dbType;
    private String url;
    private String driverName;
    private String username;
    private String password;

    public DataSourceConfig() {
    }
}
