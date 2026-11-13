package org.example.demo.db.service;

import org.example.demo.api.dto.GanttTaskDto;
import org.example.demo.db.entity.GanttTask;

import java.util.List;

public interface GanttTaskService extends BaseService<GanttTask> {

    List<GanttTaskDto> queryGanttTasks();

    Integer deleteGanttTaskById(Integer id);
}
