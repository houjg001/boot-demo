package org.example.demo.api.dto;

import java.util.List;

public class TaskModel {
    private List<GanttTaskDto> tasks;

    public List<GanttTaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<GanttTaskDto> tasks) {
        this.tasks = tasks;
    }
}
