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

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserProtoMapstruct {

    UserProtoMapstruct INSTANCE = Mappers.getMapper(UserProtoMapstruct.class);

    @Named("timestampToLocalDate")
    default LocalDate timestampToLocalDate(Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return instant.atZone(ZoneId.of("UTC")).toLocalDate();
    }

    @Mappings({
            @Mapping(target = "birthday", source = "birthday", qualifiedByName = "timestampToLocalDate"),
            @Mapping(target = "addresses", source = "addressesList")}
    )
    UserDto protoToDto(UserProto proto);

    List<UserDto> protoListToDtoList(List<UserProto> userProtoList);


}
