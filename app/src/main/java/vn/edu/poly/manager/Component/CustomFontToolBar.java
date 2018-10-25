package vn.edu.poly.manager.Component;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomFontToolBar {
    TabLayout tabLayout;
    Activity activity;

    public CustomFontToolBar(TabLayout tabLayout, Activity activity) {
        this.tabLayout = tabLayout;
        this.activity = activity;
    }

    public void setCustomFontTab() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(
                            Typeface.createFromAsset(activity.getAssets(),
                                    "roboto_light.ttf"));
                }
            }
        }
    }
}
