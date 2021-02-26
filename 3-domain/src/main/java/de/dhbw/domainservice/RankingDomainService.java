package de.dhbw.domainservice;

import de.dhbw.valueobjects.RankingVO;

import java.util.List;

public interface RankingDomainService {
    //todo verhalten kann nicht genau einer entity zugeordnet werden,
    // dafür gibt es domain de.dhbw.services, technische details in einer anderen schicht, hier nur vorgabe
    // was erwartet wird (und was geschickt wird). ein und ausgabe sind de.dhbw.entities bzw VOs
    //todo service ist zustandslos -> jede instanz des de.dhbw.services bleibt gleich. darf aber globale seiteneffekte haben
    boolean saveNewRankingForBoard(RankingVO ranking, String boardName);

    boolean isTopRankingInBoard(RankingVO ranking, String boardName);

    List<RankingVO> getTopRankingsForBoard(String boardName);
}
