package de.dhbw.helper;

import de.dhbw.entities.board.ColleagueEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ForwardAndBackMovementTest {

    private ForwardAndBackMovement forwardAndBackMovement;

    @Before
    public void setUp() {
        ColleagueEntity colleagueEntity = new ColleagueEntity("hans");
        colleagueEntity.extendPath(new CoordinatesVO(0, 0));
        colleagueEntity.extendPath(new CoordinatesVO(0, 1));
        forwardAndBackMovement = new ForwardAndBackMovement(colleagueEntity);
    }

    @Test
    public void testFirstCurrentPosition() {
        Assert.assertEquals(new CoordinatesVO(0, 0), forwardAndBackMovement.getCurrentPosition());
    }

    @Test
    public void testNextPosition() {
        Assert.assertEquals(new CoordinatesVO(0, 1), forwardAndBackMovement.nextPosition());
        Assert.assertEquals(new CoordinatesVO(0, 1), forwardAndBackMovement.getCurrentPosition());
    }

    @Test
    public void testBackPosition() {
        forwardAndBackMovement.nextPosition();
        Assert.assertEquals(new CoordinatesVO(0, 0), forwardAndBackMovement.nextPosition());
        Assert.assertEquals(new CoordinatesVO(0, 0), forwardAndBackMovement.getCurrentPosition());
    }

    @Test
    public void testBackAndForwardPosition() {
        Assert.assertEquals(new CoordinatesVO(0, 0), forwardAndBackMovement.getCurrentPosition());
        Assert.assertEquals(new CoordinatesVO(0, 1), forwardAndBackMovement.nextPosition());
        Assert.assertEquals(new CoordinatesVO(0, 1), forwardAndBackMovement.getCurrentPosition());
        Assert.assertEquals(new CoordinatesVO(0, 0), forwardAndBackMovement.nextPosition());
        Assert.assertEquals(new CoordinatesVO(0, 0), forwardAndBackMovement.getCurrentPosition());
        Assert.assertEquals(new CoordinatesVO(0, 1), forwardAndBackMovement.nextPosition());
        Assert.assertEquals(new CoordinatesVO(0, 1), forwardAndBackMovement.getCurrentPosition());
    }

    @Test
    public void testGetColleagueEntity() {
        ColleagueEntity colleagueEntity = new ColleagueEntity("AFancyCoolName");
        forwardAndBackMovement = new ForwardAndBackMovement(colleagueEntity);
        Assert.assertEquals(colleagueEntity, forwardAndBackMovement.getColleagueEntity());
    }

}
