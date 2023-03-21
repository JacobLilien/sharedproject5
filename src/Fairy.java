import java.util.*;
import processing.core.PImage;

public class Fairy implements ActiveEntity, AnimatedEntity {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;


    public Fairy(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public String getid()
    {
        return this.id;
    }

    public Point getPosition()
    {
        return this.position;
    }

    public void setPosition(Point new_pos)
    {
        this.position = new_pos;
    }

    public int getImageIndex()
    {
        return this.imageIndex;
    }

    public List<PImage> getImages()
    {
        return this.images;
    }

    public void nextImage() {
        this.imageIndex++;
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    public double getActionPeriod() {
        return actionPeriod;
    }

    private Point nextPositionFairy(WorldModel world, Point destPos) {
        PathingStrategy strat = new AStarPathingStrategy();
        List<Point> path = strat.computePath(this.position, destPos, p -> !world.isOccupied(p) && world.withinBounds(p),
                Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if (path == null || path.size() == 0)
        {
            return this.position;
        }
        return path.get(0);
    }

    private boolean moveToFairy(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.position.adjacent(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPositionFairy(world, target.getPosition());

            if (!this.position.equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(this.position, new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {

                Sapling sapling = WorldModel.createSapling(WorldModel.SAPLING_KEY + "_" + fairyTarget.get().getid(), tgtPos, imageStore.getImageList(WorldModel.SAPLING_KEY), 0);

                world.addEntity(sapling);
                sapling.scheduleAction(scheduler, world, imageStore);
                sapling.scheduleAnimation(scheduler);
            }
        }
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
    }

}
