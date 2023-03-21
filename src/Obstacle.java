import java.util.*;
import processing.core.PImage;

public class Obstacle implements AnimatedEntity {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double animationPeriod;

    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
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

}
