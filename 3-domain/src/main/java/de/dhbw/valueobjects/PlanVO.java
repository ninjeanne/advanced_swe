package de.dhbw.valueobjects;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
public final class PlanVO {

    private final int length;
    private final int width;

    public PlanVO(int length, int width) {
        if (isValid(length) && isValid(width)) {
            this.length = length;
            this.width = width;
        } else {
            throw new IllegalArgumentException("Plan is invalid for length " + length + " and width " + width);
        }
    }

    private boolean isValid(int value) {
        return value >= 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanVO) {
            PlanVO plan = (PlanVO) obj;
            return this.length == plan.length && this.width == plan.width;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, width);
    }
}
