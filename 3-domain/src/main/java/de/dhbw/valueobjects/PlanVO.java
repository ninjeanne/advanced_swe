package de.dhbw.valueobjects;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public final class PlanVO {

    @Column
    private final int height;
    @Column
    private final int width;

    public PlanVO(int height, int width) {
        if (isValid(height) && isValid(width)) {
            this.height = height;
            this.width = width;
        } else {
            throw new IllegalArgumentException("Plan is invalid for height " + height + " and width " + width);
        }
    }

    @SuppressWarnings("unused")
    public PlanVO() {
        this.width = 0;
        this.height = 0;
    }

    private boolean isValid(int value) {
        return value >= 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanVO) {
            PlanVO plan = (PlanVO) obj;
            return this.height == plan.height && this.width == plan.width;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width);
    }
}
