package de.dhbw.mapper;

import de.dhbw.dtos.CoordinateDTO;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CoordinateMapper implements Function<CoordinateDTO, CoordinatesVO> {

    private CoordinatesVO map(CoordinateDTO coordinateDTO) {
        return new CoordinatesVO(0, coordinateDTO.getX(), coordinateDTO.getY());
    }

    @Override
    public CoordinatesVO apply(CoordinateDTO coordinateDTO) {
        return map(coordinateDTO);
    }
}
