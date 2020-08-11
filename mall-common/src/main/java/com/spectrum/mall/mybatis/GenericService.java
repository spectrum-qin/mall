package com.spectrum.mall.mybatis;

import java.io.Serializable;
import java.util.List;

/**
 * @author oe_qinzuopu
 */
public interface GenericService<T, Pk extends Serializable> {
    void add(T var1);

    void addBatch(List<T> var1);

    int updateById(T var1);

    int deleteById(Pk var1);

    int delLogicById(Pk var1);

    int deleteBatchIds(List<? extends Pk> var1);

    List<T> findAll();

    List<T> find(T var1);

    List<T> findByPage(int var1, int var2, T var3);

    T findOneById(Pk var1);
}