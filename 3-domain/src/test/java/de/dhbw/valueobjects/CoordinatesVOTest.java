package de.dhbw.valueobjects;

import org.junit.Assert;
import org.junit.Test;

public class CoordinatesVOTest {

    @Test
    public void testValidConstruction() {
        CoordinatesVO coordinatesVO = new CoordinatesVO(0, 0);
        Assert.assertEquals(0, coordinatesVO.getX());
        Assert.assertEquals(0, coordinatesVO.getY());

        CoordinatesVO coordinatesVO2 = new CoordinatesVO(25, 30);
        Assert.assertEquals(25, coordinatesVO2.getX());
        Assert.assertEquals(30, coordinatesVO2.getY());
    }

    @Test
    public void testInvalidConstruction() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new CoordinatesVO(-1, 0);
        });

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new CoordinatesVO(0, -1);
        });

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new CoordinatesVO(-5, -1);
        });
    }

    @Test
    public void testEquals() {
        CoordinatesVO coordinates1 = new CoordinatesVO(5, 6);
        CoordinatesVO coordinates2 = new CoordinatesVO(5, 6);

        Assert.assertEquals(coordinates1, coordinates2);
        Assert.assertEquals(coordinates2, coordinates1);
    }

    @Test
    public void testNotEqualsX() {
        CoordinatesVO coordinates1 = new CoordinatesVO(1, 6);
        CoordinatesVO coordinates2 = new CoordinatesVO(5, 6);

        Assert.assertNotEquals(coordinates1, coordinates2);
        Assert.assertNotEquals(coordinates2, coordinates1);
    }

    @Test
    public void testNotEqualsY() {
        CoordinatesVO coordinates1 = new CoordinatesVO(5, 6);
        CoordinatesVO coordinates2 = new CoordinatesVO(5, 1);

        Assert.assertNotEquals(coordinates1, coordinates2);
        Assert.assertNotEquals(coordinates2, coordinates1);
    }

    @Test
    public void testNotEqualsXandY() {
        CoordinatesVO coordinates1 = new CoordinatesVO(1, 6);
        CoordinatesVO coordinates2 = new CoordinatesVO(5, 2);

        Assert.assertNotEquals(coordinates1, coordinates2);
        Assert.assertNotEquals(coordinates2, coordinates1);
    }
}
