package de.dhbw.mapper;

import de.dhbw.dtos.PlayerDTO;
import de.dhbw.entities.PlayerEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlayerMapper implements Function<PlayerDTO, PlayerEntity> {

    private PlayerEntity map(PlayerDTO playerDTO) {
        return new PlayerEntity(playerDTO.getName(), playerDTO.getCoordinates(), playerDTO.getLifePoints());
    }

    @Override
    public PlayerEntity apply(PlayerDTO playerDTO) {
        return map(playerDTO);
    }
}
