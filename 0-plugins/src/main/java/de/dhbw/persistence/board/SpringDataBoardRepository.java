package de.dhbw.persistence.board;

import de.dhbw.aggregates.BoardAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataBoardRepository extends JpaRepository<BoardAggregate, String> {
    BoardAggregate getBoardAggregateByName(String name);
}
