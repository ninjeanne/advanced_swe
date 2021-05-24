package de.dhbw.services;

import de.dhbw.entities.player.PlayerStatisticsEntity;
import de.dhbw.entities.ranking.RankingEntity;
import de.dhbw.repositories.RankingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HighscoreServiceTest {

    @Mock
    private RankingRepository rankingRepository;

    private HighscoreService highscoreService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        highscoreService = new HighscoreService(rankingRepository);
    }

    @Test
    public void testGetHighscore() {
        List<RankingEntity> expectedHighscore = Collections
                .singletonList(new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24)));
        Mockito.when(rankingRepository.getHighscore()).thenReturn(expectedHighscore);

        Assert.assertEquals(expectedHighscore.size(), highscoreService.getHighscore().size());
        Assert.assertEquals(expectedHighscore, rankingRepository.getHighscore());
        Assert.assertEquals(expectedHighscore, highscoreService.getHighscore());
    }

    @Test
    public void testSortOfGetHighscore() {
        RankingEntity first = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        RankingEntity second = new RankingEntity("id2", "playerName2", 3456, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        RankingEntity third = new RankingEntity("id3", "playerName3", 6789, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        List<RankingEntity> expectedHighscore = Arrays.asList(first, second, third);
        Mockito.when(rankingRepository.getHighscore()).thenReturn(expectedHighscore);

        Assert.assertEquals(expectedHighscore.size(), highscoreService.getHighscore().size());
        Assert.assertEquals(third, highscoreService.getHighscore().get(0));
        Assert.assertNotEquals(first, highscoreService.getHighscore().get(0));
        Assert.assertEquals(second, highscoreService.getHighscore().get(1));
        Assert.assertEquals(first, highscoreService.getHighscore().get(2));
        Assert.assertNotEquals(third, highscoreService.getHighscore().get(2));
    }

    @Test
    public void testIsInHighscore() {
        RankingEntity first = new RankingEntity("id1", "playerName", 1234, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        RankingEntity second = new RankingEntity("id2", "playerName2", 3456, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        RankingEntity third = new RankingEntity("id3", "playerName3", 6789, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        List<RankingEntity> expectedHighscore = Arrays.asList(first, second, third);
        Mockito.when(rankingRepository.getHighscore()).thenReturn(expectedHighscore);

        RankingEntity newRanking = new RankingEntity("id4", "playerName4", 9999, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));

        Assert.assertTrue(highscoreService.isInHighscore(newRanking));
    }

    @Test
    public void testIsInHighscoreHighEarnedPoints() {
        List<RankingEntity> expectedHighscore = new ArrayList<>();
        for (int i = 0; i < HighscoreService.NUMBER_OF_RANKINGS; i++) {
            RankingEntity newRanking = new RankingEntity("id" + i, "playerName" + i, i, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
            expectedHighscore.add(newRanking);
        }
        Mockito.when(rankingRepository.getHighscore()).thenReturn(expectedHighscore);

        RankingEntity higherRanking = new RankingEntity("idNew", "playerNameNew", 1, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        Assert.assertTrue(highscoreService.isInHighscore(higherRanking));
    }

    @Test
    public void testIsNotInHighscoreLessEarnedPoints() {
        List<RankingEntity> expectedHighscore = new ArrayList<>();
        for (int i = 0; i < HighscoreService.NUMBER_OF_RANKINGS; i++) {
            RankingEntity newRanking = new RankingEntity("id" + i, "playerName" + i, 1 + i, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
            expectedHighscore.add(newRanking);
        }
        Mockito.when(rankingRepository.getHighscore()).thenReturn(expectedHighscore);

        RankingEntity lowerRanking = new RankingEntity("idNew", "playerNameNew", 0, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        Assert.assertFalse(highscoreService.isInHighscore(lowerRanking));
    }

    @Test
    public void testIsNotInHighscoreAlreadyHighscoreSizeReached() {
        List<RankingEntity> expectedHighscore = new ArrayList<>();
        for (int i = 0; i < HighscoreService.NUMBER_OF_RANKINGS; i++) {
            RankingEntity newRanking = new RankingEntity("id" + i, "playerName" + i, i, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
            expectedHighscore.add(newRanking);
        }
        Mockito.when(rankingRepository.getHighscore()).thenReturn(expectedHighscore);

        RankingEntity lowerRanking = new RankingEntity("idNew", "playerNameNew", 0, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
        Assert.assertFalse(highscoreService.isInHighscore(lowerRanking));
    }

    @Test
    public void saveNewRanking() {
        List<RankingEntity> expectedHighscore = new ArrayList<>();
        Mockito.when(rankingRepository.getHighscore()).thenReturn(expectedHighscore);

        RankingEntity newRanking = new RankingEntity("idNew", "playerNameNew", 30, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));

        Assert.assertTrue(highscoreService.saveNewRanking(newRanking));
        verify(rankingRepository, times(1)).save(newRanking);
        verify(rankingRepository, times(0)).delete(any());
    }

    @Test
    public void saveNewRankingAndDeleteLast() {
        List<RankingEntity> expectedHighscore = new ArrayList<>();
        for (int i = 0; i < HighscoreService.NUMBER_OF_RANKINGS; i++) {
            RankingEntity newRanking = new RankingEntity("id" + i, "playerName" + i, i, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
            expectedHighscore.add(newRanking);
        }
        Mockito.when(rankingRepository.getHighscore()).thenReturn(expectedHighscore);

        RankingEntity newRanking = new RankingEntity("idNew", "playerNameNew", 30, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));

        Assert.assertTrue(highscoreService.saveNewRanking(newRanking));
        verify(rankingRepository, times(1)).save(newRanking);
        verify(rankingRepository, times(1)).delete(any());
    }

    @Test
    public void saveNotBecauseOfTooManyEntries() {
        List<RankingEntity> expectedHighscore = new ArrayList<>();
        for (int i = 0; i < HighscoreService.NUMBER_OF_RANKINGS; i++) {
            RankingEntity newRanking = new RankingEntity("id" + i, "playerName" + i, 1 + i, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));
            expectedHighscore.add(newRanking);
        }
        Mockito.when(rankingRepository.getHighscore()).thenReturn(expectedHighscore);

        RankingEntity newRanking = new RankingEntity("idNew", "playerNameNew", 0, new PlayerStatisticsEntity(), new Date(2021, Calendar.MAY, 24));

        Assert.assertFalse(highscoreService.saveNewRanking(newRanking));
        verify(rankingRepository, times(0)).save(newRanking);
        verify(rankingRepository, times(0)).delete(any());
    }

}
