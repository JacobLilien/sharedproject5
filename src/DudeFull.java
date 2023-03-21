import java.util.*;
import processing.core.PImage;

public class DudeFull implements ActiveEntity, AnimatedEntity, Dude {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private final double actionPeriod;
    private final double animationPeriod;

    public DudeFull(String id, Point position, List<PImage> images, int resourceLimit, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
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

    public List<PImage> getImages() {
        return images;
    }

    public int getImageIndex() {
        return imageIndex;
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

    private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        DudeNotFull dude = world.createDudeNotFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleAction(scheduler, world, imageStore);
        dude.scheduleAnimation(scheduler);

    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.position, new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && this.moveToDude(world, fullTarget.get(), scheduler)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
        }
    }
}
