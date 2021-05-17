package de.dhbw.repository;

import de.dhbw.entities.RankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataRankingRepository extends JpaRepository<RankingEntity, String> {
}
