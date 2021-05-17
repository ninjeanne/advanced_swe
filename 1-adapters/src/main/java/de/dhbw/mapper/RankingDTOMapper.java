package de.dhbw.mapper;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.entities.PlayerStatistics;
import de.dhbw.entities.RankingEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class RankingDTOMapper implements Function<RankingEntity, RankingDTO> {

    private RankingDTO map(RankingEntity rankingEntity) {
        return new RankingDTO(rankingEntity.getName(), rankingEntity.getEarned_points(), getStatistics(rankingEntity.getPlayerStatistics()),
                rankingEntity.getTotal(), rankingEntity.getDate());
    }

    @Override
    public RankingDTO apply(RankingEntity rankingEntity) {
        return map(rankingEntity);
    }

    private Map<String, Integer> getStatistics(PlayerStatistics playerStatistics){
        Map<String, Integer> statistics = new HashMap<>();
        playerStatistics.getStatisticsPerItems().forEach((key, value) -> {
            statistics.put(key.getName(), value.getCount());
        });
        return statistics;
    }
}
