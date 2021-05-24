package de.dhbw.dtos;

import org.junit.Assert;
import org.junit.Test;

public class PlanDTOBuilderTest {

    @Test
    public void testBuilding() {
        PlanDTO planDTO = PlanDTO.builder().height(12).width(14).build();
        Assert.assertEquals(12, planDTO.getHeight());
        Assert.assertEquals(14, planDTO.getWidth());
    }
}
