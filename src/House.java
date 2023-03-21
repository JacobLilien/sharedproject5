import java.util.*;

import processing.core.PImage;

public class House implements Entity {
    private final String id;
    private Point position;
    private final List<PImage> images;

    public House(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
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
        return 0;
    }

    public List<PImage> getImages()
    {
        return this.images;
    }

}
