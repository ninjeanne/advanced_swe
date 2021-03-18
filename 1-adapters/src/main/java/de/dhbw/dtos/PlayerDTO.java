package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Design Pattern: Builder
 */
@Data
@AllArgsConstructor
public class PlayerDTO {
    private String name;
    private CoordinatesDTO position;
    private int lifePoints;
    private int workItems;

    public static PlayerDTO.PlayerDTOBuilder builder() {
        return new PlayerDTO.PlayerDTOBuilder();
    }

    public static class PlayerDTOBuilder {
        private String name;
        private CoordinatesDTO position;
        private int lifePoints;
        private int workItems;

        PlayerDTOBuilder() {
        }

        public PlayerDTO.PlayerDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PlayerDTO.PlayerDTOBuilder position(CoordinatesDTO position) {
            this.position = position;
            return this;
        }

        public PlayerDTO.PlayerDTOBuilder lifePoints(int lifePoints) {
            this.lifePoints = lifePoints;
            return this;
        }

        public PlayerDTO.PlayerDTOBuilder workItems(int workItems) {
            this.workItems = workItems;
            return this;
        }

        public PlayerDTO build() {
            return new PlayerDTO(this.name, this.position, this.lifePoints, this.workItems);
        }

        public String toString() {
            return "PlayerDTO.PlayerDTOBuilder(name=" + this.name + ", position=" + this.position + ", lifePoints=" + this.lifePoints + ", workItems="
                    + this.workItems + ")";
        }
    }
}
