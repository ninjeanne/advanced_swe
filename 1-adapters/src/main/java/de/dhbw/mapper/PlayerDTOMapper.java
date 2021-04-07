package de.dhbw.mapper;

import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlayerDTO;
import de.dhbw.entities.PlayerEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlayerDTOMapper implements Function<PlayerEntity, PlayerDTO> {

    private PlayerDTO map(PlayerEntity playerEntity) {
        return PlayerDTO.builder()
                .lifePoints(playerEntity.getLifePoints().getNumberOfItems())
                .workItems(playerEntity.getWorkItems().getNumberOfItems())
                .name(playerEntity.getNameAsEntityID())
                .position(CoordinatesDTO.builder()
                        .x(playerEntity.getPosition().getX())
                        .y(playerEntity.getPosition().getY())
                        .build())
                .build();
    }

    @Override
    public PlayerDTO apply(PlayerEntity playerEntity) {
        return map(playerEntity);
    }
}
