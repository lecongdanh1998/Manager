package vn.edu.poly.manager.Model;

import java.io.Serializable;

public class MenuModel implements Serializable {
    private String title;

    public MenuModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
