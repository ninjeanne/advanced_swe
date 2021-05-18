package de.dhbw.entities.board;

import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.PlanVO;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Slf4j
public class BoardLayoutEntity {
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<CoordinatesVO> obstacles = new ArrayList<>();
    @NonNull
    @Embedded
    private PlanVO plan;

    public BoardLayoutEntity(PlanVO planVO){
        this.plan = planVO;
    }

    public BoardLayoutEntity(){
    }

    public CoordinatesVO getRandomCoordinate() {
        int x = (int) (Math.random() * plan.getWidth());
        int y = (int) (Math.random() * plan.getHeight());
        return new CoordinatesVO(x, y);
    }

    public boolean containsCoordinate(CoordinatesVO coordinate) {
        if (coordinate.getX() < plan.getWidth() && coordinate.getY() < plan.getHeight()) {
            return true;
        }
        log.warn("coordinate (x:{}, y:{}) not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getHeight(),
                plan.getWidth());
        return false;
    }

    public boolean addObstacle(CoordinatesVO coordinate) {
        if (this.containsCoordinate(coordinate)) {
            if (obstacles.contains(coordinate)) {
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

    public boolean isCoordinateBlocked(CoordinatesVO coordinatesVO) {
        if (containsCoordinate(coordinatesVO)) {
            if (obstacles.contains(coordinatesVO)) {
                log.debug("Coordinate is blocked by obstacle at x:{} and y:{}", coordinatesVO.getX(), coordinatesVO.getY());
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException(
                "Coordinates aren't part of board, x: " + coordinatesVO.getX() + " and y: " + coordinatesVO.getY());
    }
}
