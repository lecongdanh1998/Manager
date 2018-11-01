package vn.edu.poly.manager.Model;

import java.io.Serializable;

public class LietKeGalleryContructor implements Serializable {
    String images;

    public LietKeGalleryContructor(String images) {
        this.images = images;
    }

    public LietKeGalleryContructor() {
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
