package de.dhbw.persistence.board;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardRepositoryBridge implements BoardRepository {

    private final SpringDataBoardRepository springDataBoardRepository;

    @Autowired
    public BoardRepositoryBridge(SpringDataBoardRepository springDataBoardRepository) {
        this.springDataBoardRepository = springDataBoardRepository;
    }

    @Override
    public BoardAggregate getBoardByName(String name) {
        return springDataBoardRepository.getBoardAggregateByName(name);
    }

    @Override
    public void save(BoardAggregate boardAggregate) {
        springDataBoardRepository.save(boardAggregate);
    }
}
