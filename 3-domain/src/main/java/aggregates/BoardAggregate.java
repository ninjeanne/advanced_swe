package aggregates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import valueobjects.CoordinatesVO;
import valueobjects.PlanVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@Slf4j
public class BoardAggregate { //aggregate, weil es in der DB abgelegt werden muss! TBD: Aggregat Root, Getter dürfen nur immutable/copied instances zurückgeben

    @NonNull
    private final String uuid;
    @NonNull
    private String name;
    @NonNull
    private final List<CoordinatesVO> obstacles = new ArrayList<>();
    @NonNull
    private final CoordinatesVO vaccination;
    @NonNull
    private final PlanVO plan;

    private int velocity;

    private final List<ColleagueAggregate> colleagues = new ArrayList<>();

    public boolean addColleague(ColleagueAggregate colleague) {
        if (colleagues.contains(colleague)) {
            colleagues.add(colleague);
            log.debug("colleague {} has been added", colleague.getName());
            return true;
        }
        log.warn("colleague {} already exists", colleague.getName());
        return false;
    }

    public boolean removeColleague(ColleagueAggregate colleague) {
        if (colleagues.contains(colleague)) {
            colleagues.remove(colleague);
            log.debug("colleague {} has been removed", colleague.getName());
            return true;
        }
        log.warn("colleague {} doesn't exists", colleague.getName());
        return false;
    }

    public boolean containsCoordinate(CoordinatesVO coordinate) {
        if(coordinate.getX() < plan.getWidth() && coordinate.getY() < plan.getLength()){
            return true;
        }
        log.warn("coordinate (x:{}, y:{}) not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getLength(), plan.getWidth());
        return false;
    }

    public boolean addObstacle(CoordinatesVO coordinate){
        if (this.containsCoordinate(coordinate)) {
            if(obstacles.contains(coordinate)){
                log.warn("obstacle at x:{}, y:{} already exists", coordinate.getX(), coordinate.getY());
                return false;
            }
            return true;
        }

        log.warn("obstacle (x:{}, y:{}) was not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getLength(), plan.getWidth());
        return false;
    }

    public boolean removeObstacle(CoordinatesVO coordinate){
        if (this.containsCoordinate(coordinate)) {
            if(obstacles.remove(coordinate)){
                log.debug("obstacle at x:{}, y:{} has been removed", coordinate.getX(), coordinate.getY());
                return false;
            }
            return true;
        }

        log.warn("obstacle (x:{}, y:{}) was not within game board (length:{}, width:{})", coordinate.getX(), coordinate.getY(), plan.getLength(), plan.getWidth());
        return false;
    }

    public void setName(String name){
        if(name != null){
            this.name = name;
            return;
        }
        throw new IllegalArgumentException("The name of a board mustn't be empty.");
    }

    public void setVelocity(int velocity){
        if(velocity >= 1){
            this.velocity = velocity;
            return;
        }
        throw new IllegalArgumentException("The velocity of the board has to be positive.");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoardAggregate) {
            BoardAggregate game = (BoardAggregate) obj;
            return this.uuid.equals(game.uuid);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
