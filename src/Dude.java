import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface Dude extends Entity{
    default Point nextPositionDude(WorldModel world, Point destPos) {
        PathingStrategy strat = new AStarPathingStrategy();
        List<Point> path = strat.computePath(this.getPosition(), destPos, p -> (!world.isOccupied(p) || world.getOccupancyCell(p).getClass() == Stump.class) && world.withinBounds(p),
                Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if (path == null || path.size() == 0)
        {
            return this.getPosition();
        }
        return path.get(0);
    }



    default boolean moveToDude(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = nextPositionDude(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
}
