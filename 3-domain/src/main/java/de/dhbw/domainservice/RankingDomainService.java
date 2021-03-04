package de.dhbw.domainservice;

import de.dhbw.entities.RankingEntity;

import java.util.List;

public interface RankingDomainService {
    //todo verhalten kann nicht genau einer entity zugeordnet werden,
    // dafür gibt es domain de.dhbw.services, technische details in einer anderen schicht, hier nur vorgabe
    // was erwartet wird (und was geschickt wird). ein und ausgabe sind de.dhbw.entities bzw VOs
    //todo service ist zustandslos -> jede instanz des de.dhbw.services bleibt gleich. darf aber globale seiteneffekte haben
    boolean saveNewRankingForBoard(RankingEntity ranking, String boardName);

    boolean isTopRankingInBoard(RankingEntity ranking, String boardName);

    List<RankingEntity> getTopRankingsForBoard(String boardName);
}
