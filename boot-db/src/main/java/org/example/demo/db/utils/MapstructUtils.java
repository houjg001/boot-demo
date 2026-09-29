package org.example.demo.db.utils;

public interface MapstructUtils<E, D> {

    E dtoToEntity(D dto);
    D entityToDto(E entity);
}
