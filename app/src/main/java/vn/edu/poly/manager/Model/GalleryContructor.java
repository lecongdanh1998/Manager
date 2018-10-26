package vn.edu.poly.manager.Model;

import java.io.Serializable;

public class GalleryContructor implements Serializable {
    String imgGallery;

    public GalleryContructor(String imgGallery) {
        this.imgGallery = imgGallery;
    }

    public GalleryContructor() {
    }

    public String getImgGallery() {
        return imgGallery;
    }

    public void setImgGallery(String imgGallery) {
        this.imgGallery = imgGallery;
    }
}
