package de.dhbw.valueobjects;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Builder
@Entity
public final class CoordinatesVO {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private final int x;
    @Column
    private final int y;

    @SuppressWarnings("unused")
    public CoordinatesVO() {
        this.x = 0;
        this.y = 0;
    }

    public CoordinatesVO(long id, int x, int y) {
        this.id = id;
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
