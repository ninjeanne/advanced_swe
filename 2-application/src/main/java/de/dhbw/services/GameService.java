package de.dhbw.services;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.aggregates.ColleagueAggregate;
import de.dhbw.domainservice.GameDomainService;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.repositories.ColleagueRepository;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.RankingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GameService implements GameDomainService {

    private final BoardRepository boardRepository;
    private final ColleagueRepository colleagueRepository;

    @Autowired
    public GameService(BoardRepository boardRepository, ColleagueRepository colleagueRepository) {
        this.boardRepository = boardRepository;
        this.colleagueRepository = colleagueRepository;
    }

    @Override
    public boolean initialize(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean initialize(BoardAggregate board) {
        return false;
    }

    @Override
    public boolean initialize(Date currentDate) {
        return false;
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public boolean vaccinatePlayer() {
        return false;
    }

    @Override
    public boolean infectPlayer() {
        return false;
    }

    @Override
    public int getRankingPointsForPlayer() {
        return 0;
    }

    @Override
    public void increaseRankingPointsForPlayer() {

    }

    @Override
    public boolean movePlayer(CoordinatesVO newCoordinates) {
        return false;
    }

    @Override
    public RankingVO getRankingForPlayer() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public void addRandomVaccinationToBoard() {

    }

    @Override
    public void addVaccinationToBoard(CoordinatesVO coordinatesVO) {

    }

    @Override
    public void addColleagueToBoard(ColleagueAggregate colleagueAggregate) {

    }

    @Override
    public List<RankingVO> getTotalRankingForBoard() {
        return null;
    }
}
