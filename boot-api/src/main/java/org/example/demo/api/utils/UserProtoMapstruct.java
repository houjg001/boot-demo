package org.example.demo.api.utils;

import com.google.protobuf.Timestamp;
import org.example.demo.api.dto.UserDto;
import org.example.grpc.service.UserProto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProtoMapstruct {

    UserProtoMapstruct INSTANCE = Mappers.getMapper(UserProtoMapstruct.class);

    @Named("timestampToLocalDate")
    default LocalDate timestampToLocalDate(Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return instant.atZone(ZoneId.of("UTC")).toLocalDate();
    }

    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "timestampToLocalDate")
    UserDto toDto(UserProto userInfo);

    @Mappings({
            @Mapping(target = "birthday", source = "birthday", qualifiedByName = "timestampToLocalDate"),
            @Mapping(target = "addresses", source = "addressesList")}
    )
    List<UserDto> protoToDto(List<UserProto> userInfo);


}
