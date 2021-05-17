package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GameObjectDTO {
    private String id;
    private CoordinatesDTO coordinatesDTO;
    //TODO builder
}
