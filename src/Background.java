import java.util.List;

import processing.core.PImage;

public final class Background
{
    private String id;
    private List<PImage> images;
    private int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public List<PImage> getImages() {
        return images;
    }

    public void setImageIndex(int index){
        imageIndex = index;
    }

    public PImage getCurrentImage() {

        return this.getImages().get(this.getImageIndex());

    }
}
