package de.dhbw.mapper;

import de.dhbw.dtos.PlayerDTO;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.valueobjects.UserDetailsVO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlayerMapper implements Function<PlayerDTO, PlayerEntity> {

    private PlayerEntity map(PlayerDTO playerDTO) {
        return PlayerEntity.builder().userDetails(UserDetailsVO.builder().name(playerDTO.getName()).build()).build();
    }

    @Override
    public PlayerEntity apply(PlayerDTO playerDTO) {
        return map(playerDTO);
    }
}
