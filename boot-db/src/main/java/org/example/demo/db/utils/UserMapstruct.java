package org.example.demo.db.utils;

import com.google.protobuf.Timestamp;
import org.example.demo.api.dto.UserDto;
import org.example.demo.db.entity.User;
import org.example.grpc.service.UserProto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.*;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserMapstruct extends MapstructUtils<User, UserDto> {
    UserMapstruct INSTANCE = Mappers.getMapper(UserMapstruct.class);

    @Named("localDateToTimestamp")
    default com.google.protobuf.Timestamp localDateToTimestamp(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Instant instant = localDateTime.atZone(ZoneId.of("UTC")).toInstant();
        return Timestamp.newBuilder().setSeconds(instant.getEpochSecond()).setNanos(instant.getNano()).build();
    }

    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "localDateToTimestamp")
    UserProto toProto(User user);

    @Mappings({
            @Mapping(target = "birthday", source = "birthday", qualifiedByName = "localDateToTimestamp"),
            @Mapping(target = "addressesList", source = "addresses")}
    )
    List<UserProto> dtoToProto(List<UserDto> userDto);

}
