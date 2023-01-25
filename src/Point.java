import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public final class Point
{
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;

    private static final int ORE_REACH = 1;

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point)other).x == this.x
                && ((Point)other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }
    public Blacksmith createBlacksmith(
            String id, List<PImage> images)
    {
        return new Blacksmith(id, this, images);
    }

    public MinerFull createMinerFull(
            String id,
            int resourceLimit,

            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerFull(id, this, images,
                actionPeriod,
                animationPeriod,
                resourceLimit, resourceLimit);
    }

    public MinerNotFull createMinerNotFull(
            String id,
            int resourceLimit,

            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerNotFull(id, this, images,
                actionPeriod,
                animationPeriod,
                resourceLimit, 0);
    }

    public Obstacle createObstacle(
            String id,  List<PImage> images)
    {
        return new Obstacle(id, this, images);
    }

    public Ore createOre(
            String id,  int actionPeriod, List<PImage> images)
    {
        return new Ore(id, this, images,
                actionPeriod);
    }

    public Blob createOreBlob(
            String id,

            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Blob(id, this, images,
                actionPeriod, animationPeriod);
    }

    public Quake createQuake(
             List<PImage> images)
    {
        return new Quake(QUAKE_ID, this, images,
                QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public Vein createVein(
            String id,  int actionPeriod, List<PImage> images)
    {
        return new Vein(id, this, images,
                actionPeriod);
    }


    public boolean adjacent(Point p2) {
        return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) || (this.y == p2.y
                && Math.abs(this.x - p2.x) == 1);
    }

    public Optional<Point> findOpenAround(WorldModel world) {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
                Point newPt = new Point(this.x + dx, this.y + dy);
                if (world.withinBounds(newPt) && !world.isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
    }

    private Optional<Entity> nearestEntity(
            List<Entity> entities)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = nearest.getPosition().distanceSquared(this);

            for (Entity other : entities) {
                int otherDistance = other.getPosition().distanceSquared(this);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    private int distanceSquared(Point p2) {
        int deltaX = this.x - p2.x;
        int deltaY = this.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

    public Optional<Entity> findNearest(
            WorldModel world, Class c)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : world.getEntities()) {
            if (entity.getClass() == c) {
                ofType.add(entity);
            }
        }

        return this.nearestEntity(ofType);
    }

    public int heuristic(Point p) {
        return Math.abs((this.getY() - p.getY()) + (this.getX() - p.getX()));
    }
}
