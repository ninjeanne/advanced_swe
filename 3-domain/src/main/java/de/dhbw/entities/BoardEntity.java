package de.dhbw.entities;

import de.dhbw.aggregates.AggregateRoot;
import de.dhbw.valueobjects.CoordinatesVO;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Slf4j
@Entity
public class BoardEntity implements AggregateRoot {

    @NonNull
    @Id
    private String entityID;
    @NonNull
    @Column
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BoardLayoutEntity boardLayout;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BoardConfigurationEntity boardConfiguration;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private final List<ColleagueEntity> colleagues = new ArrayList<>();

    public BoardEntity(@NonNull String entityID, @NonNull String name, @NonNull BoardLayoutEntity boardLayout, @NonNull BoardConfigurationEntity boardConfiguration) {
        this.entityID = entityID;
        this.name = name;
        this.boardLayout = boardLayout;
        this.boardConfiguration = boardConfiguration;
    }

    @SuppressWarnings("unused")
    public BoardEntity() {
    }

    public void addColleague(ColleagueEntity colleague) {
        if (!colleagues.contains(colleague)) {
            for (CoordinatesVO coordinatesVO : colleague.getPath()) {
                if (getBoardLayout().isCoordinateBlocked(coordinatesVO)) {
                    log.debug("there is an obstacle at x {} and y {} of colleague {}", coordinatesVO.getX(), coordinatesVO.getY(), colleague.getNameAsEntityID());
                    return;
                }
            }
            colleagues.add(colleague);
            log.debug("colleague {} has been added", colleague.getNameAsEntityID());
            return;
        }
        log.warn("colleague {} already exists", colleague.getNameAsEntityID());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoardEntity) {
            BoardEntity game = (BoardEntity) obj;
            return this.entityID.equals(game.entityID);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityID);
    }
}
