package com.spectrum.mall.orm.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author oe_qinzuopu
 */
public interface MapCmd<T> {
    T getObjecFromRs(ResultSet var1) throws SQLException;
}
