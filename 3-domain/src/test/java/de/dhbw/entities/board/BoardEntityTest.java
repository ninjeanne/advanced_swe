package de.dhbw.entities.board;

import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.PlanVO;
import de.dhbw.valueobjects.ProbabilityVO;
import de.dhbw.valueobjects.RadiusVO;
import org.junit.Assert;
import org.junit.Test;

public class BoardEntityTest {

    @Test
    public void testAddColleague() {
        ColleagueEntity colleagueEntity = new ColleagueEntity("hans");
        colleagueEntity.extendPath(new CoordinatesVO(0, 0));
        colleagueEntity.extendPath(new CoordinatesVO(0, 1));
        BoardEntity boardEntity = new BoardEntity("name1", new BoardLayoutEntity(new PlanVO(3, 3)),
                new BoardConfigurationEntity(new RadiusVO(2), new ProbabilityVO(0.5)));

        Assert.assertTrue(boardEntity.getColleagues().isEmpty());
        boardEntity.addColleague(colleagueEntity);
        Assert.assertEquals(1, boardEntity.getColleagues().size());
    }

    @Test
    public void testNotAddColleagueBecauseAlreadyExists() {
        ColleagueEntity colleagueEntity = new ColleagueEntity("hans");
        colleagueEntity.extendPath(new CoordinatesVO(0, 0));
        colleagueEntity.extendPath(new CoordinatesVO(0, 1));
        BoardEntity boardEntity = new BoardEntity("name1", new BoardLayoutEntity(new PlanVO(3, 3)),
                new BoardConfigurationEntity(new RadiusVO(2), new ProbabilityVO(0.5)));

        boardEntity.addColleague(colleagueEntity);
        Assert.assertEquals(1, boardEntity.getColleagues().size());
        boardEntity.addColleague(colleagueEntity);
        Assert.assertEquals(1, boardEntity.getColleagues().size());

        ColleagueEntity colleagueEntityWithSameName = new ColleagueEntity("hans");
        Assert.assertEquals(colleagueEntity, colleagueEntityWithSameName);
        boardEntity.addColleague(colleagueEntityWithSameName);
        Assert.assertEquals(1, boardEntity.getColleagues().size());
    }

    @Test
    public void testAddNotColleagueBecauseOutOfPlan() {
        ColleagueEntity colleagueEntity = new ColleagueEntity("hans");
        colleagueEntity.extendPath(new CoordinatesVO(3, 3));
        colleagueEntity.extendPath(new CoordinatesVO(3, 4));
        BoardEntity boardEntity = new BoardEntity("name1", new BoardLayoutEntity(new PlanVO(3, 3)),
                new BoardConfigurationEntity(new RadiusVO(2), new ProbabilityVO(0.5)));

        Assert.assertTrue(boardEntity.getColleagues().isEmpty());
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            boardEntity.addColleague(colleagueEntity);
        });
        Assert.assertTrue(boardEntity.getColleagues().isEmpty());
    }

    @Test
    public void testEquals() {
        BoardEntity boardEntity1 = new BoardEntity("name1", new BoardLayoutEntity(new PlanVO(3, 3)),
                new BoardConfigurationEntity(new RadiusVO(2), new ProbabilityVO(0.5)));
        BoardEntity boardEntity2 = new BoardEntity("name1", new BoardLayoutEntity(new PlanVO(3, 24)),
                new BoardConfigurationEntity(new RadiusVO(3), new ProbabilityVO(0.6)));
        Assert.assertEquals(boardEntity1, boardEntity2);
        Assert.assertEquals(boardEntity2, boardEntity1);
    }

    @Test
    public void testNotEquals() {
        BoardEntity boardEntity1 = new BoardEntity("name1", new BoardLayoutEntity(new PlanVO(3, 3)),
                new BoardConfigurationEntity(new RadiusVO(2), new ProbabilityVO(0.5)));
        BoardEntity boardEntity2 = new BoardEntity("name2", new BoardLayoutEntity(new PlanVO(3, 3)),
                new BoardConfigurationEntity(new RadiusVO(2), new ProbabilityVO(0.5)));
        Assert.assertNotEquals(boardEntity1, boardEntity2);
        Assert.assertNotEquals(boardEntity2, boardEntity1);
    }
}
