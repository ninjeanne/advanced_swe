package de.dhbw.aggregates;

import de.dhbw.entities.RankingEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.PlanVO;
import de.dhbw.valueobjects.ProbabilityVO;
import de.dhbw.valueobjects.RadiusVO;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
@Slf4j
@Entity
public class BoardAggregate { //aggregate, weil es in der DB abgelegt werden muss! TBD: Aggregat Root, Getter dürfen nur immutable/copied instances zurückgeben

    @NonNull
    @Id
    private String uuid;
    @NonNull
    @Column
    private String name;
    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<CoordinatesVO> obstacles = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private CoordinatesVO vaccination;
    @OneToOne(cascade = CascadeType.ALL)
    private CoordinatesVO workItem;
    @NonNull
    @Embedded
    private PlanVO plan;
    @OneToOne(cascade = CascadeType.ALL)
    private RadiusVO colleagueRadius;
    @OneToOne(cascade = CascadeType.ALL)
    private ProbabilityVO infectProbability;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<ColleagueAggregate> colleagues = new ArrayList<>();
    @OrderBy
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<RankingEntity> topRankings = new ArrayList<>();

    public BoardAggregate(String uuid, String name, PlanVO plan, RadiusVO colleagueRadius, ProbabilityVO infectProbability) {
        this.uuid = uuid;
        this.name = name;
        this.plan = plan;
        this.colleagueRadius = colleagueRadius;
        this.infectProbability = infectProbability;
    }

    @SuppressWarnings("unused")
    public BoardAggregate() {
    }

    public boolean addRanking(RankingEntity ranking) {
        if (topRankings.isEmpty() || getLastTopRating() == null || topRankings.size() <= 10) {
            topRankings.add(ranking);
            return true;
        }

        if (getLastTopRating().getTotal() < ranking.getTotal()) {
            topRankings.remove(getLastTopRating());
            topRankings.add(ranking);
            return true;
        }

        return false;
    }

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

    public boolean addNewWorkItem(CoordinatesVO coordinatesOfWorkItem) {
        if (containsCoordinate(coordinatesOfWorkItem)) {
            if (obstacles.contains(coordinatesOfWorkItem)) {
                log.warn("Work item couldn't be set. Coordinate is blocked by obstacle at x:{} and y:{}", coordinatesOfWorkItem.getX(),
                        coordinatesOfWorkItem.getY());
                return false;
            }
            this.workItem = coordinatesOfWorkItem;
            return true;
        }
        throw new IllegalArgumentException(
                "Coordinates for work item aren't part of board " + getName() + ". x: " + coordinatesOfWorkItem.getX() + " and y: " + coordinatesOfWorkItem
                        .getY());
    }

    public boolean addColleague(ColleagueAggregate colleague) {
        if (!colleagues.contains(colleague)) {
            for (CoordinatesVO coordinatesVO : colleague.getPath()) {
                if (obstacles.contains(coordinatesVO)) {
                    log.debug("there is an obstacle at x {} and y {} of colleague {}", coordinatesVO.getX(), coordinatesVO.getY(), colleague.getName());
                    return false;
                }
            }
            colleagues.add(colleague);
            log.debug("colleague {} has been added", colleague.getName());
            return true;
        }
        log.warn("colleague {} already exists", colleague.getName());
        return false;
    }

    public boolean containsCoordinate(CoordinatesVO coordinate) {
        if (coordinate.getX() < plan.getWidth() && coordinate.getY() < plan.getHeight()) {
            return true;
        }
        log.warn("coordinate (x:{}, y:{}) not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getHeight(),
                plan.getWidth());
        return false;
    }

    public boolean isCoordinateBlocked(CoordinatesVO coordinate) {
        return obstacles.contains(coordinate);
    }

    public boolean addObstacle(CoordinatesVO coordinate) {
        if (this.containsCoordinate(coordinate)) {
            if (obstacles.contains(coordinate) || (vaccination != null && vaccination.equals(coordinate))) {
                log.warn("obstacle at x:{}, y:{} already exists", coordinate.getX(), coordinate.getY());
                return false;
            }
            obstacles.add(coordinate);
            return true;
        }

        log.warn("obstacle (x:{}, y:{}) was not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getHeight(),
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

        log.warn("obstacle (x:{}, y:{}) was not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getHeight(),
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

    private RankingEntity getLastTopRating() {
        if (!topRankings.isEmpty()) {
            topRankings.sort(Comparator.comparing(RankingEntity::getTotal).reversed());
            return topRankings.get(topRankings.size() - 1);
        }
        return null;
    }

    public boolean addNewTopRanking(RankingEntity rankingEntity) {
        if (topRankings.isEmpty() || getLastTopRating() == null || topRankings.size() < 10) {
            topRankings.add(rankingEntity);
            return true;
        }

        if (rankingEntity.equals(getLastTopRating())) {
            return false;
        }

        if (getLastTopRating().getEarned_points() < rankingEntity.getEarned_points()) {
            topRankings.remove(getLastTopRating());
            topRankings.add(rankingEntity);
            return true;
        }
        return false;
    }

    public boolean isNewTopRanking(RankingEntity rankingEntity) {
        if (topRankings.isEmpty() || getLastTopRating() == null || topRankings.size() < 10) {
            return true;
        }

        if (rankingEntity.equals(getLastTopRating())) {
            return false;
        }
        return getLastTopRating().getEarned_points() < rankingEntity.getEarned_points();
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
