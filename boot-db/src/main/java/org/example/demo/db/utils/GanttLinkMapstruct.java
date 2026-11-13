package org.example.demo.db.utils;

import org.example.demo.api.dto.GanttLinkDto;
import org.example.demo.db.entity.GanttLink;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GanttLinkMapstruct extends MapstructUtils<GanttLink, GanttLinkDto> {

    GanttLinkMapstruct INSTANCE = Mappers.getMapper(GanttLinkMapstruct.class);
}
