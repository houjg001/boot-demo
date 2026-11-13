package org.example.demo.db.controller;

import jakarta.annotation.Resource;
import org.example.demo.api.dto.GanttTaskDto;
import org.example.demo.api.dto.TaskModel;
import org.example.demo.api.utils.ResponseResult;
import org.example.demo.db.entity.GanttTask;
import org.example.demo.db.service.GanttTaskService;
import org.example.demo.db.utils.GanttTaskMapstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gantt")
public class GanttRestController {

    @Resource
    private GanttTaskService ganttTaskService;

    @GetMapping
    public TaskModel tasks() {
        TaskModel taskModel = new TaskModel();
        List<GanttTaskDto> taskDtoList = ganttTaskService.queryGanttTasks();
        taskModel.setTasks(taskDtoList);
        return taskModel;
    }

    @PostMapping("/task")
    public Map<String, Object> add(@RequestBody GanttTaskDto taskDto) {
        GanttTask ganttTask = ganttTaskService.save(GanttTaskMapstruct.INSTANCE.dtoToEntity(taskDto));
        Map<String, Object> data = new HashMap<>();
        data.put("action", "inserted");
        data.put("tid", ganttTask.getId());
        return data;
    }

    @PutMapping("/task/{id}")
    public Map<String, Object> update(@PathVariable Integer id, @RequestBody GanttTaskDto taskDto) {
        taskDto.setId(id);
        GanttTask ganttTask = ganttTaskService.update(GanttTaskMapstruct.INSTANCE.dtoToEntity(taskDto));
        Map<String, Object> data = new HashMap<>();
        data.put("action", "updated");
        return data;
    }

    @DeleteMapping("/task/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        Integer result = ganttTaskService.deleteGanttTaskById(id);
        Map<String, Object> data = new HashMap<>();
        if (result > 0) {
            data.put("action", "deleted");
        } else {
            data.put("action", "failed");
        }
        return data;
    }
}
