package org.example.demo.db.service.impl;

import org.example.demo.db.mapper.BaseMapper;
import org.example.demo.db.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {
    @Autowired
    M baseMapper;


    @Override
    public T save(T t) {
        baseMapper.insert(t);
        return t;
    }

    @Override
    public T update(T t) {
        baseMapper.updateByPrimaryKeySelective(t);
        return t;
    }

    @Override
    public T findById(Long id) {
        return (T) baseMapper.selectByPrimaryKey(id);
    }
}
