import processing.core.PImage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


public class ElectroBird extends MovingActive {
    private static final String SMITH_KEY = "blacksmith";
    private static final int SMITH_ID = 1;

    public ElectroBird (String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> birdTarget =
                this.getPosition().findNearest(world, FriedSmith.class);
        Optional<Entity> birdTarget2 = this.getPosition().findNearest(world, ElectroBlob.class);
        long nextPeriod = this.getActionPeriod();

        if (birdTarget.isPresent()) {
            Point tgtPos = birdTarget.get().getPosition();

            if (this.moveTo(world, birdTarget.get(), scheduler)) {
                Blacksmith smith = tgtPos.createBlacksmith("blacksmith",
                        imageStore.getImageList(SMITH_KEY));
                try {
                    world.removeEntity(birdTarget.get());
                } catch(NoSuchElementException e) {}
                try {
                    world.removeEntity(birdTarget2.get());
                } catch(NoSuchElementException e) {}
                world.addEntity(smith);
                nextPeriod += this.getActionPeriod();

            }
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                nextPeriod);
    }

    public boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents( target);
            return true;
        }
        else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents( occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

}

