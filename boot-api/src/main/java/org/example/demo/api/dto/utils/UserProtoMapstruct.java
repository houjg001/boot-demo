package org.example.demo.api.dto.utils;

import com.google.protobuf.Timestamp;
import org.example.demo.api.dto.UserDto;
import org.example.grpc.service.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProtoMapstruct {

    UserProtoMapstruct INSTANCE = Mappers.getMapper(UserProtoMapstruct.class);

    @Named("timestampToLocalDate")
    default LocalDate timestampToLocalDate(Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return instant.atZone(ZoneId.of("UTC")).toLocalDate();
    }

    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "timestampToLocalDate")
    UserDto toDto(UserInfo userInfo);


}
