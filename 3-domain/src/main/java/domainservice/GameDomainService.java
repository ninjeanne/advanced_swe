package domainservice;

import aggregates.BoardAggregate;
import aggregates.ColleagueAggregate;
import entities.PlayerEntity;
import valueobjects.CoordinatesVO;
import valueobjects.RankingVO;

import java.util.Date;
import java.util.List;

public interface GameDomainService {
    boolean initialize(PlayerEntity player);
    boolean initialize(BoardAggregate board);
    boolean initialize(Date currentDate);
    boolean isInitialized();

    boolean vaccinatePlayer();
    boolean infectPlayer();
    int getRankingPointsForPlayer();
    void increaseRankingPointsForPlayer();
    boolean movePlayer(CoordinatesVO newCoordinates);
    RankingVO getRankingForPlayer();

    void start();
    void end();
    void addRandomVaccinationToBoard();
    void addVaccinationToBoard(CoordinatesVO coordinatesVO);
    void addColleagueToBoard(ColleagueAggregate colleagueAggregate);
    List<RankingVO> getTotalRankingForBoard();
}
