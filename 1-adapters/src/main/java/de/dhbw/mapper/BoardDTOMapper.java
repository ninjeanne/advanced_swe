package de.dhbw.mapper;

import de.dhbw.dtos.BoardDTO;
import de.dhbw.dtos.CoordinatesDTO;
import de.dhbw.dtos.PlanDTO;
import de.dhbw.entities.board.BoardEntity;
import de.dhbw.entities.gameobject.GameObjectEntity;
import de.dhbw.helper.ColleagueMovement;
import de.dhbw.services.BoardService;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, CoordinatesDTO> gameObjects = new HashMap<>();
        for (GameObjectEntity gameObjectEntity : boardService.getGameObjects()) {
            gameObjects.put(gameObjectEntity.getClass().getSimpleName(),
                    CoordinatesDTO.builder()
                            .y(gameObjectEntity.getCoordinatesVO().getY())
                            .x(gameObjectEntity.getCoordinatesVO().getX())
                            .build());
        }

       return BoardDTO.builder().name(boardEntity.getNameAsEntityId())
               .obstacles(obstacles)
               .colleagues(colleaguePosition)
               .infectProbability(boardEntity.getBoardConfiguration().getInfectProbability().getProbability())
               .colleagueRadius(boardEntity.getBoardConfiguration().getColleagueRadius().getRadius())
               .gameObjects(gameObjects)
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
