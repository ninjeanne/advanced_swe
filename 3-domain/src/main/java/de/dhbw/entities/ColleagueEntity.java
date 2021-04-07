package de.dhbw.entities;

import de.dhbw.helper.ColleagueMovement;
import de.dhbw.helper.ForwardAndBackMovement;
import de.dhbw.valueobjects.CoordinatesVO;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class ColleagueEntity {

    @NonNull
    @Id
    private String nameAsEntityID;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<CoordinatesVO> path = new ArrayList<>();

    @SuppressWarnings("unused")
    public ColleagueEntity() {
    }

    public ColleagueEntity(@NonNull String nameAsEntityID) {
        this.nameAsEntityID = nameAsEntityID;
    }

    public void extendPath(CoordinatesVO coordinate) {
        if (path.isEmpty()) {
            path.add(coordinate);
            return;
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
                return;
            } else if (lastCoordinate.getY() == coordinate.getY() + 1 || lastCoordinate.getY() == coordinate.getY() - 1) {
                path.add(coordinate);
                return;
            }
        }
        throw new IllegalArgumentException("path for colleague couldn't be extended for x:" + coordinate.getX() + " and y:" + coordinate.getY());
    }

    public ColleagueMovement createColleagueIterator(){
        return new ForwardAndBackMovement(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ColleagueEntity) {
            ColleagueEntity colleague = (ColleagueEntity) obj;
            return this.nameAsEntityID.equals(colleague.nameAsEntityID);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameAsEntityID);
    }
}
