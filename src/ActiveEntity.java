public interface ActiveEntity extends Entity{
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    default Activity createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    double getActionPeriod();

    default void scheduleAction(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
    }

}
