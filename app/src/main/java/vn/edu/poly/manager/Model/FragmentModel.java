package vn.edu.poly.manager.Model;

import android.support.v4.app.Fragment;

import java.io.Serializable;

public class FragmentModel implements Serializable {
    private Fragment fragment;
    private String title;
    private int id;

    public FragmentModel(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public FragmentModel(Fragment fragment, String title, int id) {
        this.fragment = fragment;
        this.title = title;
        this.id = id;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
