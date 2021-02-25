package domainservice;

import aggregates.BoardAggregate;
import valueobjects.RankingVO;

public interface RankingDomainService {
    //todo verhalten kann nicht genau einer entity zugeordnet werden,
    // dafür gibt es domain services, technische details in einer anderen schicht, hier nur vorgabe
    // was erwartet wird (und was geschickt wird). ein und ausgabe sind entities bzw VOs
    //todo service ist zustandslos -> jede instanz des services bleibt gleich. darf aber globale seiteneffekte haben
    boolean saveNewRankingForBoard(RankingVO ranking, BoardAggregate boardAggregate);

    boolean isTopRankingInBoard(RankingVO ranking, BoardAggregate boardAggregate);
}
