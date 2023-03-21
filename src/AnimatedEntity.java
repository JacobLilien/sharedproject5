public interface AnimatedEntity extends Entity {
    void nextImage();
    double getAnimationPeriod();
    default void scheduleAnimation(EventScheduler scheduler) {
        scheduler.scheduleEvent(this, this.createAnimationAction(0), getAnimationPeriod());
    }
    default Animation createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }

}
