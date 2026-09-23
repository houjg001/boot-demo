package org.example.demo.db.utils;

import org.example.demo.db.dto.UserDto;
import org.example.demo.db.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapstruct extends MapstructUtils<User, UserDto> {
    UserMapstruct INSTANCE = Mappers.getMapper(UserMapstruct.class);
}
