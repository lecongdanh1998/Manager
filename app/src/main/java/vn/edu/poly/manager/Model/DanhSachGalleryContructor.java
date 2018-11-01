package vn.edu.poly.manager.Model;

import java.io.Serializable;

public class DanhSachGalleryContructor implements Serializable {
    String images;

    public DanhSachGalleryContructor(String images) {
        this.images = images;
    }

    public DanhSachGalleryContructor() {
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
