package de.dhbw.mapper;

import de.dhbw.dtos.BoardDTO;
import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlanDTO;
import de.dhbw.entities.BoardEntity;
import de.dhbw.entities.GameObject;
import de.dhbw.entities.Vaccination;
import de.dhbw.entities.WorkItem;
import de.dhbw.helper.ColleagueMovement;
import de.dhbw.services.BoardService;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class BoardDTOMapper implements Function<BoardEntity, BoardDTO> {

    private final BoardService boardService;

    @Autowired
    public BoardDTOMapper(BoardService boardService) {
        this.boardService = boardService;
    }

    private BoardDTO map(BoardEntity boardEntity) {
        List<CoordinatesDTO> obstacles = new ArrayList<>();
        for (CoordinatesVO obstacle : boardEntity.getBoardLayout().getObstacles()) {
            obstacles.add(CoordinatesDTO.builder()
                    .x(obstacle.getX())
                    .y(obstacle.getY())
                    .build());
        }
        List<CoordinatesDTO> colleaguePosition = new ArrayList<>();
        for (ColleagueMovement colleagueMovement : boardService.getColleagueMovements()) {
            colleaguePosition.add(CoordinatesDTO.builder()
                    .x(colleagueMovement.getCurrentPosition().getX())
                    .y(colleagueMovement.getCurrentPosition().getY())
                    .build());
        }
        CoordinatesDTO vaccination = null;
        GameObject vaccinationGO = boardService.getGameObjects().get(Vaccination.class);
        if(vaccinationGO != null){
        vaccination = CoordinatesDTO.builder()
                .y(vaccinationGO.getCoordinatesVO().getY())
                .x(vaccinationGO.getCoordinatesVO().getX())
                .build();
        }

        CoordinatesDTO workItem = null;
        GameObject workItemGO = boardService.getGameObjects().get(WorkItem.class);
        if(workItemGO != null){
        workItem = CoordinatesDTO.builder()
                .y(workItemGO.getCoordinatesVO().getY())
                .x(workItemGO.getCoordinatesVO().getX())
                .build();
        }

       return BoardDTO.builder()
               .name(boardEntity.getName())
               .obstacles(obstacles)
               .colleagues(colleaguePosition)
               .infectProbability(boardEntity.getBoardConfiguration().getInfectProbability().getProbability())
               .colleagueRadius(boardEntity.getBoardConfiguration().getColleagueRadius().getRadius())
               .workItem(workItem)
               .vaccination(vaccination)
               .plan(PlanDTO.builder()
                       .height(boardEntity.getBoardLayout().getPlan().getHeight())
                       .width(boardEntity.getBoardLayout().getPlan().getWidth())
                       .build())
               .build();
    }

    @Override
    public BoardDTO apply(BoardEntity boardEntity) {
        return map(boardEntity);
    }
}
