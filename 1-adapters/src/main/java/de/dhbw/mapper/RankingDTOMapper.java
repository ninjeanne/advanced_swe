package de.dhbw.mapper;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.entities.RankingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RankingDTOMapper implements Function<RankingEntity, RankingDTO> {

    private final PlayerStatisticsMapper playerStatisticsMapper;

    @Autowired
    public RankingDTOMapper(PlayerStatisticsMapper playerStatisticsMapper) {
        this.playerStatisticsMapper = playerStatisticsMapper;
    }

    private RankingDTO map(RankingEntity rankingEntity) {
        return new RankingDTO(rankingEntity.getName(), rankingEntity.getEarned_points(), playerStatisticsMapper.apply(rankingEntity.getPlayerStatistics()),
                rankingEntity.getTotal(), rankingEntity.getDate());
    }

    @Override
    public RankingDTO apply(RankingEntity rankingEntity) {
        return map(rankingEntity);
    }
}
