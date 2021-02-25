package domainservice;

import aggregates.BoardAggregate;
import aggregates.ColleagueAggregate;
import entities.PlayerEntity;
import valueobjects.CoordinatesVO;
import valueobjects.RankingVO;

import java.util.Date;
import java.util.List;

public interface GameService {
    boolean initialize(PlayerEntity player);
    boolean initialize(BoardAggregate board);
    boolean initialize(Date currentDate);
    boolean initialize(ColleagueAggregate colleague);
    boolean isInitialized();
    boolean vaccination(ColleagueAggregate colleague);
    void start();
    int getTotalRankingPoints();
    boolean checkIfIsInfected();
    boolean checkIfIsVaccinated();
    void increaseRankingPointsPerTime();
    boolean hasLife();
    boolean movePlayer(CoordinatesVO newCoordinates);
    RankingVO getCurrentRanking();
    List<RankingVO> getTotalRanking();
    void end();
}
