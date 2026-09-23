package org.example.demo.db.utils;

import org.example.demo.db.dto.AddressDto;
import org.example.demo.db.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;

public interface MapstructUtils<E, D> {

    E dtoToEntity(D dto);
    D entityToDto(E entity);
}
