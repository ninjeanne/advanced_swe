package de.dhbw.services;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.domainservice.RankingDomainService;
import de.dhbw.entities.RankingEntity;
import de.dhbw.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        BoardAggregate boardAggregate = boardRepository.getBoardByName(boardName);
        if (isTopRankingInBoard(ranking, boardName)) {
            boardAggregate.addNewTopRanking(ranking);
            boardRepository.save(boardAggregate);
            return true;
        }
        return false;
    }

    @Override
    public boolean isTopRankingInBoard(RankingEntity ranking, String boardName) {
        BoardAggregate boardAggregate = boardRepository.getBoardByName(boardName);
        return boardAggregate.isNewTopRanking(ranking);
    }

    @Override
    public List<RankingEntity> getTopRankingsForBoard(String boardName) {
        BoardAggregate boardAggregate = boardRepository.getBoardByName(boardName);
        return boardAggregate.getTopRankings();
    }

}
