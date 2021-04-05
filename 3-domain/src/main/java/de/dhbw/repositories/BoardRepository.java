package de.dhbw.repositories;

import de.dhbw.entities.BoardEntity;

public interface BoardRepository {
    BoardEntity getBoardByName(String name);

    void save(BoardEntity boardEntity);
}
