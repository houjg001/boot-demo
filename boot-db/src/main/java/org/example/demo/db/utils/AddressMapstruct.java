package org.example.demo.db.utils;

import org.example.demo.api.dto.AddressDto;
import org.example.demo.db.entity.Address;
import org.example.grpc.service.AddressInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapstruct extends MapstructUtils<Address, AddressDto> {
    AddressMapstruct INSTANCE = Mappers.getMapper(AddressMapstruct.class);
}
