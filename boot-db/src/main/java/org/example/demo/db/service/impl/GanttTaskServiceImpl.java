package org.example.demo.db.service.impl;

import org.example.demo.api.dto.GanttTaskDto;
import org.example.demo.db.entity.GanttTask;
import org.example.demo.db.mapper.GanttTaskMapper;
import org.example.demo.db.service.GanttTaskService;
import org.example.demo.db.utils.GanttTaskMapstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GanttTaskServiceImpl extends BaseServiceImpl<GanttTaskMapper, GanttTask> implements GanttTaskService {

    @Autowired
    private GanttTaskMapper ganttTaskMapper;

    @Override
    public List<GanttTaskDto> queryGanttTasks() {
        List<GanttTask> ganttTasks = ganttTaskMapper.queryGanttTasks();
        return GanttTaskMapstruct.INSTANCE.entityListToDtoList(ganttTasks);
    }
}
