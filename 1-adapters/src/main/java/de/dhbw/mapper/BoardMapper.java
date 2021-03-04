package de.dhbw.mapper;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.dtos.BoardDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BoardMapper implements Function<BoardDTO, BoardAggregate> {

    private BoardAggregate map(BoardDTO boardDTO) {
        return BoardAggregate.builder().name(boardDTO.getName()).name(boardDTO.getName()).build();
    }

    @Override
    public BoardAggregate apply(BoardDTO boardDTO) {
        return map(boardDTO);
    }

    public BoardDTO apply(BoardAggregate boardAggregate) {
        return BoardDTO.builder().id(boardAggregate.getUuid()).name(boardAggregate.getName()).build();
    }
}
