package org.example.demo.db.utils;

import java.util.List;

public interface MapstructUtils<E, D> {

    E dtoToEntity(D dto);
    D entityToDto(E entity);
    List<D> entityListToDtoList(List<E> entityList);
}
