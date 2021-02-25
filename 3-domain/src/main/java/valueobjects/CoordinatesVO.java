package valueobjects;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class CoordinatesVO {

    private final int x;
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
