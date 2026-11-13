package org.example.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskModel {
    private List<GanttTaskDto> tasks;
    private List<GanttLinkDto> links;
}
