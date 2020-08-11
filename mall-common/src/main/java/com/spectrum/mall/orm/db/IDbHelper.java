package com.spectrum.mall.orm.db;

import com.spectrum.mall.orm.exception.CodegenException;
import com.spectrum.mall.orm.model.TableModel;

import java.util.List;

/**
 * @author oe_qinzuopu
 */
public interface IDbHelper {
    void setUrl(String var1, String var2, String var3);

    TableModel getByTable(String var1) throws CodegenException;

    List<String> getAllTable() throws CodegenException;

    List<String> getAllTable(String var1) throws CodegenException;
}
