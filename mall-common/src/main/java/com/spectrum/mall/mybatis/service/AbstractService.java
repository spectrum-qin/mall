package com.spectrum.mall.mybatis.service;

import ch.qos.logback.classic.Logger;
import com.github.pagehelper.PageHelper;
import com.spectrum.mall.common.BaseDomain;
import com.spectrum.mall.mybatis.GenericService;
import com.spectrum.mall.mybatis.mapper.GenericMapper;
import com.spectrum.mall.utils.bean.test.Objects;
import com.spectrum.mall.utils.text.UuidUtils;
import com.spectrum.mall.utils.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface AbstractService<T extends BaseDomain, Pk extends Serializable> extends GenericService<T, Pk> {
    Logger log = (Logger) LoggerFactory.getLogger(AbstractService.class);
    int PAGENUM = 1;
    int PAGESIZE_20 = 20;
    int PAGESIZE_50 = 50;
    int PAGESIZE_100 = 100;

    GenericMapper<T, Pk> getMapper();

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    default void add(T entity) {
        if (entity.getId() == null || Objects.isEquals(entity.getId(), "")) {
            entity.setId(UuidUtils.getUuid());
        }

        this.setCreateAndUpdateTime(entity);
        this.getMapper().add(entity);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    default void addBatch(List<T> list) {
        if (!StringUtils.isEmpty(list) && list.size() != 0) {
            List<T> subList = new ArrayList();
            int i = 1;
            int k = 1;

            for(Iterator var5 = list.iterator(); var5.hasNext(); ++i) {
                T entity = (T) var5.next();
                if (entity.getId() == null || Objects.isEquals(entity.getId(), "")) {
                    entity.setId(UuidUtils.getUuid());
                }

                this.setCreateAndUpdateTime(entity);
                subList.add(entity);
                if (i % 500 == 0) {
                    long t1 = System.currentTimeMillis();
                    this.getMapper().addBatch(subList);
                    log.info("执行序号：" + k + ",执行时间:" + (System.currentTimeMillis() - t1) + ",类名:" + entity.getClass().getName());
                    ++k;
                    subList.clear();
                }
            }

            if (subList.size() > 0) {
                long t2 = System.currentTimeMillis();
                this.getMapper().addBatch(subList);
                log.debug("执行序号：" + k + ",执行时间:" + (System.currentTimeMillis() - t2));
                subList.clear();
            }

        }
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    default int updateById(T entity) {
        entity.setUpdateTime(DateUtils.getCurTimestamp());
        return this.getMapper().updateById(entity);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    default int deleteById(Pk pk) {
        return this.getMapper().deleteById(pk);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    default int delLogicById(Pk pk) {
        return this.getMapper().delLogicById(pk);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class}
    )
    default int deleteBatchIds(List<? extends Pk> pkList) {
        return this.getMapper().deleteBatchIds(pkList);
    }

    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS,
            rollbackFor = {Exception.class},
            readOnly = true
    )
    default List<T> findAll() {
        return this.getMapper().findAll();
    }

    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS,
            rollbackFor = {Exception.class},
            readOnly = true
    )
    default T findOneById(Pk pk) {
        return (T) this.getMapper().findOneById(pk);
    }

    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS,
            rollbackFor = {Exception.class},
            readOnly = true
    )
    default List<T> find(T condition) {
        return this.getMapper().find(condition);
    }

    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS,
            rollbackFor = {Exception.class},
            readOnly = true
    )
    default List<T> findByPage(int pageNum, int pageSize, T condition) {
        PageHelper.startPage(pageNum, pageSize);
        return this.getMapper().find(condition);
    }

    default void setCreateAndUpdateTime(T entity) {
        Timestamp curTimestamp = null;
        if (entity.getCreateTime() == null) {
            curTimestamp = DateUtils.getCurTimestamp();
            entity.setCreateTime(curTimestamp);
        }

        if (entity.getUpdateTime() == null) {
            entity.setUpdateTime(curTimestamp);
        }

    }
}
