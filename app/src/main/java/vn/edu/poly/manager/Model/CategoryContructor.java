package vn.edu.poly.manager.Model;

import java.io.Serializable;

public class CategoryContructor implements Serializable {
    String  idCategory;
    String NaemCategory;

    public CategoryContructor() {
    }

    public CategoryContructor(String idCategory, String naemCategory) {
        this.idCategory = idCategory;
        NaemCategory = naemCategory;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getNaemCategory() {
        return NaemCategory;
    }

    public void setNaemCategory(String naemCategory) {
        NaemCategory = naemCategory;
    }
}
