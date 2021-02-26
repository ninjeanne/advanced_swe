package de.dhbw.domainservice;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.aggregates.ColleagueAggregate;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.RankingVO;

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
