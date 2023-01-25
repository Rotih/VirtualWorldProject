public class Animation implements Action {
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Animation(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }


    public void executeAction(
            EventScheduler scheduler)
    {
        if (entity instanceof AbstractAnimated){
            AnimatedEntity e1 = (AbstractAnimated)entity;
            e1.nextImage();
            if (this.repeatCount != 1) {
                scheduler.scheduleEvent(this.entity,
                        this.entity.createAnimationAction(
                                Math.max(this.repeatCount - 1,
                                        0)),
                        e1.getAnimationPeriod());
            }
        }
        }


}
