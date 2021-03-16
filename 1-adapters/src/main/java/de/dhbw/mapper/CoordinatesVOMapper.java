package de.dhbw.mapper;

import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CoordinatesVOMapper implements Function<CoordinatesDTO, CoordinatesVO> {

    private CoordinatesVO map(CoordinatesDTO coordinatesDTO){
        return new CoordinatesVO(coordinatesDTO.getX(), coordinatesDTO.getY());
    }

    @Override
    public CoordinatesVO apply(CoordinatesDTO coordinatesDTO) {
        return map(coordinatesDTO);
    }
}
