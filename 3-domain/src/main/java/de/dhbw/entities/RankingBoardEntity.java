package de.dhbw.entities;

import lombok.*;

import java.util.Comparator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingBoardEntity {

    @NonNull
    private String uuid;//entity id
    private final SortedSet<RankingEntity> rankingBoard = new TreeSet<>(Comparator.comparing(RankingEntity::getEarned_points));

    public boolean addRanking(RankingEntity ranking) {
        if (rankingBoard.size() <= 10) {
            rankingBoard.add(ranking);
            return true;
        }

        if (rankingBoard.last().getEarned_points() < ranking.getEarned_points()) {
            rankingBoard.remove(rankingBoard.last());
            rankingBoard.add(ranking);
            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RankingBoardEntity) {
            RankingBoardEntity rankingBoard = (RankingBoardEntity) obj;
            return this.uuid.equals(rankingBoard.uuid);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
