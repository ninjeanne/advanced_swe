package de.dhbw.domainservice;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.valueobjects.CoordinatesVO;

public interface GameDomainService {
    void initializeBoard(String boardName);
    void initializePlayer(String playerName);
    void initializeRanking();
    void initializeDate();
    boolean isInitialized();

    void startGame();
    void startCountingRankingPointsForPlayer();
    boolean isRunning();
    PlayerEntity getCurrentPlayer();
    BoardAggregate getCurrentBoard();

    void stopGame();
    void stopCountingRankingPointsForPlayer();
    boolean isGameOver();

    boolean movePlayer(CoordinatesVO newCoordinates);

    int getLastRankingPointsForPlayer();
}
