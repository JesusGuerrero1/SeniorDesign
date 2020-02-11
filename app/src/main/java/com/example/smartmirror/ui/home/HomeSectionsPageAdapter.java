package com.example.smartmirror.ui.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class HomeSectionsPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public HomeSectionsPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeSetup1Fragment tab1 = new HomeSetup1Fragment();
                return tab1;
            case 1:
                HomeSetup2Fragment tab2 = new HomeSetup2Fragment();
                return tab2;
            case 2:
                HomeSetup3Fragment tab3 = new HomeSetup3Fragment();
                return tab3;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
