package org.example.demo.db.utils;

import org.example.demo.api.dto.GanttTaskDto;
import org.example.demo.db.entity.GanttTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GanttTaskMapstruct extends MapstructUtils<GanttTask, GanttTaskDto> {
    GanttTaskMapstruct INSTANCE = Mappers.getMapper(GanttTaskMapstruct.class);
}
