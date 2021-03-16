package de.dhbw.repositories;

import de.dhbw.aggregates.BoardAggregate;

public interface BoardRepository {
    BoardAggregate getBoardByName(String name);

    void save(BoardAggregate boardAggregate);
}
