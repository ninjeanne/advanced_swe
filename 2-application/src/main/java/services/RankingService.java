package services;

import aggregates.BoardAggregate;
import domainservice.RankingDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.BoardRepository;
import valueobjects.RankingVO;

@Service
public class RankingService implements RankingDomainService {

    private final BoardRepository boardRepository;

    @Autowired
    public RankingService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public boolean saveNewRankingForBoard(RankingVO ranking, BoardAggregate boardAggregate) {
        return false;
    }

    @Override
    public boolean isTopRankingInBoard(RankingVO ranking, BoardAggregate boardAggregate) {
        return false;
    }
}
