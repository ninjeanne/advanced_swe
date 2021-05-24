package de.dhbw.entities.ranking;

import de.dhbw.entities.gameobject.Infection;
import de.dhbw.entities.gameobject.Vaccination;
import de.dhbw.entities.gameobject.WorkItem;
import de.dhbw.entities.player.PlayerStatisticsEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class RankingEntityTest {
    @Test
    public void testEquals() {
        RankingEntity rankingEntity1 = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        RankingEntity rankingEntity2 = new RankingEntity("id1", "playerName2", 12345, new PlayerStatisticsEntity(), new Date(2022, Calendar.MAY, 24));

        Assert.assertEquals(rankingEntity1, rankingEntity2);
        Assert.assertEquals(rankingEntity2, rankingEntity1);
    }

    @Test
    public void testNotEquals() {
        RankingEntity rankingEntity1 = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        RankingEntity rankingEntity2 = new RankingEntity("id2", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));

        Assert.assertNotEquals(rankingEntity1, rankingEntity2);
        Assert.assertNotEquals(rankingEntity2, rankingEntity1);
    }

    @Test
    public void testEmptyPlayerStatistics() {
        RankingEntity rankingEntity1 = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));

        Assert.assertEquals(0, rankingEntity1.getPlayerStatistics().getStatisticsPerItems().size());
        Assert.assertEquals(rankingEntity1.getEarned_points(), rankingEntity1.getTotal());
    }

    @Test
    public void testGetTotalForAllItemTypes() {
        RankingEntity rankingEntity1 = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));

        rankingEntity1.getPlayerStatistics().increaseStatistics(Vaccination.class);
        Assert.assertEquals(1, rankingEntity1.getPlayerStatistics().getStatisticsPerItems().size());
        Assert.assertEquals(rankingEntity1.getEarned_points() + new Vaccination().getRankingValue(), rankingEntity1.getTotal());

        rankingEntity1.getPlayerStatistics().increaseStatistics(Infection.class);
        Assert.assertEquals(2, rankingEntity1.getPlayerStatistics().getStatisticsPerItems().size());
        Assert.assertEquals(rankingEntity1.getEarned_points() + new Infection().getRankingValue(), rankingEntity1.getTotal());

        rankingEntity1.getPlayerStatistics().increaseStatistics(WorkItem.class);
        Assert.assertEquals(3, rankingEntity1.getPlayerStatistics().getStatisticsPerItems().size());
        Assert.assertEquals(rankingEntity1.getEarned_points() + new WorkItem().getRankingValue(), rankingEntity1.getTotal());
    }

    @Test
    public void testGetTotalForMultipleWorkItems() {
        RankingEntity rankingEntity1 = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        int numberOfWorkItems = 5;

        for (int i = 0; i < numberOfWorkItems; i++) {
            rankingEntity1.getPlayerStatistics().increaseStatistics(WorkItem.class);
        }
        Assert.assertEquals(1, rankingEntity1.getPlayerStatistics().getStatisticsPerItems().size());
        Assert.assertEquals(rankingEntity1.getEarned_points() + (long) new WorkItem().getRankingValue() * numberOfWorkItems, rankingEntity1.getTotal());
    }

    @Test
    public void testGetTotalForMultipleVaccinations() {
        RankingEntity rankingEntity1 = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        int numberOfVaccination = 5;

        for (int i = 0; i < numberOfVaccination; i++) {
            rankingEntity1.getPlayerStatistics().increaseStatistics(Vaccination.class);
        }
        Assert.assertEquals(1, rankingEntity1.getPlayerStatistics().getStatisticsPerItems().size());
        Assert.assertEquals(rankingEntity1.getEarned_points() + (long) new Vaccination().getRankingValue() * numberOfVaccination, rankingEntity1.getTotal());
    }
}
