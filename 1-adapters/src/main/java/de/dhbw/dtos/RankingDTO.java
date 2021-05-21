package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

/**
 * Design Pattern: Builder
 */
@Getter
@AllArgsConstructor
public class RankingDTO {

    private final String name;
    private final int earned_points;
    private final Map<String, Integer> statistics;
    private final int total;
    private final Date date;

    public static RankingDTO.RankingDTOBuilder builder() {
        return new RankingDTO.RankingDTOBuilder();
    }

    public static class RankingDTOBuilder {
        private String name;
        private int earned_points;
        private Map<String, Integer> statistics;
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

        public RankingDTO.RankingDTOBuilder statistics(Map<String, Integer> statistics) {
            this.statistics = statistics;
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
            return new RankingDTO(this.name, this.earned_points, this.statistics, this.total, this.date);
        }

        public String toString() {
            return "RankingDTO.RankingDTOBuilder(name=" + this.name + ", earned_points=" + this.earned_points + ", statistics=" + this.statistics + ", total=" + this.total + ", date=" + this.date + ")";
        }
    }
}
