package vn.edu.poly.manager.View.Post;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.edu.poly.manager.Adapter.ViewPagerAdapter;
import vn.edu.poly.manager.Component.CustomFontToolBar;
import vn.edu.poly.manager.Model.FragmentModel;
import vn.edu.poly.manager.R;

public class Post extends Fragment {
    private View view;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private ArrayList<FragmentModel> fragmentModelArrayList;
    private ViewPagerAdapter viewPagerAdapter;
    SharedPreferences dataLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        fragmentModelArrayList = new ArrayList<>();
        fragmentModelArrayList.add(new FragmentModel(new Draff(), "Draff"));
        fragmentModelArrayList.add(new FragmentModel(new Pending(), "Pending"));
        fragmentModelArrayList.add(new FragmentModel(new Public(), "Public"));
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), fragmentModelArrayList);
        viewpager.setAdapter(viewPagerAdapter);
        new CustomFontToolBar(tablayout, getActivity()).setCustomFontTab();
        tablayout.setupWithViewPager(viewpager);
    }

    /*
     * register view
     * */
    private void initView() {
        tablayout = view.findViewById(R.id.tablayout);
        viewpager = view.findViewById(R.id.viewpager);
        tablayout.setupWithViewPager(viewpager);
    }
}
