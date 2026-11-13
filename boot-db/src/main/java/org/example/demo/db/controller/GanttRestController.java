package org.example.demo.db.controller;

import jakarta.annotation.Resource;
import org.example.demo.api.dto.GanttLinkDto;
import org.example.demo.api.dto.GanttTaskDto;
import org.example.demo.api.dto.TaskModel;
import org.example.demo.db.entity.GanttLink;
import org.example.demo.db.entity.GanttTask;
import org.example.demo.db.service.GanttLinkService;
import org.example.demo.db.service.GanttTaskService;
import org.example.demo.db.utils.GanttLinkMapstruct;
import org.example.demo.db.utils.GanttTaskMapstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gantt")
public class GanttRestController {

    @Resource
    private GanttTaskService ganttTaskService;
    @Resource
    private GanttLinkService ganttLinkService;

    @GetMapping
    public TaskModel tasks() {
        List<GanttTaskDto> taskDtoList = ganttTaskService.queryGanttTasks();
        List<GanttLinkDto> linkDtoList = ganttLinkService.queryGanttLinks();
        TaskModel taskModel = new TaskModel(taskDtoList, linkDtoList);
        return taskModel;
    }

    @PostMapping("/task")
    public Map<String, Object> addTask(@RequestBody GanttTaskDto taskDto) {
        GanttTask ganttTask = ganttTaskService.save(GanttTaskMapstruct.INSTANCE.dtoToEntity(taskDto));
        Map<String, Object> data = new HashMap<>();
        data.put("action", "inserted");
        data.put("tid", ganttTask.getId());
        return data;
    }

    @PostMapping("/link")
    public Map<String, Object> addLink(@RequestBody GanttLinkDto linkDto) {
        GanttLink ganttLink = ganttLinkService.save(GanttLinkMapstruct.INSTANCE.dtoToEntity(linkDto));
        Map<String, Object> data = new HashMap<>();
        data.put("action", "inserted");
        data.put("tid", ganttLink.getId());
        return data;
    }

    @PutMapping("/task/{id}")
    public Map<String, Object> updateTask(@PathVariable Integer id, @RequestBody GanttTaskDto taskDto) {
        taskDto.setId(id);
        GanttTask ganttTask = ganttTaskService.update(GanttTaskMapstruct.INSTANCE.dtoToEntity(taskDto));
        Map<String, Object> data = new HashMap<>();
        data.put("action", "updated");
        return data;
    }

    @PutMapping("/link/{id}")
    public Map<String, Object> updateLink(@PathVariable Integer id, @RequestBody GanttLinkDto linkDto) {
        linkDto.setId(id);
        GanttLink ganttLink = ganttLinkService.update(GanttLinkMapstruct.INSTANCE.dtoToEntity(linkDto));
        Map<String, Object> data = new HashMap<>();
        data.put("action", "updated");
        return data;
    }

    @DeleteMapping("/task/{id}")
    public Map<String, Object> deleteTask(@PathVariable Integer id) {
        Integer result = ganttTaskService.deleteGanttTaskById(id);
        Map<String, Object> data = new HashMap<>();
        if (result > 0) {
            data.put("action", "deleted");
        } else {
            data.put("action", "failed");
        }
        return data;
    }

    @DeleteMapping("/link/{id}")
    public Map<String, Object> deleteLink(@PathVariable Integer id) {
        Integer result = ganttLinkService.deleteGanttLinkById(id);
        Map<String, Object> data = new HashMap<>();
        if (result > 0) {
            data.put("action", "deleted");
        } else {
            data.put("action", "failed");
        }
        return data;
    }
}
