package de.dhbw.mapper;

import de.dhbw.dtos.PlayerDTO;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.ItemsVO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlayerMapper implements Function<PlayerDTO, PlayerEntity> {

    private PlayerEntity map(PlayerDTO playerDTO) {
        return new PlayerEntity(playerDTO.getName(), new CoordinatesVO(playerDTO.getCoordinates().getX(), playerDTO.getCoordinates().getY()),
                new ItemsVO(playerDTO.getLifePoints()), new ItemsVO(playerDTO.getWorkItems()));
    }

    @Override
    public PlayerEntity apply(PlayerDTO playerDTO) {
        return map(playerDTO);
    }
}
