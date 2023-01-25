public class Activity implements Action {
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Activity(
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


    public void executeAction(EventScheduler scheduler)
    {
        if (this.entity instanceof MinerFull) {
            MinerFull M = (MinerFull) entity;
            M.executeActivity(this.world,
                    this.imageStore, scheduler);
        }
        else if (this.entity instanceof MinerNotFull) {
            MinerNotFull m = (MinerNotFull) entity;
            m.executeActivity(this.world,
                    this.imageStore, scheduler);
        }
        else if (this.entity instanceof Ore) {
            Ore o = (Ore) entity;
            o.executeActivity(this.world,
                    this.imageStore, scheduler);

        }
        else if (this.entity instanceof Blob) {
            Blob b = (Blob) entity;
            b.executeActivity(this.world,
                    this.imageStore, scheduler);
        }

        else if (this.entity instanceof Quake) {
            Quake q = (Quake) entity;
            q.executeActivity(this.world, this.imageStore,
                    scheduler);
        }

        else if (this.entity instanceof Vein) {
            Vein v = (Vein) entity;
            v.executeActivity(this.world,
                    this.imageStore, scheduler);

        }else if (this.entity instanceof ElectroBird) {
            ElectroBird b = (ElectroBird) entity;
            b.executeActivity(this.world,
                    this.imageStore, scheduler);

        }else if (this.entity instanceof ElectroBlob) {
            ElectroBlob e = (ElectroBlob) entity;
            e.executeActivity(this.world,
                    this.imageStore, scheduler);

        } else {
            throw new UnsupportedOperationException(String.format(
                    "executeActivityAction not supported for %s",
                    this.entity.getClass()));
        }
    }
}
