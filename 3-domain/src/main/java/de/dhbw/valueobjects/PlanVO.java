package de.dhbw.valueobjects;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Builder
@Getter
@Embeddable
public final class PlanVO {

    @Column
    private final int length;
    @Column
    private final int width;

    public PlanVO(int length, int width) {
        if (isValid(length) && isValid(width)) {
            this.length = length;
            this.width = width;
        } else {
            throw new IllegalArgumentException("Plan is invalid for length " + length + " and width " + width);
        }
    }

    public PlanVO() {
        this.width = 0;
        this.length = 0;
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
