import processing.core.PImage;

import java.util.List;
import java.util.Optional;

interface Entity{
    String getid();

    Point getPosition();

    void setPosition(Point new_pos);

    int getImageIndex();

    List<PImage> getImages();

    default PImage getCurrentImage(Object object) {
        if (object instanceof Entity entity) {
            return entity.getImages().get(entity.getImageIndex() % entity.getImages().size());
        } else {
            throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", object));
        }
    }

    static Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = nearest.getPosition().distanceSquared(pos);

            for (Entity other : entities) {
                int otherDistance = other.getPosition().distanceSquared(pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }
            return Optional.of(nearest);
        }
    }

    default String log(){
        return this.getid().isEmpty() ? null :
                String.format("%s %d %d %d", this.getid(), this.getPosition().getX(), this.getPosition().getY(), this.getImageIndex());
    }
}