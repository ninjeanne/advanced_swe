package de.dhbw.valueobjects;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Getter
@IdClass(CoordinatesVO.class)
@Entity
public final class CoordinatesVO implements Serializable {

    @Id
    private final int x;
    @Id
    private final int y;

    public CoordinatesVO(int x, int y) {
        if (isValid(x) && isValid(y)) {
            this.x = x;
            this.y = y;
        } else {
            throw new IllegalArgumentException("Coordinates are invalid for x " + x + " and y " + y);
        }
    }

    private boolean isValid(int value) {
        return value >= 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CoordinatesVO) {
            CoordinatesVO coordinates = (CoordinatesVO) obj;
            return this.x == coordinates.getX() && this.y == coordinates.getY();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
