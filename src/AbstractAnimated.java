import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class AbstractAnimated extends AbstractActive implements AnimatedEntity {
    private int animationPeriod;
    public AbstractAnimated(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {

        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }
    public int getAnimationPeriod(){
        return animationPeriod;
    }
    public void nextImage() {
        super.setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                this.createAnimationAction(0),
                this.getAnimationPeriod());

    }
}
