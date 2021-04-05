package de.dhbw.services;

import de.dhbw.domainservice.RankingDomainService;
import de.dhbw.entities.BoardEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RankingService implements RankingDomainService {

    private final BoardRepository boardRepository;

    @Autowired
    public RankingService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public boolean saveNewRankingForBoard(RankingEntity ranking, String boardName) {
        BoardEntity boardEntity = boardRepository.getBoardByName(boardName);
        if (isTopRankingInBoard(ranking, boardName)) {
            if (boardEntity.addNewTopRanking(ranking)) {
                boardRepository.save(boardEntity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTopRankingInBoard(RankingEntity ranking, String boardName) {
        BoardEntity boardEntity = boardRepository.getBoardByName(boardName);
        return boardEntity.isNewTopRanking(ranking);
    }

    @Override
    public List<RankingEntity> getTopRankingsForBoard(String boardName) {
        BoardEntity boardEntity = boardRepository.getBoardByName(boardName);
        List<RankingEntity> entities = boardEntity.getTopRankings();
        entities.sort(Comparator.comparing(RankingEntity::getTotal).reversed());
        return entities;
    }

}
