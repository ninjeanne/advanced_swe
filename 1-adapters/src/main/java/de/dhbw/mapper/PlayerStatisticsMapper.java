package de.dhbw.mapper;

import de.dhbw.entities.PlayerStatisticsEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class PlayerStatisticsMapper implements Function<PlayerStatisticsEntity, Map<String, Integer>> {

    @Override
    public Map<String, Integer> apply(PlayerStatisticsEntity playerStatistics) {
        return map(playerStatistics);
    }

    private Map<String, Integer> map(PlayerStatisticsEntity playerStatistics) {
        Map<String, Integer> statistics = new HashMap<>();
        playerStatistics.getStatisticsPerItems().forEach((key, value) -> {
            statistics.put(key.getSimpleName(), value.getCount());
        });
        return statistics;
    }
}
