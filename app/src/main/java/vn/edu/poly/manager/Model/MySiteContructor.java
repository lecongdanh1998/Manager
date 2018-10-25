package vn.edu.poly.manager.Model;

import java.io.Serializable;

public class MySiteContructor implements Serializable {
    private String TitleSite;
    private String LinkeSite;

    public MySiteContructor(String titleSite, String linkeSite) {
        TitleSite = titleSite;
        LinkeSite = linkeSite;
    }

    public MySiteContructor() {
    }

    public String getTitleSite() {
        return TitleSite;
    }

    public void setTitleSite(String titleSite) {
        TitleSite = titleSite;
    }

    public String getLinkeSite() {
        return LinkeSite;
    }

    public void setLinkeSite(String linkeSite) {
        LinkeSite = linkeSite;
    }
}
