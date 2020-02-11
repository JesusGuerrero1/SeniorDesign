package com.example.smartmirror;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smartmirror.ui.edit.Setup1Fragment;
import com.example.smartmirror.ui.edit.Setup2Fragment;
import com.example.smartmirror.ui.edit.Setup3Fragment;

public class SectionsPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SectionsPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Setup1Fragment tab1 = new Setup1Fragment();
                return tab1;
            case 1:
                Setup2Fragment tab2 = new Setup2Fragment();
                return tab2;
            case 2:
                Setup3Fragment tab3 = new Setup3Fragment();
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