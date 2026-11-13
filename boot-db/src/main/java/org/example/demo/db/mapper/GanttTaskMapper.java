package org.example.demo.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.demo.db.entity.GanttTask;

import java.util.List;

@Mapper
public interface GanttTaskMapper extends BaseMapper<GanttTask> {

    List<GanttTask> queryGanttTasks();

    /**
     * 逻辑删除
     * @param ids
     * @return
     */
    Integer deleteGanttTaskById(List<Integer> ids);

    @Select("""
    WITH RECURSIVE children_task AS (
    SELECT id FROM t_gantt_tasks WHERE id=#{id} AND del_flag=0 UNION ALL
    SELECT gt.id FROM t_gantt_tasks gt INNER JOIN children_task ct ON gt.parent=ct.id WHERE gt.del_flag=0)
    SELECT id FROM children_task
    """)
    List<Integer> queryChildrenTasksByParent(Integer id);
}
