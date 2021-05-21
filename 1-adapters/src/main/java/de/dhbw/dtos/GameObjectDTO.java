package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Design Pattern: Builder
 */
@AllArgsConstructor
@Getter
public class GameObjectDTO {
    private final String id;
    private final CoordinatesDTO coordinatesDTO;

    public static GameObjectDTO.GameObjectDTOBuilder builder() {
        return new GameObjectDTO.GameObjectDTOBuilder();
    }

    public static class GameObjectDTOBuilder {
        private String id;
        private CoordinatesDTO coordinatesDTO;

        public GameObjectDTO.GameObjectDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public GameObjectDTO.GameObjectDTOBuilder coordinatesDTO(CoordinatesDTO coordinatesDTO) {
            this.coordinatesDTO = coordinatesDTO;
            return this;
        }

        public GameObjectDTO build() {
            return new GameObjectDTO(this.id, this.coordinatesDTO);
        }

        public String toString() {
            return "GameObjectDTO.GameObjectDTOBuilder(id=" + this.id + ", coordinatesDTO=" + this.coordinatesDTO + ")";
        }
    }
}
