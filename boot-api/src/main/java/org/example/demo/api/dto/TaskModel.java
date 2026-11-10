package org.example.demo.api.dto;

import java.util.List;

public class TaskModel {
    private List<TaskDto> tasks;

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
