package de.dhbw.mapper;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.dtos.BoardDTO;
import de.dhbw.valueobjects.ProbabilityVO;
import de.dhbw.valueobjects.RadiusVO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BoardMapper implements Function<BoardDTO, BoardAggregate> {

    private BoardAggregate map(BoardDTO boardDTO) {
        return new BoardAggregate(boardDTO.getId(), boardDTO.getName(), boardDTO.getPlan(), new RadiusVO(boardDTO.getColleagueRadius()),
                new ProbabilityVO(boardDTO.getProbability()));
    }

    @Override
    public BoardAggregate apply(BoardDTO boardDTO) {
        return map(boardDTO);
    }

    public BoardDTO apply(BoardAggregate boardAggregate) {
        return BoardDTO.builder().id(boardAggregate.getUuid()).name(boardAggregate.getName()).build();
    }
}
