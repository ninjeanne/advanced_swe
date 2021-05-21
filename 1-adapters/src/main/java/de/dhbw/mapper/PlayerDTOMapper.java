package de.dhbw.mapper;

import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlayerDTO;
import de.dhbw.entities.player.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlayerDTOMapper implements Function<PlayerEntity, PlayerDTO> {

    private final PlayerStatisticsMapper playerStatisticsMapper;

    @Autowired
    public PlayerDTOMapper(PlayerStatisticsMapper playerStatisticsMapper) {
        this.playerStatisticsMapper = playerStatisticsMapper;
    }

    private PlayerDTO map(PlayerEntity playerEntity) {
        return PlayerDTO.builder()
                .statistics(playerStatisticsMapper.apply(playerEntity.getPlayerStatistics()))
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
