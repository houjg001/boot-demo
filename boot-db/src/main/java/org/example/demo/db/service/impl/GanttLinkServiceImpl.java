package org.example.demo.db.service.impl;


import org.example.demo.api.dto.GanttLinkDto;
import org.example.demo.db.entity.GanttLink;
import org.example.demo.db.mapper.GanttLinkMapper;
import org.example.demo.db.service.GanttLinkService;
import org.example.demo.db.utils.GanttLinkMapstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GanttLinkServiceImpl extends BaseServiceImpl<GanttLinkMapper, GanttLink> implements GanttLinkService {

    @Autowired
    private GanttLinkMapper ganttLinkMapper;

    @Override
    public List<GanttLinkDto> queryGanttLinks() {
        List<GanttLink>  ganttLinks = ganttLinkMapper.queryGanttLinks();
        return GanttLinkMapstruct.INSTANCE.entityListToDtoList(ganttLinks);
    }

    @Override
    public Integer deleteGanttLinkById(Integer id) {
        return ganttLinkMapper.deleteGanttLinkById(id);
    }

    @Override
    public Integer deleteGanttLinkByTaskId(List<Integer> taskIds) {
        return ganttLinkMapper.deleteGanttLinkByTaskId(taskIds);
    }
}
