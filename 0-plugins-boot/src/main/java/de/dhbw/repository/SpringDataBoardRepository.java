package de.dhbw.repository;

import de.dhbw.entities.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataBoardRepository extends JpaRepository<BoardEntity, String> {
    BoardEntity getBoardAggregateByNameAsEntityId(String name);
}
