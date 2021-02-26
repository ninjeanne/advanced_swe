package de.dhbw.repositories;

import de.dhbw.aggregates.ColleagueAggregate;

public interface ColleagueRepository { //todo Vorgabe der Methoden für spätere JPA
    void save(ColleagueAggregate colleagueAggregate);
    void delete(ColleagueAggregate colleagueAggregate);
}
