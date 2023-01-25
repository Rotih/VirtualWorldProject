import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class AbstractActive extends AbstractEntity implements ActiveEntity {
        private int actionPeriod;


        public AbstractActive(String id, Point position, List<PImage> images, int actionPeriod) {
            super(id, position, images);
            this.actionPeriod = actionPeriod;
        }

    public abstract void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler);
    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());

    }
    public int getActionPeriod(){
        return actionPeriod;
    }
    }

