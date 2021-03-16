package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PlayerDTO {
    private String name;
    private CoordinatesDTO position;
    private int lifePoints;
    private int workItems;
}
