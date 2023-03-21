public interface Plant extends ActiveEntity{
    int getHealth();
    void setHealth(int health);

    default boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (getHealth() <= 0) {
            Entity stump = WorldModel.createStump(WorldModel.STUMP_KEY + "_" + getid(), getPosition(), imageStore.getImageList(WorldModel.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }
        return false;
    }

    default void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }
}
