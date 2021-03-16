package de.dhbw.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class RankingDTO {

    private String name;
    private int earned_points;
    private int workItems;
    private int total;
    private Date date;
}
