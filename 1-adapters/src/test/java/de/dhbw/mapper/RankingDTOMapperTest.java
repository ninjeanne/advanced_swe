package de.dhbw.mapper;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.entities.player.PlayerStatisticsEntity;
import de.dhbw.entities.ranking.RankingEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class RankingDTOMapperTest {

    private PlayerStatisticsMapper playerStatisticsMapper;
    private RankingDTOMapper rankingDTOMapper;

    @Before
    public void setup() {
        playerStatisticsMapper = new PlayerStatisticsMapper();
        rankingDTOMapper = new RankingDTOMapper(playerStatisticsMapper);
    }

    @Test
    public void testMapping() {
        RankingEntity rankingEntity1 = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        RankingDTO result = rankingDTOMapper.apply(rankingEntity1);

        Assert.assertEquals(result.getDate(), rankingEntity1.getDate());
        Assert.assertEquals(result.getName(), rankingEntity1.getName());
        Assert.assertEquals(result.getEarned_points(), rankingEntity1.getEarned_points());
        Assert.assertEquals(result.getTotal(), rankingEntity1.getTotal());
        Assert.assertEquals(result.getStatistics(), playerStatisticsMapper.apply(rankingEntity1.getPlayerStatistics()));
    }
}
