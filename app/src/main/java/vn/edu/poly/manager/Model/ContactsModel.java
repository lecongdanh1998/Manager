package vn.edu.poly.manager.Model;

import java.io.Serializable;

public class ContactsModel implements Serializable {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String title;
    private String content;
    private String pos;
    private String status;
    private String create_at;

    public ContactsModel(String id, String fullName, String email, String phone, String title,
                         String content, String pos, String status, String create_at) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.title = title;
        this.content = content;
        this.pos = pos;
        this.status = status;
        this.create_at = create_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }
}
