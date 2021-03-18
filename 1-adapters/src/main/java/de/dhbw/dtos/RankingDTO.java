package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Design Pattern: Builder
 */
@Data
@AllArgsConstructor
public class RankingDTO {

    private String name;
    private int earned_points;
    private int workItems;
    private int total;
    private Date date;

    public static RankingDTO.RankingDTOBuilder builder() {
        return new RankingDTO.RankingDTOBuilder();
    }

    public static class RankingDTOBuilder {
        private String name;
        private int earned_points;
        private int workItems;
        private int total;
        private Date date;

        RankingDTOBuilder() {
        }

        public RankingDTO.RankingDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RankingDTO.RankingDTOBuilder earned_points(int earned_points) {
            this.earned_points = earned_points;
            return this;
        }

        public RankingDTO.RankingDTOBuilder workItems(int workItems) {
            this.workItems = workItems;
            return this;
        }

        public RankingDTO.RankingDTOBuilder total(int total) {
            this.total = total;
            return this;
        }

        public RankingDTO.RankingDTOBuilder date(Date date) {
            this.date = date;
            return this;
        }

        public RankingDTO build() {
            return new RankingDTO(this.name, this.earned_points, this.workItems, this.total, this.date);
        }

        public String toString() {
            return "RankingDTO.RankingDTOBuilder(name=" + this.name + ", earned_points=" + this.earned_points + ", workItems=" + this.workItems + ", total=" + this.total + ", date=" + this.date + ")";
        }
    }
}
