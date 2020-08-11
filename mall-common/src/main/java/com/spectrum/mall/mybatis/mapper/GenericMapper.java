package com.spectrum.mall.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author oe_qinzuopu
 */
public interface GenericMapper<T, Pk extends Serializable> {
    void add(T var1);

    void addBatch(List<T> var1);

    int updateById(T var1);

    int deleteById(Pk var1);

    int delLogicById(Pk var1);

    int deleteBatchIds(List<? extends Pk> var1);

    List<T> findAll();

    T findOneById(Pk var1);

    List<T> find(T var1);
}
