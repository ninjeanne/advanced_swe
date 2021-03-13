package de.dhbw.domainservice;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.valueobjects.CoordinatesVO;

import java.util.List;

public interface GameDomainService { //todo kann das auch eine Abstrakte Klasse sein? Dürfen Entity Annotations in Domain Entitäten angebracht werden?
    void initialize(PlayerEntity player);

    void initialize(BoardAggregate board);

    void initializeDate();

    void initialize(RankingEntity rankingEntity);

    boolean isInitialized();

    boolean isRunning();

    void vaccinatePlayer();

    void infectPlayer();

    int getLastRankingPointsForPlayer();

    int getLifePointsForPlayer();

    void startCountingRankingPointsForPlayer();

    void stopCountingRankingPointsForPlayer();

    boolean movePlayer(CoordinatesVO newCoordinates);

    RankingEntity getLastRankingForPlayer();

    BoardAggregate getCurrentBoard();

    void startGame();

    void stopGame();

    void addRandomVaccinationToBoard();

    void startMovingColleagues();

    void stopMovingColleagues();

    List<RankingEntity> getTotalRankingForBoard();
}
