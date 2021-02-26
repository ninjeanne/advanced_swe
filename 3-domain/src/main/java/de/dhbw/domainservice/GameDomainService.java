package de.dhbw.domainservice;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.RankingVO;

import java.util.List;

public interface GameDomainService { //todo kann das auch eine Abstrakte Klasse sein? Dürfen Entity Annotations in Domain Entitäten angebracht werden?
    void initialize(PlayerEntity player);

    void initialize(BoardAggregate board);

    void initializeDate();

    void initialize(RankingVO rankingVO);

    boolean isInitialized();

    boolean isRunning();

    void vaccinatePlayer();

    void infectPlayer();

    int getLastRankingPointsForPlayer();

    int getLifePointsForPlayer();

    void startCountingRankingPointsForPlayer();

    void stopCountingRankingPointsForPlayer();

    boolean movePlayer(CoordinatesVO newCoordinates);

    RankingVO getLastRankingForPlayer();

    BoardAggregate getCurrentBoard();

    void start();

    void stop();

    void addRandomVaccinationToBoard();

    void startMovingColleagues();

    void stopMovingColleagues();

    List<RankingVO> getTotalRankingForBoard();
}
