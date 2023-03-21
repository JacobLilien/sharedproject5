import java.util.*;

import processing.core.PImage;


public class DudeNotFull implements ActiveEntity, AnimatedEntity, Dude {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private int resourceCount;
    private final double actionPeriod;
    private final double animationPeriod;

    public DudeNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
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

    public void nextImage() { this.imageIndex++; }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    public double getActionPeriod() {
        return this.actionPeriod;
    }

    public boolean moveToDude(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            Plant p = (Plant) target;
            this.resourceCount += 1;
            p.setHealth(p.getHealth() - 1);
        }
        return Dude.super.moveToDude(world, target, scheduler);
    }

    private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            DudeFull dude = world.createDudeFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);
            dude.scheduleAction(scheduler, world, imageStore);
            dude.scheduleAnimation(scheduler);

            return true;
        }
        return false;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(this.position, new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (target.isEmpty() || !moveToDude(world, target.get(), scheduler) || !this.transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
        }
    }
}
