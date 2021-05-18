package de.dhbw.repositories;

import de.dhbw.entities.board.BoardEntity;

public interface BoardRepository {
    BoardEntity getBoardByName(String name);

    void save(BoardEntity boardEntity);
}
