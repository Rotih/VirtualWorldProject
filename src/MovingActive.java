import processing.core.PImage;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


public abstract class MovingActive extends AbstractAnimated{
    public MovingActive(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }
    abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    public Point nextPosition(WorldModel world, Point destPos) {
        List<Point> points;
        PathingStrategy strategy = new AStarPathingStrategy();
        /*
        System.out.println(this.getClass());
        System.out.println(this.getPosition().getX() + " " + this.getPosition().getY());
         */
        points = strategy.computePath(getPosition(), destPos,
                p -> world.withinBounds(p) && !world.isOccupied(p), Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if (points.size() != 0)
            return points.get(points.size()-1);
        return getPosition();
    }
}
