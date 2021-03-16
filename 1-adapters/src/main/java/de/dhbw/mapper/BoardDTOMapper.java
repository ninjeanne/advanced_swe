package de.dhbw.mapper;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.dtos.BoardDTO;
import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlanDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BoardDTOMapper implements Function<BoardAggregate, BoardDTO> {

    private BoardDTO map(BoardAggregate boardAggregate) {
       return BoardDTO.builder()
               .name(boardAggregate.getName())
               .infectProbability(boardAggregate.getInfectProbability().getProbability())
               .colleagueRadius(boardAggregate.getColleagueRadius().getRadius())
               .workItem(CoordinatesDTO.builder()
                       .y(boardAggregate.getWorkItem().getY())
                       .x(boardAggregate.getWorkItem().getX())
                       .build())
               .vaccination(CoordinatesDTO.builder()
                       .y(boardAggregate.getVaccination().getY())
                       .x(boardAggregate.getVaccination().getX())
                       .build())
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
