package de.dhbw.persistence.colleague;

import de.dhbw.aggregates.ColleagueAggregate;
import de.dhbw.repositories.ColleagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColleagueRepositoryBridge implements ColleagueRepository {

    private final SpringDataColleagueRepository springDataColleagueRepository;

    @Autowired
    public ColleagueRepositoryBridge(SpringDataColleagueRepository springDataColleagueRepository) {
        this.springDataColleagueRepository = springDataColleagueRepository;
    }

    @Override
    public void save(ColleagueAggregate colleagueAggregate) {
        springDataColleagueRepository.save(colleagueAggregate);
    }

    @Override
    public void delete(ColleagueAggregate colleagueAggregate) {
        springDataColleagueRepository.delete(colleagueAggregate);
    }
}
