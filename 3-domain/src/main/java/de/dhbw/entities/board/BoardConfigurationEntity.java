package de.dhbw.entities.board;

import de.dhbw.valueobjects.ProbabilityVO;
import de.dhbw.valueobjects.RadiusVO;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class BoardConfigurationEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private RadiusVO colleagueRadius;
    @OneToOne(cascade = CascadeType.ALL)
    private ProbabilityVO infectProbability;

    public BoardConfigurationEntity(RadiusVO colleagueRadius, ProbabilityVO infectProbability) {
        this.colleagueRadius = colleagueRadius;
        this.infectProbability = infectProbability;
    }

    public BoardConfigurationEntity() {

    }
}
