package de.dhbw.valueobjects;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Entity
public final class RadiusVO {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private final int radius;

    @SuppressWarnings("unused")
    public RadiusVO() {
        this.radius = 0;
    }

    public RadiusVO(int radius) {
        if (isValid(radius)) {
            this.radius = radius;
        } else {
            throw new IllegalArgumentException("Radius is invalid for " + radius);
        }
    }

    private boolean isValid(int value) {
        return value > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RadiusVO) {
            RadiusVO radiusVO = (RadiusVO) obj;
            return this.radius == radiusVO.getRadius();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }
}
