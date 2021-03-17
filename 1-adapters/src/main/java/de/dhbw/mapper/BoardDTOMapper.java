package de.dhbw.mapper;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.aggregates.ColleagueAggregate;
import de.dhbw.dtos.BoardDTO;
import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlanDTO;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class BoardDTOMapper implements Function<BoardAggregate, BoardDTO> {

    private BoardDTO map(BoardAggregate boardAggregate) {
        List<CoordinatesDTO> obstacles = new ArrayList<>();
        for (CoordinatesVO obstacle : boardAggregate.getObstacles()) {
            obstacles.add(CoordinatesDTO.builder()
                    .x(obstacle.getX())
                    .y(obstacle.getY())
                    .build());
        }
        List<CoordinatesDTO> colleagues = new ArrayList<>();
        for (ColleagueAggregate colleagueAggregate : boardAggregate.getColleagues()) {
            colleagues.add(CoordinatesDTO.builder()
                    .x(colleagueAggregate.getPosition().getX())
                    .y(colleagueAggregate.getPosition().getY())
                    .build());
        }
        CoordinatesDTO vaccination = null;
        if(boardAggregate.getVaccination() != null){
        vaccination = CoordinatesDTO.builder()
                .y(boardAggregate.getVaccination().getY())
                .x(boardAggregate.getVaccination().getX())
                .build();
        }

        CoordinatesDTO workItem = null;
        if(boardAggregate.getWorkItem() != null){
        workItem = CoordinatesDTO.builder()
                .y(boardAggregate.getWorkItem().getY())
                .x(boardAggregate.getWorkItem().getX())
                .build();
        }

       return BoardDTO.builder()
               .name(boardAggregate.getName())
               .obstacles(obstacles)
               .colleagues(colleagues)
               .infectProbability(boardAggregate.getInfectProbability().getProbability())
               .colleagueRadius(boardAggregate.getColleagueRadius().getRadius())
               .workItem(workItem)
               .vaccination(vaccination)
               .plan(PlanDTO.builder()
                       .height(boardAggregate.getPlan().getHeight())
                       .width(boardAggregate.getPlan().getWidth())
                       .build())
               .build();
    }

    @Override
    public BoardDTO apply(BoardAggregate boardAggregate) {
        return map(boardAggregate);
    }
}
