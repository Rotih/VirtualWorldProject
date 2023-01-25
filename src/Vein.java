import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Vein extends AbstractActive {

    public Vein(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    @Override
    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Point> openPt = this.getPosition().findOpenAround(world);

        if (openPt.isPresent()) {
            Point pt = openPt.get();
            Ore ore = pt.createOre(ORE_ID_PREFIX + this.getId(),
                    ORE_CORRUPT_MIN + rand.nextInt(
                            ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    imageStore.getImageList(ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
    }
}
