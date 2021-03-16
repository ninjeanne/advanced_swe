package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BoardDTO {
    private String id;
    private String name;
    private CoordinatesDTO vaccination;
    private CoordinatesDTO workItem;
    private PlanDTO plan;
    private int infectProbability;
    private int colleagueRadius;
}
