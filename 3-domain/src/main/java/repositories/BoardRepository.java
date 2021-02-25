package repositories;

import aggregates.BoardAggregate;

public interface BoardRepository { //todo Vorgabe der Methoden für spätere JPA
    BoardAggregate getBoardByName(String name);
}
