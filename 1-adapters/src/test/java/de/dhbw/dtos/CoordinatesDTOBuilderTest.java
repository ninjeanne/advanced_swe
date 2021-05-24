package de.dhbw.dtos;

import org.junit.Assert;
import org.junit.Test;

public class CoordinatesDTOBuilderTest {

    @Test
    public void testBuilding() {
        CoordinatesDTO coordinatesDTO = CoordinatesDTO.builder().x(3).y(5).build();
        Assert.assertEquals(3, coordinatesDTO.getX());
        Assert.assertEquals(5, coordinatesDTO.getY());
    }
}
