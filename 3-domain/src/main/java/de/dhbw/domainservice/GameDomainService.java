package de.dhbw.domainservice;

import de.dhbw.entities.PlayerEntity;
import de.dhbw.valueobjects.CoordinatesVO;

public interface GameDomainService {
    void initialize(PlayerEntity player);
    void initializeBoard(String boardName);
    void initializeRanking();
    void initializeDate();
    boolean isInitialized();

    void startGame();
    void startCountingRankingPointsForPlayer();
    boolean isRunning();

    void stopGame();
    void stopCountingRankingPointsForPlayer();
    boolean isGameOver();

    boolean movePlayer(CoordinatesVO newCoordinates);
    void playerHasWorked();

    void vaccinatePlayer();
    void infectPlayer();

    int getLastRankingPointsForPlayer();
}
