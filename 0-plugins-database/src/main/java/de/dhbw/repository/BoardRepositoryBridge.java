package de.dhbw.repository;

import de.dhbw.entities.BoardEntity;
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
    public BoardEntity getBoardByName(String name) {
        return springDataBoardRepository.getBoardAggregateByName(name);
    }

    @Override
    public void save(BoardEntity boardEntity) {
        springDataBoardRepository.save(boardEntity);
    }
}
