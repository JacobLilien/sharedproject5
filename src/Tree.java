import java.util.*;

import processing.core.PImage;

public class Tree implements Plant, ActiveEntity, AnimatedEntity {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;
    private int health;

    public Tree(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
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

    public double getAnimationPeriod() { return this.animationPeriod; }

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

}
