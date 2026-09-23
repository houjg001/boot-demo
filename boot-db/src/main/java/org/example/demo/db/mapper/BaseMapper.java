package org.example.demo.db.mapper;

import java.io.Serializable;

public interface BaseMapper<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T row);

    int insertSelective(T row);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKey(T row);
}
