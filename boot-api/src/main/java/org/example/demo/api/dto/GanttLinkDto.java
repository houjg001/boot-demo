package org.example.demo.api.dto;

import lombok.Data;

@Data
public class GanttLinkDto {
    private Integer id;

    private Integer source;

    private Integer target;

    private String type;
}
