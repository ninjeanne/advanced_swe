package de.dhbw.persistence.colleague;

import de.dhbw.aggregates.ColleagueAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataColleagueRepository extends JpaRepository<ColleagueAggregate, String> {
}
