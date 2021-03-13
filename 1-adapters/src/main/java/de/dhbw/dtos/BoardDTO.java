package de.dhbw.dtos;

import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.PlanVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BoardDTO {
    private String id;
    private String name;
    private CoordinatesVO coordinates;
    private PlanVO plan;
    private int velocity;
}
