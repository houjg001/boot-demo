package org.example.demo.db.mapper;

import org.apache.ibatis.annotations.Mapper;

public interface BaseMapper<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T row);

    int insertSelective(T row);

    T selectByPrimaryKey(Long addressId);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKey(T row);
}
