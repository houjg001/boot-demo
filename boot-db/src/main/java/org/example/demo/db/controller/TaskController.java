package org.example.demo.db.controller;

import org.example.demo.api.dto.TaskDto;
import org.example.demo.api.dto.TaskModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    @GetMapping("/index")
    public String index() {

        return "task/index";
    }

    @ResponseBody
    @GetMapping("/list")
    public TaskModel tasks() {
        TaskModel taskModel = new TaskModel();
        List<TaskDto> tasks = new ArrayList<>();
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setText("任务1");
        taskDto.setOpen(true);
        taskDto.setType("project");
        tasks.add(taskDto);
        TaskDto taskDto1_1 = new TaskDto();
        taskDto1_1.setId(2L);
        taskDto1_1.setParent(1L);
        taskDto1_1.setText("完成设计");
        Calendar calendar = Calendar.getInstance();
        calendar.set(20026,11,9);
        taskDto1_1.setStartDate(calendar.getTime());
        taskDto1_1.setDuration(4);
        taskDto1_1.setProgress(0.3f);
        tasks.add(taskDto1_1);
        taskModel.setTasks(tasks);
        return taskModel;
    }
}
