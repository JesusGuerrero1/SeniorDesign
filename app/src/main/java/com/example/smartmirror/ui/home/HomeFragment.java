package com.example.smartmirror.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smartmirror.R;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TabLayout tabLayout = view.findViewById(R.id.hometabs);
        tabLayout.addTab(tabLayout.newTab().setText("Setup 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Setup 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Setup 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.homecontainer);
        final HomeSectionsPageAdapter adapter = new HomeSectionsPageAdapter(getFragmentManager(), tabLayout.getTabCount());

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

}
    /*
    private String cX, cY;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setup1, container, false);



        //Sets up the relative view
        RelativeLayout mirrorView = root.findViewById(R.id.mirror_view);

        //Creates a new image clock
        ImageView clockImage = root.findViewById(R.id.clock);
        ImageView emailImage = root.findViewById(R.id.email);

        //Size of images
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        //Size of images
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,100);

        //Removes views from parent to change location
        if(clockImage.getParent() != null) {
            ((ViewGroup) clockImage.getParent()).removeView(clockImage);
            if(emailImage.getParent() != null){
                ((ViewGroup) emailImage.getParent()).removeView(emailImage);
            }
        }

        //Show Images if they are enabled
        if(data.clockEnabled){
            clockImage.setVisibility(root.VISIBLE);
            //Sets coordinates of clock image
            layoutParams.leftMargin = data.xClock;
            layoutParams.topMargin = data.yClock;

            mirrorView.addView(clockImage, layoutParams);
        }
        else{
            clockImage.setVisibility(root.INVISIBLE);
        }

        if(data.emailEnabled){
            emailImage.setVisibility(root.VISIBLE);
            //Sets coordinates of email image
            lp.leftMargin = data.xEmail;
            lp.topMargin = data.yEmail;

            mirrorView.addView(emailImage, lp);
        }
        else{
            emailImage.setVisibility(root.INVISIBLE);
        }

        //Toast.makeText(getActivity(), "Coordinates ("+ data.xClock + ", " + data.yClock + ")",Toast.LENGTH_SHORT).show();


        return root;
    }*/
