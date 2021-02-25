package domainservice;

import valueobjects.RankingVO;

public interface RankingService {
    //todo verhalten kann nicht genau einer entity zugeordnet werden,
    // dafür gibt es domain services, technische details in einer anderen schicht, hier nur vorgabe
    // was erwartet wird (und was geschickt wird). ein und ausgabe sind entities bzw VOs
    //todo service ist zustandslos -> jede instanz des services bleibt gleich. darf aber globale seiteneffekte haben
    boolean saveNewRanking(RankingVO ranking);

    boolean isTopRanking(RankingVO ranking);
}
