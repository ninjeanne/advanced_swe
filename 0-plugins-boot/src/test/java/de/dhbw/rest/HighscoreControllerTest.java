package de.dhbw.rest;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.entities.player.PlayerStatisticsEntity;
import de.dhbw.entities.ranking.RankingEntity;
import de.dhbw.mapper.PlayerStatisticsMapper;
import de.dhbw.mapper.RankingDTOMapper;
import de.dhbw.services.HighscoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

public class HighscoreControllerTest {

    private HighscoreController highscoreController;

    @Mock
    private HighscoreService highscoreService;

    private RankingDTOMapper rankingDTOMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        rankingDTOMapper = new RankingDTOMapper(new PlayerStatisticsMapper());
        highscoreController = new HighscoreController(highscoreService, rankingDTOMapper);
    }

    @Test
    public void testGetHighscore() {
        RankingEntity first = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        RankingEntity second = new RankingEntity("id2", "playerName2", 3456, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        RankingEntity third = new RankingEntity("id3", "playerName3", 6789, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        List<RankingEntity> expectedHighscore = Arrays.asList(first, second, third);
        Mockito.when(highscoreService.getHighscore()).thenReturn(expectedHighscore);

        List<RankingDTO> rankingDTOS = new ArrayList<>();
        for (RankingEntity rankingEntity : expectedHighscore) {
            rankingDTOS.add(rankingDTOMapper.apply(rankingEntity));
        }

        List<RankingDTO> result = highscoreController.getHighscore();
        Assert.assertEquals(result.get(0).getName(), rankingDTOS.get(0).getName());
        Assert.assertEquals(result.get(1).getName(), rankingDTOS.get(1).getName());
        Assert.assertEquals(result.get(2).getName(), rankingDTOS.get(2).getName());
        Assert.assertEquals(result.get(2).getTotal(), rankingDTOS.get(2).getTotal());
        Assert.assertEquals(result.get(2).getEarned_points(), rankingDTOS.get(2).getEarned_points());
        Assert.assertEquals(result.get(2).getDate(), rankingDTOS.get(2).getDate());
    }

    @Test
    public void testGetEmptyHighscore() {
        List<RankingEntity> expectedHighscore = Collections.emptyList();
        Mockito.when(highscoreService.getHighscore()).thenReturn(expectedHighscore);

        List<RankingDTO> result = highscoreController.getHighscore();
        Assert.assertTrue(result.isEmpty());
    }

}
