package vn.edu.poly.manager.Model;

import java.io.Serializable;

public class SettingModel implements Serializable {

    private String id;
    private String key;
    private String display_name;
    private String value;
    private String details;
    private String type;
    private String order;
    private String group;

    public SettingModel(String id, String key, String display_name, String value, String details, String type, String order, String group) {
        this.id = id;
        this.key = key;
        this.display_name = display_name;
        this.value = value;
        this.details = details;
        this.type = type;
        this.order = order;
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
