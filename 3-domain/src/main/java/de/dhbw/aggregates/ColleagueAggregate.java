package de.dhbw.aggregates;

import de.dhbw.valueobjects.CoordinatesVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ColleagueAggregate {

    @NonNull
    @Id
    private String name; //name as entity id
    @Column
    private int position = 0;
    @Column
    boolean moveForward = true;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CoordinatesVO> path = new ArrayList<>();

    public CoordinatesVO getPosition() {
        return path.get(position);
    }

    public void extendPath(CoordinatesVO coordinate) {
        if (path.isEmpty()) {
            path.add(coordinate);
        } else {
            CoordinatesVO lastCoordinate = path.get(path.size() - 1);
            if (coordinate.equals(lastCoordinate)) {
                throw new IllegalArgumentException("Path for colleague couldn't be extended. New coordinate equals the last saved one.");
            }
            if (lastCoordinate.getX() == coordinate.getX() + 1 || lastCoordinate.getX() == coordinate.getX() - 1) {
                if (lastCoordinate.getY() == coordinate.getY() + 1 || lastCoordinate.getY() == coordinate.getY() - 1) {
                    throw new IllegalArgumentException("new path coordinate for colleague must be de/increased for x OR for y");
                }
                path.add(coordinate);
            } else if (lastCoordinate.getY() == coordinate.getY() + 1 || lastCoordinate.getY() == coordinate.getY() - 1) {
                path.add(coordinate);
            }
        }
        throw new IllegalArgumentException("path for colleague couldn't be extended for x:" + coordinate.getX() + " and y:" + coordinate.getY());
    }

    private void setPosition(int position) {
        if (position >= 0 && position < path.size()) {
            this.position = position;
            return;
        }

        throw new IllegalArgumentException(
                "The position has to be positive and part of the path. " + "Path Size is: " + path.size() + ", new position: " + position + ", old position: "
                        + this.position);
    }

    public CoordinatesVO nextPosition() {
        if (moveForward) {
            if (position + 1 == path.size()) {
                moveForward = false;
                return nextPosition();
            } else {
                setPosition(++position);
                return path.get(position);
            }
        } else {
            if (position == 0) {
                moveForward = true;
                return nextPosition();
            } else {
                setPosition(--position);
                return path.get(position);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ColleagueAggregate) {
            ColleagueAggregate colleague = (ColleagueAggregate) obj;
            return this.name.equals(colleague.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
