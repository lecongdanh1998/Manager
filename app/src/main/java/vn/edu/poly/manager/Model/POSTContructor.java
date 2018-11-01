package vn.edu.poly.manager.Model;

import java.io.Serializable;

public class POSTContructor implements Serializable {
    String img_title;
    String txt_title;
    String txt_Time;
    String img_people;
    String id;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public POSTContructor() {
    }

    public POSTContructor(String img_title, String txt_title, String txt_Time, String img_people, String id,String status) {
        this.img_title = img_title;
        this.txt_title = txt_title;
        this.txt_Time = txt_Time;
        this.img_people = img_people;
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_title() {
        return img_title;
    }

    public void setImg_title(String img_title) {
        this.img_title = img_title;
    }

    public String getTxt_title() {
        return txt_title;
    }

    public void setTxt_title(String txt_title) {
        this.txt_title = txt_title;
    }

    public String getTxt_Time() {
        return txt_Time;
    }

    public void setTxt_Time(String txt_Time) {
        this.txt_Time = txt_Time;
    }

    public String getImg_people() {
        return img_people;
    }

    public void setImg_people(String img_people) {
        this.img_people = img_people;
    }
}
