package de.dhbw.mapper;

import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlayerDTO;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.PlayerStatistics;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class PlayerDTOMapper implements Function<PlayerEntity, PlayerDTO> {

    private PlayerDTO map(PlayerEntity playerEntity) {
        return PlayerDTO.builder()
                .statistics(getStatistics(playerEntity.getPlayerStatistics()))
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

    private Map<String, Integer> getStatistics(PlayerStatistics playerStatistics){
        Map<String, Integer> statistics = new HashMap<>();
        playerStatistics.getStatisticsPerItems().forEach((key, value) -> {
            statistics.put(key.getSimpleName(), value.getCount());
        });
        return statistics;
    }
}
