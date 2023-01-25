import processing.core.PImage;

import java.util.List;

public class Ore extends AbstractActive {


    public Ore (String id, Point position, List<PImage> images, int actionPeriod) {
       super(id, position, images, actionPeriod);
    }


    @Override
    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Point pos = this.getPosition();

        world.removeEntity(this);
        scheduler.unscheduleAllEvents( this);

        Blob blob = pos.createOreBlob(this.getId() + BLOB_ID_SUFFIX,
                this.getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + rand.nextInt(
                        BLOB_ANIMATION_MAX
                                - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }
}
