package org.example.demo.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.demo.db.entity.GanttTask;

import java.util.List;

@Mapper
public interface GanttTaskMapper extends BaseMapper<GanttTask> {

    List<GanttTask> queryGanttTasks();
}
