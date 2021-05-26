package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Design Pattern: Builder
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesDTO {
    private int x;
    private int y;

    public static CoordinatesDTO.CoordinatesDTOBuilder builder() {
        return new CoordinatesDTO.CoordinatesDTOBuilder();
    }

    public static class CoordinatesDTOBuilder {
        private int x;
        private int y;

        CoordinatesDTOBuilder() {
        }

        public CoordinatesDTO.CoordinatesDTOBuilder x(int x) {
            this.x = x;
            return this;
        }

        public CoordinatesDTO.CoordinatesDTOBuilder y(int y) {
            this.y = y;
            return this;
        }

        public CoordinatesDTO build() {
            return new CoordinatesDTO(this.x, this.y);
        }
    }
}
