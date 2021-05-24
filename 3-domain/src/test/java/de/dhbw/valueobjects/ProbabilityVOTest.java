package de.dhbw.valueobjects;

import org.junit.Assert;
import org.junit.Test;

public class ProbabilityVOTest {

    @Test
    public void testValidConstruction() {
        ProbabilityVO probabilityVO = new ProbabilityVO(1, 0.5);
        Assert.assertEquals(0.5, probabilityVO.getProbability(), 0);
        Assert.assertEquals(1, probabilityVO.getId());

        ProbabilityVO probabilityVO2 = new ProbabilityVO(2, 0.2);
        Assert.assertEquals(0.2, probabilityVO2.getProbability(), 0);
        Assert.assertEquals(2, probabilityVO2.getId());
    }

    @Test
    public void testInvalidConstruction() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new ProbabilityVO(1, 1.0);
        });

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new ProbabilityVO(1, -1);
        });

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new ProbabilityVO(1, 0);
        });
    }

    @Test
    public void testEquals() {
        ProbabilityVO probabilityVO = new ProbabilityVO(0.4);
        ProbabilityVO probabilityVO2 = new ProbabilityVO(0.4);

        Assert.assertEquals(probabilityVO2, probabilityVO);
        Assert.assertEquals(probabilityVO, probabilityVO2);
    }

    @Test
    public void testNotEquals() {
        ProbabilityVO probabilityVO = new ProbabilityVO(0.4);
        ProbabilityVO probabilityVO2 = new ProbabilityVO(0.2);

        Assert.assertNotEquals(probabilityVO2, probabilityVO);
        Assert.assertNotEquals(probabilityVO, probabilityVO2);
    }

}
