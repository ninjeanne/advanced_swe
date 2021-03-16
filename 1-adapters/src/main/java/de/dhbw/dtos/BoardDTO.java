package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BoardDTO {
    private String name;
    private CoordinatesDTO vaccination;
    private CoordinatesDTO workItem;
    private List<CoordinatesDTO> obstacles;
    private List<CoordinatesDTO> colleagues;
    private PlanDTO plan;
    private double infectProbability;
    private int colleagueRadius;
}
