package de.dhbw.valueobjects;

import org.junit.Assert;
import org.junit.Test;

public class PlanVOTest {

    @Test
    public void testValidConstruction() {
        PlanVO planVO = new PlanVO(1, 1);
        Assert.assertEquals(1, planVO.getHeight());
        Assert.assertEquals(1, planVO.getWidth());

        PlanVO planVO1 = new PlanVO(25, 42);
        Assert.assertEquals(25, planVO1.getHeight());
        Assert.assertEquals(42, planVO1.getWidth());
    }

    @Test
    public void testInvalidConstruction() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new PlanVO(0, 1);
        });

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new PlanVO(1, 0);
        });

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new PlanVO(-5, -1);
        });
    }

    @Test
    public void testEquals() {
        PlanVO planVO1 = new PlanVO(5, 6);
        PlanVO planVO2 = new PlanVO(5, 6);

        Assert.assertEquals(planVO1, planVO2);
        Assert.assertEquals(planVO1, planVO1);
        Assert.assertEquals(planVO2, planVO1);
    }

    @Test
    public void testNotEqualsHeight() {
        PlanVO planVO1 = new PlanVO(1, 6);
        PlanVO planVO2 = new PlanVO(5, 6);

        Assert.assertNotEquals(planVO1, planVO2);
        Assert.assertNotEquals(planVO2, planVO1);
    }

    @Test
    public void testNotEqualsWidth() {
        PlanVO planVO1 = new PlanVO(5, 6);
        PlanVO planVO2 = new PlanVO(5, 3);

        Assert.assertNotEquals(planVO1, planVO2);
        Assert.assertNotEquals(planVO2, planVO1);
    }

    @Test
    public void testNotEqualsHeightAndWidth() {
        PlanVO planVO1 = new PlanVO(1, 6);
        PlanVO planVO2 = new PlanVO(5, 4);

        Assert.assertNotEquals(planVO1, planVO2);
        Assert.assertNotEquals(planVO2, planVO1);
    }
}
