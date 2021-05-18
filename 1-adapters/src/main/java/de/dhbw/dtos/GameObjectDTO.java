package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Design Pattern: Builder
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameObjectDTO {
    private String id;
    private CoordinatesDTO coordinatesDTO;

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
