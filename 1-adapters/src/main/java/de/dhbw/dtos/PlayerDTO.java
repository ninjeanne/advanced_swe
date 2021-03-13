package de.dhbw.dtos;

import de.dhbw.valueobjects.CoordinatesVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PlayerDTO {
    private String name;
    private CoordinatesVO coordinates;
    private int lifePoints;
}
