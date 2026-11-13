package org.example.demo.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.demo.db.entity.GanttLink;

import java.util.List;

@Mapper
public interface GanttLinkMapper extends BaseMapper<GanttLink>{

    List<GanttLink> queryGanttLinks();

    @Update("update t_gantt_links set del_flag = 1 where id = #{id}")
    Integer deleteGanttLinkById(Integer id);
    Integer deleteGanttLinkByTaskId(List<Integer> taskIds);
}
