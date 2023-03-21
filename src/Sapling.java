import java.util.*;

import processing.core.PImage;

public class Sapling implements Plant, ActiveEntity, AnimatedEntity{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;
    private int health;
    private final int healthLimit;
    private static final double TREE_ANIMATION_MAX = 0.600;
    private static final double TREE_ANIMATION_MIN = 0.050;
    private static final double TREE_ACTION_MAX = 1.400;
    private static final double TREE_ACTION_MIN = 1.000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;

    public Sapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;
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

    public int getHealth()
    {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health >= this.healthLimit) {
            Tree tree = WorldModel.createTree(WorldModel.TREE_KEY + "_" + this.id, this.position, getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), imageStore.getImageList(WorldModel.TREE_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleAction(scheduler, world, imageStore);
            tree.scheduleAnimation(scheduler);

            return true;
        }
        else {
            return Plant.super.transformPlant(world, scheduler, imageStore);
        }

    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health++;
        Plant.super.executeActivity(world, imageStore, scheduler);
    }

    private double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    private int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }
}
