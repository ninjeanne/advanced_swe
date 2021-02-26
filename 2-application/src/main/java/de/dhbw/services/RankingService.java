package de.dhbw.services;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.domainservice.RankingDomainService;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.valueobjects.RankingVO;
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
    public boolean saveNewRankingForBoard(RankingVO ranking, String boardName) {
        BoardAggregate boardAggregate = boardRepository.getBoardByName(boardName);
        return boardAggregate.addNewTopRanking(ranking);
    }

    @Override
    public boolean isTopRankingInBoard(RankingVO ranking, String boardName) {
        BoardAggregate boardAggregate = boardRepository.getBoardByName(boardName);
        return boardAggregate.isNewTopRanking(ranking);
    }

    @Override
    public List<RankingVO> getTopRankingsForBoard(String boardName) {
        BoardAggregate boardAggregate = boardRepository.getBoardByName(boardName);
        return boardAggregate.getTopRankings();
    }

}
