package vn.edu.poly.manager.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


import java.util.List;

import vn.edu.poly.manager.Model.FragmentModel;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    List<FragmentModel> fragmentModelList;

    public ViewPagerAdapter(FragmentManager fm, List<FragmentModel> fragmentModelList) {
        super(fm);
        this.fragmentModelList = fragmentModelList;
    }

    @Override
    public Fragment getItem(int i) {
        Log.d("item count:", fragmentModelList.size() + "");
        return fragmentModelList.get(i).getFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentModelList.get(position).getTitle().toUpperCase();
    }

    @Override
    public int getCount() {
        return fragmentModelList.size();
    }


}
