package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Design Pattern: Builder
 */
@Data
@AllArgsConstructor
public class BoardDTO {
    private String name;
    private CoordinatesDTO vaccination;
    private CoordinatesDTO workItem;
    private List<CoordinatesDTO> obstacles;
    private List<CoordinatesDTO> colleagues;
    private PlanDTO plan;
    private double infectProbability;
    private int colleagueRadius;

    public static BoardDTO.BoardDTOBuilder builder() {
        return new BoardDTO.BoardDTOBuilder();
    }

    public static class BoardDTOBuilder {
        private String name;
        private CoordinatesDTO vaccination;
        private CoordinatesDTO workItem;
        private List<CoordinatesDTO> obstacles;
        private List<CoordinatesDTO> colleagues;
        private PlanDTO plan;
        private double infectProbability;
        private int colleagueRadius;

        public BoardDTO.BoardDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BoardDTO.BoardDTOBuilder vaccination(CoordinatesDTO vaccination) {
            this.vaccination = vaccination;
            return this;
        }

        public BoardDTO.BoardDTOBuilder workItem(CoordinatesDTO workItem) {
            this.workItem = workItem;
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
            return new BoardDTO(this.name, this.vaccination, this.workItem, this.obstacles, this.colleagues, this.plan, this.infectProbability,
                    this.colleagueRadius);
        }

        public String toString() {
            return "BoardDTO.BoardDTOBuilder(name=" + this.name + ", vaccination=" + this.vaccination + ", workItem=" + this.workItem + ", obstacles="
                    + this.obstacles + ", colleagues=" + this.colleagues + ", plan=" + this.plan + ", infectProbability=" + this.infectProbability
                    + ", colleagueRadius=" + this.colleagueRadius + ")";
        }
    }
}
