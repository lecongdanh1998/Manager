package vn.edu.poly.manager.View.Post;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.poly.manager.R;

public class Public extends Fragment {
    private View view;
    SharedPreferences dataLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_public, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {

    }

    /*
     * register view
     * */
    private void initView() {

    }
}
