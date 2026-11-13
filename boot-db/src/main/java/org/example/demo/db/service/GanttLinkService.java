package org.example.demo.db.service;

import org.example.demo.api.dto.GanttLinkDto;
import org.example.demo.db.entity.GanttLink;
import org.example.demo.db.mapper.BaseMapper;

import java.util.List;

public interface GanttLinkService extends BaseService<GanttLink> {

    List<GanttLinkDto> queryGanttLinks();

    Integer deleteGanttLinkById(Integer id);
    Integer deleteGanttLinkByTaskId(List<Integer> taskIds);
}
