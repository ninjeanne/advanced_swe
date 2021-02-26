package de.dhbw.aggregates;

import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.PlanVO;
import de.dhbw.valueobjects.RankingVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Slf4j
@Entity
public class BoardAggregate { //aggregate, weil es in der DB abgelegt werden muss! TBD: Aggregat Root, Getter dürfen nur immutable/copied instances zurückgeben

    @NonNull
    @Id
    private String uuid;
    @NonNull
    private String name;
    @NonNull
    @OneToMany
    private final List<CoordinatesVO> obstacles = new ArrayList<>();
    @OneToOne
    private CoordinatesVO vaccination;
    @NonNull
    @OneToOne
    private PlanVO plan;
    private int velocity;
    @OneToMany
    private final List<ColleagueAggregate> colleagues = new ArrayList<>();
    @OneToMany
    private List<RankingVO> topRankings;

    public boolean addNewVaccination(CoordinatesVO coordinatesOfVaccination) {
        if (containsCoordinate(coordinatesOfVaccination)) {
            if (obstacles.contains(coordinatesOfVaccination)) {
                log.warn("Vaccination couldn't be set. Coordinate is blocked by obstacle at x:{} and y:{}", coordinatesOfVaccination.getX(),
                        coordinatesOfVaccination.getY());
                return false;
            }
            this.vaccination = coordinatesOfVaccination;
            return true;
        }
        throw new IllegalArgumentException(
                "Coordinates for vaccination aren't part of board " + getName() + ". x: " + coordinatesOfVaccination.getX() + " and y: "
                        + coordinatesOfVaccination.getY());
    }

    public boolean addColleague(ColleagueAggregate colleague) {
        if (colleagues.contains(colleague)) {
            colleagues.add(colleague);
            log.debug("colleague {} has been added", colleague.getName());
            return true;
        }
        log.warn("colleague {} already exists", colleague.getName());
        return false;
    }

    public boolean removeColleague(ColleagueAggregate colleague) {
        if (colleagues.contains(colleague)) {
            colleagues.remove(colleague);
            log.debug("colleague {} has been removed", colleague.getName());
            return true;
        }
        log.warn("colleague {} doesn't exists", colleague.getName());
        return false;
    }

    public boolean containsCoordinate(CoordinatesVO coordinate) {
        if (coordinate.getX() < plan.getWidth() && coordinate.getY() < plan.getLength()) {
            return true;
        }
        log.warn("coordinate (x:{}, y:{}) not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getLength(),
                plan.getWidth());
        return false;
    }

    public boolean isCoordinateBlocked(CoordinatesVO coordinate) {
        return obstacles.contains(coordinate);
    }

    public boolean addObstacle(CoordinatesVO coordinate) {
        if (this.containsCoordinate(coordinate)) {
            if (obstacles.contains(coordinate)) {
                log.warn("obstacle at x:{}, y:{} already exists", coordinate.getX(), coordinate.getY());
                return false;
            }
            return true;
        }

        log.warn("obstacle (x:{}, y:{}) was not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getLength(),
                plan.getWidth());
        return false;
    }

    public boolean removeObstacle(CoordinatesVO coordinate) {
        if (this.containsCoordinate(coordinate)) {
            if (obstacles.remove(coordinate)) {
                log.debug("obstacle at x:{}, y:{} has been removed", coordinate.getX(), coordinate.getY());
                return false;
            }
            return true;
        }

        log.warn("obstacle (x:{}, y:{}) was not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getLength(),
                plan.getWidth());
        return false;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
            return;
        }
        throw new IllegalArgumentException("The name of a board mustn't be empty.");
    }

    public void setVelocity(int velocity) {
        if (velocity >= 1) {
            this.velocity = velocity;
            return;
        }
        throw new IllegalArgumentException("The velocity of the board has to be positive.");
    }

    private RankingVO getLastTopRating() {
        return topRankings.get(topRankings.size() - 1);
    }

    public boolean addNewTopRanking(RankingVO rankingVO) {
        if (rankingVO.equals(getLastTopRating())) {
            return false;
        }

        if (topRankings.isEmpty() || topRankings.size() < 10) {
            return true;
        }
        if (getLastTopRating().getEarned_points() < rankingVO.getEarned_points()) {
            topRankings.remove(getLastTopRating());
            topRankings.add(rankingVO);
            return true;
        }
        return false;
    }

    public boolean isNewTopRanking(RankingVO rankingVO) {
        if (rankingVO.equals(getLastTopRating())) {
            return false;
        }

        if (topRankings.isEmpty() || topRankings.size() < 10) {
            return true;
        }
        return getLastTopRating().getEarned_points() < rankingVO.getEarned_points();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoardAggregate) {
            BoardAggregate game = (BoardAggregate) obj;
            return this.uuid.equals(game.uuid);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
