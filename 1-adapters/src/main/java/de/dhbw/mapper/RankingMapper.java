package de.dhbw.mapper;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.entities.RankingEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RankingMapper implements Function<RankingEntity, RankingDTO> {

    private RankingDTO map(RankingEntity rankingEntity) {
        return new RankingDTO(rankingEntity.getName(), rankingEntity.getEarned_points(), rankingEntity.getWorkItems().getNumberOfItems(),
                rankingEntity.getTotal(), rankingEntity.getDate());
    }

    @Override
    public RankingDTO apply(RankingEntity rankingEntity) {
        return map(rankingEntity);
    }
}
