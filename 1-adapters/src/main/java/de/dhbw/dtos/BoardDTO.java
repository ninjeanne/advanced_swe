package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Design Pattern: Builder
 */
@Getter
@AllArgsConstructor
public class BoardDTO {
    private final String name;
    private final Map<String, CoordinatesDTO> gameObjects;
    private final List<CoordinatesDTO> obstacles;
    private final List<CoordinatesDTO> colleagues;
    private final PlanDTO plan;
    private final double infectProbability;
    private final int colleagueRadius;

    public static BoardDTO.BoardDTOBuilder builder() {
        return new BoardDTO.BoardDTOBuilder();
    }

    public static class BoardDTOBuilder {
        private String name;
        private Map<String, CoordinatesDTO> gameObjects;
        private List<CoordinatesDTO> obstacles;
        private List<CoordinatesDTO> colleagues;
        private PlanDTO plan;
        private double infectProbability;
        private int colleagueRadius;

        public BoardDTO.BoardDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BoardDTO.BoardDTOBuilder gameObjects(Map<String, CoordinatesDTO> gameObjects) {
            this.gameObjects = gameObjects;
            return this;
        }

        public BoardDTO.BoardDTOBuilder obstacles(List<CoordinatesDTO> obstacles) {
            this.obstacles = obstacles;
            return this;
        }

        public BoardDTO.BoardDTOBuilder colleagues(List<CoordinatesDTO> colleagues) {
            this.colleagues = colleagues;
            return this;
        }

        public BoardDTO.BoardDTOBuilder plan(PlanDTO plan) {
            this.plan = plan;
            return this;
        }

        public BoardDTO.BoardDTOBuilder infectProbability(double infectProbability) {
            this.infectProbability = infectProbability;
            return this;
        }

        public BoardDTO.BoardDTOBuilder colleagueRadius(int colleagueRadius) {
            this.colleagueRadius = colleagueRadius;
            return this;
        }

        public BoardDTO build() {
            return new BoardDTO(this.name, this.gameObjects, this.obstacles, this.colleagues, this.plan, this.infectProbability,
                    this.colleagueRadius);
        }
    }
}
