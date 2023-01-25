
public interface ActiveEntity {
    int getActionPeriod();
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    void scheduleActions(EventScheduler scheduler,
                         WorldModel world,
                         ImageStore imageStore);
}
