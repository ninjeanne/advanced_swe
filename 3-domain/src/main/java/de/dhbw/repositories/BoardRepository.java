package de.dhbw.repositories;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.valueobjects.RankingVO;

import java.util.List;

public interface BoardRepository { //todo Vorgabe der Methoden für spätere JPA
    BoardAggregate getBoardByName(String name);
    void save(BoardAggregate boardAggregate);

    void delete(BoardAggregate boardAggregate);

    List<RankingVO> getTopRankingsByBoardName(String name);
}
