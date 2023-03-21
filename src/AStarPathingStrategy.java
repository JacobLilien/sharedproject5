import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();
        PriorityQueue<Point> openList = new PriorityQueue<>(Comparator.comparing(Point::getf));
        HashMap openListHash = new HashMap();
        Point current = new Point(start.x, start.y);
        openList.add(current);
        openListHash.put(current.hashCode(), current);
        HashMap closedList = new HashMap();
        while (!current.adjacent(end))
        {
            for (Point neighbor : potentialNeighbors.apply(current)
                    .filter(canPassThrough)
                    .collect(Collectors.toList())) {
                if (!closedList.containsValue(neighbor)) {
                    double g = current.getG() + 1;
                    if (openListHash.containsValue(neighbor)) {
                        if (g < neighbor.getG()) {
                            neighbor.setG(g);
                        }
                        else {
                            continue;
                        }
                    }
                    else
                    {
                        neighbor.setG(g);
                    }
                    double h = Math.abs(neighbor.x - end.x) + Math.abs(neighbor.y - end.y);
                    double f = h + g;
                    neighbor.setf(f);
                    neighbor.setPriorNode(current);
                    if (openListHash.containsValue(neighbor))
                    {
                        openList.remove(neighbor);
                        openList.add(neighbor);
                        openListHash.remove(neighbor);
                        openListHash.put(neighbor.hashCode(), neighbor);
                    }
                    else
                    {
                        openList.add(neighbor);
                        openListHash.put(neighbor.hashCode(), neighbor);
                    }
                }
            }
            closedList.put(current.hashCode(), current);
            if (!openList.isEmpty())
            {
                current = openList.remove();
                openListHash.remove(current.hashCode(), current);
            }
            else {
                return path;
            }
        }

        if (current.adjacent(end))
        {
            while (current.getPriorNode() != null)
            {
                path.add(0, current);
                current = current.getPriorNode();
            }
        }
        return path;
    }
}
