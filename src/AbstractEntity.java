import processing.core.PImage;

import java.util.List;

public abstract class AbstractEntity implements Entity{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex = 0;

    public AbstractEntity(String id, Point position, List<PImage> images){
        this.id = id;
        this.position = position;
        this.images = images;
    }

    public String getId() {return id;}
    public Point getPosition() {
        return position;
    }
    public List<PImage> getImages() {
        return images;
    }
    public int getImageIndex(){
        return imageIndex;
    }
    public void setPosition(Point position){
        this.position = position;
    }
    public void setImageIndex(int imageIndex) {this.imageIndex = imageIndex;}
    public Action createAnimationAction(int repeatCount) {
        return new Animation(this, null, null,
                repeatCount);
    }

    public Action createActivityAction(
            WorldModel world, ImageStore imageStore)
    {
        return new Activity(this, world, imageStore, 0);
    }
    public PImage getCurrentImage() {
        return (this.getImages().get(this.getImageIndex()));
    }
}
