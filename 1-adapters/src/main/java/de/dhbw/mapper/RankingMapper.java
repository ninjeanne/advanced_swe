package de.dhbw.mapper;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.entities.RankingEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RankingMapper implements Function<RankingEntity, RankingDTO> {

    private RankingDTO map(RankingEntity rankingEntity) {
        //todo implement me
        return null;
    }

    @Override
    public RankingDTO apply(RankingEntity rankingEntity) {
        return map(rankingEntity);
    }
}
