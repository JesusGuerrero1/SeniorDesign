package com.example.smartmirror.ui.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smartmirror.R;
import com.example.smartmirror.SectionsPageAdapter;
import com.example.smartmirror.data;
import com.google.android.material.tabs.TabLayout;

public class EditFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Setup 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Setup 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Setup 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.container);
        final SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        if(!data.Setup1Saved){
            data.clockEnabled = false;
            data.emailEnabled = false;
        }

        if(!data.Setup2Saved){
            data.clock2Enabled = false;
            data.email2Enabled = false;
        }

        if(!data.Setup3Saved){
            data.clock3Enabled = false;
            data.email3Enabled = false;
        }

        super.onDestroyView();
    }
}

