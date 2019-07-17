package com.douya.base;

import java.util.List;

public interface BaseDao<T> {
    T get(Long var1);

    T get(T var1);

    List<T> findList(T var1);

    List<T> findAllList();

    int insert(T var1);

    int update(T var1);

    int dynamicUpdate(T var1);

    int delete(Long var1);

    int delete(T var1);



}
