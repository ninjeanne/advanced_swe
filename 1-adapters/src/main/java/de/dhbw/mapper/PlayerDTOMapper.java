package de.dhbw.mapper;

import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlayerDTO;
import de.dhbw.entities.Infection;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.Vaccination;
import de.dhbw.entities.WorkItem;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlayerDTOMapper implements Function<PlayerEntity, PlayerDTO> {

    private PlayerDTO map(PlayerEntity playerEntity) {
        int vaccination = playerEntity.getPlayerStatistics().getStatistic(Vaccination.class).getCount();
        int infection = playerEntity.getPlayerStatistics().getStatistic(Infection.class).getCount();
        return PlayerDTO.builder()
                .lifePoints(vaccination - infection)
                .workItems(playerEntity.getPlayerStatistics().getStatistic(WorkItem.class).getCount())
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
