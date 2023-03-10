package de.dhbw.domainservice;

import de.dhbw.entities.board.BoardEntity;
import de.dhbw.entities.player.PlayerEntity;
import de.dhbw.entities.ranking.RankingEntity;
import de.dhbw.valueobjects.CoordinatesVO;

public interface GameDomainService extends InitializerService {
    void initialize(String boardName, String playerName);

    void startGame();
    boolean isRunning();
    PlayerEntity getCurrentPlayer();
    RankingEntity getCurrentRanking();
    BoardEntity getCurrentBoard();

    boolean movePlayer(CoordinatesVO newCoordinates);

    boolean isGameOver();
    void stopGame();
}
