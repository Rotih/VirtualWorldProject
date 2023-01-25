import java.util.List;
import java.util.Random;

import processing.core.PImage;

public interface Entity
{
    Random rand = new Random();

    String BLOB_KEY = "blob";
    String BLOB_ID_SUFFIX = " -- blob";
    int BLOB_PERIOD_SCALE = 4;
    int BLOB_ANIMATION_MIN = 50;
    int BLOB_ANIMATION_MAX = 150;

    String ORE_ID_PREFIX = "ore -- ";
    int ORE_CORRUPT_MIN = 20000;
    int ORE_CORRUPT_MAX = 30000;
    String ORE_KEY = "ore";

    String QUAKE_KEY = "quake";

    Point getPosition();
    List<PImage> getImages();
    int getImageIndex();
    void setPosition(Point position);

    Action createAnimationAction(int repeatCount);

    Action createActivityAction(
            WorldModel world, ImageStore imageStore);

    PImage getCurrentImage() ;

}
