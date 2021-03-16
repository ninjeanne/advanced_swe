package de.dhbw.mapper;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.dtos.BoardDTO;
import de.dhbw.valueobjects.PlanVO;
import de.dhbw.valueobjects.ProbabilityVO;
import de.dhbw.valueobjects.RadiusVO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BoardMapper implements Function<BoardDTO, BoardAggregate> {

    private BoardAggregate map(BoardDTO boardDTO) {
        PlanVO planVO = new PlanVO(boardDTO.getPlan().getHeight(), boardDTO.getPlan().getWidth());
        return new BoardAggregate(boardDTO.getId(), boardDTO.getName(), planVO, new RadiusVO(boardDTO.getColleagueRadius()),
                new ProbabilityVO(boardDTO.getInfectProbability()));
    }

    @Override
    public BoardAggregate apply(BoardDTO boardDTO) {
        return map(boardDTO);
    }
}
