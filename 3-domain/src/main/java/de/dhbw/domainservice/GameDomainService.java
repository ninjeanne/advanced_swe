package de.dhbw.domainservice;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.valueobjects.CoordinatesVO;

public interface GameDomainService {
    void initialize(PlayerEntity player);
    void initialize(BoardAggregate board);
    void initializeRanking();
    void initializeDate();
    boolean isInitialized();

    void startGame();
    void startCountingRankingPointsForPlayer();
    void startMovingColleagues();
    boolean isRunning();

    void stopGame();
    void stopCountingRankingPointsForPlayer();
    void stopMovingColleagues();
    boolean isGameOver();

    boolean movePlayer(CoordinatesVO newCoordinates);
    boolean isPlayerOnWorkItem();
    void playerHasWorked();
    void addRandomWorkItemToBoard();

    boolean isPlayerOnVaccination();
    void vaccinatePlayer();
    void addRandomVaccinationToBoard();

    boolean isPlayerInInfectionRadius();
    void infectPlayer();

    BoardAggregate getCurrentBoard();
    int getLastRankingPointsForPlayer();
}
